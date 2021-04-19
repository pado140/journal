/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.alert;


import javafx.scene.image.Image;
import com.journalisation.utils.icon.IconImage;


/**
 *
 * @author Padovano
 */
public enum Alerttype {
    ERROR("error", IconImage.of("error")),
    WARNING("warning",IconImage.of("warning")),
    QUESTION("question",IconImage.of("question")),
    INFO("infos",IconImage.of("infos")),
    SUCCESS("succes",IconImage.of("succes")),
    MATERIAL_ERROR("error", IconImage.of("error")),
    MATERIAL_WARNING("warning",IconImage.of("warning")),
    MATERIAL_QUESTION("question",IconImage.of("question")),
    MATERIAL_INFO("infos",IconImage.of("infos")),
    MATERIAL_SUCCESS("success",IconImage.of("succes"));
    
    private String styleClass;
    private Image icon;
    
    Alerttype(String classStyle, Image icon){
        this.styleClass=classStyle;
        this.icon=icon;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public Image getIcon() {
        return icon;
    }
    public Image getIconFrom() {
        icon=IconImage.of(getStyleClass());
        return icon;
    }

    @Override
    public String toString() {
        return styleClass;
    }
    
}
