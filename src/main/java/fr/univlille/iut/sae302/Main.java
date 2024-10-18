package fr.univlille.iut.sae302;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Test Border 1");
        Button btnA = new Button("A Border Test");
        HBox root = new HBox();
        root.getChildren().add(btnA);
        root.setPadding(new Insets(30, 50, 30, 50));
        root.setAlignment(Pos.CENTER);
        Border border1 = new Border(
                new BorderStroke(Color.GREEN,
                        BorderStrokeStyle.SOLID,
                        CornerRadii.EMPTY,
                        new BorderWidths(6),
                        new Insets(0) ));
        root.setBorder(border1);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }
}
