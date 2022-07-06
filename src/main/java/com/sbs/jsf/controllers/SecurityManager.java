/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.jsf.controllers;

import com.sbs.easymbank.dao.ParametresFacade;
import com.sbs.easymbank.entities.Parametres;
import com.sbs.exceptions.ParameterNotFoundException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author alex
 */
@ManagedBean
@ApplicationScoped

public class SecurityManager implements Serializable {
  
        @EJB
private ParametresFacade parametresFacade;
private Parametres delaiValiditePass;
private Parametres nbrePassToCheck;
private Parametres maxTentativeConnexion;
private Parametres maxPassParUtililisateur;
//private int unitOfMeasure;


    @PostConstruct
    public void init()  {
        try{
        List<Parametres> l=parametresFacade.findByCodeParam("DELAI_VALIDITE_PASS");
        if(!l.isEmpty())
            delaiValiditePass=l.get(0) ;
        else
            throw new ParameterNotFoundException("DELAI_VALIDITE_PASS");
        l=parametresFacade.findByCodeParam("NOMBRE_PASS_VERIFICATION");
        if(!l.isEmpty())
            nbrePassToCheck=l.get(0) ;
        else
            throw new ParameterNotFoundException("NOMBRE_PASS_VERIFICATION");
        l=parametresFacade.findByCodeParam("MAX_TENTATIVE_CONNEXION");
        if(!l.isEmpty())
            maxTentativeConnexion=l.get(0) ;
        else
            throw new ParameterNotFoundException("MAX_TENTATIVE_CONNEXION");
        l=parametresFacade.findByCodeParam("MAX_PASS_HISTORISATION");
        if(!l.isEmpty())
            maxPassParUtililisateur=l.get(0) ;
        else
            throw new ParameterNotFoundException("MAX_PASS_HISTORISATION");

 
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
            
    }



    public Parametres getNbrePassToCheck() {
        return nbrePassToCheck;
    }

    public void setNbrePassToCheck(Parametres nbrePassToCheck) {
        this.nbrePassToCheck = nbrePassToCheck;
    }

    public Parametres getDelaiValiditePass() {
        return delaiValiditePass;
    }

    public void setDelaiValiditePass(Parametres delaiValiditePass) {
        this.delaiValiditePass = delaiValiditePass;
    }

    public Parametres getMaxTentativeConnexion() {
        return maxTentativeConnexion;
    }

    public void setMaxTentativeConnexion(Parametres maxTentativeConnexion) {
        this.maxTentativeConnexion = maxTentativeConnexion;
    }

    public Parametres getMaxPassParUtililisateur() {
        return maxPassParUtililisateur;
    }

    public void setMaxPassParUtililisateur(Parametres maxPassParUtililisateur) {
        this.maxPassParUtililisateur = maxPassParUtililisateur;
    }
    
    
    
    public void enregistrerDelaiValiditePass(){
       parametresFacade.edit(delaiValiditePass);
    }
    
    public void enregistrerMaxConnexion(){
      parametresFacade.edit(maxTentativeConnexion);
    }
    
    public void enregistrerNbrePassToCheck(){
      parametresFacade.edit(nbrePassToCheck);
    }
    
    public void enregistrerNbreMaxPassUser(){
       parametresFacade.edit(maxPassParUtililisateur); 
    }

}
