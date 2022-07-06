package com.sbs.easymbank.dao;

import com.sbs.easymbank.entities.Profils;
import java.io.PrintStream;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ProfilsFacade
        extends AbstractFacade<Profils> {

    @PersistenceContext(unitName = "com.sbs_easymbank3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return this.em;
    }

    public ProfilsFacade() {
        super(Profils.class);
    }

    public Profils findProfilFromLibelle(String libelle) {
        Query query = this.em.createNamedQuery("Profils.findByLibelle");
        query.setParameter("libelle", "%" + libelle.toUpperCase() + "%");
        List<Profils> profils = query.getResultList();
        if ((profils != null) && (!profils.isEmpty())) {
            return (Profils) profils.get(0);
        }
        return null;
    }

    public boolean removeProfil(Profils profil) {
        boolean success = false;
        try {
            getEntityManager().remove(getEntityManager().merge(profil));
            getEntityManager().flush();
            success = true;
        } catch (Exception e) {
            success = false;
            System.out.println(" ex : " + e.getMessage());
            addErrorMessage("Erreur survenue : " + e.getMessage());
        }
        return success;
    }
}
