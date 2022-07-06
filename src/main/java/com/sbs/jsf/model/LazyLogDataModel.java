/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.jsf.model;

import com.sbs.easymbank.entities.Logs;
import java.util.List;

/**
 *
 * @author alex
 */
public class LazyLogDataModel extends LazyEntityModel<Logs>{

    public LazyLogDataModel(List<Logs> datasource) {
        super(datasource, Logs.class);
    }

    
    
    @Override
    public Object getRowKey(Logs logs) {
        //return super.getRowKey(object); //To change body of generated methods, choose Tools | Templates.
        return logs.getIdlogs();
    }

    @Override
    public Logs getRowData(String rowKey) {
        for(Logs logs : datasource) {
            if(logs.getIdlogs().equals(Integer.parseInt(rowKey))){
              //  System.out.println("SELECTED AB:"+abonnements.toString());
                return logs;
            }
                
        }
      //  System.out.println("ABON. NON TROUVE");
        return null;
    }
    
}
