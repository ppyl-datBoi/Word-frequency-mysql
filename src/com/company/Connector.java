package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class Connector {
    //String url = "jdbc:mysql://www.db4free.net:3306/freq_admin";
    //String username = "freq_admin";
    //String password = "freq_admin";
    private static String url = "jdbc:mysql://localhost:3306?serverTimezone=Europe/Moscow&useSSL=false";
    private static String username = "root";
    private static String password = "11111111";

    public static void executeQuery (String query){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                System.out.println("Connection to Store DB succesfull!");
                Statement statement = conn.createStatement();
                System.out.println(query);
                statement.executeUpdate(query);
            }
        }
        catch(Exception ex){
            System.out.println("Connection failed...");

            System.out.println(ex);
        }

    }
    public static Map getDataFromBD (String query){
        Map<Integer, String> result = new HashMap<>();;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                System.out.println("Connection to Store DB succesfull!");
                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery(query);
                while(rs.next()) {
                    result.put(rs.getInt("id"), rs.getString("path"));
                    String filesPath = rs.getString("path");
                    System.out.println(filesPath);
                }
            }
        }
        catch(Exception ex){
            System.out.println("Connection failed...");

            System.out.println(ex);
        }
        return result;
    }
}
