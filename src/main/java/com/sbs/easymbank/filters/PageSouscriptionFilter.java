/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.filters;

import com.sbs.easymbank.dao.PagesouscriptionFacade;
import com.sbs.easymbank.dao.ValeursparametresFacade;
import com.sbs.easymbank.entities.Pagesouscription;
import com.sbs.easymbank.entities.Valeursparametres;
import com.sbs.jsf.controllers.LoginManager;
import com.sbs.jsf.controllers.ParametrageManager;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.Persistence;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author SOCITECH-
 */
public class PageSouscriptionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String contextPath = ((HttpServletRequest) request).getContextPath();
        LoginManager loginManager = (LoginManager) ((HttpServletRequest) request).getSession().getAttribute("loginManager");
      //  ParametrageManager parametrageManager = (ParametrageManager) ((HttpServletRequest) request).getSession().getAttribute("parametrageManager");
        String url = ((HttpServletRequest) request).getRequestURI();
        if (loginManager.getUtilisateur().getIdprofils().getSouscription()) {
           // if (loginManager.getParametrageManager().getModeIdle().getValeur().equals("IDLE_NON")) {
                if (url.endsWith(loginManager.getSouscriptionPage())) {
                    chain.doFilter(request, response);

                } else {
                    ((HttpServletResponse) response).sendRedirect(contextPath + "/private/" + loginManager.getSouscriptionPage());
                }
           // }else{
           //     ((HttpServletResponse) response).sendRedirect(contextPath + "/private/serviceindisponible.xhtml");
          //  }

        } else {
            ((HttpServletResponse) response).sendRedirect(contextPath + "/private/access-denied.xhtml");
        }

    }

    @Override
    public void destroy() {
        //To change body of generated methods, choose Tools | Templates.
    }

}
