
package com.ph.tlc.mobilebank;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour getKYCRequestResponse complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="getKYCRequestResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="getKYCResponse" type="{http://mobilebank.tlc.ph.com/}standardResp" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getKYCRequestResponse", propOrder = {
    "getKYCResponse"
})
public class GetKYCRequestResponse {

    protected StandardResp getKYCResponse;

    /**
     * Obtient la valeur de la propriété getKYCResponse.
     * 
     * @return
     *     possible object is
     *     {@link StandardResp }
     *     
     */
    public StandardResp getGetKYCResponse() {
        return getKYCResponse;
    }

    /**
     * Définit la valeur de la propriété getKYCResponse.
     * 
     * @param value
     *     allowed object is
     *     {@link StandardResp }
     *     
     */
    public void setGetKYCResponse(StandardResp value) {
        this.getKYCResponse = value;
    }

}
