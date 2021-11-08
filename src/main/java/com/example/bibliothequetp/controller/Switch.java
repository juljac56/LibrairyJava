package com.example.bibliothequetp.controller;

import com.example.bibliothequetp.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

import java.util.List;
import java.util.Vector;

// Sert à lancer l'application

public class Switch extends Application {

    @Override
    public void start(Stage primaryStage) {

        try {CycledView h = new Login(primaryStage);
            h.setStyle("-fx-background-color: #3B5D88;");
        Scene scene = new Scene(h, 600, 600);  // donne les dimensions de la fenetre
        primaryStage.setTitle("Logiciel de gestion de Bibliothèque");

        String stylesheet = getClass().getResource("/style.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);  // ajout de style css à l'interface

        primaryStage.setScene(scene);
        primaryStage.show();
        }
        catch (Exception e){
            e.printStackTrace();}
    }
    public static void main(String[] args) {
        launch(args);
    }
}