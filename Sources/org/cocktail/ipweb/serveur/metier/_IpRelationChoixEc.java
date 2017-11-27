// $LastChangedRevision$ DO NOT EDIT.  Make changes to IpRelationChoixEc.java instead.
package org.cocktail.ipweb.serveur.metier;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public abstract class _IpRelationChoixEc extends  EOGenericRecord {
	public static final String ENTITY_NAME = "IpRelationChoixEc";

	// Attributes
	public static final String FANN_KEY_KEY = "fannKey";
	public static final String MREC_KEY_CIBLE_KEY = "mrecKeyCible";
	public static final String MSEM_KEY_KEY = "msemKey";
	public static final String RCE_COMMENTAIRE_CONTRAINTE_KEY = "rceCommentaireContrainte";
	public static final String RCE_ESPACE_SOLUTION_KEY = "rceEspaceSolution";
	public static final String RCE_FORMULE_CONTRAINTE_KEY = "rceFormuleContrainte";
	public static final String RCE_LISTE_VARIABLES_KEY = "rceListeVariables";
	public static final String RCE_ORDRE_KEY = "rceOrdre";
	public static final String RCE_TYPE_RELATION_KEY = "rceTypeRelation";

	// Relationships

  private static Logger LOG = Logger.getLogger(_IpRelationChoixEc.class);

  public IpRelationChoixEc localInstanceIn(EOEditingContext editingContext) {
    IpRelationChoixEc localInstance = (IpRelationChoixEc)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public Integer fannKey() {
    return (Integer) storedValueForKey("fannKey");
  }

  public void setFannKey(Integer value) {
    if (_IpRelationChoixEc.LOG.isDebugEnabled()) {
    	_IpRelationChoixEc.LOG.debug( "updating fannKey from " + fannKey() + " to " + value);
    }
    takeStoredValueForKey(value, "fannKey");
  }

  public Integer mrecKeyCible() {
    return (Integer) storedValueForKey("mrecKeyCible");
  }

  public void setMrecKeyCible(Integer value) {
    if (_IpRelationChoixEc.LOG.isDebugEnabled()) {
    	_IpRelationChoixEc.LOG.debug( "updating mrecKeyCible from " + mrecKeyCible() + " to " + value);
    }
    takeStoredValueForKey(value, "mrecKeyCible");
  }

  public Integer msemKey() {
    return (Integer) storedValueForKey("msemKey");
  }

  public void setMsemKey(Integer value) {
    if (_IpRelationChoixEc.LOG.isDebugEnabled()) {
    	_IpRelationChoixEc.LOG.debug( "updating msemKey from " + msemKey() + " to " + value);
    }
    takeStoredValueForKey(value, "msemKey");
  }

  public String rceCommentaireContrainte() {
    return (String) storedValueForKey("rceCommentaireContrainte");
  }

  public void setRceCommentaireContrainte(String value) {
    if (_IpRelationChoixEc.LOG.isDebugEnabled()) {
    	_IpRelationChoixEc.LOG.debug( "updating rceCommentaireContrainte from " + rceCommentaireContrainte() + " to " + value);
    }
    takeStoredValueForKey(value, "rceCommentaireContrainte");
  }

  public String rceEspaceSolution() {
    return (String) storedValueForKey("rceEspaceSolution");
  }

  public void setRceEspaceSolution(String value) {
    if (_IpRelationChoixEc.LOG.isDebugEnabled()) {
    	_IpRelationChoixEc.LOG.debug( "updating rceEspaceSolution from " + rceEspaceSolution() + " to " + value);
    }
    takeStoredValueForKey(value, "rceEspaceSolution");
  }

  public String rceFormuleContrainte() {
    return (String) storedValueForKey("rceFormuleContrainte");
  }

  public void setRceFormuleContrainte(String value) {
    if (_IpRelationChoixEc.LOG.isDebugEnabled()) {
    	_IpRelationChoixEc.LOG.debug( "updating rceFormuleContrainte from " + rceFormuleContrainte() + " to " + value);
    }
    takeStoredValueForKey(value, "rceFormuleContrainte");
  }

  public String rceListeVariables() {
    return (String) storedValueForKey("rceListeVariables");
  }

  public void setRceListeVariables(String value) {
    if (_IpRelationChoixEc.LOG.isDebugEnabled()) {
    	_IpRelationChoixEc.LOG.debug( "updating rceListeVariables from " + rceListeVariables() + " to " + value);
    }
    takeStoredValueForKey(value, "rceListeVariables");
  }

  public Integer rceOrdre() {
    return (Integer) storedValueForKey("rceOrdre");
  }

  public void setRceOrdre(Integer value) {
    if (_IpRelationChoixEc.LOG.isDebugEnabled()) {
    	_IpRelationChoixEc.LOG.debug( "updating rceOrdre from " + rceOrdre() + " to " + value);
    }
    takeStoredValueForKey(value, "rceOrdre");
  }

  public String rceTypeRelation() {
    return (String) storedValueForKey("rceTypeRelation");
  }

  public void setRceTypeRelation(String value) {
    if (_IpRelationChoixEc.LOG.isDebugEnabled()) {
    	_IpRelationChoixEc.LOG.debug( "updating rceTypeRelation from " + rceTypeRelation() + " to " + value);
    }
    takeStoredValueForKey(value, "rceTypeRelation");
  }


  public static IpRelationChoixEc createIpRelationChoixEc(EOEditingContext editingContext, Integer fannKey
, Integer msemKey
, String rceFormuleContrainte
, Integer rceOrdre
) {
    IpRelationChoixEc eo = (IpRelationChoixEc) EOUtilities.createAndInsertInstance(editingContext, _IpRelationChoixEc.ENTITY_NAME);    
		eo.setFannKey(fannKey);
		eo.setMsemKey(msemKey);
		eo.setRceFormuleContrainte(rceFormuleContrainte);
		eo.setRceOrdre(rceOrdre);
    return eo;
  }

  public static NSArray<IpRelationChoixEc> fetchAllIpRelationChoixEcs(EOEditingContext editingContext) {
    return _IpRelationChoixEc.fetchAllIpRelationChoixEcs(editingContext, null);
  }

  public static NSArray<IpRelationChoixEc> fetchAllIpRelationChoixEcs(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _IpRelationChoixEc.fetchIpRelationChoixEcs(editingContext, null, sortOrderings);
  }

  public static NSArray<IpRelationChoixEc> fetchIpRelationChoixEcs(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_IpRelationChoixEc.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<IpRelationChoixEc> eoObjects = (NSArray<IpRelationChoixEc>)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static IpRelationChoixEc fetchIpRelationChoixEc(EOEditingContext editingContext, String keyName, Object value) {
    return _IpRelationChoixEc.fetchIpRelationChoixEc(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static IpRelationChoixEc fetchIpRelationChoixEc(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<IpRelationChoixEc> eoObjects = _IpRelationChoixEc.fetchIpRelationChoixEcs(editingContext, qualifier, null);
    IpRelationChoixEc eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (IpRelationChoixEc)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one IpRelationChoixEc that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static IpRelationChoixEc fetchRequiredIpRelationChoixEc(EOEditingContext editingContext, String keyName, Object value) {
    return _IpRelationChoixEc.fetchRequiredIpRelationChoixEc(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static IpRelationChoixEc fetchRequiredIpRelationChoixEc(EOEditingContext editingContext, EOQualifier qualifier) {
    IpRelationChoixEc eoObject = _IpRelationChoixEc.fetchIpRelationChoixEc(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no IpRelationChoixEc that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static IpRelationChoixEc localInstanceIn(EOEditingContext editingContext, IpRelationChoixEc eo) {
    IpRelationChoixEc localInstance = (eo == null) ? null : (IpRelationChoixEc)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
