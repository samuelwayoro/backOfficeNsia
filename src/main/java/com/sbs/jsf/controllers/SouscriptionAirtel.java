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
import org.apache.commons.codec.digest.DigestUtils;
import org.tempuri.RetBankAccountRegistration;
import org.tempuri.RetKYCRequest;

/**
 *
 * @author SOCITECH-
 */
@ManagedBean
@ViewScoped
public class SouscriptionAirtel implements Serializable {

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
  //  private SSLClientAxisEngineConfig sSLConfig = new SSLClientAxisEngineConfig();

    private AbonnementBanque abBanque = new AbonnementBanque();
    private Abonnements newAbonnement = new Abonnements();

    private List<AbonnementBanque> listAbBque = new ArrayList<>();
    //fourni normalement par l'API MoMo
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
    @ManagedProperty(value = "#{parametrageAirtelManager}")
    private ParametrageAirtelManager parametrageManager;
    @ManagedProperty(value = "#{homeManager}")
    private HomeManager homeManager;
    @ManagedProperty(value = "#{sessionManager}")
    private SessionManager sessionManager;
    private Comptes selectedComptes = new Comptes();
    private Calendar calendar=Calendar.getInstance();
    private Operateurs airtel;
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
    private OperateursFacade operateursFacade;
    @EJB
    private ProfilsClientsFacade profilsClientsFacade;
    @EJB
    private TarifsProfilsOperateursFacade tarifsProfilsOperateursFacade;

    @PostConstruct
    public void init() {
        try {
            String[] patternBank = new String[]{"BANKNAME","BRANCHCODE","COUNTRYCODE"};
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
                            countryCode=para.getValeur();
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
            List<Operateurs> lop = operateursFacade.findByDesignationOperateur("AIRTEL");
            if (!lop.isEmpty()) {
                airtel = lop.get(0);
            } else {
                throw new OperatorNotFoundException("AIRTEL");
            }
            List<Parametres> para_indicatif=parametresFacade.findByCodeParam("INDICATIF_PAYS");
            if(para_indicatif != null && !para_indicatif.isEmpty()){
                indicatifPays=para_indicatif.get(0).getValeur();
            }
          if(indicatifPays == null){
                throw new ParameterNotFoundException("INDICATIF_PAYS");                
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

    public ParametrageAirtelManager getParametrageManager() {
        return parametrageManager;
    }

    public void setParametrageManager(ParametrageAirtelManager parametrageManager) {
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
//
        abOm.setMsisdn(msisdn);
        abOm.setActivationkey(activationKey);
        RetKYCRequest rkr=new RetKYCRequest();
        try {
                List<Parametres> url = parametresFacade.findByCodeParam("REGISTER_URL_AIRTEL");
                McoAtlanticBankServerSoapImplService mcoBankServerSoapImplService;
  
                if (!url.isEmpty()) {
  
                    mcoBankServerSoapImplService=new McoAtlanticBankServerSoapImplService(new URL(url.get(0).getValeur()));
                    
                } else {
                    throw new ParameterNotFoundException("REGISTER_URL_AIRTEL");
                }
                  MCOB2ASoap mbss=mcoBankServerSoapImplService.getMcoAtlanticBankServerSoapImplPort();
                  rkr=mbss.kycRequest(bankName, branchCode, msisdn, DigestUtils.sha256Hex(msisdn+loginManager.getLogin()+calendar.getTimeInMillis()), Long.toString(calendar.getTimeInMillis()) , "", "");
        } catch (Exception ex) {
//            status.value = "604";
            ex.printStackTrace();
        } finally {
            abOm.setCodeRetour(new BigInteger(rkr.getStatus().toString()));
            abOm.setDateNaissance(rkr.getDateOfBirth());
            abOm.setPrenoms(rkr.getFirstName());
            abOm.setNom(rkr.getLastName());
            abOm.setNationalite(rkr.getNationality());
            abOm.setGenre(rkr.getGender());
            abOm.setMsisdn(rkr.getAuthorizedMobileNumber());
            libelleCodeRetour=rkr.getDescription();
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
        checkKYCFromBankSide();
    }

    public void enregistrerAbonnement() {
        calendar = Calendar.getInstance();
        // newAbonnement.setOperateur(airtel);
        newAbonnement.setUsercreate(loginManager.getUtilisateur().getLogin());
        newAbonnement.setOperateur(airtel);
        newAbonnement.setService("3");
        newAbonnement.setGenre(abOm.getGenre());
        newAbonnement.setNationalite(abOm.getNationalite());
        newAbonnement.setPays(abOm.getPays());
        newAbonnement.setRegion(abOm.getRegion());
        newAbonnement.setVille(abOm.getVille());
        
        if (abOm.getCodeRetour() != null) {
            newAbonnement.setCoderetour(abOm.getCodeRetour().toString());//Le Code retour de KYCRequest
        }
        newAbonnement.setDatecreation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
        newAbonnement.setDatesouscription(newAbonnement.getDatecreation());
        if (!activationKey.equals("")) {
            newAbonnement.setActivation(activationKey);
        }
        newAbonnement.setAgence(loginManager.getUtilisateur().getIdagences().getCodeagence());
//        for (Comptes cpte : listCpte) {
//            if (cpte.getNuemro().equals(newAbonnement.getCompte())) {
//                newAbonnement.setLabel(cpte.getLibelle());
//            }
//        }
        //V??rification de l'unicit?? de l'alias
        do {
            aliasGenerator(newAbonnement);
        } while (!uniqueAlias(newAbonnement.getAlias()));

        //V??rification de l'unicit?? du couple (compte,msisdn)
        List<Abonnements> l = abonnementsFacade.findByRacineCompteAndMSISDN(newAbonnement.getCompte(), newAbonnement.getNumerotelephone());
        Abonnements abont = new Abonnements();
        if (l != null) {
            if (!l.isEmpty()) {
                abont = l.get(0);
            }
        }
        
         String unique_acount_alias = parametresFacade.findByACodeParam("UNIQUE_ACCOUNT_ALIAS");
        if (unique_acount_alias != null && unique_acount_alias.equals("TRUE")) {
            List<Abonnements> ListAbonnementWithSameAccount = abonnementsFacade.findAbonnementByNumeroCompte(newAbonnement.getCompte());
            if (ListAbonnementWithSameAccount != null && !ListAbonnementWithSameAccount.isEmpty()) {
                addErrorMessage("ERREUR", "Le compte " + newAbonnement.getCompte() + " est dej?? li?? ");
                return;
            }

        }

        //  codeRetour = 200;/****A COMMENTER****/
        if (!newAbonnement.getNumerotelephone().equals(msisdn) && msisdn != null) {
            addErrorMessage("ERREUR", "Le num??ro " + msisdn + " n'appartient pas au client ");
            return;
        }

        if (abont.getActif() != null) {
            if (abont.getActif()) {//L'abonnement a d??ja ??t?? fait.
                addErrorMessage("ERREUR", "Ce compte est deja li???? ce num??ro ");
            } else {
                callWebServiceAndRegister();
                if (parametrageManager.getSupervalidation().getValeur().equals("SUPERVALIDATION_NON") && newAbonnement.getActif()) {
                    try {
                        PDF();
                    } catch (JRException | IOException e) {
                        e.printStackTrace();
                        addErrorMessage("ERREUR", "Une erreur s'est produite lors de l'impression du formulaire de souscription");
                    }
                }

            }
        } else {
            callWebServiceAndRegister();
            if (parametrageManager.getSupervalidation().getValeur().equals("SUPERVALIDATION_NON") && newAbonnement.getActif()) {
                try {
                    PDF();
                } catch (JRException | IOException e) {
                    e.printStackTrace();
                    addErrorMessage("ERREUR", "Une erreur s'est produite lors de l'impression du formulaire de souscription");
                }
            }
        }
        newAbonnement = new Abonnements();
        abOm = new AbonnementsOm();
        listAbBque.clear();
        libelleCodeRetour = "";

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
        //Mettre le facteur al??atoire de l'alias sur 2 positions
        String alea = aleatoire < 10 ? "0" + aleatoire : String.valueOf(aleatoire);

        newAbonnement.setAlias(airtel.getBic() + "00" + ab.getRacine() + ab.getCompte().substring(ab.getCompte().length() - 4) + alea);
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
            adMessage("INFO", "Souscription cr???? avec succ??s. Veuillez proc??der ???? la supervalidation afin d'activer les services");

        } else {
            List<Codes> listCode;
        //    StringHolder alias = new StringHolder(newAbonnement.getAlias());
            try {
                if(parametrageManager.isRegisterTarifAllowed())
                    payForRegistrationService();
                List<Parametres> url = parametresFacade.findByCodeParam("REGISTER_URL_AIRTEL");
//                RegisterServiceLocator locator = new RegisterServiceLocator(sSLConfig);
                McoAtlanticBankServerSoapImplService mcoBankServerSoapImplService;
  //              RegisterPort_PortType regpt;
                if (!url.isEmpty()) {
  //                  regpt = locator.getRegisterPort(new URL(url.get(0).getValeur()));
                    mcoBankServerSoapImplService=new McoAtlanticBankServerSoapImplService(new URL(url.get(0).getValeur()));
                    
                } else {
                    throw new ParameterNotFoundException("REGISTER_URL_AIRTEL");
                }
                  MCOB2ASoap mbss=mcoBankServerSoapImplService.getMcoAtlanticBankServerSoapImplPort();
                  RetBankAccountRegistration rbar=mbss.bankAccountRegistration(bankName, branchCode, newAbonnement.getAlias(),newAbonnement.getPrenoms(), newAbonnement.getNom(), newAbonnement.getAlias()+Long.toString(calendar.getTimeInMillis()), Long.toString(calendar.getTimeInMillis()) , countryCode, newAbonnement.getNumerotelephone(), "", "");
              //  codeRetour = regpt.ombRequest(msisdn, alias, Short.valueOf(newAbonnement.getService()), newAbonnement.getLabel(), currencyCode, newAbonnement.getActivation(), calendar);
              codeRetour=rbar.getStatus();  
              System.out.println("INPUT:{msisdn:" + msisdn + ",alias:" + newAbonnement.getAlias() + ",serviceCode:" + newAbonnement.getService() + "} RETOUR BANKACCOUNTREGISTRATION: " + codeRetour);
                  
              newAbonnement.setCoderetour(String.valueOf(codeRetour));
                listCode = codesFacade.findDescFromCode(String.valueOf(codeRetour));
                if (listCode != null && !listCode.isEmpty()) {
                    libelleCodeRetour = listCode.get(0).getCodedescription();
                }
                if (codeRetour == 0) {
                    newAbonnement.setActif(true);
                    if(msisdn!=null)
                     newAbonnement.setNumerotelephone(msisdn);
                    newAbonnement.setUservalidate(newAbonnement.getUsercreate());
                    newAbonnement.setDatevalidation(newAbonnement.getDatecreation());
                    makeServiceCodeHumanReadable();
                    abonnementsFacade.create(newAbonnement);
                    sessionManager.getLogs().setAction("CREATION D'ABONNEMENT");
                    sessionManager.getLogs().setDateLog(new Date());
                    sessionManager.getLogs().setMessage("ALIAS: " + newAbonnement.getAlias() + "MSISDN: " + newAbonnement.getNumerotelephone());
                    sessionManager.logDB();
                    //TRACE POUR LA RECONCILIATION
                    AbonnementsReconciliations newAbonnementRec = new AbonnementsReconciliations(newAbonnement);
                    newAbonnementRec.setDatecreation(newAbonnement.getDatecreation());
                    newAbonnementRec.setDatevalidation(newAbonnement.getDatevalidation());
                    newAbonnementRec.setMotif("register");
                    abonnementsReconciliationsFacade.create(newAbonnementRec);
                    homeManager.getListAbonnementsJour().add(newAbonnement);
                    adMessage("INFO", "Souscription r??alis??e avec succ??s.");
                    // PDF();
                } else {
                    newAbonnement.setActif(false);
                    //TRACE POUR LA RECONCILIATION
                    AbonnementsReconciliations newAbonnementRec = new AbonnementsReconciliations(newAbonnement);
                    newAbonnementRec.setDatecreation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
                    newAbonnementRec.setMotif("register");
                    abonnementsReconciliationsFacade.create(newAbonnementRec);
                    sessionManager.getLogs().setAction("TENTATIVE D'ABONNEMENT ECHOUEE");
                    sessionManager.getLogs().setDateLog(new Date());
                    sessionManager.getLogs().setMessage("ALIAS:" + newAbonnement.getAlias() + " MSISDN:" + msisdn + " COMPTE:" + newAbonnement.getCompte());
                    sessionManager.logDB();
                    addErrorMessage("ERREUR", "La souscription a ??chou?? " + codeRetour + ":" + rbar.getDescription());
                }

            }catch(ParameterNotFoundException | FeesNotDefinedException | PaymentException ex){
             ex.printStackTrace();
             addErrorMessage("ERREUR", "Probl??me rencontr?? lors du paiement: ERREUR DE COMPTABILISATION " + ex.getMessage());
            
            }catch (Exception e) {
                e.printStackTrace();
                codeRetour = 604;
                libelleCodeRetour = "PLATFORM UNAVAILABLE";
                
                addErrorMessage("ERREUR", "La souscription a ??chou?? " + codeRetour + ":" + libelleCodeRetour);
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
                    listPhone.add(tab[i].replaceAll("\\s+", "").replaceAll("^"+indicatifPays,"").replaceAll("^00"+indicatifPays,"").replaceAll("^\\+"+indicatifPays,""));//Supprimer l'indicatif pays et les espaces
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

    //utilis?? pour l'option A pour metrre ?? jour le num??ro de t??l??phone
    public void editMSISDN(String number) {
        msisdn = number;
    }

    public boolean uniqueAlias(String alias) {
        return abonnementsFacade.findByAlias(alias) == null;
    }

    public void saveInTempTable() {
        AbonnementTemp abonnementTemp = new AbonnementTemp(newAbonnement, abOm);

        //abonnementTempFacade.create(abonnementTemp);
    }

    public void initJasper() throws JRException, FileNotFoundException {
        List<Abonnements> listAbonnements = abonnementsFacade.findAbonnementActifOfCustomerByNumber(newAbonnement);
        System.out.println("TOTAL ABONNEMENTS: " + listAbonnements.size());
        if(listAbonnements.isEmpty())
            return;
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
        parametres.put("COMPTE",newAbonnement.getCompte());
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
        if(tarifsProfilsOperateurs.getTarif()!=0){
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setAlias(newAbonnement.getAlias());
        paymentRequest.setCommissions(airtel.getIdOperateur().longValue());  /// operateur ?? la place de commissions
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
