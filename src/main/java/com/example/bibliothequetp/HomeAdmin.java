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

    public HomeAdmin(Vector<CycledView> vha, Stage stage, CycledView retour) {
        super(vha, stage, retour);
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
                callNext(retour);
            }
        };

        Button btn = new Button("Gérer les clients") {
            @Override
            public void fire() {
                callNext(vha.get(0));
            }

        };

        Button btnCat = new Button("Gérer les Catégories") {
            @Override
            public void fire() {
                callNext(vha.get(1));
            }

        };

        Button btnLR = new Button("Gérer la liste rouge") {
            @Override
            public void fire() {
                callNext(vha.get(2));
            }

        };



        gp.add(text, 0, 0, 2, 1);
        gp.add(btnR, 0,1);
        gp.add(btn, 0,2);
        gp.add(btnCat,0,3);
        gp.add(btnLR,0,4);

        getChildren().add(gp);

    }
}