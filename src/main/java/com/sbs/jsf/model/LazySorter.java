/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.jsf.model;

/**
 *
 * @author alex
 */
import java.lang.reflect.Field;
import java.util.Comparator;
import org.primefaces.model.SortOrder;

 
//public class LazySorter implements Comparator<Abonnements> {
    public class LazySorter<T> implements Comparator<T> {
        
   private Class<T> entityClass;    
 
    private String sortField;
     
    private SortOrder sortOrder;
     
    public LazySorter(String sortField, SortOrder sortOrder,Class<T> entityClass) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
        this.entityClass = entityClass;
    }
 
    @Override
    public int compare(T t1, T t2) {
        try { 
            Field field =t1.getClass().getDeclaredField(this.sortField);
            field.setAccessible(true);
            Object value1 = field.get(t1);
            field =t2.getClass().getDeclaredField(this.sortField);
            field.setAccessible(true);
            Object value2 = field.get(t2);
 
            int value = ((Comparable)value1).compareTo(value2);
             
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
           
        }
    }
}