/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.utility;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;

public class MockSSLSocketFactory extends SSLSocketFactory {
  public MockSSLSocketFactory() throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
    super(trustStrategy, hostnameVerifier);
  }
  
  private static final X509HostnameVerifier hostnameVerifier = (X509HostnameVerifier)new Object();
  
  private static final TrustStrategy trustStrategy = (TrustStrategy)new Object();
}
