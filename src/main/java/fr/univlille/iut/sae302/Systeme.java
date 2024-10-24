package fr.univlille.iut.sae302;
import fr.univlille.iut.sae302.utils.Observable;
import fr.univlille.iut.sae302.utils.Observer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class Systeme extends Stage implements Observer {
    private final ScatterChart<Number, Number> chart;
    private final XYChart.Series<Number, Number> series;
    private final Data<Iris> Data;

    public Systeme(List<Iris> irisData) {
        this.Data = new Data<>(irisData);
        this.Data.attach(this);
        double x = 2.0, y = 9.0;
        NumberAxis xAxis = new NumberAxis(x, y, 1.0);
        NumberAxis yAxis = new NumberAxis(x, y, 1.0);
        xAxis.setLabel(" ");
        yAxis.setLabel(" ");
        chart = new ScatterChart<>(xAxis, yAxis);
        series = new XYChart.Series<>();
        chart.setLegendVisible(false);
        chart.getData().add(series);

        Label labelDefault = new Label("Default");
        Circle cercleDefault = new Circle();
        cercleDefault.setFill(Color.GRAY);
        cercleDefault.setRadius(7.0);
        HBox legendeDefault = new HBox(cercleDefault, labelDefault);
        legendeDefault.setSpacing(3);

        Label labelSetosa = new Label("Setosa");
        Circle cercleSetosa = new Circle();
        cercleSetosa.setFill(Color.GREEN);
        cercleSetosa.setRadius(7.0);
        HBox legendeSetosa = new HBox(cercleSetosa, labelSetosa);
        legendeSetosa.setSpacing(3);

        Label labelVersicolor = new Label("Versicolor");
        Circle cercleVersicolor = new Circle();
        cercleVersicolor.setFill(Color.RED);
        cercleVersicolor.setRadius(7.0);
        HBox legendeVersicolor = new HBox(cercleVersicolor, labelVersicolor);
        legendeVersicolor.setSpacing(3);

        Label labelVirginica = new Label("Virginica");
        Circle cercleVirginica = new Circle();
        cercleVirginica.setFill(Color.BLUE);
        cercleVirginica.setRadius(7.0);
        HBox legendeVirginica = new HBox(cercleVirginica, labelVirginica);
        legendeVirginica.setSpacing(3);

        HBox legende = new HBox(legendeDefault, legendeSetosa, legendeVersicolor, legendeVirginica);
        legende.setSpacing(20);
        legende.setAlignment(Pos.CENTER);

        VBox nuage = new VBox(chart, legende);

        ComboBox<String> projectionComboBox = new ComboBox<>();
        projectionComboBox.getItems().addAll(null, "Sepal Width", "Sepal Length", "Petal Width", "Petal Length");
        projectionComboBox.setValue(null);

        ComboBox<String> projectionComboBox2 = new ComboBox<>();
        projectionComboBox2.getItems().addAll(null, "Sepal Width", "Sepal Length", "Petal Width", "Petal Length");
        projectionComboBox2.setValue(null);

        Button buttonProjection = new Button("Projection");
        buttonProjection.setDisable(true);

        projectionComboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue.equals(projectionComboBox2.getValue())) {
                projectionComboBox2.setValue(oldValue);
            }
        });

        projectionComboBox2.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue.equals(projectionComboBox.getValue())) {
                projectionComboBox.setValue(oldValue);
            }
        });

        projectionComboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
            buttonProjection.setDisable((newValue == null || projectionComboBox2.getValue() == null) || newValue.equals(projectionComboBox2.getValue()));
        });

        projectionComboBox2.valueProperty().addListener((obs, oldValue, newValue) -> {
            buttonProjection.setDisable((newValue == null || projectionComboBox.getValue() == null) || newValue.equals(projectionComboBox.getValue()));
        });

        buttonProjection.setOnAction(e -> {
            series.getData().clear();
            String projection = projectionComboBox.getValue();
            String projection2 = projectionComboBox2.getValue();
            for (Iris iris : irisData) {
                xAxis.setLabel(projectionComboBox.getValue());
                yAxis.setLabel(projectionComboBox2.getValue());
                XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(projectionIris(projection,iris), projectionIris(projection2,iris));
                series.getData().add(dataPoint);
                dataPoint.getNode().setStyle(drawIris(iris.getVariety()) + "-fx-background-radius: 5px;");
            }
        });
        Alert a = new Alert(Alert.AlertType.NONE);
        EventHandler<ActionEvent> AlertEventInvalidNumbers = e -> {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Veuillez entrer des nombres valides.");
            a.show();
        };
        EventHandler<ActionEvent> AlertEventInvalidRange = e -> {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Veuillez entrer des valeurs entre " + x + " et " + y + ".");
            a.show();
        };

        Button buttonIris = new Button("Ajouter un iris");
        buttonIris.setOnAction(event -> {
            Stage irisStage = new Stage();
            irisStage.initModality(Modality.APPLICATION_MODAL);
            irisStage.setTitle("Ajouter un Iris");

            Label sepalLengthLabel = new Label(projectionComboBox.getValue());
            TextField sepalLengthField = new TextField();
            Label sepalWidthLabel = new Label(projectionComboBox2.getValue());
            TextField sepalWidthField = new TextField();
            if(projectionComboBox.getValue() == null) sepalLengthLabel.setText("NULL");
            if(projectionComboBox2.getValue() == null) sepalWidthLabel.setText("NULL");
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
                        System.out.println("Veuillez entrer des valeurs entre " + x + " et " + y + ".");
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

            GridPane.setMargin(sepalLengthLabel, new Insets(20, 5, 5, 20));
            GridPane.setMargin(sepalLengthField, new Insets(20, 20, 5, 5));
            GridPane.setMargin(sepalWidthLabel, new Insets(5, 5, 10, 20));
            GridPane.setMargin(sepalWidthField, new Insets(5, 20, 10, 5));
            GridPane.setMargin(varietyLabel, new Insets(10, 5, 10, 20));
            GridPane.setMargin(varietyComboBox, new Insets(10, 20, 10, 5));
            GridPane.setMargin(buttonAdd, new Insets(20, 0, 20, 20));

            Scene scene = new Scene(grid);
            irisStage.setScene(scene);
            irisStage.showAndWait();
        });

        VBox leftPane = new VBox(10);
        leftPane.setPadding(new Insets(20));
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        leftPane.getChildren().addAll(projectionComboBox2, spacer, buttonProjection, buttonIris);
        leftPane.setAlignment(Pos.TOP_LEFT);

        HBox bottomPane = new HBox();
        bottomPane.setPadding(new Insets(20));
        bottomPane.setAlignment(Pos.CENTER);
        bottomPane.getChildren().add(projectionComboBox);

        BorderPane root = new BorderPane();
        root.setLeft(leftPane);
        root.setCenter(nuage);
        root.setBottom(bottomPane);

        leftPane.setPrefWidth(150);
        buttonProjection.setMaxWidth(leftPane.getPrefWidth());
        buttonIris.setMaxWidth(leftPane.getPrefWidth());
        projectionComboBox.setMaxWidth(leftPane.getPrefWidth());
        projectionComboBox2.setMaxWidth(leftPane.getPrefWidth());
        HBox.setMargin(projectionComboBox2, new Insets(0, 0, 200, 0));

        Scene scene = new Scene(root);
        setScene(scene);
        setTitle("Application");
        this.centerOnScreen();
        show();
    }

    private String drawIris(String variety) {
        String color = switch (variety) {
            case "Versicolor" -> "-fx-background-color: red;";
            case "Virginica" -> "-fx-background-color: blue;";
            case "Setosa" -> "-fx-background-color: green;";
            default -> "-fx-background-color: gray;";
        };
        String size = "-fx-background-radius: 7px; -fx-padding: 3.75px;"; // Size
        return color + size;
    }

    private Number projectionIris(String projection, Iris iris) {
        return switch (projection){
            case "Sepal Width" -> iris.getSepalWidth();
            case "Sepal Length" -> iris.getSepalLength();
            case "Petal Width" -> iris.getPetalWidth();
            case "Petal Length" -> iris.getPetalLength();
            default -> null;
        };
    }

    @Override
    public void update(Observable observable) {

    }

    @Override
    public void update(Observable observable, Object data) {

    }
}
