/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.logic;

import com.sbs.easymbank.dao.AbonnementBanqueFacade;
import com.sbs.easymbank.dao.AbonnementsOmFacade;
import com.sbs.easymbank.entities.AbonnementsOm;
import java.io.Serializable;
import javax.ejb.EJB;

/**
 *
 * @author SOCITECH-
 */




public class Service implements Serializable{
    @EJB
    private AbonnementsOmFacade abonnementsOMFacade;
    @EJB
    private AbonnementBanqueFacade abonnementBanque;
//    public AbonnementsOm getKYCFromOM(String cni,String msisdn,String key){
//        return abonnementsOMFacade.findKYC(cni, msisdn, key);
//    }
    
    
    
}

