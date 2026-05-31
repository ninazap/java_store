package com.shop.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static final String PROPERTIES_FILE = "db.properties";
    private static Connection connection;

    static {
        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            Properties props = new Properties();
            if (input == null) throw new RuntimeException("Properties file not found");
            props.load(input);
            Class.forName(props.getProperty("db.driver"));
            connection = DriverManager.getConnection(
                    props.getProperty("db.url"),
                    props.getProperty("db.user"),
                    props.getProperty("db.password")
            );
        } catch (ClassNotFoundException | IOException | SQLException e) {
            throw new RuntimeException("Failed to initialize DB connection", e);
        }
    }

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Reconnect if needed (simplified for MVP)
                Properties props = new Properties();
                try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
                    props.load(input);
                    connection = DriverManager.getConnection(props.getProperty("db.url"), props.getProperty("db.user"), props.getProperty("db.password"));
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException("Failed to reconnect to DB", e);
        }
        return connection;
    }
}
