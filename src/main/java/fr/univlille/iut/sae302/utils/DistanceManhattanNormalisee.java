package fr.univlille.iut.sae302.utils;

import fr.univlille.iut.sae302.Iris;
import fr.univlille.iut.sae302.MethodeKnn;
import fr.univlille.iut.sae302.Pokemon;

/**
 * Cette classe calcule la distance de Manhattan normalisée entre deux objets de type Iris ou Pokemon.
 */
public class DistanceManhattanNormalisee implements Distance {

    /**
     * Calcule la distance de Manhattan normalisée entre deux objets.
     * 
     * @param t1 Le premier objet, soit de type Iris soit de type Pokemon.
     * @param t2 Le deuxième objet, soit de type Iris soit de type Pokemon.
     * @return La distance de Manhattan normalisée entre les deux objets.
     * @throws IllegalArgumentException Si les objets ou les amplitudes sont nulles ou égales à zéro.
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
            if (MethodeKnn.amplitudePetalLength == 0 || MethodeKnn.amplitudePetalWidth == 0 ||
                    MethodeKnn.amplitudeSepalLength == 0 || MethodeKnn.amplitudeSepalWidth == 0) {
                throw new IllegalArgumentException("Les amplitudes ne doivent pas être égales à zéro");
            }
            double petalLengthDiff = Math.abs(iris1.getPetalLength().doubleValue() - iris2.getPetalLength().doubleValue()) / MethodeKnn.amplitudePetalLength;
            double petalWidthDiff = Math.abs(iris1.getPetalWidth().doubleValue() - iris2.getPetalWidth().doubleValue()) / MethodeKnn.amplitudePetalWidth;
            double sepalLengthDiff = Math.abs(iris1.getSepalLength().doubleValue() - iris2.getSepalLength().doubleValue()) / MethodeKnn.amplitudeSepalLength;
            double sepalWidthDiff = Math.abs(iris1.getSepalWidth().doubleValue() - iris2.getSepalWidth().doubleValue()) / MethodeKnn.amplitudeSepalWidth;
            distance = petalLengthDiff + petalWidthDiff + sepalLengthDiff + sepalWidthDiff;
        }
        else if (t1 instanceof Pokemon && t2 instanceof Pokemon) {
            Pokemon pokemon1 = (Pokemon) t1;
            Pokemon pokemon2 = (Pokemon) t2;
            if (MethodeKnn.amplitudeAttack == 0 || MethodeKnn.amplitudeDefense == 0 ||
                    MethodeKnn.amplitudeHp == 0 || MethodeKnn.amplitudeSpeed == 0 ||
                    MethodeKnn.amplitudeSpAttack == 0 || MethodeKnn.amplitudeSpDefense == 0 ||
                    MethodeKnn.amplitudeIsLegendary == 0) {
                throw new IllegalArgumentException("Les amplitudes ne doivent pas être égales à zéro");
            }
            double attackDiff = Math.abs(pokemon1.getAttack().doubleValue() - pokemon2.getAttack().doubleValue()) / MethodeKnn.amplitudeAttack;
            double defenseDiff = Math.abs(pokemon1.getDefense().doubleValue() - pokemon2.getDefense().doubleValue()) / MethodeKnn.amplitudeDefense;
            double hpDiff = Math.abs(pokemon1.getHp().doubleValue() - pokemon2.getHp().doubleValue()) / MethodeKnn.amplitudeHp;
            double speedDiff = Math.abs(pokemon1.getSpeed().doubleValue() - pokemon2.getSpeed().doubleValue()) / MethodeKnn.amplitudeSpeed;
            double spAttackDiff = Math.abs(pokemon1.getSpAttack().doubleValue() - pokemon2.getSpAttack().doubleValue()) / MethodeKnn.amplitudeSpAttack;
            double spDefenseDiff = Math.abs(pokemon1.getSpDefense().doubleValue() - pokemon2.getSpDefense().doubleValue()) / MethodeKnn.amplitudeSpDefense;
            double isLegendaryDiff = Math.abs(pokemon1.getIsLegendary() - pokemon2.getIsLegendary()) / MethodeKnn.amplitudeIsLegendary;
            distance = attackDiff + defenseDiff + hpDiff + speedDiff + spAttackDiff + spDefenseDiff + isLegendaryDiff;
        }

        return distance;
    }
}
