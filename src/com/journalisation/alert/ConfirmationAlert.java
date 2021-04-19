/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.alert;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import com.journalisation.utils.Boutton;

/**
 *
 * @author Padovano
 */
public class ConfirmationAlert extends SimpleAlert {
    public AlertButtons result=AlertButtons.NO;

    public ConfirmationAlert(Stage parent, boolean closeable, Alerttype type, String titre) {
        super(parent, closeable, type, titre);
    }

    public ConfirmationAlert(Stage parent, boolean closeable, Alerttype type, String titre, String message) {
        super(parent, closeable, type, titre, message);
    }

    public ConfirmationAlert(Stage parent, boolean closeable, Alerttype type, String titre, String message, AlertButtons buttons) {
        super(parent, closeable, type, titre, message, buttons);
    }

    public ConfirmationAlert(Stage parent, boolean closeable, Alerttype type, String titre, String message, AlertButtons buttons, String... ButtonText) {
        super(parent, closeable, type, titre, message, buttons, ButtonText);
        
    }
    
    @Override
    protected void initBouton(){
        
        HBox buttonContent=new HBox(5);
        switch(buttons){
            case YES:case OK:
                ok.setLabel(new SimpleStringProperty(buttonText[0]));
                ok.translate(traduction.get());
                ok.getStyleClass().add("modal-action");
                ok.setOnAction((event)->close());
                buttonContent.getChildren().add(ok);
                break;
            case YES_NO:case NO_CANCEL:case YES_CANCEL:
                for(int i=0;i<2;i++){
                    Boutton btn=new Boutton(buttonText[i]);
                    btn.translate(traduction.get());
                btn.getStyleClass().add("modal-action");
                final int j=i;
                btn.setOnAction((event)->{
                    String val=buttons.getValue();
                    String[] valTab=val.split(" ");
                    result=Alert.typeOf(valTab[j]);
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
    
    protected void execute(){
        
    }
}
