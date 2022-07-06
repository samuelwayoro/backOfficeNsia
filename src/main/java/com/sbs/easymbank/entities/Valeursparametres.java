package com.sbs.easymbank.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="valeursparametres")
@XmlRootElement
@NamedQueries({@javax.persistence.NamedQuery(name="Valeursparametres.findAll", query="SELECT v FROM Valeursparametres v"), @javax.persistence.NamedQuery(name="Valeursparametres.findByCodevaleur", query="SELECT v FROM Valeursparametres v WHERE v.codevaleur = :codevaleur"), @javax.persistence.NamedQuery(name="Valeursparametres.findByLibellevaleur", query="SELECT v FROM Valeursparametres v WHERE v.libellevaleur = :libellevaleur"), @javax.persistence.NamedQuery(name="Valeursparametres.findBySelected", query="SELECT v FROM Valeursparametres v WHERE v.selected = :selected"), @javax.persistence.NamedQuery(name="Valeursparametres.findByCodeParam", query="SELECT v FROM Valeursparametres v WHERE v.codeparam = :codeparam")})
public class Valeursparametres
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional=false)
  @NotNull
  @Column(name="codevaleur")
  private BigDecimal codevaleur;
  @Size(max=100)
  @Column(name="libellevaleur")
  private String libellevaleur;
  @Basic(optional=false)
  @NotNull
  @Column(name="selected")
  private boolean selected;
  private BigDecimal codeparam;
  
  public Valeursparametres() {}
  
  public Valeursparametres(BigDecimal codevaleur)
  {
    this.codevaleur = codevaleur;
  }
  
  public Valeursparametres(BigDecimal codevaleur, boolean selected)
  {
    this.codevaleur = codevaleur;
    this.selected = selected;
  }
  
  public BigDecimal getCodevaleur()
  {
    return this.codevaleur;
  }
  
  public void setCodevaleur(BigDecimal codevaleur)
  {
    this.codevaleur = codevaleur;
  }
  
  public String getLibellevaleur()
  {
    return this.libellevaleur;
  }
  
  public void setLibellevaleur(String libellevaleur)
  {
    this.libellevaleur = libellevaleur;
  }
  
  public boolean getSelected()
  {
    return this.selected;
  }
  
  public void setSelected(boolean selected)
  {
    this.selected = selected;
  }
  
  public BigDecimal getCodeparam()
  {
    return this.codeparam;
  }
  
  public void setCodeparam(BigDecimal codeparam)
  {
    this.codeparam = codeparam;
  }
  
  public int hashCode()
  {
    int hash = 0;
    hash += (this.codevaleur != null ? this.codevaleur.hashCode() : 0);
    return hash;
  }
  
  public boolean equals(Object object)
  {
    if (!(object instanceof Valeursparametres)) {
      return false;
    }
    Valeursparametres other = (Valeursparametres)object;
    if (((this.codevaleur == null) && (other.codevaleur != null)) || ((this.codevaleur != null) && (!this.codevaleur.equals(other.codevaleur)))) {
      return false;
    }
    return true;
  }
  
  public String toString()
  {
    return "com.sbs.easymbank.entities.Valeursparametres[ codevaleur=" + this.codevaleur + " ]";
  }
}
