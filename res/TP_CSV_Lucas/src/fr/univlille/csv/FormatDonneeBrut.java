package fr.univlille.csv;
// String / String / Date / Enum(Homme/Femme/Autre) / Double / Int / Boolean
import java.time.LocalDate;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

public class FormatDonneeBrut {
    
    @CsvBindByName(column = "Nom")
    private String nom;

    @CsvBindByName(column = "Prénom")
    private String prenom;

    @CsvBindByName(column = "Date de naissance")
    @CsvDate("yyyy-MM-dd")
    private LocalDate dateNaiss;

    @CsvBindByName(column = "Genre")
    private Genre type;

    @CsvBindByName(column = "Taille")
    private Double taille;

    @CsvBindByName(column = "Score")
    private int score;

    @CsvBindByName(column = "Souscription")
    private OuiNon souscris;

    public String toString(){
        return nom + ';' + prenom + ';' + dateNaiss.toString() + ';' + type.getName() + ';' + taille + ';' + score + ';' + souscris.getName();
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public LocalDate getDateNaiss() {
        return dateNaiss;
    }

    public Genre getType() {
        return type;
    }

    public Double getTaille() {
        return taille;
    }

    public int getScore() {
        return score;
    }

    public OuiNon getSouscris() {
        return souscris;
    }
}