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
public enum Permissions {
    ADMIN("Admin","Able to manage all about this role"),
    MANAGER("MANAGER",""),
    CLERK("CLERK","Final User");
    
    private String Desc,name;
    Permissions(String name,String desc){
        Desc=desc;
        this.name=name;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String Desc) {
        this.Desc = Desc;
    }

    @Override
    public String toString() {
        return name ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Permissions of(String name){
        return Permissions.valueOf(name);
    }
}
