package fr.univlille.iut.sae302.utils;

import fr.univlille.iut.sae302.Iris;
import fr.univlille.iut.sae302.Pokemon;
import fr.univlille.iut.sae302.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestDistanceEuclidienne {
    private DistanceEuclidienne de = new DistanceEuclidienne();
    private Iris iris1;
    private Iris iris2;
    private Iris iris3;

    private Pokemon pokemon1;
    private Pokemon pokemon2;

    @BeforeEach
    public void init(){
        iris1 = new Iris(1.0, 1.0, 1.0, 1.0, "Setosa");
        iris2 = new Iris(2.0, 2.0, 2.0, 2.0, "Setosa");
        iris3 = null;

        pokemon1 = new Pokemon("Swablu", 40, 5120, 255.0, 60, 600000, 45, 75, 50, Type.normal, Type.flying, 1.2, false);
        pokemon2 = new Pokemon("Budew", 30, 5120, 255.0, 35, 1059860, 40, 70, 55, Type.grass, Type.poison, 1.2, false);
    }

    @Test
    public void testArgument(){
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> de.distance(iris1, iris3));
        assertEquals("Les objets ne doivent pas être null", exception.getMessage());
    }

    @Test
    public void testDistanceIris(){
        assertEquals(2.0, de.distance(iris1, iris2));
    }

    @Test
    public void testDistancePokemon(){
        assertEquals(28.28, de.distance(pokemon1, pokemon2), 0.01);
    }
}
