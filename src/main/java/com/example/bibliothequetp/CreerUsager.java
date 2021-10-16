package com.example.bibliothequetp;

import com.example.bibliothequetp.controller.CreationController;
import com.example.bibliothequetp.controller.CycledView;
import com.example.bibliothequetp.controller.MainController;
import com.example.bibliothequetp.controller.UsagerController;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Vector;


public class CreerUsager extends CycledView {

    CreationController controller = new CreationController();
    UsagerController controllerUsager = new UsagerController();
    CycledView consulterClients;

    public CreerUsager(CycledView next, Stage stage, CycledView retour) {
        super(next, stage, retour);
        createGUI();
    }

    public void createGUI() {

        consulterClients = new ConsulterClients(this, this.stage, next);

        GridPane gp= new GridPane();
        gp.setPadding(new Insets(20));
        gp.setHgap(25);
        gp.setVgap(15);

        Text text = new Text();
        text.setText("Créer un Client ");

        Text creationOk = new Text("Client créé avec succès");
        Text creationFail = new Text("Il semble y avoir une erreur lors de la creation");

        Button btnR = new Button("Retour") {
            @Override
            public void fire() {
                callNext(consulterClients);
            }
        };

        TextField tfNom = new TextField();
        TextField tfPrenom = new TextField();
        TextField tfMail = new TextField();
        TextField tfCat = new TextField();


        Label labelNom = new Label("Nom");
        Label labelPrenom = new Label("Prenom");
        Label labelMail = new Label("Mail");
        Label labelCat = new Label("Categorie");

        Button buttonCreation = new Button("Créer Client");

        buttonCreation .setOnAction(action -> {
            int idCat = Integer.parseInt(tfCat.getText());
            int succes = controller.creationClient(tfNom.getText(), tfPrenom.getText(), tfMail.getText(),idCat);

            if (succes ==1){gp.add(creationOk,0,5);
                tfNom.setText("");
                tfPrenom.setText("");
                tfMail.setText("");
                tfCat.setText("");
                retour.data = controllerUsager.banqueUsagers();
                consulterClients = new ConsulterClients(this, this.stage, next);
            }
            else{gp.add(creationFail,0,5);}
        });

        gp.add(text, 2, 0, 2, 1);
        gp.add(btnR, 0,0);
        gp.add(buttonCreation, 4,0);

        gp.add(tfNom, 0,1);
        gp.add(tfPrenom, 0,2);
        gp.add(tfMail, 0,3);
        gp.add(tfCat, 0,4);

        gp.add(labelNom, 1, 1);
        gp.add(labelPrenom, 1, 2);
        gp.add(labelMail, 1, 3);
        gp.add(labelCat, 1, 4);

        getChildren().add(gp);

    }
}
