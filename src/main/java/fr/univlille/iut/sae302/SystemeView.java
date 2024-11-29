package fr.univlille.iut.sae302;

import fr.univlille.iut.sae302.utils.*;
import fr.univlille.iut.sae302.utils.Observable;
import fr.univlille.iut.sae302.utils.Observer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.paint.Color; 
import javafx.scene.shape.Circle; 
import javafx.stage.FileChooser; 
import javafx.stage.Modality; 
import javafx.stage.Screen; 
import java.io.File;
import java.io.IOException;
import java.util.*;

public class SystemeView extends Stage {
    private ScatterChart<Number, Number> chart;
    private XYChart.Series<Number, Number> series;
    private ComboBox<String> projectionComboBox;
    private ComboBox<String> projectionComboBox2;
    private TabPane tabPane;
    private double x = 0.0;
    private double y = 9.0;
    NumberAxis xAxis;
    NumberAxis yAxis;
    Label xAxisLabel;
    Label yAxisLabel;
    TextField xAxisMinField;
    TextField xAxisMaxField;
    TextField yAxisMinField;
    TextField yAxisMaxField;
    Button updateXAxisButton;
    Button updateYAxisButton;
    Button buttonProjection;
    Button buttonAddValue;
    Button openFileButton;
    VBox nuage;

    public SystemeView() {
        initializeChart();
        initializeUIComponents();
    }

    public void showHelpUnavailable() {
        Stage helpStage = new Stage();
        helpStage.initModality(Modality.APPLICATION_MODAL);
        helpStage.setTitle("Aide");

        Label helpLabel = new Label("Pas d'aide disponible");
        helpLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        helpLabel.setPadding(new Insets(20));

        VBox helpLayout = new VBox(20, helpLabel);
        helpLayout.setAlignment(Pos.CENTER);

        Scene helpScene = new Scene(helpLayout, 300, 200);
        helpStage.setScene(helpScene);
        helpStage.showAndWait();
    }

    private void initializeChart() {
        xAxis = new NumberAxis(x, y, 1.0);
        yAxis = new NumberAxis(x, y, 1.0);
        xAxis.setLabel("X Axis");
        yAxis.setLabel("Y Axis");
        chart = new ScatterChart<>(xAxis, yAxis);
        series = new XYChart.Series<>();
        chart.setLegendVisible(false);
        chart.getData().add(series);

        tabPane = new TabPane();
        Tab initialTab = new Tab("Accueil");
        initialTab.setContent(chart);
        initialTab.setClosable(false);
        tabPane.getTabs().add(initialTab);
    }

    private void initializeUIComponents() {
        xAxisLabel = new Label("L'axe X :");
        xAxisMinField = new TextField(String.valueOf(x));
        xAxisMaxField = new TextField(String.valueOf(y));
        updateXAxisButton = new Button("Mettre à jour l'axe X");

        yAxisLabel = new Label("L'axe Y :");
        yAxisMinField = new TextField(String.valueOf(x));
        yAxisMaxField = new TextField(String.valueOf(y));
        updateYAxisButton = new Button("Mettre à jour l'axe Y");

        projectionComboBox = new ComboBox<>();
        projectionComboBox.setValue(null);

        projectionComboBox2 = new ComboBox<>();
        projectionComboBox2.setValue(null);

        projectionComboBox.setDisable(true);
        projectionComboBox2.setDisable(true);

        buttonProjection = new Button("Projection");
        buttonAddValue = new Button("Ajouter");
        buttonProjection.setDisable(true);
        buttonAddValue.setDisable(true);

        HBox legende = new HBox();
        legende.setAlignment(Pos.CENTER);

        nuage = new VBox(tabPane, legende);

        configureUpdateAxisButtons();
    }

    public void resetUI() {
        projectionComboBox.getItems().clear();
        projectionComboBox2.getItems().clear();
        projectionComboBox.setDisable(true);
        projectionComboBox2.setDisable(true);
        buttonProjection.setDisable(true);
        buttonAddValue.setDisable(true);
    }

    public void configureProjectionsAndLegend() {
        projectionComboBox.setDisable(false);
        projectionComboBox2.setDisable(false);
        NumberAxis xAxis = (NumberAxis) chart.getXAxis();
        NumberAxis yAxis = (NumberAxis) chart.getYAxis();
        xAxis.setLowerBound((int) (data.getMinData() < 1 ? data.getMinData() : data.getMinData() - 1));
        xAxis.setUpperBound((int) data.getMaxData() + 1);
        yAxis.setLowerBound((int) (data.getMinData() < 1 ? data.getMinData() : data.getMinData() - 1));
        yAxis.setUpperBound((int) data.getMaxData() + 1);
        data.attach(this);
    }

    public String drawIris(String variety) {
        String color = switch (variety) {
            case "Versicolor" -> "-fx-background-color: red;";
            case "Virginica" -> "-fx-background-color: blue;";
            case "Setosa" -> "-fx-background-color: green;";
            default -> "-fx-background-color: gray;";
        };
        String size = "-fx-background-radius: 7px; -fx-padding: 3.75px;";
        return color + size;
    }

    public String drawPokemon(Type type1) {
        String color = switch (type1.getName().toLowerCase()) {
            case "fire" -> "-fx-background-color: orange;";
            case "water" -> "-fx-background-color: blue;";
            case "grass" -> "-fx-background-color: green;";
            case "electric" -> "-fx-background-color: yellow;";
            default -> "-fx-background-color: gray;";
        };
        String size = "-fx-background-radius: 7px; -fx-padding: 3.75px;";
        return color + size;
    }

    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void addTooltipToPoint(XYChart.Data<Number, Number> dataPoint, String tooltipText) {
        Tooltip tooltip = new Tooltip(tooltipText);
        Tooltip.install(dataPoint.getNode(), tooltip);
        dataPoint.getNode().setOnMouseEntered(event -> dataPoint.getNode().setStyle(
                dataPoint.getNode().getStyle() + " -fx-scale-x: 1.5; -fx-scale-y: 1.5;"));
        dataPoint.getNode().setOnMouseExited(event -> dataPoint.getNode().setStyle(
                dataPoint.getNode().getStyle() + " -fx-scale-x: 1; -fx-scale-y: 1;"));
        dataPoint.getNode().setOnMouseClicked(event -> {
            String details = String.format("X: %.2f\t Y: %.2f\nDetails: %s",
                    dataPoint.getXValue().doubleValue(),
                    dataPoint.getYValue().doubleValue(),
                    tooltipText
            );
            showAlert("Details", details);
        });
    }

    public HBox createIrisLegend() {
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

        HBox legendeIris = new HBox(legendeDefault, legendeSetosa, legendeVersicolor, legendeVirginica);
        legendeIris.setSpacing(20);
        legendeIris.setAlignment(Pos.CENTER);

        return legendeIris;
    }

    public HBox createPokemonLegend() {
        Label labelFire = new Label("Fire");
        Circle cercleFire = new Circle();
        cercleFire.setFill(Color.ORANGE);
        cercleFire.setRadius(7.0);
        HBox legendeFire = new HBox(cercleFire, labelFire);
        legendeFire.setSpacing(3);

        Label labelWater = new Label("Water");
        Circle cercleWater = new Circle();
        cercleWater.setFill(Color.BLUE);
        cercleWater.setRadius(7.0);
        HBox legendeWater = new HBox(cercleWater, labelWater);
        legendeWater.setSpacing(3);

        Label labelGrass = new Label("Grass");
        Circle cercleGrass = new Circle();
        cercleGrass.setFill(Color.GREEN);
        cercleGrass.setRadius(7.0);
        HBox legendeGrass = new HBox(cercleGrass, labelGrass);
        legendeGrass.setSpacing(3);

        Label labelElectric = new Label("Electric");
        Circle cercleElectric = new Circle();
        cercleElectric.setFill(Color.YELLOW);
        cercleElectric.setRadius(7.0);
        HBox legendeElectric = new HBox(cercleElectric, labelElectric);
        legendeElectric.setSpacing(3);

        Label labelDefaultPokemon = new Label("Default");
        Circle cercleDefaultPokemon = new Circle();
        cercleDefaultPokemon.setFill(Color.GRAY);
        cercleDefaultPokemon.setRadius(7.0);
        HBox legendeDefaultPokemon = new HBox(cercleDefaultPokemon, labelDefaultPokemon);
        legendeDefaultPokemon.setSpacing(3);

        HBox legendePokemon = new HBox(legendeFire, legendeWater, legendeGrass, legendeElectric, legendeDefaultPokemon);
        legendePokemon.setSpacing(20);
        legendePokemon.setAlignment(Pos.CENTER);

        return legendePokemon;
    }

    public void updateLegend(HBox container, boolean isPokemon) {
        container.getChildren().clear();
        if (isPokemon) {
            container.getChildren().add(createPokemonLegend());
        } else {
            container.getChildren().add(createIrisLegend());
        }
    }

    public ScatterChart<Number, Number> getChart() {
        return chart;
    }

    public XYChart.Series<Number, Number> getSeries() {
        return series;
    }

    public ComboBox<String> getProjectionComboBox() {
        return projectionComboBox;
    }

    public ComboBox<String> getProjectionComboBox2() {
        return projectionComboBox2;
    }

    public TabPane getTabPane() {
        return tabPane;
    }

    public NumberAxis getxAxis() {
        return xAxis;
    }

    public NumberAxis getyAxis() {
        return yAxis;
    }

    public Label getxAxisLabel() {
        return xAxisLabel;
    }

    public Label getyAxisLabel() {
        return yAxisLabel;
    }

    public TextField getxAxisMinField() {
        return xAxisMinField;
    }

    public TextField getxAxisMaxField() {
        return xAxisMaxField;
    }

    public TextField getyAxisMinField() {
        return yAxisMinField;
    }

    public TextField getyAxisMaxField() {
        return yAxisMaxField;
    }

    public Button getUpdateXAxisButton() {
        return updateXAxisButton;
    }

    public Button getUpdateYAxisButton() {
        return updateYAxisButton;
    }

    public Button getButtonProjection() {
        return buttonProjection;
    }

    public Button getButtonAddValue() {
        return buttonAddValue;
    }

    public Button getOpenFileButton() {
        return openFileButton;
    }

    public VBox getNuage() {
        return nuage;
    }

}