
package com.airtel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour anonymous complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BankDepositInquiryResult" type="{http://www.airtel.com/}retBankDepositInquiry"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "bankDepositInquiryResult"
})
@XmlRootElement(name = "BankDepositInquiryResponse")
public class BankDepositInquiryResponse {

    @XmlElement(name = "BankDepositInquiryResult", required = true)
    protected RetBankDepositInquiry bankDepositInquiryResult;

    /**
     * Obtient la valeur de la propriété bankDepositInquiryResult.
     * 
     * @return
     *     possible object is
     *     {@link RetBankDepositInquiry }
     *     
     */
    public RetBankDepositInquiry getBankDepositInquiryResult() {
        return bankDepositInquiryResult;
    }

    /**
     * Définit la valeur de la propriété bankDepositInquiryResult.
     * 
     * @param value
     *     allowed object is
     *     {@link RetBankDepositInquiry }
     *     
     */
    public void setBankDepositInquiryResult(RetBankDepositInquiry value) {
        this.bankDepositInquiryResult = value;
    }

}
