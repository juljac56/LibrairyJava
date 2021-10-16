package com.example.bibliothequetp.controller;

import com.example.bibliothequetp.model.Categorie;
import com.example.bibliothequetp.model.DataBase;
import com.example.bibliothequetp.model.Usager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Vector;

public class CategorieController {
    public Vector<Categorie> categories;

    public CategorieController() {

        this.categories = new Vector<Categorie>();
        try {
            Connection conn = DataBase.getConnection();
            PreparedStatement ps = conn.prepareStatement("select `ID CATEGORIE` from CATEGORIE");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                Categorie cat = new Categorie(id);
                categories.add(cat);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ObservableList<Categorie> banqueCategorie() {      // retourne les catégories enregistrés dans la BDD
        ObservableList<Categorie> liste = FXCollections.observableArrayList();

        Iterator val = this.categories.iterator();
        while (val.hasNext()) {
            Categorie c = (Categorie) val.next();
            liste.add(c);
        }
        return liste;
    }

    public void catDetails(TableView table){
        Categorie c = (Categorie) table.getSelectionModel().getSelectedItem();
        System.out.println(c.idCategorie);
    }

}
