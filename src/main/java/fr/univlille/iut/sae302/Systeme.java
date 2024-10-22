package fr.univlille.iut.sae302;
import fr.univlille.iut.sae302.utils.Observable;
import fr.univlille.iut.sae302.utils.Observer;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class Systeme extends Stage implements Observer {
    private ScatterChart<Number, Number> chart;
    private XYChart.Series<Number, Number> series;
    private Data<Iris> Data;


    public Systeme(List<Iris> irisData) {
        this.Data = new Data<>(irisData);
        this.Data.attach(this);

        NumberAxis xAxis = new NumberAxis(2.0, 9.0, 1.0);
        NumberAxis yAxis = new NumberAxis(2.0, 9.0, 1.0);
        xAxis.setLabel("sepal length");
        yAxis.setLabel("sepal width");
        chart = new ScatterChart<>(xAxis, yAxis);
        series = new XYChart.Series<>();
        chart.setLegendVisible(false);
        chart.getData().add(series);

        for (Iris iris : irisData) {
            series.getData().add(new XYChart.Data<>(iris.getSepalLength(), iris.getSepalWidth()));
        }

        HBox separationNuagePoints = new HBox(chart);

        VBox separationBarreNavigation = new VBox(separationNuagePoints);



        Scene scene = new Scene(separationBarreNavigation, 800, 600);
        setScene(scene);
        setTitle("Application");
        show();
    }

    @Override
    public void update(Observable observable) {

    }

    @Override
    public void update(Observable observable, Object data) {

    }
}
