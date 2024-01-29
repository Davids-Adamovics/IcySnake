package lv.venta;

import java.util.Arrays;
import java.util.List;

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

public class OptionsMenu {

    public static String currentMap;
    public static String currentSkin;
    public static double currentMusicVolume;

    /*
     * ==============================================================
     * ==================== OPTIONS POGAS ===========================
     * ==============================================================
     */
    static void showNewButtons(VBox pauseBox, Stage primaryStage) {
        Buttons.setPauseBoxBackground(pauseBox, "background1.png");
        pauseBox.getChildren().clear(); // izdes esc/pause menu pogas

        // Options pogas
        pauseBox.getChildren().addAll(
                createSlider("", primaryStage, pauseBox),
                Buttons.createButton("Change Map", primaryStage, pauseBox),
                Buttons.createButton("Change Music", primaryStage, pauseBox),
                Buttons.createButton("Change Skin", primaryStage, pauseBox),
                Buttons.createButton("Dificulty", primaryStage, pauseBox),
                Buttons.createButton("Back", primaryStage, pauseBox));
    }

    /*
     * ==============================================================
     * ==================== VOLUME SLIDER ===========================
     * ==============================================================
     */
    // volume slider
    static Slider createSlider(String text, Stage primaryStage, VBox pauseBox) {
        Slider slider = new Slider(0, 100, 50);
        slider.setMaxWidth(150);
        Label valueLabel;
        if ((int) slider.getValue() == currentMusicVolume) {
            valueLabel = new Label("Volume: " + (int) slider.getValue() + "%");
        }
        else if(currentMusicVolume > 0.1){
            valueLabel = new Label("Volume: " + currentMusicVolume + "%");
        }
        else{
            valueLabel = new Label("Volume: 50 %");
        }

        Font customFont = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 25);
        valueLabel.setFont(customFont);
        valueLabel.setTextFill(Color.web("#ffffff"));
        valueLabel.setAlignment(Pos.CENTER);

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double newVolume = newValue.doubleValue() / 100;
            valueLabel.setText("Volume: " + (int) (newVolume * 100) + "%");
            currentMusicVolume = Math.round(newVolume * 100);
            backgroundMusic.volume = newVolume;
            backgroundMusic.updateVolume();
        });

        VBox sliderVBox = new VBox(slider, valueLabel);
        sliderVBox.setAlignment(Pos.CENTER);
        VBox.setMargin(sliderVBox, new Insets(10, 0, 10, 0));

        pauseBox.getChildren().add(sliderVBox);

        return slider;
    }

    /*
     * ==============================================================
     * ==================== CHANGE MAP =============================
     * ==============================================================
     */
    static void changeMap(VBox pauseBox, Stage primaryStage) {
        Buttons.setPauseBoxBackground(pauseBox, "background1.png");
        pauseBox.getChildren().clear();

        Font customFont = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 25);
        Label valueLabel = new Label("Change the map");
        valueLabel.setFont(customFont);
        valueLabel.setTextFill(Color.web("#ffffff"));
        valueLabel.setAlignment(Pos.CENTER);

        pauseBox.getChildren().addAll(
                valueLabel,
                Buttons.createButton("Blue", primaryStage, pauseBox),
                Buttons.createButton("Yellow", primaryStage, pauseBox),
                Buttons.createButton("Back", primaryStage, pauseBox));

    }

    /*
     * ==============================================================
     * ==================== CHANGE MUSIC ============================
     * ==============================================================
     */
    static void changeMusic(VBox pauseBox, Stage primaryStage) {
        Buttons.setPauseBoxBackground(pauseBox, "background1.png");
        pauseBox.getChildren().clear();

        Font customFont = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 35);
        Label valueLabel = new Label("Background Music");
        valueLabel.setFont(customFont);
        valueLabel.setTextFill(Color.web("#ffffff"));
        valueLabel.setAlignment(Pos.CENTER);

        List<Button> leftButtons = Arrays.asList(
                Buttons.createButton("Crazy", primaryStage, pauseBox),
                Buttons.createButton("Gopnik", primaryStage, pauseBox),
                Buttons.createButton("GTA", primaryStage, pauseBox),
                Buttons.createButton("MidNight", primaryStage, pauseBox),
                Buttons.createButton("BeastMode", primaryStage, pauseBox));

        List<Button> rightButtons = Arrays.asList(
                Buttons.createButton("Mario", primaryStage, pauseBox),
                Buttons.createButton("WaterFall", primaryStage, pauseBox),
                Buttons.createButton("Ra", primaryStage, pauseBox),
                Buttons.createButton("Rave", primaryStage, pauseBox),
                Buttons.createButton("BlastOff", primaryStage, pauseBox),
                Buttons.createButton("Tropical", primaryStage, pauseBox));

        HBox leftButtonBox = new HBox(10, leftButtons.toArray(new Button[0]));
        HBox rightButtonBox = new HBox(10, rightButtons.toArray(new Button[0]));

        leftButtonBox.setAlignment(Pos.CENTER);
        rightButtonBox.setAlignment(Pos.CENTER);

        VBox backButtonBox = new VBox(10, Buttons.createButton("Back", primaryStage, pauseBox));
        backButtonBox.setAlignment(Pos.CENTER); // nocentree BACK pogu horizontaali

        pauseBox.getChildren().addAll(
                valueLabel,
                leftButtonBox,
                rightButtonBox,
                backButtonBox);
    }

    /*
     * ==============================================================
     * ==================== KRĀSU IZVĒLE ============================
     * ==============================================================
     */
    static ColorPicker createColorPicker(String text, Stage primaryStage, VBox pauseBox) {
        ColorPicker colorpicker = new ColorPicker(Game.snakeColor);
        colorpicker.setOnAction(e -> Game.snakeColor = colorpicker.getValue());
        currentSkin = colorpicker.getValue().toString();
        return colorpicker;
    }

    /*
     * ==============================================================
     * ==================== NOMAINA KRĀSU ===========================
     * ==============================================================
     */
    static void changeColor(VBox pauseBox, Stage primaryStage) {
        Buttons.setPauseBoxBackground(pauseBox, "background1.png");
        pauseBox.getChildren().clear();

        Font customFont = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 25);
        Label valueLabel = new Label("Snakes Color");
        valueLabel.setFont(customFont);
        valueLabel.setTextFill(Color.web("#ffffff"));
        valueLabel.setAlignment(Pos.CENTER);

        pauseBox.getChildren().addAll(
                valueLabel,
                createColorPicker("", primaryStage, pauseBox),
                Buttons.createButton("Back", primaryStage, pauseBox));
    }

    /*
     * ==============================================================
     * ==================== DIFICULTY =============================
     * ==============================================================
     */
    static void changeDificulty(VBox pauseBox, Stage primaryStage) {
        Buttons.setPauseBoxBackground(pauseBox, "background1.png");
        pauseBox.getChildren().clear();

        pauseBox.getChildren().addAll(
                Buttons.createButton("Retro", primaryStage, pauseBox),
                Buttons.createButton("Power up", primaryStage, pauseBox),
                Buttons.createButton("Back", primaryStage, pauseBox));
    }
}
