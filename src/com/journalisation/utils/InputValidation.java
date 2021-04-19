/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.utils;

/**
 *
 * @author Padovano
 */
public enum InputValidation {
    MAIL("[\\w\\.]+@[\\w\\.]+\\w{2,}","Veuillez saisir un Mail valide"),
    TEL("(\\+?\\(?\\d{1,3}\\)?)?\\d{7,8}","Veuillez saisir un numero de telephone valide"),
    NUMBER("\\d*\\.?\\d*","Veuillez saisir un nombre.(99.99)"),
    INTEGER("\\d*","Saisir un Entier");

    private String regex;
    private String errorMessage;
    private InputValidation(String regex,String errorMessage) {
        this.regex=regex;
        this.errorMessage=errorMessage;
    }

    public String getRegex() {
        return regex;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }
    
        
}
