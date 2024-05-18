package com.banksystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private final String IP = "localhost:3306";
    private final String DATABASE = "banksystem";
    private final String USERNAME = "root";
    private final String PASSWORD = "12345678";
    private Connection connection;

    public static DatabaseConnection getConnection() {
        return new DatabaseConnection();
    }

    private DatabaseConnection() {
        loadDriver(); // Load the MySQL JDBC driver when creating the DatabaseConnection instance
        this.connection = this.initConnection();
    }

    private void loadDriver() {
        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to load MySQL JDBC driver: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Connection initConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://" + IP + "/" + DATABASE,
                    USERNAME,
                    PASSWORD
            );
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
            return null;
        }
    }

    public Connection getConnectionInstance() {
        return this.connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("Error closing database connection: " + e.getMessage());
        }
    }
}