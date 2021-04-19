package com.journalisation.controller;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import com.journalisation.dao.ORM.ConnectionSql;
import com.journalisation.resources.conf.Init;
import com.journalisation.resources.conf.InitDatabase;
import com.journalisation.resources.view.ViewBehavior;
import com.journalisation.utils.AppUtils;

import static com.journalisation.Main.primarystage;
import static com.journalisation.Main.rbs;
import com.journalisation.alert.Alert;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;

public class SplashController extends ControllerManager {
    private static double x,y;

    @FXML
    private Label loading;

    private Stage dialog;

    private boolean ok;

    public boolean isOk() {
        return ok;
    }
    @FXML
    void close(ActionEvent event) {
        System.exit(0);
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }
    @Override
    protected void init() {
        x=0;
        y=0;
        loadDatabaseConfiguration load=new loadDatabaseConfiguration();
        LoadPathConfiguration loadPath=new LoadPathConfiguration();
        if(load.getState()!= Worker.State.READY)
            load.reset();

        loadPath.stateProperty().addListener((o,a,n)->{
            switch(n){
                case CANCELLED:
                case FAILED:
                    Alert.error(primarystage, "Configuration Path", rbs);
                    setOk(false);
                    showInit();
                    loadPath.reset();
                    loadPath.restart();
                    System.out.println("echec");
                break;
                case SUCCEEDED:
                    if(loadPath.getValue()){
                    setOk(true);
                    close();
                    System.out.println("succes");
                    }else{
                       Alert.error(primarystage, "Configuration Path", rbs);
                    setOk(false);
                    showInit();
                    loadPath.reset();
                    loadPath.restart();
                    System.out.println("echec"); 
                    }
                break;
            }
        });
        load.stateProperty().addListener((o,a,n)->{
            switch(n){
                case CANCELLED:
                case FAILED:
                    Alert.error(primarystage,"Configuration Database",rbs);
                    setOk(false);
                    showConf();
                    load.reset();
                    load.restart();
                    System.out.println("echec");
                break;
                case SUCCEEDED:
                    if(load.getValue()){
                    setOk(true);
                    loadPath.start();
                    System.out.println("succes");
                    }
                    else{
                        Alert.error(primarystage,"Configuration Database",rbs);
                    setOk(false);
                    showConf();
                    load.reset();
                    load.restart();
                    System.out.println("echec");
                    }
                break;
            }
        });
        load.start();

    }

    private class loadDatabaseConfiguration extends Service<Boolean>{

        @Override
        protected Task<Boolean> createTask() {

            return new Task<Boolean>() {
                @Override
                protected Boolean call() throws Exception {
                    boolean check=true;
                    loading.setVisible(true);
                    updateLabel("verification des configurations...");

                    Thread.sleep(1000);
                    updateLabel("Configurations de la connection...");
                    if(databaseCheck()) {
                        Thread.sleep(500);
                        updateLabel(AppUtils.PROVIDER);
                        Thread.sleep(500);
                        updateLabel( AppUtils.HOST);
                        Thread.sleep(500);
                        updateLabel(AppUtils.dbNAME);
                        updateLabel("Verification de la connection...");
                        ConnectionSql co=ConnectionSql.instance();

                        Thread.sleep(500);
                        if(co.getErreur().trim().isEmpty()){
                            updateLabel("Connexion réussi");
                        }else{
                            updateLabel("Configuration incorrect");
                            return false;
                        }
                    }else{
                            return false;
                    }
                    return check;
                }
            };

        }
    }
    private class LoadPathConfiguration extends Service<Boolean> {

        Thread t=new Thread();
        @Override
        protected Task<Boolean> createTask() {
            return new Task(){
                @Override

                protected Boolean call() throws Exception {
                    Thread.sleep(1000);
                    updateLabel("Configurations de stockage...");
                    if(initCheck()) {
                        Thread.sleep(500);
                        updateLabel(AppUtils.PATH_PROJET);
                        Thread.sleep(500);
                        updateLabel( AppUtils.PATH_RES);

                    }else{
                        return initCheck();
                    }
                    return true;
                }

                @Override
                protected void succeeded() {
                    super.succeeded(); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                protected void running() {
                    super.running(); //To change body of generated methods, choose Tools | Templates.
                }


            };

        }
    }

    private void updateLabel(String text){
        Platform.runLater(()->{
            loading.setText(text);
        });
    }
    private void check(String data){

    }


    private void writeprop(String cas){
        String value= InitDatabase.propertyOf(cas);
        if(value!=null){

            if(cas.equalsIgnoreCase("provider")){
                AppUtils.PROVIDER=value;
            }
            if(cas.equalsIgnoreCase("host")){
                AppUtils.HOST=value;
            }
            if(cas.equalsIgnoreCase("port")){
                AppUtils.PORT=value;
            }
            if(cas.equalsIgnoreCase("username")){
                AppUtils.dbUSER=value;
            }
            if(cas.equalsIgnoreCase("password")){
                AppUtils.dbPASSWORD=value;
            }
            if(cas.equalsIgnoreCase("database")){
                AppUtils.dbNAME=value;
            }
            if(cas.equalsIgnoreCase("api")){
                AppUtils.API=value;
            }

        }
    }
    private boolean databaseCheck(){
        System.out.println("check");
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(SplashController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(InitDatabase.propertyOf("database")!=null){
            System.out.println("dsdsds");
            writeprop("provider");
            writeprop("host");
            writeprop("port");
            writeprop("username");
            writeprop("password");
            writeprop("database");
            writeprop("api");
            return true;
        }
            return false;
    }

    private boolean initCheck(){
        if(Init.propertyOf("Doc-Path")!=null && Init.propertyOf("Img-Path")!=null){
            AppUtils.PATH_PROJET=Init.propertyOf("Doc-Path");
            AppUtils.PATH_RES=Init.propertyOf("Img-Path");
            return true;
        }
            return false;
    }

    public void showInit(){
        Stage settup=new Stage(StageStyle.UNDECORATED);
        settup.setTitle("Login");
        settup.initOwner(primarystage);
        settup.initModality(Modality.APPLICATION_MODAL);
        ViewBehavior vb=new ViewBehavior("PathSetup.fxml");
        Parent rootLog = vb.getparent();
        rootLog.setOnMousePressed((MouseEvent)->{
            x=MouseEvent.getSceneX();
            y=MouseEvent.getSceneY();
        });
        rootLog.setOnMouseDragged((event)-> {
            settup.setX(event.getScreenX()-x);
            settup.setY(event.getScreenY()-y);

        });
        settup.initStyle(StageStyle.TRANSPARENT);
        Scene scenelog=new Scene(rootLog);
        scenelog.setFill(null);
        settup.setScene(scenelog);
        settup.showAndWait();
    }
    public void showConf(){
        Stage settup=new Stage(StageStyle.UNDECORATED);
        settup.setTitle("Login");
        settup.initOwner(primarystage);
        settup.initModality(Modality.APPLICATION_MODAL);
        ViewBehavior vb=new ViewBehavior("databaseSetup.fxml");
        Parent rootLog = vb.getparent();
        rootLog.setOnMousePressed((MouseEvent)->{
            x=MouseEvent.getSceneX();
            y=MouseEvent.getSceneY();
        });
        rootLog.setOnMouseDragged((event)-> {
            settup.setX(event.getScreenX()-x);
            settup.setY(event.getScreenY()-y);

        });
        settup.initStyle(StageStyle.TRANSPARENT);
        Scene scenelog=new Scene(rootLog);
        scenelog.setFill(null);
        settup.setScene(scenelog);
        settup.showAndWait();
    }

    private void close(){
        ((Stage)loading.getScene().getWindow()).close();
    }
}
