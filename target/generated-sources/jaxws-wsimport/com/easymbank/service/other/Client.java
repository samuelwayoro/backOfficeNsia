
package com.easymbank.service.other;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour client complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="client">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="comptes" type="{http://other.easymbank.sbs.com/}comptes" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="nom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="prenom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="racine" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="telephone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cni" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dateNaiss" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "client", propOrder = {
    "comptes",
    "nom",
    "prenom",
    "racine",
    "telephone",
    "cni",
    "dateNaiss"
})
public class Client {

    @XmlElement(nillable = true)
    protected List<Comptes> comptes;
    protected String nom;
    protected String prenom;
    protected String racine;
    protected String telephone;
    protected String cni;
    protected String dateNaiss;

    /**
     * Gets the value of the comptes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the comptes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComptes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Comptes }
     * 
     * 
     */
    public List<Comptes> getComptes() {
        if (comptes == null) {
            comptes = new ArrayList<Comptes>();
        }
        return this.comptes;
    }

    /**
     * Obtient la valeur de la propriété nom.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNom() {
        return nom;
    }

    /**
     * Définit la valeur de la propriété nom.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNom(String value) {
        this.nom = value;
    }

    /**
     * Obtient la valeur de la propriété prenom.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Définit la valeur de la propriété prenom.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrenom(String value) {
        this.prenom = value;
    }

    /**
     * Obtient la valeur de la propriété racine.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRacine() {
        return racine;
    }

    /**
     * Définit la valeur de la propriété racine.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRacine(String value) {
        this.racine = value;
    }

    /**
     * Obtient la valeur de la propriété telephone.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Définit la valeur de la propriété telephone.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelephone(String value) {
        this.telephone = value;
    }

    /**
     * Obtient la valeur de la propriété cni.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCni() {
        return cni;
    }

    /**
     * Définit la valeur de la propriété cni.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCni(String value) {
        this.cni = value;
    }

    /**
     * Obtient la valeur de la propriété dateNaiss.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateNaiss() {
        return dateNaiss;
    }

    /**
     * Définit la valeur de la propriété dateNaiss.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateNaiss(String value) {
        this.dateNaiss = value;
    }

}
