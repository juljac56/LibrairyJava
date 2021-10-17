package com.example.bibliothequetp.controller;

import com.example.bibliothequetp.*;
import com.example.bibliothequetp.model.Usager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Vector;

public abstract class CycledView extends StackPane { // Choose whatever is most appropriate class
    public CycledView(Stage stage) {
        this.stage = stage;
    }

    public abstract void createGUI();


    protected void callNext(CycledView n) {
        getScene().setRoot(n);
    }

    public void goAdminPage(Stage stage) {
        CycledView n = new HomeAdmin(stage);
        getScene().setRoot(n);
    }

    public void goClientPage(Stage stage) {
        CycledView n = new HomeClient(stage);
        getScene().setRoot(n);
    }

    public void goHomePage(Stage stage) {
        CycledView n = new home(stage);
        getScene().setRoot(n);
    }

    public void goLRPage(Stage stage) {
        CycledView n = new ConsulterListeRouge(stage);
        getScene().setRoot(n);
    }

    public void goCatPage(Stage stage) {
        CycledView n = new ConsulterCategories(stage);
        getScene().setRoot(n);
    }
    public void goGererClientPage(Stage stage) {
        CycledView n = new ConsulterClients(stage);
        getScene().setRoot(n);
    }

    public void goReserverLivreAPage(Stage stage) {
        CycledView n = new ReserverLivreA(stage);
        getScene().setRoot(n);
    }

    public void goCreerLivrePage(Stage stage) {
        CycledView n = new CreerLivre(stage);
        getScene().setRoot(n);
    }

    public void goCreerClientPage(Stage stage){
        CycledView n = new CreerUsager(stage);
        getScene().setRoot(n);
    }

    public void goModifierCat(Stage stage, int num, int nb, int duree){
        CycledView n = new ModifierCat(stage, num, duree, nb);
        getScene().setRoot(n);
    }



    public Stage stage;
    public CycledView retour;

}
