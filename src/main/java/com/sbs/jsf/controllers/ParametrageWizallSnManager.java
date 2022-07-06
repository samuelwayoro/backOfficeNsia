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
import com.sbs.exceptions.ParameterNotFoundException;
import java.io.Serializable;
import java.net.URL;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author samuel
 */
@Named(value = "parametrageWizallSnManager")
@ApplicationScoped
public class ParametrageWizallSnManager implements Serializable {

    private List<Parametres> listPara;
    private List<Parametres> listValeursSouscription;
    private List<Parametres> listValeursIdle;
    private List<Pagesouscription> listPagesouscription;
    @ManagedProperty(value = "#{parametrageManager}")
    private ParametrageManager parametrageManager;
    private String libelle;
    private boolean idle;
    private boolean supvalidation;
    private SSLClientAxisEngineConfig sSLConfig = new SSLClientAxisEngineConfig();
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

    private Parametres selectedModeSouscription = new Parametres();
    private Parametres nombreMaxTrancations = new Parametres();
    private Parametres montantMaxTrancations = new Parametres();
    private Parametres modeIdle = new Parametres();
    private Pagesouscription selectedPageSouscription;
    private Parametres supervalidation = new Parametres();
    String urlLogo;
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
    @EJB
    ParametresFacade parametresFacade;
    @EJB
    ValeursparametresFacade valeursparametresFacade;
    @EJB
    PagesouscriptionFacade pagesouscriptionFacade;

    /**
     * Creates a new instance of ParametrageWizallSnManager
     */
    public ParametrageWizallSnManager() {
    }

    @PostConstruct
    public void init() {
        try {
            listValeursSouscription = parametresFacade.findListValeursForParam("MODESOUSCRIPTION_VALUE");
            listValeursIdle = parametresFacade.findListValeursForParam("MODEIDLE_VALUE");
            List<Parametres> l = parametresFacade.findByCodeParam("MODEIDLE_WIZALLSN");
            if (l != null && !l.isEmpty()) {
                modeIdle = l.get(0);
                l.clear();
            } else {
                throw new ParameterNotFoundException("MODEIDLE_WIZALLSN");
            }
            //list des pages pour les souscriptions
            listPagesouscription = pagesouscriptionFacade.findByOperateur("WIZALLSN");
            // Paramètre définissant l'activation ou non de la supervalidation
            l = parametresFacade.findByCodeParam("SUPERVALIDATION_WIZALL_SN");
            if (l != null && !l.isEmpty()) {
                supervalidation = l.get(0);
                l.clear();
            } else {
                throw new ParameterNotFoundException("SUPERVALIDATION_WIZALL_SN");
            }
            //supervalidation=parametrageManager.getSupervalidation();
            //Paramètre définissant le mode de souscription
            l = parametresFacade.findByCodeParam("MODESOUSCRIPTION_WIZALL_SN");
            if (l != null && !l.isEmpty()) {
                selectedModeSouscription = l.get(0);
                l.clear();
            } else {
                throw new ParameterNotFoundException("MODESOUSCRIPTION_WIZALL_SN");
            }
            //Paramètre définissant le nombre max de transactions
            l = parametresFacade.findByCodeParam("NOMBRE_MAX_TRANSACTION_WIZALLSN");
            if (l != null && !l.isEmpty()) {
                nombreMaxTrancations = l.get(0);
                l.clear();
            } else {
                throw new ParameterNotFoundException("NOMBRE_MAX_TRANSACTION_WIZALL_SN");
            }

            //Paramètre définissant le montant max de transactions
            l = parametresFacade.findByCodeParam("MONTANT_MAX_TRANSACTION_WIZALL_SN");
            if (l != null && !l.isEmpty()) {
                montantMaxTrancations = l.get(0);
                l.clear();
            } else {
                throw new ParameterNotFoundException("MONTANT_MAX_TRANSACTION_WIZALL_SN");
            }

            reglerPageSouscription();

            if (selectedModeSouscription == null) {
                selectedModeSouscription = new Parametres();
                selectedPageSouscription = listPagesouscription.get(0);
            }

            //System.out.println("IDLE: " + modeIdle.getCodeparam());
            idle = !modeIdle.getValeur().equals("IDLE_OUI");
            System.out.println("VALEUR DU MODE DE IDLE  -------------> " + idle);
            supvalidation = supervalidation.getValeur().equals("SUPERVALIDATION_OUI");
            System.out.println("VALEUR DE LA SUPERVALIDATION DE WIZALL ....." + supvalidation);

            String[] pattern = new String[]{"KEYSTORE", "TRUSTSTORE"};
            List<Parametres> paraList = parametresFacade.findParametresByPatten(pattern);
//            if (paraList != null && !paraList.isEmpty()) {
//                for (Parametres para : paraList) {
//                    switch (para.getCodeparam()) {
//                        case "CHEMIN_FICHIER_KEYSTORE":
//                            sSLConfig.setKeystore(para.getValeur());
//                            break;
//                        case "CHEMIN_FICHIER_TRUSTSTORE":
//                            sSLConfig.setTruststore(para.getValeur());
//                            break;
//                        default:
//                            sSLConfig.setKeystorePassword(para.getValeur());
//                            break;
//                    }
//                }
//            }

            pattern = new String[]{"AUTORISATION", "TARIFICATION"};
            paraList = parametresFacade.findParametresByPatternFromBeginning(pattern);
            if (paraList != null && !paraList.isEmpty()) {
                for (Parametres para : paraList) {
                    switch (para.getCodeparam()) {
                        case "AUTORISATION_BALANCE_WIZALL_SN":
                            autorisationBalance = para;
                            balanceAllowed = para.getValeur().equals("OPERATION_AUTORISEE");
                            break;
                        case "AUTORISATION_MINISTATEMENT_WIZALL_SN":
                            autorisationStatement = para;
                            statementAllowed = para.getValeur().equals("OPERATION_AUTORISEE");
                            break;
                        case "AUTORISATION_B2W_WIZALL_SN":
                            autorisationB2W = para;
                            b2wAllowed = para.getValeur().equals("OPERATION_AUTORISEE");
                            break;
                        case "AUTORISATION_W2B_WIZALL_SN":
                            autorisationW2B = para;
                            w2bAllowed = para.getValeur().equals("OPERATION_AUTORISEE");
                            break;
                        case "TARIFICATION_B2W_WIZALL_SN":
                            autorisationTarifB2W = para;
                            b2wTarifAllowed = para.getValeur().equals("TARIFICATION_AUTORISEE");
                            break;
                        case "TARIFICATION_W2B_WIZALL_SN":
                            autorisationTarifW2B = para;
                            w2bTarifAllowed = para.getValeur().equals("TARIFICATION_AUTORISEE");
                            break;
                        case "TARIFICATION_MINISTATEMENT_WIZALL_SN":
                            autorisationTarifStatement = para;
                            statementTarifAllowed = para.getValeur().equals("TARIFICATION_AUTORISEE");
                            break;
                        case "TARIFICATION_ACCOUNTBALANCE_WIZALL_SN":
                            autorisationTarifBalance = para;
                            balanceTarifAllowed = para.getValeur().equals("TARIFICATION_AUTORISEE");
                            break;
                        case "TARIFICATION_REGISTER_WIZALL_SN":
                            autorisationTarifRegister = para;
                            registerTarifAllowed = para.getValeur().equals("TARIFICATION_AUTORISEE");
                            break;
                        case "TARIFICATION_UNREGISTER_WIZALL_SN":
                            autorisationTarifUnregister = para;
                            unregisterTarifAllowed = para.getValeur().equals("TARIFICATION_AUTORISEE");
                            break;
                        case "TARIFICATION_WIZALL_SN":
                            modeTarificationParam = para;
                            modeTarif = para.getValeur();
                            break;
                        default:
                            break;
                    }
                }
            }

//            sSLConfig.setKeystoreType("JKS");
//            sSLConfig.setTruststoreType("JKS");
//            sSLConfig.initialize();

//            List<Parametres> listLogo = parametresFacade.findByCodeParam("LOGO_URL");
//            if (!listLogo.isEmpty()) {
//                urlLogo = listLogo.get(0).getValeur();
//            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

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

    public List<Parametres> getListValeursIdle() {
        return listValeursIdle;
    }

    public void setListValeursIdle(List<Parametres> listValeursIdle) {
        this.listValeursIdle = listValeursIdle;
    }

    public List<Pagesouscription> getListPagesouscription() {
        return listPagesouscription;
    }

    public void setListPagesouscription(List<Pagesouscription> listPagesouscription) {
        this.listPagesouscription = listPagesouscription;
    }

    public ParametrageManager getParametrageManager() {
        return parametrageManager;
    }

    public void setParametrageManager(ParametrageManager parametrageManager) {
        this.parametrageManager = parametrageManager;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public boolean isIdle() {
        return idle;
    }

    public void setIdle(boolean idle) {
        this.idle = idle;
    }

    public boolean isSupvalidation() {
        return supvalidation;
    }

    public void setSupvalidation(boolean supvalidation) {
        this.supvalidation = supvalidation;
    }

    public SSLClientAxisEngineConfig getsSLConfig() {
        return sSLConfig;
    }

    public void setsSLConfig(SSLClientAxisEngineConfig sSLConfig) {
        this.sSLConfig = sSLConfig;
    }

    public Converter getModeSouscriptionConverter() {
        return modeSouscriptionConverter;
    }

    public void setModeSouscriptionConverter(Converter modeSouscriptionConverter) {
        this.modeSouscriptionConverter = modeSouscriptionConverter;
    }

    public Parametres getSelectedModeSouscription() {
        return selectedModeSouscription;
    }

    public void setSelectedModeSouscription(Parametres selectedModeSouscription) {
        this.selectedModeSouscription = selectedModeSouscription;
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

    public Parametres getModeIdle() {
        return modeIdle;
    }

    public void setModeIdle(Parametres modeIdle) {
        this.modeIdle = modeIdle;
    }

    public Pagesouscription getSelectedPageSouscription() {
        return selectedPageSouscription;
    }

    public void setSelectedPageSouscription(Pagesouscription selectedPageSouscription) {
        this.selectedPageSouscription = selectedPageSouscription;
    }

    public Parametres getSupervalidation() {
        return supervalidation;
    }

    public void setSupervalidation(Parametres supervalidation) {
        this.supervalidation = supervalidation;
    }

    public String getUrlLogo() {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }

    public boolean isBalanceAllowed() {
        return balanceAllowed;
    }

    public void setBalanceAllowed(boolean balanceAllowed) {
        this.balanceAllowed = balanceAllowed;
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

    public void enregistrerModeSouscription() {
        // System.out.println("MODE: " + selectedModeSouscription.getCodeparam());
        parametresFacade.edit(selectedModeSouscription);
        reglerPageSouscription();

    }

//    public void modifierDisponibilite() { FONCTIONNALITE PROPRE A ORANGE
//        boolean ok = sendIdleRequest();
//        System.out.println(" MODIFICATION DE LA DISPONIBILITE  IDLE: " + ok);
//        if (!ok) {
//            idle = !idle;
//            addMessage("DISPONIBILITE", "Le serveur ne peut changer d'état en ce moment");
//        } else {
//            addMessage("DISPONIBILITE", "Le serveur a changé d'état avec succès");
//
//        }
//
//        if (idle) {
//            for (Parametres val : listValeursIdle) {
//                if (val.getCodeparam().equals("IDLE_NON")) {
//                    modeIdle.setValeur(val.getCodeparam());
//                    break;
//                }
//            }
//        } else {
//            for (Parametres val : listValeursIdle) {
//                if (val.getCodeparam().equals("IDLE_OUI")) {
//                    modeIdle.setValeur(val.getCodeparam());
//                    break;
//                }
//            }
//        }
//        reglerPageSouscription();
//        parametresFacade.edit(modeIdle);
//        // List<Object> l=new ArrayList();
//        //System.out.println(valeursparametresFacade.test_procedure());
//    }
    public void modifierSupervalidation() {
        if (supvalidation) {
            supervalidation.setValeur("SUPERVALIDATION_OUI");
            addMessage("SUPERVALIDATION", "La supervalidation est activée");
            System.out.println("ACTIVATION DE LA SUPERVALIDATION");
        } else {
            supervalidation.setValeur("SUPERVALIDATION_NON");
            addMessage("SUPERVALIDATION", "La supervalidation est désactivée");
            System.out.println("DESACTIVATION DE LA SUPERVALIDATION");
        }
        parametresFacade.edit(supervalidation);
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
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

//    public boolean sendIdleRequest() { //FONCTIONNATLITE PROPRE A ORANGE
//        IdleServiceLocator locator = new IdleServiceLocator(sSLConfig);
//        List<Parametres> url = parametresFacade.findByCodeParam("IDLE_URL");
//        try {
//            String indispo = idle ? "false" : "true";
//            IdlePort_PortType rgpt = locator.getIdlePort(new URL(url.get(0).getValeur()));
//            return rgpt.setIdle(indispo);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return false;
//        }
//    }
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
            System.out.println("ACTIVATION  DE GET BALANCE");
        } else {
            autorisationBalance.setValeur("OPERATION_NON_AUTORISEE");
            parametresFacade.edit(autorisationBalance);
            System.out.println("DESACTIVATION  DE GET BALANCE");

        }

    }

    public void modifierAutorisationStatement() {
        if (statementAllowed) {
            autorisationStatement.setValeur("OPERATION_AUTORISEE");
            parametresFacade.edit(autorisationStatement);
            System.out.println("ACTIVATION  DE MINI STATEMENT");

        } else {
            autorisationStatement.setValeur("OPERATION_NON_AUTORISEE");
            parametresFacade.edit(autorisationStatement);
            System.out.println("DESACTIVATION  DE MINI STATEMENT");

        }

    }

    public void modifierAutorisationB2W() {
        if (b2wAllowed) {
            autorisationB2W.setValeur("OPERATION_AUTORISEE");
            parametresFacade.edit(autorisationB2W);
            System.out.println("ACTIVATION  DE BANK TO WALLET");

        } else {
            autorisationB2W.setValeur("OPERATION_NON_AUTORISEE");
            parametresFacade.edit(autorisationB2W);
            System.out.println("DESACTIVATION  DE BANK TO WALLET");

        }

    }

    public void modifierAutorisationW2B() {
        if (w2bAllowed) {
            autorisationW2B.setValeur("OPERATION_AUTORISEE");
            parametresFacade.edit(autorisationW2B);
            System.out.println("ACTIVATION  DE  WALLET TO BANK");

        } else {
            autorisationW2B.setValeur("OPERATION_NON_AUTORISEE");
            parametresFacade.edit(autorisationW2B);
            System.out.println("DESACTIVATION  DE  WALLET TO BANK");

        }

    }

    public void modifierAutorisationTarifBalance() {
        if (balanceTarifAllowed) {
            autorisationTarifBalance.setValeur("TARIFICATION_AUTORISEE");
            parametresFacade.edit(autorisationTarifBalance);
            System.out.println("ACTIVATION  DE  TARIFICATION SUR GET BALANCE");

        } else {
            autorisationTarifBalance.setValeur("TARIFICATION_NON_AUTORISEE");
            parametresFacade.edit(autorisationTarifBalance);
            System.out.println("DESACTIVATION  DE  TARIFICATION SUR GET BALANCE");

        }

    }

    public void modifierAutorisationTarifStatement() {
        if (statementTarifAllowed) {
            autorisationTarifStatement.setValeur("TARIFICATION_AUTORISEE");
            parametresFacade.edit(autorisationTarifStatement);
            System.out.println("ACTIVATION  DE  TARIFICATION SUR MINI STATEMENT");

        } else {
            autorisationTarifStatement.setValeur("TARIFICATION_NON_AUTORISEE");
            parametresFacade.edit(autorisationTarifStatement);
            System.out.println("DESACTIVATION  DE  TARIFICATION SUR MINI STATEMENT");

        }

    }

    public void modifierAutorisationTarifB2W() {
        if (b2wTarifAllowed) {
            autorisationTarifB2W.setValeur("TARIFICATION_AUTORISEE");
            parametresFacade.edit(autorisationTarifB2W);
            System.out.println("ACTIVATION  DE  TARIFICATION SUR BANK TO WALLET");

        } else {
            autorisationTarifB2W.setValeur("TARIFICATION_NON_AUTORISEE");
            parametresFacade.edit(autorisationTarifB2W);
            System.out.println("DESACTIVATION  DE  TARIFICATION SUR BANK TO WALLET");

        }

    }

    public void modifierAutorisationTarifW2B() {
        if (w2bTarifAllowed) {
            autorisationTarifW2B.setValeur("TARIFICATION_AUTORISEE");
            parametresFacade.edit(autorisationTarifW2B);
            System.out.println("ACTIVATION  DE  TARIFICATION SUR WALLET TO BANK");

        } else {
            autorisationTarifW2B.setValeur("TARIFICATION_NON_AUTORISEE");
            parametresFacade.edit(autorisationTarifW2B);
            System.out.println("ACTIVATION  DE  TARIFICATION SUR WALLET TO BANK");

        }

    }

    public void modifierAutorisationTarifRegister() {
        if (registerTarifAllowed) {
            autorisationTarifRegister.setValeur("TARIFICATION_AUTORISEE");
            parametresFacade.edit(autorisationTarifRegister);
            System.out.println("DESACTIVATION  DE  TARIFICATION SUR ABONNEMENT WIZALL");

        } else {
            autorisationTarifRegister.setValeur("TARIFICATION_NON_AUTORISEE");
            parametresFacade.edit(autorisationTarifRegister);
            System.out.println("DESACTIVATION  DE  TARIFICATION SUR ABONNEMENT WIZALL");

        }

    }

    public void modifierAutorisationTarifUnregister() {
        if (unregisterTarifAllowed) {
            autorisationTarifUnregister.setValeur("TARIFICATION_AUTORISEE");
            parametresFacade.edit(autorisationTarifUnregister);
            System.out.println("ACTIVATION  DE  TARIFICATION SUR DESABONNEMENT WIZALL");

        } else {
            autorisationTarifUnregister.setValeur("TARIFICATION_NON_AUTORISEE");
            parametresFacade.edit(autorisationTarifUnregister);
            System.out.println("DESACTIVATION  DE  TARIFICATION SUR DESABONNEMENT WIZALL");

        }

    }

    public void modifierModeTarification() {

        modeTarificationParam.setValeur(modeTarif);
        parametresFacade.edit(modeTarificationParam);
    }

}
