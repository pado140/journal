package com.journalisation.controller;

import com.journalisation.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.journalisation.alert.Alert;
import com.journalisation.dao.ORM.ConnectionSql;
import com.journalisation.dao.bean.Tasks;
import com.journalisation.dao.dao.DAOFactory;
import com.journalisation.dao.dao.DAOName;
import com.journalisation.exceptions.DuplicateException;
import com.journalisation.utils.AppUtils;

public class NewTaskController extends ControllerManager {
    private Tasks task;

    public Tasks gettask() {
        return task;
    }

    public void setTask(Tasks task) {
        this.task = task;
        if(task==null)
            this.task=new Tasks();
    }

    @Override
    protected void init() {

    }

    @FXML
    private TextField taskField;

    @FXML
    void cancel(ActionEvent event) {
        closed();
    }

    @FXML
    void closed() {
        ((Stage)taskField.getScene().getWindow()).close();
    }

    @FXML
    void save(ActionEvent event) {
        boolean isnew=task.isNew();
        if(AppUtils.valid(taskField)){
            task.setTask(taskField.getText().trim());
            try {
                task = (Tasks) DAOFactory.createModel(DAOName.task).save(task);
                if (task != null) {
                    
                    Alert.success(Main.primarystage, "Nouveau genre ajouter avec succes", Main.rbs);
                    closed();  
                }
                Alert.error(Main.primarystage, "desolee" + ConnectionSql.instance().getErreur(), Main.rbs);
                return;
            }catch (DuplicateException ex){
                Alert.error((Stage) taskField.getScene().getWindow(), "desolee" + ex.getMessage(), Main.rbs);
            }
        }
        Alert.error(Main.primarystage,"Svp rempli le champs avant d'essayer d'enregistrer",Main.rbs);

    }
}
