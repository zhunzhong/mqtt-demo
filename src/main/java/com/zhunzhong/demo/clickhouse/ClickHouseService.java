package com.zhunzhong.demo.clickhouse;


import java.util.Properties;

/**
 * @author: zhunzhong
 * @date: 2023-02-07 09:36
 * @description: todo
 */
public class ClickHouseService {

    public void initConnection(){
        String url = "jdbc:ch:https://play.clickhouse.com:443";
        Properties properties = new Properties();
        properties.setProperty("user", "explorer");
        properties.setProperty("password", "");
    }
}
