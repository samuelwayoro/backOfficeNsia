
package com.mmoney.receivewebservice;

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
@WebServiceClient(name = "AccLinkService", targetNamespace = "http://receiveWebService.mmoney.com/", wsdlLocation = "file:/D:/NetBeansProjects/EASYMBANK__/EasyMBankNSIA/src/wsdl/BankLinkService.wsdl")
public class AccLinkService
    extends Service
{

    private final static URL ACCLINKSERVICE_WSDL_LOCATION;
    private final static WebServiceException ACCLINKSERVICE_EXCEPTION;
    private final static QName ACCLINKSERVICE_QNAME = new QName("http://receiveWebService.mmoney.com/", "AccLinkService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/D:/NetBeansProjects/EASYMBANK__/EasyMBankNSIA/src/wsdl/BankLinkService.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        ACCLINKSERVICE_WSDL_LOCATION = url;
        ACCLINKSERVICE_EXCEPTION = e;
    }

    public AccLinkService() {
        super(__getWsdlLocation(), ACCLINKSERVICE_QNAME);
    }

    public AccLinkService(WebServiceFeature... features) {
        super(__getWsdlLocation(), ACCLINKSERVICE_QNAME, features);
    }

    public AccLinkService(URL wsdlLocation) {
        super(wsdlLocation, ACCLINKSERVICE_QNAME);
    }

    public AccLinkService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, ACCLINKSERVICE_QNAME, features);
    }

    public AccLinkService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public AccLinkService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns AccLink
     */
    @WebEndpoint(name = "AccLinkPort")
    public AccLink getAccLinkPort() {
        return super.getPort(new QName("http://receiveWebService.mmoney.com/", "AccLinkPort"), AccLink.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns AccLink
     */
    @WebEndpoint(name = "AccLinkPort")
    public AccLink getAccLinkPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://receiveWebService.mmoney.com/", "AccLinkPort"), AccLink.class, features);
    }

    private static URL __getWsdlLocation() {
        if (ACCLINKSERVICE_EXCEPTION!= null) {
            throw ACCLINKSERVICE_EXCEPTION;
        }
        return ACCLINKSERVICE_WSDL_LOCATION;
    }

}