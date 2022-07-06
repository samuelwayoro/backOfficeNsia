/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.dao;

import com.sbs.easymbank.entities.Abonnements;
import com.sbs.easymbank.entities.Operateurs;
import com.sbs.easymbank.entities.Users;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author SOCITECH-
 */
@Stateless
public class AbonnementsFacade extends AbstractFacade<Abonnements> {

    @PersistenceContext(unitName = "com.sbs_easymbank3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AbonnementsFacade() {
        super(Abonnements.class);
    }

    public List<Abonnements> findByResilie(boolean resilie) {

        try {

            return (List<Abonnements>) getEntityManager().createNamedQuery("Abonnements.findByResilie").setParameter("resilie", resilie).getResultList();

        } catch (NoResultException e) {

            return null;
        }
    }
    
    //suppression d'un abonne de la table des abonnes 
    public void deleteAbonne(Long id){
        Query q = em.createNativeQuery("DELETE FROM Abonnements WHERE IDABONNEMENTS = "+id);
        q.executeUpdate();
        System.out.println("OK - CLIENT SUPPRIME DE LA LISTE DES ABONNES");
    }

    public Abonnements findByAlias(String alias) {

        try {
            return (Abonnements) getEntityManager().createNamedQuery("Abonnements.findByAlias").setParameter("alias", alias).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }

    public List<Abonnements> findInactif() {
        try {
            return (List<Abonnements>) getEntityManager().createNamedQuery("Abonnements.findByActif").setParameter("actif", false).getResultList();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Abonnements> findActif() {
        try {
            return (List<Abonnements>) getEntityManager().createNamedQuery("Abonnements.findByActif").setParameter("actif", true).getResultList();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Abonnements> findByRacineCompteAndMSISDN(String compte, String msisdn) {
        try {
            return (List<Abonnements>) getEntityManager().createNamedQuery("Abonnements.findByRacineCompteAndMSISDN").setParameter("compte", compte).setParameter("numerotelephone", msisdn).setParameter("actif", true).getResultList();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    
       public Abonnements findByInfosRacineCompteAndMSISDN(String compte, String msisdn) {
        try {
            return (Abonnements) getEntityManager().createNamedQuery("Abonnements.findByRacineCompteAndMSISDN").setParameter("compte", compte).setParameter("numerotelephone", msisdn).setParameter("actif", true).getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<Abonnements> findAllowedWizall(String racine ,String compte , String msisdn , String operateur){
        try {
            return getEntityManager().createNamedQuery("Abonnements.findAllowedWizall").setParameter("racine", racine).setParameter("compte", compte).setParameter("numerotelephone", msisdn).setParameter("nomOperateur", "WIZALL").setParameter("actif", true).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<Abonnements> findByDateAndLogin(String date, String login) {
        List<Abonnements> list;
        Query query = getEntityManager().createNamedQuery("Abonnements.findByDateCreationAndLogin").setParameter("datecreation", date + "%").setParameter("user", login);
        list = query.getResultList();
        return list;
    }

    public List<Abonnements> findAbonnementToDelete(String usercreate) {
        List<Abonnements> list;
        Query query = getEntityManager().createNamedQuery("Abonnements.findAbonnementToDelete").setParameter("usercreate", usercreate);
        list = query.getResultList();
        return list;
    }

    public List<Abonnements> findAbonnementRejectedOrNotYetValidate(String login) {
        List<Abonnements> list;
        Query query = getEntityManager().createNamedQuery("Abonnements.findAbonnementRejectedOrNotYetValidate").setParameter("actif", false).setParameter("usercreate", login).setParameter("resilie", false);
        list = query.getResultList();
        return list;
    }

    public List<Abonnements> findAbonnementToValidate(String agence) {
        List<Abonnements> list;
        Query query = getEntityManager().createNamedQuery("Abonnements.findAbonnementToValidate").setParameter("actif", false).setParameter("resilie", false).setParameter("agence", agence);
        list = query.getResultList();
        return list;
    }

        public List<Abonnements> findAbonnementToValidate() {
        List<Abonnements> list;
        Query query = getEntityManager().createNamedQuery("Abonnements.findAbonnementToValidate2").setParameter("actif", false).setParameter("resilie", false);
        list = query.getResultList();
        return list;
    }

    
    public List<Abonnements> findAbonnementCreatedByUserToValidate(Users users) {
        List<Abonnements> list;
        Query query = getEntityManager().createNamedQuery("Abonnements.findAbonnementCreatedByUserToValidate").setParameter("actif", false).setParameter("resilie", false).setParameter("agence", users.getIdagences().getCodeagence()).setParameter("usercreate", users.getLogin());
        list = query.getResultList();
        return list;
    }

    public List<Abonnements> findAbonnementByPeriod(String borneInf, String borneSup,String operateurs, String compte,String racine,String phone) {
        try {
         Query query =  getEntityManager().createNamedQuery("Abonnements.findAbonnementByPeriod").setParameter("borneInf", borneInf).setParameter("borneSup", borneSup).setParameter("operateurs", "%"+operateurs+"%");
         query.setParameter("phone",phone == null ? "%%" : "%"+phone+"%");
         query.setParameter("compte",compte == null ? "%%" : "%"+compte+"%");
         query.setParameter("racine",racine == null ? "%%" : "%"+racine+"%");
         return query.getResultList();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }
    
     public List<Abonnements> findAbonnementCreatedByUserValidate(String users,String datecreation) {
        List<Abonnements> list;
        Query query = getEntityManager().createNamedQuery("Abonnements.findAbonnementCreatedByUserValidate").setParameter("usercreate", users).setParameter("datecreation", datecreation+"%");
        list = query.getResultList();
        return list;
    }
     
     public List<Abonnements> findAbonnementActifOfCustomerByNumber(Abonnements abonnements){
         return em.createNamedQuery("Abonnements.findAbonnementActifOfCustomerByNumber").setParameter("numerotelephone", abonnements.getNumerotelephone()).setParameter("compte", abonnements.getCompte()).getResultList();
     }
     
     public List<Abonnements> findAbonnementsActifsByMsisdn(Abonnements a ){
         Query q = em.createQuery("SELECT a FROM Abonnements a WHERE a.numerotelephone = :numero and a.actif=true ");
         q.setParameter("numero", a.getNumerotelephone());
         return q.getResultList();
     }
     
          public List<Abonnements> findAbonnementsActifsByMsisdn(String numero ){
         Query q = em.createQuery("SELECT a FROM Abonnements a WHERE a.numerotelephone = :numero and a.actif=true ");
         q.setParameter("numero", numero);
         return q.getResultList();
     }
     
     public List<Abonnements> findByResilie(){
         return em.createNamedQuery("Abonnements.findByResilie").setParameter("resilie", true).getResultList();
     }
     
     public  List<Abonnements> findAbonnementByCompteAndMsisdn( String cpt ,String msisdn , Operateurs idOperateur ){
         
         Query q = em.createQuery("SELECT  a FROM  Abonnements a WHERE a.compte = :leCompte and a.numerotelephone = :leMsisdn and a.operateur = :operateur  and a.actif=true ");
         q.setParameter("leCompte", cpt);
         q.setParameter("leMsisdn", msisdn);
         q.setParameter("operateur", idOperateur);
         return q.getResultList();
     }
     
     public List<Abonnements> findAbonnementByNumeroCompte(String compte) {
       Query query = em.createQuery("SELECT a FROM Abonnements a WHERE a.compte = :compte AND a.actif=true "); 
       query.setParameter("compte", compte);
        return query.getResultList();
    }
     
     public Abonnements findAbonnementActifByNumeroCompte(String compte){
         Query q = em.createQuery("SELECT a FROM Abonnements a WHERE a.compte = :compte AND a.actif=true");
         q.setParameter("compte", compte);
         return (Abonnements) q.getSingleResult();
     }
     
         public List<Abonnements> findActifByAgence(String codeAgence) {
        try {
            return (List<Abonnements>) getEntityManager().createNamedQuery("Abonnements.findActifByAgence").setParameter("actif", true).setParameter("codeagence", codeAgence).getResultList();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }


    
}
