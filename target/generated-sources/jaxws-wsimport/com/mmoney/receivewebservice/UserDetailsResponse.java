
package com.mmoney.receivewebservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour userDetailsResponse complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="userDetailsResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="responseCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="KYCrspFirstname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="KYCrspLastname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="KYCrspIdRefNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="KYCrspDOB" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="KYCrspType " type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mobileNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "userDetailsResponse", propOrder = {
    "responseCode",
    "kyCrspFirstname",
    "kyCrspLastname",
    "kyCrspIdRefNo",
    "kyCrspDOB",
    "kyCrspType0020",
    "mobileNumber",
    "message"
})
public class UserDetailsResponse {

    protected String responseCode;
    @XmlElement(name = "KYCrspFirstname")
    protected String kyCrspFirstname;
    @XmlElement(name = "KYCrspLastname")
    protected String kyCrspLastname;
    @XmlElement(name = "KYCrspIdRefNo")
    protected String kyCrspIdRefNo;
    @XmlElement(name = "KYCrspDOB")
    protected String kyCrspDOB;
    @XmlElement(name = "KYCrspType ")
    protected String kyCrspType0020;
    protected String mobileNumber;
    protected String message;

    /**
     * Obtient la valeur de la propriété responseCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResponseCode() {
        return responseCode;
    }

    /**
     * Définit la valeur de la propriété responseCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResponseCode(String value) {
        this.responseCode = value;
    }

    /**
     * Obtient la valeur de la propriété kyCrspFirstname.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKYCrspFirstname() {
        return kyCrspFirstname;
    }

    /**
     * Définit la valeur de la propriété kyCrspFirstname.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKYCrspFirstname(String value) {
        this.kyCrspFirstname = value;
    }

    /**
     * Obtient la valeur de la propriété kyCrspLastname.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKYCrspLastname() {
        return kyCrspLastname;
    }

    /**
     * Définit la valeur de la propriété kyCrspLastname.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKYCrspLastname(String value) {
        this.kyCrspLastname = value;
    }

    /**
     * Obtient la valeur de la propriété kyCrspIdRefNo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKYCrspIdRefNo() {
        return kyCrspIdRefNo;
    }

    /**
     * Définit la valeur de la propriété kyCrspIdRefNo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKYCrspIdRefNo(String value) {
        this.kyCrspIdRefNo = value;
    }

    /**
     * Obtient la valeur de la propriété kyCrspDOB.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKYCrspDOB() {
        return kyCrspDOB;
    }

    /**
     * Définit la valeur de la propriété kyCrspDOB.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKYCrspDOB(String value) {
        this.kyCrspDOB = value;
    }

    /**
     * Obtient la valeur de la propriété kyCrspType0020.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKYCrspType_0020() {
        return kyCrspType0020;
    }

    /**
     * Définit la valeur de la propriété kyCrspType0020.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKYCrspType_0020(String value) {
        this.kyCrspType0020 = value;
    }

    /**
     * Obtient la valeur de la propriété mobileNumber.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMobileNumber() {
        return mobileNumber;
    }

    /**
     * Définit la valeur de la propriété mobileNumber.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMobileNumber(String value) {
        this.mobileNumber = value;
    }

    /**
     * Obtient la valeur de la propriété message.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Définit la valeur de la propriété message.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

}
