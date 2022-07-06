/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author samuel
 */
public class ClientHttpWZLSN {

    public String getToken() {

        String username = "basn";
        String grant_type = "password";
        String client_type = "public";
        String client_id = "2hAeTx73ADDzqfnhdEQ5uEMZQTpA6jZhqIPVzZg9";
        String client_secret = "yBuhYUrXtJ9H8ymTDwKp09yVVRIW0Wog56FtGuA5ZPSu3qDyCfHAbkPM3IyoRiH9bzEH99JYg9xTelDSuy0SNUKKifbFKeDKQCxU3if6uJgINNzgsVo79pLLwZQSA8Y3";
        String password = "4563638726HD";
        String contry = "sn";

        String URL_TOKEN = "https://testagent-api.wizall.com/token/";
        String corps = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\",\"client_id\":\"" + client_id + "\",\"client_secret\":\"" + client_secret + "\",\"client_type\":\"" + client_type + "\",\"grant_type\":\"" + grant_type + "\"}";

        String access_token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1OTEyODk5OTgsImRhdGEiOnsidXNlcm5hbWUiOiJ0ZXN0IiwicGFzc3dvcmQiOiJqZGt3cTAxMjc2MzI5MDIxIiwiY2xpZW50X3R5cGUiOiJwdWJsaWMiLCJncmFudF90eXBlIjoicGFzc3dvcmQiLCJjbGllbnRfaWQiOiJqZHNqa3c5MDIxODQzMDkyLTAyMTk4MzIyMzMybiIsImNsaWVudF9zZWNyZXQiOiJXVHdpeWd3YXdkM2QzZDMyZXJmbkJrMmRIaXdWclA0blc2SXAyRUJYeXVaTEhBSjE0dER4N2E0OTBMS1F2ZGtNaUJJaUF0WTNSUlhtTU1VMTF6U0tFUHp1ODhld3d3ZWZyZmV3bWF1UEV2SmRvNFZlVmhHVGt3YWhKZVhoWjdFS1pYQ2QzdFUifX0.OtM5-2uDLuUG1lMt_0MFI5lNu9OZ9XAu5ZmWfcFCnzs";

        System.out.println(corps);

        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost(URL_TOKEN);

            StringEntity input = new StringEntity(corps);
            input.setContentType("application/json");
            //&input.
            postRequest.setEntity(input);
            HttpResponse response = httpClient.execute(postRequest);
            //traitement du code erreur de la demande de token 
            
            System.out.println("REPONSE RETOURNEE : "+response.getClass());

            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
            System.out.println("---------br" + br.readLine());
            String output;
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                JSONParser parser = new JSONParser();

                Object obj = (Object) parser.parse(output);

                JSONObject jsonObject = (JSONObject) obj;

                access_token = (String) jsonObject.get("access_token");
            }
            System.out.println(access_token);
        } catch (IOException | IllegalStateException | ParseException e) {
            e.printStackTrace();
            return "";
        }
        return access_token;

    }

}
