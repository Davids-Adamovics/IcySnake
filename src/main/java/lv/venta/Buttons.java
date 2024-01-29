package lv.venta;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Buttons {

    public static int color = 0; // lai noteiktu kāda mape
    public static int dificulty = 1; // lai noteiktu dificulty
    public static String musicChoice = ""; // lai nomainītu music


     /*
     * ==============================================================
     * ==================== IZVEIDO POGAS =============================
     * ==============================================================
     */
    static Button createButton(String text, Stage primaryStage, VBox pauseBox) {
        Button button = new Button(text); // izveido button
        button.setStyle(
                "-fx-background-color: linear-gradient(#aeefff, #87cefa), #ffffff; " +
                        "-fx-background-insets: 0, 0 0 1 0; " +
                        "-fx-background-radius: 5; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-size: 1.5em; " +
                        "-fx-text-fill: #4682b4; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.25), 4, 0, 0, 1);"); // button style ar CSS

        button.setOnAction(e -> Game.handleButtonAction(text, primaryStage, pauseBox)); // lai poga veiktu noteiktu darbību
        return button; // atgriež pogu
    }

    /*
     * ==============================================================
     * ==================== POGU DARBĪBAS =============================
     * ==============================================================
     */
    static void handleButtonAction(String action, Stage primaryStage, VBox pauseBox) { // pogu nosaukumi un
        // funkciju izsauksana
        switch (action) {
            // atgriežas spēlē
            case "Resume":
                Game.gamePaused = false;
                Game.pauseStage.hide();
                backgroundMusic.PlayButtonSound();
                break;
            // restartē spēli
            case "Restart":
                Game.resetGame();
                Game.gamePaused = false;
                Game.pauseStage.hide();
                backgroundMusic.PlayButtonSound();
                break;
            // Atver options lapu
            case "Options":
                Game.options(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                break;
            // aizver spēli
            case "Quit":
                Game.closeAllStages();
                Game.pauseStage.hide();
                backgroundMusic.PlayButtonSound();
                break;
            // Aizver pašreizējo logu un atver pause menu pogas
            case "Back":
                PauseMenu.showInitialButtons(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                break;
            // atver change skin lapu
            case "Change Skin":
                OptionsMenu.changeColor(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                break;
            // atver change map lapu
            case "Change Map":
            OptionsMenu.changeMap(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                break;
            // Nomaina spēles mapi uz zilu un atver options menu lapu
            case "Blue":
                Game.BackgroundsImage = Game.backgroundImage2;
                Game.options(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                OptionsMenu.currentMap = "Blue";
                color = 0;
                break;
            // Nomaina spēles mapi uz dzeltenu un atver options menu lapu
            case "Yellow":
                Game.BackgroundsImage = Game.backgroundImage1;
                Game.options(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                OptionsMenu.currentMap = "Yellow";
                color = 1;
                break;
            // Atver change music lapu
            case "Change Music":
                OptionsMenu.changeMusic(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                break;
            // Nomaina mūziku un atver options pogas
            case "Tropical":
                Game.musicPlayer.BackgroundMusic(new String[] { "game1.wav" });
                Game.options(pauseBox, primaryStage); // atver options
                backgroundMusic.PlayButtonSound();
                musicChoice = "game1.wav";
                break;
            case "Crazy":
                Game.musicPlayer.BackgroundMusic(new String[] { "gameCrazy.wav" });
                Game.options(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                musicChoice = "gameCrazy.wav";
                break;
            case "Gopnik":
                Game.musicPlayer.BackgroundMusic(new String[] { "gameGopnik.wav" });
                Game.options(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                musicChoice = "gameGopnik.wav";
                break;
            case "GTA":
                Game.musicPlayer.BackgroundMusic(new String[] { "gameGTA.wav" });
                Game.options(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                musicChoice = "gameGTA.wav";
                break;
            case "MidNight":
                Game.musicPlayer.BackgroundMusic(new String[] { "gameHz.wav" });
                Game.options(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                musicChoice = "gameHz.wav";
                break;
            case "BeastMode":
                Game.musicPlayer.BackgroundMusic(new String[] { "gameYeat.wav" });
                Game.options(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                musicChoice = "gameYeat.wav";
                break;
            case "Mario":
                Game.musicPlayer.BackgroundMusic(new String[] { "gameMario.wav" });
                Game.options(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                musicChoice = "gameMario.wav";
                break;
            case "WaterFall":
                Game.musicPlayer.BackgroundMusic(new String[] { "gamePain.wav" });
                Game.options(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                musicChoice = "gamePain.wav";
                break;
            case "Ra":
                Game.musicPlayer.BackgroundMusic(new String[] { "gameRa.wav" });
                Game.options(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                musicChoice = "gameRa.wav";
                break;
            case "Rave":
                Game.musicPlayer.BackgroundMusic(new String[] { "gameRave.wav" });
                Game.options(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                musicChoice = "gameRave.wav";
                break;
            case "BlastOff":
                Game.musicPlayer.BackgroundMusic(new String[] { "gameUzi.wav" });
                Game.options(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                musicChoice = "gameUzi.wav";
                break;
            // atver leaderboard lapu
            case "Leaderboard":
                PauseMenu.showLeaderboard(primaryStage, pauseBox);
                backgroundMusic.PlayButtonSound();
                break;
            // atver info lapu
            case "Info":
                PauseMenu.showTutorial(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                break;
            // atver achivements lapu
            case "Achievements":
                PauseMenu.showAchivements(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                break;
            // atver dificulty lapu
            case "Dificulty":
            OptionsMenu.changeDificulty(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                break;
            // nomaina grūtības līmeni uz retro
            case "Retro":
                dificulty = 1;
                PauseMenu.showInitialButtons(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                break;
            // nomaina grūtības līmeni uz power up
            case "Power up":
                dificulty = 2;
                PauseMenu.showInitialButtons(pauseBox, primaryStage); // parada original pogas
                backgroundMusic.PlayButtonSound();
                break;
        }

        
    }
    /*
     * ==============================================================
     * ==================== BACKGROUND UN TEXT SETTINGS =============
     * ==============================================================
     */
    static void setPauseBoxBackground(VBox pauseBox, String backgroundImage) { // background visiem settings
        String bildesURL = PauseMenu.class.getResource(backgroundImage).toExternalForm(); // saņem background image nosaukumu kā string un pārvērš kā URL
        pauseBox.setStyle("-fx-background-image: url('" + bildesURL + "'); " + // uzliek background kā url
                "-fx-background-size: cover;");
    }

    static Label textSettings(String text, Font font, int strokeWidth) {
        Label label = new Label(text); // jauns teksts
        label.setFont(font); // uzliek fontu
        label.setTextFill(Color.web("#ffffff")); // nomaina krāsu
        label.setStyle("-fx-stroke: black; -fx-stroke-width: " + strokeWidth + "px;"); // css stils
        label.setAlignment(Pos.CENTER); // pozīcija
        return label;
    }
}
