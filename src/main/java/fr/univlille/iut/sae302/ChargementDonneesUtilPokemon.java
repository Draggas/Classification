package fr.univlille.iut.sae302;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class ChargementDonneesUtilPokemon {

    public static List<FormatDonneeBrutPokemon> charger(String fileName) throws IOException {
        return new CsvToBeanBuilder<FormatDonneeBrutPokemon>(Files.newBufferedReader(Paths.get(fileName)))
                .withSeparator(',')
                .withType(FormatDonneeBrutPokemon.class)
                .build().parse();
    }

    public static Pokemon createPokemon(FormatDonneeBrutPokemon d){
        boolean isLegendary = false;
        if(d.getLegendary().equals("oui")) isLegendary = true;
        return new Pokemon(d.getName(), d.getAttack(), d.getEgg_steps(), d.getCapture_rate(), d.getDefense(), d.getExperience(), d.getHp(), d.getSp_attack(), d.getSp_defense(), d.getType1(), d.getType2(), d.getSpeed(), isLegendary);
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