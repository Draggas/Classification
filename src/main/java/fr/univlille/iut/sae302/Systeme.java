package fr.univlille.iut.sae302;
import fr.univlille.iut.sae302.utils.DistanceEuclidienneNormalisee;
import fr.univlille.iut.sae302.utils.Observable;
import fr.univlille.iut.sae302.utils.Observer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class Systeme extends Stage implements Observer {
    private final XYChart.Series<Number, Number> series;
    private Data<Iris> Data;


    public Systeme(List<Iris> irisData) {
        this.Data = new Data<>(irisData);
        this.Data.attach(this);

        MethodeKnn knn = new MethodeKnn(Data);

        Iris irisCible = irisData.getFirst();

        DistanceEuclidienneNormalisee euclidienneCalc = new DistanceEuclidienneNormalisee();

        String classePredite = MethodeKnn.classifierIris(3, irisCible, euclidienneCalc);

        Label la = new Label(MethodeKnn.calculerPourcentageReussite(3, euclidienneCalc) + "");

        NumberAxis xAxis = new NumberAxis(2.0, 9.0, 1.0);
        NumberAxis yAxis = new NumberAxis(2.0, 9.0, 1.0);
        xAxis.setLabel("sepal length");
        yAxis.setLabel("sepal width");
        ScatterChart<Number, Number> chart = new ScatterChart<>(xAxis, yAxis);
        series = new XYChart.Series<>();
        chart.setLegendVisible(false);
        chart.getData().add(series);

        ComboBox<String> projectionComboBox = new ComboBox<>();
        projectionComboBox.getItems().addAll("SepalWidth", "SepalLength", "PetalWidth", "PetalLength");
        projectionComboBox.setValue("SepalWidth");

        ComboBox<String> projectionComboBox2 = new ComboBox<>();
        projectionComboBox2.getItems().addAll("SepalWidth", "SepalLength", "PetalWidth", "PetalLength");
        projectionComboBox2.setValue("SepalLength");

        projectionComboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue.equals(projectionComboBox2.getValue())) {
                projectionComboBox2.setValue(null);
            }
        });

        projectionComboBox2.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue.equals(projectionComboBox.getValue())) {
                projectionComboBox.setValue(null);
            }
        });

        Button buttonProjection = new Button("Projection");
        buttonProjection.setOnAction(e -> {
            series.getData().clear();
            String projection = projectionComboBox.getValue();
            String projection2 = projectionComboBox2.getValue();
            for (Iris iris : irisData) {
                XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(projectionIris(projection,iris), projectionIris(projection2,iris));
                series.getData().add(dataPoint);
                dataPoint.getNode().setStyle(drawIris(iris.getVariety()));
            }
        });

        Button buttonIris = new Button("Ajouter un iris");
        buttonIris.setOnAction(event -> {
            Stage irisStage = new Stage();
            irisStage.initModality(Modality.APPLICATION_MODAL);
            irisStage.setTitle("Ajouter un Iris");

            Label sepalLengthLabel = new Label(projectionComboBox.getValue());
            TextField sepalLengthField = new TextField();
            Label sepalWidthLabel = new Label(projectionComboBox2.getValue());
            TextField sepalWidthField = new TextField();
            Label varietyLabel = new Label("Variety :");
            ComboBox<String> varietyComboBox = new ComboBox<>();
            varietyComboBox.getItems().addAll("Default", "Setosa", "Versicolor", "Virginica");
            varietyComboBox.setValue("Default");

            Button buttonAdd = new Button("Ajouter");
            buttonAdd.setOnAction(ev -> {
                try {
                    double sepalLength = Double.parseDouble(sepalLengthField.getText());
                    double sepalWidth = Double.parseDouble(sepalWidthField.getText());
                    String variety = varietyComboBox.getValue();

                    if (sepalLength >= 2.0 && sepalLength <= 9.0 && sepalWidth >= 2.0 && sepalWidth <= 9.0) {
                        XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(sepalLength, sepalWidth);
                        series.getData().add(dataPoint);

                        dataPoint.getNode().setStyle(drawIris(variety));
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
            grid.add(varietyLabel, 0, 2);
            grid.add(varietyComboBox, 1, 2);
            grid.add(buttonAdd, 0, 3);

            Scene scene = new Scene(grid);
            irisStage.setScene(scene);
            irisStage.showAndWait();
        });

        Button meilleursDistance = new Button("Meilleurs Distance");
        meilleursDistance.setOnAction(e -> {
            Stage plusProcheStage = new Stage();
            plusProcheStage.initModality(Modality.APPLICATION_MODAL);
            plusProcheStage.setTitle("Plus proche voisin");

            TextArea terminal = new TextArea();
            terminal.setEditable(false);
            
            GridPane grid = new GridPane();
            grid.add(terminal, 0, 0);

            Scene scene = new Scene(grid);
            plusProcheStage.setScene(scene);
            plusProcheStage.showAndWait();
        });

        VBox vbox = new VBox(buttonProjection, projectionComboBox, projectionComboBox2, buttonIris, meilleursDistance);
        vbox.setSpacing(20);

        HBox separationNuagePoints = new HBox(vbox, chart, la);

        VBox separationBarreNavigation = new VBox(separationNuagePoints);


        Scene scene = new Scene(separationBarreNavigation);
        setScene(scene);
        setTitle("Application");
        show();
    }

    private String drawIris(String variety) {
        String color = switch (variety) {
            case "Versicolor" -> "-fx-background-color: red;";
            case "Virginica" -> "-fx-background-color: blue;";
            case "Setosa" -> "-fx-background-color: green;";
            default -> "-fx-background-color: gray;";
        };
        return  color;
    }

    private Number projectionIris(String projection, Iris iris) {
        Number nb = switch (projection){
            case "SepalWidth" -> iris.getSepalWidth();
            case "SepalLength" -> iris.getSepalLength();
            case "PetalWidth" -> iris.getPetalWidth();
            case "PetalLength" -> iris.getPetalLength();
            default -> null;
        };
        return nb;
    }

    @Override
    public void update(Observable observable) {

    }

    @Override
    public void update(Observable observable, Object data) {

    }
}
