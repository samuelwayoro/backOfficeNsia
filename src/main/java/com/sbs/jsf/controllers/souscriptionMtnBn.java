/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.jsf.controllers;

import at.telekom.util.axis.SSLClientAxisEngineConfig;
import com.easymbank.service.other.Client;
import com.easymbank.service.other.OtherWebService;
import com.easymbank.service.other.OtherWebServiceService;
import com.sbs.easymbank.dao.AbonnementBanqueFacade;
import com.sbs.easymbank.dao.AbonnementTempFacade;
import com.sbs.easymbank.dao.AbonnementsFacade;
import com.sbs.easymbank.dao.AbonnementsOmFacade;
import com.sbs.easymbank.dao.AbonnementsReconciliationsFacade;
import com.sbs.easymbank.dao.BanqueFacade;
import com.sbs.easymbank.dao.CodesFacade;
import com.sbs.easymbank.dao.OperateursFacade;
import com.sbs.easymbank.dao.ParametresFacade;
import com.sbs.easymbank.dao.ProfilsClientsFacade;
import com.sbs.easymbank.dao.TarifsProfilsOperateursFacade;
import com.sbs.easymbank.entities.AbonnementBanque;
import com.sbs.easymbank.entities.AbonnementTemp;
import com.sbs.easymbank.entities.Abonnements;
import com.sbs.easymbank.entities.AbonnementsOm;
import com.sbs.easymbank.entities.AbonnementsReconciliations;
import com.sbs.easymbank.entities.Banque;
import com.sbs.easymbank.entities.Codes;
import com.sbs.easymbank.entities.Comptes;
import com.sbs.easymbank.entities.Operateurs;
import com.sbs.easymbank.entities.Parametres;
import com.sbs.easymbank.entities.ProfilsClients;
import com.sbs.easymbank.logic.Service;
import com.sbs.easymbank.service.omapi.RegisterServiceLocator;
import com.sbs.easymbank.utility.PostXml;
import static com.sbs.easymbank.utility.PostXml.postXml;
import com.sbs.exceptions.OperatorNotFoundException;
import com.sbs.exceptions.ParameterNotFoundException;
import gw.bao.becwapi.domain.GetAccountHolderInfoResponse;
import gw.bao.becwapi.domain.HttpClientResponse;
import gw.bao.becwapi.domain.LinkfinancialresourceinformationResponse;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.rpc.holders.StringHolder;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.axis.AxisProperties;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author samuel
 */
@ManagedBean
@ViewScoped
public class souscriptionMtnBn implements Serializable {

    @EJB
    private CodesFacade codesFacade;
    private JasperPrint jasperPrint;
    private String cniNumber;
    private String activationKey = "";
    private String msisdn;
    private String customerRoot;
    private String accountNumber;
    private String serviceCode;
    private Service service;
    private AbonnementsOm abOm = new AbonnementsOm();
    private SSLClientAxisEngineConfig sSLConfig = new SSLClientAxisEngineConfig();

    private AbonnementBanque abBanque = new AbonnementBanque();
    private Abonnements newAbonnement;

    private List<AbonnementBanque> listAbBque = new ArrayList<>();
    //fourni normalement par l'API MoMo
    private Short codeRetour;
    private String listNumero;
    private List<Comptes> listCpte = new ArrayList<>();
    private List<String> listPhone = new ArrayList<>();
    private boolean exceptionWhenContactingOM;
    private String libelleCodeRetour;
    String currencyLabel;
    String currencyCode;
    String indicatifPays;
    @ManagedProperty(value = "#{loginManager}")
    private LoginManager loginManager;
    @ManagedProperty(value = "#{parametrageManager}")
    private ParametrageManager parametrageManager;
    @ManagedProperty(value = "#{homeManager}")
    private HomeManager homeManager;
    @ManagedProperty(value = "#{sessionManager}")
    private SessionManager sessionManager;
    private Comptes selectedComptes = new Comptes();
    private Calendar calendar;
    private Operateurs operateur;
    List<ProfilsClients> listProfils;
    private Parametres autorisationTarificationAbonnement;
    private Parametres t24Autorisation;
    private Parametres deltaAutorisation;
    @EJB
    private AbonnementsOmFacade abonnementsOMFacade;
    @EJB
    private AbonnementBanqueFacade abonnementBanqueFacade;
    @EJB
    private AbonnementsFacade abonnementsFacade;
    @EJB
    private AbonnementsReconciliationsFacade abonnementsReconciliationsFacade;

    @EJB
    private BanqueFacade banqueFacade;
    @EJB
    private ParametresFacade parametresFacade;
    @EJB
    private AbonnementTempFacade abonnementTempFacade;
    @EJB
    private OperateursFacade operateursFacade;
    @EJB
    private ProfilsClientsFacade profilsClientsFacade;
    @EJB
    private TarifsProfilsOperateursFacade tarifsProfilsOperateursFacade;

    @PostConstruct
    public void init() {
        try {
            String[] patternSSL = new String[]{"KEYSTORE", "TRUSTSTORE"};
            String[] patternDevise = new String[]{"DEVISE"};
            List<Parametres> paraList = parametresFacade.findParametresByPatten(patternSSL);
            if (paraList != null && !paraList.isEmpty()) {
                for (Parametres para : paraList) {
                    if (para.getCodeparam().equals("CHEMIN_FICHIER_KEYSTORE")) {
                        sSLConfig.setKeystore(para.getValeur());
                    } else if (para.getCodeparam().equals("CHEMIN_FICHIER_TRUSTSTORE")) {
                        sSLConfig.setTruststore(para.getValeur());
                    } else {
                        sSLConfig.setKeystorePassword(para.getValeur());
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
            List<Parametres> para_indicatif = parametresFacade.findByCodeParam("INDICATIF_PAYS");
            if (para_indicatif != null && !para_indicatif.isEmpty()) {
                indicatifPays = para_indicatif.get(0).getValeur();
            }
            // exceptionWhenContactingOM = false;
            sSLConfig.setKeystoreType("JKS");
            sSLConfig.setTruststoreType("JKS");
            sSLConfig.initialize();
            AxisProperties.setProperty("axis.ClientConfigFile", "/WEB-INF/client-config.wsdd");
            listProfils = profilsClientsFacade.findAll();
            List<Operateurs> lop = operateursFacade.findByDesignationOperateur("MTNGB");
            if (!lop.isEmpty()) {
                operateur = lop.get(0);
            } else {
                throw new OperatorNotFoundException("MTNGB");
            }

//            List<Parametres> t24=parametresFacade.findByCodeParam("ACTIVATION_T24");
//            if(t24 != null && !t24.isEmpty()){
//                t24Autorisation=t24.get(0);
//            }
//            List<Parametres> deltaPara=parametresFacade.findByCodeParam("ACTIVATION_DELTA");
//            if(deltaPara != null && !deltaPara.isEmpty()){
//                deltaAutorisation=deltaPara.get(0);
//            }
            if (indicatifPays == null) {
                throw new ParameterNotFoundException("INDICATIF_PAYS");
            }

            if (sSLConfig.getKeystore() == null) {
                throw new ParameterNotFoundException("CHEMIN_FICHIER_KEYSTORE");
            }
            if (sSLConfig.getKeystorepin() == null) {
                throw new ParameterNotFoundException("PASSWORD_FICHIER_TRUSTSTORE");
            }
            if (currencyCode == null) {
                throw new ParameterNotFoundException("CODE_DEVISE");
            }
            if (currencyLabel == null) {
                throw new ParameterNotFoundException("LIBELLE_DEVISE");
            }
//            if(t24Autorisation == null){
//                throw new ParameterNotFoundException("ACTIVATION_T24");
//            }
//            if(deltaAutorisation == null){
//                throw new ParameterNotFoundException("ACTIVATION_DELTA");
//            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Converter ComptesConverter = new Converter() {

        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {
            //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            for (Comptes cpte : listCpte) {
                if (cpte.getNuemro().equals(value)) {
                    return cpte;
                }
            }
            return null;
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            /// throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            if (value instanceof Comptes) {
                return ((Comptes) value).getNuemro();
            }
            return "";
        }

    };
    private Converter AbonnementBqConverter = new Converter() {

        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {
            return abonnementBanqueFacade.findByCompte(value);
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            return value.toString(); //To change body of generated methods, choose Tools | Templates.
        }

    };

    private Converter ProfilConverter = new Converter() {

        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {
            System.out.println("value:" + value);
            return profilsClientsFacade.find(Short.parseShort(value));
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            // if(value!=null){
            //            System.out.println("value:"+value.toString());
            return ((ProfilsClients) value).getIdProfils().toString();

            //}return null;
        }
    };

    public CodesFacade getCodesFacade() {
        return codesFacade;
    }

    public void setCodesFacade(CodesFacade codesFacade) {
        this.codesFacade = codesFacade;
    }

    public JasperPrint getJasperPrint() {
        return jasperPrint;
    }

    public void setJasperPrint(JasperPrint jasperPrint) {
        this.jasperPrint = jasperPrint;
    }

    public String getCniNumber() {
        return cniNumber;
    }

    public void setCniNumber(String cniNumber) {
        this.cniNumber = cniNumber;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getCustomerRoot() {
        return customerRoot;
    }

    public void setCustomerRoot(String customerRoot) {
        this.customerRoot = customerRoot;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public AbonnementsOm getAbOm() {
        return abOm;
    }

    public void setAbOm(AbonnementsOm abOm) {
        this.abOm = abOm;
    }

    public SSLClientAxisEngineConfig getsSLConfig() {
        return sSLConfig;
    }

    public void setsSLConfig(SSLClientAxisEngineConfig sSLConfig) {
        this.sSLConfig = sSLConfig;
    }

    public AbonnementBanque getAbBanque() {
        return abBanque;
    }

    public void setAbBanque(AbonnementBanque abBanque) {
        this.abBanque = abBanque;
    }

    public Abonnements getNewAbonnement() {
        return newAbonnement;
    }

    public void setNewAbonnement(Abonnements newAbonnement) {
        this.newAbonnement = newAbonnement;
    }

    public List<AbonnementBanque> getListAbBque() {
        return listAbBque;
    }

    public void setListAbBque(List<AbonnementBanque> listAbBque) {
        this.listAbBque = listAbBque;
    }

    public Short getCodeRetour() {
        return codeRetour;
    }

    public void setCodeRetour(Short codeRetour) {
        this.codeRetour = codeRetour;
    }

    public String getListNumero() {
        String listNumber = "";
        for (AbonnementBanque ab : listAbBque) {
            listNumber = listNumber + ab.getNumero() + "\n";
        }
        return listNumber;
    }

    public void setListNumero(String listNumero) {
        this.listNumero = listNumero;
    }

    public List<Comptes> getListCpte() {
        return listCpte;
    }

    public void setListCpte(List<Comptes> listCpte) {
        this.listCpte = listCpte;
    }

    public List<String> getListPhone() {
        return listPhone;
    }

    public void setListPhone(List<String> listPhone) {
        this.listPhone = listPhone;
    }

    public boolean isExceptionWhenContactingOM() {
        return exceptionWhenContactingOM;
    }

    public void setExceptionWhenContactingOM(boolean exceptionWhenContactingOM) {
        this.exceptionWhenContactingOM = exceptionWhenContactingOM;
    }

    public String getLibelleCodeRetour() {
        return libelleCodeRetour;
    }

    public void setLibelleCodeRetour(String libelleCodeRetour) {
        this.libelleCodeRetour = libelleCodeRetour;
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

    public String getIndicatifPays() {
        return indicatifPays;
    }

    public void setIndicatifPays(String indicatifPays) {
        this.indicatifPays = indicatifPays;
    }

    public LoginManager getLoginManager() {
        return loginManager;
    }

    public void setLoginManager(LoginManager loginManager) {
        this.loginManager = loginManager;
    }

    public ParametrageManager getParametrageManager() {
        return parametrageManager;
    }

    public void setParametrageManager(ParametrageManager parametrageManager) {
        this.parametrageManager = parametrageManager;
    }

    public HomeManager getHomeManager() {
        return homeManager;
    }

    public void setHomeManager(HomeManager homeManager) {
        this.homeManager = homeManager;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public Comptes getSelectedComptes() {
        return selectedComptes;
    }

    public void setSelectedComptes(Comptes selectedComptes) {
        this.selectedComptes = selectedComptes;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public Operateurs getOperateur() {
        return operateur;
    }

    public void setOperateur(Operateurs operateur) {
        this.operateur = operateur;
    }

    public List<ProfilsClients> getListProfils() {
        return listProfils;
    }

    public void setListProfils(List<ProfilsClients> listProfils) {
        this.listProfils = listProfils;
    }

    public Parametres getAutorisationTarificationAbonnement() {
        return autorisationTarificationAbonnement;
    }

    public void setAutorisationTarificationAbonnement(Parametres autorisationTarificationAbonnement) {
        this.autorisationTarificationAbonnement = autorisationTarificationAbonnement;
    }

    public Parametres getT24Autorisation() {
        return t24Autorisation;
    }

    public void setT24Autorisation(Parametres t24Autorisation) {
        this.t24Autorisation = t24Autorisation;
    }

    public Parametres getDeltaAutorisation() {
        return deltaAutorisation;
    }

    public void setDeltaAutorisation(Parametres deltaAutorisation) {
        this.deltaAutorisation = deltaAutorisation;
    }

    public AbonnementsOmFacade getAbonnementsOMFacade() {
        return abonnementsOMFacade;
    }

    public void setAbonnementsOMFacade(AbonnementsOmFacade abonnementsOMFacade) {
        this.abonnementsOMFacade = abonnementsOMFacade;
    }

    public AbonnementBanqueFacade getAbonnementBanqueFacade() {
        return abonnementBanqueFacade;
    }

    public void setAbonnementBanqueFacade(AbonnementBanqueFacade abonnementBanqueFacade) {
        this.abonnementBanqueFacade = abonnementBanqueFacade;
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

    public BanqueFacade getBanqueFacade() {
        return banqueFacade;
    }

    public void setBanqueFacade(BanqueFacade banqueFacade) {
        this.banqueFacade = banqueFacade;
    }

    public ParametresFacade getParametresFacade() {
        return parametresFacade;
    }

    public void setParametresFacade(ParametresFacade parametresFacade) {
        this.parametresFacade = parametresFacade;
    }

    public AbonnementTempFacade getAbonnementTempFacade() {
        return abonnementTempFacade;
    }

    public void setAbonnementTempFacade(AbonnementTempFacade abonnementTempFacade) {
        this.abonnementTempFacade = abonnementTempFacade;
    }

    public OperateursFacade getOperateursFacade() {
        return operateursFacade;
    }

    public void setOperateursFacade(OperateursFacade operateursFacade) {
        this.operateursFacade = operateursFacade;
    }

    public ProfilsClientsFacade getProfilsClientsFacade() {
        return profilsClientsFacade;
    }

    public void setProfilsClientsFacade(ProfilsClientsFacade profilsClientsFacade) {
        this.profilsClientsFacade = profilsClientsFacade;
    }

    public TarifsProfilsOperateursFacade getTarifsProfilsOperateursFacade() {
        return tarifsProfilsOperateursFacade;
    }

    public void setTarifsProfilsOperateursFacade(TarifsProfilsOperateursFacade tarifsProfilsOperateursFacade) {
        this.tarifsProfilsOperateursFacade = tarifsProfilsOperateursFacade;
    }

    public Converter getComptesConverter() {
        return ComptesConverter;
    }

    public void setComptesConverter(Converter ComptesConverter) {
        this.ComptesConverter = ComptesConverter;
    }

    public Converter getAbonnementBqConverter() {
        return AbonnementBqConverter;
    }

    public void setAbonnementBqConverter(Converter AbonnementBqConverter) {
        this.AbonnementBqConverter = AbonnementBqConverter;
    }

    public Converter getProfilConverter() {
        return ProfilConverter;
    }

    public void setProfilConverter(Converter ProfilConverter) {
        this.ProfilConverter = ProfilConverter;
    }

    //methode de kyc
    public void checkKYC() {

        //recuperer le numero msisdn
        abOm.setMsisdn(msisdn);
        //preparer les objets a recuperer avec MTNGB
        StringHolder status = new StringHolder(""); //accountholderstatus
        StringHolder firstname = new StringHolder("");
        StringHolder lastname = new StringHolder("");
        StringHolder dob = new StringHolder("");
        StringHolder cin = new StringHolder("");
        Parametres url = parametresFacade.findByCodeParametre("REGISTER_URL_MTNGB");
        RegisterServiceLocator locator = new RegisterServiceLocator(sSLConfig);

        try {
            //lancement du process de souscription
            System.out.println("NUMERO POUR LA SOUSCRIPTION   : " + abOm.getMsisdn());

            String request = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                    + "<ns2:getaccountholderinforequest xmlns:ns2=\"http://www.ericsson.com/em/emm/provisioning/v2_1\">\n"
                    + "	<identity>" + abOm.getMsisdn() + "/MSISDN</identity>\n"
                    + "</ns2:getaccountholderinforequest>";

            System.out.println("TRAME ENVOYEE : " + request);

            HttpClientResponse response = PostXml.postXml(url.getValeur(), request);

            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(GetAccountHolderInfoResponse.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                GetAccountHolderInfoResponse info = (GetAccountHolderInfoResponse) jaxbUnmarshaller.unmarshal(new ByteArrayInputStream(response.getHttpResponseBody().getBytes()));

                System.out.println("RESULTAT ->" + info.getAccountholderbasicinfo().getFirstname() + " " + info.getAccountholderbasicinfo().getSurname() + " ");

            } catch (JAXBException ex) {
                Logger.getLogger(SouscriptionMtngb.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (Exception e) {

        } finally {

        }

    }

    /**
     * *
     * KYC TEST
     */
    public void checkKyc() {
        //recuperation du msisdn
        abOm.setMsisdn(msisdn);

        //preparer les objets a recuperer avec MTNGB
        StringHolder status = new StringHolder(""); //accountholderstatus
        StringHolder firstname = new StringHolder("");
        StringHolder lastname = new StringHolder("");
        StringHolder dob = new StringHolder("");
        StringHolder cin = new StringHolder("");

        Parametres url = parametresFacade.findByCodeParametre("KYC_URL_MTNGB");

        GetAccountHolderInfoResponse info = new GetAccountHolderInfoResponse();

        try {
            //lancement du process de souscription
            System.out.println("NUMERO POUR LA SOUSCRIPTION   : " + abOm.getMsisdn());

            String request = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                    + "<ns2:getaccountholderinforequest xmlns:ns2=\"http://www.ericsson.com/em/emm/provisioning/v2_1\">\n"
                    + "	<identity>" + abOm.getMsisdn() + "/MSISDN</identity>\n"
                    + "</ns2:getaccountholderinforequest>";

            System.out.println("TRAME ENVOYEE : " + request);

            HttpClientResponse response = PostXml.postXml(url.getValeur(), request);
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(GetAccountHolderInfoResponse.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                info = (GetAccountHolderInfoResponse) jaxbUnmarshaller.unmarshal(new ByteArrayInputStream(response.getHttpResponseBody().getBytes()));
                System.out.println("RESULTAT ->" + info.getAccountholderbasicinfo().getFirstname() + " " + info.getAccountholderbasicinfo().getSurname() + " ");
            } catch (JAXBException ex) {
                Logger.getLogger(SouscriptionMtngb.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception e) {
            status.value = "604";
            e.printStackTrace();
        } finally {
            abOm.setStatus(info.getAccountholderbasicinfo().getAccountholderstatus());
            abOm.setPrenoms(info.getAccountholderbasicinfo().getSurname());
            abOm.setNom(info.getAccountholderbasicinfo().getFirstname());

            if (info.getAccountholderbasicinfo().getAccountholderstatus().equalsIgnoreCase("ACTIVE")) {
                abOm.setCodeRetour(new BigInteger("200"));
                List<Codes> listCode = codesFacade.findDescFromCode(abOm.getCodeRetour().toString());

                if (listCode != null && !listCode.isEmpty()) {
                    libelleCodeRetour = listCode.get(0).getCodedescription();
                }

                System.out.println("le code retour est  : " + libelleCodeRetour);

                //persistence du client a souscrire afin de repondre au verififri
                AbonnementTemp abt = new AbonnementTemp();
                abt.setNomBanque(abOm.getNom());
                abt.setPrenomsBanque(abOm.getPrenoms());
                abt.setNumero(abOm.getMsisdn());
                abonnementTempFacade.create(abt);

            }
        }

        checkKycFromBankSide();

    }

    /**
     * CHECK KYC INTERNE MTNGBN
     *
     * @throws java.net.MalformedURLException
     * @throws java.net.ProtocolException
     * @throws org.json.simple.parser.ParseException
     */
    public void checkKycInterne() throws MalformedURLException, ProtocolException, IOException, ParseException {

        System.out.println("check kyc bank side....");
        checkKycFromBankSide(); //check kyc TEST 
        //checkKYCFromBankSide();
        System.out.println("fin check kyc bank side...");

        System.out.println("debut check kyc interne ...");
        System.out.println("le numero a souscrire : " + this.msisdn);
        Parametres url_dmd_kyc = parametresFacade.findByCodeParametre("KYC_URL_INTERNE_BABN");
        System.out.println("valeur de l'url  : " + url_dmd_kyc.getValeur());
        URL url = new URL(url_dmd_kyc.getValeur() + this.msisdn);
        System.out.println("--->" + url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Erreur : code erreur http  : " + conn.getResponseCode());
        }
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
        JSONObject abonne = (JSONObject) parser.parse(trame);
        JSONObject donnees = (JSONObject) abonne.get("data");
        System.out.println("nom->" + donnees.get("name") + " prenom ->" + donnees.get("given_name") + "date de naissance->" + donnees.get("birthdate"));
        //recuperation du msisdn
        abOm.setMsisdn(msisdn);
        abOm.setPrenoms((String) donnees.get("given_name"));
        abOm.setDateNaissance((String) donnees.get("birthdate"));
        abOm.setNom((String) donnees.get("name"));
        Long codeRetour = 200L;
        abOm.setCodeRetour(BigInteger.valueOf(codeRetour));
        libelleCodeRetour = "success";

        conn.disconnect();
    }

    //check kyc pour le test 
    public void checkKycFromBankSide() {
        System.out.println("DEBUT DE LA METHODE CHECK KYC FROM BANK ");
        newAbonnement = new Abonnements();
        listCpte.clear();
        listPhone.clear();
        String nom = "CHERIFAT";
        String prenom = "TEST";

        HashMap<String, String> listeDeComptes = new HashMap<>();
        listeDeComptes.put("compte courant", "14170699");
        listeDeComptes.put("compte d'epargne", "98745612301");

        Comptes comptes = new Comptes();

        for (Map.Entry m : listeDeComptes.entrySet()) {
            comptes.setNuemro(m.getValue().toString());
            comptes.setLibelle(m.getKey().toString());
        }
        listCpte.add(comptes);

        //config du numero msisdn
        listPhone.add(this.msisdn);

        newAbonnement.setNom(nom);
        newAbonnement.setPrenoms(prenom);
        newAbonnement.setRacine(customerRoot);
        newAbonnement.setCni("cx0145795derf");
        newAbonnement.setDatenaissance("08/10/1986");
        newAbonnement.setDevise(currencyLabel);

        System.out.println("les infos " + newAbonnement.getNom() + " " + newAbonnement.getPrenoms() + " " + newAbonnement.getCni());
    }

    //check kyc pour la prod
    public void checkKYCFromBankSide() {
        try {
            newAbonnement = new Abonnements();

            listCpte.clear();
            listPhone.clear();
            List<Parametres> wsdlPara = parametresFacade.findByCodeParam("OTHER_SERVICE_WSDL");
            OtherWebServiceService oss;
            String nom = "";
            String prenom = "";
            if (!wsdlPara.isEmpty()) {
                oss = new OtherWebServiceService(new URL(wsdlPara.get(0).getValeur()));
            } else {
                throw new ParameterNotFoundException("OTHER_SERVICE_WSDL");
            }

            OtherWebService otherService = oss.getOtherWebServicePort();
            Client client = otherService.getSignaletique(customerRoot);
            if (client != null) {

                nom = client.getNom().trim();
                prenom = client.getPrenom().trim();

                for (int i = 0; i < client.getComptes().size(); i++) {
                    Comptes comptes = new Comptes();
                    if (deltaAutorisation != null && deltaAutorisation.getValeur().equals("OUI")) //Concatener code agence et numéro de compte sur Amplitude
                    {
                        comptes.setNuemro(client.getComptes().get(i).getAgence() + client.getComptes().get(i).getNumero());
                    } else {
                        comptes.setNuemro(client.getComptes().get(i).getNumero());
                    }
                    comptes.setLibelle(client.getComptes().get(i).getLibNcg());
                    listCpte.add(comptes);
                }

                String[] tab = client.getTelephone().split("##");
                for (int i = 0; i < tab.length; i++) {
                    listPhone.add(tab[i].replaceAll("\\s+", "").replaceAll("^" + indicatifPays, "").replaceAll("^00" + indicatifPays, "").replaceAll("^\\+" + indicatifPays, ""));//Supprimer l'indicatif pays et les espaces
                }

                newAbonnement.setNom(nom);
                newAbonnement.setPrenoms(prenom);
                newAbonnement.setRacine(client.getRacine());
                newAbonnement.setCni(client.getCni());
                newAbonnement.setDatenaissance(client.getDateNaiss());

            }

            if (!activationKey.equals("")) {
                newAbonnement.setActivation(activationKey);
            }

//            
//            
            //   newAbonnement.setNumerotelephone(client.getTelephone());
            newAbonnement.setDevise(currencyLabel);

            // selectedComptes=new Comptes();
            // System.out.println("CLIENT: " + client.getNom());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateAccountLabel() {
        for (Comptes cpte : listCpte) {
            if (cpte.getNuemro().equals(newAbonnement.getCompte())) {
                newAbonnement.setLabel(cpte.getLibelle());
            }
        }
    }

    public void enregistrerAbonnement() {
        System.out.println("entree dans la methode enregistrement ");
        calendar = Calendar.getInstance();
        newAbonnement.setOperateur(operateur);
        newAbonnement.setUsercreate(loginManager.getUtilisateur().getLogin());
        if (abOm.getCodeRetour() != null) {
            newAbonnement.setCoderetour(abOm.getCodeRetour().toString());
        }
        newAbonnement.setDatecreation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
        newAbonnement.setDatesouscription(newAbonnement.getDatecreation());
        newAbonnement.setAgence(loginManager.getUtilisateur().getIdagences().getCodeagence());
        newAbonnement.setCompte(newAbonnement.getCompte());

        //Vérification de l'unicité de l'alias depuis la bd
        do {
            aliasGenerator(newAbonnement);
        } while (!uniqueAlias(newAbonnement.getAlias()));

        try {
            //Vérification de l'unicité du compte
            //Vérification de l'unicité du couple (compte,msisdn)
            //verification du numero msisdn : le numero msisdn doit appartenir au client
            //enregistrement de l'abonnement dns la bd + envoi de requette a MTNGB
            callWebServiceAndRegister();

            //generation du formulaire pdf
        } catch (ParameterNotFoundException ex) {
            Logger.getLogger(SouscriptionMtngb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void aliasGenerator(Abonnements ab) {
        Banque bq = banqueFacade.find(Integer.valueOf(1));
        Random rand = new Random();
        int aleatoire = rand.nextInt(100);
        //Mettre le facteur aléatoire de l'alias sur 2 positions
        String alea = aleatoire < 10 ? "0" + aleatoire : String.valueOf(aleatoire);

        if (t24Autorisation != null && t24Autorisation.getValeur().equals("OUI")) {
            newAbonnement.setAlias(operateur.getBic() + ab.getRacine() + ab.getCompte().substring(ab.getCompte().length() - 4) + alea);
        } else if (deltaAutorisation != null && deltaAutorisation.getValeur().equals("OUI")) {
            newAbonnement.setAlias(operateur.getBic() + ab.getRacine() + ab.getCompte().substring(ab.getCompte().length() - 3) + alea);
        } else {
            newAbonnement.setAlias(operateur.getBic() + "00" + ab.getRacine() + ab.getCompte().substring(ab.getCompte().length() - 6) + alea);
        }
        //L'alias de MTNGB doit être sur 10 caractères.
        if (newAbonnement.getAlias().length() < 10) {
            //Ajout d'un nombre aléatoire pour combler à droite
            //Si le nombre à une longueur inférieure à la longueur voulue, bourrer de 0 à gauche
            int diff = 10 - newAbonnement.getAlias().length();
            String ajout = String.format("%0" + diff + "d", rand.nextInt(2 ^ diff));
            newAbonnement.setAlias(newAbonnement.getAlias() + ajout);
        }
        System.out.println("ALIAS: " + newAbonnement.getAlias());
    }

    public boolean uniqueAlias(String alias) {
        return abonnementsFacade.findByAlias(alias) == null;
    }

    public void callWebServiceAndRegister() throws ParameterNotFoundException {
        System.out.println("DEBUT DE SOUSCRIPTION CHEZ MTNGB ");
        LinkfinancialresourceinformationResponse info = new LinkfinancialresourceinformationResponse();
        List<Codes> listCode;
        //StringHolder alias = new StringHolder(newAbonnement.getAlias());
        //payer la souscription c ok
        //recup de l'url de souscription
        try {
            List<Parametres> url = parametresFacade.findByCodeParam("REGISTER_URL_MTNGB");
        } catch (Exception e) {
            throw new ParameterNotFoundException("REGISTER_URL_MTNGB");
        }
        //envoi de la requette de souscription a MTNGB
        //objet de requette :
        String request = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n"
                + "<ns0:linkfinancialresourceinformationrequest xmlns:ns0=\"http://www.ericsson.com/em/emm/serviceprovider/v1_1/frontend\"> \n"
                + "   <fri>" + newAbonnement.getAlias() + "@bagb_pushpull.sp/SP</fri>\n"
                + " <accountholder>ID:" + msisdn + "/MSISDN</accountholder> \n"
                + "</ns0:linkfinancialresourceinformationrequest> ";
        System.out.println("requette a envoyee");
        System.out.println(request);
        String url = "http://127.0.0.1:9091/provisioning/linkfinancialresourceinformation";
        HttpClientResponse response = postXml(url, request);
        if (response == null) {
            System.out.println("erreur lors de lechange avec le service de MTN ");
            adMessage("INFO", "SERVICE DE SOUSCRIPTION DE MTN INDISPONIBLE");
        } else {
            System.out.println("échange avec le service de MTN Ok , code HTTP =   " + response.getHttpResponseCode());
        }
        //si code http differents de 200 erreur sinon sauvgarder l'abonnement
        if (response.getHttpResponseCode() != 200) {
            adMessage("INFO", "Erreur lors de la souscription ." + "SERVICE DE SOUSCRIPTION MTN INDISPONIBLE ...");
        } else { //code http =  200
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(LinkfinancialresourceinformationResponse.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                info = (LinkfinancialresourceinformationResponse) jaxbUnmarshaller.unmarshal(new ByteArrayInputStream(response.getHttpResponseBody().getBytes()));
                if (info.getValid()) {//reponse de type boolean renvoyee par MTN
                    System.out.println("lie avec succes chez l'operateur MTN !");
                    if (info.getValid()) {
                        codeRetour = 200;
                        newAbonnement.setCoderetour(codeRetour.toString());
                        listCode = codesFacade.findDescFromCode(codeRetour.toString());
                        if (listCode != null && !listCode.isEmpty()) {
                            libelleCodeRetour = listCode.get(0).getCodedescription();
                        }
                        //enregistrement avec le bon code ok sinon erreur de souscription
                        newAbonnement.setActif(true);
                        newAbonnement.setNumerotelephone(msisdn);
                        newAbonnement.setUservalidate(newAbonnement.getUsercreate());
                        newAbonnement.setDatevalidation(newAbonnement.getDatecreation());
                        makeServiceCodeHumanReadable();
                        abonnementsFacade.create(newAbonnement);
                        sessionManager.getLogs().setAction("CREATION D'ABONNEMENT");
                        sessionManager.getLogs().setDateLog(new Date());
                        sessionManager.getLogs().setMessage("ALIAS: " + newAbonnement.getAlias() + "MSISDN: " + msisdn);
                        sessionManager.logDB();
                        //TRACE POUR LA RECONCILIATION
                        AbonnementsReconciliations newAbonnementRec = new AbonnementsReconciliations(newAbonnement);
                        newAbonnementRec.setDatecreation(newAbonnement.getDatecreation());
                        newAbonnementRec.setDatevalidation(newAbonnement.getDatevalidation());
                        newAbonnementRec.setMotif("register");
                        newAbonnementRec.setCoderetour(newAbonnement.getCoderetour());
                        abonnementsReconciliationsFacade.create(newAbonnementRec);
                        homeManager.getListAbonnementsJour().add(newAbonnement);
                        adMessage("INFO", "Souscription réalisée avec succès.");
                    } else {
                        System.out.println("souscription echouee chez MTN  ");
                        codeRetour = 604;
                        newAbonnement.setCoderetour(codeRetour.toString());
                        listCode = codesFacade.findDescFromCode(codeRetour.toString());
                        if (listCode != null && !listCode.isEmpty()) {
                            libelleCodeRetour = listCode.get(0).getCodedescription();
                        }
                        newAbonnement.setActif(false);
                        //TRACE POUR LA RECONCILIATION
                        AbonnementsReconciliations newAbonnementRec = new AbonnementsReconciliations(newAbonnement);
                        newAbonnementRec.setDatecreation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
                        newAbonnementRec.setMotif("register");
                        newAbonnementRec.setCoderetour(newAbonnement.getCoderetour());
                        abonnementsReconciliationsFacade.create(newAbonnementRec);
                        sessionManager.getLogs().setAction("TENTATIVE D'ABONNEMENT ECHOUEE");
                        sessionManager.getLogs().setDateLog(new Date());
                        sessionManager.getLogs().setMessage("ALIAS:" + newAbonnement.getAlias() + " MSISDN:" + msisdn + " COMPTE:" + newAbonnement.getCompte());
                        sessionManager.logDB();
                        addErrorMessage("ERREUR", "La souscription a échoué " + codeRetour + ":" + libelleCodeRetour);
                    }
                } else {
                    System.out.println("erreur de liaison chez l'operateur MTN");
                }
            } catch (JAXBException ex) {
                Logger.getLogger(SouscriptionMtngb.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //methode de conso du service de demande de souscription interne avant envoi a MTNGB
    public void souscriptionRequestIntern() throws MalformedURLException, IOException, ParseException {
        // List<Codes> listCode;
        //String response = "";
//
        calendar = Calendar.getInstance();
        newAbonnement.setOperateur(operateur);
        newAbonnement.setUsercreate(loginManager.getUtilisateur().getLogin());
//
//        if (abOm.getCodeRetour() != null) {
//            newAbonnement.setCoderetour(abOm.getCodeRetour().toString());
//        }
        newAbonnement.setDatecreation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
        newAbonnement.setDatesouscription(newAbonnement.getDatecreation());
        newAbonnement.setAgence(loginManager.getUtilisateur().getIdagences().getCodeagence());
        newAbonnement.setCompte(newAbonnement.getCompte());

        //Vérification de l'unicité de l'alias depuis la bd
        do {
            aliasGenerator(newAbonnement);
        } while (!uniqueAlias(newAbonnement.getAlias()));
        newAbonnement.setAlias(newAbonnement.getCompte());

//        //verification de l'unicité du numéro de compte 
//        if (!uniqueAlias(newAbonnement.getAlias())) {
//            adMessage("ERREUR", "UN COMPTE EASYMBANK EXISTE DEJA AVEC CET ALIAS ");
//
//        } else {
//            System.out.println("debut de la methode de demande de souscription avec les parametres suivants : " + newAbonnement.getAlias() + " " + msisdn);
//
//            Parametres url_dmd_souscription = parametresFacade.findByCodeParametre("SOUSCRIPTION_REQUEST_INTERN_URL");
//
//            System.out.println("valeur de l'url  : " + url_dmd_souscription.getValeur());
//
//            URL url = new URL(url_dmd_souscription.getValeur() + abOm.getMsisdn() + "/" + newAbonnement.getAlias());
//
//            System.out.println("--->URL PARAMETREE  :  " + url);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Accept", "application/json");
//
//            if (conn.getResponseCode() != 200) {
//                // throw new RuntimeException("Erreur : code erreur http  : "+conn.getResponseCode());
//                response = "Erreur : indisponibilité du service interne de demande de souscription- code erreur : " + conn.getResponseCode();
//                //inserte dans le log de la tentative de souscription echouee du client ...
//                System.out.println("souscription echouee chez MTN  ");
//                codeRetour = 604;
//                newAbonnement.setCoderetour(codeRetour.toString());
//                listCode = codesFacade.findDescFromCode(codeRetour.toString());
//                if (listCode != null && !listCode.isEmpty()) {
//                    libelleCodeRetour = listCode.get(0).getCodedescription();
//                }
//                newAbonnement.setActif(false);
//                //TRACE POUR LA RECONCILIATION
//                AbonnementsReconciliations newAbonnementRec = new AbonnementsReconciliations(newAbonnement);
//                newAbonnementRec.setDatecreation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
//                newAbonnementRec.setMotif("register");
//                newAbonnementRec.setCoderetour(newAbonnement.getCoderetour());
//                abonnementsReconciliationsFacade.create(newAbonnementRec);
//                sessionManager.getLogs().setAction("TENTATIVE D'ABONNEMENT ECHOUEE");
//                sessionManager.getLogs().setDateLog(new Date());
//                sessionManager.getLogs().setMessage("ALIAS:" + newAbonnement.getAlias() + " MSISDN:" + msisdn + " COMPTE:" + newAbonnement.getCompte());
//                sessionManager.logDB();
//                addErrorMessage("ERREUR", "La souscription a échoué " + codeRetour + ":" + libelleCodeRetour);
//                // return response;
//            } else {
//                //recup du resultat dns un flux 
//                InputStreamReader in = new InputStreamReader(conn.getInputStream());
//                BufferedReader br = new BufferedReader(in);
//                String output = "";
//                String trame = new String();
//
//                while ((output = br.readLine()) != null) {
//                    //System.out.println("----->>>"+output);
//                    trame += output;
//                }
//
//                System.out.println("infos recuperee : " + trame + "type de l'objet recup " + trame.getClass());
//                JSONParser parser = new JSONParser();
//                JSONObject info = (JSONObject) parser.parse(trame);
//
//                System.out.println("code->" + info.get("code_reponse") + " libelle ->" + info.get("libelle_reponse"));
//                response = info.toString();
//                //inserte du nouvel abonne dans la table abonnement ...
//
//                codeRetour = 200;
//                newAbonnement.setCoderetour(codeRetour.toString());
//                listCode = codesFacade.findDescFromCode(codeRetour.toString());
//                if (listCode != null && !listCode.isEmpty()) {
//                    libelleCodeRetour = listCode.get(0).getCodedescription();
//                }
//                //enregistrement avec le bon code ok sinon erreur de souscription
//                newAbonnement.setActif(true);
//                newAbonnement.setNumerotelephone(msisdn);
//                newAbonnement.setUservalidate(newAbonnement.getUsercreate());
//                newAbonnement.setDatevalidation(newAbonnement.getDatecreation());
//                makeServiceCodeHumanReadable();
//                abonnementsFacade.create(newAbonnement);
//                sessionManager.getLogs().setAction("CREATION D'ABONNEMENT");
//                sessionManager.getLogs().setDateLog(new Date());
//                sessionManager.getLogs().setMessage("ALIAS: " + newAbonnement.getAlias() + "MSISDN: " + msisdn);
//                sessionManager.logDB();
//                //TRACE POUR LA RECONCILIATION
//                AbonnementsReconciliations newAbonnementRec = new AbonnementsReconciliations(newAbonnement);
//                newAbonnementRec.setDatecreation(newAbonnement.getDatecreation());
//                newAbonnementRec.setDatevalidation(newAbonnement.getDatevalidation());
//                newAbonnementRec.setMotif("register");
//                newAbonnementRec.setCoderetour(newAbonnement.getCoderetour());
//                abonnementsReconciliationsFacade.create(newAbonnementRec);
//                homeManager.getListAbonnementsJour().add(newAbonnement);
//                adMessage("INFO", "Souscription réalisée avec succès.");
//
//                //  return response;
//            }
//        }

        //verification de l'unicité du numéro de compte 
        if (!uniqueAlias(newAbonnement.getAlias())) {
            adMessage("ERREUR", "UN COMPTE EASYMBANK EXISTE DEJA AVEC CET ALIAS ");
        } else {
            //enregistrement avec le bon code ok sinon erreur de souscription
            newAbonnement.setActif(true);
            newAbonnement.setNumerotelephone(msisdn);
            newAbonnement.setUservalidate(newAbonnement.getUsercreate());
            newAbonnement.setDatevalidation(newAbonnement.getDatecreation());
            makeServiceCodeHumanReadable();
            abonnementsFacade.create(newAbonnement);
            sessionManager.getLogs().setAction("CREATION D'ABONNEMENT");
            sessionManager.getLogs().setDateLog(new Date());
            sessionManager.getLogs().setMessage("ALIAS: " + newAbonnement.getAlias() + "MSISDN: " + msisdn);
            sessionManager.logDB();
            //TRACE POUR LA RECONCILIATION
            AbonnementsReconciliations newAbonnementRec = new AbonnementsReconciliations(newAbonnement);
            newAbonnementRec.setDatecreation(newAbonnement.getDatecreation());
            newAbonnementRec.setDatevalidation(newAbonnement.getDatevalidation());
            newAbonnementRec.setMotif("souscription ");
            newAbonnementRec.setCoderetour(newAbonnement.getCoderetour());
            abonnementsReconciliationsFacade.create(newAbonnementRec);
            homeManager.getListAbonnementsJour().add(newAbonnement);
            adMessage("INFO", "Souscription réalisée avec succès.");
        }

    }

    public void makeServiceCodeHumanReadable() {
        switch (newAbonnement.getService()) {
            case "1":
                newAbonnement.setService("CASH IN");
                break;
            case "2":
                newAbonnement.setService("CASH OUT");
                break;
            default:
                newAbonnement.setService("CASH IN-CASH OUT");
                break;
        }
    }

    public void addErrorMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void adMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

}
