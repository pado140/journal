package com.journalisation;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import com.journalisation.alert.Alert;
import com.journalisation.alert.AlertButtons;
import com.journalisation.controller.MainController;
import com.journalisation.controller.RegisterController;
import com.journalisation.controller.SplashController;
import com.journalisation.dao.bean.*;
import com.journalisation.dao.dao.DAOFactory;
import com.journalisation.dao.dao.DAOName;
import com.journalisation.resources.lang.RessourceBundleSevices;
import com.journalisation.resources.view.ViewBehavior;
import com.journalisation.utils.ViewUtils;
import com.journalisation.utils.envUtility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main extends Application {
    public static Stage primarystage;
    public static Users users;
    public final static ObservableList<Genre> genres= FXCollections.observableArrayList();
    public final static ObservableList<Auteurs> auteurs= FXCollections.observableArrayList();
    public final static ObservableList<Roles> roles=FXCollections.observableArrayList();
    public final static ObservableList<Groups> groups=FXCollections.observableArrayList();
    public final static ObservableList<Livres> projets=FXCollections.observableArrayList();
    public static HostServices service;
    public static RessourceBundleSevices rbs = new RessourceBundleSevices();
    private static double x,y;
    //private final connectionVerification verificateur=new connectionVerification(ConnectionSql.instance());
    @Override
    public void start(Stage primaryStage) throws IOException {
        primarystage=primaryStage;
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("resources/icons/c3.png")));

        //rbs.changeLocale(Init.default_lang());
        initComponents();

    }

    private void initComponents(){
        service=getHostServices();
        
//        primarystage.initStyle(StageStyle.UNDECORATED);
//        primarystage.initStyle(StageStyle.TRANSPARENT);
        primarystage.setTitle("C3 Editions");
        x=0.0;
        y=0.0;
        
        ViewBehavior vb=new ViewBehavior("Splash.fxml");
        Stage change= ViewUtils.ModalFrame(vb.getparent(),primarystage);
        change.showAndWait();
        if(((SplashController)vb.getController()).isOk()) {
                if (DAOFactory.createModel(DAOName.user).selectAll().size() == 0) {
                    boolean New = showAddUser();
                    if (New)
                        showLogin();
                } else
                    showLogin();
            if (users != null) {
                if(users.getEnable())
                initApp();
            }
            primarystage.onCloseRequestProperty().addListener((o, a, n) -> {
                n.toString();
            });
            primarystage.setOnCloseRequest((event) -> {
                //requestClose();
            });
        }

    }

    private void initApp(){
//        Thread t=new Thread(verificateur);
//        t.setDaemon(true);
//        t.start();
        if(users.getPass().equals(users.getDefaultpass())){
            ViewBehavior vb=new ViewBehavior("changepass.fxml");
            Stage change= ViewUtils.ModalFrame(vb.getparent(),primarystage);
            change.showAndWait();
            initApp();
        }else {
            ViewBehavior main = new ViewBehavior("new_main.fxml");
            Parent root = main.getparent();
            MainController controller = (MainController) main.getController();
            controller.I18n(rbs);
            Scene scene = new Scene(root);
            scene.setFill(null);
            primarystage.setScene(scene);

            primarystage.show();
        }
    }

    void requestClose(){

        try {
        if(Files.list(Paths.get(System.getenv("TEMP"), "c3temp")).count()>0){
            if(Alert.showConfirmMessage(primarystage,"Vous avez des fichiers ouverts, \n si vous fermer l'Application vous perdrez toutes les modifications que vous aviez apporte",rbs)== AlertButtons.YES){
                Files.list(Paths.get(System.getenv("TEMP"), "c3temp")).forEach(path -> {
                    Alert.showInputMessage(primarystage,"Ajouter des commentaires:",rbs);
                });
            }
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
//        GenreModel am=new GenreModel();
//        am.save(new Genre(Map.of("nom","Literature")));
//        ObservableList<Genre> auteurs=am.selectAll();
//        System.out.println(auteurs.size());
//        auteurs.forEach(a->{
//            System.out.println(a);
//        });
    }

    private boolean showAddUser(){
        ViewBehavior vb=new ViewBehavior("REGISTER.fxml");
        Parent rootLog = vb.getparent();
        RegisterController controller=(RegisterController)vb.getController();
        controller.setInitsu(true);
        Stage signup =ViewUtils.ModalFrame(rootLog, primarystage);
        signup.showAndWait();
        return controller.getUser()!=null;
    }
    public static void showLogin(){
        Stage login=new Stage(StageStyle.UNDECORATED);
        login.setTitle("Login");
        login.initOwner(primarystage);
        login.initModality(Modality.APPLICATION_MODAL);
        ViewBehavior vb=new ViewBehavior("login.fxml");
        Parent rootLog = vb.getparent();
        rootLog.setOnMousePressed((MouseEvent)->{
            x=MouseEvent.getSceneX();
            y=MouseEvent.getSceneY();
        });
        rootLog.setOnMouseDragged((event)-> {
            login.setX(event.getScreenX()-x);
            login.setY(event.getScreenY()-y);

        });
        login.initStyle(StageStyle.TRANSPARENT);
        Scene scenelog=new Scene(rootLog);
        scenelog.setFill(null);
        login.setScene(scenelog);
        login.showAndWait();
    }


    public static void showConf(){
        Stage settup=new Stage(StageStyle.UNDECORATED);
        settup.setTitle("Login");
        settup.initOwner(primarystage);
        settup.initModality(Modality.APPLICATION_MODAL);
        ViewBehavior vb=new ViewBehavior("databaseSetup.fxml");
        Parent rootLog = vb.getparent();
        rootLog.setOnMousePressed((MouseEvent)->{
            x=MouseEvent.getSceneX();
            y=MouseEvent.getSceneY();
        });
        rootLog.setOnMouseDragged((event)-> {
            settup.setX(event.getScreenX()-x);
            settup.setY(event.getScreenY()-y);

        });
        settup.initStyle(StageStyle.TRANSPARENT);
        Scene scenelog=new Scene(rootLog);
        scenelog.setFill(null);
        settup.setScene(scenelog);
        settup.showAndWait();
        primarystage.close();
    }
}
