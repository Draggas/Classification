package fr.univlille.iut.sae302;

import fr.univlille.iut.sae302.utils.DistanceManhattan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMethodeKnn{
    private Iris iris1;
    private Iris iris2;
    private Iris iris3;
    private ArrayList<Iris> list;
    MethodeKnn mKnn;

    @BeforeEach
    public void init(){
        iris1 = new Iris(1.1, 1.2, 1.3, 1.4, "Setosa");
        iris2 = new Iris(2.1, 4.2, 2.0, 2.4, "Setosa");
        iris3 = new Iris(1.1, 2.2, 2.3, 1.4, "Setosa");
        list = new ArrayList<>();
        list.add(iris1);
        list.add(iris2);
        list.add(iris3);
        mKnn = new MethodeKnn(new Data<>(list));
    }

    @Test
    public void testGetData(){
        assertEquals(list, mKnn.getDatas().getEData());
    }

    @Test
    public void testGetSepalWidthMin(){
        assertEquals(1.2, mKnn.getSepalWidthMin());
    }

    @Test
    public void testGetSepalLengthMin(){
        assertEquals(1.1, mKnn.getSepalLengthMin());
    }

    @Test
    public void testGetPetalWidthMin(){
        assertEquals(1.4, mKnn.getPetalWidthMin());
    }

    @Test
    public void testGetPetalLengthMin(){
        assertEquals(1.3, mKnn.getPetalLengthMin());
    }
}
