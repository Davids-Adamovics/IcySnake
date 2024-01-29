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

public class PauseMenu {

    public static int color = 0;
    public static int dificulty = 1;
    public static String musicChoice = "";
    static byte achivementCounter = 0;


   

    /*
     * ==============================================================
     * ==================== PAUSE MENU POGAS ========================
     * ==============================================================
     */
    static void showInitialButtons(VBox pauseBox, Stage primaryStage) {
        Buttons.setPauseBoxBackground(pauseBox, "background1.png");
        pauseBox.getChildren().clear();
        pauseBox.getChildren().addAll(
                Buttons.createButton("Resume", primaryStage, pauseBox),
                Buttons.createButton("Restart", primaryStage, pauseBox),
                Buttons.createButton("Info", primaryStage, pauseBox),
                Buttons.createButton("Leaderboard", primaryStage, pauseBox),
                Buttons.createButton("Achievements", primaryStage, pauseBox),
                Buttons.createButton("Options", primaryStage, pauseBox),
                Buttons.createButton("Quit", primaryStage, pauseBox));

    }
    /*
     * ==============================================================
     * ==================== INFO PANELIS =============================
     * ==============================================================
     */
    static void showTutorial(VBox pauseBox, Stage primaryStage) {
        Buttons.setPauseBoxBackground(pauseBox, "background1.png");
        pauseBox.getChildren().clear();

        Font virsraksts = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 40);
        Font teksts = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 20);

        Label valueLabel = new Label("Info panel");
        valueLabel.setFont(virsraksts);
        valueLabel.setTextFill(Color.web("#ffffff"));
        valueLabel.setAlignment(Pos.CENTER);

        ImageView apple = new ImageView(Game.augli);
        ImageView bomba = new ImageView(Game.bomb);
        ImageView pluss5 = new ImageView(Game.plus5);
        ImageView star = new ImageView(Game.powerup);
        ImageView barrier = new ImageView(Game.barjera);

        apple.setFitWidth(200);
        apple.setFitHeight(90);
        bomba.setFitWidth(40);
        bomba.setFitHeight(40);
        pluss5.setFitWidth(40);
        pluss5.setFitHeight(40);
        star.setFitWidth(40);
        star.setFitHeight(40);
        barrier.setFitWidth(40);
        barrier.setFitHeight(40);

        Label augli = new Label("Adds +1 point to the counter	" + '\n' +
                "Adds +1 speed to the Snake	");

        HBox hBoxInfoPanel = new HBox(valueLabel);
        hBoxInfoPanel.setAlignment(Pos.CENTER);

        HBox hBoxAbols = new HBox(apple, augli);
        hBoxAbols.setAlignment(Pos.CENTER_RIGHT);
        hBoxAbols.setSpacing(30);

        Label bomb = new Label("Mixes the fruits for 5s" + '\n' +
                "While slowing the speed by -3");
        HBox hBoxBomba = new HBox(bomba, bomb);
        hBoxBomba.setAlignment(Pos.CENTER_RIGHT);
        hBoxBomba.setSpacing(50);

        Label plus5 = new Label(" Adds +5 points to the counter");
        HBox hBoxPluss5 = new HBox(pluss5, plus5);
        hBoxPluss5.setAlignment(Pos.CENTER_RIGHT);
        hBoxPluss5.setSpacing(35);

        Label powerup = new Label(" Removes -2 points ");
        HBox hBoxStar = new HBox(star, powerup);
        hBoxStar.setAlignment(Pos.CENTER);
        hBoxStar.setSpacing(40);

        Label barjera = new Label("Ends the game	");
        HBox hBoxBarrier = new HBox(barrier, barjera);
        hBoxBarrier.setAlignment(Pos.CENTER);
        hBoxBarrier.setSpacing(50);

        augli.setFont(teksts);
        bomb.setFont(teksts);
        plus5.setFont(teksts);
        powerup.setFont(teksts);
        barjera.setFont(teksts);

        HBox hBoxBack = new HBox(Buttons.createButton("Back", primaryStage, pauseBox));
        hBoxBack.setAlignment(Pos.CENTER);

        VBox vBoxAll = new VBox(hBoxInfoPanel, hBoxAbols, hBoxBomba, hBoxPluss5, hBoxStar, hBoxBarrier, hBoxBack);
        vBoxAll.setSpacing(30);
        pauseBox.getChildren().addAll(vBoxAll);

    }

    /*
     * ==============================================================
     * ==================== LEADERBOARD =============================
     * ==============================================================
     */
    static void showLeaderboard(Stage primaryStage, VBox pauseBox) {
        Buttons.setPauseBoxBackground(pauseBox, "background1.png");

        pauseBox.getChildren().clear();
        Font customFont = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 25);
        Label valueLabel = new Label("Last Attempts");
        valueLabel.setFont(customFont);
        valueLabel.setTextFill(Color.web("#ffffff"));
        valueLabel.setAlignment(Pos.CENTER);

        if (Game.secondBest == 0) {
            Game.secondBest = Game.newscore;
        } else if (Game.secondBest != 0 && Game.thirdBest == 0) {
            Game.thirdBest = Game.newscore;
        } else if (Game.secondBest != 0 && Game.thirdBest != 0 && Game.fourthBest == 0) {
            Game.fourthBest = Game.newscore;
        } else if (Game.secondBest != 0 && Game.thirdBest != 0 && Game.fourthBest != 0 && Game.fifthBest == 0) {
            Game.fifthBest = Game.newscore;
        }

        else if (Game.secondBest != 0 && Game.thirdBest != 0 && Game.fourthBest != 0 && Game.fifthBest != 0) {
            Game.fifthBest = Game.fourthBest;
            Game.fourthBest = Game.thirdBest;
            Game.thirdBest = Game.secondBest;
            Game.secondBest = Game.newscore;
        }

        Label place1 = Buttons.textSettings("\nBest score: " + Game.highScore, customFont, 1);
        Label place2 = Buttons.textSettings("2nd: " + (Game.secondBest > 0 ? Game.secondBest : "N/A"), customFont, 1);
        Label place3 = Buttons.textSettings("3rd: " + (Game.thirdBest > 0 ? Game.thirdBest : "N/A"), customFont, 1);
        Label place4 = Buttons.textSettings("4th: " + (Game.fourthBest > 0 ? Game.fourthBest : "N/A"), customFont, 5);
        Label place5 = Buttons.textSettings("5th: " + (Game.fifthBest > 0 ? Game.fifthBest : "N/A"), customFont, 1);

        pauseBox.getChildren().addAll(
                valueLabel,
                place1,
                place2,
                place3,
                place4,
                place5,
                Buttons.createButton("Back", primaryStage, pauseBox));
    }
    /*
     * ==============================================================
     * ==================== ACHIVEMENTS =============================
     * ==============================================================
     */
    static void showAchivements(VBox pauseBox, Stage primaryStage) {
        Buttons.setPauseBoxBackground(pauseBox, "background1.png");
        pauseBox.getChildren().clear();

        Font customFont = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 40);
        Font customFont1 = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 20);
        Label valueLabel = new Label("Achievements");
        valueLabel.setFont(customFont);
        valueLabel.setTextFill(Color.web("#ffffff"));
        valueLabel.setAlignment(Pos.CENTER);

        ImageView i1 = new ImageView(Game.Achivement1);
        ImageView i2 = new ImageView(Game.Achivement2);
        ImageView i3 = new ImageView(Game.Achivement3);

        i1.setFitWidth(64);
        i1.setFitHeight(64);
        i1.setTranslateX(-180);
        i1.setTranslateY(-120);
        i1.setOpacity(0.3);
        if (Game.counter > 100) {
            i1.setOpacity(1);
            achivementCounter++;
        }

        i2.setFitWidth(64);
        i2.setFitHeight(64);
        i2.setTranslateX(-180);
        i2.setTranslateY(-70);
        i2.setOpacity(0.3);
        if (Food.barriersDodgedCounter > 20) {
            i2.setOpacity(1);
            achivementCounter++;
        }

        i3.setFitWidth(64);
        i3.setFitHeight(64);
        i3.setTranslateX(-180);
        i3.setTranslateY(-20);
        i3.setOpacity(0.3);
        if (Game.bombsClaimedCounter >= 10) {
            i3.setOpacity(1);
            achivementCounter++;
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
                Buttons.createButton("Back", primaryStage, pauseBox));
    }


}
