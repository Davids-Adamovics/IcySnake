package lv.venta;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
                        "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.25), 4, 0, 0, 1);");

        button.setOnAction(e -> Game.handleButtonAction(text, primaryStage, pauseBox)); // pogas pilda darbibu atkariba
                                                                                        // no
        // teksta
        return button;
    }

    static Slider createSlider(String text, Stage primaryStage, VBox pauseBox) {
        Slider slider = new Slider();
        slider.setValue(90);
        slider.setMaxWidth(150); // Set the maximum width of the slider
    
        // Create a label to display the slider value
        Font customFont = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 25);
        Label valueLabel = new Label("Volume: " + (int) slider.getValue() + "%");
        valueLabel.setFont(customFont);
        valueLabel.setTextFill(Color.web("#4682b4"));
        valueLabel.setAlignment(Pos.CENTER);
    
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double newVolume = newValue.doubleValue() / 100; // Assuming the volume ranges from 0 to 1
            valueLabel.setText("Volume: " + (int) (newVolume * 100) + "%");
            backgroundMusic.volume = newVolume; // Directly set the volume property
            backgroundMusic.updateVolume(); // Apply the volume change immediately
        });
        
        
        
        
    
        VBox sliderVBox = new VBox(slider, valueLabel);
        sliderVBox.setAlignment(Pos.CENTER); // Center the contents in the VBox
    
        VBox.setMargin(sliderVBox, new Insets(10, 0, 10, 0));
    
        pauseBox.getChildren().add(sliderVBox);
    
        return slider;
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
                createButton("Resume", primaryStage, pauseBox),
                createButton("Restart", primaryStage, pauseBox),
                createButton("Options", primaryStage, pauseBox),
                createButton("Quit", primaryStage, pauseBox));
    }

    static void showNewButtons(VBox pauseBox, Stage primaryStage) {
        pauseBox.getChildren().clear(); // Clear existing elements

        // Add new elements with proper formatting
        pauseBox.getChildren().addAll(
            createSlider("",primaryStage, pauseBox),
            createButton("Change Map", primaryStage, pauseBox),
            createButton("Change Music", primaryStage, pauseBox),
            createButton("Change Skin", primaryStage, pauseBox),
            createButton("Back", primaryStage, pauseBox));
    }

    
}
