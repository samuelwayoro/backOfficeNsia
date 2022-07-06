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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alex
 */
@Entity
@Table(name = "ABONNEMENTS")
@XmlRootElement
@Cacheable(false)
@NamedQueries({
    @NamedQuery(name = "Abonnements.findAll", query = "SELECT a FROM Abonnements a")
    ,
    @NamedQuery(name = "Abonnements.findByIdabonnements", query = "SELECT a FROM Abonnements a WHERE a.idabonnements = :idabonnements")
    ,
    @NamedQuery(name = "Abonnements.findByActif", query = "SELECT a FROM Abonnements a WHERE a.actif = :actif")
    ,
    @NamedQuery(name = "Abonnements.findByAlias", query = "SELECT a FROM Abonnements a WHERE a.alias = :alias")
    ,
    @NamedQuery(name = "Abonnements.findByResilie", query = "SELECT a FROM Abonnements a WHERE a.resilie = :resilie")
    ,
    @NamedQuery(name = "Abonnements.findByService", query = "SELECT a FROM Abonnements a WHERE a.service = :service")
    ,
    @NamedQuery(name = "Abonnements.findByUsercreate", query = "SELECT a FROM Abonnements a WHERE a.usercreate = :usercreate")
    ,
    @NamedQuery(name = "Abonnements.findByUserdesactiv", query = "SELECT a FROM Abonnements a WHERE a.userdesactiv = :userdesactiv")
    ,
    @NamedQuery(name = "Abonnements.findByUsermodif", query = "SELECT a FROM Abonnements a WHERE a.usermodif = :usermodif")
    ,
    @NamedQuery(name = "Abonnements.findByUserrejet", query = "SELECT a FROM Abonnements a WHERE a.userrejet = :userrejet")
    ,
    @NamedQuery(name = "Abonnements.findByUservalidate", query = "SELECT a FROM Abonnements a WHERE a.uservalidate = :uservalidate")
    ,
    @NamedQuery(name = "Abonnements.findByRacineCompteAndMSISDN", query = "SELECT a FROM Abonnements a WHERE  a.compte = :compte AND a.numerotelephone = :numerotelephone AND a.actif = :actif")
    ,
       @NamedQuery(name = "Abonnements.findAllowedWizall", query = "SELECT a FROM Abonnements a WHERE a.racine = :racine AND a.compte = :compte AND a.numerotelephone = :numerotelephone AND  a.operateur.designationOperateur = :nomOperateur AND a.actif = :actif")
    ,
    @NamedQuery(name = "Abonnements.findByDateCreationAndLogin", query = "SELECT a FROM Abonnements a WHERE a.datecreation LIKE :datecreation and a.usercreate = :user")
    ,
    @NamedQuery(name = "Abonnements.findAbonnementToDelete", query = "SELECT a FROM Abonnements a WHERE a.usercreate = :usercreate and a.actif = FALSE and a.resilie = FALSE and a.userrejet IS NOT NULL")
    ,
    @NamedQuery(name = "Abonnements.findAbonnementCreatedByUserValidate", query = "SELECT a FROM Abonnements a WHERE a.usercreate = :usercreate and a.datecreation LIKE :datecreation and a.actif = TRUE and a.resilie = FALSE and a.userrejet IS NULL")
    ,
    @NamedQuery(name = "Abonnements.findAbonnementRejectedOrNotYetValidate", query = "SELECT a FROM Abonnements a WHERE a.actif = :actif and a.usercreate = :usercreate and a.resilie = :resilie")
    ,
    @NamedQuery(name = "Abonnements.findAbonnementActifOfCustomerByNumber", query = "SELECT a FROM Abonnements a WHERE a.numerotelephone = :numerotelephone and a.compte = :compte and a.actif=TRUE")
    ,
    @NamedQuery(name = "Abonnements.findAbonnementToValidate", query = "SELECT a FROM Abonnements a WHERE a.actif = :actif and a.resilie = :resilie and a.userrejet IS NULL and a.agence = :agence")
    ,
    @NamedQuery(name = "Abonnements.findAbonnementToValidate2", query = "SELECT a FROM Abonnements a WHERE a.actif = :actif and a.resilie = :resilie and a.userrejet IS NULL")
    ,
    @NamedQuery(name = "Abonnements.findAbonnementCreatedByUserToValidate", query = "SELECT a FROM Abonnements a WHERE a.actif = :actif and a.resilie = :resilie and a.userrejet IS NULL and a.agence = :agence and a.usercreate = :usercreate")
    ,
    @NamedQuery(name = "Abonnements.findAbonnementByPeriod", query = "SELECT a FROM Abonnements a where (SUBSTRING(a.datevalidation,1,10) BETWEEN :borneInf AND :borneSup OR SUBSTRING(a.dateresiliation,1,10) BETWEEN :borneInf AND :borneSup) AND (a.actif = TRUE OR a.resilie =TRUE) AND a.operateur.designationOperateur LIKE :operateurs AND a.numerotelephone LIKE :phone AND a.compte LIKE :compte AND a.racine LIKE :racine ORDER BY a.datevalidation")
    ,
    @NamedQuery(name = "Abonnements.findActifByAgence", query = "SELECT a FROM Abonnements a WHERE a.actif = :actif and a.agence = :codeagence")
})

public class Abonnements implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @TableGenerator(name = "ABONNEMENTS_IDABONNEMENTS_SEQ", table = "SEQUENCE", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ABONNEMENTS_IDABONNEMENTS_SEQ")
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
    @Column(name = "AGENCE")
    private String agence;
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
    @Column(name = "ORIGINE_ABONNEMENT")
    private String origineAbonnement;
    @Size(max = 255)
    @Column(name = "PRENOMS")
    private String prenoms;
    @Size(max = 255)
    @Column(name = "RACINE")
    private String racine;
    @Column(name = "RECONCILIE")
    private Boolean reconcilie;
    @Column(name = "RESILIE")
    private boolean resilie;
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
    @Column(name = "USERREJET")
    private String userrejet;
    @Size(max = 255)
    @Column(name = "USERVALIDATE")
    private String uservalidate;
    @JoinColumn(name = "PROFIL", referencedColumnName = "ID_PROFILS")
    @ManyToOne
    private ProfilsClients profil;
    @JoinColumn(name = "OPERATEUR", referencedColumnName = "ID_OPERATEUR")
    @ManyToOne
    private Operateurs operateur;
    @Size(max = 255)
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
    @Size(max = 255)
    @Column(name = "TOKEN")
    private String token;
    @Size(max = 10)
    @Column(name = "GENRE")
    private String genre;
    @Column(name = "COMMISSIONS")
    private long commissions;
    @Transient
    private String designationOperateur;
    @Transient
    private String libelleAgence;

    public Abonnements() {
    }

    public Abonnements(Long idabonnements) {
        this.idabonnements = idabonnements;
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

    public String getAgence() {
        return agence;
    }

    public void setAgence(String agence) {
        this.agence = agence;
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

    public String getOrigineAbonnement() {
        return origineAbonnement;
    }

    public void setOrigineAbonnement(String origineAbonnement) {
        this.origineAbonnement = origineAbonnement;
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

    public Boolean getReconcilie() {
        return reconcilie;
    }

    public void setReconcilie(Boolean reconcilie) {
        this.reconcilie = reconcilie;
    }

    public boolean getResilie() {
        return resilie;
    }

    public void setResilie(boolean resilie) {
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

    public String getUserrejet() {
        return userrejet;
    }

    public void setUserrejet(String userrejet) {
        this.userrejet = userrejet;
    }

    public String getUservalidate() {
        return uservalidate;
    }

    public void setUservalidate(String uservalidate) {
        this.uservalidate = uservalidate;
    }

    public ProfilsClients getProfil() {
        return profil;
    }

    public void setProfil(ProfilsClients profil) {
        this.profil = profil;
    }

    public Operateurs getOperateur() {
        return operateur;
    }

    public void setOperateur(Operateurs operateur) {
        this.operateur = operateur;
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

    public long getCommissions() {
        return commissions;
    }

    public void setCommissions(long commissions) {
        this.commissions = commissions;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDesignationOperateur() {
        return designationOperateur;
    }

    public void setDesignationOperateur(String designationOperateur) {
        this.designationOperateur = designationOperateur;
    }

    public String getLibelleAgence() {
        return libelleAgence;
    }

    public void setLibelleAgence(String libelleAgence) {
        this.libelleAgence = libelleAgence;
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
        if (!(object instanceof Abonnements)) {
            return false;
        }
        Abonnements other = (Abonnements) object;
        if ((this.idabonnements == null && other.idabonnements != null) || (this.idabonnements != null && !this.idabonnements.equals(other.idabonnements))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sbs.easymbank.entities.Abonnements[ idabonnements=" + idabonnements + " ]";
    }

}
