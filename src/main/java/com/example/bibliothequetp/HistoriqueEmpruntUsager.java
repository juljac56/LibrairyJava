package com.example.bibliothequetp;

import com.example.bibliothequetp.controller.CycledView;
import com.example.bibliothequetp.controller.EmpruntController;
import com.example.bibliothequetp.controller.MainController;
import com.example.bibliothequetp.model.Emprunt;
import com.example.bibliothequetp.model.Livre;
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


public class HistoriqueEmpruntUsager extends CycledView {

    ObservableList<Emprunt> data;
    MainController controller = new MainController();
    public EmpruntController empruntController;
    TableView table = new TableView();

    public HistoriqueEmpruntUsager(Stage stage) {
        super(stage);
        this.empruntController = new EmpruntController();
        this.retour = new HomeAdmin(stage);
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

        TableColumn titreCol = new TableColumn<Livre, String>("titre");
        titreCol.setCellValueFactory(new PropertyValueFactory<Livre, String>("titre"));
        TableColumn titreDateDeb = new TableColumn<Livre, String>("Debut Emprunt");
        titreDateDeb.setCellValueFactory(new PropertyValueFactory<Livre, String>("dateDebut"));
        TableColumn titreDateFin = new TableColumn<Livre, String>("Rendu le");
        titreDateFin.setCellValueFactory(new PropertyValueFactory<Livre, String>("dateFin"));
        //titreCol.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        try {

            data = empruntController.empruntUsager(1);
            table.getColumns().addAll(titreCol,titreDateDeb,titreDateFin);
            table.setItems(data);
            table.setEditable(true);

        }
        catch(Exception e){e.printStackTrace();}

        Button btnR = new Button("Retour") {
            @Override
            public void fire() {
                callNext(retour);
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





