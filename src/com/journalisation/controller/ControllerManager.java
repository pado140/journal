/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.controller;


import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import com.journalisation.Main;
import com.journalisation.dao.bean.ImplEntity;
import com.journalisation.dao.bean.Livres;
import com.journalisation.dao.bean.Users;
import com.journalisation.dao.dao.DAOFactory;
import com.journalisation.resources.lang.TraductionUtils;
import com.journalisation.resources.menu.vertical.InitMenu;
import com.journalisation.resources.view.ViewBehavior;
import com.journalisation.utils.ViewUtils;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Parent;

/**
 *
 * @author Padovano
 */
public abstract class ControllerManager implements Initializable{
    protected final Runnable localeChange=this::translate;
    protected final ObservableMap<String ,Labeled> toTranslate=FXCollections.observableHashMap();
    protected final ObjectProperty<TraductionUtils> traduction=new SimpleObjectProperty<>();
    protected AnchorPane main_panel;
    protected DAOFactory<ImplEntity> factory;
    protected Stage loadDialog;
    public static ObjectProperty<Boolean> connected=new SimpleObjectProperty<>();
    protected load loader;
    public Users getUser() {
        return Main.users;
    }
    
    public Stage runloading(){
        if(loadDialog==null){
            loadDialog=ViewUtils.Modal("loading.fxml", Main.primarystage);
        }
        return loadDialog;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connected.addListener((obs,old,ne)->{
            System.out.println("ok disconneted");
        });
        traduction.addListener((observable, oldValue, newValue) -> {
            translate();
        });
        
        loader=new load();
        init();
        
    }

    protected void loadprojetinfo(Livres livre){
        String file="AdminProjet.fxml";
        if(Main.users.getType().equalsIgnoreCase("user"))
            file="a_project.fxml";
        ViewBehavior vb=new ViewBehavior(file);
        Parent pane=vb.getparent();
        ControllerManager controller=vb.getController();
        if(Main.users.getType().equalsIgnoreCase("user"))
            ((ProjectItemController)controller).setLivre(livre);
        else{
            ((AdminProjetController)controller).setProject(livre);
        }
        InitMenu.parent.getChildren().clear();
        AnchorPane.setLeftAnchor(pane,10.0);
        AnchorPane.setRightAnchor(pane,10.0);
        AnchorPane.setTopAnchor(pane,10.0);
        AnchorPane.setBottomAnchor(pane,10.0);
        InitMenu.parent.getChildren().add(vb.getparent());
    }
    protected abstract void init();
    
    public void setUser(Users user) {
        Main.users = user;
    }

    public AnchorPane getMain_view() {
        return main_panel;
    }

    public void setMain_view(AnchorPane main_view1) {
        this.main_panel = main_view1;
    }

    protected void translate(){
        if(!isI18n()){
            System.err.println(isI18n());
            return;
        }
        System.err.println(traduction.get());
        toTranslate.keySet().stream().forEach((text) -> {
            translateable(toTranslate.get(text), text);
        });
        
    }
    
    public void I18n(TraductionUtils tr){
        if (traduction.get() != null) {
            traduction.get().removeListener(localeChange);
        }
        if(tr==null)
            return;
        traduction.set(tr);
        traduction.get().addListener(localeChange);
        System.out.println("traduction:"+tr);
    }
    
    protected boolean isI18n(){
        return traduction.get()!=null;
    }
    
    protected void translateable(StringProperty lab){
        
    }
    protected void translateable(Labeled lab,String label){
        Platform.runLater(()->{lab.setText(traduction.get().Translate(label));});
    }
    protected void translateable(Pane pane){
        pane.getChildren().parallelStream().forEach(node -> {
            if(node instanceof Labeled) {
                if(((Labeled) node).getId()!=null)
                Platform.runLater(() -> {
                    ((Labeled) node).setText(traduction.get().Translate(((Labeled) node).getId()));
                });
            }
            if(node instanceof TableView){
                ((TableView)node).getColumns().parallelStream().forEach(o -> {
                    if(((TableColumn)o).getId()!=null)
                    Platform.runLater(()->{((TableColumn)o).setText(traduction.get().Translate(((TableColumn)o).getId()));});
                });

            }
            if(node instanceof Pane) {
                translateable((Pane)node);
            }
        });
    }

    protected Stage getContext(Node n){
        return (Stage)n.getScene().getWindow();
    }
    
    protected void initialized(){};
    protected class load extends Service<Void> {
        @Override
        protected Task<Void> createTask() {
            return new Task(){
                @Override
                protected Void call() throws Exception {
                    initialized();
                    return null;
                }
                

                @Override
                protected void succeeded() {
                    System.out.println("executed");
                    super.succeeded(); //To change body of generated methods, choose Tools | Templates.
                    runloading().close();
                    Main.primarystage.setOpacity(1);
                }

                @Override
                protected void failed() {
                    super.failed(); //To change body of generated methods, choose Tools | Templates.
                    runloading().close();
                    Main.primarystage.setOpacity(1);
                }

                @Override
                protected void cancelled() {
                    super.cancelled(); //To change body of generated methods, choose Tools | Templates.
                    runloading().close();
                    Main.primarystage.setOpacity(1);
                }

                @Override
                protected void scheduled() {
                    super.scheduled(); //To change body of generated methods, choose Tools | Templates.
                    runloading().close();
                    Main.primarystage.setOpacity(1);
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
