package com.example.bibliothequetp;

import com.example.bibliothequetp.controller.*;
import com.example.bibliothequetp.model.Livre;
import com.example.bibliothequetp.model.Usager;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicReference;


public class VoirDetails extends CycledView {

    EmpruntController controller = new EmpruntController();
    UsagerController UsagerController = new UsagerController();
    int idLivre;
    ObservableList<Usager> data;
    TableView table;

    public VoirDetails(Stage stage, int idL) {
        super(stage);
        this.idLivre = idL;
        this.table = new TableView();
        createGUI();
    }

    public void createGUI() {

        GridPane gp= new GridPane();
        gp.setPadding(new Insets(20));
        gp.setHgap(25);
        gp.setVgap(15);

        Text text = new Text();
        text.setText("Description de l'oeuvre");

        Text empruntenCours = new Text("");

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));

            try {
                Livre livre = new Livre(idLivre);
                Text tauteurs = new Text("Auteurs (prenom, nom) : " + livre.listAuteur());
                Text tTitre = new Text("Titre : "+ livre.getTitre());
                Text tAnnee = new Text("Annee : "+ String.valueOf(livre.getAnnee()));
                Text tEditeur = new Text("Editeur : "+ livre.getEditeur());
                Text tMots = new Text("Mot(s)-clé(s) : "+ livre.listeMots());

                vbox.getChildren().addAll( tauteurs,tTitre,tAnnee,tEditeur,tMots);

            } catch (Exception e) {
                e.printStackTrace();
            }


        Button btnR = new Button("Retour") {
            @Override
            public void fire() {
                goReserverLivreAPage(stage);
            }
        };

        //gp.add(btnR,1,0);

        //gp.add(empruntenCours,2,2);
        vbox.getChildren().addAll( btnR);
        getChildren().addAll(vbox);
        //getChildren().add(gp);
    }

    }


