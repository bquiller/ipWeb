// $LastChangedRevision$ DO NOT EDIT.  Make changes to IpwIndividuUlr.java instead.
package org.cocktail.ipweb.serveur.metier;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public abstract class _IpwIndividuUlr extends  EOGenericRecord {
	public static final String ENTITY_NAME = "IpwIndividuUlr";

	// Attributes
	public static final String CATEGORIE_PRINC_KEY = "categoriePrinc";
	public static final String C_CIVILITE_KEY = "cCivilite";
	public static final String D_DECES_KEY = "dDeces";
	public static final String D_NAISSANCE_KEY = "dNaissance";
	public static final String IND_ACTIVITE_KEY = "indActivite";
	public static final String IND_C_SITUATION_FAMILLE_KEY = "indCSituationFamille";
	public static final String IND_PHOTO_KEY = "indPhoto";
	public static final String IND_QUALITE_KEY = "indQualite";
	public static final String LANGUE_PREF_KEY = "languePref";
	public static final String NO_INDIVIDU_KEY = "noIndividu";
	public static final String NOM_PATRONYMIQUE_KEY = "nomPatronymique";
	public static final String NOM_USUEL_KEY = "nomUsuel";
	public static final String PERS_ID_KEY = "persId";
	public static final String PRENOM_KEY = "prenom";
	public static final String TEM_SS_DIPLOME_KEY = "temSsDiplome";
	public static final String TEM_VALIDE_KEY = "temValide";
	public static final String VILLE_DE_NAISSANCE_KEY = "villeDeNaissance";

	// Relationships
	public static final String TO_ETUDIANT_KEY = "toEtudiant";
	public static final String TO_PERS_TELEPHONE_KEY = "toPersTelephone";
	public static final String TO_RPT_COMPTE_KEY = "toRptCompte";
	public static final String V_SITUATIONS_INDIVIDU_KEY = "vSituationsIndividu";

  private static Logger LOG = Logger.getLogger(_IpwIndividuUlr.class);

  public IpwIndividuUlr localInstanceIn(EOEditingContext editingContext) {
    IpwIndividuUlr localInstance = (IpwIndividuUlr)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public Long categoriePrinc() {
    return (Long) storedValueForKey("categoriePrinc");
  }

  public void setCategoriePrinc(Long value) {
    if (_IpwIndividuUlr.LOG.isDebugEnabled()) {
    	_IpwIndividuUlr.LOG.debug( "updating categoriePrinc from " + categoriePrinc() + " to " + value);
    }
    takeStoredValueForKey(value, "categoriePrinc");
  }

  public String cCivilite() {
    return (String) storedValueForKey("cCivilite");
  }

  public void setCCivilite(String value) {
    if (_IpwIndividuUlr.LOG.isDebugEnabled()) {
    	_IpwIndividuUlr.LOG.debug( "updating cCivilite from " + cCivilite() + " to " + value);
    }
    takeStoredValueForKey(value, "cCivilite");
  }

  public NSTimestamp dDeces() {
    return (NSTimestamp) storedValueForKey("dDeces");
  }

  public void setDDeces(NSTimestamp value) {
    if (_IpwIndividuUlr.LOG.isDebugEnabled()) {
    	_IpwIndividuUlr.LOG.debug( "updating dDeces from " + dDeces() + " to " + value);
    }
    takeStoredValueForKey(value, "dDeces");
  }

  public NSTimestamp dNaissance() {
    return (NSTimestamp) storedValueForKey("dNaissance");
  }

  public void setDNaissance(NSTimestamp value) {
    if (_IpwIndividuUlr.LOG.isDebugEnabled()) {
    	_IpwIndividuUlr.LOG.debug( "updating dNaissance from " + dNaissance() + " to " + value);
    }
    takeStoredValueForKey(value, "dNaissance");
  }

  public String indActivite() {
    return (String) storedValueForKey("indActivite");
  }

  public void setIndActivite(String value) {
    if (_IpwIndividuUlr.LOG.isDebugEnabled()) {
    	_IpwIndividuUlr.LOG.debug( "updating indActivite from " + indActivite() + " to " + value);
    }
    takeStoredValueForKey(value, "indActivite");
  }

  public String indCSituationFamille() {
    return (String) storedValueForKey("indCSituationFamille");
  }

  public void setIndCSituationFamille(String value) {
    if (_IpwIndividuUlr.LOG.isDebugEnabled()) {
    	_IpwIndividuUlr.LOG.debug( "updating indCSituationFamille from " + indCSituationFamille() + " to " + value);
    }
    takeStoredValueForKey(value, "indCSituationFamille");
  }

  public String indPhoto() {
    return (String) storedValueForKey("indPhoto");
  }

  public void setIndPhoto(String value) {
    if (_IpwIndividuUlr.LOG.isDebugEnabled()) {
    	_IpwIndividuUlr.LOG.debug( "updating indPhoto from " + indPhoto() + " to " + value);
    }
    takeStoredValueForKey(value, "indPhoto");
  }

  public String indQualite() {
    return (String) storedValueForKey("indQualite");
  }

  public void setIndQualite(String value) {
    if (_IpwIndividuUlr.LOG.isDebugEnabled()) {
    	_IpwIndividuUlr.LOG.debug( "updating indQualite from " + indQualite() + " to " + value);
    }
    takeStoredValueForKey(value, "indQualite");
  }

  public String languePref() {
    return (String) storedValueForKey("languePref");
  }

  public void setLanguePref(String value) {
    if (_IpwIndividuUlr.LOG.isDebugEnabled()) {
    	_IpwIndividuUlr.LOG.debug( "updating languePref from " + languePref() + " to " + value);
    }
    takeStoredValueForKey(value, "languePref");
  }

  public Integer noIndividu() {
    return (Integer) storedValueForKey("noIndividu");
  }

  public void setNoIndividu(Integer value) {
    if (_IpwIndividuUlr.LOG.isDebugEnabled()) {
    	_IpwIndividuUlr.LOG.debug( "updating noIndividu from " + noIndividu() + " to " + value);
    }
    takeStoredValueForKey(value, "noIndividu");
  }

  public String nomPatronymique() {
    return (String) storedValueForKey("nomPatronymique");
  }

  public void setNomPatronymique(String value) {
    if (_IpwIndividuUlr.LOG.isDebugEnabled()) {
    	_IpwIndividuUlr.LOG.debug( "updating nomPatronymique from " + nomPatronymique() + " to " + value);
    }
    takeStoredValueForKey(value, "nomPatronymique");
  }

  public String nomUsuel() {
    return (String) storedValueForKey("nomUsuel");
  }

  public void setNomUsuel(String value) {
    if (_IpwIndividuUlr.LOG.isDebugEnabled()) {
    	_IpwIndividuUlr.LOG.debug( "updating nomUsuel from " + nomUsuel() + " to " + value);
    }
    takeStoredValueForKey(value, "nomUsuel");
  }

  public Integer persId() {
    return (Integer) storedValueForKey("persId");
  }

  public void setPersId(Integer value) {
    if (_IpwIndividuUlr.LOG.isDebugEnabled()) {
    	_IpwIndividuUlr.LOG.debug( "updating persId from " + persId() + " to " + value);
    }
    takeStoredValueForKey(value, "persId");
  }

  public String prenom() {
    return (String) storedValueForKey("prenom");
  }

  public void setPrenom(String value) {
    if (_IpwIndividuUlr.LOG.isDebugEnabled()) {
    	_IpwIndividuUlr.LOG.debug( "updating prenom from " + prenom() + " to " + value);
    }
    takeStoredValueForKey(value, "prenom");
  }

  public String temSsDiplome() {
    return (String) storedValueForKey("temSsDiplome");
  }

  public void setTemSsDiplome(String value) {
    if (_IpwIndividuUlr.LOG.isDebugEnabled()) {
    	_IpwIndividuUlr.LOG.debug( "updating temSsDiplome from " + temSsDiplome() + " to " + value);
    }
    takeStoredValueForKey(value, "temSsDiplome");
  }

  public String temValide() {
    return (String) storedValueForKey("temValide");
  }

  public void setTemValide(String value) {
    if (_IpwIndividuUlr.LOG.isDebugEnabled()) {
    	_IpwIndividuUlr.LOG.debug( "updating temValide from " + temValide() + " to " + value);
    }
    takeStoredValueForKey(value, "temValide");
  }

  public String villeDeNaissance() {
    return (String) storedValueForKey("villeDeNaissance");
  }

  public void setVilleDeNaissance(String value) {
    if (_IpwIndividuUlr.LOG.isDebugEnabled()) {
    	_IpwIndividuUlr.LOG.debug( "updating villeDeNaissance from " + villeDeNaissance() + " to " + value);
    }
    takeStoredValueForKey(value, "villeDeNaissance");
  }

  public EOGenericRecord toEtudiant() {
    return (EOGenericRecord)storedValueForKey("toEtudiant");
  }

  public void setToEtudiantRelationship(EOGenericRecord value) {
    if (_IpwIndividuUlr.LOG.isDebugEnabled()) {
      _IpwIndividuUlr.LOG.debug("updating toEtudiant from " + toEtudiant() + " to " + value);
    }
    if (value == null) {
    	EOGenericRecord oldValue = toEtudiant();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "toEtudiant");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "toEtudiant");
    }
  }
  
  public org.cocktail.ipweb.serveur.metier.IpwPersonneTelephone toPersTelephone() {
    return (org.cocktail.ipweb.serveur.metier.IpwPersonneTelephone)storedValueForKey("toPersTelephone");
  }

  public void setToPersTelephoneRelationship(org.cocktail.ipweb.serveur.metier.IpwPersonneTelephone value) {
    if (_IpwIndividuUlr.LOG.isDebugEnabled()) {
      _IpwIndividuUlr.LOG.debug("updating toPersTelephone from " + toPersTelephone() + " to " + value);
    }
    if (value == null) {
    	org.cocktail.ipweb.serveur.metier.IpwPersonneTelephone oldValue = toPersTelephone();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "toPersTelephone");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "toPersTelephone");
    }
  }
  
  public org.cocktail.ipweb.serveur.metier.VSituationsIndividu vSituationsIndividu() {
    return (org.cocktail.ipweb.serveur.metier.VSituationsIndividu)storedValueForKey("vSituationsIndividu");
  }

  public void setVSituationsIndividuRelationship(org.cocktail.ipweb.serveur.metier.VSituationsIndividu value) {
    if (_IpwIndividuUlr.LOG.isDebugEnabled()) {
      _IpwIndividuUlr.LOG.debug("updating vSituationsIndividu from " + vSituationsIndividu() + " to " + value);
    }
    if (value == null) {
    	org.cocktail.ipweb.serveur.metier.VSituationsIndividu oldValue = vSituationsIndividu();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "vSituationsIndividu");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "vSituationsIndividu");
    }
  }
  
  public NSArray<org.cocktail.ipweb.serveur.metier.EORepartCompte> toRptCompte() {
    return (NSArray<org.cocktail.ipweb.serveur.metier.EORepartCompte>)storedValueForKey("toRptCompte");
  }

  public NSArray<org.cocktail.ipweb.serveur.metier.EORepartCompte> toRptCompte(EOQualifier qualifier) {
    return toRptCompte(qualifier, null, false);
  }

  public NSArray<org.cocktail.ipweb.serveur.metier.EORepartCompte> toRptCompte(EOQualifier qualifier, boolean fetch) {
    return toRptCompte(qualifier, null, fetch);
  }

  public NSArray<org.cocktail.ipweb.serveur.metier.EORepartCompte> toRptCompte(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings, boolean fetch) {
    NSArray<org.cocktail.ipweb.serveur.metier.EORepartCompte> results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(org.cocktail.ipweb.serveur.metier.EORepartCompte.TO_INDIVIDU_ULR_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = org.cocktail.ipweb.serveur.metier.EORepartCompte.fetchIpwRepartComptes(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = toRptCompte();
      if (qualifier != null) {
        results = (NSArray<org.cocktail.ipweb.serveur.metier.EORepartCompte>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<org.cocktail.ipweb.serveur.metier.EORepartCompte>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToToRptCompteRelationship(org.cocktail.ipweb.serveur.metier.EORepartCompte object) {
    if (_IpwIndividuUlr.LOG.isDebugEnabled()) {
      _IpwIndividuUlr.LOG.debug("adding " + object + " to toRptCompte relationship");
    }
    addObjectToBothSidesOfRelationshipWithKey(object, "toRptCompte");
  }

  public void removeFromToRptCompteRelationship(org.cocktail.ipweb.serveur.metier.EORepartCompte object) {
    if (_IpwIndividuUlr.LOG.isDebugEnabled()) {
      _IpwIndividuUlr.LOG.debug("removing " + object + " from toRptCompte relationship");
    }
    removeObjectFromBothSidesOfRelationshipWithKey(object, "toRptCompte");
  }

  public org.cocktail.ipweb.serveur.metier.EORepartCompte createToRptCompteRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("IpwRepartCompte");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "toRptCompte");
    return (org.cocktail.ipweb.serveur.metier.EORepartCompte) eo;
  }

  public void deleteToRptCompteRelationship(org.cocktail.ipweb.serveur.metier.EORepartCompte object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "toRptCompte");
    editingContext().deleteObject(object);
  }

  public void deleteAllToRptCompteRelationships() {
    Enumeration objects = toRptCompte().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteToRptCompteRelationship((org.cocktail.ipweb.serveur.metier.EORepartCompte)objects.nextElement());
    }
  }


  public static IpwIndividuUlr createIpwIndividuUlr(EOEditingContext editingContext, String cCivilite
, NSTimestamp dNaissance
, Integer noIndividu
, String nomUsuel
, Integer persId
, String prenom
, String temSsDiplome
, String temValide
) {
    IpwIndividuUlr eo = (IpwIndividuUlr) EOUtilities.createAndInsertInstance(editingContext, _IpwIndividuUlr.ENTITY_NAME);    
		eo.setCCivilite(cCivilite);
		eo.setDNaissance(dNaissance);
		eo.setNoIndividu(noIndividu);
		eo.setNomUsuel(nomUsuel);
		eo.setPersId(persId);
		eo.setPrenom(prenom);
		eo.setTemSsDiplome(temSsDiplome);
		eo.setTemValide(temValide);
    return eo;
  }

  public static NSArray<IpwIndividuUlr> fetchAllIpwIndividuUlrs(EOEditingContext editingContext) {
    return _IpwIndividuUlr.fetchAllIpwIndividuUlrs(editingContext, null);
  }

  public static NSArray<IpwIndividuUlr> fetchAllIpwIndividuUlrs(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _IpwIndividuUlr.fetchIpwIndividuUlrs(editingContext, null, sortOrderings);
  }

  public static NSArray<IpwIndividuUlr> fetchIpwIndividuUlrs(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_IpwIndividuUlr.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<IpwIndividuUlr> eoObjects = (NSArray<IpwIndividuUlr>)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static IpwIndividuUlr fetchIpwIndividuUlr(EOEditingContext editingContext, String keyName, Object value) {
    return _IpwIndividuUlr.fetchIpwIndividuUlr(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static IpwIndividuUlr fetchIpwIndividuUlr(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<IpwIndividuUlr> eoObjects = _IpwIndividuUlr.fetchIpwIndividuUlrs(editingContext, qualifier, null);
    IpwIndividuUlr eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (IpwIndividuUlr)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one IpwIndividuUlr that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static IpwIndividuUlr fetchRequiredIpwIndividuUlr(EOEditingContext editingContext, String keyName, Object value) {
    return _IpwIndividuUlr.fetchRequiredIpwIndividuUlr(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static IpwIndividuUlr fetchRequiredIpwIndividuUlr(EOEditingContext editingContext, EOQualifier qualifier) {
    IpwIndividuUlr eoObject = _IpwIndividuUlr.fetchIpwIndividuUlr(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no IpwIndividuUlr that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static IpwIndividuUlr localInstanceIn(EOEditingContext editingContext, IpwIndividuUlr eo) {
    IpwIndividuUlr localInstance = (eo == null) ? null : (IpwIndividuUlr)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
