package com.example.bibliothequetp;

import com.example.bibliothequetp.controller.CategorieController;
import com.example.bibliothequetp.controller.CycledView;
import com.example.bibliothequetp.controller.MainController;
import com.example.bibliothequetp.controller.UsagerController;
import com.example.bibliothequetp.model.Categorie;
import com.example.bibliothequetp.model.DataBase;
import com.example.bibliothequetp.model.Livre;
import com.example.bibliothequetp.model.Usager;
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
import java.util.Vector;

// View servant à consulter les catégories disponibles, sous forme de tableau

public class ConsulterCategories extends CycledView {

    CategorieController controller = new CategorieController();
    ObservableList<Categorie> data;
    TableView table = new TableView();
    Usager u;

    public ConsulterCategories(Stage stage, Usager u) {
        super(stage);
        createGUI();
        this.u = u;
    }


    public void createGUI() {
        GridPane gp = new GridPane();
        gp.setPadding(new Insets(20));
        gp.setHgap(25);
        gp.setVgap(15);

        final Label label = new Label("Catégories");
        label.setFont(new Font("Arial", 20));

        // creation des colonnes de notre tableau

        TableColumn catCol = new TableColumn("Catégorie");
        catCol.setCellValueFactory(new PropertyValueFactory<Categorie, Integer>("idCategorie"));

        TableColumn nbCol= new TableColumn("Nombre Max Emprunts");
        nbCol.setCellValueFactory(new PropertyValueFactory<Categorie, Integer>("nbMax"));
        TableColumn dureeCol = new TableColumn("Durée Max d'Emprunt");
        dureeCol.setCellValueFactory(new PropertyValueFactory<Categorie, Integer>("dureeMax"));

        try {
            data = controller.banqueCategorie();
            table.getColumns().addAll(catCol, nbCol, dureeCol);

            final VBox vbox = new VBox();
            vbox.setSpacing(5);
            TextField keyword = new TextField();
            Label search = new Label("Recherche");
            Button btn = new Button("Modifier");
            btn.setOnAction(actionEvent -> {
                Vector<Integer> v = controller.debutModifierCat(table);
                goModifierCat(stage,v.get(0),v.get(1), v.get(2),u);
            });

            // boutton de retour vers la page précédente
            Button btnR = new Button("Retour") {
                @Override
                public void fire() {
                    goAdminPage(stage, u);
                }
            };
            // boutton servant à créer une nouvelle catégorie
            Button btnCreerCat = new Button("Créer une Catégorie") {
                @Override
                public void fire() {
                    goCreerCategoriePage(stage,u);
                }
            };

            vbox.setPadding(new Insets(10, 0, 0, 10));
            vbox.getChildren().addAll(label, table, keyword, btn, btnR, btnCreerCat);

            getChildren().addAll(vbox);
            FilteredList<Categorie> filteredData = new FilteredList<Categorie>(data, b -> true);

            // creation de la barre de recherche
            keyword.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(cat -> {
                    if (newValue.isBlank() || newValue.isBlank() || newValue == null) {
                        return true;
                    }
                    String searchKeyword = newValue.toLowerCase();

                    if (String.valueOf(cat.getIdCategorie()).toLowerCase().indexOf(searchKeyword) != -1) {
                        return true;
                    } else {
                        return false;
                    }});
            });

            SortedList<Categorie> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(table.comparatorProperty());
            table.setItems(sortedData);
            table.setFixedCellSize(35);
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            table.prefHeightProperty().bind(Bindings.size(table.getItems()).multiply(table.getFixedCellSize()).add(30));
            table.setEditable(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }}