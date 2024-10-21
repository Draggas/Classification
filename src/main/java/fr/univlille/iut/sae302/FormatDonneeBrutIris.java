package fr.univlille.iut.sae302;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

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

    public double getPetal_width() {
        if (petal_width.charAt(0) == '.') return Double.parseDouble('0'+petal_width);
        return Double.parseDouble(petal_width);
    }

    public String getVariety() {
        return variety;
    }
}