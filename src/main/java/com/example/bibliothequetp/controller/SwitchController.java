package com.example.bibliothequetp.controller;

import com.example.bibliothequetp.HomeAdmin;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


// COntroller test
public class SwitchController extends StackPane {

    public void callNext(Stage stage) {
        CycledView n = new HomeAdmin(stage);
        getScene().setRoot(n);
    }
}
