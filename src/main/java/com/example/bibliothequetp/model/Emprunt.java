package com.example.bibliothequetp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Emprunt {    // un emprunt dans l'interface c'est  : titre, dateDebut, DateFIn, usager (nom/prenom)
    public String dateFin;
    public String dateDebut;
    public int idSupport;
    public int idUsager;
    public String nom;
    public String prenom;
    public String titre;

    public Emprunt(String debut, String fin, int support, int usager) {
        this.dateFin = fin;
        this.dateDebut = debut;
        this.idUsager = usager;
        this.idSupport = support;

        try {
            Usager u = new Usager(usager);
            Livre l = new Livre(support);
            this.nom = u.nom;
            this.prenom = u.prenom;
            this.titre = l.titre;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean empruntEnCours() {
        if (this.dateFin == null) {
            return true;
        } else {
            return false;
        }
    }

    public String getTitre(){return this.titre;}
    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDateFin(){return this.dateFin;}
    public String getDateDebut(){return this.dateDebut;}

}

