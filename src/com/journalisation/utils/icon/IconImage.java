package com.journalisation.utils.icon;

import javafx.scene.image.Image;

public class IconImage {
    public static Image of(String name){
        Image im=new Image(IconImage.class.getResourceAsStream(name+".png"));
        return im;
    }
}
