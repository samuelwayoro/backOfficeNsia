/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.dao;

import com.sbs.easymbank.entities.TarifsProfilsOperateurs;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author alex
 */
@Stateless
public class TarifsProfilsOperateursFacade extends AbstractFacade<TarifsProfilsOperateurs> {
    @PersistenceContext(unitName = "com.sbs_easymbank3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TarifsProfilsOperateursFacade() {
        super(TarifsProfilsOperateurs.class);
    }
    
     public boolean isRedondant(TarifsProfilsOperateurs t){
        try{
          List<TarifsProfilsOperateurs> l= (List<TarifsProfilsOperateurs>)em.createNamedQuery("TarifsProfilsOperateurs.findTarifRedondant").setParameter("operateur", t.getOperateurs().getIdOperateur()).setParameter("profil", t.getProfils().getIdProfils()).setParameter("service",t.getService()).getResultList();   
           // System.out.println("LISTE: "+l.size());
            return !l.isEmpty();
        }catch(Exception ex){
            ex.printStackTrace();
            return true;
        }
        
    }        

     
      public boolean becomeRedondant(TarifsProfilsOperateurs t){
        try{
          List<TarifsProfilsOperateurs> l= (List<TarifsProfilsOperateurs>)em.createNamedQuery("TarifsProfilsOperateurs.findTarifBecomeRedondant").setParameter("operateur", t.getOperateurs().getIdOperateur()).setParameter("profil", t.getProfils().getIdProfils()).setParameter("service",t.getService()).setParameter("idtarif", t.getIdTarifs()).getResultList();   
           // System.out.println("LISTE: "+l.size());
            return !l.isEmpty();
        }catch(Exception ex){
            ex.printStackTrace();
            return true;
        }
        
        
    }
      
    public List<TarifsProfilsOperateurs> findByOperatorAndProfilAndService(String operateur,String profil,String service){
        return (List<TarifsProfilsOperateurs>)em.createNamedQuery("TarifsProfilsOperateurs.findTarifRedondant").setParameter("operateur",new BigDecimal(operateur) ).setParameter("profil", new Short(profil)).setParameter("service",service).getResultList();
        
    }
    
}
