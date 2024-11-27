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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.opencsv.exceptions.CsvException;

//Classe Contrôleur
public class SystemeGeneriqueController {
        private SystemeGeneriqueView view;
        private CSVGenerique model;
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
                    Set<String> headers = model.getHeaders();
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
    
        private void initializeAxisSelectors(Set<String> headers) {
            view.getXAxisSelector().setItems(FXCollections.observableArrayList(
                headers.stream()
                       .filter(header -> model.getHeaders() instanceof Number)
                       .collect(Collectors.toList())
            ));
    
            view.getYAxisSelector().setItems(FXCollections.observableArrayList(
                headers.stream()
                       .filter(header -> model.getHeaders() instanceof Number)
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
                double x = Double.MIN_VALUE;
                double y = Double.MIN_VALUE;
                for (int i=0;i<model.size();i++) {
                    if (model.getColumns(xKey).get(i) instanceof Number) {
                        x = ((Number) model.getColumns(xKey).get(i)).doubleValue();
                    }
                    if (model.getColumns(yKey).get(i) instanceof Number) {
                        y = ((Number) model.getColumns(xKey).get(i)).doubleValue();
                    }
                    if(x == Double.MIN_VALUE || y == Double.MIN_VALUE){
                        XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(x, y);
                        dataPoint.setNode(new Circle(5, Color.BLACK));
                        view.getSeries().getData().add(dataPoint);
                    }
                }
            }
        }
}
