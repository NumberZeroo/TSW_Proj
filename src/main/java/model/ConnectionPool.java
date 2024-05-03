package model;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/tswproj";

    private static final String USERNAME = "root";

    private static final String PASSWORD = "e.amoreroot";

    private static final int INITIAL_POOL_SIZE = 5;


    public ConnectionPool(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch(Exception e){
            System.out.println("Connessione fallita");
        }
    }

    private static List<Connection> connectionPool = new ArrayList<>();

    public static synchronized Connection getConnection() {
            try {
                Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                return conn;
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
                return null;
            }
    }

    public static synchronized void releaseConnection(Connection connection) {
        if (connection != null) {
            //connectionPool.add(connection);
        }
    }
}