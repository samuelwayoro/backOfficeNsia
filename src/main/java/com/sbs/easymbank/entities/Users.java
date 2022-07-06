/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.entities;

import com.sbs.easymbank.dao.LogsFacade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
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
import javax.persistence.PostPersist;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SOCITECH-
 */
@Entity
@Table(name = "users")
@XmlRootElement
@Cacheable(false)
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.findByIdusers", query = "SELECT u FROM Users u WHERE u.idusers = :idusers"),
    @NamedQuery(name = "Users.findByDatecreation", query = "SELECT u FROM Users u WHERE u.datecreation = :datecreation"),
    @NamedQuery(name = "Users.findByDatemodification", query = "SELECT u FROM Users u WHERE u.datemodification = :datemodification"),
    @NamedQuery(name = "Users.findByDatesuppression", query = "SELECT u FROM Users u WHERE u.datesuppression = :datesuppression"),
    @NamedQuery(name = "Users.findByLogin", query = "SELECT u FROM Users u WHERE u.login = :login"),
    @NamedQuery(name = "Users.findByNom", query = "SELECT u FROM Users u WHERE u.nom = :nom"),
    @NamedQuery(name = "Users.findByPrenom", query = "SELECT u FROM Users u WHERE u.prenom = :prenom"),
    @NamedQuery(name = "Users.findByPassword", query = "SELECT u FROM Users u WHERE u.password = :password"),
    @NamedQuery(name = "Users.findByReinitialise", query = "SELECT u FROM Users u WHERE u.reinitialise = :reinitialise"),
    @NamedQuery(name = "Users.findByActiver", query = "SELECT u FROM Users u WHERE u.activer = :activer"),
    @NamedQuery(name = "Users.findByEtatconnexion", query = "SELECT u FROM Users u WHERE u.etatconnexion = :etatconnexion")})
public class Users implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
     @TableGenerator(name="USERS_IDUSERS_SEQ",table="SEQUENCE",pkColumnName = "SEQ_NAME",valueColumnName = "SEQ_COUNT",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "USERS_IDUSERS_SEQ")
    @Basic(optional = false)
    @Column(name = "idusers")
    private Integer idusers;
    @Size(max = 255)
    @Column(name = "datecreation")
    private String datecreation;
    @Size(max = 255)
    @Column(name = "datemodification")
    private String datemodification;
    @Size(max = 255)
    @Column(name = "datesuppression")
    private String datesuppression;
    @Size(max = 255)
    @Column(name = "dateactivation")
    private String dateactivation;
    @Size(max = 255)
    @Column(name = "datedesactivation")
    private String datedesactivation;
    @Size(max = 255)
    @Column(name = "datereinitialisation")
    private String datereinitialisation;
    @Size(max = 255)
    @Column(name = "login")
    private String login;
    @Size(max = 255)
    @Column(name = "nom")
    private String nom;
    @Size(max = 255)
    @Column(name = "prenom")
    private String prenom;
    @Size(max = 255)
    @Column(name = "password")
    private String password;
    @Column(name = "reinitialise")
    private Boolean reinitialise;
    @Column(name = "activer")
    private boolean activer;
    @Size(max = 255)
    @Column(name = "etatconnexion")
    private String etatconnexion;
    @JoinColumn(name = "idprofils", referencedColumnName = "idprofils")
    @ManyToOne
    private Profils idprofils;
    @JoinColumn(name = "idagences", referencedColumnName = "idagences")
    @ManyToOne
    private Agences idagences;
    @OneToMany(mappedBy = "users")
    private List<Logs> logsList;

    

    public Users() {
    }

    public Users(Integer idusers) {
        this.idusers = idusers;
    }

    public Integer getIdusers() {
        return idusers;
    }

    public void setIdusers(Integer idusers) {
        this.idusers = idusers;
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

    public String getDatesuppression() {
        return datesuppression;
    }

    public void setDatesuppression(String datesuppression) {
        this.datesuppression = datesuppression;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getReinitialise() {
        return reinitialise;
    }

    public void setReinitialise(Boolean reinitialise) {
        this.reinitialise = reinitialise;
    }

    public boolean getActiver() {
        return activer;
    }

    public void setActiver(boolean activer) {
        this.activer = activer;
    }

    public String getEtatconnexion() {
        return etatconnexion;
    }

    public void setEtatconnexion(String etatconnexion) {
        this.etatconnexion = etatconnexion;
    }

    public Profils getIdprofils() {
        return idprofils;
    }

    public void setIdprofils(Profils idprofils) {
        this.idprofils = idprofils;
    }

    public Agences getIdagences() {
        return idagences;
    }

    public void setIdagences(Agences idagences) {
        this.idagences = idagences;
    }

    public String getDateactivation() {
        return dateactivation;
    }

    public void setDateactivation(String dateactivation) {
        this.dateactivation = dateactivation;
    }

    public String getDatedesactivation() {
        return datedesactivation;
    }

    public void setDatedesactivation(String datedesactivation) {
        this.datedesactivation = datedesactivation;
    }

    public String getDatereinitialisation() {
        return datereinitialisation;
    }

    public void setDatereinitialisation(String datereinitialisation) {
        this.datereinitialisation = datereinitialisation;
    }


    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idusers != null ? idusers.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.idusers == null && other.idusers != null) || (this.idusers != null && !this.idusers.equals(other.idusers))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sbs.easymbank.entities.Users[ idusers=" + idusers + " ]";
    }
    
   
    
}
