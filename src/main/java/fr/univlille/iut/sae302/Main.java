package fr.univlille.iut.sae302;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.univlille.iut.sae302.ChargementDonneesUtil;
import fr.univlille.iut.sae302.FormatDonneeBrutIris;
import fr.univlille.iut.sae302.Iris;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    //Lancement de l'apps
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            SystemeGeneriqueView view = new SystemeGeneriqueView();
            new SystemeGeneriqueController(view);
            view.show();
        } catch (Exception e) {
            System.err.println("Erreur lors de l'initialisation de l'application : " + e.getMessage());
        }
        /*
        List<FormatDonneeBrutIris> listBrutIris = ChargementDonneesUtil.charger("data/iris.csv", FormatDonneeBrutIris.class);
        List<Iris> listIris = new ArrayList<>();
        for (FormatDonneeBrutIris brut : listBrutIris) {
            listIris.add(ChargementDonneesUtil.createIris(brut));
        }
        Systeme systeme = new Systeme(listIris);
        */
    }
}
