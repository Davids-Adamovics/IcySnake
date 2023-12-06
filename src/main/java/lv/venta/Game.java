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

    //mainīgie
    static int speed = 5;
    static int foodX = 0;
    static int foodY = 0;
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
    static Image backgroundImage;
    static Image iconImage;
    static boolean gamePaused = false;
    static Stage pauseStage;
    private static backgroundMusic musicPlayer;
    static boolean showInstructions = true;
    private static boolean gameOverSoundPlayed = false;
    private static boolean inGameOverState = false;

    
    public void start(Stage primaryStage) {
        try {
            //mainīgie attēli
            abols = new Image(getClass().getResource("apple.png").toExternalForm());
            banans = new Image(getClass().getResource("banana.png").toExternalForm());
            vinogas = new Image(getClass().getResource("grapes.png").toExternalForm());
            zemene = new Image(getClass().getResource("strawberry.png").toExternalForm());
            backgroundImage = new Image(getClass().getResource("background1.png").toExternalForm());
            iconImage = new Image(getClass().getResource("logologo.png").toExternalForm());

            //mainīgie audio faili
            musicPlayer = new backgroundMusic(new String[]{"game1.wav", "game4.wav"});
            musicPlayer.BackgroundMusic(new String[]{"game1.wav", "game4.wav"}); // Call the method
            newFood();

            //izveido canvas
            VBox vb = new VBox();
            Canvas canvas = new Canvas(500, 500);

            GraphicsContext gc = canvas.getGraphicsContext2D();
            vb.getChildren().add(canvas);

            new AnimationTimer() {
                long lastTick = 0;

                public void handle(long now) {
                    if (!gameStarted || gamePaused) {
                        if (showInstructions) {
                            drawInstructions(gc);
                        } else {
                            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());  // Clear the canvas
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

                    if (now - lastTick > 1000000000 / speed) {
                        lastTick = now;
                        tick(gc);
                    }
                }
            }.start();

            //set scene
            Scene scene = new Scene(vb, 500, 500);
            
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
            primaryStage.setMinWidth(500);
            primaryStage.setMaxWidth(500);
            primaryStage.setMinHeight(500);
            primaryStage.setMaxHeight(500);
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
            if (gameOverSoundPlayed){
                backgroundMusic.playGameOverSound();
                gameOverSoundPlayed = false;
            }
            //gameOverSoundPlayed = true;

            return;
        }

        //background
        gc.drawImage(backgroundImage, 0, 0, 510, 510);

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
                if (snake.get(0).y >= 20) {
                    snake.get(0).y = 19; // apakša
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
                if (snake.get(0).x >= 20) {
                    snake.get(0).x = 19; // labā mala
                }
                break;
        }

        // ja galva saskaras ar edienu
        if (foodX == snake.get(0).x && foodY == snake.get(0).y) {
            snake.add(new SnakesBody(-1, -1));
            newFood();
        }

        // ja čūska nomirst
        for (int i = 1; i < snake.size(); i++) {
            if (snake.get(0).x == snake.get(i).x && snake.get(0).y == snake.get(i).y) {
                gameOver = true;
                stopBackgroundMusic();    
                gameOverSoundPlayed = true;
                                                                  
            }
        }

        //score
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("", 30));
        gc.fillText("Score: " + (speed - 6), 10, 30);
        
        
        //cuskas kermena krasa atkariba no gender
        for (SnakesBody c : snake) {
            if (PrimaryController.currentPlayer.getgender() == enumGender.male) {
                gc.setFill(Color.LIGHTBLUE);
                gc.fillRect(c.x * 25, c.y * 25, 25 - 1, 25 - 1);
                gc.setFill(Color.LIGHTBLUE);
                gc.fillRect(c.x * 25, c.y * 25, 25 - 2, 25 - 2);
            } else {
                gc.setFill(Color.PINK);
                gc.fillRect(c.x * 25, c.y * 25, 25 - 1, 25 - 1);
                gc.setFill(Color.PINK);
                gc.fillRect(c.x * 25, c.y * 25, 25 - 2, 25 - 2);
            }
        }
        gc.drawImage(abols, foodX * 25, foodY * 25, 25, 25);
    }

    //pause
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
    
    //parada tikai original pogas
    private static void showInitialButtons(VBox pauseBox, Stage primaryStage) {
    	pauseBox.getChildren().clear();
        pauseBox.getChildren().addAll(
                createButton("Resume", primaryStage, pauseBox),
                createButton("Restart", primaryStage, pauseBox),
                createButton("Options", primaryStage, pauseBox),
                createButton("Quit", primaryStage, pauseBox)
        );
    }

    //aptur background music
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
                createButton("NewOption3", primaryStage, pauseBox)
        );
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
                        "-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );"
        );

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
        newFood();
        gameOverSoundPlayed = false;

        musicPlayer = new backgroundMusic(new String[]{"game1.wav", "game4.wav"});
        musicPlayer.BackgroundMusic(new String[]{"game1.wav", "game4.wav"}); // Call the method
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

    //private static void playGameOverSound() {
       // musicPlayer = new backgroundMusic(new String[]{"gamrOver.wav"});
   // }

    public static void newFood() {
        start: while (true) {
            foodX = rand.nextInt(18);
            foodY = rand.nextInt(18);

            for (SnakesBody c : snake) {
                if (c.x == foodX && c.y == foodY) {
                    continue start;
                    
                }
            }
            speed++;
            backgroundMusic.playPickupSound();
            break;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
