package fr.univlille.iut.sae302;

import java.lang.reflect.Field;
import java.util.List;

import fr.univlille.iut.sae302.utils.Observable;

/**
 * La classe Data représente un conteneur de données qui est observable.
 * Elle permet de stocker une liste d'éléments de type générique E.
 *
 * @param <E> Le type des éléments contenus dans la liste.
 */
public class Data <E> extends Observable {
    private List<E> eData;


    /**
     * Constructeur qui initialise un nouvel objet Data avec les données fournies.
     *
     * @param eData La liste d'éléments de type {@code E} à stocker.
     */
    public Data(List<E> eData) {
        this.eData = eData;
    }

    public List<E> getEData() {
        return eData;
    }

    /**
     * Renvoie l'état de Data.
     *
     * @return True si c'est vide, sinon False.
     */
    public boolean isEmpty() {
        return eData.isEmpty();
    }

    /**
     * Trouve la valeur maximale parmi tous les attributs numériques d'une classe donnée.
     *
     * @param projection Le nom de la valeur cherché
     * @return La valeur maximale
     */
    public double getMaxData(String projection){
        double max = 0;
        for (E data: eData) {
            if (data instanceof Iris iris && iris.getValue(projection).doubleValue() > max){
                max = iris.getValue(projection).doubleValue();
            }
        }
        return max;
    }

    /**
     * Trouve la valeur maximale parmi tous les attributs numériques d'une classe donnée.
     *
     * @return La valeur maximale
     */
    public double getMaxData() {
        double max = findExtremum(Double::max, Double.NEGATIVE_INFINITY);
        return max > 300 ? 300 : max;
    }

    /**
     * Trouve la valeur minimale parmi tous les attributs numériques d'une classe donnée.
     *
     * @param projection Le nom de la valeur cherché
     * @return La valeur minimale
     */
    public double getMinData(String projection){
        double min = 100;
        for (E data: eData) {
            if (data instanceof Iris iris && iris.getValue(projection).doubleValue() < min){
                min = iris.getValue(projection).doubleValue();
            }
        }
        return min;
    }

    /**
     * Trouve la valeur minimale parmi tous les attributs numériques d'une classe donnée.
     *
     * @return La valeur minimale
     */
    public double getMinData() {
        double min = findExtremum(Double::min, Double.POSITIVE_INFINITY);
        return min < 0 ? 0 : min;
    }

    /**
     * Méthode générique pour trouver un extrême (minimum ou maximum) selon une opération.
     *
     * @param operator      L'opération (min ou max) à appliquer.
     * @param initialValue  La valeur initiale à comparer.
     * @return La valeur extrême trouvée, ou Double.NaN si aucune valeur n'est trouvée.
     */
    private double findExtremum(java.util.function.BinaryOperator<Double> operator, double initialValue) {
        double result = initialValue;
        boolean foundValue = false;

        for (E item : eData) {
            if (item != null) {
                for (Field field : item.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    if (Number.class.isAssignableFrom(field.getType())) {
                        try {
                            Number value = (Number) field.get(item);
                            if (value != null) {
                                result = operator.apply(result, value.doubleValue());
                                foundValue = true;
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return foundValue ? result : Double.NaN; // Retourne NaN si aucune valeur trouvée
    }
}