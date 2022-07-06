/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SOCITECH-
 */
@Entity
@Table(name = "type_abonnements")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TypeAbonnements.findAll", query = "SELECT t FROM TypeAbonnements t"),
    @NamedQuery(name = "TypeAbonnements.findByLibelleType", query = "SELECT t FROM TypeAbonnements t WHERE t.libelleType = :libelleType"),
    @NamedQuery(name = "TypeAbonnements.findByIdType", query = "SELECT t FROM TypeAbonnements t WHERE t.idType = :idType")})
public class TypeAbonnements implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 50)
    @Column(name = "libelle_type")
    private String libelleType;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "id_type")
    private String idType;

    public TypeAbonnements() {
    }

    public TypeAbonnements(String idType) {
        this.idType = idType;
    }

    public String getLibelleType() {
        return libelleType;
    }

    public void setLibelleType(String libelleType) {
        this.libelleType = libelleType;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idType != null ? idType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TypeAbonnements)) {
            return false;
        }
        TypeAbonnements other = (TypeAbonnements) object;
        if ((this.idType == null && other.idType != null) || (this.idType != null && !this.idType.equals(other.idType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sbs.easymbank.entities.TypeAbonnements[ idType=" + idType + " ]";
    }
    
}
