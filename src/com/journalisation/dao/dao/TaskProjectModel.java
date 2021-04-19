package com.journalisation.dao.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.journalisation.dao.bean.TaskProject;
import com.journalisation.dao.bean.Users;

import java.util.Map;
import java.util.stream.Collectors;

public class TaskProjectModel extends ImplModel<TaskProject> {
    private static TaskProjectModel model=null;

    public synchronized static TaskProjectModel getManager() {
        if(model==null)
            model=new TaskProjectModel();
        return model;
    }
    
    @Override
    public TaskProject add(TaskProject data) {
        requete="insert into project_user_task (task,livre_id,user_id,duree,state) values(?,?,?,?,'E')";
        for(Users user:data.getUser()){
            this.connectionSql.Update(requete,1,data.getTasks(),data.getLivre().getId(),user.getId(),data.getDuree());
            data.setId(this.connectionSql.getLast());
            //return data;
        }
        return data;
    }

    @Override
    public TaskProject change(TaskProject data) {
        requete="update project_user_task set task=?,livre_id=?,user_id=?,duree=?,state=? where id=?";
        if(this.connectionSql.Update(requete,1,data.getTasks(),data.getLivre().getId(),data.getUser().get(0).getId(),data.getDuree(),data.getState().toString(),data.getId())){

            return data;
        }
        return null;
    }

    @Override
    public boolean remove(TaskProject ob) {
        return this.connectionSql.Update("delete from project_user_task where id=?",0,ob.getId());
    }

    @Override
    public ObservableList<TaskProject> searchBy(String conditions,boolean with, Object... critere) {
        ObservableList<TaskProject> tasks= FXCollections.observableArrayList();
        requete="select * from taskproject";
        if(conditions!=null && !conditions.trim().isEmpty())
            requete += " where "+conditions;

        ObservableList<Map<String,Object>> data=this.connectionSql.selectlist(requete,critere);
        System.out.println(data);
        data.forEach(d->{
            String user="";
            if(d.containsKey("user_id")) {
                user = d.get("user_id").toString();
                d.remove("user_id");
            }
            System.out.println("usser:"+user);
            TaskProject task=new TaskProject(d);
            if(with)
                task.setUser(DAOFactory.createModel(DAOName.user).searchById("id=?",user));
            tasks.add(task);
        });
        return tasks;
    }

    @Override
    public ObservableList<TaskProject> csearchBy(String conditions, Object[] critere, String... field) {
        ObservableList<TaskProject> tasks= FXCollections.observableArrayList();
        String fieldRequest="*";
        if(field!=null && field.length>0){
            fieldRequest=String.join(",",field);
        }
        requete="select "+fieldRequest+" from taskproject";
        if(conditions!=null && !conditions.trim().isEmpty())
            requete += " where "+conditions;

        ObservableList<Map<String,Object>> data=this.connectionSql.selectlist(requete,critere);
        System.out.println(requete);
        System.out.println(data);
        data.forEach(d->{
            String user="";
            if(d.containsKey("user_id")) {
                user = d.get("user_id").toString();
                d.remove("user_id");
            }
            TaskProject task=new TaskProject(d);
            if(!user.trim().isEmpty())
                task.setUser(DAOFactory.createModel(DAOName.user).searchById(Integer.parseInt(user)));
            tasks.add(task);
        });
        return tasks;
    }

    @Override
    public TaskProject exists(TaskProject ob) {
        ObservableList<String> users_id=ob.getUser().parallelStream().map(user->{return String.valueOf(user.getId());})
                .collect(Collectors.toCollection(() -> {return FXCollections.observableArrayList();}));
        ObservableList<TaskProject> tasks=searchBy("livre_id=? and user_id in(?) and tasks=?",false,
                ob.getLivre().getId(),String.join(",",users_id),ob.getTasks());
        return tasks.isEmpty()?null:tasks.get(0);
    }
}
