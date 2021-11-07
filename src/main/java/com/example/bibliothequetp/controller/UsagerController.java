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

public class UsagerController {

    public Vector<Usager> usagers;   // tous les emprunts passés et en cours
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
            System.out.println(e);
        }

    }


    public ObservableList<Usager> banqueUsagers(){      // retourne les usagers enregistrés dans la BDD
        ObservableList<Usager> liste = FXCollections.observableArrayList();

        Iterator val = this.usagers.iterator();
        while (val.hasNext()) {
            Usager u = (Usager) val.next();
                liste.add(u);

        }
        return liste;
    }

    public void clientsDetails(TableView table){
        Usager u = (Usager) table.getSelectionModel().getSelectedItem();
        System.out.println(u.nom);
    }

    public void supprimerClient(TableView table){
        Usager u = (Usager) table.getSelectionModel().getSelectedItem();
        int succes = 0;
        succes = u.supprimer();

    }

    public Vector<Object> debutModifierClient(TableView table){
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

        return v;
    }

    public int modifierClient(int id, String prenom, String nom, int cat, String mail){
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
            System.out.println("succes "+succes);

            conn.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return succes;
    }


}

