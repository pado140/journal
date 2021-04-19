package com.journalisation.controller;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.journalisation.dao.bean.Documents;
import com.journalisation.dao.bean.Livres;
import com.journalisation.dao.bean.Ressources;
import com.journalisation.dao.bean.TaskProject;
import com.journalisation.dao.dao.DAOFactory;
import com.journalisation.dao.dao.DAOName;
import com.journalisation.resources.view.Document;
import com.journalisation.resources.view.Ressource;
import com.journalisation.resources.view.ViewBehavior;
import com.journalisation.utils.ViewUtils;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static com.journalisation.Main.primarystage;
import com.journalisation.dao.enumeration.TaskState;
import com.journalisation.resources.view.TaskByProjectCheck;

public class ProjectItemController extends ControllerManager {

    @FXML
    private Label title;

    @FXML
    private Label subtitle;

    @FXML
    private Label created;

    @FXML
    private VBox listDocs;

    @FXML
    private VBox listFiles;

    @FXML
    private JFXButton uploadFiles;

    @FXML
    private ScrollPane contentview;

    @FXML
    private ListView<TaskByProjectCheck> taches;

    @FXML
    private FlowPane doclist;
    
    private TaskProject task;

    private ObservableList<TaskProject> tasks= FXCollections.observableArrayList();
    private ObservableList<TaskByProjectCheck> taskcheck= FXCollections.observableArrayList();

    private Livres livre;

    public Livres getLivre() {
        return livre;
    }

    public void setLivre(Livres livre) {
        this.livre = livre;
        title.setText(livre.getTitre());
        subtitle.setText(livre.getSoustitre());
        created.setText(livre.getReceipt().format(DateTimeFormatter.ofPattern("dd/MMM/uuuu")));
        docs= DAOFactory.createModel(DAOName.doc).search("livres_id=?",false,livre.getId());
        res= DAOFactory.createModel(DAOName.res).search("livres_id=?",false,livre.getId());
        tasks.addAll(DAOFactory.createModel(DAOName.task_project).search("user_id=? and livre_id=?",false,getUser().getId(),livre.getId()));
        tasks.forEach(tp->{
            taskcheck.add(new TaskByProjectCheck(tp));
        });
        livre.setDocuments(docs);
        livre.setRessources(res);
        docs.forEach(d->{
            System.out.println("doc:"+d.getName());
            d.setLivres(livre);
            if(!d.getIsarchive())
                doclist.getChildren().add(new Document(d));
        });
        res.forEach(r->{
            r.setLivres(livre);
            if(!r.getIsarchive())
                doclist.getChildren().add(new Ressource(r));
        });

    }

    private void refresh(){
        doclist.getChildren().clear();
        docs.forEach(d->{
            if(!d.getIsarchive())
                doclist.getChildren().add(new Document(d));
        });
        res.forEach(r->{
            if(!r.getIsarchive())
            doclist.getChildren().add(new Ressource(r));
        });
    }
    private ObservableList<Documents> docs;
    private ObservableList<Ressources> res;
    @Override
    protected void init() {
        contentview.viewportBoundsProperty().addListener((o,a,n)->{
            doclist.setPrefSize(n.getWidth(),n.getWidth());
        });
    }

    private HBox createItem(Documents doc){
        HBox content=new HBox();
        content.setFillHeight(true);
        content.setSpacing(20);
        content.getChildren().addAll(new Label(doc.getName()),new Label(doc.getPages()+" pages"),new Label(doc.getMots()+" mots"));
        return content;
    }
    private HBox createRessource(Ressources res){
        HBox content=new HBox();
        content.setFillHeight(true);
        content.setSpacing(20);
        content.getChildren().addAll(new Label(res.getName()),new Label(res.getModifiedby()+" "),new Label(res.getCreated()+" mots"));
        return content;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        taches.itemsProperty().bind(new SimpleObjectProperty<>(taskcheck));
        //uploadFiles.setDisable(true);
        taches.itemsProperty().addListener((o,a,n)->{
            System.out.println(n.size());
        });
    }

    @FXML
    void upload(ActionEvent event) {
        ViewBehavior uploadFile=new ViewBehavior("uploadFiles.fxml");
        Parent p=uploadFile.getparent();
        ControllerManager cm=(ControllerManager)uploadFile.getController();
        ((UploadController)cm).setLivre(livre);
        Stage popup= ViewUtils.ModalFrame(p, primarystage);
        popup.showAndWait();
        ObservableList<Ressources> re=((UploadController) cm).getRessourcesAdded();
        re.forEach(r->{
            if(r instanceof Documents)
            docs.add((Documents)r);
            else
            res.add(r);
        });
        if(!res.isEmpty()){
            refresh();
        }
    }
    
    public TaskProject getTask() {
        return task;
    }
}
