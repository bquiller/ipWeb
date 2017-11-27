// $LastChangedRevision$ DO NOT EDIT.  Make changes to IpBilanrnOk.java instead.
package org.cocktail.ipweb.serveur.metier;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public abstract class _IpBilanrnOk extends  EOGenericRecord {
	public static final String ENTITY_NAME = "IpBilanrnOk";

	// Attributes
	public static final String FANN_KEY_KEY = "fannKey";
	public static final String MRSEM_KEY_KEY = "mrsemKey";
	public static final String RNAFF_ETAT_KEY = "rnaffEtat";

	// Relationships

  private static Logger LOG = Logger.getLogger(_IpBilanrnOk.class);

  public IpBilanrnOk localInstanceIn(EOEditingContext editingContext) {
    IpBilanrnOk localInstance = (IpBilanrnOk)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public Integer fannKey() {
    return (Integer) storedValueForKey("fannKey");
  }

  public void setFannKey(Integer value) {
    if (_IpBilanrnOk.LOG.isDebugEnabled()) {
    	_IpBilanrnOk.LOG.debug( "updating fannKey from " + fannKey() + " to " + value);
    }
    takeStoredValueForKey(value, "fannKey");
  }

  public Integer mrsemKey() {
    return (Integer) storedValueForKey("mrsemKey");
  }

  public void setMrsemKey(Integer value) {
    if (_IpBilanrnOk.LOG.isDebugEnabled()) {
    	_IpBilanrnOk.LOG.debug( "updating mrsemKey from " + mrsemKey() + " to " + value);
    }
    takeStoredValueForKey(value, "mrsemKey");
  }

  public Integer rnaffEtat() {
    return (Integer) storedValueForKey("rnaffEtat");
  }

  public void setRnaffEtat(Integer value) {
    if (_IpBilanrnOk.LOG.isDebugEnabled()) {
    	_IpBilanrnOk.LOG.debug( "updating rnaffEtat from " + rnaffEtat() + " to " + value);
    }
    takeStoredValueForKey(value, "rnaffEtat");
  }


  public static IpBilanrnOk createIpBilanrnOk(EOEditingContext editingContext, Integer fannKey
, Integer mrsemKey
, Integer rnaffEtat
) {
    IpBilanrnOk eo = (IpBilanrnOk) EOUtilities.createAndInsertInstance(editingContext, _IpBilanrnOk.ENTITY_NAME);    
		eo.setFannKey(fannKey);
		eo.setMrsemKey(mrsemKey);
		eo.setRnaffEtat(rnaffEtat);
    return eo;
  }

  public static NSArray<IpBilanrnOk> fetchAllIpBilanrnOks(EOEditingContext editingContext) {
    return _IpBilanrnOk.fetchAllIpBilanrnOks(editingContext, null);
  }

  public static NSArray<IpBilanrnOk> fetchAllIpBilanrnOks(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _IpBilanrnOk.fetchIpBilanrnOks(editingContext, null, sortOrderings);
  }

  public static NSArray<IpBilanrnOk> fetchIpBilanrnOks(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_IpBilanrnOk.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<IpBilanrnOk> eoObjects = (NSArray<IpBilanrnOk>)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static IpBilanrnOk fetchIpBilanrnOk(EOEditingContext editingContext, String keyName, Object value) {
    return _IpBilanrnOk.fetchIpBilanrnOk(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static IpBilanrnOk fetchIpBilanrnOk(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<IpBilanrnOk> eoObjects = _IpBilanrnOk.fetchIpBilanrnOks(editingContext, qualifier, null);
    IpBilanrnOk eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (IpBilanrnOk)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one IpBilanrnOk that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static IpBilanrnOk fetchRequiredIpBilanrnOk(EOEditingContext editingContext, String keyName, Object value) {
    return _IpBilanrnOk.fetchRequiredIpBilanrnOk(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static IpBilanrnOk fetchRequiredIpBilanrnOk(EOEditingContext editingContext, EOQualifier qualifier) {
    IpBilanrnOk eoObject = _IpBilanrnOk.fetchIpBilanrnOk(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no IpBilanrnOk that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static IpBilanrnOk localInstanceIn(EOEditingContext editingContext, IpBilanrnOk eo) {
    IpBilanrnOk localInstance = (eo == null) ? null : (IpBilanrnOk)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
