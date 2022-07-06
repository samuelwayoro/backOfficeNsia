/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.jsf.controllers;

import com.sbs.easymbank.dao.LogsFacade;
import com.sbs.easymbank.entities.Logs;
import com.sbs.jsf.model.LazyLogDataModel;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author alex
 */
@ManagedBean
@ViewScoped
public class AuditManager implements Serializable {

    @EJB
    LogsFacade logsFacade;
    private LazyDataModel<Logs> listLogs;
    private List<Logs> listLogsFiltered;
    @ManagedProperty(value = "#{loginManager}")
    private LoginManager loginManager;

    @PostConstruct
    private void init() {
        // listLogs= logsFacade.findAllLogs();
        if (loginManager.getUtilisateur().getIdprofils().isAudit_ttes_age()) {
            listLogs = new LazyLogDataModel(logsFacade.findAllLogs());
        } else {
            listLogs = new LazyLogDataModel(logsFacade.findLogsByAgence(loginManager.getUtilisateur().getIdagences().getIdagences()));
        }
        try {
            for (Logs logs : ((LazyLogDataModel) listLogs).getDatasource()) {
                logs.setAgence(logs.getUsers().getIdagences().getLibelle());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public LogsFacade getLogsFacade() {
        return logsFacade;
    }

    public void setLogsFacade(LogsFacade logsFacade) {
        this.logsFacade = logsFacade;
    }

    public LazyDataModel<Logs> getListLogs() {
        return listLogs;
    }

    public void setListLogs(LazyDataModel<Logs> listLogs) {
        this.listLogs = listLogs;
    }

    public List<Logs> getListLogsFiltered() {
        return listLogsFiltered;
    }

    public void setListLogsFiltered(List<Logs> listLogsFiltered) {
        this.listLogsFiltered = listLogsFiltered;
    }

    public LoginManager getLoginManager() {
        return loginManager;
    }

    public void setLoginManager(LoginManager loginManager) {
        this.loginManager = loginManager;
    }

}
