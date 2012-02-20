// Generated by the WOLips TemplateEngine Plug-in at 30 oct. 2006 10:24:51
// C'est la racine de la gestion des onglets actifs...
// On rajoute à présent la gestion de l'année de référence parmi les années existantes dans ScolPeda...

import com.webobjects.appserver.*;
import com.webobjects.foundation.NSArray;

import fr.univlr.cri.webapp.*;

public class MenuBAckOffice extends CRIWebComponent {
	protected MenuCtrlr menuCtrlr;

	public NSArray anneesUniv;
	public Integer anneeChoisie;
	public String nomFormChgtAnneeUniv = "formChgtAnnee"; 
    public Integer occurAnneeUniv;
	
    public MenuBAckOffice(WOContext context) {
        super(context);
        menuCtrlr = ((Session)session()).getMenuCtrlr();
        
        // Initialisation de la NSArray anneesUniv
        // PROVISOIRE !!!
        anneesUniv = ((Session)session()).monApp.getListeAnneesUniv();
        anneeChoisie = new Integer(((Session)session()).getAnneeEnCours());
    }

	public boolean compChoisiIpEtud() {
		return menuCtrlr.estChoisi(Application.OM_IP_ETUD);
	}
    
	public boolean compChoisiListeDipl() {
//	    	if (menuCtrlr.estChoisi(Application.OM_LISTEDIPL)) System.out.print("zzzzzzzzzzzzzzzzz Module Choisi");
		return menuCtrlr.estChoisi(Application.OM_LISTEDIPL);
	}

	public boolean compChoisiListeInscDipl() {
		return menuCtrlr.estChoisi(Application.OM_INSCRITSDIPL);
	}	
    
	public boolean compChoisiDatesIpwDipl() {
		return menuCtrlr.estChoisi(Application.OM_PARAM_DATES_DIPL);
	}
	
	public boolean compChoisiDatesIpwDom() {
		return menuCtrlr.estChoisi(Application.OM_PARAM_DATES_DOM);
	}
	
	public boolean compChoisiCommentEC() {
	    return menuCtrlr.estChoisi(Application.OM_PARAM_COMMENT);
	}
	
	public boolean compChoisiRnAutor() {
	    return menuCtrlr.estChoisi(Application.OM_RN_AUTORISE);
	}
	
	public boolean compChoisiRnListeDipl() {
	    return menuCtrlr.estChoisi(Application.OM_RN_LISTEDIPL);
	}
	
	public boolean compChoisiEnvoiMail() {
		return menuCtrlr.estChoisi(Application.OM_ENVOI_MAIL);
	}
	
		// retourner le bout de Java Script qui va faire une submit du formulaire au chgt d'année...
    public String getFctSubmitChange() {
        return "document." + nomFormChgtAnneeUniv + ".submit();";
    }
    

	// Action : changement d'une année univ => dder à App de lancer une notification...
    public WOComponent chgtAnneeAction()
    {
    	if (anneeChoisie != null)
    		((Session)session()).setAnneeEnCours(anneeChoisie.intValue());
    	//   	System.out.println("Nlle année univ = "+anneeChoisie.toString());
    	return null;
    }

}