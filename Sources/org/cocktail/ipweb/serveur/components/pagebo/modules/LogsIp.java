package org.cocktail.ipweb.serveur.components.pagebo.modules;


import org.cocktail.ipweb.serveur.Session;
import org.cocktail.ipweb.serveur.components.onglets.FonctionsCtrlr;
import org.cocktail.ipweb.serveur.controlleur.InscFormationCtrlr;
import org.cocktail.ipweb.serveur.controlleur.InscSemestreCtrlr;
import org.cocktail.ipweb.serveur.controlleur.UncWebComponent;

import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOGenericRecord;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSMutableDictionary;
import com.webobjects.foundation.NSNotification;
import com.webobjects.foundation.NSNotificationCenter;
import com.webobjects.foundation.NSSelector;
import com.webobjects.foundation.NSTimestamp;


// Generated by the WOLips Templateengine Plug-in at 5 févr. 2009 16:07:39
public class LogsIp extends UncWebComponent {
	
    private static String CONSULT_LOGS_IP = "CONSULT_LOGS_IP";
    private static String[] listeFonctions = new String[] {CONSULT_LOGS_IP};
    private FonctionsCtrlr ctlFonctions;

    //	private Integer mrsemKey;
    private Session maSession;
    private InscFormationCtrlr inscForm;	// reference a la formation choisie par ailleurs
    public InscSemestreCtrlr inscSem;		// objet controleur associe (a initialiser et maj si besoin)

    private NSMutableDictionary cacheInscSem;		// systeme de cache pour stocker les ctrlr de semestres !
    
    public NSArray lesLogs;	// Les logs une fois fetchés... utilisé pour le WORepetition
    public EOGenericRecord leLog;	// variable de parcours...

    public LogsIp(WOContext context) {
        super(context);
        ctlFonctions = new FonctionsCtrlr(((Session)this.session()).getMesOnglets(),listeFonctions);
        cacheInscSem = new NSMutableDictionary();

        // s'enregistrer pour les notifs (chgt de semestre)
        NSNotificationCenter.defaultCenter().addObserver(this,	// on doit me prévenir moi-même !
        	new NSSelector("changeSemestre",		// via cette méthode
        	new Class [] {NSNotification.class}),		// argument obligatoire !!!
        	"chgtSemestre",					// la signature de la notif
			(Session)this.session());		// instance de celui qui la poste !
        
        // appel par notification ne fonctionne pas à l'init... autre méthode donc !
        maSession = (Session)this.session();
    	chargerSemestre(maSession.getInscSemestreParDefaut());
    }

//  ex. de méthode invoquée par notification une fois enregistré :
    public void changeSemestre(NSNotification laNotif) {
    	// on va analyser ce qu'il y a à faire
    	chargerSemestre(laNotif.userInfo());
    }
    
    private void chargerSemestre(NSDictionary userInfo) {
    	// init du semestre en cours !
    	// TODO tester cas d'erreur = pas de semestre par défaut !!!
    	if (userInfo != null) {

    		inscForm = (InscFormationCtrlr)userInfo.objectForKey("InscFormCtrlr");
    		inscSem = chercherCtrlrSem(inscForm);

    		// refresh des droits par rapport à ce nouveau diplome/année...
    		String diplAnnee = (Integer)(inscSem.getMonSemestre()).valueForKey("fspnKey")
    		+"-"+(Integer)(inscSem.getMonSemestre()).valueForKey("anneeSuivie");
    		ctlFonctions.refreshDroitsFonctions(diplAnnee);

    		// prevenir la session du ctlr de semestre en cours à présent !
    		maSession.setCtlrSemestreEnCours(inscSem);

    		// charger les logs correspondants...
    		chargerLogs();
    	}    	
    }
    
    private InscSemestreCtrlr chercherCtrlrSem(InscFormationCtrlr inscForm) {
    	InscSemestreCtrlr semCt = inscForm.getInscSemCt();
    	return semCt;
    }
    
    // Pour initialiser le controleur de formation pour le WO CadreParcours...
    public InscFormationCtrlr leCtlrForm() { return inscForm; }

    // Mise en forme spéciale pour affichage CadreIP
    public String getParcours() {
	String libParcours = getParcoursInscEnCours();
	if (libParcours != null && libParcours.length() > 0)
	    return (", parcours " + libParcours);
	return ("");
    }
    
    // Le parcours auquel fait référence le semestre en cours (si parcours commun, renvoyer chaine vide)
    public String getParcoursInscEnCours() {
	return inscForm.getLibelleParcoursEnCours();
    }
    
    public String getDiplome() {
    	if (inscForm != null)
    		return inscForm.diplome();
    	else return ("Diplôme sans IP");
    }
    
    // *********************************************   
    // *********************************************   
    // **    Methodes differentes de CADREIP...   **
    // *********************************************   
    // *********************************************   

    // Utiliser les données 
    private void chargerLogs() {
    	// récupérer les données pour paramétrer la requète...
    	NSTimestamp ddeb,dfin;
    	ddeb = inscForm.dateBDDebutIP();
    	dfin = inscForm.dateBDFinIP();

    	if (ddeb != null && dfin != null) {

    		NSArray bindings = new NSArray(new Object[] {inscForm.etudNumero(), new Integer(maSession.getAnneeEnCours()),
    				inscForm.idiplNumero(),inscForm.msemKeyPC(),
    				ddeb, dfin });

    		EOQualifier qualifier = EOQualifier.qualifierWithQualifierFormat(
    				" etudNumero = %@ and fannKey = %@ and (idiplNumero = nil OR idiplNumero = %@) and (msemKey = nil or msemKey = %@) and dateLog > %@ and dateLog < %@", bindings);

    		EOSortOrdering surLaDate = EOSortOrdering.sortOrderingWithKey("dateLog",
    				EOSortOrdering.CompareAscending);

    		NSArray sortOrderings = new NSArray(new Object[] {surLaDate});

    		EOFetchSpecification fetchSpec = new EOFetchSpecification("VLogsComplets",
    				qualifier, sortOrderings);
    		fetchSpec.setRefreshesRefetchedObjects(true);

    		EOEditingContext ec = maSession.defaultEditingContext();

    		lesLogs = ec.objectsWithFetchSpecification(fetchSpec);
    	}
    	else lesLogs = null;
    }

    // --------------------------------------------------
    // --- E/S du composants...
    // --------------------------------------------------
    
    public String dateLog() {
    	return maSession.monApp.tsDHLFormat((NSTimestamp)leLog.valueForKey("dateLog"));
    }
 
    /*
    public String commentaireLog() {
    	String com = ((String)leLog.valueForKey("commentaireLog"));
    	if (com == null) return " ";
    	
    }
    */
    
    public boolean pasDeLogs() {
    	if (lesLogs == null || lesLogs.count() == 0) return true;
    	else return false;
    }
    
    
}