/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.resources.menu;

import com.jfoenix.controls.JFXCheckBox;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;
import com.journalisation.resources.lang.TraductionUtils;

/**
 *
 * @author Padovano
 */
public class MenuCheckBox extends JFXCheckBox implements MenuElements{
    private StringProperty label,key;
    private ImageView icon;
    private ObservableList<MenuCheckBox> child;

    private TraductionUtils translation;
    public MenuCheckBox(String text) {
        super(text);
        label=new SimpleStringProperty();
        key=new SimpleStringProperty();
        child=FXCollections.observableArrayList();
        label.addListener((o,a,n)->{
            Platform.runLater(()->{
                setText(n);
            });
        });
    }

    public MenuCheckBox() {
        label=new SimpleStringProperty();
        key=new SimpleStringProperty();
        child=FXCollections.observableArrayList();
        label.addListener((o,a,n)->{
            Platform.runLater(()->{
                setText(n);
            });
        });
    }

    public void initLayout(){
        this.autosize();
        this.getStyleClass().add("btn_nav");
        //this.setText(label.get());
        
        this.textProperty().bind(label);
        
        }
        
    public StringProperty getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label.set(label);
    }

    public StringProperty getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key.set(key);
    }

    public ImageView getIcon() {
        return icon;
    }

    public void setIcon(ImageView icon) {
        this.icon = icon;
    }

    public ObservableList<MenuCheckBox> getChild() {
        return child;
    }

    public void setChild(ObservableList<MenuCheckBox> child) {
        this.child = child;
    }

    public boolean addAll(MenuCheckBox... elements) {
        return child.addAll(elements);
    }

    public boolean add(MenuCheckBox e) {
        return child.add(e);
    }
    
    

    @Override
    public void translate(TraductionUtils t) {
        translation=t;
        if(translation==null)
            return;
        label.set(translation.Translate(key.get()));
        child.parallelStream().forEach(item->item.translate(t));
       
        //title.set(translation.Translate(label.get()));
        System.out.println("label:"+label.get()+"\t translate:"+translation.Translate(label.get()));
    }
    
    
    
}
