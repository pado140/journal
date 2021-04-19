package com.journalisation.dao.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.journalisation.dao.bean.Tasks;

import java.util.Map;

public class TaskModel extends ImplModel<Tasks> {
    private static TaskModel model=null;

    public synchronized static TaskModel getManager() {
        if(model==null)
            model=new TaskModel();
        return model;
    }
    @Override
    public Tasks add(Tasks data) {
        requete="insert into task(task) values(?)";
        if(this.connectionSql.Update(requete,1,data.getTask())){
            data.setId(this.connectionSql.getLast());
            return data;
        }
        return null;
    }

    @Override
    public Tasks change(Tasks data) {
        requete="update task set task=? where id=?";
        if(this.connectionSql.Update(requete,0,data.getTask(),data.getId())){
            return data;
        }
        return null;
    }

    @Override
    public boolean remove(Tasks ob) {
        requete="delete from task where id=?";
        return this.connectionSql.Update(requete,1,ob.getId());
    }

    @Override
    public ObservableList<Tasks> searchBy(String conditions,boolean with, Object... critere) {
        ObservableList<Tasks> tasks= FXCollections.observableArrayList();
        requete="select id,task from task";
        if(conditions!=null && !conditions.trim().isEmpty())
            requete += " where "+conditions;

        ObservableList<Map<String,Object>> data=this.connectionSql.selectlist(requete,critere);
        System.out.println();
        data.parallelStream().forEach(d->{tasks.add(new Tasks(d));});
        return tasks;
    }

    @Override
    public Tasks exists(Tasks ob) {
        return select ("task=?",false,ob.getTask());
    }
}
