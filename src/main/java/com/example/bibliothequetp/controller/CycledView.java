package com.example.bibliothequetp.controller;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public abstract class CycledView extends StackPane { // Choose whatever is most appropriate class
    public CycledView(CycledView next, Stage stage) {
        this.next = next;
        this.stage = stage;
        createGUI();
    }

    public CycledView(CycledView next, CycledView next1, Stage stage) {
        this.next = next;
        this.next1 = next1;
        this.stage = stage;
        createGUI();
    }

    public abstract void createGUI();

    protected void callNext(CycledView n) {
        getScene().setRoot(n);
    }

    public CycledView next;
    public CycledView next1;
    public Stage stage;
}
