package com.journalisation.dao.bean;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;

import java.util.Map;

public class Users extends Personnes {
    private ObjectProperty<String> Username,pass,defaultpass,type;
    private ObservableList<Livres> projects;
    private ObjectProperty<Groups> groups;
    private ObjectProperty<Boolean> enable;

    public Users() {
    }

    public Users(Map<String, Object> data) {
        super(data);
    }

    public String getUsername() {
        return Username.get();
    }

    public ObjectProperty<String> usernameProperty() {
        return Username;
    }

    public void setUsername(String username) {
        this.Username.set(username);
    }

    public String getPass() {
        return pass.get();
    }

    public ObjectProperty<String> passProperty() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass.set(pass);
    }

    public String getType() {
        return type.get();
    }

    public ObjectProperty<String> typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    @Override
    public boolean isValid() {
        return false;
    }

    public String getDefaultpass() {
        return defaultpass.get();
    }

    public ObjectProperty<String> defaultpassProperty() {
        return defaultpass;
    }

    public void setDefaultpass(String defaultpass) {
        this.defaultpass.set(defaultpass);
    }

    public ObservableList<Livres> getProjects() {
        return projects;
    }

    public void setProjects(ObservableList<Livres> projects) {
        this.projects = projects;
    }

    public Groups getGroups() {
        return groups.get();
    }

    public ObjectProperty<Groups> groupProperty() {
        return groups;
    }

    public void setGroups(Groups group) {
        this.groups.set(group);
    }

    public Boolean getEnable() {
        return enable.get();
    }

    public ObjectProperty<Boolean> enableProperty() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable.set(enable);
    }

    @Override
    public void init() {
        super.init();
        Username=new SimpleObjectProperty<>();
        defaultpass=new SimpleObjectProperty<>();
        pass=new SimpleObjectProperty<>();
        groups=new SimpleObjectProperty<>();
        type=new SimpleObjectProperty<>();
        enable=new SimpleObjectProperty<>();
    }

    @Override
    public String toString() {
        return getUsername();
    }
}
