
package com.sbs.easymbank.service.mmapi;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sbs.easymbank.service.mmapi package. 
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


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sbs.easymbank.service.mmapi
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UnsubscriptionResponse }
     * 
     */
    public UnsubscriptionResponse createUnsubscriptionResponse() {
        return new UnsubscriptionResponse();
    }

    /**
     * Create an instance of {@link SubscriptionResponse }
     * 
     */
    public SubscriptionResponse createSubscriptionResponse() {
        return new SubscriptionResponse();
    }

    /**
     * Create an instance of {@link KyCrequestResponse }
     * 
     */
    public KyCrequestResponse createKyCrequestResponse() {
        return new KyCrequestResponse();
    }

    /**
     * Create an instance of {@link Abonnekyc }
     * 
     */
    public Abonnekyc createAbonnekyc() {
        return new Abonnekyc();
    }

}
