package com.journalisation.dao.bean;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

public class Transaction extends ImplEntity {
    private ObjectProperty<Users> users;
    private ObjectProperty<Optional<Documents>> documents;
    private ObjectProperty<Optional<Ressources>> ressources;
    private ObjectProperty<LocalDateTime> created;
    private ObjectProperty<String> action;

    public Transaction(Map<String, Object> data) {
        super(data);
    }

    public Users getUsers() {
        return users.get();
    }

    public ObjectProperty<Users> usersProperty() {
        return users;
    }

    public void setUsers(Users users) {
        this.users.set(users);
    }

    public Optional<Documents> getDocs() {
        return documents.get();
    }

    public ObjectProperty<Optional<Documents>> documentsProperty() {
        return documents;
    }

    public void setDocuments(Optional<Documents> docs) {
        this.documents.set(docs);
    }

    public Optional<Ressources> getRessources() {
        return ressources.get();
    }

    public ObjectProperty<Optional<Ressources>> ressourcesProperty() {
        return ressources;
    }

    public void setRessource(Optional<Ressources> ressource) {
        this.ressources.set(ressource);
    }

    public LocalDateTime getCreated() {
        return created.get();
    }

    public ObjectProperty<LocalDateTime> createdProperty() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created.set(created);
    }

    public String getAction() {
        return action.get();
    }

    public ObjectProperty<String> actionProperty() {
        return action;
    }

    public void setAction(String action) {
        this.action.set(action);
    }

    @Override
    public void init() {
        users=new SimpleObjectProperty<>();
        documents=new SimpleObjectProperty<>();
        ressources=new SimpleObjectProperty<>();
        action=new SimpleObjectProperty<>();
        created=new SimpleObjectProperty<>();
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public String toString() {
        return action.get();
    }
}
