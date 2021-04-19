/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.resources.menu.horizontal;

import javafx.beans.property.StringProperty;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import com.journalisation.resources.lang.TraductionUtils;
import com.journalisation.resources.menu.MenuElements;

/**
 *
 * @author Padovano
 */
public class SeparatorButtonMenu extends Separator implements MenuElements {

    public SeparatorButtonMenu(Orientation orientation) {
        super(orientation);
        this.setPrefWidth(2);
        
    }

    
    @Override
    public void translate(TraductionUtils t) {
        
    }

    @Override
    public StringProperty getLabel() {
        return null;
    }
}
