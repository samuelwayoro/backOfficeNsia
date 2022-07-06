/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.jsf.controllers;

import com.sbs.easymbank.dao.AbonnementsFacade;
import com.sbs.easymbank.dao.AbonnementsReconciliationsFacade;
import com.sbs.easymbank.dao.TransactionsFacade;
import com.sbs.easymbank.dao.TransactionsReconciliationsFacade;
import com.sbs.easymbank.dao.TypeTransactionsFacade;
import com.sbs.easymbank.entities.Abonnements;
import com.sbs.easymbank.entities.AbonnementsReconciliations;
import com.sbs.easymbank.entities.Transactions;
import com.sbs.easymbank.entities.TransactionsReconciliations;
import com.sbs.easymbank.entities.TypeTransactions;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author SOCITECH-
 */
@ManagedBean
@ViewScoped

public class ReconciliationManager implements Serializable {
    @EJB
    private TransactionsFacade transactionsFacade;

    @EJB
    private TransactionsReconciliationsFacade transactionsRecFacade;
    @EJB
    private TypeTransactionsFacade typeTrFacade;
    @EJB
    private AbonnementsFacade AbonnementsFacade;
    @EJB
    private AbonnementsReconciliationsFacade abonnementsReconciliationsFacade;
    
    
    
    private List<AbonnementsReconciliations> listAbont;
    private List<TransactionsReconciliations> listTransac=new ArrayList<>();
    private List<TypeTransactions> listTypeTransac;
   // private List<Transactions> listTransacFiltered;
    private long total = 0;
    private String formeAlias;
    private String formeReqId;

    @PostConstruct
    public void init() {
        listTransac=transactionsRecFacade.findCleared("BANQUE");
       // List<Transactions> list =transactionsFacade.findNotCleared();
          //Ajout des transactions non reconciliées venant de la table
        /*  for(Transactions tr:list){
            tr.setReconcilie(Boolean.FALSE);
            listTransac.add(new TransactionsReconciliations(tr));
        }  
          //trie de la liste par ordre décroissant de date
        for(TransactionsReconciliations tr:listTransac){
           int i=listTransac.lastIndexOf(tr)-1;
           while(i>=1){
               TransactionsReconciliations t=listTransac.get(i);
               if(t.getTrandate().replace("-", "").compareTo(tr.getTrandate().replace("-",""))>0){
                   listTransac.add(i+1, t);
                   i--;
               }
                   
               else if(t.getTrandate().replace("-", "").compareTo(tr.getTrandate().replace("-",""))<0){
                   listTransac.add(i+1, tr);
                   i=0;
               }
                   
               else i=0;
           }
        }*/
        
        listTypeTransac = typeTrFacade.findAll();
        listAbont=abonnementsReconciliationsFacade.getCleared();
    }

    public List<TransactionsReconciliations> getListTransac() {
        return listTransac;
    }

    public void setListTransac(List<TransactionsReconciliations> listTransac) {
        this.listTransac = listTransac;
    }

//    public List<Transactions> getListTransacFiltered() {
//        return listTransacFiltered;
//    }
//
//    public void setListTransacFiltered(List<Transactions> listTransacFiltered) {
//        this.listTransacFiltered = listTransacFiltered;
//    }

    public List<TypeTransactions> getListTypeTransac() {
        return listTypeTransac;
    }

    public void setListTypeTransac(List<TypeTransactions> listTypeTransac) {
        this.listTypeTransac = listTypeTransac;
    }

    public String getFormeAlias() {
        return formeAlias;
    }

    public void setFormeAlias(String formeAlias) {
        this.formeAlias = formeAlias;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public String getFormeReqId() {
        return formeReqId;
    }

    public void setFormeReqId(String formeReqId) {
        this.formeReqId = formeReqId;
    }

    public List<AbonnementsReconciliations> getListAbont() {
        return listAbont;
    }

    public void setListAbont(List<AbonnementsReconciliations> listAbont) {
        this.listAbont = listAbont;
    }

    
    
    
    public String couleurLigneTransaction(Transactions tr) {
        return "red";
    }

   

}
