package com.example.bibliothequetp;

import com.example.bibliothequetp.controller.CycledView;
import com.example.bibliothequetp.controller.MainController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;


public class home extends CycledView {

    MainController controller = new MainController();

    public home(CycledView next,CycledView next1, Stage stage) {
        super(next, next1, stage);
        stage.setTitle("Home Page");
        createGUI();
    }

    public void createGUI() {

        GridPane gp= new GridPane();
        gp.setPadding(new Insets(20));
        gp.setHgap(25);
        gp.setVgap(15);

        Text text = new Text();
        text.setText("Bienvenue sur la page d'acceuil");

        gp.add(text, 0, 0, 2, 1);


        Button btn = new Button("Connexion Admin") {
            @Override
            public void fire() {
                callNext(next);
            }

        } ;
        btn.getStyleClass().add("btn");


        gp.add(btn, 0, 1 );

        Button btn1 = new Button("Connexion Client"){
            @Override
            public void fire() {
                callNext(next1);
            }
        } ;
        btn1.getStyleClass().add("btn");

        gp.add(btn1,1,1);

        getChildren().add(gp);
        //getChildren().add(btn);


    }
}





