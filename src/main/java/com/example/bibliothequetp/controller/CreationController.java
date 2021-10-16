package com.example.bibliothequetp.controller;

import com.example.bibliothequetp.model.DataBase;
import com.example.bibliothequetp.model.Emprunt;
import com.example.bibliothequetp.model.Livre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Vector;

public class CreationController {


    public int creationClient(String nom, String prenom, String mail, Integer idCat) {
        int status = 0;
        try {
            Connection conn = DataBase.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO USAGER (NOM, PRENOM, MAIL , `ID CATEGORIE`) VALUES(?,?,?,?)");
            ps.setString(1,nom);
            ps.setString(2,prenom);
            ps.setString(3,mail);
            ps.setInt(4,idCat);
            status = ps.executeUpdate();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }

}

