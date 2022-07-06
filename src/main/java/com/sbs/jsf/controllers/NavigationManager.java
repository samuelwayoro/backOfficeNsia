/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.jsf.controllers;

import java.io.Serializable;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author SOCITECH-
 */
@ManagedBean
@ApplicationScoped
public class NavigationManager implements Serializable {
    public String redirectToLogin(){
        return "login.xhtml?faces-redirect=true";
    }
    
    public String toLogin(){
        return "login.xhtml";
    }
    
    public String toResiliation(){
        return "resiliation.xhtml";
    }
    
    public String redirectToHome(){
        return "private/home.xhtml?faces-redirect=true";
    }
    
     public String redirectToResiliation(){
        return "resiliation.xhtml?faces-redirect=true";
    }
     
      public String redirectToReinitialisation(){
        return "reinitialisation.xhtml?faces-redirect=true";
    }
     
      public String redirectToLogout(){
        return "logout?faces-redirect=true";
    }
      
      public String toSouscription(){
          return "souscription.xhtml";
      }
      
      public String redirectToResetPassword(){
          return "resetpassword.xhtml?faces-redirect=true";
      }
    
}
