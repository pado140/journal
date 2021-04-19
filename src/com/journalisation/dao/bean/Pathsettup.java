/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.dao.bean;

import java.time.LocalDateTime;
import java.util.Map;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author ADMIN
 */
public class Pathsettup extends ImplEntity{
    private ObjectProperty<String> appPath,projetpath,createdby,modifiedby;
    private LocalDateTime created,modified;

    public String getApppath() {
        return appPath.get();
    }
    
    public ObjectProperty<String> apppathProperty() {
        return appPath;
    }

    public void setApppath(String appPath) {
        this.appPath.set(appPath);
    }

    public String getProjetpath() {
        return projetpath.get();
    }

    public ObjectProperty<String> projetpathProperty() {
        return projetpath;
    }

    public void setProjetpath(String projetpath) {
        this.projetpath.set(projetpath);
    }

    public String getCreatedby() {
        return createdby.get();
    }
    
    public ObjectProperty<String> createdbyProperty() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby.set(createdby);
    }

    public String getModifiedby() {
        return modifiedby.get();
    }

    public ObjectProperty<String> modifiedbyProperty() {
        return modifiedby;
    }

    public void setModifiedby(String modifiedby) {
        this.modifiedby.set(modifiedby);
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }
    
    
    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void marshall(Map<String, Object> data) {
        super.marshall(data); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void init() {
        appPath=new SimpleObjectProperty<>();
        projetpath=new SimpleObjectProperty<>("");
        createdby=new SimpleObjectProperty<>("");
        modifiedby=new SimpleObjectProperty<>("");
        
    }
    
}
