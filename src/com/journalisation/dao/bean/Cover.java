package com.journalisation.dao.bean;

import javafx.beans.property.ObjectProperty;
import javafx.scene.image.Image;

public class Cover extends Concept_type {
    private ObjectProperty<Image> cover;
    //private ObjectProperty<Image> cover;
    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public void init() {

    }
}
