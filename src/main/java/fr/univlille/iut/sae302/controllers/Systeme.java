package fr.univlille.iut.sae302.controllers;
import fr.univlille.iut.sae302.model.Iris;
import fr.univlille.iut.sae302.model.MethodeKnn;
import fr.univlille.iut.sae302.utils.DistanceEuclidienneNormalisee;
import fr.univlille.iut.sae302.utils.Observable;
import fr.univlille.iut.sae302.utils.Observer;
import fr.univlille.iut.sae302.view.Data;
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
import java.util.Objects;

/**
 * La classe Systeme représente une fenêtre de l'application qui affiche
 * un graphique de dispersion pour visualiser les données des iris.
 * Elle permet d'ajouter des iris à la visualisation et d'effectuer des projections 
 * sur différentes caractéristiques.
 */
public class Systeme extends Stage implements Observer {
    private final ScatterChart<Number, Number> chart;
    private final XYChart.Series<Number, Number> series;
    private Data<Iris> Data;
    private final ComboBox<String> projectionComboBox;
    private final ComboBox<String> projectionComboBox2;
    private TabPane tabPane;

    private final double x = 0.0;
    private final double y = 9.0;

    private boolean isProjectionInProgress = false;

    /**
     * Constructeur de la classe Systeme.
     *
     * @param irisData La liste des données d'iris à visualiser.
     */
    public Systeme(List<Iris> irisData) {
        this.Data = new Data<>(irisData);
        this.Data.attach(this);
        NumberAxis xAxis = new NumberAxis(x, y, 1.0);
        NumberAxis yAxis = new NumberAxis(x, y, 1.0);
        xAxis.setLabel(" ");
        yAxis.setLabel(" ");
        chart = new ScatterChart<>(xAxis, yAxis);
        series = new XYChart.Series<>();
        chart.setLegendVisible(false);
        chart.getData().add(series);

        tabPane = new TabPane();
        Tab initialTab = new Tab("Accueil");
        initialTab.setContent(chart);
        tabPane.getTabs().add(initialTab);

        Label xAxisLabel = new Label("L'axe X :");
        TextField xAxisMinField = new TextField(String.valueOf(x));
        TextField xAxisMaxField = new TextField(String.valueOf(y));
        Button updateXAxisButton = new Button("Mettre à jour l'axe X");

        Label yAxisLabel = new Label("L'axe Y :");
        TextField yAxisMinField = new TextField(String.valueOf(x));
        TextField yAxisMaxField = new TextField(String.valueOf(y));
        Button updateYAxisButton = new Button("Mettre à jour l'axe Y");

        updateXAxisButton.setOnAction(e -> {
            try {
                double newMin = Double.parseDouble(xAxisMinField.getText());
                double newMax = Double.parseDouble(xAxisMaxField.getText());
                if (newMin < newMax) {
                    xAxis.setLowerBound(newMin);
                    xAxis.setUpperBound(newMax);
                } else {
                    showAlert("Min supérieur à Max", "Minimum ne peut pas être supéreur à max pour l'axe X.");
                }
            } catch (NumberFormatException ex) {
                showAlert("Entrée non valide", "Entrer un nombre valide pour l'axe X.");
            }
        });

        updateYAxisButton.setOnAction(e -> {
            try {
                double newMin = Double.parseDouble(yAxisMinField.getText());
                double newMax = Double.parseDouble(yAxisMaxField.getText());
                if (newMin < newMax) {
                    yAxis.setLowerBound(newMin);
                    yAxis.setUpperBound(newMax);
                } else {
                    showAlert("Min supérieur à Max", "Minimum ne peut pas être supéreur à max pour l'axe Y.");
                }
            } catch (NumberFormatException ex) {
                showAlert("Entrée non valide", "Entrer un nombre valide pour l'axe Y.");
            }
        });


        DistanceEuclidienneNormalisee euclidienneCalc = new DistanceEuclidienneNormalisee();

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

        VBox nuage = new VBox(tabPane, legende);

        projectionComboBox = new ComboBox<>();
        projectionComboBox.getItems().addAll("Sepal Width", "Sepal Length", "Petal Width", "Petal Length");
        projectionComboBox.setValue(null);

        projectionComboBox2 = new ComboBox<>();
        projectionComboBox2.getItems().addAll("Sepal Width", "Sepal Length", "Petal Width", "Petal Length");
        projectionComboBox2.setValue(null);

        Button buttonProjection = new Button("Projection");
        Button buttonIris = new Button("Ajouter un iris");
        Button buttonMeilleurDistance = new Button("Meilleurs Distance");
        buttonProjection.setDisable(true);
        buttonIris.setDisable(true);
        buttonMeilleurDistance.setDisable(true);

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
            if(!(newValue == null || projectionComboBox2.getValue() == null) || Objects.equals(newValue, projectionComboBox2.getValue())) {
                buttonProjection.setDisable(false);
                buttonIris.setDisable(false);
                buttonMeilleurDistance.setDisable(false);
            }
        });

        projectionComboBox2.valueProperty().addListener((obs, oldValue, newValue) -> {
            if(!(newValue == null || projectionComboBox.getValue() == null) || Objects.equals(newValue, projectionComboBox.getValue())){
                buttonProjection.setDisable(false);
                buttonIris.setDisable(false);
                buttonMeilleurDistance.setDisable(false);
            }
        });

        buttonProjection.setOnAction(e -> {
            if (isProjectionInProgress) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Projection déjà effectuée");
                alert.setHeaderText("Une projection est déjà en cours.");
                alert.setContentText("Voulez-vous écraser la projection actuelle ou ouvrir un nouvel onglet ?");

                ButtonType overwriteButton = new ButtonType("Écraser");
                ButtonType newTabButton = new ButtonType("Nouvel onglet");
                ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

                alert.getButtonTypes().setAll(overwriteButton, newTabButton, cancelButton);

                alert.showAndWait().ifPresent(response -> {
                    if (response == overwriteButton) {
                        series.getData().clear();
                        newPerformProjection(series,chart);
                    } else if (response == newTabButton) {
                        openNewProjectionTab(tabPane);
                    }
                });
            } else {
                series.getData().clear();
                newPerformProjection(series,chart);
                isProjectionInProgress = true;
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

        buttonIris.setOnAction(event -> {
            Stage irisStage = new Stage();
            irisStage.initModality(Modality.APPLICATION_MODAL);
            irisStage.setTitle("Ajouter un Iris");

            Label xInputLabel = new Label(projectionComboBox.getValue());
            TextField xInput = new TextField();
            Label yInputLabel = new Label(projectionComboBox2.getValue());
            TextField yInput = new TextField();
            if(projectionComboBox.getValue() == null) xInputLabel.setText("INDEFINI :");
            if(projectionComboBox2.getValue() == null) yInputLabel.setText("INDEFINI :");
            Label varietyLabel = new Label("Variety :");
            ComboBox<String> varietyComboBox = new ComboBox<>();
            varietyComboBox.getItems().addAll("Defaut", "Setosa", "Versicolor", "Virginica");
            varietyComboBox.setValue("Defaut");

            Button buttonAdd = new Button("Ajouter");

            buttonAdd.setOnAction(ev -> {
                try {
                    double xNumber = Double.parseDouble(xInput.getText());
                    double yNumber = Double.parseDouble(yInput.getText());
                    String variety = varietyComboBox.getValue();

                    MethodeKnn knn = new MethodeKnn(new Data<>(this.Data.getEData()));

                    if (xNumber >= x && xNumber <= y && yNumber >= x && yNumber <= y) {
                        XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(xNumber, yNumber);
                        Iris tmp = new Iris(0, 0, 0, 0, variety);

                        if (projectionComboBox.getValue().equals("Sepal Width")) tmp.setSepalWidth(xNumber);
                        if (projectionComboBox2.getValue().equals("Sepal Width")) tmp.setSepalWidth(yNumber);

                        if (projectionComboBox.getValue().equals("Sepal Length")) tmp.setSepalLength(xNumber);
                        if (projectionComboBox2.getValue().equals("Sepal Length")) tmp.setSepalLength(yNumber);

                        if (projectionComboBox.getValue().equals("Petal Width")) tmp.setPetalWidth(xNumber);
                        if (projectionComboBox2.getValue().equals("Petal Width")) tmp.setPetalWidth(yNumber);

                        if (projectionComboBox.getValue().equals("Petal Length")) tmp.setPetalLength(xNumber);
                        if (projectionComboBox2.getValue().equals("Petal Length")) tmp.setPetalLength(yNumber);

                        if (tmp.getVariety().equals("Defaut")) {
                            tmp.setVariety(knn.classifierIris(knn.trouverMeilleurK(euclidienneCalc), tmp, euclidienneCalc));
                        }

                        irisData.add(tmp);
                        this.Data = new Data<>(irisData);
                        this.Data.attach(this);

                        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
                        if (selectedTab != null) {
                            XYChart<Number, Number> selectedChart = (XYChart<Number, Number>) selectedTab.getContent();
                            if (selectedChart != null) {
                                XYChart.Series<Number, Number> chartSeries = new XYChart.Series<>();
                                chartSeries.setName("Iris Data");
                                chartSeries.getData().add(dataPoint);
                                selectedChart.getData().add(chartSeries);
                                dataPoint.getNode().setStyle(drawIris(tmp.getVariety()));
                                String tooltipText = String.format(
                                        "X: %.2f\t Y: %.2f\t Variety: %s",
                                        ((Number) Objects.requireNonNull(projectionIris(projectionComboBox.getValue(), tmp))).doubleValue(),
                                        ((Number) Objects.requireNonNull(projectionIris(projectionComboBox2.getValue(), tmp))).doubleValue(),
                                        tmp.getVariety()
                                );
                                addTooltipToPoint(dataPoint, tooltipText);
                                irisStage.close();
                            }
                        }
                    } else {
                        AlertEventInvalidRange.handle(new ActionEvent());
                    }
                } catch (NumberFormatException e) {
                    AlertEventInvalidNumbers.handle(new ActionEvent());
                }
            });

            GridPane grid = new GridPane();
            grid.add(xInputLabel, 0, 0);
            grid.add(xInput, 1, 0);
            grid.add(yInputLabel, 0, 1);
            grid.add(yInput, 1, 1);
            grid.add(varietyLabel, 0, 2);
            grid.add(varietyComboBox, 1, 2);
            grid.add(buttonAdd, 0, 3);

            GridPane.setMargin(xInputLabel, new Insets(20, 5, 5, 20));
            GridPane.setMargin(xInput, new Insets(20, 20, 5, 5));
            GridPane.setMargin(yInputLabel, new Insets(5, 5, 10, 20));
            GridPane.setMargin(yInput, new Insets(5, 20, 10, 5));
            GridPane.setMargin(varietyLabel, new Insets(10, 5, 10, 20));
            GridPane.setMargin(varietyComboBox, new Insets(10, 20, 10, 5));
            GridPane.setMargin(buttonAdd, new Insets(20, 0, 20, 20));

            Scene scene = new Scene(grid);
            irisStage.setScene(scene);
            irisStage.showAndWait();
        });

        buttonMeilleurDistance.setOnAction(e -> {
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

        VBox leftPane = new VBox(10);
        leftPane.setPadding(new Insets(20));
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        leftPane.getChildren().addAll(projectionComboBox2, spacer, buttonProjection, buttonIris, buttonMeilleurDistance,                xAxisLabel, xAxisMinField, xAxisMaxField, updateXAxisButton,
                                        yAxisLabel, yAxisMinField, yAxisMaxField, updateYAxisButton);
        leftPane.setAlignment(Pos.TOP_LEFT);

        HBox bottomPane = new HBox();
        bottomPane.setPadding(new Insets(20));
        bottomPane.setAlignment(Pos.CENTER);
        bottomPane.getChildren().addAll(projectionComboBox);

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

    /**
     * Définit la valeur de l'iris selon l'axe de la projection.
     *
     * @param iris Iris qui sera modifié
     * @param projection Projection (Sepal Length, Sepal Width, Petal Length, Petal Width) sur laquelle mettre la valeur
     * @param value La valeur à mettre dans l'Iris
     */
    private void setProjectionValue(Iris iris, String projection, double value) {
        switch (projection) {
            case "Sepal Width":
                iris.setSepalWidth(value);
                break;
            case "Petal Width":
                iris.setPetalWidth(value);
                break;
            case "Sepal Length":
                iris.setSepalLength(value);
                break;
            case "Petal Length":
                iris.setPetalLength(value);
                break;
            default:
                throw new IllegalArgumentException("Invalid projection: " + projection);
        }
    }

    /**
     * Définit la couleur et la taille d'un point de données Iris en fonction de sa variété.
     *
     * @param variety La variété de l'iris (Setosa, Versicolor, Virginica ou autre).
     * @return Une chaîne de caractères représentant les styles CSS pour le point de données.
     */
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

    /**
     * Projette les valeurs d'un iris en fonction d'une caractéristique sélectionnée.
     *
     * @param projection La caractéristique à projeter (largeur ou longueur des sépales ou pétales).
     * @param iris L'objet Iris dont on souhaite obtenir la valeur pour la projection.
     * @return La valeur de la caractéristique sélectionnée pour l'iris, ou null si la projection est invalide.
     */
    private Number projectionIris(String projection, Iris iris) {
        return switch (projection){
            case "Sepal Width" -> iris.getSepalWidth();
            case "Sepal Length" -> iris.getSepalLength();
            case "Petal Width" -> iris.getPetalWidth();
            case "Petal Length" -> iris.getPetalLength();
            default -> null;
        };
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void addTooltipToPoint(XYChart.Data<Number, Number> dataPoint, String tooltipText) {
        Tooltip tooltip = new Tooltip(tooltipText);
        Tooltip.install(dataPoint.getNode(), tooltip);
        dataPoint.getNode().setOnMouseEntered(event -> dataPoint.getNode().setStyle(
                dataPoint.getNode().getStyle() + " -fx-scale-x: 1.5; -fx-scale-y: 1.5;"));
        dataPoint.getNode().setOnMouseExited(event -> dataPoint.getNode().setStyle(
                dataPoint.getNode().getStyle() + " -fx-scale-x: 1; -fx-scale-y: 1;"));
    }

    private void newPerformProjection(XYChart.Series<Number, Number> newSeries, ScatterChart<Number, Number> newChart) {
        String projection = projectionComboBox.getValue();
        String projection2 = projectionComboBox2.getValue();

        for (Iris iris : this.Data.getEData()) {
            newChart.getXAxis().setLabel(projectionComboBox.getValue());
            newChart.getYAxis().setLabel(projectionComboBox2.getValue());

            XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(
                    projectionIris(projection, iris),
                    projectionIris(projection2, iris)
            );

            newSeries.getData().add(dataPoint);
            dataPoint.getNode().setStyle(drawIris(iris.getVariety()));
            String tooltipText = String.format(
                    "X: %.2f\t Y: %.2f\t Variety: %s",
                    ((Number) Objects.requireNonNull(projectionIris(projection, iris))).doubleValue(),
                    ((Number) Objects.requireNonNull(projectionIris(projection2, iris))).doubleValue(),
                    iris.getVariety()
            );
            addTooltipToPoint(dataPoint, tooltipText);

            dataPoint.getNode().setOnMouseClicked(event -> {
                Tab newTab = new Tab("Iris Details");
                VBox content = new VBox();
                content.getChildren().addAll(
                        new Label("X: " + dataPoint.getXValue()),
                        new Label("Y: " + dataPoint.getYValue()),
                        new Label("Variety: " + iris.getVariety()),
                        new Label("Sepal Width: " + iris.getSepalWidth()),
                        new Label("Sepal Length: " + iris.getSepalLength()),
                        new Label("Petal Width: " + iris.getPetalWidth()),
                        new Label("Petal Length: " + iris.getPetalLength())
                );
                newTab.setContent(content);
                tabPane.getTabs().add(newTab);
                tabPane.getSelectionModel().select(newTab);
            });
        }
    }

    private void openNewProjectionTab(TabPane tabPane) {
        NumberAxis xAxis = new NumberAxis(x, y, 1.0);
        NumberAxis yAxis = new NumberAxis(x, y, 1.0);
        Tab newTab = new Tab(projectionComboBox.getValue() + "/"+projectionComboBox2.getValue());
        ScatterChart<Number, Number> newChart = new ScatterChart<>(xAxis, yAxis);
        XYChart.Series<Number, Number> newSeries = new XYChart.Series<>();
        newChart.setLegendVisible(false);
        newChart.getData().add(newSeries);
        newPerformProjection(newSeries, newChart);
        newTab.setContent(newChart);
        tabPane.getTabs().add(newTab);
        tabPane.getSelectionModel().select(newTab);
    }

    private void addIrisTab(Iris selectedIris, TabPane tabPane) {
        if (selectedIris == null) {
            showAlert("Sélection vide", "Aucun iris sélectionné !");
            return;
        }
        Tab irisTab = new Tab("Iris: " + selectedIris.getVariety());
        VBox content = new VBox();
        content.setPadding(new Insets(10));
        content.setSpacing(10);
        Label sepalWidthLabel = new Label("Sepal Width: " + selectedIris.getSepalWidth());
        Label sepalLengthLabel = new Label("Sepal Length: " + selectedIris.getSepalLength());
        Label petalWidthLabel = new Label("Petal Width: " + selectedIris.getPetalWidth());
        Label petalLengthLabel = new Label("Petal Length: " + selectedIris.getPetalLength());
        Label varietyLabel = new Label("Variety: " + selectedIris.getVariety());
        content.getChildren().addAll(
                sepalWidthLabel,
                sepalLengthLabel,
                petalWidthLabel,
                petalLengthLabel,
                varietyLabel
        );
        irisTab.setContent(content);
        irisTab.setClosable(true);
        tabPane.getTabs().add(irisTab);
        tabPane.getSelectionModel().select(irisTab);
    }

    /**
     * Met à jour l'observateur lorsque l'observable notifie un changement.
     *
     * @param observable L'objet observable qui a changé.
     */
    @Override
    public void update(Observable observable) {

    }

    /**
     * Met à jour l'observateur avec des données spécifiques fournies par l'observable.
     *
     * @param observable L'objet observable qui a changé.
     * @param data Les données spécifiques associées à l'événement de mise à jour.
     */
    @Override
    public void update(Observable observable, Object data) {

    }
}
