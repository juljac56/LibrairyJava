package com.example.bibliothequetp.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class Usager {

    public Integer idUsager;
    public String nom;
    public String prenom;
    public String mail;
    public int categorie;
    public Vector<Vector<String>> historique = new Vector<Vector<String>>();  // historique  = [titre, édition, date début, date fin d'emprunt]
    public int nbFoisListeRouge;

    public Usager(int id) throws SQLException {
        this.idUsager = id;

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

        conn.close();
    }

    public int supprimer(){
        int succes  = 0;
        try {
            Connection conn = DataBase.getConnection();
            PreparedStatement ps0 = conn.prepareStatement("delete from Usager where `ID USAGER`=?");
            ps0.setInt(1,this.idUsager);
            succes = ps0.executeUpdate();
            conn.close();
        }
        catch(Exception e){System.out.println(e);}

        return succes;
    };

    public boolean possibleEmprunt() {   // retourne un boolean indiquant si c'est possible pour un usager de faire un emprunt en fonction de la listeRouge et de sa catégorie
        int nb = new Categorie(this.categorie).nbMax;
        Integer nbActuelsEmprunts = 0;
        try {
            Connection conn = DataBase.getConnection();
            PreparedStatement ps = conn.prepareStatement("select COUNT(*) from `Emprunt`  WHERE `ID USAGER` = ? AND `Date FIN` = ?");
            ps.setInt(1, this.idUsager);
            ps.setString(2, "");

            ResultSet rs = ps.executeQuery();
            Integer nbActuelEmprunts = rs.getInt(1);

            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return (nb>nbActuelsEmprunts) && !(this.surLR());
    }

    public boolean surLR(){
        try {
            Connection conn = DataBase.getConnection();
            PreparedStatement ps = conn.prepareStatement("select `ID Liste` from `ListeRouge/Usager` where `ID Usager` = ?");
            ps.setInt(1, this.idUsager);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int idListe = rs.getInt(1);
                PreparedStatement ps1 = conn.prepareStatement("select * from LISTEROUGE where `ID LISTEROUGE` = ?");
                ps1.setInt(1, idListe);
                ResultSet rs1 = ps1.executeQuery();

                while (rs1.next()){
                    String dateFin = rs1.getString(3);
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    String date = df.format(new Date());
                    String[] finTableau = dateFin.split("/");
                    String[] currentTableau = date.split("/");
                    Collections.reverse(List.of(finTableau));
                    Collections.reverse(List.of(currentTableau));

                    if (Integer.valueOf(finTableau[0]) > Integer.valueOf(currentTableau[0])){
                        return true;
                    }
                    else if ((Integer.valueOf(finTableau[0]) == Integer.valueOf(currentTableau[0]))  )
                            { if (Integer.valueOf(finTableau[1]) > Integer.valueOf(currentTableau[1]) ) {
                                    return true;}

                            else if (Integer.valueOf(finTableau[1]) == Integer.valueOf(currentTableau[1])){
                                if (Integer.valueOf(finTableau[2]) > Integer.valueOf(currentTableau[2]) ){
                                        return true;}
                    }}}}

            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
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

    public Integer getIdUsager() {
        return idUsager;
    }

    public int getNbFoisListeRouge() {
        return nbFoisListeRouge;
    }
}