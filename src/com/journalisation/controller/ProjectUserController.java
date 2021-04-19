package com.journalisation.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.journalisation.dao.bean.Users;
import com.journalisation.dao.dao.DAOFactory;
import com.journalisation.dao.dao.DAOName;
import com.journalisation.resources.view.ListViewMultiple;

public class ProjectUserController extends ControllerManager {
    private ListViewMultiple<Users> userlist;
    private ObservableList<Users> users;

    public ListViewMultiple<Users> getUserlist() {
        return userlist;
    }

    public void setUserlist(ListViewMultiple<Users> userlist) {
        this.userlist = userlist;
    }

    @FXML
    void close() {
        ((Stage)content.getScene().getWindow()).close();
    }

    @FXML
    void save(ActionEvent event) {

    }

    @Override
    protected void init() {
        users= FXCollections.observableArrayList();
        users.addAll(DAOFactory.createModel(DAOName.user).selectAll());
        userlist=new ListViewMultiple<>();
        userlist.setLayoutX(21);
        userlist.setLayoutY(75);
        userlist.setPrefSize(559,265);
        userlist.render();
        userlist.getSources().addAll(users);
        content.getChildren().add(userlist);
    }

    @FXML
    private AnchorPane content;
}
