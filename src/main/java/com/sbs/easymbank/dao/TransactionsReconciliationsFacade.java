/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.dao;

import com.sbs.easymbank.entities.Transactions;
import com.sbs.easymbank.entities.TransactionsReconciliations;
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
public class TransactionsReconciliationsFacade extends AbstractFacade<TransactionsReconciliations> {

    @PersistenceContext(unitName = "com.sbs_easymbank3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TransactionsReconciliationsFacade() {
        super(TransactionsReconciliations.class);
    }

//    public List<TransactionsReconciliations> findCleared(String origine) {
//        Query query = getEntityManager().createNativeQuery("SELECT * FROM ((SELECT * FROM transactions_reconciliations ) UNION (SELECT * FROM transactions t WHERE t.origine = ?origine)) t ORDER BY t.trandate desc ",TransactionsReconciliations.class)/*.setParameter("origine", "BANQUE")*/;
//        query.setParameter("origine",origine);
//        return query.getResultList();
//    }

        public List<TransactionsReconciliations> findCleared(String origine) {
        Query query = getEntityManager().createNativeQuery("SELECT * FROM transactions_reconciliations",TransactionsReconciliations.class)/*.setParameter("origine", "BANQUE")*/;
       // query.setParameter("origine",origine);
        return query.getResultList();
    }
        
        public List<TransactionsReconciliations> findTransactionsByPeriod(String borneInf,String borneSup, String statusPattern){
          try{
         Query query = getEntityManager().createNamedQuery("TransactionsReconciliations.findTransactionsByPeriod").setParameter("borneInf", borneInf).setParameter("borneSup", borneSup).setParameter("status", statusPattern);  
         return query.getResultList();
      }catch(NoResultException e){
            e.printStackTrace();
            return null;
        }
    }


}
