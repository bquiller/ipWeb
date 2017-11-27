// $LastChangedRevision$ DO NOT EDIT.  Make changes to ScolMaquetteAp.java instead.
package org.cocktail.ipweb.serveur.metier.scol;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public abstract class _ScolMaquetteAp extends  EOGenericRecord {
	public static final String ENTITY_NAME = "ScolMaquetteAp";

	// Attributes
	public static final String FANN_KEY_KEY = "fannKey";
	public static final String MAP_GROUPE_PREVU_KEY = "mapGroupePrevu";
	public static final String MAP_GROUPE_REEL_KEY = "mapGroupeReel";
	public static final String MAP_LIBELLE_KEY = "mapLibelle";
	public static final String MAP_SEUIL_KEY = "mapSeuil";
	public static final String MAP_VALEUR_KEY = "mapValeur";
	public static final String MHCO_CODE_KEY = "mhcoCode";

	// Relationships
	public static final String SCOL_MAQUETTE_CHARGES_AP_KEY = "scolMaquetteChargesAp";
	public static final String SCOL_MAQUETTE_REPARTITION_APS_KEY = "scolMaquetteRepartitionAps";

  private static Logger LOG = Logger.getLogger(_ScolMaquetteAp.class);

  public ScolMaquetteAp localInstanceIn(EOEditingContext editingContext) {
    ScolMaquetteAp localInstance = (ScolMaquetteAp)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public Integer fannKey() {
    return (Integer) storedValueForKey("fannKey");
  }

  public void setFannKey(Integer value) {
    if (_ScolMaquetteAp.LOG.isDebugEnabled()) {
    	_ScolMaquetteAp.LOG.debug( "updating fannKey from " + fannKey() + " to " + value);
    }
    takeStoredValueForKey(value, "fannKey");
  }

  public Integer mapGroupePrevu() {
    return (Integer) storedValueForKey("mapGroupePrevu");
  }

  public void setMapGroupePrevu(Integer value) {
    if (_ScolMaquetteAp.LOG.isDebugEnabled()) {
    	_ScolMaquetteAp.LOG.debug( "updating mapGroupePrevu from " + mapGroupePrevu() + " to " + value);
    }
    takeStoredValueForKey(value, "mapGroupePrevu");
  }

  public Integer mapGroupeReel() {
    return (Integer) storedValueForKey("mapGroupeReel");
  }

  public void setMapGroupeReel(Integer value) {
    if (_ScolMaquetteAp.LOG.isDebugEnabled()) {
    	_ScolMaquetteAp.LOG.debug( "updating mapGroupeReel from " + mapGroupeReel() + " to " + value);
    }
    takeStoredValueForKey(value, "mapGroupeReel");
  }

  public String mapLibelle() {
    return (String) storedValueForKey("mapLibelle");
  }

  public void setMapLibelle(String value) {
    if (_ScolMaquetteAp.LOG.isDebugEnabled()) {
    	_ScolMaquetteAp.LOG.debug( "updating mapLibelle from " + mapLibelle() + " to " + value);
    }
    takeStoredValueForKey(value, "mapLibelle");
  }

  public Integer mapSeuil() {
    return (Integer) storedValueForKey("mapSeuil");
  }

  public void setMapSeuil(Integer value) {
    if (_ScolMaquetteAp.LOG.isDebugEnabled()) {
    	_ScolMaquetteAp.LOG.debug( "updating mapSeuil from " + mapSeuil() + " to " + value);
    }
    takeStoredValueForKey(value, "mapSeuil");
  }

  public java.math.BigDecimal mapValeur() {
    return (java.math.BigDecimal) storedValueForKey("mapValeur");
  }

  public void setMapValeur(java.math.BigDecimal value) {
    if (_ScolMaquetteAp.LOG.isDebugEnabled()) {
    	_ScolMaquetteAp.LOG.debug( "updating mapValeur from " + mapValeur() + " to " + value);
    }
    takeStoredValueForKey(value, "mapValeur");
  }

  public String mhcoCode() {
    return (String) storedValueForKey("mhcoCode");
  }

  public void setMhcoCode(String value) {
    if (_ScolMaquetteAp.LOG.isDebugEnabled()) {
    	_ScolMaquetteAp.LOG.debug( "updating mhcoCode from " + mhcoCode() + " to " + value);
    }
    takeStoredValueForKey(value, "mhcoCode");
  }

  public NSArray<org.cocktail.ipweb.serveur.metier.scol.ScolMaquetteChargesAp> scolMaquetteChargesAp() {
    return (NSArray<org.cocktail.ipweb.serveur.metier.scol.ScolMaquetteChargesAp>)storedValueForKey("scolMaquetteChargesAp");
  }

  public NSArray<org.cocktail.ipweb.serveur.metier.scol.ScolMaquetteChargesAp> scolMaquetteChargesAp(EOQualifier qualifier) {
    return scolMaquetteChargesAp(qualifier, null);
  }

  public NSArray<org.cocktail.ipweb.serveur.metier.scol.ScolMaquetteChargesAp> scolMaquetteChargesAp(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    NSArray<org.cocktail.ipweb.serveur.metier.scol.ScolMaquetteChargesAp> results;
      results = scolMaquetteChargesAp();
      if (qualifier != null) {
        results = (NSArray<org.cocktail.ipweb.serveur.metier.scol.ScolMaquetteChargesAp>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<org.cocktail.ipweb.serveur.metier.scol.ScolMaquetteChargesAp>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToScolMaquetteChargesApRelationship(org.cocktail.ipweb.serveur.metier.scol.ScolMaquetteChargesAp object) {
    if (_ScolMaquetteAp.LOG.isDebugEnabled()) {
      _ScolMaquetteAp.LOG.debug("adding " + object + " to scolMaquetteChargesAp relationship");
    }
    addObjectToBothSidesOfRelationshipWithKey(object, "scolMaquetteChargesAp");
  }

  public void removeFromScolMaquetteChargesApRelationship(org.cocktail.ipweb.serveur.metier.scol.ScolMaquetteChargesAp object) {
    if (_ScolMaquetteAp.LOG.isDebugEnabled()) {
      _ScolMaquetteAp.LOG.debug("removing " + object + " from scolMaquetteChargesAp relationship");
    }
    removeObjectFromBothSidesOfRelationshipWithKey(object, "scolMaquetteChargesAp");
  }

  public org.cocktail.ipweb.serveur.metier.scol.ScolMaquetteChargesAp createScolMaquetteChargesApRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ScolMaquetteChargesAp");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "scolMaquetteChargesAp");
    return (org.cocktail.ipweb.serveur.metier.scol.ScolMaquetteChargesAp) eo;
  }

  public void deleteScolMaquetteChargesApRelationship(org.cocktail.ipweb.serveur.metier.scol.ScolMaquetteChargesAp object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "scolMaquetteChargesAp");
    editingContext().deleteObject(object);
  }

  public void deleteAllScolMaquetteChargesApRelationships() {
    Enumeration objects = scolMaquetteChargesAp().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteScolMaquetteChargesApRelationship((org.cocktail.ipweb.serveur.metier.scol.ScolMaquetteChargesAp)objects.nextElement());
    }
  }

  public NSArray<org.cocktail.ipweb.serveur.metier.scol.ScolMaquetteRepartitionAp> scolMaquetteRepartitionAps() {
    return (NSArray<org.cocktail.ipweb.serveur.metier.scol.ScolMaquetteRepartitionAp>)storedValueForKey("scolMaquetteRepartitionAps");
  }

  public NSArray<org.cocktail.ipweb.serveur.metier.scol.ScolMaquetteRepartitionAp> scolMaquetteRepartitionAps(EOQualifier qualifier) {
    return scolMaquetteRepartitionAps(qualifier, null);
  }

  public NSArray<org.cocktail.ipweb.serveur.metier.scol.ScolMaquetteRepartitionAp> scolMaquetteRepartitionAps(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    NSArray<org.cocktail.ipweb.serveur.metier.scol.ScolMaquetteRepartitionAp> results;
      results = scolMaquetteRepartitionAps();
      if (qualifier != null) {
        results = (NSArray<org.cocktail.ipweb.serveur.metier.scol.ScolMaquetteRepartitionAp>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<org.cocktail.ipweb.serveur.metier.scol.ScolMaquetteRepartitionAp>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToScolMaquetteRepartitionApsRelationship(org.cocktail.ipweb.serveur.metier.scol.ScolMaquetteRepartitionAp object) {
    if (_ScolMaquetteAp.LOG.isDebugEnabled()) {
      _ScolMaquetteAp.LOG.debug("adding " + object + " to scolMaquetteRepartitionAps relationship");
    }
    addObjectToBothSidesOfRelationshipWithKey(object, "scolMaquetteRepartitionAps");
  }

  public void removeFromScolMaquetteRepartitionApsRelationship(org.cocktail.ipweb.serveur.metier.scol.ScolMaquetteRepartitionAp object) {
    if (_ScolMaquetteAp.LOG.isDebugEnabled()) {
      _ScolMaquetteAp.LOG.debug("removing " + object + " from scolMaquetteRepartitionAps relationship");
    }
    removeObjectFromBothSidesOfRelationshipWithKey(object, "scolMaquetteRepartitionAps");
  }

  public org.cocktail.ipweb.serveur.metier.scol.ScolMaquetteRepartitionAp createScolMaquetteRepartitionApsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ScolMaquetteRepartitionAp");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "scolMaquetteRepartitionAps");
    return (org.cocktail.ipweb.serveur.metier.scol.ScolMaquetteRepartitionAp) eo;
  }

  public void deleteScolMaquetteRepartitionApsRelationship(org.cocktail.ipweb.serveur.metier.scol.ScolMaquetteRepartitionAp object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "scolMaquetteRepartitionAps");
    editingContext().deleteObject(object);
  }

  public void deleteAllScolMaquetteRepartitionApsRelationships() {
    Enumeration objects = scolMaquetteRepartitionAps().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteScolMaquetteRepartitionApsRelationship((org.cocktail.ipweb.serveur.metier.scol.ScolMaquetteRepartitionAp)objects.nextElement());
    }
  }


  public static ScolMaquetteAp createScolMaquetteAp(EOEditingContext editingContext, Integer fannKey
, Integer mapGroupePrevu
, Integer mapGroupeReel
, String mapLibelle
, Integer mapSeuil
, java.math.BigDecimal mapValeur
, String mhcoCode
) {
    ScolMaquetteAp eo = (ScolMaquetteAp) EOUtilities.createAndInsertInstance(editingContext, _ScolMaquetteAp.ENTITY_NAME);    
		eo.setFannKey(fannKey);
		eo.setMapGroupePrevu(mapGroupePrevu);
		eo.setMapGroupeReel(mapGroupeReel);
		eo.setMapLibelle(mapLibelle);
		eo.setMapSeuil(mapSeuil);
		eo.setMapValeur(mapValeur);
		eo.setMhcoCode(mhcoCode);
    return eo;
  }

  public static NSArray<ScolMaquetteAp> fetchAllScolMaquetteAps(EOEditingContext editingContext) {
    return _ScolMaquetteAp.fetchAllScolMaquetteAps(editingContext, null);
  }

  public static NSArray<ScolMaquetteAp> fetchAllScolMaquetteAps(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _ScolMaquetteAp.fetchScolMaquetteAps(editingContext, null, sortOrderings);
  }

  public static NSArray<ScolMaquetteAp> fetchScolMaquetteAps(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ScolMaquetteAp.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<ScolMaquetteAp> eoObjects = (NSArray<ScolMaquetteAp>)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ScolMaquetteAp fetchScolMaquetteAp(EOEditingContext editingContext, String keyName, Object value) {
    return _ScolMaquetteAp.fetchScolMaquetteAp(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ScolMaquetteAp fetchScolMaquetteAp(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<ScolMaquetteAp> eoObjects = _ScolMaquetteAp.fetchScolMaquetteAps(editingContext, qualifier, null);
    ScolMaquetteAp eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ScolMaquetteAp)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ScolMaquetteAp that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ScolMaquetteAp fetchRequiredScolMaquetteAp(EOEditingContext editingContext, String keyName, Object value) {
    return _ScolMaquetteAp.fetchRequiredScolMaquetteAp(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ScolMaquetteAp fetchRequiredScolMaquetteAp(EOEditingContext editingContext, EOQualifier qualifier) {
    ScolMaquetteAp eoObject = _ScolMaquetteAp.fetchScolMaquetteAp(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ScolMaquetteAp that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ScolMaquetteAp localInstanceIn(EOEditingContext editingContext, ScolMaquetteAp eo) {
    ScolMaquetteAp localInstance = (eo == null) ? null : (ScolMaquetteAp)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
