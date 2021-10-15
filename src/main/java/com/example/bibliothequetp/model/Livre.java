package com.example.bibliothequetp.model;

import javafx.beans.property.SimpleStringProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

public class Livre {
    public String titre;
    public int annee;
    public int id;
    public Vector<String> auteur;
    public String editeur;
    public int ISBN;
    int idOeuvre;
    int idEditeur;
    public boolean emprunte;

    public Livre(int id) throws SQLException {   // id du support et ISBN de l'édition
        this.id = id;

        Connection conn = DataBase.getConnection();
        PreparedStatement ps0 = conn.prepareStatement("select ISBN from SUPPORT WHERE `ID SUPPORT` = ? ");
        ps0.setInt(1,id);

        ResultSet rs0 = ps0.executeQuery();

        this.ISBN = rs0.getInt(1);



        this.ISBN = ISBN;
        this.auteur = new Vector<String>();



        PreparedStatement ps = conn.prepareStatement("select * from  EDITION WHERE ISBN = ? ");
        ps.setInt(1,ISBN);

        ResultSet rs = ps.executeQuery();

        while (rs.next()){  // gives id oeuvre and idediteur
            System.out.println("ID OEUVRE " +rs.getString(2));
            idOeuvre = rs.getInt(2);
            idEditeur = rs.getInt(3);
        }

        PreparedStatement ps1 = conn.prepareStatement("select * from  OEUVRE WHERE `ID OEUVRE` = ? ");
        ps1.setInt(1,idOeuvre);
        ResultSet rs1 = ps1.executeQuery();

        while (rs1.next()){    // gives title and year
            System.out.println("Titre oeuvre " +rs1.getString(3));
            this.titre = rs1.getString(2);
            System.out.println("titre" + this.titre);
            this.annee = rs1.getInt(3);

        }

        PreparedStatement ps2 = conn.prepareStatement("select * from  EDITEUR WHERE `ID EDITEUR` = ? ");
        ps2.setInt(1,idEditeur);
        ResultSet rs2 = ps2.executeQuery();

        while (rs2.next()){   // gives name of editor
            System.out.println("Nom editeur " +rs2.getString(2));
            this.editeur = rs2.getString(2);
        }

        PreparedStatement ps3 = conn.prepareStatement("select * from  `OEUVRE/AUTEUR` WHERE `ID OEUVRE` = ? ");
        ps3.setInt(1,idOeuvre);
        ResultSet rs3 = ps3.executeQuery();

        while (rs3.next()){    // gives id(s) of writer(s)

            int idAuteur = rs3.getInt(2);
            PreparedStatement ps4 = conn.prepareStatement("select NOM, PRENOM from  AUTEUR WHERE `ID AUTEUR` = ? ");
            ps4.setInt(1,idAuteur);
            ResultSet rs4 = ps4.executeQuery();

            while (rs4.next()) {
                this.auteur.add(rs4.getString(1) +", "+ rs4.getString(2) +" ");
            }
        }

    }


    public String listAuteur(){  // renvoie un string de tous les auteurs (Nom puis prenoms)
        String aut = "";
        Iterator val = this.auteur.iterator();
        while (val.hasNext()){
            aut = aut + val.next();

        }

        return aut;

    }

    public String getTitre(){
        return this.titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getEditeur(){
        return this.editeur;
    }

    public void setEditeur(String editeur) {
        this.editeur = editeur;
    }

    /* public String getAuteurp(){
        return this.titre;
    }

    public void setAuteurp(String titre) {
        this.titre = titre;
    }

    public String getAuteurp(){
        return this.titre;
    }

    public void setAuteurp(String titre) {
        this.titre = titre;
    }

     */

    public int getId(){return this.id;}
    public int getAnnee(){
        return this.annee;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }
}


