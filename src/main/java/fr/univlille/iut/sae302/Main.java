package fr.univlille.iut.sae302;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        List<FormatDonneeBrutIris> listBrutIris = ChargementDonneesUtil.charger("data/iris.csv", FormatDonneeBrutIris.class);
        List<Iris> listIris = new ArrayList<>();
        for (FormatDonneeBrutIris brut : listBrutIris) {
            listIris.add(new Iris(brut.getSepal_length(), brut.getSepal_width(), brut.getPetal_length(), brut.getPetal_width(), brut.getVariety()));
        }
        Systeme systeme = new Systeme(listIris);
    }
}
