/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.journalisation.Main;
import com.journalisation.alert.Alert;
import com.journalisation.dao.bean.Groups;
import com.journalisation.dao.bean.Users;
import com.journalisation.dao.dao.DAOFactory;
import com.journalisation.dao.dao.DAOName;
import com.journalisation.exceptions.DuplicateException;
import com.journalisation.utils.InputValidation;

import java.net.URL;
import java.util.ResourceBundle;

import static com.journalisation.utils.AppUtils.valid;
import static com.journalisation.utils.AppUtils.validation;

/**
 *
 * @author Padovano
 */
public class RegisterController extends ControllerManager{
    
    @FXML
    private Label panLabel1;

    @FXML
    private Label panLabel2;

    @FXML
    private Label pandecription;

    @FXML
    private Tab about;

    @FXML
    private TabPane wizard;
    
    @FXML
    private Label aboutLabel;

    @FXML
    private Label firstNameLabel;

    @FXML
    private TextField fname;

    @FXML
    private Label lastNameLabel;

    @FXML
    private TextField lname;

    @FXML
    private Label birthLabel;

    @FXML
    private DatePicker bdate;

    @FXML
    private JFXButton next0;

    @FXML
    private Label placeLace;

    @FXML
    private ComboBox<String> place;

    @FXML
    private Label telLabel;

    @FXML
    private TextField tel;

    @FXML
    private Label mailLabel;

    @FXML
    private TextField mail;

    @FXML
    private Label sexeLabel;

    @FXML
    private ComboBox<String> sexe;

    @FXML
    private Label addressLabel;

    @FXML
    private TextField address;

    @FXML
    private Tab account;

    @FXML
    private Label usernameLabel;

    @FXML
    private TextField username;

    @FXML
    private Label passLabel;

    @FXML
    private PasswordField pass;

    @FXML
    private Label cPassLabel;

    @FXML
    private PasswordField cPass;

    @FXML
    private JFXButton previous1;

    @FXML
    private JFXButton done;

    @FXML
    private ComboBox<Groups> group;

    private Users user;
    
    private boolean initsu=false;

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
        if(this.user==null){
            this.user=new Users();
        }else{
            fname.setText(user.getPrenom());
            lname.setText(user.getNom());
            tel.setText(user.getTel());
            mail.setText(user.getMail());
            sexe.getSelectionModel().select(user.getSexe());
            bdate.setValue(user.getNaissance());
            username.setText(user.getUsername());
            pass.setText(user.getPass());
            cPass.setText(user.getPass());
            address.setText(user.getAdresse());
            group.getSelectionModel().select(user.getGroups());
        }
    }

    @FXML
    void close() {
         ((Stage)wizard.getScene().getWindow()).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources); //To change body of generated methods, choose Tools | Templates.

        sexe.setItems(FXCollections.observableArrayList("Homme","Femme"));
        account.setDisable(true);
        next0.setOnAction((event)->{
            System.out.println("validation ok:"+validation((AnchorPane)about.getContent()));
            if(validation((AnchorPane)about.getContent()) && valid(tel, InputValidation.TEL) &&valid(mail,InputValidation.MAIL)){
                if(account.isDisable())
                    account.setDisable(false);
                wizard.getSelectionModel().selectNext();
            }
        });

        previous1.setOnAction((event)->{
                wizard.getSelectionModel().selectPrevious();
        });

        done.setOnAction((actionEvent -> {
            if(initsu)
                user=new Users();
            boolean isNew=user.isNew();
            if(valid(pass,cPass)&&valid(username)&&!group.getSelectionModel().isEmpty()) {
                user.setPrenom(fname.getText());
                user.setNom(lname.getText());
                user.setGroups(group.getSelectionModel().getSelectedItem());
                user.setNaissance(bdate.getValue());
                user.setTel(tel.getText());
                user.setMail(mail.getText());
                user.setSexe(sexe.getSelectionModel().getSelectedItem());
                user.setAdresse(address.getText());
                user.setUsername(username.getText());
                user.setDefaultpass(pass.getText());
                user.setPass(pass.getText());
                user.setEnable(true);
                if(isNew){
                    user.setType("user");
                    if(initsu)
                        user.setType("SuperAdmin");
                }
                try{
                    user= (Users)DAOFactory.createModel(DAOName.user).save(user);
                    Alert.success((Stage) fname.getScene().getWindow(),"Enregistrement avec succes",this.traduction.get());
                }catch(DuplicateException e){
                    Alert.error((Stage) fname.getScene().getWindow(),e.getMessage(),this.traduction.get());
                }
            }
        }));
        System.out.println("content:"+about.getContent());
    }

    public void setInitsu(boolean initsu) {
        this.initsu = initsu;
        System.out.println("super:"+initsu);
        if(initsu){
            System.out.println("fdfd");
            group.setItems(DAOFactory.createModel(DAOName.group).selectAll());
        }
    }

    
    @Override
    protected void init() {
        group.setItems(Main.groups);
        
    }

}
