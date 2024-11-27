package fr.univlille.iut.sae302.utils;

import fr.univlille.iut.sae302.Iris;

public class DistanceEuclidienne implements Distance {
    @Override
    public double distance(Iris i1, Iris i2) {
        double petalLengthDiff = i1.getPetalLength().doubleValue() - i2.getPetalLength().doubleValue();
        double petalWidthDiff = i1.getPetalWidth().doubleValue() - i2.getPetalWidth().doubleValue();
        double sepalLengthDiff = i1.getSepalLength().doubleValue() - i2.getSepalLength().doubleValue();
        double sepalWidthDiff = i1.getSepalWidth().doubleValue() - i2.getSepalWidth().doubleValue();

        return Math.sqrt(
                petalLengthDiff * petalLengthDiff +
                        petalWidthDiff * petalWidthDiff +
                        sepalLengthDiff * sepalLengthDiff +
                        sepalWidthDiff * sepalWidthDiff
        );
    }
}
