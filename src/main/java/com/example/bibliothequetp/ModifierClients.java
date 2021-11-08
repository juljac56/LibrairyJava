package com.example.bibliothequetp;

import com.example.bibliothequetp.controller.*;
import com.example.bibliothequetp.model.Usager;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Vector;


public class ModifierClients extends CycledView {

    UsagerController controller = new UsagerController();
    String nom;
    String prenom;
    String mail;
    int cat;
    int id;
    Usager u;
    public ModifierClients(Stage stage,int id,   String prenom,String nom,int cat, String mail, Usager u) {
        super(stage);
        this.id = id;
        this.cat = cat;
        this.mail = mail;
        this.nom = nom;
        this.u = u;
        this.prenom = prenom;
        createGUI();
    }

    public void createGUI() {

        GridPane gp= new GridPane();
        gp.setPadding(new Insets(20));
        gp.setHgap(25);
        gp.setVgap(15);

        Text text = new Text();
        text.setText("Modifier les informations du client sélectionné");

        Text creationOk = new Text("Client modifié avec succès");
        Text creationFail = new Text("Il semble y avoir une erreur lors de la modification");

        Button btnR = new Button("Retour") {
            @Override
            public void fire() {
                goGererClientPage(stage, u);
            }
        };

        TextField tfPrenom = new TextField();
        tfPrenom.setText(String.valueOf(prenom));
        TextField tfNom = new TextField();
        tfNom.setText(String.valueOf(nom));
        TextField tfMail = new TextField();
        tfMail.setText(String.valueOf(mail));
        TextField tfCat = new TextField();
        tfCat.setText(String.valueOf(cat));

        Label labelPrenom = new Label("Prenom ");
        Label labelNom = new Label("Nom ");
        Label labelCat = new Label("Catégorie");
        Label labelMail = new Label("Email ");

        Button buttonCreation = new Button("Modifier Client");

        buttonCreation.setOnAction(action -> {

            cat = Integer.valueOf(tfCat.getText());
            int succes = controller.modifierClient(id,tfPrenom.getText(), tfNom.getText(),  cat, tfMail.getText());

            if (succes ==1){gp.add(creationOk,0,5);
            }
            else{gp.add(creationFail,0,5);}
        });

        gp.add(text, 2, 0);
        gp.add(btnR, 0,0);
        gp.add(buttonCreation, 4,0);

        gp.add(tfNom, 0,1);
        gp.add(tfPrenom, 0,2);
        gp.add(tfCat, 0,3);
        gp.add(tfMail, 0,4);

        gp.add(labelNom, 1, 1);
        gp.add(labelPrenom, 1, 2);
        gp.add(labelCat, 1, 3);
        gp.add(labelMail, 1, 4);

        getChildren().add(gp);
    }
}

