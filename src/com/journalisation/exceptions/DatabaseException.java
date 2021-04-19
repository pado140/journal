package com.journalisation.exceptions;

import com.journalisation.Main;
import com.journalisation.alert.Alert;
import com.journalisation.resources.log.AppLog;

import java.util.logging.Level;

public class DatabaseException extends RuntimeException {
    public DatabaseException() {
    }

    public DatabaseException(String message) {
        super(message);
        AppLog.Log(getClass().getName(), Level.SEVERE,message,null);
        Alert.error(Main.primarystage,message, Main.rbs);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseException(Throwable cause) {
        super(cause);
    }

    public DatabaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
