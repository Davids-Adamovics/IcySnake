package lv.venta;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GameOptions {

    //pause speli
    static void pauseGame(Stage primaryStage) {
        if (Game.pauseStage == null) { // ja pause ekrans jau nepastāv, tad to izveido
            Game.pauseStage = new Stage();
            Game.pauseStage.initModality(Modality.APPLICATION_MODAL);
            Game.pauseStage.initStyle(StageStyle.UNDECORATED);

            VBox pauseBox = new VBox(20); // izveido background / pogas
            pauseBox.setAlignment(Pos.CENTER);
            pauseBox.setPadding(new Insets(20));
            pauseBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8);");
            Buttons.showInitialButtons(pauseBox, primaryStage);
            Scene pauseScene = new Scene(pauseBox, 300, 300);
            Game.pauseStage.setScene(pauseScene);

            Game.gamePaused = true;
            Game.pauseStage.show();
            Game.pauseStage.setOnHidden(event -> Game.gamePaused = false);
        }
        Game.pauseStage.show(); // parada pause stage
    }

    // Instrukcijas pirms spēles
    static void drawInstructions(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.setFont(new Font("", 20));
        gc.fillText("Use AWSD or arrows to move", 180, 300);
        gc.fillText("Press SPACE to go to the next page", 180, 330);
        return;
    }

    //reset speli
    static void resetGame() {
        Game.speed = 5;
        Game.gameOver = false;
        Game.snake.clear();
        Game.direction = enumDirections.left;

        Game.snake.add(new SnakesBody(10, 10)); // Reset cusku original laukuma
        Game.snake.add(new SnakesBody(10, 10)); // Reset cusku original laukuma
        Game.snake.add(new SnakesBody(10, 10)); // Reset cusku original laukuma
        Game.counter -= Game.counter;
        Game.gameOverSoundPlayed = false;

        Game.musicPlayer = new backgroundMusic(new String[] { "game1.wav", "game4.wav" });
        Game.musicPlayer.BackgroundMusic(new String[] { "game1.wav", "game4.wav" });
    }

    //restart ar r
    public void restart() {
        resetGame();
        Game.gamePaused = false;
        Game.gameOver = false;
        Game.gameOverSoundPlayed = false;
        Game.inGameOverState = false;
        Game.gameStarted = true;
        Game.showInstructions = false;

    }

    // cuskas galvas custom attēls
    public static Image headImage() {
        String headFileName;

        if (PrimaryController.currentPlayer.getgender() == enumGender.male) {
            headFileName = "head.png"; // male galva
        } else {
            headFileName = "head.png"; // female galva
        }
        

        return new Image(Game.class.getResource(headFileName).toExternalForm());
    }
}
