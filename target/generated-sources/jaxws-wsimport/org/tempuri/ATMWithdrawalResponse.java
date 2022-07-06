
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
 *         &lt;element name="ATMWithdrawalResult" type="{http://tempuri.org/}ATMWithdrawalRes" minOccurs="0"/>
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
    "atmWithdrawalResult"
})
@XmlRootElement(name = "ATMWithdrawalResponse")
public class ATMWithdrawalResponse {

    @XmlElement(name = "ATMWithdrawalResult")
    protected ATMWithdrawalRes atmWithdrawalResult;

    /**
     * Obtient la valeur de la propriété atmWithdrawalResult.
     * 
     * @return
     *     possible object is
     *     {@link ATMWithdrawalRes }
     *     
     */
    public ATMWithdrawalRes getATMWithdrawalResult() {
        return atmWithdrawalResult;
    }

    /**
     * Définit la valeur de la propriété atmWithdrawalResult.
     * 
     * @param value
     *     allowed object is
     *     {@link ATMWithdrawalRes }
     *     
     */
    public void setATMWithdrawalResult(ATMWithdrawalRes value) {
        this.atmWithdrawalResult = value;
    }

}
