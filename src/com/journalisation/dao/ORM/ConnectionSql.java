package com.journalisation.dao.ORM;

import com.journalisation.alert.Alert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.journalisation.exceptions.DatabaseException;
import com.journalisation.resources.log.AppLog;
import com.journalisation.utils.AppUtils;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionSql {
    private static ConnectionSql connectionSql;
    private String url="",username="",password="";
    private String api="com.mysql.cj.jdbc.Driver";
    private Statement state=null;
    private ResultSet resultat;
    private Connection connection=null;
    private int lastinsert=0;
    private String erreur="";

    private void init(){
        username= AppUtils.dbUSER;
        password=AppUtils.dbPASSWORD;
        url="jdbc:";
        url+=AppUtils.PROVIDER+"://";
        url+=AppUtils.HOST;
        url+=(AppUtils.PORT.isEmpty()?"":":")+AppUtils.PORT;
        url+=AppUtils.PROVIDER.contains("mysql")?"/":";databaseName=";
        url+=AppUtils.dbNAME;
        api=AppUtils.API;
    }

    private ConnectionSql() {
        init();
        try
        {
            Class.forName(api);
//            connection=DatasourceUtil.instance().getConnection();
            connection = DriverManager.getConnection(
                    url, username, password);
            Logger.getLogger(ConnectionSql.class.getName()).log(Level.INFO, "success");
            System.out.println("Success");
            System.out.println("status:"+connection.getClientInfo().toString());
        }catch(SQLException | ClassNotFoundException |NullPointerException ex){
            System.out.println(ex.getMessage());
            setErreur(ex.getMessage());
            ex.printStackTrace();
            //throw new DatabaseException(ex.getMessage());
        }
    }

    public String getErreur() {
        return erreur;
    }

    public void setErreur(String erreur) {
        this.erreur = erreur;
    }
    /*
     *on utilise le Pattern singleton
     *
     */
    public static synchronized ConnectionSql instance(){
        if(connectionSql==null)
            connectionSql=new ConnectionSql();
        else{
            try {
                if(connectionSql.connection.isClosed()){
                    connectionSql=new ConnectionSql();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ConnectionSql.class.getName()).log(Level.SEVERE, null, ex);
                throw new DatabaseException(ex.getMessage());
            }
        }

        return connectionSql;
    }

    public synchronized ObservableList<? > selectl(Class<?> clas, String query, Object... critere){

        ObservableList<Object> list= FXCollections.observableArrayList();
        try {
            PreparedStatement prepare=connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            int i=1;
            for(Object ob:critere){
                prepare.setObject(i, ob);
                i++;
            }
            ResultSetMetaData metadata=prepare.getMetaData();

            resultat=prepare.executeQuery();
            while(resultat.next()){
                //Object ob=clas.newInstance();
                for(int j=1;j<=metadata.getColumnCount();j++){
                    //utility.hydrated(ob, metadata.getColumnName(j), resultat.getObject(j,Class.forName(metadata.getColumnClassName(j))));

                }
                //list.add(ob);
            }
            resultat.close();
            prepare.closeOnCompletion();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionSql.class.getName()).log(Level.SEVERE, null, ex);
            this.setErreur(ex.getMessage());
            System.err.println(ex.getErrorCode()+ex.getSQLState());
            throw new DatabaseException(ex.getMessage());
            
        }finally{
            instance();
        }
//        catch (ClassNotFoundException ex) {
//            Logger.getLogger(ConnectionSql.class.getName()).log(Level.SEVERE, null, ex);
//        }
        return list;
    }
    public synchronized ObservableList<Map<String,Object>> selectlist(String query, Object... critere){

        ObservableList<Map<String,Object>> list= FXCollections.observableArrayList();
        try {
            PreparedStatement prepare=connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            int i=1;
            for(Object ob:critere){
                prepare.setObject(i, ob);
                i++;
            }
            ResultSetMetaData metadata=prepare.getMetaData();

            resultat=prepare.executeQuery();
            while(resultat.next()){
                //Object ob=clas.newInstance();
                Map<String,Object> obj=new LinkedHashMap<>();
                for(int j=1;j<=metadata.getColumnCount();j++){
                    //utility.hydrated(ob, metadata.getColumnName(j), resultat.getObject(j,Class.forName(metadata.getColumnClassName(j))));
                    if(resultat.getObject(j)!=null)
                        obj.put(metadata.getColumnName(j),resultat.getObject(metadata.getColumnName(j)));
                }
                list.add(obj);
            }
            resultat.close();
            prepare.closeOnCompletion();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionSql.class.getName()).log(Level.SEVERE, null, ex);
            this.setErreur(ex.getMessage());
            
            throw new DatabaseException(ex.getMessage());
        }finally{
            
            instance();
        }
//        catch (ClassNotFoundException ex) {
//            Logger.getLogger(ConnectionSql.class.getName()).log(Level.SEVERE, null, ex);
//        }
        return list;
    }

    public synchronized ResultSet select(String query,Object... critere){
        try {
            PreparedStatement prepare=connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            int i=1;
            for(Object ob:critere){
                prepare.setObject(i, ob);
                i++;
            }
            resultat=prepare.executeQuery();
            prepare.closeOnCompletion();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionSql.class.getName()).log(Level.SEVERE, null, ex);
            
            this.setErreur(ex.getMessage());
            
        }finally{
            
            instance();
        }
        return resultat;
    }

    public synchronized Object selectOb(Class<?> clas,String query,Object... critere){
        Object ob=null;
        try {
            PreparedStatement prepare=connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            int i=1;
            for(Object obj:critere){
                prepare.setObject(i, obj);
                i++;
            }
            resultat=prepare.executeQuery();
            ResultSetMetaData metadata=prepare.getMetaData();
            while(resultat.next()){
                for(int j=1;j<=metadata.getColumnCount();j++){

                }
            }
            resultat.close();
            prepare.closeOnCompletion();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionSql.class.getName()).log(Level.SEVERE, null, ex);

            this.setErreur(ex.getMessage());
            instance();
        }
        return ob;
    }

    public synchronized Map<String,Object> selectOb(String query,Object... critere){
        Map<String,Object> ob=null;
        try {
            PreparedStatement prepare=connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            int i=1;
            for(Object obj:critere){
                prepare.setObject(i, obj);
                i++;
            }
            resultat=prepare.executeQuery();
            ResultSetMetaData metadata=prepare.getMetaData();
            while(resultat.next()){
                ob=new LinkedHashMap<>();
                for(int j=1;j<=metadata.getColumnCount();j++){
                    ob.put(metadata.getColumnName(j),resultat.getObject(metadata.getColumnName(j)));
                }
            }
            resultat.close();
            prepare.closeOnCompletion();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionSql.class.getName()).log(Level.SEVERE, null, ex);

            this.setErreur(ex.getMessage());
            throw new DatabaseException(ex.getMessage());
        }finally{
            instance();
        }
        return ob;
    }
    public synchronized boolean Update(String query,int auto,Object... critere){
        boolean check=false;
        try {
            PreparedStatement prepare=connection.prepareStatement(query, auto);
            int i=1;
            for(Object ob:critere){
                prepare.setObject(i, ob);
                i++;
            }
            if(prepare.executeUpdate()>0){
                check=true;
                if(auto==1){
                    ResultSet rs=prepare.getGeneratedKeys();
                    while(rs.next())
                        lastinsert=(int)rs.getInt(1);
                }

            }
            prepare.closeOnCompletion();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionSql.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            this.setErreur(ex.getMessage());
            
            throw new DatabaseException(ex.getMessage());
        }finally{
            
            instance();
        }
        return check;
    }

    public synchronized ResultSet lirecst(String procedure,Object... params){
        try {
            CallableStatement c=connection.prepareCall(procedure, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            for(int i=0;i<params.length;i++)
                c.setObject(i, params[i]);

            return c.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionSql.class.getName()).log(Level.SEVERE, null, ex);
            this.setErreur(ex.getMessage());
        }
        return null;
    }

    public synchronized boolean savecst(String procedure,Object... params){
        CallableStatement c=null;
        try {
            c=connection.prepareCall(procedure);
            for(int i=0;i<params.length;i++)
                c.setObject(i+1, params[i]);

            c.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionSql.class.getName()).log(Level.SEVERE, null, ex);
            this.setErreur(ex.getMessage());
        }finally{
            try {
                if(c!=null)
                    c.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConnectionSql.class.getName()).log(Level.SEVERE, null, ex);
                this.setErreur(ex.getMessage());
                throw new DatabaseException(ex.getMessage());
            }

        }
        return false;
    }
    public int getLast(){
        return lastinsert;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }


    public void exec(Map<String,Object[]> requetes){
        try {
            connection.setAutoCommit(false);
            for(String requete: requetes.keySet()){
                this.Update(requete, 1, requetes.get(requete));
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ConnectionSql.class.getName()).log(Level.SEVERE, null, ex1);
                throw new DatabaseException(ex.getMessage());
            }
            Logger.getLogger(ConnectionSql.class.getName()).log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        }

    }
    public boolean endTransaction(){
        try{
            connection.commit();
            return true;
        }catch(SQLException e){
            System.err.println(e.getMessage());
            Alert.error(null, e.getMessage(),null);
            roolback();
        }
        try{
            this.connection.setAutoCommit(true);
        }catch(SQLException ex){
            System.err.println(ex.getMessage());
            AppLog.Log(getClass().getName(), Level.SEVERE,ex.getMessage(),ex);
        }
        return false;
    }
    public void beginTransaction(){
        try{
            this.connection.setAutoCommit(false);
        }catch(SQLException e){
            System.err.println(e.getMessage());
            Alert.error(null, e.getMessage(),null);
        }
    }   
    public void roolback(){
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex1);
                Alert.error(null, ex1.getMessage(),null);
            }
    }
    
}
