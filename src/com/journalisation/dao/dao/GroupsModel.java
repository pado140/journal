/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.dao.dao;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.journalisation.dao.bean.Groups;
import com.journalisation.dao.bean.Roles;
import java.util.Map;

/**
 *
 * @author Padovano
 */
public class GroupsModel extends ImplModel<Groups>{

    private static GroupsModel model=null;

    public synchronized static GroupsModel getManager() {
        if(model==null)
            model=new GroupsModel();
        return model;
    }
    
    @Override
    public Groups add(Groups objet) {
        requete="insert into `groups`(name,description) values(?,?)";
        
        if(this.connectionSql.Update(requete, 1, objet.getName(),objet.getDescription())){
            objet.setId(this.connectionSql.getLast());
            for(Roles role:objet.getRoles()){
                this.connectionSql.Update("insert into groups_roles (groups_id,roles_id) values(?,?)",0,objet.getId(),role.getId());
            }
            
            return objet;
        }
        return null;
    }

    @Override
    public boolean remove(Groups Objet) {
        return connectionSql.Update("delete from `groups` where id=?", 0, Objet.getId());
    }

    @Override
    public ObservableList<Groups> searchBy(String conditions,boolean with, Object... critere) {
        ObservableList<Groups> group= FXCollections.observableArrayList();
        requete="select * from `groups`";
        if(conditions!=null && !conditions.trim().isEmpty())
            requete += " where "+conditions;

        ObservableList<Map<String,Object>> data=this.connectionSql.selectlist(requete,critere);
        System.out.println();
        data.parallelStream().forEach(d->{
            Groups g=new Groups(d);
            g.setRoles(((RolesModel)DAOFactory.createModel(DAOName.role)).load("groups_id=?",g.getId()));
            group.add(g);});
        return group;
    }

    @Override
    public Groups exists(Groups ob) {
        Groups group=select("name=?",false,ob.getName());
        return group;
    }

    @Override
    public Groups change(Groups nouveau) {
        requete="update `groups` set name=?,description=? where id=?";
        if(this.connectionSql.Update(requete, 1, nouveau.getName(),nouveau.getDescription(),nouveau.getId())){
            for(Roles role:nouveau.getRoles()){
                this.connectionSql.Update("insert into groups_roles (groups_id,roles_id) values(?,?)",0,nouveau.getId(),role.getId());
            }
            return nouveau;
        }
        return null;
    }

    @Override
    public Groups beforeUpdate(Groups data) {
            requete="delete from groups_roles where groups_id=?";
            this.connectionSql.Update(requete,0,data.getId());
        return data;
    }

    @Override
    public Groups afterUpdate(Groups data) {

        return super.afterUpdate(data);
    }
}
