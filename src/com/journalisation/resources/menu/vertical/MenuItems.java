/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.resources.menu.vertical;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import com.journalisation.resources.lang.TraductionUtils;
import com.journalisation.resources.log.AppLog;
import com.journalisation.resources.menu.MenuElements;

import java.util.Map;
import java.util.logging.Level;

/**
 *
 * @author Padovano
 */
public class MenuItems extends JFXButton implements MenuElements {
    private StringProperty label,title,ids;
    private ObservableMap<EventType,EventHandler> event;
    private ImageView icon;
    private MenuBlock parent;
    private String action;
    private boolean pin;
    
    
    private TraductionUtils translation;

    public MenuItems(String l, MenuBlock parent) {
        this(l,parent,null,false);
    }
    
    public MenuItems(String l, MenuBlock parent, String action, boolean pin) {
        label=new SimpleStringProperty(l);
        title=new SimpleStringProperty(l);
        ids=new SimpleStringProperty();
        this.textProperty().bind(title);
        this.parent=parent;
        event=FXCollections.observableHashMap();
        this.action = action;
        this.pin = pin;
        this.setFont(Font.font(14));
        this.setGraphicTextGap(6);
        this.setAlignment(Pos.CENTER_LEFT);
    }

    public MenuBlock getparent() {
        return parent;
    }

    public void setParent(MenuBlock parent) {
        this.parent = parent;
    }

    public String getIds() {
        return ids.get();
    }

    public StringProperty idsProperty() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids.set(ids);
    }

    public String getActionTag() {
        return action;
    }

    public void setActionTag(String action) {
        this.action = action;
    }

    public boolean isPin() {
        return pin;
    }

    public void setPin(boolean pin) {
        this.pin = pin;
    }

    
    public void initLayout(){
        this.autosize();
        this.getStyleClass().add("menuitem");
        
        if(this.parent!=null){
            parent.addMenuItem(this);
        }
        
    }
    public StringProperty getLabel() {
        return label;
    }

    public void setLabel(StringProperty label) {
        this.label = label;
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
            AppLog.Log("gestion", Level.ALL, e.getMessage(), e);}
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
    @Override
    public void translate(TraductionUtils tr){
        translation=tr;
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
     public boolean hasParent(){
        return parent!=null;
     }
}
