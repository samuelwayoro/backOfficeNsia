/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.jsf.model;


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
 * @param <T>
 */
public class LazyEntityModel<T> extends LazyDataModel<T>{
    
    private Class<T> entityClass;
    
    protected List<T> datasource;
    
    public LazyEntityModel(List<T> datasource,Class<T> entityClass) {
        this.datasource = datasource;
        this.entityClass=entityClass;
    }

    public List<T> getDatasource() {
        return datasource;
    }

    public void setDatasource(List<T> datasource) {
        this.datasource = datasource;
    }
    
    

    @Override
    public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                List<T> data = new ArrayList<>();
 
        //filter
        for(T t : datasource) {
            boolean match = true;
 
            if (filters != null) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                    try {
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty);
                        Field field = t.getClass().getDeclaredField(filterProperty);
                        field.setAccessible(true);
                       // String fieldValue = String.valueOf(abonnement.getClass().getField(filterProperty).get(abonnement));
                        String fieldValue = String.valueOf(  field.get(t) ).toLowerCase();
                        if (filterValue == null || fieldValue.contains(filterValue.toString().toLowerCase() )  ) {
                            match = true;
                    }
                    else {
                            match = false;
                            break;
                        }
                    } catch(Exception e) {
                        match = false;
                        e.printStackTrace();
                    }
                }
            }
 
            if(match) {
                data.add(t);
            }
        }
 
        //sort
        if(sortField != null) {
            Collections.sort(data, new LazySorter<>(sortField, sortOrder,entityClass));
        }
 
        //rowCount
        int dataSize = data.size();
        this.setRowCount(dataSize);
 
        //paginate
        if(dataSize > pageSize) {
            try {
                return data.subList(first, first + pageSize);
            }
            catch(IndexOutOfBoundsException e) {
                return data.subList(first, first + (dataSize % pageSize));
            }
        }
        else {
            return data;
        }

    }
    
}
