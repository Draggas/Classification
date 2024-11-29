package fr.univlille.iut.sae302;

import fr.univlille.iut.sae302.utils.Distance;
import fr.univlille.iut.sae302.utils.DistanceEuclidienne;
import fr.univlille.iut.sae302.utils.DistanceManhattan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestMethodeKnn{
    private Iris iris1;
    private Iris iris2;
    private Iris iris3;
    private Iris iris4;
    private Iris iris5;
    private Iris iris6;
    private Pokemon pokemon1;
    private Pokemon pokemon2;
    private ArrayList<Iris> listIris;
    private ArrayList<Pokemon> listPokemon;
    private Data<Iris> data;
    @SuppressWarnings("rawtypes")
    private MethodeKnn mKnn;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @BeforeEach
    public void init(){
        iris1 = new Iris(5.1, 3.5, 1.4, 0.2, "Setosa");
        iris2 = new Iris(4.9, 3.0, 1.4, 0.2, "Setosa");
        iris3 = new Iris(7.0, 3.2, 4.7, 1.4, "Versicolor");
        iris4 = new Iris(6.4, 3.2, 4.5, 1.5, "Versicolor");
        iris5 = new Iris(6.3, 3.3, 6.0, 2.5, "Virginica");
        iris6 = new Iris(5.8, 2.7, 5.1, 1.9, "Virginica");

        pokemon1 = new Pokemon("Swablu", 40, 5120, 255.0, 60, 600000, 45, 75, 50, Type.normal, Type.flying, 1.2, false);
        pokemon2 = new Pokemon("Budew", 30, 5120, 255.0, 35, 1059860, 40, 70, 55, Type.grass, Type.poison, 1.2, false);

        listIris = new ArrayList<>();
        listIris.add(iris1);
        listIris.add(iris2);
        listIris.add(iris3);
        listIris.add(iris4);
        listIris.add(iris5);
        listIris.add(iris6);

        listPokemon = new ArrayList<>();
        listPokemon.add(pokemon1);
        listPokemon.add(pokemon2);

        data = new Data<>(listIris);
        mKnn = new MethodeKnn(data);
    }

    @Test
    public void testGetData(){
        assertEquals(listIris, mKnn.getDatas().getEData());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSetDatas(){
        Data<Iris> dataTemp = new Data<>(new ArrayList<Iris>());
        mKnn.setDatas(dataTemp);
        assertEquals(dataTemp, mKnn.getDatas());

        mKnn.setDatas(data);
        assertEquals(data, mKnn.getDatas());
    }

    @SuppressWarnings("unchecked")
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
        testGetKPlusProchesVoisins(List.of(new Iris[]{iris4}), 1, iris3, new DistanceManhattan());
    }

    @Test
    public void testPPVTroisVoisinDistanceManhattan(){
        testGetKPlusProchesVoisins(List.of(new Iris[]{iris4, iris6, iris5}), 3, iris3, new DistanceManhattan());
    }

    @Test
    public void testPPVUnVoisinDistanceEuclidienne(){
        testGetKPlusProchesVoisins(List.of(new Iris[]{iris4}), 1, iris3, new DistanceEuclidienne());
    }

    @Test
    public void testPPVTroisVoisinDistanceEuclidienne(){
        testGetKPlusProchesVoisins(List.of(new Iris[]{iris4, iris6, iris5}), 3, iris3, new DistanceEuclidienne());
    }

    public void testGetKPlusProchesVoisins(List<Iris> resultatAttendu, int k, Iris cible, @SuppressWarnings("rawtypes") Distance distance) {
        @SuppressWarnings("unchecked")
        List<Iris> resultat = (List<Iris>) mKnn.getKPlusProchesVoisins(k, cible, distance);
        assertEquals(resultatAttendu.size(), resultat.size());
        assertTrue(resultatAttendu.containsAll(resultat) && resultat.containsAll(resultatAttendu));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testClassifierIrisUnVoisinDistanceManhattan(){
        assertEquals("Versicolor", mKnn.classifierObjet(1, iris3, new DistanceManhattan()));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testClassifierIrisTroisVoisinDistanceManhattan(){
        assertEquals("Virginica", mKnn.classifierObjet(3, iris3, new DistanceManhattan()));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testClassifierIrisUnVoisinDistanceEuclidienne(){
        assertEquals("Versicolor", mKnn.classifierObjet(1, iris3, new DistanceEuclidienne()));
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    public void testClassifierIrisTroisVoisinDistanceEuclidienne(){
        mKnn = new MethodeKnn(new Data(listPokemon));
        assertEquals("grass", mKnn.classifierObjet(3, pokemon1, new DistanceEuclidienne()));
    }

    @Test
    public void testTrouverMeilleurKDistanceManhattan(){
        assertEquals(1, mKnn.trouverMeilleurK(new DistanceManhattan()));
    }

    @Test
    public void testTrouverMeilleurKDistanceEuclidienne(){
        assertEquals(1, mKnn.trouverMeilleurK(new DistanceEuclidienne()));
    }

    @Test
    public void testGetEtSetAmplitudeAttack(){
        mKnn.setAmplitudeAttack(2.3);
        assertEquals(2.3, mKnn.getAmplitudeAttack());
    }

    @Test
    public void testGetEtSetAmplitudeBaseEggSteps(){
        mKnn.setAmplitudeBaseEggSteps(2.3);
        assertEquals(2.3, mKnn.getAmplitudeBaseEggSteps());
    }

    @Test
    public void testGetEtSetAmplitudeCaptureRate(){
        mKnn.setAmplitudeCaptureRate(2.3);
        assertEquals(2.3, mKnn.getAmplitudeCaptureRate());
    }

    @Test
    public void testGetEtSetAmplitudeDefense(){
        mKnn.setAmplitudeDefense(2.3);
        assertEquals(2.3, mKnn.getAmplitudeDefense());
    }

    @Test
    public void testGetEtSetAmplitudeExperienceGrowth(){
        mKnn.setAmplitudeExperienceGrowth(2.3);
        assertEquals(2.3, mKnn.getAmplitudeExperienceGrowth());
    }

    @Test
    public void testGetEtSetAmplitudeHp(){
        mKnn.setAmplitudeHp(2.3);
        assertEquals(2.3, mKnn.getAmplitudeHp());
    }

    @Test
    public void testGetEtSetAmplitudeSpAttack(){
        mKnn.setAmplitudeSpAttack(2.3);
        assertEquals(2.3, mKnn.getAmplitudeSpAttack());
    }

    @Test
    public void testGetEtSetAmplitudeSpDefense(){
        mKnn.setAmplitudeSpDefense(2.3);
        assertEquals(2.3, mKnn.getAmplitudeSpDefense());
    }

    @Test
    public void testGetEtSetAmplitudeSpeed(){
        mKnn.setAmplitudeSpeed(2.3);
        assertEquals(2.3, mKnn.getAmplitudeSpeed());
    }

    @Test
    public void testGetEtSetAmplitudeIsLegendary(){
        mKnn.setAmplitudeIsLegendary(2.3);
        assertEquals(2.3, mKnn.getAmplitudeIsLegendary());
    }
}
