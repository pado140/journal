package com.journalisation.resources.conf;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Init {
    private final static Properties ini=new Properties();
    private InputStream file=null;
    private static Init instan=null;

    private Init(){
        try {
            File f=new File(Paths.get(System.getenv("ProgramData"),"journalisation","conf","init.properties").toString());
            if(!f.exists()){
                new File(Paths.get(System.getenv("ProgramData"),"journalisation","conf").toString()).mkdirs();
                f.createNewFile();
            }
            file= Files.newInputStream(Paths.get(System.getenv("ProgramData"),"journalisation","conf","init.properties"));
            ini.load(file);
        } catch (IOException ex) {
            Logger.getLogger(Init.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private synchronized  static Init load(){
        instan=new Init();
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

}
