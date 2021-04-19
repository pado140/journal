package com.journalisation.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import com.journalisation.dao.dao.DAOFactory;
import com.journalisation.dao.dao.DAOName;
import com.journalisation.utils.AppUtils;

import static com.journalisation.Main.users;
import com.journalisation.dao.bean.Users;
import com.journalisation.security.Hash;

public class ChangePassController extends ControllerManager {

    boolean closed=false;
    @FXML
    private Label user;

    @FXML
    private PasswordField pass;

    @FXML
    private PasswordField passc;

    @FXML
    private Label error;

    @FXML
    void close() {
        closed=true;
        getContext(user).close();
    }

    @FXML
    void confirm() {
        error.setVisible(false);
        if(AppUtils.valid(pass,6)){
            if(AppUtils.valid(pass,passc)){
                users.setPass(Hash.crypt(pass.getText()));
                if(DAOFactory.createModel(DAOName.user).save(users)==null){
                    users.setPass(Hash.crypt(users.getDefaultpass()));
                };

                closed=false;
                getContext(user).close();
            }else{
                error.setText("Les Mot de passe doivent etre identiques");
                error.setVisible(true);
            }
        }else {
            error.setText("Le Mot de passe doit avoir 6 caracteres au minimum");
            error.setVisible(true);
        }
    }
    @Override
    protected void init() {
        user.setText(String.join(" ", users.getPrenom(), users.getNom()));
    }
}
