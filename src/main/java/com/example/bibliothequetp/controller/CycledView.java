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

    public void goAdminPage(Stage stage, Usager u) {
        CycledView n = new HomeAdmin(stage,u);
        getScene().setRoot(n);
    }

    public void goClientPage(Stage stage, Usager u) {
        CycledView n = new HomeClient(stage,u);
        getScene().setRoot(n);
    }

    public void goHomePage(Stage stage, Usager u) {
        CycledView n = new home(stage,u);
        getScene().setRoot(n);
    }

    public void goLRPage(Stage stage, Usager u) {
        CycledView n = new ConsulterListeRouge(stage,u);
        getScene().setRoot(n);
    }

    public void goCatPage(Stage stage, Usager u) {
        CycledView n = new ConsulterCategories(stage,u );
        getScene().setRoot(n);
    }

    public void goGererClientPage(Stage stage, Usager u) {
        CycledView n = new ConsulterClients(stage,u);
        getScene().setRoot(n);
    }

    public void goReserverLivreAPage(Stage stage, Usager u) {
        CycledView n = new ReserverLivreA(stage, u);
        getScene().setRoot(n);
    }

    public void goCreerLivrePage(Stage stage, Usager u) {
        CycledView n = new CreerLivre(stage, u);
        getScene().setRoot(n);
    }

    public void goCreerClientPage(Stage stage, Usager u) {
        CycledView n = new CreerUsager(stage, u);
        getScene().setRoot(n);
    }

    public void goModifierCat(Stage stage, int num, int nb, int duree, Usager u) {
        CycledView n = new ModifierCat(stage, num, duree, nb, u);
        getScene().setRoot(n);
    }

    public void goModifierLivre(Stage stage, int id, String titre, int annee, String mot1, String mot2, String mot3, String mot4, String mot5, int idOeuvre, Usager u) {
        CycledView n = new ModifierLivre(stage, id, titre, annee, mot1, mot2 , mot3, mot4, mot5, idOeuvre, u);
        getScene().setRoot(n);
    }

    public void goModifierClient(Stage stage, int id, String prenom,String nom, int cat, String mail, Usager u) {
        CycledView n = new ModifierClients(stage, id, prenom, nom , cat, mail, u);
        getScene().setRoot(n);
    }

    public void goConsulterEmprunt(Stage stage, Usager u) {
        CycledView n = new ConsulterEmprunts(stage, u);
        getScene().setRoot(n);
    }

    public void goReserverLivreC(Stage stage, Usager u) {
        CycledView n = new ReserverLivreC(stage, u);
        getScene().setRoot(n);
    }

    public void goHistoriqueEmpruntUsager(Stage stage, Usager u) {
        CycledView n = new HistoriqueEmpruntUsager(stage,u);
        getScene().setRoot(n);
    }

    public void goCreerCategoriePage(Stage stage, Usager u) {
        CycledView n = new Creercat(stage, u);
        getScene().setRoot(n);
    }

    public void goAjouterLR(Stage stage, int id, Usager u) {
        CycledView n = new CreerLR(stage, id, u);
        getScene().setRoot(n);
    }

    public void goCreerEmprunt(Stage stage, int id, Usager u){
        CycledView n = new CreerEmprunt(stage, id, u);
        getScene().setRoot(n);
    }

    public void goVoirDetails(Stage stage, int id, Usager u){
        CycledView n = new VoirDetails(stage, id, u);
        getScene().setRoot(n);
    }

    public void goLoginPage(Stage stage){
        CycledView n = new Login(stage);
        getScene().setRoot(n);
    }

    public Stage stage;
    public CycledView retour;
}
