package com.journalisation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.journalisation.Main;
import com.journalisation.alert.Alert;
import com.journalisation.dao.ORM.ConnectionSql;
import com.journalisation.dao.bean.Auteurs;
import com.journalisation.dao.dao.DAOFactory;
import com.journalisation.dao.dao.DAOName;
import com.journalisation.exceptions.DuplicateException;
import com.journalisation.utils.AppUtils;
import com.journalisation.utils.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class NewAuteurController extends ControllerManager {

    @FXML
    private AnchorPane content;
    @FXML
    private TextField nom;

    @FXML
    private TextField prenom;

    @FXML
    private TextArea notice;

    private Auteurs auteur;

    public Auteurs getAuteur() {
        return auteur;
    }

    private File selectedFile;

    public void setAuteur(Auteurs auteur) {
        this.auteur = auteur;
        if(auteur==null) {
            this.auteur = new Auteurs();
            try {
                auteur_image.setImage(new Image(new FileInputStream(AppUtils.PATH_RES+"\\"+"default.png"),
                        auteur_image.getFitWidth(),auteur_image.getFitHeight(),false,false));
            } catch (FileNotFoundException | NullPointerException e) {
               e.printStackTrace();
            }
            //auteur.setPathimage(AppUtils.PATH_DOC+"\\"+"default.png");
        }
        else{
            nom.setText(auteur.getNom());
            prenom.setText(auteur.getPrenom());
            notice.setText(auteur.getNotice());
            selectedFile=auteur.getImage();
            try {
                auteur_image.setImage(new Image(new FileInputStream(selectedFile),
                        auteur_image.getFitWidth(),auteur_image.getFitHeight(),false,false));
            } catch (FileNotFoundException | NullPointerException e) {
                auteur_image.setImage(null);
                //e.printStackTrace();
            }
        }
    }

    private final FileChooser fileChooser=new FileChooser();

    @Override
    protected void init() {

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("image","*.jpg","*.png"));
    }
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
        FileUtils fu=null;
        boolean isnew=auteur.isNew();
        if(AppUtils.validation(content)){
            auteur.setNom(nom.getText().trim());
            auteur.setPrenom(prenom.getText().trim());
            auteur.setNotice(notice.getText().trim());
            auteur.setPathimage(AppUtils.PATH_RES+"\\"+auteur.getAUTHOR_PATH()+"\\"+"default.png");
            try {

                if(selectedFile!=null) {
                    fu = FileUtils.instance(selectedFile);
                    if(fu.downloadFile(selectedFile,AppUtils.PATH_RES+"\\"+auteur.getAUTHOR_PATH())){
                        auteur.setPathimage(AppUtils.PATH_RES+"\\"+auteur.getAUTHOR_PATH()+"\\"+selectedFile.getName());
                    }
                }
                auteur = (Auteurs) DAOFactory.createModel(DAOName.auteur).save(auteur);
                if (auteur != null) {
                    if (isnew)
                        Main.auteurs.add(auteur);
                    else
                        Main.auteurs.set(Main.auteurs.indexOf(auteur), auteur);
                    Alert.success((Stage) nom.getScene().getWindow(), "Nouveau auteur ajouter avec succes", null);
                    closed();
                    return;
                }
                if(selectedFile!=null){
                    fu.deleteFile(AppUtils.PATH_RES+"\\"+auteur.getAUTHOR_PATH()+"\\"+selectedFile);
                }
                Alert.error((Stage) nom.getScene().getWindow(), "desolee" + ConnectionSql.instance().getErreur(), null);
                return;
            }catch (DuplicateException ex){
                Alert.error((Stage) nom.getScene().getWindow(), "desolee" + ex.getMessage(), null);
            }
        }
        Alert.error((Stage)nom.getScene().getWindow(),"Svp rempli le champs avant d'essayer d'enregistrer",null);
    }
    @FXML
    private ImageView auteur_image;

    @FXML
    void remove() {
        auteur_image.setImage(null);
    }

    @FXML
    void upload() {
        selectedFile=fileChooser.showOpenDialog(nom.getScene().getWindow());
        try {
            auteur_image.setImage(new Image(new FileInputStream(selectedFile),
                    auteur_image.getFitWidth(),auteur_image.getFitHeight(),false,false));
        } catch (FileNotFoundException e) {
            System.out.println("aucune image selectionne");
        }
    }
}
