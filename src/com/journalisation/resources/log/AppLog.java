/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.resources.log;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author Padovano
 */
public class AppLog {
    private static String pattern="%h/journalisation.log";
    public static void Log(String name,Level level,String message,Throwable t){
        try {
            Logger log=Logger.getLogger(name);
            FileHandler fh=new FileHandler(pattern,0,1,true);
            fh.setFormatter(new SimpleFormatter());
            
            fh.flush();
            log.setUseParentHandlers(false);
            log.addHandler(fh);
            log.setLevel(level);
            if(level.equals(Level.ALL)){
            log.warning(message);
            log.info(message);
            log.severe(message);
            log.fine(message);
            log.throwing(name, message, t);
            }
            if(level.equals(Level.FINE))
                log.fine(message);
            if(level.equals(Level.INFO))
                log.info(message);
            if(level.equals(Level.SEVERE))
                log.severe(message);
            if(level.equals(Level.WARNING))
                log.warning(message);
            
            log.log(level, message,t);
            
        } catch (IOException | SecurityException ex) {
            Logger.getLogger(AppLog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
