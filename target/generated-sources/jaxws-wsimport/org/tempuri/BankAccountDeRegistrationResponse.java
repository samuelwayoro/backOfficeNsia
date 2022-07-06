
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
 *         &lt;element name="BankAccountDeRegistrationResult" type="{http://tempuri.org/}retBankAccountDeRegistration" minOccurs="0"/>
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
    "bankAccountDeRegistrationResult"
})
@XmlRootElement(name = "BankAccountDeRegistrationResponse")
public class BankAccountDeRegistrationResponse {

    @XmlElement(name = "BankAccountDeRegistrationResult")
    protected RetBankAccountDeRegistration bankAccountDeRegistrationResult;

    /**
     * Obtient la valeur de la propriété bankAccountDeRegistrationResult.
     * 
     * @return
     *     possible object is
     *     {@link RetBankAccountDeRegistration }
     *     
     */
    public RetBankAccountDeRegistration getBankAccountDeRegistrationResult() {
        return bankAccountDeRegistrationResult;
    }

    /**
     * Définit la valeur de la propriété bankAccountDeRegistrationResult.
     * 
     * @param value
     *     allowed object is
     *     {@link RetBankAccountDeRegistration }
     *     
     */
    public void setBankAccountDeRegistrationResult(RetBankAccountDeRegistration value) {
        this.bankAccountDeRegistrationResult = value;
    }

}
