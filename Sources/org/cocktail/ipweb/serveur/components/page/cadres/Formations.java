package org.cocktail.ipweb.serveur.components.page.cadres;
// Generated by the WOLips TemplateEngine Plug-in at 30 sept. 2006 22:55:21

import org.cocktail.fwkcktlwebapp.server.components.CktlWebComponent;
import org.cocktail.ipweb.serveur.Session;
import org.cocktail.ipweb.serveur.controlleur.DownloadFic;
import org.cocktail.ipweb.serveur.controlleur.IndividuCtrlr;
import org.cocktail.ipweb.serveur.controlleur.InscDiplAnneeCtrlr;
import org.cocktail.ipweb.serveur.controlleur.InscFormationCtrlr;
import org.cocktail.ipweb.serveur.controlleur.InscriptionCtrlr;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSData;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSNotification;
import com.webobjects.foundation.NSNotificationCenter;
import com.webobjects.foundation.NSSelector;


public class Formations extends CktlWebComponent {
    protected IndividuCtrlr individuCt;
    protected InscriptionCtrlr inscCt;
//    protected int anneeUniv;

    public NSArray listeInsc;			// liste des InscriptionCtrlr correspondant aux différentes insc° adm. de la personne connectée
    public InscDiplAnneeCtrlr uneInsc;		// variable de parcours des inscriptions...
    public InscFormationCtrlr vpInscFormCt;	// Variable de parcours pour le WOComp...
    private InscFormationCtrlr iFormCTcourant;	// repère quel est le controleur de semestre en cours de sélection
	
//    protected EOGenericRecord inscSemestre;
    private Session maSession;

    private boolean autorisationSortieRN;
    
    public Formations(WOContext context) {
        super(context);
        maSession = (Session)this.session();

        autorisationSortieRN = maSession.monApp.autorisationSortirRN();
        
        // s'enregistrer pour les notifs (chgt d'étudiant en mode "BackOffice")
        NSNotificationCenter.defaultCenter().addObserver(this,	// on doit me prévenir moi-mème !
        	new NSSelector("changeEtudiant",					// via cette méthode
        	new Class [] {NSNotification.class}),				// argument obligatoire !!!
        	"chgtEtudiant",										// la signature de la notif qui m'plait
			(Session)session());								// instance de celui qui la poste !
        
        // Vérif si une notif n'a pas déjà été lancée, sans être consommée...
        if (!((Session)session()).isChgtEtudiantEnAttente()) changeEtudiant(null) ;
    }

    //	méthode invoquée par notification une fois enregistré :
    // S'il y a un dico : choix d'un diplome/semestre à priori...
    public void changeEtudiant(NSNotification laNotif) {
    	// on va analyser ce qu'il y a à faire
        individuCt = ((Session)cktlSession()).getICEtudiant(); 
        inscCt = individuCt.monCInsc();
        listeInsc = inscCt.getLesDiplAnneeCtrlr();
        // au lancement on veut que s'affiche dans le cadre des IP le semestre "par DÉfaut"...
        if (laNotif != null)
        	lanceIPparDefaut(laNotif.userInfo());
        else lanceIPparDefaut(null);

    }
    
    public DownloadFic lanceAffNotice() {
    	DownloadFic nextPage = (DownloadFic)pageWithName("DownloadFic");
    	String cheminFic = ((Session)session()).monApp.cheminPourFic("IP_WEB_doc_etudiant.pdf");
    	if (nextPage.initDownloadFichier(cheminFic,"application/pdf"))
    		return nextPage;
    	else return null;
    }


    
    public int getAnneeUniv() {
        return maSession.getAnneeEnCours();
    }
    
    public String multiInscription() {
    	if (listeInsc.count()>1)
    		return ("s");
    	else return "";
    }
    
//    // variable de parcours (utile a l'interface)
//    public EOGenericRecord inscSemestre() {
//        return inscSemestre;
//    }
    
    // Vrai si le semestre actuel de la WoRepetition est d�j� en �dition... 
    // ou si on est en mode modif des choix !
    // ou encore si on a demand� un dialogue modal !!!
    public boolean semestreEnEdition() {
    	return (maSession.modifEnCours() || vpInscFormCt == iFormCTcourant 
    		|| maSession.dmec());    	
    }
    
    // retourner soit une fl�che indiquant que le semestre est en �dition, soit le commentaire associ�
    public String getCommentSemestre() {
	if (vpInscFormCt == iFormCTcourant) return "";	// pour le semestre en cours, mettre un gif anim� de fleche...
	else return vpInscFormCt.getCommentSem();
    }
    
    public boolean semestreEnCours() {
    	return (vpInscFormCt == iFormCTcourant);    	
    }

    
    private void lanceIPparDefaut(NSDictionary userInfo) {
    	// On extrait le numero éventuel de semestre - msemKey - du dico éventuel...
    	Integer msemKey = null;
    	if (userInfo != null)
    		msemKey = (Integer)userInfo.valueForKey("msemKey");
    	
    	// si on a bien un semestre (sinon cas d'erreur ???? TODO à vérifier)
    	NSDictionary inscSemParDefaut = inscCt.getSemParDefaut(msemKey);

    	if (inscSemParDefaut != null) {
    		// Quel est le ctrlr de semestre associ� ?
    		iFormCTcourant = (InscFormationCtrlr)inscSemParDefaut.objectForKey("InscFormCtrlr");
    		if (iFormCTcourant != null) {
    			((Session)this.session()).changeSemestre(inscSemParDefaut);
    		}

    		// on passe par la session car plusieurs instances d� objets sessions et � contr�leurs � 
    		//	tournent en parall�le (applis Web)
    		// ne marche pas pour l'init par d�faut (cadre IP pas initialis� !)
    	}
    }

    // Est-ce qu'un RN est disponible ???
    public boolean rnDispo() {
    	return (vpInscFormCt.getVisibiliteRN()>0 && autorisationSortieRN);
    }
    
    public int rnSessionDispo() {
    	int sessionExam = vpInscFormCt.getImrsemEtat();
    	if (sessionExam == 0) sessionExam = vpInscFormCt.getVisibiliteRN();
    	else if (sessionExam > vpInscFormCt.getVisibiliteRN() ) sessionExam = 1;
    	return sessionExam;
    }
    
    
    // On a demandé le relevé de notes !!!
    // dans la foulée, envoi a l'étudiant de ce document par mail... pour qu'il puisse le consulter + tard !
    public DownloadFic lancerRN()
    {
    	DownloadFic nextPage = (DownloadFic)pageWithName("DownloadFic");
    	// On passe les éléments controleur...
    	String nomFichier = "ReleveDeNotes.pdf";

    	// ETUD_NUMERO : 
    	Integer rnEtudNumero = (Integer)((vpInscFormCt.eoInscEtud()).valueForKey("etudNumero"));
    	
    	// FANN_KEY : 
    	Integer rnFannKey = vpInscFormCt.anneeUniv();
    	
    	// FSPN_KEY :
    	Integer rnFspnKey = vpInscFormCt.fspnKey();
    	
    	// IDIPL_ANNEE_SUIVIE :
    	Integer rnIdiplAnneeSuivie = (Integer)((vpInscFormCt.eoInscEtud()).valueForKey("idiplAnneeSuivie"));
    	
    	// MRSEM_KEY :
    	Integer rnMrsemKey = vpInscFormCt.mrsemKeyPS();
    	
    	// MSEM_KEY :
    	Integer rnMsemKey = vpInscFormCt.msemKeyPC();
    	
    	// MSEM_ORDRE :
    	Integer rnMsemOrdre = vpInscFormCt.getMsemOrdre();
    	
    	// session :
    	int sessionExam = rnSessionDispo();

    	// Envoie des infos nécessaires pour sortie du RN :
    	// Libellé du diplome :
    	String diplome = vpInscFormCt.diplomeLl();

    	// génération du PDF... 
    	// TODO : paramétrer la VILLE !
    	// lance d'abord la proc stockée + fetch et retraitement des données pour édition du RN
    	NSData lesDatas = maSession.getReleveNotes().imprRN(rnEtudNumero, rnFannKey, rnFspnKey, rnIdiplAnneeSuivie, 
    			rnMrsemKey, rnMsemKey, rnMsemOrdre, sessionExam,"Nouméa",diplome);

    	// On loggue cette action pour cet étudiant...
    	((Session)session()).logSortieRN(vpInscFormCt.idiplNumero(), rnEtudNumero, rnFannKey, rnMrsemKey, rnMsemOrdre);
    	

    	// 1) envoi par mail [si c'est autorisé : appli en exploit° seulement !]

    	IndividuCtrlr UserReel = ((Session)session()).getIndividuCtrlr();
    	if (UserReel != null && UserReel.estUnEtudiant()) {
    		String semestre = "le semestre " + rnMsemOrdre + " de " + vpInscFormCt.diplome();

    		String leMsg = "Vous avez demandé une copie de votre relevé de notes pour "+semestre+", session " + sessionExam + " ("+ vpInscFormCt.anneeUniv() +").";
    		leMsg = leMsg+"\nVous trouverez en pièce jointe à ce mail le document récapitulatif édité à cette occasion.";
    		leMsg = leMsg+"\nATTENTION : il ne s'agit pas du relevé de notes officiel, qui doit être demandé au service de la Scolarité en cas de besoin !";
    		leMsg = leMsg+"\n\n[Ceci est un message automatique de l'application IP Web - une réponse à cette adresse ne sera pas lue.";
    		leMsg = leMsg+"\nEn cas de problème sur les données de votre inscription, veuillez contacter votre secrétariat pédagogique...]";

    		maSession.envoitMail("IPWeb : extrait de relevé de Notes",leMsg, nomFichier, lesDatas);
    	}    	
    	
    	// 2) download page pour affichage sur Acrobat Reader du poste client...

    	boolean res = nextPage.initDownloadPDF(lesDatas,nomFichier);
    	if (!res) return null;
    	else return nextPage;
    }

    
    
	// construction du message pour OVERLIB pour ce Semestre (explications pour l'�tudiant... )
	public String getMsgOlSem() {
//		"return overlib(' --Pas de Commentaire-- ', CAPTION,'R�union du consortium Cocktail');"
		StringBuffer sb = new StringBuffer("return overlib('Cliquez sur ce lien pour visualiser vos choix pour ce semestre...'");
		sb.append(",CAPTION,'Info :');");
		return sb.toString();
	}
	
	public String getMsgRNSem() {
		StringBuffer sb = new StringBuffer("return overlib('Cliquez sur ce lien pour visualiser votre relevé de notes de session " +rnSessionDispo()+" pour ce semestre...'");
		sb.append(",CAPTION,'Info :');");
		return sb.toString();
	}
	
	// On vient de cliquer sur un nouveau semestre...
    // notifier CadreIp (et CadreUe) pour changer le contenu du cadre "semestre" !
    public WOComponent changerSemestre() {
    	// on a tous les �l�ments qui faut dans les variables de parcours...
	iFormCTcourant = vpInscFormCt;
	NSDictionary userInfo= new NSDictionary(new Object[] {vpInscFormCt},
		new Object[] {"InscFormCtrlr"});

	// sous-traiter l'envoi � la session !
	((Session)this.session()).changeSemestre(userInfo);
	return null;
    }

}