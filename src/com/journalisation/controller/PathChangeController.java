/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.controller;

import com.journalisation.Main;
import static com.journalisation.Main.rbs;
import com.journalisation.alert.Alert;
import com.journalisation.resources.conf.IniStore;
import com.journalisation.resources.conf.Init;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 *
 * @author Padovano
 */
public class PathChangeController extends ControllerManager{

    private DirectoryChooser directoryChooser;
    @Override
    protected void init() {
        directoryChooser=new DirectoryChooser();
        res_path.setText(Init.propertyOf("Img-Path"));
        doc_field.setText(Init.propertyOf("Doc-Path"));
    }
    @FXML
    private TextField doc_field;

    @FXML
    private TextField res_path;

    @FXML
    void closeUnit() {
        ((Stage)res_path.getScene().getWindow()).close();
    }

    @FXML
    void saveConf(ActionEvent event) {
        IniStore.propertyOf("Doc-Path", doc_field.getText());
        IniStore.propertyOf("Img-Path", res_path.getText());
            IniStore.save();
            Alert.success((Stage)res_path.getScene().getWindow(), "Configuration reussi avec succes", rbs);
            closeUnit();
    }

    @FXML
    void loadPathdoc(ActionEvent event) {
        directoryChooser.setTitle("Selectionnez le chemin pour les Documents");
        File f=directoryChooser.showDialog(Main.primarystage);
        if(f!=null){
            doc_field.setText(f.getPath());
        }
    }

    @FXML
    void loadPathimg(ActionEvent event) {

        directoryChooser.setTitle("Selectionnez le chemin pour les images");
        File f=directoryChooser.showDialog(Main.primarystage);
        if(f!=null){
            res_path.setText(f.getPath());
        }
    }
}
