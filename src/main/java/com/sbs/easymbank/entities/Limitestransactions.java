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
 * @author samuel
 */
@Entity
@Table(name = "LIMITESTRANSACTIONS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Limitestransactions.findAll", query = "SELECT l FROM Limitestransactions l")
    , @NamedQuery(name = "Limitestransactions.findById", query = "SELECT l FROM Limitestransactions l WHERE l.id = :id")
    , @NamedQuery(name = "Limitestransactions.findByDesignation", query = "SELECT l FROM Limitestransactions l WHERE l.designation = :designation")
    , @NamedQuery(name = "Limitestransactions.findByValeur", query = "SELECT l FROM Limitestransactions l WHERE l.valeur = :valeur")
    , @NamedQuery(name = "Limitestransactions.findByProfilclient", query = "SELECT l FROM Limitestransactions l WHERE l.profilclient = :profilclient")})
public class Limitestransactions implements Serializable {
    
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @TableGenerator(name = "LIMITESTRANSACTIONS_SEQ", table = "SEQUENCE", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "LIMITESTRANSACTIONS_SEQ")
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 250)
    @Column(name = "DESIGNATION")
    private String designation;
    @Size(max = 100)
    @Column(name = "VALEUR")
    private String valeur;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "PROFILCLIENT")
    private String profilclient;
    @Column(name = "TYPETRANSAC")
    private String typeTransaction;
    @Column(name = "KEYCOLUMN")
    private String keyColumn;
    @Column(name = "TYPELIMITE")
    private String typelimite;
    

    public Limitestransactions() {
    }

    public Limitestransactions(BigDecimal id) {
        this.id = id;
    }

    public Limitestransactions(BigDecimal id, String profilclient) {
        this.id = id;
        this.profilclient = profilclient;
    }

    public String getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(String typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public String getKeyColumn() {
        return keyColumn;
    }

    public void setKeyColumn(String keyColumn) {
        this.keyColumn = keyColumn;
    }
   

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public String getProfilclient() {
        return profilclient;
    }

    public void setProfilclient(String profilclient) {
        this.profilclient = profilclient;
    }

    public String getTypelimite() {
        return typelimite;
    }

    public void setTypelimite(String typelimite) {
        this.typelimite = typelimite;
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
        if (!(object instanceof Limitestransactions)) {
            return false;
        }
        Limitestransactions other = (Limitestransactions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sbs.easymbank.entities.Limitestransactions[ id=" + id + " ]";
    }
    
}
