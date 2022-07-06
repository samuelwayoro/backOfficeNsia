
package com.ph.tlc.mobilebank;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour getKYCRequest complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="getKYCRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AccountInfoRequest" type="{http://mobilebank.tlc.ph.com/}AccountInfoRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getKYCRequest", propOrder = {
    "accountInfoRequest"
})
public class GetKYCRequest {

    @XmlElement(name = "AccountInfoRequest", required = true)
    protected AccountInfoRequest accountInfoRequest;

    /**
     * Obtient la valeur de la propriété accountInfoRequest.
     * 
     * @return
     *     possible object is
     *     {@link AccountInfoRequest }
     *     
     */
    public AccountInfoRequest getAccountInfoRequest() {
        return accountInfoRequest;
    }

    /**
     * Définit la valeur de la propriété accountInfoRequest.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountInfoRequest }
     *     
     */
    public void setAccountInfoRequest(AccountInfoRequest value) {
        this.accountInfoRequest = value;
    }

}
