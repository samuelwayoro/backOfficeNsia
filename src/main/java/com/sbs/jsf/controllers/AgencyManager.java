/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.jsf.controllers;

import com.sbs.easymbank.dao.AgencesFacade;
import com.sbs.easymbank.entities.Agences;
import com.sbs.easymbank.entities.Profils;
import com.sbs.exceptions.NonUniqueCodeException;
import com.sbs.jsf.model.LazyAgenceDataModel;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author SOCITECH-
 */
@ManagedBean
@ViewScoped
public class AgencyManager implements Serializable {

    private LazyDataModel<Agences> listAgence;
    private List<Agences> listAgenceFiltered;
    private String codeNouvelleAgence;
    private String libelleNouvelleAgence;
    private Agences newAgence = new Agences();
    private Agences selectedAgence = new Agences();
    private boolean modifEnCours = false;
    @EJB
    AgencesFacade agencesFacade;
    @ManagedProperty(value = "#{sessionManager}")
    private SessionManager sessionManager;
//    @ManagedProperty(value="#{loginManager}")
//    private LoginManager loginManager;

    @PostConstruct
    public void init() {
       // listAgence = agencesFacade.findAll();
       listAgence=new LazyAgenceDataModel(agencesFacade.findAll());
        System.out.println("nbre agences: "+((LazyAgenceDataModel)listAgence).getDatasource().size());
    }

    public LazyDataModel<Agences> getListAgence() {
        return listAgence;
    }

    public void setListAgence(LazyDataModel<Agences> listAgence) {
        this.listAgence = listAgence;
    }

//    public LoginManager getLoginManager() {
//        return loginManager;
//    }
//
//    public void setLoginManager(LoginManager loginManager) {
//        this.loginManager = loginManager;
//    }
    public List<Agences> getListAgenceFiltered() {
        return listAgenceFiltered;
    }

    public void setListAgenceFiltered(List<Agences> listAgenceFiltered) {
        this.listAgenceFiltered = listAgenceFiltered;
    }

    public String getCodeNouvelleAgence() {
        return codeNouvelleAgence;
    }

    public void setCodeNouvelleAgence(String codeNouvelleAgence) {
        this.codeNouvelleAgence = codeNouvelleAgence;
    }

    public String getLibelleNouvelleAgence() {
        return libelleNouvelleAgence;
    }

    public void setLibelleNouvelleAgence(String libelleNouvelleAgence) {
        this.libelleNouvelleAgence = libelleNouvelleAgence;
    }

    public Agences getNewAgence() {
        return newAgence;
    }

    public void setNewAgence(Agences newAgence) {
        this.newAgence = newAgence;
    }

    public Agences getSelectedAgence() {
        return selectedAgence;
    }

    public void setSelectedAgence(Agences selectedAgence) {
        this.selectedAgence = selectedAgence;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public boolean isModifEnCours() {
        return modifEnCours;
    }

    public void setModifEnCours(boolean modifEnCours) {
        this.modifEnCours = modifEnCours;
    }
    
    

    public void creerNouvelleAgence() {
//        Agences nouvelleAgence=new Agences();
//        nouvelleAgence.setCodeagence(codeNouvelleAgence);
//                nouvelleAgence.setLibelle(libelleNouvelleAgence);
        try {
            if(newAgence.getIdagences() == null){
            newAgence.setDatecreation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
            if(!uniqueCode(newAgence))
                throw new NonUniqueCodeException(newAgence.getCodeagence());
            agencesFacade.create(newAgence);
            //listAgence.add(newAgence);
            ((LazyAgenceDataModel)listAgence).getDatasource().add(newAgence);
            sessionManager.getLogs().setAction("CREATION D'AGENCE");
            sessionManager.getLogs().setDateLog(new Date());
            sessionManager.getLogs().setMessage("LIBELLE AGENCE: " + selectedAgence.getLibelle());
            sessionManager.logDB();
            }else{
            agencesFacade.edit(newAgence);
            sessionManager.getLogs().setAction("MODIFICATION D'AGENCE " + newAgence.getIdagences().toString());
            sessionManager.getLogs().setDateLog(new Date());
            sessionManager.getLogs().setMessage("LIBELLE AGENCE: " + selectedAgence.getLibelle());
            sessionManager.logDB();                
            }
            newAgence = new Agences();                

          //  System.out.println("AGENCE: " + newAgence.getCodeagence() + " " + newAgence.getLibelle());
            addInfoMessage("ACTION EFFECTUEE AVEC SUCCES");
        } catch (Exception ex) {
            ex.printStackTrace();
            addErrorMessage("ERREUR LORS DE LA CREATION: "+ex.getMessage());
        }

    }

    public void supprimerAgence() {
        try {
            agencesFacade.remove(selectedAgence);
            //listAgence.remove(selectedAgence);
            ((LazyAgenceDataModel)listAgence).getDatasource().remove(newAgence);
            sessionManager.getLogs().setAction("SUPPRESSION D'AGENCE");
            sessionManager.getLogs().setDateLog(new Date());
            sessionManager.getLogs().setMessage("LIBELLE AGENCE: " + selectedAgence.getLibelle());
            sessionManager.logDB();
            selectedAgence = new Agences();
            addInfoMessage("AGENCE SUPPRIMEE AVEC SUCCES");
        } catch (Exception ex) {
            ex.printStackTrace();
            addErrorMessage("ERREUR LORS DE LA SUPPRESSION");
        }

    }

    protected void addInfoMessage(String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "info", message));
    }

    protected void addErrorMessage(String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "erreur", message));
    }
    
    private boolean uniqueCode(Agences agences){
        for(Agences ag:listAgence){
            if (ag.getCodeagence().equals(agences.getCodeagence()))
             return false;       
        }
        return true;
    }
    
        public void onRowSelect(SelectEvent event) {
        Agences agences = (Agences) event.getObject();
        newAgence = agences;
        modifEnCours = true;

        FacesMessage msg = new FacesMessage("Agences Selected", ((Agences) event.getObject()).getLibelle());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
        public void reset(){
            newAgence = new Agences();
            modifEnCours = false;
        }


}
