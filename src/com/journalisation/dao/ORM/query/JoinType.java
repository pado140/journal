package com.journalisation.dao.ORM.query;

public enum JoinType {
    INNER("INNER JOIN"),
    LEFT("LEFT OUTER JOIN"),
    RIGHT("RIGHT OUTER JOIN"),
    FULL("JOIN");

    private String value;
    private JoinType(String value){
        this.value=value;
    }

    public String getValue() {
        return value;
    }
}
