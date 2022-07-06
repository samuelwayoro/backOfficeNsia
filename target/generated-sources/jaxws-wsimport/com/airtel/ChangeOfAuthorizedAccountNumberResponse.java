
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
 *         &lt;element name="ChangeOfAuthorizedAccountNumberResult" type="{http://www.airtel.com/}retChangeOfAuthorizedAccountNumber"/>
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
    "changeOfAuthorizedAccountNumberResult"
})
@XmlRootElement(name = "ChangeOfAuthorizedAccountNumberResponse")
public class ChangeOfAuthorizedAccountNumberResponse {

    @XmlElement(name = "ChangeOfAuthorizedAccountNumberResult", required = true)
    protected RetChangeOfAuthorizedAccountNumber changeOfAuthorizedAccountNumberResult;

    /**
     * Obtient la valeur de la propriété changeOfAuthorizedAccountNumberResult.
     * 
     * @return
     *     possible object is
     *     {@link RetChangeOfAuthorizedAccountNumber }
     *     
     */
    public RetChangeOfAuthorizedAccountNumber getChangeOfAuthorizedAccountNumberResult() {
        return changeOfAuthorizedAccountNumberResult;
    }

    /**
     * Définit la valeur de la propriété changeOfAuthorizedAccountNumberResult.
     * 
     * @param value
     *     allowed object is
     *     {@link RetChangeOfAuthorizedAccountNumber }
     *     
     */
    public void setChangeOfAuthorizedAccountNumberResult(RetChangeOfAuthorizedAccountNumber value) {
        this.changeOfAuthorizedAccountNumberResult = value;
    }

}
