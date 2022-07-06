/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.entities;

import com.sbs.easymbank.filters.UtilisateurFilter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SOCITECH-
 */
@Entity
@Table(name = "connexion")
@XmlRootElement
@Cacheable(false)
@NamedQueries({
    @NamedQuery(name = "Connexion.findAll", query = "SELECT c FROM Connexion c"),
    @NamedQuery(name = "Connexion.findById", query = "SELECT c FROM Connexion c WHERE c.id = :id"),
    @NamedQuery(name = "Connexion.findByLogin", query = "SELECT c FROM Connexion c WHERE c.login = :login and c.dateFin IS NOT NULL and c.heureFin IS NOT NULL"),
    @NamedQuery(name = "Connexion.findByDateDebut", query = "SELECT c FROM Connexion c WHERE c.dateDebut = :dateDebut"),
    @NamedQuery(name = "Connexion.findByHeureDebut", query = "SELECT c FROM Connexion c WHERE c.heureDebut = :heureDebut"),
    @NamedQuery(name = "Connexion.findByDateFin", query = "SELECT c FROM Connexion c WHERE c.dateFin = :dateFin"),
    @NamedQuery(name = "Connexion.findByHeureFin", query = "SELECT c FROM Connexion c WHERE c.heureFin = :heureFin" )})
public class Connexion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "id")
    private String id;
    @Size(max = 100)
    @Column(name = "login")
    private String login;
    @Size(max = 20)
    @Column(name = "date_debut")
    private String dateDebut;
    @Size(max = 20)
    @Column(name = "heure_debut")
    private String heureDebut;
    @Size(max = 20)
    @Column(name = "date_fin")
    private String dateFin;
    @Size(max = 20)
    @Column(name = "heure_fin")
    private String heureFin;
    @Transient
    private String creationTime;
    @Transient
    private String nom;
    @Transient
    private String agence;

    public Connexion() {
    }

    public Connexion(String id) {
        this.id = id;
    }

    public Connexion(HttpSession httpSession, Users users){
        this.id = httpSession.getId();
        this.nom = users.getPrenom() + " " + users.getNom();
        this.agence = users.getIdagences().getLibelle();
        this.creationTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(httpSession.getCreationTime()));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(String heureDebut) {
        this.heureDebut = heureDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(String heureFin) {
        this.heureFin = heureFin;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAgence() {
        return agence;
    }

    public void setAgence(String agence) {
        this.agence = agence;
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
        if (!(object instanceof Connexion)) {
            return false;
        }
        Connexion other = (Connexion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sbs.easymbank.entities.Connexion[ id=" + id + " ]";
    }
    
}
