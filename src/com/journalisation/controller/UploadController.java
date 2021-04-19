package com.journalisation.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.journalisation.Main;
import com.journalisation.alert.Alert;
import com.journalisation.dao.bean.Documents;
import com.journalisation.dao.bean.Livres;
import com.journalisation.dao.bean.Ressources;
import com.journalisation.dao.dao.DAOFactory;
import com.journalisation.dao.dao.DAOName;
import com.journalisation.utils.AppUtils;
import com.journalisation.utils.DocUtils;
import com.journalisation.utils.FileUtils;

import java.io.File;
import java.nio.file.Path;

import static com.journalisation.Main.users;
import java.nio.file.Paths;

public class UploadController extends ControllerManager {
    @Override
    protected void init() {
        fileChooser=new FileChooser();
        fileChooser.setTitle("Choisissez les fichiers ajouter dans le projet");
        listFiles.itemsProperty().bind(new SimpleObjectProperty<>(files));
        listFiles.getSelectionModel().selectedItemProperty().addListener((o,a,n)->{

        });
    }


    private final ObservableList<File> files= FXCollections.observableArrayList();
    private final ObservableList<Ressources> ressources= FXCollections.observableArrayList();
    private final ObservableList<Ressources> ressourcesAdded= FXCollections.observableArrayList();
    private Livres livre;

    public ObservableList<Ressources> getRessourcesAdded() {
        return ressourcesAdded;
    }

    private FileChooser fileChooser;

    public Livres getLivre() {
        return livre;
    }

    public void setLivre(Livres livre) {
        this.livre = livre;
        if(livre==null)
            this.livre=new Livres();
        System.out.println(this.livre);
    }
    @FXML
    private AnchorPane loader;

    @FXML
    private ListView<File> listFiles;

    @FXML
    void close(ActionEvent event) {
            ((Stage)loader.getScene().getWindow()).close();
    }

    @FXML
    void load(DragEvent event) {
        files.addAll(event.getDragboard().getFiles());
    }

    @FXML
    void open(ActionEvent event) {
        files.addAll(fileChooser.showOpenMultipleDialog(loader.getScene().getWindow()));
    }

    @FXML
    void upload(ActionEvent event) {
        setFiles();

        for(Ressources res:ressources){
            FileUtils fu=FileUtils.instance(res.getFile());
            String dest=AppUtils.PATH_PROJET;
            if(res instanceof Documents){
                //if(livre.getDocuments())
                for (Documents document : livre.getDocuments()) {
                    if(document.getName().equals(res.getName())){
                        File f=Paths.get(document.getPath()).toFile();
                        document.setName(getNameWithoutExtension(document.getName())+"-"+document.getNo()+getExt(document.getName()));
                        document.setPath(getNameWithoutExtension(document.getPath())+"-"+document.getNo()+getExt(document.getName()));
                        document.setIsarchive(true);
                        DAOFactory.createModel(DAOName.doc).save(document);
                        DAOFactory.createModel(DAOName.doc).trace(users,String.format("Document %s du projet %s a ete modifie en %s",res.getName(),livre.getTitre(),document.getName()),""+document.getId(),"0");
                        FileUtils.instance(f).renameToFile(document.getPath().substring(0,document.getPath().lastIndexOf(document.getName())-1),document.getName());
                        res.setNo(document.getNo()+1);
                    }
                }
            }else {
                for (Ressources document : livre.getRessources()) {
                    if(document.getName().equals(res.getName())){
                        File f=Paths.get(document.getPath()).toFile();
                        document.setName(getNameWithoutExtension(document.getName())+"-"+document.getNo()+getExt(document.getName()));
                        document.setPath(getNameWithoutExtension(document.getPath())+"-"+document.getNo()+getExt(document.getName()));
                        document.setIsarchive(true);
                        DAOFactory.createModel(DAOName.res).save(document);
                        //DAOFactory.createModel(DAOName.doc).trace(users,String.format("Document %s du projet %s a ete modifie en %s",res.getName(),livre.getTitre(),document.getName()),""+document.getId(),"0");
                        DAOFactory.createModel(DAOName.res).trace(users,String.format("Ressource %s du projet %s a ete modifie en %s",res.getName(),livre.getTitre(),document.getName()),"0",""+document.getId());
                        FileUtils.instance(f).renameToFile(document.getPath().substring(0,document.getPath().lastIndexOf(document.getName())),document.getName());
                        res.setNo(document.getNo()+1);
                    }
                }
            }
            Path path=Paths.get(dest,livre.getTitre().trim(),(res instanceof Documents?((Documents)res).getDOCPATH():res.getRESPATH()));

            if(fu.downloadFile(res.getFile(),path.toFile().getPath())) {
                path=Paths.get(dest,livre.getTitre().trim(),(res instanceof Documents?((Documents)res).getDOCPATH():res.getRESPATH()),res.getName());
                System.out.println("livre id:"+livre.getId());
                res.setLivres(livre);
                res.setPath(path.toFile().getPath());
                res.setUsers(users);
                if (DAOFactory.createModel(res instanceof Documents ? DAOName.doc : DAOName.res).save(res) != null) {
                    DAOFactory.createModel(DAOName.doc).trace(users, (res instanceof Documents ? "Document":"Ressource")+" (" + res.getName() + ") a éte ajoute dans le projet "+livre.getTitre()
                            ,(res instanceof Documents ? ""+res.getId():"0"),(res instanceof Documents ? "0":""+res.getId()));
                    ressourcesAdded.add(res);
                } else {
                    System.out.println(DAOFactory.createModel(DAOName.doc).connectionSql.getErreur());
                    System.err.println("erreur");
                    fu.deleteFile(path.toFile().getPath());
                }
            }
        }

        Alert.success((Stage)loader.getScene().getWindow(),"success", Main.rbs);
    }

    private void setFiles(){
        files.forEach(f->{
            System.out.println(f.getPath());
            if(FXCollections.observableArrayList(".docx",".doc",".pdf").contains(getExt(f.getPath()))){
                Documents doc=new Documents();
                doc.setFile(f);
                doc.setFormat(getExt(f.getPath()));
                doc.setPath(f.getPath());
                ressources.add(doc);
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

    private String getNameWithoutExtension(String path){
        return path.substring(0,path.lastIndexOf("."));
    }

}
