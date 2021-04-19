/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.alert;

/**
 *
 * @author Padovano
 */
public enum AlertButtons {
    YES("Oui"),
    NO("Non"),
    CANCEL("Annuler"),
    YES_NO("Oui Non"),
    YES_NO_CANCEL("Oui Non Annuler"),
    OK("Ok"),
    OK_NO_CANCEL("Ok Non Annuler"),
    YES_CANCEL("Oui Annuler"),
    NO_CANCEL("Non Annuler");
    
    private String value;

    private AlertButtons(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    
}