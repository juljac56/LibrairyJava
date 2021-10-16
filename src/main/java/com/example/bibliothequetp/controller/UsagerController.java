package com.example.bibliothequetp.controller;

import com.example.bibliothequetp.model.DataBase;
import com.example.bibliothequetp.model.Emprunt;
import com.example.bibliothequetp.model.Livre;
import com.example.bibliothequetp.model.Usager;
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


}

