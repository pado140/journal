/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.resources.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import com.journalisation.controller.ControllerManager;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Padovano
 */
public class ViewBehavior {
    private ControllerManager controller;
    private FXMLLoader loader;
    private Parent parent;
    
    public ViewBehavior(String url) {
        loader=new FXMLLoader();
        loader.setLocation(getClass().getResource(url));
        try {
            parent=loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ViewBehavior.class.getName()).log(Level.SEVERE, null, ex);
        }
        controller=loader.getController();
    }

    public final Parent getparent(){
        return parent;
    }
    public Object getDefaultController() {
        return loader.getController();
    }

    public ControllerManager getController() {
        return controller;
    }

    public void setController(ControllerManager controller) {
        this.controller = controller;
    }

    public FXMLLoader getLoader() {
        return loader;
    }

    public void setLoader(FXMLLoader loader) {
        this.loader = loader;
    }
    
    
}
