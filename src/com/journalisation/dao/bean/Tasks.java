package com.journalisation.dao.bean;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Map;

public class Tasks extends ImplEntity {
    private ObjectProperty<String> task;

    public Tasks(Map<String, Object> data) {
        super(data);
    }

    public Tasks(Object... data) {
        super(data);
    }

    public Tasks(ObjectProperty<Integer> id) {
        super(id);
    }

    public Tasks() {
    }

    public String getTask() {
        return task.get();
    }

    public ObjectProperty<String> taskProperty() {
        return task;
    }

    public void setTask(String task) {
        this.task.set(task);
    }

    @Override
    public void init() {
        task=new SimpleObjectProperty<>();
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public String toString() {
        return getTask();
    }
}
