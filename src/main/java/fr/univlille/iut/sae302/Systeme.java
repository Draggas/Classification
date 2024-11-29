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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Systeme extends Stage implements Observer {
    private ScatterChart<Number, Number> chart;
    private XYChart.Series<Number, Number> series;
    private Data<?> data;
    private ComboBox<String> projectionComboBox;
    private ComboBox<String> projectionComboBox2;
    private TabPane tabPane;
    private double x = 0.0;
    private double y = 9.0;
    private boolean isProjectionInProgress = false;
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
    NumberAxis selectedAxis;
    private double xmin;
    private double xmax;
    private double ymin;
    private double ymax;
    

    public Systeme() {
        initializeChart();
        initializeUIComponents();
        configureOpenFileButton();
        configureUpdateAxisButtons();
        configureProjectionComboBox();
        configureButtonActions();
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
    
    private void configureOpenFileButton() {
        openFileButton = new Button("Ouvrir");
        openFileButton.setOnAction(event -> {
            File selectedFile = openFile();
            if (selectedFile != null) {
                loadDataFromFile(selectedFile);
            } else {
                System.out.println("Aucun fichier sélectionné");
            }
        });
    }
    
    private File openFile() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter("Fichiers CSV (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(csvFilter);
        Stage fileChooserStage = new Stage();
        fileChooserStage.initModality(Modality.APPLICATION_MODAL);
        return fileChooser.showOpenDialog(fileChooserStage);
    }
    
    private void loadDataFromFile(File selectedFile) {
        try {
            List<String> columns = ChargementDonneesUtil.getCsvColumns(selectedFile.getAbsolutePath());
            resetUI();
    
            if (isPokemonCsv(columns)) {
                loadPokemonData(selectedFile);
            } else if (isIrisCsv(columns)) {
                loadIrisData(selectedFile);
            }
    
            configureProjectionsAndLegend();
        } catch (IOException e) {
            showAlert("Erreur de chargement", "Impossible de lire le fichier sélectionné.");
        }
    }
    
    private void resetUI() {
        projectionComboBox.getItems().clear();
        projectionComboBox2.getItems().clear();
        projectionComboBox.setDisable(true);
        projectionComboBox2.setDisable(true);
        buttonProjection.setDisable(true);
        buttonAddValue.setDisable(true);
    }
    
    private void loadPokemonData(File selectedFile) throws IOException {
        List<FormatDonneeBrutPokemon> listBrutPokemon = ChargementDonneesUtil.charger(selectedFile.getAbsolutePath(), FormatDonneeBrutPokemon.class);
        List<Pokemon> pokemonData = new ArrayList<>();
        for (FormatDonneeBrutPokemon brut : listBrutPokemon) {
            pokemonData.add(ChargementDonneesUtil.createPokemon(brut));
        }
        projectionComboBox.getItems().setAll("HP", "Attack", "Defense", "Speed", "Sp Attack", "Sp Defense");
        projectionComboBox2.getItems().setAll("HP", "Attack", "Defense", "Speed", "Sp Attack", "Sp Defense");
        projectionComboBox.setValue("HP"); // Valeur par défaut
        projectionComboBox2.setValue("Attack"); // Valeur par défaut
        data = new Data<>(pokemonData);
    
        // Ajuster les limites des axes en fonction des données
        calculateAxisLimits();
        updateAxes();
        data.attach(this);
    }
    
    
    
    
    private void loadIrisData(File selectedFile) throws IOException {
        List<FormatDonneeBrutIris> listBrutIris = ChargementDonneesUtil.charger(selectedFile.getAbsolutePath(), FormatDonneeBrutIris.class);
        List<Iris> irisData = new ArrayList<>();
        for (FormatDonneeBrutIris brut : listBrutIris) {
            irisData.add(ChargementDonneesUtil.createIris(brut));
        }
        projectionComboBox.getItems().addAll("Sepal Width", "Sepal Length", "Petal Width", "Petal Length");
        projectionComboBox2.getItems().addAll("Sepal Width", "Sepal Length", "Petal Width", "Petal Length");
        projectionComboBox.setValue("Sepal Width"); // Valeur par défaut
        projectionComboBox2.setValue("Sepal Length"); // Valeur par défaut
        data = new Data<>(irisData);
    
        // Ajuster les limites des axes en fonction des données
        calculateAxisLimits();
        updateAxes();
    }
    
    
    private void calculateAxisLimits() {
        xmin = Double.MAX_VALUE;
        xmax = Double.MIN_VALUE;
        ymin = Double.MAX_VALUE;
        ymax = Double.MIN_VALUE;
    
        for (Object o : data.getEData()) {
            if (o instanceof Iris) {
                Iris iris = (Iris) o;
                double xValue = projectionIris(projectionComboBox.getValue(), iris);
                double yValue = projectionIris(projectionComboBox2.getValue(), iris);
                xmin = Math.min(xmin, xValue);
                xmax = Math.max(xmax, xValue);
                ymin = Math.min(ymin, yValue);
                ymax = Math.max(ymax, yValue);
            } else if (o instanceof Pokemon) {
                Pokemon pokemon = (Pokemon) o;
                double xValue = projectionPokemon(projectionComboBox.getValue(), pokemon);
                double yValue = projectionPokemon(projectionComboBox2.getValue(), pokemon);
                xmin = Math.min(xmin, xValue);
                xmax = Math.max(xmax, xValue);
                ymin = Math.min(ymin, yValue);
                ymax = Math.max(ymax, yValue);
            }
        }
    }    

    private void updateAxes() {
        xAxis.setLowerBound(xmin - 1);
        xAxis.setUpperBound(xmax + 1);
        yAxis.setLowerBound(ymin - 1);
        yAxis.setUpperBound(ymax + 1);
    }    
    
    private void configureProjectionsAndLegend() {
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
    
    private void configureUpdateAxisButtons() {
        updateXAxisButton.setOnAction(e -> {
            updateAxis(xAxisMinField, xAxisMaxField, true);
        });
    
        updateYAxisButton.setOnAction(e -> {
            updateAxis(yAxisMinField, yAxisMaxField, false);
        });
    }
    
    
    private void updateAxis(TextField minField, TextField maxField, boolean isXAxis) {
        try {
            double newMin = Double.parseDouble(minField.getText());
            double newMax = Double.parseDouble(maxField.getText());
    
            if (newMin < newMax) {
                Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
                if (selectedTab != null) {
                    ScatterChart<Number, Number> selectedChart = (ScatterChart<Number, Number>) selectedTab.getContent();
                    NumberAxis selectedAxis = isXAxis ? (NumberAxis) selectedChart.getXAxis() : (NumberAxis) selectedChart.getYAxis();
                    selectedAxis.setLowerBound(newMin);
                    selectedAxis.setUpperBound(newMax);
                    selectedAxis.setTickUnit((newMax - newMin) / 10);
    
                    // Assurer la mise à jour visuelle du graphique
                    selectedChart.layout();
    
                }
            } else {
                showAlert("Min supérieur à Max", "Minimum ne peut pas être supérieur à max pour l'axe " + (isXAxis ? "X" : "Y") + ".");
            }
        } catch (NumberFormatException ex) {
            showAlert("Entrée non valide", "Entrez un nombre valide pour l'axe " + (isXAxis ? "X" : "Y") + ".");
        }
    }
    
    
    
    private void configureProjectionComboBox() {
        projectionComboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null && newValue.equals(projectionComboBox2.getValue())) {
                projectionComboBox2.setValue(oldValue);
            }
            if (!(newValue == null || projectionComboBox2.getValue() == null) || Objects.equals(newValue, projectionComboBox2.getValue())) {
                buttonProjection.setDisable(false);
                buttonAddValue.setDisable(false);
            }
        });
    
        projectionComboBox2.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null && newValue.equals(projectionComboBox.getValue())) {
                projectionComboBox.setValue(oldValue);
            }
            if (!(newValue == null || projectionComboBox.getValue() == null) || Objects.equals(newValue, projectionComboBox.getValue())) {
                buttonProjection.setDisable(false);
                buttonAddValue.setDisable(false);
            }
        });
    }
    
    private void configureButtonActions() {
        buttonProjection.setOnAction(e -> {
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            if (selectedTab != null) {
                ScatterChart<Number, Number> selectedChart = (ScatterChart<Number, Number>) selectedTab.getContent();
                if (selectedChart != null) {
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
                                selectedChart.getData().clear();
                                XYChart.Series<Number, Number> newSeries = new XYChart.Series<>();
                                newPerformProjection(newSeries, selectedChart);
                                isProjectionInProgress = true; // Marque la projection comme en cours
                            } else if (response == newTabButton) {
                                openNewProjectionTab(tabPane);
                            }
                        });
                    } else {
                        selectedChart.getData().clear();
                        XYChart.Series<Number, Number> newSeries = new XYChart.Series<>();
                        newPerformProjection(newSeries, selectedChart);
                        isProjectionInProgress = true; // Marque la projection comme en cours
                    }
                } else {
                    showAlert("Erreur", "Aucun graphique valide trouvé dans l'onglet sélectionné.");
                }
            } else {
                showAlert("Erreur", "Veuillez sélectionner un onglet pour projeter les données.");
            }
        });
        
    
        buttonAddValue.setOnAction(event -> {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
    
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
    
            boolean isPokemon = false;
            Object firstElement = this.data.getEData().get(0);
            isPokemon = firstElement instanceof Pokemon;
            stage.setTitle(isPokemon ? "Ajouter un Pokemon" : "Ajouter un Iris");
    
            Label xInputLabel = new Label(projectionComboBox.getValue());
            TextField xInput = new TextField();
            Label yInputLabel = new Label(projectionComboBox2.getValue());
            TextField yInput = new TextField();
            if (projectionComboBox.getValue() == null) xInputLabel.setText("INDEFINI :");
            if (projectionComboBox2.getValue() == null) yInputLabel.setText("INDEFINI :");
    
            Label nameLabel = new Label("Nom :");
            TextField nameInput = new TextField();
            nameLabel.setVisible(isPokemon);
            nameInput.setVisible(isPokemon);
    
            Label varietyLabel = new Label(isPokemon ? "Type :" : "Variety :");
            ComboBox<String> varietyComboBox = new ComboBox<>();
            if (isPokemon) {
                varietyComboBox.getItems().addAll("Defaut", "Fire", "Water", "Grass", "Electric", "Normal", "Fighting");
            } else {
                varietyComboBox.getItems().addAll("Defaut", "Setosa", "Versicolor", "Virginica");
            }
            varietyComboBox.setValue("Defaut");
    
            new Label("Distance :");
            ComboBox<String> distanceComboBox = new ComboBox<>();
            distanceComboBox.getItems().addAll("Distance Euclidienne", "Distance Manhattan");
            distanceComboBox.setValue("Distance Euclidienne");
    
            MethodeKnn knn = new MethodeKnn(new Data<>(this.data.getEData()));
    
            Button buttonAdd = new Button("Ajouter");
            Label pourcentage = new Label("Pourcentage: 0%");
    
            xInput.textProperty().addListener((observable, oldValue, newValue) -> {
                updatePourcentageIfValid(xInput, yInput, pourcentage, distanceComboBox, knn);
            });
    
            yInput.textProperty().addListener((observable, oldValue, newValue) -> {
                updatePourcentageIfValid(xInput, yInput, pourcentage, distanceComboBox, knn);
            });
    
            buttonAdd.setOnAction(ev -> {
                try {
                    if (this.data.getEData().get(0) instanceof Iris) {
                        double xNumber = Double.parseDouble(xInput.getText());
                        double yNumber = Double.parseDouble(yInput.getText());
                        String variety = varietyComboBox.getValue();
                        MethodeKnn<Iris> knnIris = new MethodeKnn<>((Data<Iris>) this.data);
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
                            tmp.setVariety(knnIris.classifierObjet(knnIris.trouverMeilleurK(new DistanceEuclidienneNormalisee()), tmp, new DistanceEuclidienneNormalisee()));
                        }
    
                        List<Iris> irisData = (List<Iris>) this.data.getEData();
                        irisData.add(tmp);
                        this.data = new Data<>(irisData);
                        this.data.attach(this);
    
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
                                stage.close();
                            }
                        }
                    } else if (this.data.getEData().get(0) instanceof Pokemon) {
                        if (nameInput.getText().trim().isEmpty()) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Erreur");
                            alert.setHeaderText("Nom obligatoire !");
                            alert.setContentText("Veuillez entrer un nom pour le Pokémon.");
                            alert.showAndWait();
                            return;
                        }
    
                        double xNumber = Double.parseDouble(xInput.getText());
                        double yNumber = Double.parseDouble(yInput.getText());
                        String name = varietyComboBox.getValue();
                        MethodeKnn<Pokemon> knnPokemon = new MethodeKnn<>((Data<Pokemon>) this.data);
                        XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(xNumber, yNumber);
                        Pokemon tmp = new Pokemon(nameInput.getText(), 0, 0, 0, 0, 0, 0, 0, 0, null, null, 0, false);
                        if (projectionComboBox.getValue().equals("Attack")) tmp.setAttack(xNumber);
                        if (projectionComboBox2.getValue().equals("Attack")) tmp.setAttack(yNumber);
    
                        if (projectionComboBox.getValue().equals("Defense")) tmp.setDefense(xNumber);
                        if (projectionComboBox2.getValue().equals("Defense")) tmp.setDefense(yNumber);
    
                        if (projectionComboBox.getValue().equals("Speed")) tmp.setSpeed(xNumber);
                        if (projectionComboBox2.getValue().equals("Speed")) tmp.setSpeed(yNumber);
    
                        if (projectionComboBox.getValue().equals("HP")) tmp.setHp(xNumber);
                        if (projectionComboBox2.getValue().equals("HP")) tmp.setHp(yNumber);
    
                        if (projectionComboBox.getValue().equals("Sp Attack")) tmp.setSpAttack(xNumber);
                        if (projectionComboBox2.getValue().equals("Sp Attack")) tmp.setSpAttack(yNumber);
    
                        if (projectionComboBox.getValue().equals("Sp Defense")) tmp.setSpDefense(xNumber);
                        if (projectionComboBox2.getValue().equals("Sp Defense")) tmp.setSpDefense(yNumber);
    
                        if (tmp.getName().equals("Default")) {
                            tmp.setType1(knnPokemon.classifierObjet(knnPokemon.trouverMeilleurK(new DistanceEuclidienneNormalisee()), tmp, new DistanceEuclidienneNormalisee()));
                        }
    
                        List<Pokemon> pokemonData = (List<Pokemon>) this.data.getEData();
                        pokemonData.add(tmp);
                        this.data = new Data<>(pokemonData);
                        this.data.attach(this);
    
                        if (selectedTab != null) {
                            XYChart<Number, Number> selectedChart = (XYChart<Number, Number>) selectedTab.getContent();
                            if (selectedChart != null) {
                                XYChart.Series<Number, Number> chartSeries = new XYChart.Series<>();
                                chartSeries.setName("Pokemon Data");
                                chartSeries.getData().add(dataPoint);
                                selectedChart.getData().add(chartSeries);
                                dataPoint.getNode().setStyle(drawPokemon(tmp.getType1()));
                                String tooltipText = String.format(
                                        "X: %.2f\t Y: %.2f\t Name: %s",
                                        xNumber,
                                        yNumber,
                                        tmp.getName()
                                );
                                addTooltipToPoint(dataPoint, tooltipText);
                                stage.close();
                            }
                        }
                    }
                } catch (NumberFormatException e) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText("Veuillez entrer des nombres valides.");
                    a.show();
                } catch (ClassCastException e) {
                    System.err.println("Invalid cast: Data is not of type Data<Iris>");
                }
            });
    
            // Mise en page pour le formulaire
            GridPane grid = new GridPane();
    
            grid.add(nameLabel, 0, 0);
            grid.add(nameInput, 1, 0);
    
            grid.add(xInputLabel, 0, 1);
            grid.add(xInput, 1, 1);
            grid.add(yInputLabel, 0, 2);
            grid.add(yInput, 1, 2);
    
            grid.add(varietyLabel, 0, 3);
            grid.add(varietyComboBox, 1, 3);
    
            grid.add(buttonAdd, 0, 4, 2, 1);
            grid.add(pourcentage, 1, 4);
    
            GridPane.setMargin(nameLabel, new Insets(20, 5, 5, 20));
            GridPane.setMargin(nameInput, new Insets(5, 20, 10, 5));
    
            GridPane.setMargin(xInputLabel, new Insets(20, 5, 5, 20));
            GridPane.setMargin(xInput, new Insets(20, 20, 5, 5));
            GridPane.setMargin(yInputLabel, new Insets(5, 5, 10, 20));
            GridPane.setMargin(yInput, new Insets(5, 20, 10, 5));
            GridPane.setMargin(varietyLabel, new Insets(10, 5, 10, 20));
            GridPane.setMargin(varietyComboBox, new Insets(10, 20, 10, 5));
            GridPane.setMargin(buttonAdd, new Insets(20, 0, 20, 20));
    
            Scene scene = new Scene(grid);
            stage.setScene(scene);
            stage.showAndWait();
        });
    
        VBox xChange = new VBox();
        xChange.setSpacing(10);
        xChange.setAlignment(Pos.CENTER);
        VBox yChange = new VBox();
        yChange.setSpacing(10);
        yChange.setAlignment(Pos.CENTER);
        xChange.getChildren().addAll(xAxisLabel, xAxisMinField, xAxisMaxField, updateXAxisButton);
        yChange.getChildren().addAll(yAxisLabel, yAxisMinField, yAxisMaxField, updateYAxisButton);
    
        VBox regroup = new VBox();
        regroup.getChildren().addAll(buttonProjection, buttonAddValue);
        regroup.setSpacing(10);
    
        VBox leftPane = new VBox(10);
        leftPane.setPadding(new Insets(20));
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        leftPane.setSpacing(50);
        leftPane.getChildren().addAll(openFileButton, projectionComboBox2, yChange, spacer, regroup);
        leftPane.setAlignment(Pos.TOP_LEFT);
    
        HBox bottomPane = new HBox();
        bottomPane.setPadding(new Insets(20));
        bottomPane.setAlignment(Pos.CENTER_RIGHT);
        bottomPane.setSpacing(50);
        bottomPane.getChildren().addAll(projectionComboBox, xChange);
    
        BorderPane root = new BorderPane();
        root.setLeft(leftPane);
        root.setCenter(nuage);
        root.setBottom(bottomPane);
    
        leftPane.setPrefWidth(175);
        buttonProjection.setMaxWidth(leftPane.getPrefWidth());
        buttonAddValue.setMaxWidth(leftPane.getPrefWidth());
        projectionComboBox.setMaxWidth(leftPane.getPrefWidth());
        projectionComboBox.setPrefWidth(leftPane.getPrefWidth());
        projectionComboBox2.setMaxWidth(leftPane.getPrefWidth());
        updateXAxisButton.setMaxWidth(leftPane.getPrefWidth());
        updateYAxisButton.setMaxWidth(leftPane.getPrefWidth());
        yChange.setMaxWidth(leftPane.getPrefWidth());
        xChange.setMaxWidth(leftPane.getPrefWidth());
    
        Scene mainScene = new Scene(root, Screen.getPrimary().getBounds().getWidth() / 1.5, Screen.getPrimary().getBounds().getHeight() / 1.5);
        setScene(mainScene);
        setTitle("Application");
        this.setMinWidth(root.getWidth());
        this.setMinHeight(root.getHeight() + 100);
        this.centerOnScreen();
        show();
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
     * Définit la couleur et la taille d'un point de données Pokémon en fonction de son type.
     *
     * @param type1 Le type du Pokémon (Fire, Water, Grass, Electric ou autre).
     * @return Une chaîne de caractères représentant les styles CSS pour le point de données.
     */
    private String drawPokemon(Type type1) {
        String color = switch (type1.getName().toLowerCase()) {
            case "fire" -> "-fx-background-color: orange;";
            case "water" -> "-fx-background-color: blue;";
            case "grass" -> "-fx-background-color: green;";
            case "electric" -> "-fx-background-color: yellow;";
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
    private Double projectionIris(String projection, Iris iris) {
        Number value = switch (projection) {
            case "Sepal Width" -> iris.getSepalWidth();
            case "Sepal Length" -> iris.getSepalLength();
            case "Petal Width" -> iris.getPetalWidth();
            case "Petal Length" -> iris.getPetalLength();
            default -> null;
        };
        return value != null ? value.doubleValue() : null;
    }
    
    /**
     * Projette les valeurs d'un Pokémon en fonction d'une caractéristique sélectionnée.
     *
     * @param projection La caractéristique à projeter (HP, Attack, Defense, Speed, Sp. Attack ou Sp. Defense).
     * @param pokemon L'objet Pokémon dont on souhaite obtenir la valeur pour la projection.
     * @return La valeur de la caractéristique sélectionnée pour le Pokémon, ou null si la projection est invalide.
     */
    private Double projectionPokemon(String projection, Pokemon pokemon) {
        Number value = switch (projection) {
            case "HP" -> pokemon.getHp();
            case "Attack" -> pokemon.getAttack();
            case "Defense" -> pokemon.getDefense();
            case "Speed" -> pokemon.getSpeed();
            case "Sp. Attack" -> pokemon.getSpAttack();
            case "Sp. Defense" -> pokemon.getSpDefense();
            default -> null;
        };
        return value != null ? value.doubleValue() : null;
    }    

    /**
     * Affiche une alerte avec un titre et un message spécifiés.
     *
     * @param title Le titre de l'alerte.
     * @param message Le message de l'alerte.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Ajoute une infobulle à un point de données dans un graphique.
     *
     * @param dataPoint Le point de données auquel ajouter l'infobulle.
     * @param tooltipText Le texte de l'infobulle.
     */
    private void addTooltipToPoint(XYChart.Data<Number, Number> dataPoint, String tooltipText) {
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
    

    /**
     * Réalise une nouvelle projection des données dans un graphique de dispersion.
     *
     * @param newSeries La nouvelle série de données à afficher.
     * @param newChart Le nouveau graphique de dispersion.
     */
    private void newPerformProjection(XYChart.Series<Number, Number> newSeries, ScatterChart<Number, Number> newChart) {
        String projection = projectionComboBox.getValue();
        String projection2 = projectionComboBox2.getValue();
        newChart.getXAxis().setLabel(projection);
        newChart.getYAxis().setLabel(projection2);
        newChart.getData().clear();
        for (Object o : this.data.getEData()) {
            Number xValue = null;
            Number yValue = null;
            String tooltipText;
            if (o instanceof Iris) {
                Iris iris = (Iris) o;
                xValue = projectionIris(projection, iris);
                yValue = projectionIris(projection2, iris);
                if (xValue == null || yValue == null) continue;
                tooltipText = String.format(
                        "X: %.2f\t Y: %.2f\t Variety: %s",
                        xValue.doubleValue(),
                        yValue.doubleValue(),
                        iris.getVariety()
                );
            } else if (o instanceof Pokemon) {
                Pokemon pokemon = (Pokemon) o;
                xValue = projectionPokemon(projection, pokemon);
                yValue = projectionPokemon(projection2, pokemon);
                if (xValue == null || yValue == null) continue;
                tooltipText = String.format(
                        "X: %.2f\t Y: %.2f\t Name: %s",
                        xValue.doubleValue(),
                        yValue.doubleValue(),
                        pokemon.getName()
                );
            } else {
                tooltipText = "";
                continue;
            }
            XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(xValue, yValue);
            dataPoint.nodeProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    if (o instanceof Iris) {
                        newValue.setStyle(drawIris(((Iris) o).getVariety()));
                    } else {
                        newValue.setStyle(drawPokemon(((Pokemon) o).getType1()));
                    }
                    addTooltipToPoint(dataPoint, tooltipText);
    
                    dataPoint.getNode().setOnMouseClicked(event -> {
                        Tab newTab = new Tab("Details");
                        VBox content = new VBox();
                        content.getChildren().addAll(
                                new Label("X: " + dataPoint.getXValue()),
                                new Label("Y: " + dataPoint.getYValue()),
                                new Label("Name: " + (o instanceof Pokemon ? ((Pokemon) o).getName() : ""))
                        );
                        newTab.setContent(content);
                        tabPane.getTabs().add(newTab);
                        tabPane.getSelectionModel().select(newTab);
                    });
                }
            });
            newSeries.getData().add(dataPoint);
        }
        newChart.getData().add(newSeries);
        calculateAxisLimits();
        updateAxes();
    }
    
    

    /**
     * Ouvre un nouvel onglet de projection dans un TabPane.
     *
     * @param tabPane Le TabPane dans lequel ouvrir le nouvel onglet.
     */
    private void openNewProjectionTab(TabPane tabPane) {
        NumberAxis xAxis = new NumberAxis(x, y, 1.0);
        NumberAxis yAxis = new NumberAxis(x, y, 1.0);
        Tab newTab = new Tab(projectionComboBox.getValue() + "/" + projectionComboBox2.getValue());
        ScatterChart<Number, Number> newChart = new ScatterChart<>(xAxis, yAxis);
        XYChart.Series<Number, Number> newSeries = new XYChart.Series<>();
        newChart.setLegendVisible(false);
        // newChart.getData().add(newSeries);
        newPerformProjection(newSeries, newChart);
        newTab.setContent(newChart);
        tabPane.getTabs().add(newTab);
        tabPane.getSelectionModel().select(newTab);
    }

    /**
     * Vérifie si les colonnes d'un fichier CSV correspondent à celles d'un fichier Pokémon.
     *
     * @param columns La liste des colonnes du fichier CSV.
     * @return true si les colonnes correspondent à celles d'un fichier Pokémon, sinon false.
     */
    private boolean isPokemonCsv(List<String> columns) {
        List<String> pokemonColumns = Arrays.asList("name", "attack", "base_egg_steps", "capture_rate", "defense",
                "experience_growth", "hp", "sp_attack", "sp_defense",
                "type1", "type2", "speed", "is_legendary");
        return new HashSet<>(columns).containsAll(pokemonColumns);
    }

    /**
     * Vérifie si les colonnes d'un fichier CSV correspondent à celles d'un fichier Iris.
     *
     * @param columns La liste des colonnes du fichier CSV.
     * @return true si les colonnes correspondent à celles d'un fichier Iris, sinon false.
     */
    private boolean isIrisCsv(List<String> columns) {
        List<String> irisColumns = Arrays.asList("sepal.length", "sepal.width", "petal.length", "petal.width", "variety");
        List<String> normalizedColumns = columns.stream()
                .map(column -> column.replace("\"", "").trim().toLowerCase())
                .toList();
        return new HashSet<>(normalizedColumns).containsAll(irisColumns);
    }

    /**
     * Crée une légende pour les variétés d'Iris.
     *
     * @return Un HBox contenant les éléments de la légende.
     */
    private HBox createIrisLegend() {
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

    /**
     * Crée une légende pour les types de Pokémon.
     *
     * @return Un HBox contenant les éléments de la légende.
     */
    private HBox createPokemonLegend() {
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

    /**
     * Met à jour le pourcentage de réussite si les entrées sont valides.
     *
     * @param xInput Le champ de texte pour l'entrée x.
     * @param yInput Le champ de texte pour l'entrée y.
     * @param pourcentage Le label pour afficher le pourcentage.
     * @param distanceComboBox La combobox pour sélectionner le type de distance.
     * @param knn L'objet MethodeKnn pour calculer le pourcentage de réussite.
     */
    private void updatePourcentageIfValid(TextField xInput, TextField yInput, Label pourcentage, ComboBox<String> distanceComboBox, MethodeKnn knn) {
        try {
            double xNumber = Double.parseDouble(xInput.getText());
            double yNumber = Double.parseDouble(yInput.getText());

            boolean useEuclidean = distanceComboBox.getValue().equals("Distance Euclidienne");
            DistanceEuclidienneNormalisee euclidienneCalc = new DistanceEuclidienneNormalisee();
            DistanceManhattanNormalisee manhattanCalc = new DistanceManhattanNormalisee();
            Distance distanceCalcul = useEuclidean ? euclidienneCalc : manhattanCalc;

            double percentage = knn.calculerPourcentageReussite(knn.trouverMeilleurK(distanceCalcul), distanceCalcul);
            pourcentage.setText(String.format("Pourcentage: %.2f%%", percentage));
        } catch (NumberFormatException e) {
            pourcentage.setText("Pourcentage: 0%");
        }
    }

    /**
     * Met à jour la légende dans le conteneur en fonction du type de données (Pokémon ou Iris).
     *
     * @param container Le conteneur dans lequel ajouter la légende.
     * @param isPokemon Indique si les données sont de type Pokémon (true) ou Iris (false).
     */
    void updateLegend(HBox container, boolean isPokemon) {
        container.getChildren().clear();
        if (isPokemon) {
            container.getChildren().add(createPokemonLegend());
        } else {
            container.getChildren().add(createIrisLegend());
        }
    }

    /**
     * Met à jour l'observateur lorsque l'observable notifie un changement.
     *
     * @param observable L'objet observable qui a changé.
     */
    @Override
    public void update(Observable observable) {
        // Implementation
    }

    /**
     * Met à jour l'observateur avec des données spécifiques fournies par l'observable.
     *
     * @param observable L'objet observable qui a changé.
     * @param data Les données spécifiques associées à l'événement de mise à jour.
     */
    @Override
    public void update(Observable observable, Object data) {
        // Implementation
    }
}