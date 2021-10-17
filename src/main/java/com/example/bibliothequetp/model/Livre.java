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

    public Livre(int id) throws SQLException {   // id du support
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

        conn.close();

    }


    public String listAuteur(){  // renvoie un string de tous les auteurs (Nom puis prenoms)
        String aut = "";
        Iterator val = this.auteur.iterator();
        while (val.hasNext()){
            aut = aut + val.next();
        }
        return aut;
    }


    public static int ajoutlivreBDD(String titre, int annee, int ISBN, Vector<String> auteurp, Vector<String> auteurn, String edition, String mot1) {

        int succes = 0;
        int idEditeurAjout;
        int idOeuvreAjout;
        Vector<Integer> idAuteurAjout  =new Vector<Integer>();

        try {
            Connection conn = DataBase.getConnection();

            for (int i = 0; i < auteurp.size(); i++) {
                String prenom = auteurp.get(i);
                String nom = auteurn.get(i);

                // on cherche (et créé si besoin) les id des auteurs du nouveau livre
                PreparedStatement ps = conn.prepareStatement("SELECT `ID AUTEUR` FROM AUTEUR WHERE NOM = ? and PRENOM = ? ");
                ps.setString(1, nom);
                ps.setString(2, prenom);
                ResultSet rs = ps.executeQuery();


                if (rs.next() == false) {
                    PreparedStatement ps1 = conn.prepareStatement("INSERT INTO AUTEUR(NOM, PRENOM) values (?,?) ");
                    ps1.setString(1, nom);
                    ps1.setString(2, prenom);
                    ps1.executeUpdate();
                }

                PreparedStatement ps0 = conn.prepareStatement("SELECT `ID AUTEUR` FROM AUTEUR WHERE NOM = ? and PRENOM = ? ");
                ps0.setString(1, nom);
                ps0.setString(2, prenom);
                ResultSet rs0 = ps0.executeQuery();
                idAuteurAjout.add(rs0.getInt(1));
            }

            // on cherche (et créé si besoin) l'id de l'editeur du nouveau livre
            PreparedStatement ps2 = conn.prepareStatement("SELECT `ID EDITEUR` FROM EDITEUR WHERE NOM = ? ");
            ps2.setString(1, edition);
            ResultSet rs2 = ps2.executeQuery();


            if (rs2.next() == false) {
                PreparedStatement ps3 = conn.prepareStatement("INSERT INTO EDITEUR(NOM) values (?) ");
                ps3.setString(1, edition);
                ps3.executeUpdate();
            }

            PreparedStatement ps21 = conn.prepareStatement("SELECT `ID EDITEUR` FROM EDITEUR WHERE NOM = ? ");
            ps21.setString(1, edition);
            ResultSet rs21 = ps21.executeQuery();
            idEditeurAjout = rs21.getInt(1);


            // on cherche (et créé si besoin) l'id de l'oeuvre du nouveau livre, car SUPPORT != OEUVRE
            PreparedStatement ps4 = conn.prepareStatement("SELECT `ID OEUVRE` FROM OEUVRE WHERE TITRE = ? AND ANNEE = ? ");
            ps4.setString(1, titre);
            ps4.setInt(2, annee);
            ResultSet rs4 = ps4.executeQuery();


            if (rs4.next() == false) {
                PreparedStatement ps5 = conn.prepareStatement("INSERT INTO OEUVRE(TITRE, ANNEE, MOT1) values (?,?,?) ");
                ps5.setString(1, titre);
                ps5.setInt(2, annee);
                ps5.setString(3, mot1);
                ps5.executeUpdate();
            }
            PreparedStatement ps51 = conn.prepareStatement("SELECT `ID OEUVRE` FROM OEUVRE WHERE TITRE = ? AND ANNEE = ?  ");
            ps51.setString(1, titre);
            ps51.setInt(2, annee);
            ResultSet rs51 = ps51.executeQuery();
            idOeuvreAjout = rs51.getInt(1);


            // on cherche (et créé si besoin) l'ISBN de l'édition du nouveau livre
            PreparedStatement ps6 = conn.prepareStatement("SELECT `ISBN` FROM EDITION WHERE ISBN = ? ");
            ps6.setInt(1, ISBN);
            ResultSet rs6 = ps6.executeQuery();


            if (rs6.next() == false) {
                PreparedStatement ps61 = conn.prepareStatement("INSERT INTO EDITION(ISBN,`ID OEUVRE`,`ID EDITEUR`) values (?,?,?) ");
                ps61.setInt(1, ISBN);
                ps61.setInt(2, idOeuvreAjout);
                ps61.setInt(3, idEditeurAjout);
                ps61.executeUpdate();
            }

            // Creation des relations auteurs/livre
            Iterator val = idAuteurAjout.iterator();
            while (val.hasNext()){
                PreparedStatement ps7 = conn.prepareStatement("INSERT INTO `OEUVRE/AUTEUR`(`ID OEUVRE`,`ID AUTEUR`) values (?,?) ");
                ps7.setInt(1, idOeuvreAjout);
                ps7.setInt(2, (Integer) val.next());
                ps7.executeUpdate();

            }

            // creation du support dans la table Support
            PreparedStatement ps8 = conn.prepareStatement("INSERT INTO SUPPORT(ISBN) values (?) ");
            ps8.setInt(1, ISBN);
            succes = ps8.executeUpdate();

            conn.close();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return succes;
    }

    public void supprimer(){
        try {
            Connection conn = DataBase.getConnection();
            PreparedStatement ps= conn.prepareStatement("delete from SUPPORT where `ID SUPPORT` = ? ");
            ps.setInt(1, this.id);
            ps.executeUpdate();
            conn.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }


            // Supprimer un livre c'est : supprimer le support (le reste est gardé)
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


