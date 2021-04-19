/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.controller;

/**
 *
 * @author Dina
 */
import static com.journalisation.Main.primarystage;
import static com.journalisation.Main.rbs;
import com.journalisation.alert.Alert;
import com.journalisation.dao.bean.Livres;
import com.journalisation.dao.bean.Ressources;
import com.journalisation.dao.bean.TaskProject;
import com.journalisation.dao.bean.Tasks;
import com.journalisation.dao.bean.Users;
import com.journalisation.dao.dao.DAOFactory;
import com.journalisation.dao.dao.DAOName;
import com.journalisation.utils.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.DirectoryChooser;

public class ExportReport extends ControllerManager{
    
    @FXML
    private TableView<TaskProject> tabreport;

    @FXML
    private TableColumn<TaskProject, Livres> title;

    @FXML
    private TableColumn<TaskProject, Users> user;

    @FXML
    private TableColumn<TaskProject, String> task;

    @FXML
    private TableColumn<TaskProject, LocalDateTime> date;
    
     @FXML
    private TableColumn<?, ?> date1;

    @FXML
    private TableColumn<?, ?> duree;

    @FXML
    private DatePicker start_date;

    @FXML
    private DatePicker end_date;
    
    private Livres project;
    private DirectoryChooser directoryChooser;
    
    private final ObservableList<TaskProject> taskuser = FXCollections.observableArrayList();
    private ObservableList<TaskProject> tasklistbyuser =DAOFactory.createModel(DAOName.task_project).selectAll();
    private final ObservableList<Ressources> docs=FXCollections.observableArrayList();

    @Override
    protected void init() {
        taskuser.addAll(tasklistbyuser);
        title.setCellValueFactory(data->data.getValue().livreProperty());
        //task.setCellFactory(data->data.getValue().taskProperty());
        date.setCellValueFactory(data->data.getValue().createdProperty());
        user.setCellValueFactory(data->data.getValue().usersProperty());
        tabreport.itemsProperty().bind(new SimpleObjectProperty<>(tasklistbyuser));
  }     
//      end_date.setDisable(true);
//       start_date.setOnAction((action)->{
//           end_date.setDisable(false);
//           taskuser.clear();
//           for(TaskProject tp:tasklistbyuser){
//                if(end_date.getValue()!=null){ 
//                    if ((tp.getTasks().getDate.isEqual(start_date.getValue())||
//                            tp.getTasks().getDate().isAfter(start_date.getValue()))&&
//                            (tp.getTasks().getDate().isBefore(end_date.getValue())||tp.getTasks().getDate().isEqual(end_date.getValue()))){
//                        taskuser.add(tp);
//                    }
//                }else{
//                    if (tp.getTasks().getDate().isEqual(start_date.getValue()))
//                        taskuser.add(tp);
//                }
//           }
//       });
       
//       end_date.setOnAction((action)->{
//           taskuser.clear();
//           for(TaskProject tp:tasklistbyuser){
//                if(start_date.getValue()!=null){ 
//                    if ((tp.getTasks().getDate.isEqual(start_date.getValue())||
//                            tp.getTasks().getDate().isAfter(start_date.getValue()))&&
//                            (tp.getTasks().getDate().isBefore(end_date.getValue())||tp.getTasks().getDate().isEqual(end_date.getValue()))){
//                        taskuser.add(tp);
//                    }
//                }
//           }
//       });
//    }
//    
//    @FXML
//    void export(ActionEvent event) throws IOException {
//        FileUtils fut=FileUtils.instance();
//        directoryChooser.setTitle("Selectionnez le repertoire de sauvegarde");
//        File f=directoryChooser.showDialog(primarystage);
//        if(f!=null){
//            for (Ressources doc : docs) {
//                Path source=Paths.get(doc.getPath());
//                Files.createDirectories(Paths.get(f.getPath(),project.getTitre()));
//                String destination=Paths.get(f.getPath(),project.getTitre()).toFile().getPath();
//                fut.downloadFile(source.toFile(),destination);
//            }
//        }
//        Alert.showInfo(primarystage, "Upload status", "Extraction reussi", rbs);
//    }
 }