/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.dao.bean;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.journalisation.utils.Permissions;

import java.util.Map;

/**
 *
 * @author Padovano
 */
public class Roles extends ImplEntity{
    private ObjectProperty<String> Name,Abbreviation;
    private ObjectProperty<Permissions> permission;
    private ObservableList<Integer> access;
    private ObservableList<String> menu;
    private ObservableList<String> action;

    @Override
    public void init() {
        this.Name = new SimpleObjectProperty<>();
        this.Abbreviation = new SimpleObjectProperty<>();
        this.permission = new SimpleObjectProperty<>();
        this.access = FXCollections.observableArrayList();
        this.menu = FXCollections.observableArrayList();
        this.action = FXCollections.observableArrayList();
    }

    public ObservableList<String> getMenu() {
        return menu;
    }

    public void setMenu(ObservableList<String> menu) {
        this.menu = menu;
    }

    public ObservableList<String> getAction() {
        return action;
    }

    public void setAction(ObservableList<String> action) {
        this.action = action;
    }

    public Roles(Map<String, Object> data) {
        super(data);
    }

    public Roles(Object... data) {
        super(data);
    }

    public Roles(ObjectProperty<Integer> id) {
        super(id);
    }

    public Roles() {
    }

    public String getName() {
        return Name.get();
    }

    public ObjectProperty<String> nameProperty() {
        return Name;
    }

    public void setName(String name) {
        this.Name.set(name);
    }

    public String getAbbreviation() {
        return Abbreviation.get();
    }

    public ObjectProperty<String> abbreviationProperty() {
        return Abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.Abbreviation.set(abbreviation);
    }

    public Permissions getPermission() {
        return permission.get();
    }

    public ObjectProperty<Permissions> permissionProperty() {
        return permission;
    }

    public void setPermission(Permissions permission) {
        this.permission.set(permission);
    }
    public void setPermission(String permission) {
        this.permission.set(Permissions.valueOf(permission.toUpperCase()));
    }

    public ObservableList<Integer> getAccess() {
        return access;
    }

    public void setAccess(ObservableList<Integer> access) {
        this.access = access;
    }

    public boolean addAll(Integer... elements) {
        return access.addAll(elements);
    }

    public boolean add(Integer e) {
        return access.add(e);
    }

    @Override
    public String toString() {
        return Name.get() + " (" + Abbreviation.get() + ")";
    }


    @Override
    public boolean isValid() {
        return false;
    }
}
