package fr.univlille.iut.sae302.utils;

import fr.univlille.iut.sae302.Iris;

public class DistanceManhattan implements Distance {

    @Override
    public double distance(Iris iris1, Iris iris2) {
        double petalLengthDiff = Math.abs(iris1.getPetalLength().doubleValue() - iris2.getPetalLength().doubleValue());
        double petalWidthDiff = Math.abs(iris1.getPetalWidth().doubleValue() - iris2.getPetalWidth().doubleValue());
        double sepalLengthDiff = Math.abs(iris1.getSepalLength().doubleValue() - iris2.getSepalLength().doubleValue());
        double sepalWidthDiff = Math.abs(iris1.getSepalWidth().doubleValue() - iris2.getSepalWidth().doubleValue());

        return petalLengthDiff + petalWidthDiff + sepalLengthDiff + sepalWidthDiff;
    }
}
