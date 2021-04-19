/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.resources.view;

import com.journalisation.controller.TaskTocheckController;
import com.journalisation.dao.bean.TaskProject;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author ADMIN
 */
public class TaskByProjectCheck extends AnchorPane{
    private TaskProject task;
    private TaskTocheckController project;
    private Parent content;

    public TaskByProjectCheck(TaskProject tas) {
        this.task = tas;
        ViewBehavior loadProject=new ViewBehavior("TaskTocheck.fxml");
        content=loadProject.getparent();
        project=(TaskTocheckController)loadProject.getController();
        project.setTask(task);
        doLayout();

        //pi.setContent(new Label(this.livre.getTitre()));
    }

    public AnchorPane doLayout(){
        this.getChildren().add(content);
        return this;
    }

    public TaskProject getTask() {
        return task;
    }

    public void setTask(TaskProject task) {
        this.task = task;
    }

    public TaskTocheckController getProject() {
        return project;
    }

    public void setProject(TaskTocheckController project) {
        this.project = project;
    }

    
}
