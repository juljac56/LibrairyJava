package com.example.bibliothequetp.controller;

import com.example.bibliothequetp.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Switch extends Application {

    @Override
    public void start(Stage primaryStage) {

        //CycledView b = new B(null, primaryStage);
        //CycledView a = new A(b, primaryStage);

        try {
            CycledView ReserverLivreC = new ReserverLivreC(null, primaryStage);
            CycledView hc = new HomeClient(ReserverLivreC, primaryStage);
            CycledView ha = new HomeAdmin(null, primaryStage);


        CycledView h = new home(ha, hc, primaryStage);

        //b.next = a;
        Scene scene = new Scene(h, 600, 850);

        primaryStage.setTitle("Logiciel de gestion de Bibliothèque");

        String stylesheet = getClass().getResource("/style.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);

        primaryStage.setScene(scene);
        primaryStage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}