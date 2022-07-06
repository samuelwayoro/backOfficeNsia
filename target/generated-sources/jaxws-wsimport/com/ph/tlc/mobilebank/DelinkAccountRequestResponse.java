
package com.ph.tlc.mobilebank;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour delinkAccountRequestResponse complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="delinkAccountRequestResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="delinkAccountResponse" type="{http://mobilebank.tlc.ph.com/}standardResp" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "delinkAccountRequestResponse", propOrder = {
    "delinkAccountResponse"
})
public class DelinkAccountRequestResponse {

    protected StandardResp delinkAccountResponse;

    /**
     * Obtient la valeur de la propriété delinkAccountResponse.
     * 
     * @return
     *     possible object is
     *     {@link StandardResp }
     *     
     */
    public StandardResp getDelinkAccountResponse() {
        return delinkAccountResponse;
    }

    /**
     * Définit la valeur de la propriété delinkAccountResponse.
     * 
     * @param value
     *     allowed object is
     *     {@link StandardResp }
     *     
     */
    public void setDelinkAccountResponse(StandardResp value) {
        this.delinkAccountResponse = value;
    }

}
