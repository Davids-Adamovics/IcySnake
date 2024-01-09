package lv.venta;

//       imports           
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends Application {

    // mainīgie
    static int speed = 5;
    static int food1X = 0;
    static int food1Y = 0;
    static int food2X = 0;
    static int food2Y = 0;
    static int food3X = 0;
    static int food3Y = 0;
    static int powerUpX = 0;
    static int powerUpY = 0;
    static int plus5X = 0;
    static int plus5Y = 0;
    static int bombX = 0;
    static int bombY = 0;
    static int counter = 0;
    static int barrierX = 0;
    static int barrierY = 0;
    static int highScore = 0;

    static List<Stage> openStages = new ArrayList<>();
    static List<SnakesBody> snake = new ArrayList<>();

    static boolean gameOver = false;
    static boolean gameStarted = false;
    static boolean gamePaused = false;
    static boolean showInstructions = true;
    static boolean gameOverSoundPlayed = false;
    static boolean inGameOverState = false;
    static boolean powerUpPlayed = true;

    static Image abols;
    static Image banans;
    static Image vinogas;
    static Image zemene;
    static Image kronis;
    static Image barjera;
    static Image powerup;
    static Image bomb;
    static Image Achivement1;
    static Image Achivement2;
    static Image Achivement3;
    static Image plus5;
    static Image backgroundImage1;
    static Image backgroundImage2;
    static Image iconImage;
    static Image currentBarrier;
    static Image currentPowerUp;
    static Image currentBomb;
    static Image currentPlus5;
    static Image currentFruit1;
    static Image currentFruit2;
    static Image currentFruit3;
    Image headImage = Game.headImage();

    static enumDirections direction = enumDirections.left;
    static backgroundMusic musicPlayer;
    static Random rand = new Random();
    static Random random = new Random();
    static Stage pauseStage;
    public static Color snakeColor = Color.GREEN;

    public static Image BackgroundsImage = backgroundImage2;
    public static int willBarrierSpawn = 0;

    public static int currentMusicPreference = 0;

    static {
        // Initialize BackgroundsImage with backgroundImage2
        BackgroundsImage = new Image(Game.class.getResource("background1.png").toExternalForm());
    }

    public void start(Stage primaryStage) {
        try {

            // Mainigie/base canvas //
            Image asdImage = new Image(getClass().getResource("utilStileSheet.png").toExternalForm());

            // Crop the portions for different images
            banans = cropImage(asdImage, 0, 0, 250, 250);
            zemene = cropImage(asdImage, 0, 316, 350, 350);
            vinogas = cropImage(asdImage, 0, 690, 350, 350);
            barjera = cropImage(asdImage, 460, 0, 250, 250);
            kronis = cropImage(asdImage, 420, 350, 350, 350);
            abols = cropImage(asdImage, 460, 760, 250, 265);
            plus5 = cropImage(asdImage, 950, 70, 150, 150);
            bomb = cropImage(asdImage, 950, 0430, 150, 150);
            powerup = new Image(getClass().getResource("powerup.gif").toExternalForm());
            backgroundImage1 = new Image(getClass().getResource("background4.png").toExternalForm());
            backgroundImage2 = new Image(getClass().getResource("background1.png").toExternalForm());
            iconImage = new Image(getClass().getResource("logologo.png").toExternalForm());
            musicPlayer = new backgroundMusic(new String[] { "game1.wav", "game4.wav" }); // mainīgie audio faili
            musicPlayer.BackgroundMusic(new String[] { "game1.wav", "game4.wav" });
            Achivement1 = new Image(getClass().getResource("Achivement1.png").toExternalForm());
            Achivement2 = new Image(getClass().getResource("Achivement2.png").toExternalForm());
            Achivement3 = new Image(getClass().getResource("Achivement3.png").toExternalForm());

            newFood();

            VBox vb = new VBox(); // izveido main canvas, scene pamats
            Canvas canvas = new Canvas(590, 590);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            vb.getChildren().add(canvas);

            new AnimationTimer() {
                long lastTick = 0;

                public void handle(long now) {
                    if (!gameStarted || gamePaused) {
                        if (showInstructions) {
                            drawInstructions(gc);
                        } else {
                            // gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                            gc.setFill(Color.web("#88b5d1"));
                            gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
                            gc.setFill(Color.web("#ffffff"));
                            Font customFont = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 30);
                            gc.setFont(customFont);
                            gc.fillText("Press SPACE to Start", 115, 290);
                        } // s
                        return;
                    }

                    if (lastTick == 0) {
                        lastTick = now;
                        tick(gc);
                        return;
                    }

                    if (now - lastTick > 700000000 / speed) {
                        lastTick = now;
                        tick(gc);
                    }
                }
            }.start();

            Scene scene = new Scene(vb, 590, 590); // set scene
            // =========//
            // KEYBINDS //
            // =========//
            scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> { // start game ar SPACE
                if (key.getCode() == KeyCode.SPACE) {
                    if (!gameStarted) {
                        if (showInstructions) {
                            showInstructions = false;
                        } else {
                            gameStarted = true;
                        }
                    }
                } else if (key.getCode() == KeyCode.ESCAPE) { // Pause game ar ESC
                    gamePaused = true;
                    pauseGame(primaryStage);
                } else if (key.getCode() == KeyCode.P || key.getCode() == KeyCode.R) { // Restart game ar R
                    backgroundMusic.stopMusic();
                    resetGame();
                } else {
                    if (gameStarted) {
                        // WASD un bultinas
                        if ((key.getCode() == KeyCode.W || key.getCode() == KeyCode.UP)
                                && direction != enumDirections.down) {
                            direction = enumDirections.up;
                        } else if ((key.getCode() == KeyCode.A || key.getCode() == KeyCode.LEFT)
                                && direction != enumDirections.right) {
                            direction = enumDirections.left;
                        } else if ((key.getCode() == KeyCode.S || key.getCode() == KeyCode.DOWN)
                                && direction != enumDirections.up) {
                            direction = enumDirections.down;
                        } else if ((key.getCode() == KeyCode.D || key.getCode() == KeyCode.RIGHT)
                                && direction != enumDirections.left) {
                            direction = enumDirections.right;
                        }
                    }
                }

            });

            // ===================//
            // Cuskas kermena X/Y //
            // ===================//
            snake.add(new SnakesBody(10, 10));
            snake.add(new SnakesBody(10, 10));
            snake.add(new SnakesBody(10, 10));

            // ======================//
            // Game scene definesana //
            // ======================//
            primaryStage.setScene(scene);
            primaryStage.setTitle("SNAKE GAME");
            primaryStage.getIcons().add(iconImage);
            primaryStage.setMinWidth(590);
            primaryStage.setMaxWidth(590);
            primaryStage.setMinHeight(590);
            primaryStage.setMaxHeight(590);
            primaryStage.centerOnScreen();
            primaryStage.show();
            openStages.add(primaryStage);

        } catch (Exception exception1) {
            exception1.printStackTrace();
        }
    }

    public static void tick(GraphicsContext gc) {

        // ====================//
        // check for game over //
        // ====================//
        if (gameOver) {
            inGameOverState = true;

            gc.setFill(Color.web("#5ac3d1", 0.1)); // fonta krasa un caurspidigums
            gc.fillRect(0, 0, 590, 590); // game over fons

            // Fonti
            Font HEADER = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 80);
            Font POINTS = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 35);
            Font INFO = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 25);

            gc.setGlobalAlpha(0.3); // caurspīdīgums

            // gameover
            gc.setFont(HEADER);
            gc.setFill(Color.WHITE);
            gc.fillText("GAME OVER", 60, 100);

            // info
            gc.setFont(INFO);
            gc.fillText("Press \"r\" or \"p\"  to restart", 100, 350);

            // punkti
            gc.setFont(POINTS);
            gc.fillText(Integer.toString(counter), 170, 260, 35);
            gc.fillText(Integer.toString(highScore), 420, 260, 35);

            // atteli
            gc.drawImage(abols, 110, 220, 55, 55);
            gc.drawImage(kronis, 350, 220, 55, 55);

            gc.setGlobalAlpha(1.0); // reset caurspīdīgums

            if (gameOverSoundPlayed) {
                backgroundMusic.playGameOverSound();
                gameOverSoundPlayed = false;
                if (counter > highScore) {
                    highScore = counter;
                }

            }
            return;
        }

        gc.drawImage(BackgroundsImage, 0, 0, 600, 600); // background

        for (int i = snake.size() - 1; i >= 1; i--) { // čūskas ķermenis
            snake.get(i).x = snake.get(i - 1).x; // katrai čūskas daļai piešķir iepriekšējās daļas koordinātas (Izņemot
                                                 // galvai)
            snake.get(i).y = snake.get(i - 1).y;
        }

        // ============================//
        // Cuska atrodas ieksa barjera //
        // ============================//
        switch (direction) {
            case up:
                snake.get(0).y--;
                if (snake.get(0).y < 3) {
                    snake.get(0).y = 2; // augša
                }
                break;
            case down:
                snake.get(0).y++;
                if (snake.get(0).y >= 22) {
                    snake.get(0).y = 21; // apakša
                }
                break;
            case left:
                snake.get(0).x--;
                if (snake.get(0).x < 0) {
                    snake.get(0).x = 0; // kreisā mala
                }
                break;
            case right:
                snake.get(0).x++;
                if (snake.get(0).x >= 23) {
                    snake.get(0).x = 22; // labā mala
                }
                break;
        }

        if (food1X == snake.get(0).x && food1Y == snake.get(0).y || food2X == snake.get(0).x && food2Y == snake.get(0).y
                || food3X == snake.get(0).x && food3Y == snake.get(0).y) { // food
            snake.add(new SnakesBody(-1, -1));
            newFood();
            counter += 1;
        }

        // ==========//
        // power up //
        // ==========//

        if (powerUpX == snake.get(0).x && powerUpY == snake.get(0).y) { // power up
            snake.add(new SnakesBody(-1, -1));
            newFood();
            counter += 1;

            gc.setFill(Color.RED);
            Font customFont = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 50);
            gc.setFont(customFont);
            gc.fillText("FAKE POWER UP HAHA", 140, 290); // FAKE POWER UP
            System.out.println("FAKE POWER UP HAHA");
            counter -= 2;

            backgroundMusic.playStarSound();

        }
        if (bombX == snake.get(0).x && bombY == snake.get(0).y) { // power up
            snake.add(new SnakesBody(-1, -1));
            newFood();
            counter += 1;

            // ==========//
            // bomb //
            // ==========//

            speed -= 3;
            gc.setFill(Color.RED);
            Font customFont = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 50);
            gc.setFont(customFont);
            gc.fillText("-3 speed / mix", 140, 290); // -3 speed / mix
            System.out.println("-3 speed / mix");

            backgroundMusic.playBombSound();

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                // This code will run every second
                for (int i = 0; i < 5; i++) {
                    if (gameOver == false) {
                        newFood();
                        speed--;
                    }
                }
            }));

            timeline.setCycleCount(5);
            timeline.setOnFinished(event -> {
                System.out.println("Five seconds have passed!");
                powerUpPlayed = true;
                speed += 3;
            });

            timeline.play();

        }
        if (plus5X == snake.get(0).x && plus5Y == snake.get(0).y) { // power up
            snake.add(new SnakesBody(-1, -1));
            newFood();
            counter += 1;

            // ======//
            // plus5 //
            // ======//

            counter += 5;
            speed += 0.1;
            gc.setFill(Color.RED);
            Font customFont = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 50);
            gc.setFont(customFont);
            gc.fillText("+5 points", 140, 290);
            System.out.println("+5 points");
            powerUpPlayed = true;

            backgroundMusic.playCoinSound();

        }
        // ========//
        // barrier //
        // ========//
        if (willBarrierSpawn == 1) {

            if (barrierX == snake.get(0).x && barrierY == snake.get(0).y) {
                gameOver = true;
                stopBackgroundMusic();
                gameOverSoundPlayed = true;
                backgroundMusic.playBarrierSound();
            }
        }

        for (int i = 1; i < snake.size(); i++) { // ja čūska saskarās ar savu ķermeni
            if (snake.get(0).x == snake.get(i).x && snake.get(0).y == snake.get(i).y) {
                gameOver = true;
                stopBackgroundMusic();
                gameOverSoundPlayed = true;
            }
        }

        // Set the background rectangle color and size
        Canvas canvas = new Canvas(590, 50);
        if (Buttons.color == 0) {
            gc.setFill(Color.web("#0097B2"));
        } else if (Buttons.color == 1) {
            gc.setFill(Color.web("#8EA60F"));
        }
        gc.fillRect(0, 0, canvas.getWidth(), 50);

        // Set the text color, font, and draw the score text
        gc.setFill(Color.WHITE);
        Font customFont = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 30);
        gc.setFont(customFont);
        gc.fillText("Score: " + counter, 10, 30);

        // ========================//
        // cuska galva un ķermenis //
        // ========================//

        SnakesBody head = snake.get(0);
        double headSize = 25;
        double cornerRadius = 10; // Adjust this value to control the roundness

        gc.drawImage(headImage(), head.x * 25, head.y * 25, headSize, headSize);

        for (int i = 1; i < snake.size(); i++) {
            SnakesBody bodyPart = snake.get(i);
            double partSize = 25;
            double bodyPartX = bodyPart.x * 25;
            double bodyPartY = bodyPart.y * 25;

            gc.setFill(snakeColor);

            double rectWidth = partSize;
            double rectHeight = partSize;

            // Draw filled rounded rectangle (tube)
            gc.fillRoundRect(bodyPartX, bodyPartY, rectWidth, rectHeight, cornerRadius, cornerRadius);
        }

        // =========================//
        // powerup, ediens, barjera //
        // =========================//
        gc.drawImage(currentPowerUp, powerUpX * 25, powerUpY * 25, 25, 25);
        gc.drawImage(currentPlus5, plus5X * 25, plus5Y * 25, 25, 25);
        gc.drawImage(currentBomb, bombX * 25, bombY * 25, 25, 25);
        gc.drawImage(currentFruit1, food1X * 25, food1Y * 25, 25, 25);
        gc.drawImage(currentFruit2, food2X * 25, food2Y * 25, 25, 25);
        gc.drawImage(currentFruit3, food3X * 25, food3Y * 25, 25, 25);
        if (willBarrierSpawn == 1) {
            gc.drawImage(currentBarrier, barrierX * 25, barrierY * 25, 25, 25);
        }
    }

    // pause game
    private static void pauseGame(Stage primaryStage) {
        GameOptions.pauseGame(primaryStage);
    }

    // parāda originalas pogas
    static void showInitialButtons(VBox pauseBox, Stage primaryStage) {
        Buttons.showInitialButtons(pauseBox, primaryStage);
    }

    // parada jaunas pogas
    private static void showNewButtons(VBox pauseBox, Stage primaryStage) {
        Buttons.showNewButtons(pauseBox, primaryStage);
    }

    // izveido pogas
    static Button createButton(String text, Stage primaryStage, VBox pauseBox) { // pogas un to dizains
        return Buttons.createButton(text, primaryStage, pauseBox);
    }

    // pogu darbibas
    static void handleButtonAction(String action, Stage primaryStage, VBox pauseBox) { // pogu nosaukumi un
        Buttons.handleButtonAction(action, primaryStage, pauseBox);
    }

    // aizver visus stage
    static void closeAllStages() {
        for (Stage stage : openStages) {
            stage.close();
        }
    }

    // aptur background music
    public static void stopBackgroundMusic() {
        backgroundMusic.stopMusic();
    }

    // reset game
    static void resetGame() {
        GameOptions.resetGame();
        backgroundMusic.stopMusic();

        switch (Game.currentMusicPreference) {
            case 1:
                Game.musicPlayer.BackgroundMusic(new String[] { "game1.wav" });
                break;
            case 2:
                Game.musicPlayer.BackgroundMusic(new String[] { "game4.wav" });
                break;
            case 3:
                Game.musicPlayer.BackgroundMusic(new String[] { "gameYeat.wav" });
                break;

            default:

                Game.musicPlayer.BackgroundMusic(new String[] { "game1.wav" });
                break;
        }
    }

    // Options
    static void options(VBox pauseBox, Stage primaryStage) {
        showNewButtons(pauseBox, primaryStage);
    }

    // Instrukcijas pirms spēles
    private static void drawInstructions(GraphicsContext gc) {
        GameOptions.drawInstructions(gc);
    }

    // spawn food, powerup, barrier
    public static void newFood() {
        Food.newFood();
    }

    // izveido jaunu barjeru
    static Image generateNewBarrier() {
        return barjera;
    }

    // izveido jaunu power up
    static Image generateNewPowerUp() {
        return powerup;
    }

    private Image cropImage(Image image, int x, int y, int width, int height) {
        PixelReader pixelReader = image.getPixelReader();
        WritableImage croppedImage = new WritableImage(pixelReader, x, y, width, height);
        return croppedImage;
    }

    static Image generateNewBomb() {
        return bomb;
    }

    static Image generateNewPlus5() {
        return plus5;
    }

    // izveido jaunu augli
    static Image generateNewFruit() {
        return Food.generateNewFruit();
    }

    // cuskas galvas custom attēls
    public static Image headImage() {
        return GameOptions.headImage();
    }

    // restart
    public void restart() {
        GameOptions.resetGame();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public SnakesBody get(int i) {
        return null;
    }
}
