package com.journalisation.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import com.journalisation.Main;
import com.journalisation.alert.Alert;
import com.journalisation.exceptions.DatabaseException;
import com.journalisation.resources.conf.IniStore;
import com.journalisation.resources.conf.Init;
import com.journalisation.resources.conf.InitDatabase;
import com.journalisation.resources.conf.InitStoreDatabase;
import com.journalisation.utils.AppUtils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.journalisation.utils.AppUtils.valid;

public class SettingController extends ControllerManager {
    private DirectoryChooser directoryChooser;
    private String api="com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private StringBuilder uri;
    private String Url="";
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

    private final ObservableList<String> databaseList= FXCollections.observableArrayList();

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
        Alert.showInfo((Stage)port.getScene().getWindow(), "Alert","L'application va se fermer, veillez relancer l'Application",Main.rbs);
        Main.primarystage.close();
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
                        Connection con= DriverManager.getConnection(Url, username.getText(), password.getText());
                        java.sql.DatabaseMetaData meta=con.getMetaData();
                        try (ResultSet metaData = meta.getCatalogs()) {
                            databaseList.clear();
                            while(metaData.next()){
                                System.out.println(metaData.getType());
                                System.out.println(metaData.getString(1));
                                databaseList.add(metaData.getString(1));
                            }
                            System.out.println("Success");
                            message.setText("Connection reussi");
                            Alert.showInfo(Main.primarystage,"Connection Infos","Connection reussi",Main.rbs);
                        }
                    }catch(SQLException | ClassNotFoundException ex){
                        System.out.println(ex.getMessage());
                        throw new DatabaseException(ex.getMessage());

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
    @FXML
    private TextField address_field;

    @FXML
    private TextField doc_field;

    @FXML
    private TextField res_path;

    @FXML
    void loadPathdoc(ActionEvent event) {
        directoryChooser.setTitle("Selectionnez le chemin pour les Documents");
        File f=directoryChooser.showDialog(Main.primarystage);
        if(f!=null){
            AppUtils.PATH_PROJET=f.getPath();
            AppUtils.PATH_RES=Init.propertyOf("Img-Path");
            IniStore.propertyOf("Doc-Path", f.getPath());
            IniStore.propertyOf("Img-Path", Init.propertyOf("Img-Path"));
            IniStore.save();
            doc_field.setText(f.getPath());
        }
    }

    @FXML
    void loadPathimg(ActionEvent event) {

        directoryChooser.setTitle("Selectionnez le chemin pour les images");
        File f=directoryChooser.showDialog(Main.primarystage);
        if(f!=null){
            AppUtils.PATH_RES=f.getPath();
            AppUtils.PATH_PROJET=Init.propertyOf("Doc-Path");
            IniStore.propertyOf("Img-Path", f.getPath());
            IniStore.propertyOf("Doc-Path", Init.propertyOf("Doc-Path"));
            IniStore.save();
            res_path.setText(f.getPath());
        }
    }

    @Override
    protected void init() {
        directoryChooser=new DirectoryChooser();
        uri=new StringBuilder("jdbc:");
        ObservableList<String> providerList=FXCollections.observableArrayList("mysql","sqlserver");
        provider.setItems(providerList);
        provider.itemsProperty().addListener((o,a,n)->{
            changeApi();
        });
        advanced.expandedProperty().addListener((o,a,n)->{
            if(n){

            }
        });
        databases.itemsProperty().bind(new SimpleObjectProperty<>(databaseList));
        writeprop("provider");
        writeprop("host");
        writeprop("port");
        writeprop("username");
        writeprop("password");
        writeprop("database");
        res_path.setText(Init.propertyOf("Img-Path"));
        doc_field.setText(Init.propertyOf("Doc-Path"));
    }

    @FXML
    private TitledPane advanced;

    @FXML
    private CheckBox struture;

    @FXML
    private CheckBox structurendata;

    @FXML
    private CheckBox datas;

    @FXML
    void export(ActionEvent event) {

    }
}
