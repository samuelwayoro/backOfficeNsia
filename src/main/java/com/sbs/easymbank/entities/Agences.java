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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author SOCITECH-
 */
@Entity
@Table(name = "agences")
@XmlRootElement
@Cacheable(false)
@NamedQueries({
    @NamedQuery(name = "Agences.findAll", query = "SELECT a FROM Agences a"),
    @NamedQuery(name = "Agences.findByIdagences", query = "SELECT a FROM Agences a WHERE a.idagences = :idagences"),
    @NamedQuery(name = "Agences.findByCodeagence", query = "SELECT a FROM Agences a WHERE a.codeagence = :codeagence"),
    @NamedQuery(name = "Agences.findByDatecreation", query = "SELECT a FROM Agences a WHERE a.datecreation = :datecreation"),
    @NamedQuery(name = "Agences.findByLibelle", query = "SELECT a FROM Agences a WHERE a.libelle = :libelle")})
public class Agences implements Serializable {
    @OneToMany(mappedBy = "idagences")
    private List<Users> usersList;
    private static final long serialVersionUID = 1L;
    @Id
    @TableGenerator(name="AGENCE_IDAGENCES_SEQ",table="SEQUENCE",pkColumnName = "SEQ_NAME",valueColumnName = "SEQ_COUNT",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "AGENCE_IDAGENCES_SEQ")

    @Basic(optional = false)
    @Column(name = "idagences")
    private Integer idagences;
    @Size(max = 255)
    @Column(name = "codeagence")
    private String codeagence;
    @Size(max = 255)
    @Column(name = "datecreation")
    private String datecreation;
    @Size(max = 255)
    @Column(name = "libelle")
    private String libelle;

    public Agences() {
    }

    public Agences(Integer idagences) {
        this.idagences = idagences;
    }

    public Integer getIdagences() {
        return idagences;
    }

    public void setIdagences(Integer idagences) {
        this.idagences = idagences;
    }

    public String getCodeagence() {
        return codeagence;
    }

    public void setCodeagence(String codeagence) {
        this.codeagence = codeagence;
    }

    public String getDatecreation() {
        return datecreation;
    }

    public void setDatecreation(String datecreation) {
        this.datecreation = datecreation;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idagences != null ? idagences.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Agences)) {
            return false;
        }
        Agences other = (Agences) object;
        if ((this.idagences == null && other.idagences != null) || (this.idagences != null && !this.idagences.equals(other.idagences))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return Integer.toString(idagences) ;
    }

    @XmlTransient
    public List<Users> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
    }
    
}
