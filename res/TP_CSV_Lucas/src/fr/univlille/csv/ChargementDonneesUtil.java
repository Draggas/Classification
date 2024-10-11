package fr.univlille.csv;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.nio.file.*;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;

public class ChargementDonneesUtil {

    public static List<FormatDonneeBrut> charger(String fileName) throws IOException {
        return new CsvToBeanBuilder<FormatDonneeBrut>(Files.newBufferedReader(Paths.get(fileName)))
                .withSeparator(';')
                .withType(FormatDonneeBrut.class)
                .build().parse();
    }

    public static Personne createPersonne(FormatDonneeBrut d){
        boolean souscription = false;
        if(d.getSouscris().getName().equals("oui")) souscription = true;
        Personne p = new Personne(d.getPrenom() + " " + d.getNom(), d.getDateNaiss(), d.getType(), (int) (d.getTaille()*100), d.getScore(), souscription );
        return p;
    }

    public static double normaliser_0_1(double valeur, double min, double max){
        return (valeur-min)/(max-min);
    }

    public static List<Personne> personnesNormalisees (List<FormatDonneeBrut> donnees){
        List<Personne> p = new ArrayList<>();
        double scoreMin = 9999;
        double scoreMax = 0;
        for(FormatDonneeBrut f : donnees){
            if(f.getScore() > scoreMax) {
                scoreMax = f.getScore();
            }
            if(f.getScore() < scoreMin) {
                scoreMin = f.getScore();
            }
            p.add(createPersonne(f));
        }
        for(Personne personne : p) {
            personne.setScoreNormalise(normaliser_0_1(personne.getScoreNormalise(), scoreMin, scoreMax));
        }
        return p;
    }
}