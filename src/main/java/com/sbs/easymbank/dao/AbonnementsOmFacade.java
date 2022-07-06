/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.dao;

//import com.sbs.easymbank.entities.AbonnementBanque;
import com.sbs.easymbank.entities.AbonnementsOm;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author SOCITECH-
 */
@Stateless
public class AbonnementsOmFacade extends AbstractFacade<AbonnementsOm> {
    @PersistenceContext(unitName = "com.sbs_easymbank3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AbonnementsOmFacade() {
        super(AbonnementsOm.class);
    }
    
    public AbonnementsOm findKYC(AbonnementsOm abOm){
      // AbonnementsOm abOm=new AbonnementsOm();
       
       try{
       
            abOm =(AbonnementsOm)getEntityManager().createNamedQuery("AbonnementsOm.findKYC").setParameter("msisdn", abOm.getMsisdn()).setParameter("activationkey", abOm.getActivationkey()).getSingleResult();
            abOm.setCodeRetour(new BigInteger("200"));
            return abOm;
       }catch(NoResultException e){
        e.printStackTrace();
        abOm.setCodeRetour(new BigInteger("302"));
        return abOm;
       }   
    }
    
}
