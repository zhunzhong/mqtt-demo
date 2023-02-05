package com.zhunzhong.demo.tdengine;


import java.sql.*;

/**
 * @author: zhunzhong
 * @date: 2023-02-04 15:50
 * @description: todo
 */
public class DataBaseMonitor {

    private Connection conn;
    private Statement stmt;

    public DataBaseMonitor init() throws SQLException {
        if (conn == null) {
            //String jdbcURL = System.getenv("TDENGINE_JDBC_URL");
            conn = DriverManager.getConnection(TaoConstants.jdbcURL);
            stmt = conn.createStatement();
        }
        return this;
    }

    public void close() {
        try {
            stmt.close();
        } catch (SQLException e) {
        }
        try {
            conn.close();
        } catch (SQLException e) {
        }
    }

    public void prepareDatabase() throws SQLException {
        //stmt.execute("DROP DATABASE IF EXISTS test");
        stmt.execute(String.format("CREATE DATABASE IF NOT EXISTS %s", TaoConstants.dbName));
        stmt.execute(String.format("CREATE STABLE IF NOT EXISTS %s.%s (ts TIMESTAMP, current DOUBLE, voltage DOUBLE, phase DOUBLE) TAGS (location BINARY(64), groupId INT)"
                , TaoConstants.dbName, TaoConstants.sTableName));
    }

    public Long count() throws SQLException {
        if (!stmt.isClosed()) {
            ResultSet result = stmt.executeQuery(String.format("SELECT count(*) from %s.%s", TaoConstants.dbName, TaoConstants.sTableName));
            result.next();
            return result.getLong(1);
        }
        return null;
    }

    /**
     * show test.stables;
     * <p>
     * name              |      created_time       | columns |  tags  |   tables    |
     * ============================================================================================
     * meters                         | 2022-07-20 08:39:30.902 |       4 |      2 |      620000 |
     */
    public Long getTableCount() throws SQLException {
        if (!stmt.isClosed()) {
            ResultSet result = stmt.executeQuery("show test.stables");
            result.next();
            return result.getLong(5);
        }
        return null;
    }
}
