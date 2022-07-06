package com.sbs.easymbank.dao;

import com.sbs.easymbank.entities.Parametres;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ParametresFacade extends AbstractFacade<Parametres> {

    @PersistenceContext(unitName = "com.sbs_easymbank3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return this.em;
    }

    public ParametresFacade() {
        super(Parametres.class);
    }

    public List<Parametres> findByCodeParam(String codeparam) {
        Query query = this.em.createNamedQuery("Parametres.findByCodeparam").setParameter("codeparam", codeparam);
        return query.getResultList();
    }

    /**
     * Samuel
     *
     * @param codeparam
     * @return un parametre a partir de son codeparam
     */
    public Parametres findByCodeParametre(String codeparam) {
        Query query = this.em.createNamedQuery("Parametres.findByCodeparam").setParameter("codeparam", codeparam);
        return (Parametres) query.getSingleResult();
    }

    public List<Parametres> findByCodeParamList(List<String> codeparams) {
        Query query = this.em.createNamedQuery("Parametres.findByCodeparam").setParameter("codeparams", codeparams);
        return query.getResultList();
    }

    public String findValeurByCodeAndValeur(String codeparam, String typeParam) {
        Query query = this.em.createQuery("SELECT p FROM Parametres p WHERE p.codeparam = :codeparam AND p.typeParam = :typeParam ").setParameter("codeparam", codeparam).setParameter("typeParam", typeParam);
        List<Parametres> params = query.getResultList();
        if ((params != null) && (!params.isEmpty())) {
            return ((Parametres) params.get(0)).getValeur();
        }
        return null;
    }

    public Parametres findParametre(String codeparam, String typeParam) {
        Query query = this.em.createQuery("SELECT p FROM Parametres p WHERE p.codeparam = :codeparam AND p.typeParam = :typeParam ").setParameter("codeparam", codeparam).setParameter("typeParam", typeParam);
        List<Parametres> params = query.getResultList();
        if ((params != null) && (!params.isEmpty())) {
            System.out.println("params.get(0) " + ((Parametres) params.get(0)).getCodeparam() + " getValeur " + ((Parametres) params.get(0)).getValeur());
            return (Parametres) params.get(0);
        }
        return null;
    }

    /**
     * update d'un parametre en fonction de son code param
     *
     * @param parameters
     * @return
     */
    public String updateParamByCodeParam(HashMap<Parametres, String> parameters) {
        try {
            for (Map.Entry<Parametres, String> entry : parameters.entrySet()) {
                System.out.println("cle " + entry.getKey() + "   valeur " + entry.getValue());
                updateParam(entry.getKey());
            }
            System.out.println("taille de la map   " + parameters.size());
              return String.valueOf(parameters.size());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
      
    }

    public String findByACodeParam(String codeparam) {
        Query query = this.em.createNamedQuery("Parametres.findByCodeparam").setParameter("codeparam", codeparam);
        List<Parametres> params = query.getResultList();
        if ((params != null) && (!params.isEmpty())) {
            //     System.out.println("params.get(0).getValeur()" + ((Parametres)params.get(0)).getValeur());
            return ((Parametres) params.get(0)).getValeur();
        }
        return null;
    }

    public Parametres updateParam(Parametres p) {
        return em.merge(p);
    }

    public List<Parametres> findListValeursForParam(String typeParam) {
        try {
            Query query = this.em.createNamedQuery("Parametres.findListValeur").setParameter("typeParam", typeParam);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Parametres> findParametresByPatten(String[] pattern) {
        StringBuilder requete = new StringBuilder("SELECT p FROM Parametres p WHERE p.codeparam LIKE");
        requete.append(" :codeparam0");
        for (int i = 1; i < pattern.length; i++) {
            requete.append(" OR p.codeparam LIKE :codeparam" + i);
        }
        Query query = this.em.createQuery(requete.toString()).setParameter("codeparam0", "%" + pattern[0]);
        for (int i = 1; i < pattern.length; i++) {
            query.setParameter("codeparam" + i, "%" + pattern[i]);
        }
        return query.getResultList();
    }

    public List<Parametres> findParametresByPatternFromBeginning(String[] pattern) {
        StringBuilder requete = new StringBuilder("SELECT p FROM Parametres p WHERE p.codeparam LIKE");
        requete.append(" :codeparam0");
        for (int i = 1; i < pattern.length; i++) {
            requete.append(" OR p.codeparam LIKE :codeparam" + i);
        }
        Query query = this.em.createQuery(requete.toString()).setParameter("codeparam0", pattern[0] + "%");
        for (int i = 1; i < pattern.length; i++) {
            query.setParameter("codeparam" + i, pattern[i] + "%");
        }
        return query.getResultList();
    }

}
