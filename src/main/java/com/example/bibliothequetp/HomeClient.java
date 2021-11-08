package com.example.bibliothequetp;

import com.example.bibliothequetp.controller.CycledView;
import com.example.bibliothequetp.controller.EmpruntController;
import com.example.bibliothequetp.controller.MainController;
import com.example.bibliothequetp.model.Categorie;
import com.example.bibliothequetp.model.Emprunt;
import com.example.bibliothequetp.model.Livre;
import com.example.bibliothequetp.model.Usager;
import javafx.beans.binding.Bindings;
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

        Text textBienvenue = new Text("Bienvenue "+ u.getPrenom()+ " "+ u.getNom());
        Categorie c = new Categorie(u.getCategorie());
        Text textCat = new Text("Vous appartenez à la catégorie : " + u.getCategorie()+ ", vous pouvez emprunter au total : "+c.nbMax+" livres" ) ;
        System.out.println(u.nbActuelEmprunt);
        Text textEmprunt = new Text("Vous pouvez encore emprunter : " + (c.nbMax-u.nbActuelEmprunt)+" livre(s)");

        Text textLR = new Text("Vous n'êtes pas sur liste rouge, vous pouvez emprunter des livres");
        if (u.surLR()){
            textLR.setText("Vous êtes actuellement sur liste rouge, vous ne pouvez pas emprunter de livres");
        }


        Button btn = new Button("Consulter le catalogue") {
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

        gp.add(text, 0, 0);
        gp.add(textBienvenue,0,1 );
        gp.add(btn, 0, 2);
        gp.add(btnHistoriqueEmprunt, 1, 2);
        gp.add(btnR, 2,2);
        gp.add(textCat,0,3,3,1);
        gp.add(textEmprunt,0,4,2,1);
        gp.add(textLR,0,5,3,1);
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
            table.setFixedCellSize(35);
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            table.prefHeightProperty().bind(Bindings.size(table.getItems()).multiply(table.getFixedCellSize()).add(30));
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





