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


// fichier servant de controller pour la gestion des catégories dans la bibliothèque

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
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ObservableList<Categorie> banqueCategorie() {      // retourne les catégories enregistrés dans la BDD
        ObservableList<Categorie> liste = FXCollections.observableArrayList();
        Iterator val = this.categories.iterator();
        while (val.hasNext()) {
            Categorie c = (Categorie) val.next();
            liste.add(c);}
        return liste;}

    public void catDetails(TableView table){
        Categorie c = (Categorie) table.getSelectionModel().getSelectedItem();
    }

    public Vector<Integer> debutModifierCat(TableView table){  // fonction appellée après qu'une catégorie a été sélectionné pour modification
        Vector<Integer> v = new Vector<>();
        Categorie c = (Categorie) table.getSelectionModel().getSelectedItem();
        int id = c.idCategorie;
        int duree = c.dureeMax;
        int nb = c.nbMax;
        v.add(id);
        v.add(nb);
        v.add(duree);
        return v;  // renvoie un vector avec les informations nécessaires de la catégorie pour la modifier
    }

    public int modifierCat(int id, int duree, int nb){  // modification de la catégorie dans la BDD
        int succes = 0;
        try {
            Connection conn = DataBase.getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE CATEGORIE SET DUREE = ?,`NB MAX`= ? WHERE `ID CATEGORIE` = ?;");
            ps.setInt(1,duree);
            ps.setInt( 2, nb);
            ps.setInt( 3, id);
            succes = ps.executeUpdate();
            conn.close();}
        catch (Exception e) {
            System.out.println(e);}
        return succes;
    }}
