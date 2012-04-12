package org.cocktail.ipweb.serveur.components.pagebo.modules;
// Generated by the WOLips Template engine Plug-in at 14 aoét 2007 08:36:31
import org.cocktail.fwkcktlwebapp.server.components.CktlWebComponent;
import org.cocktail.ipweb.serveur.Session;
import org.cocktail.ipweb.serveur.controlleur.SeuilCtrlr;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOGenericRecord;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSNotification;
import com.webobjects.foundation.NSNotificationCenter;
import com.webobjects.foundation.NSSelector;

public class Mod_seuil extends CktlWebComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5231304989499407170L;
	private static final int DIAL_AUCUN = 0, DIAL_MODIF = 1;
	private int dialogueActuel;

	private static final int CHOIXDIAL_DETAIL_EC = 1, CHOIXDIAL_GESTION_SEUIL = 2;
	private static final int dsSaisieSeuil = 1, dsAffSimpleSeuil = 3, dsSupprSeuil = 4, dsNouvSeuil = 5;
	private int choixDial, etatDialSeuil;

	private boolean autBtModifier, autBtSupprimer, autBtNouveau;

	public SeuilCtrlr monCtSeuil;

	public NSArray listeEc, listeSeuil;
	public EOGenericRecord ecEnCours, ecEnModif, commentOccur;

	public String nomFormWOBrowser = "formWOB";
	private Integer tmpSeuil; 

	public Mod_seuil(WOContext context) {
		super(context);

		monCtSeuil = new SeuilCtrlr((Session)session());
		initsExternes();

		// s'enregistrer pour les notifs (chgt d'année en cours)
		NSNotificationCenter.defaultCenter().addObserver(this,	// on doit me prévenir moi-même !
				new NSSelector("chgtAnnee",							// via cette méthode
						new Class [] {NSNotification.class}),				// argument obligatoire !!!
						"chgtAnneeEnCours",									// la signature de la notif qui me plait
						(Session)this.session());					// instance de celui qui la poste !
	}

	private void initsExternes() {
		choixDial = CHOIXDIAL_GESTION_SEUIL;
	}


	//  -----------------------------------------------------    
	//  ------------- Reponse aux notifications -------------
	//  -----------------------------------------------------    

	/// Un changement d'année vient d'avoir lieu...
	public void chgtAnnee(NSNotification laNotif) {
		initsExternes();
		monCtSeuil.eoDiplSelected = null;
	}


	//  --------------------------------------------------------    
	//  ---------------- Expr. conditionnelles -----------------
	//  --------------------------------------------------------    

	// Est-ce qu'on a choisi une UE ?
	public boolean isUeChoisi() {
		return (monCtSeuil.eoDiplSelected != null);
	}


	// Indiquer si un dialogue de modif est en cours...
	public boolean dialogueModifEnCours() {
		if (dialogueActuel == DIAL_AUCUN) return false;
		else return true;
	}

	// Est-ce que l'on doit afficher le dialogue de gestion des seuils ? 
	// (si non : afficher le "détail" des EC)
	public boolean afficherDialGestionSeuil() {
		if (monCtSeuil.isModifAutorisee() && choixDial == CHOIXDIAL_GESTION_SEUIL)
			return true;
		else return false;
	}

	// pour un EC choisi, dans quel état est le dialogue sur son seuil ?
	public boolean saisieSeuil() { 
		return (etatDialSeuil == dsSaisieSeuil || etatDialSeuil == dsNouvSeuil);
	}

	public boolean affSimpleSeuil() {
		return (etatDialSeuil == dsAffSimpleSeuil);
	}

	// états permettant de savoir quels boutons afficher...
	public boolean affBtModifier() {
		return autBtModifier;
	}
	public boolean affBtSupprimer() {
		return autBtSupprimer;
	}

	public boolean affBtNouveau() {
		return autBtNouveau;
	}

	public boolean supprSeuil() {
		return (etatDialSeuil == dsSupprSeuil);
	}



	//    // états pour savoir si on affiche les boutons d'action sur le seuil ou le couple Valider/Annuler
	//    public boolean phaseValidModif() {
	//    	return phaseValidModif;
	//    }

	//  --------------------------------------------------------    
	//  ---------------- Valeurs à afficher  -------------------
	//  --------------------------------------------------------    

	public String getDetailEcSeuil() {
		if (ecEnCours != null) {
			int lgMax = 150;
			String res = (String)ecEnCours.valueForKey("mecCode") +
			" : " + (String)ecEnCours.valueForKey("mecLibelleCourt");

			// Chopper le seuil...
			Integer seuil = monCtSeuil.getSeuil(ecEnCours);

			if (seuil != null) {
				res += "  ---------->>> " + seuil;
			}
			return res;
		}
		else return null;
	}

	// Affichage du seuil ET de son né d'ID (pour repérer les seuils identiques !)
	public String seuils() {
		if (ecEnCours != null) {
			Integer seuil = monCtSeuil.getSeuil(ecEnCours);
			if (seuil != null)
				return seuil.toString();
		}
		return "aucun...";
	}


	// Le texte du bouton selon le choix de dialogue en cours...
	public String getTexteBtChoixDial() {
		if (choixDial == CHOIXDIAL_DETAIL_EC)
			return "Gérer les seuils...";
		else return "Afficher détails EC";
	}

	// Retourne un submit du formulaire de choix d'EC, à la sélection dans le WOBrowser...
	public String fctSubmitChoix() {
		return "document."+nomFormWOBrowser+".submit();";
	}


	public String getRefEcAEditer() {
		if (ecEnModif != null) {
			return (String)ecEnModif.valueForKey("mecCode") +
			" : " + (String)ecEnModif.valueForKey("mecLibelleCourt");
		}
		else return "aucun EC sélectionné";
	}

	//  --------------------------------------------------------    
	//  ---------------- Valeurs en E/S  -----------------------
	//  --------------------------------------------------------    

	// le WOBrowser attend toujours une array, méme si "multiple" vaut faux...
	public NSMutableArray getEcChoisie() {
		if (ecEnModif != null) {
			return new NSMutableArray(new Object[] { ecEnModif });
		}
		else return null;
	}

	// le WOBrowser nous renvoit toujours une array, méme si "multiple" vaut faux...
	public void setEcChoisie(NSArray arrayEcChoisis) {
		if (arrayEcChoisis != null && arrayEcChoisis.count() > 0)
			ecEnModif = (EOGenericRecord)arrayEcChoisis.objectAtIndex(0);
	}


	public String getSeuilEcAEditer() {
		if (ecEnModif != null) {
			Integer seuil = monCtSeuil.getSeuil(ecEnModif);
			if (seuil != null) return seuil.toString();
			else if (etatDialSeuil == dsNouvSeuil) 
				return null;
		}
		return "aucun...";
	}

	// Le seuil a changé -> le conserver dans un tampon, pour le répercuter dans la base si validation...
	public void setSeuilEcAEditer(Integer nouvSeuil) {
		tmpSeuil = nouvSeuil;
	}

	//  --------------------------------------------------------    
	//  ------------------------- ACTIONS ----------------------
	//  --------------------------------------------------------    

	// On veut afficher une sélection d'EC pour l'UE choisie... fetcher les détails à afficher
	public WOComponent selectUE() {

		//	NSLog.err.appendln("********************>>>>>>>>>>  On genere l'action selectUE() !!!!!");

		monCtSeuil.chargerEC();
		listeEc = monCtSeuil.getListeEcAChoix();
		ecEnModif = null;

		dialogueActuel = DIAL_AUCUN;
		etatDialSeuil = dsAffSimpleSeuil;
		return null;
	}


	// Switcher les choix de dialogues !
	public WOComponent clickBtChoixDial() {
		if (choixDial == CHOIXDIAL_DETAIL_EC)
			choixDial = CHOIXDIAL_GESTION_SEUIL;
		else choixDial = CHOIXDIAL_DETAIL_EC;
		return null;
	}

	// On vient de choisir l'EC dont on veut gerer le seuil...
	public WOComponent choixEcSeuilAGerer() {
		// a ce moment la, on sait qui est choisi...
		changerEtatBoutons();
		return null;
	}

	// Selon le seuil en cours, changer l'état des boutons...
	private void changerEtatBoutons() {
		if (ecEnModif != null) {
			Integer seuil = monCtSeuil.getSeuil(ecEnModif);
			if (seuil != null) {
				autBtModifier = true;
				autBtSupprimer = true;
				autBtNouveau = false;
			}
			else {
				autBtModifier = false;
				autBtSupprimer = false;
				autBtNouveau = true;
			}
		}

	}


	// Lancer la modif du seuil...
	public WOComponent lancerModifSeuil() {
		etatDialSeuil = dsSaisieSeuil;
		dialogueActuel = DIAL_MODIF;
		return null;
	}


	// supprimer un seuil...
	public WOComponent actionSupprSeuil() {
		etatDialSeuil = dsSupprSeuil;
		dialogueActuel = DIAL_MODIF;
		return null;
	}

	public WOComponent tempAction()
	{
		return null;
	}

	// On a cliqué sur le bouton "Annuler" modif...
	public WOComponent annulerModifSeuil()
	{
		etatDialSeuil = dsAffSimpleSeuil;
		dialogueActuel = DIAL_AUCUN;
		return null;
	}

	// On vient de cliquer sur "valider" la modif...
	public WOComponent validerAction()
	{
		// L'action à entreprendre dépend du type de dialogue engagé...
		switch(etatDialSeuil) {
		case dsSaisieSeuil : monCtSeuil.changerSeuil(tmpSeuil, ecEnModif); 
		break;	
		case dsSupprSeuil : monCtSeuil.supprSeuil(ecEnModif);
		break;
		case dsNouvSeuil : monCtSeuil.nouveauSeuil(tmpSeuil, ecEnModif);
		break;
		}
		etatDialSeuil = dsAffSimpleSeuil;
		dialogueActuel = DIAL_AUCUN;

		changerEtatBoutons();
		return null;
	}

	// Ajout d'un nouveau seuil...
	public WOComponent nouveauSeuilAction()
	{
		etatDialSeuil = dsNouvSeuil;
		dialogueActuel = DIAL_MODIF;
		// Si l'EC possède déjà un seuil, démarrer une action "créer Comme"
		return null;
	}

	// On vient de clicker sur un lien dans le détail des seuils d'EC
	// Passer en mode "gestion de seuil" et sélecctionner l'ec correpondante dans le WOBrowser
	public WOComponent gestionSeuilAction()
	{
		ecEnModif = ecEnCours;
		choixDial = CHOIXDIAL_GESTION_SEUIL;
		changerEtatBoutons();
		return null;
	}

	public boolean debugEnCours()
	{
		return ((Session)session()).monApp.debug();
	}
}