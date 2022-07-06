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
public class NonUniqueCodeException extends Exception {
    
     public NonUniqueCodeException() {
    }

    public NonUniqueCodeException(String code) {
        super("Le code "+ code + " est déja utilisé");
    }
    
}
