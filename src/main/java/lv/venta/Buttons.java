package lv.venta;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Buttons {

    static Button createButton(String text, Stage primaryStage, VBox pauseBox) {
        Button button = new Button(text);
        button.setStyle(
                "-fx-background-color: linear-gradient(#aeefff, #87cefa), #ffffff; " +
                        "-fx-background-insets: 0, 0 0 1 0; " +
                        "-fx-background-radius: 5; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-size: 1.5em; " +
                        "-fx-text-fill: #4682b4; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.25), 4, 0, 0, 1);");

        button.setOnAction(e -> Game.handleButtonAction(text, primaryStage, pauseBox));
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
            case "Change Skin":
                changeColor(pauseBox, primaryStage);
                break;
            case "Change Map":
                changeMap(pauseBox, primaryStage);
                break;
            case "Blue":
                Game.BackgroundsImage = Game.backgroundImage2;
                showInitialButtons(pauseBox, primaryStage); // parada original pogas
                break;
            case "Yellow":
                Game.BackgroundsImage = Game.backgroundImage1;
                showInitialButtons(pauseBox, primaryStage); // parada original pogas
                break;
            case "Change Music":
                changeMusic(pauseBox, primaryStage);
                break;
            case "Music one":
                Game.musicPlayer.BackgroundMusic(new String[] { "game1.wav" }); // play game 1
                showInitialButtons(pauseBox, primaryStage); // parada original pogas
                break;
            case "Music two":
                Game.musicPlayer.BackgroundMusic(new String[] { "game4.wav" }); // play game 4
                showInitialButtons(pauseBox, primaryStage); // parada original pogas
                break;
            case "Music three":
                Game.musicPlayer.BackgroundMusic(new String[] { "game2.wav" }); // play game 2
                showInitialButtons(pauseBox, primaryStage); // parada original pogas
                break;
            case "Leaderboard":
                showLeaderboard(primaryStage, pauseBox);
                break;
        }
    }

    static void showInitialButtons(VBox pauseBox, Stage primaryStage) {
        pauseBox.getChildren().clear();
        pauseBox.getChildren().addAll(
                createButton("Resume", primaryStage, pauseBox),
                createButton("Restart", primaryStage, pauseBox),
                createButton("Options", primaryStage, pauseBox),
                createButton("Leaderboard", primaryStage, pauseBox),
                createButton("Quit", primaryStage, pauseBox));
    }

    static void showLeaderboard(Stage primaryStage, VBox pauseBox) {
        pauseBox.getChildren().clear();
        Font customFont = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 25);
        Label valueLabel = new Label("Top 5 Leaderboard");
        valueLabel.setFont(customFont);
        valueLabel.setTextFill(Color.web("#4682b4"));
        valueLabel.setAlignment(Pos.CENTER);

        Label place1 = new Label("\nBest score: " + Game.highScore);
        place1.setFont(customFont);
        place1.setTextFill(Color.web("#4682b4"));
        place1.setAlignment(Pos.CENTER);

        Label place2 = new Label("Last attempt: " + Game.counter);
        place2.setFont(customFont);
        place2.setTextFill(Color.web("#4682b4"));
        place2.setAlignment(Pos.CENTER);

        pauseBox.getChildren().addAll(
                valueLabel,
                place1,
                place2,
                createButton("Back", primaryStage, pauseBox));
    }

    static void showNewButtons(VBox pauseBox, Stage primaryStage) {
        pauseBox.getChildren().clear(); // izdes esc/pause menu pogas

        // Options pogas
        pauseBox.getChildren().addAll(
                createSlider("", primaryStage, pauseBox),
                createButton("Change Map", primaryStage, pauseBox),
                createButton("Change Music", primaryStage, pauseBox),
                createButton("Change Skin", primaryStage, pauseBox),
                createButton("Back", primaryStage, pauseBox));
    }

    // volume slider
    static Slider createSlider(String text, Stage primaryStage, VBox pauseBox) {
        Slider slider = new Slider(0, 100, 90);
        slider.setMaxWidth(150);

        Label valueLabel = new Label("Volume: " + (int) slider.getValue() + "%");
        Font customFont = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 25);
        valueLabel.setFont(customFont);
        valueLabel.setTextFill(Color.web("#4682b4"));
        valueLabel.setAlignment(Pos.CENTER);

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double newVolume = newValue.doubleValue() / 100;
            valueLabel.setText("Volume: " + (int) (newVolume * 100) + "%");
            backgroundMusic.volume = newVolume;
            backgroundMusic.updateVolume();
        });

        VBox sliderVBox = new VBox(slider, valueLabel);
        sliderVBox.setAlignment(Pos.CENTER);
        VBox.setMargin(sliderVBox, new Insets(10, 0, 10, 0));

        pauseBox.getChildren().add(sliderVBox);

        return slider;
    }

    static ColorPicker createColorPicker(String text, Stage primaryStage, VBox pauseBox) {
        ColorPicker colorpicker = new ColorPicker(Game.snakeColor);
        colorpicker.setOnAction(e -> Game.snakeColor = colorpicker.getValue());
        return colorpicker;
    }

    static void changeColor(VBox pauseBox, Stage primaryStage) {
        pauseBox.getChildren().clear();

        Font customFont = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 25);
        Label valueLabel = new Label("Snakes Color");
        valueLabel.setFont(customFont);
        valueLabel.setTextFill(Color.web("#4682b4"));
        valueLabel.setAlignment(Pos.CENTER);

        pauseBox.getChildren().addAll(
                valueLabel,
                createColorPicker("", primaryStage, pauseBox),
                createButton("Back", primaryStage, pauseBox));
    }

    static void changeMap(VBox pauseBox, Stage primaryStage) {
        pauseBox.getChildren().clear();

        Font customFont = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 25);
        Label valueLabel = new Label("Change the map");
        valueLabel.setFont(customFont);
        valueLabel.setTextFill(Color.web("#4682b4"));
        valueLabel.setAlignment(Pos.CENTER);

        pauseBox.getChildren().addAll(
                valueLabel,
                createButton("Blue", primaryStage, pauseBox),
                createButton("Yellow", primaryStage, pauseBox),
                createButton("Back", primaryStage, pauseBox));

    }

    static void changeMusic(VBox pauseBox, Stage primaryStage) {
        pauseBox.getChildren().clear();

        Font customFont = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 25);
        Label valueLabel = new Label("Background Music");
        valueLabel.setFont(customFont);
        valueLabel.setTextFill(Color.web("#4682b4"));
        valueLabel.setAlignment(Pos.CENTER);

        pauseBox.getChildren().addAll(
                valueLabel,
                createButton("Music one", primaryStage, pauseBox),
                createButton("Music two", primaryStage, pauseBox),
                createButton("Music three", primaryStage, pauseBox),
                createButton("Back", primaryStage, pauseBox));

    }
}
