/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.jsf.controllers;

import com.sbs.easymbank.dao.OperateursFacade;
import com.sbs.easymbank.dao.ProfilsClientsFacade;
import com.sbs.easymbank.dao.TarifsProfilsOperateursFacade;
import com.sbs.easymbank.entities.Operateurs;
import com.sbs.easymbank.entities.ProfilsClients;
import com.sbs.easymbank.entities.TarifsProfilsOperateurs;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author alex
 */
@ManagedBean
@ViewScoped
public class TarifAutreManager implements Serializable{
    private List<Operateurs> listOperateurs;
    private List<ProfilsClients> listProfils;
    private List<TarifsProfilsOperateurs> listTarif;
    private TarifsProfilsOperateurs newTarif=new TarifsProfilsOperateurs();
    private TarifsProfilsOperateurs selectedTarif=new TarifsProfilsOperateurs();
    
    @EJB
    private OperateursFacade operateursFacade;
    @EJB
    private ProfilsClientsFacade profilsClientsFacade;
    @EJB
    private TarifsProfilsOperateursFacade tarifsProfilsOperateursFacade;
    
    @ManagedProperty(value="#{sessionManager}")
    private SessionManager sessionManager;
    
     private Converter OperateursConverter = new Converter() {

        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {
            System.out.println("value:"+value);
            return operateursFacade.find(new BigDecimal(value));
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            //f(value!=null){
               System.out.println("value:"+value.toString());
                return ((Operateurs) value).getIdOperateur().toString();   
            // }
          // return null;
        }
    };
     
     private Converter ProfilConverter = new Converter() {

        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {
            System.out.println("value:"+value);
            return profilsClientsFacade.find(Short.parseShort(value));
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
           // if(value!=null){
           //            System.out.println("value:"+value.toString());
            return ((ProfilsClients) value).getIdProfils().toString();
 
            //}return null;
            }
    };
     
     @PostConstruct
    public void init(){
      listTarif=  tarifsProfilsOperateursFacade.findAll();
      listOperateurs=operateursFacade.findAll();
      listProfils=profilsClientsFacade.findAll();
      // listAgences = agencesFacade.findAll();
        }

    public List<Operateurs> getListOperateurs() {
        return listOperateurs;
    }

    public void setListOperateurs(List<Operateurs> listOperateurs) {
        this.listOperateurs = listOperateurs;
    }

    public List<ProfilsClients> getListProfils() {
        return listProfils;
    }

    public void setListProfils(List<ProfilsClients> listProfils) {
        this.listProfils = listProfils;
    }

    public TarifsProfilsOperateurs getNewTarif() {
        return newTarif;
    }

    public void setNewTarif(TarifsProfilsOperateurs newTarif) {
        this.newTarif = newTarif;
    }

    public TarifsProfilsOperateurs getSelectedTarif() {
        return selectedTarif;
    }

    public void setSelectedTarif(TarifsProfilsOperateurs selectedTarif) {
        this.selectedTarif = selectedTarif;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public Converter getOperateursConverter() {
        return OperateursConverter;
    }

    public void setOperateursConverter(Converter OperateursConverter) {
        this.OperateursConverter = OperateursConverter;
    }

    public Converter getProfilConverter() {
        return ProfilConverter;
    }

    public void setProfilConverter(Converter ProfilConverter) {
        this.ProfilConverter = ProfilConverter;
    }

    public List<TarifsProfilsOperateurs> getListTarif() {
        return listTarif;
    }

    public void setListTarif(List<TarifsProfilsOperateurs> listTarif) {
        this.listTarif = listTarif;
    }
    
     public void enregistrerTarif(){
        try{
           if(newTarif.getIdTarifs()==null){
              if(!tarifsProfilsOperateursFacade.isRedondant(newTarif)){
                 tarifsProfilsOperateursFacade.create(newTarif);  
                 sessionManager.getLogs().setAction("CREATION TARIF");
                sessionManager.getLogs().setDateLog(new Date());
                sessionManager.getLogs().setMessage("ID: "+newTarif.getOperateurs() + " SERVICE: "+newTarif.getService());
                sessionManager.logDB();
                sessionManager.getLogs().setMessage("");  
                newTarif=new TarifsProfilsOperateurs();
                //listCommissions.add(newCommissions);
               // refreshList();
              }
             
              else{
                 // refreshList();
                  addErrorMessage("ERREUR","Ce tarif est redondant"); 
              }
                  
                 
             
           } else{
               if(!tarifsProfilsOperateursFacade.becomeRedondant(newTarif)){
                tarifsProfilsOperateursFacade.edit(newTarif);  
                 sessionManager.getLogs().setAction("MODIFICATION TARIF");
                sessionManager.getLogs().setDateLog(new Date());
                sessionManager.getLogs().setMessage("ID:"+newTarif.getIdTarifs());
                sessionManager.logDB();
                sessionManager.getLogs().setMessage(""); 
               // refreshList();
                addMessage("SUCCES","Tarif créée avec succès"); 
           } else{
                   
                   addErrorMessage("ERREUR","Ce tarif est redondant"); 
               }
                 
           }     
        }catch(Exception ex){
            addErrorMessage("ERREUR","Une erreur est survenue au moment de l'opération");  
            ex.printStackTrace();
        }finally{
            refreshList();
        }
    }
     
       public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
      
        public void addErrorMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
        
         private void refreshList(){
            listTarif=  tarifsProfilsOperateursFacade.findAll();
        }
         
          public void resetTarif() {
       // System.out.println("resetProfil");

        newTarif = new TarifsProfilsOperateurs();
    }
          
          public void onRowSelect(SelectEvent event) {
        TarifsProfilsOperateurs t = (TarifsProfilsOperateurs) event.getObject();
        newTarif = t;
       // addInfoMessage("Commission : " + newCommissions.getIdPalier());

    }
          
           public void supprimerTarif(){
        try{
          tarifsProfilsOperateursFacade.remove(selectedTarif);
          sessionManager.getLogs().setAction("SUPPRESSION TARIF");
                sessionManager.getLogs().setDateLog(new Date());
                sessionManager.getLogs().setMessage("ID:"+selectedTarif.getIdTarifs());
                sessionManager.logDB();
                sessionManager.getLogs().setMessage(""); 
               // listCommissions.remove(selectedCommissions);
               refreshList();
        }catch(Exception ex){
          ex.printStackTrace();
        }
    }
    
}
