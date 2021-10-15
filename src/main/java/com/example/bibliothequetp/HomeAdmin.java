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

    public HomeAdmin(CycledView next, Stage stage) {
        super(next, stage);
    }

    public void createGUI() {

        GridPane gp= new GridPane();
        gp.setPadding(new Insets(20));
        gp.setHgap(25);
        gp.setVgap(15);

        Text text = new Text();
        text.setText("Page Admin");

        gp.add(text, 0, 0, 2, 1);

        getChildren().add(gp);
        //getChildren().add(btn);

    }
}