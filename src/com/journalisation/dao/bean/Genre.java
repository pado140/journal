package com.journalisation.dao.bean;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Map;

public class Genre extends ImplEntity {
    private ObjectProperty<String> nom;

    public Genre(Map<String, Object> data) {
        super(data);
    }

    public Genre() {
        super();
    }

    public String getNom() {
        return nom.get();
    }

    public ObjectProperty<String> nomProperty() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public void init() {

        nom=new SimpleObjectProperty<>();
    }

    @Override
    public String toString() {
        return getNom();
    }
}
