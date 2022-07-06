package com.sbs.jsf.controllers;

import com.sbs.easymbank.dao.ProfilsFacade;
import com.sbs.easymbank.entities.Profils;
import com.sbs.jsf.model.LazyProfilDataModel;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

@ManagedBean
@ViewScoped
public class ProfilsManager
        extends Controller
        implements Serializable {

    private LazyDataModel<Profils>  listProfils;
    List<Profils> listProfilsFiltered;
    private Profils newProfils=new Profils() ;
    private Profils selectedProfil;
    @EJB
    private ProfilsFacade profilsFacade;
    @ManagedProperty(value = "#{sessionManager}")
    private SessionManager sessionManager;

    @PostConstruct
    public void init() {
       // this.listProfils = this.profilsFacade.findAll();
       listProfils=new LazyProfilDataModel(profilsFacade.findAll());
    }

    public LazyDataModel<Profils> getListProfils() {
        if (this.listProfils == null) {
            listProfils=new LazyProfilDataModel(profilsFacade.findAll());
        }
        return this.listProfils;
    }

    public void setListProfils(LazyDataModel<Profils> listProfils) {
        this.listProfils = listProfils;
    }

    public List<Profils> getListProfilsFiltered() {
        return this.listProfilsFiltered;
    }

    public void setListProfilsFiltered(List<Profils> listProfilsFiltered) {
        this.listProfilsFiltered = listProfilsFiltered;
    }

    public Profils getNewProfils() {
        return this.newProfils;
    }

    public void setNewProfils(Profils newProfils) {
        this.newProfils = newProfils;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }
    
    

    public void removeProfil(Profils profil) {
       // System.out.println("removeProfil");

        boolean success = this.profilsFacade.removeProfil(profil);
        if (success) {
            ((LazyProfilDataModel)listProfils).getDatasource().remove(profil);
            sessionManager.getLogs().setAction("SUPPRESSION DE PROFIL");
                sessionManager.getLogs().setDateLog(new Date());
                sessionManager.getLogs().setMessage("LIBELLE PROFIL: " + profil.getLibelle());
                sessionManager.logDB();
        }
        newProfils = new Profils();
      //  System.out.println("Fin ");
    }

    public void creerProfils() {
        //System.out.println("creerProfils");

        Profils unProfil = this.profilsFacade.findProfilFromLibelle(this.newProfils.getLibelle());
        if ((unProfil != null) && (unProfil.getLibelle().equalsIgnoreCase(this.newProfils.getLibelle()))) {
            addErrorMessage("Profil Existant");
        } else if (newProfils.getIdprofils() == null) {
            //reation
            newProfils.setLibelle(newProfils.getLibelle().toUpperCase());
            if (!newProfils.getLibelle().isEmpty() && (newProfils.getConnexion() == true || newProfils.getCreationAgence() == true || newProfils.getCreationProfils() == true || newProfils.getCreationUtilisateur() == true || newProfils.getModificationProfils() == true
                    || newProfils.getParametrage() == true || newProfils.getReinitialisationUtilisateur() == true || newProfils.getResiliation() == true || newProfils.getResiliation() == true || newProfils.getSouscription() == true || newProfils.getSuppressionAgence() == true
                    || newProfils.getSupervalidation() || newProfils.getReporting() || newProfils.isAudits() || newProfils.isCreationCommissions() || newProfils.isCreationProfilsClients() || newProfils.isListAbonnements() || newProfils.isListCommissions() || newProfils.isSecurite() || newProfils.isSuppressionCommissions() || newProfils.isSuppressionProfilsClients() || newProfils.isTransactions())) {
                profilsFacade.create(newProfils);
                sessionManager.getLogs().setAction("CREATION DE PROFIL");
                sessionManager.getLogs().setDateLog(new Date());
                sessionManager.getLogs().setMessage("LIBELLE PROFIL: " + newProfils.getLibelle());
                sessionManager.logDB();

               ((LazyProfilDataModel)listProfils).getDatasource().add(newProfils);
                newProfils = new Profils();
            } else {
                addErrorMessage("Entrez des valeurs de droits");
            }

        } else {
            //modification 
          //  System.out.println("newProfils : " + newProfils.getConnexion());
            if (!newProfils.getLibelle().isEmpty() && (newProfils.getConnexion() == true || newProfils.getCreationAgence() == true || newProfils.getCreationProfils() == true || newProfils.getCreationUtilisateur() == true || newProfils.getModificationProfils() == true
                    || newProfils.getParametrage() == true || newProfils.getReinitialisationUtilisateur() == true || newProfils.getResiliation() == true || newProfils.getResiliation() == true || newProfils.getSouscription() == true || newProfils.getSuppressionAgence() == true
                    || newProfils.getSupervalidation() || newProfils.getReporting()) || newProfils.isAudits() || newProfils.isCreationCommissions() || newProfils.isCreationProfilsClients() || newProfils.isListAbonnements() || newProfils.isListCommissions() || newProfils.isSecurite() || newProfils.isSuppressionCommissions() || newProfils.isSuppressionProfilsClients() || newProfils.isTransactions()) {
                profilsFacade.edit(newProfils);
                sessionManager.getLogs().setAction("MODIFICATION DE PROFIL");
                sessionManager.getLogs().setDateLog(new Date());
                sessionManager.getLogs().setMessage("LIBELLE PROFIL: " + newProfils.getLibelle());
                sessionManager.logDB();
            } else {
                addErrorMessage("Entrez des valeurs de droits");
            }

        }
    }

    public void resetProfil() {
      //  System.out.println("resetProfil");

        newProfils = new Profils();
    }

    public void onRowSelect(SelectEvent event) {
        Profils p = (Profils) event.getObject();
        newProfils = p;
//        System.out.println("onRowSelect getIdprofils" + p.getIdprofils());
//        System.out.println("onRowSelect getConnexion()" + p.getConnexion());
//        System.out.println("onRowSelect getCreationAgence" + p.getCreationAgence());
//        System.out.println("onRowSelect getCreationProfils" + p.getCreationProfils());
//        System.out.println("onRowSelect getCreationUtilisateur" + p.getCreationUtilisateur());
//        System.out.println("onRowSelect getModificationProfils" + p.getModificationProfils());
//        System.out.println("onRowSelect getParametrage" + p.getParametrage());
//        System.out.println("onRowSelect getReinitialisationUtilisateur" + p.getReinitialisationUtilisateur());
//        System.out.println("onRowSelect getResiliation" + p.getResiliation());

        FacesMessage msg = new FacesMessage("Profils Selected", ((Profils) event.getObject()).getIdprofils().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public Profils getSelectedProfil() {
        return this.selectedProfil;
    }

    public void setSelectedProfil(Profils selectedProfil) {
        this.selectedProfil = selectedProfil;
    }
}
