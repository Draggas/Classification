package fr.univlille.iut.sae302.genericite;

import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

// Classe de Test
public class TestChargerGene {

    @Test
    void testChargerIris() {
        try {
            CSVGenerique csvGenerique = ChargementGenerique.chargerGenerique("data/iris.csv");
            Map<String, ArrayList<Object>> data = csvGenerique.getAllRows();

            assertEquals(150, data.size(), "Le fichier iris.csv doit contenir 150 lignes.");

            assertEquals(5.1, data.get("sepal.length").get(0));
            assertEquals(3.5, data.get("sepal.width").get(0));
            assertEquals(1.4, data.get("petal.length").get(0));
            assertEquals(0.2, data.get("petal.width").get(0));
            assertEquals("Setosa", data.get("variety").get(0));

            assertEquals(5.9, data.get("sepal.length").get(data.size() - 1));
            assertEquals(3, data.get("sepal.width").get(data.size() - 1));
            assertEquals(5.1, data.get("petal.length").get(data.size() - 1));
            assertEquals(1.8, data.get("petal.width").get(data.size() - 1));
            assertEquals("Virginica", data.get("variety").get(data.size() - 1));

        } catch (IOException | CsvException e) {
            fail("Une exception s'est produite lors du chargement de iris.csv : " + e.getMessage());
        }
    }

    @Test
    void testChargerPokemon() {
        try {
            CSVGenerique csvGenerique = ChargementGenerique.chargerGenerique("data/pokemon.csv");
            Map<String, ArrayList<Object>> data = csvGenerique.getAllRows();

            assertEquals(507, data.size(), "Le fichier pokemon.csv doit contenir 507 lignes.");

            assertEquals("Swablu", data.get("name"));
            assertEquals(40, data.get("attack"));
            assertEquals(5120, data.get("base_egg_steps"));
            assertEquals(255.0, data.get("capture_rate"));
            assertEquals(60, data.get("defense"));

            assertEquals("Seismitoad", data.get("name"));
            assertEquals(95, data.get("attack"));
            assertEquals(5120, data.get("base_egg_steps"));
            assertEquals(45.0, data.get("capture_rate"));
            assertEquals(75, data.get("defense"));

            assertTrue(data.get("attack").get(0) instanceof Integer, "La valeur 'attack' doit être un entier.");
            assertTrue(data.get("capture_rate").get(0) instanceof Double, "La valeur 'capture_rate' doit être un double.");
            assertTrue(data.get("name").get(0) instanceof String, "La valeur 'name' doit être une chaîne.");

            assertNull(data.get("type2").get(0), "La valeur 'type2' devrait être nulle si manquante.");

        } catch (IOException | CsvException e) {
            fail("Une exception s'est produite lors du chargement de pokemon.csv : " + e.getMessage());
        }
    }
}

