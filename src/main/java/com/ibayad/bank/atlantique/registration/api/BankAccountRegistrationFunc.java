
package com.ibayad.bank.atlantique.registration.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for bankAccountRegistrationFunc complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="bankAccountRegistrationFunc">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BankAccountRegistration" type="{http://api.registration.atlantique.bank.ibayad.com/}BankAccountRegistration" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "bankAccountRegistrationFunc", propOrder = {
    "bankAccountRegistration"
})
public class BankAccountRegistrationFunc {

    @XmlElement(name = "BankAccountRegistration")
    protected BankAccountRegistration bankAccountRegistration;

    /**
     * Gets the value of the bankAccountRegistration property.
     * 
     * @return
     *     possible object is
     *     {@link BankAccountRegistration }
     *     
     */
    public BankAccountRegistration getBankAccountRegistration() {
        return bankAccountRegistration;
    }

    /**
     * Sets the value of the bankAccountRegistration property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankAccountRegistration }
     *     
     */
    public void setBankAccountRegistration(BankAccountRegistration value) {
        this.bankAccountRegistration = value;
    }

}
