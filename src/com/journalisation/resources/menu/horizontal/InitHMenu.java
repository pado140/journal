/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.resources.menu.horizontal;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.scene.control.TabPane;
import com.journalisation.resources.lang.TraductionUtils;
import com.journalisation.resources.log.AppLog;
import com.journalisation.resources.menu.MenuButton;
import com.journalisation.resources.menu.MenuElements;
import com.journalisation.resources.menu.MenuRender;
import com.journalisation.utils.AppUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Padovano
 */
public class InitHMenu {
    private InputStream file;
    private DocumentBuilderFactory fact;
    private DocumentBuilder builder;
    private Document doc;
    private ObservableList<MenuElements> menu;
    private TraductionUtils translation;
    private TabPane parent;
    private TabPane target;
    private Runnable localeChange=this::translate;
    
    private ObjectProperty<TraductionUtils> traduction=new SimpleObjectProperty<>();
    public InitHMenu(TabPane p) {
        parent=p;
        file= getClass().getResourceAsStream( "MenuH.xml");
        fact=DocumentBuilderFactory.newInstance();
        menu=FXCollections.observableArrayList();
        try {
            builder=fact.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            AppLog.Log("gestion",Level.ALL, ex.getMessage(), ex);
        }
        try {
            doc=builder.parse(file);
        } catch (SAXException | IOException ex) {
            AppLog.Log("gestion",Level.ALL, ex.getMessage(), ex);
        }
        loadMenu();
        traduction.addListener((observable, oldValue, newValue) -> {
            translate();
        }); 
    }

    public void setTranslation(TraductionUtils translation) {
        this.translation = translation;
    }

    public void setParent(TabPane parent) {
        this.parent = parent;
    }

    public void setTarget(TabPane target) {
        this.target = target;
    }
    
    public ObservableList<MenuElements> loadMenu(){
        NodeList listEl=doc.getElementsByTagName("MenuBlock");
            for(int i=0;i<listEl.getLength();i++){
                org.w3c.dom.Node item=listEl.item(i);
                System.out.println(item.getNodeName());
                if(item instanceof Element){
                    System.out.println(item.getNodeName());
                    NamedNodeMap attr=item.getAttributes();
                    String label=attr.getNamedItem("name").getNodeValue();
                    System.out.println(label);
                    Menu block= MenuRender.tabMenu(label, null);
                        
                    NodeList menuitem=item.getChildNodes();
                        
                    for(int k=0;k<menuitem.getLength();k++){
                        org.w3c.dom.Node subitem=menuitem.item(k);
                        if(subitem instanceof Element){
                            NamedNodeMap attrs=menuitem.item(k).getAttributes();
                            
                            if(!menuitem.item(k).getNodeName().equals("separator")){
                                String labelm=attrs.getNamedItem("name").getNodeValue();
                                String icon=attrs.getNamedItem("icon").getNodeValue();
                                String actionm=attrs.getNamedItem("action").getNodeValue();
                                MenuButton btn=MenuRender.BlockItem(labelm, icon);
                                btn.setActionTag(actionm);
                                action(btn);
                                block.addMenuButton(btn);
                            }else{
                                SeparatorButtonMenu separator=new SeparatorButtonMenu(Orientation.VERTICAL);
                                block.addMenuSeparator(separator);
                            }
                            }
                            
                        }
                            menu.add(block);
                        
                   parent.getTabs().add(block); 
                }
                
            }
            return menu;
    }
    
    private void action(MenuButton btn){
        try{
        if(!btn.getActionTag().trim().isEmpty()){
            btn.setOnAction((ActionEvent event) -> {
                try {
                    this.getClass().getMethod(btn.getActionTag()).invoke(this);
                                            //parent.setCenter(p);
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(InitHMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        }catch(NullPointerException e){
            
        }
    }
    public InitHMenu I18n(TraductionUtils tr){
        if (traduction.get() != null) {
            traduction.get().removeListener(localeChange);
        }
        traduction.set(tr);
        traduction.get().addListener(localeChange);
        return this;
    }
    public boolean isI18n(){
        return traduction.get()!=null;
    }
    private void translate(){
        TraductionUtils t=traduction.get();
        if(!isI18n()){
            System.err.println(isI18n());
            return;
        }
                menu.parallelStream().forEach(m->{
                    m.translate(t);
                    
                        });
            
    }
    
   

    public void achat(){
        AppUtils.switchNode("Nouveau Achat","achat_de_produit.fxml",null,target,true);
    }
   
    public void switchProd(){
        AppUtils.switchNode("Produits1","Produits.fxml",null,target,true);
    }
    public void switchCutplan(){
        AppUtils.switchNode("Plan de Coupe","Cutplan.fxml",null,target,true);
    }

    public void newproduct(){
        AppUtils.switchNode("Produits1","nouveau_produit.fxml",null,target,true);
    }
    
    public void switchPart(){
        AppUtils.switchNode("Cut part","Parts.fxml",null,target,true);
    }


    public void switchGroup(){
        AppUtils.switchNode("Group","groups.fxml",null,target,true);
    }
    public void switchStyle(){
        AppUtils.switchNode("Styles","styles.fxml",null,target,true);
    }


    
    public void switchAchatProduit(){
        AppUtils.switchNode("Nouvel Achat","achat_de_produit.fxml",null,target,true);
    }
    
    
    public void switchStock(){
        AppUtils.switchNode("Stock","Stock de produit.fxml",null,target,true);
    }

    
    public void switchcolors(){
        AppUtils.switchNode("Couleurs","colors.fxml",null,target,true);
    }

    public void switchSize(){
        AppUtils.switchNode("Sizes","size.fxml",null,target,true);
    }

    public void switchNewColor(){
        AppUtils.switchNode("New Color","ColorNew.fxml",null,target,true);
    }

    
    public void switchFabric(){
        AppUtils.switchNode("Fabric","Fabrics.fxml",null,target,true);
    }
    
    
    public void switch_report_achat(){
        AppUtils.switchNode("Rapport d'Achats","Achats.fxml",null,target,true);
    }

    
    public void switchCustomers(){
        AppUtils.switchNode("Clients","customers.fxml",null,target,true);
    }
    public void switchSetting(){
        AppUtils.switchNode("Settings","Settings.fxml",null,target,true);
    }
    
}
