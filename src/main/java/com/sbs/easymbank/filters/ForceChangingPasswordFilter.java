/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.filters;

import com.sbs.jsf.controllers.LoginManager;
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
 * @author alex
 */
public class ForceChangingPasswordFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      String contextPath = ((HttpServletRequest) request).getContextPath();
        LoginManager loginManager=(LoginManager)((HttpServletRequest)request).getSession().getAttribute("loginManager");
        if(loginManager!=null && loginManager.isLoggedIn()){
            if(loginManager.isExpiratePassword()){
              // request.getRequestDispatcher( "/private/home.xhtml" ).forward( request, response );
               chain.doFilter(request, response);
            }else
              ((HttpServletResponse) response).sendRedirect(contextPath + "private/home.xhtml");  
            
        }else{
            
           ((HttpServletResponse) response).sendRedirect(contextPath + "/login.xhtml");
        }
    }
    

    @Override
    public void destroy() {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
