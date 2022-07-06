/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.dao;

import com.sbs.easymbank.entities.AbonnementTemp;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author alex
 */
@Stateless
public class AbonnementTempFacade extends AbstractFacade<AbonnementTemp> {

    @PersistenceContext(unitName = "com.sbs_easymbank3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AbonnementTempFacade() {
        super(AbonnementTemp.class);
    }

    public AbonnementTemp findByAlias(String alias) {
        try {
            return (AbonnementTemp) getEntityManager().createNamedQuery("AbonnementTemp.findByAliasTmp").setParameter("aliasTmp", alias).getSingleResult();
        } catch (NoResultException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //retourne un abonne temporaire a partir de son numero et de son alias => specialement conçu pour MTNGB 
    public AbonnementTemp findByNumberAndAlias(String msisdn, String alias) {
        try {
            return (AbonnementTemp) getEntityManager().createNamedQuery("AbonnementTemp.findByNumeroAndAlias").setParameter("msisdn", msisdn).setParameter("alias", alias).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
