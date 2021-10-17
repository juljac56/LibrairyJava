package com.example.bibliothequetp.controller;

import com.example.bibliothequetp.model.DataBase;
import com.example.bibliothequetp.model.Emprunt;
import com.example.bibliothequetp.model.Livre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Vector;

public class EmpruntController {

    public Vector<Emprunt> emprunts;   // tous les emprunts passés et en cours

    public EmpruntController() {
        this.emprunts = new Vector<Emprunt>();
        try {
            Connection conn = DataBase.getConnection();
            PreparedStatement ps = conn.prepareStatement("select * from EMPRUNT");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String dateDebut = rs.getString(1);
                String dateFin = rs.getString(2);
                int idSupport = rs.getInt(3);
                int idUsager = rs.getInt(4);

                Emprunt emprunt = new Emprunt(dateDebut, dateFin, idSupport, idUsager);
                emprunts.add(emprunt);
            }
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public ObservableList<Emprunt> listeEmpruntsActuels() {   // fonction qui renvoie les emprunts en cours, pas encore terminés

        ObservableList<Emprunt> liste = FXCollections.observableArrayList();

        Iterator val = this.emprunts.iterator();
        while (val.hasNext()) {
            Emprunt e = (Emprunt) val.next();
            if (e.empruntEnCours()) {
                liste.add(e);
            }
        }
        return liste;
    }

    public Vector<Emprunt> historiqueEmprunts(){  // retourne l'historique des tous les emprunts actuels et passés
        return this.emprunts;
    }

    public ObservableList<Emprunt> empruntUsager(int id){      // retourne l'historique d'emprunt pour un usager donné
        ObservableList<Emprunt> liste = FXCollections.observableArrayList();

        Iterator val = this.emprunts.iterator();
        while (val.hasNext()) {
            Emprunt e = (Emprunt) val.next();
            if (e.idUsager == id) {
                liste.add(e);
            }

        }
        return liste;
    }

    public ObservableList<Emprunt> empruntUsagerActuel(int id){      // retourne les emprunts en cours pour un usager donné
        ObservableList<Emprunt> liste = FXCollections.observableArrayList();

        Iterator val = this.emprunts.iterator();
        while (val.hasNext()) {
            Emprunt e = (Emprunt) val.next();
            if (e.idUsager == id && e.empruntEnCours()) {
                liste.add(e);
            }

        }
        return liste;
    }



}
