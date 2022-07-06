package com.sbs.easymbank.soap;

import com.sbs.easymbank.dao.ParametresFacade;
import com.sbs.easymbank.entities.Parametres;
import com.sbs.exceptions.ParameterNotFoundException;

public class SoapWrapperBN {

    private static Parametres nonce;

    public static synchronized String getNonce(ParametresFacade parametresFacade) throws ParameterNotFoundException {
        try {
            String valueToReturn;
            //Fetch the next value for nonce if the function is call first time
            if (nonce == null) {
                nonce = parametresFacade.findByCodeParam("NEXT_NONCE").get(0);
            }
            valueToReturn = nonce.getValeur();

            nonce.setValeur(nonce.getValeur().equals("1024") ? "1" : Integer.toString(Integer.parseInt(nonce.getValeur()) + 1));
            //Update the parameter in the databse
            parametresFacade.edit(nonce);
            return valueToReturn;

        } catch (IndexOutOfBoundsException ex) {
            throw new ParameterNotFoundException("NEXT_NONCE");
        }
    }

}
