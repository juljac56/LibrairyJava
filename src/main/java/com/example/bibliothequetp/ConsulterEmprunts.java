package com.example.bibliothequetp;

import com.example.bibliothequetp.controller.CycledView;
import com.example.bibliothequetp.controller.EmpruntController;
import com.example.bibliothequetp.controller.MainController;
import com.example.bibliothequetp.model.Emprunt;
import com.example.bibliothequetp.model.Livre;
import com.example.bibliothequetp.model.Usager;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Vector;


public class ConsulterEmprunts extends CycledView {

    ObservableList<Emprunt> data;
    MainController controller = new MainController();
    public EmpruntController empruntController = new EmpruntController();
    TableView table = new TableView();
    Usager u;

    public ConsulterEmprunts(Stage stage, Usager u) {
        super(stage);
        createGUI();
        this.u = u;
    }

    public void createGUI() {

        GridPane gp = new GridPane();
        gp.setPadding(new Insets(20));
        gp.setHgap(25);
        gp.setVgap(15);
        TextField keyword = new TextField();

        Text text = new Text();
        text.setText("Page Client");

        gp.add(text, 0, 0, 2, 1);


        //getChildren().add(btn);

        // Creation de la table des emprunts en cours d'un usager

        TableColumn titreCol = new TableColumn<Emprunt, String>("titre");
        titreCol.setCellValueFactory(new PropertyValueFactory<Emprunt, String>("titre"));
        TableColumn titreDateDeb = new TableColumn<Emprunt, String>("Debut Emprunt");
        titreDateDeb.setCellValueFactory(new PropertyValueFactory<Emprunt, String>("dateDebut"));
        TableColumn titreDateFin = new TableColumn<Emprunt, String>("A rendre pour ");
        titreDateFin.setCellValueFactory(new PropertyValueFactory<Emprunt, String>("rendrepour"));
        TableColumn nomCol = new TableColumn<Emprunt, String>("Nom");
        nomCol.setCellValueFactory(new PropertyValueFactory<Usager, String>("nom"));


        TableColumn prenomCol = new TableColumn("Prenom");
        prenomCol.setCellValueFactory(new PropertyValueFactory<Usager, Integer>("prenom"));
        TableColumn UsagerCol = new TableColumn("Usager");

        UsagerCol.getColumns().addAll(prenomCol, nomCol);

        try {
            data = empruntController.listeEmpruntsActuels();
            table.getColumns().addAll(titreCol,UsagerCol,titreDateDeb,titreDateFin);
            table.setItems(data);
            table.setEditable(true);

            FilteredList<Emprunt> filteredData = new FilteredList<Emprunt>(data, b -> true);

            keyword.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(usager -> {
                    if (newValue.isBlank() || newValue.isBlank() || newValue == null) {
                        return true;
                    }
                    String searchKeyword = newValue.toLowerCase();

                    if (usager.getPrenom().toLowerCase().indexOf(searchKeyword) != -1) {
                        return true;
                    }

                    else if(usager.getTitre().toLowerCase().indexOf(searchKeyword) !=-1){
                        return true;
                    }
                    else {
                        return false;}
                    //else if(){}
                });
            });

            SortedList<Emprunt> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(table.comparatorProperty());

            table.setItems(sortedData);
            table.setFixedCellSize(35);
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            table.prefHeightProperty().bind(Bindings.size(table.getItems()).multiply(table.getFixedCellSize()).add(30));
            table.setEditable(true);
        }
        catch(Exception e){e.printStackTrace();}

        Button btnR = new Button("Retour") {
            @Override
            public void fire() {
                goAdminPage(stage,u);
            }
        };

        Button btnRendreLivre = new Button("Rendre livre");
        btnRendreLivre.setOnAction(actionEvent -> {
           empruntController.rendreLivre(table);
           goConsulterEmprunt(stage,u);
        });

        gp.add(btnR,0,1);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(gp, table, btnRendreLivre, keyword);

        getChildren().addAll(vbox);
    }

}





