package com.example.bibliothequetp;

import com.example.bibliothequetp.controller.*;
import com.example.bibliothequetp.model.Usager;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicReference;


public class CreerEmprunt extends CycledView {

    EmpruntController controller = new EmpruntController();
    UsagerController UsagerController = new UsagerController();
    int idLivre;
    ObservableList<Usager> data;
    TableView table;
    public CreerEmprunt(Stage stage, int idL) {
        super(stage);
        this.idLivre = idL;
        this.table = new TableView();
        createGUI();
    }

    public void createGUI() {

        GridPane gp= new GridPane();
        gp.setPadding(new Insets(20));
        gp.setHgap(25);
        gp.setVgap(15);

        Text text = new Text();
        text.setText("Créer un emprunt");

        Text empruntenCours = new Text("");


        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        TextField keyword = new TextField();
        Label search = new Label("Recherche");

        vbox.setPadding(new Insets(10, 0, 0, 10));

        if (controller.verifierEmprunt(idLivre)){
            empruntenCours = new Text("Livre disponible");


            TableColumn nomCol = new TableColumn<Usager, String>("Nom");
            nomCol.setCellValueFactory(new PropertyValueFactory<Usager, String>("nom"));
            //titreCol.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

            TableColumn prenomCol = new TableColumn("Prenom");
            prenomCol.setCellValueFactory(new PropertyValueFactory<Usager, Integer>("prenom"));
            TableColumn UsagerCol = new TableColumn("Usager");
            TableColumn emailCol = new TableColumn("Email");
            emailCol.setCellValueFactory(new PropertyValueFactory<Usager, String>("mail"));

            TableColumn listeRCol = new TableColumn("nbFoisListeRouge");
            listeRCol.setCellValueFactory(new PropertyValueFactory<Usager, Integer>("nbFoisListeRouge"));

            TableColumn catCol = new TableColumn("Catégorie");
            catCol.setCellValueFactory(new PropertyValueFactory<Usager, Integer>("categorie"));

            UsagerCol.getColumns().addAll(prenomCol, nomCol);



            try {
                data = UsagerController.banqueUsagers();
                table.getColumns().addAll(UsagerCol, emailCol, listeRCol, catCol);


                vbox.getChildren().addAll( table, keyword);

                FilteredList<Usager> filteredData = new FilteredList<Usager>(data, b -> true);

                keyword.textProperty().addListener((observable, oldValue, newValue) -> {
                    filteredData.setPredicate(usager -> {
                        if (newValue.isBlank() || newValue.isBlank() || newValue == null) {
                            return true;
                        }
                        String searchKeyword = newValue.toLowerCase();

                        if (usager.getPrenom().toLowerCase().indexOf(searchKeyword) != -1) {
                            return true;
                        } else {
                            return false;
                        }
                    });
                });

                SortedList<Usager> sortedData = new SortedList<>(filteredData);
                sortedData.comparatorProperty().bind(table.comparatorProperty());

                table.setItems(sortedData);
                table.setEditable(true);

                Button buttonCreation = new Button("Créer Emprunt");
                buttonCreation.setOnAction(action -> {
                    int succes = controller.creerEmprunt(table, idLivre);

                    if (succes == 1){
                        System.out.println("Emprunt créé");
                        Text empruntCree = new Text("Livre emprunté ! ");
                        vbox.getChildren().addAll( empruntCree);
                    }
                    else{ System.out.println("Problème d'emprunt");}
                });

                vbox.getChildren().addAll( buttonCreation);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else{System.out.println("non disponible");
            empruntenCours = new Text("Livre non disponible");}


        Button btnR = new Button("Retour") {
            @Override
            public void fire() {
                goReserverLivreAPage(stage);
            }
        };

        //gp.add(btnR,1,0);

        //gp.add(empruntenCours,2,2);
        vbox.getChildren().addAll( btnR, empruntenCours);
        getChildren().addAll(vbox);
        //getChildren().add(gp);
    }
}
