package com.example.bibliothequetp.controller;

import com.example.bibliothequetp.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.TableView;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;


// Controller pour gérer les emprunts et rendus de support des clients

public class EmpruntController {

    public Vector<Emprunt> emprunts;   // tous les emprunts passés et en cours seront contenus dans ce vecteur

    public EmpruntController() {
        this.emprunts = new Vector<Emprunt>();
        try {
            Connection conn = DataBase.getConnection();
            PreparedStatement ps = conn.prepareStatement("select * from EMPRUNT");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String dateDebut = rs.getString(1);
                String dateFin = rs.getString(2);
                int idSupport = rs.getInt(3);
                int idUsager = rs.getInt(4);
                Emprunt emprunt = new Emprunt(dateDebut, dateFin, idSupport, idUsager);
                emprunts.add(emprunt);
            }
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }}

    public ObservableList<Emprunt> listeEmpruntsActuels() {   // fonction qui renvoie les emprunts en cours, pas encore terminés
        ObservableList<Emprunt> liste = FXCollections.observableArrayList();

        Iterator val = this.emprunts.iterator();
        while (val.hasNext()) {
            Emprunt e = (Emprunt) val.next();
            if (!e.empruntRendu()) {
                liste.add(e);}}

        return liste;
    }

    public Vector<Emprunt> historiqueEmprunts(){  // retourne l'historique des tous les emprunts actuels et passés
        return this.emprunts;
    }

    public ObservableList<Emprunt> empruntUsager(int id){      // retourne l'historique d'emprunt pour un usager donné
        ObservableList<Emprunt> liste = FXCollections.observableArrayList();
        Iterator val = this.emprunts.iterator();
        while (val.hasNext()) {
            Emprunt e = (Emprunt) val.next();
            if (e.idUsager == id) {
                liste.add(e);
            }}
        return liste;}

    public ObservableList<Emprunt> empruntUsagerActuel(int id){      // retourne les emprunts en cours pour un usager donné
        ObservableList<Emprunt> liste = FXCollections.observableArrayList();
        Iterator val = this.emprunts.iterator();
        while (val.hasNext()) {
            Emprunt e = (Emprunt) val.next();
            if (e.idUsager == id && !e.empruntRendu()) {
                liste.add(e);
            }}
        return liste; }

    public Integer debutCreerEmprunt(TableView table){  // méthode appelée après qu'un support (livre) ait été sélectionné
        Vector<Integer> v = new Vector<>();
        Livre l = (Livre) table.getSelectionModel().getSelectedItem();
        Integer idLivre = l.getId();
        return idLivre;
    }

    public boolean verifierEmprunt(int id ){ // renvoie si un livre peut etre emprunté ou si il l'est deja
        // en fonction de s'il est deja emprunté
        boolean rendu = true;
        try{
            Connection conn = DataBase.getConnection();
            PreparedStatement ps5 = conn.prepareStatement("select * from EMPRUNT where `ID SUPPORT` = ?");
            ps5.setInt(1,id);
            ResultSet rs5 = ps5.executeQuery();

            while (rs5.next()) {
                String dateDebut = rs5.getString(1);
                String dateFin = rs5.getString(2);
                int idUsager = rs5.getInt(4);
                Emprunt emprunt = new Emprunt(dateDebut, dateFin, id, idUsager);
                rendu = rendu && emprunt.empruntRendu();
            }
            conn.close();

        } catch (Exception e) {
            System.out.println(e);}

        return rendu;}

    public int creerEmprunt(TableView table, Integer idLivre){  // Sert a ajouter un emprunt dans la BDD
        Usager u = (Usager) table.getSelectionModel().getSelectedItem();
        int idUs = u.idUsager;
        int status = 0;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String date = df.format(new Date());
        String dateFin = Emprunt.rendrePour(date, u.categorie);
        if (u.possibleEmprunt()){ // Vérifie si la personne voulant l'emprunter est sur liste rouge ou a déjà emprunter trop de livres.
            System.out.println("Emprunt possible !");
            try {
                Connection conn = DataBase.getConnection();
                PreparedStatement ps = conn.prepareStatement("INSERT INTO EMPRUNT(`DATE Debut`,`Date FIN`, `ID SUPPORT`, `ID USAGER`) VALUES(?,?,?,?)");
                ps.setString(1,date);
                ps.setString(2,"");
                ps.setInt(3,idLivre);
                ps.setInt(4,idUs);
                status = ps.executeUpdate();
                System.out.println(status);
                conn.close();
            } catch (Exception e) {
                System.out.println(e); }
        }
        else {
            System.out.println("Emprunt impossible !");}
        return status;}

    public void rendreLivre (TableView table){  // Méthode appelée après qu'un emprunt ait été sélectionné pour être rendu
        Emprunt l = (Emprunt) table.getSelectionModel().getSelectedItem();
        int succes = 0;
        try {
            Connection conn = DataBase.getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE `EMPRUNT` SET `Date FIN` = ? WHERE `ID SUPPORT` = ?");
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");  // prend la date d'ajourd'hui pour la date de retour
            String date = df.format(new Date());
            ps.setString(1,date);
            ps.setInt(2,l.idSupport);
            succes = ps.executeUpdate();
            conn.close();

        } catch (Exception e) {
            System.out.println(e); }
    }

    public Vector<Object> debutModifierLivre(TableView table){ // méthode appelée après qu'un livre ait été modifié pour modification
        Vector<Object> v = new Vector<>();
        Livre l = (Livre) table.getSelectionModel().getSelectedItem();
        int id = l.getId();
        String titre = l.titre;
        int annee = l.annee;
        int idOeuvre = l.getIdOeuvre();
        String mot1 = l.mot1;
        String mot2 = l.mot2;
        String mot3 = l.mot3;
        String mot4 = l.mot4;
        String mot5 = l.mot5;

        v.add(id);
        v.add(titre);
        v.add(annee);
        v.add(mot1);
        v.add(mot2);
        v.add(mot3);
        v.add(mot4);
        v.add(mot5);
        v.add(idOeuvre);

        return v;  // renvoie un vector contenant les données nécessaires pour la modification du livre dans la BDD
    }

    public int modifierLivre(int id,String titre,String mot1,String mot2,String mot3,String mot4, String mot5, int annee, int idOeuvre){
        int succes = 0;
        try {
            Connection conn = DataBase.getConnection();

            PreparedStatement ps = conn.prepareStatement("UPDATE OEUVRE SET TITRE = ? ,ANNEE = ?, MOT1 = ?, MOT2 = ?, MOT3 = ? , MOT4 = ?, MOT5 = ? WHERE `ID OEUVRE` = ?;");
            ps.setString(1,titre);
            ps.setInt(2,annee);
            ps.setString(3,mot1);
            ps.setString(4,mot2);
            ps.setString(5,mot3);
            ps.setString(6,mot4);
            ps.setString(7, mot5);
            ps.setInt(8,idOeuvre);
            succes = ps.executeUpdate();
            conn.close();

        } catch (Exception e) {
            System.out.println(e);}
        return succes;
    }
}
