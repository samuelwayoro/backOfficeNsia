/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.jsf.controllers;

import com.sbs.easymbank.dao.TransactionsFacade;
import com.sbs.easymbank.dao.TransactionsReconciliationsFacade;
import com.sbs.easymbank.dao.TypeTransactionsFacade;
import com.sbs.easymbank.entities.Abonnements;
import com.sbs.easymbank.entities.Transactions;
import com.sbs.easymbank.entities.TransactionsReconciliations;
import com.sbs.easymbank.entities.TypeTransactions;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author SOCITECH-
 */
@ManagedBean
@ViewScoped
public class Reconciliation implements Serializable {

    private List<TransactionsReconciliations> listTransactionOrange;
    private List<Transactions> listTransaction;
    private List<TransactionsReconciliations> listTransactionOrangeFiltered;
    private List<Transactions> listTransactionFiltered;
    private List<TypeTransactions> listTypeTransaction;
    private TypeTransactions selectedTypeTransaction;
    private String type;
    private PieChartModel modelrecapitulatifEnNombreParTypeTransaction = new PieChartModel();
    private PieChartModel modelrecapitulatifEnMontantParTypeTransaction = new PieChartModel();
    private PieChartModel modelrecapitulatifEnNombreParTypeTransactionOrange = new PieChartModel();
    private PieChartModel modelrecapitulatifEnMontantParTypeTransactionOrange = new PieChartModel();
    private Map<String, String> recapitulatifEnNombreParTypeTransaction = new HashMap<>();
    private Map<String, String> recapitulatifEnMontantParTypeTransaction = new HashMap<>();
    private Map<String, String> recapitulatifEnNombreParTypeTransactionOrange = new HashMap<>();
    private Map<String, String> recapitulatifEnMontantParTypeTransactionOrange = new HashMap<>();
    
 

    private Date selectedDate;
    //La date à afficher au chargement de la page
    private String dateToDisplay;

    @EJB
    TransactionsFacade transactionsFacade;
    @EJB
    TransactionsReconciliationsFacade transactionsReconciliationsFacade;
    @EJB
    TypeTransactionsFacade typeTransactionsFacade;

    @PostConstruct
    public void init() {
        try {
            //Chargement des listes
            listTransaction = transactionsFacade.findAll();
          //  listTransactionOrange = transactionsReconciliationsFacade.findAll();
            listTypeTransaction = typeTransactionsFacade.findAll();
            listTransactionFiltered = new ArrayList<>();
            listTransactionOrangeFiltered = new ArrayList<>();
            for (Transactions tr : listTransaction) {
                listTransactionFiltered.add(tr);
            }
            for (TransactionsReconciliations tr : listTransactionOrange) {
                listTransactionOrangeFiltered.add(tr);
            }
            //Réglage date
            selectedTypeTransaction = new TypeTransactions();
            Date laDate = Calendar.getInstance().getTime();
            selectedDate = laDate;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            dateToDisplay = sdf.format(selectedDate);

            selectedDate = laDate;

            initCounterForModel();
            reglerListeTransactionFiltre();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<TransactionsReconciliations> getListTransactionOrange() {
        return listTransactionOrange;
    }

    public void setListTransactionOrange(List<TransactionsReconciliations> listTransactionOrange) {
        this.listTransactionOrange = listTransactionOrange;
    }

    public List<Transactions> getListTransaction() {
        return listTransaction;
    }

    public void setListTransaction(List<Transactions> listTransaction) {
        this.listTransaction = listTransaction;
    }

    public List<TransactionsReconciliations> getListTransactionOrangeFiltered() {
        return listTransactionOrangeFiltered;
    }

    public void setListTransactionOrangeFiltered(List<TransactionsReconciliations> listTransactionOrangeFiltered) {
        this.listTransactionOrangeFiltered = listTransactionOrangeFiltered;
    }

    public List<Transactions> getListTransactionFiltered() {
        return listTransactionFiltered;
    }

    public void setListTransactionFiltered(List<Transactions> listTransactionFiltered) {
        this.listTransactionFiltered = listTransactionFiltered;
    }

    public List<TypeTransactions> getListTypeTransaction() {
        return listTypeTransaction;
    }

    public void setListTypeTransaction(List<TypeTransactions> listTypeTransaction) {
        this.listTypeTransaction = listTypeTransaction;
    }

    public TypeTransactions getSelectedTypeTransaction() {
        return selectedTypeTransaction;
    }

    public void setSelectedTypeTransaction(TypeTransactions selectedTypeTransaction) {
        this.selectedTypeTransaction = selectedTypeTransaction;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }

    public PieChartModel getModelrecapitulatifEnNombreParTypeTransaction() {
        return modelrecapitulatifEnNombreParTypeTransaction;
    }

    public void setModelrecapitulatifEnNombreParTypeTransaction(PieChartModel modelrecapitulatifEnNombreParTypeTransaction) {
        this.modelrecapitulatifEnNombreParTypeTransaction = modelrecapitulatifEnNombreParTypeTransaction;
    }

    public PieChartModel getModelrecapitulatifEnMontantParTypeTransaction() {
        return modelrecapitulatifEnMontantParTypeTransaction;
    }

    public void setModelrecapitulatifEnMontantParTypeTransaction(PieChartModel modelrecapitulatifEnMontantParTypeTransaction) {
        this.modelrecapitulatifEnMontantParTypeTransaction = modelrecapitulatifEnMontantParTypeTransaction;
    }

    public String getDateToDisplay() {
        return dateToDisplay;
    }

    public void setDateToDisplay(String dateToDisplay) {
        this.dateToDisplay = dateToDisplay;
    }

    public PieChartModel getModelrecapitulatifEnNombreParTypeTransactionOrange() {
        return modelrecapitulatifEnNombreParTypeTransactionOrange;
    }

    public void setModelrecapitulatifEnNombreParTypeTransactionOrange(PieChartModel modelrecapitulatifEnNombreParTypeTransactionOrange) {
        this.modelrecapitulatifEnNombreParTypeTransactionOrange = modelrecapitulatifEnNombreParTypeTransactionOrange;
    }

    public PieChartModel getModelrecapitulatifEnMontantParTypeTransactionOrange() {
        return modelrecapitulatifEnMontantParTypeTransactionOrange;
    }

    public void setModelrecapitulatifEnMontantParTypeTransactionOrange(PieChartModel modelrecapitulatifEnMontantParTypeTransactionOrange) {
        this.modelrecapitulatifEnMontantParTypeTransactionOrange = modelrecapitulatifEnMontantParTypeTransactionOrange;
    }

    //Mettre à jour les listes à afficher en fonction des filtres
    public void reglerListeTransactionFiltre() {
        try {
            selectedTypeTransaction.setLibelleType(type);
            listTransactionFiltered.clear();
            listTransactionOrangeFiltered.clear();
            initCounterForModel();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String laDate = sdf.format(selectedDate);
            if (type != null) {

                for (Transactions tr : listTransaction) {
                    if (tr.getRequesttype().equals(type) && tr.getTrandate().contains(laDate)) {
                        listTransactionFiltered.add(tr);

                    }
                }
                for (TransactionsReconciliations tr : listTransactionOrange) {
                    if (tr.getRequesttype().equals(type) && tr.getTrandate().contains(laDate)) {
                        listTransactionOrangeFiltered.add(tr);
                    }
                }
            } else {
                for (Transactions tr : listTransaction) {
                    if (tr.getTrandate().contains(laDate)) {
                        listTransactionFiltered.add(tr);
                        recapitulatifEnNombreParTypeTransaction.put(tr.getRequesttype(), String.valueOf(Integer.parseInt(recapitulatifEnNombreParTypeTransaction.get(tr.getRequesttype())) + 1));
                        if (!tr.getAmount().isEmpty()) {
                            recapitulatifEnMontantParTypeTransaction.put(tr.getRequesttype(), String.valueOf(Long.parseLong(recapitulatifEnMontantParTypeTransaction.get(tr.getRequesttype())) + Long.parseLong(tr.getAmount())));
                        }

                    }
                }
                for (TransactionsReconciliations tr : listTransactionOrange) {
                    if (tr.getTrandate().contains(laDate)) {
                        listTransactionOrangeFiltered.add(tr);
                        recapitulatifEnNombreParTypeTransactionOrange.put(tr.getRequesttype(), String.valueOf(Integer.parseInt(recapitulatifEnNombreParTypeTransactionOrange.get(tr.getRequesttype())) + 1));
                        if (!tr.getAmount().isEmpty()) {
                            recapitulatifEnMontantParTypeTransactionOrange.put(tr.getRequesttype(), String.valueOf(Long.parseLong(recapitulatifEnMontantParTypeTransactionOrange.get(tr.getRequesttype())) + Long.parseLong(tr.getAmount())));
                        }

                        // recapitulatifEnNombreParTypeTransaction.put(tr.getRequesttype(),String.valueOf(Integer.parseInt(recapitulatifEnNombreParTypeTransaction.get(tr.getRequesttype()))+1));
                    }

                }
                dessinerDiagramme();
            }

//        System.out.println("TAILLE: "+listTransaction.size());
//        System.out.println("TAILLE2: "+listTransactionFiltered.size());
            System.out.println("DATE: " + selectedDate);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void dessinerDiagramme() {
        modelrecapitulatifEnNombreParTypeTransaction.clear();
        Iterator it = recapitulatifEnNombreParTypeTransaction.keySet().iterator();
        while (it.hasNext()) {
            String typeCourant = (String) it.next();
            modelrecapitulatifEnNombreParTypeTransaction.set(typeCourant, Integer.parseInt(recapitulatifEnNombreParTypeTransaction.get(typeCourant)));
        }
        modelrecapitulatifEnNombreParTypeTransaction.setDiameter(150);
        modelrecapitulatifEnNombreParTypeTransaction.setFill(true);
        modelrecapitulatifEnNombreParTypeTransaction.setTitle("NOMBRE");
        modelrecapitulatifEnNombreParTypeTransaction.setLegendPosition("w");
        modelrecapitulatifEnNombreParTypeTransaction.setShowDataLabels(true);
        modelrecapitulatifEnNombreParTypeTransaction.setDataFormat("value");

        modelrecapitulatifEnMontantParTypeTransaction.clear();
        it = recapitulatifEnMontantParTypeTransaction.keySet().iterator();
        while (it.hasNext()) {
            String typeCourant = (String) it.next();
            modelrecapitulatifEnMontantParTypeTransaction.set(typeCourant, Long.parseLong(recapitulatifEnMontantParTypeTransaction.get(typeCourant)));
        }
        modelrecapitulatifEnMontantParTypeTransaction.setDiameter(150);
        modelrecapitulatifEnMontantParTypeTransaction.setFill(true);
        modelrecapitulatifEnMontantParTypeTransaction.setTitle("MONTANT");
        modelrecapitulatifEnMontantParTypeTransaction.setLegendPosition("w");
        modelrecapitulatifEnMontantParTypeTransaction.setShowDataLabels(true);
        modelrecapitulatifEnMontantParTypeTransaction.setDataFormat("value");

        modelrecapitulatifEnNombreParTypeTransactionOrange.clear();
        it = recapitulatifEnNombreParTypeTransactionOrange.keySet().iterator();
        while (it.hasNext()) {
            String typeCourant = (String) it.next();
            modelrecapitulatifEnNombreParTypeTransactionOrange.set(typeCourant, Integer.parseInt(recapitulatifEnNombreParTypeTransactionOrange.get(typeCourant)));
        }
        modelrecapitulatifEnNombreParTypeTransactionOrange.setDiameter(150);
        modelrecapitulatifEnNombreParTypeTransactionOrange.setFill(true);
        modelrecapitulatifEnNombreParTypeTransactionOrange.setTitle("NOMBRE");
        modelrecapitulatifEnNombreParTypeTransactionOrange.setLegendPosition("w");
        modelrecapitulatifEnNombreParTypeTransactionOrange.setShowDataLabels(true);
        modelrecapitulatifEnNombreParTypeTransactionOrange.setDataFormat("value");

        modelrecapitulatifEnMontantParTypeTransactionOrange.clear();
        it = recapitulatifEnMontantParTypeTransactionOrange.keySet().iterator();
        while (it.hasNext()) {
            String typeCourant = (String) it.next();
            modelrecapitulatifEnMontantParTypeTransactionOrange.set(typeCourant, Long.parseLong(recapitulatifEnMontantParTypeTransactionOrange.get(typeCourant)));
        }
        modelrecapitulatifEnMontantParTypeTransactionOrange.setDiameter(150);
        modelrecapitulatifEnMontantParTypeTransactionOrange.setFill(true);
        modelrecapitulatifEnMontantParTypeTransactionOrange.setTitle("MONTANT");
        modelrecapitulatifEnMontantParTypeTransactionOrange.setLegendPosition("w");
        modelrecapitulatifEnMontantParTypeTransactionOrange.setShowDataLabels(true);
        modelrecapitulatifEnMontantParTypeTransactionOrange.setDataFormat("value");

    }

    private void initCounterForModel() {
        for (TypeTransactions typetr : listTypeTransaction) {
            recapitulatifEnNombreParTypeTransaction.put(typetr.getLibelleType(), "0");
            recapitulatifEnMontantParTypeTransaction.put(typetr.getLibelleType(), "0");
            recapitulatifEnNombreParTypeTransactionOrange.put(typetr.getLibelleType(), "0");
            recapitulatifEnMontantParTypeTransactionOrange.put(typetr.getLibelleType(), "0");
        }
    }

}
