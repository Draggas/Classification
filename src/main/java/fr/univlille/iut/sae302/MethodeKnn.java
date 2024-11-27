package fr.univlille.iut.sae302;

import fr.univlille.iut.sae302.utils.Distance;

import java.util.*;

public class MethodeKnn {
    private Data<Iris> datas;

    public static double amplitudePetalLength;
    public static double amplitudePetalWidth;
    public static double amplitudeSepalLength;
    public static double amplitudeSepalWidth;


    public MethodeKnn(Data<Iris> datas) {
        this.datas = datas;
        calculerAmplitudes();
    }

    public void calculerAmplitudes() {
        if (datas.isEmpty()) {
            amplitudePetalLength = 0;
            amplitudePetalWidth = 0;
            amplitudeSepalLength = 0;
            amplitudeSepalWidth = 0;
            return;
        }

        double petalLengthMin = Double.MAX_VALUE;
        double petalLengthMax = Double.MIN_VALUE;
        double petalWidthMin = Double.MAX_VALUE;
        double petalWidthMax = Double.MIN_VALUE;
        double sepalLengthMin = Double.MAX_VALUE;
        double sepalLengthMax = Double.MIN_VALUE;
        double sepalWidthMin = Double.MAX_VALUE;
        double sepalWidthMax = Double.MIN_VALUE;

        for (Iris iris : datas.getEData()) {
            if (iris.getPetalLength().doubleValue() < petalLengthMin) petalLengthMin = iris.getPetalLength().doubleValue();
            if (iris.getPetalLength() .doubleValue() > petalLengthMax) petalLengthMax = iris.getPetalLength().doubleValue();
            if (iris.getPetalWidth().doubleValue() < petalWidthMin) petalWidthMin = iris.getPetalWidth().doubleValue();
            if (iris.getPetalWidth().doubleValue()> petalWidthMax) petalWidthMax = iris.getPetalWidth().doubleValue();
            if (iris.getSepalLength().doubleValue() < sepalLengthMin) sepalLengthMin = iris.getSepalLength().doubleValue();
            if (iris.getSepalLength().doubleValue() > sepalLengthMax) sepalLengthMax = iris.getSepalLength().doubleValue();
            if (iris.getSepalWidth().doubleValue() < sepalWidthMin) sepalWidthMin = iris.getSepalWidth().doubleValue();
            if (iris.getSepalWidth().doubleValue() > sepalWidthMax) sepalWidthMax = iris.getSepalWidth().doubleValue();
        }

        amplitudePetalLength = petalLengthMax - petalLengthMin;
        amplitudePetalWidth = petalWidthMax - petalWidthMin;
        amplitudeSepalLength = sepalLengthMax - sepalLengthMin;
        amplitudeSepalWidth = sepalWidthMax - sepalWidthMin;
    }

    public Iris[] getKPlusProchesVoisins(int k, Iris cible, Distance distance) {
        List<Iris> autresIris = new ArrayList<>(this.datas.getEData());
        autresIris.removeIf(iris -> iris.equals(cible));


        k = Math.min(k, autresIris.size());

        List<Map.Entry<Iris, Double>> distances = new ArrayList<>();

        for (Iris iris : autresIris) {
            double dist = distance.distance(cible, iris);
            distances.add(new AbstractMap.SimpleEntry<>(iris, dist));
        }

        distances.sort(Map.Entry.comparingByValue());

        Iris[] voisins = new Iris[k];
        for (int i = 0; i < k; i++) {
            voisins[i] = distances.get(i).getKey();
        }

        return voisins;
    }

    public String classifierIris(int k, Iris cible, Distance distance) {
        Iris[] voisins = getKPlusProchesVoisins(k, cible, distance);

        Map<String, Integer> voteMap = new HashMap<>();
        voteMap.put("Setosa", 0);
        voteMap.put("Versicolor", 0);
        voteMap.put("Virginica", 0);

        for (Iris voisin : voisins) {
            String variete = voisin.getVariety();
            voteMap.put(variete, voteMap.get(variete) + 1);
        }

        String classePredite = null;
        int maxVotes = -1;
        for (Map.Entry<String, Integer> entry : voteMap.entrySet()) {
            if (entry.getValue() > maxVotes) {
                maxVotes = entry.getValue();
                classePredite = entry.getKey();
            } else if (entry.getValue() == maxVotes) {
                if (classePredite == null) {
                    classePredite = "Defaut";
                }
            }
        }
        return classePredite;
    }

    public double calculerPourcentageReussite(int k, Distance distance) {
        int total = this.datas.getEData().size();
        int correctPredictions = 0;
        for (Iris iris : this.datas.getEData()) {
            List<Iris> autresIris = new ArrayList<>(this.datas.getEData());
            autresIris.remove(iris);

            String predictedVariety = classifierIris(k, iris, distance);
            if (predictedVariety != null && predictedVariety.equals(iris.getVariety())) {
                correctPredictions++;
            }
        }
        return (correctPredictions / (double) total) * 100;
    }


    public int trouverMeilleurK(Distance distance) {
        int meilleurK = 1;
        double meilleurPourcentage = 0;
        for (int k = 1; k <= 11; k += 2) {
            double pourcentageReussite = calculerPourcentageReussite(k, distance);
            System.out.println("Pourcentage de réussite pour k=" + k + " : " + pourcentageReussite + "%");
            if (pourcentageReussite > meilleurPourcentage) {
                meilleurPourcentage = pourcentageReussite;
                meilleurK = k;
            }
        }
        System.out.println("Meilleur k pour la distance " + distance.getClass().getSimpleName() +
                " est " + meilleurK + " avec un pourcentage de réussite de " + meilleurPourcentage + "%");
        return meilleurK;
    }

    public double getPetalLengthMin() {
        return datas.getEData().stream().mapToDouble(iris -> iris.getPetalLength().doubleValue()).min().orElse(0);
    }

    public double getPetalWidthMin() {
        return datas.getEData().stream().mapToDouble(iris -> iris.getPetalWidth().doubleValue()).min().orElse(0);
    }

    public double getSepalLengthMin() {
        return datas.getEData().stream().mapToDouble(iris -> iris.getSepalLength().doubleValue()).min().orElse(0);
    }

    public double getSepalWidthMin() {
        return datas.getEData().stream().mapToDouble(iris -> iris.getSepalWidth().doubleValue()).min().orElse(0);
    }

    public Data<Iris> getDatas() {
        return datas;
    }

    public void setDatas(Data<Iris> datas) {
        this.datas = datas;
    }

}
