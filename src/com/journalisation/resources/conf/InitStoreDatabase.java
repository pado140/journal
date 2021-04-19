/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.resources.conf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.nio.file.StandardOpenOption.CREATE;

/**
 *
 * @author Padovano
 */
public class InitStoreDatabase {
    private final static Properties ini=new Properties();
    private static OutputStream fileout;
    private static InitStoreDatabase instan=null;
    
    private InitStoreDatabase(){
        try {
            File f=new File(Paths.get(System.getenv("ProgramData"),"journalisation","conf","database.properties").toString());
            fileout=Files.newOutputStream(f.toPath(),CREATE);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(InitStoreDatabase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InitStoreDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private synchronized  static InitStoreDatabase load(){
        if(instan==null)
            instan=new InitStoreDatabase();
        return instan;
    }
    
    
    public static void propertyOf(String key,String value){
        load();
        
        ini.setProperty(key,value);
    }
    public static void save(){
        try {
            ini.store(fileout, null);
        } catch (IOException ex) {
            Logger.getLogger(Init.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
}
