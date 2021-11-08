package com.example.bibliothequetp;

import com.example.bibliothequetp.controller.CycledView;
import com.example.bibliothequetp.controller.MainController;
import com.example.bibliothequetp.model.Usager;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Vector;


public class Login extends CycledView {

    MainController controller = new MainController();

    public Login(Stage stage) {
        super(stage);
        createGUI();
    }

    public void createGUI() {

        GridPane gp = new GridPane();
        gp.setPadding(new Insets(20));
        gp.setHgap(25);
        gp.setVgap(15);

        Text text = new Text();
        text.setText("Page de Login");


        TextField tfusername = new TextField();
        PasswordField tfpassword = new PasswordField();

        Label LabelUsername = new Label("Nom d'utilisateur");
        Label LabelPassword = new Label("Mot de passe");

        gp.add(tfusername, 0, 1);
        gp.add(tfpassword, 0, 2);

        gp.add(LabelUsername, 1, 1);
        gp.add(LabelPassword, 1, 2);



        Button Btnlogin = new Button("Se connecter");

        Btnlogin.setOnAction(action -> {

            String pass = tfpassword.getText();
            String username = tfusername.getText();

            Usager u = controller.login(username, pass);

            if (u == null) {
                System.out.println("Pas de connexion");
            } else {
                System.out.println("Connexion");
                int page  = controller.verifierCat(u);

                if (page == 0)
                {goAdminPage(stage, u);}
                else{goClientPage(stage, u);}
            }});

        gp.add(Btnlogin, 1, 4);
        getChildren().add(gp);

    }}
