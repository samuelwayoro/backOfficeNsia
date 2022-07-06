/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.dao;

import com.sbs.easymbank.entities.Logs;
import java.io.Serializable;
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
public class LogsFacade extends AbstractFacade<Logs> implements Serializable{
    @PersistenceContext(unitName = "com.sbs_easymbank3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LogsFacade() {
        super(Logs.class);
    }
    
    public List<Logs> findAllLogs(){
        return (List<Logs>)em.createNamedQuery("Logs.findAll").getResultList();
    }


    public List<Logs> findLogsByAgence(int idAgences){
        Query query =  em.createNativeQuery("SELECT * FROM LOGS WHERE USERS IN (SELECT IDUSERS FROM USERS WHERE IDAGENCES = (SELECT IDAGENCES FROM AGENCES WHERE IDAGENCES = ?idagences))",Logs.class).setParameter("idagences", idAgences);
        return (List<Logs>)query.getResultList();
    }
    
}
