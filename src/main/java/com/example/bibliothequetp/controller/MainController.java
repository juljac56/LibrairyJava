package com.example.bibliothequetp.controller;

import com.example.bibliothequetp.model.Livre;
import javafx.scene.control.TableView;

import java.sql.SQLException;


// controller test, pas important

public class MainController {

    TableView table ;
    public void Test(){
        System.out.println("Hello World ! ");
    }

    public void consulterLivres() throws SQLException {
        /*ConsulterLivres cl = new ConsulterLivres();
        cl.start(stage);
         */
        //getScene().setRoot(next);
    }
    public void reservation(TableView table){
        Livre l = (Livre) table.getSelectionModel().getSelectedItem();
        System.out.println(l.getTitre());
    }
}
