package com.journalisation.dao.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.journalisation.dao.bean.Genre;

import java.util.Map;

public class GenreModel extends ImplModel<Genre> {
     
    private static GenreModel model=null;

    public synchronized static GenreModel getManager() {
        if(model==null)
            model=new GenreModel();
        return model;
    }
    
    @Override
    public Genre add(Genre data) {
        requete="insert into genre(nom) values(?)";
        if(this.connectionSql.Update(requete,1,data.getNom())){
            data.setId(this.connectionSql.getLast());
            return data;
        }
        return null;
    }

    @Override
    public Genre change(Genre data) {
        return null;
    }

    @Override
    public boolean remove(Genre ob) {
        return false;
    }

    @Override
    public ObservableList<Genre> searchBy(String conditions,boolean with, Object... critere) {
        ObservableList<Genre> genres= FXCollections.observableArrayList();
        requete="select nom,id from genre";
        if(conditions!=null && !conditions.trim().isEmpty())
            requete += " where "+conditions;

        ObservableList<Map<String,Object>> data=this.connectionSql.selectlist(requete,critere);
        System.out.println();
        data.forEach(d->{
            genres.add(new Genre(d));
        });
        return genres;
    }

    @Override
    public Genre exists(Genre ob) {
        return select("nom=?",false,ob.getNom());
    }
}
