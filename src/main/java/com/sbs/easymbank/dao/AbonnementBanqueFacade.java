/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.dao;

import com.sbs.easymbank.entities.AbonnementBanque;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

/**
 *
 * @author SOCITECH-
 */
@Stateless
public class AbonnementBanqueFacade extends AbstractFacade<AbonnementBanque> {
    @PersistenceContext(unitName = "com.sbs_easymbank3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AbonnementBanqueFacade() {
        super(AbonnementBanque.class);
    }
    
    public List<AbonnementBanque> findByRacine(String cle){
       
       List<AbonnementBanque> noResultList=new ArrayList<>();
       try{
      
          BigInteger racine= new BigInteger(cle); 
          //BigInteger cpte= new BigInteger(compte);
           return (List<AbonnementBanque>)getEntityManager().createNamedQuery("AbonnementBanque.findByRacine").setParameter("racine", racine).getResultList();

       
      
      
     
      
       }catch(NoResultException e){
           
           return noResultList;}   
    }
    
     public List<AbonnementBanque> findByCompte(String compte){
         List<AbonnementBanque> noResultList=new ArrayList<>();
       BigInteger cpte= new BigInteger(compte);
       
       try{
       
           return (List<AbonnementBanque>)getEntityManager().createNamedQuery("AbonnementBanque.findByCompte").setParameter("compte",cpte).getResultList();
           
       }catch(NoResultException e){return noResultList;}   
    }
     
     
    
    
}
