package fr.univlille.iut.sae302;

import java.time.LocalDate;

public class Iris {
    private static final String NL = System.getProperty("line.separator");
    private double sepal_length;
    private double sepal_width;
    private double petal_length;
    private double petal_width;
    private String variety;

    public Iris(double sepal_length, double sepal_width, double petal_length, double petal_width, String variety) {
        this.sepal_length = sepal_length;
        this.sepal_width = sepal_width;
        this.petal_length = petal_length;
        this.petal_width = petal_width;
        this.variety = variety;
    }
    public String toString(){
        return  "Sepal.length : " + sepal_length + NL +
                "Sepal.width : " + sepal_width + NL +
                "Petal.length : " + petal_length + NL +
                "Petal.width : " + petal_width + NL +
                "Variety : " + variety;
    }

}
