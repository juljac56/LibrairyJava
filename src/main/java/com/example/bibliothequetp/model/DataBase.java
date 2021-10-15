package com.example.bibliothequetp.model;

import java.sql.*;

public class DataBase {
    public static Connection getConnection(){
        Connection conn = null;
        try{conn = DriverManager.getConnection("jdbc:sqlite:BDD_TD1.db");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }}
