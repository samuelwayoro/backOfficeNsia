
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
 *         &lt;element name="BankToMe2uMoneyResult" type="{http://www.airtel.com/}retBankToMe2uMoney"/>
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
    "bankToMe2UMoneyResult"
})
@XmlRootElement(name = "BankToMe2uMoneyResponse")
public class BankToMe2UMoneyResponse {

    @XmlElement(name = "BankToMe2uMoneyResult", required = true)
    protected RetBankToMe2UMoney bankToMe2UMoneyResult;

    /**
     * Obtient la valeur de la propriété bankToMe2UMoneyResult.
     * 
     * @return
     *     possible object is
     *     {@link RetBankToMe2UMoney }
     *     
     */
    public RetBankToMe2UMoney getBankToMe2UMoneyResult() {
        return bankToMe2UMoneyResult;
    }

    /**
     * Définit la valeur de la propriété bankToMe2UMoneyResult.
     * 
     * @param value
     *     allowed object is
     *     {@link RetBankToMe2UMoney }
     *     
     */
    public void setBankToMe2UMoneyResult(RetBankToMe2UMoney value) {
        this.bankToMe2UMoneyResult = value;
    }

}
