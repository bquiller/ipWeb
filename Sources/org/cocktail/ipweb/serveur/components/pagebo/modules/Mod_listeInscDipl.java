package org.cocktail.ipweb.serveur.components.pagebo.modules;
/// Onglet permettant d'accèder à la liste des inscrits à un diplôme donné
// Un click sur un des inscrits amène à sa fiche IPWeb..


import java.net.URL;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;

import org.cocktail.fwkcktlwebapp.server.components.CktlWebComponent;
import org.cocktail.ipweb.serveur.Application;
import org.cocktail.ipweb.serveur.Session;
import org.cocktail.ipweb.serveur.components.onglets.FonctionsCtrlr;
import org.cocktail.ipweb.serveur.controlleur.DownloadFic;
import org.cocktail.ipweb.serveur.metier.scol.ScolMaquetteAp;
import org.cocktail.ipweb.serveur.metier.scol.ScolMaquetteResponsableEc;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.eoaccess.EOModelGroup;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOGenericRecord;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSData;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableDictionary;
import com.webobjects.foundation.NSNotification;
import com.webobjects.foundation.NSNotificationCenter;
import com.webobjects.foundation.NSSelector;
import com.webobjects.foundation.NSSet;



// Generated by the WOLips Templateengine Plug-in at 2 oct. 2008 15:10:30
// PO 12/02/2009 : cet onglet prend 2 rôle différent selon le choix de l'utilisateur :
// - soit la liste des inscrits à un diplome/semestre
// - soit la liste des inscrits à un EC d'un diplome/semestre donné...

public class Mod_listeInscDipl extends CktlWebComponent {
	
	private static final Integer CODE_DISPENSE = 14;
    private static String CONSULT_LOGS_IP = "CONSULT_LOGS_IP";
    private static String[] listeFonctions = new String[] {CONSULT_LOGS_IP};
    private FonctionsCtrlr ctlFonctions;
	
    private boolean consulterLogs; // Si vrai, on affiche un lien vers les logs...
    public boolean listeInscAuxEc;	// Si vrai, on s'intéresse aux inscrits à des EC (sinon inscrits au semestre)
    
	public NSArray listeInscrits, resFetch, listeInscritsEC, listeDiplPourEC;
	public EOGenericRecord lInscrit, lInscritEC;
    
    public EOGenericRecord eoDiplSelected;
    public EOGenericRecord eoEcSelected;
    
    private Double noteECs1, noteECs2;
    private int etatRN, etatEC; 
    private boolean pasDeRes;
    
    private Integer mecKey;		// le mecKey associé à l'EC que l'on vient de sélectionner...
    private String mecCode;		// le mecCode ----
    
    private NumberFormat nf1 = NumberFormat.getInstance();
    
    public Integer fspnKey, msemOrdre, anneeSuivie, msemKey;
    
    public String nomFormAccel = "formAccelNom"; 	//Nom du formulaire contenant l'accélérateur de recherche...
    public String nomFormBR = "formBR";				// Nom du formulaire contenant les boutons radio pour type de liste...
    
    public String chaineAccel = null;				// chaine en cours de saisie...
    public String chaineAccelEc = null;				// chaine en cours de saisie pour les EC...    

	private static String DECO_MANQUE = "SemIncomplet"; // rouge quand il manque des points ECTS
	private static String DECO_OK = "SemComplet"; // vert quand il y a ce qu'il faut de points ECTS
	private static String DECO_ACONF = "SemAConfirmer"; // violet quand il faut confirmer...
	private static String DECO_PASDECHOIX = "SemSansChoix"; // noir quand aucun choix à faire pour le sem !	
	   
	private static String DECO_NONSEL = "nonselected"; 
	private static String DECO_SEL = "selected"; 
	
	private NSArray coulDipl = new NSArray(new Object[] {"#fcff4d","#ff7a00","#b9ff50","#00ff54","#00ffca","#3dceff","#6d5eff","#ff60d4","#ff5a60"});
	private NSMutableDictionary dictCoulDipl;
	
	private NSArray aps;
    private ScolMaquetteAp currentAp;
    private NSMutableDictionary groupCountForAp;
	
    public Mod_listeInscDipl(WOContext context) {
        super(context);
        
        listeInscAuxEc = false;	// Par defaut : on s'interesse aux inscrits par dipl/sem...
        nf1.setMinimumFractionDigits(2);
        
        ctlFonctions = new FonctionsCtrlr(((Session)this.session()).getMesOnglets(),listeFonctions);
        
        // s'enregistrer pour les notifs (chgt d'année en cours)
        NSNotificationCenter.defaultCenter().addObserver(this,	// on doit me prévenir moi-même !
        	new NSSelector("chgtAnnee",							// via cette méthode
        	new Class [] {NSNotification.class}),				// argument obligatoire !!!
        	"chgtAnneeEnCours",									// la signature de la notif qui me plait
			(Session)this.session());							// instance de celui qui la poste !
    }


    public WOComponent fetcherListeInscrits() {
    	System.out.println("On vient de sélectionner les dipl "+ fspnKey + ", pour le semestre "+ msemOrdre);
    	
    	// On lance un fetch de la liste des inscrits au diplôme sélectionné...
    	fetcherListeInscritsDiplSemAnnee();
    	return null;
    }

    
    // On vient de choisir une EC : Il faut récupérer la liste des inscrits et l'afficher...
    public WOComponent fetcherListeInscritsEc() {
    	// On va pouvoir récupérer les inscrits à cet EC, tous diplômes confondus...
    	fetcherListeInscritsEC();
    	// On fetch aussi les AP...
    	Integer mecKey = (Integer)eoEcSelected.valueForKey("mecKey");
    	aps = ScolMaquetteAp.fetchTpsAndTdsForEc(session().defaultEditingContext(), mecKey);
    	// On reset le cache AP
    	groupCountForAp = null;
    	return null;    	
    }
    
    // Fetcher les étudiants selon les critères choisis...
    private void fetcherListeInscritsDiplSemAnnee() {
    	chaineAccel = null;
		NSDictionary binding = new NSDictionary(
				new NSArray(new Object[] {msemKey, new Integer(((Session)session()).getAnneeEnCours())}),
				new NSArray(new String[] {"msemKey", "fannKey"}));
    	EOFetchSpecification fs = EOModelGroup.defaultGroup().fetchSpecificationNamed("etudInscSem", "vEtudInscSemestreRes");
		EOFetchSpecification fetchSpec = fs.fetchSpecificationWithQualifierBindings(binding);
		
		fetchSpec.setRefreshesRefetchedObjects(true);
		
		EOEditingContext ec = ((Session)session()).defaultEditingContext();
		resFetch = ec.objectsWithFetchSpecification(fetchSpec);
		// on garde resFetch comme base pour des sous-requète sur le nom !
		
		listeInscrits = resFetch;
		
		// refresh des droits par rapport � ce nouveau diplome/ann�e...
		String diplAnnee = fspnKey +"-"+new Integer((msemOrdre.intValue()+1)/2);
		ctlFonctions.refreshDroitsFonctions(diplAnnee);
		
		// si pas possible de modifier les choix de l'�tudiant pour ce diplome (droits ScolPeda insuffisants)
		consulterLogs = ctlFonctions.getDroitsConsult(CONSULT_LOGS_IP); 
			
    }
    
   
    // Fetcher les étudiants selon les critères choisis pour les EC...
    private void fetcherListeInscritsEC() {
    	chaineAccelEc = null;
    	if (eoEcSelected != null) {
    		mecKey = (Integer)eoEcSelected.valueForKey("mecKey");
    		mecCode = (String)eoEcSelected.valueForKey("mecCode");
    		if (mecKey != null) {
    			NSDictionary binding = new NSDictionary(
    					new NSArray(new Object[] {mecKey, new Integer(((Session)session()).getAnneeEnCours()), CODE_DISPENSE}),
    					new NSArray(new String[] {"mecKey", "fannKey", "imrecDispense"}));
    			EOFetchSpecification fs = EOModelGroup.defaultGroup().fetchSpecificationNamed("fsListeInscEc", "VListeInscEc");
    			EOFetchSpecification fetchSpec = fs.fetchSpecificationWithQualifierBindings(binding);
    			fetchSpec.setRefreshesRefetchedObjects(true);
    			fetchSpec.setUsesDistinct(true);
    			EOEditingContext ec = ((Session)session()).defaultEditingContext();
    			resFetch = ec.objectsWithFetchSpecification(fetchSpec);
    			// on garde resFetch comme base pour des sous-requète sur le nom !
    			listeInscritsEC = resFetch;
    			
    			// liste des diplomes participants à cette EC...
    			
    			binding = new NSDictionary(
    					new NSArray(new Object[] {mecKey}),
    					new NSArray(new String[] {"mecKey"}));
    			fs = EOModelGroup.defaultGroup().fetchSpecificationNamed("diplPourEc", "VListeDiplPourEc");
    			fetchSpec = fs.fetchSpecificationWithQualifierBindings(binding);
    			listeDiplPourEC = ec.objectsWithFetchSpecification(fetchSpec);
    			initCouleursDiplomes();
    			
    		}
    	}
    }
    
    // Palette de couleurs selon les diplômes choisis....
    private void initCouleursDiplomes() {
		dictCoulDipl = new NSMutableDictionary();
    	if (listeDiplPourEC != null && listeDiplPourEC.count()>=2 && listeDiplPourEC.count()<=coulDipl.count()) {
    		java.util.Enumeration e = listeDiplPourEC.objectEnumerator();
    		java.util.Enumeration e2 = coulDipl.objectEnumerator();
    		while (e.hasMoreElements()) {
    			EOGenericRecord eo = (EOGenericRecord)e.nextElement();
    			String coul = (String)e2.nextElement();
    			String dipl = (String)eo.valueForKey("dipl");
    			dictCoulDipl.takeValueForKey(coul, dipl);
    		}
    	}
    }
    
    
    //  -----------------------------------------------------    
    //  ------------- Reponse aux notifications -------------
    //  -----------------------------------------------------    
    
    /// Un changement d'année vient d'avoir lieu...
    public void chgtAnnee(NSNotification laNotif) {
    	eoDiplSelected = null;
    	eoEcSelected = null;
    	chaineAccel = null;
    	chaineAccelEc = null;
    }
    

    //  --------------------------------------------------------    
    //  ---------------- Expr. conditionnelles -----------------
    //  --------------------------------------------------------    
    
// Est-ce qu'on a choisi un dipl/sem ?
    public boolean listeDisponible() {
    	return (eoDiplSelected != null);
    }
    
 // Est-ce qu'on a choisi un EC ?
    public boolean listeDisponibleEC() {
    	return (eoEcSelected != null);
    }

    // Est-ce que l'inscrit courant a une IP au semestre ?
    public boolean ipNonDisponible() {
    	String indic = (String)lInscrit.valueForKey("libDispense");
    	if (indic != null && indic.equalsIgnoreCase("NIP")) return true;
    	else return false;
    }
        
    
    //  --------------------------------------------------------       
    //  --------------------Valeurs à afficher -----------------
    //  --------------------------------------------------------    

    public String titreFieldSet() {
    	if (listeInscAuxEc) return "aux EC d'une formation";
    	else return "à une formation";
    }
        
    public String nbInscrits() {
    	if (listeInscrits == null || listeInscrits.count() == 0)
    		return "Pas d'";
    	else return "Il y a "+ listeInscrits.count();
    }

    public String mailToInscrits() {
    	String mailTo = "mailTo:";
    	if (listeInscrits != null && listeInscrits.count() > 0) {
    		NSArray emails = (NSArray) listeInscrits.valueForKey("email");
    		String emailsStr = emails.componentsJoinedByString(";");
    		mailTo = mailTo + emailsStr;
    	}
    	return mailTo;
    }
    
    public String mailToInscritsEc() {
    	String mailTo = "mailTo:";
    	if (listeInscritsEC != null && listeInscritsEC.count() > 0) {
    		NSArray emails = (NSArray) listeInscritsEC.valueForKey("mailComplet");
    		String emailsStr = emails.componentsJoinedByString(";");
    		mailTo = mailTo + emailsStr;
    	}
    	return mailTo;
    }
    
    public boolean pasDIncrits() {
    	return (listeInscritsEC.count() == 0);
    }
    
    
    public String nbInscritsEc() {
    	if (listeInscritsEC == null || listeInscritsEC.count() == 0)
    		return "Pas d'";
    	else return "Il y a "+ listeInscritsEC.count();
    }
    
    public String getMasqueAccel() {
    	if (chaineAccel != null && chaineAccel.length() > 0) {
    		return (" dont le nom commence par '"+chaineAccel+"'");
    	}
    	else return null;
    }
    
    public String getMasqueAccelEc() {
    	if (chaineAccelEc != null && chaineAccelEc.length() > 0) {
    		return (", et dont le nom commence par '"+chaineAccelEc.toUpperCase()+"'");
    	}
    	else return null;
    }
        
    public String getLibDispense() {
    	String indic = (String)lInscrit.valueForKey("libDispense");
    	if (indic != null && indic.equalsIgnoreCase("NIP")) return "";
    	else return indic;
    }
    
    public String getRedoublant() {
    	String indic = (String)lInscrit.valueForKey("idiplRedoublant");
    	if (indic != null && indic.equalsIgnoreCase("O")) return "R";
    	else return " ";
    }
    
    public String getTypeInsc() {
    	int ti = ((Integer)lInscrit.valueForKey("idiplTypeInscription")).intValue();
    	if (ti <= 1) return "P";
    	if (ti ==4) return "E";
    	return "c";
    }
    
    
    // Retourne un submit du formulaire en cours, à la sélection du popUp...
    public String fctSubmitChoix() {
        return "document."+nomFormAccel+".submit();";
    }
    
 // Retourne un submit du formulaire en cours, à la sélection du bouton Radio ...
    public String fctSubmitChoixBR() {
        return "document."+nomFormBR+".submit();";
    }        
    
    public boolean possibiliteLogs() {
    	int etatIp = ((Integer)lInscrit.valueForKey("etatIp")).intValue();
    	return (etatIp>=2 && consulterLogs);
    }
    
    public String libEtatIp() {
    	int etatIp = ((Integer)lInscrit.valueForKey("etatIp")).intValue();
    	switch(etatIp) {
    	case 4: return "Validé";
    	case 3: return "Complet";
    	case 2: return "Incomplet";
    	case 0: return "Disp.";
    	}
    	return "-";
    }
    
//  --------------------------------
//  ---- POUR LES LISTES PAR EC ----    
//  --------------------------------

    public String integrationChoix() {
    	String integ = (String)lInscritEC.valueForKey("choixIntegre");
    	if (integ != null && integ.equalsIgnoreCase("O")) {
    		calculsResEC();
    		return "ok";
    	}
    	pasDeRes = true;
        return "En Attente";
    }
    
    private void calculsResEC() {
    	pasDeRes = false;
    	etatRN = ((Integer)lInscritEC.valueForKey("etatRN")).intValue();
    	etatEC = ((Integer)lInscritEC.valueForKey("imrecEtat")).intValue();
    	noteECs1 = (Double)lInscritEC.valueForKey("imrecSession1");
    	noteECs2 = (Double)lInscritEC.valueForKey("imrecSession2");
    }
    
 //   private Double noteECs1, noteECs2;
 //   private Integer etatRN, etatEC; 

    // Calcul du résultat...
    public String resultatEC() {
    	if (etatRN == 0) return "NC";
    	else if (etatRN == 1) {
    		if (etatEC == 1) {
    			if (noteECs1 != null && noteECs1.doubleValue() < 10.0)
    				return "comp. session 1";
    			return "validé session 1";
    		}
    		else if (etatEC == 0) {
    			if (noteECs1 != null && noteECs1.doubleValue() >= 10.0) {
    				etatEC = 1;
    				return "validé session 1";
    			}
    			return "aj.";
    		}
    	}
    	else if (etatRN==2) {
    		if (etatEC == 2) {
    			if (noteECs2 != null && noteECs2.doubleValue() < 10.0)
    				return "comp. session 2";
    			return "validé session 2";
    		}
    		else if (etatEC == 1) {
    			if (noteECs1 != null && noteECs1.doubleValue() < 10.0)
    				return "comp. session 1";
    			return "validé session 1";
    		}
    		else if (etatEC == 0) {
    			if (noteECs1 != null && noteECs1.doubleValue() >= 10.0) {
    				etatEC = 1;
    				return "validé session 1";
    			}
    			else if (noteECs2 != null && noteECs2.doubleValue() >= 10.0) {
       				etatEC = 2;
       			 	return "validé session 2";
    			}
    			return "aj.";
    		}

    	}
    	return null;
    }
    
    public String noteECSession1() {
    	if (etatRN != 0 && noteECs1 != null) {
    		return nf1.format(noteECs1.doubleValue());
    	}
    	else return "NC";	
    }

    public String noteECSession2() {
    	if (etatRN == 2) {
    		if (noteECs2 != null) return nf1.format(noteECs2.doubleValue());
    		else return null;
    	}
    	else {
    		if (etatEC == 1)
    			return null;
    		else return "NC";
    	}
    }    
    
    // Couleur de fond de la case diplôme, selon le diplome qu'elle contient...
    public String bgcolorDipl() {
    	// données du diplome...
    	String res = null;
    	String cleCoulDipl = (((Integer)lInscritEC.valueForKey("fspnKey")).toString()+"-"+((Integer)lInscritEC.valueForKey("idiplAnneeSuivie"))).toString();
    	if (dictCoulDipl != null && dictCoulDipl.count() > 0) {
    		res = (String)dictCoulDipl.objectForKey(cleCoulDipl);
    	}
    	return res;	
    }
    
    public NSArray apsForEc() {
    	return aps;
    }

    public ScolMaquetteAp getCurrentAp() {
		return currentAp;
	}
    
    public void setCurrentAp(ScolMaquetteAp currentAp) {
		this.currentAp = currentAp;
	}
    
    public boolean unSeulGroupeForCurrentAp() {
    	if (groupCountForAp == null) {
    		groupCountForAp = new NSMutableDictionary();
    	}
    	Integer groupCount = (Integer)groupCountForAp.objectForKey(currentAp.mapKey());
    	if (groupCount == null) {
    		groupCount = ScolMaquetteAp.countInscForAp(session().defaultEditingContext(), (Integer)eoEcSelected.valueForKey("mecKey"), currentAp.mapKey());
    		groupCountForAp.setObjectForKey(groupCount, currentAp.mapKey());
    	}
    	return (currentAp.isTD() && groupCount <= 30) || (currentAp.isTP() && groupCount <= 50);
    }
    
    public String titleForExtractionPdf() {
    	return "Extraire les feuilles de présence au " + currentAp.mhcoCode() + " de cet EC...";
    }
    
    //  --------------------------------------------------------    
    //  ---------------- Valeurs en E/S      -------------------
    //  --------------------------------------------------------    

    // récupérer la valeur d'une case à cocher...
    public String getBrSelected()
    {
    	if (listeInscAuxEc) return "1";
    	else return "0";
    }
    
    // modifier la valeur d'une case à cocher ...
    public void setBrSelected(Integer newBrSelected)
    {
    	if (newBrSelected.intValue() == 0) listeInscAuxEc = false;
    	else listeInscAuxEc = true;
    }

	public String styleRB1() {
		if (listeInscAuxEc) return DECO_NONSEL;
		else return DECO_SEL;
	}

	public String styleRB2() {
		if (listeInscAuxEc) return DECO_SEL;
		else return DECO_NONSEL;
	}

    //  --------------------------------------------------------       
    //  --------------------  CSS à renvoyer   -----------------
    //  --------------------------------------------------------    

    public String styleEtatIp() {
    	int etatIp = ((Integer)lInscrit.valueForKey("etatIp")).intValue();
    	switch(etatIp) {
    	case 4: return DECO_OK;
    	case 3: return DECO_ACONF;
    	case 2: return DECO_MANQUE;
    	}
    	return DECO_PASDECHOIX;
    }
    
    //  --------------------------------------------------------       
    //  --------------------Action à exécuter  -----------------
    //  --------------------------------------------------------    

    public WOComponent detaillerEtudiant() {
    	lancerNotifs(true);
    	return null;
    }

    private void lancerNotifs(boolean detaillerIp) {
		// Demande au ctrleur de menu d'afficher l'onglet qui va bien...
		((Session)session()).getMenuCtrlr().selectionneMenu(((Application)application()).OM_IP_ETUD);

		// Envoyer une notification demandant au gestionnaire d'onglet de passer sur l'onglet "détail des IP d'1 étudiant"
    	// et au détail de choisir un étudiant ET un diplôme ET un semestre...
    	
    	// avec en paramêtre à transmettre l'EO de l'étudiant choisi...
    	NSDictionary leDico;
    	if (listeInscAuxEc) leDico = new NSDictionary(new Object[] {lInscritEC, new Boolean(detaillerIp)},new Object[] {"etudiant","detaillerIp"});
    	else leDico = new NSDictionary(new Object[] {lInscrit, new Boolean(detaillerIp)},new Object[] {"etudiant","detaillerIp"});
    	
    	((Session)session()).dderDetailIPEtudiant(leDico);
    }
    
    
    public WOComponent sousRechSurDebutNom() {
    	// restriction de la recherche précédente en utilisant le texte pour le début du nom de l'étudiant...
    	
    	String chaine;
    	
    	if (chaineAccel == null || chaineAccel.length() == 0) chaine = "*";
    	else chaine = chaineAccel + "*";
    	
    	EOQualifier qualifier = EOQualifier.qualifierWithQualifierFormat("adrNom caseInsensitiveLike %s", 
    			new NSArray(new Object[] {chaine}));    	
		listeInscrits = EOQualifier.filteredArrayWithQualifier(resFetch, qualifier);;
    	return null;
    }

    public WOComponent sousRechEcSurDebutNom() {
    	// restriction de la recherche précédente en utilisant le texte pour le début du nom de l'étudiant...
    	
    	String chaine;
    	
    	if (chaineAccelEc == null || chaineAccelEc.length() == 0) chaine = "*";
    	else chaine = chaineAccelEc + "*";
    	
    	EOQualifier qualifier = EOQualifier.qualifierWithQualifierFormat("adrNom caseInsensitiveLike %s", 
    			new NSArray(new Object[] {chaine}));    	
		listeInscritsEC = EOQualifier.filteredArrayWithQualifier(resFetch, qualifier);;
    	return null;
    }
    
    public WOComponent voirLogEtud() {
    	lancerNotifs(false);
    	return null;
    }
    
    public WOComponent raffraichir() {
    	fetcherListeInscritsDiplSemAnnee();
    	return null;
    }
    
    // Nouveaux inscrits ?
    public WOComponent raffraichirListeEC() {
    	fetcherListeInscritsEC();
    	return null;
    }
    
    // Lancer une extraction Excel des listes d'inscrits à l'EC en cours...
    // Impression d'une liste : Appel d'une page traffiquée pour renvoyer le fichier, 
    // et non le contenu HTML d'une nouvelle page !
    public DownloadFic extractionFeuillePresenceEC()
    {
    	DownloadFic nextPage;
    	nextPage = (DownloadFic)(pageWithName("DownloadFic"));
    	// On passe le photo controleur...
    	boolean res = nextPage.initDownloadPDF(
    			extraitFeuillePresenceEC(), "Feuille_Presence_" + mecCode + "_" + currentAp.mhcoCode() + ".pdf");
    	if (!res) return null;
    	else return nextPage;
    }
    
    private NSData extraitFeuillePresenceEC() {
	    HashMap parametres = new HashMap();
	    parametres.put("MAPKEY", currentAp.mapKey());
	    parametres.put("MECKEY", eoEcSelected.valueForKey("mecKey"));
	    parametres.put("SEMESTRE", msemOrdre);
	    parametres.put("ANNEE", ((Session)session()).getAnneeEnCours());
	    parametres.put("MECCODE", eoEcSelected.valueForKey("mecCode"));
	    parametres.put("EC", eoEcSelected.valueForKey("mecLibelle"));
	    // On va chercher le type d'AP et le nom du prof si dispo
	    NSSet enseignants = new NSSet((NSArray)currentAp.scolMaquetteChargesAp().valueForKey("individu"));
	    parametres.put("NOM_ENS", formatNomsIndividus(enseignants));
	    parametres.put("TYPE_AP",currentAp.mhcoCode());
	    // PS : ne marche pas en dev
	    URL logoPath = application().resourceManager().pathURLForResourceNamed("images/logo.png", null, null);
	    parametres.put("LOGO_PATH", logoPath != null ? logoPath.getFile() : null);
	    return ((Session)session()).imprimePDF("feuillePresenceEc.jasper", parametres);
    }
    
    private String formatNomsIndividus(Iterable individus) {
    	String nomAffichage = "";
	    Iterator<EOGenericRecord> iterEns = individus.iterator();
	    while (iterEns.hasNext()) {
	    	EOGenericRecord enseignant = iterEns.next();
		    String ensPrenom = (String) ((EOGenericRecord)enseignant).valueForKey("prenom");
		    String ensNom = (String) ((EOGenericRecord)enseignant).valueForKey("nomUsuel");
		    if (ensNom == null && ensPrenom == null) {
		    	nomAffichage += "NC";
		    } else {
		    	nomAffichage += ensNom + " " + ensPrenom;
		    }
		    if (iterEns.hasNext()) {
		    	nomAffichage += " / ";
		    }
	    }
	    return nomAffichage;
    }
    
    public DownloadFic extractionListeExcel()
    {
    	return imprimerDocXLS(extraitListeInscrits(),"Liste_Inscrits_"+mecCode+".xls");
    }
    
    public DownloadFic extractionFeuilleNotesExcel()
    {
    	return imprimerDocXLS(extraitFeuilleNotesInscrits(),"Feuille_Notes_Inscrits_"+mecCode+".xls");
    }
    
    private DownloadFic imprimerDocXLS(NSData donneesRapport,String titreRapport) {
    	DownloadFic nextPage;
    	nextPage = (DownloadFic)(pageWithName("DownloadFic"));
    	// On passe le photo controleur...
    	boolean res = nextPage.initDownloadXLS(donneesRapport,titreRapport);
    	if (!res) return null;
    	else return nextPage; 
    }   
    
	// reponse au clic sur l'icone "Extraire la liste Excel des inscrits à l'EC"
    private NSData extraitListeInscrits() {
	// 
	    HashMap parametres = new HashMap();
	    parametres.put("MECKEY", mecKey);
	    return ((Session)session()).imprimeXLS("Liste_Inscrits_EC_Bourses.jasper", parametres);
	}
    
    private NSData extraitFeuilleNotesInscrits() {
    	// 
    	HashMap parametres = new HashMap();
    	parametres.put("MECKEY", mecKey);
	    parametres.put("ANNEE", ((Session)session()).getAnneeEnCours());
	    parametres.put("EC", eoEcSelected.valueForKey("mecLibelle"));
	    parametres.put("MECCODE", eoEcSelected.valueForKey("mecCode"));
	    NSArray responsables = ScolMaquetteResponsableEc.fetchResponsablesForEc(session().defaultEditingContext(), mecKey);
	    parametres.put("NOM_RESPONSABLE", formatNomsIndividus(responsables));
	    // PS : ne marche pas en dev
	    URL logoPath = application().resourceManager().pathURLForResourceNamed("images/logo.png", null, null);
	    parametres.put("LOGO_PATH", logoPath != null ? logoPath.getFile() : null);
	    return ((Session)session()).imprimeXLS("feuilleNotesInscritsEC.jasper", parametres);
    }
}