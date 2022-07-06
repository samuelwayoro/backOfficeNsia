/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.jsf.controllers;

import com.sbs.easymbank.dao.OperateursFacade;
import com.sbs.easymbank.dao.ProfilsClientsFacade;
import com.sbs.easymbank.entities.Operateurs;
import com.sbs.easymbank.entities.ProfilsClients;
import com.sbs.exceptions.NoOperatorFoundException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSeparator;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

/**
 *
 * @author alex
 */
@ManagedBean
@ViewScoped
public class MainMenuManager implements Serializable {

    private MenuModel model;
    @ManagedProperty("#{loginManager}")
    private LoginManager loginManager;
    private List<Operateurs> listOperateurs;
    private List<ProfilsClients> profilsClients;
    @EJB
    OperateursFacade operateursFacade;
    @EJB
    ProfilsClientsFacade profilFacade;

    @PostConstruct
    public void init() {
        try {
            //recup de la liste des operateurs
            listOperateurs = operateursFacade.findAll();
            if (listOperateurs.isEmpty()) {
                throw new NoOperatorFoundException();
            }
            //recup de la liste des profils des clients 
            profilsClients = profilFacade.findAll();
            model = new DefaultMenuModel();
            DefaultSubMenu submenu;
            DefaultMenuItem item;
            DefaultSeparator separator;
            DefaultSubMenu submenu2;

            // MENU  ADMINISTRATION 
            submenu = new DefaultSubMenu();
            submenu.setLabel("ADMINISTRATION");
            submenu.setIcon("fa fa-key");
            submenu.setRendered(loginManager.userHasAdministrationRight());
            submenu.setStyleClass("submenu");

            //SOUS MENU PARAMETRAGE
            submenu2 = new DefaultSubMenu();
            submenu2.setLabel("PARAMETRAGE");
            submenu2.setIcon("fa fa-gears");
            submenu2.setRendered(loginManager.getUtilisateur().getIdprofils().getParametrage());
            for (Operateurs op : listOperateurs) {
                item = new DefaultMenuItem();
                item.setValue(op.getDesignationOperateur().equalsIgnoreCase("AIRTEL")?"ORANGE":op.getDesignationOperateur());
                item.setUrl(op.getPageParametrage());
                submenu2.addElement(item);
            }
            submenu.addElement(submenu2);
            
            
            
            item = new DefaultMenuItem();
            item.setValue("SECURITE");
            item.setUrl("securite.xhtml");
            item.setIcon("fa fa-shield");
            item.setRendered(loginManager.getUtilisateur().getIdprofils().isSecurite());
            submenu.addElement(item);

                       
            item = new DefaultMenuItem();
            item.setValue("GESTION DES AGENCES");
            item.setUrl("agences.xhtml");
            item.setIcon("fa fa-flag");
            item.setRendered(loginManager.getUtilisateur().getIdprofils().getCreationAgence() || loginManager.getUtilisateur().getIdprofils().getSuppressionAgence());
            submenu.addElement(item);

            item = new DefaultMenuItem();
            item.setValue("GESTION DES PROFILS");
            item.setUrl("profils.xhtml");
            item.setIcon("fa fa-user-secret");
            item.setRendered(loginManager.getUtilisateur().getIdprofils().getCreationProfils() || loginManager.getUtilisateur().getIdprofils().getSuppressionProfils());
            submenu.addElement(item);

            item = new DefaultMenuItem();
            item.setValue("GESTION DES UTILISATEURS");
            item.setUrl("utilisateurs.xhtml");
            item.setIcon("fa fa-users");
            item.setRendered(loginManager.getUtilisateur().getIdprofils().getCreationUtilisateur() || loginManager.getUtilisateur().getIdprofils().getReinitialisationUtilisateur() || loginManager.getUtilisateur().getIdprofils().getSuppressionUtilisateur());
            submenu.addElement(item);
            
            item = new DefaultMenuItem();
            item.setValue("GESTION DES PROFILS CLIENTS");
            item.setUrl("profilsClients.xhtml");
            item.setIcon("fa fa-flag-checkered");
            item.setRendered(loginManager.getUtilisateur().getIdprofils().isCreationProfilsClients() || loginManager.getUtilisateur().getIdprofils().isSuppressionProfilsClients());
            submenu.addElement(item);
            
            item = new DefaultMenuItem();
            item.setValue("LIMITES TRANSACTIONNELLES");
            item.setUrl("limitesTransactions.xhtml");
            item.setIcon("fa fa fa-minus");
            item.setRendered(loginManager.getUtilisateur().getIdprofils().isCreationProfilsClients() || loginManager.getUtilisateur().getIdprofils().isSuppressionProfilsClients());
            submenu.addElement(item);
            
          

            item = new DefaultMenuItem();
            item.setValue("AUDIT");
            item.setUrl("audit.xhtml");
            item.setIcon("fa fa-stack-exchange");
            item.setRendered(loginManager.getUtilisateur().getIdprofils().isAudits());
            submenu.addElement(item);

            //SEPARATOR  
            separator = new DefaultSeparator();
            separator.setRendered(loginManager.userHasAdministrationRight());

            model.addElement(submenu);
            model.addElement(separator);

            //SOUSCRIPTION MENU  
            submenu = new DefaultSubMenu();
            submenu.setLabel("SOUSCRIPTION");
            submenu.setIcon("fa fa-bank");
            submenu.setStyleClass("submenu");
            submenu.setRendered(loginManager.getUtilisateur().getIdprofils().getSouscription() || loginManager.getUtilisateur().getIdprofils().isListAbonnements() || loginManager.getUtilisateur().getIdprofils().getSupervalidation());

            //SOUSCRIPTION SOUS MENU 
            submenu2 = new DefaultSubMenu();
            submenu2.setLabel("NOUVELLE SOUSCRIPTION");
            submenu2.setIcon("fa fa-plus-square");
            submenu2.setRendered(loginManager.getUtilisateur().getIdprofils().getSouscription());
            for (Operateurs op : listOperateurs) {
                item = new DefaultMenuItem();
                //item.setValue(op.getDesignationOperateur().equalsIgnoreCase("AIRTEL")?"ORANGE":op.getDesignationOperateur());
                item.setValue(op.getDesignationOperateur());
                //System.out.println("Page de souscription "+op.getPageSouscription());
                item.setUrl(op.getPageSouscription());
                submenu2.addElement(item);
            }
            submenu.addElement(submenu2);

            item = new DefaultMenuItem();
            item.setValue("LISTE DES ABONNES");
            item.setUrl("listAbonnements.xhtml");
            item.setIcon("fa fa-list");
            item.setRendered(loginManager.getUtilisateur().getIdprofils().isListAbonnements());
            submenu.addElement(item);

            item = new DefaultMenuItem();
            item.setValue("SUPERVALIDATION");
            item.setUrl("validation.xhtml");
            item.setIcon("fa fa-check-circle");
            item.setRendered(loginManager.getUtilisateur().getIdprofils().getSupervalidation());
            submenu.addElement(item);

            item = new DefaultMenuItem();
            item.setValue("MES SOUSCRIPTIONS");
            item.setUrl("mesSouscriptions.xhtml");
            item.setIcon("fa fa-briefcase");
            item.setRendered(loginManager.getUtilisateur().getIdprofils().getSouscription());
            submenu.addElement(item);

            item = new DefaultMenuItem();
            item.setValue("RESILIATIONS");
            item.setUrl("resiliations.xhtml");
            item.setIcon("fa fa-trash");
            item.setRendered(loginManager.getUtilisateur().getIdprofils().isListAbonnements());
            submenu.addElement(item);
            
            //SEPARATOR  
            separator = new DefaultSeparator();
            separator.setRendered(loginManager.getUtilisateur().getIdprofils().getSouscription() || loginManager.getUtilisateur().getIdprofils().isListAbonnements() || loginManager.getUtilisateur().getIdprofils().getSupervalidation());

            model.addElement(submenu);
            model.addElement(separator);

            //OPERATIONS SUBMENU 
            submenu = new DefaultSubMenu();
            submenu.setLabel("OPERATIONS");
            submenu.setIcon("fa fa-credit-card");
            submenu.setRendered(loginManager.getUtilisateur().getIdprofils().isTransactions() || loginManager.getUtilisateur().getIdprofils().isListCommissions());
            submenu.setStyleClass("submenu");

            //OPERATIONS menu items
            item = new DefaultMenuItem();
            item.setValue("RECONCILIATIONS");
            item.setUrl("reconciliationTransaction.xhtml");
            item.setIcon("fa fa-exchange");
            item.setRendered(loginManager.getUtilisateur().getIdprofils().isTransactions());
            submenu.addElement(item);

            item = new DefaultMenuItem();
            item.setValue("LISTE DES TRANSACTIONS");
            item.setUrl("listTransaction.xhtml");
            item.setIcon("fa fa-money");
            item.setRendered(loginManager.getUtilisateur().getIdprofils().isTransactions());
            submenu.addElement(item);

            item = new DefaultMenuItem();
            item.setValue("COMMISSIONS");
            item.setUrl("commissions.xhtml");
            item.setIcon("fa fa-eur");
            item.setRendered(loginManager.getUtilisateur().getIdprofils().isListCommissions());
            submenu.addElement(item);

            //SEPARATOR  
            separator = new DefaultSeparator();
            separator.setRendered(loginManager.getUtilisateur().getIdprofils().isTransactions() || loginManager.getUtilisateur().getIdprofils().isListCommissions());

            model.addElement(submenu);
            model.addElement(separator);

            //REPORTING SUBMENU 
            submenu = new DefaultSubMenu();
            submenu.setLabel("REPORTING");
            submenu.setIcon("fa fa-file-text-o");
            submenu.setStyleClass("submenu");
            submenu.setRendered(loginManager.getUtilisateur().getIdprofils().getReporting());

            //REPORTING menu items
            item = new DefaultMenuItem();
            item.setValue("PRODUIRE LES ETATS");
            item.setUrl("reporting.xhtml");
            item.setIcon("fa fa-print");
            item.setRendered(loginManager.getUtilisateur().getIdprofils().getReporting());
            submenu.addElement(item);

            model.addElement(submenu);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public MenuModel getModel() {
        return model;
    }

    public void setModel(MenuModel model) {
        this.model = model;
    }

    public LoginManager getLoginManager() {
        return loginManager;
    }

    public void setLoginManager(LoginManager loginManager) {
        this.loginManager = loginManager;
    }

    public List<Operateurs> getListOperateurs() {
        
       return listOperateurs; 
    }

    public void setListOperateurs(List<Operateurs> listOperateurs) {
        this.listOperateurs = listOperateurs;
    }
    
    

}
