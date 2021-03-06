package org.cocktail.ipweb.serveur.components.page;
// Generated by the WOLips Templateengine Plug-in at 20 juin 2008 09:49:01

import java.text.ParsePosition;
import java.util.GregorianCalendar;

import org.cocktail.fwkcktlwebapp.common.util.DateCtrl;
import org.cocktail.fwkcktlwebapp.server.components.CktlWebComponent;
import org.cocktail.ipweb.serveur.Session;
import org.cocktail.ipweb.serveur.components.Main;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSTimestamp;
import com.webobjects.foundation.NSTimestampFormatter;

/* ATTENTE
import org.cocktail.scolarix.serveur.finder.FinderPreCandidat;
import org.cocktail.scolarix.serveur.finder.FinderPreEtudiant;
import org.cocktail.scolarix.serveur.exception.EtudiantException;
import org.cocktail.scolarix.serveur.metier.eos.EOPreEtudiant;
import org.cocktail.scolarix.serveur.metier.eos.EOPreCandidat;
*/

public class AccesIpAnonyme extends CktlWebComponent {

    protected String identifiant;
    protected String password;
    
    public boolean erreurLogin;
    public String messError;
    protected String beaIne;

    private NSTimestamp dateNaissEtud;

    public AccesIpAnonyme(WOContext context) {
        super(context);
        
    	erreurLogin = false;
       messError = "";
    }
        
    public String getIdentifiant()
    {
        return identifiant;
    }
    public void setIdentifiant(String newIdentifiant)
    {
        identifiant = newIdentifiant;
    }

    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String newPassword)
    {
        password = newPassword;
    }

    public WOComponent valideConnexionIpAnonyme()
    {
    	
    	messError = "Votre identifiant ("+identifiant+") n'est pas reconnu... \nVeuillez rééssayer.";
        return null;
    }

    public String messError()
    {
    	if (erreurLogin) return messError;
    	else return null;
    }
    
    // Retour à la page d'accueil standard....
    public WOComponent retourAccueil()
    {
    	Main page = (Main)pageWithName("Main");
    	return page;
    }

    // Passe ici quand on clique sur "valider" après avoir saisi son BEA...
    public WOComponent validerSaisie()
    {
    	/* ATTENTE
        erreurLogin = false;
        if (beaIne != null && beaIne.length() == 11 && dateNaissEtud != null) {
        	// vérifier que le pré-étudiant n'a pas été identifié par la scol, via ScolPedagogie...
        	// auquel cas, il faut qu'il passe par l'accés authentifié.
        	if (preinscritToujoursAnonyme(beaIne)) {
        		// essayer de chopper un pré-étudiant selon les infos données.... 
        		try {
        			EOPreEtudiant lePreEtud =  FinderPreEtudiant.getPreEtudiantIne(((Session)session()).defaultEditingContext()  , beaIne, dateNaissEtud);
        			String emailCand = null;
        			if (lePreEtud != null) {
        				 Integer preEtudNum = lePreEtud.numero();
        				 EOPreCandidat lePreCand = FinderPreCandidat.getPreCandidatNumero(((Session)session()).defaultEditingContext(), preEtudNum,dateNaissEtud);
        				 if (lePreCand != null) emailCand = lePreCand.candEmailScol();
        			}
        			Main page =  (Main)pageWithName("Main");
        			((Session)session()).setPhotoCtrlr(new PhotoCtrlr((Session)session()));
        			return page.toProfilePreInscrit(lePreEtud,beaIne, emailCand);
        		}
        		catch(EtudiantException e) {
        			erreurLogin = true;
        			messError = e.getMessage();
        		}
        	}
        	else {
        		erreurLogin = true;
        		messError = "ATTENTION : Vous êtes inscrit administrativement ! Veuillez vous identifier sur la page d'accueil..."; 
        	}
        }
        else {
        	erreurLogin = true;
    		messError = "Vous devez saisir un INE correct (11 car.) et une date de naissance...";
        }
        */
       return null;
    }

    // renvoyer vrai si le pré-étudiant n'a pas été identifié par la scol, via ScolPedagogie...
    private boolean preinscritToujoursAnonyme(String beaIne) {
 		NSArray bindings = new NSArray(new Object[] {beaIne,
				new Integer(((Session)session()).getAnneeEnCours())});
    	
		EOQualifier qualifier = EOQualifier.qualifierWithQualifierFormat(
				"etudCodeIne = %@ and fannKey = %@ and idiplTypeInscription <> 9", bindings);

		EOFetchSpecification fetchSpec = new EOFetchSpecification("IpwScolInscriptionEtudiant",
				qualifier, null);

		EOEditingContext ec = ((Session)session()).defaultEditingContext();

		NSArray inscIdentifiees = ec.objectsWithFetchSpecification(fetchSpec);

		
    	return !(inscIdentifiees != null && inscIdentifiees.count() > 0);
    }
    
    
    public String getBeaIne()
    {
        return beaIne;
    }
    public void setBeaIne(String newBeaIne)
    {
        beaIne = newBeaIne.toUpperCase();
    }

    /**
     * @return the dateNaissEtud
     */
    public String getDateNaissEtud() {
        return DateCtrl.dateToString(dateNaissEtud,"%d/%m/%Y");
    }

    /**
     * @param dateNaissEtud the dateNaissEtud to set
     */
    public void setDateNaissEtud(String stDateNaissEtud) {
//        this.dateNaissEtud = DateCtrl.stringToDate(stDateNaissEtud, "%d/%m/%Y");
        
        try
        {
            NSTimestampFormatter formatter = new NSTimestampFormatter("%d/%m/%Y");
            dateNaissEtud = (NSTimestamp)formatter.parseObjectInUTC(stDateNaissEtud, new ParsePosition(0));
        }
        catch(Exception exception) {
            dateNaissEtud = null;
        }
        
        // Correction du siecle si ann�e sur 2 positions...
        if (dateNaissEtud != null) {
            GregorianCalendar myCalendar = new GregorianCalendar();
            myCalendar.setTime(dateNaissEtud);
            int year = myCalendar.get(GregorianCalendar.YEAR);
            if (year < 100) {
        	if (year < 20) dateNaissEtud = dateNaissEtud.timestampByAddingGregorianUnits(2000, 0, 0, 0, 0, 0);
        	else dateNaissEtud = dateNaissEtud.timestampByAddingGregorianUnits(1900, 0, 0, 0, 0, 0);
            }
        }
    }

}
    