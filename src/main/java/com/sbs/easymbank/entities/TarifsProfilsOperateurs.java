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
@Table(name = "TARIFS_PROFILS_OPERATEURS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TarifsProfilsOperateurs.findAll", query = "SELECT t FROM TarifsProfilsOperateurs t"),
    @NamedQuery(name = "TarifsProfilsOperateurs.findByIdTarifs", query = "SELECT t FROM TarifsProfilsOperateurs t WHERE t.idTarifs = :idTarifs"),
    @NamedQuery(name = "TarifsProfilsOperateurs.findTarifRedondant", query = "SELECT t FROM TarifsProfilsOperateurs t WHERE t.operateurs.idOperateur = :operateur and t.profils.idProfils = :profil and t.service = :service"),
    @NamedQuery(name = "TarifsProfilsOperateurs.findTarifBecomeRedondant", query = "SELECT t FROM TarifsProfilsOperateurs t WHERE t.operateurs.idOperateur = :operateur and t.profils.idProfils = :profil and t.service = :service and t.idTarifs <> :idtarif")})
   public class TarifsProfilsOperateurs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @TableGenerator(name="TARIFPROFILOPERATEURS_IDTARIFS_SEQ",table="SEQUENCE",pkColumnName = "SEQ_NAME",valueColumnName = "SEQ_COUNT",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "TARIFPROFILOPERATEURS_IDTARIFS_SEQ")

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_TARIFS")
    private Short idTarifs;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TARIF")
    private long tarif;
    @Size(max = 15)
    @Column(name = "SERVICE")
    private String service;
    @JoinColumn(name = "PROFILS", referencedColumnName = "ID_PROFILS")
    @ManyToOne(optional = false)
    private ProfilsClients profils;
    @JoinColumn(name = "OPERATEURS", referencedColumnName = "ID_OPERATEUR")
    @ManyToOne(optional = false)
    private Operateurs operateurs;

    public TarifsProfilsOperateurs() {
    }

    public TarifsProfilsOperateurs(Short idTarifs) {
        this.idTarifs = idTarifs;
    }

  
    public Short getIdTarifs() {
        return idTarifs;
    }

    public void setIdTarifs(Short idTarifs) {
        this.idTarifs = idTarifs;
    }

    public long getTarif() {
        return tarif;
    }

    public void setTarif(long tarif) {
        this.tarif = tarif;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

  

    public ProfilsClients getProfils() {
        return profils;
    }

    public void setProfils(ProfilsClients profils) {
        this.profils = profils;
    }

    public Operateurs getOperateurs() {
        return operateurs;
    }

    public void setOperateurs(Operateurs operateurs) {
        this.operateurs = operateurs;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTarifs != null ? idTarifs.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TarifsProfilsOperateurs)) {
            return false;
        }
        TarifsProfilsOperateurs other = (TarifsProfilsOperateurs) object;
        if ((this.idTarifs == null && other.idTarifs != null) || (this.idTarifs != null && !this.idTarifs.equals(other.idTarifs))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return  idTarifs.toString();
    }
    
}
