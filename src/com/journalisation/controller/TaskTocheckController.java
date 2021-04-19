/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.controller;

import com.journalisation.Main;
import com.journalisation.alert.Alert;
import com.journalisation.dao.bean.TaskProject;
import com.journalisation.dao.dao.DAOFactory;
import com.journalisation.dao.dao.DAOName;
import com.journalisation.dao.enumeration.TaskState;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author ADMIN
 */
public class TaskTocheckController extends ControllerManager {

    /**
     * Initializes the controller class.
     */
    private TaskProject task;
    @FXML
    private CheckBox completed;
    
    @FXML
    private Label taskname;
    
    @FXML
    private Label date;

    @FXML
    void check(ActionEvent event) {
        task.setState(TaskState.C);
        task=(TaskProject)DAOFactory.createModel(DAOName.task_project).save(task);
        if(task!=null){
            Alert.success(Main.primarystage, "Task has been completed successfully", null);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    protected void init() {
        ;
    }

    public TaskProject getTask() {
        return task;
    }

    public void setTask(TaskProject task) {
        this.task = task;
        taskname.setText(task.getTasks());
        date.setText(task.getCreated().format(DateTimeFormatter.ofPattern("dd-MMM-uuuu HH:mm:ss")));
        if(task.getState()!=TaskState.E){
            completed.setSelected(true);
            completed.setDisable(true);
        }
    }   
}
