package com.sbs.jsf.controllers;

import com.sbs.easymbank.dao.LogsFacade;
import com.sbs.easymbank.entities.Logs;
import com.sbs.easymbank.entities.Users;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import org.omnifaces.util.Faces;

public abstract class Controller
{
  
  
//  protected void logBD(String message, String login, String process, String machine)
//  {
//    System.out.println("Valeur de Date a enregistrer : " + new Date(System.currentTimeMillis()));
//    Logs tableAudit = new Logs();
//    tableAudit.setMachine(machine);
//    tableAudit.setDateLog(new Date(System.currentTimeMillis()));
//    tableAudit.setLogin(login);
//    tableAudit.setModule(process);
//    tableAudit.setMessage(message);
//    this.logsFacade.create(tableAudit);
//  }
  
 
  protected String getIPAddress()
  {
    return Faces.getRemoteAddr();
  }
  
  protected Users getUserInSession(String attr)
  {
    FacesContext context = FacesContext.getCurrentInstance();
    HttpSession session = (HttpSession)context.getExternalContext().getSession(true);
    Users user = (Users)session.getAttribute(attr);
    return user;
  }
  
  protected void addWarningMessage(String message)
  {
    FacesContext context = FacesContext.getCurrentInstance();
    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", message));
  }
  
  protected void addInfoMessage(String message)
  {
    FacesContext context = FacesContext.getCurrentInstance();
    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", message));
  }
  
  protected void addErrorMessage(String message)
  {
    FacesContext context = FacesContext.getCurrentInstance();
    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", message));
  }
  
  protected String getParam(String param)
  {
    FacesContext context = FacesContext.getCurrentInstance();
    Map<String, String> map = context.getExternalContext().getRequestParameterMap();
    String result = (String)map.get(param);
    
    return result;
  }
  
  protected Integer getParamId(String param)
  {
    Integer result = Integer.valueOf(getParam(param));
    
    return result;
  }
  
//  private LogsFacade lookupLogsFacadeBean()
//  {
//    try
//    {
//      Context c = new InitialContext();
//      return (LogsFacade)c.lookup("java:global/easymbank/LogsFacade");
//    }
//    catch (NamingException ne)
//    {
//      Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
//      throw new RuntimeException(ne);
//    }
//  }
}
