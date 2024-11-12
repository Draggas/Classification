package fr.univlille.iut.sae302.utils;

import fr.univlille.iut.sae302.Iris;
import fr.univlille.iut.sae302.MethodeKnn;

public class DistanceManhattan implements Distance {

    @Override
    public double distance(Iris iris1, Iris iris2) {
        double petalLengthDiff = Math.abs(iris1.getPetalLength().doubleValue() - iris2.getPetalLength().doubleValue()) / MethodeKnn.amplitudePetalLength;
        double petalWidthDiff = Math.abs(iris1.getPetalWidth().doubleValue() - iris2.getPetalWidth().doubleValue()) / MethodeKnn.amplitudePetalWidth;
        double sepalLengthDiff = Math.abs(iris1.getSepalLength().doubleValue() - iris2.getSepalLength().doubleValue()) / MethodeKnn.amplitudeSepalLength;
        double sepalWidthDiff = Math.abs(iris1.getSepalWidth().doubleValue() - iris2.getSepalWidth().doubleValue()) / MethodeKnn.amplitudeSepalWidth;

        return petalLengthDiff + petalWidthDiff + sepalLengthDiff + sepalWidthDiff;
    }
}
