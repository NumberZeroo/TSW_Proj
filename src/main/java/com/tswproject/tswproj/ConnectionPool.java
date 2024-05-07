package com.tswproject.tswproj;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ConnectionPool {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/tswproj"
            + "?useUnicode=true&useJDBCCompliantTimezoneShift=true"
            + "&useLegacyDatetimecode=false&serverTimezone=UTC";

    private static final String USERNAME = "root";
    private static final String PASSWORD = "Emanuele@2003";
    private static List<Connection> freeDbConnections;

    // Inizializzazione (da chiamare nel context)
    public static void init(int poolSize) {
        freeDbConnections = new LinkedList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            for (int i = 0; i < poolSize; i++) {
                freeDbConnections.add(createDBConnection());
            }
        } catch (ClassNotFoundException e) {
            System.out.println("DB driver not found:"+ e.getMessage());
        } catch (SQLException e) {
            System.out.println("DB access error:"+ e.getMessage());
        }
        System.out.println("Created " + freeDbConnections.size() + " DB connections");
    }

    private static synchronized Connection createDBConnection() throws SQLException {
        // newConnection.setAutoCommit(false);
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    public static synchronized Connection getConnection() throws EmptyPoolException {
        if (freeDbConnections.isEmpty()) {
            throw new EmptyPoolException("Connection pool vuota");
        }
        System.out.println("Getting connection from pool(" + (freeDbConnections.size() - 1) + ")");
        return freeDbConnections.remove(0);
    }

    public static synchronized void releaseConnection(Connection connection) {
        if (connection != null) {
            freeDbConnections.add(connection);
            System.out.println("Releasing connection from pool(" + freeDbConnections.size() + ")");
        }
    }

    public static void releaseResources() {
        for (Connection connection : freeDbConnections) {
            try {
                connection.close();
            } catch (SQLException ignored) {}
        }
    }
}