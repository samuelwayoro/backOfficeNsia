
package com.mmoney.receivewebservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.mmoney.receivewebservice package. 
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

    private final static QName _BankAccountLink_QNAME = new QName("http://receiveWebService.mmoney.com/", "bankAccountLink");
    private final static QName _BankAccountLinkResponse_QNAME = new QName("http://receiveWebService.mmoney.com/", "bankAccountLinkResponse");
    private final static QName _BankAccountDLinkResponse_QNAME = new QName("http://receiveWebService.mmoney.com/", "bankAccountDLinkResponse");
    private final static QName _UserDetailsResponse_QNAME = new QName("http://receiveWebService.mmoney.com/", "userDetailsResponse");
    private final static QName _BankAccountDLink_QNAME = new QName("http://receiveWebService.mmoney.com/", "bankAccountDLink");
    private final static QName _MuleException_QNAME = new QName("http://receiveWebService.mmoney.com/", "MuleException");
    private final static QName _UserDetails_QNAME = new QName("http://receiveWebService.mmoney.com/", "userDetails");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.mmoney.receivewebservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BankAccountLink }
     * 
     */
    public BankAccountLink createBankAccountLink() {
        return new BankAccountLink();
    }

    /**
     * Create an instance of {@link BankAccountLinkResponse }
     * 
     */
    public BankAccountLinkResponse createBankAccountLinkResponse() {
        return new BankAccountLinkResponse();
    }

    /**
     * Create an instance of {@link BankAccountDLinkResponse }
     * 
     */
    public BankAccountDLinkResponse createBankAccountDLinkResponse() {
        return new BankAccountDLinkResponse();
    }

    /**
     * Create an instance of {@link UserDetailsResponse }
     * 
     */
    public UserDetailsResponse createUserDetailsResponse() {
        return new UserDetailsResponse();
    }

    /**
     * Create an instance of {@link BankAccountDLink }
     * 
     */
    public BankAccountDLink createBankAccountDLink() {
        return new BankAccountDLink();
    }

    /**
     * Create an instance of {@link UserDetails }
     * 
     */
    public UserDetails createUserDetails() {
        return new UserDetails();
    }

    /**
     * Create an instance of {@link MuleException }
     * 
     */
    public MuleException createMuleException() {
        return new MuleException();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BankAccountLink }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://receiveWebService.mmoney.com/", name = "bankAccountLink")
    public JAXBElement<BankAccountLink> createBankAccountLink(BankAccountLink value) {
        return new JAXBElement<BankAccountLink>(_BankAccountLink_QNAME, BankAccountLink.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BankAccountLinkResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://receiveWebService.mmoney.com/", name = "bankAccountLinkResponse")
    public JAXBElement<BankAccountLinkResponse> createBankAccountLinkResponse(BankAccountLinkResponse value) {
        return new JAXBElement<BankAccountLinkResponse>(_BankAccountLinkResponse_QNAME, BankAccountLinkResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BankAccountDLinkResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://receiveWebService.mmoney.com/", name = "bankAccountDLinkResponse")
    public JAXBElement<BankAccountDLinkResponse> createBankAccountDLinkResponse(BankAccountDLinkResponse value) {
        return new JAXBElement<BankAccountDLinkResponse>(_BankAccountDLinkResponse_QNAME, BankAccountDLinkResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserDetailsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://receiveWebService.mmoney.com/", name = "userDetailsResponse")
    public JAXBElement<UserDetailsResponse> createUserDetailsResponse(UserDetailsResponse value) {
        return new JAXBElement<UserDetailsResponse>(_UserDetailsResponse_QNAME, UserDetailsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BankAccountDLink }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://receiveWebService.mmoney.com/", name = "bankAccountDLink")
    public JAXBElement<BankAccountDLink> createBankAccountDLink(BankAccountDLink value) {
        return new JAXBElement<BankAccountDLink>(_BankAccountDLink_QNAME, BankAccountDLink.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MuleException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://receiveWebService.mmoney.com/", name = "MuleException")
    public JAXBElement<MuleException> createMuleException(MuleException value) {
        return new JAXBElement<MuleException>(_MuleException_QNAME, MuleException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserDetails }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://receiveWebService.mmoney.com/", name = "userDetails")
    public JAXBElement<UserDetails> createUserDetails(UserDetails value) {
        return new JAXBElement<UserDetails>(_UserDetails_QNAME, UserDetails.class, null, value);
    }

}
