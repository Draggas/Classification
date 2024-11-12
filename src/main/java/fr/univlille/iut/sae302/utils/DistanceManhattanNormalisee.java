package fr.univlille.iut.sae302.utils;

import fr.univlille.iut.sae302.Iris;
import fr.univlille.iut.sae302.MethodeKnn;

public class DistanceManhattanNormalisee implements Distance {

    @Override
    public double distance(Iris iris1, Iris iris2) {
        if (MethodeKnn.amplitudePetalLength == 0 || MethodeKnn.amplitudePetalWidth == 0 ||
                MethodeKnn.amplitudeSepalLength == 0 || MethodeKnn.amplitudeSepalWidth == 0) {
            throw new IllegalArgumentException("Les amplitudes ne doivent pas être égales à zéro");
        }

        double petalLengthDiff = Math.abs(iris1.getPetalLength().doubleValue() - iris2.getPetalLength().doubleValue()) / MethodeKnn.amplitudePetalLength;
        double petalWidthDiff = Math.abs(iris1.getPetalWidth().doubleValue() - iris2.getPetalWidth().doubleValue()) / MethodeKnn.amplitudePetalWidth;
        double sepalLengthDiff = Math.abs(iris1.getSepalLength().doubleValue() - iris2.getSepalLength().doubleValue()) / MethodeKnn.amplitudeSepalLength;
        double sepalWidthDiff = Math.abs(iris1.getSepalWidth().doubleValue() - iris2.getSepalWidth().doubleValue()) / MethodeKnn.amplitudeSepalWidth;

        return petalLengthDiff + petalWidthDiff + sepalLengthDiff + sepalWidthDiff;
    }
}
