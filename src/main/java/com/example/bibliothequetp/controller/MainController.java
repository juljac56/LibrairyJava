package com.example.bibliothequetp.controller;

import com.example.bibliothequetp.model.DataBase;
import com.example.bibliothequetp.model.Livre;
import com.example.bibliothequetp.model.Usager;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


// controller test, utilisé pour le login notamment

public class MainController {

    public void Test(){
        System.out.println("Hello World ! ");
    }

    public void reservation(TableView table){
        Livre l = (Livre) table.getSelectionModel().getSelectedItem();
        System.out.println(l.getTitre());
    }

    public Usager login (String username, String password){  // methode pour la connexion
            Usager u = null;
        try {
            Connection conn = DataBase.getConnection();

            // on cherche (et créé si besoin) les id des auteurs du nouveau livre
            PreparedStatement ps = conn.prepareStatement("Select * FROM USAGER WHERE USERNAME = ? AND PASSWORD = ?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (!rs.isBeforeFirst() ) {
                System.out.println("pas de connexion");}
            else{
                int id  = rs.getInt(1);
                System.out.println("id pass" + id);
                u = new Usager(id);
            }
        conn.close();}
        catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return u;
    }

    public int verifierCat(Usager u){
        int page = 1;
        if (u.getCategorie() == 2){
            page = 0;
        }
        return page;
    }
}
