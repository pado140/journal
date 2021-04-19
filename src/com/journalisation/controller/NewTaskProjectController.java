package com.journalisation.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.journalisation.alert.Alert;
import com.journalisation.dao.bean.Livres;
import com.journalisation.dao.bean.TaskProject;
import com.journalisation.dao.bean.Users;
import com.journalisation.dao.dao.DAOFactory;
import com.journalisation.dao.dao.DAOName;
import com.journalisation.dao.enumeration.TaskState;
import com.journalisation.resources.view.ListViewMultiple;

import static javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import static com.journalisation.utils.AppUtils.valid;
import javafx.scene.control.TextArea;

public class NewTaskProjectController extends ControllerManager{

    private Livres livre;
    private ListViewMultiple<Users> userlist;
    private TaskProject task;
    private ObservableList<Users> users;
    @FXML
    private AnchorPane content;

    @FXML
    private TextArea tasktext;

    @FXML
    private Spinner<Integer> duree;

    @FXML
    void cancel(ActionEvent event) {

    }

    @FXML
    void close(ActionEvent event) {
        ((Stage)content.getScene().getWindow()).close();
    }

    @FXML
    void save(ActionEvent event) {
        _save();
         close(event);
    }

    @FXML
    void savennew(ActionEvent event) {
        _save();
    }

    @Override
    protected void init() {
        users= FXCollections.observableArrayList();
        users.addAll(DAOFactory.createModel(DAOName.user).selectAll());
        userlist=new ListViewMultiple<>();
        userlist.getAvailableText().setText("Disponible");
        userlist.getSelectedText().setText("Selectionner");
        userlist.setLayoutX(40);
        userlist.setLayoutY(190);
        userlist.setPrefSize(528,237);
        userlist.render();
        userlist.getSources().addAll(users);
        content.getChildren().add(userlist);
        
        duree.setValueFactory(new IntegerSpinnerValueFactory(1,1000));
    }

    private void _save(){
        if(valid(tasktext) && duree.getValue()!=0 && !userlist.getCible().isEmpty()){
            TaskProject task=new TaskProject();
            task.getUser().addAll(userlist.getCible());
            task.setDuree(duree.getValue());
            task.setTasks(tasktext.getText().trim());
            task.setState(TaskState.E);
            task.setLivre(livre);
            task=(TaskProject)DAOFactory.createModel(DAOName.task_project).save(task);
            if(task!=null)
                Alert.success((Stage)content.getScene().getWindow(),"Enregistrement avec succès",this.traduction.get());
            else
                Alert.error((Stage)content.getScene().getWindow(),DAOFactory.createModel(DAOName.task_project).connectionSql.getErreur(),this.traduction.get());
            return;
        }
        Alert.error((Stage)content.getScene().getWindow(),"veuillez renseigner tous les champs",this.traduction.get());
    }

    public Livres getLivre() {
        return livre;
    }

    public void setLivre(Livres livre) {
        this.livre = livre;
    }

    public TaskProject getTask() {
        return task;
    }

    public void setTask(TaskProject task) {
        this.task = task;
    }
    
}
