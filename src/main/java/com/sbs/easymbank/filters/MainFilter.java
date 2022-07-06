/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.filters;

import com.sbs.jsf.controllers.LoginManager;
import java.io.IOException;
import java.util.Calendar;
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
public class MainFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String contextPath = ((HttpServletRequest) request).getContextPath();
        // System.out.println("TRACE "+this.getClass().toString()+"init()"+Calendar.getInstance().getTime());
        LoginManager loginManager=(LoginManager)((HttpServletRequest)request).getSession().getAttribute("loginManager");
        if(loginManager!=null && loginManager.isLoggedIn()){
            if(!loginManager.isExpiratePassword()){
                chain.doFilter(request, response);
            } else {
                ((HttpServletResponse) response).sendRedirect(contextPath + "/resetpassword.xhtml");
            }
        } else {

            ((HttpServletResponse) response).sendRedirect(contextPath + "/login.xhtml");
        }
    }

    @Override
    public void destroy() {
        //To change body of generated methods, choose Tools | Templates.
    }

}
