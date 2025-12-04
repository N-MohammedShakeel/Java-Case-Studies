package com.example.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/employee_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "MSmysql@1";

    // static {
    //     try {
    //         Class.forName("com.mysql.cj.jdbc.Driver"); 
    //     } catch (ClassNotFoundException e) {
    //         throw new RuntimeException("MySQL Driver not found!", e);
    //     }
    // }

    public static Connection getConnection() throws java.sql.SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
