package com.journalisation.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import com.journalisation.Main;
import com.journalisation.alert.Alert;
import com.journalisation.alert.AlertButtons;
import com.journalisation.dao.bean.Auteurs;
import com.journalisation.dao.dao.DAOFactory;
import com.journalisation.dao.dao.DAOName;
import com.journalisation.resources.view.ViewBehavior;
import com.journalisation.utils.Boutton;
import com.journalisation.utils.ViewUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.journalisation.Main.*;
import javafx.stage.FileChooser;

public class AuteurController extends ControllerManager {
    
    private FileChooser filechooser;
    @FXML
    private TableView<Auteurs> table_auteurs;

    @FXML
    private TableColumn<Auteurs, Integer> id;

    @FXML
    private TableColumn<Auteurs, String> nom;

    @FXML
    private TableColumn<Auteurs, String> prenom;

    @FXML
    private TableColumn<Auteurs, String> notice;

    @FXML
    private TableColumn<Auteurs, Auteurs> actions;

    private final ContextMenu cm=new ContextMenu();

    @FXML
    private ImageView image;

    @FXML
    private Label name;

    @FXML
    private Label firstname;

    @FXML
    void search(KeyEvent event) {
        filtreAuteur.clear();
        String critere=((TextField)event.getSource()).getText();
        auteurs.forEach(a->{
            if(a.getNom().toLowerCase().contains(critere.toLowerCase())||a.getPrenom().toLowerCase().contains(critere.toLowerCase()))
                filtreAuteur.add(a);
        });
        table_auteurs.refresh();
    }

    private ObservableList<Auteurs> filtreAuteur= FXCollections.observableArrayList(Main.auteurs);
    private ObjectProperty<ObservableList<Auteurs>> listdata= new SimpleObjectProperty<>(filtreAuteur);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        id.setCellValueFactory((data)->data.getValue().idProperty());
        nom.setCellValueFactory(data->data.getValue().nomProperty());
        prenom.setCellValueFactory(data->data.getValue().prenomProperty());
        notice.setCellValueFactory(data->data.getValue().noticeProperty());
        actions.setCellFactory(data->new ActionsCell());
        actions.setCellValueFactory(data->{return new SimpleObjectProperty<>(data.getValue());});
        table_auteurs.itemsProperty().bind(listdata);
        table_auteurs.getSelectionModel().selectedItemProperty().addListener((o,a,n)->{

            Details(n);
        });
        table_auteurs.getSelectionModel().select(0);
        table_auteurs.selectionModelProperty().addListener((o,a,n)->{
            if(n.isEmpty())
                Details(null);
        });
    }

    private void Details(Auteurs a){
        if(a==null){
            name.setText(null);
            firstname.setText(null);
            image.setImage(null);
        }else {
            name.setText(a.getNom());
            firstname.setText(a.getPrenom());
            File img=a.getImage();
//            if(img==null){
//                a.setPathimage(AppUtils.PATH_DOC+"\\default.png");
//                img=a.getImage();
//            }
                try {
                    System.out.println(a.getPathimage());
                    image.setImage(new Image(new FileInputStream(img),
                            image.getFitWidth(),image.getFitHeight(),false,false));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

        }
    }

    @Override
    protected void init() {
        Main.auteurs.clear();
        Main.auteurs.addAll(DAOFactory.createModel(DAOName.auteur).selectAll());
        cm.getItems().clear();
        filechooser=new FileChooser();
//        filechooser.setInitialFileName(STYLESHEET_MODENA);
        MenuItem mi=new MenuItem("Export");
        mi.setOnAction(action->{
            filechooser.showSaveDialog(primarystage);
        });
        //image.
        //listdata.get().addAll(Main.genres);
    }

    @FXML
    void newAuteur(ActionEvent event) {
        add_change(null);
    }

    private void add_change(Auteurs A){
        ViewBehavior newgenre=new ViewBehavior("new_auteur.fxml");
        Parent p=newgenre.getparent();
        ControllerManager cm=(ControllerManager)newgenre.getController();
        ((NewAuteurController)cm).setAuteur(A);
        Stage popup= ViewUtils.ModalFrame(p, primarystage);
        popup.showAndWait();
    }

    private class ActionsCell extends TableCell<Auteurs ,Auteurs> {
        private Boutton delete,edit,view;

        @Override
        public void updateIndex(int i) {
            super.updateIndex(i);
        }

        @Override
        protected void updateItem(Auteurs auteur, boolean b) {
            super.updateItem(auteur, b);
            if(!b && getItem()!=null){
                render(auteur);
            }
        }
        private void render(Auteurs auteur){

            edit=new Boutton("");
            view=new Boutton("info");
            delete=new Boutton("");
            edit.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.EDIT));
            delete.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.REMOVE));

            edit.setOnAction((action)->add_change(auteur));
            delete.setOnAction((action)->delete(auteur));
            HBox box=new HBox();
            box.getChildren().addAll(edit,delete);
            setGraphic(box);

        }

        private void delete(Auteurs auteur){
            if(Alert.showConfirmMessage(primarystage,"Voulez vous supprimer cet Auteur?",rbs)== AlertButtons.YES) {

            }
        }
    }
}
