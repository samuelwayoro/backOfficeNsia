/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.utility;

import com.sbs.easymbank.entities.AbonnementsOm;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SchemeSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ClientHttpWizall {

    public String getToken() {
        System.out.println("DEBUT DEMANDE DE TOKEN");
        String username = "baci";
        String password = "baciBcQ!6o6UP8emz}";
        String client_id = "xhm23B3GG38a9Y3GbX9cEbPcW6jvdlCAri84doei";
        String client_secret = "D7SmKZlXo8kM1qE4GinznCQ0Vzkbpkw19ma7rssEsKAibJRYGStXhrDSMD12EVsn0q78Ynz92GtOR7TcjcoF3EH1UFMKWOrNz9EovsKuAB0We1qwgSN0Wa8bCbbkE1IS";
        String client_type = "public";
        String grant_type = "password";
        String url_token = "https://agent-api.wizall.com/token/";
        String country = "ci";
        String corps = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\",\"client_id\":\"" + client_id + "\",\"client_secret\":\"" + client_secret + "\",\"client_type\":\"" + client_type + "\",\"grant_type\":\"" + grant_type + "\",\"country\":\"" + country + "\"}";

        System.out.println("INFORMATION POUR LE TOKEN  : " + corps);
        String access_token;
        try {
            SchemeRegistry schemeRegistry = new SchemeRegistry();
            schemeRegistry.register(new Scheme("http", 80, (SchemeSocketFactory) PlainSocketFactory.getSocketFactory()));
            schemeRegistry.register(new Scheme("https", 443, (SchemeSocketFactory) new MockSSLSocketFactory()));
            SingleClientConnManager singleClientConnManager = new SingleClientConnManager(schemeRegistry);
            DefaultHttpClient httpClient = new DefaultHttpClient((ClientConnectionManager) singleClientConnManager);
            HttpPost postRequest = new HttpPost(url_token);
            StringEntity input = new StringEntity(corps);
            input.setContentType("application/json");
            postRequest.setEntity((HttpEntity) input);
            HttpResponse response = httpClient.execute((HttpUriRequest) postRequest);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
                String output;
                String data = "";
                while ((output = br.readLine()) != null) {
                    data = output;
                }
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(data);
                JSONObject jsonObject = (JSONObject) obj;
                access_token = (String) jsonObject.get("access_token");
                System.out.println("token recu :    " + access_token);
            }
            httpClient.getConnectionManager().shutdown();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }
        System.out.println("FIN DEMANDE DE TOKEN");
        return access_token;
    }

    public Long getSubscription(String url_token, String msisdn, String account_alias, String code_activation) {
        String kill_url = url_token;

        System.out.println("DEBUT DE DEMANDE DE SOUSCRIPTION");
        String access_token = getToken();
        System.out.println("token obtenu pour la souscription : " + access_token);
        String url = "https://agent-api.wizall.com/api/atlantic/subscription";
        Long code = 604L;
        String pays = "ci";
        String corps = "{\"msisdn\":\"" + msisdn + "\",\"account_alias\":\"" + account_alias + "\",\"code_activation\":\"" + code_activation + "\",\"country\":\"" + pays + "\"}";

        System.out.println(corps);
        try {
            SchemeRegistry schemeRegistry = new SchemeRegistry();
            schemeRegistry.register(new Scheme("http", 80, (SchemeSocketFactory) PlainSocketFactory.getSocketFactory()));
            schemeRegistry.register(new Scheme("https", 443, (SchemeSocketFactory) new MockSSLSocketFactory()));
            SingleClientConnManager singleClientConnManager = new SingleClientConnManager(schemeRegistry);
            DefaultHttpClient httpClient = new DefaultHttpClient((ClientConnectionManager) singleClientConnManager);
            HttpPost postRequest = new HttpPost(url);
            postRequest.addHeader("Authorization", "Bearer " + access_token);
            postRequest.addHeader("Content-Type", "application/json");
            StringEntity input = new StringEntity(corps);
            input.setContentType("application/json");
            postRequest.setEntity((HttpEntity) input);
            HttpResponse response = httpClient.execute((HttpUriRequest) postRequest);
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String output;
            String data = "";
            while ((output = br.readLine()) != null) {
                data = output;
            }
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(data);
            JSONObject jsonObject = (JSONObject) obj;
            code = (Long) jsonObject.get("code");
            String message = (String) jsonObject.get("message");
            System.out.println("resultat de demande de souscription :   code " + code + "message  : " + message);
            br.close();
            httpClient.getConnectionManager().shutdown();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return code;
        }
        return code;
    }

    public String getUnSubscription(String url_token, String msisdn, String account_alias) {
        String kill_url = url_token;
        System.out.println("DEBUT DEMANDE DE DESOUSCRIPTION");
        String access_token = getToken();
        System.out.println("token recu pour le desouscription  : " + access_token);
        String url = "https://agent-api.wizall.com/api/atlantic/unsubscription";
        String code = "500";
        String pays = "ci";
        String corps = "{\"msisdn\":\"" + msisdn + "\",\"account_alias\":\"" + account_alias + "\",\"country\":\"" + pays + "\"}";

        
        System.out.println("information pour la desouscription : " + corps);
        try {
            SchemeRegistry schemeRegistry = new SchemeRegistry();
            schemeRegistry.register(new Scheme("http", 80, (SchemeSocketFactory) PlainSocketFactory.getSocketFactory()));
            schemeRegistry.register(new Scheme("https", 443, (SchemeSocketFactory) new MockSSLSocketFactory()));
            SingleClientConnManager singleClientConnManager = new SingleClientConnManager(schemeRegistry);
            DefaultHttpClient httpClient = new DefaultHttpClient((ClientConnectionManager) singleClientConnManager);
            HttpPost postRequest = new HttpPost(url);
            postRequest.addHeader("Authorization", "Bearer " + access_token);
            postRequest.addHeader("Content-Type", "application/json");
            StringEntity input = new StringEntity(corps);
            input.setContentType("application/json");
            postRequest.setEntity((HttpEntity) input);
            HttpResponse response = httpClient.execute((HttpUriRequest) postRequest);
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String output;
            String data = "";
            while ((output = br.readLine()) != null) {
                data = output;
            }
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(data);
            JSONObject jsonObject = (JSONObject) obj;
            code = String.valueOf(jsonObject.get("code"));
            String message = (String) jsonObject.get("message");
            System.out.println("resultat de desouscription : code  " + code + "message : " + message);
            br.close();
            System.out.println(code);
            httpClient.getConnectionManager().shutdown();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return code;
        }
        System.out.println("FIN  DE DESOUSCRIPTION ");
        return code;
    }

    public AbonnementsOm getKYC(String url_token, String msisdn, String code_activation) {
        String kill_url_token = url_token;
        System.out.println("DEBUT DEMANDE KYC ");
        String url = "https://agent-api.wizall.com/api/atlantic/kyc";
        String access_token = getToken();
        System.out.println("token obtenu pour le kyc : " + access_token);
        AbonnementsOm abonnements = null;
        String pays = "ci";
        String corps = "{\"msisdn\":\"" + msisdn + "\",\"code_activation\":\"" + code_activation + "\",\"country\":\"" + pays + "\"}";

        System.out.println("INFORMATION A ENVOYEE POUR LE KYC : " + corps);
        try {
            System.out.println("DEBUT DEMANDE KYC ");
            SchemeRegistry schemeRegistry = new SchemeRegistry();
            schemeRegistry.register(new Scheme("http", 80, (SchemeSocketFactory) PlainSocketFactory.getSocketFactory()));
            schemeRegistry.register(new Scheme("https", 443, (SchemeSocketFactory) new MockSSLSocketFactory()));
            SingleClientConnManager singleClientConnManager = new SingleClientConnManager(schemeRegistry);
            DefaultHttpClient httpClient = new DefaultHttpClient((ClientConnectionManager) singleClientConnManager);
            HttpPost postRequest = new HttpPost(url);
            postRequest.addHeader("Authorization", "Bearer " + access_token);
            postRequest.addHeader("Content-Type", "application/json");
            StringEntity input = new StringEntity(corps);
            input.setContentType("application/json");
            postRequest.setEntity((HttpEntity) input);
            HttpResponse response = httpClient.execute((HttpUriRequest) postRequest);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
                String output;
                String data = "";
                while ((output = br.readLine()) != null) {
                    data = output;
                }
                System.out.println("resultat KYC  " + data);
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(data);
                JSONObject jsonObject = (JSONObject) obj;
                abonnements = new AbonnementsOm();
                abonnements.setMsisdn((String) jsonObject.get("phone"));
                abonnements.setNom((String) jsonObject.get("first_name"));
                abonnements.setPrenoms((String) jsonObject.get("last_name"));
                abonnements.setCni((String) jsonObject.get("cni"));
                abonnements.setStatus(String.valueOf(jsonObject.get("status")));
                abonnements.setNationalite((String) jsonObject.get("country"));
            }
            httpClient.getConnectionManager().shutdown();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return abonnements;
        }
        System.out.println("FIN DEMANDE KYC");
        return abonnements;
    }
}
