// $LastChangedRevision$ DO NOT EDIT.  Make changes to IpChoixLog.java instead.
package org.cocktail.ipweb.serveur.metier;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public abstract class _IpChoixLog extends  EOGenericRecord {
	public static final String ENTITY_NAME = "IpChoixLog";

	// Attributes
	public static final String CL_DATE_LOG_KEY = "clDateLog";
	public static final String ETAT_CHOIX_KEY = "etatChoix";
	public static final String IDIPL_NUMERO_KEY = "idiplNumero";
	public static final String IMREC_SEMESTRE_KEY = "imrecSemestre";
	public static final String MREC_KEY_KEY = "mrecKey";
	public static final String MSEM_KEY_KEY = "msemKey";

	// Relationships

  private static Logger LOG = Logger.getLogger(_IpChoixLog.class);

  public IpChoixLog localInstanceIn(EOEditingContext editingContext) {
    IpChoixLog localInstance = (IpChoixLog)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public NSTimestamp clDateLog() {
    return (NSTimestamp) storedValueForKey("clDateLog");
  }

  public void setClDateLog(NSTimestamp value) {
    if (_IpChoixLog.LOG.isDebugEnabled()) {
    	_IpChoixLog.LOG.debug( "updating clDateLog from " + clDateLog() + " to " + value);
    }
    takeStoredValueForKey(value, "clDateLog");
  }

  public String etatChoix() {
    return (String) storedValueForKey("etatChoix");
  }

  public void setEtatChoix(String value) {
    if (_IpChoixLog.LOG.isDebugEnabled()) {
    	_IpChoixLog.LOG.debug( "updating etatChoix from " + etatChoix() + " to " + value);
    }
    takeStoredValueForKey(value, "etatChoix");
  }

  public Long idiplNumero() {
    return (Long) storedValueForKey("idiplNumero");
  }

  public void setIdiplNumero(Long value) {
    if (_IpChoixLog.LOG.isDebugEnabled()) {
    	_IpChoixLog.LOG.debug( "updating idiplNumero from " + idiplNumero() + " to " + value);
    }
    takeStoredValueForKey(value, "idiplNumero");
  }

  public Long imrecSemestre() {
    return (Long) storedValueForKey("imrecSemestre");
  }

  public void setImrecSemestre(Long value) {
    if (_IpChoixLog.LOG.isDebugEnabled()) {
    	_IpChoixLog.LOG.debug( "updating imrecSemestre from " + imrecSemestre() + " to " + value);
    }
    takeStoredValueForKey(value, "imrecSemestre");
  }

  public Long mrecKey() {
    return (Long) storedValueForKey("mrecKey");
  }

  public void setMrecKey(Long value) {
    if (_IpChoixLog.LOG.isDebugEnabled()) {
    	_IpChoixLog.LOG.debug( "updating mrecKey from " + mrecKey() + " to " + value);
    }
    takeStoredValueForKey(value, "mrecKey");
  }

  public Integer msemKey() {
    return (Integer) storedValueForKey("msemKey");
  }

  public void setMsemKey(Integer value) {
    if (_IpChoixLog.LOG.isDebugEnabled()) {
    	_IpChoixLog.LOG.debug( "updating msemKey from " + msemKey() + " to " + value);
    }
    takeStoredValueForKey(value, "msemKey");
  }


  public static IpChoixLog createIpChoixLog(EOEditingContext editingContext, Long idiplNumero
, Long imrecSemestre
, Long mrecKey
, Integer msemKey
) {
    IpChoixLog eo = (IpChoixLog) EOUtilities.createAndInsertInstance(editingContext, _IpChoixLog.ENTITY_NAME);    
		eo.setIdiplNumero(idiplNumero);
		eo.setImrecSemestre(imrecSemestre);
		eo.setMrecKey(mrecKey);
		eo.setMsemKey(msemKey);
    return eo;
  }

  public static NSArray<IpChoixLog> fetchAllIpChoixLogs(EOEditingContext editingContext) {
    return _IpChoixLog.fetchAllIpChoixLogs(editingContext, null);
  }

  public static NSArray<IpChoixLog> fetchAllIpChoixLogs(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _IpChoixLog.fetchIpChoixLogs(editingContext, null, sortOrderings);
  }

  public static NSArray<IpChoixLog> fetchIpChoixLogs(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_IpChoixLog.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<IpChoixLog> eoObjects = (NSArray<IpChoixLog>)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static IpChoixLog fetchIpChoixLog(EOEditingContext editingContext, String keyName, Object value) {
    return _IpChoixLog.fetchIpChoixLog(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static IpChoixLog fetchIpChoixLog(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<IpChoixLog> eoObjects = _IpChoixLog.fetchIpChoixLogs(editingContext, qualifier, null);
    IpChoixLog eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (IpChoixLog)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one IpChoixLog that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static IpChoixLog fetchRequiredIpChoixLog(EOEditingContext editingContext, String keyName, Object value) {
    return _IpChoixLog.fetchRequiredIpChoixLog(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static IpChoixLog fetchRequiredIpChoixLog(EOEditingContext editingContext, EOQualifier qualifier) {
    IpChoixLog eoObject = _IpChoixLog.fetchIpChoixLog(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no IpChoixLog that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static IpChoixLog localInstanceIn(EOEditingContext editingContext, IpChoixLog eo) {
    IpChoixLog localInstance = (eo == null) ? null : (IpChoixLog)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
