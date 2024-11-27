package fr.univlille.iut.sae302;

import fr.univlille.iut.sae302.utils.Distance;
import fr.univlille.iut.sae302.utils.DistanceEuclidienne;
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
    private Iris iris6;
    private ArrayList<Iris> list;
    private Data<Iris> data;
    private MethodeKnn mKnn;

    @BeforeEach
    public void init(){
        iris1 = new Iris(5.1, 3.5, 1.4, 0.2, "Setosa");
        iris2 = new Iris(4.9, 3.0, 1.4, 0.2, "Setosa");
        iris3 = new Iris(7.0, 3.2, 4.7, 1.4, "Versicolor");
        iris4 = new Iris(6.4, 3.2, 4.5, 1.5, "Versicolor");
        iris5 = new Iris(6.3, 3.3, 6.0, 2.5, "Virginica");
        iris6 = new Iris(5.8, 2.7, 5.1, 1.9, "Virginica");
        list = new ArrayList<>();
        list.add(iris1);
        list.add(iris2);
        list.add(iris3);
        list.add(iris4);
        list.add(iris5);
        list.add(iris6);
        data = new Data<>(list);
        mKnn = new MethodeKnn(data);
    }

    @Test
    public void testGetData(){
        assertEquals(list, mKnn.getDatas().getEData());
    }

    @Test
    public void testGetSepalWidthMin(){
        assertEquals(2.7, mKnn.getSepalWidthMin());
    }

    @Test
    public void testGetSepalLengthMin(){
        assertEquals(4.9, mKnn.getSepalLengthMin());
    }

    @Test
    public void testGetPetalWidthMin(){
        assertEquals(0.2, mKnn.getPetalWidthMin());
    }

    @Test
    public void testGetPetalLengthMin(){
        assertEquals(1.4, mKnn.getPetalLengthMin());
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
        assertEquals(4.6, MethodeKnn.amplitudePetalLength, 0.01);
        assertEquals(2.3, MethodeKnn.amplitudePetalWidth, 0.01);
        assertEquals(2.1, MethodeKnn.amplitudeSepalLength, 0.01);
        assertEquals(0.8, MethodeKnn.amplitudeSepalWidth, 0.01);
    }

    @Test
    public void testPPVUnVoisinDistanceManhattan(){
        testGetKPlusProchesVoisins(new Iris[]{iris4}, 1, iris3, new DistanceManhattan());
    }

    @Test
    public void testPPVTroisVoisinDistanceManhattan(){
        testGetKPlusProchesVoisins(new Iris[]{iris4, iris6, iris5}, 3, iris3, new DistanceManhattan());
    }

    @Test
    public void testPPVUnVoisinDistanceEuclidienne(){
        testGetKPlusProchesVoisins(new Iris[]{iris4}, 1, iris3, new DistanceEuclidienne());
    }

    @Test
    public void testPPVTroisVoisinDistanceEuclidienne(){
        testGetKPlusProchesVoisins(new Iris[]{iris4, iris6, iris5}, 3, iris3, new DistanceEuclidienne());
    }

    public void testGetKPlusProchesVoisins(Iris[] resultatAttendu, int k, Iris cible, Distance distance) {
        Iris[] resultat = mKnn.getKPlusProchesVoisins(k, cible, distance);
        testMemeContenueListe(resultatAttendu, resultat);
    }

    public void testMemeContenueListe(Iris[] resultatAttendu, Iris[] resultat){
        assertEquals(resultatAttendu.length, resultat.length);
        for (int i=0; i<resultatAttendu.length; i++){
            assertEquals(resultatAttendu[i], resultat[i]);
        }
    }

    @Test
    public void testClassifierIrisUnVoisinDistanceManhattan(){
        assertEquals("Versicolor", mKnn.classifierIris(1, iris3, new DistanceManhattan()));
    }

    @Test
    public void testClassifierIrisTroisVoisinDistanceManhattan(){
        assertEquals("Virginica", mKnn.classifierIris(3, iris3, new DistanceManhattan()));
    }

    @Test
    public void testClassifierIrisUnVoisinDistanceEuclidienne(){
        assertEquals("Versicolor", mKnn.classifierIris(1, iris3, new DistanceEuclidienne()));
    }

    @Test
    public void testClassifierIrisTroisVoisinDistanceEuclidienne(){
        assertEquals("Virginica", mKnn.classifierIris(3, iris3, new DistanceEuclidienne()));
    }
}
