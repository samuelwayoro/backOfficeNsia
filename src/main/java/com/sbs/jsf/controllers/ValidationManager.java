/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.jsf.controllers;

import at.telekom.util.axis.SSLClientAxisEngineConfig;
import com.ph.tlc.mobilebank.StandardResp;
import com.sbs.easymbank.dao.AbonnementTempFacade;
import com.sbs.easymbank.dao.AbonnementsFacade;
import com.sbs.easymbank.dao.AbonnementsReconciliationsFacade;
import com.sbs.easymbank.dao.CodesFacade;
import com.sbs.easymbank.dao.ParametresFacade;
import com.sbs.easymbank.dao.TarifsProfilsOperateursFacade;
import com.sbs.easymbank.entities.AbonnementTemp;
import com.sbs.easymbank.entities.Abonnements;
import com.sbs.easymbank.entities.AbonnementsReconciliations;
import com.sbs.easymbank.entities.Codes;
import com.sbs.easymbank.entities.Operateurs;
import com.sbs.easymbank.entities.Parametres;
import com.sbs.easymbank.entities.TarifsProfilsOperateurs;
import com.sbs.easymbank.service.omapi.RegisterPort_PortType;
import com.sbs.easymbank.service.omapi.RegisterServiceLocator;
import com.easymbank.service.other.OtherWebService;
import com.easymbank.service.other.OtherWebServiceService;
import com.easymbank.service.other.PaymentRequest;
import com.easymbank.service.other.PaymentResponse;
import com.sbs.easymbank.dao.AgencesFacade;
import com.sbs.easymbank.soap.SoapWrapper;
import com.sbs.easymbank.utility.MoovTokenGenerator;
import com.sbs.exceptions.FeesNotDefinedException;
import com.sbs.exceptions.ParameterNotFoundException;
import com.sbs.exceptions.PaymentException;
import com.stdbankinit.server.MCOB2ASoap;
import com.stdbankinit.server.McoAtlanticBankServerSoapImplService;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.holders.StringHolder;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.primefaces.model.StreamedContent;
import org.tempuri.RetBankAccountRegistration;
import com.sbs.easymbank.service.mmapi.*;
import com.sbs.easymbank.soap.SoapWrapperBN;
import com.sbs.easymbank.utility.ClientHttp;
import com.sbs.easymbank.utility.ClientHttpWizall;
import javax.xml.ws.BindingProvider;
import mmwservice.LinkRequest;
import mmwservice.LinkResponse;
import mmwservice.MMWService;
import mmwservice.MMWServiceService;

/**
 *
 * @author SOCITECH-
 */
@ManagedBean
@ViewScoped
public class ValidationManager implements Serializable {

    @EJB
    private CodesFacade codesFacade;
    @EJB
    private AbonnementsFacade abonnementsFacade;
    @EJB
    private AbonnementsReconciliationsFacade abonnementsReconciliationsFacade;
    @EJB
    private AbonnementTempFacade abonnementTempFacade;
    private AbonnementTemp abonnementTemp;
    private List<Abonnements> listAbonnements;
    private List<Abonnements> listAbonnementsFiltered;
    private Abonnements selectedAbonnement;
    @ManagedProperty(value = "#{loginManager}")
    private LoginManager loginManager;
    @ManagedProperty(value = "#{homeManager}")
    private HomeManager homeManager;
    @ManagedProperty(value = "#{sessionManager}")
    private SessionManager sessionManager;
    @ManagedProperty(value = "#{parametrageManager}")
    private ParametrageManager parametrageManager;
    @ManagedProperty(value = "#{parametrageMoovManager}")
    private ParametrageMoovManager parametrageMoovManager;
    @ManagedProperty(value = "#{parametrageMtnManager}")
    private ParametrageMtnManager parametrageMtnManager;
    private SSLClientAxisEngineConfig SSLConfig = new SSLClientAxisEngineConfig();
    String currencyLabel;
    String currencyCode;
    private String key;
    private String user_moov;
    private String pass_moov;
    private String bankName;
    private String branchCode;
    private String countryCode;
    String indicatifPays;
    private SoapWrapper soapWrapper = SoapWrapper.getInstance();
    //client http parametre
    //private ClientHttp client;
    //client http wizall CI -- ancienne version 
    private ClientHttpWizall client;
    @EJB
    private ParametresFacade parametresFacade;
    @EJB
    private TarifsProfilsOperateursFacade tarifsProfilsOperateursFacade;
    @EJB
    private AgencesFacade agencesFacade;
    private Calendar calendar;
    private List<String> listCompte;
    private List<String> listPhone;
    private StreamedContent file;
    private InputStream stream;
    private JasperPrint jasperPrint;
    List<Parametres> paramWizallApi;
    private Abonnements newAbonnement;

    @PostConstruct
    public void init() {
        client = new ClientHttpWizall();
        try {

            //recup de la liste des parametres de connexion a l'api wizall 
            paramWizallApi = parametresFacade.findListValeursForParam("ACCESS_API_WIZALL");
            //setting du clientHttp 
            System.out.println("AFFICHAGE DES PARAMETRES POUR LAPI WIZALL ");
            for (Parametres p : paramWizallApi) {
                System.out.println("-->" + p.getCodeparam().toLowerCase() + " " + p.getValeur());
            }

            //CODE VALIDATION A ACTIVATION POUR NOUVELLE VERSION DE WIZALL 
//            for (Parametres p : paramWizallApi) {
//                if (p.getCodeparam().equalsIgnoreCase("USERNAME")) {
//                    client.setUsername(p.getValeur());
//                } else if (p.getCodeparam().equalsIgnoreCase("PASSWORD")) {
//                    client.setPassword(p.getValeur());
//                } else if (p.getCodeparam().equalsIgnoreCase("CLIENT_ID")) {
//                    client.setClient_id(p.getValeur());
//                } else if (p.getCodeparam().equalsIgnoreCase("CLIENT_SECRET")) {
//                    client.setClient_secret(p.getValeur());
//                } else if (p.getCodeparam().equalsIgnoreCase("CLIENT_TYPE")) {
//                    client.setClient_type(p.getValeur());
//                } else if (p.getCodeparam().equalsIgnoreCase("GRANT_TYPE")) {
//                    client.setGrant_type(p.getValeur());
//                } else if (p.getCodeparam().equalsIgnoreCase("URL_TOKEN")) {
//                    client.setUrl_token(p.getValeur());
//                } else if (p.getCodeparam().equalsIgnoreCase("COUNTRY")) {
//                    client.setCountry(p.getValeur());
//                }
//            }
            if (loginManager.getUtilisateur().getIdprofils().isValide_ttes_age()) {
                listAbonnements = abonnementsFacade.findAbonnementToValidate();
            } else {
                listAbonnements = abonnementsFacade.findAbonnementToValidate(loginManager.getUtilisateur().getIdagences().getCodeagence());

            }
            for (Abonnements abonnements : listAbonnements) {
                abonnements.setLibelleAgence(agencesFacade.findLibelleByCode(abonnements.getAgence()));
            }

            String[] patternSSLMoov = new String[]{"TRUSTSTORE_MOOV", "REGISTER_URL_MOOV"};
            List<Parametres> paraList = parametresFacade.findParametresByPatten(patternSSLMoov);
            if (paraList != null && !paraList.isEmpty()) {
                for (Parametres para : paraList) {
                    if (para.getCodeparam().equals("CHEMIN_FICHIER_TRUSTSTORE_MOOV")) {
                        soapWrapper.CertificatePath = para.getValeur();
                    } else if (para.getCodeparam().equals("PASSWORD_FICHIER_TRUSTSTORE_MOOV")) {
                        soapWrapper.CertificatePassword = para.getValeur();
                    } else if (para.getCodeparam().equals("REGISTER_URL_MOOV")) {
                        soapWrapper.requestURL = para.getValeur();
                    }
                }
            }

            String[] patternSSL = new String[]{"KEYSTORE", "TRUSTSTORE"};
            String[] patternDevise = new String[]{"DEVISE"};
            paraList = parametresFacade.findParametresByPatten(patternSSL);
            if (paraList != null && !paraList.isEmpty()) {
                for (Parametres para : paraList) {
                    if (para.getCodeparam().equals("CHEMIN_FICHIER_KEYSTORE")) {
                        SSLConfig.setKeystore(para.getValeur());
                    } else if (para.getCodeparam().equals("CHEMIN_FICHIER_TRUSTSTORE")) {
                        SSLConfig.setTruststore(para.getValeur());
                    } else {
                        SSLConfig.setKeystorePassword(para.getValeur());
                    }
                }
            }
            paraList = parametresFacade.findParametresByPatten(patternDevise);
            if (paraList != null && !paraList.isEmpty()) {
                for (Parametres para : paraList) {
                    if (para.getCodeparam().equals("CODE_DEVISE")) {
                        currencyCode = para.getValeur();
                    } else {
                        currencyLabel = para.getValeur();
                    }
                }
            }
            // exceptionWhenContactingOM = false;

            SSLConfig.setKeystoreType("JKS");

            SSLConfig.setTruststoreType("JKS");
            SSLConfig.initialize();
            String[] patternBank = new String[]{"BANKNAME", "BRANCHCODE", "COUNTRYCODE"};
            paraList = parametresFacade.findParametresByPatten(patternBank);
            if (paraList != null && !paraList.isEmpty()) {
                for (Parametres para : paraList) {
                    switch (para.getCodeparam()) {
                        case "BANKNAME":
                            bankName = para.getValeur();
                            break;
                        case "BRANCHCODE":
                            branchCode = para.getValeur();
                            break;
                        default:
                            countryCode = para.getValeur();
                            break;
                    }
                }
            }

            List<Parametres> para_indicatif = parametresFacade.findByCodeParam("INDICATIF_PAYS");
            if (para_indicatif != null && !para_indicatif.isEmpty()) {
                indicatifPays = para_indicatif.get(0).getValeur();
            }

            List<Parametres> p = parametresFacade.findByCodeParam("CLE_MOOV");
            //String key;
            if (!p.isEmpty()) {
                key = p.get(0).getValeur();
            } else {
                throw new ParameterNotFoundException("CLE_MOOV");
            }
            p = parametresFacade.findByCodeParam("USERNAME_MOOV");
            // String user;
            if (!p.isEmpty()) {
                user_moov = p.get(0).getValeur();
            } else {
                throw new ParameterNotFoundException("USERNAME_MOOV");
            }

            p = parametresFacade.findByCodeParam("TOKEN_MOOV");
            // String pass;
            if (!p.isEmpty()) {
                pass_moov = p.get(0).getValeur();
            } else {
                throw new ParameterNotFoundException("TOKEN_MOOV");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public CodesFacade getCodesFacade() {
        return codesFacade;
    }

    public void setCodesFacade(CodesFacade codesFacade) {
        this.codesFacade = codesFacade;
    }

    public AbonnementsFacade getAbonnementsFacade() {
        return abonnementsFacade;
    }

    public void setAbonnementsFacade(AbonnementsFacade abonnementsFacade) {
        this.abonnementsFacade = abonnementsFacade;
    }

    public AbonnementsReconciliationsFacade getAbonnementsReconciliationsFacade() {
        return abonnementsReconciliationsFacade;
    }

    public void setAbonnementsReconciliationsFacade(AbonnementsReconciliationsFacade abonnementsReconciliationsFacade) {
        this.abonnementsReconciliationsFacade = abonnementsReconciliationsFacade;
    }

    public AbonnementTempFacade getAbonnementTempFacade() {
        return abonnementTempFacade;
    }

    public void setAbonnementTempFacade(AbonnementTempFacade abonnementTempFacade) {
        this.abonnementTempFacade = abonnementTempFacade;
    }

    public SSLClientAxisEngineConfig getSSLConfig() {
        return SSLConfig;
    }

    public void setSSLConfig(SSLClientAxisEngineConfig SSLConfig) {
        this.SSLConfig = SSLConfig;
    }

    public String getCurrencyLabel() {
        return currencyLabel;
    }

    public void setCurrencyLabel(String currencyLabel) {
        this.currencyLabel = currencyLabel;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUser_moov() {
        return user_moov;
    }

    public void setUser_moov(String user_moov) {
        this.user_moov = user_moov;
    }

    public String getPass_moov() {
        return pass_moov;
    }

    public void setPass_moov(String pass_moov) {
        this.pass_moov = pass_moov;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getIndicatifPays() {
        return indicatifPays;
    }

    public void setIndicatifPays(String indicatifPays) {
        this.indicatifPays = indicatifPays;
    }

    public SoapWrapper getSoapWrapper() {
        return soapWrapper;
    }

    public void setSoapWrapper(SoapWrapper soapWrapper) {
        this.soapWrapper = soapWrapper;
    }

    public ParametresFacade getParametresFacade() {
        return parametresFacade;
    }

    public void setParametresFacade(ParametresFacade parametresFacade) {
        this.parametresFacade = parametresFacade;
    }

    public TarifsProfilsOperateursFacade getTarifsProfilsOperateursFacade() {
        return tarifsProfilsOperateursFacade;
    }

    public void setTarifsProfilsOperateursFacade(TarifsProfilsOperateursFacade tarifsProfilsOperateursFacade) {
        this.tarifsProfilsOperateursFacade = tarifsProfilsOperateursFacade;
    }

    public AgencesFacade getAgencesFacade() {
        return agencesFacade;
    }

    public void setAgencesFacade(AgencesFacade agencesFacade) {
        this.agencesFacade = agencesFacade;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public InputStream getStream() {
        return stream;
    }

    public void setStream(InputStream stream) {
        this.stream = stream;
    }

    public JasperPrint getJasperPrint() {
        return jasperPrint;
    }

    public void setJasperPrint(JasperPrint jasperPrint) {
        this.jasperPrint = jasperPrint;
    }

    public Abonnements getNewAbonnement() {
        return newAbonnement;
    }

    public void setNewAbonnement(Abonnements newAbonnement) {
        this.newAbonnement = newAbonnement;
    }

    public List<Parametres> getParamWizallApi() {
        return paramWizallApi;
    }

    public void setParamWizallApi(List<Parametres> paramWizallApi) {
        this.paramWizallApi = paramWizallApi;
    }

    public List<Abonnements> getListAbonnements() {
        return listAbonnements;
    }

    public void setListAbonnements(List<Abonnements> listAbonnements) {
        this.listAbonnements = listAbonnements;
    }

    public Abonnements getSelectedAbonnement() {
        return selectedAbonnement;
    }

    public void setSelectedAbonnement(Abonnements selectedAbonnement) {
        this.selectedAbonnement = selectedAbonnement;
    }

    public List<Abonnements> getListAbonnementsFiltered() {
        return listAbonnementsFiltered;
    }

    public void setListAbonnementsFiltered(List<Abonnements> listAbonnementsFiltered) {
        this.listAbonnementsFiltered = listAbonnementsFiltered;
    }

    public LoginManager getLoginManager() {
        return loginManager;
    }

    public void setLoginManager(LoginManager loginManager) {
        this.loginManager = loginManager;
    }

    public HomeManager getHomeManager() {
        return homeManager;
    }

    public void setHomeManager(HomeManager homeManager) {
        this.homeManager = homeManager;
    }

    public AbonnementTemp getAbonnementTemp() {
        return abonnementTemp;
    }

    public void setAbonnementTemp(AbonnementTemp abonnementTemp) {
        this.abonnementTemp = abonnementTemp;
    }

    public List<String> getListCompte() {
        return listCompte;
    }

    public void setListCompte(List<String> listCompte) {
        this.listCompte = listCompte;
    }

    public List<String> getListPhone() {
        return listPhone;
    }

    public void setListPhone(List<String> listPhone) {
        this.listPhone = listPhone;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public ParametrageManager getParametrageManager() {
        return parametrageManager;
    }

    public void setParametrageManager(ParametrageManager parametrageManager) {
        this.parametrageManager = parametrageManager;
    }

    public ParametrageMoovManager getParametrageMoovManager() {
        return parametrageMoovManager;
    }

    public void setParametrageMoovManager(ParametrageMoovManager parametrageMoovManager) {
        this.parametrageMoovManager = parametrageMoovManager;
    }

    public ParametrageMtnManager getParametrageMtnManager() {
        return parametrageMtnManager;
    }

    public void setParametrageMtnManager(ParametrageMtnManager parametrageMtnManager) {
        this.parametrageMtnManager = parametrageMtnManager;
    }

    public ClientHttpWizall getClient() {
        return client;
    }

    public void setClient(ClientHttpWizall client) {
        this.client = client;
    }

    public void supprimerAbonnement() {
        try {
            selectedAbonnement.setUserrejet(loginManager.getUtilisateur().getLogin());
            listAbonnements.remove(selectedAbonnement);
            abonnementsFacade.edit(selectedAbonnement);
            sessionManager.getLogs().setAction("REJET D'ABONNEMENT");
            sessionManager.getLogs().setDateLog(new Date());
            sessionManager.getLogs().setMessage("ALIAS: " + selectedAbonnement.getAlias());
            sessionManager.logDB();
            System.out.println("MOTIF: " + selectedAbonnement.getMotif());
            homeManager.getListAbonnementsASupprimer().add(selectedAbonnement);
            selectedAbonnement = new Abonnements();
            addMessage("SUCCES", "L'abonnement a été rétiré de la liste de façon définitive");
        } catch (Exception e) {
            e.printStackTrace();
            addErrorMessage("ERREUR", "Une erreur s'est produite lors de la suppression de l'abonnement");
        }

    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public String validerAbonnement() {
        System.out.println("ce client existe deja avec ce compte , ce numero pour cet operateur  "+customerAlreadyExist());
        if (!customerAlreadyExist()) {
            if (selectedAbonnement.getOperateur().getDesignationOperateur().equalsIgnoreCase("ORANGE")) {
                callWebServiceAndRegister();
                if (selectedAbonnement.getCoderetour().equals("200") && selectedAbonnement.getActif()) {
                    try {
                        PDF();

                    } catch (Exception e) {
                        e.printStackTrace();
                        addErrorMessage("ERREUR", "Une erreur s'est produite lors de l'impression du formulaire de souscription");
                    }
                }
                //MOOV BENIN
            } else if (selectedAbonnement.getOperateur().getDesignationOperateur().equalsIgnoreCase("MOOV") && indicatifPays.equals("229")) {
                callWebServiceAndRegisterMoovBN();
                if (selectedAbonnement.getCoderetour().equals("0") && selectedAbonnement.getActif()) {
                    try {
                        PDF();

                    } catch (Exception e) {
                        e.printStackTrace();
                        addErrorMessage("ERREUR", "Une erreur s'est produite lors de l'impression du formulaire de souscription");
                    }
                }
            } else if (selectedAbonnement.getOperateur().getDesignationOperateur().equalsIgnoreCase("MOOV")) {
                callWebServiceAndRegisterMoov();
                if (selectedAbonnement.getCoderetour().equals("0") && selectedAbonnement.getActif()) {
                    try {
                        PDF();

                    } catch (Exception e) {
                        e.printStackTrace();
                        addErrorMessage("ERREUR", "Une erreur s'est produite lors de l'impression du formulaire de souscription");
                    }
                }
            } else if (selectedAbonnement.getOperateur().getDesignationOperateur().equalsIgnoreCase("AIRTEL")) {
                callWebServiceAndRegisterAirtel();
                if (selectedAbonnement.getCoderetour().equals("0") && selectedAbonnement.getActif()) {
                    try {
                        PDF();

                    } catch (Exception e) {
                        e.printStackTrace();
                        addErrorMessage("ERREUR", "Une erreur s'est produite lors de l'impression du formulaire de souscription");
                    }
                }

            } else if (selectedAbonnement.getOperateur().getDesignationOperateur().equalsIgnoreCase("MTN")) {
                callWebServiceAndRegisterMtn();
                if (selectedAbonnement.getCoderetour().equals("200") && selectedAbonnement.getActif()) {
                    try {
                        PDF();

                    } catch (Exception e) {
                        e.printStackTrace();
                        addErrorMessage("ERREUR", "Une erreur s'est produite lors de l'impression du formulaire de souscription");
                    }
                }

            } else if (selectedAbonnement.getOperateur().getDesignationOperateur().equalsIgnoreCase("WIZALL")) {
                System.out.println("supervalidation des abonnees wizall CI ");
                callWebServiceAndRegisterWizall();
            }

            selectedAbonnement = new Abonnements();
        } else {
            addErrorMessage("ERREUR", "Ce compte est deja lié à ce numéro ");
        }
        return "validation.xhtml";
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void addErrorMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void adMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void callWebServiceAndRegister() {
        calendar = Calendar.getInstance();
        StringHolder alias = new StringHolder(selectedAbonnement.getAlias());
        List<Codes> listCode;
        Short codeRetour;
        String libelleCodeRetour = "";

        try {
            if (parametrageManager.isRegisterTarifAllowed()) {
                payForRegistrationService();
            }
            List<Parametres> url = parametresFacade.findByCodeParam("REGISTER_URL");
            RegisterServiceLocator locator = new RegisterServiceLocator(SSLConfig);
            RegisterPort_PortType regpt;
            if (!url.isEmpty()) {
                regpt = locator.getRegisterPort(new URL(url.get(0).getValeur()));
            } else {
                throw new ParameterNotFoundException("REGISTER_URL");
            }
            short codeService = makeCodeService();
            codeRetour = regpt.ombRequest(selectedAbonnement.getNumerotelephone(), alias, codeService, selectedAbonnement.getLabel(), currencyCode, selectedAbonnement.getActivation(), calendar);
            System.out.println("INPUT:{msisdn:" + selectedAbonnement.getNumerotelephone() + ",alias:" + alias.value + ",activationKey:" + selectedAbonnement.getActivation() + ",serviceCode:" + selectedAbonnement.getService() + "} RETOUR OMBREQUEST: " + codeRetour);
            selectedAbonnement.setCoderetour(codeRetour.toString());

            listCode = codesFacade.findDescFromCode(codeRetour.toString());
            if (listCode != null && !listCode.isEmpty()) {
                libelleCodeRetour = listCode.get(0).getCodedescription();
            }

            if (codeRetour == 200) {
                selectedAbonnement.setActif(true);
                selectedAbonnement.setUservalidate(loginManager.getUtilisateur().getLogin());
                selectedAbonnement.setDatevalidation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
                abonnementsFacade.edit(selectedAbonnement);
                listAbonnements.remove(selectedAbonnement);

                //TRACE POUR LA RECONCILIATION
                AbonnementsReconciliations selectedAbonnementRec = new AbonnementsReconciliations(selectedAbonnement);
                selectedAbonnementRec.setMotif("register");
                selectedAbonnementRec.setDatecreation(selectedAbonnement.getDatevalidation());
                selectedAbonnementRec.setCoderetour(selectedAbonnement.getCoderetour());
                abonnementsReconciliationsFacade.create(selectedAbonnementRec);
                sessionManager.getLogs().setAction("VALIDATION D'ABONNEMENT");
                sessionManager.getLogs().setDateLog(new Date());
                sessionManager.getLogs().setMessage("ALIAS: " + selectedAbonnement.getAlias());
                sessionManager.logDB();

                // PDF();
                addMessage("SUCCES", "Abonnement validé avec succès.");

            } else {
                selectedAbonnement.setActif(false);
                selectedAbonnement.setAgence(loginManager.getUtilisateur().getIdagences().getCodeagence());
                abonnementsFacade.edit(selectedAbonnement);

                //TRACE POUR LA RECONCILIATION
                AbonnementsReconciliations selectedAbonnementRec = new AbonnementsReconciliations(selectedAbonnement);
                selectedAbonnementRec.setMotif("register");
                selectedAbonnementRec.setDatecreation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
                selectedAbonnementRec.setCoderetour(selectedAbonnement.getCoderetour());
                abonnementsReconciliationsFacade.create(selectedAbonnementRec);
                sessionManager.getLogs().setAction("TENTATIVE D'ABONNEMENT ECHOUEE");
                sessionManager.getLogs().setDateLog(new Date());
                sessionManager.getLogs().setMessage("ALIAS:" + selectedAbonnement.getAlias() + " MSISDN:" + selectedAbonnement.getNumerotelephone() + " COMPTE:" + selectedAbonnement.getCompte());
                sessionManager.logDB();
                addErrorMessage("ERREUR", "La validation a échoué " + codeRetour + ":" + libelleCodeRetour);
            }
        } catch (ParameterNotFoundException | FeesNotDefinedException | PaymentException ex) {
            ex.printStackTrace();
            //addErrorMessage("ERREUR", "Problème rencontré lors du paiement: ERREUR DE COMPTABILISATION " + ex.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
//                codeRetour = 604;
//                listCode = codesFacade.findDescFromCode(codeRetour.toString());
//                if (listCode != null && !listCode.isEmpty()) {
//                    libelleCodeRetour = listCode.get(0).getCodedescription();
//                }
//                addErrorMessage("ERREUR", "La souscription a échoué " + codeRetour + ":" + libelleCodeRetour);
        }

    }

    public void callWebServiceAndRegisterxOrange() {
        calendar = Calendar.getInstance();
        StringHolder alias = new StringHolder(selectedAbonnement.getAlias());
        List<Codes> listCode;
        Short codeRetour;
        String libelleCodeRetour = "";
        try {
            if (parametrageManager.isRegisterTarifAllowed()) {
                payForRegistrationService();
            }
            List<Parametres> url = parametresFacade.findByCodeParam("REGISTER_URL");
            RegisterServiceLocator locator = new RegisterServiceLocator(SSLConfig);
            RegisterPort_PortType regpt;
            if (!url.isEmpty()) {
                regpt = locator.getRegisterPort(new URL(url.get(0).getValeur()));
            } else {
                regpt = locator.getRegisterPort();
            }
            short codeService = makeCodeService();
            System.out.println("CODE SERVICE:" + codeService);
            codeRetour = regpt.ombRequest(selectedAbonnement.getNumerotelephone(), alias, codeService, selectedAbonnement.getLabel(), currencyCode, selectedAbonnement.getActivation(), calendar);
            System.out.println("INPUT:{msisdn:" + selectedAbonnement.getNumerotelephone() + ",alias:" + alias.value + ",activationKey:" + selectedAbonnement.getActivation() + ",serviceCode:" + selectedAbonnement.getService() + "} RETOUR OMBREQUEST: " + codeRetour);
            selectedAbonnement.setCoderetour(codeRetour.toString());

            listCode = codesFacade.findDescFromCode(codeRetour.toString());
            if (listCode != null && !listCode.isEmpty()) {
                libelleCodeRetour = listCode.get(0).getCodedescription();
            }

            if (codeRetour == 200) {
                selectedAbonnement.setActif(true);
                selectedAbonnement.setUservalidate(loginManager.getUtilisateur().getLogin());
                selectedAbonnement.setDatevalidation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
                abonnementsFacade.edit(selectedAbonnement);
                listAbonnements.remove(selectedAbonnement);

                //TRACE POUR LA RECONCILIATION
                AbonnementsReconciliations selectedAbonnementRec = new AbonnementsReconciliations(selectedAbonnement);
                selectedAbonnementRec.setMotif("register");
                selectedAbonnementRec.setDatecreation(selectedAbonnement.getDatevalidation());
                selectedAbonnementRec.setCoderetour(selectedAbonnement.getCoderetour());
                abonnementsReconciliationsFacade.create(selectedAbonnementRec);
                sessionManager.getLogs().setAction("VALIDATION D'ABONNEMENT");
                sessionManager.getLogs().setDateLog(new Date());
                sessionManager.getLogs().setMessage("ALIAS: " + selectedAbonnement.getAlias());
                sessionManager.logDB();

                // PDF();
                addMessage("SUCCES", "Abonnement validé avec succès.");

            } else {
                selectedAbonnement.setActif(false);
                selectedAbonnement.setAgence(loginManager.getUtilisateur().getIdagences().getCodeagence());
                abonnementsFacade.edit(selectedAbonnement);

                //TRACE POUR LA RECONCILIATION
                AbonnementsReconciliations selectedAbonnementRec = new AbonnementsReconciliations(selectedAbonnement);
                selectedAbonnementRec.setMotif("register");
                selectedAbonnementRec.setDatecreation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
                selectedAbonnementRec.setCoderetour(selectedAbonnement.getCoderetour());
                abonnementsReconciliationsFacade.create(selectedAbonnementRec);
                sessionManager.getLogs().setAction("TENTATIVE D'ABONNEMENT ECHOUEE");
                sessionManager.getLogs().setDateLog(new Date());
                sessionManager.getLogs().setMessage("ALIAS:" + selectedAbonnement.getAlias() + " MSISDN:" + selectedAbonnement.getNumerotelephone() + " COMPTE:" + selectedAbonnement.getCompte());
                sessionManager.logDB();
                addErrorMessage("ERREUR", "La validation a échoué " + codeRetour + ":" + libelleCodeRetour);
            }

        } catch (ParameterNotFoundException | FeesNotDefinedException | PaymentException ex) {
            ex.printStackTrace();
            addErrorMessage("ERREUR", "Problème rencontré lors du paiement: " + ex.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            codeRetour = 604;
            listCode = codesFacade.findDescFromCode(codeRetour.toString());
            if (listCode != null && !listCode.isEmpty()) {
                libelleCodeRetour = listCode.get(0).getCodedescription();
            }
            addErrorMessage("ERREUR", "La souscription a échoué " + codeRetour + ":" + libelleCodeRetour);
        }
    }

    public void callWebServiceAndRegisterAirtel() {
        calendar = Calendar.getInstance();
        StringHolder alias = new StringHolder(selectedAbonnement.getAlias());
        List<Codes> listCode;
        Short codeRetour;
        String libelleCodeRetour = "";
        String endpointURL = "";
        try {
            if (parametrageManager.isRegisterTarifAllowed()) {
                payForRegistrationService();
            }
            List<Parametres> url = parametresFacade.findByCodeParam("REGISTER_URL_AIRTEL");

            //              RegisterPort_PortType regpt;
            if (!url.isEmpty()) {
                //                  regpt = locator.getRegisterPort(new URL(url.get(0).getValeur()));
                endpointURL = url.get(0).getValeur();
            } else {
                throw new ParameterNotFoundException("REGISTER_URL_AIRTEL");
            }
            McoAtlanticBankServerSoapImplService mcoBankServerSoapImplService = new McoAtlanticBankServerSoapImplService();
            MCOB2ASoap mbss = mcoBankServerSoapImplService.getMcoAtlanticBankServerSoapImplPort();
            BindingProvider bp = (BindingProvider) mbss;
            bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointURL);
            RetBankAccountRegistration rbar = mbss.bankAccountRegistration(bankName, branchCode, selectedAbonnement.getAlias(), selectedAbonnement.getNom(), selectedAbonnement.getPrenoms(), selectedAbonnement.getAlias() + Long.toString(calendar.getTimeInMillis()), Long.toString(calendar.getTimeInMillis()), countryCode, selectedAbonnement.getNumerotelephone(), "", "");
            codeRetour = (short) rbar.getStatus();
            System.out.println("INPUT:{msisdn:" + selectedAbonnement.getNumerotelephone() + ",serviceCode:" + selectedAbonnement.getService() + "} RETOUR OMBREQUEST: " + codeRetour);
            selectedAbonnement.setCoderetour(codeRetour.toString());
            //  libelleCodeRetour=rbar.getDescription();

            if (codeRetour == 0) {
                selectedAbonnement.setActif(true);
                selectedAbonnement.setUservalidate(loginManager.getUtilisateur().getLogin());
                selectedAbonnement.setDatevalidation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
                abonnementsFacade.edit(selectedAbonnement);
                listAbonnements.remove(selectedAbonnement);

                //TRACE POUR LA RECONCILIATION
                AbonnementsReconciliations selectedAbonnementRec = new AbonnementsReconciliations(selectedAbonnement);
                selectedAbonnementRec.setMotif("register");
                selectedAbonnementRec.setDatecreation(selectedAbonnement.getDatevalidation());
                selectedAbonnementRec.setCoderetour(selectedAbonnement.getCoderetour());
                abonnementsReconciliationsFacade.create(selectedAbonnementRec);
                sessionManager.getLogs().setAction("VALIDATION D'ABONNEMENT");
                sessionManager.getLogs().setDateLog(new Date());
                sessionManager.getLogs().setMessage("ALIAS: " + selectedAbonnement.getAlias());
                sessionManager.logDB();

                // PDF();
                addMessage("SUCCES", "Abonnement validé avec succès.");

            } else {
                selectedAbonnement.setActif(false);
                selectedAbonnement.setAgence(loginManager.getUtilisateur().getIdagences().getCodeagence());
                abonnementsFacade.edit(selectedAbonnement);

                //TRACE POUR LA RECONCILIATION
                AbonnementsReconciliations selectedAbonnementRec = new AbonnementsReconciliations(selectedAbonnement);
                selectedAbonnementRec.setMotif("register");
                selectedAbonnementRec.setDatecreation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
                selectedAbonnementRec.setCoderetour(selectedAbonnement.getCoderetour());
                abonnementsReconciliationsFacade.create(selectedAbonnementRec);
                sessionManager.getLogs().setAction("TENTATIVE D'ABONNEMENT ECHOUEE");
                sessionManager.getLogs().setDateLog(new Date());
                sessionManager.getLogs().setMessage("ALIAS:" + selectedAbonnement.getAlias() + " MSISDN:" + selectedAbonnement.getNumerotelephone() + " COMPTE:" + selectedAbonnement.getCompte());
                sessionManager.logDB();
                addErrorMessage("ERREUR", "La validation a échoué " + codeRetour + ":" + rbar.getDescription());
            }

        } catch (ParameterNotFoundException | FeesNotDefinedException | PaymentException ex) {
            ex.printStackTrace();
            addErrorMessage("ERREUR", "Problème rencontré lors du paiement: " + ex.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            codeRetour = 604;
            listCode = codesFacade.findDescFromCode(codeRetour.toString());
            if (listCode != null && !listCode.isEmpty()) {
                libelleCodeRetour = listCode.get(0).getCodedescription();
            }
            addErrorMessage("ERREUR", "La souscription a échoué " + codeRetour + ":" + libelleCodeRetour);
        }
    }

    public void callWebServiceAndRegisterMtn() {

        calendar = Calendar.getInstance();
        List<Codes> listCode;
        Short codeRetour;
        String libelleCodeRetour = "";
        StringHolder alias = new StringHolder(selectedAbonnement.getAlias());
        try {
            if (parametrageManager.isRegisterTarifAllowed()) {
                payForRegistrationService();
            }
            List<Parametres> url = parametresFacade.findByCodeParam("REGISTER_URL_MTN");
            RegisterServiceLocator locator = new RegisterServiceLocator(SSLConfig);

            RegisterPort_PortType regpt;
            if (!url.isEmpty()) {
                regpt = locator.getRegisterPort(new URL(url.get(0).getValeur()));
//                    mbInscriptionWsService=new MbInscriptionWsService(new URL(url.get(0).getValeur()));

            } else {
                throw new ParameterNotFoundException("REGISTER_URL_MTN");
            }
            short codeService = makeCodeService();
            codeRetour = regpt.ombRequest(selectedAbonnement.getNumerotelephone(), alias, codeService, selectedAbonnement.getLabel(), currencyCode, selectedAbonnement.getActivation(), calendar);
//                  MethodeService methodeService=mbInscriptionWsService.getMbInscriptionWsPort();
//                  SubscriptionResponse subscriptionResponse=methodeService.subscription(selectedAbonnement.getNumerotelephone(), Short.valueOf(codeService).toString(), selectedAbonnement.getAlias(),selectedAbonnement.getLabel(), currencyLabel, selectedAbonnement.getActivation());
//                  codeRetour=Short.parseShort(subscriptionResponse.getResponseCode());
            System.out.println("INPUT:{msisdn:" + selectedAbonnement.getNumerotelephone() + ",activationKey:" + selectedAbonnement.getActivation() + ",serviceCode:" + codeService + "} RETOUR OMBREQUEST: " + codeRetour);
            selectedAbonnement.setCoderetour(codeRetour.toString());
            listCode = codesFacade.findDescFromCode(codeRetour.toString());
            if (listCode != null && !listCode.isEmpty()) {
                libelleCodeRetour = listCode.get(0).getCodedescription();
            }
            if (codeRetour == 200) {
                selectedAbonnement.setActif(true);
                selectedAbonnement.setUservalidate(loginManager.getUtilisateur().getLogin());
                selectedAbonnement.setDatevalidation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
                abonnementsFacade.edit(selectedAbonnement);
                listAbonnements.remove(selectedAbonnement);

                //TRACE POUR LA RECONCILIATION
                AbonnementsReconciliations selectedAbonnementRec = new AbonnementsReconciliations(selectedAbonnement);
                selectedAbonnementRec.setMotif("register");
                selectedAbonnementRec.setDatecreation(selectedAbonnement.getDatevalidation());
                selectedAbonnementRec.setCoderetour(selectedAbonnement.getCoderetour());
                abonnementsReconciliationsFacade.create(selectedAbonnementRec);
                sessionManager.getLogs().setAction("VALIDATION D'ABONNEMENT");
                sessionManager.getLogs().setDateLog(new Date());
                sessionManager.getLogs().setMessage("ALIAS: " + selectedAbonnement.getAlias());
                sessionManager.logDB();

                // PDF();
                addMessage("SUCCES", "Abonnement validé avec succès.");

            } else {
                selectedAbonnement.setActif(false);
                selectedAbonnement.setAgence(loginManager.getUtilisateur().getIdagences().getCodeagence());
                abonnementsFacade.edit(selectedAbonnement);

                //TRACE POUR LA RECONCILIATION
                AbonnementsReconciliations selectedAbonnementRec = new AbonnementsReconciliations(selectedAbonnement);
                selectedAbonnementRec.setMotif("register");
                selectedAbonnementRec.setDatecreation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
                selectedAbonnementRec.setCoderetour(selectedAbonnement.getCoderetour());
                abonnementsReconciliationsFacade.create(selectedAbonnementRec);
                sessionManager.getLogs().setAction("TENTATIVE D'ABONNEMENT ECHOUEE");
                sessionManager.getLogs().setDateLog(new Date());
                sessionManager.getLogs().setMessage("ALIAS:" + selectedAbonnement.getAlias() + " MSISDN:" + selectedAbonnement.getNumerotelephone() + " COMPTE:" + selectedAbonnement.getCompte());
                sessionManager.logDB();
                addErrorMessage("ERREUR", "La validation a échoué " + codeRetour + ":" + libelleCodeRetour);
            }

        } catch (ParameterNotFoundException | FeesNotDefinedException | PaymentException ex) {
            ex.printStackTrace();
            addErrorMessage("ERREUR", "Problème rencontré lors du paiement: ERREUR DE COMPTABILISATION " + ex.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            codeRetour = 604;
            listCode = codesFacade.findDescFromCode(codeRetour.toString());
            if (listCode != null && !listCode.isEmpty()) {
                libelleCodeRetour = listCode.get(0).getCodedescription();
            }
            addErrorMessage("ERREUR", "La souscription a échoué " + codeRetour + ":" + libelleCodeRetour);
        }

    }

    public void callWebServiceAndRegisterMtnx() {
        calendar = Calendar.getInstance();
        List<Codes> listCode;
        Short codeRetour;
        String libelleCodeRetour = "";
        StringHolder alias = new StringHolder(selectedAbonnement.getAlias());

        try {
            if (parametrageMtnManager.isRegisterTarifAllowed()) {
                payForRegistrationService();
            }
            List<Parametres> url = parametresFacade.findByCodeParam("REGISTER_URL_MTN");

            //               MbInscriptionWsService mbInscriptionWsService;
            RegisterServiceLocator locator = new RegisterServiceLocator(SSLConfig);

            RegisterPort_PortType regpt;
            if (!url.isEmpty()) {
                regpt = locator.getRegisterPort(new URL(url.get(0).getValeur()));
//                    mbInscriptionWsService=new MbInscriptionWsService(new URL(url.get(0).getValeur()));

            } else {
                throw new ParameterNotFoundException("REGISTER_URL_MTN");
            }
            short codeService = makeCodeService();
            codeRetour = regpt.ombRequest(selectedAbonnement.getNumerotelephone(), alias, codeService, selectedAbonnement.getLabel(), currencyCode, selectedAbonnement.getActivation(), calendar);
//                  MethodeService methodeService=mbInscriptionWsService.getMbInscriptionWsPort();
//                  SubscriptionResponse subscriptionResponse=methodeService.subscription(selectedAbonnement.getNumerotelephone(), Short.valueOf(codeService).toString(), selectedAbonnement.getAlias(),selectedAbonnement.getLabel(), currencyLabel, selectedAbonnement.getActivation());
//                  codeRetour=Short.parseShort(subscriptionResponse.getResponseCode());
            System.out.println("INPUT:{msisdn:" + selectedAbonnement.getNumerotelephone() + ",activationKey:" + selectedAbonnement.getActivation() + ",serviceCode:" + codeService + "} RETOUR OMBREQUEST: " + codeRetour);
            selectedAbonnement.setCoderetour(codeRetour.toString());
            listCode = codesFacade.findDescFromCode(codeRetour.toString());
            if (listCode != null && !listCode.isEmpty()) {
                libelleCodeRetour = listCode.get(0).getCodedescription();
            }

            if (codeRetour == 200) {
                selectedAbonnement.setActif(true);
                selectedAbonnement.setUservalidate(loginManager.getUtilisateur().getLogin());
                selectedAbonnement.setDatevalidation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
                abonnementsFacade.edit(selectedAbonnement);
                listAbonnements.remove(selectedAbonnement);

                //TRACE POUR LA RECONCILIATION
                AbonnementsReconciliations selectedAbonnementRec = new AbonnementsReconciliations(selectedAbonnement);
                selectedAbonnementRec.setMotif("register");
                selectedAbonnementRec.setDatecreation(selectedAbonnement.getDatevalidation());
                selectedAbonnementRec.setCoderetour(selectedAbonnement.getCoderetour());
                abonnementsReconciliationsFacade.create(selectedAbonnementRec);
                sessionManager.getLogs().setAction("VALIDATION D'ABONNEMENT");
                sessionManager.getLogs().setDateLog(new Date());
                sessionManager.getLogs().setMessage("ALIAS: " + selectedAbonnement.getAlias());
                sessionManager.logDB();

                // PDF();
                addMessage("SUCCES", "Abonnement validé avec succès.");

            } else {
                selectedAbonnement.setActif(false);
                selectedAbonnement.setAgence(loginManager.getUtilisateur().getIdagences().getCodeagence());
                abonnementsFacade.edit(selectedAbonnement);

                //TRACE POUR LA RECONCILIATION
                AbonnementsReconciliations selectedAbonnementRec = new AbonnementsReconciliations(selectedAbonnement);
                selectedAbonnementRec.setMotif("register");
                selectedAbonnementRec.setDatecreation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
                selectedAbonnementRec.setCoderetour(selectedAbonnement.getCoderetour());
                abonnementsReconciliationsFacade.create(selectedAbonnementRec);
                sessionManager.getLogs().setAction("TENTATIVE D'ABONNEMENT ECHOUEE");
                sessionManager.getLogs().setDateLog(new Date());
                sessionManager.getLogs().setMessage("ALIAS:" + selectedAbonnement.getAlias() + " MSISDN:" + selectedAbonnement.getNumerotelephone() + " COMPTE:" + selectedAbonnement.getCompte());
                sessionManager.logDB();
                addErrorMessage("ERREUR", "La validation a échoué " + codeRetour + ":" + libelleCodeRetour);
            }

        } catch (ParameterNotFoundException | FeesNotDefinedException | PaymentException ex) {
            ex.printStackTrace();
            addErrorMessage("ERREUR", "Problème rencontré lors du paiement: " + ex.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            codeRetour = 604;
            listCode = codesFacade.findDescFromCode(codeRetour.toString());
            if (listCode != null && !listCode.isEmpty()) {
                libelleCodeRetour = listCode.get(0).getCodedescription();
            }
            addErrorMessage("ERREUR", "La souscription a échoué " + codeRetour + ":" + libelleCodeRetour);
        }
    }

    public void callWebServiceAndRegisterMoovBN() {
        calendar = Calendar.getInstance();
        List<Codes> listCode;
        Short codeRetour;
        String libelleCodeRetour = "";
        try {
            if (parametrageManager.isRegisterTarifAllowed()) {
                payForRegistrationService();
            }
            List<Parametres> url = parametresFacade.findByCodeParam("REGISTER_URL_MOOV");
            MMWServiceService mmws = new MMWServiceService();
            LinkResponse linkResponse = new LinkResponse();
            String endpointURL = "";

            String token = generateToken(SoapWrapperBN.getNonce(parametresFacade));
            if (!url.isEmpty()) {
                endpointURL = url.get(0).getValeur();
            } else {
                throw new ParameterNotFoundException("REGISTER_URL_MOOV");
            }
            short codeService = makeCodeService();

            MMWService mMWService = mmws.getMMWServicePort();
            BindingProvider bp = (BindingProvider) mMWService;
            bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointURL);
            LinkRequest lr = new LinkRequest();
            lr.setBankaccountname(selectedAbonnement.getLabel());
            lr.setBankaccountnumber(selectedAbonnement.getAlias());
            lr.setCurrency(currencyLabel);
            lr.setTimestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(calendar.getTime()));
            lr.setExtdata(selectedAbonnement.getRegion());
            lr.setMsisdn(selectedAbonnement.getNumerotelephone());
            linkResponse = mMWService.linkBankAccount(token, lr);

//            System.out.println("CODE SERVICE:" + codeService);
            codeRetour = Short.parseShort(linkResponse.getStatus());
            System.out.println("INPUT:{msisdn:" + selectedAbonnement.getNumerotelephone() + ",alias:" + selectedAbonnement.getAlias() + ",activationKey:" + selectedAbonnement.getActivation() + "} RETOUR OMBREQUEST: " + codeRetour);
            selectedAbonnement.setCoderetour(codeRetour.toString());

            listCode = codesFacade.findDescFromCode(codeRetour.toString());
            if (listCode != null && !listCode.isEmpty()) {
                libelleCodeRetour = listCode.get(0).getCodedescription();
            }

            if (codeRetour == 0) {
                selectedAbonnement.setActif(true);
                selectedAbonnement.setUservalidate(loginManager.getUtilisateur().getLogin());
                selectedAbonnement.setDatevalidation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
                abonnementsFacade.edit(selectedAbonnement);
                listAbonnements.remove(selectedAbonnement);

                //TRACE POUR LA RECONCILIATION
                AbonnementsReconciliations selectedAbonnementRec = new AbonnementsReconciliations(selectedAbonnement);
                selectedAbonnementRec.setMotif("register");
                selectedAbonnementRec.setDatecreation(selectedAbonnement.getDatevalidation());
                selectedAbonnementRec.setCoderetour(selectedAbonnement.getCoderetour());
                abonnementsReconciliationsFacade.create(selectedAbonnementRec);
                sessionManager.getLogs().setAction("VALIDATION D'ABONNEMENT");
                sessionManager.getLogs().setDateLog(new Date());
                sessionManager.getLogs().setMessage("ALIAS: " + selectedAbonnement.getAlias());
                sessionManager.logDB();

                // PDF();
                addMessage("SUCCES", "Abonnement validé avec succès.");

            } else {
                selectedAbonnement.setActif(false);
                selectedAbonnement.setAgence(loginManager.getUtilisateur().getIdagences().getCodeagence());
                abonnementsFacade.edit(selectedAbonnement);

                //TRACE POUR LA RECONCILIATION
                AbonnementsReconciliations selectedAbonnementRec = new AbonnementsReconciliations(selectedAbonnement);
                selectedAbonnementRec.setMotif("register");
                selectedAbonnementRec.setDatecreation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
                selectedAbonnementRec.setCoderetour(selectedAbonnement.getCoderetour());
                abonnementsReconciliationsFacade.create(selectedAbonnementRec);
                sessionManager.getLogs().setAction("TENTATIVE D'ABONNEMENT ECHOUEE");
                sessionManager.getLogs().setDateLog(new Date());
                sessionManager.getLogs().setMessage("ALIAS:" + selectedAbonnement.getAlias() + " MSISDN:" + selectedAbonnement.getNumerotelephone() + " COMPTE:" + selectedAbonnement.getCompte());
                sessionManager.logDB();
                addErrorMessage("ERREUR", "La validation a échoué " + codeRetour + ":" + libelleCodeRetour);
            }

        } catch (ParameterNotFoundException | FeesNotDefinedException | PaymentException ex) {
            ex.printStackTrace();
            addErrorMessage("ERREUR", "Problème rencontré lors du paiement: " + ex.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            codeRetour = 604;
            listCode = codesFacade.findDescFromCode(codeRetour.toString());
            if (listCode != null && !listCode.isEmpty()) {
                libelleCodeRetour = listCode.get(0).getCodedescription();
            }
            addErrorMessage("ERREUR", "La souscription a échoué " + codeRetour + ":" + libelleCodeRetour);
        }
    }

    public void callWebServiceAndRegisterWizall() {

        List<Codes> listCode;
        Long codeRetour;
        String libelleCodeRetour = "";
        String urlSouscription = "";
        
        try {
            
            if (parametrageManager.isRegisterTarifAllowed()) {
                payForRegistrationService();
            }
            Parametres url_auto_test = null;
            List<Parametres> list_url_auto = parametresFacade.findByCodeParam("REGISTER_URL_WIZALL");
            if (!list_url_auto.isEmpty()) {
                url_auto_test = list_url_auto.get(0);
            }
            urlSouscription = url_auto_test.getValeur() + "subscription";
            String pays = "ci";
            System.out.println("infos de souscription :{msisdn   :" + selectedAbonnement.getNumerotelephone() + " ,alias:  " + selectedAbonnement.getAlias() + ",activationKey:" + selectedAbonnement.getActivation() + ", country:" + pays + "}");
            //code wizall SN - dernier code a jours
            //codeRetour = client.getSubscription(urlSouscription, selectedAbonnement.getNumerotelephone(), selectedAbonnement.getAlias(), selectedAbonnement.getActivation(), this.client);
            //code wizall ci -ancienne version 
            codeRetour = client.getSubscription(urlSouscription, selectedAbonnement.getNumerotelephone(), selectedAbonnement.getAlias(), selectedAbonnement.getActivation());
            System.out.println(" code de reponse : " + codeRetour);
            selectedAbonnement.setCoderetour(Long.toString(codeRetour));
            listCode = codesFacade.findDescFromCode(Long.toString(codeRetour));
            if (listCode != null && !listCode.isEmpty()) {
                libelleCodeRetour = listCode.get(0).getCodedescription();
            }

          if (codeRetour == 200) {

                selectedAbonnement.setActif(true);
                selectedAbonnement.setNumerotelephone(selectedAbonnement.getNumerotelephone());
                selectedAbonnement.setUservalidate(selectedAbonnement.getUsercreate());
                selectedAbonnement.setDatevalidation(selectedAbonnement.getDatecreation());
                makeServiceCodeHumanReadable();
                abonnementsFacade.edit(selectedAbonnement);
                listAbonnements.remove(selectedAbonnement);
                sessionManager.getLogs().setAction("CREATION D'ABONNEMENT");
                sessionManager.getLogs().setDateLog(new Date());
                sessionManager.getLogs().setMessage("ALIAS: " + selectedAbonnement.getAlias() + "MSISDN: " + selectedAbonnement.getNumerotelephone());
                sessionManager.logDB();
                //TRACE POUR LA RECONCILIATION
                AbonnementsReconciliations selectedAbonnementRec = new AbonnementsReconciliations(selectedAbonnement);
                selectedAbonnementRec.setDatecreation(selectedAbonnement.getDatecreation());
                selectedAbonnementRec.setDatevalidation(selectedAbonnement.getDatevalidation());
                selectedAbonnementRec.setMotif("register");
                selectedAbonnementRec.setCoderetour(selectedAbonnement.getCoderetour());
                abonnementsReconciliationsFacade.create(selectedAbonnementRec);
                homeManager.getListAbonnementsJour().add(selectedAbonnement);
                addErrorMessage("INFO", "Souscription réalisée avec succès.");
                PDF();
            } else {
                selectedAbonnement.setActif(false);
                //TRACE POUR LA RECONCILIATION
                AbonnementsReconciliations selectedAbonnementRec = new AbonnementsReconciliations(selectedAbonnement);
                selectedAbonnementRec.setDatecreation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
                selectedAbonnementRec.setMotif("register");
                selectedAbonnementRec.setCoderetour(selectedAbonnement.getCoderetour());
                abonnementsReconciliationsFacade.create(selectedAbonnementRec);
                sessionManager.getLogs().setAction("TENTATIVE D'ABONNEMENT ECHOUEE");
                sessionManager.getLogs().setDateLog(new Date());
                sessionManager.getLogs().setMessage("ALIAS:" + selectedAbonnement.getAlias() + " MSISDN:" + selectedAbonnement.getNumerotelephone() + " COMPTE:" + selectedAbonnement.getCompte());
                sessionManager.logDB();
                addErrorMessage("ERREUR", "La souscription a échoué " + codeRetour + ":" + libelleCodeRetour);
            }
        } catch (ParameterNotFoundException | FeesNotDefinedException | PaymentException ex) {
            ex.printStackTrace();
            addErrorMessage("ERREUR", "Problème rencontré lors du paiement: ERREUR DE COMPTABILISATION " + ex.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            codeRetour = 604L;
            listCode = codesFacade.findDescFromCode(Long.toString(codeRetour));
            if (listCode != null && !listCode.isEmpty()) {
                libelleCodeRetour = listCode.get(0).getCodedescription();
            }
            addErrorMessage("ERREUR", "La souscription a échoué " + codeRetour + ":" + libelleCodeRetour);
        }

    }

    public void callWebServiceAndRegisterWizallx() {
        List<Codes> listCode;
        Long codeRetour;
        String libelleCodeRetour = "";
        String urlSouscription = "";
        try {
            if (parametrageManager.isRegisterTarifAllowed()) {
                payForRegistrationService();
            }
            Parametres url_auto_test = null;
            List<Parametres> list_url_auto = parametresFacade.findByCodeParam("REGISTER_URL_WIZALL");
            if (!list_url_auto.isEmpty()) {
                url_auto_test = list_url_auto.get(0);
            }
            urlSouscription = url_auto_test.getValeur() + "subscription";
            String pays = "ci";
            System.out.println("infos de souscription :{msisdn   :" + selectedAbonnement.getNumerotelephone() + " ,alias:  " + selectedAbonnement.getAlias() + ",activationKey:" + selectedAbonnement.getActivation() + ", country:" + pays + "}");
            //code wizall SN - dernier code a jours
            //codeRetour = client.getSubscription(urlSouscription, selectedAbonnement.getNumerotelephone(), selectedAbonnement.getAlias(), selectedAbonnement.getActivation(), this.client);
            //code wizall ci -ancienne version 
            codeRetour = client.getSubscription(urlSouscription, selectedAbonnement.getNumerotelephone(), selectedAbonnement.getAlias(), selectedAbonnement.getActivation());
            System.out.println(" code de reponse : " + codeRetour);
            selectedAbonnement.setCoderetour(Long.toString(codeRetour));
            listCode = codesFacade.findDescFromCode(Long.toString(codeRetour));
            if (listCode != null && !listCode.isEmpty()) {
                libelleCodeRetour = listCode.get(0).getCodedescription();
            }
            if (codeRetour == 200) {

                selectedAbonnement.setActif(true);
                selectedAbonnement.setNumerotelephone(selectedAbonnement.getNumerotelephone());
                selectedAbonnement.setUservalidate(selectedAbonnement.getUsercreate());
                selectedAbonnement.setDatevalidation(selectedAbonnement.getDatecreation());
                makeServiceCodeHumanReadable();
                abonnementsFacade.edit(selectedAbonnement);
                listAbonnements.remove(selectedAbonnement);
                sessionManager.getLogs().setAction("CREATION D'ABONNEMENT");
                sessionManager.getLogs().setDateLog(new Date());
                sessionManager.getLogs().setMessage("ALIAS: " + selectedAbonnement.getAlias() + "MSISDN: " + selectedAbonnement.getNumerotelephone());
                sessionManager.logDB();
                //TRACE POUR LA RECONCILIATION
                AbonnementsReconciliations selectedAbonnementRec = new AbonnementsReconciliations(selectedAbonnement);
                selectedAbonnementRec.setDatecreation(selectedAbonnement.getDatecreation());
                selectedAbonnementRec.setDatevalidation(selectedAbonnement.getDatevalidation());
                selectedAbonnementRec.setMotif("register");
                selectedAbonnementRec.setCoderetour(selectedAbonnement.getCoderetour());
                abonnementsReconciliationsFacade.create(selectedAbonnementRec);
                homeManager.getListAbonnementsJour().add(selectedAbonnement);
                addErrorMessage("INFO", "Souscription réalisée avec succès.");
                PDF();
            } else {
                selectedAbonnement.setActif(false);
                //TRACE POUR LA RECONCILIATION
                AbonnementsReconciliations selectedAbonnementRec = new AbonnementsReconciliations(selectedAbonnement);
                selectedAbonnementRec.setDatecreation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
                selectedAbonnementRec.setMotif("register");
                selectedAbonnementRec.setCoderetour(selectedAbonnement.getCoderetour());
                abonnementsReconciliationsFacade.create(selectedAbonnementRec);
                sessionManager.getLogs().setAction("TENTATIVE D'ABONNEMENT ECHOUEE");
                sessionManager.getLogs().setDateLog(new Date());
                sessionManager.getLogs().setMessage("ALIAS:" + selectedAbonnement.getAlias() + " MSISDN:" + selectedAbonnement.getNumerotelephone() + " COMPTE:" + selectedAbonnement.getCompte());
                sessionManager.logDB();
                addErrorMessage("ERREUR", "La souscription a échoué " + codeRetour + ":" + libelleCodeRetour);
            }
        } catch (ParameterNotFoundException | FeesNotDefinedException | PaymentException ex) {
            ex.printStackTrace();
            addErrorMessage("ERREUR", "Problème rencontré lors du paiement: ERREUR DE COMPTABILISATION " + ex.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            codeRetour = 604L;
            listCode = codesFacade.findDescFromCode(Long.toString(codeRetour));
            if (listCode != null && !listCode.isEmpty()) {
                libelleCodeRetour = listCode.get(0).getCodedescription();
            }
            addErrorMessage("ERREUR", "La souscription a échoué " + codeRetour + ":" + libelleCodeRetour);
        }

    }

    public boolean customerAlreadyExist() {
        //List<Abonnements> l = abonnementsFacade.findByRacineCompteAndMSISDN(selectedAbonnement.getCompte(), selectedAbonnement.getNumerotelephone());
         List<Abonnements> l = abonnementsFacade.findAbonnementByCompteAndMsisdn(selectedAbonnement.getCompte(), selectedAbonnement.getNumerotelephone(), selectedAbonnement.getOperateur());
        //  Abonnements abont=new Abonnements();
        if (l.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public void makeServiceCodeHumanReadable() {
        switch (selectedAbonnement.getService()) {
            case "1":
                selectedAbonnement.setService("CASH IN");
                break;
            case "2":
                selectedAbonnement.setService("CASH OUT");
                break;
            default:
                selectedAbonnement.setService("CASH IN-CASH OUT");
                break;
        }

    }

    public short makeCodeService() {
        short code;
        switch (selectedAbonnement.getService()) {
            case "CASH IN":
                code = 1;
                break;
            case "CASH OUT":
                code = 2;
                break;
            default:
                code = 3;
                break;
        }
        return code;
    }

    public void setAbonnementInfo(Abonnements abonnements) {
        selectedAbonnement = abonnements;
        System.out.println("SELECTED ABONNEMENT: " + selectedAbonnement.toString());
        abonnementTemp = abonnementTempFacade.findByAlias(selectedAbonnement.getAlias());

    }

    public void reinit() {
        selectedAbonnement = null;
    }

    public void display() {
        System.out.println("MOTIF: " + selectedAbonnement.getMotif());
    }

    public void initJasper() throws JRException, FileNotFoundException, ParameterNotFoundException {
        List<Abonnements> listAbonnementsTemp = abonnementsFacade.findAbonnementActifOfCustomerByNumber(selectedAbonnement);
        System.out.println("TOTAL ABONNEMENTS: " + listAbonnementsTemp.size());
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listAbonnementsTemp);
        // String reportPath=FacesContext.getCurrentInstance().getExternalContext().getRealPath("ficheSouscription.jasper");
        List<Parametres> list = findReportByOperator(selectedAbonnement.getOperateur().getDesignationOperateur());
        //String reportPath="";
        // if(!list.isEmpty())
        String reportPath = list.get(0).getValeur();
        // else throw new FileNotFoundException("Fichier introuvable: "+);
        Map parametres = new HashMap();
        parametres.put("NOM", selectedAbonnement.getNom());
        parametres.put("PRENOMS", selectedAbonnement.getPrenoms());
        // parametres.put("ADRESSE",selectedAbonnement.get());
        parametres.put("DATE_NAISSANCE", selectedAbonnement.getDatenaissance());
        parametres.put("CNI", selectedAbonnement.getCni());
        parametres.put("NUMERO", selectedAbonnement.getNumerotelephone());
        parametres.put("COMPTE", selectedAbonnement.getCompte());
        parametres.put("TYPECOMPTE", selectedAbonnement.getTypecompte());

        jasperPrint = JasperFillManager.fillReport(reportPath, parametres, beanCollectionDataSource);

        // stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(JasperFillManager.fillReportToFile(reportPath,parametres,beanCollectionDataSource));
    }

    public void PDF() throws JRException, IOException, ParameterNotFoundException {
        initJasper();
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + selectedAbonnement.getAlias() + ".pdf");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
        FacesContext.getCurrentInstance().responseComplete();

//    byte[] source= JasperExportManager.exportReportToPdf(jasperPrint);
//    stream = new ByteArrayInputStream(source);
//    file = new DefaultStreamedContent(stream, "application/pdf", "report-B2WSouscription"+selectedAbonnement.getAlias()+".pdf");
//       
    }

    public void printReport() {

    }

    public void payForRegistrationService() throws Exception {

        List<Parametres> wsdlPara = parametresFacade.findByCodeParam("OTHER_SERVICE_WSDL");
        OtherWebServiceService oss;
        if (!wsdlPara.isEmpty()) {
            oss = new OtherWebServiceService(new URL(wsdlPara.get(0).getValeur()));
        } else {
            throw new ParameterNotFoundException("OTHER_SERVICE_WSDL");
        }

        OtherWebService otherService = oss.getOtherWebServicePort();
        TarifsProfilsOperateurs tarifsProfilsOperateurs;
        List<TarifsProfilsOperateurs> l = tarifsProfilsOperateursFacade.findByOperatorAndProfilAndService(selectedAbonnement.getOperateur().getIdOperateur().toString(), selectedAbonnement.getProfil().getIdProfils().toString(), "ABONNEMENT");
        if (!l.isEmpty()) {
            tarifsProfilsOperateurs = l.get(0);
        } else {
            throw new FeesNotDefinedException(selectedAbonnement.getOperateur().getDesignationOperateur(), selectedAbonnement.getProfil().getDesignationProfils(), "ABONNEMENT");
        }
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setAlias(selectedAbonnement.getAlias());
        //  paymentRequest.setCommissions(tarifsProfilsOperateurs.getTarif());
        paymentRequest.setCompte(selectedAbonnement.getCompte());
        paymentRequest.setMontant(tarifsProfilsOperateurs.getTarif());
        paymentRequest.setDatePaiment(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
        PaymentResponse paymentResponse = otherService.payForService(paymentRequest);
        if (paymentResponse.getStatut().equals("1")) {
            throw new PaymentException(paymentResponse.getStatutMsg());
        }

    }

    public void callWebServiceAndRegisterMoov() {
        calendar = Calendar.getInstance();
        String libelleCodeRetour = "";
        Short codeRetour;
        try {
            if (parametrageMoovManager.isRegisterTarifAllowed()) {
                payForRegistrationService();
            }
            String counter = SoapWrapperBN.getNonce(parametresFacade);

            String token = MoovTokenGenerator.generate(counter, user_moov, pass_moov, key);

            List<Parametres> operatorCode = parametresFacade.findByCodeParam("OPERATOR_CODE_MOOV");

            if (!operatorCode.isEmpty()) {
                String response = SoapWrapper.linkAccountRequest(token, operatorCode.get(0).getValeur(), selectedAbonnement.getNumerotelephone(), selectedAbonnement.getActivation(), loginManager.getUtilisateur().getLogin(), selectedAbonnement.getAlias(), selectedAbonnement.getLabel(), "ABONNEMENT");
                StandardResp standardResp = SoapWrapper.getResponseProprety(response, "linkAccountResponse");
                selectedAbonnement.setCoderetour(Integer.toString(standardResp.getStatusCode()));
                codeRetour = new Short(selectedAbonnement.getCoderetour());
                libelleCodeRetour = standardResp.getMessage();
                System.out.println("INPUT:{msisdn:" + selectedAbonnement.getNumerotelephone() + ",alias:" + selectedAbonnement.getAlias() + ",activationKey:" + selectedAbonnement.getActivation() + ",serviceCode:" + selectedAbonnement.getService() + "} RETOUR LINKACCOUNTREQUEST: " + standardResp.getStatusCode() + standardResp.getMessage());

            } else {
                throw new ParameterNotFoundException("OPERATOR_CODE_MOOV");
            }

            // codeRetour = regpt.ombRequest(msisdn, alias, Short.valueOf(selectedAbonnement.getService()), selectedAbonnement.getLabel(), currencyCode, selectedAbonnement.getActivation(), calendar);
            if (codeRetour == 0) {
                selectedAbonnement.setActif(true);

                selectedAbonnement.setUservalidate(selectedAbonnement.getUsercreate());
                selectedAbonnement.setDatevalidation(selectedAbonnement.getDatecreation());
                makeServiceCodeHumanReadable();
                abonnementsFacade.edit(selectedAbonnement);
                sessionManager.getLogs().setAction("CREATION D'ABONNEMENT");
                sessionManager.getLogs().setDateLog(new Date());
                sessionManager.getLogs().setMessage("ALIAS: " + selectedAbonnement.getAlias() + "MSISDN: " + selectedAbonnement.getNumerotelephone());
                sessionManager.logDB();
                //TRACE POUR LA RECONCILIATION
                AbonnementsReconciliations selectedAbonnementRec = new AbonnementsReconciliations(selectedAbonnement);
                selectedAbonnementRec.setDatecreation(selectedAbonnement.getDatecreation());
                selectedAbonnementRec.setDatevalidation(selectedAbonnement.getDatevalidation());
                selectedAbonnementRec.setMotif("register");
                selectedAbonnementRec.setCoderetour(selectedAbonnement.getCoderetour());
                abonnementsReconciliationsFacade.create(selectedAbonnementRec);
                homeManager.getListAbonnementsJour().add(selectedAbonnement);
                addMessage("INFO", "Souscription réalisée avec succès.");
                // PDF();
            } else {
                selectedAbonnement.setActif(false);
                //TRACE POUR LA RECONCILIATION
                AbonnementsReconciliations selectedAbonnementRec = new AbonnementsReconciliations(selectedAbonnement);
                selectedAbonnementRec.setDatecreation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
                selectedAbonnementRec.setMotif("register");
                selectedAbonnementRec.setCoderetour(selectedAbonnement.getCoderetour());
                abonnementsReconciliationsFacade.create(selectedAbonnementRec);
                sessionManager.getLogs().setAction("TENTATIVE D'ABONNEMENT ECHOUEE");
                sessionManager.getLogs().setDateLog(new Date());
                sessionManager.getLogs().setMessage("ALIAS:" + selectedAbonnement.getAlias() + " MSISDN:" + selectedAbonnement.getNumerotelephone() + " COMPTE:" + selectedAbonnement.getCompte());
                sessionManager.logDB();
                addErrorMessage("ERREUR", "La souscription a échoué " + codeRetour + ":" + libelleCodeRetour);
            }
        } catch (ParameterNotFoundException | FeesNotDefinedException | PaymentException ex) {
            ex.printStackTrace();
            addErrorMessage("ERREUR", "Problème rencontré lors du paiement: " + ex.getMessage());

        } catch (Exception e) {
            selectedAbonnement.setActif(false);
            e.printStackTrace();
            codeRetour = 99;
            libelleCodeRetour = "INTERNAL SYSTEM ERROR";
            addErrorMessage("ERREUR", "La souscription a échoué " + codeRetour + ":" + libelleCodeRetour);
        }

    }

    private List<Parametres> findReportByOperator(String operateur) throws ParameterNotFoundException {
        List<Parametres> list = new ArrayList<>();
        switch (operateur) {
            case "ORANGE":
                list = parametresFacade.findByCodeParam("FICHE_SOUSCRIPTION");
                if (list.isEmpty()) {
                    throw new ParameterNotFoundException("FICHE_SOUSCRIPTION");
                }
                break;
            case "WIZALL":
                list = parametresFacade.findByCodeParam("FICHE_SOUSCRIPTION_WIZALL");
                if (list.isEmpty()) {
                    throw new ParameterNotFoundException("FICHE_SOUSCRIPTION_WIZALL");
                }
                break;
            case "MOOV":
                list = parametresFacade.findByCodeParam("FICHE_SOUSCRIPTION_MOOV");
                if (list.isEmpty()) {
                    throw new ParameterNotFoundException("FICHE_SOUSCRIPTION_MOOV");
                }
                break;
            case "AIRTEL":
                list = parametresFacade.findByCodeParam("FICHE_SOUSCRIPTION");
                if (list.isEmpty()) {
                    throw new ParameterNotFoundException("FICHE_SOUSCRIPTION");
                }
                break;
            case "MTN":
                list = parametresFacade.findByCodeParam("FICHE_SOUSCRIPTION_MTN");
                if (list.isEmpty()) {
                    throw new ParameterNotFoundException("FICHE_SOUSCRIPTION_MTN");
                }
                break;

        }
        return list;
    }

    String generateToken(String counter) throws Exception {
        //String counter = new SimpleDateFormat("yyyyMMddHHmmss").format(calendar.getTime()) + loginManager.getUtilisateur().getLogin() + selectedAbonnement.getNumerotelephone() + selectedAbonnement.getActivation() + selectedAbonnement.getRacine();

        String token = MoovTokenGenerator.generate(counter, user_moov, pass_moov, key);
        return token;

    }

}
