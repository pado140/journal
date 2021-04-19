/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.resources.menu;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import com.journalisation.resources.menu.horizontal.Menu;
import com.journalisation.resources.menu.icon.MenuIcon;
import com.journalisation.resources.menu.vertical.MenuBlock;
import com.journalisation.resources.menu.vertical.MenuItems;

/**
 *
 * @author Padovano
 */
public class MenuRender {
    
    public static MenuBlock ExpandableMenu(String label, String icon, Node parent, ObservableList<MenuItems> element){
        MenuBlock block=new MenuBlock((Accordion)parent);
        block.setLabel(label);
        System.out.println(MenuIcon.get(icon).getImage());
        block.setIconImage(MenuIcon.get(icon));
        block.addMenuItems(element);
        block.initLayout();
        return block;
    }
    
    public static MenuItems MenuButton(String label, String icon, Node parent){
        MenuItems button=new MenuItems(label,(MenuBlock)parent);
        button.setIconImage(MenuIcon.get(icon));
        button.initLayout();
        return button;
    }
    
    public static Menu tabMenu(String label, ObservableList<MenuButton> element){
        Menu block=new Menu();
        block.setLabel(label);
        block.addMenuButtons(element);
        block.initLayout();
        return block;
    }
    
    public static MenuButton BlockItem(String label,String icon){
        MenuButton button=new MenuButton(label);
        button.setIconImage(MenuIcon.get(icon));
        button.initLayout();
        return button;
    }
}
