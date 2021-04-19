/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import com.journalisation.Main;
import com.journalisation.alert.Alert;
import com.journalisation.dao.bean.Roles;
import com.journalisation.dao.dao.DAOFactory;
import com.journalisation.dao.dao.DAOName;
import com.journalisation.dao.dao.RolesModel;
import com.journalisation.exceptions.DuplicateException;
import com.journalisation.resources.menu.MenuElements;
import com.journalisation.resources.menu.vertical.InitMenu;
import com.journalisation.resources.menu.vertical.MenuBlock;
import com.journalisation.resources.menu.vertical.MenuItems;
import com.journalisation.resources.view.ListViewMultiple;
import com.journalisation.utils.Permissions;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.journalisation.utils.AppUtils.validation;

/**
 * FXML Controller class
 *
 * @author Padovano
 */
public class RolesController extends ControllerManager {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);

        ROLE.setDisable(true);
        next0.setOnAction((event)->{
            System.out.println("validation ok:"+validation((AnchorPane)about.getContent()));
            if(validation((AnchorPane)about.getContent())){
                if(ROLE.isDisable())
                    ROLE.setDisable(false);
                wizard.getSelectionModel().selectNext();
            }
        });
        
        previous1.setOnAction((event)->{
                wizard.getSelectionModel().selectPrevious();
        });

        ((AnchorPane)ROLE.getContent()).getChildren().add(rolesaccess);
        permission.setItems(FXCollections.observableArrayList(Permissions.ADMIN,Permissions.CLERK,Permissions.MANAGER));
        done.setOnAction((actionevent)->{
            role.setName(name.getText().trim());
            role.setAbbreviation(abbr.getText().trim());
            role.setPermission(permission.getSelectionModel().getSelectedItem());
            selectedMenu(rolesaccess.getCible());
            role.setMenu(selected_menu);
            try{
                role=((RolesModel) DAOFactory.createModel(DAOName.role)).save(role);
                Main.roles.add(role);
            }catch(DuplicateException d){
                role=null;
                Alert.error(((Stage)wizard.getScene().getWindow()), d.getMessage(), traduction.get());
            }
            if(role!=null)
                close();
        });


        rolesaccess.getSources().addAll(menuElementsList);
    }

    private void loadmenu(ObservableList<? extends MenuElements> menus){
        for(MenuElements m:menus){
            if(m instanceof MenuItems){
                menuElementsList.add(m);
            }
            if(m instanceof MenuBlock){
                //menuElementsList.add(m);
                loadmenu(((MenuBlock) m).getItems());
            }
        }
    }

    private void selectedMenu(ObservableList<? extends MenuElements> menus){
        for(MenuElements m:menus){
            System.out.println(((MenuItems)m).getIds());
            if(!((MenuItems)m).getIds().trim().isEmpty())
                selected_menu.add(((MenuItems)m).getIds());
        }
    }
    @Override
    protected void init() {
        rolesaccess=new ListViewMultiple<>();
        menuElementsList=new ArrayList<>();
        loadmenu(menu.Menu());

        rolesaccess.getSourceList().setCellFactory((listcell)->new customCell());
        rolesaccess.getCibleList().setCellFactory((listcell)->new customCell());

        rolesaccess.setLayoutX(13);
        rolesaccess.setLayoutY(46);

        rolesaccess.setPrefSize(695,322);
        rolesaccess.render();


    }


    private final InitMenu menu=new InitMenu(null);
    private Roles role;
    @FXML
    private Label panLabel1;

    @FXML
    private Label panLabel2;


    @FXML
    private TabPane wizard;

    @FXML
    private Tab about;

    @FXML
    private Label permissionLabel;

    @FXML
    private TextField abbr;

    @FXML
    private Label nameLabel;

    @FXML
    private TextField name;

    @FXML
    private Label abbrLabel;

    @FXML
    private JFXButton next0;

    @FXML
    private ComboBox<Permissions> permission;

    @FXML
    private JFXButton done;

    @FXML
    private JFXButton previous1;
     @FXML
    private Tab ROLE;

     @FXML
    private AnchorPane role_menu;

    private ListViewMultiple<MenuElements> rolesaccess;
    private List<MenuElements> menuElementsList;
    private final ObservableList<String> selected_menu=FXCollections.observableArrayList();

    @FXML
    void close() {
        ((Stage)wizard.getScene().getWindow()).close();
    }


    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
        if(role!=null){
            name.setText(role.getName());
            abbr.setText(role.getAbbreviation());
            permission.getSelectionModel().select(role.getPermission());

        }else
            this.role=new Roles();
    }
    
    private class customCell extends ListCell<MenuElements>{

        @Override
        protected void updateItem(MenuElements item, boolean empty) {
            super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
            if(item!=null){
                Label l=new Label();
                if(item instanceof MenuItems){
                    if(((MenuItems)item).hasParent())
                        l.setPadding(new Insets(0,0,0,10));
                    l.setGraphic(((MenuItems)item).getIconImage());
                    l.setText(((MenuItems)item).getLabel().get());
                    if(((MenuItems)item).hasParent()){
                        l.setText(((MenuItems)item).getparent().getLabel().get()+"/"+l.getText());
                    }
                }
//                if(item instanceof MenuBlock){
//                    l.setGraphic(((MenuBlock)item).getIconImage());
//                    l.setText(((MenuBlock)item).getLabel().get());
//                }
                l.setTextFill(Paint.valueOf("black"));
                setGraphic(l);
            }

        }
        
    }
}
