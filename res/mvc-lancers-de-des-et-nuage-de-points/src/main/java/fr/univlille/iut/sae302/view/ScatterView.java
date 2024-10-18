package fr.univlille.iut.sae302.view;

import fr.univlille.iut.r304.utils.Observable;
import fr.univlille.iut.r304.utils.Observer;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import fr.univlille.iut.sae302.model.DiceRollData;

public class ScatterView extends Stage implements Observer {
    private ScatterChart<Number, Number> chart;
    private XYChart.Series<Number, Number> series;
    private DiceRollData diceRollData;

    private Label resultLabel;
    public ScatterView(DiceRollData diceRollData) {
        this.diceRollData = diceRollData;
        this.diceRollData.attach(this);

        NumberAxis xAxis = new NumberAxis(0, 10, 1);
        NumberAxis yAxis = new NumberAxis(0, 20, 1);
        xAxis.setLabel("Valeur du Dé");
        yAxis.setLabel("Fréquence");
        chart = new ScatterChart<>(xAxis, yAxis);
        series = new XYChart.Series<>();
        chart.getData().add(series);

        resultLabel = new Label(""); // Initialisation du Label

        Button rollButton = new Button("Lancer");
        rollButton.setOnAction(e -> {
            int rolledValue = diceRollData.rollDice();
            resultLabel.setText("Dernière valeur tirée : " + rolledValue); // Met à jour le label
        });

        Button rollButton5 = new Button("Lancer 5");
        rollButton5.setOnAction(e -> {
            int rolledValue = diceRollData.rollDiceN(5);
            resultLabel.setText("Dernière valeur tirée : " + rolledValue); // Met à jour le label
        });

        VBox vbox = new VBox(chart, rollButton, rollButton5, resultLabel);
        Scene scene = new Scene(vbox, 800, 600);
        setScene(scene);
        setTitle("Simulation de Lancers de Dés");
        show();
    }

    @Override
    public void update(Observable observable) {
        if (observable instanceof DiceRollData) {
            int[] frequencies = ((DiceRollData) observable).getFrequency();
            series.getData().clear(); // Clear previous data

            for (int i = 0; i < frequencies.length; i++) {
                if (frequencies[i] > 0) { // Ajoutez uniquement les points avec une fréquence > 0
                    series.getData().add(new XYChart.Data<>(i, frequencies[i]));
                }
            }
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        // Not used in this scenario
    }
}
