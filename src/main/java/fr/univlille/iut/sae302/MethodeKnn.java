package fr.univlille.iut.sae302;

import fr.univlille.iut.sae302.utils.Distance;
import fr.univlille.iut.sae302.utils.DistanceManhattan;

import java.util.*;


public class MethodeKnn<T> {
    private Data<T> datas;

    public static double amplitudePetalLength;
    public static double amplitudePetalWidth;
    public static double amplitudeSepalLength;
    public static double amplitudeSepalWidth;

    public static  double amplitudeAttack;
    public static  double amplitudeBaseEggSteps;
    public static  double amplitudeCaptureRate;
    public static  double amplitudeDefense;
    public static  double amplitudeExperienceGrowth;
    public static  double amplitudeHp;
    public static  double amplitudeSpAttack;
    public static  double amplitudeSpDefense;
    public static  double amplitudeSpeed;
    public static  double amplitudeIsLegendary;

    // Constructeur générique
    public MethodeKnn(Data<T> datas) {
        this.datas = datas;
        calculerAmplitudes();
    }

    public void calculerAmplitudes() {
        if (datas.isEmpty()) {
            amplitudePetalLength = 0;
            amplitudePetalWidth = 0;
            amplitudeSepalLength = 0;
            amplitudeSepalWidth = 0;

            amplitudeAttack = 0;
            amplitudeBaseEggSteps = 0;
            amplitudeCaptureRate = 0;
            amplitudeDefense = 0;
            amplitudeExperienceGrowth = 0;
            amplitudeHp = 0;
            amplitudeSpAttack = 0;
            amplitudeSpDefense = 0;
            amplitudeSpeed = 0;
            amplitudeIsLegendary = 0;
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


        double minAttack = Double.MAX_VALUE, maxAttack = Double.MIN_VALUE;
        double minBaseEggSteps = Double.MAX_VALUE, maxBaseEggSteps = Double.MIN_VALUE;
        double minCaptureRate = Double.MAX_VALUE, maxCaptureRate = Double.MIN_VALUE;
        double minDefense = Double.MAX_VALUE, maxDefense = Double.MIN_VALUE;
        double minExperienceGrowth = Double.MAX_VALUE, maxExperienceGrowth = Double.MIN_VALUE;
        double minHp = Double.MAX_VALUE, maxHp = Double.MIN_VALUE;
        double minSpAttack = Double.MAX_VALUE, maxSpAttack = Double.MIN_VALUE;
        double minSpDefense = Double.MAX_VALUE, maxSpDefense = Double.MIN_VALUE;
        double minSpeed = Double.MAX_VALUE, maxSpeed = Double.MIN_VALUE;
        double minIsLegendary = Double.MAX_VALUE, maxIsLegendary = Double.MIN_VALUE;

        // Parcours des données pour calculer les min et max
        for (Object data : datas.getEData()) {
            if (data instanceof Iris) {
                Iris iris = (Iris) data;
                if (iris.getPetalLength().doubleValue() < petalLengthMin) petalLengthMin = iris.getPetalLength().doubleValue();
                if (iris.getPetalLength() .doubleValue() > petalLengthMax) petalLengthMax = iris.getPetalLength().doubleValue();
                if (iris.getPetalWidth().doubleValue() < petalWidthMin) petalWidthMin = iris.getPetalWidth().doubleValue();
                if (iris.getPetalWidth().doubleValue()> petalWidthMax) petalWidthMax = iris.getPetalWidth().doubleValue();
                if (iris.getSepalLength().doubleValue() < sepalLengthMin) sepalLengthMin = iris.getSepalLength().doubleValue();
                if (iris.getSepalLength().doubleValue() > sepalLengthMax) sepalLengthMax = iris.getSepalLength().doubleValue();
                if (iris.getSepalWidth().doubleValue() < sepalWidthMin) sepalWidthMin = iris.getSepalWidth().doubleValue();
                if (iris.getSepalWidth().doubleValue() > sepalWidthMax) sepalWidthMax = iris.getSepalWidth().doubleValue();

                amplitudePetalLength = petalLengthMax - petalLengthMin;
                amplitudePetalWidth = petalWidthMax - petalWidthMin;
                amplitudeSepalLength = sepalLengthMax - sepalLengthMin;
                amplitudeSepalWidth = sepalWidthMax - sepalWidthMin;
            } else if (data instanceof Pokemon) {
                Pokemon pokemon = (Pokemon) data;
                if (pokemon.getAttack().doubleValue() < minAttack) minAttack = pokemon.getAttack().doubleValue();
                if (pokemon.getAttack().doubleValue() > maxAttack) maxAttack = pokemon.getAttack().doubleValue();
                if (pokemon.getEggSteps().doubleValue() < minBaseEggSteps) minBaseEggSteps = pokemon.getEggSteps().doubleValue();
                if (pokemon.getEggSteps().doubleValue() > maxBaseEggSteps) maxBaseEggSteps = pokemon.getEggSteps().doubleValue();
                if (pokemon.getCaptureRate().doubleValue() < minCaptureRate) minCaptureRate = pokemon.getCaptureRate().doubleValue();
                if (pokemon.getCaptureRate().doubleValue() > maxCaptureRate) maxCaptureRate = pokemon.getCaptureRate().doubleValue();
                if (pokemon.getCaptureRate().doubleValue() < minDefense) minDefense = pokemon.getCaptureRate().doubleValue();
                if (pokemon.getCaptureRate().doubleValue() > maxDefense) maxDefense = pokemon.getCaptureRate().doubleValue();
                if (pokemon.getExperience().doubleValue() < minExperienceGrowth) minExperienceGrowth = pokemon.getExperience().doubleValue();
                if (pokemon.getExperience().doubleValue() > maxExperienceGrowth) maxExperienceGrowth = pokemon.getExperience().doubleValue();
                if (pokemon.getHp().doubleValue() < minHp) minHp = pokemon.getHp().doubleValue();
                if (pokemon.getHp().doubleValue() > maxHp) maxHp = pokemon.getHp().doubleValue();
                if (pokemon.getSpAttack().doubleValue() < minSpAttack) minSpAttack = pokemon.getSpAttack().doubleValue();
                if (pokemon.getSpAttack().doubleValue() > maxSpAttack) maxSpAttack = pokemon.getSpAttack().doubleValue();
                if (pokemon.getSpDefense().doubleValue() < minSpDefense) minSpDefense = pokemon.getSpDefense().doubleValue();
                if (pokemon.getSpDefense().doubleValue() > maxSpDefense) maxSpDefense = pokemon.getSpDefense().doubleValue();
                if (pokemon.getSpeed().doubleValue() < minSpeed) minSpeed = pokemon.getSpeed().doubleValue();
                if (pokemon.getSpeed().doubleValue() > maxSpeed) maxSpeed = pokemon.getSpeed().doubleValue();
                if (pokemon.getIsLegendary() < minIsLegendary) minIsLegendary = pokemon.getIsLegendary();
                if (pokemon.getIsLegendary() > maxIsLegendary) maxIsLegendary = pokemon.getIsLegendary();

                amplitudeAttack = maxAttack - minAttack;
                amplitudeBaseEggSteps = maxBaseEggSteps - minBaseEggSteps;
                amplitudeCaptureRate = maxCaptureRate - minCaptureRate;
                amplitudeDefense = maxDefense - minDefense;
                amplitudeExperienceGrowth = maxExperienceGrowth - minExperienceGrowth;
                amplitudeHp = maxHp - minHp;
                amplitudeSpAttack = maxSpAttack - minSpAttack;
                amplitudeSpDefense = maxSpDefense - minSpDefense;
                amplitudeSpeed = maxSpeed - minSpeed;
                amplitudeIsLegendary = maxIsLegendary - minIsLegendary;
            }
        }
    }


    public T[] getKPlusProchesVoisins(int k, T cible, Distance<T> distance) {
        List<T> autresObjets = new ArrayList<>(this.datas.getEData());
        autresObjets.removeIf(objet -> objet.equals(cible));
        k = Math.min(k, autresObjets.size());
        List<Map.Entry<T, Double>> distances = new ArrayList<>();
        for (T objet : autresObjets) {
            double dist = distance.distance(cible, objet);
            distances.add(new AbstractMap.SimpleEntry<>(objet, dist));
            System.out.println("Distance entre " + cible + " et " + objet + " : " + dist);
        }
        distances.sort(Map.Entry.comparingByValue());
        T[] voisins = (T[]) new Object[k];
        for (int i = 0; i < k; i++) {
            voisins[i] = distances.get(i).getKey();
        }

        return voisins;
    }

    public String classifierObjet(int k, T cible, Distance<T> distance) {
        T[] voisins = getKPlusProchesVoisins(k, cible, distance);
        Map<String, Integer> voteMap = new HashMap<>();
        for (T voisin : voisins) {
            String classification = getClassification(voisin);
            voteMap.put(classification, voteMap.getOrDefault(classification, 0) + 1);
        }
        String classificationPredite = null;
        int maxVotes = -1;
        for (Map.Entry<String, Integer> entry : voteMap.entrySet()) {
            if (entry.getValue() > maxVotes) {
                maxVotes = entry.getValue();
                classificationPredite = entry.getKey();
            } else if (entry.getValue() == maxVotes) {
                if (classificationPredite == null) {
                    classificationPredite = "Defaut";
                }
            }
        }
        return classificationPredite;
    }

    public double calculerPourcentageReussite(int k, Distance<T> distance) {
        int total = this.datas.getEData().size();
        int correctPredictions = 0;
        for (T cible : this.datas.getEData()) {
            List<T> autresObjets = new ArrayList<>(this.datas.getEData());
            autresObjets.remove(cible);
            Data<T> autreData = new Data<>(autresObjets);
            MethodeKnn<T> knnTemp = new MethodeKnn<>(autreData);
            String predictedClass = knnTemp.classifierObjet(k, cible, distance);
            String trueClass = getClassification(cible);
            if (predictedClass != null && predictedClass.equals(trueClass)) {
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

    /*public double getPetalLengthMin() {
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
    }*/

    public Data<T> getDatas() {
        return datas;
    }

    public void setDatas(Data<T> datas) {
        this.datas = datas;
    }

    private String getClassification(T objet) {
        if (objet instanceof Iris) {
            return ((Iris) objet).getVariety();
        } else if (objet instanceof Pokemon) {
            return ((Pokemon) objet).getType1().getName();
        }
        return "Unknown";
    }

    public double getAmplitudeAttack() {
        return amplitudeAttack;
    }

    public void setAmplitudeAttack(double amplitudeAttack) {
        this.amplitudeAttack = amplitudeAttack;
    }

    public double getAmplitudeBaseEggSteps() {
        return amplitudeBaseEggSteps;
    }

    public void setAmplitudeBaseEggSteps(double amplitudeBaseEggSteps) {
        this.amplitudeBaseEggSteps = amplitudeBaseEggSteps;
    }

    public double getAmplitudeCaptureRate() {
        return amplitudeCaptureRate;
    }

    public void setAmplitudeCaptureRate(double amplitudeCaptureRate) {
        this.amplitudeCaptureRate = amplitudeCaptureRate;
    }

    public double getAmplitudeDefense() {
        return amplitudeDefense;
    }

    public void setAmplitudeDefense(double amplitudeDefense) {
        this.amplitudeDefense = amplitudeDefense;
    }

    public double getAmplitudeExperienceGrowth() {
        return amplitudeExperienceGrowth;
    }

    public void setAmplitudeExperienceGrowth(double amplitudeExperienceGrowth) {
        this.amplitudeExperienceGrowth = amplitudeExperienceGrowth;
    }

    public double getAmplitudeHp() {
        return amplitudeHp;
    }

    public void setAmplitudeHp(double amplitudeHp) {
        this.amplitudeHp = amplitudeHp;
    }

    public double getAmplitudeSpAttack() {
        return amplitudeSpAttack;
    }

    public void setAmplitudeSpAttack(double amplitudeSpAttack) {
        this.amplitudeSpAttack = amplitudeSpAttack;
    }

    public double getAmplitudeSpDefense() {
        return amplitudeSpDefense;
    }

    public void setAmplitudeSpDefense(double amplitudeSpDefense) {
        this.amplitudeSpDefense = amplitudeSpDefense;
    }

    public double getAmplitudeSpeed() {
        return amplitudeSpeed;
    }

    public void setAmplitudeSpeed(double amplitudeSpeed) {
        this.amplitudeSpeed = amplitudeSpeed;
    }

    public double getAmplitudeIsLegendary() {
        return amplitudeIsLegendary;
    }

    public void setAmplitudeIsLegendary(double amplitudeIsLegendary) {
        this.amplitudeIsLegendary = amplitudeIsLegendary;
    }
}
