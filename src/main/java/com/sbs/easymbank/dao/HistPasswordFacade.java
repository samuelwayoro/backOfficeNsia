/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.dao;

import com.sbs.easymbank.entities.HistPassword;
import com.sbs.easymbank.entities.Users;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author alex
 */
@Stateless
public class HistPasswordFacade extends AbstractFacade<HistPassword> {

    @PersistenceContext(unitName = "com.sbs_easymbank3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HistPasswordFacade() {
        super(HistPassword.class);
    }
    
    public List<HistPassword> findUserPreviousPassword(Users users,int maxResult){
       return em.createNamedQuery("HistPassword.findUserPreviousPassword").setParameter("login", users.getLogin()).setMaxResults(maxResult).getResultList();
    }
    
}
