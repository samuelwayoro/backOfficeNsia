/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.dao;

import com.sbs.easymbank.entities.Codes;
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
public class CodesFacade extends AbstractFacade<Codes> {
    @PersistenceContext(unitName = "com.sbs_easymbank3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CodesFacade() {
        super(Codes.class);
    }
    
    public List<Codes> findDescFromCode(String codevalue){
        Query query=em.createNamedQuery("Codes.findByCodevalue").setParameter("codevalue", codevalue);
        return query.getResultList();
    }
    
}
