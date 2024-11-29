package fr.univlille.iut.sae302;

import com.opencsv.bean.CsvBindByName;

/**
 * Cette classe représente les données brutes d'une fleur Iris
 * chargées depuis un fichier CSV. Elle utilise la bibliothèque OpenCSV 
 * pour le mapping des colonnes du fichier CSV vers les attributs de la classe.
 */
public class FormatDonneeBrutIris {

    @CsvBindByName(column = "sepal.length")
    private double sepal_length;

    @CsvBindByName(column = "sepal.width")
    private double sepal_width;

    @CsvBindByName(column = "petal.length")
    private double petal_length;

    @CsvBindByName(column = "petal.width")
    private String petal_width;

    @CsvBindByName(column = "variety")
    private String variety;

    /**
     * Retourne une représentation sous forme de chaîne des données de l'Iris.
     *
     * @return Une chaîne contenant les attributs de l'Iris séparés par des virgules comme les lignes du CSV.
     */
    @Override
    public String toString() {
        return "" + sepal_length + ',' + sepal_width + ',' + petal_length + ',' + petal_width + ',' + variety;
    }

    public double getSepal_length() {
        return sepal_length;
    }

    public double getSepal_width() {
        return sepal_width;
    }

    public double getPetal_length() {
        return petal_length;
    }

    /**
     * Obtient la largeur du pétale.
     * Si la largeur commence par un point dans son format, elle est préfixée par un zéro pour éviter les bugs avant d'être convertie en double.
     *
     * @return La largeur du pétale sous forme de double.
     */
    public double getPetal_width() {
        if (petal_width.charAt(0) == '.') return Double.parseDouble('0'+petal_width);
        return Double.parseDouble(petal_width);
    }

    public String getVariety() {
        return variety;
    }
}