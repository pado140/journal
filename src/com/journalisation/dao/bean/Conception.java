package com.journalisation.dao.bean;

import javafx.beans.property.ObjectProperty;

import java.time.LocalDate;

public class Conception extends ImplEntity{
    private ObjectProperty<LocalDate> debut;
    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public void init() {

    }
}
