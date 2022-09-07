/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.jsf.controllers;

import at.telekom.util.axis.SSLClientAxisEngineConfig;
import com.sbs.easymbank.dao.PagesouscriptionFacade;
import com.sbs.easymbank.dao.ParametresFacade;
import com.sbs.easymbank.dao.ValeursparametresFacade;
import com.sbs.easymbank.entities.Pagesouscription;

import com.sbs.easymbank.entities.Parametres;
import com.sbs.easymbank.service.omapi.IdlePort_PortType;
import com.sbs.easymbank.service.omapi.IdleServiceLocator;
import com.sbs.exceptions.*;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.xml.rpc.ServiceException;

/**
 *
 * @author SOCITECH-
 */
@ManagedBean(name = "parametrageManager")
@ApplicationScoped
public class ParametrageManager implements Serializable {

    private List<Parametres> listPara;
    private List<Parametres> listValeursSouscription;
    private List<Parametres> listValeursIdle;
    private List<Pagesouscription> listPagesouscription;

    private String libelle;
    private boolean idle;
    private boolean supvalidation;
    private SSLClientAxisEngineConfig sSLConfig = new SSLClientAxisEngineConfig();
    private Parametres selectedModeSouscription = new Parametres();
    private Parametres nombreMaxTrancations = new Parametres();
    private Parametres montantMaxTrancations = new Parametres();
    private Parametres modeIdle = new Parametres();
    private Pagesouscription selectedPageSouscription;
    private Parametres supervalidation = new Parametres();
    private String urlLogo;
    private boolean balanceAllowed;
    private boolean statementAllowed;
    private boolean b2wAllowed;
    private boolean w2bAllowed;
    private boolean balanceTarifAllowed;
    private boolean statementTarifAllowed;
    private boolean b2wTarifAllowed;
    private boolean w2bTarifAllowed;
    private boolean registerTarifAllowed;
    private boolean unregisterTarifAllowed;
    private boolean isSoldeLimited;
    private boolean isReleveLimited;
    private String modeTarif;
    private Parametres autorisationBalance = new Parametres();
    private Parametres autorisationStatement = new Parametres();
    private Parametres autorisationB2W = new Parametres();
    private Parametres autorisationW2B = new Parametres();
    private Parametres autorisationTarifBalance = new Parametres();
    private Parametres autorisationTarifStatement = new Parametres();
    private Parametres autorisationTarifB2W = new Parametres();
    private Parametres autorisationTarifW2B = new Parametres();
    private Parametres autorisationTarifRegister = new Parametres();
    private Parametres autorisationTarifUnregister = new Parametres();
    private Parametres modeTarificationParam = new Parametres();
    private Parametres limiteSolde;
    private Parametres limiteReleve;

    private Converter modeSouscriptionConverter = new Converter() {

        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {

            Parametres laValeur = new Parametres();
            for (Parametres val : listValeursSouscription) {
                if (val.getCodeparam().equals(value)) {
                    System.out.println("LA VALEUR DU PARAMETRE: " + value);
                    System.out.println("LA VALEUR DE L'OBJET: " + val.getCodeparam());
                    laValeur = val;
                    break;
                }

            }
            return laValeur;
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            // System.out.println("LA VALEUR: "+value.toString() );
            return ((Parametres) value).getCodeparam();
        }
    };

    @EJB
    ParametresFacade parametresFacade;
    @EJB
    ValeursparametresFacade valeursparametresFacade;
    @EJB
    PagesouscriptionFacade pagesouscriptionFacade;

    public ParametrageManager() {
    }

    @PostConstruct
    public void init() {
        try {
            //listPara=parametresFacade.findAll();
            // valParamSouscription=new Valeursparametres();
            // String pattern[]={"AUTORISATION"};
            listValeursSouscription = parametresFacade.findListValeursForParam("MODESOUSCRIPTION_VALUE");
            listValeursIdle = parametresFacade.findListValeursForParam("MODEIDLE_VALUE");
            System.out.println("LISTE VALEUR: " + listValeursSouscription.size());
            //Paramètre définissant l'état du mode idle
            List<Parametres> l = parametresFacade.findByCodeParam("MODEIDLE");
            if (l != null && !l.isEmpty()) {
                modeIdle = l.get(0);
                l.clear();
            } else {
                throw new ParameterNotFoundException("MODEIDLE");
            }

            //liste des pages pour les souscriptions
            listPagesouscription = pagesouscriptionFacade.findByOperateur("ORANGE");
            //Paramètre définissant l'activation ou non de la supervalidation
            l = parametresFacade.findByCodeParam("SUPERVALIDATION");
            if (l != null && !l.isEmpty()) {
                supervalidation = l.get(0);
                l.clear();
            } else {
                throw new ParameterNotFoundException("SUPERVALIDATION");
            }
            //Paramètre définissant le mode de souscription
            l = parametresFacade.findByCodeParam("MODESOUSCRIPTION");
            if (l != null && !l.isEmpty()) {
                selectedModeSouscription = l.get(0);
                l.clear();
            } else {
                throw new ParameterNotFoundException("MODESOUSCRIPTION");
            }
//            //Paramètre définissant le nombre max de transactions
//            l = parametresFacade.findByCodeParam("NOMBRE_MAX_TRANSACTION");
//            if (l != null && !l.isEmpty()) {
//                nombreMaxTrancations = l.get(0);
//                l.clear();
//            } else {
//                throw new ParameterNotFoundException("NOMBRE_MAX_TRANSACTION");
//            }

            //Paramètre définissant le montant max de transactions
            l = parametresFacade.findByCodeParam("MONTANT_MAX_TRANSACTION");
            if (l != null && !l.isEmpty()) {
                montantMaxTrancations = l.get(0);
                l.clear();
            } else {
                throw new ParameterNotFoundException("MONTANT_MAX_TRANSACTION");
            }

            //Paramètre définissant le nombre max de consultation journalière de solde  par client
            l = parametresFacade.findByCodeParam("LIMITE_DEMANDE_SOLDE");
            if (l != null && !l.isEmpty()) {
                limiteSolde = l.get(0);
                isSoldeLimited = isLimitationEnabled(limiteSolde);
                l.clear();
            }

            //Paramètre définissant le nombre max de consultation journalière de relevé  par client
            l = parametresFacade.findByCodeParam("LIMITE_DEMANDE_RELEVE");
            if (l != null && !l.isEmpty()) {
                limiteReleve = l.get(0);
                isReleveLimited = isLimitationEnabled(limiteReleve);
                l.clear();
            }

            reglerPageSouscription();

            if (selectedModeSouscription == null) {
                selectedModeSouscription = new Parametres();
                selectedPageSouscription = listPagesouscription.get(0);
            }
            System.out.println("VALEUR ACTUEL DE MODEIDLE ORANGE EN BD  ---: " + modeIdle.getValeur());
            idle = !modeIdle.getValeur().equals("IDLE_OUI");
            System.out.println("valeure prise par idle en fonction de MODEIDLE   "+idle);
            supvalidation = supervalidation.getValeur().equals("SUPERVALIDATION_OUI");

            System.out.println("valeur de supervalidation  " + supervalidation.getValeur());

            String[] pattern = new String[]{"KEYSTORE", "TRUSTSTORE", "AUTORISATION"};
            List<Parametres> paraList = parametresFacade.findParametresByPatten(pattern);
            if (paraList != null && !paraList.isEmpty()) {
                for (Parametres para : paraList) {
                    switch (para.getCodeparam()) {
                        case "CHEMIN_FICHIER_KEYSTORE":
                            sSLConfig.setKeystore(para.getValeur());
                            break;
                        case "CHEMIN_FICHIER_TRUSTSTORE":
                            sSLConfig.setTruststore(para.getValeur());
                            break;
                        default:
                            sSLConfig.setKeystorePassword(para.getValeur());
                            break;
                    }
                }
            }

            pattern = new String[]{"AUTORISATION", "TARIFICATION"};
            paraList = parametresFacade.findParametresByPatternFromBeginning(pattern);
            if (paraList != null && !paraList.isEmpty()) {
                for (Parametres para : paraList) {
                    switch (para.getCodeparam()) {

                        case "AUTORISATION_BALANCE":
                            autorisationBalance = para;
                            //System.out.println("VALEUR RECUPEREE POUR PARAMETRE AUTORISATION_BALANCE    "+autorisationBalance.getValeur());
                            balanceAllowed = para.getValeur().equals("OPERATION_AUTORISEE");
                            // System.out.println("VALEUR ATTRIBUEE A balanceAllowed apres test   "+balanceAllowed);
                            break;
                        case "AUTORISATION_MINISTATEMENT":
                            autorisationStatement = para;
                            statementAllowed = para.getValeur().equals("OPERATION_AUTORISEE");
                            break;
                        case "AUTORISATION_B2W":
                            autorisationB2W = para;
                            b2wAllowed = para.getValeur().equals("OPERATION_AUTORISEE");
                            break;
                        case "AUTORISATION_W2B":
                            autorisationW2B = para;
                            w2bAllowed = para.getValeur().equals("OPERATION_AUTORISEE");
                            break;
                        case "TARIFICATION_B2W":
                            autorisationTarifB2W = para;
                            b2wTarifAllowed = para.getValeur().equals("TARIFICATION_AUTORISEE");
                            break;
                        case "TARIFICATION_W2B":
                            autorisationTarifW2B = para;
                            w2bTarifAllowed = para.getValeur().equals("TARIFICATION_AUTORISEE");
                            break;
                        case "TARIFICATION_MINISTATEMENT":
                            autorisationTarifStatement = para;
                            statementTarifAllowed = para.getValeur().equals("TARIFICATION_AUTORISEE");
                            break;
                        case "TARIFICATION_ACCOUNTBALANCE":
                            autorisationTarifBalance = para;
                            balanceTarifAllowed = para.getValeur().equals("TARIFICATION_AUTORISEE");
                            break;
                        case "TARIFICATION_REGISTER":
                            autorisationTarifRegister = para;
                            registerTarifAllowed = para.getValeur().equals("TARIFICATION_AUTORISEE");
                            break;
                        case "TARIFICATION_UNREGISTER":
                            autorisationTarifUnregister = para;
                            unregisterTarifAllowed = para.getValeur().equals("TARIFICATION_AUTORISEE");
                            break;
                        case "TARIFICATION":
                            modeTarificationParam = para;
                            modeTarif = para.getValeur();
                            break;
                        default:
                            break;
                    }
                }
            }

            sSLConfig.setKeystoreType("JKS");

            sSLConfig.setTruststoreType("JKS");
            sSLConfig.initialize();

            List<Parametres> listLogo = parametresFacade.findByCodeParam("LOGO_URL");
            if (!listLogo.isEmpty()) {
                urlLogo = listLogo.get(0).getValeur();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public SSLClientAxisEngineConfig getsSLConfig() {
        return sSLConfig;
    }

    public void setsSLConfig(SSLClientAxisEngineConfig sSLConfig) {
        this.sSLConfig = sSLConfig;
    }

    public Parametres getAutorisationBalance() {
        return autorisationBalance;
    }

    public void setAutorisationBalance(Parametres autorisationBalance) {
        this.autorisationBalance = autorisationBalance;
    }

    public Parametres getAutorisationStatement() {
        return autorisationStatement;
    }

    public void setAutorisationStatement(Parametres autorisationStatement) {
        this.autorisationStatement = autorisationStatement;
    }

    public Parametres getAutorisationB2W() {
        return autorisationB2W;
    }

    public void setAutorisationB2W(Parametres autorisationB2W) {
        this.autorisationB2W = autorisationB2W;
    }

    public Parametres getAutorisationW2B() {
        return autorisationW2B;
    }

    public void setAutorisationW2B(Parametres autorisationW2B) {
        this.autorisationW2B = autorisationW2B;
    }

    public Parametres getAutorisationTarifBalance() {
        return autorisationTarifBalance;
    }

    public void setAutorisationTarifBalance(Parametres autorisationTarifBalance) {
        this.autorisationTarifBalance = autorisationTarifBalance;
    }

    public Parametres getAutorisationTarifStatement() {
        return autorisationTarifStatement;
    }

    public void setAutorisationTarifStatement(Parametres autorisationTarifStatement) {
        this.autorisationTarifStatement = autorisationTarifStatement;
    }

    public Parametres getAutorisationTarifB2W() {
        return autorisationTarifB2W;
    }

    public void setAutorisationTarifB2W(Parametres autorisationTarifB2W) {
        this.autorisationTarifB2W = autorisationTarifB2W;
    }

    public Parametres getAutorisationTarifW2B() {
        return autorisationTarifW2B;
    }

    public void setAutorisationTarifW2B(Parametres autorisationTarifW2B) {
        this.autorisationTarifW2B = autorisationTarifW2B;
    }

    public Parametres getAutorisationTarifRegister() {
        return autorisationTarifRegister;
    }

    public void setAutorisationTarifRegister(Parametres autorisationTarifRegister) {
        this.autorisationTarifRegister = autorisationTarifRegister;
    }

    public Parametres getAutorisationTarifUnregister() {
        return autorisationTarifUnregister;
    }

    public void setAutorisationTarifUnregister(Parametres autorisationTarifUnregister) {
        this.autorisationTarifUnregister = autorisationTarifUnregister;
    }

    public Parametres getModeTarificationParam() {
        return modeTarificationParam;
    }

    public void setModeTarificationParam(Parametres modeTarificationParam) {
        this.modeTarificationParam = modeTarificationParam;
    }

    public Parametres getLimiteSolde() {
        return limiteSolde;
    }

    public void setLimiteSolde(Parametres limiteSolde) {
        this.limiteSolde = limiteSolde;
    }

    public Parametres getLimiteReleve() {
        return limiteReleve;
    }

    public void setLimiteReleve(Parametres limiteReleve) {
        this.limiteReleve = limiteReleve;
    }

    public ParametresFacade getParametresFacade() {
        return parametresFacade;
    }

    public void setParametresFacade(ParametresFacade parametresFacade) {
        this.parametresFacade = parametresFacade;
    }

    public ValeursparametresFacade getValeursparametresFacade() {
        return valeursparametresFacade;
    }

    public void setValeursparametresFacade(ValeursparametresFacade valeursparametresFacade) {
        this.valeursparametresFacade = valeursparametresFacade;
    }

    public PagesouscriptionFacade getPagesouscriptionFacade() {
        return pagesouscriptionFacade;
    }

    public void setPagesouscriptionFacade(PagesouscriptionFacade pagesouscriptionFacade) {
        this.pagesouscriptionFacade = pagesouscriptionFacade;
    }

    public List<Parametres> getListPara() {
        return listPara;
    }

    public void setListPara(List<Parametres> listPara) {
        this.listPara = listPara;
    }

    public List<Parametres> getListValeursSouscription() {
        return listValeursSouscription;
    }

    public void setListValeursSouscription(List<Parametres> listValeursSouscription) {
        this.listValeursSouscription = listValeursSouscription;
    }

    public Parametres getSelectedModeSouscription() {
        return selectedModeSouscription;
    }

    public void setSelectedModeSouscription(Parametres selectedModeSouscription) {
        this.selectedModeSouscription = selectedModeSouscription;
    }

//   public Converter getModeSouscriptionConverter() {
//        return modeSouscriptionConverter;
//    }
//
//    public void setModeSouscriptionConverter(Converter modeSouscriptionConverter) {
//        this.modeSouscriptionConverter = modeSouscriptionConverter;
//    }
//    
    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public List<Pagesouscription> getListPagesouscription() {
        return listPagesouscription;
    }

    public void setListPagesouscription(List<Pagesouscription> listPagesouscription) {
        this.listPagesouscription = listPagesouscription;
    }

    public Pagesouscription getSelectedPageSouscription() {
        return selectedPageSouscription;
    }

    public void setSelectedPageSouscription(Pagesouscription selectedPageSouscription) {
        this.selectedPageSouscription = selectedPageSouscription;
    }

    public boolean isIdle() {
        return idle;
    }

    public void setIdle(boolean idle) {
        this.idle = idle;

    }

    public Parametres getModeIdle() {
        return modeIdle;
    }

    public void setModeIdle(Parametres modeIdle) {
        this.modeIdle = modeIdle;
    }

    public Parametres getSupervalidation() {
        return supervalidation;
    }

    public void setSupervalidation(Parametres supervalidation) {
        this.supervalidation = supervalidation;
    }

    public Converter getModeSouscriptionConverter() {
        return modeSouscriptionConverter;
    }

    public void setModeSouscriptionConverter(Converter modeSouscriptionConverter) {
        this.modeSouscriptionConverter = modeSouscriptionConverter;
    }

    public List<Parametres> getListValeursIdle() {
        return listValeursIdle;
    }

    public void setListValeursIdle(List<Parametres> listValeursIdle) {
        this.listValeursIdle = listValeursIdle;
    }

    public boolean isSupvalidation() {
        return supvalidation;
    }

    public void setSupvalidation(boolean supvalidation) {
        this.supvalidation = supvalidation;
    }

    public Parametres getNombreMaxTrancations() {
        return nombreMaxTrancations;
    }

    public void setNombreMaxTrancations(Parametres nombreMaxTrancations) {
        this.nombreMaxTrancations = nombreMaxTrancations;
    }

    public Parametres getMontantMaxTrancations() {
        return montantMaxTrancations;
    }

    public void setMontantMaxTrancations(Parametres montantMaxTrancations) {
        this.montantMaxTrancations = montantMaxTrancations;
    }

    public String getUrlLogo() {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }

    public boolean isStatementAllowed() {
        return statementAllowed;
    }

    public void setStatementAllowed(boolean statementAllowed) {
        this.statementAllowed = statementAllowed;
    }

    public boolean isB2wAllowed() {
        return b2wAllowed;
    }

    public void setB2wAllowed(boolean b2wAllowed) {
        this.b2wAllowed = b2wAllowed;
    }

    public boolean isW2bAllowed() {
        return w2bAllowed;
    }

    public void setW2bAllowed(boolean w2bAllowed) {
        this.w2bAllowed = w2bAllowed;
    }

    public boolean isBalanceAllowed() {
        return balanceAllowed;
    }

    public void setBalanceAllowed(boolean balanceAllowed) {
        this.balanceAllowed = balanceAllowed;
    }

    public boolean isBalanceTarifAllowed() {
        return balanceTarifAllowed;
    }

    public void setBalanceTarifAllowed(boolean balanceTarifAllowed) {
        this.balanceTarifAllowed = balanceTarifAllowed;
    }

    public boolean isStatementTarifAllowed() {
        return statementTarifAllowed;
    }

    public void setStatementTarifAllowed(boolean statementTarifAllowed) {
        this.statementTarifAllowed = statementTarifAllowed;
    }

    public boolean isB2wTarifAllowed() {
        return b2wTarifAllowed;
    }

    public void setB2wTarifAllowed(boolean b2wTarifAllowed) {
        this.b2wTarifAllowed = b2wTarifAllowed;
    }

    public boolean isW2bTarifAllowed() {
        return w2bTarifAllowed;
    }

    public void setW2bTarifAllowed(boolean w2bTarifAllowed) {
        this.w2bTarifAllowed = w2bTarifAllowed;
    }

    public boolean isRegisterTarifAllowed() {
        return registerTarifAllowed;
    }

    public void setRegisterTarifAllowed(boolean registerTarifAllowed) {
        this.registerTarifAllowed = registerTarifAllowed;
    }

    public boolean isUnregisterTarifAllowed() {
        return unregisterTarifAllowed;
    }

    public void setUnregisterTarifAllowed(boolean unregisterTarifAllowed) {
        this.unregisterTarifAllowed = unregisterTarifAllowed;
    }

    public String getModeTarif() {
        return modeTarif;
    }

    public void setModeTarif(String modeTarif) {
        this.modeTarif = modeTarif;
    }

    public boolean isIsSoldeLimited() {
        return isSoldeLimited;
    }

    public void setIsSoldeLimited(boolean isSoldeLimited) {
        this.isSoldeLimited = isSoldeLimited;
    }

    public boolean isIsReleveLimited() {
        return isReleveLimited;
    }

    public void setIsReleveLimited(boolean isReleveLimited) {
        this.isReleveLimited = isReleveLimited;
    }

    public void enregistrerModeSouscription() {
        // System.out.println("MODE: " + selectedModeSouscription.getCodeparam());
        parametresFacade.edit(selectedModeSouscription);
        reglerPageSouscription();

    }

    public void modifierDisponibilite() {
        System.out.println("lacement du changement de la disponibilite de mode idle");
        System.out.println("entree dans sendIdleRequest pour recup dans ok ");
        boolean ok = sendIdleRequest();
        System.out.println("fin de sendIdleRequest");
        System.out.println(" VALEUR DE ok  " + ok);
        System.out.println("changement de la valeur d idle en fonction de celle de ok - valeure actuelle de idle :  " + idle);

        if (!ok) {
            idle = !idle;
            addMessage("DISPONIBILITE", "Le serveur ne peut changer d'état en ce moment");
        } else {
            addMessage("DISPONIBILITE", "Le serveur a changé d'état avec succès");
        }

        if (idle) {
            for (Parametres val : listValeursIdle) {
                if (val.getCodeparam().equals("IDLE_NON")) {
                    modeIdle.setValeur(val.getCodeparam());
                    break;
                }
            }
        } else {
            for (Parametres val : listValeursIdle) {
                if (val.getCodeparam().equals("IDLE_OUI")) {
                    modeIdle.setValeur(val.getCodeparam());

                    break;
                }
            }
        }
        reglerPageSouscription();
        parametresFacade.edit(modeIdle);
        // List<Object> l=new ArrayList();
        //System.out.println(valeursparametresFacade.test_procedure());
    }

    public void modifierSupervalidation() {
        if (supvalidation) {
            supervalidation.setValeur("SUPERVALIDATION_OUI");
            addMessage("SUPERVALIDATION", "La supervalidation est activée");
        } else {
            supervalidation.setValeur("SUPERVALIDATION_NON");
            addMessage("SUPERVALIDATION", "La supervalidation est désactivée");
        }

        parametresFacade.edit(supervalidation);

    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void addMessageErreur(String summary, String detail) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    //Met à jour la page de souscription en fonction du mode de souscription
    public void reglerPageSouscription() {
        if (modeIdle.getValeur().equals("IDLE_OUI")) {
            for (Pagesouscription page : listPagesouscription) {
                if (page.getMode().contains("IDLE")) {
                    //   System.out.println(page.getMode());
                    selectedPageSouscription = page;
                    break;
                }
            }
        } else {
            for (Pagesouscription page : listPagesouscription) {
                if (page.getMode().equals(selectedModeSouscription.getValeur())) {
                    selectedPageSouscription = page;
                    break;
                }

            }
        }

    }

    public boolean sendIdleRequest() {
        System.out.println("lacement de la methode sendIdlerequest");
        System.out.println("parametrage de locator de type IdleServiceLocator ");
        IdleServiceLocator locator = new IdleServiceLocator(sSLConfig);
        System.out.println("recup de IDLE_URL dans la bd");
        List<Parametres> url = parametresFacade.findByCodeParam("IDLE_URL");
        System.out.println("nbre de IDLE_URL trouve  :"+url.size());
        try {
            System.out.println("valeur de idle pour parametrage de indispo  " + idle);
            String indispo = idle ? "false" : "true";
            System.out.println("valeur prise par indispo    " + indispo);
            IdlePort_PortType rgpt = locator.getIdlePort(new URL(url.get(0).getValeur()));
            return rgpt.setIdle(indispo);
        } catch (MalformedURLException | RemoteException | ServiceException ex) {
            return false;
        }
    }

    public void enregistrerNombreMaxTransactions() {
        parametresFacade.edit(nombreMaxTrancations);
    }

    public void enregistrerMontantMaxTransactions() {
        parametresFacade.edit(montantMaxTrancations);
    }

    public void modifierAutorisationBalance() {
        if (balanceAllowed) {
            autorisationBalance.setValeur("OPERATION_AUTORISEE");
            parametresFacade.edit(autorisationBalance);
        } else {
            autorisationBalance.setValeur("OPERATION_NON_AUTORISEE");
            parametresFacade.edit(autorisationBalance);
        }

    }

    public void modifierAutorisationStatement() {
        if (statementAllowed) {
            autorisationStatement.setValeur("OPERATION_AUTORISEE");
            parametresFacade.edit(autorisationStatement);
        } else {
            autorisationStatement.setValeur("OPERATION_NON_AUTORISEE");
            parametresFacade.edit(autorisationStatement);
        }

    }

    public void modifierAutorisationB2W() {
        if (b2wAllowed) {
            autorisationB2W.setValeur("OPERATION_AUTORISEE");
            parametresFacade.edit(autorisationB2W);
        } else {
            autorisationB2W.setValeur("OPERATION_NON_AUTORISEE");
            parametresFacade.edit(autorisationB2W);
        }

    }

    public void modifierAutorisationW2B() {
        if (w2bAllowed) {
            autorisationW2B.setValeur("OPERATION_AUTORISEE");
            parametresFacade.edit(autorisationW2B);
        } else {
            autorisationW2B.setValeur("OPERATION_NON_AUTORISEE");
            parametresFacade.edit(autorisationW2B);
        }

    }

    public void modifierAutorisationTarifBalance() {
        if (balanceTarifAllowed) {
            autorisationTarifBalance.setValeur("TARIFICATION_AUTORISEE");
            parametresFacade.edit(autorisationTarifBalance);
        } else {
            autorisationTarifBalance.setValeur("TARIFICATION_NON_AUTORISEE");
            parametresFacade.edit(autorisationTarifBalance);
        }

    }

    public void modifierAutorisationTarifStatement() {
        if (statementTarifAllowed) {
            autorisationTarifStatement.setValeur("TARIFICATION_AUTORISEE");
            parametresFacade.edit(autorisationTarifStatement);
        } else {
            autorisationTarifStatement.setValeur("TARIFICATION_NON_AUTORISEE");
            parametresFacade.edit(autorisationTarifStatement);
        }

    }

    public void modifierAutorisationTarifB2W() {
        if (b2wTarifAllowed) {
            autorisationTarifB2W.setValeur("TARIFICATION_AUTORISEE");
            parametresFacade.edit(autorisationTarifB2W);
        } else {
            autorisationTarifB2W.setValeur("TARIFICATION_NON_AUTORISEE");
            parametresFacade.edit(autorisationTarifB2W);
        }

    }

    public void modifierAutorisationTarifW2B() {
        if (w2bTarifAllowed) {
            autorisationTarifW2B.setValeur("TARIFICATION_AUTORISEE");
            parametresFacade.edit(autorisationTarifW2B);
        } else {
            autorisationTarifW2B.setValeur("TARIFICATION_NON_AUTORISEE");
            parametresFacade.edit(autorisationTarifW2B);
        }

    }

    public void modifierAutorisationTarifRegister() {
        if (registerTarifAllowed) {
            autorisationTarifRegister.setValeur("TARIFICATION_AUTORISEE");
            parametresFacade.edit(autorisationTarifRegister);
        } else {
            autorisationTarifRegister.setValeur("TARIFICATION_NON_AUTORISEE");
            parametresFacade.edit(autorisationTarifRegister);
        }

    }

    public void modifierAutorisationTarifUnregister() {
        if (unregisterTarifAllowed) {
            autorisationTarifUnregister.setValeur("TARIFICATION_AUTORISEE");
            parametresFacade.edit(autorisationTarifUnregister);
        } else {
            autorisationTarifUnregister.setValeur("TARIFICATION_NON_AUTORISEE");
            parametresFacade.edit(autorisationTarifUnregister);
        }

    }

    public void modifierModeTarification() {

        modeTarificationParam.setValeur(modeTarif);
        parametresFacade.edit(modeTarificationParam);
    }

    private boolean isLimitationEnabled(Parametres parametres) {
        return (parametres != null && parametres.getValeur() != null && !parametres.getValeur().isEmpty());
    }

    public void resetParamsupdate() {
        System.out.println("reset param");
    }

}
