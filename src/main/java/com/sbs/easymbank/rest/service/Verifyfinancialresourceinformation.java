/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.rest.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author samuel
 */
@Path("verifyfinancialresourceinformation")
public class Verifyfinancialresourceinformation {
    
    @POST
    @Produces(MediaType.TEXT_XML)
    @Consumes(MediaType.TEXT_XML)
    @Path("verifyfri")
    public String verifyfri(String request ){
        //reponse a envoyee en cas de succes 
        String response = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                + "<ns2:verifyfinancialresourceinformationresponse xmlns:ns2=\"http://www.ericsson.com/em/emm/serviceprovider/v1_2/backend/client\">\n"
                + "	<valid>true</valid>\n"
                + "</ns2:verifyfinancialresourceinformationresponse>";
        System.out.println("===>" + response);
        return response;
    }
    
}
