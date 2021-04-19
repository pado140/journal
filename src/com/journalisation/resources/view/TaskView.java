package com.journalisation.resources.view;

import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import com.journalisation.controller.ATaskController;
import com.journalisation.dao.bean.TaskProject;
import javafx.scene.control.ListView;

public class TaskView extends AnchorPane {
    private TaskProject task;
    private ATaskController project;
    private Parent content;
    private ListView parent;

    public TaskView(TaskProject task,boolean t,ListView p) {
        this.task = task;
        this.parent=p;
        ViewBehavior loadProject=new ViewBehavior("a_task.fxml");
        content=loadProject.getparent();
        project=(ATaskController)loadProject.getController();
        project.setTasks(task,p);
        project.getDel().setVisible(t);
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

    public ATaskController getProject() {
        return project;
    }

    public void setProject(ATaskController project) {
        this.project = project;
    }
}
