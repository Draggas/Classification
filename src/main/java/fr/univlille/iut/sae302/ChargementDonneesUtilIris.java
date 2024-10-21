package fr.univlille.iut.sae302;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class ChargementDonneesUtilIris {

    public static List<FormatDonneeBrutIris> charger(String fileName) throws IOException {
        return new CsvToBeanBuilder<FormatDonneeBrutIris>(Files.newBufferedReader(Paths.get(fileName)))
                .withSeparator(',')
                .withType(FormatDonneeBrutIris.class)
                .build().parse();
    }

    public static Iris createIris(FormatDonneeBrutIris d){
        return new Iris(d.getSepal_length(), d.getSepal_width(), d.getPetal_length(), d.getPetal_width(), d.getVariety());
    }

    /*
    public static double normaliser_valeur(double valeur, double min, double max){
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
            personne.setScoreNormalise(normaliser_valeur(personne.getScoreNormalise(), scoreMin, scoreMax));
        }
        return p;
    }
    */
}