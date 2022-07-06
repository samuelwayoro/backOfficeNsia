/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.entities;

import java.io.Serializable;
import java.math.BigInteger;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alex
 */
@Entity
@Table(name = "TRANSACTIONS")
@XmlRootElement
@Cacheable(false)
@NamedQueries({
    @NamedQuery(name = "Transactions.findAll", query = "SELECT t FROM Transactions t order by t.trandate desc"),
    @NamedQuery(name = "Transactions.findGreaterThanIdtransactions", query = "SELECT t FROM Transactions t WHERE t.idtransactions > :idtransactions order by t.trandate DESC"),
    @NamedQuery(name = "Transactions.findNotCleared", query = "SELECT t FROM Transactions t WHERE t.origine = :origine and t.reconcilie = :reconcilie"),
    @NamedQuery(name= "Transactions.findTransactionsByPeriod",query = "SELECT t FROM Transactions t WHERE SUBSTRING(t.trandate,1,10) BETWEEN :borneInf AND :borneSup AND t.requesttype IN :listType AND t.cbareferenceno NOT IN (SELECT tr.externalrefno FROM Transactions tr WHERE tr.responsecode = '000' AND tr.requesttype = 'canceltran') AND t.operateurs.designationOperateur LIKE :operateurs AND t.accountalias IN (SELECT a.alias FROM Abonnements a WHERE a.numerotelephone LIKE :phone AND a.compte LIKE :compte AND a.racine LIKE :racine) ORDER BY t.trandate desc")})
public class Transactions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
   @TableGenerator(name="TRANSACTIONS_SEQ",table="SEQUENCE",pkColumnName = "SEQ_NAME",valueColumnName = "SEQ_COUNT",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "TRANSACTIONS_SEQ")

    @Basic(optional = false)
    @NotNull
    @Column(name = "IDTRANSACTIONS")
    private Integer idtransactions;
    @Size(max = 255)
    @Column(name = "ACCOUNTALIAS")
    private String accountalias;
    @Size(max = 255)
    @Column(name = "ACCOUNTCLASS")
    private String accountclass;
    @Size(max = 255)
    @Column(name = "ACCOUNTNAME")
    private String accountname;
    @Size(max = 255)
    @Column(name = "ACCOUNTNO")
    private String accountno;
    @Size(max = 255)
    @Column(name = "ACCOUNTSTATUS")
    private String accountstatus;
    @Size(max = 255)
    @Column(name = "ACCOUNTTYPE")
    private String accounttype;
    @Size(max = 255)
    @Column(name = "AFFILIATECODE")
    private String affiliatecode;
    @Size(max = 255)
    @Column(name = "AMOUNT")
    private String amount;
    @Size(max = 255)
    @Column(name = "AVAILABLEBALANCE")
    private String availablebalance;
    @Size(max = 255)
    @Column(name = "BRANCHCODE")
    private String branchcode;
    @Size(max = 255)
    @Column(name = "CBAREFERENCENO")
    private String cbareferenceno;
    @Size(max = 255)
    @Column(name = "CCY")
    private String ccy;
    @Size(max = 255)
    @Column(name = "CHARGE")
    private String charge;
    @Size(max = 255)
    @Column(name = "CRDR")
    private String crdr;
    @Size(max = 255)
    @Column(name = "CURRENTBALANCE")
    private String currentbalance;
    @Size(max = 255)
    @Column(name = "EXTERNALREFNO")
    private String externalrefno;
    @Size(max = 255)
    @Column(name = "MOBILEALIAS")
    private String mobilealias;
    @Size(max = 255)
    @Column(name = "MOBILENAME")
    private String mobilename;
    @Size(max = 255)
    @Column(name = "MOBILENO")
    private String mobileno;
    @Size(max = 255)
    @Column(name = "NARRATION")
    private String narration;
    @Size(max = 255)
    @Column(name = "OPERATORCODE")
    private String operatorcode;
    @Size(max = 255)
    @Column(name = "ORIGINE")
    private String origine;
    @Size(max = 255)
    @Column(name = "REASON")
    private String reason;
    @Column(name = "RECONCILIE")
    private Boolean reconcilie;
    @Size(max = 255)
    @Column(name = "REQUESTID")
    private String requestid;
    @Size(max = 255)
    @Column(name = "REQUESTTOKEN")
    private String requesttoken;
    @Size(max = 255)
    @Column(name = "REQUESTTYPE")
    private String requesttype;
    @Size(max = 255)
    @Column(name = "RESPONSECODE")
    private String responsecode;
    @Size(max = 255)
    @Column(name = "RESPONSEMESSAGE")
    private String responsemessage;
    @Size(max = 255)
    @Column(name = "TRANDATE")
    private String trandate;
    @Size(max = 255)
    @Column(name = "TRANREFNO")
    private String tranrefno;
    @Size(max = 255)
    @Column(name = "TRANSFERDESCRIPTION")
    private String transferdescription;
    @Size(max = 255)
    @Column(name = "TRANTYPE")
    private String trantype;
    @Size(max = 255)
    @Column(name = "UDF1")
    private String udf1;
    @Size(max = 255)
    @Column(name = "UDF2")
    private String udf2;
    @Size(max = 255)
    @Column(name = "UDF3")
    private String udf3;
    @Column(name = "COMMISSION")
    private BigInteger commission;
    @JoinColumn(name = "OPERATEURS", referencedColumnName = "ID_OPERATEUR")
    @ManyToOne
    private Operateurs operateurs;
    @Transient
    private Abonnements abonnements;
    @Transient
    private String designationOperateur;
    @Transient
    private String agence;
    public Transactions() {
    }

    public Transactions(Integer idtransactions) {
        this.idtransactions = idtransactions;
    }

    public Integer getIdtransactions() {
        return idtransactions;
    }

    public void setIdtransactions(Integer idtransactions) {
        this.idtransactions = idtransactions;
    }

    public String getAccountalias() {
        return accountalias;
    }

    public void setAccountalias(String accountalias) {
        this.accountalias = accountalias;
    }

    public String getAccountclass() {
        return accountclass;
    }

    public void setAccountclass(String accountclass) {
        this.accountclass = accountclass;
    }

    public String getAccountname() {
        return accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public String getAccountstatus() {
        return accountstatus;
    }

    public void setAccountstatus(String accountstatus) {
        this.accountstatus = accountstatus;
    }

    public String getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype;
    }

    public String getAffiliatecode() {
        return affiliatecode;
    }

    public void setAffiliatecode(String affiliatecode) {
        this.affiliatecode = affiliatecode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAvailablebalance() {
        return availablebalance;
    }

    public void setAvailablebalance(String availablebalance) {
        this.availablebalance = availablebalance;
    }

    public String getBranchcode() {
        return branchcode;
    }

    public void setBranchcode(String branchcode) {
        this.branchcode = branchcode;
    }

    public String getCbareferenceno() {
        return cbareferenceno;
    }

    public void setCbareferenceno(String cbareferenceno) {
        this.cbareferenceno = cbareferenceno;
    }

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getCrdr() {
        return crdr;
    }

    public void setCrdr(String crdr) {
        this.crdr = crdr;
    }

    public String getCurrentbalance() {
        return currentbalance;
    }

    public void setCurrentbalance(String currentbalance) {
        this.currentbalance = currentbalance;
    }

    public String getExternalrefno() {
        return externalrefno;
    }

    public void setExternalrefno(String externalrefno) {
        this.externalrefno = externalrefno;
    }

    public String getMobilealias() {
        return mobilealias;
    }

    public void setMobilealias(String mobilealias) {
        this.mobilealias = mobilealias;
    }

    public String getMobilename() {
        return mobilename;
    }

    public void setMobilename(String mobilename) {
        this.mobilename = mobilename;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getOperatorcode() {
        return operatorcode;
    }

    public void setOperatorcode(String operatorcode) {
        this.operatorcode = operatorcode;
    }

    public String getOrigine() {
        return origine;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Boolean getReconcilie() {
        return reconcilie;
    }

    public void setReconcilie(Boolean reconcilie) {
        this.reconcilie = reconcilie;
    }

    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }

    public String getRequesttoken() {
        return requesttoken;
    }

    public void setRequesttoken(String requesttoken) {
        this.requesttoken = requesttoken;
    }

    public String getRequesttype() {
        return requesttype;
    }

    public void setRequesttype(String requesttype) {
        this.requesttype = requesttype;
    }

    public String getResponsecode() {
        return responsecode;
    }

    public void setResponsecode(String responsecode) {
        this.responsecode = responsecode;
    }

    public String getResponsemessage() {
        return responsemessage;
    }

    public void setResponsemessage(String responsemessage) {
        this.responsemessage = responsemessage;
    }

    public String getTrandate() {
        return trandate;
    }

    public void setTrandate(String trandate) {
        this.trandate = trandate;
    }

    public String getTranrefno() {
        return tranrefno;
    }

    public void setTranrefno(String tranrefno) {
        this.tranrefno = tranrefno;
    }

    public String getTransferdescription() {
        return transferdescription;
    }

    public void setTransferdescription(String transferdescription) {
        this.transferdescription = transferdescription;
    }

    public String getTrantype() {
        return trantype;
    }

    public void setTrantype(String trantype) {
        this.trantype = trantype;
    }

    public String getUdf1() {
        return udf1;
    }

    public void setUdf1(String udf1) {
        this.udf1 = udf1;
    }

    public String getUdf2() {
        return udf2;
    }

    public void setUdf2(String udf2) {
        this.udf2 = udf2;
    }

    public String getUdf3() {
        return udf3;
    }

    public void setUdf3(String udf3) {
        this.udf3 = udf3;
    }

    public BigInteger getCommission() {
        return commission;
    }

    public void setCommission(BigInteger commission) {
        this.commission = commission;
    }

    public Operateurs getOperateurs() {
        return operateurs;
    }

    public void setOperateurs(Operateurs operateurs) {
        this.operateurs = operateurs;
    }

    public String getDesignationOperateur() {
        return designationOperateur;
    }

    public void setDesignationOperateur(String designationOperateur) {
        this.designationOperateur = designationOperateur;
    }

    public String getAgence() {
        return agence;
    }

    public void setAgence(String agence) {
        this.agence = agence;
    }

    public Abonnements getAbonnements() {
        return abonnements;
    }

    public void setAbonnements(Abonnements abonnements) {
        this.abonnements = abonnements;
    }

    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtransactions != null ? idtransactions.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transactions)) {
            return false;
        }
        Transactions other = (Transactions) object;
        if ((this.idtransactions == null && other.idtransactions != null) || (this.idtransactions != null && !this.idtransactions.equals(other.idtransactions))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sbs.easymbank.entities.Transactions[ idtransactions=" + idtransactions + " ]";
    }
    
}
