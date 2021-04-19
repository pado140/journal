package com.journalisation.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import com.journalisation.utils.filelistener;

public class listen extends Service<Void> {
    private static listen li=null;
    private filelistener listener;

    private listen(filelistener listener) {
        this.listener = listener;
    }

    public static synchronized listen instance(filelistener f){
        if(li==null)
            li=new listen(f);
        return li;
    }

    @Override
    protected Task<Void> createTask() {
        listener.processEvents(null);
        return null;
    }

}
