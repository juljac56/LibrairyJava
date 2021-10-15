module com.example.bibliothequetp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;

    opens com.example.bibliothequetp to javafx.fxml;
    opens com.example.bibliothequetp.model to javafx.fxml;
    opens com.example.bibliothequetp.controller to javafx.fxml;

    exports com.example.bibliothequetp;
    exports com.example.bibliothequetp.model;
    exports com.example.bibliothequetp.controller;
}