
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
 *         &lt;element name="CheckingOfAuthorizedAccountNumberResult" type="{http://www.airtel.com/}retCheckingOfAuthorizedAccountNumber"/>
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
    "checkingOfAuthorizedAccountNumberResult"
})
@XmlRootElement(name = "CheckingOfAuthorizedAccountNumberResponse")
public class CheckingOfAuthorizedAccountNumberResponse {

    @XmlElement(name = "CheckingOfAuthorizedAccountNumberResult", required = true)
    protected RetCheckingOfAuthorizedAccountNumber checkingOfAuthorizedAccountNumberResult;

    /**
     * Obtient la valeur de la propriété checkingOfAuthorizedAccountNumberResult.
     * 
     * @return
     *     possible object is
     *     {@link RetCheckingOfAuthorizedAccountNumber }
     *     
     */
    public RetCheckingOfAuthorizedAccountNumber getCheckingOfAuthorizedAccountNumberResult() {
        return checkingOfAuthorizedAccountNumberResult;
    }

    /**
     * Définit la valeur de la propriété checkingOfAuthorizedAccountNumberResult.
     * 
     * @param value
     *     allowed object is
     *     {@link RetCheckingOfAuthorizedAccountNumber }
     *     
     */
    public void setCheckingOfAuthorizedAccountNumberResult(RetCheckingOfAuthorizedAccountNumber value) {
        this.checkingOfAuthorizedAccountNumberResult = value;
    }

}
