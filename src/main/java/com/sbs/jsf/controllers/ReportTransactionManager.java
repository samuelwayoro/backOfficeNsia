/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.jsf.controllers;

import com.sbs.easymbank.dao.OperateursFacade;
import com.sbs.easymbank.dao.ParametresFacade;
import com.sbs.easymbank.dao.TransactionsFacade;
import com.sbs.easymbank.dao.TransactionsReconciliationsFacade;
import com.sbs.easymbank.entities.Operateurs;
import com.sbs.easymbank.entities.Parametres;
import com.sbs.easymbank.entities.Transactions;
import com.sbs.easymbank.entities.TransactionsReconciliations;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

/**
 *
 * @author alex
 */
@ManagedBean
@ViewScoped
public class ReportTransactionManager implements Serializable {

    @EJB
    private TransactionsReconciliationsFacade transactionsReconciliationsFacade;
    @EJB
    private TransactionsFacade transactionsFacade;
    @EJB
    private ParametresFacade parametresFacade;
    @EJB
    private OperateursFacade operateursFacade;
    

    private Date borneInf,borneSup;
    private Date borneInfRap,borneSupRap;
    private String telephone, racine, compte;
    private JasperPrint jasperPrint;
    private List<Transactions> listTransactions;
    private List<TransactionsReconciliations> listTransactionsReconciliations;
    private String operateurs="";    
    private String operateursRap="";    
    private String format, formatRap;
    private List<String> listTypeTransact=new ArrayList<>();
    private String type;
    private String typeToDisplayOnReport;
    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat sdf3=new SimpleDateFormat("ddMMyy");
    private boolean onlySuccessfullTransactions = true;
    private Converter typeConverter=new Converter(){
        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {
           // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
          listTypeTransact.clear();
       if(value.equals("all")){
          listTypeTransact.add("bank2wallet");
          listTypeTransact.add("wallet2bank"); 
       }else
           listTypeTransact.add(value);
           
        return listTypeTransact;                         
           
       
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if(value instanceof ArrayList){
            List<String> l=(ArrayList<String>)value;
            if(l.size()==2 || l.isEmpty())
                return "all";
            else
                return l.get(0);
        }else
            return "";
        }
        
    };
    
        private List<Operateurs> listOperateurs;
    
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

    public List<Operateurs> getListOperateurs() {
        return listOperateurs;
    }

    public void setListOperateurs(List<Operateurs> listOperateurs) {
        this.listOperateurs = listOperateurs;
    }

    public Converter getOperateursConverter() {
        return OperateursConverter;
    }

    public void setOperateursConverter(Converter OperateursConverter) {
        this.OperateursConverter = OperateursConverter;
    }

         
    
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

    public List<String> getListTypeTransact() {
        return listTypeTransact;
    }

    public void setListTypeTransact(List<String> listTypeTransact) {
        this.listTypeTransact = listTypeTransact;
    }

    public Converter getTypeConverter() {
        return typeConverter;
    }

    public void setTypeConverter(Converter typeConverter) {
        this.typeConverter = typeConverter;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOperateurs() {
        return operateurs;
    }

    public void setOperateurs(String operateurs) {
        this.operateurs = operateurs;
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

    public Date getBorneInfRap() {
        return borneInfRap;
    }

    public void setBorneInfRap(Date borneInfRap) {
        this.borneInfRap = borneInfRap;
    }

    public Date getBorneSupRap() {
        return borneSupRap;
    }

    public void setBorneSupRap(Date borneSupRap) {
        this.borneSupRap = borneSupRap;
    }

    public String getOperateursRap() {
        return operateursRap;
    }

    public void setOperateursRap(String operateursRap) {
        this.operateursRap = operateursRap;
    }

    public String getFormatRap() {
        return formatRap;
    }

    public void setFormatRap(String formatRap) {
        this.formatRap = formatRap;
    }

    public boolean isOnlySuccessfullTransactions() {
        return onlySuccessfullTransactions;
    }

    public void setOnlySuccessfullTransactions(boolean onlySuccessfullTransactions) {
        this.onlySuccessfullTransactions = onlySuccessfullTransactions;
    }
    
        
        @PostConstruct
   private void initBean(){
       listOperateurs=operateursFacade.findAll();
   }

    
     public void init(boolean xlsxFormat) throws JRException, FileNotFoundException{
        listTransactions=transactionsFacade.findTransactionsByPeriod(sdf.format(borneInf).substring(0, 10), sdf.format(borneSup).substring(0, 10),listTypeTransact,operateurs.trim(),compte,racine,telephone);
        for(Transactions tr:listTransactions)
            tr.setDesignationOperateur(tr.getOperateurs().getDesignationOperateur());
        System.out.println("TOTAL TRANSACTIONS: "+listTransactions.size());
        if(listTransactions!=null && !listTransactions.isEmpty()){
            for(Transactions tr:listTransactions){
                tr.setTrandate(tr.getTrandate().substring(0,10));
            }
        }
        JRBeanCollectionDataSource beanCollectionDataSource= new JRBeanCollectionDataSource(listTransactions);
        
       // String reportPath=FacesContext.getCurrentInstance().getExternalContext().getRealPath("TransactionReport.jasper");
        List<Parametres> list=parametresFacade.findByCodeParam("RECAP_TRANSACTION");
        String reportPath="";
        if(!list.isEmpty())
         reportPath=list.get(0).getValeur();
        else throw new FileNotFoundException("fichier "+reportPath+" introuvable");
        Map parametres=new HashMap();
        parametres.put("BORNE_INFERIEURE",sdf2.format(borneInf));
        parametres.put("BORNE_SUPERIEURE",sdf2.format(borneSup));
        parametres.put("TYPE_TRANSACTION", typeToDisplayOnReport);
        //Ignore pagination for Excel report
        if(xlsxFormat)
            parametres.put(JRParameter.IS_IGNORE_PAGINATION, true);
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

     public void initRap(boolean xlsxFormat) throws JRException, FileNotFoundException{
        String status = onlySuccessfullTransactions ?  "000" : "%";
        listTransactionsReconciliations=transactionsReconciliationsFacade.findTransactionsByPeriod(sdf.format(borneInfRap).substring(0, 10), sdf.format(borneSupRap).substring(0, 10), status);
 //       System.out.println("TOTAL TRANSACTIONSREC: "+listTransactions.size());
//        if(listTransactionsReconciliations!=null && !listTransactionsReconciliations.isEmpty()){
//            for(TransactionsReconciliations tr:listTransactionsReconciliations){
//                tr.setTrandate(tr.getTrandate().substring(0,10));
//            }
//        }
        JRBeanCollectionDataSource beanCollectionDataSource= new JRBeanCollectionDataSource(listTransactionsReconciliations);
        
       // String reportPath=FacesContext.getCurrentInstance().getExternalContext().getRealPath("TransactionReport.jasper");
        List<Parametres> list=parametresFacade.findByCodeParam("RECAP_RAPPROCHEMENT");
        String reportPath="";
        if(!list.isEmpty())
         reportPath=list.get(0).getValeur();
        else throw new FileNotFoundException("fichier "+reportPath+" introuvable");
        Map parametres=new HashMap();   
        parametres.put("BORNE_INFERIEURE",sdf2.format(borneInfRap));
        parametres.put("BORNE_SUPERIEURE",sdf2.format(borneSupRap));
        //Ignore pagination for Excel report
        if(xlsxFormat)
            parametres.put(JRParameter.IS_IGNORE_PAGINATION, true);
        jasperPrint=JasperFillManager.fillReport(reportPath,parametres,beanCollectionDataSource);
    }

     
     public void PDF(boolean rapprochement) throws JRException,IOException{
       HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
       if(!rapprochement){
         init(false);
         httpServletResponse.addHeader("Content-disposition", "attachment; filename=reportTransaction-"+sdf3.format(borneInf)+"-"+sdf3.format(borneSup)+".pdf");                    
       }else{
           initRap(false);
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=reportRapprochement-"+sdf3.format(borneInfRap)+"-"+sdf3.format(borneSupRap)+".pdf");          
       }
       ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();
       JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
       FacesContext.getCurrentInstance().responseComplete();
        
       }
     
      public void DOCX(boolean rapprochement) throws JRException, IOException{
       HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
       if(!rapprochement){
         init(false);
         httpServletResponse.addHeader("Content-disposition", "attachment; filename=reportTransaction-"+sdf3.format(borneInf)+"-"+sdf3.format(borneSup)+".pdf");                    
       }else{
           initRap(false);
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=reportRapprochement-"+sdf3.format(borneInfRap)+"-"+sdf3.format(borneSupRap)+".pdf");          
       }
       ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();
       JRDocxExporter docxExporter=new JRDocxExporter();
       docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
       docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
       docxExporter.exportReport();
       FacesContext.getCurrentInstance().responseComplete();
   }
     public void XLSX(boolean rapprochement) throws JRException, IOException{
       HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
       if(!rapprochement){
         init(true);
         httpServletResponse.addHeader("Content-disposition", "attachment; filename=reportTransaction-"+sdf3.format(borneInf)+"-"+sdf3.format(borneSup)+".xlsx");                    
       }else{
           initRap(true);
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=reportRapprochement-"+sdf3.format(borneInfRap)+"-"+sdf3.format(borneSupRap)+".xlsx");          
       }
       ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();
       JRXlsxExporter exporter=new JRXlsxExporter();
       exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
       exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(servletOutputStream));
       SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration(); 
       configuration.setDetectCellType(true);
       configuration.setCollapseRowSpan(true);
       configuration.setOnePagePerSheet(true);
       configuration.setWhitePageBackground(true);
       configuration.setShowGridLines(true);
       configuration.setRemoveEmptySpaceBetweenColumns(true);
       configuration.setRemoveEmptySpaceBetweenRows(true);
       configuration.setColumnWidthRatio(2.0f);
       configuration.setCellLocked(false);
       configuration.setMaxRowsPerSheet(100);
       configuration.setSheetFooterLeft("");
       exporter.setConfiguration(configuration);
       exporter.exportReport();
       FacesContext.getCurrentInstance().responseComplete();
   }
      public void ODT(boolean rapprochement) throws JRException, IOException{
       HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
       if(!rapprochement){
         init(false);
         httpServletResponse.addHeader("Content-disposition", "attachment; filename=reportTransaction-"+sdf3.format(borneInf)+"-"+sdf3.format(borneSup)+".pdf");                    
       }else{
           initRap(false);
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=reportRapprochement-"+sdf3.format(borneInfRap)+"-"+sdf3.format(borneSupRap)+".pdf");          
       }
       ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();
       JROdtExporter docxExporter=new JROdtExporter();
       docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
       docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
       docxExporter.exportReport();
       FacesContext.getCurrentInstance().responseComplete();
   }
       public void PPT() throws JRException, IOException{
       init(false);
       HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
     httpServletResponse.addHeader("Content-disposition", "attachment; filename=reportTransaction-"+sdf3.format(borneInf)+"-"+sdf3.format(borneSup)+".ppt");
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
            listTypeTransact.clear();
            if(type.equals("all")){
                listTypeTransact.add("bank2wallet");
                listTypeTransact.add("wallet2bank");
                typeToDisplayOnReport="B2W ET W2B";
            }else if(type.equals("bank2wallet")){
               listTypeTransact.add(type); 
               typeToDisplayOnReport="B2W";
            }else{
                listTypeTransact.add(type);
                typeToDisplayOnReport="W2B";
            }
                if(operateurs.equals("TOUT"))
                operateurs="";
            
            
            if(format.equals("PDF"))
                PDF(false);
            else if(format.equals("DOCX"))
                DOCX(false);
            else if(format.equals("XLSX"))
                XLSX(false);
            else if(format.equals("ODT"))
                ODT(false);
            else 
               PPT();
        }catch(JRException | IOException ex){
            ex.printStackTrace();
        }
    }
      
      
            public void reportRapprochement(){
        try{
            System.out.println("FORMAT: "+formatRap+" BORNE INF: "+borneInfRap);
            if(formatRap.equals("PDF"))
                PDF(true);
            else if(formatRap.equals("DOCX"))
                DOCX(true);
            else if(formatRap.equals("XLSX"))
                XLSX(true);
            else if(formatRap.equals("ODT"))
                ODT(true);
            else 
               PPT();
        }catch(JRException | IOException ex){
            ex.printStackTrace();
        }
    }

     
      
    public void rapprochement(){
        
    }  
    

}