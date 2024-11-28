package fr.univlille.iut.sae302.utils;

/**
 * Interface représentant une fonction de distance entre deux objets de type T.
 *
 * @param <T> Le type des objets entre lesquels calculer la distance.
 */
public interface Distance<T> {

    /**
     * Calcule la distance entre deux objets de type T.
     *
     * @param t1 Le premier objet.
     * @param t2 Le deuxième objet.
     * @return La distance calculée entre t1 et t2.
     */
    public double distance(T t1, T t2);
}
