package fr.univlille.iut.sae302;

public class Iris {
    private static final String NL = System.getProperty("line.separator");
    private Number sepalLength;
    private Number sepalWidth;
    private Number petalLength;
    private Number petalWidth;
    private final String variety;

    public Iris(Number sepalLength, Number sepalWidth, Number petalLength, Number petalWidth, String variety) {
        this.sepalLength = sepalLength;
        this.sepalWidth = sepalWidth;
        this.petalLength = petalLength;
        this.petalWidth = petalWidth;
        this.variety = variety;
    }

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

}
