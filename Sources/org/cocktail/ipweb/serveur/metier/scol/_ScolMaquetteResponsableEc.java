// $LastChangedRevision$ DO NOT EDIT.  Make changes to ScolMaquetteResponsableEc.java instead.
package org.cocktail.ipweb.serveur.metier.scol;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public abstract class _ScolMaquetteResponsableEc extends  EOGenericRecord {
	public static final String ENTITY_NAME = "ScolMaquetteResponsableEc";

	// Attributes
	public static final String FANN_KEY_KEY = "fannKey";
	public static final String MBEC_TYPE_KEY = "mbecType";
	public static final String MEC_KEY_KEY = "mecKey";

	// Relationships
	public static final String INDIVIDU_KEY = "individu";

  private static Logger LOG = Logger.getLogger(_ScolMaquetteResponsableEc.class);

  public ScolMaquetteResponsableEc localInstanceIn(EOEditingContext editingContext) {
    ScolMaquetteResponsableEc localInstance = (ScolMaquetteResponsableEc)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public Integer fannKey() {
    return (Integer) storedValueForKey("fannKey");
  }

  public void setFannKey(Integer value) {
    if (_ScolMaquetteResponsableEc.LOG.isDebugEnabled()) {
    	_ScolMaquetteResponsableEc.LOG.debug( "updating fannKey from " + fannKey() + " to " + value);
    }
    takeStoredValueForKey(value, "fannKey");
  }

  public String mbecType() {
    return (String) storedValueForKey("mbecType");
  }

  public void setMbecType(String value) {
    if (_ScolMaquetteResponsableEc.LOG.isDebugEnabled()) {
    	_ScolMaquetteResponsableEc.LOG.debug( "updating mbecType from " + mbecType() + " to " + value);
    }
    takeStoredValueForKey(value, "mbecType");
  }

  public Integer mecKey() {
    return (Integer) storedValueForKey("mecKey");
  }

  public void setMecKey(Integer value) {
    if (_ScolMaquetteResponsableEc.LOG.isDebugEnabled()) {
    	_ScolMaquetteResponsableEc.LOG.debug( "updating mecKey from " + mecKey() + " to " + value);
    }
    takeStoredValueForKey(value, "mecKey");
  }

  public org.cocktail.fwkcktlwebapp.common.metier.EOIndividuUlrPersId individu() {
    return (org.cocktail.fwkcktlwebapp.common.metier.EOIndividuUlrPersId)storedValueForKey("individu");
  }

  public void setIndividuRelationship(org.cocktail.fwkcktlwebapp.common.metier.EOIndividuUlrPersId value) {
    if (_ScolMaquetteResponsableEc.LOG.isDebugEnabled()) {
      _ScolMaquetteResponsableEc.LOG.debug("updating individu from " + individu() + " to " + value);
    }
    if (value == null) {
    	org.cocktail.fwkcktlwebapp.common.metier.EOIndividuUlrPersId oldValue = individu();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "individu");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "individu");
    }
  }
  

  public static ScolMaquetteResponsableEc createScolMaquetteResponsableEc(EOEditingContext editingContext, Integer fannKey
, String mbecType
, Integer mecKey
, org.cocktail.fwkcktlwebapp.common.metier.EOIndividuUlrPersId individu) {
    ScolMaquetteResponsableEc eo = (ScolMaquetteResponsableEc) EOUtilities.createAndInsertInstance(editingContext, _ScolMaquetteResponsableEc.ENTITY_NAME);    
		eo.setFannKey(fannKey);
		eo.setMbecType(mbecType);
		eo.setMecKey(mecKey);
    eo.setIndividuRelationship(individu);
    return eo;
  }

  public static NSArray<ScolMaquetteResponsableEc> fetchAllScolMaquetteResponsableEcs(EOEditingContext editingContext) {
    return _ScolMaquetteResponsableEc.fetchAllScolMaquetteResponsableEcs(editingContext, null);
  }

  public static NSArray<ScolMaquetteResponsableEc> fetchAllScolMaquetteResponsableEcs(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _ScolMaquetteResponsableEc.fetchScolMaquetteResponsableEcs(editingContext, null, sortOrderings);
  }

  public static NSArray<ScolMaquetteResponsableEc> fetchScolMaquetteResponsableEcs(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ScolMaquetteResponsableEc.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<ScolMaquetteResponsableEc> eoObjects = (NSArray<ScolMaquetteResponsableEc>)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ScolMaquetteResponsableEc fetchScolMaquetteResponsableEc(EOEditingContext editingContext, String keyName, Object value) {
    return _ScolMaquetteResponsableEc.fetchScolMaquetteResponsableEc(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ScolMaquetteResponsableEc fetchScolMaquetteResponsableEc(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<ScolMaquetteResponsableEc> eoObjects = _ScolMaquetteResponsableEc.fetchScolMaquetteResponsableEcs(editingContext, qualifier, null);
    ScolMaquetteResponsableEc eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ScolMaquetteResponsableEc)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ScolMaquetteResponsableEc that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ScolMaquetteResponsableEc fetchRequiredScolMaquetteResponsableEc(EOEditingContext editingContext, String keyName, Object value) {
    return _ScolMaquetteResponsableEc.fetchRequiredScolMaquetteResponsableEc(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ScolMaquetteResponsableEc fetchRequiredScolMaquetteResponsableEc(EOEditingContext editingContext, EOQualifier qualifier) {
    ScolMaquetteResponsableEc eoObject = _ScolMaquetteResponsableEc.fetchScolMaquetteResponsableEc(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ScolMaquetteResponsableEc that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ScolMaquetteResponsableEc localInstanceIn(EOEditingContext editingContext, ScolMaquetteResponsableEc eo) {
    ScolMaquetteResponsableEc localInstance = (eo == null) ? null : (ScolMaquetteResponsableEc)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
