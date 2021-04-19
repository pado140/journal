package com.journalisation.dao.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import com.journalisation.Main;
import com.journalisation.dao.bean.Auteurs;
import com.journalisation.dao.bean.Genre;
import com.journalisation.dao.bean.Livres;

import java.time.format.DateTimeFormatter;
import java.util.Map;

public class LivreModel extends ImplModel<Livres> {
    
    private static LivreModel model=null;

    public synchronized static LivreModel getManager() {
        if(model==null)
            model=new LivreModel();
        return model;
    }
    
    @Override
    public Livres add(Livres data) {
        requete="insert into livre(titre,soustitre,format,receipt,genre_id) values (?,?,?,?,?)";
        if(this.connectionSql.Update(requete,1,data.getTitre(),data.getSoustitre(),data.getFormat(),data.getReceipt().format(DateTimeFormatter.ISO_DATE),data.getGenre().getId())){
            data.setId(this.connectionSql.getLast());
            trace(Main.users,String.format("Creation du Projet(%s)",data.getTitre()),"0","0");
            for(Auteurs auteur:data.getDirige()) {
                this.connectionSql.Update("insert into diriger(ref_auteur,ref_livre) values(?,?)",0,auteur.getId(),data.getId() );
                //trace(Main.users,String.format("Creation du Projet(%s)",data.getTitre()));
            }
            for(Auteurs auteur:data.getEcrit()) {
                this.connectionSql.Update("insert into ecrire(ref_auteur,ref_livre) values(?,?)",0,auteur.getId(),data.getId() );
            }
            return data;
        }
        return null;
    }

    @Override
    public Livres change(Livres data) {
        requete="update livre titre=?,soustitre=?,format=?,receipt=?,genre_id=? where id=?";
        if(this.connectionSql.Update(requete,1,data.getTitre(),data.getSoustitre(),data.getFormat(),data.getReceipt().format(DateTimeFormatter.ISO_DATE),
                data.getGenre().getId(),data.getId())){
            trace(Main.users,String.format("modification du Projet(%s)",data.getTitre()),"NULL","NULL");
            for(Auteurs auteur:data.getDirige()) {
                this.connectionSql.Update("insert into diriger(ref_auteur,ref_livre) values(?,?)",0,auteur.getId(),data.getId() );
                //trace(Main.users,String.format("Creation du Projet(%s)",data.getTitre()));
            }
            for(Auteurs auteur:data.getEcrit()) {
                this.connectionSql.Update("insert into ecrire(ref_auteur,ref_livre) values(?,?)",0,auteur.getId(),data.getId() );
            }
            return data;
        }
        return null;
    }

    @Override
    public boolean remove(Livres ob) {
        return false;
    }

    @Override
    public ObservableList<Livres> searchBy(String conditions,boolean with, Object... critere) {
        ObservableList<Livres> livres= FXCollections.observableArrayList();
        requete="select id,titre,soustitre,format,genre_id,receipt,genre_nom from livres";
        if(conditions!=null && !conditions.trim().isEmpty())
            requete += " where "+conditions;

        ObservableList<Map<String,Object>> data=this.connectionSql.selectlist(requete,critere);
        System.out.println();
        data.forEach(d->{
            Livres livre=new Livres(d);
            System.out.println(livre.getGenre());
            //livre.setGenre((Genre)DAOFactory.createModel(DAOName.genre).select("id=?",livre.getGenre().getId()));
            livres.add(livre);
        });
        return livres;
    }

    @Override
    public Livres exists( Livres ob) {
        return select("titre=? and soustitre=?",false,ob.getTitre(),ob.getSoustitre());
    }
//
//    @Override
//    public ObservableList<Livres> customSql(String sql, Object... critere) {
//        ObservableList<Livres> livres= FXCollections.observableArrayList();
//        ObservableList<Map<String,Object>> data=this.connectionSql.selectlist(sql,critere);
//        System.out.println();
//        data.parallelStream().forEach(d->{
//            Livres livre=new Livres(d);
//            //livre.setGenre((Genre)DAOFactory.createModel(DAOName.genre).select("id=?",livre.getGenre().getId()));
//            livres.add(livre);
//        });
//        return livres;
//    }

    @Override
    public Livres beforeUpdate( Livres data) {
        this.connectionSql.Update("delete from ecrire where Ref_livre=?",0,data.getId());
        this.connectionSql.Update("delete from diriger where Ref_livre=?",0,data.getId());
        return super.beforeUpdate(data);
    }
}
