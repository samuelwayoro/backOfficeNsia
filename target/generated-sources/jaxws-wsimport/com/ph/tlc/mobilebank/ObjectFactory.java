
package com.ph.tlc.mobilebank;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.ph.tlc.mobilebank package. 
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

    private final static QName _GetKYCRequest_QNAME = new QName("http://mobilebank.tlc.ph.com/", "getKYCRequest");
    private final static QName _LinkAccountRequest_QNAME = new QName("http://mobilebank.tlc.ph.com/", "linkAccountRequest");
    private final static QName _DelinkAccountRequest_QNAME = new QName("http://mobilebank.tlc.ph.com/", "delinkAccountRequest");
    private final static QName _DelinkAccountRequestResponse_QNAME = new QName("http://mobilebank.tlc.ph.com/", "delinkAccountRequestResponse");
    private final static QName _LinkAccountRequestResponse_QNAME = new QName("http://mobilebank.tlc.ph.com/", "linkAccountRequestResponse");
    private final static QName _GetKYCRequestResponse_QNAME = new QName("http://mobilebank.tlc.ph.com/", "getKYCRequestResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.ph.tlc.mobilebank
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetKYCRequest }
     * 
     */
    public GetKYCRequest createGetKYCRequest() {
        return new GetKYCRequest();
    }

    /**
     * Create an instance of {@link LinkAccountRequest }
     * 
     */
    public LinkAccountRequest createLinkAccountRequest() {
        return new LinkAccountRequest();
    }

    /**
     * Create an instance of {@link DelinkAccountRequest }
     * 
     */
    public DelinkAccountRequest createDelinkAccountRequest() {
        return new DelinkAccountRequest();
    }

    /**
     * Create an instance of {@link DelinkAccountRequestResponse }
     * 
     */
    public DelinkAccountRequestResponse createDelinkAccountRequestResponse() {
        return new DelinkAccountRequestResponse();
    }

    /**
     * Create an instance of {@link LinkAccountRequestResponse }
     * 
     */
    public LinkAccountRequestResponse createLinkAccountRequestResponse() {
        return new LinkAccountRequestResponse();
    }

    /**
     * Create an instance of {@link GetKYCRequestResponse }
     * 
     */
    public GetKYCRequestResponse createGetKYCRequestResponse() {
        return new GetKYCRequestResponse();
    }

    /**
     * Create an instance of {@link StandardResp }
     * 
     */
    public StandardResp createStandardResp() {
        return new StandardResp();
    }

    /**
     * Create an instance of {@link LinkAccountRequest2 }
     * 
     */
    public LinkAccountRequest2 createLinkAccountRequest2() {
        return new LinkAccountRequest2();
    }

    /**
     * Create an instance of {@link AccountInfoRequest }
     * 
     */
    public AccountInfoRequest createAccountInfoRequest() {
        return new AccountInfoRequest();
    }

    /**
     * Create an instance of {@link DelinkAccountRequest2 }
     * 
     */
    public DelinkAccountRequest2 createDelinkAccountRequest2() {
        return new DelinkAccountRequest2();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetKYCRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://mobilebank.tlc.ph.com/", name = "getKYCRequest")
    public JAXBElement<GetKYCRequest> createGetKYCRequest(GetKYCRequest value) {
        return new JAXBElement<GetKYCRequest>(_GetKYCRequest_QNAME, GetKYCRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LinkAccountRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://mobilebank.tlc.ph.com/", name = "linkAccountRequest")
    public JAXBElement<LinkAccountRequest> createLinkAccountRequest(LinkAccountRequest value) {
        return new JAXBElement<LinkAccountRequest>(_LinkAccountRequest_QNAME, LinkAccountRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DelinkAccountRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://mobilebank.tlc.ph.com/", name = "delinkAccountRequest")
    public JAXBElement<DelinkAccountRequest> createDelinkAccountRequest(DelinkAccountRequest value) {
        return new JAXBElement<DelinkAccountRequest>(_DelinkAccountRequest_QNAME, DelinkAccountRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DelinkAccountRequestResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://mobilebank.tlc.ph.com/", name = "delinkAccountRequestResponse")
    public JAXBElement<DelinkAccountRequestResponse> createDelinkAccountRequestResponse(DelinkAccountRequestResponse value) {
        return new JAXBElement<DelinkAccountRequestResponse>(_DelinkAccountRequestResponse_QNAME, DelinkAccountRequestResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LinkAccountRequestResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://mobilebank.tlc.ph.com/", name = "linkAccountRequestResponse")
    public JAXBElement<LinkAccountRequestResponse> createLinkAccountRequestResponse(LinkAccountRequestResponse value) {
        return new JAXBElement<LinkAccountRequestResponse>(_LinkAccountRequestResponse_QNAME, LinkAccountRequestResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetKYCRequestResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://mobilebank.tlc.ph.com/", name = "getKYCRequestResponse")
    public JAXBElement<GetKYCRequestResponse> createGetKYCRequestResponse(GetKYCRequestResponse value) {
        return new JAXBElement<GetKYCRequestResponse>(_GetKYCRequestResponse_QNAME, GetKYCRequestResponse.class, null, value);
    }

}
