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
    private Iris iris4;
    private Iris iris5;
    private ArrayList<Iris> list;
    private Data<Iris> data;
    private MethodeKnn mKnn;

    @BeforeEach
    public void init(){
        iris1 = new Iris(1.1, 1.2, 1.3, 1.4, "Setosa");
        iris2 = new Iris(2.1, 4.2, 2.0, 2.4, "Setosa");
        iris3 = new Iris(1.1, 2.2, 2.3, 1.4, "Setosa");
        iris4 = new Iris(1.5, 3.5, 1.4, 1.7, "Setosa");
        iris5 = new Iris(1.9, 2.7, 1.7, 2.0, "Setosa");
        list = new ArrayList<>();
        list.add(iris1);
        list.add(iris2);
        list.add(iris3);
        list.add(iris4);
        list.add(iris5);
        data = new Data<>(list);
        mKnn = new MethodeKnn(data);
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

    @Test
    public void testSetDatas(){
        Data<Iris> dataTemp = new Data<>(new ArrayList<Iris>());
        mKnn.setDatas(dataTemp);
        assertEquals(dataTemp, mKnn.getDatas());

        mKnn.setDatas(data);
        assertEquals(data, mKnn.getDatas());
    }

    @Test
    public void testCalculerAmplitudesDataVide(){
        mKnn.setDatas(new Data<>(new ArrayList<>()));
        mKnn.calculerAmplitudes();
        assertEquals(0, MethodeKnn.amplitudePetalLength);
        assertEquals(0, MethodeKnn.amplitudePetalWidth);
        assertEquals(0, MethodeKnn.amplitudeSepalLength);
        assertEquals(0, MethodeKnn.amplitudeSepalWidth);
    }

    @Test
    public void testCalculerAmplitudes(){
        mKnn.calculerAmplitudes();
        assertEquals(1.0, MethodeKnn.amplitudePetalLength, 0.0001);
        assertEquals(1.0, MethodeKnn.amplitudePetalWidth, 0.0001);
        assertEquals(1.0, MethodeKnn.amplitudeSepalLength, 0.0001);
        assertEquals(3.0, MethodeKnn.amplitudeSepalWidth, 0.0001);
    }

    @Test
    public void testGetKPlusProchesVoisinsUnVoisin(){
        Iris[] resultatAttendu = new Iris[]{iris1};
        Iris[] resultat = mKnn.getKPlusProchesVoisins(1, iris3, new DistanceManhattan());

        testMemeContenueListe(resultatAttendu, resultat);
    }

    @Test
    public void testGetKPlusProchesVoisinsDeuxVoisin(){
        Iris[] resultatAttendu = new Iris[]{iris1, iris5};
        Iris[] resultat = mKnn.getKPlusProchesVoisins(2, iris3, new DistanceManhattan());

        testMemeContenueListe(resultatAttendu, resultat);
    }

    @Test
    public void testGetKPlusProchesVoisinsTroisVoisin(){
        Iris[] resultatAttendu = new Iris[]{iris1, iris5, iris4};
        Iris[] resultat = mKnn.getKPlusProchesVoisins(3, iris3, new DistanceManhattan());

        testMemeContenueListe(resultatAttendu, resultat);
    }

    public void testMemeContenueListe(Iris[] resultatAttendu, Iris[] resultat){
        assertEquals(resultatAttendu.length, resultat.length);
        for (int i=0; i<resultatAttendu.length; i++){
            assertEquals(resultatAttendu[i], resultat[i]);
        }
    }
}
