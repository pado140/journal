/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.controller;

import com.journalisation.Main;
import static com.journalisation.Main.rbs;
import com.journalisation.alert.Alert;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.journalisation.resources.conf.InitDatabase;
import com.journalisation.resources.conf.InitStoreDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.journalisation.utils.AppUtils.valid;

/**
 * FXML Controller class
 *
 * @author Padovano
 */
public class DatabaseSetupController extends ControllerManager {

    /**
     * Initializes the controller class.
     */
    private String api="com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private StringBuilder uri;
    private String Url="";

    @Override
    protected void init() {
        uri=new StringBuilder("jdbc:");
        ObservableList<String> providerList=FXCollections.observableArrayList("mysql","sqlserver");
        provider.setItems(providerList);
        databases.itemsProperty().bind(new SimpleObjectProperty<>(databaseList));
        writeprop("provider");
        writeprop("host");
        writeprop("port");
        writeprop("username");
        writeprop("password");
        writeprop("database");
    }

    @FXML
    private ComboBox<String> provider;

    @FXML
    private TextField host;

    @FXML
    private TextField port;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private ComboBox<String> databases;
    
    @FXML
    private Label message;
    
    private final ObservableList<String> databaseList=FXCollections.observableArrayList();

    @FXML
    void changeApi() {
        uri=new StringBuilder("jdbc:");
        if(provider.getSelectionModel().getSelectedItem().equals("mysql")){
            api="com.mysql.cj.jdbc.Driver";
            uri.append("mysql://");
        }
        if(provider.getSelectionModel().getSelectedItem().equals("sqlserver")){
            api="com.microsoft.sqlserver.jdbc.SQLServerDriver";
            uri.append("sqlserver://");
        }
    }

    @FXML
    void closeUnit(ActionEvent event) {
        ((Stage)port.getScene().getWindow()).close();
    }

    @FXML
    void saveConf(ActionEvent event) {
        read("provider");
        read("host");
        read("port");
        read("username");
        read("password");
        read("database");
        read("api",api);
        read("uri",uri.toString());
        InitStoreDatabase.save();
        //Alert.showInfo((Stage)port.getScene().getWindow(),"Alert", "L'application va se fermer, veillez relancer l'Application", Main.rbs);
        closeUnit(event);
    }
    
    @FXML
    void test(ActionEvent event) {
        Url=uri.toString();
        if(valid(provider)&&valid(host)&& valid(username)){
            Url+=host.getText().trim();
            if(!port.getText().trim().isEmpty())
                Url+=":"+port.getText();
            Task task=new Task() {

                @Override
                protected Object call() throws Exception {
                    try{
                        Class.forName(api);
                        Connection con=DriverManager.getConnection(Url, username.getText(), password.getText());
                        java.sql.DatabaseMetaData meta=con.getMetaData();
                        try (ResultSet metaData = meta.getCatalogs()) {
                            databaseList.clear();
                            while(metaData.next()){
                                System.out.println(metaData.getString(1));
                                databaseList.add(metaData.getString(1));
                            }
                            System.out.println("Success");
                            Alert.success(Main.primarystage,  "Connexion etablie avec succes", rbs);
                        }
                    }catch(SQLException | ClassNotFoundException ex){
                        System.out.println(ex.getMessage());
                        Alert.error(Main.primarystage, ex.getMessage(), rbs);
                        ex.printStackTrace();
                    }
                    return null;
                }
            };
            
            task.run();
        }
        System.out.println("url:"+Url);
    }
    
    private void writeprop(String cas){
        String value= InitDatabase.propertyOf(cas);
        if(value!=null){
            
        if(cas.equalsIgnoreCase("provider")){
            provider.setValue(value);
        }
        if(cas.equalsIgnoreCase("host")){
            host.setText(value);
        }
        if(cas.equalsIgnoreCase("port")){
            port.setText(value);
        }
        if(cas.equalsIgnoreCase("username")){
            username.setText(value);
        }
        if(cas.equalsIgnoreCase("password")){
            password.setText(value);
        }
        if(cas.equalsIgnoreCase("database")){
            databases.getSelectionModel().select(value);
        }
        
        }
    }
    private void read(String cas){
        String key=cas;
        String value="";
        
        if(cas.equalsIgnoreCase("provider")){
            value=provider.getValue();
        }
        if(cas.equalsIgnoreCase("host")){
            value=host.getText();
        }
        if(cas.equalsIgnoreCase("port")){
            value=port.getText();
        }
        if(cas.equalsIgnoreCase("username")){
            value=username.getText();
        }
        if(cas.equalsIgnoreCase("password")){
            value=password.getText();
        }
        if(cas.equalsIgnoreCase("database")){
            value=databases.getValue();
        }
        
        if(!value.trim().isEmpty()){
            InitStoreDatabase.propertyOf(key, value);
        }
    }
    private void read(String cas,String value){
        String key=cas;
        
        if(!value.trim().isEmpty()){
            InitStoreDatabase.propertyOf(key, value);
        }
    }

}
