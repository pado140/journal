package com.journalisation.controller;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.journalisation.Main;
import static com.journalisation.Main.users;
import com.journalisation.alert.Alert;
import com.journalisation.dao.bean.*;
import com.journalisation.dao.dao.DAOFactory;
import com.journalisation.dao.dao.DAOName;
import com.journalisation.resources.view.ListViewMultiple;
import com.journalisation.utils.AppUtils;
import com.journalisation.utils.DocUtils;
import com.journalisation.utils.FileUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.concurrent.Worker;

public class NewProjectController extends ControllerManager {
    @FXML
    private TextField titre;

    private final ObservableList<File> files= FXCollections.observableArrayList();
    private final ObservableList<Documents> documents= FXCollections.observableArrayList();
    private final ObservableList<Ressources> ressources= FXCollections.observableArrayList();

    @FXML
    private TextField sous_titre;

    @FXML
    private DatePicker receipt;

    @FXML
    private ComboBox<Genre> genre;

    @FXML
    private TextField format;
    @FXML
    private AnchorPane dirige;

    @FXML
    private AnchorPane content;

    @FXML
    private AnchorPane ecrit;

    @FXML
    private AnchorPane loader;

    @FXML
    private ListView<File> listFiles;

    private Livres livre;

    private ListViewMultiple<Auteurs> dirigeby,ecritby;

    private FileChooser fileChooser;
    
    private load loading;
    
    
    public Livres getLivre() {
        return livre;
    }

    public void setLivre(Livres livre) {
        this.livre = livre;
        if(livre==null)
            this.livre=new Livres();
    }

    @FXML
    void closed() {
        ((Stage)dirige.getScene().getWindow()).close();
    }
    private Stage dialog;
    @Override
    protected void init() {
        //listFiles.setCellFactory(data->{return new ListAdapter();} );
        livre=new Livres();
        loading=new load();
        listFiles.itemsProperty().bind(new SimpleObjectProperty<>(files));
        listFiles.itemsProperty().addListener((o,a,n)->
        {

        });
        fileChooser=new FileChooser();
        genre.itemsProperty().bind(new ObservableValue<ObservableList<Genre>>() {
            @Override
            public void addListener(ChangeListener<? super ObservableList<Genre>> changeListener) {

            }

            @Override
            public void removeListener(ChangeListener<? super ObservableList<Genre>> changeListener) {

            }

            @Override
            public ObservableList<Genre> getValue() {
                return Main.genres;
            }

            @Override
            public void addListener(InvalidationListener invalidationListener) {

            }

            @Override
            public void removeListener(InvalidationListener invalidationListener) {

            }
        });

        loader.setOnDragOver((event)->{
            if(event.getDragboard().hasFiles())
                event.acceptTransferModes(TransferMode.ANY);
        });
        dirigeby=new ListViewMultiple<>();
        ecritby=new ListViewMultiple<>();
        dirigeby.getSources().addAll(Main.auteurs);
        ecritby.getSources().addAll(Main.auteurs);

        dirigeby.render();
        ecritby.render();
        dirige.getChildren().add(render(dirigeby));
        ecrit.getChildren().add(render(ecritby));
    }

    ListViewMultiple render(ListViewMultiple<Auteurs> v){
        AnchorPane.setTopAnchor(v,0.0);
        AnchorPane.setBottomAnchor(v,0.0);
        AnchorPane.setLeftAnchor(v,0.0);
        AnchorPane.setRightAnchor(v,0.0);
        return v;
    }

    @FXML
    void create() {
        //this.loade
        if(loading.getState()!=Worker.State.RUNNING){
        if(loading.getState()!= Worker.State.READY)
                loading.reset();
            loading.start();
        }
                            
    }
    @FXML
    void load(DragEvent event) {
        files.addAll(event.getDragboard().getFiles());
        //setFiles();
    }

    @FXML
    void open(ActionEvent event) {
        files.addAll(fileChooser.showOpenMultipleDialog(dirige.getScene().getWindow()));
        //setFiles();
    }

    private void setFiles(){
        files.forEach(f->{
            System.out.println(f.getPath());
            if(FXCollections.observableArrayList(".docx",".doc",".pdf").contains(getExt(f.getPath()))){
                    Documents doc=new Documents();
                    doc.setFile(f);
                    doc.setFormat(getExt(f.getPath()));
                    doc.setPath(f.getPath());
                    documents.add(doc);
            }else{
                Ressources res=new Ressources();
                res.setFile(f);
                res.setModifiedby(getUser().getUsername());
                res.setDescription("Ressource du projet");
                res.setFormat(getExt(f.getPath()));
                res.setPath(f.getPath());
                ressources.add(res);
            }

        });
    }

    private void DocFile(File f){
        DocUtils du=new DocUtils(f);
        System.out.println(du.docProperties().getPages()+" pages");
        System.out.println(du.docProperties().getWords()+" mots");
        System.out.println(du.docProperties().getParagraphs()+" paragraphs");
    }

    private String getExt(String path){
        System.out.println(path.substring(path.lastIndexOf(".")));
        return path.substring(path.lastIndexOf("."));
    }

    private class ListAdapter extends ListCell<File>{
        @Override
        public void updateIndex(int i) {
            super.updateIndex(i);
        }

        @Override
        protected void updateItem(File file, boolean b) {

            setGraphic(new fileName(file.getName(),0));
        }
    }

    private class SaveServices extends Service<Void>{

        @Override
        protected Task<Void> createTask() {
            return null;
        }
    }

    private class fileName extends Label{
        private int index;
        public fileName(String s,int index) {
            super(s);
            this.index=index;
            this.hoverProperty().addListener((o,a,n)->{
                if(n.booleanValue()){
                    System.out.println(n);
                    this.setBackground(new Background(new BackgroundFill(Color.valueOf("#ffdac4")
                    , CornerRadii.EMPTY, Insets.EMPTY)));
                }
                else{
                    this.setBackground(Background.EMPTY);
                }
            });

        }
    }
    @FXML
    void clear(ActionEvent event) {
        files.clear();
    }

    @Override
    protected void initialized() {
        super.initialized(); //To change body of generated methods, choose Tools | Templates.
    }
    
    private class load extends Service<Livres> {
        @Override
        protected Task<Livres> createTask() {
            return new Task(){
                @Override
                protected Livres call() throws Exception {
                    if(AppUtils.validation(content,sous_titre)){
                        System.out.println("test");
                        livre.setTitre(titre.getText().trim());
                        livre.setSoustitre(sous_titre.getText());
                        livre.setDirige(dirigeby.getCible());
                        livre.setEcrit(ecritby.getCible());
                        livre.setFormat(format.getText());
                        livre.setReceipt(receipt.getValue());
                        livre.setGenre(genre.getValue());

                        livre=(Livres) DAOFactory.createModel(DAOName.livre).save(livre);
                        System.out.println("livre id:"+livre.getId());
                        if(livre!=null){
                            setFiles();

                            for(Documents doc:documents){
                                FileUtils fu=FileUtils.instance(doc.getFile());
                                String dest=AppUtils.PATH_PROJET;
                                Path path=Paths.get(dest,livre.getTitre().trim(),doc.getDOCPATH());

                                if(fu.downloadFile(doc.getFile(),path.toFile().getPath())) {
                                    path=Paths.get(dest,livre.getTitre().trim(),doc.getDOCPATH(),doc.getName());
                                    System.out.println("livre id:"+livre.getId());
                                    doc.setLivres(livre);
                                    doc.setPath(path.toFile().getPath());
                                    if (DAOFactory.createModel(DAOName.doc).save(doc) != null) {
                                        DAOFactory.createModel(DAOName.doc).trace(users, "Document"+" (" + doc.getName() + ") a éte ajoute dans le projet "+livre.getTitre()
                                        ,""+doc.getId(),"0");
                                    } else {
                                        System.out.println(DAOFactory.createModel(DAOName.doc).connectionSql.getErreur());
                                        System.err.println("erreur");
                                        fu.deleteFile(path.toFile().getPath());
                                    }
                                }
                            }
                            for(Ressources res:ressources){
                                FileUtils fu=FileUtils.instance(res.getFile());
                                String dest=AppUtils.PATH_PROJET;
                                Path path=Paths.get(dest,livre.getTitre().trim(),res.getRESPATH());

                                if(fu.downloadFile(res.getFile(),path.toFile().getPath())) {
                                    path=Paths.get(dest,livre.getTitre().trim(),res.getRESPATH(),res.getName());
                                    System.out.println("livre id:"+livre.getId());
                                    res.setLivres(livre);
                                    res.setPath(path.toFile().getPath());
                                    if (DAOFactory.createModel(DAOName.res).save(res) != null) {
                                        DAOFactory.createModel(DAOName.res).trace(users, "Ressource"+" (" + res.getName() + ") a éte ajoute dans le projet "+livre.getTitre()
                                        ,"0",""+res.getId());
                                    } else {
                                        System.out.println(DAOFactory.createModel(DAOName.res).connectionSql.getErreur());
                                        System.err.println("erreur");
                                        fu.deleteFile(path.toFile().getPath());
                                    }
                                }
                            }
                            
                        }
                    }
                    return livre;
                }
                

                @Override
                protected void succeeded() {
                    System.out.println("executed");
                    super.succeeded(); //To change body of generated methods, choose Tools | Templates.
                    runloading().close();
                    Livres result=(Livres)this.getValue();
                    Alert.success((Stage)dirige.getScene().getWindow(),"success",Main.rbs);
                    loadprojetinfo(result);
                }

                @Override
                protected void failed() {
                    super.failed(); //To change body of generated methods, choose Tools | Templates.
                    runloading().close();
                }

                @Override
                protected void cancelled() {
                    super.cancelled(); //To change body of generated methods, choose Tools | Templates.
                    runloading().close();
                }

                @Override
                protected void scheduled() {
                    super.scheduled(); //To change body of generated methods, choose Tools | Templates.
                    runloading().close();
                }
                

                @Override
                protected void running() {
                    super.running(); //To change body of generated methods, choose Tools | Templates.
                    runloading().show();
                }
            };

        }
    }
}
