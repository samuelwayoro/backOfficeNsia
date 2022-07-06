/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.jsf.controllers;

import com.sbs.easymbank.entities.Connexion;
import com.sbs.easymbank.entities.Users;
import com.sbs.easymbank.filters.UtilisateurFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.servlet.http.HttpSession;

/**
 *
 * @author alexa
 */
@ManagedBean
@ApplicationScoped
public class AppManager implements Serializable{
    List<Connexion> connexions;
    
    @PostConstruct
    public void init(){
      connexions = new ArrayList<>();  
    }

    public List<Connexion> getConnexions() {
        return connexions;
    }

    public void setConnexions(List<Connexion> connexions) {
        this.connexions = connexions;
    }
    
}
