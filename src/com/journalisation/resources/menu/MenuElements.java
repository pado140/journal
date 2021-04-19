/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.resources.menu;


import javafx.beans.property.StringProperty;
import com.journalisation.resources.lang.TraductionUtils;

/**
 *
 * @author Padovano
 */
public interface MenuElements<T>  {
    void translate(TraductionUtils t);
    StringProperty getLabel();
}
