
package com.ph.tlc.mobilebank;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour linkAccountRequest complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="linkAccountRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LinkAccountRequest" type="{http://mobilebank.tlc.ph.com/}LinkAccountRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "linkAccountRequest", propOrder = {
    "linkAccountRequest"
})
public class LinkAccountRequest {

    @XmlElement(name = "LinkAccountRequest", required = true)
    protected LinkAccountRequest2 linkAccountRequest;

    /**
     * Obtient la valeur de la propriété linkAccountRequest.
     * 
     * @return
     *     possible object is
     *     {@link LinkAccountRequest2 }
     *     
     */
    public LinkAccountRequest2 getLinkAccountRequest() {
        return linkAccountRequest;
    }

    /**
     * Définit la valeur de la propriété linkAccountRequest.
     * 
     * @param value
     *     allowed object is
     *     {@link LinkAccountRequest2 }
     *     
     */
    public void setLinkAccountRequest(LinkAccountRequest2 value) {
        this.linkAccountRequest = value;
    }

}
