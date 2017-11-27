// $LastChangedRevision$ DO NOT EDIT.  Make changes to IpDiplSansRn.java instead.
package org.cocktail.ipweb.serveur.metier;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public abstract class _IpDiplSansRn extends  EOGenericRecord {
	public static final String ENTITY_NAME = "IpDiplSansRn";

	// Attributes
	public static final String FANN_KEY_KEY = "fannKey";
	public static final String FSPN_KEY_KEY = "fspnKey";

	// Relationships

  private static Logger LOG = Logger.getLogger(_IpDiplSansRn.class);

  public IpDiplSansRn localInstanceIn(EOEditingContext editingContext) {
    IpDiplSansRn localInstance = (IpDiplSansRn)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public Integer fannKey() {
    return (Integer) storedValueForKey("fannKey");
  }

  public void setFannKey(Integer value) {
    if (_IpDiplSansRn.LOG.isDebugEnabled()) {
    	_IpDiplSansRn.LOG.debug( "updating fannKey from " + fannKey() + " to " + value);
    }
    takeStoredValueForKey(value, "fannKey");
  }

  public Integer fspnKey() {
    return (Integer) storedValueForKey("fspnKey");
  }

  public void setFspnKey(Integer value) {
    if (_IpDiplSansRn.LOG.isDebugEnabled()) {
    	_IpDiplSansRn.LOG.debug( "updating fspnKey from " + fspnKey() + " to " + value);
    }
    takeStoredValueForKey(value, "fspnKey");
  }


  public static IpDiplSansRn createIpDiplSansRn(EOEditingContext editingContext, Integer fannKey
, Integer fspnKey
) {
    IpDiplSansRn eo = (IpDiplSansRn) EOUtilities.createAndInsertInstance(editingContext, _IpDiplSansRn.ENTITY_NAME);    
		eo.setFannKey(fannKey);
		eo.setFspnKey(fspnKey);
    return eo;
  }

  public static NSArray<IpDiplSansRn> fetchAllIpDiplSansRns(EOEditingContext editingContext) {
    return _IpDiplSansRn.fetchAllIpDiplSansRns(editingContext, null);
  }

  public static NSArray<IpDiplSansRn> fetchAllIpDiplSansRns(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _IpDiplSansRn.fetchIpDiplSansRns(editingContext, null, sortOrderings);
  }

  public static NSArray<IpDiplSansRn> fetchIpDiplSansRns(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_IpDiplSansRn.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<IpDiplSansRn> eoObjects = (NSArray<IpDiplSansRn>)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static IpDiplSansRn fetchIpDiplSansRn(EOEditingContext editingContext, String keyName, Object value) {
    return _IpDiplSansRn.fetchIpDiplSansRn(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static IpDiplSansRn fetchIpDiplSansRn(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<IpDiplSansRn> eoObjects = _IpDiplSansRn.fetchIpDiplSansRns(editingContext, qualifier, null);
    IpDiplSansRn eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (IpDiplSansRn)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one IpDiplSansRn that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static IpDiplSansRn fetchRequiredIpDiplSansRn(EOEditingContext editingContext, String keyName, Object value) {
    return _IpDiplSansRn.fetchRequiredIpDiplSansRn(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static IpDiplSansRn fetchRequiredIpDiplSansRn(EOEditingContext editingContext, EOQualifier qualifier) {
    IpDiplSansRn eoObject = _IpDiplSansRn.fetchIpDiplSansRn(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no IpDiplSansRn that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static IpDiplSansRn localInstanceIn(EOEditingContext editingContext, IpDiplSansRn eo) {
    IpDiplSansRn localInstance = (eo == null) ? null : (IpDiplSansRn)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
