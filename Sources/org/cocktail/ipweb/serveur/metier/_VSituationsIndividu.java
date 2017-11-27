// $LastChangedRevision$ DO NOT EDIT.  Make changes to VSituationsIndividu.java instead.
package org.cocktail.ipweb.serveur.metier;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public abstract class _VSituationsIndividu extends  EOGenericRecord {
	public static final String ENTITY_NAME = "VSituationsIndividu";

	// Attributes
	public static final String ACTUEL_ENS_KEY = "actuelEns";
	public static final String ACTUEL_ETUD_KEY = "actuelEtud";
	public static final String ACTUEL_NON_ENS_KEY = "actuelNonEns";
	public static final String ACTUEL_VAC_KEY = "actuelVac";
	public static final String ANCIEN_ENS_KEY = "ancienEns";
	public static final String ANCIEN_ETUD_KEY = "ancienEtud";
	public static final String ANCIEN_NON_ENS_KEY = "ancienNonEns";
	public static final String ANCIEN_VAC_KEY = "ancienVac";
	public static final String NO_INDIVIDU_KEY = "noIndividu";

	// Relationships

  private static Logger LOG = Logger.getLogger(_VSituationsIndividu.class);

  public VSituationsIndividu localInstanceIn(EOEditingContext editingContext) {
    VSituationsIndividu localInstance = (VSituationsIndividu)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public Integer actuelEns() {
    return (Integer) storedValueForKey("actuelEns");
  }

  public void setActuelEns(Integer value) {
    if (_VSituationsIndividu.LOG.isDebugEnabled()) {
    	_VSituationsIndividu.LOG.debug( "updating actuelEns from " + actuelEns() + " to " + value);
    }
    takeStoredValueForKey(value, "actuelEns");
  }

  public Integer actuelEtud() {
    return (Integer) storedValueForKey("actuelEtud");
  }

  public void setActuelEtud(Integer value) {
    if (_VSituationsIndividu.LOG.isDebugEnabled()) {
    	_VSituationsIndividu.LOG.debug( "updating actuelEtud from " + actuelEtud() + " to " + value);
    }
    takeStoredValueForKey(value, "actuelEtud");
  }

  public Integer actuelNonEns() {
    return (Integer) storedValueForKey("actuelNonEns");
  }

  public void setActuelNonEns(Integer value) {
    if (_VSituationsIndividu.LOG.isDebugEnabled()) {
    	_VSituationsIndividu.LOG.debug( "updating actuelNonEns from " + actuelNonEns() + " to " + value);
    }
    takeStoredValueForKey(value, "actuelNonEns");
  }

  public Integer actuelVac() {
    return (Integer) storedValueForKey("actuelVac");
  }

  public void setActuelVac(Integer value) {
    if (_VSituationsIndividu.LOG.isDebugEnabled()) {
    	_VSituationsIndividu.LOG.debug( "updating actuelVac from " + actuelVac() + " to " + value);
    }
    takeStoredValueForKey(value, "actuelVac");
  }

  public Integer ancienEns() {
    return (Integer) storedValueForKey("ancienEns");
  }

  public void setAncienEns(Integer value) {
    if (_VSituationsIndividu.LOG.isDebugEnabled()) {
    	_VSituationsIndividu.LOG.debug( "updating ancienEns from " + ancienEns() + " to " + value);
    }
    takeStoredValueForKey(value, "ancienEns");
  }

  public Integer ancienEtud() {
    return (Integer) storedValueForKey("ancienEtud");
  }

  public void setAncienEtud(Integer value) {
    if (_VSituationsIndividu.LOG.isDebugEnabled()) {
    	_VSituationsIndividu.LOG.debug( "updating ancienEtud from " + ancienEtud() + " to " + value);
    }
    takeStoredValueForKey(value, "ancienEtud");
  }

  public Integer ancienNonEns() {
    return (Integer) storedValueForKey("ancienNonEns");
  }

  public void setAncienNonEns(Integer value) {
    if (_VSituationsIndividu.LOG.isDebugEnabled()) {
    	_VSituationsIndividu.LOG.debug( "updating ancienNonEns from " + ancienNonEns() + " to " + value);
    }
    takeStoredValueForKey(value, "ancienNonEns");
  }

  public Integer ancienVac() {
    return (Integer) storedValueForKey("ancienVac");
  }

  public void setAncienVac(Integer value) {
    if (_VSituationsIndividu.LOG.isDebugEnabled()) {
    	_VSituationsIndividu.LOG.debug( "updating ancienVac from " + ancienVac() + " to " + value);
    }
    takeStoredValueForKey(value, "ancienVac");
  }

  public Integer noIndividu() {
    return (Integer) storedValueForKey("noIndividu");
  }

  public void setNoIndividu(Integer value) {
    if (_VSituationsIndividu.LOG.isDebugEnabled()) {
    	_VSituationsIndividu.LOG.debug( "updating noIndividu from " + noIndividu() + " to " + value);
    }
    takeStoredValueForKey(value, "noIndividu");
  }


  public static VSituationsIndividu createVSituationsIndividu(EOEditingContext editingContext, Integer noIndividu
) {
    VSituationsIndividu eo = (VSituationsIndividu) EOUtilities.createAndInsertInstance(editingContext, _VSituationsIndividu.ENTITY_NAME);    
		eo.setNoIndividu(noIndividu);
    return eo;
  }

  public static NSArray<VSituationsIndividu> fetchAllVSituationsIndividus(EOEditingContext editingContext) {
    return _VSituationsIndividu.fetchAllVSituationsIndividus(editingContext, null);
  }

  public static NSArray<VSituationsIndividu> fetchAllVSituationsIndividus(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _VSituationsIndividu.fetchVSituationsIndividus(editingContext, null, sortOrderings);
  }

  public static NSArray<VSituationsIndividu> fetchVSituationsIndividus(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_VSituationsIndividu.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<VSituationsIndividu> eoObjects = (NSArray<VSituationsIndividu>)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static VSituationsIndividu fetchVSituationsIndividu(EOEditingContext editingContext, String keyName, Object value) {
    return _VSituationsIndividu.fetchVSituationsIndividu(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static VSituationsIndividu fetchVSituationsIndividu(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<VSituationsIndividu> eoObjects = _VSituationsIndividu.fetchVSituationsIndividus(editingContext, qualifier, null);
    VSituationsIndividu eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (VSituationsIndividu)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one VSituationsIndividu that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static VSituationsIndividu fetchRequiredVSituationsIndividu(EOEditingContext editingContext, String keyName, Object value) {
    return _VSituationsIndividu.fetchRequiredVSituationsIndividu(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static VSituationsIndividu fetchRequiredVSituationsIndividu(EOEditingContext editingContext, EOQualifier qualifier) {
    VSituationsIndividu eoObject = _VSituationsIndividu.fetchVSituationsIndividu(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no VSituationsIndividu that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static VSituationsIndividu localInstanceIn(EOEditingContext editingContext, VSituationsIndividu eo) {
    VSituationsIndividu localInstance = (eo == null) ? null : (VSituationsIndividu)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
