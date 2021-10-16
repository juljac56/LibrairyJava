package com.example.bibliothequetp.controller;

import com.example.bibliothequetp.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.List;
import java.util.Vector;

public class Switch extends Application {

    @Override
    public void start(Stage primaryStage) {

        //CycledView b = new B(null, primaryStage);
        //CycledView a = new A(b, primaryStage);

        try {
            Vector<CycledView> vHA = new Vector<CycledView>();

            CycledView creerUsager = new CreerUsager(null, primaryStage, null);
            CycledView consulterListeRouge = new ConsulterListeRouge(null, primaryStage, null);
            CycledView consulterCategories = new ConsulterCategories (null, primaryStage, null);
            CycledView consulterClients = new ConsulterClients(creerUsager, primaryStage, null);
            CycledView historiqueEmrpuntUsager = new HistoriqueEmpruntUsager(null, primaryStage, null);
            CycledView reserverLivreC = new ReserverLivreC(null, primaryStage, null);

            CycledView hc = new HomeClient(reserverLivreC, historiqueEmrpuntUsager, primaryStage,null);

            vHA.addAll(List.of(consulterClients, consulterCategories, consulterListeRouge));
            CycledView ha = new HomeAdmin(vHA, primaryStage, null);

            CycledView h = new home(ha, hc, primaryStage, null);

            // gerer les retours arrieres
            historiqueEmrpuntUsager.retour = hc;
            reserverLivreC.retour = hc;
            hc.retour = h;
            ha.retour = h;
            creerUsager.retour = consulterClients;
            creerUsager.next = ha;
            consulterClients.retour = ha;
            consulterCategories.retour = ha;
            consulterListeRouge.retour = ha;





        //b.next = a;
        Scene scene = new Scene(h, 600, 550);

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