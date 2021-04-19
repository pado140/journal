package com.journalisation.resources.view;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import com.journalisation.dao.bean.ImplEntity;

import java.util.Map;

public class critereSearch extends HBox {
    private final ObservableList<String> filter= FXCollections.observableArrayList("Equals To","Contains","Begins with","Not equal to","Greater than","Less than");
    private final ComboBox<String> filterCombo=new ComboBox();
    private final ComboBox<String> columnsCombo=new ComboBox();
    private final TextField value=new TextField();
    private final JFXButton remove=new JFXButton("x");
    private final ObservableMap<String,String> fieldToSearch= FXCollections.observableHashMap();
    private final ObservableList<String> columnsTitle= FXCollections.observableArrayList();
    private VBox parents;
    private final ObservableMap<String,String> fields=FXCollections.observableHashMap();
    private TableView<? extends ImplEntity> table;

    public critereSearch(VBox parents, TableView<? extends ImplEntity> table, Map<String,String> fields) {
        this.parents = parents;
        this.table = table;
        this.fields.putAll(fields);
        this.columnsTitle.addAll(fields.keySet());
        InitUI();
    }

    private void InitUI(){
        columnsCombo.itemsProperty().bind(new SimpleObjectProperty<>(columnsTitle));
        filterCombo.setItems(filter);
        this.setSpacing(5);
        this.getChildren().addAll(columnsCombo,filterCombo,value,remove);

        remove.setOnAction((action)->removeCritere());
    }


    private void removeCritere(){
        System.out.println(this.parents.getChildren());
        this.parents.getChildren().removeAll(this);
    }

    public ObservableMap<String, String> getFields() {
        return fields;
    }

    public ObservableList<String> getColumnsTitle() {
        return columnsTitle;
    }

    public void addColumnTitle(String column,String field){
        columnsTitle.add(column);
        fields.put(column,field);
    }

    public String getField(String column){
        return fields.get(column);
    }

    public ComboBox<String> getFilterCombo() {
        return filterCombo;
    }

    public ComboBox<String> getColumnsCombo() {
        return columnsCombo;
    }

    public TextField getValue() {
        return value;
    }
}
