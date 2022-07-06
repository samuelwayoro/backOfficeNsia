
package com.easymbank.service.other;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour paymentResponse complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="paymentResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="statut" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="statutData" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="statutMsg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paymentResponse", propOrder = {
    "statut",
    "statutData",
    "statutMsg"
})
public class PaymentResponse {

    protected String statut;
    protected String statutData;
    protected String statutMsg;

    /**
     * Obtient la valeur de la propriété statut.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatut() {
        return statut;
    }

    /**
     * Définit la valeur de la propriété statut.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatut(String value) {
        this.statut = value;
    }

    /**
     * Obtient la valeur de la propriété statutData.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutData() {
        return statutData;
    }

    /**
     * Définit la valeur de la propriété statutData.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutData(String value) {
        this.statutData = value;
    }

    /**
     * Obtient la valeur de la propriété statutMsg.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatutMsg() {
        return statutMsg;
    }

    /**
     * Définit la valeur de la propriété statutMsg.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatutMsg(String value) {
        this.statutMsg = value;
    }

}
