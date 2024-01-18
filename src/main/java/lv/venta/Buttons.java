package lv.venta;

import java.util.Arrays;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Buttons {

    public static int color = 0;
    public static int dificulty = 1;

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
                backgroundMusic.PlayButtonSound();
                break;
            case "Restart":
                Game.resetGame();
                Game.gamePaused = false;
                Game.pauseStage.hide();
                backgroundMusic.PlayButtonSound();
                break;
            case "Options":
                Game.options(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                break;
            case "Quit":
                Game.closeAllStages();
                Game.pauseStage.hide();
                backgroundMusic.PlayButtonSound();
                break;
            case "Back":
                showInitialButtons(pauseBox, primaryStage); // parada original pogas
                backgroundMusic.PlayButtonSound();
                break;
            case "Change Skin":
                changeColor(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                break;
            case "Change Map":
                changeMap(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                break;
            case "Blue":
                Game.BackgroundsImage = Game.backgroundImage2;
                showInitialButtons(pauseBox, primaryStage); // restore original buttons
                backgroundMusic.PlayButtonSound();
                color = 0;
                break;

            case "Yellow":
                Game.BackgroundsImage = Game.backgroundImage1;
                showInitialButtons(pauseBox, primaryStage); // parada original pogas
                backgroundMusic.PlayButtonSound();
                color = 1;
                break;
            case "Change Music":
                changeMusic(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                break;
            case "Tropical":
                Game.musicPlayer.BackgroundMusic(new String[] { "game1.wav" });
                showInitialButtons(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                break;
            case "Crazy":
                Game.musicPlayer.BackgroundMusic(new String[] { "gameCrazy.wav" });
                showInitialButtons(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                break;
            case "Gopnik":
                Game.musicPlayer.BackgroundMusic(new String[] { "gameGopnik.wav" });
                showInitialButtons(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                break;
            case "GTA":
                Game.musicPlayer.BackgroundMusic(new String[] { "gameGTA.wav" });
                showInitialButtons(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                break;
            case "Blue Ark":
                Game.musicPlayer.BackgroundMusic(new String[] { "gameHz.wav" });
                showInitialButtons(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                break;
            case "Yeat":
                Game.musicPlayer.BackgroundMusic(new String[] { "gameYeat.wav" });
                showInitialButtons(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                break;
            case "Mario":
                Game.musicPlayer.BackgroundMusic(new String[] { "gameMario.wav" });
                showInitialButtons(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                break;
            case "Pain":
                Game.musicPlayer.BackgroundMusic(new String[] { "gamePain.wav" });
                showInitialButtons(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                break;
            case "Ra":
                Game.musicPlayer.BackgroundMusic(new String[] { "gameRa.wav" });
                showInitialButtons(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                break;
            case "Rave":
                Game.musicPlayer.BackgroundMusic(new String[] { "gameRave.wav" });
                showInitialButtons(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                break;
            case "Uzi":
                Game.musicPlayer.BackgroundMusic(new String[] { "gameUzi.wav" });
                showInitialButtons(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                break;
            case "Leaderboard":
                showLeaderboard(primaryStage, pauseBox);
                backgroundMusic.PlayButtonSound();
                break;
            case "Tutorial":
                showTutorial(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                break;
            case "Achivements":
                showAchivements(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                break;
            case "Dificulty":
                changeDificulty(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                break;
            case "Retro":
                dificulty = 1;
                showInitialButtons(pauseBox, primaryStage); // parada original pogas
                backgroundMusic.PlayButtonSound();
                break;
            case "Power up":
                dificulty = 2;
                showInitialButtons(pauseBox, primaryStage); // parada original pogas
                backgroundMusic.PlayButtonSound();
                break;

        }
    }

    static void showInitialButtons(VBox pauseBox, Stage primaryStage) {
        setPauseBoxBackground(pauseBox, "background1.png");
        pauseBox.getChildren().clear();
        pauseBox.getChildren().addAll(
                createButton("Resume", primaryStage, pauseBox),
                createButton("Restart", primaryStage, pauseBox),
                createButton("Tutorial", primaryStage, pauseBox),
                createButton("Leaderboard", primaryStage, pauseBox),
                createButton("Achivements", primaryStage, pauseBox),
                createButton("Options", primaryStage, pauseBox),
                createButton("Quit", primaryStage, pauseBox));

    }

    static void showLeaderboard(Stage primaryStage, VBox pauseBox) {
        setPauseBoxBackground(pauseBox, "background1.png");
        pauseBox.getChildren().clear();
        Font customFont = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 25);
        Label valueLabel = new Label("Top 5 Leaderboard");
        valueLabel.setFont(customFont);
        valueLabel.setTextFill(Color.web("#ffffff"));
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
        setPauseBoxBackground(pauseBox, "background1.png");
        pauseBox.getChildren().clear(); // izdes esc/pause menu pogas

        // Options pogas
        pauseBox.getChildren().addAll(
                createSlider("", primaryStage, pauseBox),
                createButton("Change Map", primaryStage, pauseBox),
                createButton("Change Music", primaryStage, pauseBox),
                createButton("Change Skin", primaryStage, pauseBox),
                createButton("Dificulty", primaryStage, pauseBox),
                createButton("Back", primaryStage, pauseBox));
    }

    // volume slider
    static Slider createSlider(String text, Stage primaryStage, VBox pauseBox) {
        Slider slider = new Slider(0, 100, 90);
        slider.setMaxWidth(150);

        Label valueLabel = new Label("Volume: " + (int) slider.getValue() + "%");
        Font customFont = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 25);
        valueLabel.setFont(customFont);
        valueLabel.setTextFill(Color.web("#ffffff"));
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
        setPauseBoxBackground(pauseBox, "background1.png");
        pauseBox.getChildren().clear();

        Font customFont = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 25);
        Label valueLabel = new Label("Snakes Color");
        valueLabel.setFont(customFont);
        valueLabel.setTextFill(Color.web("#ffffff"));
        valueLabel.setAlignment(Pos.CENTER);

        pauseBox.getChildren().addAll(
                valueLabel,
                createColorPicker("", primaryStage, pauseBox),
                createButton("Back", primaryStage, pauseBox));
    }

    static void changeMap(VBox pauseBox, Stage primaryStage) {
        setPauseBoxBackground(pauseBox, "background1.png");
        pauseBox.getChildren().clear();

        Font customFont = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 25);
        Label valueLabel = new Label("Change the map");
        valueLabel.setFont(customFont);
        valueLabel.setTextFill(Color.web("#ffffff"));
        valueLabel.setAlignment(Pos.CENTER);

        pauseBox.getChildren().addAll(
                valueLabel,
                createButton("Blue", primaryStage, pauseBox),
                createButton("Yellow", primaryStage, pauseBox),
                createButton("Back", primaryStage, pauseBox));

    }

    static void changeMusic(VBox pauseBox, Stage primaryStage) {
        setPauseBoxBackground(pauseBox, "background1.png");
        pauseBox.getChildren().clear();

        Font customFont = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 35);
        Label valueLabel = new Label("Background Music");
        valueLabel.setFont(customFont);
        valueLabel.setTextFill(Color.web("#ffffff"));
        valueLabel.setAlignment(Pos.CENTER);

        List<Button> leftButtons = Arrays.asList(
                createButton("Crazy", primaryStage, pauseBox),
                createButton("Gopnik", primaryStage, pauseBox),
                createButton("GTA", primaryStage, pauseBox),
                createButton("Blue Ark", primaryStage, pauseBox),
                createButton("Yeat", primaryStage, pauseBox));

        List<Button> rightButtons = Arrays.asList(
                createButton("Mario", primaryStage, pauseBox),
                createButton("Pain", primaryStage, pauseBox),
                createButton("Ra", primaryStage, pauseBox),
                createButton("Rave", primaryStage, pauseBox),
                createButton("Uzi", primaryStage, pauseBox),
                createButton("Tropical", primaryStage, pauseBox));

        HBox leftButtonBox = new HBox(10, leftButtons.toArray(new Button[0]));
        HBox rightButtonBox = new HBox(10, rightButtons.toArray(new Button[0]));

        leftButtonBox.setAlignment(Pos.CENTER);
        rightButtonBox.setAlignment(Pos.CENTER);

        VBox backButtonBox = new VBox(10, createButton("Back", primaryStage, pauseBox));
        backButtonBox.setAlignment(Pos.CENTER); // Center the "Back" button horizontally

        pauseBox.getChildren().addAll(
                valueLabel,
                leftButtonBox,
                rightButtonBox,
                backButtonBox);
    }

    static void showTutorial(VBox pauseBox, Stage primaryStage) {
        setPauseBoxBackground(pauseBox, "background1.png");
        pauseBox.getChildren().clear();

        Font customFont = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 35);
        Font customFont1 = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 10);
        Label valueLabel = new Label("Info panel");
        valueLabel.setFont(customFont);
        valueLabel.setTextFill(Color.web("#ffffff"));
        valueLabel.setAlignment(Pos.CENTER);

        Label powerUpInfo = new Label("Bomb - Mixes the fruits for 5s while slowing the speed by -3\n" +
                "+5 - Adds +5 points to the counter\n" +
                "Star - removes -2 points from the counter\n" +
                "Fruit - adds +1 to the counter and +1 speed\n" +
                "Barrier - Ends the game");
        powerUpInfo.setFont(customFont1);

        pauseBox.getChildren().addAll(
                valueLabel,
                powerUpInfo,

                createButton("Back", primaryStage, pauseBox));
    }

    static void showAchivements(VBox pauseBox, Stage primaryStage) {
        setPauseBoxBackground(pauseBox, "background1.png");
        pauseBox.getChildren().clear();

        Font customFont = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 40);
        Font customFont1 = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 20);
        Label valueLabel = new Label("Achivements");
        valueLabel.setFont(customFont);
        valueLabel.setTextFill(Color.web("#ffffff"));
        valueLabel.setAlignment(Pos.CENTER);

        ImageView i1 = new ImageView(Game.Achivement1);
        ImageView i2 = new ImageView(Game.Achivement2); // Add this line
        ImageView i3 = new ImageView(Game.Achivement3); // Add this line

        i1.setFitWidth(64);
        i1.setFitHeight(64);
        i1.setTranslateX(-180);
        i1.setTranslateY(-120);
        i1.setOpacity(0.3);
        if (Game.counter > 100) {
            i1.setOpacity(1);
        }

        i2.setFitWidth(64);
        i2.setFitHeight(64);
        i2.setTranslateX(-180);
        i2.setTranslateY(-70);
        i2.setOpacity(0.3);
        if (Food.barriersDodgedCounter > 20) {
            i2.setOpacity(1);
        }

        i3.setFitWidth(64);
        i3.setFitHeight(64);
        i3.setTranslateX(-180);
        i3.setTranslateY(-20);
        i3.setOpacity(0.3);
        if (Game.bombsClaimedCounter > 10) {
            i3.setOpacity(1);
        }

        Label Achivement1 = new Label("Reach 100 points");
        Label Achivement2 = new Label("Dodge 20 Barriers");
        Label Achivement3 = new Label("Claim 10 Bombs");

        Achivement1.setTranslateX(0);
        Achivement1.setTranslateY(20);
        Achivement2.setTranslateX(0);
        Achivement2.setTranslateY(120);
        Achivement3.setTranslateX(50);
        Achivement3.setTranslateY(220);
        Achivement1.setFont(customFont1);
        Achivement2.setFont(customFont1);
        Achivement3.setFont(customFont1);

        pauseBox.getChildren().addAll(
                valueLabel,
                Achivement1,
                Achivement2,
                Achivement3,
                i1,
                i2,
                i3,
                createButton("Back", primaryStage, pauseBox));
    }

    static void changeDificulty(VBox pauseBox, Stage primaryStage) {
        setPauseBoxBackground(pauseBox, "background1.png");
        pauseBox.getChildren().clear();

        pauseBox.getChildren().addAll(
                createButton("Retro", primaryStage, pauseBox),
                createButton("Power up", primaryStage, pauseBox),
                createButton("Back", primaryStage, pauseBox));
    }

    // Helper method to set background image for VBox
    private static void setPauseBoxBackground(VBox pauseBox, String backgroundImage) {
        String imageUrl = Buttons.class.getResource(backgroundImage).toExternalForm();
        pauseBox.setStyle("-fx-background-image: url('" + imageUrl + "'); " +
                "-fx-background-size: cover;");
    }

}
