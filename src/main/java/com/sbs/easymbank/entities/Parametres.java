package com.sbs.easymbank.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="parametres")
@Cacheable(false)
@XmlRootElement
@NamedQueries({@javax.persistence.NamedQuery(name="Parametres.findAll", query="SELECT p FROM Parametres p"),
               @javax.persistence.NamedQuery(name="Parametres.findById", query="SELECT p FROM Parametres p WHERE p.id = :id"), 
               @javax.persistence.NamedQuery(name="Parametres.findByCodeparam", query="SELECT p FROM Parametres p WHERE p.codeparam = :codeparam"), 
               @javax.persistence.NamedQuery(name="Parametres.findByListCodeparam", query="SELECT p FROM Parametres p WHERE p.codeparam in :codeparams"), 
               @javax.persistence.NamedQuery(name="Parametres.findByValeur", query="SELECT p FROM Parametres p WHERE p.valeur = :valeur"), 
               @javax.persistence.NamedQuery(name="Parametres.findByDescription", query="SELECT p FROM Parametres p WHERE p.description = :description"),
               @javax.persistence.NamedQuery(name="Parametres.findListValeur", query="SELECT p FROM Parametres p WHERE p.typeParam = :typeParam  ")})
public class Parametres
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="PARAMETRES_ID_SEQ")
  @Basic(optional=false)
  @Column(name="id")
  private Integer id;
  @Size(max=100)
  @Column(name="codeparam")
  private String codeparam;
  @Size(max=500)
  @Column(name="valeur")
  private String valeur;
  @Size(max=255)
  @Column(name="description")
  private String description;
  @Column(name="typeparam")
  private String typeParam;
  
  public Parametres() {}
  
  public Parametres(Integer id)
  {
    this.id = id;
  }
  
  public Integer getId()
  {
    return this.id;
  }
  
  public void setId(Integer id)
  {
    this.id = id;
  }
  
  public String getCodeparam()
  {
    return this.codeparam;
  }
  
  public void setCodeparam(String codeparam)
  {
    this.codeparam = codeparam;
  }
  
  public String getValeur()
  {
    return this.valeur;
  }
  
  public void setValeur(String valeur)
  {
    this.valeur = valeur;
  }
  
  public String getDescription()
  {
    return this.description;
  }
  
  public void setDescription(String description)
  {
    this.description = description;
  }
  
  public int hashCode()
  {
    int hash = 0;
    hash += (this.id != null ? this.id.hashCode() : 0);
    return hash;
  }
  
  public boolean equals(Object object)
  {
    if (!(object instanceof Parametres)) {
      return false;
    }
    Parametres other = (Parametres)object;
    if (((this.id == null) && (other.id != null)) || ((this.id != null) && (!this.id.equals(other.id)))) {
      return false;
    }
    return true;
  }
  
  public String toString()
  {
    return "com.sbs.easymbank.entities.Parametres[ id=" + this.id + " ]";
  }
  
  public String getTypeParam()
  {
    return this.typeParam;
  }
  
  public void setTypeParam(String typeParam)
  {
    this.typeParam = typeParam;
  }
}
