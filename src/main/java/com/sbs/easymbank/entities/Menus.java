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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SOCITECH-
 */
@Entity
@Table(name = "menus")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Menus.findAll", query = "SELECT m FROM Menus m"),
    @NamedQuery(name = "Menus.findById", query = "SELECT m FROM Menus m WHERE m.id = :id"),
    @NamedQuery(name = "Menus.findByIdmenu", query = "SELECT m FROM Menus m WHERE m.idmenu = :idmenu"),
    @NamedQuery(name = "Menus.findByIdpere", query = "SELECT m FROM Menus m WHERE m.idpere = :idpere"),
    @NamedQuery(name = "Menus.findByPos", query = "SELECT m FROM Menus m WHERE m.pos = :pos"),
    @NamedQuery(name = "Menus.findByLibelle", query = "SELECT m FROM Menus m WHERE m.libelle = :libelle"),
    @NamedQuery(name = "Menus.findByHref", query = "SELECT m FROM Menus m WHERE m.href = :href"),
    @NamedQuery(name = "Menus.findByOnclick", query = "SELECT m FROM Menus m WHERE m.onclick = :onclick"),
    @NamedQuery(name = "Menus.findByActiver", query = "SELECT m FROM Menus m WHERE m.activer = :activer"),
    @NamedQuery(name = "Menus.findByFonctionformulaire", query = "SELECT m FROM Menus m WHERE m.fonctionformulaire = :fonctionformulaire"),
    @NamedQuery(name = "Menus.findByFonctionaffichage", query = "SELECT m FROM Menus m WHERE m.fonctionaffichage = :fonctionaffichage"),
    @NamedQuery(name = "Menus.findByUserenreg", query = "SELECT m FROM Menus m WHERE m.userenreg = :userenreg"),
    @NamedQuery(name = "Menus.findByUsermaj", query = "SELECT m FROM Menus m WHERE m.usermaj = :usermaj"),
    @NamedQuery(name = "Menus.findByDateenreg", query = "SELECT m FROM Menus m WHERE m.dateenreg = :dateenreg"),
    @NamedQuery(name = "Menus.findByDatemaj", query = "SELECT m FROM Menus m WHERE m.datemaj = :datemaj")})
public class Menus implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "idmenu")
    private Integer idmenu;
    @Column(name = "idpere")
    private Integer idpere;
    @Column(name = "pos")
    private Integer pos;
    @Size(max = 80)
    @Column(name = "libelle")
    private String libelle;
    @Size(max = 250)
    @Column(name = "href")
    private String href;
    @Size(max = 255)
    @Column(name = "onclick")
    private String onclick;
    @Size(max = 1)
    @Column(name = "activer")
    private String activer;
    @Size(max = 50)
    @Column(name = "fonctionformulaire")
    private String fonctionformulaire;
    @Size(max = 50)
    @Column(name = "fonctionaffichage")
    private String fonctionaffichage;
    @Size(max = 10)
    @Column(name = "userenreg")
    private String userenreg;
    @Size(max = 10)
    @Column(name = "usermaj")
    private String usermaj;
    @Size(max = 20)
    @Column(name = "dateenreg")
    private String dateenreg;
    @Size(max = 20)
    @Column(name = "datemaj")
    private String datemaj;

    public Menus() {
    }

    public Menus(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdmenu() {
        return idmenu;
    }

    public void setIdmenu(Integer idmenu) {
        this.idmenu = idmenu;
    }

    public Integer getIdpere() {
        return idpere;
    }

    public void setIdpere(Integer idpere) {
        this.idpere = idpere;
    }

    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getOnclick() {
        return onclick;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    public String getActiver() {
        return activer;
    }

    public void setActiver(String activer) {
        this.activer = activer;
    }

    public String getFonctionformulaire() {
        return fonctionformulaire;
    }

    public void setFonctionformulaire(String fonctionformulaire) {
        this.fonctionformulaire = fonctionformulaire;
    }

    public String getFonctionaffichage() {
        return fonctionaffichage;
    }

    public void setFonctionaffichage(String fonctionaffichage) {
        this.fonctionaffichage = fonctionaffichage;
    }

    public String getUserenreg() {
        return userenreg;
    }

    public void setUserenreg(String userenreg) {
        this.userenreg = userenreg;
    }

    public String getUsermaj() {
        return usermaj;
    }

    public void setUsermaj(String usermaj) {
        this.usermaj = usermaj;
    }

    public String getDateenreg() {
        return dateenreg;
    }

    public void setDateenreg(String dateenreg) {
        this.dateenreg = dateenreg;
    }

    public String getDatemaj() {
        return datemaj;
    }

    public void setDatemaj(String datemaj) {
        this.datemaj = datemaj;
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
        if (!(object instanceof Menus)) {
            return false;
        }
        Menus other = (Menus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sbs.easymbank.entities.Menus[ id=" + id + " ]";
    }
    
}
