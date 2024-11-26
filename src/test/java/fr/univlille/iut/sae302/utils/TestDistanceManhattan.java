package fr.univlille.iut.sae302.utils;

import fr.univlille.iut.sae302.Data;
import fr.univlille.iut.sae302.Iris;
import fr.univlille.iut.sae302.MethodeKnn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDistanceManhattan {
    private Iris iris1;
    private Iris iris2;
    private Iris iris3;
    private ArrayList<Iris> list;
    private DistanceManhattan dm;
    MethodeKnn mKnn;

    @BeforeEach
    public void init(){
        iris1 = new Iris(1.1, 1.2, 1.3, 1.4, "Setosa");
        iris2 = new Iris(2.1, 4.2, 2.0, 2.4, "Setosa");
        iris3 = new Iris(1.1, 2.2, 2.3, 1.4, "Setosa");
        list = new ArrayList<>();
        dm = new DistanceManhattan();
        list.add(iris1);
        list.add(iris2);
        list.add(iris3);
        mKnn = new MethodeKnn(new Data<>(list));
        mKnn.calculerAmplitudes();
    }

    @Test
    public void testDistance(){
        assertEquals(5.7, dm.distance(iris1, iris2));
        assertEquals(4.3, dm.distance(iris2, iris3));

    }
}
