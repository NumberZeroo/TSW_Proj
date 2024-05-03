package model;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/campionato" +
            "?useUnicode=true&useJDBCCompliantTimezoneShift=true" +
            "&useLegacyDatetimecode=false&serverTimezone=UTC";

    private static final String username = "root";
    private static final String pswd = "e.amoreroot";

    private Connection con;

    public ConnectionPool(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(JDBC_URL, username, pswd);
        }
        catch(Exception e){
            System.out.println("Connessione fallita");
        }
    }

    /*private static final String USERNAME = "root"; //todo
    private static final String PASSWORD = "e.amoreroot"; //todo
    private static final int INITIAL_POOL_SIZE = 5;

    private static List<Connection> connectionPool = new ArrayList<>();

    static {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }*/

    public static synchronized Connection getConnection() {
            try {
                return DriverManager.getConnection(JDBC_URL, username, pswd);
            } catch (SQLException e) {
                return null;
            }
    }

    public static synchronized void releaseConnection(Connection connection) {
        if (connection != null) {
            //connectionPool.add(connection);
        }
    }
}