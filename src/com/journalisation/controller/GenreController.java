package com.journalisation.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import com.journalisation.Main;
import com.journalisation.dao.bean.Genre;
import com.journalisation.dao.dao.DAOFactory;
import com.journalisation.dao.dao.DAOName;
import com.journalisation.resources.view.ViewBehavior;
import com.journalisation.utils.ViewUtils;

import java.net.URL;
import java.util.ResourceBundle;

import static com.journalisation.Main.primarystage;

public class GenreController extends ControllerManager {
    @FXML
    private TableView<Genre> table_genres;

    @FXML
    private TableColumn<Genre, Integer> id;

    @FXML
    private TableColumn<Genre, String> genre;
    private ObjectProperty<ObservableList<Genre>> listdata= new SimpleObjectProperty<>(Main.genres);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        id.setCellValueFactory((data)->data.getValue().idProperty());
        genre.setCellValueFactory(data->data.getValue().nomProperty());
        table_genres.itemsProperty().bind(listdata);
    }

    @Override
    protected void init() {
        Main.genres.clear();
        Main.genres.addAll(DAOFactory.createModel(DAOName.genre).selectAll());
        //listdata.get().addAll(Main.genres);
    }

    @FXML
    void newGenre(ActionEvent event) {
        add_change(null);
    }

    private void add_change(Genre g){
        ViewBehavior newgenre=new ViewBehavior("new_genre.fxml");
        Parent p=newgenre.getparent();
        ControllerManager cm=(ControllerManager)newgenre.getController();
        ((NewGenreController)cm).setGenre(g);
        Stage popup= ViewUtils.ModalFrame(p, primarystage);
        popup.showAndWait();
    }
}
