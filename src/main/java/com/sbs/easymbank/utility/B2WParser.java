/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.utility;

import com.sbs.easymbank.entities.AbonnementsReconciliations;
import com.sbs.easymbank.entities.TransactionsReconciliations;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.json.JSONArray;

/**
 *
 * @author SOCITECH-
 */
public class B2WParser {
    
    private String flux;
    private File fichier;

    public B2WParser() {
        
    }

    public String getFlux() {
        return flux;
    }

    public void setFlux(String flux) {
        this.flux = flux;
    }

    public File getFichier() {
        return fichier;
    }

    public void setFichier(File fichier) {
        this.fichier = fichier;
    }
    
    

    public B2WParser(String flux) {
        this.flux = flux;
    }
    
    public List<TransactionsReconciliations> parseTransaction() throws Exception{
      InputStream ips= new FileInputStream(fichier);
         SAXReader xmlReader = new SAXReader();
        Document doc = xmlReader.read(ips);
        List<TransactionsReconciliations> listTransact=new ArrayList<>();
       
        List<Node> transactNode=doc.selectNodes("/eod/partnerRequests/partnerRequest");
        
        for(Node node:transactNode){
            TransactionsReconciliations tr=new TransactionsReconciliations();
            tr.setAccountalias(node.selectSingleNode("alias").getText());
            tr.setRequesttype(node.selectSingleNode("type").getText());
            tr.setAmount(node.selectSingleNode("amount").getText().replace(".00", ""));
            tr.setRequestid(node.selectSingleNode("requestCode").getText());
            tr.setResponsecode(node.selectSingleNode("responseCode").getText());
            tr.setTrandate(node.selectSingleNode("requestDate").getText());
            listTransact.add(tr);
            
        }
        return listTransact;
    }
    
    
      public JsonArray parseTransactionToJSON() throws Exception{
      InputStream ips= new FileInputStream(fichier);
         SAXReader xmlReader = new SAXReader();
        Document doc = xmlReader.read(ips);
        JsonArrayBuilder jb = Json.createArrayBuilder();
        List<TransactionsReconciliations> listTransact=new ArrayList<>();
        
       
        List<Node> transactNode=doc.selectNodes("/eod/partnerRequests/partnerRequest");
        
        for(Node node:transactNode){
            
            JsonObjectBuilder jpb = Json.createObjectBuilder().
                    add("accountalias", node.selectSingleNode("alias").getText()).
                    add("requesttype", node.selectSingleNode("type").getText()).
                    add("amount", node.selectSingleNode("amount").getText()). 
                    add("requestid", node.selectSingleNode("requestCode").getText()).
                    add("responsecode", node.selectSingleNode("responseCode").getText()).
                    add("setTrandate", node.selectSingleNode("requestDate").getText());     
                jb.add(jpb);
            
//            TransactionsReconciliations tr=new TransactionsReconciliations();
//            tr.setAccountalias(node.selectSingleNode("alias").getText());
//            tr.setRequesttype(node.selectSingleNode("type").getText());
//            tr.setAmount(node.selectSingleNode("amount").getText());
//            tr.setRequestid(node.selectSingleNode("requestCode").getText());
//            tr.setResponsecode(node.selectSingleNode("responseCode").getText());
//            tr.setTrandate(node.selectSingleNode("requestDate").getText());
//            listTransact.add(tr);
            
        }
         return jb.build();
    }

    
    public List<AbonnementsReconciliations> parseAbonnement() throws DocumentException{
      InputStream ips= getClass().getClassLoader().getResourceAsStream(flux);
         SAXReader xmlReader = new SAXReader();
        Document doc = xmlReader.read(ips);
        List<AbonnementsReconciliations> listAbonnements=new ArrayList<>();
       
        List<Node> transactNode=doc.selectNodes("/eod/aliasesHistory/aliasHistory");
        
        for(Node node:transactNode){
            AbonnementsReconciliations ab=new AbonnementsReconciliations();
            ab.setAlias(node.selectSingleNode("alias").getText());
            String date=node.selectSingleNode("requestDate").getText();
            date=date.replace("T", " ").substring(0,19);
            if(node.selectSingleNode("type").getText().equals("register")){
                ab.setCoderetour(node.selectSingleNode("responseCode").getText());
                ab.setDatecreation(date);
            }else{
                ab.setCoderetourresiliation(node.selectSingleNode("responseCode").getText());
                ab.setDateresiliation(date);
            }
            listAbonnements.add(ab);
            
        }
        return listAbonnements;
    }


    
    public Map<String,String> parseDELRES()throws Exception{
        Map reponse=new HashMap();
        //log.info("Creating XML Reader");
        SAXReader xmlReader = createXmlReader();
        Document doc = xmlReader.read(flux);
        //log.info("Parsing XML Response");
        Node node=doc.selectSingleNode("/s:Envelope/s:Body/o:ombClose");
        reponse.put("alias",node.selectSingleNode("alias").getText());
        reponse.put("return_code",node.selectSingleNode("return_code").getText());
        
        return reponse;
    }
    
    private SAXReader createXmlReader() {
        Map<String, String> uris = new HashMap<String, String>();
        uris.put("s", "http://schemas.xmlsoap.org/soap/envelope/");
        uris.put("o", "http://mbanking.com");
        DocumentFactory factory = new DocumentFactory();
        factory.setXPathNamespaceURIs(uris);
        SAXReader xmlReader = new SAXReader();
        xmlReader.setDocumentFactory(factory);
        return xmlReader;   
     }
}
