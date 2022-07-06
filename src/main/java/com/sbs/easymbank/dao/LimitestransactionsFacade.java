/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.dao;

import com.sbs.easymbank.entities.Limitestransactions;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author samuel
 */
@Stateless
public class LimitestransactionsFacade  extends AbstractFacade<Limitestransactions>{
    
        @PersistenceContext(unitName = "com.sbs_easymbank3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public LimitestransactionsFacade() {
        super(Limitestransactions.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    
    
//    methode permettant la mise a jour d'une limite de transaction
    public Limitestransactions updateLimitestransactionByKeyColumn(String key,String val){
        System.out.println("key recuperee  "+key);
        //recup depuis la bd
        Query q = this.em.createQuery("select l from Limitestransactions l WHERE l.keyColumn = :key");
        q.setParameter("key", key);
        Limitestransactions limite = (Limitestransactions) q.getSingleResult();
        limite.setValeur(val);
        System.out.println("limite recuperee   "+limite.getDesignation()+" ");
         return this.updateLimitesTransaction(limite);
    }
    
    
//      public Limitestransactions updateLimitestransactionByKeyColumn(String key){
//            System.out.println("key recuperee  "+key);
//      Query qr = em.createNativeQuery("UPDATE Limitestransactions SET l.valeur = :val WHERE l.keycolumn = :key  ");
//      qr.setParameter("key", key);
//      return (Limitestransactions) qr.getSingleResult();
//      }
    
    
    public Limitestransactions updateLimitesTransaction (Limitestransactions l){
        return em.merge(l);
    }
    
}
