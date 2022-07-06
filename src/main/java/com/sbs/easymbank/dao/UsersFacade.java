/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.dao;

import com.sbs.easymbank.entities.Users;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author SOCITECH-
 */
@Stateless
public class UsersFacade extends AbstractFacade<Users> {

    @PersistenceContext(unitName = "com.sbs_easymbank3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsersFacade() {
        super(Users.class);
    }

    public Users findByLogin(String login) {
        try {
            //        System.out.println("findByLogin DATE:"+new Date(System.currentTimeMillis()));
            return (Users) getEntityManager().createQuery("SELECT u FROM Users u WHERE u.login = :login").setParameter("login", login).getSingleResult();

        } catch (Exception ex) {
            return null;
        }
    }

    public List<Users> findByActiver(boolean actif) {
        try {
            //        System.out.println("findByActiver DATE:"+new Date(System.currentTimeMillis()));
            return (List<Users>) getEntityManager().createNamedQuery("Users.findByActiver").setParameter("activer", actif).getResultList();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

}
