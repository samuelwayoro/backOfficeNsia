/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.jsf.controllers;

import com.sbs.easymbank.dao.AgencesFacade;
import com.sbs.easymbank.dao.CommissionsFacade;
import com.sbs.easymbank.dao.OperateursFacade;
import com.sbs.easymbank.dao.ProfilsClientsFacade;

import com.sbs.easymbank.entities.Commissions;
import com.sbs.easymbank.entities.Operateurs;
import com.sbs.easymbank.entities.ProfilsClients;
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
public class CommissionsManager implements Serializable {

    @EJB
    private CommissionsFacade commissionsFacade;
    @EJB
    private OperateursFacade operateursFacade;
    @EJB
    private ProfilsClientsFacade profilsClientsFacade;
     @EJB
    private AgencesFacade agencesFacade;
   
    private Commissions newCommissions= new Commissions();
    private Commissions selectedCommissions= new Commissions();
    private List<Commissions> listCommissions;
    private List<Commissions> filteredListCommissions;
    private List<Operateurs> listOperateurs;
    private List<ProfilsClients> listProfils;
    
    
    // private List<Agences> listAgences;
    // private Agences agences;
    
   
    @ManagedProperty(value="#{sessionManager}")
    private SessionManager sessionManager;
    
    @PostConstruct
    public void init(){
      listCommissions=  commissionsFacade.findAll();
      listOperateurs=operateursFacade.findAll();
      listProfils=profilsClientsFacade.findAll();
      // listAgences = agencesFacade.findAll();
        }
    
     private Converter OperateursConverter = new Converter() {

        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {
         //   System.out.println("value:"+value);
            return operateursFacade.find(new BigDecimal(value));
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            //f(value!=null){
             //  System.out.println("value:"+value.toString());
                return ((Operateurs) value).getIdOperateur().toString();   
            // }
          // return null;
        }
    };
     
     private Converter ProfilConverter = new Converter() {

        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {
         //  System.out.println("value:"+value);
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
     
    
   

    public Commissions getNewCommissions() {
        return newCommissions;
    }

    public void setNewCommissions(Commissions commissions) {
        newCommissions = commissions;
    }

    public List<Commissions> getListCommissions() {
        return listCommissions;
    }

    public void setListCommissions(List<Commissions> listCommissions) {
        this.listCommissions = listCommissions;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public Commissions getSelectedCommissions() {
        return selectedCommissions;
    }

    public void setSelectedCommissions(Commissions selectedCommissions) {
        this.selectedCommissions = selectedCommissions;
    }

    public Converter getOperateursConverter() {
        return OperateursConverter;
    }

    public void setOperateursConverter(Converter operateursConverter) {
        this.OperateursConverter = operateursConverter;
    }

    public Converter getProfilConverter() {
        return ProfilConverter;
    }

    public void setProfilConverter(Converter profilConverter) {
        this.ProfilConverter = profilConverter;
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

    public List<Commissions> getFilteredListCommissions() {
        return filteredListCommissions;
    }

    public void setFilteredListCommissions(List<Commissions> filteredListCommissions) {
        this.filteredListCommissions = filteredListCommissions;
    }

  
   
    
    
    
    public void onRowSelect(SelectEvent event) {
        Commissions c = (Commissions) event.getObject();
        newCommissions = c;
       // addInfoMessage("Commission : " + newCommissions.getIdPalier());

    }
    
    public void enregistrerCommission(){
        try{
           if(newCommissions.getIdPalier()==null){
              if(!commissionsFacade.isRedondant(newCommissions)){
                 commissionsFacade.create(newCommissions);  
                 sessionManager.getLogs().setAction("CREATION PALIER COMMISSION");
                sessionManager.getLogs().setDateLog(new Date());
                sessionManager.getLogs().setMessage("ID:"+newCommissions.getIdPalier());
                sessionManager.logDB();
                sessionManager.getLogs().setMessage("");  
                newCommissions=new Commissions();
                //listCommissions.add(newCommissions);
               // refreshList();
                addMessage("SUCCES","Palier de Commission créée avec succès");
              } else{
                 // refreshList();
                  addErrorMessage("ERREUR","un pallier existe deja avec cet ID"); 
              }
           } else{
               if(!commissionsFacade.becomeRedondant(newCommissions)){
                 commissionsFacade.edit(newCommissions);  
                 sessionManager.getLogs().setAction("MODIFICATION PALIER COMMISSION");
                sessionManager.getLogs().setDateLog(new Date());
                sessionManager.getLogs().setMessage("ID:"+newCommissions.getIdPalier());
                sessionManager.logDB();
                sessionManager.getLogs().setMessage(""); 
               // refreshList();
                addMessage("SUCCES","Palier de Commission modifiée avec succès"); 
 
               } else{
                  
                  addErrorMessage("ERREUR","Ce palier de commission est redondante"); 
              }
           }
              
        }catch(Exception ex){
            addErrorMessage("ERREUR","Une erreur est survenue au moment de l'opération");  
            ex.printStackTrace();
        }finally{
          refreshList()  ;
        }
    }
    
    public void supprimerPalier(){
        try{
          commissionsFacade.remove(selectedCommissions);
          sessionManager.getLogs().setAction("SUPPRESSION PALIER COMMISSION");
                sessionManager.getLogs().setDateLog(new Date());
                sessionManager.getLogs().setMessage("ID:"+selectedCommissions.getIdPalier());
                sessionManager.logDB();
                sessionManager.getLogs().setMessage(""); 
               // listCommissions.remove(selectedCommissions);
               refreshList();
        }catch(Exception ex){
          ex.printStackTrace();
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
        
        public void resetCommission() {
       // System.out.println("resetProfil");
        newCommissions = new Commissions();
    }
        
//        public void trierList(){
//            int indexOfFisrtItemWithSameSens=0;
//            int indexOfLastItemWithSameSens=listCommissions.size()-1;
//            while(indexOfFisrtItemWithSameSens<=indexOfLastItemWithSameSens){
//                if()
//            }
//            for(Commissions c:listCommissions){
//              if(c.getSens().equals(newCommissions.getSens())){
//                    indexOfFisrtItemWithSameSens=listCommissions.indexOf(c);
//                    break;
//                }  
//            }
//            for(int i=indexOfFisrtItemWithSameSens;)
//                
//                    
//        }
        
        private void refreshList(){
            listCommissions=  commissionsFacade.findAll();
        }
    
    
}
