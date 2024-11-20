package fr.univlille.iut.sae302;

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

    /**
     * Renvoie la liste des données stockées.
     * 
     * @return Une liste d'éléments de type {@code E}.
     */
    public List<E> getEData() {
        return eData;
    }

    public boolean isEmpty() {
        return eData.isEmpty();
    }

    public double getMaxData(String projection){
        double max = 0;
        for (E data: eData) {
            if (data instanceof Iris iris && iris.getValue(projection).doubleValue() > max){
                max = iris.getValue(projection).doubleValue();
            }
        }
        return max;
    }

    public double getMinData(String projection){
        double min = 100;
        for (E data: eData) {
            if (data instanceof Iris iris && iris.getValue(projection).doubleValue() < min){
                min = iris.getValue(projection).doubleValue();
            }
        }
        return min;
    }

}
