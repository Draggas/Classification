package fr.univlille.iut.sae302.genericite;

import javafx.scene.paint.Color;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

//Classe vue
public class SystemeGeneriqueView extends Stage {
    private ScatterChart<Number, Number> chart;
    private XYChart.Series<Number, Number> series;
    private ComboBox<String> xAxisSelector;
    private ComboBox<String> yAxisSelector;
    private Button projectionButton;
    private Button addPointButton;
    private Button loadFileButton;
    private Map<String, TextField> dynamicFields = new HashMap<>();
    private Map<String, Color> varietyColors;
    private Map<String, Color> legendaryColors;
    private File selectedFile;

    public SystemeGeneriqueView() {
        varietyColors = new HashMap<>();
        legendaryColors = new HashMap<>();
        loadColorsFromFile("data/couleur.txt");

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        chart = new ScatterChart<>(xAxis, yAxis);
        series = new XYChart.Series<>();
        chart.getData().add(series);
        chart.setLegendVisible(false);

        xAxisSelector = new ComboBox<>();
        yAxisSelector = new ComboBox<>();

        projectionButton = new Button("Projeter");

        addPointButton = new Button("Ajouter un point");

        loadFileButton = new Button("Charger fichier");

        loadFileButton.setOnAction(event -> loadFile());

        VBox controlBox = new VBox(xAxisSelector, yAxisSelector, projectionButton, loadFileButton);
        HBox mainBox = new HBox(controlBox, chart);

        Scene scene = new Scene(mainBox, 800, 600);
        this.setScene(scene);
        this.setTitle("Système Générique");

        addPointButton.setOnAction(event -> addPoint());
    }

    private void loadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        selectedFile = fileChooser.showOpenDialog(this);
        if (selectedFile != null) {
        }
    }

    public void addPoint() {
        try {
        } catch (NumberFormatException e) {
            showError("Erreur", "Les coordonnées doivent être numériques.");
        }
    }
    
    private void loadColorsFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String section = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                if (line.startsWith("-")) {
                    section = line.substring(1).trim();
                } else {
                    if (section != null) {
                        String[] parts = line.split(":");
                        if (parts.length == 2) {
                            String name = parts[0].trim();
                            String color = parts[1].trim();
                            Color colorValue = parseColor(color);

                            if (section.equals("variety")) {
                                varietyColors.put(name, colorValue);
                            } else if (section.equals("is_legendary")) {
                                legendaryColors.put(name, colorValue);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Color parseColor(String color) {
        switch (color.toLowerCase()) {
            case "red": return Color.RED;
            case "blue": return Color.BLUE;
            case "green": return Color.GREEN;
            case "gray": return Color.GRAY;
            default: return Color.BLACK;
        }
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public ComboBox<String> getXAxisSelector() {
        return xAxisSelector;
    }

    public ComboBox<String> getYAxisSelector() {
        return yAxisSelector;
    }

    public Button getProjectionButton() {
        return projectionButton;
    }

    public Button getAddPointButton() {
        return addPointButton;
    }

    public XYChart.Series<Number, Number> getSeries() {
        return series;
    }

    public void addDynamicField(String key, TextField textField) {
        dynamicFields.put(key, textField);
    }

    public Button getChargerButton() {
        return loadFileButton;
    }
}
