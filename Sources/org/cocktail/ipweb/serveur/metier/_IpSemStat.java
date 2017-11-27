// $LastChangedRevision$ DO NOT EDIT.  Make changes to IpSemStat.java instead.
package org.cocktail.ipweb.serveur.metier;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public abstract class _IpSemStat extends  EOGenericRecord {
	public static final String ENTITY_NAME = "IpSemStat";

	// Attributes
	public static final String CHOIX_VALIDES_KEY = "choixValides";
	public static final String CUMUL_ECTS_KEY = "cumulEcts";
	public static final String FANN_KEY_KEY = "fannKey";
	public static final String IDIPL_NUMERO_KEY = "idiplNumero";
	public static final String INSC_PS_INCOMPLETE_KEY = "inscPsIncomplete";
	public static final String MRSEM_KEY_PS_KEY = "mrsemKeyPs";
	public static final String MSEM_KEY_KEY = "msemKey";
	public static final String NB_UE_INCOMPLETES_KEY = "nbUeIncompletes";
	public static final String SS_DATE_MODIF_KEY = "ssDateModif";

	// Relationships

  private static Logger LOG = Logger.getLogger(_IpSemStat.class);

  public IpSemStat localInstanceIn(EOEditingContext editingContext) {
    IpSemStat localInstance = (IpSemStat)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public String choixValides() {
    return (String) storedValueForKey("choixValides");
  }

  public void setChoixValides(String value) {
    if (_IpSemStat.LOG.isDebugEnabled()) {
    	_IpSemStat.LOG.debug( "updating choixValides from " + choixValides() + " to " + value);
    }
    takeStoredValueForKey(value, "choixValides");
  }

  public Double cumulEcts() {
    return (Double) storedValueForKey("cumulEcts");
  }

  public void setCumulEcts(Double value) {
    if (_IpSemStat.LOG.isDebugEnabled()) {
    	_IpSemStat.LOG.debug( "updating cumulEcts from " + cumulEcts() + " to " + value);
    }
    takeStoredValueForKey(value, "cumulEcts");
  }

  public Integer fannKey() {
    return (Integer) storedValueForKey("fannKey");
  }

  public void setFannKey(Integer value) {
    if (_IpSemStat.LOG.isDebugEnabled()) {
    	_IpSemStat.LOG.debug( "updating fannKey from " + fannKey() + " to " + value);
    }
    takeStoredValueForKey(value, "fannKey");
  }

  public Integer idiplNumero() {
    return (Integer) storedValueForKey("idiplNumero");
  }

  public void setIdiplNumero(Integer value) {
    if (_IpSemStat.LOG.isDebugEnabled()) {
    	_IpSemStat.LOG.debug( "updating idiplNumero from " + idiplNumero() + " to " + value);
    }
    takeStoredValueForKey(value, "idiplNumero");
  }

  public String inscPsIncomplete() {
    return (String) storedValueForKey("inscPsIncomplete");
  }

  public void setInscPsIncomplete(String value) {
    if (_IpSemStat.LOG.isDebugEnabled()) {
    	_IpSemStat.LOG.debug( "updating inscPsIncomplete from " + inscPsIncomplete() + " to " + value);
    }
    takeStoredValueForKey(value, "inscPsIncomplete");
  }

  public Integer mrsemKeyPs() {
    return (Integer) storedValueForKey("mrsemKeyPs");
  }

  public void setMrsemKeyPs(Integer value) {
    if (_IpSemStat.LOG.isDebugEnabled()) {
    	_IpSemStat.LOG.debug( "updating mrsemKeyPs from " + mrsemKeyPs() + " to " + value);
    }
    takeStoredValueForKey(value, "mrsemKeyPs");
  }

  public Integer msemKey() {
    return (Integer) storedValueForKey("msemKey");
  }

  public void setMsemKey(Integer value) {
    if (_IpSemStat.LOG.isDebugEnabled()) {
    	_IpSemStat.LOG.debug( "updating msemKey from " + msemKey() + " to " + value);
    }
    takeStoredValueForKey(value, "msemKey");
  }

  public Integer nbUeIncompletes() {
    return (Integer) storedValueForKey("nbUeIncompletes");
  }

  public void setNbUeIncompletes(Integer value) {
    if (_IpSemStat.LOG.isDebugEnabled()) {
    	_IpSemStat.LOG.debug( "updating nbUeIncompletes from " + nbUeIncompletes() + " to " + value);
    }
    takeStoredValueForKey(value, "nbUeIncompletes");
  }

  public NSTimestamp ssDateModif() {
    return (NSTimestamp) storedValueForKey("ssDateModif");
  }

  public void setSsDateModif(NSTimestamp value) {
    if (_IpSemStat.LOG.isDebugEnabled()) {
    	_IpSemStat.LOG.debug( "updating ssDateModif from " + ssDateModif() + " to " + value);
    }
    takeStoredValueForKey(value, "ssDateModif");
  }


  public static IpSemStat createIpSemStat(EOEditingContext editingContext, Integer fannKey
, Integer idiplNumero
, String inscPsIncomplete
, Integer msemKey
) {
    IpSemStat eo = (IpSemStat) EOUtilities.createAndInsertInstance(editingContext, _IpSemStat.ENTITY_NAME);    
		eo.setFannKey(fannKey);
		eo.setIdiplNumero(idiplNumero);
		eo.setInscPsIncomplete(inscPsIncomplete);
		eo.setMsemKey(msemKey);
    return eo;
  }

  public static NSArray<IpSemStat> fetchAllIpSemStats(EOEditingContext editingContext) {
    return _IpSemStat.fetchAllIpSemStats(editingContext, null);
  }

  public static NSArray<IpSemStat> fetchAllIpSemStats(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _IpSemStat.fetchIpSemStats(editingContext, null, sortOrderings);
  }

  public static NSArray<IpSemStat> fetchIpSemStats(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_IpSemStat.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<IpSemStat> eoObjects = (NSArray<IpSemStat>)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static IpSemStat fetchIpSemStat(EOEditingContext editingContext, String keyName, Object value) {
    return _IpSemStat.fetchIpSemStat(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static IpSemStat fetchIpSemStat(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<IpSemStat> eoObjects = _IpSemStat.fetchIpSemStats(editingContext, qualifier, null);
    IpSemStat eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (IpSemStat)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one IpSemStat that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static IpSemStat fetchRequiredIpSemStat(EOEditingContext editingContext, String keyName, Object value) {
    return _IpSemStat.fetchRequiredIpSemStat(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static IpSemStat fetchRequiredIpSemStat(EOEditingContext editingContext, EOQualifier qualifier) {
    IpSemStat eoObject = _IpSemStat.fetchIpSemStat(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no IpSemStat that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static IpSemStat localInstanceIn(EOEditingContext editingContext, IpSemStat eo) {
    IpSemStat localInstance = (eo == null) ? null : (IpSemStat)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
