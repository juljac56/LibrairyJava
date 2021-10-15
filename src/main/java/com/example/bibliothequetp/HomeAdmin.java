package com.example.bibliothequetp;

import com.example.bibliothequetp.controller.CycledView;
import com.example.bibliothequetp.controller.MainController;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class HomeAdmin extends CycledView {

    MainController controller = new MainController();

    public HomeAdmin(CycledView next, Stage stage, CycledView retour) {
        super(next, stage, retour);
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

        gp.add(text, 0, 0, 2, 1);
        gp.add(btnR, 0,1);


        getChildren().add(gp);

    }
}