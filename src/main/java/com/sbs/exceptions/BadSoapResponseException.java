/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.exceptions;

/**
 *
 * @author alex
 */
public class BadSoapResponseException extends Exception {

    public BadSoapResponseException() {
    }

    public BadSoapResponseException(int responseCode,String responseMessage) {
        super("Réponse inattendue du Service: "+responseCode+" "+responseMessage);
    }
    
}
