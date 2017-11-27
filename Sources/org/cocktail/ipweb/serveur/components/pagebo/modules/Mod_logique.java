package org.cocktail.ipweb.serveur.components.pagebo.modules;

import org.cocktail.fwkcktlwebapp.server.components.CktlWebComponent;
import org.cocktail.ipweb.serveur.Session;
import org.cocktail.ipweb.serveur.controlleur.LogiqueCtrlr;
import org.cocktail.ipweb.serveur.metier.IpRelationChoixEc;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOGenericRecord;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSNotification;
import com.webobjects.foundation.NSNotificationCenter;
import com.webobjects.foundation.NSSelector;

public class Mod_logique extends CktlWebComponent {
	

	private static final int DIAL_AUCUN = 0, DIAL_MODIF = 1;
	private static final int CHOIXDIAL_DETAIL_EC = 1, CHOIXDIAL_GESTION_LOGIQUE = 2;
	private static final int dsSaisieLogique = 1, dsAffSimpleLogique = 3, dsSupprLogique = 4, dsNouvLogique = 5;
	private int choixDial, etatDialLogique;
	private int dialogueActuel;
	public NSArray listeEc, listeLogique;
	private String formule, commentaire;
	public EOGenericRecord ecEnCours, ecEnModif, commentOccur;
	private boolean autBtModifier, autBtSupprimer, autBtNouveau;
	public LogiqueCtrlr monCtLogique;
	public String nomFormWOBrowser = "formWOB";
	public IpRelationChoixEc logiqueEnCours;

    public Mod_logique(WOContext context) {
		super(context);

		monCtLogique = new LogiqueCtrlr((Session)session());
		initsExternes();

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
		initsExternes();
		monCtLogique.eoDiplSelected = null;
	}
	
	//  --------------------------------------------------------    
	//  ---------------- Expr. conditionnelles -----------------
	//  --------------------------------------------------------    

	// Est-ce que l'on doit afficher le dialogue de gestion des logiques ? 
	public boolean afficherDialGestionLogique() {
		if (monCtLogique.isModifAutorisee() && choixDial == CHOIXDIAL_GESTION_LOGIQUE)
			return true;
		else return false;
	}

	// Est-ce qu'on a choisi une UE ?
	public boolean isUeChoisi() {
		return (monCtLogique.eoDiplSelected != null);
	}

	//  --------------------------------------------------------    
	//  ---------------- Valeurs à afficher  -------------------
	//  --------------------------------------------------------    

	public String getDetailEcLogique() {
		if (ecEnCours != null) {
			int lgMax = 150;
			String res = (String)ecEnCours.valueForKey("mecCode") +
			" : " + (String)ecEnCours.valueForKey("mecLibelleCourt") +
			" (" + ecEnCours.valueForKey("mrecKey") + ")";

			// Chopper la logique ...
			String logique = monCtLogique.getLogiqueAsString(ecEnCours);

			if (logique != null && !logique.isEmpty()) {
				res += "  ---------->>> " + logique;
			}
			return res;
		}
		else return null;
	}

	// Affichage de la logique ET de son num d'ID (pour repérer les logiques identiques !)
	public NSArray logiques() {
		if (ecEnCours != null) {
			return monCtLogique.getLogique(ecEnCours);
		}
		return null;
	}

	// Indiquer si un dialogue de modif est en cours...
	public boolean dialogueModifEnCours() {
		if (dialogueActuel == DIAL_AUCUN) return false;
		else return true;
	}

	private void initsExternes() {
		choixDial = CHOIXDIAL_GESTION_LOGIQUE;
	}

	// Retourne un submit du formulaire de choix d'EC, à la sélection dans le WOBrowser...
	public String fctSubmitChoix() {
		return "document."+nomFormWOBrowser+".submit();";
	}

	// Le texte du bouton selon le choix de dialogue en cours...
	public String getTexteBtChoixDial() {
		if (choixDial == CHOIXDIAL_DETAIL_EC)
			return "Ajouter des logiques...";
		else return "Supprimer des logiques";
	}

	//  --------------------------------------------------------    
	//  ---------------- Valeurs en E/S  -----------------------
	//  --------------------------------------------------------    

	public String getFormule() {
		return formule;
	}

	public void setFormule(String formule) {
		this.formule = formule;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

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


	//  --------------------------------------------------------    
	//  ------------------------- ACTIONS ----------------------
	//  --------------------------------------------------------    

	// On vient de choisir l'EC dont on veut gerer la logique ...
	public WOComponent choixEcDepartLogique() {
		// a ce moment la, on sait qui est choisi...
		changerEtatBoutons();
		return null;
	}

	// Switcher les choix de dialogues !
	public WOComponent clickBtChoixDial() {
		if (choixDial == CHOIXDIAL_DETAIL_EC)
			choixDial = CHOIXDIAL_GESTION_LOGIQUE;
		else choixDial = CHOIXDIAL_DETAIL_EC;
		return null;
	}

	// On vient de cliquer sur "valider" la modif...
	public WOComponent ajouterRelation()
	{
		System.out.println("ecEnCours "  + ecEnCours + " - ecEnModif : " + ecEnModif);
		monCtLogique.nouvelleRelation(formule, commentaire, ecEnCours);
		formule = commentaire = "";
		dialogueActuel = DIAL_AUCUN;

		changerEtatBoutons();
		return null;
	}
	
	public WOComponent supprimerRelation()
	{
		monCtLogique.supprLogique(logiqueEnCours);
		return null;
	}

	// On veut afficher une sélection d'EC pour l'UE choisie... fetcher les détails à afficher
	public WOComponent selectUE() {

		//	NSLog.err.appendln("********************>>>>>>>>>>  On genere l'action selectUE() !!!!!");

		monCtLogique.chargerEC();
		listeEc = monCtLogique.getListeEcAChoix();
		ecEnModif = null;

		dialogueActuel = DIAL_AUCUN;
		etatDialLogique = dsAffSimpleLogique;
		return null;
	}
	

	// Selon le seuil en cours, changer l'état des boutons...
	private void changerEtatBoutons() {
		if (ecEnModif != null) {
			NSArray<IpRelationChoixEc> logique = monCtLogique.getLogique(ecEnModif);
			if (logique != null && !logique.isEmpty()) {
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

	// On vient de clicker sur un lien dans le détail des logiques d'EC
	// Passer en mode "gestion de seuil" et sélecctionner l'ec correpondante dans le WOBrowser
	public WOComponent gestionLogiqueAction()
	{
		ecEnModif = ecEnCours;
		choixDial = CHOIXDIAL_GESTION_LOGIQUE;
		changerEtatBoutons();
		return null;
	}

	public boolean debugEnCours()
	{
		return ((Session)session()).monApp.debug();
	}
}