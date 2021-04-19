/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.utils;

import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import com.journalisation.resources.lang.TraductionUtils;

import java.util.Map;

/**
 *
 * @author Padovano
 */
public class Render {
    
    public static TraductionUtils traduit;
    public static TitledPane renderExpandableMenu(String Name,ObservableList child,TraductionUtils t){
        VBox container=new VBox();
        container.setMaxWidth(Region.USE_COMPUTED_SIZE);
        container.getChildren().addAll(child);
        TitledPane pane=new TitledPane(t.Translate(Name),container);
        return pane;
    }
    
    public static JFXButton renderFxButtonMenu(String name,JFXButton.ButtonType type,Node icon,Map<EventType,EventHandler> event,String classname, TraductionUtils t){
        JFXButton button=new JFXButton(t.Translate(name));
        button.setButtonType(type);
        button.getStyleClass().add("btn_nav");
        if(classname!=null&&!classname.trim().isEmpty())
            button.getStyleClass().add(classname);
        if(icon!=null)
            button.setGraphic(icon);
        if(event!=null){
            event.keySet().stream().forEach((e) -> {
                button.addEventHandler(e.getSuperType(), event.get(e));
            });
        }
        return button;
    }
}
