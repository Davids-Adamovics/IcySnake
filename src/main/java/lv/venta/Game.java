package lv.venta;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lv.venta.PrimaryController;

public class Game extends Application {

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
    static Image backgroundImage;
    static Image iconImage;
    static boolean gamePaused = false;
    static Stage pauseStage;
    private static backgroundMusic musicPlayer;

    // Add a boolean to control whether to show instructions
    static boolean showInstructions = true;

    
    public void start(Stage primaryStage) {
        try {
            abols = new Image(getClass().getResource("apple.png").toExternalForm());
            backgroundImage = new Image(getClass().getResource("background1.png").toExternalForm());
            iconImage = new Image(getClass().getResource("logologo.png").toExternalForm());

            newFood();

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


            Scene scene = new Scene(vb, 500, 500);
            

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
                    // Pause the game when ESC is pressed
                    pauseGame(primaryStage);
                } else {
                    // WASD
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

            snake.add(new SnakesBody(10, 10));
            snake.add(new SnakesBody(10, 10));
            snake.add(new SnakesBody(10, 10));

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
            gc.setFill(Color.RED);
            gc.setFont(new Font("Old English Text MT", 50));
            gc.fillText("GAME OVER", 100, 250);
            return;
        }

        gc.drawImage(backgroundImage, 0, 0, 510, 510);

        for (int i = snake.size() - 1; i >= 1; i--) {
            snake.get(i).x = snake.get(i - 1).x;
            snake.get(i).y = snake.get(i - 1).y;
        }

        // Adjust the movement logic to keep the snake within bounds
        switch (direction) {
            case up:
                snake.get(0).y--;
                if (snake.get(0).y < 0) {
                    snake.get(0).y = 0; // Limit to the top edge
                }
                break;
            case down:
                snake.get(0).y++;
                if (snake.get(0).y >= 20) {
                    snake.get(0).y = 19; // Limit to the bottom edge
                }
                break;
            case left:
                snake.get(0).x--;
                if (snake.get(0).x < 0) {
                    snake.get(0).x = 0; // Limit to the left edge
                }
                break;
            case right:
                snake.get(0).x++;
                if (snake.get(0).x >= 20) {
                    snake.get(0).x = 19; // Limit to the right edge
                }
                break;
        }

        if (foodX == snake.get(0).x && foodY == snake.get(0).y) {
            snake.add(new SnakesBody(-1, -1));
            newFood();
        }

        for (int i = 1; i < snake.size(); i++) {
            if (snake.get(0).x == snake.get(i).x && snake.get(0).y == snake.get(i).y) {
                gameOver = true;
                playGameOverSound();
            }
        }

        gc.setFill(Color.WHITE);
        gc.setFont(new Font("", 30));
        gc.fillText("Score: " + (speed - 6), 10, 30);
        
        

        

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

    private static void pauseGame(Stage primaryStage) {
        if (pauseStage == null) {
            pauseStage = new Stage();
            pauseStage.initModality(Modality.APPLICATION_MODAL);
            pauseStage.initStyle(StageStyle.UNDECORATED);

            VBox pauseBox = new VBox(20);
            pauseBox.setAlignment(Pos.CENTER);
            pauseBox.setPadding(new Insets(20));
            pauseBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8);");

            // Initially, show the first set of buttons
            showInitialButtons(pauseBox, primaryStage);

            Scene pauseScene = new Scene(pauseBox, 300, 300);
            pauseStage.setScene(pauseScene);

            gamePaused = true;
            pauseStage.show();
            pauseStage.setOnHidden(event -> gamePaused = false);
        }

        pauseStage.show();
    }
    
    private static void showInitialButtons(VBox pauseBox, Stage primaryStage) {
    	pauseBox.getChildren().clear();
        pauseBox.getChildren().addAll(
                createButton("Resume", primaryStage, pauseBox),
                createButton("Restart", primaryStage, pauseBox),
                createButton("Options", primaryStage, pauseBox),
                createButton("Quit", primaryStage, pauseBox)
        );
    }

    private static void showNewButtons(VBox pauseBox, Stage primaryStage) {
        // Clear the existing buttons and show the new set of buttons
        pauseBox.getChildren().clear();

        // Add new buttons
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

        // Handle button actions based on text
        button.setOnAction(e -> handleButtonAction(text, primaryStage, pauseBox));
        return button;
    }
    


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
                // Show the initial set of buttons
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
        // Reset game-related variables
        speed = 5;
        gameOver = false;
        snake.clear();
        direction = enumDirections.left;

        // Reset the snake to its initial state
        snake.add(new SnakesBody(10, 10));
        snake.add(new SnakesBody(10, 10));
        snake.add(new SnakesBody(10, 10));

        // Reset other necessary variables or game elements
        newFood();
    }
    
    private static void options(VBox pauseBox, Stage primaryStage) {
        // Clear existing buttons and show the new set of buttons
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

    private static void playGameOverSound() {
        musicPlayer = new backgroundMusic(new String[]{"gamrOver.wav"});
    }

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
            break;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
