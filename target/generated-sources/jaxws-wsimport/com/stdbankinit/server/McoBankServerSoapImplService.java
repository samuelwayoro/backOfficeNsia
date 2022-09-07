
package com.stdbankinit.server;

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
@WebServiceClient(name = "McoBankServerSoapImplService", targetNamespace = "http://server.stdbankinit.com/", wsdlLocation = "file:/D:/apache_netbeans_projects/EasyMBankNSIA/src/wsdl/MCOB2A.wsdl")
public class McoBankServerSoapImplService
    extends Service
{

    private final static URL MCOBANKSERVERSOAPIMPLSERVICE_WSDL_LOCATION;
    private final static WebServiceException MCOBANKSERVERSOAPIMPLSERVICE_EXCEPTION;
    private final static QName MCOBANKSERVERSOAPIMPLSERVICE_QNAME = new QName("http://server.stdbankinit.com/", "McoBankServerSoapImplService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/D:/apache_netbeans_projects/EasyMBankNSIA/src/wsdl/MCOB2A.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        MCOBANKSERVERSOAPIMPLSERVICE_WSDL_LOCATION = url;
        MCOBANKSERVERSOAPIMPLSERVICE_EXCEPTION = e;
    }

    public McoBankServerSoapImplService() {
        super(__getWsdlLocation(), MCOBANKSERVERSOAPIMPLSERVICE_QNAME);
    }

    public McoBankServerSoapImplService(WebServiceFeature... features) {
        super(__getWsdlLocation(), MCOBANKSERVERSOAPIMPLSERVICE_QNAME, features);
    }

    public McoBankServerSoapImplService(URL wsdlLocation) {
        super(wsdlLocation, MCOBANKSERVERSOAPIMPLSERVICE_QNAME);
    }

    public McoBankServerSoapImplService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, MCOBANKSERVERSOAPIMPLSERVICE_QNAME, features);
    }

    public McoBankServerSoapImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public McoBankServerSoapImplService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns MCOB2ASoap
     */
    @WebEndpoint(name = "McoBankServerSoapImplPort")
    public MCOB2ASoap getMcoBankServerSoapImplPort() {
        return super.getPort(new QName("http://server.stdbankinit.com/", "McoBankServerSoapImplPort"), MCOB2ASoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns MCOB2ASoap
     */
    @WebEndpoint(name = "McoBankServerSoapImplPort")
    public MCOB2ASoap getMcoBankServerSoapImplPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://server.stdbankinit.com/", "McoBankServerSoapImplPort"), MCOB2ASoap.class, features);
    }

    private static URL __getWsdlLocation() {
        if (MCOBANKSERVERSOAPIMPLSERVICE_EXCEPTION!= null) {
            throw MCOBANKSERVERSOAPIMPLSERVICE_EXCEPTION;
        }
        return MCOBANKSERVERSOAPIMPLSERVICE_WSDL_LOCATION;
    }

}
