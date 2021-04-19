/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.controller;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import com.journalisation.Main;
import com.journalisation.alert.Alert;
import com.journalisation.alert.AlertButtons;
import com.journalisation.dao.bean.Groups;
import com.journalisation.dao.bean.Roles;
import com.journalisation.dao.bean.Users;
import com.journalisation.dao.dao.DAOFactory;
import com.journalisation.dao.dao.DAOName;
import com.journalisation.resources.view.ViewBehavior;
import com.journalisation.utils.Boutton;
import com.journalisation.utils.ViewUtils;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static com.journalisation.Main.*;

/**
 * FXML Controller class
 *
 * @author Padovano
 */
public class GroupsController extends ControllerManager {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        // TODO
        groupdatas=new SimpleObjectProperty<>(groupFilter);
        roledatas=new SimpleObjectProperty<>(roleFilter);
        userdatas=new SimpleObjectProperty<>(userFilter);
        name_role.setCellValueFactory((data)->data.getValue().nameProperty());
        abbreviation.setCellValueFactory((data)->data.getValue().abbreviationProperty());
        fullname.setCellValueFactory((data)->{return new ReadOnlyObjectWrapper<>(data.getValue().getPrenom()+" "+data.getValue().getNom());});
        sexe.setCellValueFactory((data)->data.getValue().sexeProperty());
        //dept.setCellValueFactory((data)->data.getValue().telProperty());
        username.setCellValueFactory((data)->data.getValue().usernameProperty());
        mail.setCellValueFactory((data)->data.getValue().mailProperty());
        grouptab.setCellValueFactory((data)->data.getValue().groupProperty());
        name.setCellValueFactory(data->data.getValue().nameProperty());
        description.setCellValueFactory(data->data.getValue().descriptionProperty());
        //date.setCellValueFactory(data->data.getValue().createdProperty());
        actions.setCellFactory(data->{return new UserActionsCell();});
        actions.setCellValueFactory(data->{return new SimpleObjectProperty<>(data.getValue());});
        action.setCellFactory(data->{return new GroupActionsCell();});
        action.setCellValueFactory(data->{return new SimpleObjectProperty<>(data.getValue());});
        roleActions.setCellFactory(data->{return new RoleActionsCell();});
        roleActions.setCellValueFactory(data->{return new SimpleObjectProperty<>(data.getValue());});
        tab_roles.itemsProperty().bind(roledatas);
        tab_group.itemsProperty().bind(groupdatas);
        tab_users.itemsProperty().bind(userdatas);
    }

    @Override
    protected void init() {
        roles.clear();
        users.clear();
        Main.groups.clear();
        roles.addAll(DAOFactory.createModel(DAOName.role).selectAll());
        ObservableList<Groups> gp=DAOFactory.createModel(DAOName.group).selectAll();
        Main.groups.addAll(DAOFactory.createModel(DAOName.group).selectAll());
        users.addAll(DAOFactory.createModel(DAOName.user).selectAll());
        groupFilter.addAll(groups);
        userFilter.addAll(users);
        roleFilter.addAll(roles);
    }


    @FXML
    private JFXButton newUser;

    @FXML
    private TableView<Users> tab_users;

    @FXML
    private TableColumn<Users,String> name_prod;

     @FXML
    private TableColumn<Users,String> fullname;

    @FXML
    private TableColumn<Users,String> sexe;

    @FXML
    private TableColumn<Users,String> dept;

    @FXML
    private TableColumn<Users,String> username;

    @FXML
    private TableColumn<Users,String> mail;

    @FXML
    private TableColumn<Users, Users> actions;

    @FXML
    private TableColumn<Users, Groups> grouptab;

    @FXML
    private JFXButton newGroup;

    @FXML
    private TableView<Groups> tab_group;

    @FXML
    private TableColumn<Groups, String> name;

    @FXML
    private TableColumn<Groups, String> description;

    @FXML
    private TableColumn<Groups, LocalDate> date;

    @FXML
    private TableColumn<Groups, Groups> action;

    @FXML
    private JFXButton newRole;

    @FXML
    private TableView<Roles> tab_roles;

    @FXML
    private TableColumn<Roles, String> name_role;

    @FXML
    private TableColumn<Roles, String> abbreviation;

    @FXML
    private TableColumn<Roles, Roles> roleActions;

    private ObjectProperty<ObservableList<Groups>>groupdatas;
    private ObjectProperty<ObservableList<Roles>>roledatas;
    private ObjectProperty<ObservableList<Users>>userdatas;

    private final ObservableList<Users> users= FXCollections.observableArrayList();
    private final ObservableList<Groups> groupFilter= FXCollections.observableArrayList();
    private final ObservableList<Roles> roleFilter= FXCollections.observableArrayList();
    private final ObservableList<Users> userFilter= FXCollections.observableArrayList();

    private Stage primary;

    public Stage getPrimary() {
        return primary;
    }

    public void setPrimary(Stage primary) {
        this.primary = primary;
    }
    

    @FXML
    void new_user() {
       add_changeUser(null);
    }
    
    @FXML
    void new_group(ActionEvent event) {
        add_changeGroup(null);
    }
    @FXML
    void new_role(ActionEvent event) {
        add_changeRole(null);
    }

    private void add_changeUser(Users user){
        ViewBehavior newtask=new ViewBehavior("REGISTER.fxml");
        Parent p=newtask.getparent();
        RegisterController rc=(RegisterController)newtask.getController();
        rc.setUser(user);
        Stage popup= ViewUtils.ModalFrame(p, primarystage);
        popup.showAndWait();
        user=rc.getUser();
        if(user!=null){
            if(users.contains(user)){
                users.set(users.indexOf(user),user);
            }else{
                users.add(user);
            }
            if(userFilter.contains(user)){
                userFilter.set(userFilter.indexOf(user),user);
            }else{
                userFilter.add(user);
            }
        }
        tab_users.refresh();
    }
    private void add_changeRole(Roles role){
        ViewBehavior newtask=new ViewBehavior("Roles.fxml");
        Parent p=newtask.getparent();
        RolesController rc=(RolesController)newtask.getController();
        rc.setRole(role);
        Stage popup= ViewUtils.ModalFrame(p, primarystage);
        popup.showAndWait();
        role=rc.getRole();
        if(role!=null){
            if(roles.contains(role)){
                roles.set(roles.indexOf(role),role);
            }else{
                roles.add(role);
            }
            if(roleFilter.contains(role)){
                roleFilter.set(roleFilter.indexOf(role),role);
            }else{
                roleFilter.add(role);
            }
        }
        tab_roles.refresh();
    }
    private void add_changeGroup(Groups group){
        ViewBehavior newtask=new ViewBehavior("new_group.fxml");
        Parent p=newtask.getparent();
        New_groupController rc=(New_groupController)newtask.getController();
        rc.setGroup(group);
        Stage popup= ViewUtils.ModalFrame(p, primarystage);
        popup.showAndWait();
        group=rc.getGroup();
        if(group!=null){
            if(groups.contains(group)){
                groups.set(groups.indexOf(group),group);
            }else{
                groups.add(group);
            }
            if(groupFilter.contains(group)){
                groupFilter.set(groupFilter.indexOf(group),group);
            }else{
                groupFilter.add(group);
            }
        }
        tab_group.refresh();
    }

    private class UserActionsCell extends TableCell<Users ,Users> {
        private Boutton delete,edit,reset;

        @Override
        public void updateIndex(int i) {
            super.updateIndex(i);
        }

        @Override
        protected void updateItem(Users user, boolean b) {
            super.updateItem(user, b);
            if(!b && getItem()!=null){
                render(user);
            }
        }
        private void render(Users user){

            edit=new Boutton("");
            reset=new Boutton("reset password");
            delete=new Boutton(user.getEnable()?"Disable":"Enable");
            edit.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.EDIT));
            reset.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.REFRESH));
            //delete.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.));

            edit.setOnAction((action)->add_changeUser(user));
            reset.setOnAction(action->reset(user));
            delete.setOnAction((action)->enable(user));
            HBox box=new HBox();
            box.getChildren().addAll(edit,delete,reset);
            setGraphic(box);

        }

        private void enable(Users user){
            if(Alert.showConfirmMessage(primarystage,"Voulez vous changer le statut de cet Utilisateur?",rbs)== AlertButtons.YES) {
                user.setEnable(!user.getEnable());
                DAOFactory.createModel(DAOName.user).save(user);
                if(user!=null) {
                    delete.setLabel(user.getEnable()?"Disable":"Enable");
                    if (users.contains(user)) {
                        users.set(users.indexOf(user), user);
                    }
                }
                tab_users.refresh();
            }
        }
        private void reset(Users user){
            if(Alert.showConfirmMessage(primarystage,"Voulez vous restaurer le mot de passe de cet Utilisateur?",rbs)== AlertButtons.YES) {
                user.setPass(user.getDefaultpass());
                DAOFactory.createModel(DAOName.user).save(user);
                if(user!=null) {
                    if (users.contains(user)) {
                        users.set(users.indexOf(user), user);
                    }
                }
                tab_users.refresh();
            }
        }
    }

    private class GroupActionsCell extends TableCell<Groups ,Groups> {
        private Boutton delete,edit,view;

        @Override
        public void updateIndex(int i) {
            super.updateIndex(i);
        }

        @Override
        protected void updateItem(Groups group, boolean b) {
            super.updateItem(group, b);
            if(!b && getItem()!=null){
                render(group);
            }
        }
        private void render(Groups group){

            edit=new Boutton("");
            edit.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.EDIT));

            edit.setOnAction((action)->add_changeGroup(group));

            HBox box=new HBox();
            box.getChildren().addAll(edit);
            setGraphic(box);

        }

    }
    private class RoleActionsCell extends TableCell<Roles ,Roles> {
        private Boutton delete,edit,view;

        @Override
        public void updateIndex(int i) {
            super.updateIndex(i);
        }

        @Override
        protected void updateItem(Roles role, boolean b) {
            super.updateItem(role, b);
            if(!b && getItem()!=null){
                render(role);
            }
        }
        private void render(Roles role){

            edit=new Boutton("Editer");
            edit.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.EDIT));

            edit.setOnAction((action)->add_changeRole(role));

            HBox box=new HBox();
            box.getChildren().addAll(edit);
            setGraphic(box);

        }

    }
    @FXML
    void filterGroup(KeyEvent event) {
        groupFilter.clear();
        String value=((TextField)event.getSource()).getText().trim().toLowerCase();
        for (Groups group : groups) {
            if(group.getName().toLowerCase().contains(value)||group.getDescription().toLowerCase().contains(value))
                groupFilter.add(group);
        }
        tab_group.refresh();
    }

    @FXML
    void filterRole(KeyEvent event) {
        roleFilter.clear();
        String value=((TextField)event.getSource()).getText().trim().toLowerCase();
        for (Roles role : roles) {
            if(role.getName().toLowerCase().contains(value)||role.getAbbreviation().toLowerCase().contains(value))
                roleFilter.add(role);
        }
        tab_roles.refresh();
    }

    @FXML
    void filterUser(KeyEvent event) {
        userFilter.clear();
        String value=((TextField)event.getSource()).getText().trim().toLowerCase();
        for (Users user : users) {
            if(user.getUsername().toLowerCase().contains(value)||user.getNom().toLowerCase().contains(value)||user.getPrenom().toLowerCase().contains(value))
                userFilter.add(user);
        }
        tab_users.refresh();
    }
}
