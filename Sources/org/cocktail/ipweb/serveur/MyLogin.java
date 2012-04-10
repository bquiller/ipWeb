package org.cocktail.ipweb.serveur;
// Generated by the WOLips TemplateEngine Plug-in at 28 sept. 2006 15:11:47

//************************************************
// TODO il faut filtrer les connexions => slt des personnes avec au moins une inscription valide en DEG pour 2007 
//************************************************

import java.text.ParsePosition;
import java.util.GregorianCalendar;

import org.cocktail.fwkcktlwebapp.common.CktlLog;
import org.cocktail.fwkcktlwebapp.common.CktlUserInfo;
import org.cocktail.fwkcktlwebapp.common.database.CktlUserInfoDB;
import org.cocktail.fwkcktlwebapp.common.util.DateCtrl;
import org.cocktail.fwkcktlwebapp.server.CktlWebSession;
import org.cocktail.fwkcktlwebapp.server.components.CktlLogin;
import org.cocktail.fwkcktlwebapp.server.components.CktlLoginResponder;
import org.cocktail.fwkcktlwebapp.server.components.CktlWebComponent;
import org.cocktail.ipweb.serveur.components.Main;
import org.cocktail.ipweb.serveur.controlleur.Compte;
import org.cocktail.ipweb.serveur.controlleur.CompteCtrlr;
import org.cocktail.ipweb.serveur.controlleur.DownloadFic;
import org.cocktail.ipweb.serveur.controlleur.PhotoCtrlr;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WORedirect;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSTimestamp;
import com.webobjects.foundation.NSTimestampFormatter;


public class MyLogin extends CktlWebComponent {
	public Responder loginResponder;
    
	
    public String login;
    public String password;
    public String messError;
    public String email;
    public String domain;
    
    public Integer numEtud;
    private NSTimestamp dateNaissEtud;
    
    public boolean erreurLogin;
    protected String nomApplication;

    
    public MyLogin(WOContext context) {
        super(context);
		loginResponder = new Responder();
		nomApplication =(String) valueForBinding("nomApplication");
		domain = ((Application)cktlApp).domaineEtudiant();
    }
    
/*	public void appendToResponse(WOResponse arg0, WOContext arg1) {
		loginResponder = new Responder();
		super.appendToResponse(arg0, arg1);
	}
*/
    
    private EOEditingContext localEContext(){
    	return session().defaultEditingContext();
    }
    
    public static  boolean action(CktlWebSession session,EOEditingContext ec){
    	return (true);
    }
    
    public boolean appUseCas(){
    	return DirectAction.useCasService();
    }
    
    // Pour l'université de Provence...
    public boolean etudiantUseCas() {
    	return ((Session)session()).monApp.etudiantUseCas();
    }
    
    
    public WOComponent goCas() {
    	WORedirect page =(WORedirect)pageWithName("WORedirect");
    	page.setUrl(DirectAction.getLoginActionURL(this.context(),true,null,false,null));
        return page;
    }
    
    public WOComponent goIpAnonymes()
    {
    	// On envoit l'étudiant sur une page où il va pouvoir se connecter via les infos de pré-inscription...
        return pageWithName("AccesIpAnonyme");
    }

    
    public boolean modeDebug() { return ((Session)session()).debug(); }
    
    public String nomApplication() {
        return nomApplication;
    }

    public void setNomApplication(String newNomApplication) {
        nomApplication = newNomApplication;
    }

    public DownloadFic lanceAffNotice() {
	DownloadFic nextPage = (DownloadFic)pageWithName("DownloadFic");
	String cheminFic = ((Session)session()).monApp.cheminPourFic("IP_WEB_doc_etudiant.pdf");
	if (nextPage.initDownloadFichier(cheminFic,"application/pdf"))
	    return nextPage;
	else return null;
    }

    
    public WOComponent valideConnexion(){
    	CompteCtrlr monCC;
    	boolean passOk = false;

    	messError = null;
    	erreurLogin = false;
//  	boolean identificationEtudiant = true;	

    	// tester d'abord l'id etudiantes !
    	if (numEtud != null && dateNaissEtud != null) {
    		monCC = new CompteCtrlr((Session)session(),numEtud);
    		passOk = monCC.checkDateNaiss(dateNaissEtud);
    	}

    	// Voir si un administratif tente de se connecter (autres champs)
    	else {
			// 2 cas possibles : connexion d'un personnel (login non nul) ou d'un étudiant (evt avec son mail)
    		if (!useMail() || login !=null) {
    			if(login==null || "".equals(login))
    			{
    				erreurLogin = true;
    				messError="Erreur: identifiant obligatoire";
    				return null;
    			}
    		}
    		else {
    			if(email==null || "".equals(email) || domain==null || "".equals(domain))
    			{
    				erreurLogin = true;
    				messError="Erreur: email incomplet";
    				return null;
    			}
    		}
    		if(password==null || "".equals(password))
    		{
    			erreurLogin = true;
    			messError="Erreur: mot de passe obligatoire";
    			return null;
    		}
    		// on n'utilise pas le framework criwebapp2 car on a besoin de gerer le cryptage md5
    		if (!useMail() || login != null) monCC = new CompteCtrlr((Session)session(),login);
    		else monCC = new CompteCtrlr((Session)session(),email,domain);
    		passOk = monCC.checkPassword(password);
    	}

    	Compte cpte = null;
    	if (monCC != null) cpte = monCC.compteCourant();

    	if(cpte==null || cpte.compte()==null)
    	{
    		erreurLogin = true;
    		if (cpte==null) messError = monCC.getErreur();
    		else messError="Compte non reconnu (problème avec l'identifiant)";
    		return null;
    	}

    	if(passOk)
    	{
    		CktlUserInfoDB user = new CktlUserInfoDB(cktlApp.dataBus());
    		user.compteForPersId(monCC.persId(),true);
    		if (user.errorCode() > 0) {
    			erreurLogin = true;
    			messError="Erreur avec ce compte (pb de base test !?)";
    			return null;
    		}
    		else {
    			cktlSession().setConnectedUserInfo(user);
    			action(cktlSession(),localEContext());
    			Main page =  (Main)pageWithName("Main");
    			if (!useMail()) page.login = login;
    			else {
    				page.login = user.login();
//  				((Session)session()).setEmailCourant(email+"@"+domain);
    			}
    			((Session)session()).setCtrlrCompte(monCC);

    			((Session)session()).setPhotoCtrlr(new PhotoCtrlr((Session)session()));
    			return page.toProfile();
    		}
    	}
    	erreurLogin = true;
    	messError="Erreur: Mot de passe incorrect !";
    	return null;
    }
    
    /**
     * @return Returns the useMail.
     */
    public boolean useMail() {
        CktlLog.trace("useMail = "+((Application)application()).useEmailForLogin());
        return ((Application)application()).useEmailForLogin();
    }
    
    public boolean useIpAnonymes() {
    	CktlLog.trace("useIpAnonymes = "+((Application)application()).useIpAnonymes());
        return ((Application)application()).useIpAnonymes();
    }
    


    class Responder implements CktlLoginResponder{

		public WOComponent loginAccepted(CktlLogin arg0) {
			CktlUserInfoDB user = new CktlUserInfoDB(cktlApp.dataBus());
			user.compteForLogin(arg0.login,null,true);
//			LRLog.trace("pwd = "+arg0.password);
			if(user.errorCode()!=CktlUserInfo.ERROR_NONE)
			{
				return ((Application)application()).pageErreur("Erreur Individu : Il y a eut un problème avec l'identification !", context());
			}
			cktlSession().setConnectedUserInfo(user);
			action(cktlSession(),localEContext());
			Main page =  (Main)pageWithName("Main");
			page.login = user.login();
			return page.toProfile();
		}


		public boolean acceptLoginName(String arg0) {
			return ((Application)application()).acceptLoginName(arg0);
		}


		public boolean acceptEmptyPassword() {
			return false;
		}


		public String getRootPassword() {
			return ((Application)application()).getRootPassword();
		}
    	
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