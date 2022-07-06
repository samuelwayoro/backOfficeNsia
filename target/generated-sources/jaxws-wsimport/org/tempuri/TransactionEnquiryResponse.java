
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="transactionEnquiryResult" type="{http://tempuri.org/}rettransactionEnquiry" minOccurs="0"/>
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
    "transactionEnquiryResult"
})
@XmlRootElement(name = "transactionEnquiryResponse")
public class TransactionEnquiryResponse {

    protected RettransactionEnquiry transactionEnquiryResult;

    /**
     * Obtient la valeur de la propriété transactionEnquiryResult.
     * 
     * @return
     *     possible object is
     *     {@link RettransactionEnquiry }
     *     
     */
    public RettransactionEnquiry getTransactionEnquiryResult() {
        return transactionEnquiryResult;
    }

    /**
     * Définit la valeur de la propriété transactionEnquiryResult.
     * 
     * @param value
     *     allowed object is
     *     {@link RettransactionEnquiry }
     *     
     */
    public void setTransactionEnquiryResult(RettransactionEnquiry value) {
        this.transactionEnquiryResult = value;
    }

}
