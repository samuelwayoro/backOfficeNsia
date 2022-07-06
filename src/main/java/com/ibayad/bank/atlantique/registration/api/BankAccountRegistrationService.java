
package com.ibayad.bank.atlantique.registration.api;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebService(name = "BankAccountRegistrationService", targetNamespace = "http://api.registration.atlantique.bank.ibayad.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface BankAccountRegistrationService {


    /**
     * 
     * @param bankAccountRegistration
     * @return
     *     returns java.lang.Object
     */
    @WebMethod
    @WebResult(name = "BankAccountRegistrationReponse", targetNamespace = "")
    @RequestWrapper(localName = "bankAccountRegistrationFunc", targetNamespace = "http://api.registration.atlantique.bank.ibayad.com/", className = "com.ibayad.bank.atlantique.registration.api.BankAccountRegistrationFunc")
    @ResponseWrapper(localName = "bankAccountRegistrationFuncResponse", targetNamespace = "http://api.registration.atlantique.bank.ibayad.com/", className = "com.ibayad.bank.atlantique.registration.api.BankAccountRegistrationFuncResponse")
    public Object bankAccountRegistrationFunc(
        @WebParam(name = "BankAccountRegistration", targetNamespace = "")
        BankAccountRegistration bankAccountRegistration);

    /**
     * 
     * @param bankAccountUnRegistration
     * @return
     *     returns java.lang.Object
     */
    @WebMethod
    @WebResult(name = "BankAccountUnRegistrationReponse", targetNamespace = "")
    @RequestWrapper(localName = "bankAccountUnRegistrationFunc", targetNamespace = "http://api.registration.atlantique.bank.ibayad.com/", className = "com.ibayad.bank.atlantique.registration.api.BankAccountUnRegistrationFunc")
    @ResponseWrapper(localName = "bankAccountUnRegistrationFuncResponse", targetNamespace = "http://api.registration.atlantique.bank.ibayad.com/", className = "com.ibayad.bank.atlantique.registration.api.BankAccountUnRegistrationFuncResponse")
    public Object bankAccountUnRegistrationFunc(
        @WebParam(name = "BankAccountUnRegistration", targetNamespace = "")
        BankAccountunRegistration bankAccountUnRegistration);

}