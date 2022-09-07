
package mmwservice;

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
@WebServiceClient(name = "MMWServiceService", targetNamespace = "http://mmwservice/", wsdlLocation = "file:/D:/apache_netbeans_projects/EasyMBankNSIA/src/wsdl/newWsdl3.wsdl")
public class MMWServiceService
    extends Service
{

    private final static URL MMWSERVICESERVICE_WSDL_LOCATION;
    private final static WebServiceException MMWSERVICESERVICE_EXCEPTION;
    private final static QName MMWSERVICESERVICE_QNAME = new QName("http://mmwservice/", "MMWServiceService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/D:/apache_netbeans_projects/EasyMBankNSIA/src/wsdl/newWsdl3.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        MMWSERVICESERVICE_WSDL_LOCATION = url;
        MMWSERVICESERVICE_EXCEPTION = e;
    }

    public MMWServiceService() {
        super(__getWsdlLocation(), MMWSERVICESERVICE_QNAME);
    }

    public MMWServiceService(WebServiceFeature... features) {
        super(__getWsdlLocation(), MMWSERVICESERVICE_QNAME, features);
    }

    public MMWServiceService(URL wsdlLocation) {
        super(wsdlLocation, MMWSERVICESERVICE_QNAME);
    }

    public MMWServiceService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, MMWSERVICESERVICE_QNAME, features);
    }

    public MMWServiceService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public MMWServiceService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns MMWService
     */
    @WebEndpoint(name = "MMWServicePort")
    public MMWService getMMWServicePort() {
        return super.getPort(new QName("http://mmwservice/", "MMWServicePort"), MMWService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns MMWService
     */
    @WebEndpoint(name = "MMWServicePort")
    public MMWService getMMWServicePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://mmwservice/", "MMWServicePort"), MMWService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (MMWSERVICESERVICE_EXCEPTION!= null) {
            throw MMWSERVICESERVICE_EXCEPTION;
        }
        return MMWSERVICESERVICE_WSDL_LOCATION;
    }

}
