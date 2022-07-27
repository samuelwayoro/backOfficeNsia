/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.jsf.controllers;

import com.sbs.easymbank.dao.LimitestransactionsFacade;
import com.sbs.easymbank.dao.OperateursFacade;
import com.sbs.easymbank.dao.ParametresFacade;
import com.sbs.easymbank.dao.ProfilsClientsFacade;
import com.sbs.easymbank.entities.Limitestransactions;
import com.sbs.easymbank.entities.Operateurs;
import com.sbs.easymbank.entities.Parametres;
import com.sbs.easymbank.entities.ProfilsClients;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

/**
 *
 * @author samuel
 */
@ManagedBean(name = "limitesTransactionsManager")
@RequestScoped
public class LimitesTransactionsManager implements Serializable {

    List<ProfilsClients> listProfils = new ArrayList<>();
    List<String> listeInfosLimites = new ArrayList<>();
    List<Operateurs> operateurs = new ArrayList<>();
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
    private String keyColumn;
    private String typeDeTransactions;
    private String initialtransac;
    private String designation;
    private String nomOperateurTelco;
    private String info;
    private List<Parametres> paraList = new ArrayList<>();
    private List<Limitestransactions> listeLimites;
    private List<String> designationsParametre;
    private Long newPamValue;
    @EJB
    ProfilsClientsFacade profilsClientsFacade;
    @EJB
    ParametresFacade parametresFacade;
    @EJB
    LimitestransactionsFacade limitesFacade;
    @EJB
    OperateursFacade operateurFacade;

    @ManagedProperty(value = "#{sessionManager}")
    private SessionManager sessionManager;

    public String getTypeTransaction() {
        return typeTransaction;
    }

    @PostConstruct
    public void init() {

        designationsParametre = new ArrayList<>();

        listProfils = profilsClientsFacade.findAll();

        listeLimites = limitesFacade.findAll();

        listeInfosLimites = limitesFacade.listeInfosLimitesTransactions();

        operateurs = operateurFacade.findAll();

        initialtransac = "";

        designation = "";
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

    public String getNomOperateurTelco() {
        return nomOperateurTelco;
    }

    public void setNomOperateurTelco(String nomOperateurTelco) {
        this.nomOperateurTelco = nomOperateurTelco;
    }

    public List<Operateurs> getOperateurs() {
        return operateurs;
    }

    public void setOperateurs(List<Operateurs> operateurs) {
        this.operateurs = operateurs;
    }

    public OperateursFacade getOperateurFacade() {
        return operateurFacade;
    }

    public void setOperateurFacade(OperateursFacade operateurFacade) {
        this.operateurFacade = operateurFacade;
    }

    public List<String> getListeInfosLimites() {
        return listeInfosLimites;
    }

    public void setListeInfosLimites(List<String> listeInfosLimites) {
        this.listeInfosLimites = listeInfosLimites;
    }

    public LimitestransactionsFacade getLimitesFacade() {
        return limitesFacade;
    }

    public void setLimitesFacade(LimitestransactionsFacade limitesFacade) {
        this.limitesFacade = limitesFacade;
    }

    public void setTypeTransaction(String typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public String getTypeDeTransactions() {
        return typeDeTransactions;
    }

    public void setTypeDeTransactions(String typeDeTransactions) {
        this.typeDeTransactions = typeDeTransactions;
    }

    public String getInitialtransac() {
        return initialtransac;
    }

    public void setInitialtransac(String initialtransac) {
        this.initialtransac = initialtransac;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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

    public String getKeyColumn() {
        return keyColumn;
    }

    public void setKeyColumn(String keyColumn) {
        this.keyColumn = keyColumn;
    }

    /**
     * Creates a new instance of LimitesTransactionsManager
     */
    public LimitesTransactionsManager() {
    }

    /**
     * ajout d'une nouvelle limite de transaction
     */
    public void addTransactionsLimite() {
        System.out.println("valeur a rajouter ...");
        String newValue = newPamValue.toString();
        String keyColumn;
        //recup des infos 
        System.out.println("type de transaction  " + typeTransaction);
        System.out.println("profils de clients  " + leProfilClient);
        System.out.println("type de limite  " + info);
        System.out.println("nouvelle valeur  " + newValue);
        System.out.println("OPERATEUR SELECTIONNE  " + nomOperateurTelco);

        if (typeTransaction.equalsIgnoreCase("WALLET TO BANK")) {//W2B

            String[] words = typeTransaction.split(" ");
            typeDeTransactions = "";
            for (String w : words) {
                typeDeTransactions += w + "_";
            }
            initialtransac = "B2W";
            designation = "";

            //si standard et type de limite : recuperer l'operateur et concatener a l'image de la valeur attendu dans la collonne keycolumn ...
            switch (info) {
                case "nombre d'opérations journalier":
                    System.out.println("nouvelle valeur de type transaction : " + typeDeTransactions);
                    keyColumn = "nombreDoperationJournalier_" + leProfilClient + "_" + typeDeTransactions + nomOperateurTelco;
                    designation = "NOMBRE_DOPERATION_JOURNALIER_" + initialtransac + "_" + leProfilClient.toUpperCase() + "_" + nomOperateurTelco.toUpperCase();
                    System.out.println("valeur de keycolumn a ajouter ..." + keyColumn);
                    break;
                case "transaction limite journaliere":
                    System.out.println("nouvelle valeur de type transaction : " + typeDeTransactions);
                    keyColumn = "transactionLimiteJournaliere_" + leProfilClient + "_" + typeDeTransactions + nomOperateurTelco;
                    designation = "TRANSACTION_LIMITE_JOURNALIERE_" + initialtransac + "_" + leProfilClient.toUpperCase() + "_" + nomOperateurTelco.toUpperCase();
                    System.out.println("valeur de keycolumn a ajouter ..." + keyColumn);
                    break;
                case "plafonnement mensuel":
                    System.out.println("nouvelle valeur de type transaction : " + typeDeTransactions);
                    keyColumn = "plafonnementMensuel_" + leProfilClient + "_" + typeDeTransactions + nomOperateurTelco;
                    designation = "PLAFONNEMENT_MENSUEL_" + initialtransac + "_" + leProfilClient.toUpperCase() + "_" + nomOperateurTelco.toUpperCase();
                    System.out.println("valeur de keycolumn a ajouter ..." + keyColumn);
                    break;
                case "nombre d'opérations mensuel": //nombreDoperationMensuel_
                    System.out.println("nouvelle valeur de type transaction : " + typeDeTransactions);
                    keyColumn = "nombreDoperationMensuel_" + leProfilClient + "_" + typeDeTransactions + nomOperateurTelco;
                    designation = "NOMBRE_OPERATIONS_MENSUELLES_" + initialtransac + "_" + leProfilClient.toUpperCase() + "_" + nomOperateurTelco.toUpperCase();
                    System.out.println("valeur de keycolumn a ajouter ..." + keyColumn);
                    break;
                default:
                    throw new AssertionError();
            }

        } else {//B2W
            String[] words = typeTransaction.split(" ");
            typeDeTransactions = "";
            for (String w : words) {
                typeDeTransactions += w + "_";
            }
            initialtransac = "B2W";
            designation = "";

            //si standard et type de limite : recuperer l'operateur et concatener a l'image de la valeur attendu dans la collonne keycolumn ...
            switch (info) {
                case "nombre d'opérations journalier":
                    System.out.println("nouvelle valeur de type transaction : " + typeDeTransactions);
                    keyColumn = "nombreDoperationJournalier_" + leProfilClient + "_" + typeDeTransactions + nomOperateurTelco;
                    designation = "NOMBRE_DOPERATION_JOURNALIER_" + initialtransac + "_" + leProfilClient.toUpperCase() + "_" + nomOperateurTelco.toUpperCase();
                    System.out.println("valeur de keycolumn a ajouter ..." + keyColumn);
                    break;
                case "transaction limite journaliere":
                    System.out.println("nouvelle valeur de type transaction : " + typeDeTransactions);
                    keyColumn = "transactionLimiteJournaliere_" + leProfilClient + "_" + typeDeTransactions + nomOperateurTelco;
                    designation = "TRANSACTION_LIMITE_JOURNALIERE_" + initialtransac + "_" + leProfilClient.toUpperCase() + "_" + nomOperateurTelco.toUpperCase();
                    System.out.println("valeur de keycolumn a ajouter ..." + keyColumn);
                    break;
                case "plafonnement mensuel":
                    System.out.println("nouvelle valeur de type transaction : " + typeDeTransactions);
                    keyColumn = "plafonnementMensuel_" + leProfilClient + "_" + typeDeTransactions + nomOperateurTelco;
                    designation = "PLAFONNEMENT_MENSUEL_" + initialtransac + "_" + leProfilClient.toUpperCase() + "_" + nomOperateurTelco.toUpperCase();
                    System.out.println("valeur de keycolumn a ajouter ..." + keyColumn);
                    break;
                case "nombre d'opérations mensuel": //nombreDoperationMensuel_
                    System.out.println("nouvelle valeur de type transaction : " + typeDeTransactions);
                    keyColumn = "nombreDoperationMensuel_" + leProfilClient + "_" + typeDeTransactions + nomOperateurTelco;
                    designation = "NOMBRE_OPERATIONS_MENSUELLES_" + initialtransac + "_" + leProfilClient.toUpperCase() + "_" + nomOperateurTelco.toUpperCase();
                    System.out.println("valeur de keycolumn a ajouter ..." + keyColumn);
                    break;
                default:
                    throw new AssertionError();
            }
        }
        //et appeler la methode d'insertion dans la bd 
        Limitestransactions uneLimite = new Limitestransactions(designation, newValue, leProfilClient, typeTransaction, keyColumn, info, nomOperateurTelco);

        if (limitesFacade.isUnique(uneLimite)) {
            try {
                limitesFacade.addLimite(uneLimite, newValue);
                limitesFacade.create(uneLimite);

                addInfoMessage("Limite transactionnelle créee avec succès");

                sessionManager.getLogs().setAction("CREATION D'UNE NOUVELLE LIMITE DE TRANSACTION");
                sessionManager.getLogs().setDateLog(new Date());
                sessionManager.getLogs().setMessage("LIMITE : " + uneLimite.getKeyColumn() + " SERVICE: " + typeTransaction);
                sessionManager.logDB();

                uneLimite = new Limitestransactions();
                designation = null;
                newValue = null;
                leProfilClient = null;
                typeTransaction = null;
                keyColumn = null;
                info = null;
                nomOperateurTelco = null;

            } catch (Exception e) {
            }
        } else {
            addErrorMessage("ERREUR , cette limite de transaction existe déjà");
        }

    }

    /**
     * *
     * mise a jours de parametres sur transactions
     */
    public void transactionsParamsUpdate() {
        String newValue = newPamValue.toString();
        //recup des infos 
        System.out.println("type de transaction  " + typeTransaction);
        System.out.println("profils de clients  " + leProfilClient);
        System.out.println("type de limite  " + info);
        System.out.println("nouvelle valeur  " + newValue);
        System.out.println("OPERATEUR SELECTIONNE  " + nomOperateurTelco);

        //appel de la methode de update 
        Limitestransactions leupdate = new Limitestransactions();
        leupdate = limitesFacade.updateLimitestransactionByKeyColumn(leProfilClient, typeTransaction, info, nomOperateurTelco, newValue);

        if (leupdate == null) {
            System.out.println("erreur dans la mise a jours ");
            addErrorMessage("Erreur , cette limite n'existe pas ");
        } else {
            System.out.println("mise a jours ok ");
            System.out.println("sauvegarde des modifs des parametres ...");
            addInfoMessage("parametrage modifié avec succès");
        }

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
