package org.cocktail.ipweb.serveur.controlleur;

import java.util.Enumeration;
import java.util.HashMap;

import org.cocktail.ipweb.serveur.Session;
import org.cocktail.ipweb.serveur.metier.IpChoixEc;
import org.cocktail.ipweb.serveur.metier.IpRelationChoixEc;

import com.webobjects.eoaccess.EOEntity;
import com.webobjects.eoaccess.EOSQLQualifier;
import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOGenericRecord;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSharedEditingContext;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSData;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSLog;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;
import com.webobjects.foundation.NSTimestamp;

/**
 * @author olive
 * Cree le 2 oct. 2006 
 *
 */

public class InscSemestreCtrlr {

	private Session maSession;
	private EOGenericRecord monSemestre;

	private NSArray listeUe;		// les enreg. de "scolMaqSemestreUe" qui vont bien... 
	private NSArray listeUeCt;		// les controleurs a créer pour les différentes UE de ce semestre
	private InscFormationCtrlr inscFormCt;	// le ctrlr du niveau supérieur...
	private Integer semImpair;		// 1 si impair, 0 si pair

	private int nbreMaxEcFacultatifsAChoisir;	// va indiquer combien d'EC facultatifs peuvent encore être choisis...

	private double cumECTS;			// les ECTS cumulés des EC avec IP ou choix pour ce semestre

	private NSArray listeInscEc,listeChoixEc;		// le résultat du fetch...
	private NSDictionary dictInscEc,dictChoixEc;	// dictionnaires pour recherche accélérée...
	private NSMutableDictionary dictEcSem;			// dico de toutes les EC du semestre, clé = mrecKey

	private NSMutableDictionary dictEcSameCode;		// classement des EC du semestre par code EC...(dico de listes)
	private NSArray lesRelationsEntreEc;			// stockage des objets relations du semestre.
	private NSArray lesContraintes;			// liste des EO représentant des équations booléennes de contraintes 

	private NSMutableArray listeEcCTaChoix;	// liste des ctrl d'EC a choix non bloqué...
	private NSMutableArray listeEcCTFacultatifs;	// liste des ctrl d'EC Facultatifs...	

	// afin de retrouver rapidement par MREC_KEY
	private int nbUeIncompletes;	// nbre d'UE ou il y a encore des choix pédagogiques a faire...
	private String derniereUeAvecErreur;	// le code mueKey de l'UE contenant un EC en erreur
	private String derniereUeIncomplete;	// le code mueKey de la dernière UE de la page qui est incomplete

	private boolean modeModif;		// indicateur de mode (si vrai, on a demandé la modif des choix d'EC du semestre)
	private boolean aucunChoixAFaire;	// vrai si on est dans les dates d'inscr° mais que le redoublant a déjà validé toutes les UE à choix

	private boolean validationChoixParEtudiant;	// Vrai si l'étudiant a "confirmé" ses choix (signature électronique, en gros)
	// repasse à faux s'il y a une modif à faire !

	private boolean consultSeule;	// permet au mode Back-Office de bloquer les modifs si droits insuffisants	
	/**
	 * Objectif : gérer le semestre pour lequel on veut gérer les IP...
	 * @throws Exception
	 * 
	 */
	public InscSemestreCtrlr(Session sess,EOGenericRecord semestre,InscFormationCtrlr inscForm) {
		super();
		//
		maSession = sess;
		monSemestre = semestre;		// ref de ScolInsParcoursSemestre
		inscFormCt = inscForm;

		// aller voir dans IP_SEM_STAT la valeur de Choix_valides...
		validationChoixParEtudiant = inscFormCt.choixValides();

		// Principe : on ne charge completement la maquette de ce parcours/semestre qu'en cas de besoin
		initsBefore();
		lancerInits(); 
	}

	// 	Lancement tout au début puis avant chgt de parcours !
	private void initsBefore() {
		aucunChoixAFaire = false;	// par défaut cet étudiant peut faire des choix...
	}

	// Bascule confirmation/modification choix, avec svgde de l'état à chaque fois...
	public void confirmerChoix(boolean confirmer) {
		validationChoixParEtudiant = confirmer;

		// maj de la valeur de Choix_valides dans IP_SEM_STAT
		inscFormCt.majIpSemStatConfChoix(confirmer);

		// Logguer l'action de l'étudiant (validation de ses choix OU annulation de cette validation)
		maSession.confirmerChoixEtudiant(inscFormCt.idiplNumero(), (Integer)((inscFormCt.eoInscEtud()).valueForKey("etudNumero")),
				inscFormCt.anneeUniv(), inscFormCt.mrsemKeyPS(), inscFormCt.getMsemOrdre(), confirmer);
	}

	// A lancer en cas de besoin (préInits pas suffisantes pour répondre aux besoins de base OU sélection du semestre)
	private void lancerInits() 
	{
		int msemOrdre = (inscFormCt.getMsemOrdre()).intValue();
		if (msemOrdre%2== 1) semImpair = new Integer(1);
		else semImpair = new Integer(0);

		// Gestion des parcours spécialisés : fetcher la liste des parcours possibles pour ce dipl/semestre/annee
		init();
		parcoursSpecialiseAvecDispenses();	// vérifier si ce semestre comporte un parcours spécialisé ou il y aurait des notes avec dispenses
	}


	// Acces a l'EO du semestre (scolInsParcoursSemestre) de l'extérieur...
	public EOGenericRecord getMonSemestre() { return monSemestre; }

	// vérifier si ce semestre comporte un parcours spécialisé 
	// ou il y aurait des notes avec dispenses (soit d'EC, soit d'UE)
	private void parcoursSpecialiseAvecDispenses() {
		boolean chgtDeParcoursPossible = true;

		// Ce semestre presente t'il des parcours spécialisés (plus d'un ?) ; un parcours est-il déjà choisi ?
		// BRICE : ajout test null 
		if (listeUeCt != null && inscFormCt.getParcoursSpecialises() != null && inscFormCt.getParcoursSpecialises().count()>1 
				&& inscFormCt.existeInsParSpe()) {

			// faire le tour des différentes UE du parcours actuel ...
			java.util.Enumeration e = listeUeCt.objectEnumerator();
			while (chgtDeParcoursPossible && e.hasMoreElements()) {
				InscUeCtrlr ueCt = (InscUeCtrlr)e.nextElement();
				// si une UE faisant référence au parcours specialisé a une dispense...
				if (ueCt.ueAyantDispense(inscFormCt.msemKeyPP()))
					chgtDeParcoursPossible = false;
			}
		}
		inscFormCt.setChgtDeParcoursPossible(chgtDeParcoursPossible);
	}


	private void init() {
		modeModif = false; // on commence en visu (la modif n'est peut-être pas possible !)

		dictEcSem = new NSMutableDictionary();
		dictEcSameCode = new NSMutableDictionary();
		listeEcCTaChoix = new NSMutableArray();
		listeEcCTFacultatifs = new NSMutableArray();

		chargerInscEtChoixEc();	// charger globalement toutes les insc° aux EC pour cette idipl_numero et imrec_semestre
		// ainsi que tous les choix aux EC à choix pour cette idipl_numero et imrec_semestre

		lesContraintes = chargerContraintesEc();	// charger globalement toutes les contraintes pour les EC de se semestre... 

		chargerUe();		// ensuite : chargement des UE associées à ce semestre...
		chargerCommentaires();						// charger globalement les commentaires des EC de ce semestre...

		inventaireContraintes();
		majIpSemStatUeEcts();
	}



	// appelé quand il y a eu chgt de parcours, pour faire un refresh (recharger maquette)
	public void relanceInits(EOGenericRecord inscSemestre) {

		monSemestre = inscSemestre;

		// relancer l'init du semestre en cours !!!
		initsBefore();
		init();

	}    

	// Indique si ce dipl.semestre comporte des parcours ou non...
	public boolean isInscAParcours() { return inscFormCt.isInscAparcours(); }

	// Indique s'il y a une dispense complete du semestre :
	public boolean isSemestreDejaObtenu() { return inscFormCt.semestreDejaObtenu(); }

	// Indique si un parcours specialise a déjà été choisi !
	public boolean isParcSpeChoisi() { 
		// modif Brice 
		return (inscFormCt.existeInsParSpe() | inscFormCt.getParcoursSelected() != null );
	}

	public String dateFinIPWebIncluse() {	// ip possibles jusqu'au...inclus !
		if (semIpWeb()) {
			String res = maSession.monApp.tsFormat(inscFormCt.dateFinIP());
			return res;
		}
		else return "";
	}

	public String dateDebutIPWeb() {	// ip possibles à partir du...inclus !
		if (modifPossible() || inscFormCt.dateDebutIP() != null) 
			return maSession.monApp.tsFormat(inscFormCt.dateDebutIP());
		else return "";
	}

	public boolean dateDebutIPWebConnue() {
		return (inscFormCt.dateDebutIP() != null);
	}

	public boolean modifPossible() { 
		// Si c'est un enseignant qui consulte, renvoyer FALSE
		if (maSession.estUnEnseignant()) return false;
		else {

			if (consultSeule) return false;
			else return inscFormCt.modifPossible(); 
		}
	}

	public boolean semIpWeb() { 
		if (consultSeule) return false;
		return (inscFormCt.semIpWeb() & !inscFormCt.semestreDejaObtenu()); 
	}

	// blocage du mode modification pour le backOffice (droits insuffisants)
	public void consultSeule(boolean etat) {
		consultSeule = etat;
	}

	public Integer getMsemKey() {
		return inscFormCt.msemKeyPP();
	}

	public Integer getMsemOrdre() {
		return (Integer)monSemestre.valueForKey("msemOrdre");
	}

	public Integer getIdiplNumero() {
		return (Integer)monSemestre.valueForKey("idiplNumero");
	}


	// chargement des UE associées à ce semestre, dans l'ordre...
	private void chargerUe()  {
		//		NSArray bindings = new NSArray(new Object[] {(Integer)monSemestre.valueForKey("msemKey")});

		//		EOQualifier qualifier = EOQualifier.qualifierWithQualifierFormat(
		//		"msemKey = %@ ", bindings);

		//		EOSortOrdering rueOrdre = EOSortOrdering.sortOrderingWithKey("mrueOrdre",
		//		EOSortOrdering.CompareAscending);

		//		NSArray sortOrderings = new NSArray(new Object[] {rueOrdre});

		//		EOFetchSpecification fetchSpec = new EOFetchSpecification("ScolMaqSemestreUe",qualifier, sortOrderings);

		//		EOEditingContext ec = maSession.defaultEditingContext();

		//		listeUe = ec.objectsWithFetchSpecification(fetchSpec);

		listeUe = maSession.monApp.chargerDesUe(getMsemKey(),inscFormCt.msemKeyPC());
		// si des UE ont bien été récupérées... générer les objets Ctrlr qui vont les gérer !
		cumECTS = 0.0;
		boolean auMoinsUneUeAvecChoix = false;	// vérifier qu'il y a des choix possibles !
		if (listeUe != null && listeUe.count()>0) {
			nbUeIncompletes = 0;
			NSMutableArray lesUeCt = new NSMutableArray();
			java.util.Enumeration enumerator = listeUe.objectEnumerator();
			while (enumerator.hasMoreElements()) {
				EOGenericRecord ueMaq = (EOGenericRecord)enumerator.nextElement();
				InscUeCtrlr ueCt = new InscUeCtrlr(maSession,ueMaq,this,inscFormCt.modifPossible(),inscFormCt.semestreIntegre());
				ueCt.cumulCasesCochee();
				if (ueCt.ueOuverte() && ueCt.compareCumulEcts()<0) {
					nbUeIncompletes++;
					derniereUeIncomplete = ueCt.getUeKey().toString();
				}
				cumECTS += ueCt.cumulEctsUe();	// cumul des points ECTS du diplôme...
				lesUeCt.addObject(ueCt);

				if (ueCt.ueAvecChoix()) auMoinsUneUeAvecChoix = true; 
			}
			listeUeCt = new NSArray(lesUeCt);
		}   

		if (!auMoinsUneUeAvecChoix) {
			if (!isInscAParcours()) inscFormCt.setModifPossible(false);
			aucunChoixAFaire = true;
		}

		if (nbUeIncompletes == 0 && !inscFormCt.inscPsIncomplete()) inscFormCt.setSemestreComplet(true);
	}



	public boolean getAucunChoixAFaire() {
		return aucunChoixAFaire;
	}

	public String stCumulEctsSem() {
		return maSession.formattedouble(cumECTS,false);
	}

	private void chargerCommentaires() {

		// ENCOURS : Modifier pour faire un fetch des commentaires suivant les 2 msemKey...
		NSArray listeComment = (NSArray)monSemestre.valueForKey("vEcComments");
		if (listeComment != null && listeComment.count()>0) {
			// pour chaque commentaire....
			java.util.Enumeration enumerator = listeComment.objectEnumerator();
			while (enumerator.hasMoreElements()) {
				EOGenericRecord eoCom = (EOGenericRecord)enumerator.nextElement();
				Integer mrecK = (Integer)eoCom.valueForKey("mrecKey");
				InscEcCtrlr ecCt = (InscEcCtrlr)dictEcSem.objectForKey(mrecK);
				if (ecCt != null) ecCt.setComment((String)eoCom.valueForKey("mrecComment"));
			}

		}
	}


	// Création des contraintes inter EC, inventaire des contraintes par règles de gestion
	// ajout éventuel d'une relation englobant toutes les EC facultatives (si existent)
	public void inventaireContraintes() {
		System.out.println("Dans inventaireContraintes ...");
		// A présent on utilise ce dico pour créer des relations d'exclusions entre EC ayant méme code..
		NSArray listeSameCode = dictEcSameCode.allValues();
		NSArray sameCode;
		NSMutableArray listeRelations = new NSMutableArray();
		RelationChoixEc relEc;
		NSMutableArray listeRel = new NSMutableArray();

		java.util.Enumeration enumerator = listeSameCode.objectEnumerator();
		while (enumerator.hasMoreElements()) {
			sameCode = (NSArray)enumerator.nextElement();
			if (sameCode != null && sameCode.count()>=2) {
				// creer une relation d'exclusion entre au moins 2 EC ayant même mec_key...
				relEc = new RelationChoixEc(sameCode);
				listeRel.addObject(relEc);
			}
		}
		System.out.println("    après while : " + listeRel.toString()); // Vide mais pas grave
		System.out.println("    listeEcCTFacultatifs " + listeEcCTFacultatifs);
		// Traiter les EC facultatifs parmi les EC à choix...
		if (listeEcCTFacultatifs != null && listeEcCTFacultatifs.count() > 0) {

			// Nbre d'EC facultatifs max par niveaux
			int nbreMaxEcFacultatifs = maSession.interrogeParamConfigInt("NBRE_MAX_EC_FACULTATIFS");
			RelationChoixFacultatif relFac = new RelationChoixFacultatif(
					inscFormCt.idiplNumero(),
					listeEcCTFacultatifs, 
					maSession.getAnneeEnCours(),
					maSession.defaultEditingContext(), 
					maSession.interrogeParamConfig("GROUPE_EC_FACULTATIFS_INCOMPATIBLES"),
					nbreMaxEcFacultatifs);
			listeRel.addObject(relFac);
			// Nbre max d'EC facultatifs à choisir
			nbreMaxEcFacultatifsAChoisir = relFac.nbEcFacultatifsRestant();
		}
		System.out.println("    lesContraintes " + lesContraintes);
		
/*		
		// traiter les formules de contraintes (équations booléennes) ...
		if (lesContraintes != null && lesContraintes.count()>0) {
			boolean demandeSave = false;
			enumerator = lesContraintes.objectEnumerator();
			while (enumerator.hasMoreElements()) {
				IpRelationChoixEc ctrte = (IpRelationChoixEc)enumerator.nextElement();
				try {
					RelationChoixExpression relExp=new RelationChoixExpression(ctrte,
							(NSDictionary)dictEcSem,
							maSession.defaultEditingContext());
					listeRel.addObject(relExp);
					if (relExp.majExpressionCompacte()) {
						demandeSave=true;
					}
				}
				catch (Exception e) {	// on ne peut pas générer cette relation (pb avec équation booléenne)
					System.out.println("err inscSemCtrl : "+e.getMessage());	// on signale et on passe à la suivante...
					e.printStackTrace();
				}	
			}			
			if (demandeSave) {
				maSession.commitChgt();
			}
		}
		System.out.println("Les EC : " + dictEcSem + " stop !");
*/		
		
		// BRICE : traiter les formules de contraintes (équations booléennes) ... sur le semestre précédent ...
		// if (getMsemOrdre() > 1 && lesContraintes != null && lesContraintes.count()>0) {
		if (getMsemOrdre() >= 1 && lesContraintes != null && lesContraintes.count()>0) {
			System.out.println("Dans le code de BRICE !");
			// On récupère tous les SCOL_INSCRIPTION_EC correspondant au IDIPL_NUMERO 
			// Modif de BRICE pour gérer l'année précédente

			/* 
			NSArray bindings = new NSArray(new Object[] {inscFormCt.idiplNumero()});
			EOQualifier qualifier = EOQualifier.qualifierWithQualifierFormat(
					"idiplNumero = %@", bindings);
			*/
			EOEditingContext ec = maSession.defaultEditingContext();

			Integer[] param = {inscFormCt.anneeUniv()-1, (Integer)((inscFormCt.eoInscEtud()).valueForKey("etudNumero"))};
			EOEntity inscription = EOUtilities.entityNamed(ec,"ScolInscriptionEc");
			EOSQLQualifier qualifier = new EOSQLQualifier(inscription,
					"fannKey >= %@ and idiplNumero in (select IDIPL_NUMERO from IP_WEB.SCOL_INSCRIPTION_ETUDIANT where etud_numero = %@) ", param);
			
			EOSortOrdering mrecKey = EOSortOrdering.sortOrderingWithKey("mrecKey",
					EOSortOrdering.CompareDescending);
			NSArray sortOrderings = new NSArray(new Object[] {mrecKey});
			EOFetchSpecification fetchInscEc = new EOFetchSpecification("ScolInscriptionEc",
					qualifier, sortOrderings);
			fetchInscEc.setRefreshesRefetchedObjects(true);	// fait en sorte de refetcher des EOS déjà fetchés si besoin

			NSArray<EOGenericRecord> inscEc = ec.objectsWithFetchSpecification(fetchInscEc);
			
			NSMutableDictionary dictEcSems = new NSMutableDictionary();
			
			for (EOGenericRecord ecs : inscEc) {
				System.out.println("Inscrit à " + ecs.toString());
				Integer mrecK = (Integer)ecs.valueForKey("mrecKey");
				InscEcCtrlr ect = new InscEcCtrlr(mrecK, true); // Le choix est forcément bloqué !
				
				ect.setUeKey("test"); // Il faudrait avoir un lien sur l'UE !!!
				dictEcSems.setObjectForKey(ect,mrecK);
			}
			
			// TODO faire la modif pour que la vérification se fasse aussi sur les choix non intégrés
			NSArray bindings= new NSArray(new Object[] {inscFormCt.idiplNumero()});
			EOQualifier qualifier2 = EOQualifier.qualifierWithQualifierFormat(
					"idiplNumero = %@", bindings);
			EOFetchSpecification fetchSpec = new EOFetchSpecification("IpChoixEc",qualifier2, sortOrderings);
			fetchSpec.setRefreshesRefetchedObjects(true);	// fait en sorte de refetcher des EOS déjà fetchés si besoin

			NSArray<IpChoixEc> voeux = ec.objectsWithFetchSpecification(fetchSpec);
			for (IpChoixEc voeu : voeux) {
				System.out.println("Voeu sur " + voeu.toString());
				Integer mrecK = (Integer)voeu.valueForKey("mrecKey");
				InscEcCtrlr ect = new InscEcCtrlr(mrecK, true); // Le choix est forcément bloqué !
				
				ect.setUeKey("test"); // Il faudrait avoir un lien sur l'UE !!!
				dictEcSems.setObjectForKey(ect,mrecK);
			}

			dictEcSems.addEntriesFromDictionary((NSDictionary)dictEcSem); // On doit merger pour avoir les EC cochées ...
			System.out.println("Les EC par BRICE : " + dictEcSems + " stop !");
			
			boolean demandeSave = false;
			enumerator = lesContraintes.objectEnumerator();
			while (enumerator.hasMoreElements()) {
				IpRelationChoixEc ctrte = (IpRelationChoixEc)enumerator.nextElement();
				try {
					RelationChoixExpression relExp=new RelationChoixExpression(ctrte,
							(NSDictionary)dictEcSems,
							maSession.defaultEditingContext());
					listeRel.addObject(relExp);
					if (relExp.majExpressionCompacte()) {
						demandeSave=true;
					}
				}
				catch (Exception e) {	// on ne peut pas générer cette relation (pb avec équation booléenne)
					System.out.println("err inscSemCtrl : "+e.getMessage());	// on signale et on passe à la suivante...
					e.printStackTrace();
				}	
			}			
			if (demandeSave) {
				maSession.commitChgt();
			}
		}
		// FIN BRICE

		// Pour tester : ajoute évaluation d'une contrainte sur les ec du semestre...
		//		String equationBooleenne = "(2519|2532)&(2519|2520)";
		//		RelationChoixExpression relExp=new RelationChoixExpression(equationBooleenne,
		//		(NSDictionary) dictEcSem,true);
		//		listeRel.addObject(relExp);

		lesRelationsEntreEc = (NSArray)listeRel;

	}

	public Integer getSemImpair() {
		return semImpair;
	}

	// Dire à combien on a droit d'EC facultatifs ce semestre... 
	public int getNbreEcFacultatifsAChoisir() {
		return nbreMaxEcFacultatifsAChoisir;
	}

	//  charger globalement toutes les insc° aux EC pour cette idipl_numero et imrec_semestre (ordre sur MREC_KEY)
	// ainsi que tous les choix aux EC à choix pour cette idipl_numero et imrec_semestre
	public void chargerInscEtChoixEc() {
		NSArray bindings = new NSArray(new Object[] {inscFormCt.idiplNumero(),semImpair,
				inscFormCt.msemKeyPP(),inscFormCt.msemKeyPC()});

		EOQualifier qualifier = EOQualifier.qualifierWithQualifierFormat(
				"idiplNumero = %@ and imrecSemestre = %@ and (msemKey = %@ or msemKey = %@)", bindings);

		EOSortOrdering rueOrdre = EOSortOrdering.sortOrderingWithKey("mrecKey",
				EOSortOrdering.CompareAscending);

		NSArray sortOrderings = new NSArray(new Object[] {rueOrdre});

		EOFetchSpecification fetchSpec = new EOFetchSpecification("ScolInscriptionEc",qualifier, sortOrderings);
		fetchSpec.setRefreshesRefetchedObjects(true);	// fait en sorte de refetcher des EOS déjà fetchés si besoin

		EOEditingContext ec = maSession.defaultEditingContext();

		listeInscEc = ec.objectsWithFetchSpecification(fetchSpec);
		dictInscEc = inventaireDict(listeInscEc,"mrecKey");

		fetchSpec = new EOFetchSpecification("IpChoixEc",qualifier, sortOrderings);
		fetchSpec.setRefreshesRefetchedObjects(true);	// fait en sorte de refetcher des EOS déjà fetchés si besoin

		listeChoixEc = ec.objectsWithFetchSpecification(fetchSpec);
		dictChoixEc = inventaireChoix(listeChoixEc,"mrecKey");

	}
	// charger globalement toutes les contraintes pour les EC de ce semestre...
	// puis les intégrer aux relations à vérifier sur les choix d'EC !
	private NSArray chargerContraintesEc() {
		System.out.println("Dans chargerContraintesEc");
		NSArray bindings = new NSArray(new Object[] {getMsemKey(),inscFormCt.msemKeyPC()});
		EOQualifier qualifier = EOQualifier.qualifierWithQualifierFormat("msemKey = %@ or msemKey = %@", bindings);
		// BRICE: on tente de charger toutes les contraintes pour voir ... suppression du qualifier
		// EOFetchSpecification fetchSpec = new EOFetchSpecification("IpRelationChoixEc",qualifier, null);
		EOFetchSpecification fetchSpec = new EOFetchSpecification("IpRelationChoixEc",null, null);
		fetchSpec.setRefreshesRefetchedObjects(true);	// fait en sorte de refetcher des EOS déjà fetchés si besoin
		EOEditingContext ec = maSession.defaultEditingContext();

		NSArray lesContraintes = ec.objectsWithFetchSpecification(fetchSpec);
		return lesContraintes;
		// l'init des RelationChoixExpression est faites dans "cataloguer()"
	}


	// création d'un dico à partir d'une liste, basé sur une clé donnée
	private NSDictionary inventaireDict(NSArray listeEO,String chpCle) {
		NSMutableDictionary dictTemp = new NSMutableDictionary();

		java.util.Enumeration enumerator = listeEO.objectEnumerator();
		while (enumerator.hasMoreElements()) {
			EOGenericRecord eo = (EOGenericRecord)enumerator.nextElement();
			Object cle = eo.valueForKey(chpCle);
			// TODO ; exception à traiter si clé n'existe pas ! 
			dictTemp.setObjectForKey(eo,cle);
		}
		return new NSDictionary(dictTemp);
	}

	// création d'un dico à partir d'une liste, basé sur une classe en particulier...
	private NSDictionary inventaireChoix(NSArray listeEO,String chpCle) {
		NSMutableDictionary dictTemp = new NSMutableDictionary();

		java.util.Enumeration enumerator = listeEO.objectEnumerator();
		while (enumerator.hasMoreElements()) {
			IpChoixEc choix = (IpChoixEc)enumerator.nextElement();
			Object cle = choix.valueForKey(chpCle);
			// TODO ; exception à traiter si clé n'existe pas ! 
			dictTemp.setObjectForKey(choix,cle);
		}
		return new NSDictionary(dictTemp);
	}

	public NSArray getLesUeCtrlr() {
		return listeUeCt;
	}

	// ATTENTION : appele a tour de role par chaque UECtrlr... la liste globale des EC se construit en +ieurs fois
	// terminer la creation des objets ctrlr d'EC... 
	// et constituer des dictionnaires d'EC globaux au semestre en cours (cle : MREC_KEY et aussi MEC_CODE !!!)
	// 
	public void cataloguer(NSArray listeEcCt) {
		Integer mrecKey;
		EOGenericRecord inscEc;
		IpChoixEc choixEc;
		InscEcCtrlr ecCT;

		java.util.Enumeration enumerator = listeEcCt.objectEnumerator();
		while (enumerator.hasMoreElements()) {
			ecCT = (InscEcCtrlr)enumerator.nextElement();
			mrecKey = ecCT.getMrecKey();
			inscEc = (EOGenericRecord)dictInscEc.objectForKey(mrecKey);	// y a t'il une inscription pour cet EC de la maquette ?
			if (inscEc != null) {
				ecCT.setInsc(inscEc);	// si oui la référencer dans le ctrlr d'EC...
			}
			else if (ecCT.isEcAChoix()) {		// si non on peut alors choisir cet EC si a choix (non bloqué)
				if (!ecCT.choixAMasquer()) {	// on scanne aussi si l'EC ne doit pas être masquée des choix possibles ("DIV_xxxx")
					choixEc = (IpChoixEc)dictChoixEc.objectForKey(mrecKey);	// y a t'il un choix déja enregistré ??
					if (choixEc != null) ecCT.setChoix(choixEc);	// si oui le référencer dans le ctrlr d'EC...
					listeEcCTaChoix.addObject(ecCT);	// encore un EC a choix modifiable...
				}
			}
			// Cet EC est-il facultatif ?
			if (ecCT.ecFacultatif()) {
				listeEcCTFacultatifs.addObject(ecCT);	// encore un EC facultatif...
			}
			dictEcSem.setObjectForKey(ecCT,mrecKey);	// on ajoute cette EC à la liste globale du semestre....
			ajouteEcDicoMecCode(ecCT);			// on MAJ le dico des listes d'EC de même code
		}

	}


	public boolean ipSurEc(Integer mrecKey) {
		if (dictInscEc.objectForKey(mrecKey) != null) return true;
		else return false;
		// Ci dessous : méthode WO 5.3.3 !!!
		// return (dictInscEc.containsKey(mrecKey));
	}

	// constituer un dico des listes d'EC portant le même MEC_CODE pour les vérifs à faire...
	private void ajouteEcDicoMecCode(InscEcCtrlr ecCT) {
		NSMutableArray sameCode;
		Integer mecKey = ecCT.getMecKey();
		// y a t'il déjà une EC enregistrée avec ce mecCode ?
		sameCode = (NSMutableArray)dictEcSameCode.objectForKey(mecKey);
		if (sameCode == null) 
			sameCode = new NSMutableArray(new Object[] {ecCT});
		else {
			sameCode.addObject(ecCT);	
		}
		dictEcSameCode.setObjectForKey(sameCode,mecKey);
	}

	public boolean modeModif() {
		return modeModif;
	}


	// On passe en mode modif : si toutes les UE sont "masquées", faire un tour des UE pour démasquer :
	// - soit toutes les UE ou il manque des inscr aux EC...
	// - soit la dernière UE ou il y a + d'ECTS pris que nécessaire...
	// - soit la dernière UE ou il y a des choix à faire.
	public void demanderModif(boolean global) {
		maSession.demarreDM();
		if (inscFormCt.modifPossible()) {
			modeModif = true;

			// méthode globale à la page...
			if (global) {
				boolean pasdUEincompletes = true;
				boolean toutesLesUeMasquees = true;
				InscUeCtrlr aChoix,tropDeChoix;
				aChoix = null; tropDeChoix = null;

				java.util.Enumeration enumerator = listeUeCt.objectEnumerator();
				while (enumerator.hasMoreElements()) {
					InscUeCtrlr ueCt = (InscUeCtrlr)enumerator.nextElement();
					if (ueCt.ueAvecChoix()) {	// UE avec des choix
						if (ueCt.ueDetaillee()) toutesLesUeMasquees = false;
						if (ueCt.ueIncomplete()) {	// UE ou il manque des choix d'EC
							//							if (pasdUEincompletes) ueCt.donnerFocus();	// donner le focus à la première
							pasdUEincompletes = false;
							ueCt.afficherDetails();
						}
						else if (ueCt.compareCumulEcts()>0) tropDeChoix = ueCt;	// UE avec trop de choix
						else aChoix = ueCt;
					}
				}
				if (pasdUEincompletes && toutesLesUeMasquees) {
					if (tropDeChoix != null) tropDeChoix.afficherDetails();
					else if (aChoix != null) aChoix.afficherDetails();
				}
			}		
		}
	}

	// une fois la modif terminée, "refermer" toutes les UE
	private void cleanUpUE() {
		java.util.Enumeration enumerator = listeUeCt.objectEnumerator();
		while (enumerator.hasMoreElements()) {
			InscUeCtrlr ueCt = (InscUeCtrlr)enumerator.nextElement();
			ueCt.masquerDetails();
		}
	}

	private void restaureLesUes() {
		java.util.Enumeration enumerator = listeUeCt.objectEnumerator();
		while (enumerator.hasMoreElements()) {
			InscUeCtrlr ueCt = (InscUeCtrlr)enumerator.nextElement();
			if (ueCt.ueAvecChoix()) {
				ueCt.resetErreur();			// en profiter pour faire un RAZ aussi au niveau UE !
				ueCt.annuleCumulCasesCochee();
			}
		}

	}

	public boolean verifierModif() {
		// parcours de la liste des EC à choix non bloqué...
		// pour chacun lui demander de faire sa vérif et ajuster ses codes erreurs...
		boolean yaPasDeProb=true;
		derniereUeAvecErreur="";

		// RAZ général des messages d'erreur
		java.util.Enumeration enumerator = listeEcCTaChoix.objectEnumerator();
		while (enumerator.hasMoreElements()) {
			InscEcCtrlr ecCT = (InscEcCtrlr)enumerator.nextElement();
			ecCT.enleveErreur();
		}

		// parcours des UE et demande de cumul sur les cases cochées
		enumerator = listeUeCt.objectEnumerator();
		while (enumerator.hasMoreElements()) {
			InscUeCtrlr ueCt = (InscUeCtrlr)enumerator.nextElement();
			if (ueCt.ueAvecChoix()) {
				ueCt.resetErreur();			// en profiter pour faire un RAZ aussi au niveau UE !
				ueCt.cumulCasesCochee();	// valeurs temporaires pour la vérif...
				if (ueCt.compareCumulEcts()==1) {	// trop d'EC pour cette UE !
					yaPasDeProb = false;
					derniereUeAvecErreur = ueCt.getUeKey().toString();
					ueCt.afficherDetails();	// au cas ou ce serait masqué...
				}
			}
		}

		// évaluation des relations... ON S'ARRETE DES LA PREMIERE ERREUR POUR NE PAS EN METTRE PARTOUT
		// (démarche = faire corriger une erreur après l'autre...)
		enumerator = lesRelationsEntreEc.objectEnumerator();
		while (enumerator.hasMoreElements()) {
			RelationChoixEc relEc = (RelationChoixEc)enumerator.nextElement();
			try {
				if (!relEc.verifierRelation()) {
					derniereUeAvecErreur = relEc.derniereUeAvecErreur();
					return false;
				}
			}
			catch (Exception e) {
				// pb avec cette relation, on la passe...
				if (maSession.debug()) System.out.println(e.getMessage());
				e.printStackTrace();

			}
		}
		// si on arrive ici c'est que les relations sont OK...
		// TODO : vérifier qu'il n'y a pas trop d'EC pour chaque UE... 

		return yaPasDeProb;
	}

	public String getDerniereUeAvecErreur() { return derniereUeAvecErreur; } 

	// annuler les modifs en cours...
	public void annulerModif() {
		// parcours de la liste des EC à choix non bloqué...
		// pour chacun lui demander de remettre à jour  son champ coché en fonction de son état antérieur...
		Enumeration enumerator = listeEcCTaChoix.objectEnumerator();
		while (enumerator.hasMoreElements()) {
			InscEcCtrlr ecCT = (InscEcCtrlr)enumerator.nextElement();
			ecCT.annulerModif();
		}
		modeModif = false;
		// enlever les cumuls d'ECTS temporaire des UEs
		restaureLesUes();
		cleanUpUE();
		maSession.arreteDM();
	}

	public boolean validerModif(InscUeCtrlr ueCtFocus) {
		// On suppose que la modif est bien possible (on s'rait pas la sinon)
		//--> phase de vérif à faire d'abord et validation si tout est OK seulement !!!!
		if (!verifierModif()) return false;

		// MAJ les choix d'EC à masquer si besoin... 
		java.util.Enumeration enumerator = lesRelationsEntreEc.objectEnumerator();
		while (enumerator.hasMoreElements()) {
			RelationChoixEc relEc = (RelationChoixEc)enumerator.nextElement();
			if (relEc.choixIncoherentCoche()) 
				relEc.scannerChoixAMasquer();
		}

		// parcours de la liste des EC à choix non bloqué...
		// pour chacun lui demander de mettre à jour la base en fonction de la modif de son état par l'utilisateur
		enumerator = listeEcCTaChoix.objectEnumerator();
		while (enumerator.hasMoreElements()) {
			InscEcCtrlr ecCT = (InscEcCtrlr)enumerator.nextElement();
			ecCT.validerModif();
		}

		// demander à chaque UeCtrlr de raffraichir sa liste... sauf si aucune EC à choix dispo pour cette UE
		cumECTS = 0;

		if (listeUeCt != null && listeUeCt.count()>0) {
			nbUeIncompletes = 0;
			enumerator = listeUeCt.objectEnumerator();
			while (enumerator.hasMoreElements()) {
				InscUeCtrlr ueCt = (InscUeCtrlr)enumerator.nextElement();
				cumECTS += ueCt.cumulEctsUe();	// cumul des points ECTS du diplôme...
				if (ueCt.ueAvecChoix()) {
					ueCt.extraireListeEcIp();
					if (ueCt.ueOuverte() && ueCt.compareCumulEcts()<0) {
						nbUeIncompletes++;
						derniereUeIncomplete = ueCt.getUeKey().toString();
					}
				}
			}
			if (nbUeIncompletes == 0) inscFormCt.setSemestreComplet(true);
			else inscFormCt.setSemestreComplet(false);
		}

		if (maSession.changementChoix()) {
			// Mettre à jour les stats pour cet étudiant + ce semestre...
			majIpSemStatUeEcts();
		}

		// valider les changements dans l'Editing Context...
		maSession.commitChgt();
		modeModif = false;  
		cleanUpUE();
		maSession.arreteDM();
		return true;
	}

	// Mettre à jour ipSemStat et en particulier le nb d'UE ou il manque des choix, et le cumul de pt ECTS 
	private void majIpSemStatUeEcts()  {
		inscFormCt.majIpSemStatUeEcts(cumECTS, nbUeIncompletes);
	}


	// message à afficher en haut du formulaire pour indiquer ce qui va pas (récap au niveau semestre)
	public String statutInscSemestre() {
		if (nbUeIncompletes>0) return "Il reste des choix p&eacute;dagogiques &agrave; faire ";
		else // return "Toutes les UE sont compl&egrave;tes.";
		{
			if (!validationChoixParEtudiant)
				return "Vos choix pédagogiques pour ce semestre sont complets <b>mais pas encore <u>confirm&eacute;s</u> !</b>";
			else
				return "Vos choix pédagogiques pour ce semestre sont <b>complets <u>et confirm&eacute;s</u>.</b>";
		}
	}	

	// indic pour le WoComponents : cas ou les choix sont complets MAIS pas validés...
	public boolean choixCompletsPasValides() {
		return ((nbUeIncompletes==0) && !validationChoixParEtudiant);
	}

	// indic pour le WoComponents : cas ou les choix sont complets ET validés...
	public boolean choixCompletsValides() {
		return ((nbUeIncompletes==0) && validationChoixParEtudiant);
	}

	public boolean resteUeIncomplete() { 
		return (nbUeIncompletes>0); 
	}

	public String stNbreUeIncomplete() {
		if (modeModif) return ("<A HREF=\"#"+derniereUeIncomplete+"\">pour "+nbUeIncompletes+" UE.<A>");
		else return ("pour "+nbUeIncompletes+" UE !");
	}

	// couleur du message...
	public String coulStatutInscSemestre() {
		if (nbUeIncompletes>0) return "#ED1C24";
		else if (nbUeIncompletes==0 && !validationChoixParEtudiant) return "#c000f2";
		else return "#027d2f";
	}

	/**
	 * @return the semestreDejaObtenu
	 */
	public boolean semestreDejaObtenu() {
		return inscFormCt.semestreDejaObtenu();
	}

	/**
	 * @return the ipPasEncoreOuvertes
	 */
	public boolean ipPasEncoreOuvertes() {
		return inscFormCt.ipPasEncoreOuvertes();
	}

	public boolean ipTerminees() {
		return inscFormCt.ipTerminees();
	}

	public boolean redoublantBloque() {
		return inscFormCt.redoublantBloque();
	}

	//	************** Gestion de l'impression de la fiche IP du semestre via JASPER...
	// reponse au clic sur l'icone "PDF"
	public NSData imprChoixPedag(String diplSem,int semOrdre){
		// constituer liste des parametres...
		String nomPrenom=inscFormCt.prenomNomEtud();
		String genre = inscFormCt.genreEtud();
		String nomEtab = maSession.monApp.nomEtablissement();
		//String nomEtab = "Université l'ondulèêé";

		// NSLog.out.appendln("************************ Nom etab à imprimer =   |"+ maSession.monApp.nomEtablissement()+"|");

		HashMap parametres = new HashMap();
		parametres.put("NOM_PRENOM", nomPrenom);
		parametres.put("DIPL_SEMESTRE", diplSem);
		parametres.put("ANNEE_UNIV", inscFormCt.anneeUniv());
		parametres.put("GENRE",genre); 
		parametres.put("ETABLISSEMENT",nomEtab); 

		// Si on est en mode BackOffice, imprimer un report avec partie attestation...
		// sinon report des choix pedag habituel...

		String nomReport = "choixPedagogiques.jasper";

		if (maSession.modeBackOffice())  parametres.put("IMPR_BASPAGE",new Boolean(true)) ;
		else parametres.put("IMPR_BASPAGE",new Boolean(false)) ;

		NSData res = maSession.imprimePDFavecDataSource(nomReport, 
				parametres,
				new JRDataChoixEtud(this));
		return res;
		// créer une source de données Jaser basée sur les objets de ce controleur !
	}

	private Enumeration monEnum;
	private InscUeCtrlr jrUeCt=null;

	public void resetBoucle() {
		//		System.out.println("> premiere UE");
		monEnum = listeUeCt.objectEnumerator();
	}

	public boolean nextElement() {
		//		System.out.println("> next UE");
		// premier élément ?
		boolean encoreDesElements;
		if (jrUeCt==null) {
			System.out.println("Debut boucle des UE");
			jrUeCt = (InscUeCtrlr)monEnum.nextElement();
			jrUeCt.resetBoucle();
			encoreDesElements = true;
		}
		// attention... on parcourt des listes imbriquées !
		else {
			encoreDesElements = jrUeCt.nextElement();
			if (!encoreDesElements && (monEnum.hasMoreElements())) {
				jrUeCt = (InscUeCtrlr)monEnum.nextElement();
				//				System.out.println("Passage à l'UE suivant : " + jrUeCt.getNomUe());
				encoreDesElements = jrUeCt.resetBoucle();;
			}
		}
		return (encoreDesElements | (monEnum.hasMoreElements()));
	}

	public Object fetchJRChamp(String jrName) {
		Object res = null;
		if (jrName.equals("TITRE_UE")) 
			res = jrUeCt.getNomUe()+" ("+jrUeCt.getCodeUe()+") :"+
					jrUeCt.getPointsUe()+" points ECTS";
		else if (jrName.equals("NUM_UE"))
			res = new Integer(jrUeCt.getOrdreUe());
		else if (jrName.equals("REM_UE"))
			res = jrUeCt.getRemUe();
		else res = jrUeCt.fetchJRChamp(jrName);
		//		if (res==null) System.out.println(">> "+jrName+" = NULL");
		//		else System.out.println(">> "+jrName+" = "+res.toString());
		return res;
	}

	/**
	 * @return the urgenceSignalee
	 */
	public boolean isUrgenceSignalee() {
		return inscFormCt.urgenceSignalee();
	}


	//	************** Fin Gestion de l'impression de la fiche IP du semestre via JASPER...
}