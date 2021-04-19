package com.journalisation.dao.ORM.query;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Query {
    enum QueryType{
        SELECT,INSERT,UPDATE,DELETE
    }
    protected Map<String,String> fields=new LinkedHashMap<>();
    protected String tablename;
    protected ObservableList<QueryExpression> expression= FXCollections.observableArrayList();
    protected ObservableList<Joins> joins= FXCollections.observableArrayList();
    protected QueryType type=QueryType.SELECT;
    protected  String condition="";
    protected String join="";

    protected String  query;
    public  String selectAll(String tableName){
        query="select * from "+tableName;
        return query;
    }


    public Query And(String columnName,Object value){
        this.query.concat(" and "+columnName+"=?");
        return this;
    }
    public Query select(String tableName,Map<String,String> fields){
        tablename=tableName;
        query="select ";
        this.fields.putAll(fields);
        type=QueryType.SELECT;
        return this;
    }
    public Query Join(Joins j){
        joins.add(j);
        return this;
    }
    public Query where(String columnName,Object value){
        this.query.concat(" where "+columnName+"=?");
        return this;
    }

    public String build(){
        switch(type){
            case SELECT:
                if(!joins.isEmpty()) {
                    for(Joins j:joins){
                                fields.putAll(j.field);
                            }
                }

                query+=Stringify(fields);
                query+=" from "+tablename+ " ";
                if(!joins.isEmpty()) {
                    for(Joins j:joins){
                        query+=j.getType().getValue()+" ";
                        query+=j.getTablen()+(j.getAlias().trim().isEmpty()?" ":" as "+j.getAlias());
                        query+=" on "+j.conditions+" ";
                    }
                }
                break;
            case INSERT:
                query="insert into "+tablename+" ";
                query+="("+")";

        }
        return query;
    }

    protected String Stringify(Map<String,String> param){
        Set<String> params=new HashSet<>();
        param.forEach((k,v)->{params.add(k+" as "+v);});
        return String.join(",", params);
    }
    public static class Joins{
        private String tablen;
        private String Alias;
        private Map<String,String> field;
        private String conditions;
        private JoinType type=JoinType.FULL;

        public Joins(String tablen, String alias, Map<String, String> field, String conditions, JoinType type) {
            this.tablen = tablen;
            Alias = alias;
            this.field = field;
            this.conditions = conditions;
            this.type = type;
        }

        public String getTablen() {
            return tablen;
        }

        public void setTablen(String tablen) {
            this.tablen = tablen;
        }

        public String getAlias() {
            return Alias;
        }

        public void setAlias(String alias) {
            Alias = alias;
        }

        public Map<String, String> getField() {
            return field;
        }

        public void setField(Map<String, String> field) {
            this.field = field;
        }

        public String getConditions() {
            return conditions;
        }

        public void setConditions(String conditions) {
            this.conditions = conditions;
        }

        public JoinType getType() {
            return type;
        }

        public void setType(JoinType type) {
            this.type = type;
        }
    }
}
