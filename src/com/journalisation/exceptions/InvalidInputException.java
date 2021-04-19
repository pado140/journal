/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.exceptions;

import com.journalisation.resources.log.AppLog;

import java.util.logging.Level;

/**
 *
 * @author Padovano
 */
public class InvalidInputException extends RuntimeException{

    
    public InvalidInputException() {
    }

    public InvalidInputException(String message) {
        super(message);
        AppLog.Log(InvalidInputException.class.getName(), Level.SEVERE,message,null);
    }
    
}
