package org.example.kognitivne_nauke;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class KonekcijaBaze {
    private static final String URL = "jdbc:mysql://localhost:3306/kognitivne_nauke_dod_spec?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static Connection kreirajKonekciju(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
