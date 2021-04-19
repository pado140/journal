package com.journalisation.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import com.journalisation.Main;
import com.journalisation.alert.Alert;
import com.journalisation.alert.AlertButtons;
import com.journalisation.dao.bean.Genre;
import com.journalisation.dao.bean.Livres;
import com.journalisation.dao.bean.TaskProject;
import com.journalisation.dao.dao.DAOFactory;
import com.journalisation.dao.dao.DAOName;
import com.journalisation.resources.menu.vertical.InitMenu;
import com.journalisation.resources.view.ViewBehavior;
import com.journalisation.utils.Boutton;
import com.journalisation.utils.ViewUtils;
import com.journalisation.utils.icon.IconImage;

import java.net.URL;
import java.util.ResourceBundle;

import static com.journalisation.Main.primarystage;
import static com.journalisation.Main.rbs;

public class ProjectController extends ControllerManager {
    @FXML
    private TableView<Livres> table_projet;

    @FXML
    private TableColumn<Livres, Integer> id;

    @FXML
    private TableColumn<Livres, String> title;

    @FXML
    private TableColumn<Livres, String> subtitle;

    @FXML
    private TableColumn<Livres, Genre> genre;

    @FXML
    private TableColumn<Livres, Livres> action;

    private  ObservableList<Livres> livres= DAOFactory.createModel(DAOName.livre).selectAll();
    private  ObservableList<Livres> livreFilter= FXCollections.observableArrayList();
    @FXML
    void newProjet(ActionEvent event) {
        add_change(null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        id.setCellValueFactory((data)->data.getValue().idProperty());
        title.setCellValueFactory(data->data.getValue().titreProperty());
        subtitle.setCellValueFactory(data->data.getValue().soustitreProperty());
        genre.setCellValueFactory(data->data.getValue().genreProperty());
        action.setCellValueFactory(data->{
            return new ReadOnlyObjectWrapper<>(data.getValue());
        });
        action.setCellFactory(data->new ActionsCell());
        table_projet.itemsProperty().bind(new SimpleObjectProperty<>(livreFilter));
        table_projet.getSelectionModel().selectedItemProperty().addListener((o,a,n)->{
            switchNode(new ViewBehavior("a_project.fxml"),n);
        });
    }

    private void add_change(Livres L){
        ViewBehavior newProjet=new ViewBehavior("creer_projet.fxml");
        Parent p=newProjet.getparent();
        ControllerManager cm=(ControllerManager)newProjet.getController();
        ((NewProjectController)cm).setLivre(L);
        Stage popup= ViewUtils.ModalFrame(p, primarystage);
        popup.initStyle(StageStyle.DECORATED);
        popup.showAndWait();
    }
    @Override
    protected void init() {
        ObservableList<TaskProject> task=DAOFactory.createModel(DAOName.task_project).search("user_id=?",false,getUser().getId());
        task.stream().filter((tp) -> (!Main.projets.contains(tp.getLivre()))).forEachOrdered((tp) -> {
            System.out.println(tp.getLivre().getId());
            if((Livres)DAOFactory.createModel(DAOName.livre).selectById(tp.getLivre().getId())!=null)
            Main.projets.add((Livres)DAOFactory.createModel(DAOName.livre).selectById(tp.getLivre().getId()));
        });
        livres=Main.projets;
        livreFilter.addAll(livres);
    }

    private void switchNode(ViewBehavior vb, Livres l){
        //System.out.println("parent:"+content.getParent());
//        Object controller=vb.getController();
//            System.out.println(controller);
//            ((ControllerManager)controller).I18n(t);

        //parent.centerProperty().set(vb.getparent());
        Parent pane=vb.getparent();
        ProjectItemController controller=(ProjectItemController)vb.getController();
        controller.setLivre(l);
        InitMenu.parent.getChildren().clear();
        AnchorPane.setLeftAnchor(pane,10.0);
        AnchorPane.setRightAnchor(pane,10.0);
        AnchorPane.setTopAnchor(pane,10.0);
        AnchorPane.setBottomAnchor(pane,10.0);
        InitMenu.parent.getChildren().add(vb.getparent());

    }


    private class ActionsCell extends TableCell<Livres,Livres> {
        private Boutton delete,edit,view;

        @Override
        protected void updateItem(Livres livre, boolean b) {
            super.updateItem(livre, b);
            if(!b && livre!=null && getIndex()< livres.size()){
                render(livre);
            }
        }
        private void render(Livres livre){

            edit=new Boutton("");
            view=new Boutton("info");
            delete=new Boutton("");
            edit.setGraphic(icon("edit"));
            view.setGraphic(icon("infos"));
            delete.setGraphic(icon("delete"));

            edit.setOnAction((action)->edit(livre));
            delete.setOnAction((action)->delete(livre));
            view.setOnAction(action->info(livre));
            HBox box=new HBox();
            box.getChildren().addAll(view);
            setGraphic(box);
        }

        private void edit(Livres part){
            add_change(part);
        }
        private void info(Livres part){
            switchNode(new ViewBehavior("a_project.fxml"),part);
        }

        private void delete(Livres part){
            if(Alert.showConfirmMessage(primarystage,"Would you like to delete this Part?",rbs)== AlertButtons.YES){

            }
        }

        private ImageView icon(String name){
            //Image im=new Image();
            ImageView iv=new ImageView();
            iv.setImage(IconImage.of(name));
            iv.setFitHeight(20);
            iv.setFitWidth(20);
            return iv;
        }

    }

    @FXML
    void filter(KeyEvent event) {
        livreFilter.clear();
        String value=((TextField)event.getSource()).getText().toLowerCase().trim();
        System.out.println(value);
        for (Livres livre : livres) {
            if(livre.getTitre().toLowerCase().contains(value))
                livreFilter.add(livre);
        }
    }

}
