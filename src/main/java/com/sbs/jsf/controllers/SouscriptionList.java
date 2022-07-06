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
import com.sbs.easymbank.entities.Parametres;
import com.sbs.easymbank.entities.TarifsProfilsOperateurs;
import com.easymbank.service.other.OtherWebService;
import com.easymbank.service.other.OtherWebServiceService;
import com.easymbank.service.other.PaymentRequest;
import com.easymbank.service.other.PaymentResponse;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.sbs.easymbank.dao.AgencesFacade;
import com.sbs.easymbank.service.omapi.RegisterPort_PortType;
import com.sbs.easymbank.service.omapi.RegisterServiceLocator;
import com.sbs.easymbank.soap.SoapWrapper;
import com.sbs.easymbank.utility.MoovTokenGenerator;
import com.sbs.exceptions.FeesNotDefinedException;
import com.sbs.exceptions.ParameterNotFoundException;
import com.sbs.exceptions.PaymentException;
import com.sbs.jsf.model.LazyAbonnementDataModel;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import com.sbs.easymbank.soap.SoapWrapperBN;
import com.sbs.easymbank.utility.BankAccountDLinkResponse;
import com.sbs.easymbank.utility.ClientHttp;
import com.stdbankinit.server.MCOB2ASoap;
import com.stdbankinit.server.McoAtlanticBankServerSoapImplService;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.ws.BindingProvider;
import mmwservice.LinkResponse;
import mmwservice.MMWService;
import mmwservice.MMWServiceService;
import mmwservice.UnLinkRequest;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.primefaces.component.export.ExcelExporter;
//import org.primefaces.component.export.ExcelOptions;
//import org.primefaces.component.export.PDFOptions;
import org.tempuri.RetBankAccountDeRegistration;
import com.ibayad.bank.atlantique.registration.api.BankAccountRegistrationService;
import com.ibayad.bank.atlantique.registration.api.BankAccountRegistrationServiceService;
import com.ibayad.bank.atlantique.registration.api.BankAccountUnRegistrationReponse;
import com.ibayad.bank.atlantique.registration.api.BankAccountunRegistration;
import com.ibayad.bank.atlantique.registration.api.ObjectFactory;
import javax.xml.bind.JAXBElement;
import org.w3c.dom.Element;

/**
 *
 * @author SOCITECH-
 */
@ManagedBean
@ViewScoped
public class SouscriptionList implements Serializable {

    @EJB
    private CodesFacade codesFacade;

    private LazyDataModel<Abonnements> listAbonne;
    private LazyDataModel<Abonnements> listResilie;
    private List<Abonnements> listAbonnementsToValidate;
    private List<Abonnements> listAbonnementsToDelete;
    private List<Abonnements> listAbonnementsValidateDay;
    private List<Abonnements> listMesSouscriptions = new ArrayList<>();
    private List<Abonnements> filteredAbonnements;
    private Abonnements selectedAbonnement;
    private Map<String, String> map = new HashMap();
    //variable qui garde temporairement le motif d'une eventuelle résiliation
    private String tempMotifResiliation;
    private String outcome;
    private String libelleCodeRetour;
    private SSLClientAxisEngineConfig SSLConfig = new SSLClientAxisEngineConfig();
    private Calendar calendar;
    private ExcelExporter excelOpt;
//    private PDFOptions pdfOpt;
    String indicatifPays;
    List<Parametres> paramWizallApi;
    private Short codeRetour;
    private Abonnements newAbonnement;
    private String leCodeRetour;

    @EJB
    AbonnementsFacade abonnementsFacade;
    @EJB
    private ParametresFacade parametresFacade;
    @EJB
    private AbonnementsReconciliationsFacade abonnementsReconciliationsFacade;
    @EJB
    private TarifsProfilsOperateursFacade tarifsProfilsOperateursFacade;

    @ManagedProperty(value = "#{loginManager}")
    private LoginManager loginManager;
    @ManagedProperty(value = "#{sessionManager}")
    private SessionManager sessionManager;
    @ManagedProperty(value = "#{parametrageManager}")
    private ParametrageManager parametrageManager;
    @ManagedProperty(value = "#{parametrageMoovManager}")
    private ParametrageMoovManager parametrageMoovManager;
    @ManagedProperty(value = "#{parametrageMtnManager}")
    private ParametrageMtnManager parametrageMtnManager;
    @ManagedProperty(value = "#{parametrageAirtelManager}")
    private ParametrageAirtelManager parametrageAirtelManager;
    @EJB
    private AbonnementTempFacade abonnementTempFacade;
    @EJB
    private AgencesFacade agencesFacade;
    private AbonnementTemp abonnementTemp;
    private String format;
    private Abonnements abonnementToPrint;
    private JasperPrint jasperPrint;
    private ClientHttp client;
    private String url_unsubscription;
    private String key;
    private String user_moov;
    private String pass_moov;

    @PostConstruct
    public void init() {
        newAbonnement = new Abonnements();
        client = new ClientHttp();
        try {

            String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
            listAbonnementsValidateDay = abonnementsFacade.findAbonnementCreatedByUserValidate(loginManager.getUtilisateur().getLogin(), date);
            listAbonnementsToValidate = abonnementsFacade.findAbonnementCreatedByUserToValidate(loginManager.getUtilisateur());
            listAbonnementsToDelete = abonnementsFacade.findAbonnementToDelete(loginManager.getUtilisateur().getLogin());
            if (loginManager.getUtilisateur().getIdprofils().isAbonn_ttes_age()) {
                listAbonne = new LazyAbonnementDataModel(abonnementsFacade.findActif());
            } else {
                listAbonne = new LazyAbonnementDataModel(abonnementsFacade.findActifByAgence(loginManager.getUtilisateur().getIdagences().getCodeagence()));
            }
            for (Abonnements abonnements : ((LazyAbonnementDataModel) listAbonne).getDatasource()) {
                abonnements.setLibelleAgence(agencesFacade.findLibelleByCode(abonnements.getAgence()));
                abonnements.setDesignationOperateur(abonnements.getOperateur().getDesignationOperateur());
            }
            listResilie = new LazyAbonnementDataModel((abonnementsFacade.findByResilie()));
            listMesSouscriptions = abonnementsFacade.findAbonnementRejectedOrNotYetValidate(loginManager.getUtilisateur().getLogin());
            String[] patternSSL = new String[]{"KEYSTORE", "TRUSTSTORE",};
            List<Parametres> paraList = parametresFacade.findParametresByPatten(patternSSL);
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
            SSLConfig.setKeystoreType("JKS");

            SSLConfig.setTruststoreType("JKS");
            SSLConfig.initialize();
            List<Parametres> para_indicatif = parametresFacade.findByCodeParam("INDICATIF_PAYS");
            if (para_indicatif != null && !para_indicatif.isEmpty()) {
                indicatifPays = para_indicatif.get(0).getValeur();
            }

//            List<Parametres> p = parametresFacade.findByCodeParam("CLE_MOOV");
//            //String key;
//            if (!p.isEmpty()) {
//                key = p.get(0).getValeur();
//            } else {
//                throw new ParameterNotFoundException("CLE_MOOV");
//            }
//            p = parametresFacade.findByCodeParam("USERNAME_MOOV");
//            // String user;
//            if (!p.isEmpty()) {
//                user_moov = p.get(0).getValeur();
//            } else {
//                throw new ParameterNotFoundException("USERNAME_MOOV");
//            }
//
//            p = parametresFacade.findByCodeParam("TOKEN_MOOV");
//            // String pass;
//            if (!p.isEmpty()) {
//                pass_moov = p.get(0).getValeur();
//            } else {
//                throw new ParameterNotFoundException("TOKEN_MOOV");
//            }
//
//            patternSSL = new String[]{"TRUSTSTORE_MOOV", "REGISTER_URL_MOOV"};
//            paraList = parametresFacade.findParametresByPatten(patternSSL);
//            if (paraList != null && !paraList.isEmpty()) {
//                for (Parametres para : paraList) {
//                    if (para.getCodeparam().equals("CHEMIN_FICHIER_TRUSTSTORE_MOOV")) {
//                        SoapWrapper.CertificatePath = para.getValeur();
//                    } else if (para.getCodeparam().equals("PASSWORD_FICHIER_TRUSTSTORE_MOOV")) {
//                        SoapWrapper.CertificatePassword = para.getValeur();
//                    } else if (para.getCodeparam().equals("REGISTER_URL_MOOV")) {
//                        SoapWrapper.requestURL = para.getValeur();
//                    }
//                }
//            }
            //customizationOptions();
            //recup de la liste des parametres de connexion a l'api wizall 
            paramWizallApi = parametresFacade.findListValeursForParam("ACCESS_API_WIZALL");
            //setting du clientHttp 
            System.out.println("AFFICHAGE DES PARAMETRES POUR LAPI WIZALL ");
            for (Parametres para : paramWizallApi) {
                System.out.println("-->" + para.getCodeparam().toLowerCase() + " " + para.getValeur());
            }

            for (Parametres param : paramWizallApi) {
                if (param.getCodeparam().equalsIgnoreCase("USERNAME")) {
                    client.setUsername(param.getValeur());
                } else if (param.getCodeparam().equalsIgnoreCase("PASSWORD")) {
                    client.setPassword(param.getValeur());
                } else if (param.getCodeparam().equalsIgnoreCase("CLIENT_ID")) {
                    client.setClient_id(param.getValeur());
                } else if (param.getCodeparam().equalsIgnoreCase("CLIENT_SECRET")) {
                    client.setClient_secret(param.getValeur());
                } else if (param.getCodeparam().equalsIgnoreCase("CLIENT_TYPE")) {
                    client.setClient_type(param.getValeur());
                } else if (param.getCodeparam().equalsIgnoreCase("GRANT_TYPE")) {
                    client.setGrant_type(param.getValeur());
                } else if (param.getCodeparam().equalsIgnoreCase("URL_TOKEN")) {
                    client.setUrl_token(param.getValeur());
                } else if (param.getCodeparam().equalsIgnoreCase("URL_KYC")) {
                    client.setUrl_kyc(param.getValeur());
                } else if (param.getCodeparam().equalsIgnoreCase("COUNTRY")) {
                    client.setCountry(param.getValeur());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public String getLeCodeRetour() {
        return leCodeRetour;
    }

    public void setLeCodeRetour(String leCodeRetour) {
        this.leCodeRetour = leCodeRetour;
    }

    public Abonnements getNewAbonnement() {
        return newAbonnement;
    }

    public void setNewAbonnement(Abonnements newAbonnement) {
        this.newAbonnement = newAbonnement;
    }

    public CodesFacade getCodesFacade() {
        return codesFacade;
    }

    public void setCodesFacade(CodesFacade codesFacade) {
        this.codesFacade = codesFacade;
    }

    public String getLibelleCodeRetour() {
        return libelleCodeRetour;
    }

    public void setLibelleCodeRetour(String libelleCodeRetour) {
        this.libelleCodeRetour = libelleCodeRetour;
    }

    public SSLClientAxisEngineConfig getSSLConfig() {
        return SSLConfig;
    }

    public void setSSLConfig(SSLClientAxisEngineConfig SSLConfig) {
        this.SSLConfig = SSLConfig;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public ExcelExporter getExcelOpt() {
        return excelOpt;
    }

    public void setExcelOpt(ExcelExporter excelOpt) {
        this.excelOpt = excelOpt;
    }

    public String getIndicatifPays() {
        return indicatifPays;
    }

    public void setIndicatifPays(String indicatifPays) {
        this.indicatifPays = indicatifPays;
    }

    public AbonnementsFacade getAbonnementsFacade() {
        return abonnementsFacade;
    }

    public void setAbonnementsFacade(AbonnementsFacade abonnementsFacade) {
        this.abonnementsFacade = abonnementsFacade;
    }

    public ParametresFacade getParametresFacade() {
        return parametresFacade;
    }

    public void setParametresFacade(ParametresFacade parametresFacade) {
        this.parametresFacade = parametresFacade;
    }

    public AbonnementsReconciliationsFacade getAbonnementsReconciliationsFacade() {
        return abonnementsReconciliationsFacade;
    }

    public void setAbonnementsReconciliationsFacade(AbonnementsReconciliationsFacade abonnementsReconciliationsFacade) {
        this.abonnementsReconciliationsFacade = abonnementsReconciliationsFacade;
    }

    public TarifsProfilsOperateursFacade getTarifsProfilsOperateursFacade() {
        return tarifsProfilsOperateursFacade;
    }

    public void setTarifsProfilsOperateursFacade(TarifsProfilsOperateursFacade tarifsProfilsOperateursFacade) {
        this.tarifsProfilsOperateursFacade = tarifsProfilsOperateursFacade;
    }

    public AbonnementTempFacade getAbonnementTempFacade() {
        return abonnementTempFacade;
    }

    public void setAbonnementTempFacade(AbonnementTempFacade abonnementTempFacade) {
        this.abonnementTempFacade = abonnementTempFacade;
    }

    public AgencesFacade getAgencesFacade() {
        return agencesFacade;
    }

    public void setAgencesFacade(AgencesFacade agencesFacade) {
        this.agencesFacade = agencesFacade;
    }

    public JasperPrint getJasperPrint() {
        return jasperPrint;
    }

    public void setJasperPrint(JasperPrint jasperPrint) {
        this.jasperPrint = jasperPrint;
    }

    public ClientHttp getClient() {
        return client;
    }

    public void setClient(ClientHttp client) {
        this.client = client;
    }

    public String getUrl_unsubscription() {
        return url_unsubscription;
    }

    public void setUrl_unsubscription(String url_unsubscription) {
        this.url_unsubscription = url_unsubscription;
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

    public Short getCodeRetour() {
        return codeRetour;
    }

    public void setCodeRetour(Short codeRetour) {
        this.codeRetour = codeRetour;
    }

    public SouscriptionList() {
    }

    public List<Parametres> getParamWizallApi() {
        return paramWizallApi;
    }

    public void setParamWizallApi(List<Parametres> paramWizallApi) {
        this.paramWizallApi = paramWizallApi;
    }

    public LazyDataModel<Abonnements> getListAbonne() {
        return listAbonne;
    }

    public void setListAbonne(LazyDataModel<Abonnements> listAbonne) {
        this.listAbonne = listAbonne;
    }

    public Abonnements getSelectedAbonnement() {
        return selectedAbonnement;
    }

    public void setSelectedAbonnement(Abonnements selectedAbonnement) {
        this.selectedAbonnement = selectedAbonnement;
    }

    public List<Abonnements> getFilteredAbonnements() {
        return filteredAbonnements;
    }

    public void setFilteredAbonnements(List<Abonnements> filteredAbonnements) {
        this.filteredAbonnements = filteredAbonnements;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public String getTempMotifResiliation() {
        return tempMotifResiliation;
    }

    public void setTempMotifResiliation(String tempMotifResiliation) {
        this.tempMotifResiliation = tempMotifResiliation;
    }

    public void majMotif() {
        selectedAbonnement.setMotif(tempMotifResiliation);
        System.out.println(selectedAbonnement.getMotif());
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public List<Abonnements> getListMesSouscriptions() {
        return listMesSouscriptions;
    }

    public void setListMesSouscriptions(List<Abonnements> listMesSouscriptions) {
        this.listMesSouscriptions = listMesSouscriptions;
    }

    public LoginManager getLoginManager() {
        return loginManager;
    }

    public void setLoginManager(LoginManager loginManager) {
        this.loginManager = loginManager;
    }

    public List<Abonnements> getListAbonnementsToValidate() {
        return listAbonnementsToValidate;
    }

    public void setListAbonnementsToValidate(List<Abonnements> listAbonnementsToValidate) {
        this.listAbonnementsToValidate = listAbonnementsToValidate;
    }

    public AbonnementTemp getAbonnementTemp() {
        return abonnementTemp;
    }

    public void setAbonnementTemp(AbonnementTemp abonnementTemp) {
        this.abonnementTemp = abonnementTemp;
    }

    public List<Abonnements> getListAbonnementsToDelete() {
        return listAbonnementsToDelete;
    }

    public void setListAbonnementsToDelete(List<Abonnements> listAbonnementsToDelete) {
        this.listAbonnementsToDelete = listAbonnementsToDelete;
    }

    public List<Abonnements> getListAbonnementsValidateDay() {
        return listAbonnementsValidateDay;
    }

    public void setListAbonnementsValidateDay(List<Abonnements> listAbonnementsValidate) {
        this.listAbonnementsValidateDay = listAbonnementsValidate;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Abonnements getAbonnementToPrint() {
        return abonnementToPrint;
    }

    public void setAbonnementToPrint(Abonnements abonnementToPrint) {
        this.abonnementToPrint = abonnementToPrint;
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

    public LazyDataModel<Abonnements> getListResilie() {
        return listResilie;
    }

    public void setListResilie(LazyDataModel<Abonnements> listResilie) {
        this.listResilie = listResilie;
    }

    public ParametrageMtnManager getParametrageMtnManager() {
        return parametrageMtnManager;
    }

    public void setParametrageMtnManager(ParametrageMtnManager parametrageMtnManager) {
        this.parametrageMtnManager = parametrageMtnManager;
    }

    public ParametrageAirtelManager getParametrageAirtelManager() {
        return parametrageAirtelManager;
    }

    public void setParametrageAirtelManager(ParametrageAirtelManager parametrageAirtelManager) {
        this.parametrageAirtelManager = parametrageAirtelManager;
    }

//    public ExcelOptions getExcelOpt() {
//        return excelOpt;
//    }
//
//    public void setExcelOpt(ExcelOptions excelOpt) {
//        this.excelOpt = excelOpt;
//    }
//
//    public PDFOptions getPdfOpt() {
//        return pdfOpt;
//    }
//
//    public void setPdfOpt(PDFOptions pdfOpt) {
//        this.pdfOpt = pdfOpt;
//    }
    public void resilier() {
        if (selectedAbonnement.getOperateur().getDesignationOperateur().equalsIgnoreCase("MOOV") && indicatifPays.equals("229")) {
            resilierMoovBN();
            return;
        }
        if (selectedAbonnement.getOperateur().getDesignationOperateur().equals("MOOV")) {
            resilierMoov();
            return;
        }
        if (selectedAbonnement.getOperateur().getDesignationOperateur().equals("AIRTEL")) {
            resilierAirtel();
            return;
        }
        if (selectedAbonnement.getOperateur().getDesignationOperateur().equals("MTN")) {
            resilierMtn();
            return;
        }
        if (selectedAbonnement.getOperateur().getDesignationOperateur().equals("WIZALL")) {
            System.out.println("desouscription d_un client wizall---------------");
            resilierWizall();
            System.out.println("-------------------fin de la desouscription du client wizall");
            return;
        }
        if (selectedAbonnement.getOperateur().getDesignationOperateur().equals("MALITEL")) {
            resilierMalitel();
            return;
        }
        if (selectedAbonnement.getOperateur().getDesignationOperateur().equals("ONATEL")) {
            resilierOnatelBF();
            return;
        }
        //desouscription client MTNGB
        if (selectedAbonnement.getOperateur().getDesignationOperateur().equals("MTNGB")) {
            try {
                resiliationMTNGB();
                return;
            } catch (IOException ex) {
                Logger.getLogger(SouscriptionList.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(SouscriptionList.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        calendar = Calendar.getInstance();
        System.out.println("ORIGINE: " + selectedAbonnement.getOrigine() + " ALIAS: " + selectedAbonnement.getAlias() + "Opreateur: " + selectedAbonnement.getOperateur().getDesignationOperateur());
        if (selectedAbonnement.getOrigine() != null) {

            // if (!selectedAbonnement.getOrigine().equals("orange")) {//si la résiliaation est initié par orange, il n'y plus besoin de contacter le service web
            try {
                StringHolder alias = new StringHolder(selectedAbonnement.getAlias());
                RegisterServiceLocator locator = new RegisterServiceLocator(SSLConfig);
                List<Parametres> url = parametresFacade.findByCodeParam("REGISTER_URL");
                RegisterPort_PortType regpt;
                if (!url.isEmpty()) {
                    regpt = locator.getRegisterPort(new URL(url.get(0).getValeur()));
                } else {
                    regpt = locator.getRegisterPort();
                }
                String reponse = regpt.ombClose(alias, calendar, selectedAbonnement.getOrigine(), selectedAbonnement.getMotif());
                System.out.println("REPONSE OMBCLOSE: " + reponse);

                selectedAbonnement.setCoderetourresiliation(reponse);
                //  selectedAbonnement.setCoderetourresiliation("200");/*****A COMMENTER****/
                if (selectedAbonnement.getCoderetourresiliation().equals("200")) {
                    selectedAbonnement.setDateresiliation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
                    selectedAbonnement.setResilie(true);
                    selectedAbonnement.setActif(Boolean.FALSE);
                    selectedAbonnement.setUserdesactiv(loginManager.getUtilisateur().getLogin());
                    abonnementsFacade.edit(selectedAbonnement);
                    sessionManager.getLogs().setAction("RESILIATION D'ABONNEMENT");
                    sessionManager.getLogs().setDateLog(new Date());
                    sessionManager.getLogs().setMessage("ALIAS: " + selectedAbonnement.getAlias());
                    sessionManager.logDB();
                    ((LazyAbonnementDataModel) listAbonne).getDatasource().remove(selectedAbonnement);
                    //TRACE POUR LA RECONCILIATION
                    AbonnementsReconciliations newAbonnementRec = new AbonnementsReconciliations(selectedAbonnement);
                    newAbonnementRec.setDatecreation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
                    newAbonnementRec.setMotif("close");
                    newAbonnementRec.setCoderetour(selectedAbonnement.getCoderetourresiliation());
                    abonnementsReconciliationsFacade.create(newAbonnementRec);
                    selectedAbonnement = new Abonnements();
                    addMessage("SUCCES", "L'abonnement a été résilié");
                } else {

                    //TRACE POUR LA RECONCILIATION
                    AbonnementsReconciliations newAbonnementRec = new AbonnementsReconciliations(selectedAbonnement);
                    newAbonnementRec.setMotif("close");
                    newAbonnementRec.setDatecreation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
                    newAbonnementRec.setCoderetour(selectedAbonnement.getCoderetourresiliation());
                    abonnementsReconciliationsFacade.create(newAbonnementRec);
                    addErrorMessage("ECHEC", "La résiliation a échoué " + reponse + ":" + getErrorDescription(reponse));
                }

            } catch (Exception ex) {
                addErrorMessage("ECHEC", "La résiliation a échoué " + "604:" + getErrorDescription("604"));
                ex.printStackTrace();
            }

        } else {
            addErrorMessage("ECHEC", "Veuiilez renseigner l'initiateur de la résiliation");
        }

        // addMessage("INFO", "Abonnement résilié avec succès");
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void addErrorMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void supprimer() {
        abonnementsFacade.remove(selectedAbonnement);
        listAbonnementsToDelete.remove(selectedAbonnement);
        addMessage("SUCCES", "L'abonnement a été supprimé de façon définitive");
    }

    public void supprimer(Abonnements abonnements) {
        selectedAbonnement = abonnements;
        abonnementsFacade.remove(selectedAbonnement);
        listAbonnementsToDelete.remove(selectedAbonnement);
        sessionManager.getLogs().setAction("SUPPRESSION D'ABONNEMENT");
        sessionManager.getLogs().setDateLog(new Date());
        sessionManager.getLogs().setMessage("ALIAS: " + selectedAbonnement.getAlias());
        sessionManager.logDB();
        addMessage("SUCCES", "L'abonnement a été supprimé de façon définitive");
    }

    public String getErrorDescription(String code_error) {
        List<Codes> listCode = codesFacade.findDescFromCode(code_error);
        if (listCode != null && !listCode.isEmpty()) {
            return listCode.get(0).getCodedescription();
        }
        return "";
    }

    public void setAbonnementInfo(Abonnements abonnements) {
        selectedAbonnement = abonnements;
        System.out.println("SELECTED ABONNEMENT: " + selectedAbonnement.toString());
        abonnementTemp = abonnementTempFacade.findByAlias(selectedAbonnement.getAlias());

    }

    public void initJasper() throws JRException, FileNotFoundException {
        List<Abonnements> listAbonnementsTemp = abonnementsFacade.findAbonnementActifOfCustomerByNumber(abonnementToPrint);
        System.out.println("TOTAL ABONNEMENTS: " + listAbonnementsTemp.size());
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listAbonnementsTemp);
        // String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("ficheSouscription.jasper");
        List<Parametres> list;
        if (abonnementToPrint.getOperateur().getDesignationOperateur().equalsIgnoreCase("MOOV")) {
            list = parametresFacade.findByCodeParam("FICHE_SOUSCRIPTION_MOOV");
        } else if (abonnementToPrint.getOperateur().getDesignationOperateur().equalsIgnoreCase("ORANGE")) {
            list = parametresFacade.findByCodeParam("FICHE_SOUSCRIPTION");
        } else if (abonnementToPrint.getOperateur().getDesignationOperateur().equalsIgnoreCase("MTN")) {
            list = parametresFacade.findByCodeParam("FICHE_SOUSCRIPTION_MTN");
        } else {
            list = parametresFacade.findByCodeParam("FICHE_SOUSCRIPTION");
        }
        String reportPath = "";
        if (!list.isEmpty()) {
            reportPath = list.get(0).getValeur();
        } else {
            throw new FileNotFoundException("fichier " + reportPath + " introuvable");
        }
        Map parametres = new HashMap();
        parametres.put("NOM", abonnementToPrint.getNom());
        parametres.put("PRENOMS", abonnementToPrint.getPrenoms());
        // parametres.put("ADRESSE",selectedAbonnement.get());
        parametres.put("DATE_NAISSANCE", abonnementToPrint.getDatenaissance());
        parametres.put("CNI", abonnementToPrint.getCni());
        parametres.put("NUMERO", abonnementToPrint.getNumerotelephone());
        parametres.put("COMPTE", abonnementToPrint.getCompte());
        parametres.put("TYPECOMPTE", abonnementToPrint.getTypecompte());
        jasperPrint = JasperFillManager.fillReport(reportPath, parametres, beanCollectionDataSource);

        // stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(JasperFillManager.fillReportToFile(reportPath,parametres,beanCollectionDataSource));
    }

//    byte[] source= JasperExportManager.exportReportToPdf(jasperPrint);
//    stream = new ByteArrayInputStream(source);
//    file = new DefaultStreamedContent(stream, "application/pdf", "report-B2WSouscription"+selectedAbonnement.getAlias()+".pdf");
//       
    public void report() {
        try {
            // System.out.println("FORMAT: "+format+" BORNE INF: "+borneInf);
            if (abonnementToPrint != null) {
                if (format.equals("PDF")) {
                    PDF();
                } else if (format.equals("DOCX")) {
                    DOCX();
                } else if (format.equals("XLSX")) {
                    XLSX();
                } else if (format.equals("ODT")) {
                    ODT();
                } else {
                    PPT();
                }
            }
        } catch (JRException | IOException ex) {
            ex.printStackTrace();
        }
    }

    public void PDF() throws JRException, IOException {
        initJasper();
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=report-" + "B2WSouscription" + abonnementToPrint.getAlias() + ".pdf");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
        FacesContext.getCurrentInstance().responseComplete();

    }

    public void DOCX() throws JRException, IOException {
        initJasper();
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=report-" + "B2WSouscription" + abonnementToPrint.getAlias() + ".docx");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JRDocxExporter docxExporter = new JRDocxExporter();
        docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
        docxExporter.exportReport();
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void XLSX() throws JRException, IOException {
        initJasper();
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=report-" + "B2WSouscription" + abonnementToPrint.getAlias() + ".xlsx");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JRXlsxExporter docxExporter = new JRXlsxExporter();
        docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
        docxExporter.exportReport();
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void ODT() throws JRException, IOException {
        initJasper();
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=report-" + "B2WSouscription" + abonnementToPrint.getAlias() + ".odt");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JROdtExporter docxExporter = new JROdtExporter();
        docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
        docxExporter.exportReport();
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void PPT() throws JRException, IOException {
        initJasper();
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=report-" + "B2WSouscription" + abonnementToPrint.getAlias() + ".ppt");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JRPptxExporter docxExporter = new JRPptxExporter();
        docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
        docxExporter.exportReport();
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void onRowSelect(SelectEvent event) {
        // System.out.println("SELECTION: "+event.getObject().toString());
        //Abonnements a = (Abonnements) event.getObject();
        //abonnementToPrint = a;
        System.out.println("ABONNEMENT A IMPRIMER: " + abonnementToPrint.getAlias());
        // addInfoMessage("Utilisateur : " + newUtilisateur.getLogin());

    }

    public void resilierMoov() {

        calendar = Calendar.getInstance();
        try {
            if (parametrageMoovManager.isRegisterTarifAllowed()) {
                payForRegistrationService();
            }
            String counter = new SimpleDateFormat("yyyyMMddHHmmss").format(calendar.getTime()) + loginManager.getUtilisateur().getLogin() + selectedAbonnement.getNumerotelephone() + selectedAbonnement.getActivation() + selectedAbonnement.getRacine();

            String token = MoovTokenGenerator.generate(counter, user_moov, pass_moov, key);

            List<Parametres> operatorCode = parametresFacade.findByCodeParam("OPERATOR_CODE_MOOV");
            String delinkdate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(calendar.getTime());

            if (!operatorCode.isEmpty()) {
                String response = SoapWrapper.delinkAccountRequest(token, operatorCode.get(0).getValeur(), selectedAbonnement.getNumerotelephone(), selectedAbonnement.getAlias(), delinkdate, loginManager.getUtilisateur().getLogin(), selectedAbonnement.getMotif());
                StandardResp standardResp = SoapWrapper.getResponseProprety(response, "delinkAccountResponse");
                selectedAbonnement.setCoderetourresiliation(Integer.toString(standardResp.getStatusCode()));
                //  codeRetour = new Short(selectedAbonnement.getCoderetour());
                libelleCodeRetour = standardResp.getMessage();
                System.out.println("INPUT:{msisdn:" + selectedAbonnement.getNumerotelephone() + ",alias:" + selectedAbonnement.getAlias() + ",activationKey:" + selectedAbonnement.getActivation() + ",serviceCode:" + selectedAbonnement.getService() + "} RETOUR DELINKACCOUNTREQUEST: " + standardResp.getStatusCode() + standardResp.getMessage());

            } else {
                throw new ParameterNotFoundException("OPERATOR_CODE_MOOV");
            }

            //System.out.println("REPONSE DELINKACCOUNT: " + reponse);
            //  selectedAbonnement.setCoderetourresiliation(reponse);
            //  selectedAbonnement.setCoderetourresiliation("200");/*****A COMMENTER****/
            if (selectedAbonnement.getCoderetourresiliation().equals("0")) {
                selectedAbonnement.setDateresiliation(delinkdate);
                selectedAbonnement.setResilie(true);
                selectedAbonnement.setActif(Boolean.FALSE);
                selectedAbonnement.setUserdesactiv(loginManager.getUtilisateur().getLogin());
                abonnementsFacade.edit(selectedAbonnement);
                sessionManager.getLogs().setAction("RESILIATION D'ABONNEMENT");
                sessionManager.getLogs().setDateLog(new Date());
                sessionManager.getLogs().setMessage("ALIAS: " + selectedAbonnement.getAlias());
                sessionManager.logDB();
                ((LazyAbonnementDataModel) listAbonne).getDatasource().remove(selectedAbonnement);
                //TRACE POUR LA RECONCILIATION
                AbonnementsReconciliations newAbonnementRec = new AbonnementsReconciliations(selectedAbonnement);
                newAbonnementRec.setDatecreation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
                newAbonnementRec.setMotif("close");
                newAbonnementRec.setCoderetour(selectedAbonnement.getCoderetourresiliation());
                abonnementsReconciliationsFacade.create(newAbonnementRec);
                selectedAbonnement = new Abonnements();
                addMessage("SUCCES", "L'abonnement a été résilié");
            } else {

                //TRACE POUR LA RECONCILIATION
                AbonnementsReconciliations newAbonnementRec = new AbonnementsReconciliations(selectedAbonnement);
                newAbonnementRec.setMotif("close");
                newAbonnementRec.setDatecreation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
                newAbonnementRec.setCoderetour(selectedAbonnement.getCoderetourresiliation());
                abonnementsReconciliationsFacade.create(newAbonnementRec);
                addErrorMessage("ECHEC", "La résiliation a échoué " + selectedAbonnement.getCoderetourresiliation() + ":" + libelleCodeRetour);
            }

        } catch (Exception ex) {
            addErrorMessage("ECHEC", "La résiliation a échoué " + "604:" + getErrorDescription("604"));
            ex.printStackTrace();
        }

    }

    public void resilierMoovBN() {

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

            MMWService mMWService = mmws.getMMWServicePort();
            BindingProvider bp = (BindingProvider) mMWService;
            bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointURL);
            UnLinkRequest lr = new UnLinkRequest();
            lr.setBankaccountname(selectedAbonnement.getLabel());
            lr.setBankaccountnumber(selectedAbonnement.getAlias());
            lr.setUnlinkreason(selectedAbonnement.getMotif());
            lr.setTimestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(calendar.getTime()));
            lr.setExtdata(selectedAbonnement.getRegion());
            lr.setMsisdn(selectedAbonnement.getNumerotelephone());
            linkResponse = mMWService.unlinkBankAccount(token, lr);

//            System.out.println("CODE SERVICE:" + codeService);
            codeRetour = Short.parseShort(linkResponse.getStatus());
            System.out.println("INPUT:{msisdn:" + selectedAbonnement.getNumerotelephone() + ",alias:" + selectedAbonnement.getAlias() + ",motif:" + selectedAbonnement.getMotif() + "} RETOUR OMBREQUEST: " + codeRetour);
            selectedAbonnement.setCoderetourresiliation(codeRetour.toString());

            //System.out.println("REPONSE DELINKACCOUNT: " + reponse);
            //  selectedAbonnement.setCoderetourresiliation(reponse);
            //  selectedAbonnement.setCoderetourresiliation("200");/*****A COMMENTER****/
            if (selectedAbonnement.getCoderetourresiliation().equals("0")) {
                selectedAbonnement.setDateresiliation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
                selectedAbonnement.setResilie(true);
                selectedAbonnement.setActif(Boolean.FALSE);
                selectedAbonnement.setUserdesactiv(loginManager.getUtilisateur().getLogin());
                abonnementsFacade.edit(selectedAbonnement);
                sessionManager.getLogs().setAction("RESILIATION D'ABONNEMENT");
                sessionManager.getLogs().setDateLog(new Date());
                sessionManager.getLogs().setMessage("ALIAS: " + selectedAbonnement.getAlias());
                sessionManager.logDB();
                ((LazyAbonnementDataModel) listAbonne).getDatasource().remove(selectedAbonnement);
                //TRACE POUR LA RECONCILIATION
                AbonnementsReconciliations newAbonnementRec = new AbonnementsReconciliations(selectedAbonnement);
                newAbonnementRec.setDatecreation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
                newAbonnementRec.setMotif("close");
                newAbonnementRec.setCoderetour(selectedAbonnement.getCoderetourresiliation());
                abonnementsReconciliationsFacade.create(newAbonnementRec);
                selectedAbonnement = new Abonnements();
                addMessage("SUCCES", "L'abonnement a été résilié");
            } else {

                //TRACE POUR LA RECONCILIATION
                AbonnementsReconciliations newAbonnementRec = new AbonnementsReconciliations(selectedAbonnement);
                newAbonnementRec.setMotif("close");
                newAbonnementRec.setDatecreation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
                newAbonnementRec.setCoderetour(selectedAbonnement.getCoderetourresiliation());
                abonnementsReconciliationsFacade.create(newAbonnementRec);
                addErrorMessage("ECHEC", "La résiliation a échoué " + selectedAbonnement.getCoderetourresiliation() + ":" + libelleCodeRetour);
            }

        } catch (Exception ex) {
            addErrorMessage("ECHEC", "La résiliation a échoué " + "604:" + getErrorDescription("604"));
            ex.printStackTrace();
        }

    }

    public void resilierWizall() {
        System.out.println("desouscription  client wizall SN");
        calendar = Calendar.getInstance();
        if (parametrageMtnManager.isRegisterTarifAllowed()) {
            try {
                payForRegistrationService();
            } catch (Exception ex) {
                Logger.getLogger(SouscriptionList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        url_unsubscription = this.client.getUrl_kyc() + "unsubscription";

        System.out.println("url de desouscription   : " + url_unsubscription);

        System.out.println("infos pour la desouscription :    url : " + url_unsubscription + " numero de telephone  " + selectedAbonnement.getNumerotelephone() + " alias  " + selectedAbonnement.getAlias() + " country " + this.client.getCountry());

        Long reponse = 604L;
        try {
            reponse = client.getUnSubscription(url_unsubscription, selectedAbonnement.getNumerotelephone(), selectedAbonnement.getAlias(), this.client.getCountry());
        } catch (IOException | ParseException ex) {
            Logger.getLogger(SouscriptionList.class.getName()).log(Level.SEVERE, null, ex);
        }
        //String reponse = regpt.ombClose(alias, calendar, selectedAbonnement.getOrigine(), selectedAbonnement.getMotif());
        System.out.println("CODE REPONSE DESOUSCRIPTION : " + reponse.toString());
        String response = Long.toString(reponse);
        System.out.println("reponse apres convertion : " + response);
        selectedAbonnement.setCoderetourresiliation(response);

        if (selectedAbonnement.getCoderetourresiliation().equalsIgnoreCase("200")) {
            selectedAbonnement.setDateresiliation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
            selectedAbonnement.setResilie(true);
            selectedAbonnement.setActif(Boolean.FALSE);
            selectedAbonnement.setUserdesactiv(loginManager.getUtilisateur().getLogin());
            abonnementsFacade.edit(selectedAbonnement);
            sessionManager.getLogs().setAction("RESILIATION D'ABONNEMENT");
            sessionManager.getLogs().setDateLog(new Date());
            sessionManager.getLogs().setMessage("ALIAS: " + selectedAbonnement.getAlias());
            sessionManager.logDB();
            ((LazyAbonnementDataModel) listAbonne).getDatasource().remove(selectedAbonnement);
            //TRACE POUR LA RECONCILIATION
            AbonnementsReconciliations newAbonnementRec = new AbonnementsReconciliations(selectedAbonnement);
            newAbonnementRec.setDatecreation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
            newAbonnementRec.setMotif("close");
            newAbonnementRec.setCoderetour(selectedAbonnement.getCoderetourresiliation());
            abonnementsReconciliationsFacade.create(newAbonnementRec);
            selectedAbonnement = new Abonnements();
            addMessage("SUCCES", "L'abonnement a été résilié");
        } else {
            //TRACE POUR LA RECONCILIATION
            AbonnementsReconciliations newAbonnementRec = new AbonnementsReconciliations(selectedAbonnement);
            newAbonnementRec.setMotif("close");
            newAbonnementRec.setDatecreation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
            newAbonnementRec.setCoderetour(selectedAbonnement.getCoderetourresiliation());
            abonnementsReconciliationsFacade.create(newAbonnementRec);
            addErrorMessage("ECHEC", "La résiliation a échoué , code erreur : " + selectedAbonnement.getCoderetourresiliation());
        }
    }

    //SamySamwell
    public void resiliationMTNGB() throws MalformedURLException, IOException, ParseException {

        List<Codes> listCode;
        String response = "";
        System.out.println("lancement du service rest de  demande de desouscription ...");
        calendar = Calendar.getInstance();

        //recup de l'adresse de desouscription 
        List<Parametres> urlunregister = parametresFacade.findByCodeParam("DESOUSCRIPTION_REQUEST_INTERN_URL");
        Parametres url_auto = null;

        if (!urlunregister.isEmpty()) {
            url_auto = urlunregister.get(0);
            url_unsubscription = url_auto.getValeur();
            System.out.println("URL DE DESOUSCRIPTION :  " + url_unsubscription);
        }

        //prepare l'url de desouscription interne avec ses parametres 
        URL url = new URL(url_unsubscription + selectedAbonnement.getNumerotelephone() + "/" + selectedAbonnement.getAlias());
        System.out.println("URL PARAMETREE POUR LA DESOUSCRIPTION ..." + url);

        //envoi de la demande de desouscription au service interne ==>DESOUSCRIPTION_REQUEST_INTERN_URL
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() != 200) {
            //cas d'erreur
            response = "Erreur : indisponibilité du service interne de demande de souscription- code erreur : " + conn.getResponseCode();
            System.out.println("souscription echouee chez MTN  ");
            codeRetour = 604;
            newAbonnement.setCoderetour(codeRetour.toString());
            listCode = codesFacade.findDescFromCode(codeRetour.toString());

            if (listCode != null && !listCode.isEmpty()) {
                libelleCodeRetour = listCode.get(0).getCodedescription();
            }
            System.out.println("le code retour est  " + codeRetour + " le motif   " + libelleCodeRetour);
            selectedAbonnement.setCoderetourresiliation(codeRetour.toString());

            //TRACE POUR LA RECONCILIATION
            AbonnementsReconciliations newAbonnementRec = new AbonnementsReconciliations(selectedAbonnement);
            newAbonnementRec.setMotif("close");
            newAbonnementRec.setDatecreation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
            newAbonnementRec.setCoderetour(selectedAbonnement.getCoderetourresiliation());
            abonnementsReconciliationsFacade.create(newAbonnementRec);
            addErrorMessage("ECHEC", "La résiliation a échoué " + selectedAbonnement.getCoderetourresiliation() + ":" + libelleCodeRetour);

        } else {
            //cas de reussite 
            //recup du resultat dns un flux 
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String output = "";
            String trame = new String();

            while ((output = br.readLine()) != null) {
                //System.out.println("----->>>"+output);
                trame += output;
            }

            System.out.println("infos recuperee : " + trame + "type de l'objet recup " + trame.getClass());
            JSONParser parser = new JSONParser();
            JSONObject info = (JSONObject) parser.parse(trame);

            System.out.println("code->" + info.get("code_reponse") + " libelle ->" + info.get("libelle_reponse"));
            response = info.toString();
            codeRetour = 200;
            //PLUS DE MODIF DE L'ABONNE DANS LA BD MAIS DELETE 
//            selectedAbonnement.setCoderetourresiliation(codeRetour.toString());
//            selectedAbonnement.setDateresiliation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
//            selectedAbonnement.setResilie(true);
//            selectedAbonnement.setActif(Boolean.FALSE);
//            selectedAbonnement.setUserdesactiv(loginManager.getUtilisateur().getLogin());
//            abonnementsFacade.edit(selectedAbonnement);
            sessionManager.getLogs().setAction("RESILIATION D'ABONNEMENT CLIENT MTN GB");
            sessionManager.getLogs().setDateLog(new Date());
            sessionManager.getLogs().setMessage("ALIAS: " + selectedAbonnement.getAlias());
            sessionManager.logDB();

            //TRACE POUR LA RECONCILIATION
            AbonnementsReconciliations newAbonnementRec = new AbonnementsReconciliations(selectedAbonnement);
            newAbonnementRec.setDatecreation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
            newAbonnementRec.setMotif("close");
            newAbonnementRec.setCoderetour(selectedAbonnement.getCoderetourresiliation());
            abonnementsReconciliationsFacade.create(newAbonnementRec);

            //RETIRE DU TABLEAU DES ABONNEES
            ((LazyAbonnementDataModel) listAbonne).getDatasource().remove(selectedAbonnement);
            //RETIRE DE LA TABLE DES ABONNES
            System.out.println("suppression du user dans la table des abonnes ");
            try {
                abonnementsFacade.deleteAbonne(selectedAbonnement.getIdabonnements());
                System.out.println("suppression definitive ... OK ");
            } catch (Exception e) {
                System.out.println("soucis lors de la suppression dans la bd ");
                e.getMessage();
            }

            selectedAbonnement = new Abonnements();
            addMessage("SUCCES", "L'abonnement a été résilié");

            System.out.println("fin de la desouscription");
        }
    }

    /**
     * ONATEL BF UNSUBSCRIPTION
     */
    public void resilierOnatelBF() {

        calendar = Calendar.getInstance();
        List<Codes> listCode;
        Short codeRetour;
        String libelleCodeRetour = "";
        try {
            if (parametrageManager.isRegisterTarifAllowed()) {
                payForRegistrationService();
            }
            System.out.println("DEBUT SIMULATION DE DESOUSCRIPTION BABF *******");

            BankAccountRegistrationServiceService bankAccountRegistrationServiceService = new BankAccountRegistrationServiceService();
            BankAccountRegistrationService bankAccountRegistrationService = bankAccountRegistrationServiceService.getBankAccountRegistrationServicePort();
            BindingProvider bindingProvider = (BindingProvider) bankAccountRegistrationService;
            bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://10.133.0.84:60443/mbs-bank-workflow-atlantique/BankAccountRegistrationService?wsdl");

            BankAccountunRegistration accountunRegistration = new ObjectFactory().createBankAccountunRegistration();

            //accountRegistration.setAccountNumber("012345678101");
            accountunRegistration.setReferenceID("BABF-" + newAbonnement.getCompte());//numero de reference demande par ONATEL
            accountunRegistration.setBankName("BANKATLANTIQUE");
            accountunRegistration.setAccountNumber(newAbonnement.getCompte());
            accountunRegistration.setAuthorizedMobileNumber(newAbonnement.getNumerotelephone());
            accountunRegistration.setBeneficiaryFirstName(newAbonnement.getNom());
            accountunRegistration.setBeneficiaryLastName(newAbonnement.getPrenoms());
            accountunRegistration.setBranchCode("BABF");
            accountunRegistration.setCountryCode("226");
            accountunRegistration.setExternalData1(newAbonnement.getNumerotelephone());
            accountunRegistration.setExternalData2("linkage");
            accountunRegistration.setTimestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));

            Object response = bankAccountRegistrationService.bankAccountUnRegistrationFunc(accountunRegistration);

            if (response != null) {
                JAXBContext jAXBContext = JAXBContext.newInstance(ObjectFactory.class);
                Unmarshaller unmarshaller = jAXBContext.createUnmarshaller();

                JAXBElement<BankAccountUnRegistrationReponse> jAXBElement = unmarshaller.unmarshal((Element) response, BankAccountUnRegistrationReponse.class);

                BankAccountUnRegistrationReponse unRegistrationReponse = jAXBElement.getValue();
                System.out.println("Status : " + unRegistrationReponse.getBankAccountUnRegistrationResult().getStatus());
                System.out.println("Description : " + unRegistrationReponse.getBankAccountUnRegistrationResult().getDescription());

            } else {
                System.out.println("erreur de fonctionnement de l'api ");
                //TRACE POUR LA RECONCILIATION
                AbonnementsReconciliations newAbonnementRec = new AbonnementsReconciliations(selectedAbonnement);
                newAbonnementRec.setMotif("close");
                newAbonnementRec.setDatecreation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
                newAbonnementRec.setCoderetour(selectedAbonnement.getCoderetourresiliation());
                abonnementsReconciliationsFacade.create(newAbonnementRec);
                addErrorMessage("ECHEC", "La résiliation a échoué " + selectedAbonnement.getCoderetourresiliation() + ":" + libelleCodeRetour);
            }

        } catch (Exception ex) {
            addErrorMessage("ECHEC", "La résiliation a échoué " + "604:" + getErrorDescription("604"));
            ex.printStackTrace();
        }

    }

    public void resilierMalitel() {

        calendar = Calendar.getInstance();
        try {
            if (parametrageMtnManager.isRegisterTarifAllowed()) {
                payForRegistrationService();
            }
            Parametres url_auto = null;
            List<Parametres> list_url_auto = parametresFacade.findByCodeParam("REGISTER_URL_MALITEL");
            if (!list_url_auto.isEmpty()) {
                url_auto = list_url_auto.get(0);
                System.out.println("URL: " + url_auto.getValeur());
            }

            BankAccountDLinkResponse bankAccountDLinkResponse;
            JAXBContext jaxbContextResp = JAXBContext.newInstance(BankAccountDLinkResponse.class);
            Unmarshaller jaxbUnmarshaller = jaxbContextResp.createUnmarshaller();

            String URL_TOKEN = url_auto.getValeur();
            ClientHttp client = new ClientHttp();

            //String reponse =client.getUnSubscription(URL_TOKEN, selectedAbonnement.getNumerotelephone(),selectedAbonnement.getAlias());
            String reponse = client.bankAccountDLink(URL_TOKEN, selectedAbonnement.getNumerotelephone(), selectedAbonnement.getAlias());

            System.out.println("reponse : " + reponse);

            if (reponse != null) {
                bankAccountDLinkResponse = (BankAccountDLinkResponse) jaxbUnmarshaller.unmarshal(new StringReader(reponse));
                String retour = "0";
                System.out.println("Status : " + bankAccountDLinkResponse.getStatus());
                if (bankAccountDLinkResponse.getStatus().equals(retour)) {
                    leCodeRetour = "200";
                    //String reponse = regpt.ombClose(alias, calendar, selectedAbonnement.getOrigine(), selectedAbonnement.getMotif());
                    System.out.println("REPONSE MALITELCLOSE: " + leCodeRetour);
                } else {
                    leCodeRetour = "604";
                }
                selectedAbonnement.setCoderetourresiliation(leCodeRetour);
            }

            if (selectedAbonnement.getCoderetourresiliation().equals("200")) {
                selectedAbonnement.setDateresiliation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
                selectedAbonnement.setResilie(true);
                selectedAbonnement.setActif(Boolean.FALSE);
                selectedAbonnement.setUserdesactiv(loginManager.getUtilisateur().getLogin());
                abonnementsFacade.edit(selectedAbonnement);
                sessionManager.getLogs().setAction("RESILIATION D'ABONNEMENT");
                sessionManager.getLogs().setDateLog(new Date());
                sessionManager.getLogs().setMessage("ALIAS: " + selectedAbonnement.getAlias());
                sessionManager.logDB();
                ((LazyAbonnementDataModel) listAbonne).getDatasource().remove(selectedAbonnement);
                //TRACE POUR LA RECONCILIATION
                AbonnementsReconciliations newAbonnementRec = new AbonnementsReconciliations(selectedAbonnement);
                newAbonnementRec.setDatecreation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
                newAbonnementRec.setMotif("close");
                newAbonnementRec.setCoderetour(selectedAbonnement.getCoderetourresiliation());
                abonnementsReconciliationsFacade.create(newAbonnementRec);
                selectedAbonnement = new Abonnements();
                addMessage("SUCCES", "L'abonnement a été résilié");
            } else {

                //TRACE POUR LA RECONCILIATION
                AbonnementsReconciliations newAbonnementRec = new AbonnementsReconciliations(selectedAbonnement);
                newAbonnementRec.setMotif("close");
                newAbonnementRec.setDatecreation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
                newAbonnementRec.setCoderetour(selectedAbonnement.getCoderetourresiliation());
                abonnementsReconciliationsFacade.create(newAbonnementRec);
                addErrorMessage("ECHEC", "La résiliation a échoué " + selectedAbonnement.getCoderetourresiliation() + ":" + libelleCodeRetour);
            }
        } catch (Exception ex) {
            addErrorMessage("ECHEC", "La résiliation a échoué " + "604:" + getErrorDescription("604"));
            ex.printStackTrace();
        }

    }

    public void resilierMtn() {

        calendar = Calendar.getInstance();
        try {
            if (parametrageMtnManager.isRegisterTarifAllowed()) {
                payForRegistrationService();
            }
            List<Parametres> url = parametresFacade.findByCodeParam("REGISTER_URL_MTN");
            StringHolder alias = new StringHolder(selectedAbonnement.getAlias());
            RegisterServiceLocator locator = new RegisterServiceLocator(SSLConfig);

            //               MbInscriptionWsService mbInscriptionWsService;
            RegisterPort_PortType regpt;
            if (!url.isEmpty()) {
                regpt = locator.getRegisterPort(new URL(url.get(0).getValeur()));
                //                   mbInscriptionWsService=new MbInscriptionWsService(new URL(url.get(0).getValeur()));

            } else {
                throw new ParameterNotFoundException("REGISTER_URL_MTN");
            }
            String reponse = regpt.ombClose(alias, calendar, selectedAbonnement.getOrigine(), selectedAbonnement.getMotif());
            System.out.println("REPONSE OMBCLOSE: " + reponse);
            selectedAbonnement.setCoderetourresiliation(reponse);
            if (selectedAbonnement.getCoderetourresiliation().equals("200")) {
                selectedAbonnement.setDateresiliation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
                selectedAbonnement.setResilie(true);
                selectedAbonnement.setActif(Boolean.FALSE);
                selectedAbonnement.setUserdesactiv(loginManager.getUtilisateur().getLogin());
                abonnementsFacade.edit(selectedAbonnement);
                sessionManager.getLogs().setAction("RESILIATION D'ABONNEMENT");
                sessionManager.getLogs().setDateLog(new Date());
                sessionManager.getLogs().setMessage("ALIAS: " + selectedAbonnement.getAlias());
                sessionManager.logDB();
                ((LazyAbonnementDataModel) listAbonne).getDatasource().remove(selectedAbonnement);
                //TRACE POUR LA RECONCILIATION
                AbonnementsReconciliations newAbonnementRec = new AbonnementsReconciliations(selectedAbonnement);
                newAbonnementRec.setDatecreation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
                newAbonnementRec.setMotif("close");
                newAbonnementRec.setCoderetour(selectedAbonnement.getCoderetourresiliation());
                abonnementsReconciliationsFacade.create(newAbonnementRec);
                selectedAbonnement = new Abonnements();
                addMessage("SUCCES", "L'abonnement a été résilié");
            } else {

                //TRACE POUR LA RECONCILIATION
                AbonnementsReconciliations newAbonnementRec = new AbonnementsReconciliations(selectedAbonnement);
                newAbonnementRec.setMotif("close");
                newAbonnementRec.setDatecreation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
                newAbonnementRec.setCoderetour(selectedAbonnement.getCoderetourresiliation());
                abonnementsReconciliationsFacade.create(newAbonnementRec);
                addErrorMessage("ECHEC", "La résiliation a échoué " + selectedAbonnement.getCoderetourresiliation() + ":" + libelleCodeRetour);
            }

        } catch (Exception ex) {
            addErrorMessage("ECHEC", "La résiliation a échoué " + "604:" + getErrorDescription("604"));
            ex.printStackTrace();
        }

    }

    public void resilierAirtel() {

        calendar = Calendar.getInstance();
        try {
            if (parametrageAirtelManager.isRegisterTarifAllowed()) {
                payForRegistrationService();
            }
            List<Parametres> url = parametresFacade.findByCodeParam("REGISTER_URL_AIRTEL");
            String[] pattern = new String[]{"BANKNAME", "BRANCHCODE", "COUNTRYCODE"};
            List<Parametres> paraList = parametresFacade.findParametresByPatten(pattern);
            String bankName = "", branchCode = "", countryCode = "";
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
            if (bankName == null) {
                throw new ParameterNotFoundException("BANKNAME");
            }
            if (branchCode == null) {
                throw new ParameterNotFoundException("BRANCHCODE");
            }
            if (countryCode == null) {
                throw new ParameterNotFoundException("COUNTRYCODE");
            }

            String endpointURL = "";
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
            RetBankAccountDeRegistration rbar = mbss.bankAccountDeRegistration(bankName, branchCode, selectedAbonnement.getNumerotelephone(), selectedAbonnement.getAlias(), selectedAbonnement.getNom(), selectedAbonnement.getPrenoms(), selectedAbonnement.getAlias() + Long.toString(calendar.getTimeInMillis()), Long.toString(calendar.getTimeInMillis()), countryCode, "Résiliation " + selectedAbonnement.getAlias(), "");
            selectedAbonnement.setCoderetourresiliation(String.valueOf(rbar.getStatus()));
            if (selectedAbonnement.getCoderetourresiliation().equals("0")) {
                selectedAbonnement.setDateresiliation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
                selectedAbonnement.setResilie(true);
                selectedAbonnement.setActif(Boolean.FALSE);
                selectedAbonnement.setUserdesactiv(loginManager.getUtilisateur().getLogin());
                abonnementsFacade.edit(selectedAbonnement);
                sessionManager.getLogs().setAction("RESILIATION D'ABONNEMENT");
                sessionManager.getLogs().setDateLog(new Date());
                sessionManager.getLogs().setMessage("ALIAS: " + selectedAbonnement.getAlias());
                sessionManager.logDB();
                ((LazyAbonnementDataModel) listAbonne).getDatasource().remove(selectedAbonnement);
                //TRACE POUR LA RECONCILIATION
                AbonnementsReconciliations newAbonnementRec = new AbonnementsReconciliations(selectedAbonnement);
                newAbonnementRec.setDatecreation(new SimpleDateFormat("yyyy-MM-dd HH:m  m:ss").format(calendar.getTime()));
                newAbonnementRec.setMotif("close");
                newAbonnementRec.setCoderetour(selectedAbonnement.getCoderetourresiliation());
                abonnementsReconciliationsFacade.create(newAbonnementRec);
                selectedAbonnement = new Abonnements();
                addMessage("SUCCES", "L'abonnement a été résilié");
            } else {

                //TRACE POUR LA RECONCILIATION
                AbonnementsReconciliations newAbonnementRec = new AbonnementsReconciliations(selectedAbonnement);
                newAbonnementRec.setMotif("close");
                newAbonnementRec.setDatecreation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
                newAbonnementRec.setCoderetour(selectedAbonnement.getCoderetourresiliation());
                abonnementsReconciliationsFacade.create(newAbonnementRec);
                addErrorMessage("ECHEC", "La résiliation a échoué " + selectedAbonnement.getCoderetourresiliation() + ":" + rbar.getDescription());
            }

        } catch (Exception ex) {
            addErrorMessage("ECHEC", "La résiliation a échoué " + "604:" + getErrorDescription("604"));
            ex.printStackTrace();
        }

    }

    public void payForRegistrationService() throws Exception {

        TarifsProfilsOperateurs tarifsProfilsOperateurs;
        List<TarifsProfilsOperateurs> l = tarifsProfilsOperateursFacade.findByOperatorAndProfilAndService(selectedAbonnement.getOperateur().getIdOperateur().toString(), selectedAbonnement.getProfil().getIdProfils().toString(), "ABONNEMENT");
        if (!l.isEmpty()) {
            tarifsProfilsOperateurs = l.get(0);
        } else {
            throw new FeesNotDefinedException(selectedAbonnement.getOperateur().getDesignationOperateur(), selectedAbonnement.getProfil().getDesignationProfils(), "ABONNEMENT");
        }

        if (tarifsProfilsOperateurs.getTarif() != 0) {
            List<Parametres> wsdlPara = parametresFacade.findByCodeParam("OTHER_SERVICE_WSDL");
            OtherWebServiceService oss;
            if (!wsdlPara.isEmpty()) {
                oss = new OtherWebServiceService(new URL(wsdlPara.get(0).getValeur()));
            } else {
                throw new ParameterNotFoundException("OTHER_SERVICE_WSDL");
            }

            OtherWebService otherService = oss.getOtherWebServicePort();

            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setAlias(selectedAbonnement.getAlias());
            paymentRequest.setCommissions(selectedAbonnement.getOperateur().getIdOperateur().longValue());  /// operateur à la place de commissions
            paymentRequest.setCompte(selectedAbonnement.getCompte());
            paymentRequest.setMontant(tarifsProfilsOperateurs.getTarif());
            paymentRequest.setDatePaiment(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
            PaymentResponse paymentResponse = otherService.payForService(paymentRequest);
            if (paymentResponse.getStatut().equals("1")) {
                throw new PaymentException(paymentResponse.getStatutMsg());
            }

        }

    }

    String generateToken(String counter) throws Exception {
        //String counter = new SimpleDateFormat("yyyyMMddHHmmss").format(calendar.getTime()) + loginManager.getUtilisateur().getLogin() + selectedAbonnement.getNumerotelephone() + selectedAbonnement.getActivation() + selectedAbonnement.getRacine();

        String token = MoovTokenGenerator.generate(counter, user_moov, pass_moov, key);
        return token;

    }

//            private void customizationOptions() {
//        excelOpt = new ExcelExporter();excelOpt.
//        excelOpt.setFacetBgColor("#F88017");
//        excelOpt.setFacetFontSize("10");
////        excelOpt.setFacetFontColor("#0000ff");
//        excelOpt.setFacetFontStyle("BOLD");
//        excelOpt.setCellFontColor("#00ff00");
//        excelOpt.setCellFontSize("8");
//         
//        pdfOpt = new PDFOptions();
//        pdfOpt.setFacetBgColor("#F88017");
////        pdfOpt.setFacetFontColor("#0000ff");
//        pdfOpt.setFacetFontStyle("BOLD");
//        pdfOpt.setCellFontSize("12");
//    }
    public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);

        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
//        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
            HSSFCell cell = header.getCell(i);

            cell.setCellStyle(cellStyle);
        }
    }

    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);
        pdf.setMargins(1, 2, 2, 1);

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
//        String logo = externalContext.getRealPath("") + File.separator + "resources" + File.separator + "demo" + File.separator + "images" + File.separator + "prime_logo.png";
//         
//        pdf.add(Image.getInstance(logo));
    }

}
