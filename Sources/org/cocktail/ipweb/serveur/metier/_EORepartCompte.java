// $LastChangedRevision$ DO NOT EDIT.  Make changes to EORepartCompte.java instead.
package org.cocktail.ipweb.serveur.metier;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public abstract class _EORepartCompte extends  EOGenericRecord {
	public static final String ENTITY_NAME = "IpwRepartCompte";

	// Attributes
	public static final String D_CREATION_KEY = "dCreation";
	public static final String D_MODIFICATION_KEY = "dModification";

	// Relationships
	public static final String TO_COMPTE_KEY = "toCompte";
	public static final String TO_INDIVIDU_ULR_KEY = "toIndividuUlr";

  private static Logger LOG = Logger.getLogger(_EORepartCompte.class);

  public EORepartCompte localInstanceIn(EOEditingContext editingContext) {
    EORepartCompte localInstance = (EORepartCompte)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public NSTimestamp dCreation() {
    return (NSTimestamp) storedValueForKey("dCreation");
  }

  public void setDCreation(NSTimestamp value) {
    if (_EORepartCompte.LOG.isDebugEnabled()) {
    	_EORepartCompte.LOG.debug( "updating dCreation from " + dCreation() + " to " + value);
    }
    takeStoredValueForKey(value, "dCreation");
  }

  public NSTimestamp dModification() {
    return (NSTimestamp) storedValueForKey("dModification");
  }

  public void setDModification(NSTimestamp value) {
    if (_EORepartCompte.LOG.isDebugEnabled()) {
    	_EORepartCompte.LOG.debug( "updating dModification from " + dModification() + " to " + value);
    }
    takeStoredValueForKey(value, "dModification");
  }

  public org.cocktail.ipweb.serveur.metier.EOCompte toCompte() {
    return (org.cocktail.ipweb.serveur.metier.EOCompte)storedValueForKey("toCompte");
  }

  public void setToCompteRelationship(org.cocktail.ipweb.serveur.metier.EOCompte value) {
    if (_EORepartCompte.LOG.isDebugEnabled()) {
      _EORepartCompte.LOG.debug("updating toCompte from " + toCompte() + " to " + value);
    }
    if (value == null) {
    	org.cocktail.ipweb.serveur.metier.EOCompte oldValue = toCompte();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "toCompte");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "toCompte");
    }
  }
  
  public org.cocktail.ipweb.serveur.metier.IpwIndividuUlr toIndividuUlr() {
    return (org.cocktail.ipweb.serveur.metier.IpwIndividuUlr)storedValueForKey("toIndividuUlr");
  }

  public void setToIndividuUlrRelationship(org.cocktail.ipweb.serveur.metier.IpwIndividuUlr value) {
    if (_EORepartCompte.LOG.isDebugEnabled()) {
      _EORepartCompte.LOG.debug("updating toIndividuUlr from " + toIndividuUlr() + " to " + value);
    }
    if (value == null) {
    	org.cocktail.ipweb.serveur.metier.IpwIndividuUlr oldValue = toIndividuUlr();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "toIndividuUlr");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "toIndividuUlr");
    }
  }
  

  public static EORepartCompte createIpwRepartCompte(EOEditingContext editingContext, NSTimestamp dCreation
, NSTimestamp dModification
, org.cocktail.ipweb.serveur.metier.EOCompte toCompte, org.cocktail.ipweb.serveur.metier.IpwIndividuUlr toIndividuUlr) {
    EORepartCompte eo = (EORepartCompte) EOUtilities.createAndInsertInstance(editingContext, _EORepartCompte.ENTITY_NAME);    
		eo.setDCreation(dCreation);
		eo.setDModification(dModification);
    eo.setToCompteRelationship(toCompte);
    eo.setToIndividuUlrRelationship(toIndividuUlr);
    return eo;
  }

  public static NSArray<EORepartCompte> fetchAllIpwRepartComptes(EOEditingContext editingContext) {
    return _EORepartCompte.fetchAllIpwRepartComptes(editingContext, null);
  }

  public static NSArray<EORepartCompte> fetchAllIpwRepartComptes(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _EORepartCompte.fetchIpwRepartComptes(editingContext, null, sortOrderings);
  }

  public static NSArray<EORepartCompte> fetchIpwRepartComptes(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_EORepartCompte.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<EORepartCompte> eoObjects = (NSArray<EORepartCompte>)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static EORepartCompte fetchIpwRepartCompte(EOEditingContext editingContext, String keyName, Object value) {
    return _EORepartCompte.fetchIpwRepartCompte(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static EORepartCompte fetchIpwRepartCompte(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<EORepartCompte> eoObjects = _EORepartCompte.fetchIpwRepartComptes(editingContext, qualifier, null);
    EORepartCompte eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (EORepartCompte)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one IpwRepartCompte that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static EORepartCompte fetchRequiredIpwRepartCompte(EOEditingContext editingContext, String keyName, Object value) {
    return _EORepartCompte.fetchRequiredIpwRepartCompte(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static EORepartCompte fetchRequiredIpwRepartCompte(EOEditingContext editingContext, EOQualifier qualifier) {
    EORepartCompte eoObject = _EORepartCompte.fetchIpwRepartCompte(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no IpwRepartCompte that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static EORepartCompte localInstanceIn(EOEditingContext editingContext, EORepartCompte eo) {
    EORepartCompte localInstance = (eo == null) ? null : (EORepartCompte)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
