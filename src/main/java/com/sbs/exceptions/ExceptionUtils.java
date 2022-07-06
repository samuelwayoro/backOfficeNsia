/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.exceptions;


import javax.ejb.EJBException;

/**
 *
 * @author dg
 */
public class ExceptionUtils {
 // ======================================
    // =             Attributs              = 
    // ======================================

    // ======================================
    // =             Constantes             =
    // ======================================

    // ======================================
    // =            Constructeurs           =
    // ======================================

    // ======================================
    // =          Methodes publiques        = 
    // ======================================

    public static Throwable getRootCause(Throwable throwable) {
        Throwable cause;

        if (throwable instanceof EJBException) {
            cause = ((EJBException)throwable).getCausedByException();
        } else {
            cause = throwable.getCause();
        }

        if (cause != null) {
            throwable = cause;
            while ((throwable = throwable.getCause()) != null) {
                cause = throwable;
            }
        }
        return cause;
    }

    public static boolean isApplicationException(Throwable throwable) {
        if (throwable instanceof ValidationsExceptions) {
            return true;
        } else {
            return false;
        }
    }

    // ======================================
    // =          Methodes Protégées        = 
    // ======================================

    // ======================================
    // =             Accesseurs             = 
    // ======================================

    // ======================================
    // =           Methodes Privées         =
    // ======================================

    // ======================================
    // =   Methodes hash, equals, toString  =
    // ======================================
}