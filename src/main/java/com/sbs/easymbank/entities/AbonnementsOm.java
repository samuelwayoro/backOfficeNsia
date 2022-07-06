/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.entities;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
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
@Table(name = "abonnements_om")
@Cacheable(false)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AbonnementsOm.findAll", query = "SELECT a FROM AbonnementsOm a"),
    @NamedQuery(name = "AbonnementsOm.findByMsisdn", query = "SELECT a FROM AbonnementsOm a WHERE a.msisdn = :msisdn"),
    @NamedQuery(name = "AbonnementsOm.findByNom", query = "SELECT a FROM AbonnementsOm a WHERE a.nom = :nom"),
    @NamedQuery(name = "AbonnementsOm.findByPrenoms", query = "SELECT a FROM AbonnementsOm a WHERE a.prenoms = :prenoms"),
    @NamedQuery(name = "AbonnementsOm.findByDateNaissance", query = "SELECT a FROM AbonnementsOm a WHERE a.dateNaissance = :dateNaissance"),
    @NamedQuery(name = "AbonnementsOm.findByCni", query = "SELECT a FROM AbonnementsOm a WHERE a.cni = :cni"),
    @NamedQuery(name = "AbonnementsOm.findByCodeRetour", query = "SELECT a FROM AbonnementsOm a WHERE a.codeRetour = :codeRetour"),
    @NamedQuery(name = "AbonnementsOm.findByActivationkey", query = "SELECT a FROM AbonnementsOm a WHERE a.activationkey = :activationkey"),
    @NamedQuery(name = "AbonnementsOm.findKYC", query = "SELECT a FROM AbonnementsOm a WHERE a.msisdn = :msisdn AND a.activationkey = :activationkey")})
public class AbonnementsOm implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "msisdn")
    private String msisdn;
    @Size(max = 10)
    @Column(name = "nom")
    private String nom;
    @Size(max = 30)
    @Column(name = "prenoms")
    private String prenoms;
    @Size(max = 10)
    @Column(name = "date_naissance")
    private String dateNaissance;
    @Size(max = 20)
    @Column(name = "cni")
    private String cni;
    @Column(name = "code_retour")
    private BigInteger codeRetour;
    @Size(max = 20)
    @Column(name = "activationkey")
    private String activationkey;
    @Column(name = "TYPECOMPTE")
    private String typecompte;
    @Size(max = 255)
    @Column(name = "NATIONALITE")
    private String nationalite;
    @Size(max = 255)
    @Column(name = "VILLE")
    private String ville;
    @Size(max = 255)
    @Column(name = "PAYS")
    private String pays;
    @Size(max = 255)
    @Column(name = "REGION")
    private String region;
    @Size(max = 1)
    @Column(name = "GENRE")
    private String genre;
    private String status;


    public AbonnementsOm() {
    }

    public AbonnementsOm(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenoms() {
        return prenoms;
    }

    public void setPrenoms(String prenoms) {
        this.prenoms = prenoms;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getCni() {
        return cni;
    }

    public void setCni(String cni) {
        this.cni = cni;
    }

    public BigInteger getCodeRetour() {
        return codeRetour;
    }

    public void setCodeRetour(BigInteger codeRetour) {
        this.codeRetour = codeRetour;
    }

    public String getActivationkey() {
        return activationkey;
    }

    public void setActivationkey(String activationkey) {
        this.activationkey = activationkey;
    }

    public String getTypecompte() {
        return typecompte;
    }

    public void setTypecompte(String typecompte) {
        this.typecompte = typecompte;
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (msisdn != null ? msisdn.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AbonnementsOm)) {
            return false;
        }
        AbonnementsOm other = (AbonnementsOm) object;
        if ((this.msisdn == null && other.msisdn != null) || (this.msisdn != null && !this.msisdn.equals(other.msisdn))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sbs.easymbank.entities.AbonnementsOm[ msisdn=" + msisdn + " ]";
    }
    
}
