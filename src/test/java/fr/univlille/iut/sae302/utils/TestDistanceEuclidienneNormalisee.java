package fr.univlille.iut.sae302.utils;

import fr.univlille.iut.sae302.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestDistanceEuclidienneNormalisee {
    Iris iris1;
    Iris iris2;
    Iris iris3;
    Pokemon pokemon1;
    Pokemon pokemon2;
    ArrayList<Iris> listIris;
    ArrayList<Pokemon> listPokemon;
    MethodeKnn mKnn;

    DistanceEuclidienneNormalisee den = new DistanceEuclidienneNormalisee();

    @BeforeEach
    public void init(){
        iris1 = new Iris(1.0, 1.0, 1.0, 1.0, "Setosa");
        iris2 = new Iris(2.0, 2.0, 2.0, 2.0, "Setosa");
        iris3 = null;
        pokemon1 = new Pokemon("Swablu", 40, 5120, 255.0, 60, 600000, 45, 75, 50, Type.normal, Type.flying, 1.2, false);
        pokemon2 = new Pokemon("Budew", 30, 5120, 255.0, 35, 1059860, 40, 70, 55, Type.grass, Type.poison, 1.2, false);
        listIris = new ArrayList<>();
        listPokemon = new ArrayList<>();
        listIris.add(iris1);
        listIris.add(iris2);
        listPokemon.add(pokemon1);
        listPokemon.add(pokemon2);
    }

    @Test
    public void testArgument(){
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> den.distance(iris1, iris3));
        assertEquals("Les objets ne doivent pas être null", exception.getMessage());
    }

    @Test
    public void testDistanceIris(){
        mKnn = new MethodeKnn(new Data<>(listIris));
        assertEquals(2.0, den.distance(iris1, iris2));
    }

    @Test
    public void testDistancePokemon(){
        mKnn = new MethodeKnn(new Data<>(listPokemon));
        assertEquals(2.23, den.distance(pokemon1, pokemon2), 0.01);
    }
}
