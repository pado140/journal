package com.journalisation.dao.dao;

import javafx.collections.ObservableList;
import com.journalisation.dao.bean.ImplEntity;

import java.io.Serializable;
import java.util.Map;

public interface IModel<T extends ImplEntity> extends Serializable {
    T beforeSave(T data);
    T afterSave(T data);
    T insert(T data);
    T add(T data);
    T beforeUpdate(T data);
    T afterUpdate(T data);
    T update(T data);
    T change(T data);
    T save(T ob);
    ObservableList<T> selectAll();
    boolean remove(T ob);
    ObservableList<T> search(Object... ob);
    ObservableList<T> searchById(Object... ob);
    ObservableList<T> searchBy(String conditions,boolean withcontain, Object... critere);
    ObservableList<T> searchBy(String conditions,boolean withcontain, Object[] critere,String... field);
    boolean beforeDelete(T ob);
    boolean afterDelete(T ob);
    boolean delete(T ob);
    T select(String conditions,boolean encaps, Object... ob);
    T selectById(int ob);
    T exists(T ob);
    ObservableList<Map<String,Object>> customSql(String sql, Object ... critere);
    ObservableList<T> csearchBy(String conditions, Object[] critere,String... field);
}
