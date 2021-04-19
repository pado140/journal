/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.utils;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.image.ImageView;
import com.journalisation.resources.lang.TraductionUtils;
import com.journalisation.resources.log.AppLog;

import java.util.Map;
import java.util.logging.Level;

/**
 *
 * @author Padovano
 */
public class Boutton extends JFXButton{
    private StringProperty label,title;
    private ObservableMap<EventType,EventHandler> event;
    private ImageView icon;
    private TraductionUtils translation;


    public Boutton(String l) {
        label=new SimpleStringProperty(l);
        title=new SimpleStringProperty();
        this.textProperty().bind(label);
//                addListener((obs,ol,ne)->{
//            textProperty().setValue(ne);
//        });
        event=FXCollections.observableHashMap();
    }

    public void initLayout(){
        this.autosize();
    }
    public StringProperty getLabel() {
        return label;
    }

    public void setLabel(StringProperty label) {
        this.label = label;
    }
    public void setLabel(String label) {
        this.label.set(label);
    }


    public ObservableMap<EventType, EventHandler> getEvent() {
        return event;
    }

    public void setEvent(ObservableMap<EventType, EventHandler> event) {
        this.event = event;
    }

    public ImageView getIconImage() {
        return icon;
    }

    public void setIconImage(ImageView icon) {
        try{
        this.icon=icon;
        
        this.setGraphic(icon);
        }catch(NullPointerException e){
            AppLog.Log("Journalisation", Level.ALL, e.getMessage(), e);}
    }

    public EventHandler put(EventType key, EventHandler value) {
        return event.put(key, value);
    }

    public void putAll(Map<? extends EventType, ? extends EventHandler> m) {
        event.putAll(m);
    }
    
    public boolean addEvent(EventType t,EventHandler e){
        return event.put(t, e)!=null;
    }

    public void translate(TraductionUtils tr){
        translation=tr;
        System.out.println(tr);
        if(translation==null)
            return;
        Platform.runLater(()->{title.set(translation.Translate(label.get()));});
        //title.set(translation.Translate(label.get()));
        System.out.println("label:"+label.get()+"\t translate:"+translation.Translate(label.get()));
    }
    public void fireEvent(){
        event.keySet().parallelStream().forEach((e)->{
            this.addEventHandler(e, event::get);
        });
    }
 
}
