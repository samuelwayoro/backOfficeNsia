/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.dao;

import com.sbs.easymbank.entities.Pagesouscription;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author SOCITECH-
 */
@Stateless
public class PagesouscriptionFacade extends AbstractFacade<Pagesouscription> {
    @PersistenceContext(unitName = "com.sbs_easymbank3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PagesouscriptionFacade() {
        super(Pagesouscription.class);
    }
    
    public List<Pagesouscription> findByOperateur(String operateur){
        return em.createNamedQuery("Pagesouscription.findByOperateur").setParameter("operateur",operateur).getResultList();
    }
    
}
