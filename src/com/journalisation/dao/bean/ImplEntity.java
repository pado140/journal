package com.journalisation.dao.bean;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Map;
import java.util.Objects;

public abstract class ImplEntity implements IEntity{

    public ImplEntity(Map<String,Object> data) {
        marshall(data);
    }
    public ImplEntity(Object... data) {
        marshall(data);
    }

    public ImplEntity(ObjectProperty<Integer> id) {
        this.id = id;
    }

    public ImplEntity() {
        init();
    }

    public ObjectProperty<Integer> id=new SimpleObjectProperty(0);
    public ObjectProperty<String> messageAction=new SimpleObjectProperty("");

    public ObjectProperty<Integer> idProperty(){
        return id;
    }
    public int getId(){
        System.out.println(id);
        return id.get();
    }
    public  boolean isNew(){
        System.out.println(id.get());
        return id.get()==0||id==null||id.get()==null;
    }
    public void setIdProperty(ObjectProperty<Integer> ids) {

    }

    public void setId(int id) {
    this.id.set(id);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.getId());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ImplEntity other = (ImplEntity) obj;
        return Objects.equals(this.getId(), other.getId());
    }

    public String getMessageAction() {
        return messageAction.get();
    }

    public ObjectProperty<String> messageActionProperty() {
        return messageAction;
    }

    public void setMessageAction(String messageAction) {
        this.messageAction.set(messageAction);
    }
}
