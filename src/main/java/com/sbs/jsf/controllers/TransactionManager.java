/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.jsf.controllers;

import com.sbs.easymbank.dao.AbonnementsFacade;
import com.sbs.easymbank.dao.AgencesFacade;
import com.sbs.easymbank.dao.TransactionsFacade;
import com.sbs.easymbank.dao.TypeTransactionsFacade;
import com.sbs.easymbank.entities.Transactions;
import com.sbs.easymbank.entities.TypeTransactions;
import com.sbs.jsf.model.LazyTransactionDataModel;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.LazyDataModel;


/**
 *
 * @author SOCITECH-
 */
@ManagedBean
@ViewScoped
public class TransactionManager implements Serializable {
     @EJB
    private TransactionsFacade transactionsFacade;
     @EJB
    private TypeTransactionsFacade typeTrFacade;
     @EJB
     private AgencesFacade agencesFacade;
     @EJB
      private  AbonnementsFacade abonnementsFacade;
    private LazyDataModel<Transactions> listTransac;
    private List<TypeTransactions> listTypeTransac;
    private List<Transactions> listTransacFiltered;
    private int maxId=0;
    @ManagedProperty(value = "#{loginManager}")
    private LoginManager loginManager;

    
     @PostConstruct
    public void init() {
        if(loginManager.getUtilisateur().getIdprofils().isTransac_ttes_age())
        listTransac =new LazyTransactionDataModel(transactionsFacade.findAllTransactions()) ;
        else
        listTransac =new LazyTransactionDataModel(transactionsFacade.findTransactionsByAgence(loginManager.getUtilisateur().getIdagences().getCodeagence())) ;
        listTypeTransac = typeTrFacade.findAll();
        for(Transactions tr:((LazyTransactionDataModel)listTransac).getDatasource()){
            tr.setDesignationOperateur(tr.getOperateurs().getDesignationOperateur());
            tr.setAbonnements(abonnementsFacade.findByAlias(tr.getAccountalias()));
            if(tr.getAbonnements() != null)
                tr.setAgence(agencesFacade.findLibelleByCode(tr.getAbonnements().getAgence()));
            if(tr.getIdtransactions()>maxId)
                maxId=tr.getIdtransactions();
        }
    }

    public LazyDataModel<Transactions> getListTransac() {
        return listTransac;
    }

    public void setListTransac(LazyDataModel<Transactions> listTransac) {
        this.listTransac = listTransac;
    }


    public List<TypeTransactions> getListTypeTransac() {
        return listTypeTransac;
    }

    public void setListTypeTransac(List<TypeTransactions> listTypeTransac) {
        this.listTypeTransac = listTypeTransac;
    }

    public List<Transactions> getListTransacFiltered() {
        return listTransacFiltered;
    }

    public void setListTransacFiltered(List<Transactions> listTransacFiltered) {
        this.listTransacFiltered = listTransacFiltered;
    }

    public int getMaxId() {
        return maxId;
    }

    public void setMaxId(int maxId) {
        this.maxId = maxId;
    }
    
    public void rafraichirListTransactions(){
        ((LazyTransactionDataModel)listTransac).getDatasource().addAll(0,transactionsFacade.findGreaterThanIdtransactions(maxId));
         for(Transactions tr:listTransac){
            if(tr.getIdtransactions()>maxId)
                maxId=tr.getIdtransactions();
        }
    }

    public LoginManager getLoginManager() {
        return loginManager;
    }

    public void setLoginManager(LoginManager loginManager) {
        this.loginManager = loginManager;
    }
    
    
    
}
