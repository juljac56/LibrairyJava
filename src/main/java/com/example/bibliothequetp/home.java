package com.example.bibliothequetp;

import com.example.bibliothequetp.controller.CycledView;
import com.example.bibliothequetp.controller.MainController;
import com.example.bibliothequetp.model.Usager;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class home extends CycledView {

    MainController controller = new MainController();
    Usager u ;

    public home( Stage stage, Usager u) {
        super(stage);
        this.u = u;
        createGUI();}

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
                goAdminPage(stage, u);
            }
        };

        btn.getStyleClass().add("btn");
        gp.add(btn, 0, 1 );

        Button btn1 = new Button("Connexion Client"){
            @Override
            public void fire() {
                goClientPage(stage, u);
            }};
        btn1.getStyleClass().add("btn");
        gp.add(btn1,1,1);
        getChildren().add(gp);
    }
}





