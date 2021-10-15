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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class HomeClient extends CycledView {

    ObservableList<Emprunt> data;
    MainController controller = new MainController();
    public EmpruntController empruntController;

    public HomeClient(CycledView next, Stage stage) {
        super(next, stage);
        this.empruntController = new EmpruntController();
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


        Button btn = new Button("Réserver un livre") {
            @Override
            public void fire() {
                callNext(next);
            }

        };

        btn.getStyleClass().add("btn");

        gp.add(btn, 0, 0);


        getChildren().add(gp);


        //getChildren().add(btn);

        // Creation de la table des emprunts en cours d'un usager

        TableColumn titreCol = new TableColumn<Livre, String>("titre");
        titreCol.setCellValueFactory(new PropertyValueFactory<Livre, String>("titre"));
        TableColumn titreDateDeb = new TableColumn<Livre, String>("Debut Emprunt");
        titreDateDeb.setCellValueFactory(new PropertyValueFactory<Livre, String>("dateDebut"));
        TableColumn titreDateFin = new TableColumn<Livre, String>("A rendre pour ");
        titreDateFin.setCellValueFactory(new PropertyValueFactory<Livre, String>("dateFin"));
        //titreCol.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        try {

            data = empruntController.empruntUsagerActuel(1);
            table.getColumns().addAll(titreCol,titreDateDeb,titreDateFin);
            table.setItems(data);
            table.setEditable(true);

        }
        catch(Exception e){e.printStackTrace();}

        final VBox vbox = new VBox();
        vbox.setSpacing(5);

        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(gp, table);

        getChildren().addAll(vbox);
    }

}





