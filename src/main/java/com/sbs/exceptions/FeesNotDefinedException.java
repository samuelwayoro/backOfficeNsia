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
public class FeesNotDefinedException extends Exception{

    public FeesNotDefinedException() {
    }
    
    public FeesNotDefinedException(String operateur,String profil,String service){
        super("le montant du service "+service+" n'est pas défini pour le profil "+profil+" avec un abonnement "+operateur);
    }
    
}
