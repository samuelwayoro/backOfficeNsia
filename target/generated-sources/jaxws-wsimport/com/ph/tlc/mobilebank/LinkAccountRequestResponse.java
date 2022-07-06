
package com.ph.tlc.mobilebank;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour linkAccountRequestResponse complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="linkAccountRequestResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="linkAccountResponse" type="{http://mobilebank.tlc.ph.com/}standardResp" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "linkAccountRequestResponse", propOrder = {
    "linkAccountResponse"
})
public class LinkAccountRequestResponse {

    protected StandardResp linkAccountResponse;

    /**
     * Obtient la valeur de la propriété linkAccountResponse.
     * 
     * @return
     *     possible object is
     *     {@link StandardResp }
     *     
     */
    public StandardResp getLinkAccountResponse() {
        return linkAccountResponse;
    }

    /**
     * Définit la valeur de la propriété linkAccountResponse.
     * 
     * @param value
     *     allowed object is
     *     {@link StandardResp }
     *     
     */
    public void setLinkAccountResponse(StandardResp value) {
        this.linkAccountResponse = value;
    }

}
