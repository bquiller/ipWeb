// $LastChangedRevision$ DO NOT EDIT.  Make changes to IpChoixEc.java instead.
package org.cocktail.ipweb.serveur.metier;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public abstract class _IpChoixEc extends  EOGenericRecord {
	public static final String ENTITY_NAME = "IpChoixEc";

	// Attributes
	public static final String CE_DATE_CHOIX_KEY = "ceDateChoix";
	public static final String CHOIX_INTEGRE_KEY = "choixIntegre";
	public static final String FANN_KEY_KEY = "fannKey";
	public static final String IDIPL_NUMERO_KEY = "idiplNumero";
	public static final String IMREC_SEMESTRE_KEY = "imrecSemestre";
	public static final String MREC_KEY_KEY = "mrecKey";
	public static final String MRUE_KEY_KEY = "mrueKey";
	public static final String MSEM_KEY_KEY = "msemKey";

	// Relationships

  private static Logger LOG = Logger.getLogger(_IpChoixEc.class);

  public IpChoixEc localInstanceIn(EOEditingContext editingContext) {
    IpChoixEc localInstance = (IpChoixEc)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public NSTimestamp ceDateChoix() {
    return (NSTimestamp) storedValueForKey("ceDateChoix");
  }

  public void setCeDateChoix(NSTimestamp value) {
    if (_IpChoixEc.LOG.isDebugEnabled()) {
    	_IpChoixEc.LOG.debug( "updating ceDateChoix from " + ceDateChoix() + " to " + value);
    }
    takeStoredValueForKey(value, "ceDateChoix");
  }

  public String choixIntegre() {
    return (String) storedValueForKey("choixIntegre");
  }

  public void setChoixIntegre(String value) {
    if (_IpChoixEc.LOG.isDebugEnabled()) {
    	_IpChoixEc.LOG.debug( "updating choixIntegre from " + choixIntegre() + " to " + value);
    }
    takeStoredValueForKey(value, "choixIntegre");
  }

  public Integer fannKey() {
    return (Integer) storedValueForKey("fannKey");
  }

  public void setFannKey(Integer value) {
    if (_IpChoixEc.LOG.isDebugEnabled()) {
    	_IpChoixEc.LOG.debug( "updating fannKey from " + fannKey() + " to " + value);
    }
    takeStoredValueForKey(value, "fannKey");
  }

  public Integer idiplNumero() {
    return (Integer) storedValueForKey("idiplNumero");
  }

  public void setIdiplNumero(Integer value) {
    if (_IpChoixEc.LOG.isDebugEnabled()) {
    	_IpChoixEc.LOG.debug( "updating idiplNumero from " + idiplNumero() + " to " + value);
    }
    takeStoredValueForKey(value, "idiplNumero");
  }

  public Integer imrecSemestre() {
    return (Integer) storedValueForKey("imrecSemestre");
  }

  public void setImrecSemestre(Integer value) {
    if (_IpChoixEc.LOG.isDebugEnabled()) {
    	_IpChoixEc.LOG.debug( "updating imrecSemestre from " + imrecSemestre() + " to " + value);
    }
    takeStoredValueForKey(value, "imrecSemestre");
  }

  public Integer mrecKey() {
    return (Integer) storedValueForKey("mrecKey");
  }

  public void setMrecKey(Integer value) {
    if (_IpChoixEc.LOG.isDebugEnabled()) {
    	_IpChoixEc.LOG.debug( "updating mrecKey from " + mrecKey() + " to " + value);
    }
    takeStoredValueForKey(value, "mrecKey");
  }

  public Integer mrueKey() {
    return (Integer) storedValueForKey("mrueKey");
  }

  public void setMrueKey(Integer value) {
    if (_IpChoixEc.LOG.isDebugEnabled()) {
    	_IpChoixEc.LOG.debug( "updating mrueKey from " + mrueKey() + " to " + value);
    }
    takeStoredValueForKey(value, "mrueKey");
  }

  public Integer msemKey() {
    return (Integer) storedValueForKey("msemKey");
  }

  public void setMsemKey(Integer value) {
    if (_IpChoixEc.LOG.isDebugEnabled()) {
    	_IpChoixEc.LOG.debug( "updating msemKey from " + msemKey() + " to " + value);
    }
    takeStoredValueForKey(value, "msemKey");
  }


  public static IpChoixEc createIpChoixEc(EOEditingContext editingContext, String choixIntegre
, Integer fannKey
, Integer idiplNumero
, Integer imrecSemestre
, Integer mrecKey
, Integer mrueKey
, Integer msemKey
) {
    IpChoixEc eo = (IpChoixEc) EOUtilities.createAndInsertInstance(editingContext, _IpChoixEc.ENTITY_NAME);    
		eo.setChoixIntegre(choixIntegre);
		eo.setFannKey(fannKey);
		eo.setIdiplNumero(idiplNumero);
		eo.setImrecSemestre(imrecSemestre);
		eo.setMrecKey(mrecKey);
		eo.setMrueKey(mrueKey);
		eo.setMsemKey(msemKey);
    return eo;
  }

  public static NSArray<IpChoixEc> fetchAllIpChoixEcs(EOEditingContext editingContext) {
    return _IpChoixEc.fetchAllIpChoixEcs(editingContext, null);
  }

  public static NSArray<IpChoixEc> fetchAllIpChoixEcs(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _IpChoixEc.fetchIpChoixEcs(editingContext, null, sortOrderings);
  }

  public static NSArray<IpChoixEc> fetchIpChoixEcs(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_IpChoixEc.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<IpChoixEc> eoObjects = (NSArray<IpChoixEc>)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static IpChoixEc fetchIpChoixEc(EOEditingContext editingContext, String keyName, Object value) {
    return _IpChoixEc.fetchIpChoixEc(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static IpChoixEc fetchIpChoixEc(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<IpChoixEc> eoObjects = _IpChoixEc.fetchIpChoixEcs(editingContext, qualifier, null);
    IpChoixEc eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (IpChoixEc)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one IpChoixEc that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static IpChoixEc fetchRequiredIpChoixEc(EOEditingContext editingContext, String keyName, Object value) {
    return _IpChoixEc.fetchRequiredIpChoixEc(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static IpChoixEc fetchRequiredIpChoixEc(EOEditingContext editingContext, EOQualifier qualifier) {
    IpChoixEc eoObject = _IpChoixEc.fetchIpChoixEc(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no IpChoixEc that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static IpChoixEc localInstanceIn(EOEditingContext editingContext, IpChoixEc eo) {
    IpChoixEc localInstance = (eo == null) ? null : (IpChoixEc)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
