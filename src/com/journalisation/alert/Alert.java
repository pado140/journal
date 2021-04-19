/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.alert;

import javafx.collections.ObservableList;
import javafx.stage.Stage;
import com.journalisation.resources.lang.TraductionUtils;
import com.journalisation.utils.AppUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Optional;

/**
 *
 * @author Padovano
 */
public class Alert {
    public static void success(Stage parent, String message, TraductionUtils t){
        AlertBoxes alert=new SimpleAlert(parent, true, Alerttype.SUCCESS, AppUtils.SUCCESS_MODAL_TITLE, message).I18n(t);
        alert.build();
        alert.showAndWait();
    }
    public static void error(Stage parent,String message,TraductionUtils t){
        AlertBoxes alert=new SimpleAlert(parent, true, Alerttype.ERROR,AppUtils.ERROR_MODAL_TITLE, message).I18n(t);
        alert.build();
        alert.showAndWait();
    }
    public static AlertButtons showConfirmMessage(Stage parent,String message,TraductionUtils t){
        AlertBoxes alert=new ConfirmationAlert(parent, true, Alerttype.QUESTION,"Confirmation", message,AlertButtons.YES_NO,"Oui","Non").I18n(t);
        alert.build();
        alert.showAndWait();
        return ((ConfirmationAlert)alert).result;
    }
    
    public static Optional<String> showInputMessage(Stage parent,String message,TraductionUtils t){
        AlertBoxes alert=new InputAlert(parent, true, Alerttype.QUESTION,"Confirmation", message,AlertButtons.YES_NO_CANCEL,"Oui","Non","Annuler").I18n(t);
        alert.build();
        alert.showAndWait();
        return ((InputAlert)alert).result;
    }
    
    public static Optional<Object> showSelectedInputMessage(Stage parent,String message,ObservableList<Object> data,TraductionUtils t){
        AlertBoxes alert=new SelectionAlert(parent, true, Alerttype.QUESTION,"Confirmation", message,AlertButtons.YES_NO_CANCEL,data,"Oui","Non","Annuler").I18n(t);
        alert.build();
        alert.showAndWait();
        return ((SelectionAlert)alert).result;
    }
    
    public static AlertButtons typeOf(String value){
        if(value.equalsIgnoreCase("oui"))
            return AlertButtons.YES;
        
        if(value.equalsIgnoreCase("non"))
            return AlertButtons.NO;
        
        if(value.equalsIgnoreCase("oui"))
            return AlertButtons.OK;
        
        return AlertButtons.CANCEL;
    }
    public static void showTrayMessage(String title, String message) {
        try {
            SystemTray tray = SystemTray.getSystemTray();
            BufferedImage image = ImageIO.read(Alert.class.getResource("/resources/icon/wiplogo1_buU_1.ico"));
            TrayIcon trayIcon = new TrayIcon(image, "Library Assistant");
            trayIcon.setImageAutoSize(true);
            trayIcon.setToolTip("Wip");
            tray.add(trayIcon);
            trayIcon.displayMessage(title, message, TrayIcon.MessageType.INFO);
            tray.remove(trayIcon);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    public static void showInfo(Stage stage, String titre, String string,TraductionUtils t){
        AlertBoxes alert=new SimpleAlert(stage, true, Alerttype.INFO,titre, string).I18n(t);
        alert.build();
        alert.showAndWait();
    }
}
