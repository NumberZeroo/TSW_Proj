package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/tswProj"; //todo
    private static final String USERNAME = ""; //todo
    private static final String PASSWORD = ""; //todo
    private static final int INITIAL_POOL_SIZE = 5;

    private static List<Connection> connectionPool = new ArrayList<>();

    static {
        try {
            for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
                Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                connectionPool.add(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized Connection getConnection() {
        if (connectionPool.isEmpty()) {
            try {
                Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                return connection;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        return connectionPool.remove(connectionPool.size() - 1);
    }

    public static synchronized void releaseConnection(Connection connection) {
        if (connection != null) {
            connectionPool.add(connection);
        }
    }
}