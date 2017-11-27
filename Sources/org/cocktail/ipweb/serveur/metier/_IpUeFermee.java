// $LastChangedRevision$ DO NOT EDIT.  Make changes to IpUeFermee.java instead.
package org.cocktail.ipweb.serveur.metier;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public abstract class _IpUeFermee extends  EOGenericRecord {
	public static final String ENTITY_NAME = "IpUeFermees";

	// Attributes
	public static final String FANN_KEY_KEY = "fannKey";
	public static final String MUE_KEY_KEY = "mueKey";

	// Relationships

  private static Logger LOG = Logger.getLogger(_IpUeFermee.class);

  public IpUeFermee localInstanceIn(EOEditingContext editingContext) {
    IpUeFermee localInstance = (IpUeFermee)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public Integer fannKey() {
    return (Integer) storedValueForKey("fannKey");
  }

  public void setFannKey(Integer value) {
    if (_IpUeFermee.LOG.isDebugEnabled()) {
    	_IpUeFermee.LOG.debug( "updating fannKey from " + fannKey() + " to " + value);
    }
    takeStoredValueForKey(value, "fannKey");
  }

  public Integer mueKey() {
    return (Integer) storedValueForKey("mueKey");
  }

  public void setMueKey(Integer value) {
    if (_IpUeFermee.LOG.isDebugEnabled()) {
    	_IpUeFermee.LOG.debug( "updating mueKey from " + mueKey() + " to " + value);
    }
    takeStoredValueForKey(value, "mueKey");
  }


  public static IpUeFermee createIpUeFermees(EOEditingContext editingContext, Integer fannKey
, Integer mueKey
) {
    IpUeFermee eo = (IpUeFermee) EOUtilities.createAndInsertInstance(editingContext, _IpUeFermee.ENTITY_NAME);    
		eo.setFannKey(fannKey);
		eo.setMueKey(mueKey);
    return eo;
  }

  public static NSArray<IpUeFermee> fetchAllIpUeFermeeses(EOEditingContext editingContext) {
    return _IpUeFermee.fetchAllIpUeFermeeses(editingContext, null);
  }

  public static NSArray<IpUeFermee> fetchAllIpUeFermeeses(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _IpUeFermee.fetchIpUeFermeeses(editingContext, null, sortOrderings);
  }

  public static NSArray<IpUeFermee> fetchIpUeFermeeses(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_IpUeFermee.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<IpUeFermee> eoObjects = (NSArray<IpUeFermee>)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static IpUeFermee fetchIpUeFermees(EOEditingContext editingContext, String keyName, Object value) {
    return _IpUeFermee.fetchIpUeFermees(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static IpUeFermee fetchIpUeFermees(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<IpUeFermee> eoObjects = _IpUeFermee.fetchIpUeFermeeses(editingContext, qualifier, null);
    IpUeFermee eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (IpUeFermee)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one IpUeFermees that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static IpUeFermee fetchRequiredIpUeFermees(EOEditingContext editingContext, String keyName, Object value) {
    return _IpUeFermee.fetchRequiredIpUeFermees(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static IpUeFermee fetchRequiredIpUeFermees(EOEditingContext editingContext, EOQualifier qualifier) {
    IpUeFermee eoObject = _IpUeFermee.fetchIpUeFermees(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no IpUeFermees that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static IpUeFermee localInstanceIn(EOEditingContext editingContext, IpUeFermee eo) {
    IpUeFermee localInstance = (eo == null) ? null : (IpUeFermee)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
