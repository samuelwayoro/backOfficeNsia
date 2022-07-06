/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.dao;

import com.sbs.easymbank.entities.Agences;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 *
 * @author SOCITECH-
 */
@Stateless
public class AgencesFacade extends AbstractFacade<Agences> {
    @PersistenceContext(unitName = "com.sbs_easymbank3_war_1.0-SNAPSHOTPU") 
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AgencesFacade() {
        super(Agences.class);
    }
    
    public String findLibelleByCode(String code){
        String retour = "";
        try{
         List<Agences> agences = em.createNamedQuery("Agences.findByCodeagence").setParameter("codeagence", code).getResultList();
         if(!agences.isEmpty())
            retour = agences.get(0).getLibelle(); 
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return retour;
    }
    
}
