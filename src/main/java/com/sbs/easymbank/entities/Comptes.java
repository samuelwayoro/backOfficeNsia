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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SOCITECH-
 */
@Entity
@Table(name = "comptes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comptes.findAll", query = "SELECT c FROM Comptes c"),
    @NamedQuery(name = "Comptes.findByIdcomptes", query = "SELECT c FROM Comptes c WHERE c.idcomptes = :idcomptes"),
    @NamedQuery(name = "Comptes.findByLibelle", query = "SELECT c FROM Comptes c WHERE c.libelle = :libelle"),
    @NamedQuery(name = "Comptes.findByNuemro", query = "SELECT c FROM Comptes c WHERE c.nuemro = :nuemro")})
public class Comptes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "idcomptes")
    private Integer idcomptes;
    @Size(max = 255)
    @Column(name = "libelle")
    private String libelle;
    @Size(max = 255)
    @Column(name = "nuemro")
    private String nuemro;
    @Size(max = 255)
    @Column(name = "agence")
    private String agence;
    @Transient
    private String typeCompte;

    public Comptes() {
    }

    public Comptes(String libelle, String nuemro) {
        this.libelle = libelle;
        this.nuemro = nuemro;
    }
    
    

    public Comptes(Integer idcomptes) {
        this.idcomptes = idcomptes;
    }

    public Integer getIdcomptes() {
        return idcomptes;
    }

    public void setIdcomptes(Integer idcomptes) {
        this.idcomptes = idcomptes;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getNuemro() {
        return nuemro;
    }

    public void setNuemro(String nuemro) {
        this.nuemro = nuemro;
    }

    public String getAgence() {
        return agence;
    }

    public void setAgence(String agence) {
        this.agence = agence;
    }

    public String getTypeCompte() {
        return typeCompte;
    }

    public void setTypeCompte(String ncg) {
        //Pour satisfaire les contraintes de Moov BN
        // Ncg 2511 : compte courant
        // Ncg 25311 : compte d'épargne
        this.typeCompte = ncg.startsWith("2511") ? "CA" : "SA";
    }
    
    
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcomptes != null ? idcomptes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comptes)) {
            return false;
        }
        Comptes other = (Comptes) object;
        if ((this.idcomptes == null && other.idcomptes != null) || (this.idcomptes != null && !this.idcomptes.equals(other.idcomptes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sbs.easymbank.entities.Comptes[ idcomptes=" + idcomptes + " ]";
    }
    
}
