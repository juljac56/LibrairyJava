package com.example.bibliothequetp.controller;

import com.example.bibliothequetp.model.Categorie;
import com.example.bibliothequetp.model.DataBase;
import com.example.bibliothequetp.model.ListeRouge;
import com.example.bibliothequetp.model.Usager;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

// Controller pour gérer la liste rouge, ie, la liste interdisant les emprunts de certains utilisateurs pour une certaine durée

public class ListeRougeController {

    public static ObservableList<ListeRouge> listeRouge() {  // retourne l'historique de liste rouge
        ObservableList<ListeRouge> liste = ListeRouge.listeRougeTotal();
        return liste;
    }

    public void ListeRDetails(TableView table){  // méthode test
        ListeRouge lr = (ListeRouge) table.getSelectionModel().getSelectedItem();
        System.out.println(lr.nom);
    }

    public Integer debutAjoutLR(TableView table){  // Début d'ajout d'un client dans la LR après qu'il ait été sélectionné
        Usager u = (Usager) table.getSelectionModel().getSelectedItem();
        Integer idUsager = u.getIdUsager();
        return idUsager;
    }

    public int ajoutLR(int id, String debut, String fin){  // Ajout d'un client dans la liste rouge dans la BDD
        int succes = 0;
        try {
            Connection conn = DataBase.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO LISTEROUGE(`DATE DEBUT`,`DATE FIN`) values (?,?)");
            ps.setString(1,debut);
            ps.setString(2,fin);
            succes = ps.executeUpdate();

            PreparedStatement ps0 = conn.prepareStatement("Select `ID LISTEROUGE` FROM LISTEROUGE  WHERE `DATE DEBUT` = ? AND `DATE FIN` = ?");
            ps0.setString(1,debut);
            ps0.setString(2,fin);
            ResultSet rs0 = ps0.executeQuery();
            int idLR = rs0.getInt(1);


            PreparedStatement ps1 = conn.prepareStatement("INSERT INTO `ListeRouge/Usager` (`ID Liste`,`ID USAGER`) values (?,?)");
            ps1.setInt(1,idLR);
            ps1.setInt(2,id);
            succes = ps1.executeUpdate();

            conn.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return succes;
    }

    public int supprimerLR(TableView table){  // Enlever un client de la liste rouge
        int succes;
        ListeRouge lr = (ListeRouge) table.getSelectionModel().getSelectedItem();
        succes = lr.supprimer();
        return succes;
    }
}
