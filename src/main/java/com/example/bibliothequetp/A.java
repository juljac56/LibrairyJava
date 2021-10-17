package com.example.bibliothequetp;

import com.example.bibliothequetp.controller.CycledView;
import com.example.bibliothequetp.controller.Switch;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class A extends CycledView {

    public A(Stage stage) {
        super(stage);
    }

    public void createGUI() {
        getChildren().add(new Button("I'm A") {
            /*@Override
            public void fire() {
                System.out.println("GOOO B");
                callNext(next);
                 }
             */


        });
    }

}

