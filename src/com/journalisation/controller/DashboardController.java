package com.journalisation.controller;

import com.journalisation.Main;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import com.journalisation.dao.bean.Livres;
import com.journalisation.dao.bean.Transaction;
import com.journalisation.dao.bean.Users;
import com.journalisation.dao.dao.DAOFactory;
import com.journalisation.dao.dao.DAOName;
import com.journalisation.resources.menu.vertical.InitMenu;
import com.journalisation.resources.view.ViewBehavior;
import com.journalisation.services.TaskServices;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Worker;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

public class DashboardController extends ControllerManager {
    @FXML
    private TableView<Livres> projects;

    @FXML
    private TableColumn<Livres, String> title;

    @FXML
    private TableColumn<Livres, LocalDate> received;

    @FXML
    private TableView<Transaction> transactions;

    @FXML
    private TableColumn<Transaction, String> transac;

    @FXML
    private TableColumn<Transaction, LocalDateTime> date;

    @FXML
    private TableColumn<Transaction, Users> user;
    
    private ObservableList<Livres> projetlist=FXCollections.observableArrayList();
    private ObservableList<Transaction> transaction=FXCollections.observableArrayList();

    @FXML
    void seeall(ActionEvent event) {

    }

    @FXML
    void allprojects(ActionEvent event) {

    }


    @Override
    protected void init() {
        
        transac.setCellValueFactory(data->data.getValue().actionProperty());
        date.setCellValueFactory(data->data.getValue().createdProperty());
        user.setCellValueFactory(data->data.getValue().usersProperty());
        
        title.setCellValueFactory(data->data.getValue().titreProperty());
        received.setCellValueFactory(data->data.getValue().receiptProperty());
        projects.getSelectionModel().selectedItemProperty().addListener((o,a,n)->{
            loadprojetinfo(n);
        });
        projects.itemsProperty().bind(new SimpleObjectProperty<>(Main.projets));
       
        
        if(loader.getState()!= Worker.State.READY)
                loader.reset();
            loader.start();
    }
    
    @FXML
    void reports(ActionEvent event) {

    }
    @Override
    protected void initialized(){
        projetlist=DAOFactory.createModel(DAOName.livre).selectAll();
        if(getUser().getType().equalsIgnoreCase("user")){
            projetlist.clear();
            projetlist.addAll(TaskServices.getProjectByUser(getUser()));
            
                        //livreFilter.addAll(livres);
        }
        Main.projets.clear();
        Main.projets.addAll(projetlist);
        System.out.println("qt:"+projetlist.size());
        if(getUser().getType().equals("user")){
            transaction=DAOFactory.createModel(DAOName.transac).search("users_id=?",false, getUser().getId());
        }
        else
            transaction =DAOFactory.createModel(DAOName.transac).selectAll();
        
        Platform.runLater(()->{
            transactions.setItems(transaction);
        });
               
    }
                

}
