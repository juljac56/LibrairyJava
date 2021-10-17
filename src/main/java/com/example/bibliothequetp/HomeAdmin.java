package com.example.bibliothequetp;

import com.example.bibliothequetp.controller.CycledView;
import com.example.bibliothequetp.controller.MainController;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Vector;


public class HomeAdmin extends CycledView {

    MainController controller = new MainController();

    public HomeAdmin(Stage stage) {
        super(stage);
        createGUI();
    }

    public void createGUI() {

        GridPane gp= new GridPane();
        gp.setPadding(new Insets(20));
        gp.setHgap(25);
        gp.setVgap(15);

        Text text = new Text();
        text.setText("Page Admin");

        Button btnR = new Button("Retour") {
            @Override
            public void fire() {
                goHomePage(stage);

            }
        };

        Button btn = new Button("Gérer les clients") {
            @Override
            public void fire() {
                goGererClientPage(stage);

            }

        };

        Button btnCat = new Button("Gérer les Catégories") {
            @Override
            public void fire() {
                goCatPage(stage);
            }

        };

        Button btnLR = new Button("Gérer la liste rouge") {
            @Override
            public void fire() {
                goLRPage(stage);
            }
        };

        Button btnLivre = new Button("Gérer les livres") {
            @Override
            public void fire() {
                goReserverLivreAPage(stage);
            }
        };



        gp.add(text, 0, 0, 2, 1);
        gp.add(btnR, 0,1);
        gp.add(btn, 0,2);
        gp.add(btnCat,0,3);
        gp.add(btnLR,0,4);
        gp.add(btnLivre,0,5);

        getChildren().add(gp);

    }
}