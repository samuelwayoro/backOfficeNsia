
package com.ph.tlc.mobilebank;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour delinkAccountRequest complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="delinkAccountRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DelinkAccountRequest" type="{http://mobilebank.tlc.ph.com/}DelinkAccountRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "delinkAccountRequest", propOrder = {
    "delinkAccountRequest"
})
public class DelinkAccountRequest {

    @XmlElement(name = "DelinkAccountRequest", required = true)
    protected DelinkAccountRequest2 delinkAccountRequest;

    /**
     * Obtient la valeur de la propriété delinkAccountRequest.
     * 
     * @return
     *     possible object is
     *     {@link DelinkAccountRequest2 }
     *     
     */
    public DelinkAccountRequest2 getDelinkAccountRequest() {
        return delinkAccountRequest;
    }

    /**
     * Définit la valeur de la propriété delinkAccountRequest.
     * 
     * @param value
     *     allowed object is
     *     {@link DelinkAccountRequest2 }
     *     
     */
    public void setDelinkAccountRequest(DelinkAccountRequest2 value) {
        this.delinkAccountRequest = value;
    }

}
