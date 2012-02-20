// Generated by the WOLips TemplateEngine Plug-in at 28 sept. 2006 12:21:15


import com.webobjects.appserver.*;
import com.webobjects.eoaccess.EOModelGroup;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;

import fr.univlr.cri.webapp.CRIMailBus;
import fr.univlr.cri.webapp.CRIWebComponent;
import fr.univlr.cri.webapp.CRIWebPage;
import fr.univlr.cri.webapp.LRConfig;
import fr.univlr.cri.webapp.LRLog;

/* ATTENTE
import org.cocktail.scolarix.serveur.metier.eos.EOPreEtudiant;
*/
public class Main extends CRIWebPage {
    public String login;
    public String unCoucou = "Salut le monde !";

    public Main(WOContext context) {
        super(context);
        
	System.out.println("<<<<<<<<<<<<<<<<<<  Init de la classe Main  >>>>>>>>>>>>>>>>\n");        
        
    }
    
	// appelé depuis la page de login au "démarrage"... 
    // en fonction de l'utilisateur connecté on switch sur :
    //	- MenuBackOffice (personnels autorisés ou enseignants/vac)
    //	- PageIP (étudiants)
	public WOComponent toProfile() 
	{
		
		// Initialize your component here
		Session session = (Session)session();
		EOEditingContext ec = session.defaultEditingContext();
		
		// init dates IP pour cette ann�e..
		((Application)this.application()).initDatesIP(session.getAnneeEnCours());
		
//		((Application)application()).chargeValParDefaut();
		// Fetch de la personne connect�e..
		IndividuCtrlr monIndC = new IndividuCtrlr(session,session.connectedUserInfo().persId());
		
		// Il s'agit d'un étudiant... donc switcher sur ses IP étudiantes
		if (monIndC.estUnEtudiant()) {	// s'agit-il d'un étudiant ?
			session.setICEtudiant(monIndC);
			// enregistre passage au portillon de cet étudiant...
			session.logConnexion();
			return pageWithName("PageIP");	// accés à la page principale de l'appli !
		}// s'agit-il d'un individu pouvant se connecter au back office ?
		else if (monIndC.estUnPersonnel() || monIndC.estUnEnseignant() || monIndC.estUnVacataire()) {
			// sinon personnel au sens large...
			session.setModeBackOffice();
			session.setIndividuCtrlr(monIndC);
			// Init des onglets en fonction de cette personne :
			OngletsCtrlr lesOnglets = session.getMesOnglets();

			if (lesOnglets.droitsDansScolPeda()) {
				session.getMenuCtrlr().setOnglets(lesOnglets.listeOnglets());
				return pageWithName("MenuBAckOffice");	// accés à la partie "Back Office" de l'appli !
			}
			else {
				prevenirCriParMailPbAcces(monIndC);
				return pageWithName("AccesNonAutorise");	// sinon pas d'accés !
			}
		}
		else {
			prevenirCriParMailPbAcces(monIndC);
			return pageWithName("AccesNonAutorise");	// sinon pas d'accés !
		}
		
	}
	
	// on essaie de se connecter mais on est reconnu ni comme �tudiant, ni comme personnel au sens large !
	private void prevenirCriParMailPbAcces(IndividuCtrlr monIndC) {
		// 1) envoi par mail [si c'est autoris� : appli en exploit� seulement !]
		// --> sinon me renvoyer les mails... 
		// TODO : changer l'@ mail en dur !!!!
		CRIMailBus leBusDeMail = ((Session)session()).monApp.getMailBus();

		if (leBusDeMail != null) {
		    String emailDest = ((Session)session()).monApp.getEmailRedirection();

		    String leMsg = monIndC.getUserFirstName()+ " "+ monIndC.getUserLastName() 
		    			+ " (PERS_ID = " + monIndC.getPersId(); 
		    leMsg = leMsg+") a essayé de se connecter à ipWeb...\nMais cette utilisateur n'est reconnu ni comme étudiant, ni comme personnel au sens large !";
		    leBusDeMail.sendMail("ipWeb@univ-nc.nc", emailDest, null, "IPWeb: acces non autorisé", leMsg, null, null);
		}
		else {
		    System.err.println("*********** ERREUR : Pas de serveur de mail configuré et disponible...");
		}

	}
/* ATTENTE
	// appelé depuis la page de login "anonyme" (cas des étudiants pré-inscrits) ... 
    // ==> On switche sur PageIP (étudiants)
	// l'étudiant est soit un pur "préCandidat", soit un "préEtudiant", selon les cas)
	// mais il n'est pas censé être un vrai étudiant ... là, il doit passer par le login authentifié classique !
	public WOComponent toProfilePreInscrit(EOPreEtudiant preInsc, String beaIne, String candEMail) 
	{
		
		// Initialize your component here
		Session session = (Session)session();
		EOEditingContext ec = session.defaultEditingContext();
		
		// init dates IP pour cette année..
		((Application)this.application()).initDatesIP(session.getAnneeEnCours());
		
//		((Application)application()).chargeValParDefaut();
		// Fetch de la personne connectée..
		IndividuCtrlr monIndC = new IndividuCtrlr(session,preInsc, beaIne, candEMail);
		
		// Il s'agit d'un étudiant... donc switcher sur ses IP étudiantes
		if (monIndC.estUnEtudiant()) {	// s'agit-il d'un étudiant ?
			session.setICEtudiant(monIndC);
			return pageWithName("PageIP");	// accés à la page principale de l'appli !
		}// s'agit-il d'un individu pouvant se connecter au back office ?
		else if (monIndC.estUnPersonnel() || monIndC.estUnEnseignant() || monIndC.estUnVacataire()) {
			// sinon personnel au sens large...
			session.setModeBackOffice();
			session.setIndividuCtrlr(monIndC);
			// Init des onglets en fonction de cette personne :
			OngletsCtrlr lesOnglets = session.getMesOnglets();
			session.getMenuCtrlr().setOnglets(lesOnglets.listeOnglets());
			return pageWithName("MenuBAckOffice");	// accés à la partie "Back Office" de l'appli !
		}
		else {
			prevenirCriParMailPbAcces(monIndC);
			return pageWithName("AccesNonAutorise");	// sinon pas d'accés !
		}
		
	}
*/	

	
	
	// Ajouter un style par d�faut pour l'appli
//	LRConfig maConfig = criApp.config();
//	String style = (String)maConfig.valueForKey("HTML_CSS_STYLES");
//	style += "<link ref=\"toto\">";
//	maConfig.takeValueForKey(style,"HTML_CSS_STYLES");

//	CRIWebPage page = (CRIWebPage)pageWithName("PageIP");
//	System.out.println(">>>>>>>>>>>>\n Style = "+page.getUserStyles()+"\n>>>>>>>>>>>>\n");
//	page.setUserStyles(page.getUserStyles()+"<link toto /link>");
	
}