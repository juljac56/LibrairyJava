package com.example.bibliothequetp;

import com.example.bibliothequetp.controller.CreationController;
import com.example.bibliothequetp.controller.CycledView;
import com.example.bibliothequetp.controller.EmpruntController;
import com.example.bibliothequetp.controller.MainController;
import com.example.bibliothequetp.model.DataBase;
import com.example.bibliothequetp.model.Livre;
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


public class ReserverLivreA extends CycledView {

    MainController controller = new MainController();
    CreationController creaController = new CreationController();
    EmpruntController empruntController = new EmpruntController();

    ObservableList<Livre> data;
    TableView table = new TableView();

    public ReserverLivreA(Stage stage) {
        super(stage);

        createGUI();
    }

    public void createGUI() {

        GridPane gp = new GridPane();
        gp.setPadding(new Insets(20));
        gp.setHgap(25);
        gp.setVgap(15);

        final Label label = new Label("Livres");
        label.setFont(new Font("Arial", 20));


        TableColumn titreCol = new TableColumn<Livre, String>("titre");
        titreCol.setCellValueFactory(new PropertyValueFactory<Livre, String>("titre"));
        //titreCol.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        TableColumn anneeCol = new TableColumn("Annee");
        anneeCol.setCellValueFactory(new PropertyValueFactory<Livre, Integer>("annee"));
        TableColumn auteurCol = new TableColumn("Auteur");
        TableColumn editeurCol = new TableColumn("Editeur");
        editeurCol.setCellValueFactory(new PropertyValueFactory<Livre, String>("editeur"));
        TableColumn prenomAuteur = new TableColumn("Prenom");
        prenomAuteur.setCellValueFactory(new PropertyValueFactory<Livre, String>("prenom"));
        TableColumn nomAuteur = new TableColumn("Nom");
        nomAuteur.setCellValueFactory(new PropertyValueFactory<Livre, String>("nom"));

        auteurCol.getColumns().addAll(prenomAuteur, nomAuteur);

        try {

            data = getLivreList();

            table.getColumns().addAll(titreCol, anneeCol, auteurCol, editeurCol);

            final VBox vbox = new VBox();
            vbox.setSpacing(5);
            TextField keyword = new TextField();
            Label search = new Label("Recherche");

            Button btn = new Button("reserve");
            btn.setOnAction( actionEvent -> {
                ReserverLivreA.this.controller.reservation(table);
            });

            Button btnR = new Button("Retour") {
                @Override
                public void fire() {
                    goAdminPage(stage);
                }
            };

            Button btnSupprimerLivre = new Button("Supprimer Livre") {
                @Override
                public void fire() {
                    creaController.supprimerlivre(table);
                    goReserverLivreAPage(stage);
                }
            };

            Button btnCreerLivre = new Button("Créer Livre") {
                @Override
                public void fire() {
                    goCreerLivrePage(stage);
                }
            };

            Button btnCreerEmprunt = new Button("Emprunter");
            btnCreerEmprunt.setOnAction(actionEvent -> {
                Integer idL = empruntController.debutCreerEmprunt(table);
                goCreerEmprunt(stage, idL);
            });

            Button btnModifier = new Button("Modifier");
            btnModifier.setOnAction(actionEvent -> {
                Vector<Object> v = empruntController.debutModifierLivre(table);
                goModifierLivre(stage,(int) v.get(0),(String) v.get(1), (int) v.get(2), (String) v.get(3), (String) v.get(4), (String) v.get(5), (String) v.get(6), (String) v.get(7), (int) v.get(8));
            });

            Button btnDetailLivre = new Button("Details");
            btnDetailLivre.setOnAction(actionEvent -> {
                Integer idL = empruntController.debutCreerEmprunt(table);
                goVoirDetails(stage, idL);
            });

            vbox.setPadding(new Insets(10, 0, 0, 10));
            vbox.getChildren().addAll(label, table, keyword, btnCreerEmprunt,btnDetailLivre, btnR, btnCreerLivre,btnModifier,btnSupprimerLivre);

            getChildren().addAll(vbox);

            FilteredList<Livre> filteredData = new FilteredList<Livre>(data, b -> true);


            keyword.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(livre -> {
                    if (newValue.isBlank() || newValue.isBlank() || newValue == null) {
                        return true;
                    }
                    String searchKeyword = newValue.toLowerCase();

                    if (livre.getTitre().toLowerCase().indexOf(searchKeyword) != -1) {
                        return true;
                    } else {
                        return false;
                    }
                    //else if(){}


                });
            });

            SortedList<Livre> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(table.comparatorProperty());

            table.setItems(sortedData);
            table.setEditable(true);


        }

        catch (Exception e) {
            e.printStackTrace();

        }}

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
