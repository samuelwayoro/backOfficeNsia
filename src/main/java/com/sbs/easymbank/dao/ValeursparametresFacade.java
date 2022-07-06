package com.sbs.easymbank.dao;

import com.sbs.easymbank.entities.Valeursparametres;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ValeursparametresFacade
  extends AbstractFacade<Valeursparametres>
{
  @PersistenceContext(unitName="com.sbs_easymbank3_war_1.0-SNAPSHOTPU")
  private EntityManager em;
  
  protected EntityManager getEntityManager()
  {
    return this.em;
  }
  
  public ValeursparametresFacade()
  {
    super(Valeursparametres.class);
  }
  
  public List<Valeursparametres> findSelected()
  {
    try
    {
      return getEntityManager().createNamedQuery("Valeursparametres.findBySelected").setParameter("selected", Boolean.valueOf(true)).getResultList();
    }
    catch (NoResultException e) {}
    return null;
  }
  
  public List<Valeursparametres> findByCodeParam(BigDecimal codeParam)
  {
    try
    {
      return getEntityManager().createNamedQuery("Valeursparametres.findByCodeParam").setParameter("codeparam", codeParam).getResultList();
    }
    catch (NoResultException e) {}
    return new ArrayList();
  }
}
