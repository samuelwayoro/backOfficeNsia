/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.jsf.controllers;

import com.sbs.easymbank.dao.AbonnementBanqueFacade;
import com.sbs.easymbank.dao.AbonnementsFacade;
import com.sbs.easymbank.dao.AbonnementsOmFacade;
import com.sbs.easymbank.dao.BanqueFacade;
import com.sbs.easymbank.dao.CodesFacade;
import com.sbs.easymbank.entities.AbonnementBanque;
import com.sbs.easymbank.entities.Abonnements;
import com.sbs.easymbank.entities.AbonnementsOm;
import com.sbs.easymbank.entities.Banque;
import com.sbs.easymbank.entities.Codes;
import com.sbs.easymbank.logic.Service;
import com.sbs.easymbank.service.omapi.RegisterPort_PortType;
import com.sbs.easymbank.service.omapi.RegisterServiceLocator;
import com.easymbank.service.other.Client;
import com.easymbank.service.other.OtherWebService;
import com.easymbank.service.other.OtherWebServiceService;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.xml.rpc.holders.StringHolder;
import at.telekom.util.axis.SSLClientAxisEngineConfig;
import com.sbs.easymbank.dao.AbonnementTempFacade;
import com.sbs.easymbank.dao.AbonnementsReconciliationsFacade;
import com.sbs.easymbank.dao.OperateursFacade;
import com.sbs.easymbank.dao.ParametresFacade;
import com.sbs.easymbank.dao.ProfilsClientsFacade;
import com.sbs.easymbank.dao.TarifsProfilsOperateursFacade;
import com.sbs.easymbank.entities.AbonnementTemp;
import com.sbs.easymbank.entities.AbonnementsReconciliations;
import com.sbs.easymbank.entities.Comptes;
import com.sbs.easymbank.entities.Operateurs;
import com.sbs.easymbank.entities.Parametres;
import com.sbs.easymbank.entities.ProfilsClients;
import com.sbs.easymbank.entities.TarifsProfilsOperateurs;
import com.easymbank.service.other.PaymentRequest;
import com.easymbank.service.other.PaymentResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import com.sbs.exceptions.*;
import org.apache.axis.EngineConfiguration;

/**
 *
 * @author SOCITECH-
 */
@ManagedBean
@ViewScoped
public class SouscriptionMTN implements Serializable {

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
    private Abonnements newAbonnement = new Abonnements();

    private List<AbonnementBanque> listAbBque = new ArrayList<>();
    //fourni normalement par l'API MoMo
    private Short codeRetour;
    private String listNumero;
    private List<Comptes> listCpte = new ArrayList<>();
    List<String> numerosComptes;
    List<Abonnements> ListAbonnementActifs;
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
    private Operateurs orange;
    List<ProfilsClients> listProfils;
    private Parametres autorisationTarificationAbonnement;
    private Parametres t24Autorisation;
    private Parametres deltaAutorisation;
    private List<String> listOp;
    private List<Abonnements> l;
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
        listOp = new ArrayList<>();
        l = new ArrayList<>();
        listCpte = new ArrayList<>();
        numerosComptes = new ArrayList<>();
        ListAbonnementActifs = new ArrayList<>();
        try {
            String[] patternSSL = new String[]{"KEYSTORE_MTN", "TRUSTSTORE_MTN"};
            String[] patternDevise = new String[]{"DEVISE"};
            List<Parametres> paraList = parametresFacade.findParametresByPatten(patternSSL);
            if (paraList != null && !paraList.isEmpty()) {
                for (Parametres para : paraList) {
                    if (para.getCodeparam().equals("CHEMIN_FICHIER_KEYSTORE_MTN")) {
                        sSLConfig.setKeystore(para.getValeur());
                    } else if (para.getCodeparam().equals("CHEMIN_FICHIER_TRUSTSTORE_MTN")) {
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
            sSLConfig.initialize(true);
            listProfils = profilsClientsFacade.findAll();
            List<Operateurs> lop = operateursFacade.findByDesignationOperateur("MTN");
            if (!lop.isEmpty()) {
                orange = lop.get(0);
            } else {
                throw new OperatorNotFoundException("MTN");
            }

            List<Parametres> t24 = parametresFacade.findByCodeParam("ACTIVATION_T24");
            if (t24 != null && !t24.isEmpty()) {
                t24Autorisation = t24.get(0);
            }

            List<Parametres> deltaPara = parametresFacade.findByCodeParam("ACTIVATION_DELTA");
            if (deltaPara != null && !deltaPara.isEmpty()) {
                deltaAutorisation = deltaPara.get(0);
            }

            if (indicatifPays == null) {
                throw new ParameterNotFoundException("INDICATIF_PAYS");
            }

            if (sSLConfig.getKeystore() == null) {
                throw new ParameterNotFoundException("CHEMIN_FICHIER_KEYSTORE_MTN");
            }
            if (sSLConfig.getKeystorepin() == null) {
                throw new ParameterNotFoundException("PASSWORD_FICHIER_TRUSTSTORE_MTN");
            }
            if (currencyCode == null) {
                throw new ParameterNotFoundException("CODE_DEVISE");
            }
            if (currencyLabel == null) {
                throw new ParameterNotFoundException("LIBELLE_DEVISE");
            }
            if (t24Autorisation == null) {
                throw new ParameterNotFoundException("ACTIVATION_T24");
            }
            if (deltaAutorisation == null) {
                throw new ParameterNotFoundException("ACTIVATION_DELTA");
            }

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

    public List<String> getNumerosComptes() {
        return numerosComptes;
    }

    public void setNumerosComptes(List<String> numerosComptes) {
        this.numerosComptes = numerosComptes;
    }

    public List<Abonnements> getListAbonnementActifs() {
        return ListAbonnementActifs;
    }

    public void setListAbonnementActifs(List<Abonnements> ListAbonnementActifs) {
        this.ListAbonnementActifs = ListAbonnementActifs;
    }

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

    public SSLClientAxisEngineConfig getsSLConfig() {
        return sSLConfig;
    }

    public void setsSLConfig(SSLClientAxisEngineConfig sSLConfig) {
        this.sSLConfig = sSLConfig;
    }

    public Short getCodeRetour() {
        return codeRetour;
    }

    public void setCodeRetour(Short codeRetour) {
        this.codeRetour = codeRetour;
    }

    public boolean isExceptionWhenContactingOM() {
        return exceptionWhenContactingOM;
    }

    public void setExceptionWhenContactingOM(boolean exceptionWhenContactingOM) {
        this.exceptionWhenContactingOM = exceptionWhenContactingOM;
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

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public Operateurs getOrange() {
        return orange;
    }

    public void setOrange(Operateurs orange) {
        this.orange = orange;
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

    public List<String> getListOp() {
        return listOp;
    }

    public void setListOp(List<String> listOp) {
        this.listOp = listOp;
    }

    public List<Abonnements> getL() {
        return l;
    }

    public void setL(List<Abonnements> l) {
        this.l = l;
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

    public String getLibelleCodeRetour() {
        return libelleCodeRetour;
    }

    public void setLibelleCodeRetour(String libelleCodeRetour) {
        this.libelleCodeRetour = libelleCodeRetour;
    }

    public AbonnementBanque getAbBanque() {
        if (listAbBque.isEmpty()) {
            return null;
        } else {
            return listAbBque.get(0);
        }
    }

    public void setAbBanque() {
        if (listAbBque.isEmpty()) {
            this.abBanque = null;
        } else {
            this.abBanque = listAbBque.get(0);
        }
    }

    public List<AbonnementBanque> getListAbBque() {
        return listAbBque;
    }

    public void setListAbBque(List<AbonnementBanque> listAbBque) {
        this.listAbBque = listAbBque;
    }

    public Abonnements getNewAbonnement() {
        return newAbonnement;
    }

    public void setNewAbonnement(Abonnements newAbonnement) {
        this.newAbonnement = newAbonnement;
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

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public Converter getAbonnementBqConverter() {
        return AbonnementBqConverter;
    }

    public void setAbonnementBqConverter(Converter AbonnementBqConverter) {
        this.AbonnementBqConverter = AbonnementBqConverter;
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

    public Converter getComptesConverter() {
        return ComptesConverter;
    }

    public void setComptesConverter(Converter ComptesConverter) {
        this.ComptesConverter = ComptesConverter;
    }

    public Comptes getSelectedComptes() {
        return selectedComptes;
    }

    public void setSelectedComptes(Comptes selectedComptes) {
        this.selectedComptes = selectedComptes;
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

    public List<ProfilsClients> getListProfils() {
        return listProfils;
    }

    public void setListProfils(List<ProfilsClients> listProfils) {
        this.listProfils = listProfils;
    }

    public Converter getProfilConverter() {
        return ProfilConverter;
    }

    public void setProfilConverter(Converter ProfilConverter) {
        this.ProfilConverter = ProfilConverter;
    }

    public void checkKYC() {

        abOm.setMsisdn(msisdn);
        abOm.setActivationkey(activationKey);
        StringHolder status = new StringHolder("");
        StringHolder fName = new StringHolder("");
        StringHolder lName = new StringHolder("");
        StringHolder dob = new StringHolder("");
        StringHolder cin = new StringHolder("");
        List<Parametres> url = parametresFacade.findByCodeParam("REGISTER_URL_MTN");
        RegisterServiceLocator locator = new RegisterServiceLocator((EngineConfiguration)sSLConfig);
        try {
            RegisterPort_PortType regpt = locator.getRegisterPort(new URL(url.get(0).getValeur()));
            regpt.KYCRequest(msisdn, activationKey, status, lName, fName, dob, cin);
            System.out.println("REQUETE KYC/ INPUT: " + msisdn + " " + activationKey + " OUTPUT: " + status.value);

        } catch (Exception ex) {
            status.value = "604";
            ex.printStackTrace();
        } finally {
            abOm.setCodeRetour(new BigInteger(status.value));
            abOm.setDateNaissance(dob.value);
            abOm.setPrenoms(fName.value);
            abOm.setNom(lName.value);
            abOm.setCni(cin.value);
            List<Codes> listCode = codesFacade.findDescFromCode(abOm.getCodeRetour().toString());
            if (listCode != null && !listCode.isEmpty()) {
                libelleCodeRetour = listCode.get(0).getCodedescription();
            }
        }

        checkKYCFromBankSide();

    }

    /**
     * SIMULATION DU KYC *
     */
    public void checkKYCTest() {
        System.out.println("check keyc test");
        //AbonnementsOm abonnementsOm = null;
        abOm.setMsisdn(msisdn);
        abOm.setActivationkey(activationKey);
        System.out.println("les infos saisies  " + abOm.getMsisdn() + "   " + abOm.getActivationkey());
        //recherche compte a partir de racine pour remplissage volet banque 
        checkKycFromBankSide();
        //remplissage volet operateur 
        abOm.setCodeRetour(new BigInteger("200"));
        abOm.setDateNaissance("08/10/1986");
        abOm.setPrenoms("Pacome");
        abOm.setNom("KOUMAN");
        abOm.setCni("cni01523698");
        List<Codes> listCode = codesFacade.findDescFromCode(abOm.getCodeRetour().toString());
        if (listCode != null && !listCode.isEmpty()) {
            libelleCodeRetour = listCode.get(0).getCodedescription();
        }
        System.out.println("fin de check kyc test");
    }

    //check kyc pour le test 
    public void checkKycFromBankSide() {

        System.out.println("DEBUT DE LA METHODE CHECK KYC FROM BANK TEST SIMULATION ");
        newAbonnement = new Abonnements();
        listCpte.clear();
        listPhone.clear();
        listOp.clear();
        l.clear();

        String nom = "KOUMAN";
        String prenom = "Pacome";

        HashMap<String, String> listeDeComptes = new HashMap<>();
        listeDeComptes.put("compte_courant", "14170699");
        Comptes c1 = new Comptes("compte_courant", "14170699");
        listeDeComptes.put("compte_depargne", "98745612301");
        Comptes c2 = new Comptes("compte_depargne", "98745612301");

        listCpte.add(c1);
        listCpte.add(c2);

        List<String> lesComptes = new ArrayList<>();

        for (Map.Entry m : listeDeComptes.entrySet()) {
            System.out.println("compte enregistree  " + m.getValue());
            lesComptes.add((String) m.getValue());
        }
        // System.out.println("taille de la liste des comptes " + lesComptes.size());
        System.out.println("lancement de la verification des abonnements du client ");

        try {
            l = abonnementsFacade.findAbonnementsActifsByMsisdn(abOm.getMsisdn());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // l = abonnementsFacade.findByRacineCompteAndMSISDN(newAbonnement.getCompte(), newAbonnement.getNumerotelephone());
//        for (int i = 0; i < lesComptes.size(); i++) {
//            try {
//                // l.add(abonnementsFacade.findByInfosRacineCompteAndMSISDN(lesComptes.get(i), abOm.getMsisdn()));
//                l = abonnementsFacade.findByRacineCompteAndMSISDN(lesComptes.get(i), abOm.getMsisdn()); //liste des abonnements actif avec le numero de t
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        System.out.println("nombre d'abonnement orange  actif trouves  " + l.size());

        if (!l.isEmpty()) {
            l.stream().map((ab) -> {
                System.out.println("- operateur trouves  " + ab.getOperateur().getDesignationOperateur());
                return ab;
            }).forEachOrdered((ab) -> {
                listOp.add(ab.getOperateur().getDesignationOperateur());
            });
        }
        if (listOp.size() > 0) {
            for (int i = 0; i < listOp.size(); i++) {
                addMessage("INFORMATION", "Ce client a deja un abonnement easymbank actif avec l'opérateur : " + listOp.get(i) + " avec le numéro " + abOm.getMsisdn() + "  et le compte  " + l.get(i).getCompte());
            }
        } else {
            System.out.println("aucun operateur trouve");
        }

        //config du numero msisdn
        listPhone.add(this.msisdn);

        newAbonnement.setNom(nom);
        newAbonnement.setPrenoms(prenom);
        newAbonnement.setRacine(customerRoot);
        newAbonnement.setCni("cni01523698");
        newAbonnement.setDatenaissance("08/10/1986");
        newAbonnement.setDevise(currencyLabel);
//        newAbonnement.setCompte(nom);
//        try {
//            System.out.println("infos pour controle  " + newAbonnement.getCompte() + "  " + abOm.getMsisdn());
//            for (Abonnements ab : l) {
//                    System.out.println("---"+ab.getCompte()+" "+ab.getNumerotelephone());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        System.out.println("les infos du clients trouve " + newAbonnement.getNom() + " " + newAbonnement.getPrenoms() + " " + newAbonnement.getCni());

    }

    public void checkKYCOptionA() {
        checkKYCFromBankSide();
    }

    /**
     * METHODE DE POUR LE TEST *
     */
    public void enregistrerAbonnementTest() {

        calendar = Calendar.getInstance();
        newAbonnement.setOperateur(orange);
        newAbonnement.setUsercreate(loginManager.getUtilisateur().getLogin());
        if (abOm.getCodeRetour() != null) {
            newAbonnement.setCoderetour(abOm.getCodeRetour().toString());//Le Code retour de KYCRequest
        }
        newAbonnement.setDatecreation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
        newAbonnement.setDatesouscription(newAbonnement.getDatecreation());
        if (!activationKey.equals("")) {
            newAbonnement.setActivation(activationKey);
        }
        newAbonnement.setAgence(loginManager.getUtilisateur().getIdagences().getCodeagence());
        // newAbonnement.setCompte(listCpte.get(0).getNuemro());
        //generation et Vérification de l'unicité de l'alias
        do {
            aliasGenerator(newAbonnement);
        } while (!uniqueAlias(newAbonnement.getAlias()));

        //afficher un msg d'erreur si le numéro donné par le client ne fait pas partir de la liste des msisdn du compte bancaire ---A DECOMENTER POUR LA MISE EN PROD
        if (!listPhone.contains(msisdn) && msisdn != null) {
            addErrorMessage("ERREUR", "Le numéro " + msisdn + " n'appartient pas au client ");
            return;
        }

        System.out.println("numero de compte selectionne   " + newAbonnement.getCompte());

        ListAbonnementActifs = abonnementsFacade.findAbonnementsActifsByMsisdn(newAbonnement); //renvoi une liste de compte d'abonnements a partir du msisdn entrer 

        System.out.println("taille de la liste " + ListAbonnementActifs.size());

        //si liste vide : premiere souscription wizall : on continue
        if ((ListAbonnementActifs.isEmpty()) && (ListAbonnementActifs.size() == 0)) {//si il y a pas d'abonnement avec le numero de telephone saisie 

            System.out.println("liste  vide , premiere soucription de ce client dans easymbank ");
            System.out.println("-PREMIERE SOUSCRIPTION EASYMBANK POUR CE CLIENT AVEC L'OPERATEUR orange  -");
            //callWebServiceAndRegister();
            callWebServiceAndRegisterTest();
            newAbonnement = new Abonnements();
            abOm = new AbonnementsOm();
            listAbBque.clear();
            libelleCodeRetour = "";

        } else { //sinon si liste ayant au moins une ligne 

            System.out.println("liste remplie car existance dabonnement actif avec ce numero de telephone  ");

            for (Abonnements ab : ListAbonnementActifs) {
                System.out.println("  " + ab.getNumerotelephone() + "  " + ab.getCompte() + "  operateur " + ab.getOperateur().getDesignationOperateur());
            }
            
             //si le numero de compte saisi est deja utiliser
                List<Abonnements> abn = abonnementsFacade.findAbonnementByCompteAndMsisdn(newAbonnement.getCompte(), msisdn, orange);

                System.out.println("taille de la liste des abonnes trouve  " + abn.size());

                if (abn.size() > 0) {

                    System.out.println("ce numero de compte est deja lie a ce numero de telephone ... abonnement interdit ");
                    addErrorMessage("ERREUR", "ABONNEMENT DEJA EXISTANT AVEC CE NUMERO DE COMPTE ET CE NUMERO DE TELEPHONE");

                } else {
                    
                    System.out.println(" ce numero de compte n'est pas encore utilise sur ce numero de telephone pour une abonnement Orange.... abonnement permis    ");
                    callWebServiceAndRegisterTest();
                    newAbonnement = new Abonnements();
                    abOm = new AbonnementsOm();
                    listAbBque.clear();
                    libelleCodeRetour = "";

                }
        }

    }

    public void callWebServiceAndRegisterTest() {
        System.out.println("debut de souscription simulation ****");
        List<Codes> listCode;
        String codeResponse = null;
        //String alias = newAbonnement.getAlias();
        try {

            newAbonnement.setCoderetour("200");
            listCode = codesFacade.findDescFromCode("200");
            for (Codes codes : listCode) {
                System.out.println("id code " + codes.getIdcodes() + " code description " + codes.getCodedescription() + "  codevalue" + codes.getCodevalue());
            }

            if (listCode != null && !listCode.isEmpty()) {
                libelleCodeRetour = listCode.get(0).getCodedescription();
                codeResponse = listCode.get(0).getCodevalue();
                System.out.println("valeur du libelle et du code " + libelleCodeRetour + "    200");
            }

            System.out.println("lancement de la souscription test de MTN test ----- valeur de coderesponse  " + codeResponse);
            if (codeResponse.equalsIgnoreCase("200")) {
                System.out.println("SOUSCRIPTION REUSSIE , SAUVEGARDE DANS LA BD DE EASYMBANK ");
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
                addMessage("INFO", "Souscription réalisée avec succès.");
                PDF();
                //addErrorMessage("INFORMATION", "Attention : Cet abonné à déjà un compte Easymbank avec l'opérateur  " + listOp.get(0) + " ");

                System.out.println("fin de la souscription...");
            } else {
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
            }
        } catch (Exception e) {
            e.printStackTrace();
//            codeRetour = 604;
//            listCode = codesFacade.findDescFromCode(Long.toString(codeRetour));
//            if (listCode != null && !listCode.isEmpty()) {
//                libelleCodeRetour = listCode.get(0).getCodedescription();
//            }
//            addErrorMessage("ERREUR", "La souscription a échoué " + codeRetour + ":" + libelleCodeRetour);
//            System.out.println("erreur au niveau du test , revoir le code ");
        }

    }

    /**
     * METHODE POUR LA PRODUCTION *
     */
//    public void enregistrerAbonnement() {
//        calendar = Calendar.getInstance();
//        newAbonnement.setOperateur(orange);
//        newAbonnement.setUsercreate(loginManager.getUtilisateur().getLogin());
////        newAbonnement.setOperateur(orange);
//        if (abOm.getCodeRetour() != null) {
//            newAbonnement.setCoderetour(abOm.getCodeRetour().toString());//Le Code retour de KYCRequest
//        }
//        newAbonnement.setDatecreation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
//        newAbonnement.setDatesouscription(newAbonnement.getDatecreation());
//        if (!activationKey.equals("")) {
//            newAbonnement.setActivation(activationKey);
//        }
//        newAbonnement.setAgence(loginManager.getUtilisateur().getIdagences().getCodeagence());
//        newAbonnement.setCompte(newAbonnement.getCompte());
////        for (Comptes cpte : listCpte) {
////            if (cpte.getNuemro().equals(newAbonnement.getCompte())) {
////                newAbonnement.setLabel(cpte.getLibelle());
////            }
////        }
//        //Vérification de l'unicité de l'alias
//        do {
//            aliasGenerator(newAbonnement);
//        } while (!uniqueAlias(newAbonnement.getAlias()));
//
//        //Vérification de l'unicité du compte
//        String unique_acount_alias = parametresFacade.findByACodeParam("UNIQUE_ACCOUNT_ALIAS");
//        if (unique_acount_alias != null && !unique_acount_alias.equals("TRUE")) {
//            List<Abonnements> ListAbonnementWithSameAccount = abonnementsFacade.findAbonnementByNumeroCompte(newAbonnement.getCompte());
//            if (ListAbonnementWithSameAccount != null && !ListAbonnementWithSameAccount.isEmpty()) {
//                addErrorMessage("ERREUR", "Le compte " + newAbonnement.getCompte() + " est dejà lié ");
//                return;
//            }
//
//        }
//
//        //Vérification de l'unicité du couple (compte,msisdn)
//        List<Abonnements> l = abonnementsFacade.findByRacineCompteAndMSISDN(newAbonnement.getCompte(), newAbonnement.getNumerotelephone());
//        Abonnements abont = new Abonnements();
//        if (l != null) {
//            if (!l.isEmpty()) {
//                abont = l.get(0);
//            }
//        }
//
//        //  codeRetour = 200;/****A COMMENTER****/
//        if (!newAbonnement.getNumerotelephone().equals(msisdn) && msisdn != null) {
//            addErrorMessage("ERREUR", "Le numéro " + msisdn + " n'appartient pas au client ");
//            return;
//        }
//
//        if (abont.getActif() != null) {
//            if (abont.getActif()) {//L'abonnement a déja été fait.
//                addErrorMessage("ERREUR", "Ce compte est deja lié  ce numéro ");
//            } else {
//                callWebServiceAndRegister();
//                if (parametrageManager.getSupervalidation().getValeur().equals("SUPERVALIDATION_NON") && newAbonnement.getActif()) {
//                    try {
//                        PDF();
//                    } catch (JRException | IOException e) {
//                        e.printStackTrace();
//                        addErrorMessage("ERREUR", "Une erreur s'est produite lors de l'impression du formulaire de souscription");
//                    }
//                }
//
//            }
//        } else {
//            callWebServiceAndRegister();
//            if (parametrageManager.getSupervalidation().getValeur().equals("SUPERVALIDATION_NON") && newAbonnement.getActif()) {
//                try {
//                    PDF();
//                } catch (JRException | IOException e) {
//                    e.printStackTrace();
//                    addErrorMessage("ERREUR", "Une erreur s'est produite lors de l'impression du formulaire de souscription");
//                }
//            }
//        }
//        newAbonnement = new Abonnements();
//        abOm = new AbonnementsOm();
//        listAbBque.clear();
//        libelleCodeRetour = "";
//
//    }
    
     //enregistrement du nouvel abonnee
    public void enregistrerAbonnement() {

        calendar = Calendar.getInstance();
        newAbonnement.setOperateur(orange);
        newAbonnement.setUsercreate(loginManager.getUtilisateur().getLogin());
//        newAbonnement.setOperateur(orange);
        if (abOm.getCodeRetour() != null) {
            newAbonnement.setCoderetour(abOm.getCodeRetour().toString());//Le Code retour de KYCRequest
        }
        newAbonnement.setDatecreation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
        newAbonnement.setDatesouscription(newAbonnement.getDatecreation());
        if (!activationKey.equals("")) {
            newAbonnement.setActivation(activationKey);
        }
        newAbonnement.setAgence(loginManager.getUtilisateur().getIdagences().getCodeagence());
        newAbonnement.setCompte(newAbonnement.getCompte());
//        for (Comptes cpte : listCpte) {
//            if (cpte.getNuemro().equals(newAbonnement.getCompte())) {
//                newAbonnement.setLabel(cpte.getLibelle());
//            }
//        }
        //Vérification de l'unicité de l'alias depuis la bd
        do {
            aliasGenerator(newAbonnement);
        } while (!uniqueAlias(newAbonnement.getAlias()));


//INTEGRATION
        System.out.println("numero de compte selectionne   " + newAbonnement.getCompte());

        ListAbonnementActifs = abonnementsFacade.findAbonnementsActifsByMsisdn(newAbonnement); //renvoi une liste de compte d'abonnements a partir du msisdn entrer 

        System.out.println("taille de la liste " + ListAbonnementActifs.size());

        //si liste vide : premiere souscription wizall : on continue
        if ((ListAbonnementActifs.isEmpty()) && (ListAbonnementActifs.size() == 0)) {//si il y a pas d'abonnement avec le numero de telephone saisie 

            System.out.println("liste  vide , premiere soucription de ce client dans easymbank ");
            System.out.println("-PREMIERE SOUSCRIPTION EASYMBANK POUR CE CLIENT AVEC L'OPERATEUR orange  -");
            callWebServiceAndRegister();
            //callWebServiceAndRegisterTest();
            if (parametrageManager.getSupervalidation().getValeur().equals("SUPERVALIDATION_NON") && newAbonnement.getActif()) {
                try {
                    PDF();
                } catch (JRException | IOException e) {
                    e.printStackTrace();
                    addErrorMessage("ERREUR", "Une erreur s'est produite lors de l'impression du formulaire de souscription");
                }
            }
            newAbonnement = new Abonnements();
            abOm = new AbonnementsOm();
            listAbBque.clear();
            libelleCodeRetour = "";

        } else { //sinon si liste ayant au moins une ligne 

            System.out.println("liste remplie car existance dabonnement actif avec ce numero de telephone  ");

            for (Abonnements ab : ListAbonnementActifs) {
                System.out.println("  " + ab.getNumerotelephone() + "  " + ab.getCompte() + "  operateur " + ab.getOperateur().getDesignationOperateur());
            }

            //si le numero de compte saisi est deja utiliser
            List<Abonnements> abn = abonnementsFacade.findAbonnementByCompteAndMsisdn(newAbonnement.getCompte(), msisdn, orange);

            System.out.println("taille de la liste des abonnes trouve  " + abn.size());

            if (abn.size() > 0) {

                System.out.println("ce numero de compte est deja lie a ce numero de telephone ... abonnement interdit ");
                addErrorMessage("ERREUR", "ABONNEMENT DEJA EXISTANT AVEC CE NUMERO DE COMPTE ET CE NUMERO DE TELEPHONE");
                return;

            } else {

                System.out.println(" ce numero de compte n'est pas encore utilise sur ce numero de telephone pour une abonnement Orange.... abonnement permis    ");
                // callWebServiceAndRegisterTest();
                callWebServiceAndRegister();
                if (parametrageManager.getSupervalidation().getValeur().equals("SUPERVALIDATION_NON") && newAbonnement.getActif()) {
                    try {
                        PDF();
                    } catch (JRException | IOException e) {
                        e.printStackTrace();
                        addErrorMessage("ERREUR", "Une erreur s'est produite lors de l'impression du formulaire de souscription");
                    }
                }
                newAbonnement = new Abonnements();
                abOm = new AbonnementsOm();
                listAbBque.clear();
                libelleCodeRetour = "";

            }

        }
    }

    public void updateDeviseSelectedAbonnement() {
        for (AbonnementBanque ab : listAbBque) {
            if (ab.getCompte().toString().equals(newAbonnement.getCompte())) {
                newAbonnement.setDevise(ab.getDevise());
            }
        }
    }

    public void aliasGenerator(Abonnements ab) {
        Banque bq = banqueFacade.find(Integer.valueOf(1));
        Random rand = new Random();
        int aleatoire = rand.nextInt(100);
        //Mettre le facteur aléatoire de l'alias sur 2 positions
        String alea = aleatoire < 10 ? "0" + aleatoire : String.valueOf(aleatoire);

        if (t24Autorisation != null && t24Autorisation.getValeur().equals("OUI")) {
            newAbonnement.setAlias(orange.getBic() + ab.getRacine() + ab.getCompte().substring(ab.getCompte().length() - 4) + alea);
        } else if (deltaAutorisation != null && deltaAutorisation.getValeur().equals("OUI")) {
            newAbonnement.setAlias(orange.getBic() + ab.getRacine() + ab.getCompte().substring(ab.getCompte().length() - 3) + alea);
        } else {
            newAbonnement.setAlias(orange.getBic() + "00" + ab.getRacine() + ab.getCompte().substring(ab.getCompte().length() - 4) + alea);
        }
        //L'alias doit être sur 22 caractères.
        if (newAbonnement.getAlias().length() < 22) {
            //Ajout d'un nombre aléatoire pour combler à droite
            //Si le nombre à une longueur inférieure à la longueur voulue, bourrer de 0 à gauche
            int diff = 22 - newAbonnement.getAlias().length();
            String ajout = String.format("%0" + diff + "d", rand.nextInt(10 ^ diff));
            newAbonnement.setAlias(newAbonnement.getAlias() + ajout);
        }
        System.out.println("ALIAS: " + newAbonnement.getAlias());

    }

    public void addErrorMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void adMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void callWebServiceAndRegister() {

        if (parametrageManager.getSupervalidation().getValeur().equals("SUPERVALIDATION_OUI")) {
            /**
             * ***A COMMENTER*****
             */

            makeServiceCodeHumanReadable();
            newAbonnement.setActif(false);
            newAbonnement.setResilie(false);
            abonnementsFacade.create(newAbonnement);
            saveInTempTable();
            homeManager.getListAbonnementsJour().add(newAbonnement);
            adMessage("INFO", "Souscription créé avec succès. Veuillez procéder à  la supervalidation afin d'activer les services");

        } else {
            List<Codes> listCode;
            StringHolder alias = new StringHolder(newAbonnement.getAlias());
            try {
                if (parametrageManager.isRegisterTarifAllowed()) {
                    payForRegistrationService();
                }
                List<Parametres> url = parametresFacade.findByCodeParam("REGISTER_URL_MTN");
                RegisterServiceLocator locator = new RegisterServiceLocator(sSLConfig);
                RegisterPort_PortType regpt;
                if (!url.isEmpty()) {
                    regpt = locator.getRegisterPort(new URL(url.get(0).getValeur()));
                } else {
                    throw new ParameterNotFoundException("REGISTER_URL_MTN");
                }

                codeRetour = regpt.ombRequest(msisdn, alias, Short.valueOf(newAbonnement.getService()), newAbonnement.getLabel(), currencyCode, newAbonnement.getActivation(), calendar);
                System.out.println("INPUT:{msisdn:" + msisdn + ",alias:" + alias.value + ",activationKey:" + newAbonnement.getActivation() + ",serviceCode:" + newAbonnement.getService() + "} RETOUR OMBREQUEST: " + codeRetour);
                newAbonnement.setCoderetour(codeRetour.toString());
                listCode = codesFacade.findDescFromCode(codeRetour.toString());
                if (listCode != null && !listCode.isEmpty()) {
                    libelleCodeRetour = listCode.get(0).getCodedescription();
                }
                if (codeRetour == 200) {
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
                    // PDF();
                } else {
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
    
     public void checkKYCFromBankSide() {
        try {
            newAbonnement = new Abonnements();
            listCpte.clear();
            listPhone.clear();
            l.clear();

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
            //   newAbonnement.setNumerotelephone(client.getTelephone());
            newAbonnement.setDevise(currencyLabel);

            //integration
            List<String> lesComptes = new ArrayList<>();

            for (Comptes comptes : listCpte) {
                System.out.println("les comptes enregistres   " + comptes.getNuemro());
                lesComptes.add(comptes.getNuemro());
            }

            // System.out.println("taille de la liste des comptes " + lesComptes.size());
            System.out.println("lancement de la verification des abonnements du client ");

            try {
                l = abonnementsFacade.findAbonnementsActifsByMsisdn(abOm.getMsisdn());
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("nombre d'abonnement orange  actif trouves  " + l.size());

            if (!l.isEmpty()) {
                l.stream().map((ab) -> {
                    System.out.println("- operateur trouves  " + ab.getOperateur().getDesignationOperateur());
                    return ab;
                }).forEachOrdered((ab) -> {
                    listOp.add(ab.getOperateur().getDesignationOperateur());
                });
            }
            if (listOp.size() > 0) {
                for (int i = 0; i < listOp.size(); i++) {
                    addMessage("INFORMATION", "Ce client a deja un abonnement easymbank actif avec l'opérateur : " + listOp.get(i) + " avec le numéro " + abOm.getMsisdn() + "  et le compte  " + l.get(i).getCompte());
                }
            } else {
                System.out.println("aucun operateur trouve");
            }

            //config du numero msisdn
            listPhone.add(this.msisdn);

            System.out.println("les infos du clients trouve " + newAbonnement.getNom() + " " + newAbonnement.getPrenoms() + " " + newAbonnement.getCni());

            // selectedComptes=new Comptes();
            // System.out.println("CLIENT: " + client.getNom());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void checkKYCFromBankSide() {
//        try {
//            newAbonnement = new Abonnements();
//
//            listCpte.clear();
//            listPhone.clear();
//            List<Parametres> wsdlPara = parametresFacade.findByCodeParam("OTHER_SERVICE_WSDL");
//            OtherWebServiceService oss;
//            String nom = "";
//            String prenom = "";
//            if (!wsdlPara.isEmpty()) {
//                oss = new OtherWebServiceService(new URL(wsdlPara.get(0).getValeur()));
//            } else {
//                throw new ParameterNotFoundException("OTHER_SERVICE_WSDL");
//            }
//
//            OtherWebService otherService = oss.getOtherWebServicePort();
//            Client client = otherService.getSignaletique(customerRoot);
//            if (client != null) {
//
//                nom = client.getNom().trim();
//                prenom = client.getPrenom().trim();
//
//                for (int i = 0; i < client.getComptes().size(); i++) {
//                    Comptes comptes = new Comptes();
//                    if (deltaAutorisation != null && deltaAutorisation.getValeur().equals("OUI")) //Concatener code agence et numéro de compte sur Amplitude
//                    {
//                        comptes.setNuemro(client.getComptes().get(i).getAgence() + client.getComptes().get(i).getNumero());
//                    } else {
//                        comptes.setNuemro(client.getComptes().get(i).getNumero());
//                    }
//                    comptes.setLibelle(client.getComptes().get(i).getLibNcg());
//                    listCpte.add(comptes);
//                }
//
//                String[] tab = client.getTelephone().split("##");
//                for (int i = 0; i < tab.length; i++) {
//                    listPhone.add(tab[i].replaceAll("\\s+", "").replaceAll("^" + indicatifPays, "").replaceAll("^00" + indicatifPays, "").replaceAll("^\\+" + indicatifPays, ""));//Supprimer l'indicatif pays et les espaces
//                }
//
//                newAbonnement.setNom(nom);
//                newAbonnement.setPrenoms(prenom);
//                newAbonnement.setRacine(client.getRacine());
//                newAbonnement.setCni(client.getCni());
//                newAbonnement.setDatenaissance(client.getDateNaiss());
//
//            }
//
//            if (!activationKey.equals("")) {
//                newAbonnement.setActivation(activationKey);
//            }
//
////            
////            
//            //   newAbonnement.setNumerotelephone(client.getTelephone());
//            newAbonnement.setDevise(currencyLabel);
//
//            // selectedComptes=new Comptes();
//            // System.out.println("CLIENT: " + client.getNom());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    //utilisé pour l'option A pour metrre à jour le numéro de téléphone
    public void editMSISDN(String number) {
        msisdn = number;
    }

    public boolean uniqueAlias(String alias) {
        return abonnementsFacade.findByAlias(alias) == null;
    }

    public void saveInTempTable() {
        AbonnementTemp abonnementTemp = new AbonnementTemp(newAbonnement, abOm);

        abonnementTempFacade.create(abonnementTemp);
    }

    public void initJasper() throws JRException, FileNotFoundException {
        List<Abonnements> listAbonnements = abonnementsFacade.findAbonnementActifOfCustomerByNumber(newAbonnement);
        System.out.println("TOTAL ABONNEMENTS: " + listAbonnements.size());
        if (listAbonnements.isEmpty()) {
            return;
        }
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listAbonnements);
        // String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("ficheSouscription.jasper");
        List<Parametres> list = parametresFacade.findByCodeParam("FICHE_SOUSCRIPTION_MTN");
        String reportPath = "";
        if (!list.isEmpty()) {
            reportPath = list.get(0).getValeur();
        } else {
            throw new FileNotFoundException("fichier " + reportPath + " introuvable");
        }
        Map parametres = new HashMap();
        parametres.put("NOM", newAbonnement.getNom());
        parametres.put("PRENOMS", newAbonnement.getPrenoms());
        // parametres.put("ADRESSE",newAbonnement.get());
        parametres.put("DATE_NAISSANCE", newAbonnement.getDatenaissance());
        parametres.put("CNI", newAbonnement.getCni());
        parametres.put("NUMERO", newAbonnement.getNumerotelephone());
        parametres.put("COMPTE", newAbonnement.getCompte());
        parametres.put("TYPECOMPTE", newAbonnement.getTypecompte());
        jasperPrint = JasperFillManager.fillReport(reportPath, parametres, beanCollectionDataSource);
    }

    public void PDF() throws JRException, IOException {
        initJasper();
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + newAbonnement.getAlias() + ".pdf");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
        FacesContext.getCurrentInstance().responseComplete();

    }

    public void updateAccountLabel() {
        for (Comptes cpte : listCpte) {
            if (cpte.getNuemro().equals(newAbonnement.getCompte())) {
                newAbonnement.setLabel(cpte.getLibelle());
            }
        }
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
        List<TarifsProfilsOperateurs> l = tarifsProfilsOperateursFacade.findByOperatorAndProfilAndService(newAbonnement.getOperateur().getIdOperateur().toString(), newAbonnement.getProfil().getIdProfils().toString(), "ABONNEMENT");
        if (!l.isEmpty()) {
            tarifsProfilsOperateurs = l.get(0);
        } else {
            throw new FeesNotDefinedException(newAbonnement.getOperateur().getDesignationOperateur(), newAbonnement.getProfil().getDesignationProfils(), "ABONNEMENT");
        }
        if (tarifsProfilsOperateurs.getTarif() != 0) {
            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setAlias(newAbonnement.getAlias());
            paymentRequest.setCommissions(orange.getIdOperateur().longValue());  /// operateur à la place de commissions
            paymentRequest.setCompte(newAbonnement.getCompte());
            paymentRequest.setMontant(tarifsProfilsOperateurs.getTarif());
            paymentRequest.setDatePaiment(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
            PaymentResponse paymentResponse = otherService.payForService(paymentRequest);
            if (paymentResponse.getStatut().equals("1")) {
                throw new PaymentException(paymentResponse.getStatutMsg());
            }

        }
        newAbonnement.setCommissions(tarifsProfilsOperateurs.getTarif());
    }

}
