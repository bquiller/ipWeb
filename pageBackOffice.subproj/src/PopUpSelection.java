// Generated by the WOLips Templateengine Plug-in at 9 ao�t 2007 15:27:20

import com.webobjects.appserver.*;
import com.webobjects.foundation.NSLog;
import com.webobjects.foundation.NSSelector;

public class PopUpSelection extends WOComponent {

    
    // BINDINGS Obligatoires :
    private PopUpDataAccessCtrlr lePopUpDACT;	// fixé de l'extérieur
    // pour les autres bindings ou utilise ^nomBindings (cf Pratical WebObjects, page 207 !)
    
    public PopUpSelection(WOContext context) {
        super(context);
        lePopUpDACT = null;
    }
    
    // On veut gérer à la mano la synchro des bindings !!!!!! (comme un DynamicElements)
    // et en plus être stateLess (par défaut : pas de synchro des Bindings !)
//    public boolean synchronizesVariablesWithBindings() { return false; }
    public boolean isStateless() { return true; }
    
    public void reset() {
	// un coup de m�nage en fin de cycle !
	lePopUpDACT = null;
	super.reset();
    }
    
    
    public PopUpDataAccessCtrlr getLePopUpDACT() {
	if (lePopUpDACT == null)
	    lePopUpDACT = (PopUpDataAccessCtrlr) valueForBinding("lePopUpDACT");
	return lePopUpDACT;
    }
    
    // Retourne un submit du formulaire en cours, à la sélection du popUp...
    public String fctSubmitChoix() {
        return "document."+(String)valueForBinding("nomPopUp")+".submit();";
    }

}