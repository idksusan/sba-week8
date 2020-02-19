package com.github.perscholas;

import java.sql.SQLException;

public class MainApplication {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        JdbcConfigurator.initialize();
        Runnable sms = new SchoolManagementSystem();
        sms.run();
    }
}
