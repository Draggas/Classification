package fr.univlille.iut.sae302;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        SystemeView view = new SystemeView();
        SystemeController controller = new SystemeController(view, primaryStage);
        controller.showHomePage();
    }
}
