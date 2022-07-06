
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
 *         &lt;element name="KYCRequestResult" type="{http://tempuri.org/}retKYCRequest" minOccurs="0"/>
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
    "kycRequestResult"
})
@XmlRootElement(name = "KYCRequestResponse")
public class KYCRequestResponse {

    @XmlElement(name = "KYCRequestResult")
    protected RetKYCRequest kycRequestResult;

    /**
     * Obtient la valeur de la propriété kycRequestResult.
     * 
     * @return
     *     possible object is
     *     {@link RetKYCRequest }
     *     
     */
    public RetKYCRequest getKYCRequestResult() {
        return kycRequestResult;
    }

    /**
     * Définit la valeur de la propriété kycRequestResult.
     * 
     * @param value
     *     allowed object is
     *     {@link RetKYCRequest }
     *     
     */
    public void setKYCRequestResult(RetKYCRequest value) {
        this.kycRequestResult = value;
    }

}
