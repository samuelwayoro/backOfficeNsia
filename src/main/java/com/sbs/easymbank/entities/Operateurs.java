/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author alex
 */
@Entity
@Table(name = "OPERATEURS")
@XmlRootElement
@Cacheable(false)
@NamedQueries({
    @NamedQuery(name = "Operateurs.findAll", query = "SELECT o FROM Operateurs o"),
    @NamedQuery(name = "Operateurs.findByIdOperateur", query = "SELECT o FROM Operateurs o WHERE o.idOperateur = :idOperateur"),
    @NamedQuery(name = "Operateurs.findByDesignationOperateur", query = "SELECT o FROM Operateurs o WHERE o.designationOperateur = :designationOperateur"),
    @NamedQuery(name = "Operateurs.findByBic", query = "SELECT o FROM Operateurs o WHERE o.bic = :bic")})
public class Operateurs implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_OPERATEUR")
    private BigDecimal idOperateur;
    @Size(max = 50)
    @Column(name = "DESIGNATION_OPERATEUR")
    private String designationOperateur;
    @Size(max = 10)
    @Column(name = "BIC")
    private String bic;
    @Size(max = 100)
    @Column(name = "PAGE_SOUSCRIPTION")
    private String pageSouscription;
    @Size(max = 100)
    @Column(name = "PAGE_PARAMETRAGE")
    private String pageParametrage;
    @OneToMany(mappedBy = "operateur")
    private List<Abonnements> abonnementsList;
    @OneToMany(mappedBy = "operateurs")
    private List<Commissions> commissionsList;
    @OneToMany(mappedBy = "operateurs")
    private List<Transactions> transactionsList;

    public Operateurs() {
    }

    public Operateurs(BigDecimal idOperateur) {
        this.idOperateur = idOperateur;
    }

    public BigDecimal getIdOperateur() {
        return idOperateur;
    }

    public void setIdOperateur(BigDecimal idOperateur) {
        this.idOperateur = idOperateur;
    }

    public String getDesignationOperateur() {
        return designationOperateur;
    }

    public void setDesignationOperateur(String designationOperateur) {
        this.designationOperateur = designationOperateur;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public String getPageSouscription() {
        return pageSouscription;
    }

    public void setPageSouscription(String pageSouscription) {
        this.pageSouscription = pageSouscription;
    }

    public String getPageParametrage() {
        return pageParametrage;
    }

    public void setPageParametrage(String pageParametrage) {
        this.pageParametrage = pageParametrage;
    }
    
    
    
    

    @XmlTransient
    public List<Abonnements> getAbonnementsList() {
        return abonnementsList;
    }

    public void setAbonnementsList(List<Abonnements> abonnementsList) {
        this.abonnementsList = abonnementsList;
    }

    @XmlTransient
    public List<Commissions> getCommissionsList() {
        return commissionsList;
    }

    public void setCommissionsList(List<Commissions> commissionsList) {
        this.commissionsList = commissionsList;
    }

    @XmlTransient
    public List<Transactions> getTransactionsList() {
        return transactionsList;
    }

    public void setTransactionsList(List<Transactions> transactionsList) {
        this.transactionsList = transactionsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOperateur != null ? idOperateur.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Operateurs)) {
            return false;
        }
        Operateurs other = (Operateurs) object;
        if ((this.idOperateur == null && other.idOperateur != null) || (this.idOperateur != null && !this.idOperateur.equals(other.idOperateur))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return  idOperateur.toString();
    }
    
}
