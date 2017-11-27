package org.cocktail.ipweb.serveur.controlleur;

import org.cocktail.ipweb.serveur.Session;
import org.cocktail.ipweb.serveur.components.onglets.Droit;
import org.cocktail.ipweb.serveur.components.onglets.FonctionsCtrlr;
import org.cocktail.ipweb.serveur.components.onglets.OngletsCtrlr;
import org.cocktail.ipweb.serveur.metier.IpRelationChoixEc;

import com.webobjects.eocontrol.EOClassDescription;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOGenericRecord;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;

import er.extensions.eof.ERXGenericRecord;

/**
 * 
 * @author olive
 * @version 1.0
 * @category gestion des seuils sur les EC (selection par le dipl/sem-parcours/UE...)
 *
 */
public class LogiqueCtrlr {

	private static String fonction = "MODIF_PARAM_COMMENT";
	private static String DATES_IP_DIPL = "PARAM_COMMENT";
	private static String[] listeFonctions = new String[] {DATES_IP_DIPL};

	private Session maSession;
	private FonctionsCtrlr ctlFonctions;
	private EOEditingContext monEcSsShared;

	public EOGenericRecord eoDiplSelected;
	public NSArray listeEC;

	public Integer fspnKey, anneeSuivie, mueKey;

	public boolean cacheIsModifAutorise, modifAutorisee;

	// Constructeur
	public LogiqueCtrlr(Session sess) {
		maSession = sess;
		OngletsCtrlr mesOngCt = maSession.getMesOnglets();
		ctlFonctions = new FonctionsCtrlr(mesOngCt,listeFonctions);

		// Pb : les commentaires sont associés a des objets read-only, car fetchés dans le sharedEditingContext...
		// Il faut donc créer un editingContext local, sans sharedEditingContext, et y recopier les EC sélectionnées... pour MAJ en cascade du shared

		//	monEcSsShared = maSession.defaultEditingContext();
		monEcSsShared = new EOEditingContext();
		monEcSsShared.setSharedEditingContext(null);
	}


	// remonter les infos sur l'UE actuellement choisie !
	public String getLibCompletUeChoisie() {
		if (eoDiplSelected != null) {
			return (String)eoDiplSelected.valueForKey("mueCode")+ " " +
			(String)eoDiplSelected.valueForKey("mueLibelle");
		}
		else return null;
	}

	// On veut charger les EC à choix correspondant à l'UE actuellement choisie... dans ScolMaqUeEc
	public void chargerEC() {
		//  	y a t'il une UE choisie ?
		if (mueKey != null) {

			NSArray bindings = new NSArray(new Object[] {new Integer(maSession.getAnneeEnCours()), mueKey});
			EOQualifier qualifier = EOQualifier.qualifierWithQualifierFormat(
					"fannKey = %@ and mueKey = %@", bindings);

			EOSortOrdering ordre1 = EOSortOrdering.sortOrderingWithKey("mrecOrdre",EOSortOrdering.CompareAscending);
			EOSortOrdering ordre2 = EOSortOrdering.sortOrderingWithKey("mecCode",EOSortOrdering.CompareAscending);
			NSArray sortOrderings = new NSArray(new Object[] {ordre1, ordre2});

			EOFetchSpecification fetchSpec = new EOFetchSpecification("VMaqEcChoix",qualifier, sortOrderings);
			fetchSpec.setRefreshesRefetchedObjects(true);
			// EOEditingContext ec = maSession.defaultEditingContext();

			listeEC = monEcSsShared.objectsWithFetchSpecification(fetchSpec);  

		}
		else listeEC = null;
		cacheIsModifAutorise = false;

		System.out.println("chargerEC() ... " + listeEC.size());
	}

	// On va charger tous les seuils de l'année en cours pour pouvoir les présenter dans une POP UP...
	public NSArray chargerLogique() {

		NSArray bindings = new NSArray(new Object[] {new Integer(maSession.getAnneeEnCours())});
		EOQualifier qualifier = EOQualifier.qualifierWithQualifierFormat(
				"fannKey = %@", bindings);

		NSArray sortOrderings = new NSArray(new Object[] {});

		EOFetchSpecification fetchSpec = new EOFetchSpecification("IpRelationChoixEc",qualifier, sortOrderings);

		return monEcSsShared.objectsWithFetchSpecification(fetchSpec);  

	}



	public NSArray getListeEcAChoix() { return listeEC; }

	// Indiquer si le user connecté à un droit lui permettant la modif pour ce diplome et cette année... (droit sur MAQUETTES par défaut)
	public boolean isModifAutorisee() {
		if (!cacheIsModifAutorise) {
			cacheIsModifAutorise = true;
			modifAutorisee = false;
			if (mueKey != null) {
				String diplAnnee = fspnKey + "-" + anneeSuivie;
				int droit = ctlFonctions.droitsPourFonctionEtDiplome(fonction,diplAnnee);
				if (droit == Droit.DROIT_MODIF) modifAutorisee = true;
			}
		}
		return modifAutorisee;
	}


	// Retourner les logiques associées à une EC...
	public NSArray<IpRelationChoixEc> getLogique(EOGenericRecord ecEnCours) {
		NSArray bindings = new NSArray(new String[] {"*"+ecEnCours.valueForKey("mrecKey")+"*"});
		EOQualifier qualifier = //null; 
				EOQualifier.qualifierWithQualifierFormat("rceFormuleContrainte like %@",bindings);

		NSArray sortOrderings = new NSArray(new Object[] {});
		EOFetchSpecification fetchSpec = new EOFetchSpecification("IpRelationChoixEc",qualifier, sortOrderings);
		NSArray<IpRelationChoixEc> logique = monEcSsShared.objectsWithFetchSpecification(fetchSpec);  
		
		return logique;
	}

	public String getLogiqueAsString(EOGenericRecord ecEnCours) {
		String logique = "";
		for (IpRelationChoixEc iprcec : this.getLogique(ecEnCours)) {
			logique += "(" + iprcec.rceFormuleContrainte() + ")&";
		}
		if (logique.length()>0) logique = logique.substring(0, logique.length()-1);
		return logique;
	}
	
	// Changer dans la base le seuil d'un EC ...
	// TODO
	public void changerLogique(String nouvFormule,String nouvCommentaire,EOGenericRecord ecEnModif) {
		// retrouver l'entité IpUeSeuil qui va bien...

		if (ecEnModif != null) {
			ERXGenericRecord toEcSeuil = (ERXGenericRecord)ecEnModif.valueForKey("toEcSeuil");
			if (toEcSeuil != null) {

				toEcSeuil.takeStoredValueForKey(nouvFormule, "rceFormuleContrainte");
				toEcSeuil.takeStoredValueForKey(nouvFormule, "rceFormuleContrainte");
				EOEditingContext ecEnt = ecEnModif.editingContext();
				ecEnt.saveChanges();

				// Il faut provoquer un refresh des objets dans le shared editing context de l'application...
				maSession.monApp.InvaliderSharedEC();
			}
		}
	}

	// on veut virer la référence au seuil
	public void supprLogique(IpRelationChoixEc logique) {
		// supprimer de IP_RELATION_CHOIX_EC
		monEcSsShared.deleteObject(logique);
		monEcSsShared.saveChanges();
		// logique.removeObjectFromPropertyWithKey(logique.valueForKey("rceOrdre"),"rceOrdre");
	}

	// Ajouter dans la base le seuil d'un EC ...
	public void nouvelleRelation(String formule,String commentaire, EOGenericRecord ecEnCours) {
		EOClassDescription descriptionIPrelation = EOClassDescription.classDescriptionForEntityName("IpRelationChoixEc");
		EOGenericRecord nouvIpRelation=(EOGenericRecord)descriptionIPrelation.createInstanceWithEditingContext(null, null);

		monEcSsShared.insertObject(nouvIpRelation);
		nouvIpRelation.takeValueForKey(formule,"rceFormuleContrainte");	// normallement la clé primaire est attribuée d'office
		nouvIpRelation.takeValueForKey(commentaire,"rceCommentaireContrainte");
		System.out.println("ecEnCours ::::::::: " + ecEnCours);
		nouvIpRelation.takeValueForKey(ecEnCours.valueForKey("fannKey"), "fannKey");
		nouvIpRelation.takeValueForKey(ecEnCours.valueForKey("mrecKey"), "mrecKeyCible");
		nouvIpRelation.takeValueForKey(eoDiplSelected.valueForKey("msemKey"), "msemKey");
		nouvIpRelation.takeValueForKey("L", "rceTypeRelation");
		monEcSsShared.saveChanges();
	}

}