package fr.univlille.iut.sae302.utils;

import fr.univlille.iut.sae302.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestDistanceManhattan {
    private Iris iris1;
    private Iris iris2;
    private Iris iris3;
    private Iris iris4;
    private Pokemon pokemon1;
    private Pokemon pokemon2;
    private DistanceManhattan dm;

    @BeforeEach
    public void init(){
        iris1 = new Iris(1.1, 1.2, 1.3, 1.4, "Setosa");
        iris2 = new Iris(2.1, 4.2, 2.0, 2.4, "Setosa");
        iris3 = new Iris(1.1, 2.2, 2.3, 1.4, "Setosa");
        iris4 = null;
        dm = new DistanceManhattan();

        pokemon1 = new Pokemon("Swablu", 40, 5120, 255.0, 60, 600000, 45, 75, 50, Type.normal, Type.flying, 1.2, false);
        pokemon2 = new Pokemon("Budew", 30, 5120, 255.0, 35, 1059860, 40, 70, 55, Type.grass, Type.poison, 1.2, false);
    }

    @Test
    public void testArgument(){
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> dm.distance(iris1, iris4));
        assertEquals("Les objets ne doivent pas être null", exception.getMessage());
    }

    @Test
    public void testDistanceIris(){
        assertEquals(5.7, dm.distance(iris1, iris2));
        assertEquals(4.3, dm.distance(iris2, iris3));
    }

    @Test
    public void testDistancePokemon(){
        assertEquals(50.0, dm.distance(pokemon1, pokemon2), 0.01);
    }
}
