package com.journalisation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.journalisation.Main;
import com.journalisation.alert.Alert;
import com.journalisation.dao.ORM.ConnectionSql;
import com.journalisation.dao.bean.Genre;
import com.journalisation.dao.dao.DAOFactory;
import com.journalisation.dao.dao.DAOName;
import com.journalisation.exceptions.DuplicateException;
import com.journalisation.utils.AppUtils;

public class NewGenreController extends ControllerManager {
    private Genre genre;

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
        if(genre==null)
            this.genre=new Genre();
    }

    @Override
    protected void init() {

    }

    @FXML
    private TextField nom;

    @FXML
    void cancel(ActionEvent event) {
        closed();
    }

    @FXML
    void closed() {
        ((Stage)nom.getScene().getWindow()).close();
    }

    @FXML
    void save(ActionEvent event) {
        boolean isnew=genre.isNew();
        if(AppUtils.valid(nom)){
            genre.setNom(nom.getText().trim());
            try {
                genre = (Genre) DAOFactory.createModel(DAOName.genre).save(genre);
                if (genre != null) {
                    if (isnew)
                        Main.genres.add(genre);
                    else
                        Main.genres.set(Main.genres.indexOf(genre), genre);
                    Alert.success((Stage) nom.getScene().getWindow(), "Nouveau gnre ajouter avec succes", null);
                    return;
                }
                Alert.error((Stage) nom.getScene().getWindow(), "desolee" + ConnectionSql.instance().getErreur(), null);
                return;
            }catch (DuplicateException ex){
                Alert.error((Stage) nom.getScene().getWindow(), "desolee" + ex.getMessage(), null);
            }
        }
        Alert.error((Stage)nom.getScene().getWindow(),"Svp rempli le champs avant d'essayer d'enregistrer",null);
    }
}
