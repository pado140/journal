package com.journalisation.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import com.journalisation.dao.bean.Auteurs;
import com.journalisation.dao.bean.Genre;
import com.journalisation.dao.bean.Livres;
import com.journalisation.dao.bean.Users;
import com.journalisation.dao.dao.DAOFactory;
import com.journalisation.dao.dao.DAOName;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.concurrent.Worker;

public class SearchController extends ControllerManager {
    @FXML
    private CheckBox select_auteur;

    @FXML
    private CheckBox select_titre;

    @FXML
    private CheckBox select_date;

    @FXML
    private CheckBox select_graph;

    @FXML
    private CheckBox select_correcteur;

    @FXML
    private CheckBox select_gender;

    @FXML
    private TextField search_field;

    @FXML
    private TableColumn<Livres, String> titre;
    @FXML
    private TableView<Livres> table_list;
    @FXML
    private TableColumn<Livres, Genre> genre;

    @FXML
    private TableColumn<Livres , LocalDate> date;

    @FXML
    private TableColumn<Livres  , String> write;

    @FXML
    private TableColumn<Livres  , String> lead;

    @FXML
    private TableColumn<Livres  , String> graphist;


    private ObservableList<Livres> projects=FXCollections.observableArrayList();
    private ObservableList<Livres> result;


    private final Set<String> criteres=new HashSet<>();
    @FXML
    private void search() {

    }

    @FXML
    private void searchtype(KeyEvent event) {
        projects.clear();
        String value=((TextField)event.getSource()).getText().toLowerCase().trim();
        for (Livres livres : result) {
            if(livres.getTitre().trim().toLowerCase().contains(value)||livres.getSoustitre().trim().toLowerCase().contains(value)||
            AuteurToString(livres.getDirige()).toLowerCase().contains(value)||AuteurToString(livres.getEcrit()).toLowerCase().contains(value)
            ||UserToString(livres.getUserwork()).toLowerCase().contains(value)||livres.getGenre().getNom().toLowerCase().contains(value))
                projects.add(livres);
        }
    }
    @Override
    protected void init() {
        if(loader.getState()!= Worker.State.READY)
                loader.reset();
            loader.start();
        titre.setCellValueFactory(data->data.getValue().titreProperty());
        genre.setCellValueFactory(data->data.getValue().genreProperty());
        write.setCellValueFactory(data->{return new SimpleObjectProperty<>(AuteurToString(data.getValue().getEcrit()));});
        lead.setCellValueFactory(data->{return new SimpleObjectProperty<>(AuteurToString(data.getValue().getDirige()));});
        graphist.setCellValueFactory(data->{return new SimpleObjectProperty<>(UserToString(data.getValue().getUserwork()));});
        date.setCellValueFactory(data->data.getValue().receiptProperty());
        select_auteur.selectedProperty().addListener((o,a,n)->{
            validateCritere(select_auteur,"auteur");
        });
        select_titre.selectedProperty().addListener((o,a,n)->{
            validateCritere(select_titre,"titre");
        });
    }
    private void recherche(String critere){

    }
    String validateCritere(CheckBox check,String critere){
        if(check.isSelected()){
            criteres.add(critere+"=?");
        }else{
            if(criteres.contains(critere+"=?"))
                criteres.remove(critere+"=?");
        }
        System.out.println(String.join(" and ",criteres));
        return String.join(" and ",criteres);
    }

    String AuteurToString(ObservableList<Auteurs> auteurs){
        ObservableList<String> strauteur=auteurs.parallelStream().map(auteur->{return auteur.getPrenom()+" "+auteur.getNom();}).collect(Collectors.toCollection(()->FXCollections.observableArrayList()));
        return String.join("/",strauteur);
    }

    String UserToString(ObservableList<Users> users){
        ObservableList<String> struser=users.parallelStream().map(user->{return user.getPrenom()+" "+user.getNom();}).collect(Collectors.toCollection(()->FXCollections.observableArrayList()));
        return String.join("/",struser);
    }

    @Override
    protected void initialized() {
        super.initialized(); //To change body of generated methods, choose Tools | Templates.
        result= DAOFactory.createModel(DAOName.livre).selectAll();
        result.forEach(livre->{
            livre.setEcrit(FXCollections.observableArrayList(DAOFactory.createModel(DAOName.auteur).customSql("" +
                    "select id,nom,prenom,notice from ecritpar where ref_livre=?",livre.getId())));
            livre.setDirige(FXCollections.observableArrayList(DAOFactory.createModel(DAOName.auteur).customSql("" +
                    "select id,nom,prenom,notice from dirigepar where ref_livre=?",livre.getId())));

            livre.setUserwork(FXCollections.observableArrayList(DAOFactory.createModel(DAOName.user).
                    customSql("select id, username,nom,prenom from usertask where livre_id=?",livre.getId())));
        });
        projects.addAll(result);
        table_list.itemsProperty().bind(new SimpleObjectProperty<>(projects));
    }
    
    
}
