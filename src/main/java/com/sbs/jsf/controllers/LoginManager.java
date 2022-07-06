package com.sbs.jsf.controllers;

import com.sbs.easymbank.dao.ConnexionFacade;
import com.sbs.easymbank.dao.HistPasswordFacade;
import com.sbs.easymbank.dao.ParametresFacade;
import com.sbs.easymbank.dao.UsersFacade;
import com.sbs.easymbank.entities.Connexion;
import com.sbs.easymbank.entities.HistPassword;
import com.sbs.easymbank.entities.Operateurs;

import com.sbs.easymbank.entities.Parametres;
import com.sbs.easymbank.entities.Users;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
import org.omnifaces.util.Faces;

@ManagedBean
@SessionScoped
public class LoginManager
        extends Controller
        implements Serializable {

    @EJB
    private ConnexionFacade connexionFacade;

    @EJB
    private ParametresFacade parametresFacade;
    @EJB
    private UsersFacade usersFacade;

    private Users utilisateur;
    private String login;
    private String pass;
    private String passForChange;
    private String newPassForChange;
    private String newPassConfirmForChange;
    private boolean loggedIn;
    private int nbreTentativeConnexion;
    @ManagedProperty("#{navigationManager}")
    private NavigationManager navigationManager;
    @ManagedProperty("#{parametrageManager}")
    private ParametrageManager parametrageManager;
    @ManagedProperty("#{parametrageMtnManager}")
    private ParametrageMtnManager parametrageMtnManager;
    @ManagedProperty("#{sessionManager}")
    private SessionManager sessionManager;
    @ManagedProperty("#{appManager}")
    private AppManager appManager;
    private Connexion connexion;
    private String adressIP;
    String urlLogo;
    private List<Operateurs> listOperateurs;
    @EJB
    private HistPasswordFacade histPasswordFacade;
    @ManagedProperty("#{securityManager}")
    private SecurityManager securityManager;
    private boolean expiratePassword;
    private String currentPass;
    private String newPass;
    private String newPassConfirmation;
    private Pattern pattern;
    private Matcher matcher;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20})";
    Calendar myCal = Calendar.getInstance();
    Map<Users, Integer> tableConnexion;

    @PostConstruct
    public void init() {
        pattern = Pattern.compile(PASSWORD_PATTERN);
        tableConnexion = new HashMap();
        List<Parametres> lPara = parametresFacade.findByCodeParam("LOGO_URL");
        if (!lPara.isEmpty()) {
            urlLogo = lPara.get(0).getValeur();
        }
    }

    @PreDestroy
    public void sessionDestroyed() {

    }

    public Users getUtilisateur() {
        return this.utilisateur;
    }

    public void setUtilisateur(Users utilisateur) {
        this.utilisateur = utilisateur;
    }

    public boolean isLoggedIn() {
        return this.loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public NavigationManager getNavigationManager() {
        return this.navigationManager;
    }

    public void setNavigationManager(NavigationManager navigationManager) {
        this.navigationManager = navigationManager;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return this.pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public ParametrageManager getParametrageManager() {
        return this.parametrageManager;
    }

    public void setParametrageManager(ParametrageManager parametrageManager) {
        this.parametrageManager = parametrageManager;
    }

    public Connexion getConnexion() {
        return connexion;
    }

    public void setConnexion(Connexion connexion) {
        this.connexion = connexion;
    }

    public String getPassForChange() {
        return passForChange;
    }

    public void setPassForChange(String passForChange) {
        this.passForChange = passForChange;
    }

    public String getNewPassForChange() {
        return newPassForChange;
    }

    public void setNewPassForChange(String newPassForChange) {
        this.newPassForChange = newPassForChange;
    }

    public String getNewPassConfirmForChange() {
        return newPassConfirmForChange;
    }

    public void setNewPassConfirmForChange(String newPassConfirmForChange) {
        this.newPassConfirmForChange = newPassConfirmForChange;
    }

    public String getAdressIP() {
        return adressIP;
    }

    public void setAdressIP(String adressIP) {
        this.adressIP = adressIP;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public String getUrlLogo() {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }

    public ParametrageMtnManager getParametrageMtnManager() {
        return parametrageMtnManager;
    }

    public void setParametrageMtnManager(ParametrageMtnManager parametrageMtnManager) {
        this.parametrageMtnManager = parametrageMtnManager;
    }

    public boolean isExpiratePassword() {
        return expiratePassword;
    }

    public void setExpiratePassword(boolean expiratePassword) {
        this.expiratePassword = expiratePassword;
    }

    public String getCurrentPass() {
        return currentPass;
    }

    public void setCurrentPass(String currentPass) {
        this.currentPass = currentPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getNewPassConfirmation() {
        return newPassConfirmation;
    }

    public void setNewPassConfirmation(String newPassConfirmation) {
        this.newPassConfirmation = newPassConfirmation;
    }

    public SecurityManager getSecurityManager() {
        return securityManager;
    }

    public void setSecurityManager(SecurityManager securityManager) {
        this.securityManager = securityManager;
    }

    public AppManager getAppManager() {
        return appManager;
    }

    public void setAppManager(AppManager appManager) {
        this.appManager = appManager;
    }
    
    

    public void exitinApp() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        session.invalidate();
        String laDate = dateFormat.format(Calendar.getInstance().getTime());
        // connexion.setDateFin(laDate.substring(0,10));
        // connexion.setHeureFin(laDate.substring(11));
        // connexionFacade.edit(connexion);
        try {
            FacesContext.getCurrentInstance()
                    .getExternalContext().redirect("login.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String doLogin() throws ParseException {
        
        // Calendar myCal2 = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String retour = navigationManager.redirectToHome();
        // System.out.println("ENTREE DANS DOLOGIN");
        Users user = this.usersFacade.findByLogin(this.login);
        if (user != null) {
            nbreTentativeConnexion++;
            System.out.println("login : " + this.login);
            System.out.println("login : " + user.getPassword());
            if ((user.getLogin().equals(this.login)) && (user.getPassword().equals(DigestUtils.sha256Hex(this.pass)))) {
                
                if (user.getActiver() && user.getIdprofils().getConnexion()) {
                    List<HistPassword> l = histPasswordFacade.findUserPreviousPassword(user, 1);
                    if (!l.isEmpty()) { //L'utilisateur s'est une fois connecté
                        Date lastPassdate = dateFormat.parse(l.get(0).getDatecreation());
                        long duration = myCal.getTimeInMillis() - lastPassdate.getTime();
                        long diffInDay = TimeUnit.MILLISECONDS.toDays(duration);
                        if (diffInDay > Long.parseLong(securityManager.getDelaiValiditePass().getValeur()) || user.getReinitialise()) {
                            //son mot de passe a expiré ou a été réinitialisé 
                            expiratePassword = true;
                            retour = navigationManager.redirectToResetPassword();
                        }

                    } else {//L'utilisateur est à sa première connexion
                        expiratePassword = true;
                        retour = navigationManager.redirectToResetPassword();
                    }
                } else {
                    addErrorMessage("Ce compte ne peut se connecter: Il a été désactivé ou le profil auquel il est rattaché n'a pas le droit de connexion. Veuillez contacter votre administrateur");
                    return null;

                }

                this.utilisateur = user;
                this.loggedIn = true;
                sessionManager.setUtilisateur(utilisateur);
                /*PISTE D'AUDIT*/
                sessionManager.getLogs().setMachine(Faces.getRemoteAddr());
                sessionManager.getLogs().setLogin(utilisateur.getLogin());
                // Connexion connexion=new Connexion();

                FacesContext facesContext = FacesContext.getCurrentInstance();
                HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
                connexion = new Connexion(session, user);
                appManager.getConnexions().add(connexion);
                //  System.out.println("DATE: "+session.getCreationTime());
                //  String date=dateFormat.format(new Date(session.getCreationTime()));

                return retour;
            }
            blockUser(user);
            addErrorMessage("Login ou mot de passe incorrect");
            login = pass = null;
            return null;
        }
        addErrorMessage("Login ou mot de passe incorrect");
        login = pass = null;
        return null;
        
    }

    public String doLogout() {
        this.utilisateur = null;
        this.loggedIn = false;
        return this.navigationManager.redirectToLogin();
    }

    public String getSouscriptionPage() {
        return this.parametrageManager.getSelectedPageSouscription().getPage();
    }

    public String getSouscriptionPageMtn() {
        return this.parametrageMtnManager.getSelectedPageSouscription().getPage();
    }

    public String changePassword() {
        if (DigestUtils.sha256Hex(passForChange).equals(utilisateur.getPassword())) {
            if (newPassForChange.equals(newPassConfirmForChange)) {
                if (uniquePassword(utilisateur, DigestUtils.sha256Hex(newPassForChange), Integer.parseInt(securityManager.getNbrePassToCheck().getValeur()))) {
                    utilisateur.setPassword(DigestUtils.sha256Hex(newPassForChange));
                    utilisateur.setReinitialise(false);
                    usersFacade.edit(utilisateur);
                    HistPassword histPassword = new HistPassword();
                    histPassword.setIdUsers(utilisateur);
                    histPassword.setPassword(utilisateur.getPassword());
                    histPassword.setDatecreation(sdf.format(myCal.getTime()));
                    List<HistPassword> l = histPasswordFacade.findUserPreviousPassword(utilisateur, Integer.parseInt(securityManager.getMaxPassParUtililisateur().getValeur()));
                    if (!l.isEmpty() && l.size() == Integer.parseInt(securityManager.getMaxPassParUtililisateur().getValeur())) {
                        histPasswordFacade.remove(l.get(l.size() - 1));
                    }

                    histPasswordFacade.create(histPassword);
                    expiratePassword = false;
                    addMessage("MOT DE PASSE", "Mot de passe changé avec succès");
                    return "toHome";

                } else {
                    addErrorMessage("Veuillez entrer un mot de passe différent des " + Integer.parseInt(securityManager.getNbrePassToCheck().getValeur()) + " précédents");
                    return "";

                }
            } else {
                addErrorMessage("Veuillez confirmer le mot de passe");
                return "";
            }
        } else {
            addErrorMessage("Mot de passe incorrect");
            return "";
        }

    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public boolean userHasAdministrationRight() {
        return (utilisateur.getIdprofils().getCreationProfils() || utilisateur.getIdprofils().getCreationAgence() || utilisateur.getIdprofils().getCreationUtilisateur() || utilisateur.getIdprofils().getModificationProfils() || utilisateur.getIdprofils().getParametrage() || utilisateur.getIdprofils().getReinitialisationUtilisateur() || utilisateur.getIdprofils().getSuppressionAgence() || utilisateur.getIdprofils().getSuppressionProfils() || utilisateur.getIdprofils().getSuppressionUtilisateur() || utilisateur.getIdprofils().isAudits() || utilisateur.getIdprofils().isSecurite());
    }

    public boolean validate(final String password) {

        matcher = pattern.matcher(password);
        return matcher.matches();

    }

    private void blockUser(Users users) {
        if (tableConnexion.containsKey(users)) {
            tableConnexion.put(users, tableConnexion.get(users) + 1);
        } else {
            tableConnexion.put(users, 1);
        }
        if (tableConnexion.get(users).equals(Integer.parseInt(securityManager.getMaxTentativeConnexion().getValeur()))) {
            users.setActiver(false);
            users.setDatedesactivation(sdf.format(myCal.getTime()));
            usersFacade.edit(users);
        }

    }

    private boolean uniquePassword(Users users, String password, int maxResult) {
        List<HistPassword> l = histPasswordFacade.findUserPreviousPassword(users, maxResult);
        for (HistPassword h : l) {
            if (h.getPassword().equals(password)) {
                return false;
            }
        }
        return true;
    }

}
