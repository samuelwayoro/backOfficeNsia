/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.jsf.controllers;

import com.sbs.easymbank.dao.AbonnementsFacade;
import com.sbs.easymbank.dao.OperateursFacade;
import com.sbs.easymbank.dao.ParametresFacade;
import com.sbs.easymbank.entities.Abonnements;
import com.sbs.easymbank.entities.Operateurs;
import com.sbs.easymbank.entities.Parametres;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

/**
 *
 * @author alex
 */
@ManagedBean
@ViewScoped
public class ReportManager implements Serializable {
    
    @EJB
    private AbonnementsFacade abonnementsFacade;
    private Date borneInf,borneSup;
    private String telephone, racine, compte;
    private JasperPrint jasperPrint;
    private String operateurs="";
    private List<Abonnements> listAbonnements=new ArrayList<>();
    private String format;
    @EJB
    private ParametresFacade parametresFacade;
    @EJB
    private OperateursFacade operateursFacade;
    
    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat sdf3=new SimpleDateFormat("ddMMyy");
    private List<Operateurs> listOperateurs;
    private String entete;
    
         private Converter OperateursConverter = new Converter() {

        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {
            System.out.println("value:"+value);
            return operateursFacade.find(new BigDecimal(value));
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            //f(value!=null){
               System.out.println("value:"+value.toString());
                return ((Operateurs) value).getIdOperateur().toString();   
            // }
          // return null;
        }
    };

    
    public Date getBorneInf() {
        return borneInf;
    }

    public void setBorneInf(Date borneInf) {
        this.borneInf = borneInf;
    }

    public Date getBorneSup() {
        return borneSup;
    }

    public void setBorneSup(Date borneSup) {
        this.borneSup = borneSup;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public List<Operateurs> getListOperateurs() {
        return listOperateurs;
    }

    public void setListOperateurs(List<Operateurs> listOperateurs) {
        this.listOperateurs = listOperateurs;
    }

    public String getOperateurs() {
        return operateurs;
    }

    public void setOperateurs(String operateurs) {
        this.operateurs = operateurs;
    }

    public Converter getOperateursConverter() {
        return OperateursConverter;
    }

    public void setOperateursConverter(Converter OperateursConverter) {
        this.OperateursConverter = OperateursConverter;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRacine() {
        return racine;
    }

    public void setRacine(String racine) {
        this.racine = racine;
    }

    public String getCompte() {
        return compte;
    }

    public void setCompte(String compte) {
        this.compte = compte;
    }
    
    
    
    
    @PostConstruct
   private void initBean(){
       listOperateurs=operateursFacade.findAll();
   }

    public void init() throws JRException,FileNotFoundException{
        listAbonnements=abonnementsFacade.findAbonnementByPeriod(sdf.format(borneInf).substring(0, 10), sdf.format(borneSup).substring(0, 10),operateurs.trim(),compte, racine, telephone);
        for(Abonnements ab:listAbonnements)
            ab.setDesignationOperateur(ab.getOperateur().getDesignationOperateur());
        System.out.println("TOTAL ABONNEMENTS: "+listAbonnements.size());
        JRBeanCollectionDataSource beanCollectionDataSource= new JRBeanCollectionDataSource(listAbonnements);
        //String reportPath=FacesContext.getCurrentInstance().getExternalContext().getRealPath("abonnementReport2.jasper");
        String[] lCodePara = {"RECAP_SOUSCRIPTION", "ENTETE_ETAT"};
         List<Parametres> list=parametresFacade.findByCodeParamList(Arrays.asList(lCodePara));
        String reportPath="";
        String entete = "";
        for(Parametres p:list){
            if(p.getCodeparam().equals("RECAP_SOUSCRIPTION"))
                         reportPath=p.getValeur();
            else if(p.getCodeparam().equals("ENTETE_ETAT"))
                entete = p.getValeur();
        }
        if(reportPath.isEmpty())
         throw new FileNotFoundException("fichier "+reportPath+" introuvable");
        Map parametres=new HashMap();
        parametres.put("BORNE_INFERIEURE",sdf2.format(borneInf));
        parametres.put("BORNE_SUPERIEURE",sdf2.format(borneSup));
        if(!entete.isEmpty())
            parametres.put("ENTETE", entete);
        StringBuilder operateurConcatClient = new StringBuilder(operateurs);
        if(racine != null && !racine.isEmpty())
            operateurConcatClient.append(" RAC:").append(racine);
        if(compte != null && !compte.isEmpty())
            operateurConcatClient.append(" CPT:").append(compte);
        if(telephone != null && !telephone.isEmpty())
            operateurConcatClient.append(" TEL:").append(telephone);
        parametres.put("OPERATEUR",operateurConcatClient.toString());
        jasperPrint=JasperFillManager.fillReport(reportPath,parametres,beanCollectionDataSource);
    }
    
    public void PDF() throws JRException,IOException{
         init();
       HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
       httpServletResponse.addHeader("Content-disposition", "attachment; filename=report-"+sdf3.format(borneInf)+"-"+sdf3.format(borneSup)+".pdf");
       ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();
       JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
       FacesContext.getCurrentInstance().responseComplete();
        
        
    }
    
     public void DOCX() throws JRException, IOException{
        init();
       HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
       httpServletResponse.addHeader("Content-disposition", "attachment; filename=report-"+sdf3.format(borneInf)+"-"+sdf3.format(borneSup)+".docx");
       ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();
       JRDocxExporter docxExporter=new JRDocxExporter();
       docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
       docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
       docxExporter.exportReport();
       FacesContext.getCurrentInstance().responseComplete();
   }
     public void XLSX() throws JRException, IOException{
        init();
       HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
      httpServletResponse.addHeader("Content-disposition", "attachment; filename=report-"+sdf3.format(borneInf)+"-"+sdf3.format(borneSup)+".xlsx");
       ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();
       JRXlsxExporter docxExporter=new JRXlsxExporter();
       docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
       docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
       docxExporter.exportReport();
       FacesContext.getCurrentInstance().responseComplete();
   }
      public void ODT() throws JRException, IOException{
       init();
       HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
      httpServletResponse.addHeader("Content-disposition", "attachment; filename=report-"+sdf3.format(borneInf)+"-"+sdf3.format(borneSup)+".odt");
       ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();
       JROdtExporter docxExporter=new JROdtExporter();
       docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
       docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
       docxExporter.exportReport();
       FacesContext.getCurrentInstance().responseComplete();
   }
       public void PPT() throws JRException, IOException{
       init();
       HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
     httpServletResponse.addHeader("Content-disposition", "attachment; filename=report-"+sdf3.format(borneInf)+"-"+sdf3.format(borneSup)+".ppt");
       ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();
       JRPptxExporter docxExporter=new JRPptxExporter();
       docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
       docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
       docxExporter.exportReport();
       FacesContext.getCurrentInstance().responseComplete();
   }
    
    public void report(){
        try{
            System.out.println("FORMAT: "+format+" BORNE INF: "+borneInf);
            if(operateurs.equals("TOUT"))
                operateurs="";
            if(format.equals("PDF"))
                PDF();
            else if(format.equals("DOCX"))
                DOCX();
            else if(format.equals("XLSX"))
                XLSX();
            else if(format.equals("ODT"))
                ODT();
            else 
                PPT();
        }catch(JRException | IOException ex){
            ex.printStackTrace();
        }
    }
    
}
