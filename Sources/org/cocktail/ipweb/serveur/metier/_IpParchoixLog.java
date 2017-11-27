// $LastChangedRevision$ DO NOT EDIT.  Make changes to IpParchoixLog.java instead.
package org.cocktail.ipweb.serveur.metier;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public abstract class _IpParchoixLog extends  EOGenericRecord {
	public static final String ENTITY_NAME = "IpParchoixLog";

	// Attributes
	public static final String ETUD_NUMERO_KEY = "etudNumero";
	public static final String FANN_KEY_KEY = "fannKey";
	public static final String IDIPL_NUMERO_KEY = "idiplNumero";
	public static final String MRSEM_KEY_KEY = "mrsemKey";
	public static final String MSEM_ORDRE_KEY = "msemOrdre";
	public static final String PCL_DATE_LOG_KEY = "pclDateLog";
	public static final String TYPE_ACTION_KEY = "typeAction";

	// Relationships

  private static Logger LOG = Logger.getLogger(_IpParchoixLog.class);

  public IpParchoixLog localInstanceIn(EOEditingContext editingContext) {
    IpParchoixLog localInstance = (IpParchoixLog)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public Long etudNumero() {
    return (Long) storedValueForKey("etudNumero");
  }

  public void setEtudNumero(Long value) {
    if (_IpParchoixLog.LOG.isDebugEnabled()) {
    	_IpParchoixLog.LOG.debug( "updating etudNumero from " + etudNumero() + " to " + value);
    }
    takeStoredValueForKey(value, "etudNumero");
  }

  public Integer fannKey() {
    return (Integer) storedValueForKey("fannKey");
  }

  public void setFannKey(Integer value) {
    if (_IpParchoixLog.LOG.isDebugEnabled()) {
    	_IpParchoixLog.LOG.debug( "updating fannKey from " + fannKey() + " to " + value);
    }
    takeStoredValueForKey(value, "fannKey");
  }

  public Long idiplNumero() {
    return (Long) storedValueForKey("idiplNumero");
  }

  public void setIdiplNumero(Long value) {
    if (_IpParchoixLog.LOG.isDebugEnabled()) {
    	_IpParchoixLog.LOG.debug( "updating idiplNumero from " + idiplNumero() + " to " + value);
    }
    takeStoredValueForKey(value, "idiplNumero");
  }

  public Integer mrsemKey() {
    return (Integer) storedValueForKey("mrsemKey");
  }

  public void setMrsemKey(Integer value) {
    if (_IpParchoixLog.LOG.isDebugEnabled()) {
    	_IpParchoixLog.LOG.debug( "updating mrsemKey from " + mrsemKey() + " to " + value);
    }
    takeStoredValueForKey(value, "mrsemKey");
  }

  public Integer msemOrdre() {
    return (Integer) storedValueForKey("msemOrdre");
  }

  public void setMsemOrdre(Integer value) {
    if (_IpParchoixLog.LOG.isDebugEnabled()) {
    	_IpParchoixLog.LOG.debug( "updating msemOrdre from " + msemOrdre() + " to " + value);
    }
    takeStoredValueForKey(value, "msemOrdre");
  }

  public NSTimestamp pclDateLog() {
    return (NSTimestamp) storedValueForKey("pclDateLog");
  }

  public void setPclDateLog(NSTimestamp value) {
    if (_IpParchoixLog.LOG.isDebugEnabled()) {
    	_IpParchoixLog.LOG.debug( "updating pclDateLog from " + pclDateLog() + " to " + value);
    }
    takeStoredValueForKey(value, "pclDateLog");
  }

  public String typeAction() {
    return (String) storedValueForKey("typeAction");
  }

  public void setTypeAction(String value) {
    if (_IpParchoixLog.LOG.isDebugEnabled()) {
    	_IpParchoixLog.LOG.debug( "updating typeAction from " + typeAction() + " to " + value);
    }
    takeStoredValueForKey(value, "typeAction");
  }


  public static IpParchoixLog createIpParchoixLog(EOEditingContext editingContext, Integer fannKey
, NSTimestamp pclDateLog
, String typeAction
) {
    IpParchoixLog eo = (IpParchoixLog) EOUtilities.createAndInsertInstance(editingContext, _IpParchoixLog.ENTITY_NAME);    
		eo.setFannKey(fannKey);
		eo.setPclDateLog(pclDateLog);
		eo.setTypeAction(typeAction);
    return eo;
  }

  public static NSArray<IpParchoixLog> fetchAllIpParchoixLogs(EOEditingContext editingContext) {
    return _IpParchoixLog.fetchAllIpParchoixLogs(editingContext, null);
  }

  public static NSArray<IpParchoixLog> fetchAllIpParchoixLogs(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _IpParchoixLog.fetchIpParchoixLogs(editingContext, null, sortOrderings);
  }

  public static NSArray<IpParchoixLog> fetchIpParchoixLogs(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_IpParchoixLog.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<IpParchoixLog> eoObjects = (NSArray<IpParchoixLog>)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static IpParchoixLog fetchIpParchoixLog(EOEditingContext editingContext, String keyName, Object value) {
    return _IpParchoixLog.fetchIpParchoixLog(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static IpParchoixLog fetchIpParchoixLog(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<IpParchoixLog> eoObjects = _IpParchoixLog.fetchIpParchoixLogs(editingContext, qualifier, null);
    IpParchoixLog eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (IpParchoixLog)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one IpParchoixLog that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static IpParchoixLog fetchRequiredIpParchoixLog(EOEditingContext editingContext, String keyName, Object value) {
    return _IpParchoixLog.fetchRequiredIpParchoixLog(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static IpParchoixLog fetchRequiredIpParchoixLog(EOEditingContext editingContext, EOQualifier qualifier) {
    IpParchoixLog eoObject = _IpParchoixLog.fetchIpParchoixLog(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no IpParchoixLog that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static IpParchoixLog localInstanceIn(EOEditingContext editingContext, IpParchoixLog eo) {
    IpParchoixLog localInstance = (eo == null) ? null : (IpParchoixLog)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
