package com.journalisation.dao.bean;

import java.util.Map;

public class Concepteur extends Personnes {
    public Concepteur(Map<String, Object> data) {
        super(data);
    }

    @Override
    public boolean isValid() {
        return false;
    }
}
