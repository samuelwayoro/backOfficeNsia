/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.jsf.controllers;

import com.sbs.easymbank.dao.AgencesFacade;
import com.sbs.easymbank.dao.ProfilsFacade;
import com.sbs.easymbank.dao.UsersFacade;
import com.sbs.easymbank.entities.Agences;
import com.sbs.easymbank.entities.Profils;
import com.sbs.easymbank.entities.Users;
import com.sbs.jsf.model.LazyUtilisateurDataModel;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.zip.CRC32;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author SOCITECH-
 */
@ManagedBean
@ViewScoped
public class UserManager extends Controller implements Serializable {

    //private LazyDataModel<Users> listUtilisateur;
    private List<Users> listUtilisateur;

   // private LazyDataModel<Users> listUtilisateurDesactive;
    
    private List<Users> listUtilisateurDesactive;
    
    private List<Users> listUtilisateurFiltered;
    private List<Profils> listProfils;
    private List<Agences> listAgences;
    //Etant donné la modification et l'ajout d'utilisateur se fontt grâce au bouton VALIDER, cette variable permet de savoir à quel moment il s
    private boolean pourModification;
    @EJB
    private UsersFacade usersFacade;
    @EJB
    private ProfilsFacade profilsFacade;
    @EJB
    private AgencesFacade agencesFacade;
    private Users newUtilisateur = new Users();
    private Users selectedUser = new Users();
    @ManagedProperty(value = "#{sessionManager}")
    private SessionManager sessionManager;
    Random random = new Random();

    @PostConstruct
    public void init() {
        //listUtilisateur = new LazyUtilisateurDataModel(usersFacade.findByActiver(true));
        listUtilisateur = usersFacade.findByActiver(true);
       // listUtilisateurDesactive = new LazyUtilisateurDataModel(usersFacade.findByActiver(false));
        listUtilisateurDesactive = usersFacade.findByActiver(false);
        listProfils = profilsFacade.findAll();
        listAgences = agencesFacade.findAll();
    }

    private Converter AgencesConverter = new Converter() {

        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {
            return agencesFacade.find(Integer.parseInt(value));
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            return ((Agences) value).getIdagences().toString();
        }
    };

    private Converter ProfilsConverter = new Converter() {

        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {
            return profilsFacade.find(Integer.parseInt(value));
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            return ((Profils) value).getIdprofils().toString();
        }
    };
//
//    public LazyDataModel<Users> getListUtilisateur() {
//        return listUtilisateur;
//    }
//
//    public void setListUtilisateur(LazyDataModel<Users> listUtilisateur) {
//        this.listUtilisateur = listUtilisateur;
//    }

    public List<Users> getListUtilisateur() {
        return listUtilisateur;
    }

    public void setListUtilisateur(List<Users> listUtilisateur) {
        this.listUtilisateur = listUtilisateur;
    }

    
    
    
    public Users getNewUtilisateur() {
        return newUtilisateur;
    }

    public void setNewUtilisateur(Users newUtilisateur) {
        this.newUtilisateur = newUtilisateur;
    }

    public List<Profils> getListProfils() {
        return listProfils;
    }

    public void setListProfils(List<Profils> listProfils) {
        this.listProfils = listProfils;
    }

    public List<Agences> getListAgences() {
        return listAgences;
    }

    public void setListAgences(List<Agences> listAgences) {
        this.listAgences = listAgences;
    }

    public List<Users> getListUtilisateurFiltered() {
        return listUtilisateurFiltered;
    }

    public void setListUtilisateurFiltered(List<Users> listUtilisateurFiltered) {
        this.listUtilisateurFiltered = listUtilisateurFiltered;
    }

    public Converter getAgencesConverter() {
        return AgencesConverter;
    }

    public void setAgencesConverter(Converter AgencesConverter) {
        this.AgencesConverter = AgencesConverter;
    }

    public Converter getProfilsConverter() {
        return ProfilsConverter;
    }

    public void setProfilsConverter(Converter ProfilsConverter) {
        this.ProfilsConverter = ProfilsConverter;
    }

    public Users getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(Users selectedUser) {
        this.selectedUser = selectedUser;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

//    public LazyDataModel<Users> getListUtilisateurDesactive() {
//        return listUtilisateurDesactive;
//    }
//
//    public void setListUtilisateurDesactive(LazyDataModel<Users> listUtilisateurDesactive) {
//        this.listUtilisateurDesactive = listUtilisateurDesactive;
//    }

    public List<Users> getListUtilisateurDesactive() {
        return listUtilisateurDesactive;
    }

    public void setListUtilisateurDesactive(List<Users> listUtilisateurDesactive) {
        this.listUtilisateurDesactive = listUtilisateurDesactive;
    }

    
    
    
    public void passwordGenerator() {
        if (newUtilisateur != null) {
            if (!newUtilisateur.getLogin().isEmpty()) {
                //  CRC32 crc = new CRC32();
                byte[] encrPassword = Base64.encodeBase64((newUtilisateur.getLogin() + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())).getBytes());
                // crc.update(newUtilisateur.getLogin().getBytes());
                newUtilisateur.setPassword(new String(encrPassword));
            }
        }
    }

    public void generatePassword(Users user) {
        if (user != null) {
            if (!user.getLogin().isEmpty()) {
                String strForEncrypting = DigestUtils.sha512Hex(user.getLogin() + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                byte[] encrPassword = Base64.encodeBase64(strForEncrypting.getBytes());
                // crc.update(newUtilisateur.getLogin().getBytes());

                user.setPassword(new String(Arrays.copyOf(encrPassword, 9)) + random.nextInt(10));
            }
        }
    }

    public void onRowSelect(SelectEvent event) {
        Users p = (Users) event.getObject();
        newUtilisateur = p;
        addInfoMessage("Utilisateur : " + newUtilisateur.getLogin());

    }

    public void resetUtilisateur() {
        System.out.println("resetProfil");

        newUtilisateur = new Users();
    }

    public void enregistrerUtilisateur() {
        try {
            Users user = usersFacade.findByLogin(newUtilisateur.getLogin());
            if (user == null) {
                newUtilisateur.setDatecreation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                newUtilisateur.setReinitialise(Boolean.FALSE);
                newUtilisateur.setPassword(DigestUtils.sha256Hex(newUtilisateur.getPassword()));
                usersFacade.create(newUtilisateur);
                /*PISTE D'AUDIT*/
                sessionManager.getLogs().setAction("CREATION UTILISATEUR");
                sessionManager.getLogs().setDateLog(new Date());
                sessionManager.getLogs().setMessage("login:" + newUtilisateur.getLogin() + " nom:" + newUtilisateur.getNom() + " agences:" + newUtilisateur.getIdagences().getLibelle() + " profil:" + newUtilisateur.getIdprofils().getLibelle());
                sessionManager.logDB();
                sessionManager.getLogs().setMessage("");
                ((LazyUtilisateurDataModel) listUtilisateurDesactive).getDatasource().add(newUtilisateur);

                addMessage("CREATION D'UTILISATEUR", "L'utilisateur a été créé avec succès. Veuillez lui communiquer son mot de passe: " + newUtilisateur.getPassword());
                newUtilisateur = new Users();

            } else {

                if (newUtilisateur.getIdusers() != null) {
                    // newUtilisateur.setIdusers(user.getIdusers());
                    newUtilisateur.setDatemodification(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                    usersFacade.edit(newUtilisateur);
                    /*PISTE D'AUDIT*/
                    sessionManager.getLogs().setAction("MODIFICATION UTILISATEUR");
                    sessionManager.getLogs().setDateLog(new Date());
                    // sessionManager.getLogs().setMessage("login:"+newUtilisateur.getLogin()++ " agences:"+newUtilisateur.getIdagences().getLibelle()+ " profil:"+newUtilisateur.getIdprofils().getLibelle());
                    sessionManager.getLogs().setMessage("");
                    if (!newUtilisateur.getNom().equals(user.getNom())) {
                        sessionManager.getLogs().setMessage(sessionManager.getLogs().getMessage() + " nom:" + newUtilisateur.getNom());
                    }
                    if (!newUtilisateur.getPrenom().equals(user.getPrenom())) {
                        sessionManager.getLogs().setMessage(sessionManager.getLogs().getMessage() + " prenom:" + newUtilisateur.getPrenom());
                    }
                    if (!newUtilisateur.getIdagences().equals(user.getIdagences())) {
                        sessionManager.getLogs().setMessage(sessionManager.getLogs().getMessage() + " agences:" + newUtilisateur.getIdagences().getLibelle());
                    }
                    if (!newUtilisateur.getIdprofils().equals(user.getIdprofils())) {
                        sessionManager.getLogs().setMessage(sessionManager.getLogs().getMessage() + " profil:" + newUtilisateur.getIdprofils().getLibelle());
                    }
                    sessionManager.logDB();
                    sessionManager.getLogs().setMessage("");
                    addInfoMessage("Modification réalisée avec succès");
                } else {
                    addErrorMessage("Ce login est déja utilisé");
                }

                rafraichirList();
                newUtilisateur = new Users();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addMessage(String summary, String detail) {

        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void desactiverUtilisateur() {
        //  System.out.println("supprimerUtilisateur");
        try {
            if (selectedUser != null) {
                selectedUser.setActiver(false);
                selectedUser.setDatedesactivation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                usersFacade.edit(selectedUser);
                ((LazyUtilisateurDataModel) listUtilisateurDesactive).getDatasource().add(selectedUser);
                //  usersFacade.remove(selectedUser);
                ((LazyUtilisateurDataModel) listUtilisateur).getDatasource().remove(selectedUser);
                /*PISTE D'AUDIT*/
                sessionManager.getLogs().setAction("DESACTIVATION UTILISATEUR");
                sessionManager.getLogs().setDateLog(new Date());
                sessionManager.getLogs().setMessage("login:" + selectedUser.getLogin());
                sessionManager.logDB();

                addMessage("DESACTIVATION D'UTILISATEUR", "L'utilisateur a été désactivé avec succès");
                selectedUser = new Users();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void reinitialiserUtilisateur() {
        try {
            if (selectedUser != null) {
                generatePassword(selectedUser);
                selectedUser.setReinitialise(Boolean.TRUE);
                selectedUser.setDatereinitialisation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                addMessage("REINITIALISATION D'UTILISATEUR", "Veuillez communiquer à l'utilisateur son nouveau mot de passe: " + selectedUser.getPassword());
                selectedUser.setPassword(DigestUtils.sha256Hex(selectedUser.getPassword()));
                usersFacade.edit(selectedUser);
                /*PISTE D'AUDIT*/
                sessionManager.getLogs().setAction("REINITIALISATION UTILISATEUR");
                sessionManager.getLogs().setDateLog(new Date());
                sessionManager.getLogs().setMessage("login:" + selectedUser.getLogin());
                sessionManager.logDB();
                selectedUser = new Users();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void activerUtilisateur() {
        try {
            if (selectedUser != null) {
                selectedUser.setActiver(true);
                selectedUser.setDateactivation(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                usersFacade.edit(selectedUser);
                //((LazyUtilisateurDataModel) listUtilisateur).getDatasource().add(selectedUser);
                listUtilisateur.add(selectedUser);
                listUtilisateurDesactive.remove(selectedUser);
                //  usersFacade.remove(selectedUser);
                ((LazyUtilisateurDataModel) listUtilisateurDesactive).getDatasource().remove(selectedUser);
                /*PISTE D'AUDIT*/
                sessionManager.getLogs().setAction("ACTIVATION UTILISATEUR");
                sessionManager.getLogs().setDateLog(new Date());
                sessionManager.getLogs().setMessage("login:" + selectedUser.getLogin());
                sessionManager.logDB();
                addMessage("ACTIVATION D'UTILISATEUR", "L'utilisateur a été activé avec succès");
                selectedUser = new Users();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void rafraichirList() {
       // listUtilisateur = new LazyUtilisateurDataModel(usersFacade.findByActiver(true));
        listUtilisateur = usersFacade.findByActiver(true);
        
       // listUtilisateurDesactive = new LazyUtilisateurDataModel(usersFacade.findByActiver(false));
       listUtilisateurDesactive = usersFacade.findByActiver(false);
    }
}
