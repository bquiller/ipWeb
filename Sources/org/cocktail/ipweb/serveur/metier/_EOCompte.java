// $LastChangedRevision$ DO NOT EDIT.  Make changes to EOCompte.java instead.
package org.cocktail.ipweb.serveur.metier;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public abstract class _EOCompte extends  EOGenericRecord {
	public static final String ENTITY_NAME = "IpwCompte";

	// Attributes
	public static final String CPT_CHARTE_KEY = "cptCharte";
	public static final String CPT_CONNEXION_KEY = "cptConnexion";
	public static final String CPT_DOMAINE_KEY = "cptDomaine";
	public static final String CPT_EMAIL_KEY = "cptEmail";
	public static final String CPT_LOGIN_KEY = "cptLogin";
	public static final String CPT_PASSWD_KEY = "cptPasswd";
	public static final String CPT_UID_GID_KEY = "cptUidGid";
	public static final String CPT_VLAN_KEY = "cptVlan";
	public static final String D_CREATION_KEY = "dCreation";
	public static final String D_MODIFICATION_KEY = "dModification";
	public static final String VLANS_PRIORITE_KEY = "vlans_priorite";

	// Relationships
	public static final String TO_RPT_COMPTE_KEY = "toRptCompte";
	public static final String VLANS_KEY = "vlans";

  private static Logger LOG = Logger.getLogger(_EOCompte.class);

  public EOCompte localInstanceIn(EOEditingContext editingContext) {
    EOCompte localInstance = (EOCompte)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public String cptCharte() {
    return (String) storedValueForKey("cptCharte");
  }

  public void setCptCharte(String value) {
    if (_EOCompte.LOG.isDebugEnabled()) {
    	_EOCompte.LOG.debug( "updating cptCharte from " + cptCharte() + " to " + value);
    }
    takeStoredValueForKey(value, "cptCharte");
  }

  public String cptConnexion() {
    return (String) storedValueForKey("cptConnexion");
  }

  public void setCptConnexion(String value) {
    if (_EOCompte.LOG.isDebugEnabled()) {
    	_EOCompte.LOG.debug( "updating cptConnexion from " + cptConnexion() + " to " + value);
    }
    takeStoredValueForKey(value, "cptConnexion");
  }

  public String cptDomaine() {
    return (String) storedValueForKey("cptDomaine");
  }

  public void setCptDomaine(String value) {
    if (_EOCompte.LOG.isDebugEnabled()) {
    	_EOCompte.LOG.debug( "updating cptDomaine from " + cptDomaine() + " to " + value);
    }
    takeStoredValueForKey(value, "cptDomaine");
  }

  public String cptEmail() {
    return (String) storedValueForKey("cptEmail");
  }

  public void setCptEmail(String value) {
    if (_EOCompte.LOG.isDebugEnabled()) {
    	_EOCompte.LOG.debug( "updating cptEmail from " + cptEmail() + " to " + value);
    }
    takeStoredValueForKey(value, "cptEmail");
  }

  public String cptLogin() {
    return (String) storedValueForKey("cptLogin");
  }

  public void setCptLogin(String value) {
    if (_EOCompte.LOG.isDebugEnabled()) {
    	_EOCompte.LOG.debug( "updating cptLogin from " + cptLogin() + " to " + value);
    }
    takeStoredValueForKey(value, "cptLogin");
  }

  public String cptPasswd() {
    return (String) storedValueForKey("cptPasswd");
  }

  public void setCptPasswd(String value) {
    if (_EOCompte.LOG.isDebugEnabled()) {
    	_EOCompte.LOG.debug( "updating cptPasswd from " + cptPasswd() + " to " + value);
    }
    takeStoredValueForKey(value, "cptPasswd");
  }

  public Double cptUidGid() {
    return (Double) storedValueForKey("cptUidGid");
  }

  public void setCptUidGid(Double value) {
    if (_EOCompte.LOG.isDebugEnabled()) {
    	_EOCompte.LOG.debug( "updating cptUidGid from " + cptUidGid() + " to " + value);
    }
    takeStoredValueForKey(value, "cptUidGid");
  }

  public String cptVlan() {
    return (String) storedValueForKey("cptVlan");
  }

  public void setCptVlan(String value) {
    if (_EOCompte.LOG.isDebugEnabled()) {
    	_EOCompte.LOG.debug( "updating cptVlan from " + cptVlan() + " to " + value);
    }
    takeStoredValueForKey(value, "cptVlan");
  }

  public NSTimestamp dCreation() {
    return (NSTimestamp) storedValueForKey("dCreation");
  }

  public void setDCreation(NSTimestamp value) {
    if (_EOCompte.LOG.isDebugEnabled()) {
    	_EOCompte.LOG.debug( "updating dCreation from " + dCreation() + " to " + value);
    }
    takeStoredValueForKey(value, "dCreation");
  }

  public NSTimestamp dModification() {
    return (NSTimestamp) storedValueForKey("dModification");
  }

  public void setDModification(NSTimestamp value) {
    if (_EOCompte.LOG.isDebugEnabled()) {
    	_EOCompte.LOG.debug( "updating dModification from " + dModification() + " to " + value);
    }
    takeStoredValueForKey(value, "dModification");
  }

  public Double vlans_priorite() {
    return (Double) storedValueForKey("vlans_priorite");
  }

  public void setVlans_priorite(Double value) {
    if (_EOCompte.LOG.isDebugEnabled()) {
    	_EOCompte.LOG.debug( "updating vlans_priorite from " + vlans_priorite() + " to " + value);
    }
    takeStoredValueForKey(value, "vlans_priorite");
  }

  public EOGenericRecord vlans() {
    return (EOGenericRecord)storedValueForKey("vlans");
  }

  public void setVlansRelationship(EOGenericRecord value) {
    if (_EOCompte.LOG.isDebugEnabled()) {
      _EOCompte.LOG.debug("updating vlans from " + vlans() + " to " + value);
    }
    if (value == null) {
    	EOGenericRecord oldValue = vlans();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "vlans");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "vlans");
    }
  }
  
  public NSArray<org.cocktail.ipweb.serveur.metier.EORepartCompte> toRptCompte() {
    return (NSArray<org.cocktail.ipweb.serveur.metier.EORepartCompte>)storedValueForKey("toRptCompte");
  }

  public NSArray<org.cocktail.ipweb.serveur.metier.EORepartCompte> toRptCompte(EOQualifier qualifier) {
    return toRptCompte(qualifier, null, false);
  }

  public NSArray<org.cocktail.ipweb.serveur.metier.EORepartCompte> toRptCompte(EOQualifier qualifier, boolean fetch) {
    return toRptCompte(qualifier, null, fetch);
  }

  public NSArray<org.cocktail.ipweb.serveur.metier.EORepartCompte> toRptCompte(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings, boolean fetch) {
    NSArray<org.cocktail.ipweb.serveur.metier.EORepartCompte> results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.ipweb.serveur.metier.EORepartCompte.TO_COMPTE_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.ipweb.serveur.metier.EORepartCompte.fetchIpwRepartComptes(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = toRptCompte();
      if (qualifier != null) {
        results = (NSArray<org.cocktail.ipweb.serveur.metier.EORepartCompte>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<org.cocktail.ipweb.serveur.metier.EORepartCompte>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToToRptCompteRelationship(org.cocktail.ipweb.serveur.metier.EORepartCompte object) {
    if (_EOCompte.LOG.isDebugEnabled()) {
      _EOCompte.LOG.debug("adding " + object + " to toRptCompte relationship");
    }
    addObjectToBothSidesOfRelationshipWithKey(object, "toRptCompte");
  }

  public void removeFromToRptCompteRelationship(org.cocktail.ipweb.serveur.metier.EORepartCompte object) {
    if (_EOCompte.LOG.isDebugEnabled()) {
      _EOCompte.LOG.debug("removing " + object + " from toRptCompte relationship");
    }
    removeObjectFromBothSidesOfRelationshipWithKey(object, "toRptCompte");
  }

  public org.cocktail.ipweb.serveur.metier.EORepartCompte createToRptCompteRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("IpwRepartCompte");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "toRptCompte");
    return (org.cocktail.ipweb.serveur.metier.EORepartCompte) eo;
  }

  public void deleteToRptCompteRelationship(org.cocktail.ipweb.serveur.metier.EORepartCompte object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "toRptCompte");
    editingContext().deleteObject(object);
  }

  public void deleteAllToRptCompteRelationships() {
    Enumeration objects = toRptCompte().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteToRptCompteRelationship((org.cocktail.ipweb.serveur.metier.EORepartCompte)objects.nextElement());
    }
  }


  public static EOCompte createIpwCompte(EOEditingContext editingContext, String cptCharte
, String cptConnexion
, Double cptUidGid
, String cptVlan
, NSTimestamp dCreation
, NSTimestamp dModification
) {
    EOCompte eo = (EOCompte) EOUtilities.createAndInsertInstance(editingContext, _EOCompte.ENTITY_NAME);    
		eo.setCptCharte(cptCharte);
		eo.setCptConnexion(cptConnexion);
		eo.setCptUidGid(cptUidGid);
		eo.setCptVlan(cptVlan);
		eo.setDCreation(dCreation);
		eo.setDModification(dModification);
    return eo;
  }

  public static NSArray<EOCompte> fetchAllIpwComptes(EOEditingContext editingContext) {
    return _EOCompte.fetchAllIpwComptes(editingContext, null);
  }

  public static NSArray<EOCompte> fetchAllIpwComptes(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _EOCompte.fetchIpwComptes(editingContext, null, sortOrderings);
  }

  public static NSArray<EOCompte> fetchIpwComptes(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_EOCompte.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<EOCompte> eoObjects = (NSArray<EOCompte>)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static EOCompte fetchIpwCompte(EOEditingContext editingContext, String keyName, Object value) {
    return _EOCompte.fetchIpwCompte(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static EOCompte fetchIpwCompte(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<EOCompte> eoObjects = _EOCompte.fetchIpwComptes(editingContext, qualifier, null);
    EOCompte eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (EOCompte)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one IpwCompte that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static EOCompte fetchRequiredIpwCompte(EOEditingContext editingContext, String keyName, Object value) {
    return _EOCompte.fetchRequiredIpwCompte(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static EOCompte fetchRequiredIpwCompte(EOEditingContext editingContext, EOQualifier qualifier) {
    EOCompte eoObject = _EOCompte.fetchIpwCompte(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no IpwCompte that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static EOCompte localInstanceIn(EOEditingContext editingContext, EOCompte eo) {
    EOCompte localInstance = (eo == null) ? null : (EOCompte)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
