
package com.ibayad.bank.atlantique.registration.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for bankAccountRegistrationFuncResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="bankAccountRegistrationFuncResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BankAccountRegistrationReponse" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "bankAccountRegistrationFuncResponse", propOrder = {
    "bankAccountRegistrationReponse"
})
public class BankAccountRegistrationFuncResponse {

    @XmlElement(name = "BankAccountRegistrationReponse")
    protected Object bankAccountRegistrationReponse;

    /**
     * Gets the value of the bankAccountRegistrationReponse property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getBankAccountRegistrationReponse() {
        return bankAccountRegistrationReponse;
    }

    /**
     * Sets the value of the bankAccountRegistrationReponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setBankAccountRegistrationReponse(Object value) {
        this.bankAccountRegistrationReponse = value;
    }

}
