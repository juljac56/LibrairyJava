package com.example.bibliothequetp;

import com.example.bibliothequetp.controller.MainController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApplication extends Application {

    MainController controller = new MainController();

    public void start(Stage primaryStage) {

        StackPane root = new StackPane();



        Button btn = new Button("This is a button");

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                System.out.println("Button clicked");

            }
        } );
        Scene scene = new Scene(root,300,300);
        root.getChildren().add(btn);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Button Class Example");
        primaryStage.show();

        btn.setOnAction( actionEvent -> {
        MainApplication.this.controller.Test();
    });
    }

    public static void main(String[] args){
        launch(args);
    }

}
