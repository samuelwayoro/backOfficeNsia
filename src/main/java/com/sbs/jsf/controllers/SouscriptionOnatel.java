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
import com.sbs.easymbank.entities.Codes;
import com.sbs.easymbank.logic.Service;
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
import com.ibayad.bank.atlantique.registration.api.BankAccountRegistrationService;
import com.ibayad.bank.atlantique.registration.api.BankAccountRegistrationServiceService;
import com.ibayad.bank.atlantique.registration.api.BankAccountRegistration;
import com.ibayad.bank.atlantique.registration.api.BankAccountRegistrationReponse;
import com.ibayad.bank.atlantique.registration.api.ObjectFactory;
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
import com.stdbankinit.server.MCOB2ASoap;
import com.stdbankinit.server.McoAtlanticBankServerSoapImplService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.ws.BindingProvider;
import org.apache.commons.codec.digest.DigestUtils;
import org.tempuri.RetKYCRequest;
import org.w3c.dom.Element;

/**
 *
 * @author samuel
 */
@ManagedBean
@ViewScoped
public class SouscriptionOnatel implements Serializable {

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
    private AbonnementBanque abBanque = new AbonnementBanque();
    private Abonnements newAbonnement = new Abonnements();

    private List<AbonnementBanque> listAbBque = new ArrayList<>();
    private int codeRetour;
    private String listNumero;
    private List<Comptes> listCpte = new ArrayList<>();
    private List<String> listPhone = new ArrayList<>();
    private boolean exceptionWhenContactingOM;
    private String libelleCodeRetour;
    String currencyLabel;
    String currencyCode;
    @ManagedProperty(value = "#{loginManager}")
    private LoginManager loginManager;
    @ManagedProperty(value = "#{parametrageOnatelManager}")
    private ParametrageOnatelManager parametrageManager;
    @ManagedProperty(value = "#{homeManager}")
    private HomeManager homeManager;
    @ManagedProperty(value = "#{sessionManager}")
    private SessionManager sessionManager;
    private Comptes selectedComptes = new Comptes();
    private Calendar calendar = Calendar.getInstance();
    private Operateurs onatel;
    private String bankName;
    private String branchCode;
    private String countryCode;
    String indicatifPays;

    List<ProfilsClients> listProfils;
    private Parametres autorisationTarificationAbonnement;
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
    private OperateursFacade ONATELsFacade;
    @EJB
    private ProfilsClientsFacade profilsClientsFacade;
    @EJB
    private TarifsProfilsOperateursFacade tarifsProfilsOperateursFacade;

    @PostConstruct
    public void init() {
        try {
            String[] patternBank = new String[]{"BANKNAME", "BRANCHCODE", "COUNTRYCODE"};
            String[] patternDevise = new String[]{"DEVISE"};
//            List<Parametres> paraList = parametresFacade.findParametresByPatten(patternSSL);
//            if (paraList != null && !paraList.isEmpty()) {
//                for (Parametres para : paraList) {
//                    if (para.getCodeparam().equals("CHEMIN_FICHIER_KEYSTORE")) {
//                        sSLConfig.setKeystore(para.getValeur());
//                    } else if (para.getCodeparam().equals("CHEMIN_FICHIER_TRUSTSTORE")) {
//                        sSLConfig.setTruststore(para.getValeur());
//                    } else {
//                        sSLConfig.setKeystorePassword(para.getValeur());
//                    }
//                }
//            }
            List<Parametres> paraList = parametresFacade.findParametresByPatten(patternDevise);
            if (paraList != null && !paraList.isEmpty()) {
                for (Parametres para : paraList) {
                    if (para.getCodeparam().equals("CODE_DEVISE")) {
                        currencyCode = para.getValeur();
                    } else {
                        currencyLabel = para.getValeur();
                    }
                }
            }
            if (currencyCode == null) {
                throw new ParameterNotFoundException("CODE_DEVISE");
            }
            if (currencyLabel == null) {
                throw new ParameterNotFoundException("LIBELLE_DEVISE");
            }

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
            if (bankName == null) {
                throw new ParameterNotFoundException("BANKNAME");
            }
            if (branchCode == null) {
                throw new ParameterNotFoundException("BRANCHCODE");
            }
            if (countryCode == null) {
                throw new ParameterNotFoundException("COUNTRYCODE");
            }

            listProfils = profilsClientsFacade.findAll();
            List<Operateurs> lop = ONATELsFacade.findByDesignationOperateur("ONATEL");
            if (!lop.isEmpty()) {
                onatel = lop.get(0);
            } else {
                throw new OperatorNotFoundException("ONATEL");
            }
            List<Parametres> para_indicatif = parametresFacade.findByCodeParam("INDICATIF_PAYS");
            if (para_indicatif != null && !para_indicatif.isEmpty()) {
                indicatifPays = para_indicatif.get(0).getValeur();
            }
            if (indicatifPays == null) {
                throw new ParameterNotFoundException("INDICATIF_PAYS");
            }

        } catch (Exception ex) {
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

    public int getCodeRetour() {
        return codeRetour;
    }

    public void setCodeRetour(int codeRetour) {
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

    public ParametrageOnatelManager getParametrageManager() {
        return parametrageManager;
    }

    public void setParametrageManager(ParametrageOnatelManager parametrageManager) {
        this.parametrageManager = parametrageManager;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public Operateurs getAirtel() {
        return onatel;
    }

    public void setAirtel(Operateurs onatel) {
        this.onatel = onatel;
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

    public Parametres getAutorisationTarificationAbonnement() {
        return autorisationTarificationAbonnement;
    }

    public void setAutorisationTarificationAbonnement(Parametres autorisationTarificationAbonnement) {
        this.autorisationTarificationAbonnement = autorisationTarificationAbonnement;
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
        return ONATELsFacade;
    }

    public void setOperateursFacade(OperateursFacade ONATELsFacade) {
        this.ONATELsFacade = ONATELsFacade;
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
//
        abOm.setMsisdn(msisdn);
        abOm.setActivationkey(activationKey);
        RetKYCRequest rkr = new RetKYCRequest();
        try {
            List<Parametres> url = parametresFacade.findByCodeParam("REGISTER_URL_ONATEL");
            McoAtlanticBankServerSoapImplService mcoBankServerSoapImplService;

            if (!url.isEmpty()) {

                mcoBankServerSoapImplService = new McoAtlanticBankServerSoapImplService(new URL(url.get(0).getValeur()));

            } else {
                throw new ParameterNotFoundException("REGISTER_URL_ONATEL");
            }
            MCOB2ASoap mbss = mcoBankServerSoapImplService.getMcoAtlanticBankServerSoapImplPort();
            rkr = mbss.kycRequest(bankName, branchCode, msisdn, DigestUtils.sha256Hex(msisdn + loginManager.getLogin() + calendar.getTimeInMillis()), Long.toString(calendar.getTimeInMillis()), "", "");
        } catch (Exception ex) {
//            status.value = "604";

        } finally {
            abOm.setCodeRetour(new BigInteger(rkr.getStatus().toString()));
            abOm.setDateNaissance(rkr.getDateOfBirth());
            abOm.setPrenoms(rkr.getFirstName());
            abOm.setNom(rkr.getLastName());
            abOm.setNationalite(rkr.getNationality());
            abOm.setGenre(rkr.getGender());
            abOm.setMsisdn(rkr.getAuthorizedMobileNumber());
            libelleCodeRetour = rkr.getDescription();
//            List<Codes> listCode = codesFacade.findDescFromCode(abOm.getCodeRetour().toString());
//            if (listCode != null && !listCode.isEmpty()) {
//                libelleCodeRetour = listCode.get(0).getCodedescription();
//            }
        }
//
        checkKYCFromBankSide();
//
    }

    public void checkKYCOptionA() {
        System.out.println("la racine entree " + customerRoot);
        checkKYCFromBankSide();
    }

    public void enregistrerAbonnement() {

        System.out.println("****debut de la validation de la souscription***");

        calendar = Calendar.getInstance();
        // newAbonnement.setOperateur(onatel);
        newAbonnement.setUsercreate(loginManager.getUtilisateur().getLogin());
        newAbonnement.setOperateur(onatel);
        newAbonnement.setService("3");
        newAbonnement.setGenre(abOm.getGenre());
        newAbonnement.setNationalite(abOm.getNationalite());
        newAbonnement.setPays(abOm.getPays());
        newAbonnement.setRegion(abOm.getRegion());
        newAbonnement.setVille(abOm.getVille());

        if (abOm.getCodeRetour() != null) {
            newAbonnement.setCoderetour(abOm.getCodeRetour().toString());//Le Code retour de KYCRequest bancaire 
        }
        newAbonnement.setDatecreation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
        newAbonnement.setDatesouscription(newAbonnement.getDatecreation());

        newAbonnement.setAgence(loginManager.getUtilisateur().getIdagences().getCodeagence());

        //Vérification de l'unicité de l'alias
        do {
            aliasGenerator(newAbonnement);
        } while (!uniqueAlias(newAbonnement.getAlias()));

//        String unique_acount_alias = parametresFacade.findByACodeParam("UNIQUE_ACCOUNT_ALIAS");
//        if (unique_acount_alias != null && unique_acount_alias.equals("TRUE")) {
//            System.out.println("verification de l'unicite de l'alias client ....");
//            List<Abonnements> ListAbonnementWithSameAccount = abonnementsFacade.findAbonnementByNumeroCompte(newAbonnement.getCompte());
//            if (ListAbonnementWithSameAccount != null && !ListAbonnementWithSameAccount.isEmpty()) {
//                System.out.println("ce client est deja abonne");
//                addErrorMessage("ERREUR", "Le compte " + newAbonnement.getCompte() + " est dejà lié ");
//                return;
//            }else{
//                System.out.println("ce client n'est pas deja abonne , nous pouvons le faire ...");
//            }
//        }
        //Vérification de l'unicité du couple (compte,msisdn)
        System.out.println("compte selectionne  " + newAbonnement.getCompte() + "    numero msisdn  choisi " + newAbonnement.getNumerotelephone());

        List<Abonnements> l = abonnementsFacade.findByRacineCompteAndMSISDN(newAbonnement.getCompte(), newAbonnement.getNumerotelephone());
        System.out.println("abonnee trouvee    " + l.size());
        Abonnements abont = new Abonnements();
        if (l.size() > 0) {
            System.out.println("il y a un abonne en base avec ce numero de compte et ce numero de telephone ...");
            addErrorMessage("ERREUR", "un compte est deja lié  ce numéro ");
        } else {
            System.out.println("ce client n'est pas encore abonne , nous pouvons l'abonne ");
            callWebServiceAndRegister();

        }

//        if (abont.getActif() != null && abont.getActif()) {
//            addErrorMessage("ERREUR", "un compte est deja lié  ce numéro ");
//            return;
//        } else {
//            callWebServiceAndRegister();
//            if (parametrageManager.getSupervalidation().getValeur().equals("SUPERVALIDATION_NON") && newAbonnement.getActif()) {
//                try {
//                    PDF();
//                } catch (JRException | IOException e) {
//                    addErrorMessage("ERREUR", "Une erreur s'est produite lors de l'impression du formulaire de souscription");
//                }
//            }
//        }
        newAbonnement = new Abonnements();
        abOm = new AbonnementsOm();
        listAbBque.clear();
        libelleCodeRetour = "";

    }

    public void callWebServiceAndRegister() {

        System.out.println("lancement du service de souscription");

        if (parametrageManager.getSupervalidation().getValeur().equals("SUPERVALIDATION_OUI")) {
            System.out.println("abonnement  avec supervalidation ");
            makeServiceCodeHumanReadable();
            newAbonnement.setActif(false);
            newAbonnement.setResilie(false);
            abonnementsFacade.create(newAbonnement);
            saveInTempTable();
            homeManager.getListAbonnementsJour().add(newAbonnement);
            adMessage("INFO", "Souscription créé avec succès. Veuillez procéder à  la supervalidation afin d'activer les services");

        } else {

            System.out.println("abonnement sans supervalidation");

            try {

                if (parametrageManager.isRegisterTarifAllowed()) {
                    payForRegistrationService();
                }

//                List<Parametres> url = parametresFacade.findByCodeParam("REGISTER_URL_ONATEL");
//                System.out.println("URL RECUP DANS LA BD " + url.toString());
                System.out.println("DEBUT SIMULATION SOUSCRIPTION BABF *******");

                BankAccountRegistrationServiceService bankAccountRegistrationServiceService = new BankAccountRegistrationServiceService();
                BankAccountRegistrationService bankAccountRegistrationService = bankAccountRegistrationServiceService.getBankAccountRegistrationServicePort();
                BindingProvider bindingProvider = (BindingProvider) bankAccountRegistrationService;
                bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://10.133.0.84:60443/mbs-bank-workflow-atlantique/BankAccountRegistrationService?wsdl");

                BankAccountRegistration accountRegistration = new ObjectFactory().createBankAccountRegistration();

                //accountRegistration.setAccountNumber("012345678101");
                accountRegistration.setReferenceID("BABF-" + newAbonnement.getCompte());//numero de reference demande par ONATEL
                accountRegistration.setBankName("BANKATLANTIQUE");
                accountRegistration.setAccountNumber(newAbonnement.getCompte());
                accountRegistration.setAuthorizedMobileNumber(newAbonnement.getNumerotelephone());
                accountRegistration.setBeneficiaryFirstName(newAbonnement.getNom());
                accountRegistration.setBeneficiaryLastName(newAbonnement.getPrenoms());
                accountRegistration.setBranchCode("BABF");
                accountRegistration.setCountryCode("226");
                accountRegistration.setExternalData1(newAbonnement.getNumerotelephone());
                accountRegistration.setExternalData2("linkage");
                accountRegistration.setTimestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));

                Object response = bankAccountRegistrationService.bankAccountRegistrationFunc(accountRegistration);

                if (response != null) {
                    JAXBContext jAXBContext = JAXBContext.newInstance(ObjectFactory.class);
                    Unmarshaller unmarshaller = jAXBContext.createUnmarshaller();

                    JAXBElement<BankAccountRegistrationReponse> jAXBElement = unmarshaller.unmarshal((Element) response, BankAccountRegistrationReponse.class);

                    BankAccountRegistrationReponse registrationReponse = jAXBElement.getValue();
                    System.out.println("Status : " + registrationReponse.getBankAccountRegistrationResult().getStatus());
                    System.out.println("Description : " + registrationReponse.getBankAccountRegistrationResult().getDescription());

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
                    System.out.println("erreur de fonctionnement de l'api ");
                }
            } catch (ParameterNotFoundException | FeesNotDefinedException | PaymentException ex) {
                ex.printStackTrace();
                addErrorMessage("ERREUR", "Problème rencontré lors du paiement: ERREUR DE COMPTABILISATION " + ex.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                codeRetour = 604;
                libelleCodeRetour = "PLATFORM UNAVAILABLE";
                addErrorMessage("ERREUR", "La souscription a échoué " + codeRetour + ":" + libelleCodeRetour);
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
        //  Banque bq = banqueFacade.find(Integer.valueOf(1));
        Random rand = new Random();
        int aleatoire = rand.nextInt(100);
        //Mettre le facteur aléatoire de l'alias sur 2 positions
        String alea = aleatoire < 10 ? "0" + aleatoire : String.valueOf(aleatoire);

        newAbonnement.setAlias(onatel.getBic() + "00" + ab.getRacine() + ab.getCompte().substring(ab.getCompte().length() - 4) + alea);
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
                    comptes.setNuemro(client.getComptes().get(i).getNumero());
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

            // selectedComptes=new Comptes();
            // System.out.println("CLIENT: " + client.getNom());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
        List<Parametres> list = parametresFacade.findByCodeParam("FICHE_SOUSCRIPTION");
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
            paymentRequest.setCommissions(onatel.getIdOperateur().longValue());  /// ONATEL à la place de commissions
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
