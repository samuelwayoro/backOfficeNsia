/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.utility;

import com.sbs.easymbank.entities.AbonnementsOm;
import org.json.JSONObject;


/**
 *
 * @author alex
 */
public class JsonToObject {
    public static void abonnementFromMoovResponse(String data,AbonnementsOm abonnementsOm){
        JSONObject jSONObject=new JSONObject(data);
        //AbonnementsOm abonnementsOm=new AbonnementsOm();
        //abonnementsOm.setMsisdn(jSONObject.getString("msisdn"));
        abonnementsOm.setDateNaissance(jSONObject.optString("dateofbirth"));
        abonnementsOm.setNom(jSONObject.optString("firstname"));
        abonnementsOm.setPrenoms(jSONObject.optString("lastname"));
        abonnementsOm.setGenre(jSONObject.optString("gender"));
        abonnementsOm.setNationalite(jSONObject.optString("nationality"));
        abonnementsOm.setPays(jSONObject.optString("country"));
        abonnementsOm.setTypecompte(jSONObject.optString("accounttype"));
        abonnementsOm.setRegion(jSONObject.optString("region"));
        abonnementsOm.setVille(jSONObject.optString("city"));
            }
}
