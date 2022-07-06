/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.jsf.controllers;

import com.sbs.easymbank.dao.PagesouscriptionFacade;
import com.sbs.easymbank.dao.ParametresFacade;
import com.sbs.easymbank.dao.ValeursparametresFacade;
import com.sbs.easymbank.entities.Pagesouscription;

import com.sbs.easymbank.entities.Parametres;
import com.sbs.easymbank.entities.Valeursparametres;
import com.sbs.easymbank.service.omapi.IdlePort_PortType;
import com.sbs.easymbank.service.omapi.IdleServiceLocator;
import com.sbs.easymbank.service.omapi.RegisterPort_PortType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.xml.rpc.ServiceException;

/**
 *
 * @author SOCITECH-
 */
@ManagedBean
@ApplicationScoped
public class SettingsManager implements Serializable {

    private List<Parametres> listPara;
    private List<Valeursparametres> listValeursSouscription;
    private List<Pagesouscription> listPagesouscription;
    private String libelle;
    private boolean idle;
//    private Converter modeSouscriptionConverter=new Converter() {
//
//        @Override
//        public Object getAsObject(FacesContext context, UIComponent component, String value) {
//           
//            Valeursparametres laValeur=new Valeursparametres();
//            for(Valeursparametres val:listValeursSouscription){
//                if(val.getLibellevaleur().equals(value))
//                    System.out.println("LA VALEUR: "+value );
//                    laValeur=val;
//                    break; 
//            }
//            return laValeur;
//        }
//
//        @Override
//        public String getAsString(FacesContext context, UIComponent component, Object value) {
//            
//            return ((Valeursparametres)value).toString();
//        }
//    };

    private Valeursparametres selectedModeSouscription;
    private Valeursparametres modeIdle;
    private Pagesouscription selectedPageSouscription;
    @EJB
    ParametresFacade parametresFacade;
    @EJB
    ValeursparametresFacade valeursparametresFacade;
    @EJB
    PagesouscriptionFacade pagesouscriptionFacade;

    public SettingsManager()  {
    }

    @PostConstruct
    public void init() {
        //listPara=parametresFacade.findAll();
        // valParamSouscription=new Valeursparametres();
        listValeursSouscription = valeursparametresFacade.findByCodeParam(BigDecimal.ONE);
        modeIdle = valeursparametresFacade.findByCodeParam(new BigDecimal("2")).get(0);
        listPagesouscription = pagesouscriptionFacade.findAll();

        for (Valeursparametres val : listValeursSouscription) {
            if (val.getSelected()) {
                selectedModeSouscription = val;
                break;
            }
        }
        reglerPageSouscription();
        if (selectedModeSouscription == null) {
            selectedModeSouscription = new Valeursparametres();
            selectedPageSouscription = listPagesouscription.get(0);
        }
        System.out.println("IDLE: "+modeIdle.getLibellevaleur()+" "+modeIdle.getSelected());
        idle=!modeIdle.getSelected();

    }

    public List<Parametres> getListPara() {
        return listPara;
    }

    public void setListPara(List<Parametres> listPara) {
        this.listPara = listPara;
    }

    public List<Valeursparametres> getListValeursSouscription() {
        return listValeursSouscription;
    }

    public void setListValeursSouscription(List<Valeursparametres> listValeursSouscription) {
        this.listValeursSouscription = listValeursSouscription;
    }

    public Valeursparametres getSelectedModeSouscription() {
        return selectedModeSouscription;
    }

    public void setSelectedModeSouscription(Valeursparametres selectedModeSouscription) {
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

    public Valeursparametres getModeIdle() {
        return modeIdle;
    }

    public void setModeIdle(Valeursparametres modeIdle) {
        this.modeIdle = modeIdle;
    }

    public void enregistrerModeSouscription() {
        for (Valeursparametres val : listValeursSouscription) {
            if (val.getLibellevaleur().equals(libelle)) {
                val.setSelected(true);
                valeursparametresFacade.edit(val);
                selectedModeSouscription = val;
                System.out.println("VALEUR" + val.getCodevaleur() + ": " + val.getLibellevaleur());

            } else {
                val.setSelected(false);
                valeursparametresFacade.edit(val);
            }
        }
        // selectedModeSouscription.setLibellevaleur(libelle);
        // System.out.println("MODE SELECTIONNE: "+selectedModeSouscription.getLibellevaleur());
        reglerPageSouscription();
    }

    public void modifierDisponibilite() {
        boolean ok = true;
//        IdleServiceLocator locator=new IdleServiceLocator();
//        try {
//            String indispo= idle ? "false":"true";
//            IdlePort_PortType rgpt=locator.getIdlePort();
//            ok=rgpt.setIdle(indispo);
//        } catch (Exception ex) {
//           Logger.getLogger(SettingsManager.class.getName()).log(Level.SEVERE, null, ex);
//        }
        if (!ok) {
            idle = !idle;
            addMessage("Le serveur ne peut changer d'état en ce moment","DISPONIBILITE");
        } else {
            addMessage("Le serveur a changé d'état avec succès","DISPONIBILITE");
        }

        modeIdle.setSelected(!idle);
        reglerPageSouscription();
        valeursparametresFacade.edit(modeIdle);
       // List<Object> l=new ArrayList();

        //System.out.println(valeursparametresFacade.test_procedure());
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    //Met à jour la page de souscription en fonction du mode de souscription
    public void reglerPageSouscription() {
        if (modeIdle.getSelected()) {
            for (Pagesouscription page : listPagesouscription) {
                if (page.getMode().contains("IDLE")) {
                    System.out.println(page.getMode());
                    selectedPageSouscription = page;
                    break;
                }
            }
        } else {
            for (Pagesouscription page : listPagesouscription) {
                if (page.getMode().equals(selectedModeSouscription.getLibellevaleur())) {
                    selectedPageSouscription = page;
                    break;
                }

            }
        }

    }
    
    

}
