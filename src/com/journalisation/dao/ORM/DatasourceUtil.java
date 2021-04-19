/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.dao.ORM;

import com.journalisation.utils.AppUtils;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;

/**
 *
 * @author ADMIN
 */
public class DatasourceUtil {
    
    private final HikariConfig cfg=new HikariConfig();
    private static HikariDataSource datasource;
    private String url;

    public DatasourceUtil() {
        url="jdbc:";
        url+=AppUtils.PROVIDER+"://";
        url+=AppUtils.HOST;
        url+=(AppUtils.PORT.isEmpty()?"":":")+AppUtils.PORT;
        url+=AppUtils.PROVIDER.contains("mysql")?"/":";databaseName=";
        url+=AppUtils.dbNAME;
        cfg.setJdbcUrl(url);
        cfg.setDriverClassName(AppUtils.API);
        cfg.setUsername(AppUtils.dbUSER);
        cfg.setPassword(AppUtils.dbPASSWORD);
        cfg.setMaximumPoolSize(20);
        cfg.addDataSourceProperty( "cachePrepStmts" , "true" );
        cfg.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        cfg.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        datasource=new HikariDataSource(cfg);
    }
    
    public static DataSource instance(){
        if(datasource==null)
            new DatasourceUtil();
        return datasource;
    }
}
