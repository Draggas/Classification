package fr.univlille.iut.sae302.utils;

import fr.univlille.iut.sae302.Iris;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDistanceEuclidienne {
    DistanceEuclidienne de = new DistanceEuclidienne();

    @Test
    public void testDistance(){
        Iris iris1 = new Iris(1.0, 1.0, 1.0, 1.0, "Setosa");
        Iris iris2 = new Iris(2.0, 2.0, 2.0, 2.0, "Setosa");
        assertEquals(2.0, de.distance(iris1, iris2));
    }
}
