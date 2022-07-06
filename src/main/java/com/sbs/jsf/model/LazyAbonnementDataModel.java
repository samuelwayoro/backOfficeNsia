/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.jsf.model;

import com.sbs.easymbank.entities.Abonnements;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author alex
 */
public class LazyAbonnementDataModel extends LazyEntityModel<Abonnements> {
    
     
    public LazyAbonnementDataModel(List<Abonnements> datasource) {
        super(datasource, Abonnements.class);
    }

       
    
     
    @Override
    public Abonnements getRowData(String rowKey) {
        for(Abonnements abonnements : datasource) {
            if(abonnements.getIdabonnements().equals(Long.parseLong(rowKey))){
                System.out.println("SELECTED AB:"+abonnements.toString());
                return abonnements;
            }
                
        }
        System.out.println("ABON. NON TROUVE");
        return null;
    }
 
    @Override
    public Object getRowKey(Abonnements abonnements) {
        return abonnements.getIdabonnements();
    }
    
//     @Override
//    public List<Abonnements> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
//        List<Abonnements> data = new ArrayList<Abonnements>();
// 
//        //filter
//        for(Abonnements abonnement : datasource) {
//            boolean match = true;
// 
//            if (filters != null) {
//                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
//                    try {
//                        String filterProperty = it.next();
//                        Object filterValue = filters.get(filterProperty);
//                        Field field = abonnement.getClass().getDeclaredField(filterProperty);
//                        field.setAccessible(true);
//                       // String fieldValue = String.valueOf(abonnement.getClass().getField(filterProperty).get(abonnement));
//                        String fieldValue = String.valueOf(  field.get(abonnement) ).toLowerCase();
//                        if (filterValue == null || fieldValue.contains(filterValue.toString().toLowerCase() )  ) {
//                            match = true;
//                    }
//                    else {
//                            match = false;
//                            break;
//                        }
//                    } catch(Exception e) {
//                        match = false;
//                        e.printStackTrace();
//                    }
//                }
//            }
// 
//            if(match) {
//                data.add(abonnement);
//            }
//        }
// 
//        //sort
//        if(sortField != null) {
//            Collections.sort(data, new LazySorter<>(sortField, sortOrder,Abonnements.class));
//        }
// 
//        //rowCount
//        int dataSize = data.size();
//        this.setRowCount(dataSize);
// 
//        //paginate
//        if(dataSize > pageSize) {
//            try {
//                return data.subList(first, first + pageSize);
//            }
//            catch(IndexOutOfBoundsException e) {
//                return data.subList(first, first + (dataSize % pageSize));
//            }
//        }
//        else {
//            return data;
//        }
//    }
}
