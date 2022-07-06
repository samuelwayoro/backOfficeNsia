/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.dao;

import com.sbs.easymbank.entities.Commissions;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author alex
 */
@Stateless
public class CommissionsFacade extends AbstractFacade<Commissions> {

    @PersistenceContext(unitName = "com.sbs_easymbank3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CommissionsFacade() {
        super(Commissions.class);
    }
    
    @Override
    public List<Commissions> findAll(){
     return (List<Commissions>)em.createNamedQuery("Commissions.findAll").getResultList();
    }
    
    public boolean isRedondant(Commissions c){
        try{
          List<Commissions> l= (List<Commissions>)em.createNamedQuery("Commissions.findCommissionRedondant").setParameter("minimum", c.getMinimum().intValue()).setParameter("maximum", c.getMaximum().intValue()).setParameter("sens", c.getSens()).setParameter("operateur", c.getOperateurs().getIdOperateur()).setParameter("profil", c.getProfils().getIdProfils()).getResultList();   
            System.out.println("LISTE: "+l.size());
            return !l.isEmpty();
        }catch(Exception ex){
            ex.printStackTrace();
            return true;
        }
        
        
    }
    
     public boolean becomeRedondant(Commissions c){
        try{
          List<Commissions> l= (List<Commissions>)em.createNamedQuery("Commissions.findCommissionBecomeRedondant").setParameter("minimum", c.getMinimum().intValue()).setParameter("maximum", c.getMaximum().intValue()).setParameter("sens", c.getSens()).setParameter("operateur", c.getOperateurs().getIdOperateur()).setParameter("profil", c.getProfils().getIdProfils()).setParameter("idPalier",c.getIdPalier()).getResultList();   
            System.out.println("LISTE: "+l.size());
            return !l.isEmpty();
        }catch(Exception ex){
            ex.printStackTrace();
            return true;
        }
        
        
    }
    
    
//    @Override
//    public List<Commissions> findAll(){
//     return (List<Commissions>)em.createNativeQuery("select * from (select * from commissions where sens='B2W' order by minimum) " +
//                                             "UNION " +
//                                             "select * from (select * from commissions where sens='W2B' order by minimum)").getResultList();
//    }
    
}
