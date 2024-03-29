package lv.venta;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GameOptions {

    public static String currentHeadPosition; // atlasa patreizējo galvas virzienu

    //pause speli
    static void pauseGame(Stage primaryStage) {
        if (Game.pauseStage == null) { // ja pause ekrans jau nepastāv, tad to izveido
            Game.pauseStage = new Stage();
            Game.pauseStage.initModality(Modality.APPLICATION_MODAL); // nevar divas programmas reizē būt atvērtas
            Game.pauseStage.initStyle(StageStyle.UNDECORATED); // noņem visus dekorācijas elementus no loga

            VBox pauseBox = new VBox(20); // izveido background / pogas
            pauseBox.setAlignment(Pos.CENTER);
            pauseBox.setPadding(new Insets(20));
            pauseBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8);");
            PauseMenu.showInitialButtons(pauseBox, primaryStage);
            Scene pauseScene = new Scene(pauseBox, 590, 590);
            Game.pauseStage.setScene(pauseScene);

            Game.gamePaused = true;
            Game.pauseStage.show();
            Game.pauseStage.setOnHidden(event -> Game.gamePaused = false); // atļauj veikt citas funkcijas, kad logs ir aizvērts 
        }
        Game.pauseStage.show(); // parada pause stage
    }

    // Instrukcijas pirms spēles
    static void drawInstructions(GraphicsContext gc) {
        gc.setFill(Color.web("#88b5d1")); // Dark gray background color
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        gc.setFill(Color.web("#ffffff"));
        Font customFont = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 25);
        gc.setFont(customFont);
        gc.fillText("Use AWSD or arrows to move", 60, 250);
        gc.fillText("Press SPACE to go to the next page", 60, 280);
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

    // cuskas galvas custom attēls, mainās atkarībā no kustības virziena
    public static Image headImage() {
        String headFileName = "headUp.png";
        currentHeadPosition = "Up";

        if (Game.direction == Game.direction.up){
            headFileName = "headUp.png";
            currentHeadPosition = "Up";
        }
        else if (Game.direction == Game.direction.left){
            headFileName = "headLeft.png";
            currentHeadPosition = "Left";
        }
        else if (Game.direction == Game.direction.right){
            headFileName = "headRight.png";
            currentHeadPosition = "Right";
        }
        else {
            headFileName = "headDown.png";
            currentHeadPosition = "Down";
        }

        return new Image(Game.class.getResource(headFileName).toExternalForm());
    }
}
