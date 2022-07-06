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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SOCITECH-
 */
@Entity
@Table(name = "transactions_reconciliations")
@XmlRootElement
@Cacheable(false)
@NamedQueries({
    @NamedQuery(name = "TransactionsReconciliations.findAll", query = "SELECT t FROM TransactionsReconciliations t"),
    @NamedQuery(name = "TransactionsReconciliations.findByIdtransactions", query = "SELECT t FROM TransactionsReconciliations t WHERE t.idtransactions = :idtransactions"),
    @NamedQuery(name = "TransactionsReconciliations.findByAccountalias", query = "SELECT t FROM TransactionsReconciliations t WHERE t.accountalias = :accountalias"),
    @NamedQuery(name = "TransactionsReconciliations.findByAccountclass", query = "SELECT t FROM TransactionsReconciliations t WHERE t.accountclass = :accountclass"),
    @NamedQuery(name = "TransactionsReconciliations.findByAccountname", query = "SELECT t FROM TransactionsReconciliations t WHERE t.accountname = :accountname"),
    @NamedQuery(name = "TransactionsReconciliations.findByAccountno", query = "SELECT t FROM TransactionsReconciliations t WHERE t.accountno = :accountno"),
    @NamedQuery(name = "TransactionsReconciliations.findByAccountstatus", query = "SELECT t FROM TransactionsReconciliations t WHERE t.accountstatus = :accountstatus"),
    @NamedQuery(name = "TransactionsReconciliations.findByAccounttype", query = "SELECT t FROM TransactionsReconciliations t WHERE t.accounttype = :accounttype"),
    @NamedQuery(name = "TransactionsReconciliations.findByAffiliatecode", query = "SELECT t FROM TransactionsReconciliations t WHERE t.affiliatecode = :affiliatecode"),
    @NamedQuery(name = "TransactionsReconciliations.findByAmount", query = "SELECT t FROM TransactionsReconciliations t WHERE t.amount = :amount"),
    @NamedQuery(name = "TransactionsReconciliations.findByAvailablebalance", query = "SELECT t FROM TransactionsReconciliations t WHERE t.availablebalance = :availablebalance"),
    @NamedQuery(name = "TransactionsReconciliations.findByBranchcode", query = "SELECT t FROM TransactionsReconciliations t WHERE t.branchcode = :branchcode"),
    @NamedQuery(name = "TransactionsReconciliations.findByCbareferenceno", query = "SELECT t FROM TransactionsReconciliations t WHERE t.cbareferenceno = :cbareferenceno"),
    @NamedQuery(name = "TransactionsReconciliations.findByCcy", query = "SELECT t FROM TransactionsReconciliations t WHERE t.ccy = :ccy"),
    @NamedQuery(name = "TransactionsReconciliations.findByCharge", query = "SELECT t FROM TransactionsReconciliations t WHERE t.charge = :charge"),
    @NamedQuery(name = "TransactionsReconciliations.findByCrdr", query = "SELECT t FROM TransactionsReconciliations t WHERE t.crdr = :crdr"),
    @NamedQuery(name = "TransactionsReconciliations.findByCurrentbalance", query = "SELECT t FROM TransactionsReconciliations t WHERE t.currentbalance = :currentbalance"),
    @NamedQuery(name = "TransactionsReconciliations.findByExternalrefno", query = "SELECT t FROM TransactionsReconciliations t WHERE t.externalrefno = :externalrefno"),
    @NamedQuery(name = "TransactionsReconciliations.findByMobilealias", query = "SELECT t FROM TransactionsReconciliations t WHERE t.mobilealias = :mobilealias"),
    @NamedQuery(name = "TransactionsReconciliations.findByMobilename", query = "SELECT t FROM TransactionsReconciliations t WHERE t.mobilename = :mobilename"),
    @NamedQuery(name = "TransactionsReconciliations.findByMobileno", query = "SELECT t FROM TransactionsReconciliations t WHERE t.mobileno = :mobileno"),
    @NamedQuery(name = "TransactionsReconciliations.findByNarration", query = "SELECT t FROM TransactionsReconciliations t WHERE t.narration = :narration"),
    @NamedQuery(name = "TransactionsReconciliations.findByOperatorcode", query = "SELECT t FROM TransactionsReconciliations t WHERE t.operatorcode = :operatorcode"),
    @NamedQuery(name = "TransactionsReconciliations.findByReason", query = "SELECT t FROM TransactionsReconciliations t WHERE t.reason = :reason"),
    @NamedQuery(name = "TransactionsReconciliations.findByRequestid", query = "SELECT t FROM TransactionsReconciliations t WHERE t.requestid = :requestid"),
    @NamedQuery(name = "TransactionsReconciliations.findByRequesttoken", query = "SELECT t FROM TransactionsReconciliations t WHERE t.requesttoken = :requesttoken"),
    @NamedQuery(name = "TransactionsReconciliations.findByRequesttype", query = "SELECT t FROM TransactionsReconciliations t WHERE t.requesttype = :requesttype"),
    @NamedQuery(name = "TransactionsReconciliations.findByResponsecode", query = "SELECT t FROM TransactionsReconciliations t WHERE t.responsecode = :responsecode"),
    @NamedQuery(name = "TransactionsReconciliations.findByResponsemessage", query = "SELECT t FROM TransactionsReconciliations t WHERE t.responsemessage = :responsemessage"),
    @NamedQuery(name = "TransactionsReconciliations.findByTrandate", query = "SELECT t FROM TransactionsReconciliations t WHERE t.trandate = :trandate"),
    @NamedQuery(name = "TransactionsReconciliations.findByTranrefno", query = "SELECT t FROM TransactionsReconciliations t WHERE t.tranrefno = :tranrefno"),
    @NamedQuery(name = "TransactionsReconciliations.findByTrantype", query = "SELECT t FROM TransactionsReconciliations t WHERE t.trantype = :trantype"),
    @NamedQuery(name = "TransactionsReconciliations.findByTransferdescription", query = "SELECT t FROM TransactionsReconciliations t WHERE t.transferdescription = :transferdescription"),
    @NamedQuery(name = "TransactionsReconciliations.findByUdf1", query = "SELECT t FROM TransactionsReconciliations t WHERE t.udf1 = :udf1"),
    @NamedQuery(name = "TransactionsReconciliations.findByUdf2", query = "SELECT t FROM TransactionsReconciliations t WHERE t.udf2 = :udf2"),
    @NamedQuery(name = "TransactionsReconciliations.findByUdf3", query = "SELECT t FROM TransactionsReconciliations t WHERE t.udf3 = :udf3"),
    @NamedQuery(name = "TransactionsReconciliations.findByReconcilie", query = "SELECT t FROM TransactionsReconciliations t WHERE t.reconcilie = :reconcilie"),
    @NamedQuery(name = "TransactionsReconciliations.findByOrigine", query = "SELECT t FROM TransactionsReconciliations t WHERE t.origine = :origine"),
    @NamedQuery(name= "TransactionsReconciliations.findTransactionsByPeriod",query = "SELECT t FROM TransactionsReconciliations t WHERE SUBSTRING(t.trandate,1,10) BETWEEN :borneInf AND :borneSup AND t.responsecode LIKE :status"),
    @NamedQuery(name= "TransactionsReconciliations.findOnlyClearedAndSuccessfullByPeriod",query = "SELECT t FROM TransactionsReconciliations t WHERE SUBSTRING(t.trandate,1,10) BETWEEN :borneInf AND :borneSup AND t.responsecode='000'")

   /* @NamedQuery(name = "TransactionsReconciliations.findCleared", query = "SELECT rec FROM ((SELECT tr FROM TransactionsReconciliations tr) UNION (SELECT t FROM Transactions t WHERE t.origine = :origine)) rec ORDER BY rec.trandate desc  ")*/})
public class TransactionsReconciliations implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @TableGenerator(name="TRANSACTIONS_REC_SEQ",table="SEQUENCE",pkColumnName = "SEQ_NAME",valueColumnName = "SEQ_COUNT",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "TRANSACTIONS_REC_SEQ")
    @Basic(optional = false)
    
    @Column(name = "idtransactions")
    private Integer idtransactions;
    @Size(max = 255)
    @Column(name = "accountalias")
    private String accountalias;
    @Size(max = 255)
    @Column(name = "accountclass")
    private String accountclass;
    @Size(max = 255)
    @Column(name = "accountname")
    private String accountname;
    @Size(max = 255)
    @Column(name = "accountno")
    private String accountno;
    @Size(max = 255)
    @Column(name = "accountstatus")
    private String accountstatus;
    @Size(max = 255)
    @Column(name = "accounttype")
    private String accounttype;
    @Size(max = 255)
    @Column(name = "affiliatecode")
    private String affiliatecode;
    @Size(max = 255)
    @Column(name = "amount")
    private String amount;
    @Size(max = 255)
    @Column(name = "availablebalance")
    private String availablebalance;
    @Size(max = 255)
    @Column(name = "branchcode")
    private String branchcode;
    @Size(max = 255)
    @Column(name = "cbareferenceno")
    private String cbareferenceno;
    @Size(max = 255)
    @Column(name = "ccy")
    private String ccy;
    @Size(max = 255)
    @Column(name = "charge")
    private String charge;
    @Size(max = 255)
    @Column(name = "crdr")
    private String crdr;
    @Size(max = 255)
    @Column(name = "currentbalance")
    private String currentbalance;
    @Size(max = 255)
    @Column(name = "externalrefno")
    private String externalrefno;
    @Size(max = 255)
    @Column(name = "mobilealias")
    private String mobilealias;
    @Size(max = 255)
    @Column(name = "mobilename")
    private String mobilename;
    @Size(max = 255)
    @Column(name = "mobileno")
    private String mobileno;
    @Size(max = 255)
    @Column(name = "narration")
    private String narration;
    @Size(max = 255)
    @Column(name = "operatorcode")
    private String operatorcode;
    @Size(max = 255)
    @Column(name = "reason")
    private String reason;
    @Size(max = 255)
    @Column(name = "requestid")
    private String requestid;
    @Size(max = 255)
    @Column(name = "requesttoken")
    private String requesttoken;
    @Size(max = 255)
    @Column(name = "requesttype")
    private String requesttype;
    @Size(max = 255)
    @Column(name = "responsecode")
    private String responsecode;
    @Size(max = 255)
    @Column(name = "responsemessage")
    private String responsemessage;
    @Size(max = 255)
    @Column(name = "trandate")
    private String trandate;
    @Size(max = 255)
    @Column(name = "tranrefno")
    private String tranrefno;
    @Size(max = 255)
    @Column(name = "trantype")
    private String trantype;
    @Size(max = 255)
    @Column(name = "transferdescription")
    private String transferdescription;
    @Size(max = 255)
    @Column(name = "udf1")
    private String udf1;
    @Size(max = 255)
    @Column(name = "udf2")
    private String udf2;
    @Size(max = 255)
    @Column(name = "udf3")
    private String udf3;
    @Column(name = "reconcilie")
    private Boolean reconcilie;
    @Column(name = "origine")
    private String origine;
    @Column(name = "COMMISSION")
    private BigInteger commission;
    @Column(name = "OPERATEURS")
    private Integer operateurs;
    

    public TransactionsReconciliations() {
    }
    
     

    public TransactionsReconciliations(Integer idtransactions) {
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public String getTrantype() {
        return trantype;
    }

    public void setTrantype(String trantype) {
        this.trantype = trantype;
    }

    public String getTransferdescription() {
        return transferdescription;
    }

    public void setTransferdescription(String transferdescription) {
        this.transferdescription = transferdescription;
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
public Boolean getReconcilie() {
        return reconcilie;
    }

    public void setReconcilie(Boolean reconcilie) {
        this.reconcilie = reconcilie;
    }

    public String getOrigine() {
        return origine;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public BigInteger getCommission() {
        return commission;
    }

    public void setCommission(BigInteger commission) {
        this.commission = commission;
    }

    public Integer getOperateurs() {
        return operateurs;
    }

    public void setOperateurs(Integer operateurs) {
        this.operateurs = operateurs;
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
        if (!(object instanceof TransactionsReconciliations)) {
            return false;
        }
        TransactionsReconciliations other = (TransactionsReconciliations) object;
        if ((this.idtransactions == null && other.idtransactions != null) || (this.idtransactions != null && !this.idtransactions.equals(other.idtransactions))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sbs.easymbank.entities.TransactionsReconciliations[ idtransactions=" + idtransactions + " ]";
    }
    
}
