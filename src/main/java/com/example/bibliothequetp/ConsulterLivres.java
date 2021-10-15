package com.example.bibliothequetp;


import com.example.bibliothequetp.model.DataBase;
import com.example.bibliothequetp.model.Livre;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.*;
import java.util.List;
import java.util.Vector;


import static java.lang.String.valueOf;


//  ResultSet rs = stmt.executeQuery("SELECT a, b, c FROM TABLE2");
//     ResultSetMetaData rsmd = rs.getMetaData();
//     int numberOfColumns = rsmd.getColumnCount();
// rsmd.getColumnName(i);
//
public class ConsulterLivres extends Application {

    Vector<String> nomsColonnes = new Vector<String>(List.of("Titre",  "Année", "Auteur(s)","Editeur"));
    Vector<Vector<String>> donnees = new Vector<Vector<String>>();
    private TableView table = new TableView();

    public void  start(Stage stage) throws SQLException {
        
        Scene scene = new Scene(new Group());
        stage.setTitle("Consulter les livres de la bibliotheque");
        stage.setWidth(800);
        stage.setHeight(600);

        final Label label = new Label("Livres");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        TableColumn titreCol = new TableColumn<Livre, String> ("titre");
        titreCol.setCellValueFactory(new PropertyValueFactory<Livre, String>("titre")  );
        //titreCol.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        TableColumn anneeCol = new TableColumn("Annee");
        anneeCol.setCellValueFactory(new PropertyValueFactory<Livre,Integer>("annee"));
        TableColumn auteurCol = new TableColumn("Auteur");
        TableColumn editeurCol = new TableColumn("Editeur");
        editeurCol.setCellValueFactory(new PropertyValueFactory<Livre, String>("editeur")  );
        TableColumn prenomAuteur = new TableColumn("Prenom");
        TableColumn nomAuteur = new TableColumn("Nom");

        auteurCol.getColumns().addAll(prenomAuteur,nomAuteur);
        final ObservableList<Livre> data = getLivreList();
        table.setItems(data);
        table.getColumns().addAll(titreCol,anneeCol,auteurCol,editeurCol);





        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    private ObservableList<Livre> getLivreList() throws SQLException {

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
                donnees.add(ligne);
                list.add(livre);
                //System.out.println(data);
            }


        }
        catch(Exception e){System.out.println(e);}


        return list;
    }

    public static void main(String[] args){
        launch(args);
    }


}

