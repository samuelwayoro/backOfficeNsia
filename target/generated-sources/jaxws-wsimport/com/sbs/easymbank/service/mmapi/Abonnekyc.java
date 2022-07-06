
package com.sbs.easymbank.service.mmapi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java pour abonnekyc complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="abonnekyc">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dateExpiration" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="imsi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="KYCrspDOB" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="KYCrspFirstname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="KYCrspIdRefNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="KYCrspLastname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="KYCrspType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nameFilePieceRecto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nameFilePieceVerso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="number" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pieceRecto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pieceVerso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "abonnekyc", propOrder = {
    "dateExpiration",
    "imsi",
    "kyCrspDOB",
    "kyCrspFirstname",
    "kyCrspIdRefNo",
    "kyCrspLastname",
    "kyCrspType",
    "nameFilePieceRecto",
    "nameFilePieceVerso",
    "number",
    "pieceRecto",
    "pieceVerso"
})
@XmlSeeAlso({
    SubscriptionResponse.class,
    KyCrequestResponse.class
})
public class Abonnekyc {

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateExpiration;
    protected String imsi;
    @XmlElement(name = "KYCrspDOB")
    protected String kyCrspDOB;
    @XmlElement(name = "KYCrspFirstname")
    protected String kyCrspFirstname;
    @XmlElement(name = "KYCrspIdRefNo")
    protected String kyCrspIdRefNo;
    @XmlElement(name = "KYCrspLastname")
    protected String kyCrspLastname;
    @XmlElement(name = "KYCrspType")
    protected String kyCrspType;
    protected String nameFilePieceRecto;
    protected String nameFilePieceVerso;
    protected String number;
    protected String pieceRecto;
    protected String pieceVerso;

    /**
     * Obtient la valeur de la propriété dateExpiration.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateExpiration() {
        return dateExpiration;
    }

    /**
     * Définit la valeur de la propriété dateExpiration.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateExpiration(XMLGregorianCalendar value) {
        this.dateExpiration = value;
    }

    /**
     * Obtient la valeur de la propriété imsi.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImsi() {
        return imsi;
    }

    /**
     * Définit la valeur de la propriété imsi.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImsi(String value) {
        this.imsi = value;
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
     * Obtient la valeur de la propriété kyCrspType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKYCrspType() {
        return kyCrspType;
    }

    /**
     * Définit la valeur de la propriété kyCrspType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKYCrspType(String value) {
        this.kyCrspType = value;
    }

    /**
     * Obtient la valeur de la propriété nameFilePieceRecto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameFilePieceRecto() {
        return nameFilePieceRecto;
    }

    /**
     * Définit la valeur de la propriété nameFilePieceRecto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameFilePieceRecto(String value) {
        this.nameFilePieceRecto = value;
    }

    /**
     * Obtient la valeur de la propriété nameFilePieceVerso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameFilePieceVerso() {
        return nameFilePieceVerso;
    }

    /**
     * Définit la valeur de la propriété nameFilePieceVerso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameFilePieceVerso(String value) {
        this.nameFilePieceVerso = value;
    }

    /**
     * Obtient la valeur de la propriété number.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumber() {
        return number;
    }

    /**
     * Définit la valeur de la propriété number.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumber(String value) {
        this.number = value;
    }

    /**
     * Obtient la valeur de la propriété pieceRecto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPieceRecto() {
        return pieceRecto;
    }

    /**
     * Définit la valeur de la propriété pieceRecto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPieceRecto(String value) {
        this.pieceRecto = value;
    }

    /**
     * Obtient la valeur de la propriété pieceVerso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPieceVerso() {
        return pieceVerso;
    }

    /**
     * Définit la valeur de la propriété pieceVerso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPieceVerso(String value) {
        this.pieceVerso = value;
    }

}
