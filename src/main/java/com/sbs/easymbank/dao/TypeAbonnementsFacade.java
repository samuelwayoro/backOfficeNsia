/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.dao;

import com.sbs.easymbank.entities.TypeAbonnements;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author SOCITECH-
 */
@Stateless
public class TypeAbonnementsFacade extends AbstractFacade<TypeAbonnements> {
    @PersistenceContext(unitName = "com.sbs_easymbank3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TypeAbonnementsFacade() {
        super(TypeAbonnements.class);
    }
    
}
