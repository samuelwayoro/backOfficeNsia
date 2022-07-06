
package com.ibayad.bank.atlantique.registration.api;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.ibayad.bank.atlantique.registration.api package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _BankAccountRegistrationFunc_QNAME = new QName("http://api.registration.atlantique.bank.ibayad.com/", "bankAccountRegistrationFunc");
    private final static QName _BankAccountUnRegistrationResult_QNAME = new QName("http://api.registration.atlantique.bank.ibayad.com/", "BankAccountUnRegistrationResult");
    private final static QName _BankAccountUnRegistrationFunc_QNAME = new QName("http://api.registration.atlantique.bank.ibayad.com/", "bankAccountUnRegistrationFunc");
    private final static QName _BankAccountRegistrationFuncResponse_QNAME = new QName("http://api.registration.atlantique.bank.ibayad.com/", "bankAccountRegistrationFuncResponse");
    private final static QName _BankAccountRegistration_QNAME = new QName("http://api.registration.atlantique.bank.ibayad.com/", "BankAccountRegistration");
    private final static QName _BankAccountUnRegistration_QNAME = new QName("http://api.registration.atlantique.bank.ibayad.com/", "BankAccountUnRegistration");
    private final static QName _BankAccountRegistrationResult_QNAME = new QName("http://api.registration.atlantique.bank.ibayad.com/", "BankAccountRegistrationResult");
    private final static QName _BankAccountUnRegistrationFuncResponse_QNAME = new QName("http://api.registration.atlantique.bank.ibayad.com/", "bankAccountUnRegistrationFuncResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.ibayad.bank.atlantique.registration.api
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BankAccountUnRegistrationFunc }
     * 
     */
    public BankAccountUnRegistrationFunc createBankAccountUnRegistrationFunc() {
        return new BankAccountUnRegistrationFunc();
    }

    /**
     * Create an instance of {@link BankAccountRegistration }
     * 
     */
    public BankAccountRegistration createBankAccountRegistration() {
        return new BankAccountRegistration();
    }

    /**
     * Create an instance of {@link BankAccountunRegistration }
     * 
     */
    public BankAccountunRegistration createBankAccountunRegistration() {
        return new BankAccountunRegistration();
    }

    /**
     * Create an instance of {@link BankAccountRegistrationFuncResponse }
     * 
     */
    public BankAccountRegistrationFuncResponse createBankAccountRegistrationFuncResponse() {
        return new BankAccountRegistrationFuncResponse();
    }

    /**
     * Create an instance of {@link BankAccountUnRegistrationReponse.BankAccountUnRegistrationResult }
     * 
     */
    public BankAccountUnRegistrationReponse.BankAccountUnRegistrationResult createBankAccountUnRegistrationReponseBankAccountUnRegistrationResult() {
        return new BankAccountUnRegistrationReponse.BankAccountUnRegistrationResult();
    }

    /**
     * Create an instance of {@link BankAccountRegistrationFunc }
     * 
     */
    public BankAccountRegistrationFunc createBankAccountRegistrationFunc() {
        return new BankAccountRegistrationFunc();
    }

    /**
     * Create an instance of {@link BankAccountUnRegistrationReponse }
     * 
     */
    public BankAccountUnRegistrationReponse createBankAccountUnRegistrationReponse() {
        return new BankAccountUnRegistrationReponse();
    }

    /**
     * Create an instance of {@link BankAccountRegistrationReponse.BankAccountRegistrationResult }
     * 
     */
    public BankAccountRegistrationReponse.BankAccountRegistrationResult createBankAccountRegistrationReponseBankAccountRegistrationResult() {
        return new BankAccountRegistrationReponse.BankAccountRegistrationResult();
    }

    /**
     * Create an instance of {@link BankAccountRegistrationReponse }
     * 
     */
    public BankAccountRegistrationReponse createBankAccountRegistrationReponse() {
        return new BankAccountRegistrationReponse();
    }

    /**
     * Create an instance of {@link BankAccountUnRegistrationFuncResponse }
     * 
     */
    public BankAccountUnRegistrationFuncResponse createBankAccountUnRegistrationFuncResponse() {
        return new BankAccountUnRegistrationFuncResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BankAccountRegistrationFunc }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.registration.atlantique.bank.ibayad.com/", name = "bankAccountRegistrationFunc")
    public JAXBElement<BankAccountRegistrationFunc> createBankAccountRegistrationFunc(BankAccountRegistrationFunc value) {
        return new JAXBElement<BankAccountRegistrationFunc>(_BankAccountRegistrationFunc_QNAME, BankAccountRegistrationFunc.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.registration.atlantique.bank.ibayad.com/", name = "BankAccountUnRegistrationResult")
    public JAXBElement<Object> createBankAccountUnRegistrationResult(Object value) {
        return new JAXBElement<Object>(_BankAccountUnRegistrationResult_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BankAccountUnRegistrationFunc }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.registration.atlantique.bank.ibayad.com/", name = "bankAccountUnRegistrationFunc")
    public JAXBElement<BankAccountUnRegistrationFunc> createBankAccountUnRegistrationFunc(BankAccountUnRegistrationFunc value) {
        return new JAXBElement<BankAccountUnRegistrationFunc>(_BankAccountUnRegistrationFunc_QNAME, BankAccountUnRegistrationFunc.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BankAccountRegistrationFuncResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.registration.atlantique.bank.ibayad.com/", name = "bankAccountRegistrationFuncResponse")
    public JAXBElement<BankAccountRegistrationFuncResponse> createBankAccountRegistrationFuncResponse(BankAccountRegistrationFuncResponse value) {
        return new JAXBElement<BankAccountRegistrationFuncResponse>(_BankAccountRegistrationFuncResponse_QNAME, BankAccountRegistrationFuncResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BankAccountRegistration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.registration.atlantique.bank.ibayad.com/", name = "BankAccountRegistration")
    public JAXBElement<BankAccountRegistration> createBankAccountRegistration(BankAccountRegistration value) {
        return new JAXBElement<BankAccountRegistration>(_BankAccountRegistration_QNAME, BankAccountRegistration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BankAccountunRegistration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.registration.atlantique.bank.ibayad.com/", name = "BankAccountUnRegistration")
    public JAXBElement<BankAccountunRegistration> createBankAccountUnRegistration(BankAccountunRegistration value) {
        return new JAXBElement<BankAccountunRegistration>(_BankAccountUnRegistration_QNAME, BankAccountunRegistration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.registration.atlantique.bank.ibayad.com/", name = "BankAccountRegistrationResult")
    public JAXBElement<Object> createBankAccountRegistrationResult(Object value) {
        return new JAXBElement<Object>(_BankAccountRegistrationResult_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BankAccountUnRegistrationFuncResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.registration.atlantique.bank.ibayad.com/", name = "bankAccountUnRegistrationFuncResponse")
    public JAXBElement<BankAccountUnRegistrationFuncResponse> createBankAccountUnRegistrationFuncResponse(BankAccountUnRegistrationFuncResponse value) {
        return new JAXBElement<BankAccountUnRegistrationFuncResponse>(_BankAccountUnRegistrationFuncResponse_QNAME, BankAccountUnRegistrationFuncResponse.class, null, value);
    }

}
