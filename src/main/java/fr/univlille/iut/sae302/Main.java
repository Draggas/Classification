package fr.univlille.iut.sae302;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * La classe principale de l'application qui gère le démarrage de l'interface graphique
 * et le chargement des données.
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        List<FormatDonneeBrutIris> listBrutIris = ChargementDonneesUtil.charger("data/iris.csv", FormatDonneeBrutIris.class);
        List<Iris> listIris = new ArrayList<>();
        for (FormatDonneeBrutIris brut : listBrutIris) {
            listIris.add(ChargementDonneesUtil.createIris(brut));
        }
        Systeme systeme = new Systeme(listIris);
    }
}
