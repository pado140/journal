/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.dao.enumeration;

/**
 *
 * @author ADMIN
 */
public enum ProjectState {
    R("Running"),
    C("Complete"),
    I("Incomplete");
    
    private String val;
    ProjectState(String val){
        this.val=val;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }  
}
