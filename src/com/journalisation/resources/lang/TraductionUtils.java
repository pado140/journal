package com.journalisation.resources.lang;

import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;

public abstract class TraductionUtils {
    private List<Runnable> listener=new ArrayList<>();

    public void addListener(Runnable r){
        this.listener.add(r);
    }
    public void removeListener(Runnable r){
        this.listener.remove(r);
    }

    public abstract String Translate(String a);


    public void notifyListeners(){
        this.listener.forEach(r-> Platform.runLater(r));
    }
}
