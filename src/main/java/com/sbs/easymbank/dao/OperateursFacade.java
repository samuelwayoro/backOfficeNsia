/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.dao;

/**
 *
 * @author alex
 */
import com.sbs.easymbank.entities.Operateurs;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class OperateursFacade extends AbstractFacade<Operateurs>{
    @PersistenceContext(unitName = "com.sbs_easymbank3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OperateursFacade() {
        super(Operateurs.class);
    }

    public List<Operateurs> findByDesignationOperateur(String designationOperateur){
      return (List<Operateurs>)em.createNamedQuery("Operateurs.findByDesignationOperateur").setParameter("designationOperateur", designationOperateur).getResultList();
    }
}
