package lv.venta;

//importi
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends Application {

    // mainīgie
    static int speed = 5;
    static int foodX = 0;
    static int foodY = 0;
    static int powerUpX = 0;
    static int powerUpY = 0;
    static int barrierX = 0;
    static int barrierY = 0;
    private static List<Stage> openStages = new ArrayList<>();
    static List<SnakesBody> snake = new ArrayList<>();
    static enumDirections direction = enumDirections.left;
    static boolean gameOver = false;
    static Random rand = new Random();
    static boolean gameStarted = false;
    static Image abols;
    static Image banans;
    static Image vinogas;
    static Image zemene;
    static Image barjera;
    static Image powerup;
    static Image backgroundImage;
    static Image iconImage;
    static boolean gamePaused = false;
    static Stage pauseStage;
    private static backgroundMusic musicPlayer;
    static boolean showInstructions = true;
    private static boolean gameOverSoundPlayed = false;
    private static boolean inGameOverState = false;
    private static Image currentFruit;
    private static Image currentPowerUp;
    static Image currentBarrier;
    Image headImage = Game.headImage();
    static int counter = 0;
    
    public void restart() {
        resetGame();
        gamePaused=false;
        gameOver=false;
        gameOverSoundPlayed=false;
        inGameOverState=false;
        gameStarted=true;
        showInstructions=false;

    }
    
    public void start(Stage primaryStage) {
        try {
     
            // mainīgie attēli
            abols = new Image(getClass().getResource("apple.png").toExternalForm());
            banans = new Image(getClass().getResource("banana.png").toExternalForm());
            vinogas = new Image(getClass().getResource("grapes.png").toExternalForm());
            zemene = new Image(getClass().getResource("strawberry.png").toExternalForm());
            barjera = new Image(getClass().getResource("barrier.png").toExternalForm());
            backgroundImage = new Image(getClass().getResource("background3.gif").toExternalForm());
            iconImage = new Image(getClass().getResource("logologo.png").toExternalForm());
            powerup = new Image(getClass().getResource("powerup.gif").toExternalForm());

            // mainīgie audio faili
            musicPlayer = new backgroundMusic(new String[] { "game1.wav", "game4.wav" });
            musicPlayer.BackgroundMusic(new String[] { "game1.wav", "game4.wav" }); // Call the method
            newFood();

            // izveido canvas
            VBox vb = new VBox();
            Canvas canvas = new Canvas(650, 650);

            GraphicsContext gc = canvas.getGraphicsContext2D();
            vb.getChildren().add(canvas);

            new AnimationTimer() {
                long lastTick = 0;

                public void handle(long now) {
                    if (!gameStarted || gamePaused) {
                        if (showInstructions) {
                            drawInstructions(gc);
                        } else {
                            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // Clear the canvas
                            gc.setFill(Color.BLACK);
                            gc.setFont(new Font("", 30));
                            gc.fillText("Press SPACE to Start", 100, 250);
                        }
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

            // set scene
            Scene scene = new Scene(vb, 650, 650);

            // start game ar SPACE
            scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
                if (key.getCode() == KeyCode.SPACE) {
                    if (!gameStarted) {
                        if (showInstructions) {
                            showInstructions = false;
                        } else {
                            gameStarted = true;
                        }
                    }
                } else if (key.getCode() == KeyCode.ESCAPE) {
                    // Pause game ar ESC
                    pauseGame(primaryStage);
                }else if (key.getCode()==KeyCode.R) {
                    resetGame();
                } else {
                	
                    // WASD un bultinas
                    if (gameStarted) {
                        if (key.getCode() == KeyCode.W || key.getCode() == KeyCode.UP) {
                            direction = enumDirections.up;
                        } else if (key.getCode() == KeyCode.A || key.getCode() == KeyCode.LEFT) {
                            direction = enumDirections.left;
                        } else if (key.getCode() == KeyCode.S || key.getCode() == KeyCode.DOWN) {
                            direction = enumDirections.down;
                        } else if (key.getCode() == KeyCode.D || key.getCode() == KeyCode.RIGHT) {
                            direction = enumDirections.right;
                        }
                    }
                }
            });

            // cuskas kermenis
            snake.add(new SnakesBody(10, 10));
            snake.add(new SnakesBody(10, 10));
            snake.add(new SnakesBody(10, 10));

            // scene regigesana
            primaryStage.setScene(scene);
            primaryStage.setTitle("SNAKE GAME");
            primaryStage.getIcons().add(iconImage);
            primaryStage.setMinWidth(650);
            primaryStage.setMaxWidth(650);
            primaryStage.setMinHeight(650);
            primaryStage.setMaxHeight(650);
            primaryStage.centerOnScreen();
            primaryStage.show();
            openStages.add(primaryStage);

        } catch (Exception exception1) {
            exception1.printStackTrace();
        }
    }

    public static void tick(GraphicsContext gc) {
        if (gameOver) {
            inGameOverState = true;
            gc.setFill(Color.RED);
            gc.setFont(new Font("Cascadia Mono", 50));
            gc.fillText("GAME OVER", 100, 250);
            if (gameOverSoundPlayed) {
                backgroundMusic.playGameOverSound();
                gameOverSoundPlayed = false;
            }
            // gameOverSoundPlayed = true;

            return;
        }

        // background
        gc.drawImage(backgroundImage, 0, 0, 650, 650);

        // čūskas ķermenis
        for (int i = snake.size() - 1; i >= 1; i--) {
            // katrai čūskas daļai piešķir iepriekšējās daļas koordinātas
            snake.get(i).x = snake.get(i - 1).x;
            snake.get(i).y = snake.get(i - 1).y;
        }

        // Gādā, ka čūska atrodas
        switch (direction) {
            case up:
                snake.get(0).y--;
                if (snake.get(0).y < 0) {
                    snake.get(0).y = 0; // augša
                }
                break;
            case down:
                snake.get(0).y++;
                if (snake.get(0).y >= 25) {
                    snake.get(0).y = 24; // apakša
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
                if (snake.get(0).x >= 25) {
                    snake.get(0).x = 24; // labā mala
                }
                break;
        }

        // ja galva saskaras ar edienu
        if (foodX == snake.get(0).x && foodY == snake.get(0).y) {
            snake.add(new SnakesBody(-1, -1));
            newFood();
            counter += 1;
        }
        if (powerUpX == snake.get(0).x && powerUpY == snake.get(0).y) {
            snake.add(new SnakesBody(-1, -1));
            newFood();
            counter += 1;
            Random random = new Random();
            int x = random.nextInt(1) + 1; // 1-4

            long slowMotionStartTime = 0;
            boolean isInSlowMotion = false;

            if (x == 1 && !isInSlowMotion) {
                isInSlowMotion = true;
                speed -= 3;
                slowMotionStartTime = System.currentTimeMillis();
                gc.setFill(Color.RED);
                gc.setFont(new Font("Cascadia Mono", 50));
                gc.fillText("slow motion", 100, 250);
                System.out.println("slow motion");

                for (int i = 1; i <= 5; i++) {
                    System.out.println(i);
                    try {
                        Thread.sleep(1000); // Sleep for 1000 milliseconds (1 second)
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (i == 5) {
                        System.out.println("test - Current Time: " + System.currentTimeMillis());
                        isInSlowMotion = false;
                        speed += 3; // Restore the original speed
                    }
                }
            }

            if (x == 2) {
                counter += 5;
                speed++;
                gc.setFill(Color.RED);
                gc.setFont(new Font("Cascadia Mono", 50));
                gc.fillText("+5 points", 100, 250);
                System.out.println("+5 points");
            }
        }

        // ja čūska nomirst
        for (int i = 1; i < snake.size(); i++) {
            if (snake.get(0).x == snake.get(i).x && snake.get(0).y == snake.get(i).y) {
                gameOver = true;
                stopBackgroundMusic();
                gameOverSoundPlayed = true;

            }
        }

        // score
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("", 30));
        gc.fillText("Score: " + counter, 10, 30);

        // cuskas kermena krasa atkariba no gender
        // First, check if it's the head of the snake
        SnakesBody head = snake.get(0);

        gc.drawImage(headImage(), head.x * 25, head.y * 25, 30, 30);

        // Rest of the snake's body
        for (int i = 1; i < snake.size(); i++) {
            SnakesBody bodyPart = snake.get(i);
            double circleRadius = 12.5; // Adjust the radius as needed
            double circleCenterX = bodyPart.x * 25 + 12.5; // Adjust the center position as needed
            double circleCenterY = bodyPart.y * 25 + 12.5; // Adjust the center position as needed

            if (PrimaryController.currentPlayer.getgender() == enumGender.male) {
                gc.setFill(Color.LIGHTBLUE);
            } else {
                gc.setFill(Color.PINK);
            }

            gc.setStroke(Color.rgb(0, 0, 0, 0.5)); // Adjust the darkness of the stroke

            gc.setLineWidth(1.0); // Adjust the line width as needed
            gc.fillOval(circleCenterX - circleRadius, circleCenterY - circleRadius, 2 * circleRadius,
                    2 * circleRadius);
        }
        gc.drawImage(currentPowerUp, powerUpX * 25, powerUpY * 25, 25, 25);
        gc.drawImage(currentFruit, foodX * 25, foodY * 25, 25, 25);
        gc.drawImage(currentBarrier, barrierX * 25, barrierY * 25, 25, 25);

        // Check if the snake head collides with the barrier
        if (barrierX == snake.get(0).x && barrierY == snake.get(0).y) {
            gameOver = true;
            stopBackgroundMusic();
            gameOverSoundPlayed = true;
        }
    }

    // pause
    private static void pauseGame(Stage primaryStage) {
        if (pauseStage == null) {
            pauseStage = new Stage();
            pauseStage.initModality(Modality.APPLICATION_MODAL);
            pauseStage.initStyle(StageStyle.UNDECORATED);

            VBox pauseBox = new VBox(20);
            pauseBox.setAlignment(Pos.CENTER);
            pauseBox.setPadding(new Insets(20));
            pauseBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8);");

            // original pogas
            showInitialButtons(pauseBox, primaryStage);

            Scene pauseScene = new Scene(pauseBox, 300, 300);
            pauseStage.setScene(pauseScene);

            gamePaused = true;
            pauseStage.show();
            pauseStage.setOnHidden(event -> gamePaused = false);
        }

        pauseStage.show();
    }

    // parada tikai original pogas
    private static void showInitialButtons(VBox pauseBox, Stage primaryStage) {
        pauseBox.getChildren().clear();
        pauseBox.getChildren().addAll(
                createButton("Resume", primaryStage, pauseBox),
                createButton("Restart", primaryStage, pauseBox),
                createButton("Options", primaryStage, pauseBox),
                createButton("Quit", primaryStage, pauseBox));
    }

    // aptur background music
    public static void stopBackgroundMusic() {
        backgroundMusic.stopMusic();
    }

    private static void showNewButtons(VBox pauseBox, Stage primaryStage) {
        // izdzes jaunas pogas
        pauseBox.getChildren().clear();

        // izveido jaunas pogas
        pauseBox.getChildren().addAll(
                createButton("Back", primaryStage, pauseBox),
                createButton("NewOption1", primaryStage, pauseBox),
                createButton("NewOption2", primaryStage, pauseBox),
                createButton("NewOption3", primaryStage, pauseBox));
    }

    private static Button createButton(String text, Stage primaryStage, VBox pauseBox) {
        Button button = new Button(text);
        button.setStyle(
                "-fx-background-color: " +
                        "linear-gradient(#ff5400, #be1d00), " +
                        "#9d4024, " +
                        "#d86e3a, " +
                        "radial-gradient(center 50% 50%, radius 100%, #d86e3a, #c54e2c); " +
                        "-fx-background-insets: 0,0 0 1 0, 0 0 2 0, 0 0 3 0; " +
                        "-fx-background-radius: 5; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-size: 1.5em; " +
                        "-fx-text-fill: white; " +
                        "-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );");

        // pogas pilda darbibu atkariba no teksta
        button.setOnAction(e -> handleButtonAction(text, primaryStage, pauseBox));
        return button;
    }

    // pogu nosaukumi un funkciju izsauksana
    private static void handleButtonAction(String action, Stage primaryStage, VBox pauseBox) {
        switch (action) {
            case "Resume":
                gamePaused = false;
                pauseStage.hide();
                break;
            case "Restart":
                resetGame();
                gamePaused = false;
                pauseStage.hide();

                break;
            case "Options":
                options(pauseBox, primaryStage);
                break;
            case "Quit":
                closeAllStages();
                pauseStage.hide();
                break;
            case "Back":
                // parada original pogas
                showInitialButtons(pauseBox, primaryStage);

                break;
        }
    }

    private static void closeAllStages() {
        for (Stage stage : openStages) {
            stage.close();
        }
    }

    private static void resetGame() {
        // reset for new game
        speed = 5;
        gameOver = false;
        snake.clear();
        direction = enumDirections.left;

        // Reset cusku original laukuma
        snake.add(new SnakesBody(10, 10));
        snake.add(new SnakesBody(10, 10));
        snake.add(new SnakesBody(10, 10));
        counter -= (counter + 1);
        counter += 1;
        gameOverSoundPlayed = false;

        musicPlayer = new backgroundMusic(new String[] { "game1.wav", "game4.wav" });
        musicPlayer.BackgroundMusic(new String[] { "game1.wav", "game4.wav" }); // Call the method
    }

    private static void options(VBox pauseBox, Stage primaryStage) {

        showNewButtons(pauseBox, primaryStage);
    }

    private static void options() {

    }

    private static void drawInstructions(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.setFont(new Font("", 20));
        gc.fillText("Use AWSD or arrows to move", 100, 200);
        gc.fillText("Press SPACE to go to the next page", 100, 230);
        return;
    }

    // private static void playGameOverSound() {
    // musicPlayer = new backgroundMusic(new String[]{"gamrOver.wav"});
    // }

    public static void newFood() {
        start: while (true) {
            foodX = rand.nextInt(24);
            foodY = rand.nextInt(24);

            powerUpX = rand.nextInt(24);
            powerUpY = rand.nextInt(24);

            barrierX = rand.nextInt(24);
            barrierY = rand.nextInt(24);

            for (SnakesBody c : snake) {
                if (c.x == foodX && c.y == foodY) {
                    continue start;
                }
            }

            // Generate a new fruit type
            currentFruit = generateNewFruit();

            currentPowerUp = generateNewPowerUp();

            // Generate a new barrier type
            currentBarrier = generateNewBarrier();

            speed += 0.7;
            backgroundMusic.playPickupSound();
            break;
        }
    }

    // Method to generate a new barrier type
    private static Image generateNewBarrier() {
        return barjera;
    }

    private static Image generateNewPowerUp() {
        return powerup;
    }

    // Method to generate a new fruit type
    private static Image generateNewFruit() {
        Image[] fruits = { abols, banans, vinogas, zemene };
        Random random = new Random();
        int randomIndex = random.nextInt(fruits.length);
        return fruits[randomIndex];
    }

    public static Image headImage() {
        String headFileName;

        if (PrimaryController.currentPlayer.getgender() == enumGender.male) {
            headFileName = "head.png"; // Replace with the actual file name for male head
        } else {
            headFileName = "head.png"; // Replace with the actual file name for female head
        }

        return new Image(Game.class.getResource(headFileName).toExternalForm());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
