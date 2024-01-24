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
                showInitialButtons(pauseBox, primaryStage);
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
                showInitialButtons(pauseBox, primaryStage);
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
            case "MidNight":
                Game.musicPlayer.BackgroundMusic(new String[] { "gameHz.wav" });
                showInitialButtons(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                break;
            case "BeastMode":
                Game.musicPlayer.BackgroundMusic(new String[] { "gameYeat.wav" });
                showInitialButtons(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                break;
            case "Mario":
                Game.musicPlayer.BackgroundMusic(new String[] { "gameMario.wav" });
                showInitialButtons(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                break;
            case "WaterFall":
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
            case "BlastOff":
                Game.musicPlayer.BackgroundMusic(new String[] { "gameUzi.wav" });
                showInitialButtons(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                break;
            case "Leaderboard":
                showLeaderboard(primaryStage, pauseBox);
                backgroundMusic.PlayButtonSound();
                break;
            case "Info":
                showTutorial(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                break;
            case "Achievements":

                showAchivements(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                break;
            case "Dificulty":
                changeDificulty(pauseBox, primaryStage);
                backgroundMusic.PlayButtonSound();
                break;
            case "Retro":
                dificulty = 1;
                showInitialButtons(pauseBox, primaryStage);
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
                createButton("Info", primaryStage, pauseBox),
                createButton("Leaderboard", primaryStage, pauseBox),
                createButton("Achievements", primaryStage, pauseBox),
                createButton("Options", primaryStage, pauseBox),
                createButton("Quit", primaryStage, pauseBox));

    }

    static void showLeaderboard(Stage primaryStage, VBox pauseBox) {
        setPauseBoxBackground(pauseBox, "background1.png");
        
        pauseBox.getChildren().clear();
        Font customFont = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 25);
        Label valueLabel = new Label("Last Attempts");
        valueLabel.setFont(customFont);
        valueLabel.setTextFill(Color.web("#ffffff"));
        valueLabel.setAlignment(Pos.CENTER);

        if(Game.secondBest == 0){
            Game.secondBest = Game.newscore;
        }
        else if(Game.secondBest != 0 && Game.thirdBest == 0){
            Game.thirdBest = Game.newscore;
        }
        else if(Game.secondBest != 0 && Game.thirdBest != 0 && Game.fourthBest == 0){
            Game.fourthBest = Game.newscore;
        }
        else if(Game.secondBest != 0 && Game.thirdBest != 0 && Game.fourthBest != 0 && Game.fifthBest == 0){
            Game.fifthBest = Game.newscore;
        }
        
        else if (Game.secondBest != 0 && Game.thirdBest != 0 && Game.fourthBest != 0 && Game.fifthBest != 0) {
            Game.fifthBest = Game.fourthBest;
            Game.fourthBest = Game.thirdBest;
            Game.thirdBest = Game.secondBest;
            Game.secondBest = Game.newscore;
        }
    
        Label place1 = textSettings("\nBest score: " + Game.highScore, customFont, 1);
        Label place2 = textSettings("2nd: " + (Game.secondBest > 0 ? Game.secondBest : "N/A"), customFont, 1);
        Label place3 = textSettings("3rd: " + (Game.thirdBest > 0 ? Game.thirdBest : "N/A"), customFont, 1);
        Label place4 = textSettings("4th: " + (Game.fourthBest > 0 ? Game.fourthBest : "N/A"), customFont, 5);
        Label place5 = textSettings("5th: " + (Game.fifthBest > 0 ? Game.fifthBest : "N/A"), customFont, 1);
        
    
        pauseBox.getChildren().addAll(
                valueLabel,
                place1,
                place2,
                place3,
                place4,
                place5,
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
        Slider slider = new Slider(0, 100, 50);
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
                createButton("MidNight", primaryStage, pauseBox),
                createButton("BeastMode", primaryStage, pauseBox));

        List<Button> rightButtons = Arrays.asList(
                createButton("Mario", primaryStage, pauseBox),
                createButton("WaterFall", primaryStage, pauseBox),
                createButton("Ra", primaryStage, pauseBox),
                createButton("Rave", primaryStage, pauseBox),
                createButton("BlastOff", primaryStage, pauseBox),
                createButton("Tropical", primaryStage, pauseBox));

        HBox leftButtonBox = new HBox(10, leftButtons.toArray(new Button[0]));
        HBox rightButtonBox = new HBox(10, rightButtons.toArray(new Button[0]));

        leftButtonBox.setAlignment(Pos.CENTER);
        rightButtonBox.setAlignment(Pos.CENTER);

        VBox backButtonBox = new VBox(10, createButton("Back", primaryStage, pauseBox));
        backButtonBox.setAlignment(Pos.CENTER); // nocentree BACK pogu horizontaali

        pauseBox.getChildren().addAll(
                valueLabel,
                leftButtonBox,
                rightButtonBox,
                backButtonBox);
    }

    //info panelis
    static void showTutorial(VBox pauseBox, Stage primaryStage) {
        setPauseBoxBackground(pauseBox, "background1.png");
        pauseBox.getChildren().clear();

        Font customFont = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 40);
        Font customFont1 = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 20);

        Label valueLabel = new Label("Info panel");
        valueLabel.setFont(customFont);
        valueLabel.setTextFill(Color.web("#ffffff"));
        valueLabel.setAlignment(Pos.CENTER);

        //VBox vBoxInfo = new Label("Info Panel", primaryStage, pauseBox);
        //vBoxInfo.setAlignment(Pos.CENTER);	
        
        ImageView apple = new ImageView(Game.augli);// to do ielikt ieksaa jaunu bildi ar visaam ikonaam
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
        //barrier.setTranslateX(-190);
        //barrier.setTranslateY(-40);

        Label augli = new Label("Adds +1 point to the counter	" + '\n' + 
        						"Adds +1 speed to the Snake	");
        
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

        //barjera.setTranslateX(0);
        //barjera.setTranslateY(60);
        
        augli.setFont(customFont1);
        bomb.setFont(customFont1);
        plus5.setFont(customFont1);
        powerup.setFont(customFont1);
        barjera.setFont(customFont1);
        
        HBox hBoxButton = new HBox(createButton("Back", primaryStage, pauseBox));
        hBoxButton.setAlignment(Pos.CENTER);
        VBox vBoxAll = new VBox(hBoxAbols, hBoxBomba, hBoxPluss5, hBoxStar, hBoxBarrier, hBoxButton);
        //vBoxInfo
        vBoxAll.setSpacing(30);
        pauseBox.getChildren().addAll(
        		vBoxAll
        		 );
        
      /*pauseBox.getChildren().addAll(
                valueLabel,
                abols,
                bomb,
                plus5,
                powerup,
                barjera,
                apple,
                bomba,
                pluss5,
                star,
                barrier,
                createButton("Back", primaryStage, pauseBox));*/
    }

    // achievementi
    static void showAchivements(VBox pauseBox, Stage primaryStage) {
        setPauseBoxBackground(pauseBox, "background1.png");
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

        Label Achivement1 = new Label("Reach 100 points");
        Label Achivement2 = new Label("Dodge 20 Barriers");
        Label Achivement3 = new Label("Claim 10 Bombs");
        // asdasdasd
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

    // background bilde
    private static void setPauseBoxBackground(VBox pauseBox, String backgroundImage) {
        String imageUrl = Buttons.class.getResource(backgroundImage).toExternalForm();
        pauseBox.setStyle("-fx-background-image: url('" + imageUrl + "'); " +
                "-fx-background-size: cover;");
    }
    private static Label textSettings(String text, Font font, int strokeWidth) {
        Label label = new Label(text);
        label.setFont(font);
        label.setTextFill(Color.web("#ffffff"));
        label.setStyle("-fx-stroke: black; -fx-stroke-width: " + strokeWidth + "px;");
        label.setAlignment(Pos.CENTER);
        return label;
    }

}
