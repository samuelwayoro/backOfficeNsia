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
public class OperatorNotFoundException extends Exception{

    public OperatorNotFoundException() {
    }

    public OperatorNotFoundException(String message) {
        super("Operateur "+ message + " introuvable");
    }
    
}
