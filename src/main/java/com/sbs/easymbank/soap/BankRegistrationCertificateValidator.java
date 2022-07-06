/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.soap;

import com.ph.tlc.mobilebank.BankRegistrationService;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

/**
 *
 * @author alex
 */
public class BankRegistrationCertificateValidator /*extends BankRegistrationService */{

    static {
//        sslHandShake();
    }
    public static String CertificatePath /*= "C:\\baci.p12" */;
    public static String CertificatePassword /* = "P@ssw0rdBA" */;
    public static String requestIp /*= "10.177.24.77" */;

//    public BankRegistrationCertificateValidator(URL wsdlLocation) {
//        super(wsdlLocation);
//    }

    private static void disableSslVerification() {
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts;
            trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }

            }
            };

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    public static void turnOffCertificateValidation() {
        try {
            // HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(new FileInputStream(new File(CertificatePath)), CertificatePassword.toCharArray());

            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, CertificatePassword.toCharArray());

            X509TrustManager tm = new X509TrustManager() {

                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };

            SSLContext context = SSLContext.getInstance("TLS");
            context.init(kmf.getKeyManagers(), new TrustManager[]{tm}, null);

            SSLSocketFactory sockFactory = context.getSocketFactory();

            HttpsURLConnection.setDefaultSSLSocketFactory(sockFactory);

            HttpsURLConnection.setDefaultHostnameVerifier(new javax.net.ssl.HostnameVerifier() {

                public boolean verify(String hostname, javax.net.ssl.SSLSession sslSession) {
//                    if (hostname.equals(requestIp)) {
//                        return true;
//                    }
                    return true;
                }
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        
    }
}


