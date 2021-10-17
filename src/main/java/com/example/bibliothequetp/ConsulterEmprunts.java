package com.example.bibliothequetp;

import com.example.bibliothequetp.controller.CycledView;
import com.example.bibliothequetp.controller.EmpruntController;
import com.example.bibliothequetp.controller.MainController;
import com.example.bibliothequetp.model.Emprunt;
import com.example.bibliothequetp.model.Livre;
import com.example.bibliothequetp.model.Usager;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class ConsulterEmprunts extends CycledView {

    ObservableList<Emprunt> data;
    MainController controller = new MainController();
    public EmpruntController empruntController = new EmpruntController();
    TableView table = new TableView();

    public ConsulterEmprunts(Stage stage) {
        super(stage);
        createGUI();
    }

    public void createGUI() {

        GridPane gp = new GridPane();
        gp.setPadding(new Insets(20));
        gp.setHgap(25);
        gp.setVgap(15);

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
        //titreCol.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        TableColumn prenomCol = new TableColumn("Prenom");
        prenomCol.setCellValueFactory(new PropertyValueFactory<Usager, Integer>("prenom"));
        TableColumn UsagerCol = new TableColumn("Usager");

        UsagerCol.getColumns().addAll(prenomCol, nomCol);

        try {
            data = empruntController.listeEmpruntsActuels();
            table.getColumns().addAll(titreCol,UsagerCol,titreDateDeb,titreDateFin);
            table.setItems(data);
            table.setEditable(true);
        }
        catch(Exception e){e.printStackTrace();}

        Button btnR = new Button("Retour") {
            @Override
            public void fire() {
                goAdminPage(stage);
            }
        };
        gp.add(btnR,1,0);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);



        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(gp, table);

        getChildren().addAll(vbox);
    }

}





