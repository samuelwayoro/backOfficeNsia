
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
 *         &lt;element name="ATMWithdrawalReversalResult" type="{http://tempuri.org/}ATMWithdrawalReversalRes" minOccurs="0"/>
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
    "atmWithdrawalReversalResult"
})
@XmlRootElement(name = "ATMWithdrawalReversalResponse")
public class ATMWithdrawalReversalResponse {

    @XmlElement(name = "ATMWithdrawalReversalResult")
    protected ATMWithdrawalReversalRes atmWithdrawalReversalResult;

    /**
     * Obtient la valeur de la propriété atmWithdrawalReversalResult.
     * 
     * @return
     *     possible object is
     *     {@link ATMWithdrawalReversalRes }
     *     
     */
    public ATMWithdrawalReversalRes getATMWithdrawalReversalResult() {
        return atmWithdrawalReversalResult;
    }

    /**
     * Définit la valeur de la propriété atmWithdrawalReversalResult.
     * 
     * @param value
     *     allowed object is
     *     {@link ATMWithdrawalReversalRes }
     *     
     */
    public void setATMWithdrawalReversalResult(ATMWithdrawalReversalRes value) {
        this.atmWithdrawalReversalResult = value;
    }

}
