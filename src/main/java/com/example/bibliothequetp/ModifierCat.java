package com.example.bibliothequetp;

import com.example.bibliothequetp.controller.*;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Vector;


public class ModifierCat extends CycledView {

    CategorieController controller = new CategorieController();
    int num;
    int duree;
    int nb;
    public ModifierCat(Stage stage, int num, int duree, int nb) {
        super(stage);
        this.nb = nb;
        this.duree = duree;
        this.num = num;
        createGUI();
    }

    public void createGUI() {

        GridPane gp= new GridPane();
        gp.setPadding(new Insets(20));
        gp.setHgap(25);
        gp.setVgap(15);

        Text text = new Text();
        text.setText("Modifier la catégorie sélectionnée");

        Text creationOk = new Text("Catégorie modifiée avec succès");
        Text creationFail = new Text("Il semble y avoir une erreur lors de la modification");

        Button btnR = new Button("Retour") {
            @Override
            public void fire() {
                goCatPage(stage);
            }
        };

        TextField tfNum = new TextField();
        tfNum.setText(String.valueOf(num));
        TextField tfDuree = new TextField();
        tfDuree.setText(String.valueOf(duree));
        TextField tfNbMax = new TextField();
        tfNbMax.setText(String.valueOf(nb));

        Label labelNum = new Label("Numéro de Categorie");
        Label labelDuree = new Label("Durée d'emprunt");
        Label labelNbMax = new Label("Nombre maxi d'emprunts simultanés");

        Button buttonCreation = new Button("Modifier Catégorie");

        buttonCreation.setOnAction(action -> {

            duree = Integer.valueOf(tfDuree.getText());
            nb = Integer.valueOf(tfNbMax.getText());
            int succes = controller.modifierCat(num, duree, nb);

            if (succes ==1){gp.add(creationOk,0,5);
            }
            else{gp.add(creationFail,0,5);}
        });

        gp.add(text, 2, 0);
        gp.add(btnR, 0,0);
        gp.add(buttonCreation, 4,0);

        gp.add(tfNum, 0,1);
        gp.add(tfDuree, 0,2);
        gp.add(tfNbMax, 0,3);

        gp.add(labelNum, 1, 1);
        gp.add(labelNbMax, 1, 2);
        gp.add(labelDuree, 1, 3);

        getChildren().add(gp);
    }
}
