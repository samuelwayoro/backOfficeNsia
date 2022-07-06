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
 * @author SOCITECH-
 */
@Entity
@Table(name = "profils")
@XmlRootElement
@Cacheable(false)
@NamedQueries({
    @NamedQuery(name = "Profils.findAll", query = "SELECT p FROM Profils p"),
    @NamedQuery(name = "Profils.findByIdprofils", query = "SELECT p FROM Profils p WHERE p.idprofils = :idprofils"),
    @NamedQuery(name = "Profils.findByLibelle", query = "SELECT p FROM Profils p WHERE p.libelle = :libelle"),
    @NamedQuery(name = "Profils.findByConnexion", query = "SELECT p FROM Profils p WHERE p.connexion = :connexion"),
    @NamedQuery(name = "Profils.findBySouscription", query = "SELECT p FROM Profils p WHERE p.souscription = :souscription"),
    @NamedQuery(name = "Profils.findByResiliation", query = "SELECT p FROM Profils p WHERE p.resiliation = :resiliation"),
    @NamedQuery(name = "Profils.findByCreationProfils", query = "SELECT p FROM Profils p WHERE p.creationProfils = :creationProfils"),
    @NamedQuery(name = "Profils.findByModificationProfils", query = "SELECT p FROM Profils p WHERE p.modificationProfils = :modificationProfils"),
    @NamedQuery(name = "Profils.findBySuppressionProfils", query = "SELECT p FROM Profils p WHERE p.suppressionProfils = :suppressionProfils"),
    @NamedQuery(name = "Profils.findByCreationUtilisateur", query = "SELECT p FROM Profils p WHERE p.creationUtilisateur = :creationUtilisateur"),
    @NamedQuery(name = "Profils.findBySuppressionUtilisateur", query = "SELECT p FROM Profils p WHERE p.suppressionUtilisateur = :suppressionUtilisateur"),
    @NamedQuery(name = "Profils.findByModificationProfilsUtilisateur", query = "SELECT p FROM Profils p WHERE p.modificationProfilsUtilisateur = :modificationProfilsUtilisateur"),
    @NamedQuery(name = "Profils.findByParametrage", query = "SELECT p FROM Profils p WHERE p.parametrage = :parametrage"),
    @NamedQuery(name = "Profils.findByReinitialisationUtilisateur", query = "SELECT p FROM Profils p WHERE p.reinitialisationUtilisateur = :reinitialisationUtilisateur"),
    @NamedQuery(name = "Profils.findByCreationAgence", query = "SELECT p FROM Profils p WHERE p.creationAgence = :creationAgence"),
    @NamedQuery(name = "Profils.findBySuppressionAgence", query = "SELECT p FROM Profils p WHERE p.suppressionAgence = :suppressionAgence"),
    @NamedQuery(name = "Profils.findBySupervalidation", query = "SELECT p FROM Profils p WHERE p.supervalidation = :supervalidation"),
    @NamedQuery(name = "Profils.findByReporting", query = "SELECT p FROM Profils p WHERE p.reporting = :reporting")})
public class Profils implements Serializable {
    @OneToMany(mappedBy = "idprofils")
    private List<Users> usersList;
    private static final long serialVersionUID = 1L;
    @Id
   @TableGenerator(name="PROFILS_IDPROFILS_SEQ",table="SEQUENCE",pkColumnName = "SEQ_NAME",valueColumnName = "SEQ_COUNT",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "PROFILS_IDPROFILS_SEQ")
    @Basic(optional = false)
    @Column(name = "idprofils")
    private Integer idprofils;
    @Size(max = 255)
    @Column(name = "libelle")
    private String libelle;
    @Basic(optional = false)
    @NotNull
    @Column(name = "connexion")
    private boolean connexion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "souscription")
    private boolean souscription;
    @Basic(optional = false)
    @NotNull
    @Column(name = "resiliation")
    private boolean resiliation;
    @Basic(optional = false)
    @NotNull
    @Column(name = "creation_profils")
    private boolean creationProfils;
    @Basic(optional = false)
    @NotNull
    @Column(name = "modification_profils")
    private boolean modificationProfils;
    @Basic(optional = false)
    @NotNull
    @Column(name = "suppression_profils")
    private boolean suppressionProfils;
    @Basic(optional = false)
    @NotNull
    @Column(name = "creation_utilisateur")
    private boolean creationUtilisateur;
    @Basic(optional = false)
    @NotNull
    @Column(name = "suppression_utilisateur")
    private boolean suppressionUtilisateur;
    @Basic(optional = false)
    @NotNull
    @Column(name = "modif_profils_user")
    private boolean modificationProfilsUtilisateur;
    @Basic(optional = false)
    @NotNull
    @Column(name = "parametrage")
    private boolean parametrage;
    @Basic(optional = false)
    @NotNull
    @Column(name = "reinit_user")
    private boolean reinitialisationUtilisateur;
    @Basic(optional = false)
    @NotNull
    @Column(name = "creation_agence")
    private boolean creationAgence;
    @Basic(optional = false)
    @NotNull
    @Column(name = "suppression_agence")
    private boolean suppressionAgence;
    @Basic(optional = false)
    @NotNull
    @Column(name = "supervalidation")
    private boolean supervalidation;
    @Basic(optional = false)
    @NotNull
    @Column(name = "reporting")
    private boolean reporting;
    @Basic(optional = false)
    @NotNull
    @Column(name = "transactions")
    private boolean transactions;
    @Basic(optional = false)
    @NotNull
    @Column(name = "audits")
    private boolean audits;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_PROFILS_CLIENTS")
    private boolean creationProfilsClients;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SUPPRESSION_PROFILS_CLIENTS")
    private boolean suppressionProfilsClients;
    @Basic(optional = false)
    @NotNull
    @Column(name = "securite")
    private boolean securite;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_COMMISSIONS")
    private boolean creationCommissions;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SUPPRESSION_COMMISSIONS")
    private boolean suppressionCommissions;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LIST_ABONNEMENTS")
    private boolean listAbonnements;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LIST_COMMISSIONS")
    private boolean listCommissions;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TRANSAC_TTES_AGE")
    private boolean transac_ttes_age;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ABONN_TTES_AGE")
    private boolean abonn_ttes_age;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VALIDE_TTES_AGE")
    private boolean valide_ttes_age;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AUDIT_TTES_AGE")
    private boolean audit_ttes_age;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CONNEXION_TTES_AGE")
    private boolean connexion_ttes_age;


    public Profils() {
    }

    public Profils(Integer idprofils) {
        this.idprofils = idprofils;
    }

    public Profils(Integer idprofils, boolean connexion, boolean souscription, boolean resiliation, boolean creationProfils, boolean modificationProfils, boolean suppressionProfils, boolean creationUtilisateur, boolean suppressionUtilisateur, boolean modificationProfilsUtilisateur, boolean parametrage, boolean reinitialisationUtilisateur, boolean creationAgence, boolean suppressionAgence, boolean supervalidation, boolean reporting) {
        this.idprofils = idprofils;
        this.connexion = connexion;
        this.souscription = souscription;
        this.resiliation = resiliation;
        this.creationProfils = creationProfils;
        this.modificationProfils = modificationProfils;
        this.suppressionProfils = suppressionProfils;
        this.creationUtilisateur = creationUtilisateur;
        this.suppressionUtilisateur = suppressionUtilisateur;
        this.modificationProfilsUtilisateur = modificationProfilsUtilisateur;
        this.parametrage = parametrage;
        this.reinitialisationUtilisateur = reinitialisationUtilisateur;
        this.creationAgence = creationAgence;
        this.suppressionAgence = suppressionAgence;
        this.supervalidation = supervalidation;
        this.reporting = reporting;
    }

    public Integer getIdprofils() {
        return idprofils;
    }

    public void setIdprofils(Integer idprofils) {
        this.idprofils = idprofils;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public boolean getConnexion() {
        return connexion;
    }

    public void setConnexion(boolean connexion) {
        this.connexion = connexion;
    }

    public boolean getSouscription() {
        return souscription;
    }

    public void setSouscription(boolean souscription) {
        this.souscription = souscription;
    }

    public boolean getResiliation() {
        return resiliation;
    }

    public void setResiliation(boolean resiliation) {
        this.resiliation = resiliation;
    }

    public boolean getCreationProfils() {
        return creationProfils;
    }

    public void setCreationProfils(boolean creationProfils) {
        this.creationProfils = creationProfils;
    }

    public boolean getModificationProfils() {
        return modificationProfils;
    }

    public void setModificationProfils(boolean modificationProfils) {
        this.modificationProfils = modificationProfils;
    }

    public boolean getSuppressionProfils() {
        return suppressionProfils;
    }

    public void setSuppressionProfils(boolean suppressionProfils) {
        this.suppressionProfils = suppressionProfils;
    }

    public boolean getCreationUtilisateur() {
        return creationUtilisateur;
    }

    public void setCreationUtilisateur(boolean creationUtilisateur) {
        this.creationUtilisateur = creationUtilisateur;
    }

    public boolean getSuppressionUtilisateur() {
        return suppressionUtilisateur;
    }

    public void setSuppressionUtilisateur(boolean suppressionUtilisateur) {
        this.suppressionUtilisateur = suppressionUtilisateur;
    }

    public boolean getModificationProfilsUtilisateur() {
        return modificationProfilsUtilisateur;
    }

    public void setModificationProfilsUtilisateur(boolean modificationProfilsUtilisateur) {
        this.modificationProfilsUtilisateur = modificationProfilsUtilisateur;
    }

    public boolean getParametrage() {
        return parametrage;
    }

    public void setParametrage(boolean parametrage) {
        this.parametrage = parametrage;
    }

    public boolean getReinitialisationUtilisateur() {
        return reinitialisationUtilisateur;
    }

    public void setReinitialisationUtilisateur(boolean reinitialisationUtilisateur) {
        this.reinitialisationUtilisateur = reinitialisationUtilisateur;
    }

    public boolean getCreationAgence() {
        return creationAgence;
    }

    public void setCreationAgence(boolean creationAgence) {
        this.creationAgence = creationAgence;
    }

    public boolean getSuppressionAgence() {
        return suppressionAgence;
    }

    public void setSuppressionAgence(boolean suppressionAgence) {
        this.suppressionAgence = suppressionAgence;
    }

    public boolean getSupervalidation() {
        return supervalidation;
    }

    public void setSupervalidation(boolean supervalidation) {
        this.supervalidation = supervalidation;
    }

    public boolean getReporting() {
        return reporting;
    }

    public void setReporting(boolean reporting) {
        this.reporting = reporting;
    }

    public boolean isTransactions() {
        return transactions;
    }

    public void setTransactions(boolean transactions) {
        this.transactions = transactions;
    }

    public boolean isAudits() {
        return audits;
    }

    public void setAudits(boolean audit) {
        this.audits = audit;
    }

    public boolean isCreationProfilsClients() {
        return creationProfilsClients;
    }

    public void setCreationProfilsClients(boolean creationProfilsClients) {
        this.creationProfilsClients = creationProfilsClients;
    }

    public boolean isSuppressionProfilsClients() {
        return suppressionProfilsClients;
    }

    public void setSuppressionProfilsClients(boolean suppressionProfilsClients) {
        this.suppressionProfilsClients = suppressionProfilsClients;
    }  

    public boolean isSecurite() {
        return securite;
    }

    public void setSecurite(boolean securite) {
        this.securite = securite;
    }

    public boolean isCreationCommissions() {
        return creationCommissions;
    }

    public void setCreationCommissions(boolean commissions) {
        this.creationCommissions = commissions;
    }

    public boolean isSuppressionCommissions() {
        return suppressionCommissions;
    }

    public void setSuppressionCommissions(boolean suppressionCommissions) {
        this.suppressionCommissions = suppressionCommissions;
    }

    public boolean isListAbonnements() {
        return listAbonnements;
    }

    public void setListAbonnements(boolean listAbonnements) {
        this.listAbonnements = listAbonnements;
    }

    public boolean isListCommissions() {
        return listCommissions;
    }

    public void setListCommissions(boolean listCommissions) {
        this.listCommissions = listCommissions;
    }

    public boolean isConnexion_ttes_age() {
        return connexion_ttes_age;
    }

    public void setConnexion_ttes_age(boolean connexion_ttes_age) {
        this.connexion_ttes_age = connexion_ttes_age;
    }
    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idprofils != null ? idprofils.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Profils)) {
            return false;
        }
        Profils other = (Profils) object;
        if ((this.idprofils == null && other.idprofils != null) || (this.idprofils != null && !this.idprofils.equals(other.idprofils))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sbs.easymbank.entities.Profils[ idprofils=" + idprofils + " ]";
    }

    @XmlTransient
    public List<Users> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
    }

    public boolean isTransac_ttes_age() {
        return transac_ttes_age;
    }

    public void setTransac_ttes_age(boolean transac_ttes_age) {
        this.transac_ttes_age = transac_ttes_age;
    }

    public boolean isAbonn_ttes_age() {
        return abonn_ttes_age;
    }

    public void setAbonn_ttes_age(boolean abonn_ttes_age) {
        this.abonn_ttes_age = abonn_ttes_age;
    }

    public boolean isValide_ttes_age() {
        return valide_ttes_age;
    }

    public void setValide_ttes_age(boolean valide_ttes_age) {
        this.valide_ttes_age = valide_ttes_age;
    }

    public boolean isAudit_ttes_age() {
        return audit_ttes_age;
    }

    public void setAudit_ttes_age(boolean audit_ttes_age) {
        this.audit_ttes_age = audit_ttes_age;
    }
    
    
}
