package com.journalisation.dao.ORM.query;

import com.journalisation.utils.AppUtils;

import java.util.Map;
import java.util.Set;

public class QueryExpression {
    private String query;
    private Object[] param;
    public static String AND(Set<String> critere){
        return AppUtils.serializedParam(" AND ",AppUtils.PreparedExpression(critere));
    }

    public static String AND(String expression, String queryExpression){
        return " AND ("+queryExpression+")";
    }

    public static final String OR(Map<String, Object> critere,QueryExpression... expression){
        return null;
    }
}
