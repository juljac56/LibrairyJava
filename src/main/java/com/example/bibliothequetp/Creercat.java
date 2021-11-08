package com.example.bibliothequetp;

import com.example.bibliothequetp.controller.CreationController;
import com.example.bibliothequetp.controller.CycledView;
import com.example.bibliothequetp.controller.MainController;
import com.example.bibliothequetp.controller.UsagerController;
import com.example.bibliothequetp.model.Usager;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Vector;

public class Creercat extends CycledView {

    CreationController controller = new CreationController();
    UsagerController controllerUsager = new UsagerController();
    Usager u;

    public Creercat(Stage stage, Usager u) {
        super(stage);
        this.u = u;
        createGUI();
    }

    public void createGUI() {

        GridPane gp= new GridPane();
        gp.setPadding(new Insets(20));
        gp.setHgap(25);
        gp.setVgap(15);

        Text text = new Text();
        text.setText("Créer une Catégorie ");

        Text creationOk = new Text("catégorie créée avec succès");
        Text creationFail = new Text("Il semble y avoir une erreur lors de la creation");

        Button btnR = new Button("Retour") {
            @Override
            public void fire() {
                goCatPage(stage, u);
            }
        };

        TextField tfDuree = new TextField();
        TextField tfNbMax = new TextField();

        Label labelDuree = new Label("Durée d'emprunt");
        Label labelNbMax = new Label("Nombre maxi d'emprunts simultanés");

        Button buttonCreation = new Button("Créer Catégorie");

        buttonCreation.setOnAction(action -> {

            Integer duree = Integer.valueOf(tfDuree.getText());
            Integer nb = Integer.valueOf(tfNbMax.getText());
            int succes = controller.creationCategorie( duree, nb);

            if (succes ==1){gp.add(creationOk,0,5);
                tfDuree.setText("");
                tfNbMax.setText("");
            }
            else{gp.add(creationFail,0,5);}
        });

        gp.add(text, 2, 0);
        gp.add(btnR, 0,0);
        gp.add(buttonCreation, 4,0);
        gp.add(tfDuree, 0,2);
        gp.add(tfNbMax, 0,3);
        gp.add(labelNbMax, 1, 2);
        gp.add(labelDuree, 1, 3);

        getChildren().add(gp);

    }
}

