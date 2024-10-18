package fr.univlille.iut.sae302.view;

import fr.univlille.iut.r304.utils.Observable;
import fr.univlille.iut.r304.utils.Observer;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import fr.univlille.iut.sae302.model.DiceRollData;

public class PercentageView extends Stage implements Observer {
    private DiceRollData diceRollData;
    private Label percentageLabel;

    public PercentageView(DiceRollData diceRollData) {
        this.diceRollData = diceRollData;
        this.diceRollData.attach(this);

        percentageLabel = new Label("Pourcentages des résultats :"); // Initialisation du Label

        VBox vbox = new VBox(percentageLabel);
        Scene scene = new Scene(vbox, 400, 300);
        setScene(scene);
        setTitle("Pourcentages des Lancers de Dés");
        show();
    }

    @Override
    public void update(Observable observable) {
        if (observable instanceof DiceRollData) {
            updatePercentageLabel();
        }
    }

    private void updatePercentageLabel() {
        int[] frequencies = diceRollData.getFrequency();
        int totalRolls = 0;

        // Calculer le nombre total de lancers
        for (int frequency : frequencies) {
            totalRolls += frequency;
        }

        StringBuilder percentages = new StringBuilder("Pourcentages des résultats :\n");
        for (int i = 0; i < frequencies.length; i++) {
            if (totalRolls > 0) {
                double percentage = (frequencies[i] / (double) totalRolls) * 100;
                percentages.append(i).append(" : ").append(String.format("%.1f", percentage)).append("%\n");
            } else {
                percentages.append(i).append(" : 0.0%\n");
            }
        }

        percentageLabel.setText(percentages.toString()); // Met à jour le label avec les pourcentages
    }

    @Override
    public void update(Observable observable, Object data) {
        // Not used in this scenario
    }
}
