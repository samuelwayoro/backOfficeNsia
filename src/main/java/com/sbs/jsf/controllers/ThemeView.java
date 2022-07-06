package com.sbs.jsf.controllers;

import com.sbs.easymbank.dao.ParametresFacade;
import com.sbs.easymbank.entities.Parametres;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class ThemeView implements Serializable {
    @EJB
    ParametresFacade parametresFacade;
 
    @PostConstruct
    private void init(){
           List<Parametres> lTheme=parametresFacade.findByCodeParam("MAIN_THEME");
            if(lTheme != null && !lTheme.isEmpty()){
                color=lTheme.get(0).getValeur();
            }
            avatarColor = color.replace("-", "");
 
    }
    
    private String color ;
    private String avatarColor;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAvatarColor() {
        return avatarColor;
    }

    public void setAvatarColor(String avatarColor) {
        this.avatarColor = avatarColor;
    }
    
    
    
    public void change() {
//       if(color.equals("green"))
//            this.color = null;
//        else
//            this.color = "-" + color;
        color=color=="-blue"? null:"-blue";
        System.out.println("THEME: "+color);
    }
    
    
}
