package fr.univlille.iut.sae302.utils;

import fr.univlille.iut.sae302.Data;
import fr.univlille.iut.sae302.Iris;
import fr.univlille.iut.sae302.MethodeKnn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDistanceEuclidienneNormalisee {
    Iris iris1;
    Iris iris2;
    MethodeKnn mKnn;

    DistanceEuclidienneNormalisee den = new DistanceEuclidienneNormalisee();

    @BeforeEach
    public void init(){
        iris1 = new Iris(1.0, 1.0, 1.0, 1.0, "Setosa");
        iris2 = new Iris(2.0, 2.0, 2.0, 2.0, "Setosa");
        ArrayList<Iris> list = new ArrayList<>();
        list.add(iris1);
        list.add(iris2);
        mKnn = new MethodeKnn(new Data<>(list));
        mKnn.calculerAmplitudes();
    }

    @Test
    public void testDistance(){
        assertEquals(2.0, den.distance(iris1, iris2));
    }
}
