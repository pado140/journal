/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.alert;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.journalisation.exceptions.InvalidInputException;
import com.journalisation.resources.lang.TraductionUtils;
import com.journalisation.utils.Boutton;

/**
 *
 * @author Padovano
 */
public class SimpleAlert extends AlertBoxes{
    protected final Label message=new Label();
    protected ObjectProperty<String> messagetext;
    protected final Boutton ok=new Boutton("Ok");
    protected AlertButtons buttons;
    protected String[] buttonText;
    protected final VBox container=new VBox();
    public SimpleAlert(Stage parent, boolean closeable, Alerttype type, String titre) {
        this (parent,closeable,type,titre,null,AlertButtons.OK,"Ok");
    }

    public SimpleAlert(Stage parent, boolean closeable, Alerttype type, String titre,String message) {
        this (parent,closeable,type,titre,message,AlertButtons.YES_NO,"Oui","Non");
    }

    public SimpleAlert(Stage parent, boolean closeable, Alerttype type, String titre,String message,AlertButtons buttons) {
        this (parent,closeable,type,titre,message,buttons,"Ok");
    }
    public SimpleAlert(Stage parent, boolean closeable, Alerttype type, String titre,String message,AlertButtons buttons,String... ButtonText) {
        super(parent, closeable, type, titre);
        if(buttons==AlertButtons.NO_CANCEL||buttons==AlertButtons.YES_CANCEL||buttons==AlertButtons.YES_NO)
            if(ButtonText.length!=2)
                throw new InvalidInputException(String.format("ButtonText doit avoir %d arguments!", 2));
        if(buttons==AlertButtons.YES_NO_CANCEL||buttons==AlertButtons.OK_NO_CANCEL)
            if(ButtonText.length!=3)
                throw new InvalidInputException(String.format("ButtonText doit avoir %d arguments!", 3));
        this.messagetext=new SimpleObjectProperty<>();
        this.message.setWrapText(true);
        this.message.setMaxWidth(180);
        this.message.textProperty().bind(messagetext);
        buttonText=ButtonText;
        this.buttons=buttons;
        container.getChildren().add(this.message);
        setMessage(message);
        
    }
    public void setMessage(String message){
        messagetext.setValue(message);
    }
    @Override
    protected void build() {
        content.getChildren().add(container);
        initBouton();
        super.build(); //To change body of generated methods, choose Tools | Templates.
    }
    protected void initBouton(){
        switch(buttons){
            case YES:case OK:
                ok.setLabel(new SimpleStringProperty(buttonText[0]));
                break;
        }
        ok.translate(traduction.get());
        ok.getStyleClass().add("modal-action");
        ok.setOnAction((event)->close());
        HBox buttonContent=new HBox(5, ok);
        AnchorPane.setRightAnchor(buttonContent, 10.0);
        AnchorPane.setTopAnchor(buttonContent, 10.0);
        AnchorPane.setBottomAnchor(buttonContent, 10.0);
        displayButton.getChildren().addAll(buttonContent);
    }
    @Override
    protected void translate(){
        super.translate();
        TraductionUtils t=  traduction.get();
        if(!isI18n()){
            System.err.println(isI18n());
            titre.setText(t.Translate(title));
            message.setText(t.Translate(messagetext.get()));
            return;
        }
              
            
    }
    
}
