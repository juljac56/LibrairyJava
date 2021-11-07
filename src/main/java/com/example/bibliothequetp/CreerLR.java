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

public class CreerLR extends CycledView {

    ListeRougeController controller = new ListeRougeController();
    int idUsager;

    public CreerLR(Stage stage, int id) {
        super(stage);
        this.idUsager = Integer.valueOf(id);
        createGUI();
    }

    public void createGUI() {

        GridPane gp= new GridPane();
        gp.setPadding(new Insets(20));
        gp.setHgap(25);
        gp.setVgap(15);

        Text text = new Text();
        text.setText("Ajout Liste Rouge ");

        Text creationOk = new Text("LR créée avec succès");
        Text creationFail = new Text("Il semble y avoir une erreur lors de la creation");

        Button btnR = new Button("Retour") {
            @Override
            public void fire() {
                goGererClientPage(stage);
            }
        };

        TextField tfDebut = new TextField();
        TextField tfFin = new TextField();

        Label labelDebut = new Label("Debut");
        Label labelFin = new Label("Fin");

        Button buttonCreation = new Button("Ajout liste rouge");

        buttonCreation.setOnAction(action -> {
            String debut = tfDebut.getText();
            String fin = tfFin.getText();

            int succes = controller.ajoutLR( this.idUsager, debut, fin );

            if (succes ==1){gp.add(creationOk,0,5);
                tfDebut.setText("");
                tfFin.setText("");
            }
            else{gp.add(creationFail,0,5);}
        });

        gp.add(text, 2, 0);
        gp.add(btnR, 0,0);
        gp.add(buttonCreation, 4,0);

        gp.add(tfDebut, 0,2);
        gp.add(tfFin, 0,3);

        gp.add(labelDebut, 1, 2);
        gp.add(labelFin, 1, 3);

        getChildren().add(gp);

    }
}

