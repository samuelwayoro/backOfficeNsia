
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
 *         &lt;element name="BankZapDepositResult" type="{http://www.airtel.com/}retBankDeposit"/>
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
    "bankZapDepositResult"
})
@XmlRootElement(name = "BankZapDepositResponse")
public class BankZapDepositResponse {

    @XmlElement(name = "BankZapDepositResult", required = true)
    protected RetBankDeposit bankZapDepositResult;

    /**
     * Obtient la valeur de la propriété bankZapDepositResult.
     * 
     * @return
     *     possible object is
     *     {@link RetBankDeposit }
     *     
     */
    public RetBankDeposit getBankZapDepositResult() {
        return bankZapDepositResult;
    }

    /**
     * Définit la valeur de la propriété bankZapDepositResult.
     * 
     * @param value
     *     allowed object is
     *     {@link RetBankDeposit }
     *     
     */
    public void setBankZapDepositResult(RetBankDeposit value) {
        this.bankZapDepositResult = value;
    }

}
