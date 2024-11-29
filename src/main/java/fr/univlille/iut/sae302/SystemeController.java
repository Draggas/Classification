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
import javafx.stage.Screen; 
import java.io.File;
import java.io.IOException;
import java.util.*;

public class SystemeController extends Stage implements Observer {
    @SuppressWarnings("rawtypes")
    private Data data;
    private SystemeView view;
    private Stage primaryStage;
    private boolean isProjectionInProgress = false;
    private double xmin;
    private double xmax;
    private double ymin;
    private double ymax;


    public SystemeController(SystemeView view, Stage primaryStage) {
        this.view = view;
        this.primaryStage = primaryStage;
        configureOpenFileButton();
        configureUpdateAxisButtons();
        configureProjectionComboBox();
        configureButtonActions();
    }

    /**
     * Affiche la page d'accueil et configure les actions des boutons.
     */
    public void showHomePage() {
        Stage homeStage = new Stage();
        view.showHomePage(homeStage);
        view.getLoadFileButton().setOnAction(e -> {
            File selectedFile = openFile();
            if (selectedFile != null) {
                loadDataFromFile(selectedFile);
                homeStage.close();
            }
        });
    }

    /**
     * Configure l'action du bouton d'ouverture de fichier.
     */
    private void configureOpenFileButton() {
        view.getOpenFileButton().setOnAction(event -> {
            File selectedFile = openFile();
            if (selectedFile != null) {
                loadDataFromFile(selectedFile);
            } else {
                System.out.println("Aucun fichier sélectionné");
            }
        });
    }

    /**
     * Ouvre une boîte de dialogue pour sélectionner un fichier CSV.
     *
     * @return Le fichier sélectionné ou null si aucun fichier n'a été sélectionné.
     */
    private File openFile() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter("Fichiers CSV (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(csvFilter);
        return fileChooser.showOpenDialog(primaryStage);
    }

    /**
     * Charge les données à partir d'un fichier CSV et met à jour l'interface utilisateur.
     *
     * @param selectedFile Le fichier CSV sélectionné.
     */
    private void loadDataFromFile(File selectedFile) {
        try {
            List<String> columns = ChargementDonneesUtil.getCsvColumns(selectedFile.getAbsolutePath());
            view.resetUI();

            if (isPokemonCsv(columns)) {
                loadPokemonData(selectedFile);
            } else if (isIrisCsv(columns)) {
                loadIrisData(selectedFile);
            }

            configureProjectionsAndLegend();
        } catch (IOException e) {
            view.showAlert("Erreur de chargement", "Impossible de lire le fichier sélectionné.");
        }
    }

    private void openNewProjectionTab(TabPane tabPane) {
        NumberAxis xAxis = new NumberAxis(0, 9, 1.0);
        NumberAxis yAxis = new NumberAxis(view.x, view.y, 1.0);
        Tab newTab = new Tab(view.getProjectionComboBox().getValue() + "/" + view.getProjectionComboBox2().getValue());
        ScatterChart<Number, Number> newChart = new ScatterChart<>(xAxis, yAxis);
        XYChart.Series<Number, Number> newSeries = new XYChart.Series<>();
        newChart.setLegendVisible(false);
        newPerformProjection(newSeries, newChart);
        newTab.setContent(newChart);
        tabPane.getTabs().add(newTab);
        tabPane.getSelectionModel().select(newTab);
    }

    /**
     * Configure les projections et la légende après le chargement des données.
     */
    private void configureProjectionsAndLegend() {
        view.getProjectionComboBox().setDisable(false);
        view.getProjectionComboBox2().setDisable(false);

        double minData = data.getMinData();
        double maxData = data.getMaxData();
        double lowerBound = (int) (minData < 1 ? minData : minData - 1);
        double upperBound = (int) maxData + 1;

        view.setChartBounds(lowerBound, upperBound);
        data.attach(this);
    }

    /**
     * Charge les données de Pokémon à partir d'un fichier CSV.
     *
     * @param selectedFile Le fichier CSV contenant les données de Pokémon.
     * @throws IOException Si une erreur survient lors de la lecture du fichier.
     */
    private void loadPokemonData(File selectedFile) throws IOException {
        List<FormatDonneeBrutPokemon> listBrutPokemon = ChargementDonneesUtil.charger(selectedFile.getAbsolutePath(), FormatDonneeBrutPokemon.class);
        List<Pokemon> pokemonData = new ArrayList<>();
        for (FormatDonneeBrutPokemon brut : listBrutPokemon) {
            pokemonData.add(ChargementDonneesUtil.createPokemon(brut));
        }
        view.getProjectionComboBox().getItems().setAll("HP", "Attack", "Defense", "Speed", "Sp Attack", "Sp Defense");
        view.getProjectionComboBox2().getItems().setAll("HP", "Attack", "Defense", "Speed", "Sp Attack", "Sp Defense");
        view.getProjectionComboBox().setValue("HP");
        view.getProjectionComboBox2().setValue("Attack");
        data = new Data<>(pokemonData);

        calculateAxisLimits();
        updateAxes();
        data.attach(this);
    }

    /**
     * Charge les données d'Iris à partir d'un fichier CSV.
     *
     * @param selectedFile Le fichier CSV contenant les données d'Iris.
     * @throws IOException Si une erreur survient lors de la lecture du fichier.
     */
    private void loadIrisData(File selectedFile) throws IOException {
        List<FormatDonneeBrutIris> listBrutIris = ChargementDonneesUtil.charger(selectedFile.getAbsolutePath(), FormatDonneeBrutIris.class);
        List<Iris> irisData = new ArrayList<>();
        for (FormatDonneeBrutIris brut : listBrutIris) {
            irisData.add(ChargementDonneesUtil.createIris(brut));
        }
        view.getProjectionComboBox().getItems().addAll("Sepal Width", "Sepal Length", "Petal Width", "Petal Length");
        view.getProjectionComboBox2().getItems().addAll("Sepal Width", "Sepal Length", "Petal Width", "Petal Length");
        view.getProjectionComboBox().setValue("Sepal Width");
        view.getProjectionComboBox2().setValue("Sepal Length");
        data = new Data<>(irisData);

        calculateAxisLimits();
        updateAxes();
    }

    /**
     * Calcule les limites des axes en fonction des données chargées.
     */
    private void calculateAxisLimits() {
        xmin = Double.MAX_VALUE;
        xmax = Double.MIN_VALUE;
        ymin = Double.MAX_VALUE;
        ymax = Double.MIN_VALUE;

        for (Object o : data.getEData()) {
            if (o instanceof Iris) {
                Iris iris = (Iris) o;
                double xValue = projectionIris(view.getProjectionComboBox().getValue(), iris);
                double yValue = projectionIris(view.getProjectionComboBox2().getValue(), iris);
                xmin = Math.min(xmin, xValue);
                xmax = Math.max(xmax, xValue);
                ymin = Math.min(ymin, yValue);
                ymax = Math.max(ymax, yValue);
            } else if (o instanceof Pokemon) {
                Pokemon pokemon = (Pokemon) o;
                double xValue = projectionPokemon(view.getProjectionComboBox().getValue(), pokemon);
                double yValue = projectionPokemon(view.getProjectionComboBox2().getValue(), pokemon);
                xmin = Math.min(xmin, xValue);
                xmax = Math.max(xmax, xValue);
                ymin = Math.min(ymin, yValue);
                ymax = Math.max(ymax, yValue);
            }
        }
    }

    /**
     * Met à jour les axes du graphique en fonction des limites calculées.
     */
    private void updateAxes() {
        view.setxAxisLowerBound(xmin - 1);
        view.setxAxisUpperBound(xmax + 1);
        view.setyAxisLowerBound(ymin - 1);
        view.setyAxisUpperBound(ymax + 1);
    }

    /**
     * Configure les actions des boutons de mise à jour des axes.
     */
    private void configureUpdateAxisButtons() {
        view.getUpdateXAxisButton().setOnAction(e -> {
            updateAxis(view.getxAxisMinField(), view.getxAxisMaxField(), true);
        });

        view.getUpdateYAxisButton().setOnAction(e -> {
            updateAxis(view.getyAxisMinField(), view.getyAxisMaxField(), false);
        });
    }

    private void updateAxis(TextField minField, TextField maxField, boolean isXAxis) {
        try {
            double newMin = Double.parseDouble(minField.getText());
            double newMax = Double.parseDouble(maxField.getText());

            if (newMin < newMax) {
                Tab selectedTab = view.getTabPane().getSelectionModel().getSelectedItem();
                if (selectedTab != null) {
                    @SuppressWarnings("unchecked")
                    ScatterChart<Number, Number> selectedChart = (ScatterChart<Number, Number>) selectedTab.getContent();
                    NumberAxis selectedAxis = isXAxis ? (NumberAxis) selectedChart.getXAxis() : (NumberAxis) selectedChart.getYAxis();
                    selectedAxis.setLowerBound(newMin);
                    selectedAxis.setUpperBound(newMax);
                    selectedAxis.setTickUnit((newMax - newMin) / 10);

                    selectedChart.layout();

                }
            } else {
                view.showAlert("Min supérieur à Max", "Minimum ne peut pas être supérieur à max pour l'axe " + (isXAxis ? "X" : "Y") + ".");
            }
        } catch (NumberFormatException ex) {
            view.showAlert("Entrée non valide", "Entrez un nombre valide pour l'axe " + (isXAxis ? "X" : "Y") + ".");
        }
    }

    /**
     * Configure les actions des combobox de projection.
     */
    private void configureProjectionComboBox() {
        view.getProjectionComboBox().valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null && newValue.equals(view.getProjectionComboBox2().getValue())) {
                view.getProjectionComboBox2().setValue(oldValue);
            }
            if (!(newValue == null || view.getProjectionComboBox2().getValue() == null) || Objects.equals(newValue, view.getProjectionComboBox2().getValue())) {
                view.getButtonProjection().setDisable(false);
                view.getButtonAddValue().setDisable(false);
            }
        });

        view.getProjectionComboBox2().valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null && newValue.equals(view.getProjectionComboBox().getValue())) {
                view.getProjectionComboBox().setValue(oldValue);
            }
            if (!(newValue == null || view.getProjectionComboBox().getValue() == null) || Objects.equals(newValue, view.getProjectionComboBox().getValue())) {
                view.getButtonProjection().setDisable(false);
                view.getButtonAddValue().setDisable(false);
            }
        });
    }

/**
 * Configure les actions des boutons, y compris le bouton d'ajout de valeur.
 */
@SuppressWarnings("unchecked")
private void configureButtonActions() {
    view.getButtonProjection().setOnAction(e -> {
        Tab selectedTab = view.getTabPane().getSelectionModel().getSelectedItem();
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
                            isProjectionInProgress = true;
                        } else if (response == newTabButton) {
                            openNewProjectionTab(view.getTabPane());
                        }
                    });
                } else {
                    selectedChart.getData().clear();
                    XYChart.Series<Number, Number> newSeries = new XYChart.Series<>();
                    newPerformProjection(newSeries, selectedChart);
                    isProjectionInProgress = true;
                }
            } else {
                view.showAlert("Erreur", "Aucun graphique valide trouvé dans l'onglet sélectionné.");
            }
        } else {
            view.showAlert("Erreur", "Veuillez sélectionner un onglet pour projeter les données.");
        }
    });

    view.getButtonAddValue().setOnAction(event -> {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        Tab selectedTab = view.getTabPane().getSelectionModel().getSelectedItem();

        boolean isPokemon = false;
        Object firstElement = this.data.getEData().get(0);
        isPokemon = firstElement instanceof Pokemon;
        stage.setTitle(isPokemon ? "Ajouter un Pokemon" : "Ajouter un Iris");

        Label xInputLabel = new Label(view.getProjectionComboBox().getValue());
        TextField xInput = new TextField();
        Label yInputLabel = new Label(view.getProjectionComboBox2().getValue());
        TextField yInput = new TextField();
        if (view.getProjectionComboBox().getValue() == null) xInputLabel.setText("INDEFINI :");
        if (view.getProjectionComboBox2().getValue() == null) yInputLabel.setText("INDEFINI :");

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

        Distance<?> distance;
        if(distanceComboBox.getValue().equals("Distance Euclidienne")) {
        distance = new DistanceEuclidienneNormalisee();
        }else{
        distance = new DistanceManhattanNormalisee();
        }
        
        MethodeKnn<?> knn = new MethodeKnn<>(new Data<>(this.data.getEData()));

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

                    if (view.getProjectionComboBox().getValue().equals("Sepal Width")) tmp.setSepalWidth(xNumber);
                    if (view.getProjectionComboBox2().getValue().equals("Sepal Width")) tmp.setSepalWidth(yNumber);

                    if (view.getProjectionComboBox().getValue().equals("Sepal Length")) tmp.setSepalLength(xNumber);
                    if (view.getProjectionComboBox2().getValue().equals("Sepal Length")) tmp.setSepalLength(yNumber);

                    if (view.getProjectionComboBox().getValue().equals("Petal Width")) tmp.setPetalWidth(xNumber);
                    if (view.getProjectionComboBox2().getValue().equals("Petal Width")) tmp.setPetalWidth(yNumber);

                    if (view.getProjectionComboBox().getValue().equals("Petal Length")) tmp.setPetalLength(xNumber);
                    if (view.getProjectionComboBox2().getValue().equals("Petal Length")) tmp.setPetalLength(yNumber);

                    if (tmp.getVariety().equals("Defaut")) {
                            tmp.setVariety(knnIris.classifierObjet(knnIris.trouverMeilleurK(distance), tmp, (Distance<Iris>) distance));
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
                            dataPoint.getNode().setStyle(view.drawIris(tmp.getVariety()));
                            String tooltipText = String.format(
                                    "X: %.2f\t Y: %.2f\t Variety: %s",
                                    ((Number) Objects.requireNonNull(projectionIris(view.getProjectionComboBox().getValue(), tmp))).doubleValue(),
                                    ((Number) Objects.requireNonNull(projectionIris(view.getProjectionComboBox2().getValue(), tmp))).doubleValue(),
                                    tmp.getVariety()
                            );
                            view.addTooltipToPoint(dataPoint, tooltipText);
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
                    MethodeKnn<Pokemon> knnPokemon = new MethodeKnn<>((Data<Pokemon>) this.data);
                    XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(xNumber, yNumber);
                    Pokemon tmp = new Pokemon(nameInput.getText(), 0, 0, 0, 0, 0, 0, 0, 0, null, null, 0, false);
                    if (view.getProjectionComboBox().getValue().equals("Attack")) tmp.setAttack(xNumber);
                    if (view.getProjectionComboBox2().getValue().equals("Attack")) tmp.setAttack(yNumber);

                    if (view.getProjectionComboBox().getValue().equals("Defense")) tmp.setDefense(xNumber);
                    if (view.getProjectionComboBox2().getValue().equals("Defense")) tmp.setDefense(yNumber);

                    if (view.getProjectionComboBox().getValue().equals("Speed")) tmp.setSpeed(xNumber);
                    if (view.getProjectionComboBox2().getValue().equals("Speed")) tmp.setSpeed(yNumber);

                    if (view.getProjectionComboBox().getValue().equals("HP")) tmp.setHp(xNumber);
                    if (view.getProjectionComboBox2().getValue().equals("HP")) tmp.setHp(yNumber);

                    if (view.getProjectionComboBox().getValue().equals("Sp Attack")) tmp.setSpAttack(xNumber);
                    if (view.getProjectionComboBox2().getValue().equals("Sp Attack")) tmp.setSpAttack(yNumber);

                    if (view.getProjectionComboBox().getValue().equals("Sp Defense")) tmp.setSpDefense(xNumber);
                    if (view.getProjectionComboBox2().getValue().equals("Sp Defense")) tmp.setSpDefense(yNumber);

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
                            dataPoint.getNode().setStyle(view.drawPokemon(tmp.getType1()));
                            String tooltipText = String.format(
                                    "X: %.2f\t Y: %.2f\t Name: %s",
                                    xNumber,
                                    yNumber,
                                    tmp.getName()
                            );
                            view.addTooltipToPoint(dataPoint, tooltipText);
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
        grid.add(distanceComboBox, 2, 4, 2, 1);
        grid.add(pourcentage, 4, 4);

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
        xChange.getChildren().addAll(view.getxAxisLabel(), view.getxAxisMinField(), view.getxAxisMaxField(), view.getUpdateXAxisButton());
        yChange.getChildren().addAll(view.getyAxisLabel(), view.getyAxisMinField(), view.getyAxisMaxField(), view.getUpdateYAxisButton());

        VBox regroup = new VBox();
        regroup.getChildren().addAll(view.getButtonProjection(), view.getButtonAddValue());
        regroup.setSpacing(10);

        VBox leftPane = new VBox(10);
        leftPane.setPadding(new Insets(20));
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        leftPane.setSpacing(50);
        leftPane.getChildren().addAll(view.getOpenFileButton(), view.getProjectionComboBox2(), yChange, spacer, regroup);
        leftPane.setAlignment(Pos.TOP_LEFT);

        HBox bottomPane = new HBox();
        bottomPane.setPadding(new Insets(20));
        bottomPane.setAlignment(Pos.CENTER_RIGHT);
        bottomPane.setSpacing(50);
        bottomPane.getChildren().addAll(view.getProjectionComboBox(), xChange);

        BorderPane root = new BorderPane();
        root.setLeft(leftPane);
        root.setCenter(view.getNuage());
        root.setBottom(bottomPane);

        leftPane.setPrefWidth(175);
        view.getButtonProjection().setMaxWidth(leftPane.getPrefWidth());
        view.getButtonAddValue().setMaxWidth(leftPane.getPrefWidth());
        view.getProjectionComboBox().setMaxWidth(leftPane.getPrefWidth());
        view.getProjectionComboBox().setPrefWidth(leftPane.getPrefWidth());
        view.getProjectionComboBox2().setMaxWidth(leftPane.getPrefWidth());
        view.getUpdateXAxisButton().setMaxWidth(leftPane.getPrefWidth());
        view.getUpdateYAxisButton().setMaxWidth(leftPane.getPrefWidth());
        yChange.setMaxWidth(leftPane.getPrefWidth());
        xChange.setMaxWidth(leftPane.getPrefWidth());

        Scene mainScene = new Scene(root, Screen.getPrimary().getBounds().getWidth() / 1.5, Screen.getPrimary().getBounds().getHeight() / 1.5);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Application");
        primaryStage.setMinWidth(root.getWidth());
        primaryStage.setMinHeight(root.getHeight() + 100);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    /**
     * Retourne la valeur de projection pour un Iris en fonction de l'attribut spécifié.
     *
     * @param projection L'attribut de projection (e.g., "Sepal Width", "Petal Length").
     * @param iris L'objet Iris.
     * @return La valeur de projection ou null si l'attribut n'est pas reconnu.
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
     * Retourne la valeur de projection pour un Pokémon en fonction de l'attribut spécifié.
     *
     * @param projection L'attribut de projection (e.g., "HP", "Attack").
     * @param pokemon L'objet Pokémon.
     * @return La valeur de projection ou null si l'attribut n'est pas reconnu.
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
     * Effectue une nouvelle projection et ajoute les données au graphique.
     *
     * @param newSeries La nouvelle série de données pour la projection.
     * @param newChart Le nouveau graphique de projection.
     */
    private void newPerformProjection(XYChart.Series<Number, Number> newSeries, ScatterChart<Number, Number> newChart) {
        String projection = view.getProjectionComboBox().getValue();
        String projection2 = view.getProjectionComboBox2().getValue();
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
                        newValue.setStyle(view.drawIris(((Iris) o).getVariety()));
                    } else {
                        newValue.setStyle(view.drawPokemon(((Pokemon) o).getType1()));
                    }
                    view.addTooltipToPoint(dataPoint, tooltipText);
    
                    dataPoint.getNode().setOnMouseClicked(event -> {
                        Tab newTab = new Tab("Details");
                        VBox content = new VBox();
                        content.getChildren().addAll(
                                new Label("X: " + dataPoint.getXValue()),
                                new Label("Y: " + dataPoint.getYValue()),
                                new Label("Name: " + (o instanceof Pokemon ? ((Pokemon) o).getName() : ""))
                        );
                        newTab.setContent(content);
                        view.getTabPane().getTabs().add(newTab);
                        view.getTabPane().getSelectionModel().select(newTab);
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
     * Vérifie si les colonnes du fichier CSV correspondent aux attributs d'un Pokémon.
     *
     * @param columns La liste des colonnes du fichier CSV.
     * @return true si les colonnes correspondent aux attributs d'un Pokémon, sinon false.
     */
    private boolean isPokemonCsv(List<String> columns) {
        List<String> pokemonColumns = Arrays.asList("name", "attack", "base_egg_steps", "capture_rate", "defense",
                "experience_growth", "hp", "sp_attack", "sp_defense",
                "type1", "type2", "speed", "is_legendary");
        return new HashSet<>(columns).containsAll(pokemonColumns);
    }

    /**
     * Vérifie si les colonnes du fichier CSV correspondent aux attributs d'un Iris.
     *
     * @param columns La liste des colonnes du fichier CSV.
     * @return true si les colonnes correspondent aux attributs d'un Iris, sinon false.
     */
    private boolean isIrisCsv(List<String> columns) {
        List<String> irisColumns = Arrays.asList("sepal.length", "sepal.width", "petal.length", "petal.width", "variety");
        List<String> normalizedColumns = columns.stream()
                .map(column -> column.replace("\"", "").trim().toLowerCase())
                .toList();
        return new HashSet<>(normalizedColumns).containsAll(irisColumns);
    }

    /**
     * Met à jour le pourcentage de réussite si les valeurs saisies sont valides.
     *
     * @param xInput Le champ de saisie pour la valeur X.
     * @param yInput Le champ de saisie pour la valeur Y.
     * @param pourcentage Le label pour afficher le pourcentage de réussite.
     * @param distanceComboBox La combobox pour sélectionner le type de distance.
     * @param knn L'instance de MethodeKnn pour calculer le pourcentage de réussite.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void updatePourcentageIfValid(TextField xInput, TextField yInput, Label pourcentage, ComboBox<String> distanceComboBox, MethodeKnn knn) {
        try {

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

    @Override
    public void update(Observable observable) {
        // Non utilisé
    }

    @Override
    public void update(Observable observable, Object data) {
        // Non utilisé
    }
}    