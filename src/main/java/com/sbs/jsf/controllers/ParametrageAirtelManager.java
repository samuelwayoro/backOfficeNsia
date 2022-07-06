/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.jsf.controllers;

import at.telekom.util.axis.SSLClientAxisEngineConfig;
import com.sbs.easymbank.dao.OperateursFacade;
import com.sbs.easymbank.dao.PagesouscriptionFacade;
import com.sbs.easymbank.dao.ParametresFacade;
import com.sbs.easymbank.dao.ValeursparametresFacade;
import com.sbs.easymbank.entities.Operateurs;
import com.sbs.easymbank.entities.Pagesouscription;

import com.sbs.easymbank.entities.Parametres;
import com.sbs.exceptions.*;

import java.io.Serializable;
import java.net.URL;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author SOCITECH-
 */
@ManagedBean(name = "parametrageAirtelManager")
@ApplicationScoped
public class ParametrageAirtelManager implements Serializable {

    private List<Parametres> listPara;
    private List<Parametres> listValeursSouscription;
    private List<Parametres> listValeursIdle;
    private List<Pagesouscription> listPagesouscription;
    @ManagedProperty(value = "#{parametrageManager}")
    private ParametrageManager parametrageManager;

    private String libelle;
    private boolean idle;
    private boolean supvalidation;
    //   private SSLClientAxisEngineConfig sSLConfig = new SSLClientAxisEngineConfig();
    private Converter modeSouscriptionConverter = new Converter() {

        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {

            Parametres laValeur = new Parametres();
            for (Parametres val : listValeursSouscription) {
                if (val.getCodeparam().equals(value)) {
                    // System.out.println("LA VALEUR DU PARAMETRE: " + value);
                    //System.out.println("LA VALEUR DE L'OBJET: " + val.getCodeparam());
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

    public ParametrageAirtelManager() {
    }

    @PostConstruct
    public void init() {
        try {
            //listPara=parametresFacade.findAll();
            // valParamSouscription=new Valeursparametres();
            // String pattern[]={"AUTORISATION"};
            listValeursSouscription = parametresFacade.findListValeursForParam("MODESOUSCRIPTION_VALUE");
            listValeursIdle = parametresFacade.findListValeursForParam("MODEIDLE_VALUE");
            // System.out.println("LISTE VALEUR: " + listValeursSouscription.size());
            //Paramètre définissant l'état du mode idle
//            List<Parametres> l = parametresFacade.findByCodeParam("MODEIDLE");
//            if (l != null && !l.isEmpty()) {
//                modeIdle = l.get(0);
//                l.clear();
//            } else {
//                throw new ParameterNotFoundException("MODEIDLE");
//            }
            //list des pages pour les souscriptions
            listPagesouscription = pagesouscriptionFacade.findByOperateur("AIRTEL");
            //Paramètre définissant l'activation ou non de la supervalidation
            List<Parametres> l = parametresFacade.findByCodeParam("SUPERVALIDATION");
//            if (l != null && !l.isEmpty()) {
//                supervalidation = l.get(0);
//                l.clear();
//            } else {
//                throw new ParameterNotFoundException("SUPERVALIDATION");
//            }
            supervalidation = parametrageManager.getSupervalidation();
            //Paramètre définissant le mode de souscription
//            l = parametresFacade.findByCodeParam("MODESOUSCRIPTION");
//            if (l != null && !l.isEmpty()) {
//                selectedModeSouscription = l.get(0);
//                l.clear();
//            } else {
//                throw new ParameterNotFoundException("MODESOUSCRIPTION");
//            }
            //Paramètre définissant le nombre max de transactions
            l = parametresFacade.findByCodeParam("NOMBRE_MAX_TRANSACTION");
            if (l != null && !l.isEmpty()) {
                nombreMaxTrancations = l.get(0);
                l.clear();
            } else {
                throw new ParameterNotFoundException("NOMBRE_MAX_TRANSACTION");
            }

            //Paramètre définissant le montant max de transactions
            l = parametresFacade.findByCodeParam("MONTANT_MAX_TRANSACTION");
            if (l != null && !l.isEmpty()) {
                montantMaxTrancations = l.get(0);
                l.clear();
            } else {
                throw new ParameterNotFoundException("MONTANT_MAX_TRANSACTION");
            }

            //   reglerPageSouscription();
            if (selectedModeSouscription == null) {
                selectedModeSouscription = new Parametres();
                selectedPageSouscription = listPagesouscription.get(0);
            }
            //           System.out.println("IDLE: " + modeIdle.getCodeparam());
            //           idle = !modeIdle.getValeur().equals("IDLE_OUI");
            supvalidation = supervalidation.getValeur().equals("SUPERVALIDATION_OUI");

//            String[] pattern = new String[]{"KEYSTORE", "TRUSTSTORE", "AUTORISATION"};
//            List<Parametres> paraList = parametresFacade.findParametresByPatten(pattern);
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
            String[] pattern = new String[]{"AUTORISATION", "TARIFICATION"};
            List<Parametres> paraList = parametresFacade.findParametresByPatternFromBeginning(pattern);
            if (paraList != null && !paraList.isEmpty()) {
                for (Parametres para : paraList) {
                    switch (para.getCodeparam()) {

                        case "AUTORISATION_BALANCE_AIRTEL":
                            autorisationBalance = para;
                            balanceAllowed = para.getValeur().equals("OPERATION_AUTORISEE");
                            break;
                        case "AUTORISATION_MINISTATEMENT_AIRTEL":
                            autorisationStatement = para;
                            statementAllowed = para.getValeur().equals("OPERATION_AUTORISEE");
                            break;
                        case "AUTORISATION_B2W_AIRTEL":
                            autorisationB2W = para;
                            b2wAllowed = para.getValeur().equals("OPERATION_AUTORISEE");
                            break;
                        case "AUTORISATION_W2B_AIRTEL":
                            autorisationW2B = para;
                            w2bAllowed = para.getValeur().equals("OPERATION_AUTORISEE");
                            break;
                        case "TARIFICATION_B2W_AIRTEL":
                            autorisationTarifB2W = para;
                            b2wTarifAllowed = para.getValeur().equals("TARIFICATION_AUTORISEE");
                            break;
                        case "TARIFICATION_W2B_AIRTEL":
                            autorisationTarifW2B = para;
                            w2bTarifAllowed = para.getValeur().equals("TARIFICATION_AUTORISEE");
                            break;
                        case "TARIFICATION_MINISTATEMENT_AIRTEL":
                            autorisationTarifStatement = para;
                            statementTarifAllowed = para.getValeur().equals("TARIFICATION_AUTORISEE");
                            break;
                        case "TARIFICATION_ACCOUNTBALANCE_AIRTEL":
                            autorisationTarifBalance = para;
                            balanceTarifAllowed = para.getValeur().equals("TARIFICATION_AUTORISEE");
                            break;
                        case "TARIFICATION_REGISTER_AIRTEL":
                            autorisationTarifRegister = para;
                            registerTarifAllowed = para.getValeur().equals("TARIFICATION_AUTORISEE");
                            break;
                        case "TARIFICATION_UNREGISTER_AIRTEL":
                            autorisationTarifUnregister = para;
                            unregisterTarifAllowed = para.getValeur().equals("TARIFICATION_AUTORISEE");
                            break;
                        case "TARIFICATION_AIRTEL":
                            modeTarificationParam = para;
                            modeTarif = para.getValeur();
                            break;
                        default:
                            break;
                    }
                }
            }

            //     sSLConfig.setKeystoreType("JKS");
            //   sSLConfig.setTruststoreType("JKS");
            //   sSLConfig.initialize();
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

    public ParametrageManager getParametrageManager() {
        return parametrageManager;
    }

    public void setParametrageManager(ParametrageManager parametrageManager) {
        this.parametrageManager = parametrageManager;
    }

    public void enregistrerModeSouscription() {
        // System.out.println("MODE: " + selectedModeSouscription.getCodeparam());
        parametresFacade.edit(selectedModeSouscription);
        reglerPageSouscription();

    }

    public void modifierDisponibilite() {

        boolean ok = sendIdleRequest();
        // System.out.println("IDLE: " + ok);
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
//        parametrageManager.setSupervalidation(supervalidation);
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

    public boolean sendIdleRequest() {
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
        return true;
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

}
