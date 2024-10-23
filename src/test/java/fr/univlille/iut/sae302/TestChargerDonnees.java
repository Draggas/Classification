package fr.univlille.iut.sae302;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestChargerDonnees {

    @Test
    public void testCharger () throws IOException {
        List<FormatDonneeBrutIris> il = ChargementDonneesUtil.charger("data/iris.csv", FormatDonneeBrutIris.class);
        List<String> testIris = new ArrayList<>();
        testIris.add("5.1,3.5,1.4,.2,Setosa");
        testIris.add("4.9,3.0,1.4,.2,Setosa");
        testIris.add("4.7,3.2,1.3,.2,Setosa");
        testIris.add("4.6,3.1,1.5,.2,Setosa");
        String TexteIrisChargé = "";
        String TexteIrisCSV = "";
        for(int i=0;i<4;i++) {
            TexteIrisChargé = TexteIrisChargé + il.get(i).toString() + '\n';
        }
        for(String ligne : testIris){
            TexteIrisCSV = TexteIrisCSV + ligne + '\n';
        }

        List<FormatDonneeBrutPokemon> pl = ChargementDonneesUtil.charger("data/pokemon.csv", FormatDonneeBrutPokemon.class);
        List<String> testPoké = new ArrayList<>();
        testPoké.add("Swablu,40,5120,255.0,60,600000,45,75,50,normal,flying,1.2,false");
        testPoké.add("Budew,30,5120,255.0,35,1059860,40,70,55,grass,poison,1.2,false");
        testPoké.add("Minun,40,5120,200.0,50,1000000,60,85,95,electric,,4.2,false");
        testPoké.add("Metapod,20,3840,120.0,55,1000000,50,25,30,bug,,9.9,false");
        String TextePokémonChargé = "";
        String TextePokémonCSV = "";
        for(int i=0;i<4;i++) {
            TextePokémonChargé = TextePokémonChargé + pl.get(i).toString() + '\n';
        }
        for(String ligne : testPoké){
            TextePokémonCSV = TextePokémonCSV + ligne + '\n';
        }

        
        Assertions.assertEquals(TexteIrisCSV, TexteIrisChargé);
        Assertions.assertEquals(TextePokémonCSV, TextePokémonChargé);
    }

    @Test
    public void testCreateIris () throws IOException {
        List<FormatDonneeBrutIris> l = ChargementDonneesUtil.charger("data/iris.csv", FormatDonneeBrutIris.class);
        Iris r2 = new Iris(5.1,3.5,1.4,0.2,"Setosa");
        Iris r4 = new Iris(4.7,3.2,1.3,.2,"Setosa");
        Iris l2 = ChargementDonneesUtil.createIris(l.get(0));
        Iris l4 = ChargementDonneesUtil.createIris(l.get(2));
        Assertions.assertEquals(r2.toString(), l2.toString());
        Assertions.assertEquals(r4.toString(), l4.toString());
    }

    @Test
    public void testCreatePokemon () throws IOException {
        List<FormatDonneeBrutPokemon> l = ChargementDonneesUtil.charger("data/pokemon.csv", FormatDonneeBrutPokemon.class);
        Pokemon r2 = new Pokemon("Swablu",40,5120,255.0,60,600000,45,75,50,Type.normal,Type.flying,1.2,false);
        Pokemon r4 = new Pokemon("Minun",40,5120,200.0,50,1000000,60,85,95,Type.electric,Type.none,4.2,false);
        Pokemon l2 = ChargementDonneesUtil.createPokemon(l.get(0));
        Pokemon l4 = ChargementDonneesUtil.createPokemon(l.get(2));
        Assertions.assertEquals(r2.toString(), l2.toString());
        Assertions.assertEquals(r4.toString(), l4.toString());
    }

    @Test
    public void testNormaliser() throws IOException {
        double minEx = 5;
        double maxEx = 25;
        Assertions.assertEquals(0,ChargementDonneesUtil.normaliserValeur(5,minEx,maxEx));
        Assertions.assertEquals(1,ChargementDonneesUtil.normaliserValeur(25.0,minEx,maxEx));
        Assertions.assertEquals(0.25,ChargementDonneesUtil.normaliserValeur(10,minEx,maxEx));
        Assertions.assertEquals(0.35,ChargementDonneesUtil.normaliserValeur(12.0,minEx,maxEx));
        Assertions.assertEquals(0.6,ChargementDonneesUtil.normaliserValeur(17,minEx,maxEx));
    }

    @Test
    public void testIrisNormalisees() throws IOException {
        List<FormatDonneeBrutIris> l = ChargementDonneesUtil.charger("data/iris.csv", FormatDonneeBrutIris.class);
        List<Iris> p = ChargementDonneesUtil.normaliserIris(l);
        double maxSepalLength = 7.9;
        double minSepalLength = 4.3;
        double maxSepalWidth = 4.4;
        double minSepalWidth = 2;
        double maxPetalLength = 6.9;
        double minPetalLength = 1;
        double maxPetalWidth = 2.5;
        double minPetalWidth = 0.1;
        
        Iris l2 = new Iris( ChargementDonneesUtil.normaliserValeur(5.1, minSepalLength, maxSepalLength),
                            ChargementDonneesUtil.normaliserValeur(3.5, minSepalWidth, maxSepalWidth),
                            ChargementDonneesUtil.normaliserValeur(1.4, minPetalLength, maxPetalLength),
                            ChargementDonneesUtil.normaliserValeur(0.2, minPetalWidth, maxPetalWidth),"Setosa");
        Iris l4 = new Iris( ChargementDonneesUtil.normaliserValeur(4.7, minSepalLength, maxSepalLength),
                            ChargementDonneesUtil.normaliserValeur(3.2, minSepalWidth, maxSepalWidth),
                            ChargementDonneesUtil.normaliserValeur(1.3, minPetalLength, maxPetalLength),
                            ChargementDonneesUtil.normaliserValeur(0.2, minPetalWidth, maxPetalWidth),"Setosa");
        Assertions.assertEquals(l2.toString(),p.get(0).toString());
        Assertions.assertEquals(l4.toString(),p.get(2).toString());
    }

    @Test
    public void testPokemonNormalisees() throws IOException {
        List<FormatDonneeBrutPokemon> l = ChargementDonneesUtil.charger("data/pokemon.csv", FormatDonneeBrutPokemon.class);
        List<Pokemon> p = ChargementDonneesUtil.normaliserPokemon(l);
        
        double maxAttack = 185;
        double minAttack = 5;
        double maxEggSteps = 30720;
        double minEggSteps = 1280;
        double maxCaptureRate = 255;
        double minCaptureRate = 3;
        double maxDefense = 230;
        double minDefense = 5;
        double maxExperience = 1640000;
        double minExperience = 600000;
        double maxHp = 190;
        double minHp = 1;
        double maxSpAttack = 230;
        double minSpAttack = 20;
        double maxSpDefense = 180;
        double minSpDefense = 5;
        double maxSpeed = 950;
        double minSpeed = 0.1;
        
        Pokemon l2 = new Pokemon("Swablu",
                                ChargementDonneesUtil.normaliserValeur(40, minAttack, maxAttack),
                                ChargementDonneesUtil.normaliserValeur(5120, minEggSteps, maxEggSteps),
                                ChargementDonneesUtil.normaliserValeur(255, minCaptureRate, maxCaptureRate),
                                ChargementDonneesUtil.normaliserValeur(60, minDefense, maxDefense),
                                ChargementDonneesUtil.normaliserValeur(600000, minExperience, maxExperience),
                                ChargementDonneesUtil.normaliserValeur(45, minHp, maxHp),
                                ChargementDonneesUtil.normaliserValeur(75, minSpAttack, maxSpAttack),
                                ChargementDonneesUtil.normaliserValeur(50, minSpDefense, maxSpDefense),
                                Type.normal, Type.flying,
                                ChargementDonneesUtil.normaliserValeur(1.2, minSpeed, maxSpeed), 
                                false);

        Pokemon l4 = new Pokemon("Minun",
                                ChargementDonneesUtil.normaliserValeur(40, minAttack, maxAttack),
                                ChargementDonneesUtil.normaliserValeur(5120, minEggSteps, maxEggSteps),
                                ChargementDonneesUtil.normaliserValeur(200, minCaptureRate, maxCaptureRate),
                                ChargementDonneesUtil.normaliserValeur(50, minDefense, maxDefense),
                                ChargementDonneesUtil.normaliserValeur(1000000, minExperience, maxExperience),
                                ChargementDonneesUtil.normaliserValeur(60, minHp, maxHp),
                                ChargementDonneesUtil.normaliserValeur(85, minSpAttack, maxSpAttack),
                                ChargementDonneesUtil.normaliserValeur(95, minSpDefense, maxSpDefense),
                                Type.electric, Type.none,
                                ChargementDonneesUtil.normaliserValeur(4.2, minSpeed, maxSpeed), 
                                false);

        Pokemon l14 = new Pokemon("Dialga",
                                ChargementDonneesUtil.normaliserValeur(120, minAttack, maxAttack),
                                ChargementDonneesUtil.normaliserValeur(30720, minEggSteps, maxEggSteps),
                                ChargementDonneesUtil.normaliserValeur(3.0, minCaptureRate, maxCaptureRate),
                                ChargementDonneesUtil.normaliserValeur(120, minDefense, maxDefense),
                                ChargementDonneesUtil.normaliserValeur(1250000, minExperience, maxExperience),
                                ChargementDonneesUtil.normaliserValeur(100, minHp, maxHp),
                                ChargementDonneesUtil.normaliserValeur(100, minSpAttack, maxSpAttack),
                                ChargementDonneesUtil.normaliserValeur(90, minSpDefense, maxSpDefense),
                                Type.steel, Type.dragon,
                                ChargementDonneesUtil.normaliserValeur(683.0, minSpeed, maxSpeed),
                                true);

        Assertions.assertEquals(l14.toString(), p.get(12).toString());
        Assertions.assertEquals(l2.toString(), p.get(0).toString());
        Assertions.assertEquals(l4.toString(), p.get(2).toString());
    }
}