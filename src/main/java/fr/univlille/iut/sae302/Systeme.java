package fr.univlille.iut.sae302;
import fr.univlille.iut.sae302.utils.Observable;
import fr.univlille.iut.sae302.utils.Observer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
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
            XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(iris.getSepalWidth(), iris.getSepalLength());
            series.getData().add(dataPoint);

            if (iris.getVariety().equals("Versicolor")) {
                dataPoint.getNode().setStyle("-fx-background-color: red;");
            } else if (iris.getVariety().equals("Virginica")) {
                dataPoint.getNode().setStyle("-fx-background-color: blue;");
            } else {
                dataPoint.getNode().setStyle("-fx-background-color: green;");
            }
        }


        Button buttonIris = new Button("Ajouter un iris");
        buttonIris.setOnAction(event -> {
            Stage irisStage = new Stage();
            irisStage.initModality(Modality.APPLICATION_MODAL);
            irisStage.setTitle("Ajouter un Iris");

            Label sepalLengthLabel = new Label("Sepal Length:");
            TextField sepalLengthField = new TextField();
            Label sepalWidthLabel = new Label("Sepal Width:");
            TextField sepalWidthField = new TextField();

            Button buttonAdd = new Button("Ajouter");
            buttonAdd.setOnAction(ev -> {
                try {
                    double sepalLength = Double.parseDouble(sepalLengthField.getText());
                    double sepalWidth = Double.parseDouble(sepalWidthField.getText());

                    if (sepalLength >= 2.0 && sepalLength <= 9.0 && sepalWidth >= 2.0 && sepalWidth <= 9.0) {
                        XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(sepalLength, sepalWidth);
                        series.getData().add(dataPoint);

                        dataPoint.getNode().setStyle("-fx-background-color: grey;");
                        irisStage.close();
                    } else {
                        System.out.println("Veuillez entrer des valeurs entre 2.0 et 9.0.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Veuillez entrer des nombres valides.");
                }
            });

            GridPane grid = new GridPane();
            grid.add(sepalLengthLabel, 0, 0);
            grid.add(sepalLengthField, 1, 0);
            grid.add(sepalWidthLabel, 0, 1);
            grid.add(sepalWidthField, 1, 1);
            grid.add(buttonAdd, 1, 2);

            Scene scene = new Scene(grid);
            irisStage.setScene(scene);
            irisStage.showAndWait();
        });
        HBox separationNuagePoints = new HBox(chart);

        VBox separationBarreNavigation = new VBox(buttonIris, separationNuagePoints);


        Scene scene = new Scene(separationBarreNavigation);
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
