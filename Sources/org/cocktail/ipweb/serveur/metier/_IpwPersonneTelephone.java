// $LastChangedRevision$ DO NOT EDIT.  Make changes to IpwPersonneTelephone.java instead.
package org.cocktail.ipweb.serveur.metier;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public abstract class _IpwPersonneTelephone extends  EOGenericRecord {
	public static final String ENTITY_NAME = "IpwPersonneTelephone";

	// Attributes
	public static final String D_CREATION_KEY = "dCreation";
	public static final String D_MODIFICATION_KEY = "dModification";
	public static final String NO_TELEPHONE_KEY = "noTelephone";
	public static final String PERS_ID_KEY = "persId";
	public static final String TYPE_NO_KEY = "typeNo";
	public static final String TYPE_TEL_KEY = "typeTel";

	// Relationships

  private static Logger LOG = Logger.getLogger(_IpwPersonneTelephone.class);

  public IpwPersonneTelephone localInstanceIn(EOEditingContext editingContext) {
    IpwPersonneTelephone localInstance = (IpwPersonneTelephone)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public NSTimestamp dCreation() {
    return (NSTimestamp) storedValueForKey("dCreation");
  }

  public void setDCreation(NSTimestamp value) {
    if (_IpwPersonneTelephone.LOG.isDebugEnabled()) {
    	_IpwPersonneTelephone.LOG.debug( "updating dCreation from " + dCreation() + " to " + value);
    }
    takeStoredValueForKey(value, "dCreation");
  }

  public NSTimestamp dModification() {
    return (NSTimestamp) storedValueForKey("dModification");
  }

  public void setDModification(NSTimestamp value) {
    if (_IpwPersonneTelephone.LOG.isDebugEnabled()) {
    	_IpwPersonneTelephone.LOG.debug( "updating dModification from " + dModification() + " to " + value);
    }
    takeStoredValueForKey(value, "dModification");
  }

  public String noTelephone() {
    return (String) storedValueForKey("noTelephone");
  }

  public void setNoTelephone(String value) {
    if (_IpwPersonneTelephone.LOG.isDebugEnabled()) {
    	_IpwPersonneTelephone.LOG.debug( "updating noTelephone from " + noTelephone() + " to " + value);
    }
    takeStoredValueForKey(value, "noTelephone");
  }

  public Integer persId() {
    return (Integer) storedValueForKey("persId");
  }

  public void setPersId(Integer value) {
    if (_IpwPersonneTelephone.LOG.isDebugEnabled()) {
    	_IpwPersonneTelephone.LOG.debug( "updating persId from " + persId() + " to " + value);
    }
    takeStoredValueForKey(value, "persId");
  }

  public String typeNo() {
    return (String) storedValueForKey("typeNo");
  }

  public void setTypeNo(String value) {
    if (_IpwPersonneTelephone.LOG.isDebugEnabled()) {
    	_IpwPersonneTelephone.LOG.debug( "updating typeNo from " + typeNo() + " to " + value);
    }
    takeStoredValueForKey(value, "typeNo");
  }

  public String typeTel() {
    return (String) storedValueForKey("typeTel");
  }

  public void setTypeTel(String value) {
    if (_IpwPersonneTelephone.LOG.isDebugEnabled()) {
    	_IpwPersonneTelephone.LOG.debug( "updating typeTel from " + typeTel() + " to " + value);
    }
    takeStoredValueForKey(value, "typeTel");
  }


  public static IpwPersonneTelephone createIpwPersonneTelephone(EOEditingContext editingContext, NSTimestamp dCreation
, NSTimestamp dModification
, String noTelephone
, Integer persId
, String typeNo
, String typeTel
) {
    IpwPersonneTelephone eo = (IpwPersonneTelephone) EOUtilities.createAndInsertInstance(editingContext, _IpwPersonneTelephone.ENTITY_NAME);    
		eo.setDCreation(dCreation);
		eo.setDModification(dModification);
		eo.setNoTelephone(noTelephone);
		eo.setPersId(persId);
		eo.setTypeNo(typeNo);
		eo.setTypeTel(typeTel);
    return eo;
  }

  public static NSArray<IpwPersonneTelephone> fetchAllIpwPersonneTelephones(EOEditingContext editingContext) {
    return _IpwPersonneTelephone.fetchAllIpwPersonneTelephones(editingContext, null);
  }

  public static NSArray<IpwPersonneTelephone> fetchAllIpwPersonneTelephones(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _IpwPersonneTelephone.fetchIpwPersonneTelephones(editingContext, null, sortOrderings);
  }

  public static NSArray<IpwPersonneTelephone> fetchIpwPersonneTelephones(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_IpwPersonneTelephone.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<IpwPersonneTelephone> eoObjects = (NSArray<IpwPersonneTelephone>)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static IpwPersonneTelephone fetchIpwPersonneTelephone(EOEditingContext editingContext, String keyName, Object value) {
    return _IpwPersonneTelephone.fetchIpwPersonneTelephone(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static IpwPersonneTelephone fetchIpwPersonneTelephone(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<IpwPersonneTelephone> eoObjects = _IpwPersonneTelephone.fetchIpwPersonneTelephones(editingContext, qualifier, null);
    IpwPersonneTelephone eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (IpwPersonneTelephone)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one IpwPersonneTelephone that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static IpwPersonneTelephone fetchRequiredIpwPersonneTelephone(EOEditingContext editingContext, String keyName, Object value) {
    return _IpwPersonneTelephone.fetchRequiredIpwPersonneTelephone(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static IpwPersonneTelephone fetchRequiredIpwPersonneTelephone(EOEditingContext editingContext, EOQualifier qualifier) {
    IpwPersonneTelephone eoObject = _IpwPersonneTelephone.fetchIpwPersonneTelephone(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no IpwPersonneTelephone that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static IpwPersonneTelephone localInstanceIn(EOEditingContext editingContext, IpwPersonneTelephone eo) {
    IpwPersonneTelephone localInstance = (eo == null) ? null : (IpwPersonneTelephone)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
  public static NSArray<org.cocktail.ipweb.serveur.metier.IpwPersonneTelephone> fetchFsPersTel(EOEditingContext editingContext, NSDictionary<String, Object> bindings) {
    EOFetchSpecification fetchSpec = EOFetchSpecification.fetchSpecificationNamed("fsPersTel", "IpwPersonneTelephone");
    fetchSpec = fetchSpec.fetchSpecificationWithQualifierBindings(bindings);
    return (NSArray<org.cocktail.ipweb.serveur.metier.IpwPersonneTelephone>)editingContext.objectsWithFetchSpecification(fetchSpec);
  }
  
  public static NSArray<org.cocktail.ipweb.serveur.metier.IpwPersonneTelephone> fetchFsPersTel(EOEditingContext editingContext,
	Integer persIdBinding)
  {
    EOFetchSpecification fetchSpec = EOFetchSpecification.fetchSpecificationNamed("fsPersTel", "IpwPersonneTelephone");
    NSMutableDictionary<String, Object> bindings = new NSMutableDictionary<String, Object>();
    bindings.takeValueForKey(persIdBinding, "persId");
	fetchSpec = fetchSpec.fetchSpecificationWithQualifierBindings(bindings);
    return (NSArray<org.cocktail.ipweb.serveur.metier.IpwPersonneTelephone>)editingContext.objectsWithFetchSpecification(fetchSpec);
  }
  
}
