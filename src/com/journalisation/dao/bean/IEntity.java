package com.journalisation.dao.bean;


import com.journalisation.utils.AppUtils;

import java.io.Serializable;
import java.util.Map;

public interface IEntity extends Serializable {

    boolean isValid();
    default void marshall(Object... data){
        init();
        for(int i=0;i<data.length;i+=2){
            AppUtils.hydrated(this,data[i].toString(),data[i+1]);
        }
    }
    default void marshall(Map<String,Object> data){
        init();
        data.keySet().forEach((key) -> {
            AppUtils.hydrated(this,key,data.getOrDefault(key,null));
        });
    }

    
    void init();
}
