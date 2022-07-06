/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
 * @author alex
 */
@Entity
@Table(name = "ABONNEMENT_BANQUE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AbonnementBanque.findAll", query = "SELECT a FROM AbonnementBanque a"),
    @NamedQuery(name = "AbonnementBanque.findById", query = "SELECT a FROM AbonnementBanque a WHERE a.id = :id"),
    @NamedQuery(name = "AbonnementBanque.findByCni", query = "SELECT a FROM AbonnementBanque a WHERE a.cni = :cni"),
    @NamedQuery(name = "AbonnementBanque.findByCompte", query = "SELECT a FROM AbonnementBanque a WHERE a.compte = :compte"),
    @NamedQuery(name = "AbonnementBanque.findByRacine", query = "SELECT a FROM AbonnementBanque a WHERE a.racine = :racine")})
public class AbonnementBanque implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 255)
    @Column(name = "CNI")
    private String cni;
    @Column(name = "COMPTE")
    private BigInteger compte;
    @Size(max = 255)
    @Column(name = "DATE_NAISSANCE")
    private String dateNaissance;
    @Size(max = 255)
    @Column(name = "DEVISE")
    private String devise;
    @Size(max = 255)
    @Column(name = "LIBELLE")
    private String libelle;
    @Size(max = 255)
    @Column(name = "NOM")
    private String nom;
    @Size(max = 255)
    @Column(name = "NUMERO")
    private String numero;
    @Size(max = 255)
    @Column(name = "PRENOMS")
    private String prenoms;
    @Column(name = "RACINE")
    private BigInteger racine;
    @Column(name = "SOLDE_DISPO")
    private BigInteger soldeDispo;
    @Column(name = "SOLDE_COURANT")
    private BigInteger soldeCourant;

    public AbonnementBanque() {
    }

    public AbonnementBanque(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getCni() {
        return cni;
    }

    public void setCni(String cni) {
        this.cni = cni;
    }

    public BigInteger getCompte() {
        return compte;
    }

    public void setCompte(BigInteger compte) {
        this.compte = compte;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getDevise() {
        return devise;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getPrenoms() {
        return prenoms;
    }

    public void setPrenoms(String prenoms) {
        this.prenoms = prenoms;
    }

    public BigInteger getRacine() {
        return racine;
    }

    public void setRacine(BigInteger racine) {
        this.racine = racine;
    }

    public BigInteger getSoldeDispo() {
        return soldeDispo;
    }

    public void setSoldeDispo(BigInteger soldeDispo) {
        this.soldeDispo = soldeDispo;
    }

    public BigInteger getSoldeCourant() {
        return soldeCourant;
    }

    public void setSoldeCourant(BigInteger soldeCourant) {
        this.soldeCourant = soldeCourant;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AbonnementBanque)) {
            return false;
        }
        AbonnementBanque other = (AbonnementBanque) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sbs.easymbank.entities.AbonnementBanque[ id=" + id + " ]";
    }
    
}
