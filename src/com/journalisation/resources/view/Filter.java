package com.journalisation.resources.view;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import com.journalisation.dao.bean.ImplEntity;
import com.journalisation.utils.AppUtils;

import java.lang.reflect.Method;

public class Filter extends AnchorPane {
    private final HBox colName=new HBox();
    private final HBox actions=new HBox();
    private final VBox criteres=new VBox();
    private final Hyperlink newCritere=new Hyperlink("+ add Filter");
    private JFXButton apply,cancel;
    private Label nameCol;
    private Label filterCol;
    private Label valCol;
    private ObjectProperty<TableView<? extends ImplEntity>> target;
    private final ScrollPane critereContainer=new ScrollPane();
    private final ObservableMap<String,String> critereField= FXCollections.observableHashMap();
    private Class clazz;

    public Filter(TableView<? extends ImplEntity> target, ObservableMap<String,String> fields) {
        this.target = new SimpleObjectProperty<>(target);
        this.critereField.putAll(fields);
        //this.clazz=clazz;
        initUi();
    }

    private void initUi(){
        this.setPrefSize(531,362);
        this.getStyleClass().add("paneFrame");
        this.getStylesheets().add(getClass().getResource("../css/style.css").toExternalForm());
        Label titre = new Label("Filter Columns");
        titre.setFont(Font.font(null, FontWeight.BOLD,20));
        titre.setLayoutX(28);
        titre.setLayoutY(14);
        nameCol=new Label("Column Name");
        nameCol.setFont(Font.font(14));
        filterCol=new Label("Filter");
        filterCol.setFont(Font.font(14));
        valCol=new Label("Value");
        valCol.setFont(Font.font(14));
        colName.setPrefSize(422,17);
        colName.getChildren().addAll(nameCol,filterCol,valCol);
        HBox.setMargin(nameCol, new Insets(0, 0, 0, 55));
        HBox.setMargin(filterCol, new Insets(0, 0, 0, 110));
        colName.setLayoutX(28);
        colName.setLayoutY(57);

        critereContainer.setFitToWidth(true);
        critereContainer.setFitToHeight(true);
        critereContainer.setPrefSize(463,206);
        critereContainer.setLayoutX(28);
        critereContainer.setLayoutY(77);

        criteres.setFillWidth(true);
        criteres.setPrefSize(453,200);

        critereContainer.setContent(criteres);
        apply=new JFXButton("Apply");
        apply.setOnAction((ation)->{
            Apply();
        });
        cancel=new JFXButton("Cancel");
        cancel.setOnAction(act->cancel());

        actions.getChildren().addAll(apply,cancel);
        actions.setLayoutX(28);
        actions.setLayoutY(315);

        newCritere.setLayoutY(293);
        newCritere.setLayoutX(28);
        newCritere.setOnAction((e)->addCritere());
        this.getChildren().addAll(titre,colName,critereContainer,newCritere,actions);
    }

    public VBox getCriteres(){
        return criteres;
    }

    private void addCritere(){
        criteres.getChildren().add(new critereSearch(criteres,target.get(),critereField));
    }

    private void cancel()
    {

        ((Stage)this.getScene().getWindow()).close();
    }

    private void Apply(){

        ObservableList<? extends ImplEntity> list=target.get().getItems();
        ObservableList<ImplEntity> listfiltre=FXCollections.observableArrayList();
        listfiltre.clear();
        for(Node n:criteres.getChildren()){
            if(n instanceof critereSearch){
                critereSearch cs=(critereSearch)n;
                for (ImplEntity o : list) {
                    Method m = AppUtils.loadMethod(((ImplEntity) o).getClass(),
                            "get"+AppUtils.ucfirst(cs.getField(cs.getColumnsCombo().getSelectionModel().getSelectedItem())));
                    try {
                        Object ab=m.invoke(o);
                         switch(cs.getFilterCombo().getSelectionModel().getSelectedItem())   {
                             case "Equals To":
                                 if(equalsTo(ab,cs.getValue().getText()))
                                     listfiltre.add(o);
                                 break;
                             case "Contains":
                                 if(contains(ab,cs.getValue().getText()))
                                     listfiltre.add(o);
                                 break;
                             case "Begins with":
                                 if(beginsWith(ab,cs.getValue().getText()))
                                     listfiltre.add(o);
                                 break;
                             case "Not equal to":
                                 if(!equalsTo(ab,cs.getValue().getText()))
                                     listfiltre.add(o);
                                 break;
                             case "Greater than":
                                 break;
                             case "Less than":
                                 break;
                         }
                         target.get().itemsProperty().bind(new SimpleObjectProperty(listfiltre));
                        System.out.println(ab);
                    }catch(Exception e){

                    }
                }
            }
        }
    }

    private boolean equalsTo(Object ob,Object other){
        return ob.equals(other);
    }

    private boolean beginsWith(Object ob,Object other){
        return ob.toString().startsWith(other.toString());
    }
    private boolean contains(Object ob,Object other){
        return ob.toString().contains(other.toString());
    }
    private boolean LessThan(Object ob,Object other){
        return ob.equals(other);
    }

    private boolean greaterThan(Object ob,Object other){
        return ob.toString().startsWith(other.toString());
    }
    private boolean endWith(Object ob,Object other){
        return ob.toString().contains(other.toString());
    }
}
