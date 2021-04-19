/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.utils;

import com.journalisation.Main;
import com.journalisation.controller.ControllerManager;
import com.journalisation.dao.ORM.ConnectionSql;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Padovano
 */
public class connectionVerification implements Runnable{
    private ConnectionSql c;

    public connectionVerification(ConnectionSql c) {
        this.c = c;
    }
    

    @Override
    public void run() {
        try {
            ControllerManager.connected.set(!c.getConnection().isClosed());
        } catch (SQLException ex) {
            Logger.getLogger(connectionVerification.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
