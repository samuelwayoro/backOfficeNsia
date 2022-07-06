
package com.easymbank.service.other;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour paymentRequest complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="paymentRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="alias" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="commissions" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="compte" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="datePaiment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="montant" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paymentRequest", propOrder = {
    "alias",
    "commissions",
    "compte",
    "datePaiment",
    "montant"
})
public class PaymentRequest {

    protected String alias;
    protected double commissions;
    protected String compte;
    protected String datePaiment;
    protected double montant;

    /**
     * Obtient la valeur de la propriété alias.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Définit la valeur de la propriété alias.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlias(String value) {
        this.alias = value;
    }

    /**
     * Obtient la valeur de la propriété commissions.
     * 
     */
    public double getCommissions() {
        return commissions;
    }

    /**
     * Définit la valeur de la propriété commissions.
     * 
     */
    public void setCommissions(double value) {
        this.commissions = value;
    }

    /**
     * Obtient la valeur de la propriété compte.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompte() {
        return compte;
    }

    /**
     * Définit la valeur de la propriété compte.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompte(String value) {
        this.compte = value;
    }

    /**
     * Obtient la valeur de la propriété datePaiment.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatePaiment() {
        return datePaiment;
    }

    /**
     * Définit la valeur de la propriété datePaiment.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatePaiment(String value) {
        this.datePaiment = value;
    }

    /**
     * Obtient la valeur de la propriété montant.
     * 
     */
    public double getMontant() {
        return montant;
    }

    /**
     * Définit la valeur de la propriété montant.
     * 
     */
    public void setMontant(double value) {
        this.montant = value;
    }

}
