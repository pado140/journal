/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.resources.menu.vertical;

import com.journalisation.Main;
import com.journalisation.alert.Alert;
import com.journalisation.alert.AlertButtons;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import com.journalisation.controller.ControllerManager;
import com.journalisation.dao.bean.Roles;
import com.journalisation.dao.bean.Users;
import com.journalisation.resources.lang.TraductionUtils;
import com.journalisation.resources.log.AppLog;
import com.journalisation.resources.menu.MenuElements;
import com.journalisation.resources.menu.MenuRender;
import com.journalisation.resources.menu.icon.MenuIcon;
import com.journalisation.resources.view.ViewBehavior;
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
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Padovano
 */
public class InitMenu {
    private InputStream file;
    private DocumentBuilderFactory fact;
    private DocumentBuilder builder;
    private Document doc;
    private ObservableList<MenuElements> menu;
    private static TraductionUtils translation;
    public static AnchorPane parent;
    private VBox menu_block;
    private MenuItems ActiveMenu;
    private Users user;
    public InitMenu(Users user) {
        file= getClass().getResourceAsStream("Menu.xml");
        fact=DocumentBuilderFactory.newInstance();
        menu=FXCollections.observableArrayList();
        try {
            builder=fact.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            AppLog.Log("Journalisation",Level.ALL, ex.getMessage(), ex);
        }
        try {
            doc=builder.parse(file);
        } catch (SAXException | IOException ex) {
            AppLog.Log("Journalisation",Level.ALL, ex.getMessage(), ex);
        }
        this.user=user;
    }

    public void setTranslation(TraductionUtils translation) {
        this.translation = translation;
    }

    public void setParent(AnchorPane parent) {
        InitMenu.parent = parent;
    }

    private Set<String> availablemenu(){
        Set<String> menus=new HashSet<>();
        for(Roles r:user.getGroups().getRoles()){
            menus.addAll(r.getMenu());
        }
        return menus;
    }
    public ObservableList<MenuElements> loadMenu(){
        ObservableSet<String> menuready=FXCollections.observableSet(availablemenu());
        //if(!user.getType().equalsIgnoreCase("user"))
        NodeList listEl=doc.getElementsByTagName("MenuItem");
            System.out.println("listEl:"+listEl.getLength());
            for(int i=0;i<listEl.getLength();i++){
                org.w3c.dom.Node item=listEl.item(i);
                System.out.println(item.getNodeName());
                if(item instanceof Element){
                    System.out.println(item.getNodeName());
                    NamedNodeMap attr=item.getAttributes();
                    boolean expandable=Boolean.parseBoolean(attr.getNamedItem("canExpand").getNodeValue());
                    String label=attr.getNamedItem("name").getNodeValue();
                    String icon=attr.getNamedItem("icon").getNodeValue();
                    String id=attr.getNamedItem("id").getNodeValue().substring(1);
                    if(expandable){
                        System.out.println(label);
                        boolean blockselected=false;
                        MenuBlock block= MenuRender.ExpandableMenu(label, icon, null, null);
                        block.setIds(id);
                        NodeList menuitem=item.getChildNodes();
                        
                        for(int k=0;k<menuitem.getLength();k++){
                            org.w3c.dom.Node subitem=menuitem.item(k);
                            if(subitem instanceof Element){
                                NamedNodeMap attrs=menuitem.item(k).getAttributes();
                                String labelm=attrs.getNamedItem("name").getNodeValue();
                                String iconm=attrs.getNamedItem("icon").getNodeValue();
                                String idm=attrs.getNamedItem("id").getNodeValue().substring(1);
                                if(!user.getType().equalsIgnoreCase("user")||menuready.contains(idm)) {
                                    String actionm = attrs.getNamedItem("action").getNodeValue();
                                    MenuItems btn = MenuRender.MenuButton(labelm, iconm, block);
                                    btn.setActionTag(actionm);
                                    btn.setIds(idm);
                                    btn.setMaxWidth(Double.MAX_VALUE);
                                    action(btn);
                                    blockselected=true;
                                }

                            }
                            
                        }
                        if(blockselected)
                            menu.add(block);
                        }
                    else{
                        if(!user.getType().equalsIgnoreCase("user")||menuready.contains(id)) {
                            System.out.println(icon);
                            MenuIcon.get(icon);
                            MenuItems btn = MenuRender.MenuButton(label, icon, null);
                            btn.setIds(id);
                            btn.setMaxWidth(Double.MAX_VALUE);
                            String action = attr.getNamedItem("action").getNodeValue();
                            btn.setActionTag(action);
                            if (attr.getNamedItem("pin").getNodeValue() != null) {
                                boolean pin = Boolean.parseBoolean(attr.getNamedItem("pin").getNodeValue());
                                btn.setPin(pin);
                            }
                            action(btn);
                            menu.add(btn);
                        }
                    }
                }
            }
            return menu;
    }
    public ObservableList<MenuElements> Menu(){
        //ObservableSet<String> menuready=FXCollections.observableSet(availablemenu());
        //if(!user.getType().equalsIgnoreCase("user"))
        NodeList listEl=doc.getElementsByTagName("MenuItem");
            System.out.println("listEl:"+listEl.getLength());
            for(int i=0;i<listEl.getLength();i++){
                org.w3c.dom.Node item=listEl.item(i);
                System.out.println(item.getNodeName());
                if(item instanceof Element){
                    System.out.println(item.getNodeName());
                    NamedNodeMap attr=item.getAttributes();
                    boolean expandable=Boolean.parseBoolean(attr.getNamedItem("canExpand").getNodeValue());
                    String label=attr.getNamedItem("name").getNodeValue();
                    String icon=attr.getNamedItem("icon").getNodeValue();
                    String id=attr.getNamedItem("id").getNodeValue().substring(1);
                    if(expandable){
                        System.out.println(label);
                        boolean blockselected=false;
                        MenuBlock block= MenuRender.ExpandableMenu(label, icon, null, null);
                        block.setIds(id);
                        NodeList menuitem=item.getChildNodes();

                        for(int k=0;k<menuitem.getLength();k++){
                            org.w3c.dom.Node subitem=menuitem.item(k);
                            if(subitem instanceof Element){
                                NamedNodeMap attrs=menuitem.item(k).getAttributes();
                                String labelm=attrs.getNamedItem("name").getNodeValue();
                                String iconm=attrs.getNamedItem("icon").getNodeValue();
                                String idm=attrs.getNamedItem("id").getNodeValue().substring(1);
                                //if(!user.getType().equalsIgnoreCase("user")||menuready.contains(idm)) {
                                    String actionm = attrs.getNamedItem("action").getNodeValue();
                                    MenuItems btn = MenuRender.MenuButton(labelm, iconm, block);
                                    btn.setActionTag(actionm);
                                    btn.setIds(idm);
                                    btn.setMaxWidth(Double.MAX_VALUE);
                                    action(btn);
                                    blockselected=true;
                                //}

                            }

                        }
                        //if(blockselected)
                            menu.add(block);
                        }
                    else{
                        //if(!user.getType().equalsIgnoreCase("user")||menuready.contains(id)) {
                            System.out.println(icon);
                            MenuIcon.get(icon);
                            MenuItems btn = MenuRender.MenuButton(label, icon, null);
                            btn.setIds(id);
                            btn.setMaxWidth(Double.MAX_VALUE);
                            String action = attr.getNamedItem("action").getNodeValue();
                            btn.setActionTag(action);
                            if (attr.getNamedItem("pin").getNodeValue() != null) {
                                boolean pin = Boolean.parseBoolean(attr.getNamedItem("pin").getNodeValue());
                                btn.setPin(pin);
                            }
                            action(btn);
                            menu.add(btn);
                        //}
                    }
                }
            }
            return menu;
    }

    private void action(MenuItems btn){
        try{
        if(!btn.getActionTag().trim().isEmpty()){
            btn.setOnAction((ActionEvent event) -> {
                try {
                    this.getClass().getMethod(btn.getActionTag()).invoke(this);
                    setActiveMenu(btn);
                                            //parent.setCenter(p);
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(InitMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        }catch(NullPointerException e){
            
        }
    }
    
    
    public void Home(){
        //parent.centerProperty().set(new DashBoard());
//        Parent pane=new DashBoard();
//        parent.getChildren().clear();
//        parent.setLeftAnchor(pane,10.0);
//        parent.setRightAnchor(pane,10.0);
//        parent.setTopAnchor(pane,10.0);
//        parent.setBottomAnchor(pane,10.0);
//        parent.getChildren().add(pane);
        switchNode(new ViewBehavior("dashboard_view.fxml"),translation);
        //transition();
    }

    private void switchNode(String url,TraductionUtils t){
        //System.out.println("parent:"+content.getParent());
        FXMLLoader loader=new FXMLLoader();
        Pane pane=null;
        try {
            pane = FXMLLoader.load(getClass().getResource(url));
//
        } catch (IOException ex) {
            Logger.getLogger(InitMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        parent.getChildren().clear();
        parent.getChildren().add(pane);
        Object controller=loader.getController();
        ((ControllerManager)controller).I18n(t);

    }


    private static void switchNode(ViewBehavior vb, TraductionUtils t){
        //System.out.println("parent:"+content.getParent());
//        Object controller=vb.getController();
//            System.out.println(controller);
//            ((ControllerManager)controller).I18n(t);

        //parent.centerProperty().set(vb.getparent());
        Parent pane=vb.getparent();
        transition(pane);
        parent.getChildren().clear();
        AnchorPane.setLeftAnchor(pane,10.0);
        AnchorPane.setRightAnchor(pane,10.0);
        AnchorPane.setTopAnchor(pane,10.0);
        AnchorPane.setBottomAnchor(pane,10.0);
        parent.getChildren().add(pane);

    }


    public static void switchNode(ScrollPane pane){
        //System.out.println("parent:"+content.getParent());

        //parent.centerProperty().set(pane);
        parent.getChildren().clear();
        AnchorPane.setLeftAnchor(pane,10.0);
        AnchorPane.setRightAnchor(pane,10.0);
        AnchorPane.setTopAnchor(pane,10.0);
        AnchorPane.setBottomAnchor(pane,10.0);
        parent.getChildren().add(pane);
        //transition((Node)pane);
    }

   
    public static void  newProject(){
        switchNode(expandablePane(new ViewBehavior("creer_projet.fxml")));
    }
    
    public void genres(){
        switchNode(new ViewBehavior("genres.fxml"),translation);
        //transition();
    }
    
    public void report(){
        switchNode(new ViewBehavior("Report.fxml"),translation);
    }
    
    public void auteurs(){
        switchNode(new ViewBehavior("auteurs.fxml"),translation);
    }
    
    
    public static void projet(){
        String file="AdminProjet.fxml";
        if(Main.users.getType().equalsIgnoreCase("user"))
            file="projectlist.fxml";
        switchNode(new ViewBehavior(file),translation);
    }
    
    public void task(){
        switchNode(new ViewBehavior("tasks.fxml"),translation);
    }
    
    
    public void user(){
        switchNode(new ViewBehavior("groups.fxml"),translation);
    }

    
    public void transactions(){
        switchNode(new ViewBehavior("transactions.fxml"),translation);
    }
    
    
    public void switch_report_vente(){
        switchNode(new ViewBehavior("Vente_transaction.fxml"),translation);
    }
    
    
    public void switch_report_achat(){
        switchNode(new ViewBehavior("Achats.fxml"),translation);
    }
    public void searchFilter(){
        switchNode(new ViewBehavior("recherchefilter.fxml"),translation);
    }

    
    public void logout(){
        if(Alert.showConfirmMessage(Main.primarystage,  "Etes-vous sur de vouloir deconnecter?", translation)==AlertButtons.YES){
            System.exit(0);
        }
    }
    public void setting(){
        
        switchNode(expandablePane(new ViewBehavior("setting.fxml")));
    }
    public MenuItems getActiveMenu() {
        return ActiveMenu;
    }

    public void setActiveMenu(MenuItems activeMenu) {
        if(getActiveMenu()!=null){
            getActiveMenu().pseudoClassStateChanged(PseudoClass.getPseudoClass("active"),false);
        }
        ActiveMenu = activeMenu;
        getActiveMenu().pseudoClassStateChanged(PseudoClass.getPseudoClass("active"),true);
    }

    static ScrollPane expandablePane(ViewBehavior vb){
        ScrollPane pane=new ScrollPane();
        pane.setContent((Pane)vb.getparent());
        pane.setPannable(true);
        return pane;
    }
    ScrollPane expandablePane(Pane p){
        ScrollPane pane=new ScrollPane();
        pane.viewportBoundsProperty().addListener((o,a,n)->{
            (p).setPrefSize(n.getWidth(), n.getHeight());
        });
        pane.setContent(p);
        return pane;
    }
    ScrollPane expandablePane(String url){
        FXMLLoader loader=new FXMLLoader();
        Pane p=null;
        try {
            p = FXMLLoader.load(getClass().getResource(url));
//
        } catch (IOException ex) {
            Logger.getLogger(InitMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        ScrollPane pane=new ScrollPane();
        Pane finalP = p;
        pane.viewportBoundsProperty().addListener((o, a, n)->{
            finalP.setPrefSize(n.getWidth(), n.getHeight());
        });
        pane.setContent(p);
        return pane;
    }

    static void transition(Node n){
        FadeTransition parallelTransition=new FadeTransition(Duration.seconds(3));
        //parallelTransition.setDelay(Duration.millis(5000));
        parallelTransition.setAutoReverse(true);
        parallelTransition.setNode(n);
        parallelTransition.setFromValue(0.1);
        parallelTransition.setToValue(1);
        parallelTransition.setCycleCount(1);
        parallelTransition.play();
    }
}
