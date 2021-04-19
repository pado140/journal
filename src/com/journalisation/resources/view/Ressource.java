package com.journalisation.resources.view;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import com.journalisation.alert.Alert;
import com.journalisation.alert.AlertButtons;
import com.journalisation.dao.bean.Ressources;
import com.journalisation.dao.dao.DAOFactory;
import com.journalisation.dao.dao.DAOName;
import com.journalisation.resources.icons.IconImage;
import com.journalisation.services.listen;
import com.journalisation.utils.FileUtils;
import com.journalisation.utils.filelistener;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

import static com.journalisation.Main.primarystage;
import static com.journalisation.Main.users;
import static com.journalisation.resources.view.Document.getPath;
import com.journalisation.utils.AppUtils;
import com.journalisation.utils.envUtility;

public class Ressource extends AnchorPane {
    private enum typePath {
        DIRECTORY,FILE
    }

    private final String ICON_DOC="icon_doc",ICON_PDF="icon_pdf",ICON_IMAGE="icon_image",OTHER="file";
    private final Path path;
    private final Ressources doc;
    private final FileChooser fileChooser=new FileChooser();
    private final filelistener listener;
    private listen list;
    private Image icon;
    ContextMenu cm;
    JFXButton menuAction=new JFXButton();
    private MenuItem rename,delete,open;
    private ObjectProperty<String> titre=new SimpleObjectProperty<>();
    private final Label title=new Label();
    //private WatchKey
    private FileUtils fu;
    private String pathdoc;

    private void init(){

    }

    private void renameFile(){
        String paths=doc.getPath().substring(0, doc.getPath().lastIndexOf("\\"));
        File f=fu.getFile();
        String path=envUtility.user_agent().get("os.name").toString().toLowerCase().contains("mac")?
                paths.replaceFirst("([A-za-z]+:)|(^(/|\\\\){1,2}[0-9.]+(/|\\\\)$)", "/Volumes"):paths;
        String Newpath=fu.renameToFile(path);
        if(Newpath!=null) {
            String oldname=doc.getName();
            String name=Newpath.substring(Newpath.lastIndexOf("\\")+1);
            doc.setName(name);
            doc.setPath(Newpath);
            titre.set(name);
                DAOFactory.createModel(DAOName.res).save(doc);
                DAOFactory.createModel(DAOName.doc).trace(users, "Ressource (" + oldname + ") a éte renome en <<" + name,"0",doc.getId()+"");
            }


    }
    public Ressource(Ressources doc) {
        this.doc = doc;
        pathdoc=AppUtils.PATH_RES;
        if(envUtility.user_agent().get("os.name").toString().toLowerCase().contains("mac")){
                pathdoc=pathdoc.replaceFirst("([A-za-z]+:)|([\\/]{1,2}[0-9\\.]+[\\/])", "/Volumes/");
                System.out.println(pathdoc);
        }
        pathdoc=getPath(typePath.FILE, pathdoc,doc.getLivres().getTitre(),"Res",doc.getName()).toString();
        
        pathdoc=pathdoc.replace("\\", "/");
        System.out.println(pathdoc);
//        pathdoc=envUtility.user_agent().get("os.name").toString().toLowerCase().contains("mac")?
//                doc.getPath().replaceFirst("([A-za-z]+:)|(^[\\/]{1,2}[0-9.]+[\\/])", "/Volumes"):doc.getPath();
//        
        fu=FileUtils.instance(Paths.get(pathdoc).toFile());
        path=getPath(typePath.DIRECTORY,System.getenv("TEMP"),"c3temp");
        listener=filelistener.instance(path,false);
        this.focusedProperty().addListener((o,a,n)->{
            setStyle("-fx-background-color:#ccc;");
        });

        titre.set(doc.getName());
        title.textProperty().bind(titre);
        initMenu();
        open.setOnAction((event)->{
                AlertButtons but= Alert.showConfirmMessage(primarystage,"voulez-vous le telecharger ?",null);
                if(but==AlertButtons.YES){
                    Path p=getPath(typePath.FILE,System.getenv("TEMP"),"c3temp",doc.getName());
                    if(Files.exists(p)){
                        if(Alert.showConfirmMessage(primarystage,"voulez-vous remplacer le fichier existant",null)==AlertButtons.YES){
                            try {
                                Files.copy(getPath(typePath.FILE,pathdoc),p, StandardCopyOption.REPLACE_EXISTING);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }else
                    {
                        try {
                            Files.copy(getPath(typePath.FILE,pathdoc),p);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    p.toFile().deleteOnExit();
                    try {
                        Desktop.getDesktop().open(p.toFile());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println(p.toString());
                    //listener.processEvents(p.toString());
                }

        });
        if(users.getId()==doc.getUsers().getId())
            delete.setDisable(false);
        else
            delete.setDisable(true);
        cm=new ContextMenu();
        cm.getItems().addAll(open,rename,delete);
        doLayout();
        menuAction.setContextMenu(cm);
    }

    void initMenu(){
        rename=new MenuItem("renommer le fichier",new FontAwesomeIconView(FontAwesomeIcon.EDIT));
        rename.setOnAction(event->renameFile());
        delete=new MenuItem("Supprimer le fichier",new FontAwesomeIconView(FontAwesomeIcon.REMOVE));
        delete.setOnAction(evt->deleteFile());
        open=new MenuItem(String.format("Ouvrir le fichier "),new FontAwesomeIconView(FontAwesomeIcon.FOLDER_OPEN));
    }

    private void deleteFile(){
        File f=fu.getFile();
//        if(f.toPath().getFileSystem().isOpen()){
//            try {
//                f.toPath().getFileSystem().close();
//            } catch (IOException ex) {
//                Logger.getLogger(Ressource.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
        if(fu.deleteFile(pathdoc))
            DAOFactory.createModel(DAOName.res).delete(doc);

    }
    public static Path getPath(typePath t,String ... part){
        if(t== typePath.DIRECTORY){
        if(Files.notExists(Paths.get(part[0], Arrays.copyOfRange(part,1,part.length)))){
            try {
                Files.createDirectories(Paths.get(part[0], Arrays.copyOfRange(part,1,part.length)));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        }
        return Paths.get(part[0], Arrays.copyOfRange(part, 1, part.length));
    }

    private void doLayout(){
        boolean other=false;
        menuAction.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.NAVICON,"12"));
        if(doc.getFormat().contains("doc"))
            icon= IconImage.of(ICON_DOC);
        else if(doc.getFormat().contains("pdf"))
            icon= IconImage.of(ICON_PDF);
        else if(doc.getFormat().contains("jpg")||doc.getFormat().contains("jpeg")||
                doc.getFormat().contains("png")||doc.getFormat().contains("bmp")) {
            icon = IconImage.of(ICON_IMAGE);
        }
        else{ icon=IconImage.of(OTHER);
        other=true;}
        setPrefSize(185,200);
        getStyleClass().add("card-view");
        AnchorPane paneIcon=new AnchorPane();
        paneIcon.setPrefSize(147,141);
        paneIcon.setLayoutX(18);
        paneIcon.setLayoutY(15);
        paneIcon.setStyle("-fx-background-color: #eef;");
        ImageView imview=new ImageView(icon);
        imview.setLayoutX(2);
        imview.setFitHeight(143);
        imview.setFitWidth(153);
        paneIcon.getChildren().add(other?other(doc.getFormat()):imview);
        title.setLayoutX(18);
        title.setLayoutY(158);
        title.setPrefWidth(141);
        title.setWrapText(true);
        title.setEllipsisString("...");
        title.setTooltip(new Tooltip(doc.getName()));
        menuAction.setLayoutX(152);
        menuAction.setLayoutY(158);
        AnchorPane.setLeftAnchor(title,18.0);
        AnchorPane.setRightAnchor(menuAction,5.0);
        this.setStyle("-fx-background-color: #fff;");
        getChildren().addAll(paneIcon,title,menuAction);

    }

    private AnchorPane other(String extension){
        AnchorPane panel=new AnchorPane();
        Label l=new Label(extension.toUpperCase());
        l.getStyleClass().add("h1");
        l.setAlignment(Pos.CENTER);
        panel.setPrefSize(153,143);
        AnchorPane.setLeftAnchor(l,0.0);
        AnchorPane.setTopAnchor(l,0.0);
        AnchorPane.setRightAnchor(l,0.0);
        AnchorPane.setBottomAnchor(l,0.0);
        panel.getChildren().addAll(l);
        return panel;
    }


    private class menu extends MenuItem{

    }

}
