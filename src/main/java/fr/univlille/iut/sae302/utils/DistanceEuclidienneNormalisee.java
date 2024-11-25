package fr.univlille.iut.sae302.utils;

import fr.univlille.iut.sae302.model.Iris;
import fr.univlille.iut.sae302.model.MethodeKnn;

public class DistanceEuclidienneNormalisee implements Distance {

    @Override
    public double distance(Iris iris1, Iris iris2) {
        double petalLengthDiff = (iris1.getPetalLength().doubleValue() - iris2.getPetalLength().doubleValue()) / MethodeKnn.amplitudePetalLength;
        double petalWidthDiff = (iris1.getPetalWidth().doubleValue() - iris2.getPetalWidth().doubleValue()) / MethodeKnn.amplitudePetalWidth;
        double sepalLengthDiff = (iris1.getSepalLength().doubleValue() - iris2.getSepalLength().doubleValue()) / MethodeKnn.amplitudeSepalLength;
        double sepalWidthDiff = (iris1.getSepalWidth().doubleValue() - iris2.getSepalWidth().doubleValue()) / MethodeKnn.amplitudeSepalWidth;

        return Math.sqrt(
                petalLengthDiff * petalLengthDiff +
                        petalWidthDiff * petalWidthDiff +
                        sepalLengthDiff * sepalLengthDiff +
                        sepalWidthDiff * sepalWidthDiff
        );
    }
}