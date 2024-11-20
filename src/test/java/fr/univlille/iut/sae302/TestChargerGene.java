package fr.univlille.iut.sae302;

import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

// Classe de Test
public class TestChargerGene {

    @Test
    void testChargerIris() {
        try {
            ObjetGenerique objetGenerique = ChargementGenerique.chargerGenerique("data/iris.csv");
            List<Map<String, Object>> data = objetGenerique.getAllRows();

            assertEquals(150, data.size(), "Le fichier iris.csv doit contenir 150 lignes.");

            Map<String, Object> firstRow = data.get(0);
            assertEquals(5.1, firstRow.get("sepal.length"));
            assertEquals(3.5, firstRow.get("sepal.width"));
            assertEquals(1.4, firstRow.get("petal.length"));
            assertEquals(0.2, firstRow.get("petal.width"));
            assertEquals("Setosa", firstRow.get("variety"));

            Map<String, Object> lastRow = data.get(data.size() - 1);
            assertEquals(5.9, lastRow.get("sepal.length"));
            assertEquals(3, lastRow.get("sepal.width"));
            assertEquals(5.1, lastRow.get("petal.length"));
            assertEquals(1.8, lastRow.get("petal.width"));
            assertEquals("Virginica", lastRow.get("variety"));

        } catch (IOException | CsvException e) {
            fail("Une exception s'est produite lors du chargement de iris.csv : " + e.getMessage());
        }
    }

    @Test
    void testChargerPokemon() {
        try {
            ObjetGenerique objetGenerique = ChargementGenerique.chargerGenerique("data/pokemon.csv");
            List<Map<String, Object>> data = objetGenerique.getAllRows();

            assertEquals(507, data.size(), "Le fichier pokemon.csv doit contenir 507 lignes.");

            Map<String, Object> firstRow = data.get(0);
            assertEquals("Swablu", firstRow.get("name"));
            assertEquals(40, firstRow.get("attack"));
            assertEquals(5120, firstRow.get("base_egg_steps"));
            assertEquals(255.0, firstRow.get("capture_rate"));
            assertEquals(60, firstRow.get("defense"));

            Map<String, Object> lastRow = data.get(data.size() - 1);
            assertEquals("Seismitoad", lastRow.get("name"));
            assertEquals(95, lastRow.get("attack"));
            assertEquals(5120, lastRow.get("base_egg_steps"));
            assertEquals(45.0, lastRow.get("capture_rate"));
            assertEquals(75, lastRow.get("defense"));

            Map<String, Object> randomRow = data.get(100);
            assertTrue(randomRow.get("attack") instanceof Integer, "La valeur 'attack' doit être un entier.");
            assertTrue(randomRow.get("capture_rate") instanceof Double, "La valeur 'capture_rate' doit être un double.");
            assertTrue(randomRow.get("name") instanceof String, "La valeur 'name' doit être une chaîne.");

            Map<String, Object> rowWithMissingValue = data.get(10);
            assertNull(rowWithMissingValue.get("type2"), "La valeur 'type2' devrait être nulle si manquante.");

        } catch (IOException | CsvException e) {
            fail("Une exception s'est produite lors du chargement de pokemon.csv : " + e.getMessage());
        }
    }
}

