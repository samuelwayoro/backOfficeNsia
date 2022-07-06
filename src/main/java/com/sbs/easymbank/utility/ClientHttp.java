/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.utility;

import com.sbs.easymbank.entities.AbonnementsOm;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author SamySamWell
 */
public class ClientHttp {

    //methode pour verifier la double authentification entrust
    private  String username;
    private String password;
    private String client_id;
    private String client_secret;
    private String client_type;
    private String grant_type;
    private String url_token;
    private String country;
    private String url_kyc;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String getClient_type() {
        return client_type;
    }

    public void setClient_type(String client_type) {
        this.client_type = client_type;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getUrl_token() {
        return url_token;
    }

    public void setUrl_token(String url_token) {
        this.url_token = url_token;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUrl_kyc() {
        return url_kyc;
    }

    public void setUrl_kyc(String url_kyc) {
        this.url_kyc = url_kyc;
    }
    
    

    public ClientHttp() {
    }

    public ClientHttp(String username, String password, String client_id, String client_secret, String client_type, String grant_type, String url_token, String country,String url_kyc) {
        this.username = username;
        this.password = password;
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.client_type = client_type;
        this.grant_type = grant_type;
        this.url_token = url_token;
        this.country = country;
        this.url_kyc = url_kyc;
    }

    //gettoken nouveau comme sur le site et faire une simulation complète ...
    public String getToken(ClientHttp cli) throws IOException, ParseException {
        System.out.println("---- debut process get token ----");
        String access_token = "";
        String data = "";
        int code;

        System.out.println("affichage des parametres a envoyer ---");
        data = "{\"username\":\"" + cli.username + "\",\"password\":\"" + cli.password + "\",\"client_id\":\"" + cli.client_id + "\",\"client_secret\":\"" + cli.client_secret + "\",\"client_type\":\"" + cli.client_type + "\",\"grant_type\":\"" + cli.grant_type + "\",\"country\":\"" + cli.country + "\"}";
        System.out.println(data);
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost postRequest = new HttpPost(cli.getUrl_token());
        StringEntity input = new StringEntity(data);
        input.setContentType("application/json");
        //&input.
        postRequest.setEntity(input);
        System.out.println("envoi de la requette demande de  access_token");
        HttpResponse response = httpClient.execute(postRequest);
        System.out.println(" code http de la reponse ->" + response.getStatusLine().getStatusCode());

        code = response.getStatusLine().getStatusCode();

        if (code == 500) {
            //msg alerte erreur de code d'activation expiré
            access_token = "500";
        } else {
            //bon traitement
            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
            String output;

            while ((output = br.readLine()) != null) {
                data = output;
            }

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(data);
            access_token = (String) jsonObject.get("access_token");
            System.out.println("token recupere    " + access_token);
        }
        return access_token;
    }
//
//    public String getToken() throws IOException, ParseException {
//        System.out.println("GENERATION DU TOKEN VIA API DE WIZALL ");
//        //String username = "test";
//        String username = "basn";
//        //String password = "jdkwq01276329021";
//        String password = "4563638726HD";
//        //String client_id = "jdsjkw9021843092-02198322332n";
//        String client_id = "2hAeTx73ADDzqfnhdEQ5uEMZQTpA6jZhqIPVzZg9";
//        //String client_secret = "WTwiygwawd3d3d32erfnBk2dHiwVrP4nW6Ip2EBXyuZLHAJ14tDx7a490LKQvdkMiBIiAtY3RRXmMMU11zSKEPzu88ewwwefrfewmauPEvJdo4VeVhGTkwahJeXhZ7EKZXCd3tU";
//        String client_secret = "yBuhYUrXtJ9H8ymTDwKp09yVVRIW0Wog56FtGuA5ZPSu3qDyCfHAbkPM3IyoRiH9bzEH99JYg9xTelDSuy0SNUKKifbFKeDKQCxU3if6uJgINNzgsVo79pLLwZQSA8Y3";
//        String client_type = "public";
//        String grant_type = "password";
//        //String url_token = "https://testwpay.wizall.com/o/token/";
//        String url_token = "https://testagent-api.wizall.com/token/";
//        String corps = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\",\"client_id\":\"" + client_id + "\",\"client_secret\":\"" + client_secret + "\",\"client_type\":\"" + client_type + "\",\"grant_type\":\"" + grant_type + "\"}";
//        System.out.println("DONNEES A ENVOYER A WIZALL " + corps);
//        String data = "";
//        String access_token = "";
//        try {
//            DefaultHttpClient httpClient = new DefaultHttpClient();
//            HttpPost postRequest = new HttpPost(url_token);
//            StringEntity input = new StringEntity(corps);
//            input.setContentType("application/json");
//            //&input.
//            postRequest.setEntity(input);
//            HttpResponse response = httpClient.execute(postRequest);
//            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
//            //System.out.println("---------CONTENU DU BUFFER --------------" + br.readLine());
//            //System.out.println("premiere valeur du buffeur  : "+br.readLine());
//            String output;
//            while ((output = br.readLine()) != null) {
////                    System.out.println(output);
//                data = output;
//                System.out.println("token bien recupere...");
//            }
//            JSONParser parser = new JSONParser();
//            JSONObject jsonObject = (JSONObject) parser.parse(data);
//            access_token = (String) jsonObject.get("access_token");
//            System.out.println("token recupere    " + access_token);
////            while ((output = br.readLine()) != null) {
////                System.out.println("======================>"+output);
////                JSONParser parser = new JSONParser();
////                JSONObject jsonObject= (JSONObject) parser.parse(output);
////                access_token = (String) jsonObject.get("access_token");
////                token_type = (String) jsonObject.get("token_type");
////            }
////            System.out.println("token recupere   :   "+access_token+" token_type   : "+token_type);
//        } catch (IOException | IllegalStateException e) {
//            e.printStackTrace();
//        }
//        System.out.println("FIN DE LA GENERATION DU TOKEN VIA WIZALL API");
//        return access_token;
//    }

    public Long getSubscription(String url_token, String msisdn, String account_alias, String code_activation, ClientHttp cli) throws IOException, ParseException {

        //String access_token = getToken();
        String access_token = getToken(cli);

        Long code = 604L;
        //String theCountry = "sn";

        String corps = "{\"msisdn\":\"" + msisdn + "\",\"account_alias\":\"" + account_alias + "\",\"code_activation\":\"" + code_activation + "\",\"country\":\"" + cli.getCountry() + "\"}";

        System.out.println(corps);
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost(url_token);
            postRequest.addHeader("Authorization", "Bearer " + access_token);
            postRequest.addHeader("Content-Type", "application/json");

            StringEntity input = new StringEntity(corps);
            input.setContentType("application/json");

            //&input.
            postRequest.setEntity(input);
            HttpResponse response = httpClient.execute(postRequest);
            System.out.println(" code http de la reponse ->" + response.getStatusLine().getStatusCode());
            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

            String data = "";
            String output = "";

            while ((output = br.readLine()) != null) {
                data = output;
            }
            System.out.println("le contenu " + data);

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(data);

            System.out.println("----->" + jsonObject);
            code = (Long) jsonObject.get("code");

            br.close();

            String message = (String) jsonObject.get("message");
            System.out.println("message du resultat  " + message + "   code  " + code);

            httpClient.getConnectionManager().shutdown();
        } catch (IOException | IllegalStateException | ParseException e) {
            return code;
        }
        return code;
    }

    public Long getUnSubscription(String url_token, String msisdn, String account_alias, String country) throws IOException, ParseException {

        String access_token = getToken(this);
        Long code = 500L;

        //String theCountry = "sn";
        String corps = "{\"msisdn\":\"" + msisdn + "\",\"account_alias\":\"" + account_alias + "\",\"country\":\"" + country + "\"}";

        System.out.println(corps);

        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost(url_token);
            postRequest.addHeader("Authorization", "Bearer " + access_token);
            postRequest.addHeader("Content-Type", "application/json");

            StringEntity input = new StringEntity(corps);
            input.setContentType("application/json");
            //&input.
            postRequest.setEntity(input);
            HttpResponse response = httpClient.execute(postRequest);
            System.out.println(" code http de la reponse ->" + response.getStatusLine().getStatusCode());
            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

            String data = "";
            String output = "";

            while ((output = br.readLine()) != null) {
                data = output;
            }
            System.out.println("le contenu " + data);

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(data);

            System.out.println("resultat ----->" + jsonObject);
            code = (Long) jsonObject.get("code");

            String message = (String) jsonObject.get("message");
            System.out.println("message du resultat  " + message + "   code  " + code);
            br.close();

            httpClient.getConnectionManager().shutdown();
        } catch (IOException | IllegalStateException | ParseException e) {
            return code;
        }
        return code;
    }

//    public AbonnementsOm getKYC(String url_token, String msisdn, String codeActivation) throws IOException, ParseException {
//        String access_token = this.getToken();//recup du token
//        System.out.println("token genere  " + access_token);
//        AbonnementsOm abonnements = null;
//        String corps = "{\"msisdn\":\"" + msisdn + "\",\"code_activation\":\"" + codeActivation + "\",\"country\":\"" + this.country + "\"}";
//        System.out.println("NUMERO A SOUSCRIRE " + corps);
//        System.out.println("URL DE SOUSCRIPTION" + url_token);
//        System.out.println("TOKEN A UTILISER POUR LA SOUSCRIPTION   " + access_token);
//
//        DefaultHttpClient httpClient = new DefaultHttpClient();
//        HttpPost postRequest = new HttpPost(url_token);
//        postRequest.addHeader("Authorization", "Bearer " + access_token);
//        postRequest.addHeader("Content-Type", "application/json");
//
//        StringEntity input = new StringEntity(corps);
//        input.setContentType("application/json");
//        postRequest.setEntity(input);
//        HttpResponse response = httpClient.execute(postRequest);
//        BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
//        String output = "";
//        String data = "";
//        while ((output = br.readLine()) != null) {
//            data = output;
//            System.out.println("information bien recuperees");
//        }
//        JSONParser parser = new JSONParser();
//        JSONObject jsonObject = (JSONObject) parser.parse(data);
//        System.out.println("affichage des donnees en json  : " + jsonObject.toString());
//        abonnements = new AbonnementsOm();
//        abonnements.setMsisdn((String) jsonObject.get("phone"));
//        abonnements.setNom((String) jsonObject.get("first_name"));
//        abonnements.setPrenoms((String) jsonObject.get("last_name"));
//        abonnements.setCni((String) jsonObject.get("cni"));
//        abonnements.setStatus((String) String.valueOf(jsonObject.get("status")));
//        System.out.println("affichage des donnees attribuees a l_objet abonnements  " + abonnements.getNom() + " " + abonnements.getPrenoms() + " " + abonnements.getDateNaissance());
//        return abonnements;
//    }
    //getKYC parametré
    public AbonnementsOm getKYC(String url_kyc, String msisdn, String codeActivation, ClientHttp cli ) throws UnsupportedEncodingException, IOException, ParseException {
        String access_token = this.getToken(cli);//recup du token
        AbonnementsOm abonnements = null;
          String output = "";
            String data = "";

        if (access_token.equalsIgnoreCase("500")) {
            System.out.println("erreur de connexion a lapi");
            return abonnements;
        } else {
            System.out.println("token genere  " + access_token);
            String corps = "{\"msisdn\":\"" + msisdn + "\",\"code_activation\":\"" + codeActivation + "\",\"country\":\"" + cli.country + "\"}";
            System.out.println("INFORMATION A ENVOYER A L_API     " + corps);
            System.out.println("URL DE KYC    " + url_kyc);
            System.out.println("TOKEN A UTILISER POUR LA SOUSCRIPTION   " + access_token);

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost(url_kyc);
            postRequest.addHeader("Authorization", "Bearer " + access_token);
            postRequest.addHeader("Content-Type", "application/json");

            StringEntity input = new StringEntity(corps);
            input.setContentType("application/json");
            postRequest.setEntity(input);
            HttpResponse response = httpClient.execute(postRequest);
            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
          
            while ((output = br.readLine()) != null) {
                data = output;
            }
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(data);
            System.out.println("affichage des donnees en json  : " + jsonObject.toString());
            abonnements = new AbonnementsOm();
            abonnements.setMsisdn((String) jsonObject.get("phone"));
            abonnements.setNom((String) jsonObject.get("first_name"));
            abonnements.setPrenoms((String) jsonObject.get("last_name"));
            abonnements.setCni((String) jsonObject.get("cni"));
            abonnements.setStatus((String) String.valueOf(jsonObject.get("status")));
            System.out.println("affichage des donnees attribuees a l_objet abonnements  " + abonnements.getNom() + " " + abonnements.getPrenoms() + " " + abonnements.getDateNaissance());
            return abonnements;
        }
    }

    public String userDetails(String url_token, String msisdn, String activationKey) {

        String corps = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:rec=\"http://receiveWebService.mmoney.com/\">\n"
                + "   <soapenv:Header/>\n"
                + "   <soapenv:Body>\n"
                + "      <rec:userDetails>\n"
                + "         <keyOperator>" + activationKey + "</keyOperator>\n"
                + "         <msisdn>" + msisdn + "</msisdn>\n"
                + "      </rec:userDetails>\n"
                + "   </soapenv:Body>\n"
                + "</soapenv:Envelope>";

        System.out.println(corps);

        System.out.println(url_token);
        String reponse = "";

        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost(url_token);

            StringEntity input = new StringEntity(corps);
            input.setContentType("application/xml");
            //&input.

            postRequest.setEntity(input);
            HttpResponse response = httpClient.execute(postRequest);

            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

            String output;
            StringBuffer tmp = new StringBuffer();
            while ((output = br.readLine()) != null) {

                tmp.append(output);
                tmp.append("\n");
            }

            System.out.println(tmp);

            if (!tmp.toString().contains("<soapenv:Body> </soapenv:Body>")) {

                String temporaire = tmp.substring(tmp.indexOf("<rec:userDetailsResponse>"), tmp.indexOf("</soapenv:Body>")).trim();
                reponse = temporaire.toString().replaceAll("rec:userDetailsResponse", "userDetailsResponse");

            }
            System.out.println(reponse);
            //System.out.println(access_token);
        } catch (IOException e) {
        }
        return reponse;
    }

    public String bankAccountDLink(String url_token, String msisdn, String accountAlias) {

        String corps = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:rec=\"http://receiveWebService.mmoney.com/\">\n"
                + "   <soapenv:Header/>\n"
                + "   <soapenv:Body>\n"
                + "      <rec:bankAccountDLink>\n"
                + "         <type>BKDREG</type>\n"
                + "         <msisdn>" + msisdn + "</msisdn>\n"
                + "         <bankName>ATLANTIC</bankName>\n"
                + "         <accountAlias>" + accountAlias + "</accountAlias>\n"
                + "         <cifId></cifId>\n"
                + "         <userRole></userRole>\n"
                + "      </rec:bankAccountDLink>\n"
                + "   </soapenv:Body>\n"
                + "</soapenv:Envelope>";

        System.out.println(corps);

        System.out.println(url_token);
        String reponse = "";

        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost(url_token);

            StringEntity input = new StringEntity(corps);
            input.setContentType("application/xml");
            //&input.

            postRequest.setEntity(input);
            HttpResponse response = httpClient.execute(postRequest);

            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

            String output;
            StringBuffer tmp = new StringBuffer();
            while ((output = br.readLine()) != null) {

                tmp.append(output);
                tmp.append("\n");
            }

            System.out.println(tmp);

            if (!tmp.toString().contains("<soapenv:Body> </soapenv:Body>")) {

                String temporaire = tmp.substring(tmp.indexOf("<rec:bankAccountDLinkResponse>"), tmp.indexOf("</soapenv:Body>")).trim();
                reponse = temporaire.toString().replaceAll("rec:bankAccountDLinkResponse", "bankAccountDLinkResponse");

            }
            System.out.println(reponse);
            //System.out.println(access_token);
        } catch (IOException e) {
        }
        return reponse;
    }

    public String bankAccountLink(String url_token, String msisdn, String accountAlias, String otp) {

        String corps = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:rec=\"http://receiveWebService.mmoney.com/\">\n"
                + "   <soapenv:Header/>\n"
                + "   <soapenv:Body>\n"
                + "      <rec:bankAccountLink>\n"
                + "         <type>BANKREGC</type>\n"
                + "         <msisdn>" + msisdn + "</msisdn>\n"
                + "         <bankName>ATLANTIC</bankName>\n"
                + "         <accountAlias>" + accountAlias + "</accountAlias>\n"
                + "         <cifId></cifId>\n"
                + "         <userRole></userRole>\n"
                + "         <otp>" + otp + "</otp>\n"
                + "      </rec:bankAccountLink>\n"
                + "   </soapenv:Body>\n"
                + "</soapenv:Envelope>";

        System.out.println(corps);

        System.out.println(url_token);
        String reponse = "";

        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost(url_token);

            StringEntity input = new StringEntity(corps);
            input.setContentType("application/xml");
            //&input.

            postRequest.setEntity(input);
            HttpResponse response = httpClient.execute(postRequest);

            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

            String output;
            StringBuffer tmp = new StringBuffer();
            while ((output = br.readLine()) != null) {

                tmp.append(output);
                tmp.append("\n");
            }

            System.out.println(tmp);

            if (!tmp.toString().contains("<soapenv:Body> </soapenv:Body>")) {

                String temporaire = tmp.substring(tmp.indexOf("<rec:bankAccountLinkResponse>"), tmp.indexOf("</soapenv:Body>")).trim();
                reponse = temporaire.toString().replaceAll("rec:bankAccountLinkResponse", "bankAccountLinkResponse");

            }
            System.out.println(reponse);
            //System.out.println(access_token);
        } catch (IOException e) {
        }
        return reponse;
    }

}
