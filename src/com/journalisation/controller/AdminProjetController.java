package com.journalisation.controller;

import com.jfoenix.controls.JFXButton;
import com.journalisation.Main;
import com.journalisation.alert.Alert;
import com.journalisation.alert.AlertButtons;
import com.journalisation.dao.bean.*;
import com.journalisation.dao.dao.DAOFactory;
import com.journalisation.dao.dao.DAOName;
import com.journalisation.resources.view.ProjectItem;
import com.journalisation.resources.view.TaskView;
import com.journalisation.resources.view.ViewBehavior;
import com.journalisation.utils.Boutton;
import com.journalisation.utils.FileUtils;
import com.journalisation.utils.ViewUtils;
import com.journalisation.utils.icon.IconImage;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

import static com.journalisation.Main.primarystage;
import static com.journalisation.Main.rbs;
import com.journalisation.dao.enumeration.TaskState;
import com.journalisation.services.TaskServices;
import java.awt.Desktop;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Collectors;
import javafx.concurrent.Worker;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class AdminProjetController extends ControllerManager{
    @FXML
    private ListView<ProjectItem> pojectlist;
    
    private DirectoryChooser directoryChooser;

    @FXML
    private Label project_name;

    @FXML
    private Label time;

    @FXML
    private VBox userlist;
    
    @FXML
    private AnchorPane taskpane;

    @FXML
    private AnchorPane alltaskpane;

    @FXML
    private AnchorPane completetaskpane;

    @FXML
    private ListView<TaskView> alltask;

    @FXML
    private TextField searchfield;
    
    @FXML
    private VBox projectfiles;

    @FXML
    private ListView<TaskView> completedtask;

    @FXML
    private Label taskcount;

    @FXML
    private Label completedcount;

    @FXML
    private TableView<Ressources> tableProjet;

    @FXML
    private TableColumn<Ressources, String> titre;

    @FXML
    private TableColumn<Ressources, String> type;

    @FXML
    private TableColumn<Ressources, LocalDate> modified;

    @FXML
    private TableColumn<Ressources, Integer> pages;

    @FXML
    private TableColumn<Ressources, Integer> mots;

    @FXML
    private TableColumn<Ressources, Ressources> actions;

    private FileUtils fu;

    @FXML
    private JFXButton uploadButton,btn_export;

    private Livres project;
    private ObjectProperty<ObservableList<ProjectItem>> items;
    private ObservableList<ProjectItem> itemlists;
    private final ObservableList<TaskView> taskByProject=FXCollections.observableArrayList();
    private final ObservableList<TaskView> CompleteTaskByProject=FXCollections.observableArrayList();
    private ObservableList<Users> users;
    private final ObservableList<Ressources> docs=FXCollections.observableArrayList();
    private ObservableList<Livres> livres;

    public Livres getProject() {
        return project;
    }

    public void setProject(Livres project) {
        this.project = project;
        if(project!=null){
            pojectlist.getSelectionModel().select(new ProjectItem(project));
        }
    }

    
    private HBox createItem(Documents doc){
        HBox content=new HBox();
        content.setFillHeight(true);
        content.setSpacing(20);
        content.getChildren().addAll(new Label(doc.getName()),new Label(doc.getPages()+" pages"),new Label(doc.getMots()+" mots"));
        return content;
    }


    @FXML
    void add_user() {

    }

    private void loadProjectItems(ObservableList<Livres> livre){
        itemlists= FXCollections.observableArrayList();
        livre.forEach((l) -> {
            itemlists.add(new ProjectItem(l));
        });
    }

    @FXML
    void createTask(ActionEvent event) {
        ViewBehavior newProjet=new ViewBehavior("newTask.fxml");
        Parent p=newProjet.getparent();
        NewTaskProjectController cm=(NewTaskProjectController)newProjet.getController();
        cm.setLivre(project);
        Stage popup=ViewUtils.ModalFrame(p, Main.primarystage);
        popup.showAndWait();
        initTask(project);
    }

    @Override
    protected void init() {
        if(loader.getState()!= Worker.State.READY)
                loader.reset();
            loader.start();
        pojectlist.getSelectionModel().selectedItemProperty().addListener((o,a,n)->{
            display(n);
            btn_export.setVisible(true);
            uploadButton.setVisible(true);
        });
        titre.setCellValueFactory((data)->data.getValue().nameProperty());
        type.setCellValueFactory((data)->{return new SimpleObjectProperty<>(data.getValue().getName().substring(data.getValue().getName().lastIndexOf(".")));});
        modified.setCellValueFactory(data->data.getValue().modifiedProperty());
        pages.setCellValueFactory(data->{
            ObjectProperty<Integer> val=new SimpleObjectProperty(0);
            if(data.getValue() instanceof Documents){
            val.set(((Documents)data.getValue()).getPages());
        }else{
            val.set(0);
            }
            return val;
        });
        mots.setCellValueFactory(data->{
            ObjectProperty<Integer> val=new SimpleObjectProperty(0);
            if(data.getValue() instanceof Documents){
                val.set(((Documents)data.getValue()).getMots());
            }else{
                val.set(0);
            }
            return val;
        });
        actions.setCellValueFactory(data->{
            return new SimpleObjectProperty<>(data.getValue());
        });
        actions.setCellFactory(data->new ActionsCell());
        
        btn_export.setVisible(false);
            uploadButton.setVisible(false);
        pojectlist.selectionModelProperty().addListener((o,a,n)->{
            btn_export.setVisible(!n.isEmpty());
            uploadButton.setVisible(!n.isEmpty());
        });
        users=FXCollections.observableArrayList();
        uploadButton.disableProperty().bind(new SimpleObjectProperty<>(project!=null));
        docs.clear();
        taskpane.widthProperty().addListener((observable, oldValue, newValue) -> {
            completetaskpane.setPrefWidth((newValue.doubleValue()/2)-50);
            alltaskpane.setPrefWidth((newValue.doubleValue()/2)-50);
        });
//        alltask.itemsProperty().bind(new SimpleObjectProperty<>(taskByProject));
//        alltask.setCellFactory(value->new EraseItem());
        completedtask.itemsProperty().bind(new SimpleObjectProperty<>(CompleteTaskByProject));
        tableProjet.itemsProperty().bind(new SimpleObjectProperty<>(docs));
        directoryChooser=new DirectoryChooser();
      
    }
    
        private class EraseItem extends ListCell<TaskView> {
        Button button = new Button("");
        @Override
        public void updateIndex(int i) {
            super.updateIndex(i);
        }
        
        @Override
        protected void updateItem(TaskView taskView, boolean b) {
            super.updateItem(taskView, b);
            if(!b && getItem()!=null){
                render(taskView);
            }
        }
        
        private void render(TaskView taskView){
            if(isEmpty())
            return;

            button.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.ERASER));            
            button.setTooltip(new Tooltip("Supprimer"));
            button.setOnAction(event -> {
                if(Alert.showConfirmMessage(primarystage, "Voulez vous supprimer cette tache", rbs)==AlertButtons.YES){
                    if(DAOFactory.createModel(DAOName.task_project).delete(taskView.getTask()))
                        getListView().getItems().remove(getItem());
                }
            }
            );
            
            HBox box=new HBox();
            box.getChildren().addAll(taskView,button);
            setGraphic(box);

        }
    }
    
    @FXML
    void openselected(MouseEvent event) {
        if(!tableProjet.getSelectionModel().isEmpty()){
            if(event.getButton()==MouseButton.PRIMARY){
                if(event.getClickCount()==2){
                    Ressources res=tableProjet.getSelectionModel().getSelectedItem();
                    Path p=Paths.get(res.getPath());
                    p.toFile().deleteOnExit();
                        try {
                            Desktop.getDesktop().open(p.toFile());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
            }
        }
    }

    private void display(ProjectItem pi){
        docs.clear();
        //projectfiles.getChildren().clear();
        project=pi.getLivre();
        LocalDate date=project.getReceipt();
        time.setText(LocalDate.now().toEpochDay()-date.toEpochDay()+"");
        project_name.setText(project.getTitre());
        docs.addAll(DAOFactory.createModel(DAOName.doc).search("livres_id=?",false,project.getId()));
        docs.addAll(DAOFactory.createModel(DAOName.res).search("livres_id=?",false,project.getId()));
        initTask(pi.getLivre());
        tableProjet.refresh();
    }

    private void initTask(Livres livre){
        taskByProject.clear();
        CompleteTaskByProject.clear();
        ObservableSet<TaskView> viewTask=FXCollections.observableSet();
        ObservableList<TaskProject> tasks_projects=TaskServices.getByProject(livre);
        for(TaskProject tp:tasks_projects){
            System.out.println("task state:"+tp.getState());
            if(Objects.equals(tp.getState(),TaskState.E))
                taskByProject.add(new TaskView(tp,true,alltask));
            else
                CompleteTaskByProject.add(new TaskView(tp,false,alltask));
            viewTask.add(new TaskView(tp,false,alltask));
        }
        alltask.setItems(taskByProject);
    }
    private class ActionsCell extends TableCell<Ressources,Ressources> {
        private Boutton delete,edit,view;

        @Override
        public void updateIndex(int i) {
            super.updateIndex(i);
        }

        @Override
        protected void updateItem(Ressources res, boolean b) {
            super.updateItem(res, b);
            if(!b && getItem()!=null){
                System.out.println(b);
                System.out.println(res);
                render(res);
            }
        }
        private void render(Ressources ressources){

            edit=new Boutton("");
            view=new Boutton("ouvrir");
            delete=new Boutton("");
            edit.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.EDIT));
            view.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.DOWNLOAD));
            delete.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.REMOVE));

            edit.setOnAction((action)->edit(ressources));
            delete.setOnAction((action)->delete(ressources));
            view.setOnAction(action->info(ressources));
            HBox box=new HBox();
            box.getChildren().addAll(edit,delete);
            setGraphic(box);

        }

        private void edit(Ressources ressources){
            String path=ressources.getPath();
            fu=FileUtils.instance(Paths.get(path).toFile());
            String newName=fu.renameToFile(path.substring(0, path.lastIndexOf("\\")));
            if(newName!=null){
                String oldname=ressources.getName();
                String name=newName.substring(newName.lastIndexOf("\\")+1);
                ressources.setName(name);
                ressources.setPath(newName);


                DAOFactory.createModel(ressources instanceof Documents?DAOName.doc:DAOName.res).save(ressources);
                DAOFactory.createModel(DAOName.doc).trace(getUser(), (ressources instanceof Documents?"Document":"Ressource")+" (" + oldname + ") a éte renome en <<" + name
                ,ressources instanceof Documents ?ressources.getId()+"":"0",ressources instanceof Documents ?"0":""+ressources.getId());
            }
        }

        private void info(Ressources res){
        }

        private void delete(Ressources ressources){
            if(Alert.showConfirmMessage(primarystage,"Voulez vous supprimer ce ressource?",rbs)== AlertButtons.YES) {
                fu=FileUtils.instance(Paths.get(ressources.getPath()).toFile());
                if (fu.deleteFile(ressources.getPath())) {
                    DAOFactory.createModel(ressources instanceof Documents ? DAOName.doc : DAOName.res).delete(ressources);
                    DAOFactory.createModel(DAOName.doc).trace(getUser(), (ressources instanceof Documents ? "Document" : "Ressource") + " (" + ressources.getName() + ") a éte supprime"
                            ,ressources instanceof Documents ?ressources.getId()+"":"0",ressources instanceof Documents ?"0":""+ressources.getId());
                    docs.remove(docs.indexOf(ressources));
                    tableProjet.refresh();
                }
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
    void upload(ActionEvent event) {
        ViewBehavior uploadFile=new ViewBehavior("uploadFiles.fxml");
        Parent p=uploadFile.getparent();
        ControllerManager cm=(ControllerManager)uploadFile.getController();
        ((UploadController)cm).setLivre(project);
        Stage popup= ViewUtils.ModalFrame(p, primarystage);
        popup.showAndWait();
        docs.addAll(((UploadController) cm).getRessourcesAdded());
    }

    @FXML
    void export(ActionEvent event) throws IOException {
        FileUtils fut=FileUtils.instance();
        directoryChooser.setTitle("Selectionnez le repertoire de sauvegarde");
        File f=directoryChooser.showDialog(primarystage);
        if(f!=null){
            for (Ressources doc : docs) {
                Path source=Paths.get(doc.getPath());
                Files.createDirectories(Paths.get(f.getPath(),project.getTitre()));
                String destination=Paths.get(f.getPath(),project.getTitre()).toFile().getPath();
                fut.downloadFile(source.toFile(),destination);
            }
        }
        Alert.showInfo(primarystage, "Upload status", "Extraction reussi", rbs);
    }

    @Override
    protected void initialized() {
        super.initialized(); //To change body of generated methods, choose Tools | Templates.
        livres= DAOFactory.createModel(DAOName.livre).selectAll();
        loadProjectItems(livres);
        items=new SimpleObjectProperty<>(itemlists);
        pojectlist.itemsProperty().bind(items);
    }

    @FXML
    void searchclick(MouseEvent event) {
        find(false);
    }

    @FXML
    void searchkey(KeyEvent event) {
        find(true);
    }
    
    private void find(boolean live ){
        if(live){
            loadProjectItems(FXCollections.observableArrayList(livres.parallelStream().filter((livre)->livre.getTitre().trim().toLowerCase().contains(searchfield.getText().trim().toLowerCase())).collect(Collectors.toList())));
        }else{
            loadProjectItems(FXCollections.observableArrayList(livres.parallelStream().filter((livre)->livre.getTitre().trim().equalsIgnoreCase(searchfield.getText().trim())).collect(Collectors.toList())));
        }
        items=new SimpleObjectProperty<>(itemlists);
        pojectlist.itemsProperty().bind(items);
    }
}
