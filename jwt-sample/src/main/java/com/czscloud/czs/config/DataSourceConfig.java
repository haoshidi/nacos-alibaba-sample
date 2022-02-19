package com.czscloud.czs.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    @Bean
    public DataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/jwt_demo?rewriteBatchedStatements=true&autoReconnect=true&useUnicode=true&characterEncoding=utf8&characterSetResults=utf8&useSSL=false&serverTimezone=UTC&useOldAliasMetadataBehavior=true");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        return dataSource;
    }
}
