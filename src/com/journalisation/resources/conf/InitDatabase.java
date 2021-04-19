/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.resources.conf;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.journalisation.resources.log.AppLog;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.journalisation.Main.service;

/**
 *
 * @author Padovano
 */
public class InitDatabase {
    private final static Properties ini=new Properties();
    private InputStream file=null;
    private static InitDatabase instan=null;
    
    private InitDatabase(){
        try {
            File f=new File(Paths.get(System.getenv("ProgramData"),"journalisation","conf","database.properties").toString());
            if(!f.exists()){
                new File(Paths.get(System.getenv("ProgramData"),"journalisation","conf").toString()).mkdirs();
                f.createNewFile();
            }
            file=Files.newInputStream(f.toPath(),StandardOpenOption.READ);
            ini.load(file);
        } catch (IOException ex) {
            AppLog.Log("path", Level.INFO, ex.getMessage(), ex);
            AppLog.Log("path", Level.INFO, "path:"+service.resolveURI(service.getDocumentBase(),"database.properties").substring(6), ex);
            Logger.getLogger(Init.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private synchronized  static InitDatabase load(){
        if(instan==null)
            instan=new InitDatabase();
        return instan;
    }
    
    public static ObservableList<String> lang(){
        load();
        String[] langs=ini.getProperty("langs").split(",");
        return FXCollections.observableArrayList(langs);
    }
    
    public static String default_lang(){
        load();
        return ini.getProperty("default_lang");
    }
    
    public static String default_View(){
        load();
        return ini.getProperty("default_layout");
    }
    
    public static String Achat_format(){
        load();
        return ini.getProperty("achat_format");
    }
    public static String Vente_format(){
        load();
        return ini.getProperty("default_layout");
    }
    public static String propertyOf(String key){
        load();
        String value="";
        try{
            value=ini.getProperty(key);
        }catch(NullPointerException e){
            value="";
        }
        return value;
    }
    
    
    
    
//    public static String default_View(){
//        load();
//        return ini.getProperty("default_layout");
//    }
//    public static String default_View(){
//        load();
//        return ini.getProperty("default_layout");
//    }

}