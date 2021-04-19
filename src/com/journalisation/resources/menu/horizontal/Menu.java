/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.resources.menu.horizontal;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import com.journalisation.resources.lang.RessourceBundleSevices;
import com.journalisation.resources.lang.TraductionUtils;
import com.journalisation.resources.menu.MenuButton;
import com.journalisation.resources.menu.MenuElements;

import java.util.Collection;

import static com.journalisation.utils.AppUtils.ucfirst;


/**
 *
 * @author Padovano
 */
public class Menu extends Tab implements MenuElements {
    private StringProperty label,title;
    private TraductionUtils translation;
    private HBox container;
    
    private ObservableList<MenuButton> items;

    public Menu() {

        initComposant();
        this.setClosable(false);
    }
    
    public Menu(StringProperty label, ObservableList<MenuButton> items) {
        this.label = label;
        this.items = items;
    }
    
    private void initComposant(){
        label=new SimpleStringProperty();
        title=new SimpleStringProperty();
        items=FXCollections.observableArrayList();
        
        this.textProperty().bind(title);
        container=new HBox();
        container.setFillHeight(true);
        container.setSpacing(12);
    }
    public void initLayout(){
        //container.getChildren().addAll(getItems());
        System.out.println(getItems());
        this.setContent(container);
    }

    public StringProperty getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label.set(label);
    }

    public ObservableList<MenuButton> getItems() {
        return items;
    }

    public void setItems(ObservableList<MenuButton> items) {
        this.items = items;
    }
    
    public void addMenuButton(MenuButton mi){
        this.items.add(mi);
        container.getChildren().addAll(mi);
    }
    
    public void addMenuButton(MenuButton mi,int id){
        this.items.add(id, mi);
        container.getChildren().add(id,mi);
    }
    
    public void addMenuSeparator(SeparatorButtonMenu mi){
        container.getChildren().add(mi);
    }
    
    public void addMenuSeparator(SeparatorButtonMenu mi,int id){
        container.getChildren().add(id,mi);
    }
    
    public void addMenuButtons(Collection<?extends MenuButton> items){
        if(items==null)
            return;
        this.items.addAll(items);
        container.getChildren().addAll(items);
    }
    @Override
    public void translate(TraductionUtils t) {
        translation=t;
        if(translation==null)
            return;
        System.out.println(((RessourceBundleSevices)translation).getLocale());
        Platform.runLater(()->{
            title.set(ucfirst(translation.Translate(label.get())));
        });
        items.parallelStream().forEach(item->item.translate(t));
    }

    public HBox getContainer() {
        return container;
    }

    public void setContainer(HBox container) {
        this.container = container;
    }
    
}
