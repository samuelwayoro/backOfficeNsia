
package com.ph.tlc.mobilebank;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour DelinkAccountRequest complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="DelinkAccountRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="operatorcode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="msisdn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="accountalias" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="delinkdate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="delinkername" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="remarks" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DelinkAccountRequest", propOrder = {
    "token",
    "operatorcode",
    "msisdn",
    "accountalias",
    "delinkdate",
    "delinkername",
    "remarks"
})
public class DelinkAccountRequest2 {

    @XmlElement(required = true)
    protected String token;
    @XmlElement(required = true)
    protected String operatorcode;
    @XmlElement(required = true)
    protected String msisdn;
    @XmlElement(required = true)
    protected String accountalias;
    @XmlElement(required = true)
    protected String delinkdate;
    @XmlElement(required = true)
    protected String delinkername;
    @XmlElement(required = true)
    protected String remarks;

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
     * Obtient la valeur de la propriété delinkdate.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDelinkdate() {
        return delinkdate;
    }

    /**
     * Définit la valeur de la propriété delinkdate.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDelinkdate(String value) {
        this.delinkdate = value;
    }

    /**
     * Obtient la valeur de la propriété delinkername.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDelinkername() {
        return delinkername;
    }

    /**
     * Définit la valeur de la propriété delinkername.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDelinkername(String value) {
        this.delinkername = value;
    }

    /**
     * Obtient la valeur de la propriété remarks.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * Définit la valeur de la propriété remarks.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemarks(String value) {
        this.remarks = value;
    }

}
