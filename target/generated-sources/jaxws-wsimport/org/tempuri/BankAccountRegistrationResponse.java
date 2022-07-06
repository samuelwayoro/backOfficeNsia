
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
 *         &lt;element name="BankAccountRegistrationResult" type="{http://tempuri.org/}retBankAccountRegistration" minOccurs="0"/>
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
    "bankAccountRegistrationResult"
})
@XmlRootElement(name = "BankAccountRegistrationResponse")
public class BankAccountRegistrationResponse {

    @XmlElement(name = "BankAccountRegistrationResult")
    protected RetBankAccountRegistration bankAccountRegistrationResult;

    /**
     * Obtient la valeur de la propriété bankAccountRegistrationResult.
     * 
     * @return
     *     possible object is
     *     {@link RetBankAccountRegistration }
     *     
     */
    public RetBankAccountRegistration getBankAccountRegistrationResult() {
        return bankAccountRegistrationResult;
    }

    /**
     * Définit la valeur de la propriété bankAccountRegistrationResult.
     * 
     * @param value
     *     allowed object is
     *     {@link RetBankAccountRegistration }
     *     
     */
    public void setBankAccountRegistrationResult(RetBankAccountRegistration value) {
        this.bankAccountRegistrationResult = value;
    }

}
