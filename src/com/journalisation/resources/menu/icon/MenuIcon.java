/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.resources.menu.icon;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Padovano
 */
public class MenuIcon extends ImageView{
    private final double width=30;
    private final double height=30;

    private MenuIcon(String url) {
        super();
        Image im=new Image(getClass().getResourceAsStream(url));
        this.setImage(im);
        render();
    }

    private MenuIcon(Image image) {
        super(image);
        render();
    }
    
    private void render(){
        this.setFitHeight(height);
        this.setFitWidth(width);
    }
    
    public static MenuIcon get(String iconName){
        MenuIcon icon=null;
        if(!iconName.trim().isEmpty()){
            icon=new MenuIcon(iconName);
        }
        return icon;
    }
    
}
