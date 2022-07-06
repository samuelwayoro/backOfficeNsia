/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.jsf.controllers;

import com.sbs.easymbank.dao.AbonnementsFacade;
import com.sbs.easymbank.dao.AbonnementsReconciliationsFacade;

import com.sbs.easymbank.dao.TypeAbonnementsFacade;
import com.sbs.easymbank.entities.Abonnements;
import com.sbs.easymbank.entities.AbonnementsReconciliations;
import com.sbs.easymbank.entities.TypeAbonnements;
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
public class ReconciliationAbonnements implements Serializable {

    private List<Abonnements> listAbonnements;
    private List<AbonnementsReconciliations> listAbonnementsReconciliations;
    private List<Abonnements> listAbonnementsFiltered;
    private List<AbonnementsReconciliations> listAbonnementsReconciliationsFiltered;
    private List<TypeAbonnements> listTypeAbonnements;
    private TypeAbonnements selectedTypeAbonnement;
    private String type;
    private Date selectedDate;
    
    private PieChartModel modelrecapitulatifEnNombreParTypeAbonnement;
    
    private PieChartModel modelrecapitulatifEnNombreParTypeAbonnementOrange;
    
    private Map<String,String> recapitulatifEnNombreParTypeAbonnement;
    
    private Map<String,String> recapitulatifEnNombreParTypeAbonnementOrange;
    


    @EJB
    TypeAbonnementsFacade typeAbonnementsFacade;
    @EJB
    AbonnementsReconciliationsFacade abonnementsReconciliationsFacade;
    @EJB
    AbonnementsFacade abonnementsFacade;

    @PostConstruct
    public void init() {
        try{
            listAbonnements = abonnementsFacade.findAll();
        listAbonnementsReconciliations = abonnementsReconciliationsFacade.findAll();
        listTypeAbonnements = typeAbonnementsFacade.findAll();
        listAbonnementsReconciliationsFiltered = new ArrayList<>();
        listAbonnementsFiltered = new ArrayList<>();
        for (Abonnements ab : listAbonnements) {
            listAbonnementsFiltered.add(ab);
        }
        for (AbonnementsReconciliations ab : listAbonnementsReconciliations) {
            listAbonnementsReconciliationsFiltered.add(ab);
        }
        selectedTypeAbonnement = new TypeAbonnements();
        Date laDate = Calendar.getInstance().getTime();

        selectedDate = laDate;
        
         //graphique
         modelrecapitulatifEnNombreParTypeAbonnement=new PieChartModel();
         
         modelrecapitulatifEnNombreParTypeAbonnementOrange=new PieChartModel();
         
         //Initialisation du recapitulatif
        recapitulatifEnNombreParTypeAbonnement=new HashMap<>();
        
        recapitulatifEnNombreParTypeAbonnementOrange=new HashMap<>();
        

        initCounterForModel();
        reglerListeAbonnementFiltre();

        }catch(Exception e){
            e.printStackTrace();
        }
            }

    public List<Abonnements> getListAbonnements() {
        return listAbonnements;
    }

    public void setListAbonnements(List<Abonnements> listAbonnements) {
        this.listAbonnements = listAbonnements;
    }

    public List<AbonnementsReconciliations> getListAbonnementsReconciliations() {
        return listAbonnementsReconciliations;
    }

    public void setListAbonnementsReconciliations(List<AbonnementsReconciliations> listAbonnementsReconciliations) {
        this.listAbonnementsReconciliations = listAbonnementsReconciliations;
    }

    public List<Abonnements> getListAbonnementsFiltered() {
        return listAbonnementsFiltered;
    }

    public void setListAbonnementsFiltered(List<Abonnements> listAbonnementsFiltered) {
        this.listAbonnementsFiltered = listAbonnementsFiltered;
    }

    public List<AbonnementsReconciliations> getListAbonnementsReconciliationsFiltered() {
        return listAbonnementsReconciliationsFiltered;
    }

    public void setListAbonnementsReconciliationsFiltered(List<AbonnementsReconciliations> listAbonnementsReconciliationsFiltered) {
        this.listAbonnementsReconciliationsFiltered = listAbonnementsReconciliationsFiltered;
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

    public List<TypeAbonnements> getListTypeAbonnements() {
        return listTypeAbonnements;
    }

    public void setListTypeAbonnements(List<TypeAbonnements> listTypeAbonnements) {
        this.listTypeAbonnements = listTypeAbonnements;
    }

    public PieChartModel getModelrecapitulatifEnNombreParTypeAbonnement() {
        return modelrecapitulatifEnNombreParTypeAbonnement;
    }

    public void setModelrecapitulatifEnNombreParTypeAbonnement(PieChartModel modelrecapitulatifEnNombreParTypeAbonnement) {
        this.modelrecapitulatifEnNombreParTypeAbonnement = modelrecapitulatifEnNombreParTypeAbonnement;
    }

    
    public PieChartModel getModelrecapitulatifEnNombreParTypeAbonnementOrange() {
        return modelrecapitulatifEnNombreParTypeAbonnementOrange;
    }

    public void setModelrecapitulatifEnNombreParTypeAbonnementOrange(PieChartModel modelrecapitulatifEnNombreParTypeAbonnementOrange) {
        this.modelrecapitulatifEnNombreParTypeAbonnementOrange = modelrecapitulatifEnNombreParTypeAbonnementOrange;
    }

    
    public void reglerListeAbonnementFiltre() {
        try {
            selectedTypeAbonnement.setIdType(type);
            listAbonnementsFiltered.clear();
            listAbonnementsReconciliationsFiltered.clear();
            initCounterForModel();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String laDate = sdf.format(selectedDate);
            if (type != null) {
                //On vérifie si l'utilisateur souhaite ne voir que les abonnements ou les résiliations
                switch (type) {
                    case "register":
                        for (Abonnements ab : listAbonnements) {
                            if (ab.getDatecreation()!= null) {
                                if (ab.getDatecreation().contains(laDate) && !ab.getResilie()) {
                                    listAbonnementsFiltered.add(ab);
                                }
                            }
                        }
                        for (AbonnementsReconciliations ab : listAbonnementsReconciliations) {
                            if (ab.getDatecreation()!= null) {
                                if (ab.getDatecreation().contains(laDate) && !ab.getResilie()) {
                                    listAbonnementsReconciliationsFiltered.add(ab);
                                }
                            }
                        }
                    case "delete":
                        for (Abonnements ab : listAbonnements) {
                            if (ab.getDateresiliation() != null) {
                                if (ab.getDateresiliation().contains(laDate) && ab.getResilie()) {
                                    listAbonnementsFiltered.add(ab);
                                }
                            }
                        }
                        for (AbonnementsReconciliations ab : listAbonnementsReconciliations) {
                            if (ab.getDateresiliation()!= null) {
                                if (ab.getDateresiliation().contains(laDate) && ab.getResilie()) {
                                    listAbonnementsReconciliationsFiltered.add(ab);
                                }
                            }
                        }
                }

            } else {
                for (Abonnements ab : listAbonnements) {
                    if(ab.getDateresiliation()!=null){
                    if (ab.getDatecreation().contains(laDate) || ab.getDateresiliation().contains(laDate)) {
                        listAbonnementsFiltered.add(ab);
                        if(ab.getDatecreation().contains(laDate))
                            //Comptage pour le diagrame
                            recapitulatifEnNombreParTypeAbonnement.put("register",String.valueOf(Integer.parseInt(recapitulatifEnNombreParTypeAbonnement.get("register"))+1));
                        if(ab.getDateresiliation().contains(laDate))
                            recapitulatifEnNombreParTypeAbonnement.put("delete",String.valueOf(Integer.parseInt(recapitulatifEnNombreParTypeAbonnement.get("delete"))+1));
                    }}else{
                         if (ab.getDatecreation().contains(laDate)) {
                        listAbonnementsFiltered.add(ab);
                        recapitulatifEnNombreParTypeAbonnement.put("register",String.valueOf(Integer.parseInt(recapitulatifEnNombreParTypeAbonnement.get("register"))+1));
                    }
                    }
                }
                for (AbonnementsReconciliations ab : listAbonnementsReconciliations) {
                    if(ab.getDateresiliation()!=null){
                    if (ab.getDatecreation().contains(laDate) || ab.getDateresiliation().contains(laDate)) {
                        listAbonnementsReconciliationsFiltered.add(ab);
                        if(ab.getDatecreation().contains(laDate))
                            recapitulatifEnNombreParTypeAbonnementOrange.put("register",String.valueOf(Integer.parseInt(recapitulatifEnNombreParTypeAbonnementOrange.get("register"))+1));
                        if(ab.getDateresiliation().contains(laDate))
                            recapitulatifEnNombreParTypeAbonnementOrange.put("delete",String.valueOf(Integer.parseInt(recapitulatifEnNombreParTypeAbonnementOrange.get("delete"))+1));

                    }}else{
                         if (ab.getDatecreation().contains(laDate)) {
                        listAbonnementsReconciliationsFiltered.add(ab);
                        recapitulatifEnNombreParTypeAbonnementOrange.put("register",String.valueOf(Integer.parseInt(recapitulatifEnNombreParTypeAbonnementOrange.get("register"))+1));
                    }
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
    
     public void dessinerDiagramme(){
        modelrecapitulatifEnNombreParTypeAbonnement.clear();
        Iterator it=recapitulatifEnNombreParTypeAbonnement.keySet().iterator();
        while(it.hasNext()){
            String typeCourant=(String)it.next();
            modelrecapitulatifEnNombreParTypeAbonnement.set(typeCourant,Integer.parseInt(recapitulatifEnNombreParTypeAbonnement.get(typeCourant)));
        }
        modelrecapitulatifEnNombreParTypeAbonnement.setDiameter(220);
        modelrecapitulatifEnNombreParTypeAbonnement.setFill(true);
        modelrecapitulatifEnNombreParTypeAbonnement.setTitle("NOMBRE D'ABONNEMENTS/RESILIATIONS");
        modelrecapitulatifEnNombreParTypeAbonnement.setLegendPosition("w");
        modelrecapitulatifEnNombreParTypeAbonnement.setShowDataLabels(true);
        modelrecapitulatifEnNombreParTypeAbonnement.setDataFormat("value");
        
        
                
        
        modelrecapitulatifEnNombreParTypeAbonnementOrange.clear();
        it=recapitulatifEnNombreParTypeAbonnementOrange.keySet().iterator();
        while(it.hasNext()){
            String typeCourant=(String)it.next();
            modelrecapitulatifEnNombreParTypeAbonnementOrange.set(typeCourant,Integer.parseInt(recapitulatifEnNombreParTypeAbonnementOrange.get(typeCourant)));
        }
        modelrecapitulatifEnNombreParTypeAbonnementOrange.setDiameter(220);
        modelrecapitulatifEnNombreParTypeAbonnementOrange.setFill(true);
        modelrecapitulatifEnNombreParTypeAbonnementOrange.setTitle("NOMBRE D'ABONNEMENTS/RESILIATIONS");
        modelrecapitulatifEnNombreParTypeAbonnementOrange.setLegendPosition("w");
        modelrecapitulatifEnNombreParTypeAbonnementOrange.setShowDataLabels(true);
        modelrecapitulatifEnNombreParTypeAbonnementOrange.setDataFormat("value");
        
               
    }

    
     private  void initCounterForModel(){
         for(TypeAbonnements typeab:listTypeAbonnements){
            recapitulatifEnNombreParTypeAbonnement.put(typeab.getIdType(), "0");
            
            recapitulatifEnNombreParTypeAbonnementOrange.put(typeab.getIdType(), "0");
            
        }
    }


}
