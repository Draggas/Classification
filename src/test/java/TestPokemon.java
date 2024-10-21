import fr.univlille.iut.sae302.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestPokemon {

    @Test
    public void testCharger () throws IOException {
        List<FormatDonneeBrutPokemon> l = ChargementDonneesUtilPokemon.charger("data/iris.csv");
        List<String> test = new ArrayList<>();
        test.add("5.1,3.5,1.4,.2,Setosa");
        test.add("4.9,3.0,1.4,.2,Setosa");
        test.add("4.7,3.2,1.3,.2,Setosa");
        test.add("4.6,3.1,1.5,.2,Setosa");
        String TexteChargé = "";
        String TexteCSV = "";
        for(int i=0;i<4;i++) {
            TexteChargé = TexteChargé + l.get(i).toString() + '\n';
        }
        for(String ligne : test){
            TexteCSV = TexteCSV + ligne + '\n';
        }
        Assertions.assertEquals(TexteChargé, TexteCSV);
    }

    @Test
    public void testCreatePersonne () throws IOException {
        List<FormatDonneeBrutIris> l = ChargementDonneesUtilIris.charger("data/iris.csv");
        Iris m1 = new Iris(5.1,3.5,1.4,0.2,"Setosa");
        Iris f1 = new Iris(4.7,3.2,1.3,.2,"Setosa");
        Iris m2 = ChargementDonneesUtilIris.createIris(l.get(0));
        Iris f2 = ChargementDonneesUtilIris.createIris(l.get(2));
        Assertions.assertEquals(m1.toString(), m2.toString());
        Assertions.assertEquals(f1.toString(), f2.toString());
        // à compléter avec des tests pour vérifier que le chargement s'effectue correctement
    }
/*
    @Test
    public void testNormaliser() throws IOException {
        double minEx = 5;
        double maxEx = 25;
        Assertions.assertEquals(ChargementDonneesUtil.normaliser_valeur(5,minEx,maxEx),0);
        Assertions.assertEquals(ChargementDonneesUtil.normaliser_valeur(25,minEx,maxEx),1);
        Assertions.assertEquals(ChargementDonneesUtil.normaliser_valeur(10,minEx,maxEx),0.25);
        Assertions.assertEquals(ChargementDonneesUtil.normaliser_valeur(12,minEx,maxEx),0.35);
        Assertions.assertEquals(ChargementDonneesUtil.normaliser_valeur(17,minEx,maxEx),0.6);
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

 */
}