/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.jsf.model;

import com.sbs.easymbank.entities.Profils;
import java.util.List;

/**
 *
 * @author alex
 */
public class LazyProfilDataModel extends LazyEntityModel<Profils>{
    
     public LazyProfilDataModel(List<Profils> datasource) {
        super(datasource, Profils.class);
    }

    @Override
    public Object getRowKey(Profils profils) {
        return profils.getIdprofils();
    }

    @Override
    public Profils getRowData(String rowKey) {
         for(Profils profils : datasource) {
            if(profils.getIdprofils().equals(Integer.parseInt(rowKey)))
                return profils;
        }
 
        return null;
    }
    
}
