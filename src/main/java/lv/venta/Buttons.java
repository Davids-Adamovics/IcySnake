package lv.venta;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Buttons {

    static Button createButton(String text, Stage primaryStage, VBox pauseBox) { // pogas un to dizains
        Button button = new Button(text);
        button.setStyle(
                "-fx-background-color: linear-gradient(#aeefff, #87cefa), #ffffff; " + 
                        "-fx-background-insets: 0, 0 0 1 0; " + 
                        "-fx-background-radius: 5; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-size: 1.5em; " +
                        "-fx-text-fill: #4682b4; " + 
                        "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.25), 4, 0, 0, 1);" 
        );

        button.setOnAction(e -> Game.handleButtonAction(text, primaryStage, pauseBox)); // pogas pilda darbibu atkariba
                                                                                        // no
        // teksta
        return button;
    }

    static void handleButtonAction(String action, Stage primaryStage, VBox pauseBox) { // pogu nosaukumi un
        // funkciju izsauksana
        switch (action) {
            case "Resume":
                Game.gamePaused = false;
                Game.pauseStage.hide();
                break;
            case "Restart":
                Game.resetGame();
                Game.gamePaused = false;
                Game.pauseStage.hide();

                break;
            case "Options":
                Game.options(pauseBox, primaryStage);
                break;
            case "Quit":
                Game.closeAllStages();
                Game.pauseStage.hide();
                break;
            case "Back":
                showInitialButtons(pauseBox, primaryStage); // parada original pogas

                break;
        }
    }

    static void showInitialButtons(VBox pauseBox, Stage primaryStage) {
        pauseBox.getChildren().clear();
        pauseBox.getChildren().addAll(
                Game.createButton("Resume", primaryStage, pauseBox),
                Game.createButton("Restart", primaryStage, pauseBox),
                Game.createButton("Options", primaryStage, pauseBox),
                Game.createButton("Quit", primaryStage, pauseBox));
    }

    static void showNewButtons(VBox pauseBox, Stage primaryStage) {
        pauseBox.getChildren().clear(); // izdzes jaunas pogas
        pauseBox.getChildren().addAll( // izveido jaunas pogas
                Game.createButton("Back", primaryStage, pauseBox),
                Game.createButton("NewOption1", primaryStage, pauseBox),
                Game.createButton("NewOption2", primaryStage, pauseBox),
                Game.createButton("NewOption3", primaryStage, pauseBox));
    }
}
