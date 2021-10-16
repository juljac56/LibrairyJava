package com.example.bibliothequetp.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class Categorie {  // une catégorie c'est un numéro (id), un nb max d'emprunt, une durée maximale (en jours) pour chaque emprunt

    public int idCategorie;
    public int nbMax;
    public int dureeMax;

    public Categorie(int id){
            this.idCategorie = id;
        try {
            Connection conn = DataBase.getConnection();
            PreparedStatement ps0 = conn.prepareStatement("select DUREE, `NB MAX` from CATEGORIE WHERE `ID CATEGORIE` = ?");
            ps0.setInt(1,id);

            ResultSet rs0 = ps0.executeQuery();
            this.dureeMax = rs0.getInt(1);
            this.nbMax = rs0.getInt(2);
        }

        catch(Exception e){System.out.println(e);}
        }

    public String getIdCategorie() {
        String alert = "transformer int en string";
        return alert;
    }

    public int getDureeMax() {
        return dureeMax;
    }

    public int getNbMax() {
        return nbMax;
    }
}
