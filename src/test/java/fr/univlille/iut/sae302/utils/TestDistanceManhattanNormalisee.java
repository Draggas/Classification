package fr.univlille.iut.sae302.utils;

import fr.univlille.iut.sae302.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestDistanceManhattanNormalisee {
    private Iris iris1;
    private Iris iris2;
    private Iris iris3;
    private Iris iris4;
    private Pokemon pokemon1;
    private Pokemon pokemon2;
    private ArrayList<Iris> listIris;
    private ArrayList<Pokemon> listPokemon;
    private DistanceManhattanNormalisee dmn;
    MethodeKnn mKnn;

    @BeforeEach
    public void init(){
        iris1 = new Iris(1.1, 1.2, 1.3, 1.4, "Setosa");
        iris2 = new Iris(2.1, 4.2, 2.0, 2.4, "Setosa");
        iris3 = new Iris(1.1, 2.2, 2.3, 1.4, "Setosa");
        iris4 = null;

        pokemon1 = new Pokemon("Swablu", 40, 5120, 255.0, 60, 600000, 45, 75, 50, Type.normal, Type.flying, 1.2, false);
        pokemon2 = new Pokemon("Budew", 30, 5120, 255.0, 35, 1059860, 40, 70, 55, Type.grass, Type.poison, 1.2, false);

        listIris = new ArrayList<>();
        listPokemon = new ArrayList<>();
        dmn = new DistanceManhattanNormalisee();
        listIris.add(iris1);
        listIris.add(iris2);
        listIris.add(iris3);
        listIris.add(iris4);
        listPokemon.add(pokemon1);
        listPokemon.add(pokemon2);

    }

    @Test
    public void testArgument(){
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> dmn.distance(iris1, iris4));
        assertEquals("Les objets ne doivent pas être null", exception.getMessage());
    }

    @Test
    public void testDistanceIris(){
        mKnn = new MethodeKnn(new Data<>(listIris));
        assertEquals(3.7, dmn.distance(iris1, iris2), 0.01);
        assertEquals(2.96, dmn.distance(iris2, iris3), 0.01);
    }

    @Test
    public void testDistancePokemon(){
        mKnn = new MethodeKnn(new Data<>(listPokemon));
        assertEquals(5.0, dmn.distance(pokemon1, pokemon2), 0.01);
    }


}
