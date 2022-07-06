/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.dao;

import com.sbs.easymbank.entities.Connexion;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author SOCITECH-
 */
@Stateless
public class ConnexionFacade extends AbstractFacade<Connexion> {
    @PersistenceContext(unitName = "com.sbs_easymbank3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConnexionFacade() {
        super(Connexion.class);
    }
    
    public List<Connexion> findByLogin(String login){
        List<Connexion> list;
        Query query=getEntityManager().createNamedQuery("Connexion.findByLogin").setParameter( "login",login);
        list=query.getResultList();
        return list;
    }
    
}
