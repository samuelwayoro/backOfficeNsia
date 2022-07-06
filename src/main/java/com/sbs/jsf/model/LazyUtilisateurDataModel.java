/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.jsf.model;

import com.sbs.easymbank.entities.Users;
import java.util.List;

/**
 *
 * @author hp
 */
public class LazyUtilisateurDataModel extends LazyEntityModel<Users> {

    public LazyUtilisateurDataModel(List<Users> datasource) {
        super(datasource, Users.class);
    }
    
    

    @Override
    public Object getRowKey(Users users) {
        return users.getIdusers(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Users getRowData(String rowKey) {
         for(Users users : datasource) {
            if(users.getIdusers().equals(Integer.parseInt(rowKey)))
                return users;
        }
 
        return null;
    }
    
    
    
}
