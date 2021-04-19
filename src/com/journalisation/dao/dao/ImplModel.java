package com.journalisation.dao.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.journalisation.dao.ORM.ConnectionSql;
import com.journalisation.dao.ORM.query.Query;
import com.journalisation.dao.bean.ImplEntity;
import com.journalisation.dao.bean.Users;
import com.journalisation.exceptions.DuplicateException;

import java.sql.Connection;
import java.util.Arrays;
import java.util.Map;

public abstract class ImplModel<T extends ImplEntity> implements IModel<T> {
    protected  enum Status{
        PRET,OCCUPE,REUSSI,ECHOUE
    }
    protected String requete;
    protected String tableName;
    protected Query query;
    public ConnectionSql connectionSql=ConnectionSql.instance();
    protected Connection connection=connectionSql.getConnection();
    protected Status status;

    
    @Override
    public T afterUpdate(T data) {
        boolean ok=this.connectionSql.endTransaction();
        if(!ok)
            return null;
        return (T)data; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public T beforeUpdate(T data) {
        connectionSql.beginTransaction();
        return data; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public T afterSave(T data) {
        boolean ok=this.connectionSql.endTransaction();
        if(!ok)
            return null;
        return (T)data; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public T beforeSave(T data) {
        connectionSql.beginTransaction();
        return (T)data; //To change body of generated methods, choose Tools | Templates.
    }
    

    @Override
    public boolean beforeDelete(T ob) {
        return true;
    }

    @Override
    public boolean afterDelete(T ob) {
        return true;
    }

    @Override
    public T insert(T data) {
        data=beforeSave(data);
        data=add(data);
        data=afterSave(data);
        return (T)data;
    }

    @Override
    public T update(T data) {
        data=beforeUpdate(data);
        data=change(data);
        data=afterUpdate(data);
        return (T)data;
    }

    @Override
    public T save(T ob) {
        System.out.println("isnew="+ob.isNew());
        if(ob.isNew()) {
            if(exists(ob)!=null)
                throw new DuplicateException("this item is already exist!!");
            ob = insert(ob);
        }
        else
            ob=update(ob);

        return (T)ob;
    }

    @Override
    public boolean delete(T ob) {
        if(beforeDelete(ob))
            if(remove(ob))
                return afterDelete(ob);
            return false;
    }

    @Override
    public ObservableList<T> selectAll() {
        return searchBy(null,false);
    }

    @Override
    public ObservableList<T> search(Object... ob) {
        return searchBy(ob[0].toString(),(Boolean)ob[1], Arrays.copyOfRange(ob,2,ob.length));
    }

    @Override
    public ObservableList<T> csearchBy(String conditions, Object[] critere,String... field) {
        return FXCollections.observableArrayList();
    }
    @Override
    public ObservableList<T> searchBy(String conditions,boolean encaps, Object[] critere,String... field) {
        return FXCollections.observableArrayList();
    }

    @Override
    public ObservableList<T> searchById(Object... ob) {
        return searchBy(ob[0].toString(),(Boolean)ob[1], Arrays.copyOfRange(ob,2,ob.length));
    }

    @Override
    public T selectById(int ob) {
        return (T)select("id=?",false,ob);
    }

    @Override
    public T select(String conditions,boolean encaps, Object... ob) {
        ObservableList<T> datas=searchBy(conditions,encaps,ob);
        return datas.isEmpty()?null:(T)datas.get(0);
    }

    @Override
    public ObservableList<Map<String,Object>> customSql(String sql, Object... critere) {
        return connectionSql.selectlist(sql,critere); 
    }

   public boolean trace(Users users,String... action){
        requete="insert into historicite values(NULL,?,NOW(),?,?,?)";
        return connectionSql.Update(requete,0,users.getId(),action[0],action[1],action[2]);
    }
    
    public String getSqlErreur(){
        return connectionSql.getErreur();
    }
    
    public int _save(String requete,Object... data){
        connectionSql.Update(requete, 1, data);
        return connectionSql.getLast();
    }
}
