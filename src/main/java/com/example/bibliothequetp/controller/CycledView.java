package com.example.bibliothequetp.controller;

import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public abstract class CycledView extends StackPane { // Choose whatever is most appropriate class
    public CycledView(CycledView next, Stage stage, CycledView retour) {
        this.next = next;
        this.stage = stage;
        this.table = new TableView();
        this.retour = retour;

    }

    public CycledView(CycledView next, CycledView next1, Stage stage, CycledView retour) {
        this.next = next;
        this.next1 = next1;
        this.stage = stage;
        this.table = new TableView();
        this.retour = retour;

    }

    public abstract void createGUI();

    protected void callNext(CycledView n) {
        getScene().setRoot(n);
    }

    public CycledView next;
    public CycledView next1;
    public Stage stage;
    public TableView table;
    public CycledView retour;
}
