package fr.univlille.iut.sae302.utils;

import fr.univlille.iut.sae302.Iris;
import fr.univlille.iut.sae302.MethodeKnn;
import fr.univlille.iut.sae302.Pokemon;

/**
 * Classe implémentant l'interface Distance pour calculer la distance euclidienne normalisée entre deux objets.
 */
@SuppressWarnings("rawtypes")
public class DistanceEuclidienneNormalisee implements Distance {

    /**
     * Calcule la distance euclidienne normalisée entre deux objets.
     * 
     * @param t1 Le premier objet, soit de type Iris soit de type Pokemon.
     * @param t2 Le deuxième objet, soit de type Iris soit de type Pokemon.
     * @return La distance euclidienne normalisée entre les deux objets.
     * @throws IllegalArgumentException Si les objets sont null.
     */
    @Override
    public double distance(Object t1, Object t2) {
        if (t1 == null || t2 == null) {
            throw new IllegalArgumentException("Les objets ne doivent pas être null");
        }
        double distance = 0;
        if (t1 instanceof Iris && t2 instanceof Iris) {
            Iris iris1 = (Iris) t1;
            Iris iris2 = (Iris) t2;
            double petalLengthDiff = (iris1.getPetalLength().doubleValue() - iris2.getPetalLength().doubleValue()) / MethodeKnn.amplitudePetalLength;
            double petalWidthDiff = (iris1.getPetalWidth().doubleValue() - iris2.getPetalWidth().doubleValue()) / MethodeKnn.amplitudePetalWidth;
            double sepalLengthDiff = (iris1.getSepalLength().doubleValue() - iris2.getSepalLength().doubleValue()) / MethodeKnn.amplitudeSepalLength;
            double sepalWidthDiff = (iris1.getSepalWidth().doubleValue() - iris2.getSepalWidth().doubleValue()) / MethodeKnn.amplitudeSepalWidth;
            distance = Math.sqrt(
                    petalLengthDiff * petalLengthDiff +
                            petalWidthDiff * petalWidthDiff +
                            sepalLengthDiff * sepalLengthDiff +
                            sepalWidthDiff * sepalWidthDiff
            );
        }
        else if (t1 instanceof Pokemon && t2 instanceof Pokemon) {
            Pokemon pokemon1 = (Pokemon) t1;
            Pokemon pokemon2 = (Pokemon) t2;
            double attackDiff = (pokemon1.getAttack().doubleValue() - pokemon2.getAttack().doubleValue()) / MethodeKnn.amplitudeAttack;
            double defenseDiff = (pokemon1.getDefense().doubleValue() - pokemon2.getDefense().doubleValue()) / MethodeKnn.amplitudeDefense;
            double hpDiff = (pokemon1.getHp().doubleValue() - pokemon2.getHp().doubleValue()) / MethodeKnn.amplitudeHp;
            double speedDiff = (pokemon1.getSpeed().doubleValue() - pokemon2.getSpeed().doubleValue()) / MethodeKnn.amplitudeSpeed;
            double spAttackDiff = (pokemon1.getSpAttack().doubleValue() - pokemon2.getSpAttack().doubleValue()) / MethodeKnn.amplitudeSpAttack;
            double spDefenseDiff = (pokemon1.getSpDefense().doubleValue() - pokemon2.getSpDefense().doubleValue()) / MethodeKnn.amplitudeSpDefense;
            double isLegendaryDiff = (pokemon1.getIsLegendary() - pokemon2.getIsLegendary()) / MethodeKnn.amplitudeIsLegendary;
            distance = Math.sqrt(
                    attackDiff * attackDiff +
                            defenseDiff * defenseDiff +
                            hpDiff * hpDiff +
                            speedDiff * speedDiff +
                            spAttackDiff * spAttackDiff +
                            spDefenseDiff * spDefenseDiff +
                            isLegendaryDiff * isLegendaryDiff
            );
        }
        return distance;
    }
}
