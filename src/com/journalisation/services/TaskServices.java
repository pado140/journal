/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.services;

import com.journalisation.dao.bean.Livres;
import com.journalisation.dao.bean.TaskProject;
import com.journalisation.dao.bean.Users;
import com.journalisation.dao.dao.DAOFactory;
import com.journalisation.dao.dao.DAOName;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

/**
 *
 * @author ADMIN
 */
public class TaskServices {
    public static ObservableList<TaskProject> getAll(){
        return DAOFactory.createModel(DAOName.task_project).csearchBy(null,null,"livre_id"
                ,"tasks","created","state","duree");
    }
    
    public static ObservableList<TaskProject> getByProject(Livres livre){
        ObservableList<TaskProject> tasks=DAOFactory.createModel(DAOName.task_project).csearchBy("livre_id=?",new Object[]{livre.getId()},"livre_id"
                ,"tasks","created","state","duree","id");
//        tasks.forEach(t->{
//            t.setUser(DAOFactory.createModel(DAOName.task));
//        });
        return tasks;
    }
    
    public static ObservableList<Livres> getProjectByUser(Users user){
        ObservableList<Map<String,Object>> livres=DAOFactory.createModel(DAOName.livre).customSql(
                "select id,titre,soustitre,format,genre_id,receipt from getbook_by_usertask where user_id=? group by user_id,id",user.getId());
        
        System.out.println("user:"+user.getId());
        ObservableList<Livres> ls=FXCollections.observableArrayList();
        livres.forEach(l->{
            ls.add(new Livres(l));
            
        });
        return ls;
    }
}
