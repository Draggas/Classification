package fr.univlille.iut.sae302;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.bean.CsvToBeanBuilder;

public class ChargementDonneesUtil<T> {

    public static <T> List<T> charger(String fileName, Class<T> type) throws IOException {
        return new CsvToBeanBuilder<T>(Files.newBufferedReader(Paths.get(fileName)))
                .withSeparator(',')
                .withType(type)
                .build().parse();
    }

    public static Iris createIris(FormatDonneeBrutIris d) {
        return new Iris(d.getSepal_length(), d.getSepal_width(), d.getPetal_length(), d.getPetal_width(), d.getVariety());
    }

    public static Pokemon createPokemon(FormatDonneeBrutPokemon d) {
        if (d.getType2() == null) {
            d.setType2(Type.none);
        }
        return new Pokemon(d.getName(), d.getAttack(), d.getEggSteps(), d.getCaptureRate(), d.getDefense(), d.getExperience(), d.getHp(), d.getSpAttack(), d.getSpDefense(), d.getType1(), d.getType2(), d.getSpeed(), d.getLegendary());
    }

    public static double normaliserValeur(double valeur, double min, double max){
        return ((valeur-min)/(max-min));
    }

    public static List<Iris> normaliserIris(List<FormatDonneeBrutIris> donnees) {
        List<Iris> irisList = new ArrayList<>();
        
        double minSepalLength = Double.MAX_VALUE, maxSepalLength = Double.MIN_VALUE;
        double minSepalWidth = Double.MAX_VALUE, maxSepalWidth = Double.MIN_VALUE;
        double minPetalLength = Double.MAX_VALUE, maxPetalLength = Double.MIN_VALUE;
        double minPetalWidth = Double.MAX_VALUE, maxPetalWidth = Double.MIN_VALUE;

        for (FormatDonneeBrutIris f : donnees) {
            minSepalLength = Math.min(minSepalLength, f.getSepal_length());
            maxSepalLength = Math.max(maxSepalLength, f.getSepal_length());
            minSepalWidth = Math.min(minSepalWidth, f.getSepal_width());
            maxSepalWidth = Math.max(maxSepalWidth, f.getSepal_width());
            minPetalLength = Math.min(minPetalLength, f.getPetal_length());
            maxPetalLength = Math.max(maxPetalLength, f.getPetal_length());
            minPetalWidth = Math.min(minPetalWidth, f.getPetal_width());
            maxPetalWidth = Math.max(maxPetalWidth, f.getPetal_width());
            irisList.add(createIris(f));
        }

        for (Iris iris : irisList) {
            iris.setSepalLength(normaliserValeur(iris.getSepalLength().doubleValue(), minSepalLength, maxSepalLength));
            iris.setSepalWidth(normaliserValeur(iris.getSepalWidth().doubleValue(), minSepalWidth, maxSepalWidth));
            iris.setPetalLength(normaliserValeur(iris.getPetalLength().doubleValue(), minPetalLength, maxPetalLength));
            iris.setPetalWidth(normaliserValeur(iris.getPetalWidth().doubleValue(), minPetalWidth, maxPetalWidth));
        }

        return irisList;
    }

    public static List<Pokemon> normaliserPokemon(List<FormatDonneeBrutPokemon> donnees) {
        List<Pokemon> pokemonList = new ArrayList<>();
        
        double minAttack = Double.MAX_VALUE, maxAttack = Double.MIN_VALUE;
        double minEggSteps = Double.MAX_VALUE, maxEggSteps = Double.MIN_VALUE;
        double minCaptureRate = Double.MAX_VALUE, maxCaptureRate = Double.MIN_VALUE;
        double minDefense = Double.MAX_VALUE, maxDefense = Double.MIN_VALUE;
        double minExperience = Double.MAX_VALUE, maxExperience = Double.MIN_VALUE;
        double minHp = Double.MAX_VALUE, maxHp = Double.MIN_VALUE;
        double minSpAttack = Double.MAX_VALUE, maxSpAttack = Double.MIN_VALUE;
        double minSpDefense = Double.MAX_VALUE, maxSpDefense = Double.MIN_VALUE;
        double minSpeed = Double.MAX_VALUE, maxSpeed = Double.MIN_VALUE;
        
        // Parcourir les données brutes et trouver les min/max pour chaque attribut
        for (FormatDonneeBrutPokemon f : donnees) {
            minAttack = Math.min(minAttack, f.getAttack());
            maxAttack = Math.max(maxAttack, f.getAttack());
            minEggSteps = Math.min(minEggSteps, f.getEggSteps());
            maxEggSteps = Math.max(maxEggSteps, f.getEggSteps());
            minCaptureRate = Math.min(minCaptureRate, f.getCaptureRate());
            maxCaptureRate = Math.max(maxCaptureRate, f.getCaptureRate());
            minDefense = Math.min(minDefense, f.getDefense());
            maxDefense = Math.max(maxDefense, f.getDefense());
            minExperience = Math.min(minExperience, f.getExperience());
            maxExperience = Math.max(maxExperience, f.getExperience());
            minHp = Math.min(minHp, f.getHp());
            maxHp = Math.max(maxHp, f.getHp());
            minSpAttack = Math.min(minSpAttack, f.getSpAttack());
            maxSpAttack = Math.max(maxSpAttack, f.getSpAttack());
            minSpDefense = Math.min(minSpDefense, f.getSpDefense());
            maxSpDefense = Math.max(maxSpDefense, f.getSpDefense());
            minSpeed = Math.min(minSpeed, f.getSpeed());
            maxSpeed = Math.max(maxSpeed, f.getSpeed());
            
            // Ajouter le Pokémon à la liste
            pokemonList.add(createPokemon(f));
        }
    
        // Normaliser les valeurs des Pokémon
        for (Pokemon pokemon : pokemonList) {
            pokemon.setAttack(normaliserValeur(pokemon.getAttack().doubleValue(), minAttack, maxAttack));
            pokemon.setEggSteps(normaliserValeur(pokemon.getEggSteps().doubleValue(), minEggSteps, maxEggSteps));
            pokemon.setCaptureRate(normaliserValeur(pokemon.getCaptureRate().doubleValue(), minCaptureRate, maxCaptureRate));
            pokemon.setDefense(normaliserValeur(pokemon.getDefense().doubleValue(), minDefense, maxDefense));
            pokemon.setExperience(normaliserValeur(pokemon.getExperience().doubleValue(), minExperience, maxExperience));
            pokemon.setHp(normaliserValeur(pokemon.getHp().doubleValue(), minHp, maxHp));
            pokemon.setSpAttack(normaliserValeur(pokemon.getSpAttack().doubleValue(), minSpAttack, maxSpAttack));
            pokemon.setSpDefense(normaliserValeur(pokemon.getSpDefense().doubleValue(), minSpDefense, maxSpDefense));
            pokemon.setSpeed(normaliserValeur(pokemon.getSpeed().doubleValue(), minSpeed, maxSpeed));
        }
    
        return pokemonList;
    }
}