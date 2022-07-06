/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.jsf.controllers;

import com.sbs.easymbank.dao.LogsFacade;
import com.sbs.easymbank.entities.Abonnements;
import com.sbs.easymbank.entities.Logs;
import com.sbs.easymbank.entities.Users;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import javax.faces.bean.SessionScoped;

/**
 *
 * @author alex
 */
@ManagedBean
@SessionScoped
public class SessionManager implements Serializable {
   
    @EJB
    LogsFacade logsFacade;
    private Logs logs=new Logs();
    private Abonnements selectedAbonnement;
private Users utilisateur;

   
    public void logDB(){
        logs.setUsers(utilisateur);
        logsFacade.create(logs);
        logs.setIdlogs(null);
    }
    public Logs getLogs() {
        return logs;
    }

    public void setLogs(Logs logs) {
        this.logs = logs;
    }

    public Abonnements getSelectedAbonnement() {
        return selectedAbonnement;
    }

    public void setSelectedAbonnement(Abonnements selectedAbonnement) {
        this.selectedAbonnement = selectedAbonnement;
    }

    public Users getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Users utilisateur) {
        this.utilisateur = utilisateur;
    }


    
    
    
    
}
