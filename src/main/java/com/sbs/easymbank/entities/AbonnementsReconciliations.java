/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
@Table(name = "ABONNEMENTS_RECONCILIATIONS")
@XmlRootElement
@Cacheable(false)
@NamedQueries({
    @NamedQuery(name = "AbonnementsReconciliations.findAll", query = "SELECT a FROM AbonnementsReconciliations a"),
    @NamedQuery(name = "AbonnementsReconciliations.findCleared", query = "SELECT a FROM AbonnementsReconciliations a WHERE a.resilie IS NOT NULL")})        
public class AbonnementsReconciliations implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @TableGenerator(name="ABONNEMENTS_REC_SEQ",table="SEQUENCE",pkColumnName = "SEQ_NAME",valueColumnName = "SEQ_COUNT",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "ABONNEMENTS_REC_SEQ")
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDABONNEMENTS")
    private Long idabonnements;
    @Column(name = "ACTIF")
    private Boolean actif;
    @Size(max = 255)
    @Column(name = "ACTIVATION")
    private String activation;
    @Size(max = 255)
    @Column(name = "ALIAS")
    private String alias;
    @Size(max = 255)
    @Column(name = "CNI")
    private String cni;
    @Size(max = 255)
    @Column(name = "CODERETOUR")
    private String coderetour;
    @Size(max = 255)
    @Column(name = "CODERETOURRESILIATION")
    private String coderetourresiliation;
    @Size(max = 255)
    @Column(name = "COMPTE")
    private String compte;
    @Size(max = 255)
    @Column(name = "DATECREATION")
    private String datecreation;
    @Size(max = 255)
    @Column(name = "DATEMODIFICATION")
    private String datemodification;
    @Size(max = 255)
    @Column(name = "DATENAISSANCE")
    private String datenaissance;
    @Size(max = 255)
    @Column(name = "DATERESILIATION")
    private String dateresiliation;
    @Size(max = 255)
    @Column(name = "DATESOUSCRIPTION")
    private String datesouscription;
    @Size(max = 255)
    @Column(name = "DATEVALIDATION")
    private String datevalidation;
    @Size(max = 255)
    @Column(name = "DEVISE")
    private String devise;
    @Size(max = 255)
    @Column(name = "LABEL")
    private String label;
    @Size(max = 255)
    @Column(name = "MOTIF")
    private String motif;
    @Size(max = 255)
    @Column(name = "NOM")
    private String nom;
    @Size(max = 255)
    @Column(name = "NUMEROTELEPHONE")
    private String numerotelephone;
    @Size(max = 255)
    @Column(name = "ORIGINE")
    private String origine;
    @Size(max = 255)
    @Column(name = "PRENOMS")
    private String prenoms;
    @Size(max = 255)
    @Column(name = "RACINE")
    private String racine;
    @Column(name = "RESILIE")
    private Boolean resilie;
    @Size(max = 255)
    @Column(name = "SERVICE")
    private String service;
    @Size(max = 255)
    @Column(name = "USERCREATE")
    private String usercreate;
    @Size(max = 255)
    @Column(name = "USERDESACTIV")
    private String userdesactiv;
    @Size(max = 255)
    @Column(name = "USERMODIF")
    private String usermodif;
    @Size(max = 255)
    @Column(name = "USERVALIDATE")
    private String uservalidate;
    @Column(name = "OPERATEUR")
    private Short operateur;

    public AbonnementsReconciliations() {
    }

    public AbonnementsReconciliations(Long idabonnements) {
        this.idabonnements = idabonnements;
    }
    
    public AbonnementsReconciliations(Abonnements ab){
        activation=ab.getActivation();
        alias=ab.getAlias();
        cni=ab.getCni();
        //coderetour=ab.getCoderetour();
        compte=ab.getCompte();
      //  datecreation=ab.getDatecreation();
        label=ab.getLabel();
        nom=ab.getNom();
        numerotelephone=ab.getNumerotelephone();
        prenoms=ab.getPrenoms();
        racine=ab.getRacine();
        service=ab.getService();
        usercreate=ab.getUsercreate();
        uservalidate=ab.getUservalidate();
    }

    public Long getIdabonnements() {
        return idabonnements;
    }

    public void setIdabonnements(Long idabonnements) {
        this.idabonnements = idabonnements;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public String getActivation() {
        return activation;
    }

    public void setActivation(String activation) {
        this.activation = activation;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCni() {
        return cni;
    }

    public void setCni(String cni) {
        this.cni = cni;
    }

    public String getCoderetour() {
        return coderetour;
    }

    public void setCoderetour(String coderetour) {
        this.coderetour = coderetour;
    }

    public String getCoderetourresiliation() {
        return coderetourresiliation;
    }

    public void setCoderetourresiliation(String coderetourresiliation) {
        this.coderetourresiliation = coderetourresiliation;
    }

    public String getCompte() {
        return compte;
    }

    public void setCompte(String compte) {
        this.compte = compte;
    }

    public String getDatecreation() {
        return datecreation;
    }

    public void setDatecreation(String datecreation) {
        this.datecreation = datecreation;
    }

    public String getDatemodification() {
        return datemodification;
    }

    public void setDatemodification(String datemodification) {
        this.datemodification = datemodification;
    }

    public String getDatenaissance() {
        return datenaissance;
    }

    public void setDatenaissance(String datenaissance) {
        this.datenaissance = datenaissance;
    }

    public String getDateresiliation() {
        return dateresiliation;
    }

    public void setDateresiliation(String dateresiliation) {
        this.dateresiliation = dateresiliation;
    }

    public String getDatesouscription() {
        return datesouscription;
    }

    public void setDatesouscription(String datesouscription) {
        this.datesouscription = datesouscription;
    }

    public String getDatevalidation() {
        return datevalidation;
    }

    public void setDatevalidation(String datevalidation) {
        this.datevalidation = datevalidation;
    }

    public String getDevise() {
        return devise;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNumerotelephone() {
        return numerotelephone;
    }

    public void setNumerotelephone(String numerotelephone) {
        this.numerotelephone = numerotelephone;
    }

    public String getOrigine() {
        return origine;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public String getPrenoms() {
        return prenoms;
    }

    public void setPrenoms(String prenoms) {
        this.prenoms = prenoms;
    }

    public String getRacine() {
        return racine;
    }

    public void setRacine(String racine) {
        this.racine = racine;
    }

    public Boolean getResilie() {
        return resilie;
    }

    public void setResilie(Boolean resilie) {
        this.resilie = resilie;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getUsercreate() {
        return usercreate;
    }

    public void setUsercreate(String usercreate) {
        this.usercreate = usercreate;
    }

    public String getUserdesactiv() {
        return userdesactiv;
    }

    public void setUserdesactiv(String userdesactiv) {
        this.userdesactiv = userdesactiv;
    }

    public String getUsermodif() {
        return usermodif;
    }

    public void setUsermodif(String usermodif) {
        this.usermodif = usermodif;
    }

    public String getUservalidate() {
        return uservalidate;
    }

    public void setUservalidate(String uservalidate) {
        this.uservalidate = uservalidate;
    }

    public Short getOperateur() {
        return operateur;
    }

    public void setOperateur(Short operateur) {
        this.operateur = operateur;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idabonnements != null ? idabonnements.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AbonnementsReconciliations)) {
            return false;
        }
        AbonnementsReconciliations other = (AbonnementsReconciliations) object;
        if ((this.idabonnements == null && other.idabonnements != null) || (this.idabonnements != null && !this.idabonnements.equals(other.idabonnements))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sbs.easymbank.entities.AbonnementsReconciliations[ idabonnements=" + idabonnements + " ]";
    }
    
}
