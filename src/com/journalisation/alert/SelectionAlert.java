/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.alert;

import com.jfoenix.controls.JFXComboBox;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import com.journalisation.utils.Boutton;

import java.util.Optional;

/**
 *
 * @author Padovano
 */
public class SelectionAlert<T> extends SimpleAlert{
    
    public Optional<T> result=Optional.empty();
    private JFXComboBox<T> list;
    public SelectionAlert(Stage parent, boolean closeable, Alerttype type, String titre) {
        super(parent, closeable, type, titre);
    }

    public SelectionAlert(Stage parent, boolean closeable, Alerttype type, String titre, String message) {
        super(parent, closeable, type, titre, message);
    }

    public SelectionAlert(Stage parent, boolean closeable, Alerttype type, String titre, String message, AlertButtons buttons) {
        super(parent, closeable, type, titre, message, buttons);
    }

    public SelectionAlert(Stage parent, boolean closeable, Alerttype type, String titre, String message, AlertButtons buttons,ObservableList<T> dataList, String... ButtonText) {
        super(parent, closeable, type, titre, message, buttons, ButtonText);
        list=new JFXComboBox<>(dataList);
        
    }
    @Override
    protected void initBouton(){
        
        HBox buttonContent=new HBox(5);
        switch(buttons){
            case YES:case OK: case NO: case CANCEL:
                ok.setLabel(new SimpleStringProperty(buttonText[0]));
                ok.translate(traduction.get());
                ok.getStyleClass().add("modal-action");
                ok.setOnAction((event)->close());
                buttonContent.getChildren().add(ok);
                break;
            default:
                String val=buttons.getValue();
                String[] valTab=val.split(" ");
                for(int i=0;i<valTab.length;i++){
                    Boutton btn=new Boutton(buttonText[i]);
                    btn.translate(traduction.get());
                btn.getStyleClass().add("modal-action");
                final int j=i;
                btn.setOnAction((event)->{
                    
                    if(valTab[j].trim().equalsIgnoreCase("oui"))
                        result=Optional.of(list.getSelectionModel().getSelectedItem());
                    close();
                });
                buttonContent.getChildren().add(btn);
                }
            break;
                
                
        }
        
        AnchorPane.setRightAnchor(buttonContent, 10.0);
        AnchorPane.setTopAnchor(buttonContent, 10.0);
        AnchorPane.setBottomAnchor(buttonContent, 10.0);
        displayButton.getChildren().addAll(buttonContent);
    }

    @Override
    protected void build() {
        container.getChildren().add(list);
        super.build(); //To change body of generated methods, choose Tools | Templates.
    }
    
}
