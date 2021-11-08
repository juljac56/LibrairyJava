package com.example.bibliothequetp;

import com.example.bibliothequetp.controller.*;
import com.example.bibliothequetp.model.*;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import static java.lang.String.valueOf;


public class ConsulterListeRouge extends CycledView {

    ListeRougeController controller = new ListeRougeController();
    ObservableList<ListeRouge> data;
    TableView table = new TableView();
    Usager u;

    public ConsulterListeRouge(Stage stage, Usager u) {
        super(stage);
        this.u  = u;
        createGUI();  }

    public void createGUI() {

        GridPane gp = new GridPane();
        gp.setPadding(new Insets(20));
        gp.setHgap(25);
        gp.setVgap(15);

        final Label label = new Label("ListeRouge");
        label.setFont(new Font("Arial", 20));

        TableColumn nomCol = new TableColumn("Nom");
        nomCol.setCellValueFactory(new PropertyValueFactory<ListeRouge, String>("nom"));
        //titreCol.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        TableColumn prenomCol = new TableColumn("Prenom");
        prenomCol.setCellValueFactory(new PropertyValueFactory<ListeRouge, String>("prenom"));
        TableColumn UsagerCol = new TableColumn("Usager");
        UsagerCol.getColumns().addAll(prenomCol, nomCol);
        TableColumn titreDateDeb = new TableColumn<ListeRouge, String>("Debut Rouge");
        titreDateDeb.setCellValueFactory(new PropertyValueFactory<ListeRouge, String>("dateDebut"));
        TableColumn titreDateFin = new TableColumn<ListeRouge, String>("Fin Rouge");
        titreDateFin.setCellValueFactory(new PropertyValueFactory<ListeRouge, String>("dateFin"));


        try {
            data = controller.listeRouge();
            System.out.println("data LR "+ data);
            table.getColumns().addAll(UsagerCol, titreDateDeb, titreDateFin);

            final VBox vbox = new VBox();
            vbox.setSpacing(5);
            TextField keyword = new TextField();
            Label search = new Label("Recherche");

            Button btnSupprimerLR = new Button("retirer de la liste rouge") {
                @Override
                public void fire() {
                    controller.supprimerLR(table);
                    goLRPage(stage,u);
                }
            };

            Button btnR = new Button("Retour") {
                @Override
                public void fire() {
                    goAdminPage(stage,u);
                }
            };

            vbox.setPadding(new Insets(10, 0, 0, 10));
            vbox.getChildren().addAll(label, table, keyword, btnR, btnSupprimerLR);

            getChildren().addAll(vbox);

            FilteredList<ListeRouge> filteredData = new FilteredList<ListeRouge>(data, b -> true);


            keyword.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(cat -> {
                    if (newValue.isBlank() || newValue.isBlank() || newValue == null) {
                        return true;
                    }
                    String searchKeyword = newValue.toLowerCase();

                    if (cat.getNom().toLowerCase().indexOf(searchKeyword) != -1) {
                        return true;
                    } else {
                        return false;
                    }
                    //else if(){}
                });
            });

            SortedList<ListeRouge> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(table.comparatorProperty());
            table.setItems(sortedData);
            table.setFixedCellSize(55);
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            table.prefHeightProperty().bind(Bindings.size(table.getItems()).multiply(table.getFixedCellSize()).add(50));
            table.setEditable(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
