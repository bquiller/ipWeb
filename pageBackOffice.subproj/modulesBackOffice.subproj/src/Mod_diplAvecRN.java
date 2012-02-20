// Generated by the WOLips Templateengine Plug-in at 14 mars 2008 13:04:56

import java.util.Enumeration;

import com.webobjects.appserver.*;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOGenericRecord;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSLog;
import com.webobjects.foundation.NSNotification;
import com.webobjects.foundation.NSNotificationCenter;
import com.webobjects.foundation.NSSelector;


public class Mod_diplAvecRN extends WOComponent {

	public ListeDiplRNCtrlr monCtrlrListeDiplRN;
    public EOGenericRecord eoDiplAvecRN;
    public EOGenericRecord eoDiplSansRN;
	
    private NSArray listeChoixDiplAvec, listeChoixDiplSans;
    
    private boolean autorisationGestionSortieRN;

    
    // ----------- CONSTRUCTEUR ------------
    public Mod_diplAvecRN(WOContext context) {
        super(context);
 
        autorisationGestionSortieRN = ((Session)session()).monApp.autorisationSortirRN();

    	if (autorisationGestionSortieRN) {
    		monCtrlrListeDiplRN = new ListeDiplRNCtrlr((Session)this.session());
            monCtrlrListeDiplRN.initListeDiplRN();
    	}
    	// s'enregistrer pour les notifs (chgt d'année en cours)
    	NSNotificationCenter.defaultCenter().addObserver(this,	// on doit me prévenir moi-même !
    			new NSSelector("chgtAnnee",							// via cette méthode
    					new Class [] {NSNotification.class}),				// argument obligatoire !!!
    					"chgtAnneeEnCours",									// la signature de la notif qui me plait
    					(Session)this.session());					// instance de celui qui la poste !

    }

    
    //  -----------------------------------------------------    
    //  ------------- Reponse aux notifications -------------
    //  -----------------------------------------------------    
    
    /// Un changement d'année vient d'avoir lieu...
    public void chgtAnnee(NSNotification laNotif) {
    	if (autorisationGestionSortieRN)
    		monCtrlrListeDiplRN.initListeDiplRN();
    }
    
    //  --------------------------------------------------------    
    //  ---------------- Valeurs à afficher  -------------------
    //  --------------------------------------------------------    

 
    
    
    //  --------------------------------------------------------    
    //  ---------------- Expr. conditionnelles -----------------
    //  --------------------------------------------------------    

    
    public boolean gestionSortieRNAutorisee()
    {
        return autorisationGestionSortieRN;
    }
    
 
    //  --------------------------------------------------------    
    //  ---------------- Valeurs en E/S      -------------------
    //  --------------------------------------------------------    


    public NSArray getSelectionDiplomesAvec()
    {
        return null;
    }

    public void setSelectionDiplomesAvec(NSArray newSelectionDiplomes)
    {
    	listeChoixDiplAvec = newSelectionDiplomes;
    }   	

    public NSArray getSelectionDiplomesSans()
    {
        return null;
    }
    
    public void setSelectionDiplomesSans(NSArray newSelectionDiplomesSans)
    {
       listeChoixDiplSans = newSelectionDiplomesSans;
    }

    

    //  --------------------------------------------------------    
    //  ------------------------- ACTIONS ----------------------
    //  --------------------------------------------------------    
    
    public WOComponent supprDiplAvecRN()
    {
    	// Y a t'il des diplomes à virer ?
    	if (listeChoixDiplAvec != null && listeChoixDiplAvec.count()>0) {
    		monCtrlrListeDiplRN.ajouterRestriction(listeChoixDiplAvec);
    	    ((Session)session()).signaleChgtListeDiplAutorn();
    	}

        return null;
    }

      
    public WOComponent ajoutDiplAvecRN()
    {
    	// Y a t'il des diplomes à ajouter ?
    	if (listeChoixDiplSans != null && listeChoixDiplSans.count()>0) {
    		monCtrlrListeDiplRN.enleverRestriction(listeChoixDiplSans);
    	    ((Session)session()).signaleChgtListeDiplAutorn();
    	}
    	return null;
    }

}