/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.jsf.model;


import com.sbs.easymbank.entities.Agences;
import java.util.List;

/**
 *
 * @author alex
 */
public class LazyAgenceDataModel extends LazyEntityModel<Agences> {
    
     public LazyAgenceDataModel(List<Agences> datasource) {
        super(datasource, Agences.class);
    }

    @Override
    public Object getRowKey(Agences agences) {
        return agences.getIdagences();
    }

    @Override
    public Agences getRowData(String rowKey) {
         for(Agences agences : datasource) {
            if(agences.getIdagences().equals(Integer.parseInt(rowKey)))
                return agences;
        }
 
        return null;
    }
    }
    
    
