//Generated by the WOLips TemplateEngine Plug-in at 21 mai 2006 21:10:56

//Il s'agit du bandeau de contr�le de l'appli (r�cup�r� de Profil...) :
//=> Liste de fonctionnalit�es accessibles au user
//Cette liste est adapt�e en fonction du type d'utilisateur !

import com.webobjects.appserver.*;
import com.webobjects.foundation.NSMutableDictionary;
import fr.univlr.cri.webapp.*;

public class BandeauControle extends CRIWebComponent {
 protected String nouvLogin;
 protected MenuCtrlr menuCtrlr;
 protected OptionMenu uneOption;	// var de parcours...

 public BandeauControle(WOContext context) {
     super(context);
     menuCtrlr = ((Session)session()).getMenuCtrlr();
     menuCtrlr.donneContext(context);	// premi�re init au bon moment...
 }

	// MAJ le ctrlr de menu...qui a son tour va d�bloquer le composant !
 // on retourne Null, ce qui provoque le recalcul de la page et l'affichage du comp choisi !
 public WOComponent choixOM() { 
 	menuCtrlr.choisirOM(uneOption);
 	return null; 
 	}
 
}