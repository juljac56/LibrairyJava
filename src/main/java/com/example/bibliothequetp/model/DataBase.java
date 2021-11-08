package com.example.bibliothequetp.model;

import java.sql.*;

// Classe servant à se connecter à la BDD, grâce à JDBC

public class DataBase {
    public static Connection getConnection(){
        Connection conn = null;
        try{conn = DriverManager.getConnection("jdbc:sqlite:BDD_TD1.db");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;}}
