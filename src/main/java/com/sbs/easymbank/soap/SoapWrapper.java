package com.sbs.easymbank.soap;

import com.ph.tlc.mobilebank.StandardResp;
import com.sbs.exceptions.BadSoapResponseException;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyStore;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPMessage;

import org.w3c.dom.NodeList;

public class SoapWrapper {

  public  static String CertificatePath = "C:\\baci.p12";
  public  static String CertificatePassword = "P@ssw0rdBA";
  public  static String requestURL = "https://10.177.24.77/BankRegistration";
  public  static String requestIp = "10.177.24.77";

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        String Token = "/1yoVpfHKEtF3inrNMa9sQdEs4SXnhGXm+kH/URb5RS96m+7jj3qZ0CCLSZKAx6y";
        String OperatorCode = "BACITEST";
        String Msisdn = "22501000701";
        String Code = "992425";

        System.out.println("=================================================================");
        System.out.println("REPONSE SOAP");
        System.out.println("=================================================================");
//        String soapResponse = getMsisdnKYC(Token, OperatorCode, Msisdn, Code);

   //     System.out.println(soapResponse);
        System.out.println("=================================================================");
        System.out.println("=================================================================");
        System.out.println("ATTRIBUTS");
        System.out.println("=================================================================");
   //     getResponseProprety(soapResponse, "getKYCRequest");

    }

    private static SoapWrapper anInstance;

    synchronized public static SoapWrapper getInstance() {
        if (anInstance == null) {
            anInstance = new SoapWrapper();
        }
        return anInstance;
    }

    private SoapWrapper() {

    }

    public static StandardResp getResponseProprety(String soapResponse, String property) {
        StandardResp result = new StandardResp();
        try {

            MessageFactory factory = MessageFactory.newInstance();
            SOAPMessage msg = factory.createMessage(new MimeHeaders(), new ByteArrayInputStream(soapResponse.getBytes(Charset.forName("UTF-8"))));

            SOAPBody body = msg.getSOAPBody();

            NodeList list = body.getElementsByTagName(property);

            for (int i = 0; i < list.getLength(); i++) {

                NodeList innerList = list.item(i).getChildNodes();

                for (int j = 0; j < innerList.getLength(); j++) {

                    if ("statusCode".equalsIgnoreCase(innerList.item(j).getNodeName())) {
                        System.out.println("statusCode = " + innerList.item(j).getTextContent());
                        result.setStatusCode(Integer.parseInt(innerList.item(j).getTextContent()));
                    } else if ("message".equalsIgnoreCase(innerList.item(j).getNodeName())) {
                        System.out.println("message = " + innerList.item(j).getTextContent());
                        result.setMessage(innerList.item(j).getTextContent());
                    } else if ("data".equalsIgnoreCase(innerList.item(j).getNodeName())) {
                        System.out.println("data = " + innerList.item(j).getTextContent());
                        result.setData(innerList.item(j).getTextContent());

                    }
                }
            }

        } catch (Exception ex) {
            //System.out.println(ex.getMessage());
            ex.printStackTrace();
        } finally {
            return result;
        }

    }

    public static String getMsisdnKYC(String Token, String OperatorCode, String Msisdn, String Code) throws Exception {
   //     try {

            javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(new javax.net.ssl.HostnameVerifier() {

                @Override
                public boolean verify(String hostname, javax.net.ssl.SSLSession sslSession) {
//                    if (hostname.equals(requestIp)) {
//                        return true;
//                    }
                    return true;
                }
            });

            URL url = new URL(requestURL);
            HttpsURLConnection con = connect(url);

            String data = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:mob=\"http://mobilebank.tlc.ph.com/\">"
                    + "<soapenv:Header/>"
                    + "<soapenv:Body>"
                    + "<mob:getKYCRequest>"
                    + "<AccountInfoRequest>"
                    + "<token>" + Token + "</token>"
                    + "<operatorcode>" + OperatorCode + "</operatorcode>"
                    + "<msisdn>" + Msisdn + "</msisdn>"
                    + "<code>" + Code + "</code>"
                    + "</AccountInfoRequest>"
                    + "</mob:getKYCRequest>"
                    + "</soapenv:Body>"
                    + "</soapenv:Envelope>";

            String moovResponse = getResponse(con, data);

            return moovResponse;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
    }

    private static String getResponse(HttpsURLConnection con, String data) throws IOException, BadSoapResponseException {
       System.out.println("INPUT: "+data);
        OutputStream reqStream = con.getOutputStream();
        
        BufferedReader in=null;
        reqStream.write(data.getBytes());
        //System.out.println("REAL INPUT: "+con.get);
        System.out.println("SERVER RESPONSE: "+con.getResponseCode()+" "+con.getResponseMessage());
        if(con.getResponseCode()==200)
        in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        else{
            in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            throw new BadSoapResponseException(con.getResponseCode(),con.getResponseMessage());
        }
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();
        System.out.println(response.toString());
        return response.toString();
    }

    private static HttpsURLConnection connect(URL url) {

        try {
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

            KeyStore ks = KeyStore.getInstance("PKCS12");
        // KeyStore ks = KeyStore.getInstance("JKS");
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

            con.setSSLSocketFactory(sockFactory);

            con.setRequestMethod("POST");
            con.setRequestProperty("Content-type", "text/xml; charset=utf-8");
           con.setRequestProperty("SOAPAction", "");
           con.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
           
            con.setDoOutput(true);

            return con;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String linkAccountRequest(String token, String operatorCode, String msisdn, String code, String registeredby, String accountalias, String accountname, String extdata) throws Exception {
     //   try {

            javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(new javax.net.ssl.HostnameVerifier() {

                @Override
                public boolean verify(String hostname, javax.net.ssl.SSLSession sslSession) {
//                    if (hostname.equals(requestIp)) {
//                        return true;
//                    }
                    return true;
                }
            });

            URL url = new URL(requestURL);
            HttpsURLConnection con = connect(url);

            String data = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:mob=\"http://mobilebank.tlc.ph.com/\">"+
                    "<soapenv:Header/>"+
                    "<soapenv:Body>"+
                      "<mob:linkAccountRequest>"+
                      "<LinkAccountRequest>"+
                      "<token>"+   token   +"</token>"+
                      "<operatorcode>"+   operatorCode  + "</operatorcode>"+
                      "<msisdn>"+   msisdn   +"</msisdn>"+
                      "<code>"+   code   +"</code>"+
                      "<registeredby>"+   registeredby  + "</registeredby>"+
                      "<accountalias>"+   accountalias   +"</accountalias>"+
                      "<accountname>"+   accountname   +"</accountname>"+
                      "<extdata>"+   extdata   +"</extdata>"+
                      "</LinkAccountRequest>"+
                      "</mob:linkAccountRequest>"+
                      "</soapenv:Body>"+
                      "</soapenv:Envelope>";

            System.out.println("INPUT: "+data);
            String moovResponse = getResponse(con, data);

            return moovResponse;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
       // return null;
    }
    
        public static String delinkAccountRequest(String token, String operatorCode, String msisdn, String accountalias, String delinkdate,String delinkername, String remarks) throws Exception {
//        try {

            javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(new javax.net.ssl.HostnameVerifier() {

                @Override
                public boolean verify(String hostname, javax.net.ssl.SSLSession sslSession) {
//                    if (hostname.equals(requestIp)) {
//                        return true;
//                    }
                    return true;
                }
            });

            URL url = new URL(requestURL);
            HttpsURLConnection con = connect(url);

            String data = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:mob=\"http://mobilebank.tlc.ph.com/\">"
   +"<soapenv:Header/>"
   +"<soapenv:Body>"
      +"<mob:delinkAccountRequest>"
         +"<DelinkAccountRequest>"
            +"<token>"+token+"</token>"
            +"<operatorcode>"+operatorCode+"</operatorcode>"
            +"<msisdn>"+msisdn+"</msisdn>"
            +"<accountalias>"+accountalias+"</accountalias>"
            +"<delinkdate>"+delinkdate+"</delinkdate>"
            +"<delinkername>"+delinkername+"</delinkername>"
            +"<remarks>"+remarks+"</remarks>"
         +"</DelinkAccountRequest>"
      +"</mob:delinkAccountRequest>"
   +"</soapenv:Body>"
+"</soapenv:Envelope>";

            String moovResponse = getResponse(con, data);

            return moovResponse;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
    }


}
