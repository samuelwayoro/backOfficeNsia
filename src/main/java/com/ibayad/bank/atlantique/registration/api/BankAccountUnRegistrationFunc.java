
package com.ibayad.bank.atlantique.registration.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for bankAccountUnRegistrationFunc complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="bankAccountUnRegistrationFunc">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BankAccountUnRegistration" type="{http://api.registration.atlantique.bank.ibayad.com/}BankAccountunRegistration" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "bankAccountUnRegistrationFunc", propOrder = {
    "bankAccountUnRegistration"
})
public class BankAccountUnRegistrationFunc {

    @XmlElement(name = "BankAccountUnRegistration")
    protected BankAccountunRegistration bankAccountUnRegistration;

    /**
     * Gets the value of the bankAccountUnRegistration property.
     * 
     * @return
     *     possible object is
     *     {@link BankAccountunRegistration }
     *     
     */
    public BankAccountunRegistration getBankAccountUnRegistration() {
        return bankAccountUnRegistration;
    }

    /**
     * Sets the value of the bankAccountUnRegistration property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankAccountunRegistration }
     *     
     */
    public void setBankAccountUnRegistration(BankAccountunRegistration value) {
        this.bankAccountUnRegistration = value;
    }

}
