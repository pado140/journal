package com.journalisation.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import com.journalisation.dao.bean.Tasks;
import com.journalisation.dao.dao.DAOFactory;
import com.journalisation.dao.dao.DAOName;
import com.journalisation.resources.view.ViewBehavior;
import com.journalisation.utils.ViewUtils;

import java.net.URL;
import java.util.ResourceBundle;

import static com.journalisation.Main.primarystage;

public class TaskController extends ControllerManager {
    @FXML
    private TableView<Tasks> table_tasks;

    @FXML
    private TableColumn<Tasks, Integer> id;

    @FXML
    private TableColumn<Tasks, String> task;
    private ObservableList<Tasks> taskslist= FXCollections.observableArrayList();
    private ObjectProperty<ObservableList<Tasks>> listdata= new SimpleObjectProperty<>(taskslist);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        id.setCellValueFactory((data)->data.getValue().idProperty());
        task.setCellValueFactory(data->data.getValue().taskProperty());
        table_tasks.itemsProperty().bind(listdata);
    }

    @Override
    protected void init() {
        taskslist.clear();
        taskslist.addAll(DAOFactory.createModel(DAOName.task).selectAll());
        //listdata.get().addAll(Main.genres);
    }

    @FXML
    void newTask(ActionEvent event) {
        add_change(null);
    }

    private void add_change(Tasks task){
        boolean isnew=task==null;
        ViewBehavior newtask=new ViewBehavior("new_task.fxml");
        Parent p=newtask.getparent();
        NewTaskController cm=(NewTaskController)newtask.getController();
        cm.setTask(task);
        Stage popup= ViewUtils.ModalFrame(p, primarystage);
        popup.showAndWait();
        task=cm.gettask();
        if(task!=null){
            if(isnew){
                taskslist.add(task);
            }else{
                taskslist.set(taskslist.indexOf(task),task);
            }
        }

    }
}
