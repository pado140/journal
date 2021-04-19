package com.journalisation.exceptions;

import com.journalisation.resources.log.AppLog;

import java.util.logging.Level;

public class DuplicateException extends RuntimeException {
    public DuplicateException() {
    }

    public DuplicateException(String message) {
        super(message);
        AppLog.Log(DuplicateException.class.getName(), Level.SEVERE,message,null);
    }

    public DuplicateException(String message, Throwable cause) {
        super(message, cause);
        AppLog.Log(DuplicateException.class.getName(), Level.SEVERE,message,cause);
    }

    public DuplicateException(Throwable cause) {
        super(cause);
    }

    public DuplicateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
