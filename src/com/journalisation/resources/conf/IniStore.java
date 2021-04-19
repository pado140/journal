package com.journalisation.resources.conf;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.nio.file.StandardOpenOption.CREATE;

public class IniStore {
    private final static Properties ini=new Properties();
    private static OutputStream fileout;
    private static IniStore instan=null;

    private IniStore(){

        try {
            File f=new File(Paths.get(System.getenv("ProgramData"),"journalisation","conf","init.properties").toString());
            if(!f.exists()){
                new File(Paths.get(System.getenv("ProgramData"),"journalisation","conf").toString()).mkdirs();
                f.createNewFile();
            }
            fileout=Files.newOutputStream(f.toPath(),StandardOpenOption.TRUNCATE_EXISTING,StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private synchronized  static IniStore load(){
        if(instan==null)
            instan=new IniStore();
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
            Logger.getLogger(IniStore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
