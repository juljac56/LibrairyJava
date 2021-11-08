package com.example.bibliothequetp;

import com.example.bibliothequetp.controller.CreationController;
import com.example.bibliothequetp.controller.CycledView;
import com.example.bibliothequetp.controller.MainController;
import com.example.bibliothequetp.controller.UsagerController;
import com.example.bibliothequetp.model.Editeur;
import com.example.bibliothequetp.model.Usager;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.util.Iterator;
import java.util.Vector;


public class CreerLivre extends CycledView {

    CreationController controller = new CreationController();
    UsagerController controllerUsager = new UsagerController();
    Usager u;

    public CreerLivre(Stage stage, Usager u) {
        super(stage);
        createGUI();
        this.u = u;
    }

    public void createGUI() {

        GridPane gp= new GridPane();
        gp.setPadding(new Insets(20));
        gp.setHgap(25);
        gp.setVgap(15);

        ComboBox boxEditeur = new ComboBox();

        Vector<Editeur> listEditeur = Editeur.tousEditeur();
        Iterator val = listEditeur.iterator();
        while (val.hasNext()){
            Editeur e = (Editeur) val.next();
            String str = e.nom;
            boxEditeur.getItems().add(str);
        }

        boxEditeur.setEditable(true);
        Text text = new Text();
        text.setText("Ajouter un livre ");

        Text creationOk = new Text("Livre créé avec succès");
        Text creationFail = new Text("Il semble y avoir une erreur lors de la creation");

        Button btnR = new Button("Retour") {
            @Override
            public void fire() {
                goReserverLivreAPage(stage, u);
            }
        };

        TextField tfTitre = new TextField();
        TextField tfAnnee = new TextField();
        TextField tfMot1 = new TextField();
        TextField tfMot2= new TextField();
        TextField tfMot3 = new TextField();
        TextField tfMot4 = new TextField();
        TextField tfMot5 = new TextField();
        TextField tfEditeur = new TextField();
        TextField tfNomE = new TextField();
        TextField tfPrenomE = new TextField();
        TextField tfISBN = new TextField();


        Label labelTitre = new Label("Titre");
        Label labelEditeur = new Label("Editeur");
        Label labelPrenomAuteur = new Label("Prénom(s) Auteur(s) (à séparer par un virgule)");
        Label labelNomAuteur = new Label("Nom(s) Auteur(s) (à séparer par un virgule)");
        Label labelAnnee = new Label("Annee");
        Label labelMot1 = new Label("Mot1");
        Label labelMot5 = new Label("Mot5");
        Label labelMot2 = new Label("Mot2");
        Label labelMot3 = new Label("Mot3");
        Label labelMot4 = new Label("Mot4");
        Label labelISBN  = new Label("ISBN");

        Button buttonCreation = new Button("Créer Livre");

        buttonCreation.setOnAction(action -> {
            int annee = Integer.parseInt(tfAnnee.getText());
            int isbn = Integer.parseInt(tfISBN.getText());
            int succes = controller.creationLivre(tfTitre.getText(), annee,isbn, tfMot1.getText(),tfMot2.getText(),tfMot3.getText(),tfMot4.getText(), tfMot5.getText(),(String) boxEditeur.getValue(), tfPrenomE.getText(), tfNomE.getText());

            if (succes ==1){gp.add(creationOk,0,8);

                tfTitre.setText("");
                 tfAnnee.setText("");
                 tfMot1.setText("");
                tfMot2.setText("");
                tfMot3.setText("");
                tfMot4.setText("");
                tfMot5.setText("");
                 //tfEditeur.setText("");
                 tfNomE.setText("");
                 tfPrenomE.setText("");
                 tfISBN.setText("");
                 boxEditeur.getSelectionModel().clearSelection();
                System.out.println("Livre créé");
            }
            else{gp.add(creationFail,0,8);}
        });

        gp.add(text, 2, 0, 2, 1);
        gp.add(btnR, 0,0);
        gp.add(buttonCreation, 4,0);

        gp.add(tfTitre, 0,1);
        gp.add(tfAnnee, 0,2);
        //gp.add(tfEditeur, 0,3);
        gp.add(boxEditeur,0,3);
        gp.add(tfNomE, 0,4);
        gp.add(tfPrenomE, 0,5);
        gp.add(tfMot1, 0,6);
        gp.add(tfMot2, 0,7);
        gp.add(tfMot3, 0,8);
        gp.add(tfMot4, 0,9);
        gp.add(tfMot5, 0,10);
        gp.add(tfISBN, 0,11);

        gp.add(labelTitre, 1, 1);
        gp.add(labelAnnee, 1, 2);
        gp.add(labelEditeur, 1, 3);
        gp.add(labelNomAuteur, 1, 4);
        gp.add(labelPrenomAuteur, 1, 5);
        gp.add(labelMot1, 1, 6);
        gp.add(labelMot2, 1, 7);
        gp.add(labelMot3, 1, 8);
        gp.add(labelMot4, 1, 9);
        gp.add(labelMot5, 1, 10);
        gp.add(labelISBN,1,11);

        getChildren().add(gp);
    }
}
