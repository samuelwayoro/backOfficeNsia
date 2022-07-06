
package mmwservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour bankRequest complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="bankRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://mmwservice/}xmlStruct">
 *       &lt;sequence>
 *         &lt;element name="accountnumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankbalanceafter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankbalancebefore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="banktransactionreferenceid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="firstname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lastname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="msisdn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="secondname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="timestamp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="transamount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "bankRequest", propOrder = {
    "accountnumber",
    "bankbalanceafter",
    "bankbalancebefore",
    "banktransactionreferenceid",
    "firstname",
    "lastname",
    "msisdn",
    "secondname",
    "timestamp",
    "transamount"
})
public class BankRequest
    extends XmlStruct
{

    protected String accountnumber;
    protected String bankbalanceafter;
    protected String bankbalancebefore;
    protected String banktransactionreferenceid;
    protected String firstname;
    protected String lastname;
    protected String msisdn;
    protected String secondname;
    protected String timestamp;
    protected String transamount;

    /**
     * Obtient la valeur de la propriété accountnumber.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountnumber() {
        return accountnumber;
    }

    /**
     * Définit la valeur de la propriété accountnumber.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountnumber(String value) {
        this.accountnumber = value;
    }

    /**
     * Obtient la valeur de la propriété bankbalanceafter.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankbalanceafter() {
        return bankbalanceafter;
    }

    /**
     * Définit la valeur de la propriété bankbalanceafter.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankbalanceafter(String value) {
        this.bankbalanceafter = value;
    }

    /**
     * Obtient la valeur de la propriété bankbalancebefore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankbalancebefore() {
        return bankbalancebefore;
    }

    /**
     * Définit la valeur de la propriété bankbalancebefore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankbalancebefore(String value) {
        this.bankbalancebefore = value;
    }

    /**
     * Obtient la valeur de la propriété banktransactionreferenceid.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBanktransactionreferenceid() {
        return banktransactionreferenceid;
    }

    /**
     * Définit la valeur de la propriété banktransactionreferenceid.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBanktransactionreferenceid(String value) {
        this.banktransactionreferenceid = value;
    }

    /**
     * Obtient la valeur de la propriété firstname.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Définit la valeur de la propriété firstname.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstname(String value) {
        this.firstname = value;
    }

    /**
     * Obtient la valeur de la propriété lastname.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Définit la valeur de la propriété lastname.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastname(String value) {
        this.lastname = value;
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
     * Obtient la valeur de la propriété secondname.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecondname() {
        return secondname;
    }

    /**
     * Définit la valeur de la propriété secondname.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecondname(String value) {
        this.secondname = value;
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
     * Obtient la valeur de la propriété transamount.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransamount() {
        return transamount;
    }

    /**
     * Définit la valeur de la propriété transamount.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransamount(String value) {
        this.transamount = value;
    }

}
