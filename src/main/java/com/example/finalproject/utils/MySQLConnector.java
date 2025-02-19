package com.example.finalproject.utils;
import com.example.finalproject.configs.EnvConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLConnector {
    String url = EnvConfig.get("DB_HOST");
    private static final String URL = EnvConfig.get("DB_HOST");
    private static final String USER = EnvConfig.get("DB_USER");
    private static final String PASSWORD = EnvConfig.get("DB_PASSWORD");

    Connection connection;
    public static MySQLConnector instance = null;
    private MySQLConnector(){

    }
    public static MySQLConnector getInstance(){
        if (instance ==null){
            instance = new MySQLConnector();
        }
        return instance;
    }
    public boolean Connect(String username, String password){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connect successful.........");
//            System.out.println("Database Host: " + EnvConfig.get("DB_HOST"));
//            System.out.println("Database User: " + EnvConfig.get("DB_PASSWORD"));
            return true;
        } catch (Exception  ex) {
//            System.out.println(ex);
//            System.out.println("Connect fail..........");
        }
        return false;
    }
    public boolean queryUpdate(String sql){
        if (connection != null){
            try{
                connection.createStatement().executeUpdate(sql);
                return true;
            }catch (Exception ex){
                System.out.println(ex);
                return false;
            }

        }
        return false;
    }
    public ResultSet queryResults(String sql){
        if (connection != null){
            try{
                return connection.createStatement().executeQuery(sql);
            }catch (Exception ex){
                System.out.println(ex);
            }

        }
        return null;
    }
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
