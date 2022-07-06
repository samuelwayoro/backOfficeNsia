
package com.sbs.easymbank.service.other;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sbs.easymbank.service.other package. 
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

    private final static QName _GetSignaletique_QNAME = new QName("http://other.easymbank.sbs.com/", "getSignaletique");
    private final static QName _GetSignaletiqueResponse_QNAME = new QName("http://other.easymbank.sbs.com/", "getSignaletiqueResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sbs.easymbank.service.other
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetSignaletiqueResponse }
     * 
     */
    public GetSignaletiqueResponse createGetSignaletiqueResponse() {
        return new GetSignaletiqueResponse();
    }

    /**
     * Create an instance of {@link GetSignaletique }
     * 
     */
    public GetSignaletique createGetSignaletique() {
        return new GetSignaletique();
    }

    /**
     * Create an instance of {@link Comptes }
     * 
     */
    public Comptes createComptes() {
        return new Comptes();
    }

    /**
     * Create an instance of {@link Client }
     * 
     */
    public Client createClient() {
        return new Client();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSignaletique }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://other.easymbank.sbs.com/", name = "getSignaletique")
    public JAXBElement<GetSignaletique> createGetSignaletique(GetSignaletique value) {
        return new JAXBElement<GetSignaletique>(_GetSignaletique_QNAME, GetSignaletique.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSignaletiqueResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://other.easymbank.sbs.com/", name = "getSignaletiqueResponse")
    public JAXBElement<GetSignaletiqueResponse> createGetSignaletiqueResponse(GetSignaletiqueResponse value) {
        return new JAXBElement<GetSignaletiqueResponse>(_GetSignaletiqueResponse_QNAME, GetSignaletiqueResponse.class, null, value);
    }

}
