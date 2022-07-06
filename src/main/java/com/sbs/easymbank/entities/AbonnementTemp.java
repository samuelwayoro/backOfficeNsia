/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alex
 */
@Entity
@Table(name = "ABONNEMENT_TEMP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AbonnementTemp.findAll", query = "SELECT a FROM AbonnementTemp a"),
    @NamedQuery(name="AbonnementTemp.findByNumeroAndAlias",query="SELECT a FROM AbonnementTemp a WHERE a.numero =:msisdn and a.aliasTmp =:alias "),
    @NamedQuery(name = "AbonnementTemp.findByAliasTmp", query = "SELECT a FROM AbonnementTemp a WHERE a.aliasTmp = :aliasTmp"),
    @NamedQuery(name = "AbonnementTemp.findByNumero", query = "SELECT a FROM AbonnementTemp a WHERE a.numero = :numero"),
    @NamedQuery(name = "AbonnementTemp.findByNomOperateur", query = "SELECT a FROM AbonnementTemp a WHERE a.nomOperateur = :nomOperateur"),
    @NamedQuery(name = "AbonnementTemp.findByPrenomsOperateur", query = "SELECT a FROM AbonnementTemp a WHERE a.prenomsOperateur = :prenomsOperateur"),
    @NamedQuery(name = "AbonnementTemp.findByDateNaissanceOperateur", query = "SELECT a FROM AbonnementTemp a WHERE a.dateNaissanceOperateur = :dateNaissanceOperateur"),
    @NamedQuery(name = "AbonnementTemp.findByCniOperateur", query = "SELECT a FROM AbonnementTemp a WHERE a.cniOperateur = :cniOperateur"),
    @NamedQuery(name = "AbonnementTemp.findByCompte", query = "SELECT a FROM AbonnementTemp a WHERE a.compte = :compte"),
    @NamedQuery(name = "AbonnementTemp.findByNomBanque", query = "SELECT a FROM AbonnementTemp a WHERE a.nomBanque = :nomBanque"),
    @NamedQuery(name = "AbonnementTemp.findByPrenomsBanque", query = "SELECT a FROM AbonnementTemp a WHERE a.prenomsBanque = :prenomsBanque"),
    @NamedQuery(name = "AbonnementTemp.findByDateNaissanceBanque", query = "SELECT a FROM AbonnementTemp a WHERE a.dateNaissanceBanque = :dateNaissanceBanque"),
    @NamedQuery(name = "AbonnementTemp.findById", query = "SELECT a FROM AbonnementTemp a WHERE a.idTemp = :idTemp"),
    @NamedQuery(name = "AbonnementTemp.findByCniBanque", query = "SELECT a FROM AbonnementTemp a WHERE a.cniBanque = :cniBanque"),
    @NamedQuery(name = "AbonnementTemp.findByListeNumero", query = "SELECT a FROM AbonnementTemp a WHERE a.listeNumero = :listeNumero"),
    @NamedQuery(name = "AbonnementTemp.findByLibelleCompte", query = "SELECT a FROM AbonnementTemp a WHERE a.libelleCompte = :libelleCompte"),
    @NamedQuery(name = "AbonnementTemp.findByService", query = "SELECT a FROM AbonnementTemp a WHERE a.service = :service"),
    @NamedQuery(name = "AbonnementTemp.findByActivation", query = "SELECT a FROM AbonnementTemp a WHERE a.activation = :activation")})
public class AbonnementTemp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30,message = "ALIAS_TMP constraint")
    @Column(name = "ALIAS_TMP")
    private String aliasTmp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30,message = "NUMERO constraint")
    @Column(name = "NUMERO")
    private String numero;
    @Basic(optional = false)
    
    @Size( max = 225,message = "NOM_OPERATEUR constraint")
    @Column(name = "NOM_OPERATEUR")
    private String nomOperateur;
    @Basic(optional = false)
    
    @Size( max = 255,message = "PRENOMS_OPERATEUR constraint")
    @Column(name = "PRENOMS_OPERATEUR")
    private String prenomsOperateur;
    @Basic(optional = false)
    
    @Size( max = 15,message = "DATE_NAISSANCE constraint")
    @Column(name = "DATE_NAISSANCE_OPERATEUR")
    private String dateNaissanceOperateur;
    @Basic(optional = false)
    
    @Size(max = 30,message = "CNI_OPERATEUR constraint")
    @Column(name = "CNI_OPERATEUR")
    private String cniOperateur;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30,message = "COMPTE constraint")
    @Column(name = "COMPTE")
    private String compte;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255,message = "NOM_BANQUE constraint")
    @Column(name = "NOM_BANQUE")
    private String nomBanque;
    @Basic(optional = false)
    @NotNull
    @Size( max = 255,message = "PRENOMS_BANQUE constraint")
    @Column(name = "PRENOMS_BANQUE")
    private String prenomsBanque;
    @Basic(optional = false)
    @NotNull
    @Size( max = 15,message = "DATE_NAISSANCE_BANQUE constraint")
    @Column(name = "DATE_NAISSANCE_BANQUE")
    private String dateNaissanceBanque;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @TableGenerator(name="ABONNEMENTTMP_ID_SEQ",table="SEQUENCE",pkColumnName = "SEQ_NAME",valueColumnName = "SEQ_COUNT",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "ABONNEMENTTMP_ID_SEQ")
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_TEMP")
    private BigDecimal idTemp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30,message = "CNI_BANQUE constraint")
    @Column(name = "CNI_BANQUE")
    private String cniBanque;
    @Basic(optional = false)
    
    @Size(min = 1, max = 100,message = "LISTE_NUMERO constraint")
    @Column(name = "LISTE_NUMERO")
    private String listeNumero;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200,message = "LIBELLE_COMPTE constraint")
    @Column(name = "LIBELLE_COMPTE")
    private String libelleCompte;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30,message = "SERVICE constraint")
    @Column(name = "SERVICE")
    private String service;
    @Basic(optional = false)
    @NotNull
    @Size(max = 20,message = "ACTIVATION constraint")
    @Column(name = "ACTIVATION")
    private String activation;

    public AbonnementTemp() {
    }

    public AbonnementTemp(BigDecimal id) {
        this.idTemp = id;
    }

    public AbonnementTemp(BigDecimal id, String aliasTmp, String numero, String nomOperateur, String prenomsOperateur, String dateNaissanceOperateur, String cniOperateur, String compte, String nomBanque, String prenomsBanque, String dateNaissanceBanque, String cniBanque, String listeNumero, String listeCompte, String service, String activation) {
        this.idTemp = id;
        this.aliasTmp = aliasTmp;
        this.numero = numero;
        this.nomOperateur = nomOperateur;
        this.prenomsOperateur = prenomsOperateur;
        this.dateNaissanceOperateur = dateNaissanceOperateur;
        this.cniOperateur = cniOperateur;
        this.compte = compte;
        this.nomBanque = nomBanque;
        this.prenomsBanque = prenomsBanque;
        this.dateNaissanceBanque = dateNaissanceBanque;
        this.cniBanque = cniBanque;
        this.listeNumero = listeNumero;
        this.libelleCompte = listeCompte;
        this.service = service;
        this.activation = activation;
    }
    
     public AbonnementTemp(Abonnements abonnements,AbonnementsOm abonnementsOm){
        aliasTmp=abonnements.getAlias();
        compte=abonnements.getCompte();
        libelleCompte=abonnements.getLabel();
        activation=abonnements.getActivation();
        service=abonnements.getService();
        nomBanque=abonnements.getNom();
        prenomsBanque=abonnements.getPrenoms();
        dateNaissanceBanque=abonnements.getDatenaissance();
        cniBanque=abonnements.getCni();
        numero=abonnements.getNumerotelephone();
        nomOperateur=abonnementsOm.getNom();
        prenomsOperateur=abonnementsOm.getPrenoms();
        dateNaissanceOperateur=abonnementsOm.getDateNaissance();
        cniOperateur=abonnementsOm.getCni();
        
           
    }

    public String getAliasTmp() {
        return aliasTmp;
    }

    public void setAliasTmp(String aliasTmp) {
        this.aliasTmp = aliasTmp;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNomOperateur() {
        return nomOperateur;
    }

    public void setNomOperateur(String nomOperateur) {
        this.nomOperateur = nomOperateur;
    }

    public String getPrenomsOperateur() {
        return prenomsOperateur;
    }

    public void setPrenomsOperateur(String prenomsOperateur) {
        this.prenomsOperateur = prenomsOperateur;
    }

    public String getDateNaissanceOperateur() {
        return dateNaissanceOperateur;
    }

    public void setDateNaissanceOperateur(String dateNaissanceOperateur) {
        this.dateNaissanceOperateur = dateNaissanceOperateur;
    }

    public String getCniOperateur() {
        return cniOperateur;
    }

    public void setCniOperateur(String cniOperateur) {
        this.cniOperateur = cniOperateur;
    }

    public String getCompte() {
        return compte;
    }

    public void setCompte(String compte) {
        this.compte = compte;
    }

    public String getNomBanque() {
        return nomBanque;
    }

    public void setNomBanque(String nomBanque) {
        this.nomBanque = nomBanque;
    }

    public String getPrenomsBanque() {
        return prenomsBanque;
    }

    public void setPrenomsBanque(String prenomsBanque) {
        this.prenomsBanque = prenomsBanque;
    }

    public String getDateNaissanceBanque() {
        return dateNaissanceBanque;
    }

    public void setDateNaissanceBanque(String dateNaissanceBanque) {
        this.dateNaissanceBanque = dateNaissanceBanque;
    }

    public BigDecimal getId() {
        return idTemp;
    }

    public void setId(BigDecimal id) {
        this.idTemp = id;
    }

    public String getCniBanque() {
        return cniBanque;
    }

    public void setCniBanque(String cniBanque) {
        this.cniBanque = cniBanque;
    }

    public String getListeNumero() {
        return listeNumero;
    }

    public void setListeNumero(String listeNumero) {
        this.listeNumero = listeNumero;
    }

    public String getLibelleCompte() {
        return libelleCompte;
    }

    public void setLibelleCompte(String listeCompte) {
        this.libelleCompte = listeCompte;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getActivation() {
        return activation;
    }

    public void setActivation(String activation) {
        this.activation = activation;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTemp != null ? idTemp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AbonnementTemp)) {
            return false;
        }
        AbonnementTemp other = (AbonnementTemp) object;
        if ((this.idTemp == null && other.idTemp != null) || (this.idTemp != null && !this.idTemp.equals(other.idTemp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sbs.easymbank.entities.AbonnementTemp[ id=" + idTemp + " ]";
    }
    
}
