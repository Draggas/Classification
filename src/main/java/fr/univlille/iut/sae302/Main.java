package fr.univlille.iut.sae302;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    //Lancement de l'apps
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            SystemeGeneriqueView view = new SystemeGeneriqueView();
            new SystemeGeneriqueController(view);
            view.show();
        } catch (Exception e) {
            System.err.println("Erreur lors de l'initialisation de l'application : " + e.getMessage());
        }
    }
}
