package org.cocktail.ipweb.serveur.controlleur;
import java.util.Enumeration;

import org.cocktail.ipweb.serveur.Session;
import org.cocktail.ipweb.serveur.components.onglets.Droit;
import org.cocktail.ipweb.serveur.components.onglets.FonctionsCtrlr;
import org.cocktail.ipweb.serveur.components.onglets.OngletsCtrlr;
import org.cocktail.ipweb.serveur.metier.IpUeFermee;

import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOGenericRecord;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSKeyValueCoding;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;

import er.extensions.eof.qualifiers.ERXInQualifier;
import er.extensions.qualifiers.ERXNotQualifier;


/**
 * @author olive
 * @version 1.0
 * @category gestion du ctrlr d'autorisation des RN par diplome/semestre/parcours...  
 *
 */
public class FermetureCtrlr implements NSKeyValueCoding {
	private static String fonction = "MODIF_AUTORN_DIPL"; 
	private static String RN_AUTOR = "RN_AUTORISE";
	private static String[] listeFonctions = new String[] {RN_AUTOR};

	private Session maSession;
	private FonctionsCtrlr ctlFonctions;
	private String cptLogin;

	public NSArray<IpUeFermee> listeUEouvertes, listeUEfermees;
	private NSMutableDictionary<Integer,EOGenericRecord> listeEOipUEfermees;

	public Integer fspnKey;
	private NSMutableArray listeScolUE;
	private NSArray listeSemUE;
	private NSArray<IpUeFermee> listeIpUeFermees;


	public FermetureCtrlr(Session sess) {
		listeEOipUEfermees = new NSMutableDictionary();
		maSession = sess;
		OngletsCtrlr mesOngCt = maSession.getMesOnglets();
		ctlFonctions = new FonctionsCtrlr(mesOngCt,listeFonctions);
		cptLogin = mesOngCt.cptLogin();	// on obtient le code de l'utilisateur dans ScolPeda depuis 
		// le controleur d'onglet (récupéré lors de son init)
	}
	
    
    public void initListeUE(Integer newFspnKey) {
    	if (fspnKey != newFspnKey) {
    		fspnKey = newFspnKey;
    		chargerUEFermees();

    		if (listeIpUeFermees!= null && listeIpUeFermees.count()>0) {
        		
        		Enumeration e = listeIpUeFermees.objectEnumerator();
        		while (e.hasMoreElements())  {
        			IpUeFermee eoIpUeFermee = (IpUeFermee)e.nextElement(); 
        			Integer mueKey = (Integer)eoIpUeFermee.mueKey();
        			
        			listeEOipUEfermees.setObjectForKey(eoIpUeFermee, mueKey);
        		}
        	}
    		
    	}
    }
    

	public Integer getFannKey() { return new Integer(maSession.getAnneeEnCours()); }

	//   public Integer getDlogKey() { return dlogKey; }
	public String getCptLogin() { return cptLogin; }

	public boolean isModifAutorisee(int noSem) {
		int noAnnee = (noSem+1)/2;
		String diplAnnee = fspnKey + "-" + noAnnee;
		int droit = ctlFonctions.droitsPourFonctionEtDiplome(fonction,diplAnnee);
		if (droit == Droit.DROIT_MODIF) return true;
		else return false;
	}


	// *******************************

	// Pour le NSKeyValueCoding :
	public void takeValueForKey(Object arg0, String arg1) {
		// on s'en sert pas...
	}

	// on accède aux variables d'instances en direct !!!
	public Object valueForKey(String key) {
		return NSKeyValueCoding.DefaultImplementation.valueForKey(this, key);
	}

	public NSArray listeSemUE() {
		EOEditingContext unEc = maSession.defaultEditingContext();

		EOSortOrdering ordre1 = EOSortOrdering.sortOrderingWithKey("msemOrdre",EOSortOrdering.CompareAscending);
		EOSortOrdering ordre2 = EOSortOrdering.sortOrderingWithKey("mparLibelle",EOSortOrdering.CompareAscending);
		NSArray eoSortOrderings = new NSArray(new Object[] {ordre1, ordre2});

		String chaineQualif = "cptLogin = %@ and fannKey = %@ and fspnKey= %@";
		String nomEntiteSem = "VSemParcoursDroits";

		NSArray paramsSem = new NSArray(new Object[] {cptLogin, getFannKey(), fspnKey});
		NSArray bindingsSem = new NSArray(paramsSem);
		EOQualifier qualifier = EOQualifier.qualifierWithQualifierFormat(chaineQualif, bindingsSem);
		EOFetchSpecification fetchSpec = new EOFetchSpecification(nomEntiteSem,qualifier, eoSortOrderings);

		fetchSpec.setRefreshesRefetchedObjects(true);

		NSArray<EOGenericRecord> res = unEc.objectsWithFetchSpecification(fetchSpec);

		// On récupère les MUEKEY des semestres récupérés
		EOSortOrdering ordre = EOSortOrdering.sortOrderingWithKey("mueCode",EOSortOrdering.CompareAscending);
		eoSortOrderings = new NSArray(new Object[] {ordre});

		String nomEntiteUE = "VMaqSemestreUeChoix";

		EOQualifier qualUE = new ERXInQualifier("msemKey", (NSArray)res.valueForKey("msemKey"));
		fetchSpec = new EOFetchSpecification(nomEntiteUE,qualUE, eoSortOrderings);
		fetchSpec.setRefreshesRefetchedObjects(true);

		listeSemUE = unEc.objectsWithFetchSpecification(fetchSpec);

		return listeSemUE;
	}


	public NSArray listeScolUE (Integer fspnKey) {

		if (fspnKey != this.fspnKey || listeScolUE != null) {
			this.fspnKey = fspnKey;
			System.out.println("--------------------- fspnKey : " + fspnKey);
			
			EOEditingContext unEc = maSession.defaultEditingContext();

			// On récupère les ScolMaquetteUE des VMaqSemestreUeChoix récupérés

			String nomEntiteScolUE = "ScolMaquetteUe";

			EOSortOrdering ordre = EOSortOrdering.sortOrderingWithKey("mueCode",EOSortOrdering.CompareAscending);
			NSArray eoSortOrderings = new NSArray(new Object[] {ordre});

			EOQualifier qualScolUE = new ERXInQualifier("mueKey", (NSArray)listeSemUE().valueForKey("mueKey"));
			EOFetchSpecification fetchSpec = new EOFetchSpecification(nomEntiteScolUE,qualScolUE, eoSortOrderings);
			fetchSpec = new EOFetchSpecification(nomEntiteScolUE,qualScolUE, eoSortOrderings);
			fetchSpec.setRefreshesRefetchedObjects(true);
			fetchSpec.setUsesDistinct(true);

			NSArray tmp = unEc.objectsWithFetchSpecification(fetchSpec);

			NSMutableArray  retour = new NSMutableArray();
			retour.addObjectsFromArray(tmp);

			listeScolUE = retour;
		}
		return listeScolUE;
	}

	// Préviens de charger la liste des UE fermées ... et celle ouvertes, pour l'année en cours
	// à chaque refetch, on raffraichit avec les datas de la base...
	public void chargerUEFermees() {
		EOEditingContext ec = maSession.defaultEditingContext();

		listeSemUE();
		
		EOQualifier qualifier = new ERXInQualifier("mueKey", (NSArray)listeSemUE.valueForKey("mueKey"));

		EOSortOrdering ordre = EOSortOrdering.sortOrderingWithKey("mueKey",EOSortOrdering.CompareAscending);
		NSArray sortOrderings = new NSArray(new Object[] {ordre});

		// la liste des UE fermées
		EOFetchSpecification fs = new EOFetchSpecification("IpUeFermees",qualifier, sortOrderings);
		listeIpUeFermees = ec.objectsWithFetchSpecification(fs);  

		// La liste des ScolMaquetteUE correspondantes
		EOQualifier qualScolUE = new ERXInQualifier("mueKey", (NSArray)listeIpUeFermees.valueForKey("mueKey"));
		EOFetchSpecification fetchSpec = new EOFetchSpecification("ScolMaquetteUe",qualScolUE, sortOrderings);
		fetchSpec.setRefreshesRefetchedObjects(true);

		listeUEfermees = ec.objectsWithFetchSpecification(fetchSpec);  

		// la liste des ScolMaquetteUE ouvertes
		NSMutableArray<EOQualifier> quals = new NSMutableArray<EOQualifier>();
		quals.add(new ERXNotQualifier(new ERXInQualifier("mueKey", (NSArray)listeIpUeFermees.valueForKey("mueKey"))));
		quals.add(qualifier);
		fetchSpec = new EOFetchSpecification("ScolMaquetteUe",new EOAndQualifier(quals), sortOrderings);
		fetchSpec.setRefreshesRefetchedObjects(true);

		listeUEouvertes = ec.objectsWithFetchSpecification(fetchSpec);

	}

	// ON veut ajouter ces UE à la liste des UE fermées ...
	public void ajouterRestriction(NSArray listeUE) {
		EOEditingContext ec = maSession.defaultEditingContext();
		boolean travail = false;

		Enumeration e = listeUE.objectEnumerator();
		while (e.hasMoreElements()) {
			EOGenericRecord ueSel = (EOGenericRecord)e.nextElement();
			//    		NSLog.out.appendln("A virer : "+diplSel.valueForKey("diplome"));

			// Pas déjà ?
			Integer mueKey = (Integer)ueSel.valueForKey("mueKey");
			if (listeEOipUEfermees.objectForKey(mueKey) == null) {

				IpUeFermee eoAAjouter = new IpUeFermee();
				eoAAjouter.setFannKey((Number)ueSel.valueForKey("fannKey"));
				eoAAjouter.setMueKey((Number)mueKey);
				travail = true;
				ec.insertObject(eoAAjouter);
				listeEOipUEfermees.setObjectForKey(eoAAjouter, mueKey);
			}
		}
		if (travail) {
			ec.saveChanges();
			chargerUEFermees();
		}
	}

	// ON veut enlever ces UE de la liste des UE fermées ...
	public void enleverRestriction(NSArray listeDipl) {
		EOEditingContext ec = maSession.defaultEditingContext();
		boolean travail = false;
System.out.println("------------------------------ listeDipl : " + listeDipl);
		Enumeration e = listeDipl.objectEnumerator();
		while (e.hasMoreElements()) {
			EOGenericRecord diplSel = (EOGenericRecord)e.nextElement();
			System.out.println("A enlever : "+diplSel.valueForKey("mueKey") + " listeEOipUEfermees : " + listeEOipUEfermees);

			if (listeEOipUEfermees != null) {
				IpUeFermee eoAVirer = (IpUeFermee)listeEOipUEfermees.removeObjectForKey((Integer)diplSel.valueForKey("mueKey"));
				System.out.println("eoAVirer : "+eoAVirer);
				if (eoAVirer != null) {
					ec.deleteObject(eoAVirer);
					travail = true;
				}
			}
		}
		if (travail) {
			ec.saveChanges();
			chargerUEFermees();
		}
	}

}
