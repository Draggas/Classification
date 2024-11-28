package fr.univlille.iut.sae302.utils;

import fr.univlille.iut.sae302.Iris;
import fr.univlille.iut.sae302.Pokemon;

public class DistanceEuclidienne implements Distance {

    @Override
    public double distance(Object t1, Object t2) {
        if (t1 == null || t2 == null) {
            throw new IllegalArgumentException("Les objets ne doivent pas être null");
        }
        double distance = 0;
        if (t1 instanceof Iris && t2 instanceof Iris) {
            Iris i1 = (Iris) t1;
            Iris i2 = (Iris) t2;
            double petalLengthDiff = i1.getPetalLength().doubleValue() - i2.getPetalLength().doubleValue();
            double petalWidthDiff = i1.getPetalWidth().doubleValue() - i2.getPetalWidth().doubleValue();
            double sepalLengthDiff = i1.getSepalLength().doubleValue() - i2.getSepalLength().doubleValue();
            double sepalWidthDiff = i1.getSepalWidth().doubleValue() - i2.getSepalWidth().doubleValue();
            distance = Math.sqrt(
                    petalLengthDiff * petalLengthDiff +
                            petalWidthDiff * petalWidthDiff +
                            sepalLengthDiff * sepalLengthDiff +
                            sepalWidthDiff * sepalWidthDiff
            );
        }
        else if (t1 instanceof Pokemon && t2 instanceof Pokemon) {
            Pokemon p1 = (Pokemon) t1;
            Pokemon p2 = (Pokemon) t2;
            double attackDiff = p1.getAttack().doubleValue() - p2.getAttack().doubleValue();
            double defenseDiff = p1.getDefense().doubleValue() - p2.getDefense().doubleValue();
            double hpDiff = p1.getHp().doubleValue() - p2.getHp().doubleValue();
            double speedDiff = p1.getSpeed().doubleValue() - p2.getSpeed().doubleValue();
            double spAttackDiff = p1.getSpAttack().doubleValue() - p2.getSpAttack().doubleValue();
            double spDefenseDiff = p1.getSpDefense().doubleValue() - p2.getSpDefense().doubleValue();
            double isLegendaryDiff = p1.getIsLegendary() - p2.getIsLegendary();
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
