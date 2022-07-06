
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
 *         &lt;element name="CustomerRegistrationResult" type="{http://tempuri.org/}retCustomerRegistration" minOccurs="0"/>
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
    "customerRegistrationResult"
})
@XmlRootElement(name = "CustomerRegistrationResponse")
public class CustomerRegistrationResponse {

    @XmlElement(name = "CustomerRegistrationResult")
    protected RetCustomerRegistration customerRegistrationResult;

    /**
     * Obtient la valeur de la propriété customerRegistrationResult.
     * 
     * @return
     *     possible object is
     *     {@link RetCustomerRegistration }
     *     
     */
    public RetCustomerRegistration getCustomerRegistrationResult() {
        return customerRegistrationResult;
    }

    /**
     * Définit la valeur de la propriété customerRegistrationResult.
     * 
     * @param value
     *     allowed object is
     *     {@link RetCustomerRegistration }
     *     
     */
    public void setCustomerRegistrationResult(RetCustomerRegistration value) {
        this.customerRegistrationResult = value;
    }

}
