package com.example.employee_management;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

class DBConnection {

    private static final Properties props = loadProperties();

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {

            if (input == null) {
                throw new RuntimeException("Sorry, unable to find db.properties. " + "Make sure it's in src/main/resources/");
            }
            properties.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load database configuration", e);
        }
        return properties;
    }

    public static Connection getConnection() throws java.sql.SQLException {
        String url = props.getProperty("db.url");
        String username = props.getProperty("db.username");
        String password = props.getProperty("db.password");

        return DriverManager.getConnection(url, username, password);
    }
}