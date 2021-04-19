/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.resources.menu;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextAlignment;
import com.journalisation.resources.lang.TraductionUtils;
import com.journalisation.resources.log.AppLog;

import java.util.logging.Level;

import static com.journalisation.utils.AppUtils.ucfirst;


/**
 *
 * @author Padovano
 */
public class MenuButton extends JFXButton implements MenuElements{
    private StringProperty label,title;
    private ImageView icon;
    private String action;
    
    
    private TraductionUtils translation;

    public MenuButton(String l) {
        this(l,null,false);
    }
    
    public MenuButton(String l,  String action, boolean pin) {
        label=new SimpleStringProperty(l);
        title=new SimpleStringProperty();
        setText(l);
        this.textProperty().bind(label);
        this.action = action;
    }

    public String getActionTag() {
        return action;
    }

    public void setActionTag(String action) {
        this.action = action;
    }


    
    public void initLayout(){
        this.setContentDisplay(ContentDisplay.TOP);
        this.setPrefHeight(90);
        this.setPrefWidth(83);
        this.setWrapText(true);
        this.setTextAlignment(TextAlignment.CENTER);
        
    }
    public StringProperty getLabel() {
        return label;
    }

    public void setLabel(StringProperty label) {
        this.label = label;
    }

    public ImageView getIconImage() {
        return icon;
    }

    public void setIconImage(ImageView icon) {
        try{
        this.icon=icon;
        
        this.setGraphic(icon);
        }catch(NullPointerException e){
            AppLog.Log("journalisation", Level.ALL, e.getMessage(), e);}
    }

    @Override
    public void translate(TraductionUtils tr){
        translation=tr;
        if(translation==null)
            return;
        Platform.runLater(()->{title.set(ucfirst(translation.Translate(label.get())));});
    }
    
}
