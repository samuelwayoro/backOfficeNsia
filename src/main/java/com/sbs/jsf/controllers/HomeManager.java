/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.jsf.controllers;

import com.sbs.easymbank.dao.AbonnementsFacade;
import com.sbs.easymbank.dao.ConnexionFacade;
import com.sbs.easymbank.entities.Abonnements;
import com.sbs.easymbank.entities.Connexion;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;


/**
 *
 * @author SOCITECH-
 */
@ManagedBean
@SessionScoped
public class HomeManager implements Serializable {
    @EJB
    private AbonnementsFacade abonnementsFacade;
    @EJB
    private ConnexionFacade connexionFacade;
    
   
    @ManagedProperty("#{loginManager}")
    private LoginManager loginManager;
    private List<Abonnements> listAbonnementsJour=new ArrayList<>();
    private List<Abonnements> listAbonnementsASupprimer=new ArrayList<>();
    @PostConstruct
    public void init(){
      // List<Connexion> listConnect= connexionFacade.findByLogin(loginManager.getUtilisateur().getLogin());
      // List<Long>listDuree=new ArrayList();
       //
       

//        PeriodType periodType=PeriodType.yearDayTime();
//       // Period debut=new Period
//       DateTime dt1;
//       DateTime dt2;
//       Duration duree=new Duration(0);
//       String[] tabDateDebut;
//       String[] tabDateFin;
//       String[] tabHeureDebut;
//       String[] tabHeureFin;
//        for(Connexion con:listConnect){
//            tabDateDebut=con.getDateDebut().split("/");
//            tabDateFin=con.getDateFin().split("/");
//            tabHeureDebut=con.getHeureDebut().split(":");
//            tabHeureFin=con.getHeureFin().split(":");
//            dt1=new DateTime(Integer.parseInt(tabDateDebut[2]),Integer.parseInt(tabDateDebut[1]),Integer.parseInt(tabDateDebut[0]),Integer.parseInt(tabHeureDebut[0]),Integer.parseInt(tabHeureDebut[1]),Integer.parseInt(tabHeureDebut[2]));
//            dt2=new DateTime(Integer.parseInt(tabDateFin[2]),Integer.parseInt(tabDateFin[1]),Integer.parseInt(tabDateFin[0]),Integer.parseInt(tabHeureFin[0]),Integer.parseInt(tabHeureFin[1]),Integer.parseInt(tabHeureFin[2]));
//            Duration dur=new Duration(dt1, dt2);
//            if(dur.getMillis()>duree.getMillis())
//                duree=dur;
//            
//        }
//        maxConnexionTime=new Period(duree.getMillis());
        String date=new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        listAbonnementsJour= abonnementsFacade.findByDateAndLogin(date,loginManager.getUtilisateur().getLogin());
        if(listAbonnementsJour==null){
            listAbonnementsJour=new ArrayList<>();
        }
        listAbonnementsASupprimer=abonnementsFacade.findAbonnementToDelete(loginManager.getUtilisateur().getLogin());
        if(listAbonnementsASupprimer==null){
            listAbonnementsASupprimer=new ArrayList<>();
        }
    }

  

    public LoginManager getLoginManager() {
        return loginManager;
    }

    public void setLoginManager(LoginManager loginManager) {
        this.loginManager = loginManager;
    }
    
    public void checkMaxConnexionTime(){
       
    }

    public List<Abonnements> getListAbonnementsJour() {
        return listAbonnementsJour;
    }

    public void setListAbonnementsJour(List<Abonnements> listAbonnementsJour) {
        this.listAbonnementsJour = listAbonnementsJour;
    }

    public List<Abonnements> getListAbonnementsASupprimer() {
        return listAbonnementsASupprimer;
    }

    public void setListAbonnementsASupprimer(List<Abonnements> listAbonnementsASupprimer) {
        this.listAbonnementsASupprimer = listAbonnementsASupprimer;
    }
    
    public void rafraichirListAbonnementASupprimer(){
         listAbonnementsASupprimer=abonnementsFacade.findAbonnementToDelete(loginManager.getUtilisateur().getLogin());
        if(listAbonnementsASupprimer==null){
            listAbonnementsASupprimer=new ArrayList<>();
        }
    }
    
    
}
