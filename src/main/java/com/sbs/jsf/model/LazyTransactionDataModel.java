/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.jsf.model;

import com.sbs.easymbank.entities.Transactions;
import java.util.List;

/**
 *
 * @author alex
 */
public class LazyTransactionDataModel extends LazyEntityModel<Transactions> {

    public LazyTransactionDataModel(List<Transactions> datasource) {
        super(datasource, Transactions.class);
    }

    @Override
    public Object getRowKey(Transactions transactions) {
        return transactions.getIdtransactions(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Transactions getRowData(String rowKey) {
        for (Transactions transactions : datasource) {
            if (transactions.getIdtransactions().equals(Integer.parseInt(rowKey))) {
                return transactions;
            }
        }

        return null;
    }

}


