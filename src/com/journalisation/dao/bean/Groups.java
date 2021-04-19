/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.dao.bean;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.Map;

/**
 *
 * @author Padovano
 */
public class Groups extends ImplEntity{
    private ObjectProperty<String> name,Description;
    private ObjectProperty<ObservableList<Roles>> roles;
    private ObjectProperty<LocalDate> created;

    @Override
    public void init() {
        this.name = new SimpleObjectProperty<>();
        this.Description = new SimpleObjectProperty<>();
        this.roles = new SimpleObjectProperty<>();
        created=new SimpleObjectProperty<>();
    }

    public Groups(Map<String, Object> data) {
        super(data);
    }

    public Groups(Object... data) {
        super(data);
    }

    public Groups(ObjectProperty<Integer> id) {
        super(id);
    }

    public Groups() {
    }

    public boolean addRole(Roles role){
        return roles.get().add(role);
    }
    public boolean removeRole(Roles role){
        return roles.get().remove(role);
    }

    public String getName() {
        return name.get();
    }

    public ObjectProperty<String> nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDescription() {
        return Description.get();
    }

    public ObjectProperty<String> descriptionProperty() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description.set(description);
    }

    public ObservableList<Roles> getRoles() {
        return roles.get();
    }

    public ObjectProperty<ObservableList<Roles>> rolesProperty() {
        return roles;
    }

    public void setRoles(ObservableList<Roles> roles) {
        this.roles.set(roles);
    }

    public LocalDate getCreated() {
        return created.get();
    }

    public ObjectProperty<LocalDate> createdProperty() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created.set(created);
    }

    @Override
    public String toString() {
        return name.get();
    }

    @Override
    public boolean isValid() {
        return false;
    }
}
