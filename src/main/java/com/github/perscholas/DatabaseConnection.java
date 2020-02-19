package com.github.perscholas;

import java.sql.*;

public enum DatabaseConnection {
    MARIADB;

    public Connection getConnection() {
        return getConnection(name().toLowerCase());
    }

    public Connection getConnection(String dbVendor) {
        String username = "root";
        String password = "";
        String database = "class_management";
        String url = "jdbc:" + dbVendor + "://127.0.0.1/";
        try {
            Connection connection = DriverManager.getConnection(url + database, username, password);
            return connection;
        } catch (SQLException e) {
            try {
                Connection connection = DriverManager.getConnection(url, username, password);
                return connection;
            } catch (SQLException err) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public void executeStatement(String sqlStatement) {
        try {
            Statement statement = getScrollableStatement();
            statement.execute(sqlStatement);
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    public ResultSet executeQuery(String sqlQuery) {
        try {
            Statement statement = getScrollableStatement();
            return statement.executeQuery(sqlQuery);
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    private Statement getScrollableStatement() {
        int resultSetType = ResultSet.TYPE_SCROLL_INSENSITIVE;
        int resultSetConcurrency = ResultSet.CONCUR_READ_ONLY;
        try {
            return getConnection().createStatement(resultSetType, resultSetConcurrency);
        } catch (SQLException e) {
            throw new Error(e);
        }
    }
}
