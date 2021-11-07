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


public class ModifierLivre extends CycledView {

    EmpruntController controller = new EmpruntController();
    String titre;
    String mot1;
    String mot2;
    String mot3;
    String mot4;
    String mot5;
    int idOeuvre;
    int annee;
    int id;
    public ModifierLivre(Stage stage, int id, String titre,int annee , String mot1, String mot2 , String mot3, String mot4, String mot5, int idOeuvre) {
        super(stage);
        this.id = id;
        this.annee =annee;
        this.mot1 = mot1;
        this.mot2 =mot2;
        this.mot3 = mot3;
        this.mot4 = mot4;
        this.mot5 = mot5;
        this.titre = titre;
        this.idOeuvre =idOeuvre;
        createGUI();
    }

    public void createGUI() {

        GridPane gp= new GridPane();
        gp.setPadding(new Insets(20));
        gp.setHgap(25);
        gp.setVgap(15);

        Text text = new Text();
        text.setText("Modifier les informations du livre sélectionné");

        Text creationOk = new Text("Livre modifié avec succès");
        Text creationFail = new Text("Il semble y avoir une erreur lors de la modification");

        Button btnR = new Button("Retour") {
            @Override
            public void fire() {
                goReserverLivreAPage(stage);
            }
        };

        TextField tfTitre = new TextField();
        tfTitre.setText(String.valueOf(titre));
        TextField tfAnnee = new TextField();
        tfAnnee.setText(String.valueOf(annee));
        TextField tfMot1 = new TextField();
        tfMot1.setText(String.valueOf(mot1));
        TextField tfMot2= new TextField();
        tfMot2.setText(String.valueOf(mot2));
        TextField tfMot3 = new TextField();
        tfMot3.setText(String.valueOf(mot3));
        TextField tfMot4 = new TextField();
        tfMot4.setText(String.valueOf(mot4));
        TextField tfMot5 = new TextField();
        tfMot5.setText(String.valueOf(mot5));



        Label labelTitre = new Label("Titre");
        Label labelAnnee = new Label("Annee");
        Label labelMot1 = new Label("Mot1");
        Label labelMot5 = new Label("Mot5");
        Label labelMot2 = new Label("Mot2");
        Label labelMot3 = new Label("Mot3");
        Label labelMot4 = new Label("Mot4");

        Button buttonCreation = new Button("Modifier Livre");

        buttonCreation.setOnAction(action -> {

            annee = Integer.valueOf(tfAnnee.getText());
            int succes = controller.modifierLivre(id,tfTitre.getText(),tfMot1.getText(),tfMot2.getText(),tfMot3.getText(),tfMot4.getText(),tfMot5.getText(),annee, idOeuvre);

            if (succes ==1){gp.add(creationOk,0,5);
            }
            else{gp.add(creationFail,0,5);}
        });

        gp.add(text, 2, 0);
        gp.add(btnR, 0,0);
        gp.add(buttonCreation, 4,0);

        gp.add(tfTitre, 0,1);
        gp.add(tfAnnee, 0,2);
        gp.add(tfMot1, 0,3);
        gp.add(tfMot2, 0,4);
        gp.add(tfMot3, 0,5);
        gp.add(tfMot4, 0,6);
        gp.add(tfMot5, 0,7);

        gp.add(labelTitre, 1, 1);
        gp.add(labelAnnee, 1, 2);
        gp.add(labelMot1, 1, 3);
        gp.add(labelMot2, 1, 4);
        gp.add(labelMot3, 1, 5);
        gp.add(labelMot4, 1, 6);
        gp.add(labelMot5, 1, 7);

        getChildren().add(gp);
    }
}


