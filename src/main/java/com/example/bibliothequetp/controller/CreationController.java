package com.example.bibliothequetp.controller;

import com.example.bibliothequetp.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.TableView;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

// Controller servant à l'ajout et au retrait de certains types de données dans la BDD

public class CreationController {

    public int creationClient(String nom, String prenom, String mail, String username, String password, Integer idCat) {  // sert à ajouter de nouveaux clients dans la BDD
        int status = 0;
        try {
            Connection conn = DataBase.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO USAGER (NOM, PRENOM, MAIL , `ID CATEGORIE`, USERNAME, PASSWORD) VALUES(?,?,?,?,?,?)");
            ps.setString(1, nom);
            ps.setString(2, prenom);
            ps.setString(3, mail);
            ps.setInt(4, idCat);
            ps.setString(5, username);
            ps.setString(6, password);
            status = ps.executeUpdate();
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }

    public int creationLivre(String titre, Integer annee, Integer ISBN, String mot1, String mot2, String mot3, String mot4, String mot5, String editeur, String prenom, String nom) {

        Vector<String> auteurn = new Vector<>();
        Vector<String> auteurp = new Vector<>();

        String[] tableauPrenom = prenom.split(",");  // Les prénoms des auteurs sont séparés par une , dans le cas ou il y en a plusieurs
        String[] tableauNom = nom.split(",");  // Les noms des auteurs sont séparés par une , dans le cas ou il y en a plusieurs

        for (int i = 0; i <= tableauNom.length - 1; i++) {
            auteurn.add(tableauNom[i]);
            auteurp.add(tableauPrenom[i]);
        }
        int status = 0;
        status = Livre.ajoutlivreBDD(titre, annee, ISBN, auteurp, auteurn, editeur, mot1, mot2, mot3, mot4, mot5);
        return status;
    }

    public int creationCategorie(Integer duree, Integer nbMax) {  // Ajout d'une nouvelle catégorie dans une BDD
        int status = 0;
        status = Categorie.ajoutCatBDD(duree, nbMax);
        return status;
    }

    public void supprimerlivre(TableView table) {  // Retirer un support de la BDD
        Livre l = (Livre) table.getSelectionModel().getSelectedItem();
        int livreId = l.getId();
        try {
            Livre livre = new Livre(livreId);
            livre.supprimer();
        } catch (Exception e) {
            e.printStackTrace();
        }}

    public void supprimerClient(TableView table) {  // Retirer un client de la BDD
        Usager u = (Usager) table.getSelectionModel().getSelectedItem();
        int idUsager = u.getIdUsager();
        try {
            Usager usager = new Usager(idUsager);
            usager.supprimer();
        } catch (Exception e) {
            e.printStackTrace();
        }}
}

