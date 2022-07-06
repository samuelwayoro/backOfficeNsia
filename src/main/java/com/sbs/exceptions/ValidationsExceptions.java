/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.exceptions;

import javax.ejb.ApplicationException;

/**
 *
 * @author dg
 */
@ApplicationException(rollback = true)
public class ValidationsExceptions extends RuntimeException {

    /**
     * Creates a new instance of <code>ValidationsExceptions</code> without detail message.
     */
    public ValidationsExceptions() {
    }

    /**
     * Constructs an instance of <code>ValidationsExceptions</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ValidationsExceptions(String msg) {
        super(msg);
    }
}
