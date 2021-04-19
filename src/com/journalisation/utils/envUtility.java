/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.utils;

import java.util.Map;
import java.util.Properties;

/**
 *
 * @author ADMIN
 */
public class envUtility{
    private final Map<String,String> env=System.getenv();
    private final static Properties props=System.getProperties();
    
    public  String user_agent;
    
    public static Properties user_agent() {
        return props;
    }
}
