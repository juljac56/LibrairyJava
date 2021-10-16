package com.example.bibliothequetp.controller;

import com.example.bibliothequetp.model.ListeRouge;
import com.example.bibliothequetp.model.Usager;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class ListeRougeController {

    public static ObservableList<ListeRouge> listeRouge() {  // retourne l'historique de liste rouge pour un usager donné (son id)
        ObservableList<ListeRouge> liste = ListeRouge.listeRougeTotal();
        return liste;

    }

    public void ListeRDetails(TableView table){
        ListeRouge lr = (ListeRouge) table.getSelectionModel().getSelectedItem();
        System.out.println(lr.nom);
    }

}
