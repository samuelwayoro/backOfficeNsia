/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.exceptions;

/*
 *
 * @author alex
 *
 * Exception lancée lorsqu'un paramètre nécessaire au bon fonctionnement de l'application
 * est introuvable dans la base de données. 
 */
 
public class ParameterNotFoundException extends Exception {

    public ParameterNotFoundException() {
    }

    public ParameterNotFoundException(String parameter) {
        super("Paramètre "+ parameter + " introuvable");
    }
   
}
