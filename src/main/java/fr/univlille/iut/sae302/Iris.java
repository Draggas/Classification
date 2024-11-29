package fr.univlille.iut.sae302;

/**
 * Cette classe représente une fleur Iris avec ses attributs de caractéristiques
 * (longueur et largeur des sépales et pétales) ainsi que la variété de l'Iris.
 */
public class Iris {
    private static final String NL = System.lineSeparator();
    private Number sepalLength;
    private Number sepalWidth;
    private Number petalLength;
    private Number petalWidth;
    private String variety;


    /**
     * Constructeur pour créer une instance de la classe Iris.
     *
     * @param sepalLength Longueur du sépale.
     * @param sepalWidth Largeur du sépale.
     * @param petalLength Longueur du pétale.
     * @param petalWidth Largeur du pétale.
     * @param variety Variété de l'Iris.
     */
    public Iris(Number sepalLength, Number sepalWidth, Number petalLength, Number petalWidth, String variety) {
        this.sepalLength = sepalLength;
        this.sepalWidth = sepalWidth;
        this.petalLength = petalLength;
        this.petalWidth = petalWidth;
        this.variety = variety;
    }

    /**
     * Retourne une représentation sous forme de chaîne des caractéristiques de l'Iris.
     *
     * @return Une chaîne contenant les caractéristiques de l'Iris.
     */
    @Override
    public String toString(){
        return  "Sepal.length : " + sepalLength + NL +
                "Sepal.width : " + sepalWidth + NL +
                "Petal.length : " + petalLength + NL +
                "Petal.width : " + petalWidth + NL +
                "Variety : " + variety;
    }

    public Number getPetalLength() {
        return petalLength;
    }

    public Number getPetalWidth() {
        return petalWidth;
    }

    public Number getSepalLength() {
        return sepalLength;
    }

    public Number getSepalWidth() {
        return sepalWidth;
    }

    public String getVariety() {
        return variety;
    }

    /**
     * Récupere la valeur demandé par le parametre
     *
     * @param donnee Le nom du de la donnée demandé
     */
    public Number getValue(String donnee){
        if(donnee.equals("Sepal Width")) return getSepalWidth();
        if(donnee.equals("Sepal Length")) return getSepalLength();
        if(donnee.equals("Petal Width")) return getPetalWidth();
        if(donnee.equals("Petal Length")) return getPetalLength();
        return 0;
    }

    public void setPetalLength(Number petalLength) {
        this.petalLength = petalLength;
    }

    public void setPetalWidth(Number petalWidth) {
        this.petalWidth = petalWidth;
    }

    public void setSepalLength(Number sepalLength) {
        this.sepalLength = sepalLength;
    }

    public void setSepalWidth(Number sepalWidth) {
        this.sepalWidth = sepalWidth;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }
}
