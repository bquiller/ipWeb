package org.cocktail.ipweb.serveur.controlleur;
/*
 * Cré le 30 sept. 2006 - grosses modif juillet 2007 (gestion des parcours - 1 ctrlr par MSEM_ORDRE)
 *
 * Objectif : gérer le niveau inscription à un parcours/semestre pour un "diplome/année suivie" donné...
 * ----------
 * Cet objet recupère les rows de la vue SCOL_INS_PARCOURS_SEMESTRE
 * pour un idiplN°, fspnKey, MSEM_ORDRE  (une "inscription à un semestre et un parcours")
 *  
 * selon les formations on récupère 1 ou 2 rows pour le semestre concerné...
 * Il faut donc gérer les formations avec parcours :
 *  - etre en mesure d'indiquer pour un MSEM_KEY donné s'il sagit d'un semestre avec parcours ou non...
 *  - publier une seule ligne par semestre (celle du parcours spécialisé si existe, sinon parcours commun)	
 *    - pour les formations sans parcours, ça ne change rien
 *    - pour les formations avec parcours, on doit pouvoir renvoyer le MSEM_KEY commun à la demande
 *    	selon le MSEM_KEY du semestre "spécialisé"
 */

/**
 * @author olive
 *
 */
import org.cocktail.ipweb.serveur.Session;

import com.webobjects.foundation.*;
import com.webobjects.eocontrol.*;

public class InscFormationCtrlr {

	private static String DECO_PASDECHOIX = "SemSansChoix"; // noir quand aucun choix à faire pour le sem !	
	private static String DECO_MANQUE = "SemIncomplet"; // rouge quand il manque des points ECTS
	private static String DECO_HORSIP = "SemHorsIp"; // quand le diplome ne donne pas lieu a choix ou hors période IP !
	private static String DECO_OK = "SemComplet"; // vert quand il y a ce qu'il faut de points ECTS
	private static String DECO_ACONF = "SemAConfirmer"; // violet quand il faut confirmer...

	protected Session maSession;
	private InscDiplAnneeCtrlr inscEtudCt;
	private IpsemstatCtlr ipSemStatCtlr;

	private Integer noSemestre;		// N° de semestre que ce ctrl gère...
	private boolean inscSemValide;		// Vrai si le fetch retourne des insc pour ce n° de sem.	

	private boolean existeInsParCommun, existeInsParSpe;  

	private Integer mrsemKeyPS;			// ref. du mrsemKey du parcours spécialisé choisi, s'il existe...
	private boolean inscAparcours;		// VRAI si cette inscription (dipl.sem) possède des choix de parcours
	private boolean inscPsIncomplete;	// VRAI si le diplome propose un choix de parcours et qu'aucun n'est choisi (faux sinon) 
	private boolean semestreComplet;	// Vrai si ce semestre est complet.. (plus d'UE avec des choix à faire)

	private boolean chgtDeParcoursPossible;	// vrai si plus d'un parcours et pas de dispenses au parcours courant...
	private boolean cdtChoixParcoursCalculee;
	private boolean choixParcoursPossible;

	private Integer msemKeyPP, msemKeyPC;	// Parcours principal et parcours commun (peuvent être identiques)
	private int visibiliteRN, imrsemEtat;
	
	private String libelleParcoursEnCours;

	private NSTimestamp dateDebutIP,dateFinIP;	// dates pour la session (si IP Web possibles)
	private NSTimestamp dateBDDebutIP,dateBDFinIP;	// dates NON CONVERTIES pour la session (si IP Web possibles)

	private boolean autoriseIpRedoublant; // si VRAI, alors de dipl/sem permet aux redoublants de faire leurs IP Web... 
	private boolean redoublantBloque; // si VRAI, alors on doit bloquer l'IP pour ce dipl/sem (redoublants pas encore autorisés)
	private boolean modifPossible;	// vrai si on la formation/semestre permet de faire des IP Web 
	private boolean semestreIntegre; // vrai si l'intégraion a déjà été faites pour ce semestre...
	private boolean semestreDejaObtenu;	// vrai s'il s'agit d'un redoublant ayant eu ce semestre (pas de modifs possibles alors)
	private boolean ipPasEncoreOuvertes;	// vrai si les IP pour ce semestre ne sont pas encore ouvertes (ou même prévues)
	private boolean ipTerminees;			// vrai si la période des IP est passée...
	//	ET que l'on est encore dans les dates pour faire ses IP sur ce semestre...
	private boolean semIpWeb;		// vrai si cette formation/semestre permet de faire des IP Web !
	private boolean urgenceSignalee;	// vrai s'il ne reste que 3 jours pour s'inscrire !

	private EOGenericRecord inscSemestresParcPrin;	// lien vers l'EO de ScolInsParcoursSemestre
	private InscSemestreCtrlr leInscSemCtrlr;	// lien vers le ctrlr de la maquette du semestre concerné

	private NSArray parcoursSpecialise;		// Une array pour les <> parcours ...
	private EOGenericRecord parcoursSelected;	// Le parcours effectivement sélectionné dans cette Array...

//	private NSDictionary parcoursCommunAssocie;	// pour correspondance parcours spécialisé/commun

	private EOFetchSpecification fetchSpecParcours;	// pour pouvoir la raffraichir au besoin !!!



	/** La création de cet objet se fait depuis InscriptionCtrlr (via InscDiplAnneeCtrlr), quand on charge/change d'étudiant
	 * 	il faut a présent charger la partie parcours/semestres associée a un dipl/annee
	 * 	pour les semestres 1,3 et 5, générer le parcours commun s'il n'existe pas !!!
	 * 
	 * PO le 22/09/2008 : Pour les candidats en préInscription (pas encore inscrits administrativement) on force la génération 
	 * 					  d'une insc° au parcours commun (par définition, il n'est pas encore connu de ScolPedagogie, donc pas ds les parcours)
	 */
	public InscFormationCtrlr(Session sess,InscDiplAnneeCtrlr inscCtPere,int noSemestreI, boolean preInscriptionEnCours) {
		super();
		System.out.println("Constructeur de BASE / sem " + noSemestreI);
		maSession = sess;
		inscEtudCt = inscCtPere;	// 
		noSemestre = new Integer(noSemestreI);
		inscriptionsSemestre(preInscriptionEnCours);
	}
			
	public int getVisibiliteRN() {
		return visibiliteRN;
	}
	
	public int getImrsemEtat() {
		return imrsemEtat;
	}
	
	public String libelleInscription() {
		return inscEtudCt.libelleInscription();
	}

	public String diplome() {
		return inscEtudCt.diplome();
	}

	public String diplomeLl() {
		return inscEtudCt.diplomeLl();
	}

	public String anneeDiplome() {
		return inscEtudCt.anneeDiplome();
	}

	public Integer anneeUniv() {
		return inscEtudCt.anneeUniv();
	}

	public Integer fspnKey() {
		return inscEtudCt.fspnKey();
	}

	public String prenomNomEtud() {
		return inscEtudCt.prenomNomEtud();
	}

	public String genreEtud()  {
		return inscEtudCt.genreEtud();
	}

	public Integer idiplNumero() {
		return inscEtudCt.idiplNumero();
	}
	
	public Integer etudNumero() {
		return inscEtudCt.etudNumero();
	}

	// Renseigne pour savoir si au final il y a des insc pour ce n° de sem.	
	public boolean valide() {
		return inscSemValide;
	}

	public boolean modifPossible() {
		return modifPossible;
	}

	public boolean semestreIntegre() {
		return semestreIntegre;
	}
	
	// faire gaffe a l'usage externe !!!
	public void setModifPossible(boolean etat) {
		modifPossible = etat;
	}

	public boolean semestreDejaObtenu() {
		return semestreDejaObtenu;
	}
	public boolean semIpWeb() {
		return semIpWeb;
	}
	public boolean ipPasEncoreOuvertes() {
		return ipPasEncoreOuvertes;
	}
	
	public boolean ipTerminees() {
		return ipTerminees;
	}

	public boolean redoublantBloque() {
		return redoublantBloque;
	}
	
	public boolean urgenceSignalee() {
		return urgenceSignalee;
	}

	public NSTimestamp dateDebutIP() {
		return dateDebutIP;
	}

	public NSTimestamp dateFinIP() {
		return dateFinIP;
	}

	public NSTimestamp dateBDDebutIP() {
		return dateBDDebutIP;
	}

	public NSTimestamp dateBDFinIP() {
		return dateBDFinIP;
	}

	// Gestion des insc° au semestre
	// PO : on ajoute le cas de la Pré-inscription (force l'IP au parcours commun associé au semestre)
	private void inscriptionsSemestre( boolean preInscriptionEnCours) {

		// 0) savoir si ce diplome/semestre donne lieu a modifs possibles ou pas 
		//	et charger la liste des parcours specialise qui y sont rattaches...
		verifDatesDiplSem();
		initParcoursSpecialises();

		// 1) charger les insce existantes
		chargerInscSemestre(true);

		// 2) verifier qu'il n'y a pas d'inscriptions aux parcours communs qui manqueraient 
		// - pas de parc. commun, modifs possible...
		// - et soit semestre impair, soit il existe une insc° a un parcours spécialisé... (cas rare !), soit on force la préinscription
		// 
		if (!existeInsParCommun && modifPossible && ((noSemestre.intValue()%2)==1 || existeInsParSpe || preInscriptionEnCours)) {
			genererInscParcCommun(noSemestre);
			// et recharger comme en 1..
			chargerInscSemestre(true);
		}

		// Si on a bien à présent une insc° principale de généré...
		if (inscSemValide) {

			// 3)	Vérifier si le semestre chargé présente une dispense...
			reinitParcoursSelected();
			// Vérifier si le nouveau semestre/parcours chargé présente une dispense...
			if (existeInsParSpe) verifDispenseSemestre();    

			// 4) si insc sem valide, Generer des ctrlr de semestres (ceux-ci seront complets ou non, selon les dates et le statut des IP)
			if (modifPossible) {
				boolean chargerMaquetteSemestre = loadIpSemStat();
				if (valide() && chargerMaquetteSemestre)
						creationInscSemCtrlr();
			}

			// 5) Vérifier si IP_SEM_STAT est bien à jour (s'il existe), sinon faire les MAJ qui s'imposent et les sauver...
			verifIpSemStat();
		}

	}

	// L'instanciaton minimale : doit permettre à ce contrôleur de répondre aux questions suivantes
	// 1) mon semestre est-il en phase d'IP ? (date du jour comprise dans la période associée au semestre)
	// 2) ces ip sont-elles complètes ? SI LES STATS NE PERMETTENT PAS DE REPONDRE LANCER CHGT COMPLET !
	private boolean loadIpSemStat() {
		// ATTENTION : ne faire que les inits qui ne peuvent pas varier en cas de CHGT DE PARCOURS !!!!
		ipSemStatCtlr = new IpsemstatCtlr(idiplNumero(),msemKeyPC(), anneeUniv(), maSession);
		ipSemStatCtlr.verifSync(inscPsIncomplete, mrsemKeyPS); // faire une synchro à tout hasard...

		if (!ipSemStatCtlr.aVide() && !ipSemStatCtlr.inscPsIncomplete()) {
			if (ipSemStatCtlr.nbUeIncompletes() != null 
					&& (ipSemStatCtlr.nbUeIncompletes()).intValue() == 0) 
				semestreComplet = true;
		}
		// décider si l'on doit continuer les inits ou si l'on arrête là...
		// (cas ou le semestre fait partie des sem. à IP Web, qu'on est dans les dates d'IP et qu'aucune stat n'est disponible) 
		boolean continuerInits = false;
		if (modifPossible() && ipSemStatCtlr.aVide())
			continuerInits = true;

		return continuerInits;
	}

	// méthode relai...
	public boolean choixValides() {
		// si on est endehors des clous : 
		// BRICE : voir pquoi ipSemStatCtlr est null
		if (ipSemStatCtlr == null) {
			loadIpSemStat();
			System.out.println("loadIpSemStat ... " + ipSemStatCtlr);
		}
		if (modifPossible && ipSemStatCtlr != null)
			return ipSemStatCtlr.choixSemestreValides();
		else return true;
	}
	

	// fetcher avec fspnKey, année en cours et idiplNumero : les semestres concernés (pair et impair)
	// on récupère en fait 2 lignes par semestre pour les étudiants inscrits à des semestres avec parcours
	// et une seule ligne pour les semestres sans parcours spécialisés...
	private void chargerInscSemestre(boolean refetchDde) {
		NSArray inscSemestres;
		existeInsParCommun = false;
		existeInsParSpe = false;
		semestreComplet = false;
		cdtChoixParcoursCalculee = false;	// réinit cache de calcul indiquant si choix de parcours possible !

		Integer semKeyPP = null, semKeyPC = null;
		msemKeyPP = null; msemKeyPC = null;
		mrsemKeyPS = null;

		inscSemestresParcPrin = null;

		NSArray bindings = new NSArray(new Object[] {fspnKey(), noSemestre, idiplNumero()});

		EOQualifier qualifier = EOQualifier.qualifierWithQualifierFormat(
				"fspnKey = %@ and msemOrdre = %@ and idiplNumero = %@", bindings);

//		EOSortOrdering semOrdre = EOSortOrdering.sortOrderingWithKey("msemOrdre",
//		EOSortOrdering.CompareAscending);

		EOSortOrdering mparAbr = EOSortOrdering.sortOrderingWithKey("mparAbreviation",
				EOSortOrdering.CompareDescending);
		// 	Le sort assure que l'on aura d'abord le parcours spécialisé s'il existe puis le commun... 

		NSArray sortOrderings = new NSArray(new Object[] {mparAbr});
//		NSArray sortOrderings = new NSArray(new Object[] {semOrdre, mparAbr});

		fetchSpecParcours = new EOFetchSpecification("ScolInsParcoursSemestre",
				qualifier, sortOrderings);
		fetchSpecParcours.setRefreshesRefetchedObjects(refetchDde);	// fait en sorte de refetcher des EOS déjà fetchés si besoin

		EOEditingContext ec = maSession.defaultEditingContext();

		inscSemestres = ec.objectsWithFetchSpecification(fetchSpecParcours);
		// il est possible que le fetch ne donne rien (ex. des CAPA pour qui il n'y a pas d'IP aux semestres de faites !)

		// si des formations ont bien été récupérées...
		if (inscSemestres != null && inscSemestres.count()>0) {
			boolean debutBoucle = true;
			
			int visibRN = 0;
			int mrsEtat = 0;
			
			// parcours des formations pour extraire le parcours principal 
			// (parc. commun si pas de spécialisation, sinon parc. spécialisé)
			java.util.Enumeration enumerator = inscSemestres.objectEnumerator();
			while (enumerator.hasMoreElements()) {
				EOGenericRecord insc = (EOGenericRecord)enumerator.nextElement();

				if (visibRN < ((Integer)insc.valueForKey("affRn")).intValue())
					visibRN = ((Integer)insc.valueForKey("affRn")).intValue();
				
				if (mrsEtat < ((Integer)insc.valueForKey("imrsemEtat")).intValue())
					mrsEtat = ((Integer)insc.valueForKey("imrsemEtat")).intValue();
				
				
				// le premier enreg. fetché est le parcours principal (sauf erreur)
				if (debutBoucle) {
					debutBoucle = false;
					inscSemestresParcPrin = insc;
					System.out.println("set inscSemestresParcPrin : " + insc);
				}
				// savoir si l'étudiant est inscrit à un parcours specialisé ou non ...
				if (insc.valueForKey("mparAbreviation") != null) {	// Parcours commun
					semKeyPC = ((Integer)insc.valueForKey("msemKey"));
					existeInsParCommun = true;
				}
				else {	//  parcours specialisé
					semKeyPP = ((Integer)insc.valueForKey("msemKey"));
					existeInsParSpe = true;
				}
			}
			
			// Doit-on bloquer l'IP d'un redoublant ???
			boolean redoublant = "O".equalsIgnoreCase((String)inscSemestresParcPrin.valueForKey("idiplRedoublant"));
			if (!autoriseIpRedoublant && redoublant) {
				redoublantBloque = true;
				modifPossible = false;
			}
			else redoublantBloque = false;
			
			// Tester si le semestre n'aurait pas été déja validé (code dispense = 4)
			verifDispenseSemestre();
			if (semestreDejaObtenu) {
				modifPossible = false;
				redoublantBloque = false;
			}
			
			visibiliteRN = visibRN;
			imrsemEtat = mrsEtat;
			
			if (existeInsParCommun) {
				inscSemValide = true;
				msemKeyPC = semKeyPC;
				if (existeInsParSpe)
					msemKeyPP = semKeyPP;
				else msemKeyPP = semKeyPC;
			} 
			// BRICE
			else { 
				msemKeyPP = semKeyPP;
				msemKeyPC = 0; // zero obligatoirement !!!
				System.out.println("semKeyPP " + semKeyPP +  " semKeyPC " + semKeyPC +  " inscSemestresParcPrin " + inscSemestresParcPrin);
			}
			
			if (existeInsParSpe) {
				libelleParcoursEnCours = filtrerLibParcours((String)inscSemestresParcPrin.valueForKey("mparLibelle"));
			}
			
			if (existeInsParCommun || existeInsParSpe) 
				mrsemKeyPS = (Integer)inscSemestresParcPrin.valueForKey("mrsemKey");
			
			else libelleParcoursEnCours = "";
		}
		else {
			// Il n'y a aucune inscription pour cet insc/dipl/semestre...
			// ce controleur est invalide !
			inscSemValide = false;
		}

	}

	// Filtrage de l'intitulé du parcours : si celui-ci contient le mot "parcours" au début, l'enlever
	private String filtrerLibParcours(String libParcours) {
		String res = libParcours;
		if (libParcours != null && libParcours.length() > 0)
		{
			String prefixe = "parcours ";
			// vérifier que le libellé ne comporte pas déjà le mot "parcours" en en-tête :
			if (libParcours.toLowerCase().startsWith(prefixe))
				res = libParcours.substring(prefixe.length(), libParcours.length());
		}
		return res;
	}



	// Cas particulier : dispense pour un semestre avec parcours ???
	// TODO : à creuser un peu plus !!!!!!!!
	private void verifDispenseSemestre() {
		semestreDejaObtenu = false;

		Integer imrsemDispense = (Integer)inscSemestresParcPrin.valueForKey("imrsemDispense");
		int dispense = 0;
		if (imrsemDispense != null) dispense = imrsemDispense.intValue();

		if (dispense ==4 || dispense == 14) // le semestre en question a déjà été obtenu
			semestreDejaObtenu = true;


	}


	private void verifDatesDiplSem(){

		// la modif des choix d'IP est possible ssi :
		//	- les dates ne sont pas passées 
		//		(!!! ATTENTION !!! date de fin forcée à 23h59 pour inclure toute la journée !!!)
		//	- le semestre en question n'a pas été obtenu (redoublant)
		//  - il n'y a pas de blocage en cours des IP des redoublants par la scol (pour report de notes...)

		
		modifPossible = false;
		semIpWeb = false;
		ipPasEncoreOuvertes = false;
		ipTerminees = false;
		autoriseIpRedoublant = true;

		dateDebutIP = null;
		dateBDDebutIP = null;

		Integer fspnKey = fspnKey();
		Integer msemOrdre = noSemestre;
		
		// vérifier si ce semestre a déjà été intégré...
		semestreIntegre = maSession.monApp.semestreIntegre(fspnKey,msemOrdre, new Integer(maSession.getAnneeEnCours()));		

		// vérifier que ce semestre figure dans ceux qui sont editable via IP Web...
		if (maSession.monApp.semestreEditable(fspnKey,msemOrdre, new Integer(maSession.getAnneeEnCours()))) {

			NSTimestamp[] dates = maSession.chercherDatesDiplSem(fspnKey,msemOrdre);
			autoriseIpRedoublant = maSession.autoriseIpRedoublant(fspnKey,msemOrdre);

			if (dates!=null && dates.length>=2 && dates[0] != null && dates[1] != null) {
				semIpWeb = true;
				urgenceSignalee = false;

				NSTimestamp now = new NSTimestamp();

				now = (new NSTimestamp());

				if (now.after(dates[0]) && now.before(dates[1])) {
					modifPossible = true;
					// quel est le degré d'urgence ? 3 jours avant indiquer par un msg que ça urge !
					NSTimestamp nowPlus3 = (new NSTimestamp()).timestampByAddingGregorianUnits(0, 0, 3, 0, 0, 0);
					if (nowPlus3.after(dates[1])) 
						urgenceSignalee = true;
				}
				else if (now.before(dates[0])) ipPasEncoreOuvertes = true;
				else ipTerminees = true;

				dateDebutIP = dates[0];
				dateFinIP = dates[1];
				
				dateBDDebutIP = dates[2];
				dateBDFinIP = dates[3];
				
			}
			else {
				if (dates != null) {
					semIpWeb = true;
					ipPasEncoreOuvertes = true;
				}
			}
		}
	}

	// Si un parcours spécialisé existe ou peut être chosi :
	// Vérifier si IP_SEM_STAT est bien à jour (s'il existe), sinon faire les MAJ qui s'imposent et les sauver...
	public void verifIpSemStat() {
		// et si les modifs sont possibles !!!
		// BRICE : voir pquoi ipSemStatCtlr est null
		if (ipSemStatCtlr == null) {
			loadIpSemStat();
			System.out.println("loadIpSemStat ... " + ipSemStatCtlr);
		}
		if (modifPossible && ipSemStatCtlr != null) {
			if (existeInsParSpe || inscPsIncomplete()) {
				majIpSemStat();
			}
			ipSemStatCtlr.svgde();	// stratégie gérée par l'objet... sauve slt si nécessaire !
			System.out.println("TEST BRICE");
		}
	}

	public void majIpSemStat()  {
		// Mettre à jour les stats pour cet étudiant + ce semestre... ssi c'est possible
		// (modifPossible)
		// BRICE : voir pquoi ipSemStatCtlr est null
		if (ipSemStatCtlr == null) {
			loadIpSemStat();
			System.out.println("loadIpSemStat ... " + ipSemStatCtlr);
		}
				
		if (modifPossible && ipSemStatCtlr != null) {
			ipSemStatCtlr.setMrsemKey(mrsemKeyPS);
			ipSemStatCtlr.setInscPsIncomplete(inscPsIncomplete);
		}
	}

	// appelé depuis InscSemestreCtrlr pour mettre à jour la stat avec nb d'UE incomplets et points ECTS...
	public void majIpSemStatUeEcts(double cumECTS ,int nbUeIncompletes) {
		// ssi c'est possible (modifPossible)
		// BRICE : voir pquoi ipSemStatCtlr est null
		if (ipSemStatCtlr == null) {
			loadIpSemStat();
			System.out.println("loadIpSemStat ... " + ipSemStatCtlr);
		}
		if (modifPossible && ipSemStatCtlr != null) {
			majIpSemStat();
			ipSemStatCtlr.setCumulEcts(new Double(cumECTS));
			if (!inscPsIncomplete())
				ipSemStatCtlr.setNbUeIncompletes(new Integer(nbUeIncompletes));
		}
	}

	// appelé depuis InscSemestreCtrlr pour confirmer ou non validation choix de ses EC par l'étudiant...
	public void majIpSemStatConfChoix(boolean confirme) {
		// ssi c'est possible (modifPossible)
		if (modifPossible) {
			if (confirme) ipSemStatCtlr.valideChoixSemestre();
			else ipSemStatCtlr.invalideChoixSemestre();
		}
	}

	// Pour le semestre existant, lancer la création du controleur associé
	// celui-ci se chargera + ou - selon ses besoins !
	private void creationInscSemCtrlr() {
		// si des formations ont bien été récupérées...
		if (inscSemestresParcPrin != null) {
			leInscSemCtrlr = new InscSemestreCtrlr(maSession,inscSemestresParcPrin,this);
			// en profiter pour mettre à jour l'enregistrement ipSemStat si besoin...
			verifIpSemStat();
		}
		// BRICE : besoin d'un else pour creer leInscSemCtrlr
		System.out.println("creationInscSemCtrlr : " + inscSemestresParcPrin +  " / " + leInscSemCtrlr);
	}


	private void genererInscParcCommun(Integer noSem) {
		// récupérer le n° mrsemKey qui va bien pour ce n°de sem, fspnKey et fannKey...
		NSArray bindings = new NSArray(new Object[] {fspnKey(), anneeUniv(), noSem});
		EOQualifier qualifier = EOQualifier.qualifierWithQualifierFormat(
				"fspnKey = %@ and fannKey = %@ and msemOrdre = %@ and mparAbreviation != nil", bindings);

		EOEditingContext ec = maSession.defaultEditingContext();

		fetchSpecParcours = new EOFetchSpecification("ScolMaqParcoursSemestre",
				qualifier, null);
		NSArray res = ec.objectsWithFetchSpecification(fetchSpecParcours);

		if (res != null && res.count()==1) {
			// on pecho le mrsemKey du parcours commun ou l'on doit faire l'insc... 
			Integer mrsemKey = (Integer)((EOGenericRecord)res.objectAtIndex(0)).valueForKey("mrsemKey");
			if (echangeParcours(noSem, new Integer(-1), mrsemKey)) {
				// si l'inscr° au sem commun s'est bien passée, logguer l'action de l'étudiant 
				maSession.creationParcoursCommunEtudiant(idiplNumero(), (Integer)((eoInscEtud()).valueForKey("etudNumero")),
					anneeUniv(), mrsemKey, getMsemOrdre());
			}
		} else System.out.println("Pas d'inscription au parcours : " + res);
		// le else serait un cas d'erreur sur la création de la maquette  !!!
	}


	// execution de l'appel de Procedure stockée pour faire une désinscription/inscription à un parcours
	public boolean echangeParcours(Integer semOrdre, Integer ancienParcours,Integer nouveauParcours) {
		NSArray clefs = new NSArray(new Object[] {"10_idiplnumero","20_mrsemkeyAnc","30_mrsemkeyNou","40_msemordre","50_fannkey"});
		NSArray vals;
		NSDictionary dico;
		vals = new NSArray(new Object[] {idiplNumero(),ancienParcours,nouveauParcours,semOrdre,anneeUniv()});
		dico = new NSDictionary(vals,clefs);

		return maSession.execProc("pIpwInsIsemestre",dico);
	}


	// Processus de refresh des inscriptions apres changement de parcours...
	// Etapes necessaires =>>
	// 1) refetcher toutes les inscriptions 
	// 2) recharger le semestre de facon a recreer sa hierarchie d'objets...
	public void refreshInscParcours() {
		chargerInscSemestre(true);
		reinitParcoursSelected();
		// Vérifier si le nouveau semestre/parcours chargé présente une dispense...
		if (existeInsParSpe) verifDispenseSemestre();    
	}


	public InscSemestreCtrlr getInscSemCt() {
		if (leInscSemCtrlr == null) {
			// Premier affichage de la maquette du semestre ; création du ctrlr correspondant !
				creationInscSemCtrlr();
		}


		return leInscSemCtrlr;
	}


	// renvoyer le EO pour l'inscription de l'étudiant...
	public EOGenericRecord eoInscEtud() {
		return inscEtudCt.eoInscEtud();
	}

	// Condition pour pouvoir choisir/modifier son parcours :
	// insc à parcours
	// dates OK pour ipWeb (pas avant, pas après)
	// semestre non déjà obtenu précédemment
	// aucune note déjà saisie dans une UE appartenant à un parcours spécialisé !
	public boolean isChoixParcoursPossible() {
		// on gère "un cache" pour le calcul de la condition... pas l'évaluer à chaque refresh !
		if (!cdtChoixParcoursCalculee) {
			choixParcoursPossible = inscAparcours && modifPossible
			&& !semestreDejaObtenu && chgtDeParcoursPossible;
			// TODO : vérif encore à faire = aucune note déjà saisie dans une UE appartenant à un parcours spécialisé

			cdtChoixParcoursCalculee = true;	// cache ok
		}
		return choixParcoursPossible;
	}   

	public boolean isInscAparcours() { return inscAparcours; }


	// Fonction renvoyant "l'ordre de priorité" de ce semestre en fonction de "ses états" :
	public int getOrdrePriorite() {
		int res = 0;
		if (!semIpWeb()) res -= 5;
		if (semestreDejaObtenu()) res -= 2;
		if (modifPossible()) res += 2;
		if (semestreComplet) res -= 1;
		if (ipPasEncoreOuvertes() || ipTerminees()) res -= 2;

		return res;
	}


	// Renvoyer une classe Css pour caractériser la déco de texte à appliquer au commentaire sur ce sem...
	public String getCoulComment() {
		if (!semIpWeb() || !modifPossible()) return DECO_HORSIP;
		if (semestreDejaObtenu()) return DECO_PASDECHOIX;
		if (leInscSemCtrlr != null && leInscSemCtrlr.choixCompletsPasValides()) return DECO_ACONF;
		if (semestreComplet) return DECO_OK;
		else return DECO_MANQUE;
	}

	// Renvoyer un commentaire sur le semestre, selon son état...
	public String getCommentSem() {
		if (!semIpWeb()) return " -- Hors IP !--";
		if (semestreDejaObtenu()) return "déjà obtenu...";
		if (redoublantBloque) return "IP bloquées";
		if (!modifPossible()) return "(IP closes)";
		if (leInscSemCtrlr != null && leInscSemCtrlr.choixCompletsPasValides()) return "à confirmer";
		if (semestreComplet) return "complet.";
		else return "choix à faire !";
	}

	public Integer getMsemOrdre() { return noSemestre; }

	// A la création du ctrl de semestre seulement :
	private void initParcoursSpecialises() {
		// Fetcher les parcours en rapport avec le fspnKey, msemOrdre et fannKey de l'inscription !
		inscAparcours = false;
		// BRICE
		// parcoursSpecialise = null;

		NSArray bindings = new NSArray(new Object[] {fspnKey(), anneeUniv(), getMsemOrdre()});

		EOQualifier qualifier = EOQualifier.qualifierWithQualifierFormat(
				"fspnKey = %@ and fannKey = %@ and msemOrdre = %@",
				bindings);

		EOFetchSpecification fetchSpec = new EOFetchSpecification("ParcoursSpecialise",
				qualifier, null);

		EOEditingContext ec = maSession.defaultEditingContext();

		// fetcher le tuple pour les stats au semestre...
		parcoursSpecialise = ec.objectsWithFetchSpecification(fetchSpec);
		// BRICE
		System.out.println("initParcoursSpecialises : " + parcoursSpecialise);
	}


	// mettre à jour 'parcoursSelected'  après le choix d'un (nouveau) parcours !!! 
	private void reinitParcoursSelected() {
		parcoursSelected = null;
		inscPsIncomplete = false;
		System.out.println("============> "  + inscSemestresParcPrin);
		if (parcoursSpecialise != null && parcoursSpecialise.count()>0) {
			// Ce diplome/semestre possède des parcours...
			inscAparcours = true;
			// a partir du fetch des differents parcours, selectionner l'EOG du parcours spe. actuels...
			
			int parcSelected = ((Integer)inscSemestresParcPrin.valueForKey("mrsemKey")).intValue();
			EOGenericRecord occurence = null;
			boolean valTrouve = false;
			java.util.Enumeration enumerator = parcoursSpecialise.objectEnumerator();
			while (enumerator.hasMoreElements() && !valTrouve) {
				occurence = (EOGenericRecord)enumerator.nextElement();
				if (((Integer)occurence.valueForKey("mrsemKey")).intValue() == parcSelected)
					parcoursSelected = occurence;
			}

			// il n'existe pas de parcours spécialisé choisi par l'étudiant...
			if (!existeInsParSpe) {
				inscPsIncomplete = true;
			}

		}
	}


	// Responsabilité : 
	// A) appeller la procédure stockée de désinsc / insc aux parcours
	// B) lancer une notif de refresh pour signaler chgt de parcours (et refetch à faire)...
	// retourne TRUE si le chgt a bien pu avoir lieu...
	public boolean changerParcours(Integer ancienParcours,Integer nouveauParcours) {
		boolean executionCorrectePS = true;

		// Phase A)

		executionCorrectePS = echangeParcours(getMsemOrdre(), ancienParcours, nouveauParcours);

		// Phase B)
		// La phase A s'est-elle bien passée ?!
		if (executionCorrectePS) {
			// alors lancer le processus de refresh des insc de cet étudiant pour ce msemOrdre...
			refreshInscParcours();
			if (inscSemestresParcPrin != null) {
				// le parcours change et donc le tuple pour monIpSemStat doit être MAJ !!!
				mrsemKeyPS = nouveauParcours;
				inscPsIncomplete = false;	// on a choisi un parcours... donc choix parcours sem. complet 

				// relancer l'init du semestre en cours !!! 
				if (leInscSemCtrlr != null) leInscSemCtrlr.relanceInits(inscSemestresParcPrin);
				else {//  BRICE : ajout test non null
					this.creationInscSemCtrlr(); 
					leInscSemCtrlr.relanceInits(inscSemestresParcPrin);
				}
				verifIpSemStat();
			}
		}
		return executionCorrectePS;
	}

	// Renvoyé l'EO du parcours de l'étudiant parmi ceux possibles (pour radioBoutons dans gestion choix parcours)
	public EOGenericRecord getParcoursSelected() {
		return parcoursSelected;
	}

	// Accès aux EO correspondant aux <> parcours possibles (fetch sur entité "ParcoursSpecialise")
	// la gestion devant se faire depuis un autre composant... on donne juste accès aux datas !
	public NSArray getParcoursSpecialises() {
		System.out.println("parcoursSpecialise : " + parcoursSpecialise);
		return parcoursSpecialise;
	}

	public Integer msemKeyPP() { return msemKeyPP; }
	public Integer msemKeyPC() { return msemKeyPC; }

	public Integer mrsemKeyPS() { return mrsemKeyPS; }
	public boolean inscPsIncomplete() { return inscPsIncomplete; }

	public boolean existeInsParSpe() { return existeInsParSpe; }

	public boolean semestreComplet() { return semestreComplet; }
	public void setSemestreComplet(boolean etat) { semestreComplet = etat; }

	public boolean chgtDeParcoursPossible() { return chgtDeParcoursPossible; }
	public void setChgtDeParcoursPossible(boolean etat) { chgtDeParcoursPossible = etat; }

	public String getLibelleParcoursEnCours() { return libelleParcoursEnCours; }
}
