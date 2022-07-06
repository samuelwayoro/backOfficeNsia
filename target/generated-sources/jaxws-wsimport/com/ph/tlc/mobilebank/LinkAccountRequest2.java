
package com.ph.tlc.mobilebank;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour LinkAccountRequest complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="LinkAccountRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="operatorcode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="msisdn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="registeredby" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="accountalias" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="accountname" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="extdata" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LinkAccountRequest", propOrder = {
    "token",
    "operatorcode",
    "msisdn",
    "code",
    "registeredby",
    "accountalias",
    "accountname",
    "extdata"
})
public class LinkAccountRequest2 {

    @XmlElement(required = true)
    protected String token;
    @XmlElement(required = true)
    protected String operatorcode;
    @XmlElement(required = true)
    protected String msisdn;
    @XmlElement(required = true)
    protected String code;
    @XmlElement(required = true)
    protected String registeredby;
    @XmlElement(required = true)
    protected String accountalias;
    @XmlElement(required = true)
    protected String accountname;
    @XmlElement(required = true)
    protected String extdata;

    /**
     * Obtient la valeur de la propriété token.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToken() {
        return token;
    }

    /**
     * Définit la valeur de la propriété token.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToken(String value) {
        this.token = value;
    }

    /**
     * Obtient la valeur de la propriété operatorcode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperatorcode() {
        return operatorcode;
    }

    /**
     * Définit la valeur de la propriété operatorcode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperatorcode(String value) {
        this.operatorcode = value;
    }

    /**
     * Obtient la valeur de la propriété msisdn.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMsisdn() {
        return msisdn;
    }

    /**
     * Définit la valeur de la propriété msisdn.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMsisdn(String value) {
        this.msisdn = value;
    }

    /**
     * Obtient la valeur de la propriété code.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Définit la valeur de la propriété code.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Obtient la valeur de la propriété registeredby.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegisteredby() {
        return registeredby;
    }

    /**
     * Définit la valeur de la propriété registeredby.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegisteredby(String value) {
        this.registeredby = value;
    }

    /**
     * Obtient la valeur de la propriété accountalias.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountalias() {
        return accountalias;
    }

    /**
     * Définit la valeur de la propriété accountalias.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountalias(String value) {
        this.accountalias = value;
    }

    /**
     * Obtient la valeur de la propriété accountname.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountname() {
        return accountname;
    }

    /**
     * Définit la valeur de la propriété accountname.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountname(String value) {
        this.accountname = value;
    }

    /**
     * Obtient la valeur de la propriété extdata.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtdata() {
        return extdata;
    }

    /**
     * Définit la valeur de la propriété extdata.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtdata(String value) {
        this.extdata = value;
    }

}
