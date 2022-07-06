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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "HIST_PASSWORD")
@XmlRootElement
@Cacheable(false)
@NamedQueries({
    @NamedQuery(name = "HistPassword.findAll", query = "SELECT h FROM HistPassword h"),
    @NamedQuery(name = "HistPassword.findByIdHist", query = "SELECT h FROM HistPassword h WHERE h.idHist = :idHist"),
    @NamedQuery(name = "HistPassword.findByPassword", query = "SELECT h FROM HistPassword h WHERE h.password = :password"),
    @NamedQuery(name = "HistPassword.findByDatecreation", query = "SELECT h FROM HistPassword h WHERE h.datecreation = :datecreation"),
    @NamedQuery(name = "HistPassword.findUserPreviousPassword", query = "SELECT h FROM HistPassword h WHERE h.idUsers.login = :login ORDER BY h.datecreation DESC")})
public class HistPassword implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @TableGenerator(name="HISTPASSWORD_IDHISTPASSWORD_SEQ",table="SEQUENCE",pkColumnName = "SEQ_NAME",valueColumnName = "SEQ_COUNT",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "HISTPASSWORD_IDHISTPASSWORD_SEQ")    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_HIST")
    private Long idHist;
    @Size(max = 255)
    @Column(name = "PASSWORD")
    private String password;
    @Size(max = 255)
    @Column(name = "DATECREATION")
    private String datecreation;
    @JoinColumn(name = "ID_USERS", referencedColumnName = "IDUSERS")
    @ManyToOne
    private Users idUsers;

    public HistPassword() {
    }

    public HistPassword(Long idHist) {
        this.idHist = idHist;
    }

    public Long getIdHist() {
        return idHist;
    }

    public void setIdHist(Long idHist) {
        this.idHist = idHist;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatecreation() {
        return datecreation;
    }

    public void setDatecreation(String datecreation) {
        this.datecreation = datecreation;
    }

    public Users getIdUsers() {
        return idUsers;
    }

    public void setIdUsers(Users idUsers) {
        this.idUsers = idUsers;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHist != null ? idHist.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistPassword)) {
            return false;
        }
        HistPassword other = (HistPassword) object;
        if ((this.idHist == null && other.idHist != null) || (this.idHist != null && !this.idHist.equals(other.idHist))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sbs.easymbank.entities.HistPassword[ idHist=" + idHist + " ]";
    }
    
}
