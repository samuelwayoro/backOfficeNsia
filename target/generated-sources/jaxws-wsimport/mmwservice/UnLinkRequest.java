
package mmwservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour unLinkRequest complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="unLinkRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://mmwservice/}xmlStruct">
 *       &lt;sequence>
 *         &lt;element name="bankaccountname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankaccountnumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="extdata" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="msisdn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="operatorcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="registeredby" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="timestamp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="unlinkreason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "unLinkRequest", propOrder = {
    "bankaccountname",
    "bankaccountnumber",
    "extdata",
    "msisdn",
    "operatorcode",
    "registeredby",
    "timestamp",
    "unlinkreason"
})
public class UnLinkRequest
    extends XmlStruct
{

    protected String bankaccountname;
    protected String bankaccountnumber;
    protected String extdata;
    protected String msisdn;
    protected String operatorcode;
    protected String registeredby;
    protected String timestamp;
    protected String unlinkreason;

    /**
     * Obtient la valeur de la propriété bankaccountname.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankaccountname() {
        return bankaccountname;
    }

    /**
     * Définit la valeur de la propriété bankaccountname.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankaccountname(String value) {
        this.bankaccountname = value;
    }

    /**
     * Obtient la valeur de la propriété bankaccountnumber.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankaccountnumber() {
        return bankaccountnumber;
    }

    /**
     * Définit la valeur de la propriété bankaccountnumber.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankaccountnumber(String value) {
        this.bankaccountnumber = value;
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
     * Obtient la valeur de la propriété timestamp.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Définit la valeur de la propriété timestamp.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimestamp(String value) {
        this.timestamp = value;
    }

    /**
     * Obtient la valeur de la propriété unlinkreason.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnlinkreason() {
        return unlinkreason;
    }

    /**
     * Définit la valeur de la propriété unlinkreason.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnlinkreason(String value) {
        this.unlinkreason = value;
    }

}
