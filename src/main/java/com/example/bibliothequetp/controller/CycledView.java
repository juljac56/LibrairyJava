package com.example.bibliothequetp.controller;

import com.example.bibliothequetp.*;
import com.example.bibliothequetp.model.Usager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

// Sert à naviguer de page en page.

public abstract class CycledView extends StackPane {
    public CycledView(Stage stage) {
        this.stage = stage;
    }

    public abstract void createGUI();  // sert à créer l'interface graphique

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

    public void goCreerClientPage(Stage stage) {
        CycledView n = new CreerUsager(stage);
        getScene().setRoot(n);
    }

    public void goModifierCat(Stage stage, int num, int nb, int duree) {
        CycledView n = new ModifierCat(stage, num, duree, nb);
        getScene().setRoot(n);
    }

    public void goModifierLivre(Stage stage, int id, String titre, int annee, String mot1, String mot2, String mot3, String mot4, String mot5, int idOeuvre) {
        CycledView n = new ModifierLivre(stage, id, titre, annee, mot1, mot2 , mot3, mot4, mot5, idOeuvre);
        getScene().setRoot(n);
    }

    public void goModifierClient(Stage stage, int id, String prenom,String nom, int cat, String mail) {
        CycledView n = new ModifierClients(stage, id, prenom, nom , cat, mail);
        getScene().setRoot(n);
    }

    public void goConsulterEmprunt(Stage stage) {
        CycledView n = new ConsulterEmprunts(stage);
        getScene().setRoot(n);
    }

    public void goReserverLivreC(Stage stage) {
        CycledView n = new ReserverLivreC(stage);
        getScene().setRoot(n);
    }

    public void goHistoriqueEmpruntUsager(Stage stage) {
        CycledView n = new HistoriqueEmpruntUsager(stage);
        getScene().setRoot(n);
    }

    public void goCreerCategoriePage(Stage stage) {
        CycledView n = new Creercat(stage);
        getScene().setRoot(n);
    }

    public void goAjouterLR(Stage stage, int id) {
        CycledView n = new CreerLR(stage, id);
        getScene().setRoot(n);
    }

    public void goCreerEmprunt(Stage stage, int id){
        CycledView n = new CreerEmprunt(stage, id);
        getScene().setRoot(n);
    }

    public void goVoirDetails(Stage stage, int id){
        CycledView n = new VoirDetails(stage, id);
        getScene().setRoot(n);
    }

    public Stage stage;
    public CycledView retour;
}
