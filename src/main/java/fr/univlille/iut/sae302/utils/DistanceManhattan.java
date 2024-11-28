package fr.univlille.iut.sae302.utils;

import fr.univlille.iut.sae302.Iris;
import fr.univlille.iut.sae302.Pokemon;

public class DistanceManhattan implements Distance {

    @Override
    public double distance(Object t1, Object t2) {
        if (t1 == null || t2 == null) {
            throw new IllegalArgumentException("Les objets ne doivent pas être null");
        }
        double distance = 0;
        if (t1 instanceof Iris && t2 instanceof Iris) {
            Iris iris1 = (Iris) t1;
            Iris iris2 = (Iris) t2;
            double petalLengthDiff = Math.abs(iris1.getPetalLength().doubleValue() - iris2.getPetalLength().doubleValue());
            double petalWidthDiff = Math.abs(iris1.getPetalWidth().doubleValue() - iris2.getPetalWidth().doubleValue());
            double sepalLengthDiff = Math.abs(iris1.getSepalLength().doubleValue() - iris2.getSepalLength().doubleValue());
            double sepalWidthDiff = Math.abs(iris1.getSepalWidth().doubleValue() - iris2.getSepalWidth().doubleValue());
            distance = petalLengthDiff + petalWidthDiff + sepalLengthDiff + sepalWidthDiff;
        }
        else if (t1 instanceof Pokemon && t2 instanceof Pokemon) {
            Pokemon pokemon1 = (Pokemon) t1;
            Pokemon pokemon2 = (Pokemon) t2;
            double attackDiff = Math.abs(pokemon1.getAttack().doubleValue() - pokemon2.getAttack().doubleValue());
            double defenseDiff = Math.abs(pokemon1.getDefense().doubleValue() - pokemon2.getDefense().doubleValue());
            double hpDiff = Math.abs(pokemon1.getHp().doubleValue() - pokemon2.getHp().doubleValue());
            double speedDiff = Math.abs(pokemon1.getSpeed().doubleValue() - pokemon2.getSpeed().doubleValue());
            double spAttackDiff = Math.abs(pokemon1.getSpAttack().doubleValue() - pokemon2.getSpAttack().doubleValue());
            double spDefenseDiff = Math.abs(pokemon1.getSpDefense().doubleValue() - pokemon2.getSpDefense().doubleValue());
            double isLegendaryDiff = Math.abs(pokemon1.getIsLegendary() - pokemon2.getIsLegendary());
            distance = attackDiff + defenseDiff + hpDiff + speedDiff + spAttackDiff + spDefenseDiff + isLegendaryDiff;
        }
        return distance;
    }
}

