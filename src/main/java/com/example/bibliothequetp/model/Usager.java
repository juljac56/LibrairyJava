package com.example.bibliothequetp.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

// Classe définissant les clients de la BDD

public class Usager {

    public String username;
    public String mdp;
    public Integer idUsager;
    public String nom;
    public String prenom;
    public String mail;
    public int categorie;
    public Vector<Vector<String>> historique = new Vector<Vector<String>>();  // historique  = [titre, édition, date début, date fin d'emprunt]
    public int nbFoisListeRouge;
    public int nbActuelEmprunt;

    public Usager(int id){

        try {
            this.idUsager = id;

            Connection conn = DataBase.getConnection();
            PreparedStatement ps = conn.prepareStatement("select * from  USAGER WHERE `ID USAGER` = ?");
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {  // gives idOeuvre and idEditeur
                this.nom = rs.getString(2);
                this.prenom = rs.getString(3);
                this.mail = rs.getString(4);
                this.categorie = rs.getInt(5);
                this.username = rs.getString(6);
                this.mdp = rs.getString(7);
            }

            PreparedStatement ps1 = conn.prepareStatement("select * from  EMPRUNT WHERE `ID USAGER` = ?");
            ps1.setInt(1, id);

            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                Vector<String> ligne = new Vector<String>();
                Livre livre = new Livre(rs1.getInt(3));
                ligne.add(livre.titre);
                ligne.add(livre.editeur);
                ligne.add(rs1.getString(1));
                ligne.add(rs1.getString(2));
                historique.add(ligne);
            }

            PreparedStatement ps2 = conn.prepareStatement("select COUNT(*) from `ListeRouge/Usager`  WHERE `ID USAGER` = ?");
            ps2.setInt(1, id);

            ResultSet rs2 = ps2.executeQuery();
            this.nbFoisListeRouge = rs2.getInt(1);


            PreparedStatement ps3 = conn.prepareStatement("select COUNT(*) from `Emprunt`  WHERE `ID USAGER` = ? AND `Date FIN` = ?");
            ps3.setInt(1, this.idUsager);
            ps3.setString(2, "");
            ResultSet rs3 = ps3.executeQuery();
            this.nbActuelEmprunt = rs3.getInt(1);

            conn.close();
        }
        catch (Exception e){System.out.println(e);}
    }

    public int supprimer(){  // suppression d'un usager de la BDD
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
        Integer nbActuelEmprunts = 0;
        Integer nbActuelsEmprunts = 0;
        try {
            Connection conn = DataBase.getConnection();
            PreparedStatement ps = conn.prepareStatement("select COUNT(*) from `Emprunt`  WHERE `ID USAGER` = ? AND `Date FIN` = ?");
            ps.setInt(1, this.idUsager);
            ps.setString(2, "");

            ResultSet rs = ps.executeQuery();
            nbActuelEmprunts = rs.getInt(1);
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return (nb>nbActuelsEmprunts) && !(this.surLR());}

    public boolean surLR(){  // verifier si l'usager en question est sur liste rouge, et ne peut donc pas emprunter de livres
        boolean surlr = false;
        try {
            Connection conn = DataBase.getConnection();
            PreparedStatement ps = conn.prepareStatement("select `ID Liste` from `ListeRouge/Usager` where `ID Usager` = ?");
            ps.setInt(1, this.idUsager);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int idListe = rs.getInt(1);
                PreparedStatement ps1 = conn.prepareStatement("select * from LISTEROUGE where `ID LISTEROUGE` = ?");
                ps1.setInt(1, idListe);
                System.out.println("idliste"+ idListe);
                ResultSet rs1 = ps1.executeQuery();

                while (rs1.next()){
                    String dateFin = rs1.getString(3);
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    String date = df.format(new Date());
                    String[] finTableau = dateFin.split("/");
                    String[] currentTableau = date.split("/");
                    List<String> listft = Arrays.asList(finTableau);
                    List<String> listct = Arrays.asList(currentTableau);
                    Collections.reverse(listft);
                    Collections.reverse(listct);

                    if (Integer.valueOf(listft.get(0)) > Integer.valueOf(listct.get(0))){
                        surlr = true;
                    }
                    else if ((Integer.valueOf(listft.get(0)) >= Integer.valueOf(listct.get(0)))  )
                            { if (Integer.valueOf(listft.get(1)) > Integer.valueOf(listct.get(1)) ) {
                                surlr = true;}
                            else if (Integer.valueOf(listft.get(1)) >= Integer.valueOf(listct.get(1))){
                                if (Integer.valueOf(listft.get(2)) > Integer.valueOf(listct.get(2)) ){
                                    surlr = true;}
                    }}}}
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(surlr);
        return surlr;}

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
    public String getMdp() { return mdp; }
    public String getUsername() {return username;}
}