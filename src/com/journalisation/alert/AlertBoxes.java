/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.alert;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import com.journalisation.resources.lang.TraductionUtils;
import com.journalisation.utils.Boutton;

/**
 *
 * @author Padovano
 */
public class AlertBoxes extends Stage{
    protected AnchorPane modal,content,header,displayButton;
    protected DialogPane dialogPaneModal;
    protected final JFXButton BTN_CLOSE=new JFXButton();
    protected final ObservableList<JFXButton> bouttons=FXCollections.observableArrayList();
    protected final ImageView icon=new ImageView();
    protected boolean closeable=true;
    protected final Label titre=new Label();
    protected Stage parent;
    protected Alerttype type;
    protected AlertDesign design=AlertDesign.defaut;
    private double x,y;
    private Boutton ok;
    private final Line line=new Line(0, 0, 305 , 0);
    protected ObjectProperty<String> tile;
    protected String title;
    protected final Runnable localeChange=this::translate;
    
    protected final ObjectProperty<TraductionUtils> traduction=new SimpleObjectProperty<>();

    public AlertBoxes(Stage parent, boolean closeable, Alerttype type, String titre) {
        this.parent=parent;
        this.closeable=closeable;
        this.type=type;
        this.title=titre;
        tile=new SimpleObjectProperty<>();
        this.titre.textProperty().bind(tile);
        traduction.addListener((observable, oldValue, newValue) -> {
            translate();
        });
        initOwner(parent);
        init();
        initLayout();
    }

    public void setCloseable(boolean closeable) {
        this.closeable = closeable;
    }
    
    private void init(){
        this.initStyle(StageStyle.TRANSPARENT);
        initModality(Modality.NONE);
        if(parent!=null)
            initModality(Modality.APPLICATION_MODAL);
        
        //initialize

        
    }
    
    protected void build(){
        //content.getChildren().addAll();
        header.getChildren().addAll(titre,BTN_CLOSE);
        modal.getChildren().addAll(header,line, content,icon,displayButton);
        Scene scene=new Scene(modal, null);
        this.setScene(scene);
    }

    
    public AnchorPane getContent() {
        return content;
    }

    public Stage getParent() {
        return parent;
    }

    public AnchorPane getDisplayButton() {
        return displayButton;
    }
    
    protected AlertBoxes I18n(TraductionUtils tr){
        if (traduction.get() != null) {
            traduction.get().removeListener(localeChange);
        }
        traduction.set(tr);
        //traduction.get().addListener(localeChange);
        return this;
    }

    private void material(){
        //Icon initialize
        icon.setFitWidth(75);
        icon.setFitHeight(75);
        icon.setImage(type.getIcon());
        icon.setLayoutX(150);
        icon.setLayoutY(20);

        //header initialize
        header=new AnchorPane();
        header.setPrefSize(432,173);
        header.getChildren().add(icon);
        displayButton=new AnchorPane();

        dialogPaneModal=new DialogPane();
        dialogPaneModal.setPrefSize(432,130);
        content=new AnchorPane();

    }

    private void statical(){
        //Icon initialize
        icon.setFitWidth(150);
        icon.setFitHeight(100);
        icon.setImage(type.getIcon());
        icon.setLayoutX(155);
        icon.setLayoutY(14);


        //header initialize
        header=new AnchorPane();
        header.setPrefSize(418,119);
        header.getChildren().add(icon);

        modal=new AnchorPane();
        content=new AnchorPane();
        displayButton=new AnchorPane();
        AnchorPane.setTopAnchor(content, 52.0);
        AnchorPane.setLeftAnchor(content, 107.0);
        AnchorPane.setRightAnchor(content, 28.0);
        AnchorPane.setBottomAnchor(content, 72.0);
        AnchorPane.setBottomAnchor(displayButton, 5.0);
        AnchorPane.setLeftAnchor(displayButton, 5.0);
        AnchorPane.setRightAnchor(displayButton, 5.0);
    }
    private void defaut(){
        modal=new AnchorPane();
        content=new AnchorPane();
        header=new AnchorPane();
        displayButton=new AnchorPane();
        BTN_CLOSE.setText("X");
        BTN_CLOSE.setOnAction((action)->{
            close();
        });
        x=0;y=0;
        modal.setOnMousePressed((MouseEvent)->{
            x=MouseEvent.getSceneX();
            y=MouseEvent.getSceneY();
        });
        modal.setOnMouseDragged((event)-> {
            setX(event.getScreenX()-x);
            setY(event.getScreenY()-y);

        });
        line.setStrokeWidth(1);
        line.setStroke(Paint.valueOf("#cccccc"));
        displayButton.setPrefHeight(40);
        displayButton.getChildren().addAll(bouttons);
        displayButton.setStyle("-fx-background-color:#ddd");
        //style
        modal.getStylesheets().add(getClass().getResource("/com/journalisation/resources/css/style.css").toExternalForm());
        BTN_CLOSE.getStyleClass().add("closable");
        titre.getStyleClass().add("titre");
        modal.getStyleClass().add("com/journalisation/alert");
        content.getStyleClass().add("content");
        icon.getStyleClass().add(type.getStyleClass());
        icon.setImage(type.getIcon());
        icon.setFitWidth(75);
        icon.setFitHeight(75);

        //build
        AnchorPane.setRightAnchor(BTN_CLOSE, 7.0);
        AnchorPane.setTopAnchor(BTN_CLOSE, 10.0);
        AnchorPane.setLeftAnchor(header, 5.0);
        AnchorPane.setRightAnchor(header, 5.0);
        AnchorPane.setRightAnchor(line, 5.0);
        AnchorPane.setLeftAnchor(titre, 19.0);
        AnchorPane.setTopAnchor(titre, 10.0);
        AnchorPane.setLeftAnchor(icon, 30.0);
        AnchorPane.setTopAnchor(icon, 48.0);
        AnchorPane.setBottomAnchor(icon, 58.0);
        AnchorPane.setTopAnchor(content, 52.0);
        AnchorPane.setLeftAnchor(content, 107.0);
        AnchorPane.setRightAnchor(content, 28.0);
        AnchorPane.setBottomAnchor(content, 72.0);
        AnchorPane.setBottomAnchor(displayButton, 5.0);
        AnchorPane.setLeftAnchor(displayButton, 5.0);
        AnchorPane.setRightAnchor(displayButton, 5.0);
        line.setLayoutX(5);
        line.setLayoutY(42);
        modal.setMaxWidth(300);

    }
    public void initLayout(){
        switch ((AlertDesign)design){
            case material:
                material();
                break;
            case custom:
                statical();
                break;
            default: defaut();
        }

    }
    public boolean isI18n(){
        return traduction.get()!=null;
    }
    protected void translate(){
        TraductionUtils t=traduction.get();
        if(!isI18n()){
            System.err.println(isI18n());
            return;
        }
              Platform.runLater(()->{
                  tile.set(t.Translate(title));
                  
              });
            
    }
    
}
