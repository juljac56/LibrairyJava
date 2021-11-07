package com.example.bibliothequetp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Emprunt {    // un emprunt dans l'interface c'est  : titre, dateDebut, DateFIn, usager (nom/prenom)
    public String dateFin;
    public String dateDebut;
    public int idSupport;
    public int idUsager;
    public String nom;
    public String prenom;
    public String titre;
    public int cat;
    public String rendrepour;

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
            this.cat = u.categorie;
            this.rendrepour = rendrePour(dateDebut,cat);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        String dateSupposeFin = rendrePour(dateDebut,cat);

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String date = df.format(new Date());
        String[] finTableau = dateSupposeFin.split("/");
        String[] currentTableau = date.split("/");
        Collections.reverse(List.of(finTableau));
        Collections.reverse(List.of(currentTableau));


    }

    public boolean empruntRendu() {
        if (this.dateFin == null || this.dateFin.length() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public String getTitre(){return this.titre;}
    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDateFin(){return this.dateFin;}
    public String getDateDebut(){return this.dateDebut;}

    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return nom;
    }

    public String getRendrepour() {
        return rendrepour;
    }

    public static String rendrePour(String dateDebut, int idCat ){

        String[] debutTableau = dateDebut.split("/");
        System.out.println(debutTableau[1]);
        Categorie cat = new Categorie(idCat);
        String fin = "";
        Integer JoursPlus = cat.dureeMax;

        boolean moisajout = false;
        boolean anneeajout = false;

        int j = Integer.parseInt(debutTableau[0]);
        int m = Integer.parseInt(debutTableau[1]);
        System.out.println(m);
        int a = Integer.parseInt(debutTableau[2]);

        if (m != 2){
        if (JoursPlus+j <= 30 ) {
            Integer somme = j+JoursPlus;
            fin += somme.toString() +"/"+m+"/"+a;}
        else{
            if (m<=11){
                m =m+1;
                Integer reste = 30-JoursPlus-j;
                fin += reste.toString() +"/"+m+"/"+a;}

            else{
                m = 1;
                a = a+1;
                Integer reste = 30-JoursPlus-j;
                fin += reste.toString() +"/"+m+"/"+a;}
            }
        }
        else{
            if (JoursPlus+j <= 28 ) {
                Integer somme = j+JoursPlus;
                fin += somme.toString() +"/"+m+"/"+a;}
            else{
                    m =m+1;
                    Integer reste = 28-JoursPlus-j;
                    fin += reste.toString() +"/"+m+"/"+a;}
        }
        return fin;
        }

    }


