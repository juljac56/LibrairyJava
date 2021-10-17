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

    public int creationLivre(String titre, Integer annee, Integer ISBN, String mot1, String editeur, String prenom,String nom) {

        Vector<String> auteurn = new Vector<>();
        auteurn.add(nom);
        Vector<String> auteurp = new Vector<>();
        auteurp.add(prenom);

        int status = 0;
        status = Livre.ajoutlivreBDD(titre, annee, ISBN, auteurp, auteurn, editeur, mot1);


        return status;
    }

    public int creationCategorie( Integer duree, Integer nbMax) {
        int status = 0;
        status = Categorie.ajoutCatBDD(duree, nbMax);
        return status;
    }

    public void supprimerlivre(TableView table){
        Livre l = (Livre) table.getSelectionModel().getSelectedItem();
        int livreId = l.getId();
        try {
            Livre livre = new Livre(livreId);
            livre.supprimer();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void supprimerClient(TableView table){
        Usager u = (Usager) table.getSelectionModel().getSelectedItem();
        int idUsager = u.getIdUsager();
        try {
            Usager usager = new Usager(idUsager);
            usager.supprimer();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

