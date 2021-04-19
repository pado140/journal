/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.dao.dao;

import com.journalisation.dao.bean.Pathsettup;
import javafx.collections.ObservableList;

/**
 *
 * @author ADMIN
 */
public class PathsettupModel extends ImplModel<Pathsettup>{

    private static PathsettupModel model=null;

    public synchronized static PathsettupModel getManager() {
        if(model==null)
            model=new PathsettupModel();
        return model;
    }
    
    @Override
    public Pathsettup add(Pathsettup data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Pathsettup change(Pathsettup data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean remove(Pathsettup ob) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ObservableList<Pathsettup> searchBy(String conditions,boolean with, Object... critere) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Pathsettup exists(Pathsettup ob) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
