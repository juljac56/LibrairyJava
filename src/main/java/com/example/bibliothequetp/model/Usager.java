package com.example.bibliothequetp.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class Usager {

    public String nom;
    public String prenom;
    public String mail;
    public int categorie;
    public Vector<Vector<String>> historique = new Vector<Vector<String>>();  // historique  = [titre, édition, date début, date fin d'emprunt]
    public int nbFoisListeRouge;

    public Usager(int id) throws SQLException {

        Connection conn = DataBase.getConnection();
        PreparedStatement ps = conn.prepareStatement("select * from  USAGER WHERE `ID USAGER` = ?");
        ps.setInt(1,id);

        ResultSet rs = ps.executeQuery();

        while (rs.next()){  // gives id oeuvre and idediteur
            this.nom = rs.getString(2);
            this.prenom = rs.getString(3);
            this.mail = rs.getString(4);
            this.categorie = rs.getInt(5);

            System.out.println(this.nom);

        }

        PreparedStatement ps1 = conn.prepareStatement("select * from  EMPRUNT WHERE `ID USAGER` = ?");
        ps1.setInt(1,id);

        ResultSet rs1 = ps1.executeQuery();
        while (rs1.next()){
            Vector<String> ligne = new Vector<String>();
            System.out.println(rs1.getInt(3));
            Livre livre = new Livre(rs1.getInt(3));
            ligne.add(livre.titre);
            ligne.add(livre.editeur);
            ligne.add(rs1.getString(1));
            ligne.add(rs1.getString(2));
            historique.add(ligne);
        }

        PreparedStatement ps2 = conn.prepareStatement("select COUNT(*) from `ListeRouge/Usager`  WHERE `ID USAGER` = ?");
        ps2.setInt(1,id);

        ResultSet rs2 = ps2.executeQuery();
        this.nbFoisListeRouge = rs2.getInt(1);


    }

    public String getPrenom() {
        return this.prenom;
    }

    public String getMail() {
        return mail;
    }

    public String getNom() {
        return nom;
    }

    public int getCategorie() {
        return categorie;
    }

    public int getNbFoisListeRouge() {
        return nbFoisListeRouge;
    }
}