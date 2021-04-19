package com.journalisation.dao.dao;

import javafx.collections.ObservableList;
import com.journalisation.dao.bean.Affiche;

public class AfficheModel extends ImplModel<Affiche> {
    private static AfficheModel model=null;

    public synchronized static AfficheModel getManager() {
        if(model==null)
            model=new AfficheModel();
        return model;
    }
    
    @Override
    public Affiche add(Affiche data) {
        return null;
    }

    @Override
    public Affiche change(Affiche data) {
        return null;
    }

    @Override
    public boolean remove(Affiche ob) {
        return false;
    }

    @Override
    public ObservableList<Affiche> searchBy(String conditions,boolean with, Object... critere) {
        return null;
    }

    @Override
    public Affiche exists(Affiche ob) {
        return null;
    }
}
