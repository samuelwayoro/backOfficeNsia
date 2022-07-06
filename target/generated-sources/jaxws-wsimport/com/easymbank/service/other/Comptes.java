
package com.easymbank.service.other;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour comptes complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="comptes">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="agence" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="coddci" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="expl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="libNcg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ncg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numero" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "comptes", propOrder = {
    "agence",
    "coddci",
    "expl",
    "libNcg",
    "ncg",
    "numero"
})
public class Comptes {

    protected String agence;
    protected String coddci;
    protected String expl;
    protected String libNcg;
    protected String ncg;
    protected String numero;

    /**
     * Obtient la valeur de la propriété agence.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgence() {
        return agence;
    }

    /**
     * Définit la valeur de la propriété agence.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgence(String value) {
        this.agence = value;
    }

    /**
     * Obtient la valeur de la propriété coddci.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCoddci() {
        return coddci;
    }

    /**
     * Définit la valeur de la propriété coddci.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCoddci(String value) {
        this.coddci = value;
    }

    /**
     * Obtient la valeur de la propriété expl.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpl() {
        return expl;
    }

    /**
     * Définit la valeur de la propriété expl.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpl(String value) {
        this.expl = value;
    }

    /**
     * Obtient la valeur de la propriété libNcg.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLibNcg() {
        return libNcg;
    }

    /**
     * Définit la valeur de la propriété libNcg.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLibNcg(String value) {
        this.libNcg = value;
    }

    /**
     * Obtient la valeur de la propriété ncg.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNcg() {
        return ncg;
    }

    /**
     * Définit la valeur de la propriété ncg.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNcg(String value) {
        this.ncg = value;
    }

    /**
     * Obtient la valeur de la propriété numero.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Définit la valeur de la propriété numero.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumero(String value) {
        this.numero = value;
    }

}
