package com.journalisation.resources.icons;

import javafx.scene.image.Image;

public class IconImage {
    public static Image of(String name){

        System.out.println(name+".jpg");
        Image im=new Image(IconImage.class.getResourceAsStream(name+".png"));
        return im;
    }
}
