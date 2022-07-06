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
@Table(name = "banque")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Banque.findAll", query = "SELECT b FROM Banque b"),
    @NamedQuery(name = "Banque.findByIdbanque", query = "SELECT b FROM Banque b WHERE b.idbanque = :idbanque"),
    @NamedQuery(name = "Banque.findByDatecreation", query = "SELECT b FROM Banque b WHERE b.datecreation = :datecreation"),
    @NamedQuery(name = "Banque.findByLibelle", query = "SELECT b FROM Banque b WHERE b.libelle = :libelle"),
    @NamedQuery(name = "Banque.findByBic", query = "SELECT b FROM Banque b WHERE b.bic = :bic")})
public class Banque implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idbanque")
    private Integer idbanque;
    @Size(max = 255)
    @Column(name = "datecreation")
    private String datecreation;
    @Size(max = 255)
    @Column(name = "libelle")
    private String libelle;
    @Size(max = 50)
    @Column(name = "bic")
    private String bic;

    public Banque() {
    }

    public Banque(Integer idbanque) {
        this.idbanque = idbanque;
    }

    public Integer getIdbanque() {
        return idbanque;
    }

    public void setIdbanque(Integer idbanque) {
        this.idbanque = idbanque;
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

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idbanque != null ? idbanque.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Banque)) {
            return false;
        }
        Banque other = (Banque) object;
        if ((this.idbanque == null && other.idbanque != null) || (this.idbanque != null && !this.idbanque.equals(other.idbanque))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sbs.easymbank.entities.Banque[ idbanque=" + idbanque + " ]";
    }
    
}
