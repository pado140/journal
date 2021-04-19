/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.utils;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import com.journalisation.resources.lang.TraductionUtils;

/**
 *
 * @author Padovano
 */
public class CreateCustomTab extends Tab{
    private String title,id,value;
    private ImageView icon;
    ContextMenu popup;
    protected final Runnable localeChange=this::translateable;
    protected final ObjectProperty<TraductionUtils> traduction=new SimpleObjectProperty<>();

    public CreateCustomTab(String title, String id, ImageView icon) {
        super(title);
        this.title = title;
        this.id = id;
        this.icon = icon;
        
        super.setGraphic(icon);
        initpopup();
        this.setContextMenu(popup);
        traduction.addListener((observable, oldValue, newValue) -> {
            translateable();
        });
        
    }

    public CreateCustomTab(String title, String id, ImageView icon,Node content) {
        super(title, content);
        this.title = title;
        this.id = id;
        this.icon = icon;
        traduction.addListener((observable, oldValue, newValue) -> {
            translateable();
        });
    }

    public CreateCustomTab() {
        initpopup();
        traduction.addListener((observable, oldValue, newValue) -> {
            translateable();
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
    @Override
    public boolean equals(Object obj) {
        CreateCustomTab OtherObject =(CreateCustomTab)obj;
        if(obj!=null)
            return this.title.equals(OtherObject.getTitle())&&this.getId().equals(OtherObject.getId());
        return false;
    }
    
    private void initpopup(){
        MenuItem item=new MenuItem("fermer");
        item.setOnAction((action)->{this.getTabPane().getTabs().remove(this);});
        popup=new ContextMenu(item);
        
    }

    public void closeable(){
        if(isClosable()){
            this.setContextMenu(popup);
        }
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        setText(title);
        this.title = title;
    }

    public ImageView getIcon() {
        return icon;
    }

    public void setIcon(ImageView icon) {
        setGraphic(icon);
        this.icon = icon;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        int hash=31;
        hash+=hash*(title.hashCode());
        hash+=hash*(id.hashCode());
        return hash;
    }

    protected void translateable(){
        this.setText(traduction.get().Translate(value));
        if(this.getContent() instanceof Pane) {
            Pane pane = (Pane) this.getContent();
            translateable(pane);
        }
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
}
