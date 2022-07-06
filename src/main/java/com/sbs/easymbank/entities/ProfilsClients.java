/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author alex
 */
@Entity
@Table(name = "PROFILS_CLIENTS")
@XmlRootElement
@Cacheable(false)
@NamedQueries({
    @NamedQuery(name = "ProfilsClients.findAll", query = "SELECT p FROM ProfilsClients p"),
    @NamedQuery(name = "ProfilsClients.findByIdProfils", query = "SELECT p FROM ProfilsClients p WHERE p.idProfils = :idProfils"),
    @NamedQuery(name = "ProfilsClients.findByDesignationProfils", query = "SELECT p FROM ProfilsClients p WHERE p.designationProfils = :designationProfils")})
public class ProfilsClients implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @TableGenerator(name="PROFILCLIENT_IDPROFILS_SEQ",table="SEQUENCE",pkColumnName = "SEQ_NAME",valueColumnName = "SEQ_COUNT",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "PROFILCLIENT_IDPROFILS_SEQ")
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PROFILS")
    private Short idProfils;
    @Size(max = 50)
    @Column(name = "DESIGNATION_PROFILS")
    private String designationProfils;
    @Size(max = 20)
    @Column(name = "CODE_PROFILS")
    private String codeProfils;
    @Size(max = 20)
    @Column(name = "DATE_CREATION_PROFILS")
    private String dateCreationProfils;
    @OneToMany(mappedBy = "profil")
    private List<Abonnements> abonnementsList;
    @OneToMany(mappedBy = "profils")
    private List<Commissions> commissionsList;

    public ProfilsClients() {
    }

    public ProfilsClients(Short idProfils) {
        this.idProfils = idProfils;
    }

    public Short getIdProfils() {
        return idProfils;
    }

    public void setIdProfils(Short idProfils) {
        this.idProfils = idProfils;
    }

    public String getDesignationProfils() {
        return designationProfils;
    }

    public void setDesignationProfils(String designationProfils) {
        this.designationProfils = designationProfils;
    }

    public String getCodeProfils() {
        return codeProfils;
    }

    public void setCodeProfils(String codeProfils) {
        this.codeProfils = codeProfils;
    }

    public String getDateCreationProfils() {
        return dateCreationProfils;
    }

    public void setDateCreationProfils(String dateCreationProfils) {
        this.dateCreationProfils = dateCreationProfils;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProfils != null ? idProfils.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProfilsClients)) {
            return false;
        }
        ProfilsClients other = (ProfilsClients) object;
        if ((this.idProfils == null && other.idProfils != null) || (this.idProfils != null && !this.idProfils.equals(other.idProfils))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return Short.toString(idProfils)  ;
    }
    
}
