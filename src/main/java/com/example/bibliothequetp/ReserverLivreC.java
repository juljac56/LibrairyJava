package com.example.bibliothequetp;

import com.example.bibliothequetp.controller.CycledView;
import com.example.bibliothequetp.controller.MainController;
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


public class ReserverLivreC extends CycledView {

        MainController controller = new MainController();
     ObservableList<Livre> data;
     TableView table = new TableView();
     Usager u;

    public ReserverLivreC(Stage stage, Usager u) {
        super(stage);
        this.u =u;
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
        TableColumn nomAuteur = new TableColumn("Nom");

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
                ReserverLivreC.this.controller.reservation(table);
            });

            Button btnR = new Button("Retour") {
                @Override
                public void fire() {
                    goClientPage(stage,u);
                }
            };

        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, keyword, btn, btnR);
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