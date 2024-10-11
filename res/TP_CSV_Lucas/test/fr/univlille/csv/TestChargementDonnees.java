package test.fr.univlille.csv;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.*;
import fr.univlille.csv.*;

import java.time.LocalDate;
import java.util.*;

public class TestChargementDonnees {

    @Test
    public void testCharger () throws IOException {
        List<FormatDonneeBrut> l = ChargementDonneesUtil.charger("data/personnes.csv");
        List<String> test = new ArrayList<>();
        test.add("Chevallier;Vincent;1978-05-18;Homme;1.76;79;oui");
        test.add("Teixeira-Sauvage;Émile;1973-12-22;Femme;1.5;77;oui");
        test.add("Delaunay;Brigitte;1973-03-24;Femme;1.81;83;non");
        test.add("De Oliveira;Claudine;1954-12-10;Femme;1.66;291;oui");
        test.add("Leclerc;Jacques;1949-09-25;Homme;1.67;56;non");
        test.add("Allard;Pénélope;1948-12-08;Femme;1.8;80;non");
        test.add("Grenier;Robert;1969-12-25;Autre;1.78;2;oui");
        test.add("Bousquet;Dorothée;1990-03-07;Femme;1.62;43;oui");
        test.add("Chekrar;Younes;1991-11-17;Homme;2.02;62;non");
        test.add("Peron;Franck;1976-04-17;Homme;1.54;85;oui");
        String TexteChargé = "";
        String TexteCSV = "";
        for(FormatDonneeBrut ligne : l){
            TexteChargé = TexteChargé + ligne.toString() + '\n';
        }
        for(String ligne : test){
            TexteCSV = TexteCSV + ligne + '\n';
        }

        Assertions.assertEquals(TexteChargé, TexteCSV);
        // à compléter avec des tests pour vérifier que le chargement s'effectue correctement
    }

    @Test
    public void testCreatePersonne () throws IOException {
        List<FormatDonneeBrut> l = ChargementDonneesUtil.charger("data/personnes.csv");
        Personne m1 = new Personne("Vincent Chevallier", LocalDate.of(1978,5,18), Genre.Homme, 176, 79, true);
        Personne f1 = new Personne("Brigitte Delaunay", LocalDate.of(1973,3,24), Genre.Femme, 181, 83, false);
        Personne m2 = ChargementDonneesUtil.createPersonne(l.get(0));
        Personne f2 = ChargementDonneesUtil.createPersonne(l.get(2));
        Assertions.assertEquals(m1.toString(), m2.toString());
        Assertions.assertEquals(f1.toString(), f2.toString());
        // à compléter avec des tests pour vérifier que le chargement s'effectue correctement
    }

    @Test
    public void testNormaliser() throws IOException {
        double minEx = 5;
        double maxEx = 25;
        Assertions.assertEquals(ChargementDonneesUtil.normaliser_0_1(5,minEx,maxEx),0);
        Assertions.assertEquals(ChargementDonneesUtil.normaliser_0_1(25,minEx,maxEx),1);
        Assertions.assertEquals(ChargementDonneesUtil.normaliser_0_1(10,minEx,maxEx),0.25);
        Assertions.assertEquals(ChargementDonneesUtil.normaliser_0_1(12,minEx,maxEx),0.35);
        Assertions.assertEquals(ChargementDonneesUtil.normaliser_0_1(17,minEx,maxEx),0.6);
    }

    @Test
    public void testpersonnesNormalisees() throws IOException {
        List<FormatDonneeBrut> l = ChargementDonneesUtil.charger("data/personnes.csv");
        List<Personne> p = ChargementDonneesUtil.personnesNormalisees(l);
        double m11 = (double) (79 - 2) /(291-2);
        double f11 = (double) (83 - 2) /(291-2);
        Personne m1 = new Personne("Vincent Chevallier", LocalDate.of(1978,5,18), Genre.Homme, 176, m11, true);
        Personne f1 = new Personne("Brigitte Delaunay", LocalDate.of(1973,3,24), Genre.Femme, 181, f11, false);
        Assertions.assertEquals(p.get(0).toString(), m1.toString());
        Assertions.assertEquals(p.get(2).toString(), f1.toString());
    }
}