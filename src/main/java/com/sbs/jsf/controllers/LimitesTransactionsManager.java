/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.jsf.controllers;

import com.sbs.easymbank.dao.LimitestransactionsFacade;
import com.sbs.easymbank.dao.ParametresFacade;
import com.sbs.easymbank.dao.ProfilsClientsFacade;
import com.sbs.easymbank.entities.Limitestransactions;
import com.sbs.easymbank.entities.Parametres;
import com.sbs.easymbank.entities.ProfilsClients;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author samuel
 */
@ManagedBean(name = "limitesTransactionsManager")
@RequestScoped
public class LimitesTransactionsManager implements Serializable {

    List<ProfilsClients> listProfils = new ArrayList<>();
    private ProfilsClients selectedProfilsClients = new ProfilsClients();
    private String leProfilClient;
    private Parametres nbreMaxWtoBParjrs;
    private Parametres nbreMaxBtowParJrs;
    private Parametres montantMaxWtoBParJrs;
    private Parametres montantMaxBtoWParjrs;
    private Parametres montantMaxBtoWParMois;
    private Parametres cumulMaxWtoB;
    private Parametres plafonnementMensuelB2W;
    private ProfilsClients selectedCar;
    private String typeTransaction;
    private String leParametre;
    private List<Parametres> paraList = new ArrayList<>();
    private List<Limitestransactions> listeLimites ;
    private List<String> designationsParametre;
    private Long newPamValue;
    @EJB
    ProfilsClientsFacade profilsClientsFacade;
    @EJB
    ParametresFacade parametresFacade;
    @EJB
    LimitestransactionsFacade limitesFacade;

    @ManagedProperty(value = "#{sessionManager}")
    private SessionManager sessionManager;

    public String getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(String typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public List<Limitestransactions> getListeLimites() {
        return listeLimites;
    }

    public void setListeLimites(List<Limitestransactions> listeLimites) {
        this.listeLimites = listeLimites;
    }

    public List<ProfilsClients> getListProfils() {
        return listProfils;
    }

    public void setListProfils(List<ProfilsClients> listProfils) {
        this.listProfils = listProfils;
    }

    public String getLeProfilClient() {
        return leProfilClient;
    }

    public void setLeProfilClient(String leProfilClient) {
        this.leProfilClient = leProfilClient;
    }

    public ProfilsClients getSelectedProfilsClients() {
        return selectedProfilsClients;
    }

    public void setSelectedProfilsClients(ProfilsClients selectedProfilsClients) {
        this.selectedProfilsClients = selectedProfilsClients;
    }

    public Parametres getNbreMaxWtoBParjrs() {
        return nbreMaxWtoBParjrs;
    }

    public void setNbreMaxWtoBParjrs(Parametres nbreMaxWtoBParjrs) {
        this.nbreMaxWtoBParjrs = nbreMaxWtoBParjrs;
    }

    public Parametres getNbreMaxBtowParJrs() {
        return nbreMaxBtowParJrs;
    }

    public void setNbreMaxBtowParJrs(Parametres nbreMaxBtowParJrs) {
        this.nbreMaxBtowParJrs = nbreMaxBtowParJrs;
    }

    public Parametres getMontantMaxWtoBParJrs() {
        return montantMaxWtoBParJrs;
    }

    public void setMontantMaxWtoBParJrs(Parametres montantMaxWtoBParJrs) {
        this.montantMaxWtoBParJrs = montantMaxWtoBParJrs;
    }

    public Parametres getMontantMaxBtoWParjrs() {
        return montantMaxBtoWParjrs;
    }

    public void setMontantMaxBtoWParjrs(Parametres montantMaxBtoWParjrs) {
        this.montantMaxBtoWParjrs = montantMaxBtoWParjrs;
    }

    public Parametres getMontantMaxBtoWParMois() {
        return montantMaxBtoWParMois;
    }

    public void setMontantMaxBtoWParMois(Parametres montantMaxBtoWParMois) {
        this.montantMaxBtoWParMois = montantMaxBtoWParMois;
    }

    public Parametres getCumulMaxWtoB() {
        return cumulMaxWtoB;
    }

    public void setCumulMaxWtoB(Parametres cumulMaxWtoB) {
        this.cumulMaxWtoB = cumulMaxWtoB;
    }

    public Parametres getPlafonnementMensuelB2W() {
        return plafonnementMensuelB2W;
    }

    public void setPlafonnementMensuelB2W(Parametres plafonnementMensuelB2W) {
        this.plafonnementMensuelB2W = plafonnementMensuelB2W;
    }

    public ProfilsClientsFacade getProfilsClientsFacade() {
        return profilsClientsFacade;
    }

    public void setProfilsClientsFacade(ProfilsClientsFacade profilsClientsFacade) {
        this.profilsClientsFacade = profilsClientsFacade;
    }

    public ParametresFacade getParametresFacade() {
        return parametresFacade;
    }

    public void setParametresFacade(ParametresFacade parametresFacade) {
        this.parametresFacade = parametresFacade;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public ProfilsClients getSelectedCar() {
        return selectedCar;
    }

    public String getLeParametre() {
        return leParametre;
    }

    public void setLeParametre(String leParametre) {
        this.leParametre = leParametre;
    }

    public List<Parametres> getParaList() {
        return paraList;
    }

    public void setParaList(List<Parametres> paraList) {
        this.paraList = paraList;
    }

    public List<String> getDesignationsParametre() {
        return designationsParametre;
    }

    public void setDesignationsParametre(List<String> designationsParametre) {
        this.designationsParametre = designationsParametre;
    }

    public Long getNewPamValue() {
        return newPamValue;
    }

    public void setNewPamValue(Long newPamValue) {
        this.newPamValue = newPamValue;
    }

    /**
     * Creates a new instance of LimitesTransactionsManager
     */
    public LimitesTransactionsManager() {
    }

    @PostConstruct
    public void init() {

        designationsParametre = new ArrayList<>();

        listProfils = profilsClientsFacade.findAll();
        
        listeLimites = limitesFacade.findAll();
//
//        System.out.println("liste de tout les profils");
//        for (ProfilsClients listProfil : listProfils) {
//            System.out.println(listProfil.getDesignationProfils());
//        }

//        System.out.println("liste de toutes les limites");
//        for (Limitestransactions listeLimite : listeLimites) {
//            System.out.println(" " + listeLimite.getDesignation());
//        }


    }

    /**
     * *
     * mise a jours de parametres sur transactions
     */
    public void transactionsParamsUpdate() {
        String laKey = "";
        //recup des infos 
        System.out.println("type de transaction  " + typeTransaction);
        System.out.println("profils de clients  " + leProfilClient);
        System.out.println("parametre  " + leParametre);
        System.out.println("valeur  " + newPamValue);

        //appel de la methode de update 
        Limitestransactions leupdate = new Limitestransactions();
        try {
            laKey = leParametre + leProfilClient + typeTransaction ;
            leupdate = limitesFacade.updateLimitestransactionByKeyColumn(laKey,newPamValue.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (leupdate != null) {
                System.out.println("mise a jours ok ");
            } else {
                System.out.println("erreur dans la mise a jours ");
            }
            laKey = "";
        }
        
        System.out.println("sauvegarde des modifs des parametres ...");
        addInfoMessage("parametrage modifié avec succès");

    }

    public void resetParamUpdate() {
        System.out.println("reset ...");
    }

    public void setSelectedCar(ProfilsClients selectedCar) {
        this.selectedCar = selectedCar;
    }

    protected void addInfoMessage(String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, "info"));
    }

    protected void addErrorMessage(String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, "erreur"));
    }

}
