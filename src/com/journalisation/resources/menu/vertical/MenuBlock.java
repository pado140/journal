/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.resources.menu.vertical;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import com.journalisation.resources.lang.TraductionUtils;
import com.journalisation.resources.menu.MenuElements;

import java.util.Collection;

/**
 *
 * @author Padovano
 */
public class MenuBlock extends TitledPane implements MenuElements {
    private StringProperty label,title,ids;
    private ImageView icon;
    private TraductionUtils translation;
    private Accordion parent;
    private VBox container;
    
    private ObservableList<MenuItems> items;

    public MenuBlock(Accordion parent) {
        initComposant();
        this.parent=parent;
        //items.addListener(null);
        
    }

    public Accordion getParen() {
        return parent;
    }

    public void setParen(Accordion parent) {
        this.parent = parent;
        this.parent.getPanes().add(this);
    }
    

    public MenuBlock(StringProperty label, ObservableList<MenuItems> items, Accordion parent) {
        this.label = label;
        this.items = items;
        this.parent=parent;
    }
    
    private void initComposant(){
        label=new SimpleStringProperty();
        title=new SimpleStringProperty();
        ids=new SimpleStringProperty();
        icon=new ImageView();
        items=FXCollections.observableArrayList();
        this.textProperty().bind(title);
        
//                addListener((obs,ol,ne)->{
//            textProperty().setValue(ne);
//        });
        container=new VBox();
        container.setFillWidth(true);
        container.setSpacing(12);
    }
    public void initLayout(){
        container.getChildren().addAll(getItems());
        System.out.println(getItems());
        this.setContent(container);
        this.setGraphic(icon);
        
        if(parent!=null)
            parent.getPanes().add(this);
    }

    public StringProperty getLabel() {
        return label;
    }

    public void setLabel(String label) {
        Platform.runLater(()->{title.set(label);});
        this.label.set(label);
    }

    public String getIds() {
        return ids.get();
    }

    public StringProperty idsProperty() {
        return ids;
    }

    public void setIds(String id) {
        this.ids.set(id);
    }

    public ImageView getIconImage() {
        return icon;
    }

    public void setIconImage(ImageView icon) {
        this.icon = icon;
    }

    public ObservableList<MenuItems> getItems() {
        return items;
    }

    public void setItems(ObservableList<MenuItems> items) {
        this.items = items;
    }
    
    public void addMenuItem(MenuItems mi){
        this.items.add(mi);
        container.getChildren().addAll(mi);
    }
    
    public void addMenuItem(MenuItems mi, int id){
        this.items.add(id, mi);
    }
    
    public void addMenuItems(Collection<?extends MenuItems> items){
        if(items==null)
            return;
        this.items.addAll(items);
        container.getChildren().addAll(items);
    }
    @Override
    public void translate(TraductionUtils tr){
        translation=tr;
        if(translation==null)
            return;
        Platform.runLater(()->{title.set(translation.Translate(label.get()));});
        
        items.parallelStream().forEach(item->item.translate(tr));
    }

}
