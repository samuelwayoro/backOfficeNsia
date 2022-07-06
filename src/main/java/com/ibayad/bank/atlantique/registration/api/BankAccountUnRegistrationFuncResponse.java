
package com.ibayad.bank.atlantique.registration.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for bankAccountUnRegistrationFuncResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="bankAccountUnRegistrationFuncResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BankAccountUnRegistrationReponse" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "bankAccountUnRegistrationFuncResponse", propOrder = {
    "bankAccountUnRegistrationReponse"
})
public class BankAccountUnRegistrationFuncResponse {

    @XmlElement(name = "BankAccountUnRegistrationReponse")
    protected Object bankAccountUnRegistrationReponse;

    /**
     * Gets the value of the bankAccountUnRegistrationReponse property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getBankAccountUnRegistrationReponse() {
        return bankAccountUnRegistrationReponse;
    }

    /**
     * Sets the value of the bankAccountUnRegistrationReponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setBankAccountUnRegistrationReponse(Object value) {
        this.bankAccountUnRegistrationReponse = value;
    }

}
