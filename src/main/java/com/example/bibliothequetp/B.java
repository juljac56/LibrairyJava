package com.example.bibliothequetp;

import com.example.bibliothequetp.controller.CycledView;
import com.example.bibliothequetp.controller.Switch;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class B extends CycledView {


    public B(Stage stage ) {
        super(stage);
    }

    public void createGUI() {
        getChildren().add(new Button("I'm B") {
            /*@Override
            public void fire() {
                callNext(next);
                }
             */


        });
    }

}


