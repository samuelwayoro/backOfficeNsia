
package org.tempuri;

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
 *         &lt;element name="BankDepositResult" type="{http://tempuri.org/}retBankDeposit" minOccurs="0"/>
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
    "bankDepositResult"
})
@XmlRootElement(name = "BankDepositResponse")
public class BankDepositResponse {

    @XmlElement(name = "BankDepositResult")
    protected RetBankDeposit bankDepositResult;

    /**
     * Obtient la valeur de la propriété bankDepositResult.
     * 
     * @return
     *     possible object is
     *     {@link RetBankDeposit }
     *     
     */
    public RetBankDeposit getBankDepositResult() {
        return bankDepositResult;
    }

    /**
     * Définit la valeur de la propriété bankDepositResult.
     * 
     * @param value
     *     allowed object is
     *     {@link RetBankDeposit }
     *     
     */
    public void setBankDepositResult(RetBankDeposit value) {
        this.bankDepositResult = value;
    }

}
