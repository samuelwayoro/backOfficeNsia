/**
 * RegisterService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sbs.easymbank.service.omapi;

public interface RegisterService extends javax.xml.rpc.Service {

/**
 * Service d'inscription OM-Banking
 */
    public java.lang.String getRegisterPortAddress();

    public com.sbs.easymbank.service.omapi.RegisterPort_PortType getRegisterPort() throws javax.xml.rpc.ServiceException;

    public com.sbs.easymbank.service.omapi.RegisterPort_PortType getRegisterPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
