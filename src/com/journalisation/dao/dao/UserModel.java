package com.journalisation.dao.dao;


import com.journalisation.dao.bean.Groups;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import com.journalisation.dao.bean.Users;
import com.journalisation.security.Hash;

import java.time.format.DateTimeFormatter;
import java.util.Map;

public class UserModel extends ImplModel<Users> {
    private static UserModel model=null;

    public synchronized static UserModel getManager() {
        if(model==null)
            model=new UserModel();
        return model;
    }
    @Override
    public Users add(Users data) {
        requete="insert into user(nom,prenom,sexe,adresse,naissance,mail,tel,username,defaultpass,pass,type,groups_id,enable) " +
                "values(?,?,?,?,?,?,?,?,?,?,?,?,1)";
        if(this.connectionSql.Update(requete,1,data.getNom(),data.getPrenom(),data.getSexe(),data.getAdresse(),
                data.getNaissance().format(DateTimeFormatter.ISO_LOCAL_DATE),data.getMail(),data.getTel(),data.getUsername(),Hash.crypt("pass"),
                Hash.crypt("pass"),data.getType(),data.getGroups().getId())){
            data.setId(this.connectionSql.getLast());
            return data;
        }
        return null;
    }

    @Override
    public Users change(Users data) {
        requete="update user set nom=?,prenom=?,sexe=?,adresse=?,naissance=?,mail=?,tel=?,username=?," +
                "type=?,groups_id=?,pass=?,enable=? where id=?";
        if(this.connectionSql.Update(requete,0,data.getNom(),data.getPrenom(),data.getSexe(),data.getAdresse(),
                data.getNaissance().format(DateTimeFormatter.ISO_LOCAL_DATE),data.getMail(),data.getTel(),data.getUsername(),
                data.getType(),data.getGroups().getId(),data.getPass(),data.getEnable(),data.getId())){
            return data;
        }
        return null;
    }

    @Override
    public boolean remove(Users ob) {
        return false;
    }

    @Override
    public ObservableList<Users> searchBy(String conditions,boolean with, Object... critere) {
        ObservableList<Users> users= FXCollections.observableArrayList();
        requete="select * from user";
        if(conditions!=null && !conditions.trim().isEmpty())
            requete += " where "+conditions;

        ObservableList<Map<String,Object>> data=this.connectionSql.selectlist(requete,critere);
        System.out.println();
        data.forEach(d->{
            Users u=new Users(d);
            if(with)
                u.setGroups((Groups)DAOFactory.createModel(DAOName.group).selectById(u.getGroups().getId()));
            users.add(u);
        });
        return users;
    }

    @Override
    public Users exists(Users ob) {
        return select("username=? and pass=?",false,ob.getUsername(),ob.getPass());
    }

    public ObservableList<Users> userByProject(String conditions, Object... critere) {
        ObservableList<Users> users= FXCollections.observableArrayList();
        requete="select user.* from user inner join user_project on(users_id=id)";
        if(conditions!=null && !conditions.trim().isEmpty())
            requete += " where "+conditions;

        ObservableList<Map<String,Object>> data=this.connectionSql.selectlist(requete,critere);
        System.out.println();
        data.parallelStream().forEach(d->{users.add(new Users(d));});
        return users;
    }

}
