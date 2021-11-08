package com.example.bibliothequetp.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

//Définit la classe catégorie qui permet de définir les nombres d'emprunts, la durée maximale d'emprunt

public class Categorie {  // une catégorie c'est un numéro (id), un nb max d'emprunt, une durée maximale (en jours) pour chaque emprunt

    public int idCategorie;
    public int nbMax;
    public int dureeMax;

    public Categorie(int id) {
        this.idCategorie = id;
        try {
            Connection conn = DataBase.getConnection();
            PreparedStatement ps0 = conn.prepareStatement("select DUREE, `NB MAX` from CATEGORIE WHERE `ID CATEGORIE` = ?");
            ps0.setInt(1, id);

            ResultSet rs0 = ps0.executeQuery();
            this.dureeMax = rs0.getInt(1);
            this.nbMax = rs0.getInt(2);
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public int getDureeMax() {
        return dureeMax;
    }

    public int getNbMax() {
        return nbMax;
    }

    public static int ajoutCatBDD(Integer nb, Integer dureeMax) {  // méthode d'ajout de catégorie dans une BDD
        int succes = 0;

        try {
            Connection conn = DataBase.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO CATEGORIE(DUREE, `NB MAX`) values (?,?) ");
            ps.setInt(1, dureeMax);
            ps.setInt(2, nb);
            succes = ps.executeUpdate();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);}
        return succes;
    }

    public static Vector<Categorie> tousCat(){  // renvoie un vector avec tous les ids des catégories existantes
        Vector<Categorie> categories = new Vector<Categorie>();
        try {
            Connection conn = DataBase.getConnection();
            PreparedStatement ps= conn.prepareStatement("SELECT `ID CATEGORIE` FROM CATEGORIE");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Categorie c = new Categorie(rs.getInt(1));
                categories.add(c);}
            conn.close();}

        catch (Exception e) {
            e.printStackTrace(); }
        return categories;}
}
