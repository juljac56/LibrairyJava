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
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomeClient extends CycledView {

    ObservableList<Emprunt> data;
    MainController controller = new MainController();
    public EmpruntController empruntController;
    TableView table = new TableView();
    Usager u;

    public HomeClient(Stage stage, Usager u) {
        super(stage);
        this.u = u;
        this.empruntController = new EmpruntController();
        this.retour = new home(stage, u);
        createGUI();}

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
            public void fire() {goReserverLivreC(stage,u);}
        };

        Button btnR = new Button("Retour") {
            @Override
            public void fire() {
                goLoginPage(stage);
            }};

        Button btnHistoriqueEmprunt = new Button("Historique de vos Emprunts") {
            @Override
            public void fire() {goHistoriqueEmpruntUsager(stage, u);
            }
        };

        btn.getStyleClass().add("btn");
        gp.add(btn, 0, 0);
        gp.add(btnHistoriqueEmprunt, 1, 0);
        gp.add(btnR, 2,0);

        getChildren().add(gp);

        TableColumn titreCol = new TableColumn<Livre, String>("titre");
        titreCol.setCellValueFactory(new PropertyValueFactory<Livre, String>("titre"));
        TableColumn titreDateDeb = new TableColumn<Livre, String>("Debut Emprunt");
        titreDateDeb.setCellValueFactory(new PropertyValueFactory<Livre, String>("dateDebut"));
        TableColumn titreDateFin = new TableColumn<Emprunt, String>("A rendre pour ");
        titreDateFin.setCellValueFactory(new PropertyValueFactory<Emprunt, String>("rendrepour"));

        try {
            data = empruntController.empruntUsagerActuel(u.getIdUsager());
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





