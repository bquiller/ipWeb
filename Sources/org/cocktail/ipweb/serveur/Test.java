package org.cocktail.ipweb.serveur;

import org.cocktail.ipweb.serveur.components.Main;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;

// Generated by the WOLips Templateengine Plug-in at 25 f�vr. 2008 11:34:15

@SuppressWarnings("serial")
public class Test extends com.webobjects.appserver.WOComponent {
    public String login;
   public Test(WOContext context) {
       super(context);
   }
     public WOComponent redirection(){
       Main page =  (Main)pageWithName("Main");          
       page.login = login;
       return page.toProfile();
   }
  }