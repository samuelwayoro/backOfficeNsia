/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.jsf.controllers;

import com.sbs.easymbank.dao.ParametresFacade;
import com.sbs.easymbank.dao.ProfilsClientsFacade;
import com.sbs.easymbank.entities.Parametres;
import com.sbs.easymbank.entities.ProfilsClients;
import com.sbs.exceptions.NonUniqueCodeException;
import com.sbs.exceptions.ParameterNotFoundException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author alex
 */
@ManagedBean
@ViewScoped

public class ProfilsClientsManager implements Serializable {

    List<ProfilsClients> listProfils;
    private ProfilsClients newProfilsClients = new ProfilsClients();
    private ProfilsClients selectedProfilsClients = new ProfilsClients();
    private Parametres nbreMaxWtoBParjrs;
    private Parametres nbreMaxBtowParJrs;
    private Parametres montantMaxWtoBParJrs;
    private Parametres montantMaxBtoWParjrs;
    private Parametres montantMaxBtoWParMois;
    private Parametres cumulMaxWtoB;
    private Parametres cumulMaxBtoW;
    private Parametres plafonnementMensuelB2W;

    @EJB
    ProfilsClientsFacade profilsClientsFacade;
    @EJB
    ParametresFacade parametresFacade;

    @ManagedProperty(value = "#{sessionManager}")
    private SessionManager sessionManager;

    public ProfilsClientsManager() {
    }

    @PostConstruct
    public void init() {
        listProfils = profilsClientsFacade.findAll();

         String[]  pattern = new String[]{"NOMBRE", "MONTANT","CUMUL"};
         List<Parametres> paraList = parametresFacade.findParametresByPatternFromBeginning(pattern);
        if (paraList != null && !paraList.isEmpty()) {
            for (Parametres para : paraList) {
                switch (para.getCodeparam()) {

                    case "NOMBRE_DOPERATION_JOURNALIER_B2W":
                        nbreMaxBtowParJrs = para;
                        System.out.println("valeur actuelle de nbre d'operations journlieres b2w " + nbreMaxBtowParJrs.getValeur());
                        break;
                    case "TRANSACTION_LIMITE_JOURNALIERE_B2W":
                        montantMaxBtoWParjrs = para;
                        System.out.println("valeur actuelle de montant maximum  b2w par jours " + montantMaxBtoWParjrs.getValeur());
                        break;
                    case "NOMBRE_OPERATIONS_MENSUELLES_B2W":
                        montantMaxBtoWParMois = para ;
                        System.out.println("valeur actuelle du nbre d'operations mensuelle b2w  "+montantMaxBtoWParMois.getValeur());
                        break;
                    case "PLAFONNEMENT_MENSUEL_B2W":
                        plafonnementMensuelB2W = para;
                        System.out.println("valeure actuelle de PLAFONNEMENT_MENSUEL_B2W  " + plafonnementMensuelB2W.getValeur());
                        break;
                        //debut recup parametre w2b
                    case "NOMBRE_MAX_TRANSACTION_W2B_JOURNALIER":
                        nbreMaxWtoBParjrs = para;
                        System.out.println("valeur actuelle de nbre max transaction w2b par jours " + nbreMaxWtoBParjrs.getValeur());
                        break;
                    case "MONTANT_MAX_TRANSACTION_W2B_JOURNALIER":
                        montantMaxWtoBParJrs = para;
                        System.out.println("valeur actuelle de  montant maximum  w2b par jours   " + montantMaxWtoBParJrs.getValeur());
                        break;
                    case "CUMUL_MAX_W2B_ORANGE":
                        cumulMaxWtoB = para;
                        System.out.println("valeur actuelle de CUMUL_MAX_W2B_ORANGE " + cumulMaxWtoB.getValeur());
                    default:
                        break;
                }

            }
        }
    }

    public Parametres getNbreMaxBtowParJrs() {
        return nbreMaxBtowParJrs;
    }

    public void setNbreMaxBtowParJrs(Parametres nbreMaxBtowParJrs) {
        this.nbreMaxBtowParJrs = nbreMaxBtowParJrs;
    }

    public Parametres getMontantMaxBtoWParMois() {
        return montantMaxBtoWParMois;
    }

    public void setMontantMaxBtoWParMois(Parametres montantMaxBtoWParMois) {
        this.montantMaxBtoWParMois = montantMaxBtoWParMois;
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

    
    public List<ProfilsClients> getListProfils() {
        return listProfils;
    }

    public void setListProfils(List<ProfilsClients> listProfils) {
        this.listProfils = listProfils;
    }

    public ProfilsClients getNewProfilsClients() {
        return newProfilsClients;
    }

    public void setNewProfilsClients(ProfilsClients newProfilsClients) {
        this.newProfilsClients = newProfilsClients;
    }

    public ProfilsClients getSelectedProfilsClients() {
        return selectedProfilsClients;
    }

    public void setSelectedProfilsClients(ProfilsClients selectedProfilsClients) {
        this.selectedProfilsClients = selectedProfilsClients;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public Parametres getNbreMaxWtoBParjrs() {
        return nbreMaxWtoBParjrs;
    }

    public void setNbreMaxWtoBParjrs(Parametres nbreMaxWtoBParjrs) {
        this.nbreMaxWtoBParjrs = nbreMaxWtoBParjrs;
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

    public Parametres getCumulMaxBtoW() {
        return cumulMaxBtoW;
    }

    public void setCumulMaxBtoW(Parametres cumulMaxBtoW) {
        this.cumulMaxBtoW = cumulMaxBtoW;
    }
    

    public void creerNouveauProfils() {
//        Agences nouvelleAgence=new Agences();
//        nouvelleAgence.setCodeagence(codeNouvelleAgence);
//                nouvelleAgence.setLibelle(libelleNouvelleAgence);
        try {
            newProfilsClients.setDateCreationProfils(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
            if (!uniqueCode(newProfilsClients)) {
                throw new NonUniqueCodeException(newProfilsClients.getCodeProfils());
            }
            profilsClientsFacade.create(newProfilsClients);
            listProfils.add(newProfilsClients);
            sessionManager.getLogs().setAction("CREATION DE PROFIL CLIENT");
            sessionManager.getLogs().setDateLog(new Date());
            sessionManager.getLogs().setMessage("LIBELLE PROFIL CLIENT: " + newProfilsClients.getDesignationProfils());
            sessionManager.logDB();
            newProfilsClients = new ProfilsClients();

            //  System.out.println("AGENCE: " + newProfilsClients.getCodeagence() + " " + newProfilsClients.getLibelle());
            addInfoMessage("PROFIL CREE AVEC SUCCES");
        } catch (Exception ex) {
            ex.printStackTrace();
            addErrorMessage("ERREUR LORS DE LA CREATION: " + ex.getMessage());
        }

    }

    public void supprimerProfilClient() {
        try {
            profilsClientsFacade.remove(selectedProfilsClients);
            listProfils.remove(selectedProfilsClients);
            sessionManager.getLogs().setAction("SUPPRESSION DE PROFIL CLIENT");
            sessionManager.getLogs().setDateLog(new Date());
            sessionManager.getLogs().setMessage("LIBELLE PROFIL: " + selectedProfilsClients.getDesignationProfils());
            sessionManager.logDB();
            selectedProfilsClients = new ProfilsClients();
            addInfoMessage("PROFIL CLIENT SUPPRIMEE AVEC SUCCES");
        } catch (Exception ex) {
            ex.printStackTrace();
            addErrorMessage("ERREUR LORS DE LA SUPPRESSION");
        }

    }

    private boolean uniqueCode(ProfilsClients profilsClients) {
        for (ProfilsClients pr : listProfils) {
            if (pr.getCodeProfils().equals(profilsClients.getCodeProfils())) {
                return false;
            }
        }
        return true;
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
