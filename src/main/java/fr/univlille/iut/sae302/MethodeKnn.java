package fr.univlille.iut.sae302;

import fr.univlille.iut.sae302.utils.Distance;
import fr.univlille.iut.sae302.utils.DistanceManhattan;

import java.util.*;

/** 
 * Constructeur générique de la classe.
 */
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

    /**
     * Constructeur générique de la classe.
     *
     * @param datas Les données utilisées pour l'algorithme KNN.
     */
    public MethodeKnn(Data<T> datas) {
        this.datas = datas;
        calculerAmplitudes();
    }

    /**
     * Calcule les amplitudes des différents attributs des données.
     */
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

    /**
     * Récupère les k plus proches voisins d'un objet cible.
     * 
     * @param k Le nombre de voisins à trouver.
     * @param cible L'objet pour lequel trouver les voisins.
     * @param distance La fonction de distance à utiliser.
     * @return Un tableau des k plus proches voisins.
     */
    public List<T> getKPlusProchesVoisins(int k, T cible, Distance<T> distance) {
        List<T> autresObjets = new ArrayList<>(this.datas.getEData());
        autresObjets.removeIf(objet -> objet.equals(cible));
        k = Math.min(k, autresObjets.size());
        List<Map.Entry<T, Double>> distances = new ArrayList<>();
        for (T objet : autresObjets) {
            double dist = distance.distance(cible, objet);
            distances.add(new AbstractMap.SimpleEntry<>(objet, dist));
        }
        distances.sort(Map.Entry.comparingByValue());
        List<T> voisins = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            voisins.add(distances.get(i).getKey());
        }

        return voisins;
    }

    /**
     * Classifie un objet en fonction de ses k plus proches voisins.
     * 
     * @param k Le nombre de voisins à utiliser pour la classification.
     * @param cible L'objet cible à classifier.
     * @param distance La fonction de distance à utiliser.
     * @return La classe prédite pour l'objet cible.
     */
    public String classifierObjet(int k, T cible, Distance<T> distance) {
        List<T> voisins = getKPlusProchesVoisins(k, cible, distance);
        Map<String, Integer> voteMap = new HashMap<>();

        if (cible instanceof Iris) {
            voteMap.put("Setosa", 0);
            voteMap.put("Versicolor", 0);
            voteMap.put("Virginica", 0);

            for (T voisin : voisins) {
                if (voisin instanceof Iris) {
                    String variete = ((Iris) voisin).getVariety();
                    voteMap.put(variete, voteMap.getOrDefault(variete, 0) + 1);
                }
            }
        } else if (cible instanceof Pokemon) {
            voteMap.put("Fire", 0);
            voteMap.put("Water", 0);
            voteMap.put("Grass", 0);
            voteMap.put("Electric", 0);
            voteMap.put("Normal", 0);
            voteMap.put("Fighting", 0);

            for (T voisin : voisins) {
                if (voisin instanceof Pokemon) {
                    String type = String.valueOf(((Pokemon) voisin).getType1());
                    voteMap.put(type, voteMap.getOrDefault(type, 0) + 1);
                }
            }
        }

        String classePredite = null;
        int maxVotes = -1;

        for (Map.Entry<String, Integer> entry : voteMap.entrySet()) {
            if (entry.getValue() > maxVotes) {
                maxVotes = entry.getValue();
                classePredite = entry.getKey();
            } else if (entry.getValue() == maxVotes) {
                if (classePredite == null) {
                    classePredite = entry.getKey();
                } else {
                    classePredite = "Defaut";
                }
            }
        }

        return classePredite != null ? classePredite : "Defaut";
    }

    /**
     * Calcule le pourcentage de réussite de la classification.
     * 
     * @param k Le nombre de voisins à utiliser pour la classification.
     * @param distance La fonction de distance à utiliser.
     * @return Le pourcentage de réussite de la classification.
     */
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

    /**
     * Trouve la meilleure valeur de k pour la classification.
     * 
     * @param distance La fonction de distance à utiliser.
     * @return La meilleure valeur de k.
     */
    public int trouverMeilleurK(Distance distance) {
        int meilleurK = 1;
        double meilleurPourcentage = 0;
        for (int k = 1; k <= 11; k += 2) {
            double pourcentageReussite = calculerPourcentageReussite(k, distance);
            if (pourcentageReussite > meilleurPourcentage) {
                meilleurPourcentage = pourcentageReussite;
                meilleurK = k;
            }
        }
        return meilleurK;
    }

    /**
     * Obtient les données utilisées par l'algorithme KNN.
     *
     * @return Les données utilisées par l'algorithme KNN.
     */
    public Data<T> getDatas() {
        return datas;
    }

    /**
     * Définit les données utilisées par l'algorithme KNN.
     *
     * @param datas Les données à utiliser par l'algorithme KNN.
     */
    public void setDatas(Data<T> datas) {
        this.datas = datas;
    }

    /**
     * Obtient la classification d'un objet.
     * 
     * @param objet L'objet dont on souhaite connaître la classification.
     * @return La classification de l'objet.
     */
    private String getClassification(T objet) {
        if (objet instanceof Iris) {
            return ((Iris) objet).getVariety();
        } else if (objet instanceof Pokemon) {
            return ((Pokemon) objet).getType1().getName();
        }
        return "Unknown";
    }

    /**
     * Obtient l'amplitude de l'attribut Attack.
     *
     * @return L'amplitude de l'attribut Attack.
     */
    public double getAmplitudeAttack() {
        return amplitudeAttack;
    }

    /**
     * Définit l'amplitude de l'attribut Attack.
     *
     * @param amplitudeAttack L'amplitude de l'attribut Attack.
     */
    public void setAmplitudeAttack(double amplitudeAttack) {
        this.amplitudeAttack = amplitudeAttack;
    }

    /**
     * Obtient l'amplitude de l'attribut BaseEggSteps.
     *
     * @return L'amplitude de l'attribut BaseEggSteps.
     */
    public double getAmplitudeBaseEggSteps() {
        return amplitudeBaseEggSteps;
    }

    /**
     * Définit l'amplitude de l'attribut BaseEggSteps.
     *
     * @param amplitudeBaseEggSteps L'amplitude de l'attribut BaseEggSteps.
     */
    public void setAmplitudeBaseEggSteps(double amplitudeBaseEggSteps) {
        this.amplitudeBaseEggSteps = amplitudeBaseEggSteps;
    }

    /**
     * Obtient l'amplitude de l'attribut CaptureRate.
     *
     * @return L'amplitude de l'attribut CaptureRate.
     */
    public double getAmplitudeCaptureRate() {
        return amplitudeCaptureRate;
    }

    /**
     * Définit l'amplitude de l'attribut CaptureRate.
     *
     * @param amplitudeCaptureRate L'amplitude de l'attribut CaptureRate.
     */
    public void setAmplitudeCaptureRate(double amplitudeCaptureRate) {
        this.amplitudeCaptureRate = amplitudeCaptureRate;
    }

    /**
     * Obtient l'amplitude de l'attribut Defense.
     *
     * @return L'amplitude de l'attribut Defense.
     */
    public double getAmplitudeDefense() {
        return amplitudeDefense;
    }

    /**
     * Définit l'amplitude de l'attribut Defense.
     *
     * @param amplitudeDefense L'amplitude de l'attribut Defense.
     */
    public void setAmplitudeDefense(double amplitudeDefense) {
        this.amplitudeDefense = amplitudeDefense;
    }

    /**
     * Obtient l'amplitude de l'attribut ExperienceGrowth.
     *
     * @return L'amplitude de l'attribut ExperienceGrowth.
     */
    public double getAmplitudeExperienceGrowth() {
        return amplitudeExperienceGrowth;
    }

    /**
     * Définit l'amplitude de l'attribut ExperienceGrowth.
     *
     * @param amplitudeExperienceGrowth L'amplitude de l'attribut ExperienceGrowth.
     */
    public void setAmplitudeExperienceGrowth(double amplitudeExperienceGrowth) {
        this.amplitudeExperienceGrowth = amplitudeExperienceGrowth;
    }

    /**
     * Obtient l'amplitude de l'attribut Hp.
     *
     * @return L'amplitude de l'attribut Hp.
     */
    public double getAmplitudeHp() {
        return amplitudeHp;
    }


    /**
     * Définit l'amplitude de l'attribut Hp.
     *
     * @param amplitudeHp L'amplitude de l'attribut Hp.
     */
    public void setAmplitudeHp(double amplitudeHp) {
        this.amplitudeHp = amplitudeHp;
    }

    /**
     * Obtient l'amplitude de l'attribut SpAttack.
     *
     * @return L'amplitude de l'attribut SpAttack.
     */
    public double getAmplitudeSpAttack() {
        return amplitudeSpAttack;
    }

    /**
     * Définit l'amplitude de l'attribut SpAttack.
     *
     * @param amplitudeSpAttack L'amplitude de l'attribut SpAttack.
     */
    public void setAmplitudeSpAttack(double amplitudeSpAttack) {
        this.amplitudeSpAttack = amplitudeSpAttack;
    }

    /**
     * Obtient l'amplitude de l'attribut SpDefense.
     *
     * @return L'amplitude de l'attribut SpDefense.
     */
    public double getAmplitudeSpDefense() {
        return amplitudeSpDefense;
    }

    /**
     * Définit l'amplitude de l'attribut SpDefense.
     *
     * @param amplitudeSpDefense L'amplitude de l'attribut SpDefense.
     */
    public void setAmplitudeSpDefense(double amplitudeSpDefense) {
        this.amplitudeSpDefense = amplitudeSpDefense;
    }

    /**
     * Obtient l'amplitude de l'attribut Speed.
     *
     * @return L'amplitude de l'attribut Speed.
     */
    public double getAmplitudeSpeed() {
        return amplitudeSpeed;
    }

    /**
     * Définit l'amplitude de l'attribut Speed.
     *
     * @param amplitudeSpeed L'amplitude de l'attribut Speed.
     */
    public void setAmplitudeSpeed(double amplitudeSpeed) {
        this.amplitudeSpeed = amplitudeSpeed;
    }

    /**
     * Obtient l'amplitude de l'attribut IsLegendary.
     *
     * @return L'amplitude de l'attribut IsLegendary.
     */
    public double getAmplitudeIsLegendary() {
        return amplitudeIsLegendary;
    }

    /**
     * Définit l'amplitude de l'attribut IsLegendary.
     *
     * @param amplitudeIsLegendary L'amplitude de l'attribut IsLegendary.
     */
    public void setAmplitudeIsLegendary(double amplitudeIsLegendary) {
        this.amplitudeIsLegendary = amplitudeIsLegendary;
    }
}