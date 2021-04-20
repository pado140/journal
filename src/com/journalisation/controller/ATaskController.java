package com.journalisation.controller;

import com.jfoenix.controls.JFXButton;
import static com.journalisation.Main.primarystage;
import static com.journalisation.Main.rbs;
import com.journalisation.alert.Alert;
import com.journalisation.alert.AlertButtons;
import com.journalisation.dao.bean.Livres;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.journalisation.dao.bean.TaskProject;
import com.journalisation.dao.bean.Users;
import com.journalisation.dao.dao.DAOFactory;
import com.journalisation.dao.dao.DAOName;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;

public class ATaskController extends ControllerManager {
    @FXML
    private Label task;

    @FXML
    private Label user_task;
    
    @FXML
    private JFXButton del;

    @FXML
    boolean delete() {
        if(Alert.showConfirmMessage(primarystage, "Voulez vous supprimer cette tache", rbs)==AlertButtons.YES){
            Livres l=tasks.getLivre();
                    if(DAOFactory.createModel(DAOName.task_project).delete(tasks)){
                        Alert.success(primarystage, "Tache supprime avec succes", rbs);
                        loadprojetinfo(l);
                    }
                }
        return false;
    }

    private TaskProject tasks;

    public TaskProject getTasks() {
        return tasks;
    }

    public void setTasks(TaskProject tasks) {
        this.tasks = tasks;
        System.out.println(tasks.getTasks());
         task.setText(tasks.getTasks());
        ObservableList<Map<String,Object>> users= DAOFactory.createModel(DAOName.user)
                .customSql("select user.* from user inner join project_user_task"
                        + " p on(p.user_id=user.id) where livre_id=? and task=? and state=?",
                tasks.getLivre().getId(),tasks.getTasks(),tasks.getState().toString());
         user_task.setText(String.join(",",users.parallelStream().map(u->{return ""+new Users(u);}).collect(Collectors.toCollection(ArrayList::new))));
    }

    @Override
    protected void init() {
        del.setVisible(false);
    }

    public JFXButton getDel() {
        return del;
    }

    public void setDel(JFXButton del) {
        this.del = del;
    }
    
}
