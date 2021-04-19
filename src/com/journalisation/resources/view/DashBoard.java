/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.resources.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import com.sun.javafx.css.Size;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Padovano
 */
public class DashBoard extends ScrollPane{
    private VBox container;
    private FlowPane summary;
    private ScrollPane Activities;
    private AnchorPane charts;
    

    public DashBoard() {
        initComponents();
        this.viewportBoundsProperty().addListener((o,a,n)->{
            container.setPrefSize(n.getWidth(), n.getHeight());
        });
    }
    
    private void initComponents(){
        //this.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        
        container=new VBox();
        charts=new AnchorPane();
        summary=new FlowPane(Orientation.HORIZONTAL);
        Activities=new ScrollPane();
        ListViewMultiple<Size> v=new ListViewMultiple();
        v.render();
        
        //int OpenOrders=Iw_last_update.orders.parallelStream().filter((o)->o.)
        
        chartbar cb=new chartbar();
        ObservableMap<String,Map<String,Number>> data=FXCollections.observableHashMap();
        Map<String,Number> value=new HashMap<>();
        value.put("Avion", 50);
        value.put("Avion1", 100);
        value.put("Avion2", 20);
        value.put("Avion3", 40);
        Map<String,Number> value1=new HashMap<>();
        value1.put("Avion", 50);
        value1.put("Avion1", 100);
        value1.put("Avion2", 20);
        value1.put("Avion3", 40);
        data.put("2000", value);
        data.put("2001", value1);
        cb.setData(data);
        cb.setTitre("Produits");
        cb.initLayout();
        AnchorPane.setTopAnchor(cb, 10.0);
        AnchorPane.setLeftAnchor(cb, 10.0);
        AnchorPane.setBottomAnchor(cb, 10.0);
        AnchorPane.setRightAnchor(cb, 10.0);
//        AnchorPane.setTopAnchor(summary, 150.0);
//        AnchorPane.setRightAnchor(Activities, 10.0);
//        AnchorPane.setTopAnchor(Activities, 150.0);
        charts.getChildren().add(cb);
        
        summary.setMinWidth(837);
        summary.setHgap(15);
        summary.setVgap(15);
        //Map<Boolean,List<Orders>> state=Iw_last_update.orders.parallelStream().collect(Collectors.groupingBy(Orders::isOpened));
//        summary.getChildren().add(new Card("Produit disponible","Total Orders",String.valueOf(Iw_last_update.orders.size()),null));
//        summary.getChildren().add(new Card("Produit disponible","Open Orders",String.valueOf(state.get(true).size()),null));
//        summary.getChildren().add(new Card("Produit disponible","Close Orders",String.valueOf(state.get(false).size()),null));
//        summary.getChildren().add(new Card("Produit disponible","Plan Pieces","0",null));
//        summary.getChildren().add(new Card("Produit disponible","Pieces Cut","0",null));
//        summary.getChildren().add(new Card("Produit disponible","Pieces Sewn","0",null));
//        summary.getChildren().add(new Card("Produit disponible","Boxes Scanned","0",null));
        
        
//        AnchorPane.setTopAnchor(charts, 10.0);
//        AnchorPane.setTopAnchor(summary, 150.0);
//        AnchorPane.setRightAnchor(Activities, 10.0);
//        AnchorPane.setTopAnchor(Activities, 150.0);
        container.getChildren().addAll(summary,charts,Activities,v);
        setPannable(true);
        this.setContent(container);
        
    }
    
    
}
