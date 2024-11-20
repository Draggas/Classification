package fr.univlille.iut.sae302;

import javafx.collections.FXCollections;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.opencsv.exceptions.CsvException;

//Classe Contrôleur
public class SystemeGeneriqueController {
        private SystemeGeneriqueView view;
        private ObjetGenerique model;
        private Map<String, Color> varietyColors = new HashMap<>();
        private Map<String, Color> legendaryColors = new HashMap<>();
        private boolean isDataLoaded = false;
    
        public SystemeGeneriqueController(SystemeGeneriqueView view) {
            this.view = view;
    
            view.getChargerButton().setOnAction(event -> {
                try {
                    loadData();
                } catch (CsvException e) {
                    e.printStackTrace();
                }
            });
    
            view.getProjectionButton().setOnAction(event -> {
                if (isDataLoaded) {
                    updateChart();
                }
            });
    
            view.getXAxisSelector().setOnAction(event -> checkAxesAndEnableProjection());
            view.getYAxisSelector().setOnAction(event -> checkAxesAndEnableProjection());
    
            view.getAddPointButton().setOnAction(event -> {
                if (isDataLoaded) {
                    view.addPoint();
                }
            });
        }
    
        private void loadData() throws CsvException {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers CSV (*.csv)", "*.csv"));
            File selectedFile = fileChooser.showOpenDialog(view.getScene().getWindow());
    
            if (selectedFile != null) {
                try {
                    model = ChargementGenerique.chargerGenerique(selectedFile.getAbsolutePath());
                    loadColors();
                    List<String> headers = loadHeadersFromFile(selectedFile.getAbsolutePath());
                    initializeAxisSelectors(headers);
    
                    isDataLoaded = true;
                    view.getProjectionButton().setDisable(false);
                    view.getAddPointButton().setDisable(false);
                    view.getXAxisSelector().setDisable(false);
                    view.getYAxisSelector().setDisable(false);
    
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    
        private void loadColors() {
            try (BufferedReader reader = new BufferedReader(new FileReader("data/couleur.txt"))) {
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
    
        private List<String> loadHeadersFromFile(String filePath) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line = reader.readLine();
                if (line != null) {
                    return List.of(line.split(",")).stream()
                            .map(header -> header.replace("\"", "").trim())
                            .collect(Collectors.toList());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return List.of();
        }
        
        private void initializeAxisSelectors(List<String> headers) {
            view.getXAxisSelector().setItems(FXCollections.observableArrayList(
                headers.stream()
                       .filter(header -> model.getRow(0).get(header) instanceof Number)
                       .collect(Collectors.toList())
            ));
    
            view.getYAxisSelector().setItems(FXCollections.observableArrayList(
                headers.stream()
                       .filter(header -> model.getRow(0).get(header) instanceof Number)
                       .collect(Collectors.toList())
            ));
        }
    
        private void checkAxesAndEnableProjection() {
            String xKey = view.getXAxisSelector().getValue();
            String yKey = view.getYAxisSelector().getValue();
    
            if (xKey != null && yKey != null && !xKey.equals(yKey)) {
                view.getProjectionButton().setDisable(false);
            } else {
                view.getProjectionButton().setDisable(true);
            }
        }
    
        private void updateChart() {
            String xKey = view.getXAxisSelector().getValue();
            String yKey = view.getYAxisSelector().getValue();
    
            if (xKey != null && yKey != null) {
                view.getSeries().getData().clear();
                for (Map<String, Object> row : model.getAllRows()) {
                    if (row.get(xKey) instanceof Number && row.get(yKey) instanceof Number) {
                        double x = ((Number) row.get(xKey)).doubleValue();
                        double y = ((Number) row.get(yKey)).doubleValue();
                        String variety = (String) row.get("variety");
                        Boolean legendary = (Boolean) row.get("is_legendary");
    
                        Color pointColor = varietyColors.getOrDefault(variety, Color.BLACK);
                        if (legendary != null && legendary) {
                            pointColor = legendaryColors.getOrDefault("True", Color.BLACK);
                        }
    
                        XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(x, y);
                        dataPoint.setNode(new Circle(5, pointColor));
                        view.getSeries().getData().add(dataPoint);
                    }
                }
            }
        }
    }
    