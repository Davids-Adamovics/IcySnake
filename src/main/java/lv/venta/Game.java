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
    static int barrierY = 2;
    static int highScore = 0;
    static int newscore = 0;
    static int bombsClaimedCounter = 0;
    static int secondBest = 0;
    static int thirdBest = 0;
    static int fourthBest = 0;
    static int fifthBest = 0;

    static List<Stage> openStages = new ArrayList<>();
    static List<SnakesBody> snake = new ArrayList<>();

    static boolean gameOver = false;
    static boolean gameStarted = false;
    static boolean gamePaused = false;
    static boolean showInstructions = true;
    static boolean gameOverSoundPlayed = false;
    static boolean inGameOverState = false;
    static boolean powerUpPlayed = true;

    static Image augli;
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
        BackgroundsImage = new Image(Game.class.getResource("background1.png").toExternalForm());
    }

    public void start(Stage primaryStage) {
        try {

            // Mainigie/sprite sheet
            Image augluSpriteSheet = new Image(getClass().getResource("utilStileSheet.png").toExternalForm());

            /*
             * izgriez atsevišķus attēlus no sprite sheet
             * x lokācija, y lokācija, platums, augstums
             */
            banans = cropImage(augluSpriteSheet, 0, 0, 250, 250);
            zemene = cropImage(augluSpriteSheet, 0, 316, 350, 350);
            vinogas = cropImage(augluSpriteSheet, 0, 690, 350, 350);
            barjera = cropImage(augluSpriteSheet, 460, 0, 250, 250);
            kronis = cropImage(augluSpriteSheet, 420, 350, 350, 350);
            abols = cropImage(augluSpriteSheet, 460, 760, 250, 265);
            plus5 = cropImage(augluSpriteSheet, 950, 70, 150, 150);
            bomb = cropImage(augluSpriteSheet, 950, 430, 150, 170);

            augli = new Image(getClass().getResource("augli.png").toExternalForm());
            powerup = new Image(getClass().getResource("powerup.gif").toExternalForm());
            // backgrounds
            backgroundImage1 = new Image(getClass().getResource("background4.png").toExternalForm());
            backgroundImage2 = new Image(getClass().getResource("background1.png").toExternalForm());
            // speles ikona
            iconImage = new Image(getClass().getResource("logologo.png").toExternalForm());
            // audio faili
            musicPlayer = new backgroundMusic(new String[] { "game1.wav", "gameCrazy.wav", "gameGopnik.wav",
                    "gameGTA.wav", "gameHz.wav", "gameMario.wav", "gamePain.wav", "gameRa.wav", "gameRave.wav",
                    "gameUzi.wav", "gameYeat.wav" });
            musicPlayer.BackgroundMusic(new String[] { "game1.wav", "gameCrazy.wav", "gameGopnik.wav", "gameGTA.wav",
                    "gameHz.wav", "gameMario.wav", "gamePain.wav", "gameRa.wav", "gameRave.wav", "gameUzi.wav",
                    "gameYeat.wav" });
            // Achivements
            Achivement1 = new Image(getClass().getResource("Achivement1.png").toExternalForm());
            Achivement2 = new Image(getClass().getResource("Achivement2.png").toExternalForm());
            Achivement3 = new Image(getClass().getResource("Achivement3.png").toExternalForm());

            newFood();

            VBox vb = new VBox(); // izveido main canvas, scene pamats
            Canvas canvas = new Canvas(590, 590);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            vb.getChildren().add(canvas);

            // Izveido JAVAFX Animation timer, kur definē tick skaitītāju nanosekundēs
            new AnimationTimer() {
                long lastTick = 0; // sākumā tas ir definēts ar nulli

                public void handle(long now) {
                    if (!gameStarted || gamePaused) { // Ja spēle vēl nav sākusies vai ir iepauzēta
                        if (showInstructions) { // un showInstructions == True
                            drawInstructions(gc); // parāda instrukcijas
                        } else {
                            // instrukcijas
                            gc.setFill(Color.web("#88b5d1")); // canvas krāsa
                            gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight()); // rectangle uz
                                                                                                      // canvas
                            gc.setFill(Color.web("#ffffff")); // teksta krāsa
                            Font customFont = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 30); // teksta
                                                                                                                       // fonts
                            gc.setFont(customFont);
                            gc.fillText("Press SPACE to Start", 115, 290);
                        }
                        return;
                    }
                    // Ja funkcija iepriekš vēl nav notikusi, tad lasttick definē ar Animation timer
                    // "now"
                    if (lastTick == 0) {
                        lastTick = now;
                        tick(gc);
                        return;
                    }
                    // pārbauda, vai ir pagājis pietiekami daudz laika kopš iepriekšējā tick
                    // Tas tiek pārbaudīts skatoties, vai esošais tick ir lielāks par 700'000'000
                    // dalīts ar laiku nanosekudēs
                    if (now - lastTick > 700000000 / speed) {
                        // ja ir, tad notiek nākošais tick un palaiž tick funkciju
                        lastTick = now;
                        tick(gc);
                    }
                }
            }.start(); // starto animationTimer loop

            Scene scene = new Scene(vb, 590, 590); // set scene
            // =========//
            // KEYBINDS //
            // =========//
            scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> { // start game ar SPACE
                if (key.getCode() == KeyCode.SPACE) {
                    if (!gameStarted) { // Ja spēle atrodas "Press space to start" režīmā
                        if (showInstructions) { // Tad nospiežot space tas pazūd
                            showInstructions = false;
                        } else {
                            gameStarted = true;
                        }
                    }
                } else if (key.getCode() == KeyCode.ESCAPE) { // Pause game ar ESC
                    gamePaused = true;
                    pauseGame(primaryStage); // Palaiž pause ekrānu
                } else if (key.getCode() == KeyCode.P || key.getCode() == KeyCode.R) { // Restart game ar R
                    backgroundMusic.stopMusic();
                    resetGame();
                } else {
                    if (gameStarted) {
                        // WASD un bultinas
                        if ((key.getCode() == KeyCode.W || key.getCode() == KeyCode.UP) // nospiežot W vai bultiņa uz
                                                                                        // augšu čūska dodas augšup
                                && direction != enumDirections.down) { // Pārbauda, vai čūska necešās pagriezties pa 180
                                                                       // grādiem ieskrienot sevī
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
            primaryStage.setScene(scene); // Uzliek scene
            primaryStage.setTitle("SNAKE GAME"); // Ekrāna nosaukums
            primaryStage.getIcons().add(iconImage); // Ekrāna ikona
            primaryStage.setMinWidth(scene.getWidth()); // Ekrāna min un max izmērs, lai nevarētu mainīt loga izmērus
            primaryStage.setMaxWidth(scene.getWidth());
            primaryStage.setMinHeight(scene.getHeight());
            primaryStage.setMaxHeight(scene.getHeight());
            primaryStage.centerOnScreen();
            primaryStage.show();
            openStages.add(primaryStage);

        } catch (Exception exception1) {
            exception1.printStackTrace(); // noķer kļūdas
        }
    }

    public static void tick(GraphicsContext gc) {

        // ====================//
        // check for game over //
        // ====================//
        if (gameOver) {
            inGameOverState = true;

            gc.setFill(Color.web("#5ac3d1", 0.1)); // fonta krasa un caurspidigums, lai ar tick palīdzību izveidotu
                                                   // gameover animāciju
            gc.fillRect(0, 0, 590, 590); // game over fons

            // Fonti
            Font HEADER = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 80); // Game over teksts
            Font POINTS = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 35); // Punktu un labākā
                                                                                                   // rezultāta skaits
            Font INFO = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 25); // Info teksts

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

            // atteli priekš punktiem un HIGH score
            gc.drawImage(abols, 110, 220, 55, 55);
            gc.drawImage(kronis, 350, 220, 55, 55);

            if (gameOverSoundPlayed) {
                backgroundMusic.playGameOverSound(); // Ja game over skaņa ir noskanējusi, tā neatkārtojas
                gameOverSoundPlayed = false;
                if (counter > highScore) {
                    highScore = counter;
                    // saglabā jauno highscore un iepriekšējo rezultātu, ko izmanto Leaderboard
                }
                newscore = counter;

            }
            return;
        }

        gc.drawImage(BackgroundsImage, 0, 0, 600, 600); // background attēls

        for (int i = snake.size() - 1; i >= 1; i--) { // For cikls, kas iet cauri visam čūskas garumam
            snake.get(i).x = snake.get(i - 1).x; // katrai čūskas daļai piešķir iepriekšējās daļas koordinātas (Izņemot
                                                 // galvai)
            snake.get(i).y = snake.get(i - 1).y;
        }

        // ============================//
        // Cuska atrodas ieksa robežās //
        // ============================//
        switch (direction) {
            case up:
                snake.get(0).y--; // cuskas galva visu laiku iet uz augšu
                if (snake.get(0).y < 3) { // ja galvas koordinātas ir mazākas par 3, tad tām piešķir 2
                    snake.get(0).y = 2; // nav 0, jo pirmajos 2 segmentos atrodas punktu lauks
                }
                break;
            case down:
                snake.get(0).y++; // Y palielinās un iet uz leju
                if (snake.get(0).y >= 22) { // Ja Y lielāks/vienāds ar 22, tad piešķir 21
                    snake.get(0).y = 21;
                }
                break;
            case left:
                snake.get(0).x--;
                if (snake.get(0).x < 0) { // dodas pa kreisi
                    snake.get(0).x = 0; // nevar būt mazāks par nulli
                }
                break;
            case right:
                snake.get(0).x++; // dodas pa labi
                if (snake.get(0).x >= 23) { // Ja X lielāks/vienāds ar 23, tad piešķir 22
                    snake.get(0).x = 22;
                }
                break;
        }

        // Ja cuska apēd augli
        if (food1X == snake.get(0).x && food1Y == snake.get(0).y || food2X == snake.get(0).x && food2Y == snake.get(0).y
                || food3X == snake.get(0).x && food3Y == snake.get(0).y) {
            snake.add(new SnakesBody(-1, -1)); // piešķir jaunu kubu -1 -1 lokācijā
            newFood(); // Izveido jaunus ēdienus
            counter++; // score palielina par +1
        }


        if (powerUpX == snake.get(0).x && powerUpY == snake.get(0).y) { // Ja cuska apēd power up
            newFood(); // Izveido jaunus ēdienus

            gc.setFill(Color.RED);
            Font customFont = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 50);
            gc.setFont(customFont);
            gc.fillText("FAKE POWER UP HAHA", 140, 290); // UZ ekrāna un konsolē parādas apēstā power up nosaukums
            System.out.println("FAKE POWER UP HAHA");
            counter -= 2; // score samazina par -2

            backgroundMusic.playStarSound(); // atskaņo powerup skaņu

        }

        if (bombX == snake.get(0).x && bombY == snake.get(0).y) { // Bumbas power up
            snake.add(new SnakesBody(-1, -1));
            newFood();
            counter += 3;     // 3 punkti

            speed -= 3;       // speed samazina par -3
            gc.setFill(Color.RED);
            Font customFont = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 50);
            gc.setFont(customFont);
            gc.fillText("-3 speed / mix", 140, 290); // UZ ekrāna un konsolē parādas apēstā power up nosaukums
            System.out.println("-3 speed / mix");
            bombsClaimedCounter++;  // saskaita apēstās bumbas priekš achivement

            backgroundMusic.playBombSound();    // noskan bumbas audio fails

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                //kods atkārtojas ik pēc 5s
                for (int i = 0; i < 5; i++) {
                    if (gameOver == false) {
                        newFood();  // ēdieni maisās
                        speed--;    // ātrums palēninās ar katru sekundi
                    }
                }
            }));

            timeline.setCycleCount(5);  // timeline darbojas tikai 5s
            timeline.setOnFinished(event -> {   // kad pagājušas 5s, ātrums atgriežas un konsolē izvade powerup beigas
                System.out.println("Five seconds have passed!");
                powerUpPlayed = true;
                speed += 3;
            });

            timeline.play();

        }
        if (plus5X == snake.get(0).x && plus5Y == snake.get(0).y) { // power up +5
            snake.add(new SnakesBody(-1, -1));
            newFood();
            counter += 3;
            speed -= 0.1;
            gc.setFill(Color.RED);
            Font customFont = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 50);
            gc.setFont(customFont);
            gc.fillText("+3 points", 140, 290);
            System.out.println("+3 points");
            powerUpPlayed = true;

            backgroundMusic.playCoinSound();

        }

        if (willBarrierSpawn == 1 && Buttons.dificulty == 2) {  // barjera nespwanojas katru reizi, bet ja ir powerup režīms un willBarrierSpawn == 1

            if (barrierX == snake.get(0).x && barrierY == snake.get(0).y) { // cuskas galva saskarās ar barjeru
                gameOver = true;    // spēles beigas
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

        // Punktu skaitītāja laukums
        Canvas canvas = new Canvas(590, 50); // Vēlreiz definē canvas
        if (Buttons.color == 0) {
            gc.setFill(Color.web("#0097B2"));   // Ja tiek izmantota zilā mape, lauks ir zils
        } else if (Buttons.color == 1) {
            gc.setFill(Color.web("#8EA60F"));   // Ja tiek izmantota dzltenā mape, lauks ir dzltens
        }
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight()); // piešķir laukam izmērus

        gc.setFill(Color.WHITE); // teksta krāsa
        Font customFont = Font.loadFont(Game.class.getResourceAsStream("zorque.regular.ttf"), 30); //teksta fonts
        gc.setFont(customFont);
        if (counter > highScore) {
            gc.fillText("NEW High Score: " + counter, 10, 30);  // Ja rezultāts pārspēj esošo high score
        } else {
            gc.fillText("Score: " + counter, 10, 30);   // ja erzultāts nepārspēj esošo high score
        }
        // ========================//
        // cuska galva un ķermenis //
        // ========================//

        SnakesBody head = snake.get(0);
        double headSize = 25;
        double cornerRadius = 10;

        gc.drawImage(headImage(), head.x * 25, head.y * 25, headSize, headSize);    // cuskas galva un tās izmēri

        for (int i = 1; i < snake.size(); i++) {    //Čūskas ķermeņa izmēri
            SnakesBody bodyPart = snake.get(i);
            double partSize = 25;
            double bodyPartX = bodyPart.x * 25;
            double bodyPartY = bodyPart.y * 25;

            gc.setFill(snakeColor); // ķermeņa krāsa

            double rectWidth = partSize;
            double rectHeight = partSize;

            gc.fillRoundRect(bodyPartX, bodyPartY, rectWidth, rectHeight, cornerRadius, cornerRadius);
        }

        // =========================//
        // powerup, ediens, barjera //
        // =========================//
        if (Buttons.dificulty == 2) {   // Tiek uzzīmēti visi objekti tikai tad, kad ir aktivizēts powerUP režīms
            gc.drawImage(currentPowerUp, powerUpX * 25, powerUpY * 25, 25, 25);
            gc.drawImage(currentPlus5, plus5X * 25, plus5Y * 25, 25, 25);
            gc.drawImage(currentBomb, bombX * 25, bombY * 25, 25, 25);
            gc.drawImage(currentFruit1, food1X * 25, food1Y * 25, 25, 25);
            gc.drawImage(currentFruit2, food2X * 25, food2Y * 25, 25, 25);
            gc.drawImage(currentFruit3, food3X * 25, food3Y * 25, 25, 25);
            if (willBarrierSpawn == 1) {    // Ja barjerai ir atļauts nospawnoties, tad arī tā parādās
                gc.drawImage(currentBarrier, barrierX * 25, barrierY * 25, 25, 23);
            }
        } else {    // retro versiā palaižas tikai viens auglis
            gc.drawImage(currentFruit1, food1X * 25, food1Y * 25, 25, 25);
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
                Game.musicPlayer.BackgroundMusic(new String[] { "gameCrazy.wav" });
                break;
            case 2:
                Game.musicPlayer.BackgroundMusic(new String[] { "gameGopnik.wav" });
                break;
            case 3:
                Game.musicPlayer.BackgroundMusic(new String[] { "gameGTA.wav" });
                break;
            case 4:
                Game.musicPlayer.BackgroundMusic(new String[] { "gameHz.wav" });
                break;
            case 5:
                Game.musicPlayer.BackgroundMusic(new String[] { "gameMario.wav" });
                break;
            case 6:
                Game.musicPlayer.BackgroundMusic(new String[] { "gamePain.wav" });
                break;
            case 7:
                Game.musicPlayer.BackgroundMusic(new String[] { "gameRa.wav" });
                break;
            case 8:
                Game.musicPlayer.BackgroundMusic(new String[] { "gameRave.wav" });
                break;
            case 9:
                Game.musicPlayer.BackgroundMusic(new String[] { "gameUzi.wav" });
                break;
            case 10:
                Game.musicPlayer.BackgroundMusic(new String[] { "game1.wav" });
                break;

            default:
                Game.musicPlayer.BackgroundMusic(new String[] { "game1.wav" });
                if (Buttons.musicChoice != ""){
                    Game.musicPlayer.BackgroundMusic(new String[] { Buttons.musicChoice });
                }
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
    //apgriež sprite sheet attēlu    x lokācija, y lokācija, platums, augstums
    private Image cropImage(Image image, int x, int y, int width, int height) { 
        PixelReader pixelReader = image.getPixelReader();   //izmato pixel reader
        WritableImage croppedImage = new WritableImage(pixelReader, x, y, width, height);   // izveido jaunu attēlu
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
