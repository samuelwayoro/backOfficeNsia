/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.dao;

import com.sbs.easymbank.entities.Limitestransactions;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author samuel
 */
@Stateless
public class LimitestransactionsFacade extends AbstractFacade<Limitestransactions> {

    @PersistenceContext(unitName = "com.sbs_easymbank3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public LimitestransactionsFacade() {
        super(Limitestransactions.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

//    methode permettant la mise a jour d'une limite de transaction
    public Limitestransactions updateLimitestransactionByKeyColumn(String profilClient, String typeTransaction, String infos, String operateurTelco, String newValue) {
        //recup depuis la bd
        Query q = this.em.createQuery("select l from Limitestransactions l WHERE l.profilclient= :profCli and l.typeTransaction =:typTransac and l.infos =:infos and l.operateur =:oper");
        q.setParameter("profCli", profilClient);
        q.setParameter("typTransac", typeTransaction);
        q.setParameter("infos", infos);
        q.setParameter("oper", operateurTelco);

        Limitestransactions limite = new Limitestransactions();
        try {
            limite = (Limitestransactions) q.getSingleResult();
            System.out.println("limite recuperee   " + limite.getDesignation() + " ");
            limite.setValeur(newValue);
            return this.updateLimitesTransaction(limite);
        } catch (NoResultException e) {
            return null;
        }

    }

    //methode d'insertion de nouvelles limites transactionnelles
    public void addLimite(Limitestransactions l, String valeur) {
        System.out.println("les donnees pour l'insertion : " + l.toString() + " ,sa valeur " + valeur);

    }

    //methode de verification d'unicité de la limite de transactions;
    public Boolean isUnique(Limitestransactions theLimite) {
        System.out.println("la limite a verifier ... " + theLimite);
        Query qr = em.createNamedQuery("Limitestransactions.findByDesignation").setParameter("designation", theLimite.getDesignation());
        List<Limitestransactions> listelimites = qr.getResultList();
        if (listelimites.isEmpty()) {//retourne true si unique en bd sinon false 
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }

    }

    public Limitestransactions updateLimitestransaction(String newValue, String operateur, String profilClient, String infos, String typeTransac) {
        System.out.println(" nouvelle valeur " + newValue);
        Query qr = em.createNativeQuery("UPDATE limitestransactions SET valeur = :val WHERE operateur = :op AND profilclient = :profCli AND infos = :info AND typetransac = :typTransac");
        qr.setParameter("val", newValue);
        qr.setParameter("op", operateur);
        qr.setParameter("profCli", profilClient);
        qr.setParameter("info", infos);
        qr.setParameter("typTransac", typeTransac);

        return (Limitestransactions) qr.getSingleResult();
    }

    public Limitestransactions updateLimitesTransaction(Limitestransactions l) {
        return em.merge(l);
    }

    /**
     * *
     * recupère toutes la liste des libelle de limites transactionnelles
     *
     * @return
     */
    public List<String> listeInfosLimitesTransactions() {
        List<String> infos = this.em.createNativeQuery("select distinct infos from limitestransactions").getResultList();
        return infos;
    }

}
