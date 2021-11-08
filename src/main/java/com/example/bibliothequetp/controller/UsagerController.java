package com.example.bibliothequetp.controller;

import com.example.bibliothequetp.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Vector;

// Controller gérants les clients de la bibliothèques

public class UsagerController {

    public Vector<Usager> usagers;   // vector donnant les clients enregistrés dans la BDD
    int idUsager;

    public UsagerController() {
        this.usagers = new Vector<Usager>();
        try {
            Connection conn = DataBase.getConnection();
            PreparedStatement ps = conn.prepareStatement("select * from USAGER");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                idUsager = rs.getInt(1);
                Usager usager = new Usager(idUsager);
                usagers.add(usager);
            }
            conn.close();
        } catch (Exception e) {
            System.out.println(e); }
    }

    public ObservableList<Usager> banqueUsagers(){      // retourne les usagers enregistrés dans la BDD
        ObservableList<Usager> liste = FXCollections.observableArrayList();

        Iterator val = this.usagers.iterator();
        while (val.hasNext()) {
            Usager u = (Usager) val.next();
                liste.add(u);
        }
        return liste;}

    public void clientsDetails(TableView table){  // methode test pour tester l'action des boutons de l'interface
        Usager u = (Usager) table.getSelectionModel().getSelectedItem();
    }

    public void supprimerClient(TableView table){ // sert à supprimer des clients de la BDD
        Usager u = (Usager) table.getSelectionModel().getSelectedItem();
        int succes = 0;
        succes = u.supprimer();
    }

    public Vector<Object> debutModifierClient(TableView table){  // methode appelée pour modifier le client sélectionné pour modification
        Vector<Object> v = new Vector<>();
        Usager c = (Usager) table.getSelectionModel().getSelectedItem();
        int id = c.getIdUsager();
        String prenom = c.getPrenom();
        String nom = c.getNom();
        int cat = c.categorie;
        String mail = c.mail;

        v.add(id);
        v.add(prenom);
        v.add(nom);
        v.add(cat);
        v.add(mail);

        return v;  // renvoie un vector contenant les donnees utiles pour modifier un client
    }

    public int modifierClient(int id, String prenom, String nom, int cat, String mail){  // modifie un client dans la BDD
        int succes = 0;
        try {
            Connection conn = DataBase.getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE USAGER SET PRENOM = ?,NOM = ?, MAIL = ?, `ID CATEGORIE`= ? WHERE `ID USAGER` = ?;");
            ps.setString(1,prenom);
            ps.setString(2,nom);
            ps.setString(3,mail);
            ps.setInt( 4, cat);
            ps.setInt(5,id);

            succes = ps.executeUpdate();
            conn.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return succes;}
}

