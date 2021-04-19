package com.journalisation.resources.view;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import com.journalisation.resources.lang.TraductionUtils;

public class ListViewMultiple<T> extends StackPane {
    private final ObservableList<T> sources= FXCollections.observableArrayList();
    private final ObservableList<T> cible= FXCollections.observableArrayList();
    private final ListView<T> sourceList=new ListView<>();
    private final ListView<T> cibleList=new ListView<>();
    private final Label availableText=new Label("Available");
    private final Label selectedText=new Label("Selected");
    private boolean selectall=false;

    protected final Runnable localeChange=this::translate;
    protected final ObservableMap<String , Labeled> toTranslate=FXCollections.observableHashMap();
    protected final ObjectProperty<TraductionUtils> traduction=new SimpleObjectProperty<>();

    public ListViewMultiple() {
        ObjectProperty<ObservableList<T>> observablesource=new SimpleObjectProperty<>(sources);
        ObjectProperty<ObservableList<T>> observablecible=new SimpleObjectProperty<>(cible);
        availableText.setId("Disponible");
        selectedText.setId("Selectionner");
        sourceList.itemsProperty().bind(observablesource);
        sourceList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        cibleList.itemsProperty().bind(observablecible);
        cibleList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        translateable(this);
    }

    public Label getAvailableText() {
        return availableText;
    }

    public Label getSelectedText() {
        return selectedText;
    }

    public void render(){
        VBox actionButton =new VBox();
        JFXButton ltr,ltrall,rtl,rtlall;
        ltr=new JFXButton(">");
        ltr.setOnAction((action)->{
            fromSourcetoCible();
        });
        ltrall=new JFXButton(">>");
        ltrall.setOnAction((action)->{
            selectall=true;
            fromSourcetoCible();
        });
        rtl=new JFXButton("<");
        rtl.setOnAction((action)->{
            fromCibletoSource();
        });
        rtlall=new JFXButton("<<");
        rtlall.setOnAction((action)->{
            selectall=true;
            fromCibletoSource();
        });
        actionButton.getChildren().addAll(ltr,ltrall,rtl,rtlall);
        actionButton.setPrefWidth(45);
        actionButton.setPrefHeight(USE_COMPUTED_SIZE);
        actionButton.setAlignment(Pos.CENTER);
        HBox box=new HBox();
        box.getChildren().addAll(side("l"),actionButton,side("r"));
        box.setAlignment(Pos.CENTER);
        this.getChildren().add(box);
    }

    private VBox side(String l){
        VBox pane=new VBox();
        Label titre;
        ListView<T> list;
        if(l.equalsIgnoreCase("l")){
            titre=availableText;
            list=sourceList;
        }else{
            titre=selectedText;
            list=cibleList;
        }
        pane.getChildren().addAll(titre,list);
        return pane;
    }
    private boolean fromSourcetoCible(){
        boolean result=false;
        if(selectall){
            selectall=false;
            result=cible.addAll(sources);
            sources.clear();
        }else{
            ObservableList<T> selection=sourceList.getSelectionModel().getSelectedItems();
            cible.addAll(selection);
            result=sources.removeAll(selection);
        }
        sourceList.refresh();
        cibleList.refresh();
        return result;
    }
    private boolean fromCibletoSource(){
        boolean result=false;
        if(selectall){
            selectall=false;
            result=sources.addAll(cible);
            cible.clear();
        }else{
            ObservableList<T> selection=cibleList.getSelectionModel().getSelectedItems();
            sources.addAll(selection);
            result= cible.removeAll(selection);
        }
        sourceList.refresh();
        cibleList.refresh();
        return result;
    }
    public ObservableList<T> getSources() {
        return sources;
    }

    public ObservableList<T> getCible() {
        return cible;
    }

    public ListView<T> getSourceList() {
        return sourceList;
    }

    public ListView<T> getCibleList() {
        return cibleList;
    }

    protected void translate(){
        if(!isI18n()){
            System.err.println(isI18n());
            return;
        }
        System.err.println(traduction.get());
        toTranslate.keySet().stream().forEach((text) -> {
            translateable(toTranslate.get(text), text);
        });

    }

    public void I18n(TraductionUtils tr){
        if (traduction.get() != null) {
            traduction.get().removeListener(localeChange);
        }
        if(tr==null)
            return;
        traduction.set(tr);
        traduction.get().addListener(localeChange);
        System.out.println("traduction:"+tr);
    }

    protected boolean isI18n(){
        return traduction.get()!=null;
    }

    protected void translateable(StringProperty lab){

    }
    public void translateable(Labeled lab,String label){
        Platform.runLater(()->{lab.setText(traduction.get().Translate(label));});
    }
    protected void translateable(Pane pane){
        pane.getChildren().parallelStream().forEach(node -> {
            if(node instanceof Labeled) {
                Platform.runLater(() -> {
                    ((Labeled) node).setText(traduction.get().Translate(((Labeled) node).getId()));
                });
            }
            if(node instanceof TableView){
                ((TableView)node).getColumns().parallelStream().forEach(o -> {
                    Platform.runLater(()->{((TableColumn)o).setText(traduction.get().Translate(((TableColumn)o).getId()));});
                });

            }
        });
    }
    public void translateable(VBox pane){
        pane.getChildren().parallelStream().forEach(node -> {
            if(node instanceof Labeled) {
                Platform.runLater(() -> {
                    ((Labeled) node).setText(traduction.get().Translate(((Labeled) node).getId()));
                });
            }
            if(node instanceof TableView){
                ((TableView)node).getColumns().parallelStream().forEach(o -> {
                    Platform.runLater(()->{((TableColumn)o).setText(traduction.get().Translate(((TableColumn)o).getId()));});
                });

            }
        });
    }
}
