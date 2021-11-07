package com.example.bibliothequetp.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;

public class ListeRouge {

    public String nom;
    public String prenom;
    public String dateDebut;
    public String dateFin;
    public int idListeR;
    public int idUsager;


    public ListeRouge(int id){

        this.idListeR = id;

        try {
            Connection conn = DataBase.getConnection();
            PreparedStatement ps0 = conn.prepareStatement("select * from LISTEROUGE WHERE `ID LISTEROUGE` = ?");
            ps0.setInt(1,id);

            ResultSet rs0 = ps0.executeQuery();
            this.dateDebut = rs0.getString(2);
            this.dateFin = rs0.getString(3);

            PreparedStatement ps1 = conn.prepareStatement("select * from `ListeRouge/Usager` WHERE `ID Liste` = ?");
            ps1.setInt(1,id);

            ResultSet rs1 = ps1.executeQuery();

            while (rs1.next()){    // gives title and year
                this.idUsager = rs1.getInt(2);

                Usager u = new Usager(this.idUsager);
                this.nom = u.nom;
                this.prenom = u.prenom;
                System.out.println("Id Usager ::: " + u.nom);

            }
            conn.close();
        }

        catch(Exception e){System.out.println(e);}
    }

    public static ObservableList<ListeRouge>  listeRougeUsager(int idU){  // retourne l'historique de liste rouge pour un usager donné (son id)
        ObservableList<ListeRouge> liste = FXCollections.observableArrayList();

        try {
            Connection conn = DataBase.getConnection();
            PreparedStatement ps2 = conn.prepareStatement("select `ID Liste` from `ListeRouge/Usager` WHERE `ID USAGER` = ?");
            ps2.setInt(1,idU);

            ResultSet rs2 = ps2.executeQuery();

            while (rs2.next()){    // gives title and year
                int idLr = rs2.getInt(1);
                ListeRouge LR = new ListeRouge(idLr);
                liste.add(LR);
            }
            conn.close();
        }
        catch(Exception e){System.out.println(e);}
        return liste;
    }

    public static ObservableList<ListeRouge>  listeRougeTotal(){  // retourne l'historique de liste rouge pour un usager donné (son id)
        ObservableList<ListeRouge> liste = FXCollections.observableArrayList();

        try {
            Connection conn = DataBase.getConnection();
            PreparedStatement ps2 = conn.prepareStatement("select `ID LISTEROUGE` from LISTEROUGE");

            ResultSet rs2 = ps2.executeQuery();

            while (rs2.next()){
                int idLr = rs2.getInt(1);
                ListeRouge LR = new ListeRouge(idLr);
                liste.add(LR);
            }
            conn.close();
        }
        catch(Exception e){System.out.println(e);}
        return liste;
    }

    public int supprimer(){
        int succes  = 0;
        try {
            Connection conn = DataBase.getConnection();
            PreparedStatement ps0 = conn.prepareStatement("delete from LISTEROUGE where `ID LISTEROUGE`=?");
            ps0.setInt(1,this.idListeR);
            succes = ps0.executeUpdate();

            PreparedStatement ps1 = conn.prepareStatement("delete from LISTEROUGE where `ID Liste`=?");
            ps1.setInt(1,this.idListeR);
            succes = ps1.executeUpdate();

            conn.close();
        }
        catch(Exception e){System.out.println(e);}

        return succes;
    };

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getIdUsager() {
        return idUsager;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public int getIdListeR(){
        return idListeR;
    }
}

