package com.example.bibliothequetp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import static java.lang.String.valueOf;

public class Editeur {
    Integer id;
    public String nom;

    public Editeur(int id){
        this.id = id;

        try {
            Connection conn = DataBase.getConnection();
            PreparedStatement ps= conn.prepareStatement("SELECT NOM FROM EDITEUR WHERE `ID EDITEUR` = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            this.nom = rs.getString(1);

            conn.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Vector<Editeur> tousEditeur(){
        Vector<Editeur> editeurs = new Vector<Editeur>();
        try {

            Connection conn = DataBase.getConnection();
            PreparedStatement ps= conn.prepareStatement("SELECT * FROM EDITEUR");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Editeur e = new Editeur(rs.getInt(1));
                editeurs.add(e);
            }
            conn.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return editeurs;
    }
}
