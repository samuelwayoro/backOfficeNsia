/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.filters;

import com.sbs.jsf.controllers.LoginManager;
import com.sbs.jsf.controllers.ParametrageManager;
import java.io.IOException;
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
public class SuperValidationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String contextPath = ((HttpServletRequest) request).getContextPath();
        LoginManager loginManager = (LoginManager) ((HttpServletRequest) request).getSession().getAttribute("loginManager");
       // ParametrageManager parametrageManager = (ParametrageManager) ((HttpServletRequest) request).getSession().getAttribute("parametrageManager");
        if (loginManager.getUtilisateur().getIdprofils().getSupervalidation()) {
            if (loginManager.getParametrageManager().getSupervalidation().getValeur().equals("SUPERVALIDATION_OUI")) {

                chain.doFilter(request, response);
            } else {
                ((HttpServletResponse) response).sendRedirect(contextPath + "/private/serviceindisponible.xhtml");

            }
        } else {
            ((HttpServletResponse) response).sendRedirect(contextPath + "/private/access-denied.xhtml");
        }

    }

    @Override
    public void destroy() {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
