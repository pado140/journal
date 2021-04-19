package com.journalisation.dao.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.journalisation.dao.bean.Auteurs;
import com.journalisation.utils.AppUtils;

import java.util.Map;

public class AuteurModel extends ImplModel<Auteurs> {

    private static AuteurModel model=null;

    public synchronized static AuteurModel getManager() {
        if(model==null)
            model=new AuteurModel();
        return model;
    }

    @Override
    public Auteurs add(Auteurs data) {
        requete="insert into auteur(nom,prenom,Notice,pathImage) values(?,?,?,?)";
        if(this.connectionSql.Update(requete,1,data.getNom(),data.getPrenom(),data.getNotice(),data.getPathimage())){
            data.setId(this.connectionSql.getLast());
            return data;
        }
        return null;
    }

    @Override
    public Auteurs change(Auteurs data) {
        requete="update auteur set nom=?,prenom=?,Notice=?,pathImage=? where id=?";
        if(this.connectionSql.Update(requete,0,data.getNom(),data.getPrenom(),data.getNotice(),data.getPathimage(),data.getId())){
            return data;
        }
        return null;
    }

    @Override
    public boolean remove(Auteurs ob) {
        return false;
    }

    @Override
    public ObservableList<Auteurs> searchBy(String conditions,boolean with, Object... critere) {
        ObservableList<Auteurs> auteurs= FXCollections.observableArrayList();
        requete="select * from Auteur";
        if(conditions!=null && !conditions.trim().isEmpty())
            requete += " where "+conditions;

        ObservableList<Map<String,Object>> data=this.connectionSql.selectlist(requete,critere);
        System.out.println();
        data.forEach(d->{
            Auteurs a=new Auteurs(d);
            if(a.getImage()==null)
                a.setPathimage(AppUtils.PATH_RES+"\\default.png");
            auteurs.add(a);
        });
        return auteurs;
    }

    @Override
    public Auteurs exists(Auteurs ob) {
        return select("nom=? and prenom=?",false,ob.getNom(),ob.getPrenom());
    }
}
