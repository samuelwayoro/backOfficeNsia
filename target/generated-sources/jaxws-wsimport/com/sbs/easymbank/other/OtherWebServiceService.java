
package com.sbs.easymbank.other;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.8
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "OtherWebServiceService", targetNamespace = "http://other.easymbank.sbs.com/", wsdlLocation = "file:/D:/NetBeansProjects/EASYMBANK__/EasyMBankNSIA/src/wsdl/localhost_8080/B2WServiceProject/OtherWebServiceService.wsdl")
public class OtherWebServiceService
    extends Service
{

    private final static URL OTHERWEBSERVICESERVICE_WSDL_LOCATION;
    private final static WebServiceException OTHERWEBSERVICESERVICE_EXCEPTION;
    private final static QName OTHERWEBSERVICESERVICE_QNAME = new QName("http://other.easymbank.sbs.com/", "OtherWebServiceService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/D:/NetBeansProjects/EASYMBANK__/EasyMBankNSIA/src/wsdl/localhost_8080/B2WServiceProject/OtherWebServiceService.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        OTHERWEBSERVICESERVICE_WSDL_LOCATION = url;
        OTHERWEBSERVICESERVICE_EXCEPTION = e;
    }

    public OtherWebServiceService() {
        super(__getWsdlLocation(), OTHERWEBSERVICESERVICE_QNAME);
    }

    public OtherWebServiceService(WebServiceFeature... features) {
        super(__getWsdlLocation(), OTHERWEBSERVICESERVICE_QNAME, features);
    }

    public OtherWebServiceService(URL wsdlLocation) {
        super(wsdlLocation, OTHERWEBSERVICESERVICE_QNAME);
    }

    public OtherWebServiceService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, OTHERWEBSERVICESERVICE_QNAME, features);
    }

    public OtherWebServiceService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public OtherWebServiceService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns OtherWebService
     */
    @WebEndpoint(name = "OtherWebServicePort")
    public OtherWebService getOtherWebServicePort() {
        return super.getPort(new QName("http://other.easymbank.sbs.com/", "OtherWebServicePort"), OtherWebService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns OtherWebService
     */
    @WebEndpoint(name = "OtherWebServicePort")
    public OtherWebService getOtherWebServicePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://other.easymbank.sbs.com/", "OtherWebServicePort"), OtherWebService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (OTHERWEBSERVICESERVICE_EXCEPTION!= null) {
            throw OTHERWEBSERVICESERVICE_EXCEPTION;
        }
        return OTHERWEBSERVICESERVICE_WSDL_LOCATION;
    }

}
