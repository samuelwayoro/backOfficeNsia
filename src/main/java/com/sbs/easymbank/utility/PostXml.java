/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.utility;

import gw.bao.becwapi.domain.HttpClientResponse;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author samuel
 * classe permettant de poster les requetes vers l'environnement de MTN GUINNEE BISSAU 
 */
public class PostXml{
    //methode de post d'objet xml over http 
    public static HttpClientResponse postXml(String url, String payload) {
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            int timeout = 8000;
            // connection settings
            con.setConnectTimeout(timeout);
            con.setReadTimeout(timeout);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(payload);
            wr.flush();
            wr.close();
            HttpClientResponse res = new HttpClientResponse();
            int responseCode = con.getResponseCode();
            res.setHttpResponseCode(responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
//				if (con.getHeaderField("Content-Length").equals("0")) {
//					res.setHttpResponseBody("Erreur ligne 51");
//					res.setHttpResponseHeader("FB");
//					 System.out.println(String.format("%s return empty response", url));
//					return res;
//				}
                // print result
                res.setHttpResponseBody(response.toString());
                res.setHttpResponseHeader("");
            } else {
                res.setHttpResponseBody("Erreur ligne 61");
                System.out.println(String.format("Application giving response code %d", responseCode));

            }
            System.out.println(String.format("%s", res.toString()));
            return res;
        } catch (java.net.SocketTimeoutException ex) {
            System.out.println("Application taking long to reply. Please retry! " + ex.getMessage());
            return new HttpClientResponse("Erreur ligne 70", -1, "FB");
        } catch (java.net.MalformedURLException ex) {
            System.out.println(String.format("Error in the url %s > %s ", url, ex.getMessage()));
            return new HttpClientResponse("Error in the application url!", -1, "FB");
        } catch (java.io.IOException ex) {
            System.out.println(String.format("url %s not accessible > %s ", url, ex.getMessage()));
            return new HttpClientResponse("Application not accessible. Please retry later!", -1, "FB");
        }
    }
}
