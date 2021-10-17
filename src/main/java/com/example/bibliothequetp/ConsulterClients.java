package com.example.bibliothequetp;

import com.example.bibliothequetp.controller.CycledView;
import com.example.bibliothequetp.controller.MainController;
import com.example.bibliothequetp.controller.UsagerController;
import com.example.bibliothequetp.model.DataBase;
import com.example.bibliothequetp.model.Livre;
import com.example.bibliothequetp.model.Usager;
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


public class ConsulterClients extends CycledView {

    UsagerController controller = new UsagerController();
    ObservableList<Usager> data;
    TableView table;

    public ConsulterClients(Stage stage) {
        super(stage);
        this.table = new TableView();
        createGUI();
    }

    public void createGUI() {
                GridPane gp = new GridPane();
        gp.setPadding(new Insets(20));
        gp.setHgap(25);
        gp.setVgap(15);

        final Label label = new Label("Clients");
        label.setFont(new Font("Arial", 20));


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
            data = controller.banqueUsagers();
            table.getColumns().addAll(UsagerCol, emailCol, listeRCol, catCol);

            final VBox vbox = new VBox();
            vbox.setSpacing(5);
            TextField keyword = new TextField();
            Label search = new Label("Recherche");

            Button btn = new Button("Supprimer");
            btn.setOnAction(actionEvent -> {
                ConsulterClients.this.controller.supprimerClient(table);
            });

            Button btnR = new Button("Retour") {
                @Override
                public void fire() {
                    goAdminPage(stage);
                }
            };

            Button btnCreerUsage = new Button("Créer un Client") {
                @Override
                public void fire() {
                    goCreerClientPage(stage);
                }
            };

            vbox.setPadding(new Insets(10, 0, 0, 10));
            vbox.getChildren().addAll(label, table, keyword, btn, btnR, btnCreerUsage);

            getChildren().addAll(vbox);

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
                    //else if(){}


                });
            });

            SortedList<Usager> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(table.comparatorProperty());

            table.setItems(sortedData);
            table.setEditable(true);


        } catch (Exception e) {
            e.printStackTrace();
        }

        Button btnR = new Button("Retour") {
            @Override
            public void fire() {
                callNext(retour);
            }
        };
    }

    public ObservableList<Livre> getLivreList() {

        ObservableList<Livre> list = FXCollections.observableArrayList();

        try {
            Livre l = new Livre(1);
            System.out.println("new titre" + l.titre);

            Connection conn = DataBase.getConnection();
            PreparedStatement ps = conn.prepareStatement("select * from SUPPORT");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Vector<String> ligne = new Vector<String>();
                Livre livre = new Livre(rs.getInt(1));
                //ligne.add(livre.titre);
                ligne.add(valueOf(livre.annee));
                ligne.add(livre.listAuteur());
                ligne.add(livre.editeur);
                list.add(livre);
            }

            conn.close();
        }
        catch(Exception e){System.out.println(e);}

        return list;

    }
}