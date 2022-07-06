/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.dao;

import com.sbs.easymbank.entities.Transactions;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author SOCITECH-
 */
@Stateless
public class TransactionsFacade extends AbstractFacade<Transactions> {

    @PersistenceContext(unitName = "com.sbs_easymbank3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TransactionsFacade() {
        super(Transactions.class);
    }

    public List<Transactions> findByOrigineDateDesc(String origine) {
        Query query = getEntityManager().createNamedQuery("Transactions.findByOrigineDateDesc").setParameter("origine", origine);
        return query.getResultList();
    }

    public List<Transactions> findGreaterThanIdtransactions(int id) {

        Query query = getEntityManager().createNamedQuery("Transactions.findGreaterThanIdtransactions").setParameter("idtransactions", id);
        return query.getResultList();
    }

    public List<Transactions> findAllTransactions() {

        Query query = getEntityManager().createNamedQuery("Transactions.findAll");
        return query.getResultList();
    }

          public List<Transactions> findTransactionsByPeriod(String borneInf,String borneSup,List<String> listType,String operateurs, String compte,String racine,String phone ){
          try{
         Query query = getEntityManager().createNamedQuery("Transactions.findTransactionsByPeriod").setParameter("borneInf", borneInf).setParameter("borneSup", borneSup).setParameter("listType", listType).setParameter("operateurs", "%"+operateurs+"%");  
         query.setParameter("phone",phone == null ? "%%" : "%"+phone+"%");
         query.setParameter("compte",compte == null ? "%%" : "%"+compte+"%");
         query.setParameter("racine",racine == null ? "%%" : "%"+racine+"%");
         return query.getResultList();
      }catch(NoResultException e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Transactions> findTransactionsByAgence(String codeAgence) {

        Query query = getEntityManager().createNativeQuery("SELECT * FROM TRANSACTIONS WHERE ACCOUNTALIAS IN (SELECT ALIAS FROM ABONNEMENTS WHERE AGENCE = ?code)", Transactions.class);
        query.setParameter("code", codeAgence);
        return query.getResultList();
    }

}
