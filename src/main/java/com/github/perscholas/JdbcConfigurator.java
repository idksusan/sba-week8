package com.github.perscholas;

import com.mysql.cj.jdbc.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConfigurator {
    public static void initialize() {
        // TODO - Implement JDBC registration process
        // Done
        try {
            DriverManager.registerDriver(Driver.class.newInstance());
        } catch (InstantiationException | IllegalAccessException | SQLException e1) {
            throw new Error(e1);
        }
    }
}
