package com.journalisation.dao.bean;

import com.journalisation.dao.enumeration.TaskState;
import java.time.LocalDateTime;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Map;

public class TaskProject extends ImplEntity {
    private ObjectProperty<String> tasks;
    private ObservableList<Users> users;
    private ObjectProperty<Users> user;
    private ObjectProperty<Livres> livres;
    private ObjectProperty<Integer> duree;
    private ObjectProperty<TaskState> state;
    private ObjectProperty<LocalDateTime> created;

    public TaskState getState() {
        return state.get();
    }

    public LocalDateTime getCreated() {
        return created.get();
    }

    public void setCreated(LocalDateTime created) {
        this.created.set(created);
    }
    
    public ObjectProperty<LocalDateTime> createdProperty(){
        return created;
    }

    public ObjectProperty<TaskState> stateProperty() {
        return state;
    }

    public void setState(TaskState state) {
        this.state.set(state);
    }
    
    public void setState(String state) {
        this.state.set(TaskState.valueOf(state));
    }

    public TaskProject(Map<String, Object> data) {
        super(data);
    }

    public TaskProject(Object... data) {
        super(data);
    }

    public TaskProject(ObjectProperty<Integer> id) {
        super(id);
    }

    public TaskProject() {
    }

    public String getTasks() {
        return tasks.get();
    }

    public ObjectProperty<String> taskProperty() {
        return tasks;
    }
    
    public ObjectProperty<Users> usersProperty() {
        return user;
    }

    public void setTasks(String task) {
        this.tasks.set(task);
    }

    public Livres getLivre() {
        return livres.get();
    }

    public ObjectProperty<Livres> livreProperty() {
        return livres;
    }

    public void setLivre(Livres livre) {
        this.livres.set(livre);
    }

    public Integer getDuree() {
        return duree.get();
    }

    public ObservableList<Users> getUser() {
        return users;
    }

    public void setUser(ObservableList<Users> user) {
        this.users = user;
    }

    public ObjectProperty<Integer> dureeProperty() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree.set(duree);
    }

    @Override
    public void init() {
        duree=new SimpleObjectProperty<>();
        state=new SimpleObjectProperty<>();
        tasks=new SimpleObjectProperty<>();
        livres=new SimpleObjectProperty<>();
        users= FXCollections.observableArrayList();
        created=new SimpleObjectProperty<>();
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public String toString() {
        return tasks.get();
    }
}
