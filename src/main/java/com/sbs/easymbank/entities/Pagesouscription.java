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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "pagesouscription")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pagesouscription.findAll", query = "SELECT p FROM Pagesouscription p"),
    @NamedQuery(name = "Pagesouscription.findByMode", query = "SELECT p FROM Pagesouscription p WHERE p.mode = :mode"),
    @NamedQuery(name = "Pagesouscription.findByPage", query = "SELECT p FROM Pagesouscription p WHERE p.page = :page"),
    @NamedQuery(name = "Pagesouscription.findByOperateur", query = "SELECT p FROM Pagesouscription p WHERE p.operateur.designationOperateur = :operateur OR p.operateur IS NULL")})
public class Pagesouscription implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name="id")
    private Integer id;
    @Size(min = 1, max = 20)
    @Column(name = "modesousc")
    private String mode;
    @Size(max = 40)
    @Column(name = "page")
    private String page;
    @JoinColumn(name = "OPERATEUR", referencedColumnName = "ID_OPERATEUR")
    @ManyToOne
    private Operateurs operateur;


    public Pagesouscription() {
    }

    public Pagesouscription(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Operateurs getOperateur() {
        return operateur;
    }

    public void setOperateur(Operateurs operateur) {
        this.operateur = operateur;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mode != null ? mode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pagesouscription)) {
            return false;
        }
        Pagesouscription other = (Pagesouscription) object;
        if ((this.mode == null && other.mode != null) || (this.mode != null && !this.mode.equals(other.mode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sbs.easymbank.entities.Pagesouscription[ mode=" + mode + " ]";
    }
    
}
