package fr.univlille.iut.sae302;

import fr.univlille.iut.sae302.model.DiceRollData;
import fr.univlille.iut.sae302.view.PercentageView;
import fr.univlille.iut.sae302.view.ScatterView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        DiceRollData diceRollData = new DiceRollData();
        new ScatterView(diceRollData);
        new PercentageView(diceRollData);

    }
}
