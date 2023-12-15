package lv.venta;

//=========================//
//       imports           //
//=========================//
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
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
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends Application {

//=========================//
//       mainīgie          //
//=========================//
    static int speed = 5;
    static int foodX = 0;
    static int foodY = 0;
    static int powerUpX = 0;
    static int powerUpY = 0;
    static int counter = 0;
    static int barrierX = 0;
    static int barrierY = 0;

    private static List<Stage> openStages = new ArrayList<>();
    static List<SnakesBody> snake = new ArrayList<>();

    static boolean gameOver = false;
    static boolean gameStarted = false;
    static boolean gamePaused = false;
    static boolean showInstructions = true;
    private static boolean gameOverSoundPlayed = false;
    private static boolean inGameOverState = false;

    static Image abols;
    static Image banans;
    static Image vinogas;
    static Image zemene;
    static Image barjera;
    static Image powerup;
    static Image backgroundImage;
    static Image iconImage;
    static Image currentBarrier;
    private static Image currentPowerUp;
    private static Image currentFruit;
    Image headImage = Game.headImage();

    static enumDirections direction = enumDirections.left;
    private static backgroundMusic musicPlayer;
    static Random rand = new Random();
    static Stage pauseStage;
    
    public void start(Stage primaryStage) {
        try {
            
//=======================================//
//         Mainigie/base canvas          //
//=======================================//
            abols = new Image(getClass().getResource("apple.png").toExternalForm());
            banans = new Image(getClass().getResource("banana.png").toExternalForm());
            vinogas = new Image(getClass().getResource("grapes.png").toExternalForm());
            zemene = new Image(getClass().getResource("strawberry.png").toExternalForm());
            barjera = new Image(getClass().getResource("barrier.png").toExternalForm());
            backgroundImage = new Image(getClass().getResource("background5.png").toExternalForm());
            iconImage = new Image(getClass().getResource("logologo.png").toExternalForm());
            powerup = new Image(getClass().getResource("powerup.gif").toExternalForm());
            
            musicPlayer = new backgroundMusic(new String[] { "game1.wav", "game4.wav" }); // mainīgie audio faili
            musicPlayer.BackgroundMusic(new String[] { "game1.wav", "game4.wav" }); 
            newFood();

            VBox vb = new VBox();   // izveido main canvas, scene pamats
            Canvas canvas = new Canvas(650, 650);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            vb.getChildren().add(canvas);

            new AnimationTimer() {               //izveido animation timer
                long lastTick = 0;               //saglabā pēdējo saglabāto frame
                public void handle(long now) {   //funkcija, kas tiek izsaukta pēc katra frame
                    if (!gameStarted || gamePaused) {
                        if (showInstructions) {  //palaiz balto scene "use WASD or arrow keys"
                            drawInstructions(gc);
                        } else {
                            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());   // ja nerada instrukcijas, nodzes visu canvas un parada :
                            gc.setFill(Color.BLACK);
                            gc.setFont(new Font("", 30));
                            gc.fillText("Press SPACE to Start", 180, 300);
                        }
                        return;
                    }

                    if (lastTick == 0) {  //parbauda, vai ir pagajis frame, ja ir, tad tam pieskir jaunu vertibu un atjaunina 
                        lastTick = now;
                        tick(gc);
                        return;
                    }

                    if (now - lastTick > 700000000 / speed) {   //tick speed
                        lastTick = now;
                        tick(gc);
                    }

                }
            }.start();

            Scene scene = new Scene(vb, 650, 650);   // set scene
//=======================================//
//           KEYBINDS                    //
//=======================================//
            scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {   // start game ar SPACE
                if (key.getCode() == KeyCode.SPACE) {
                    if (!gameStarted) {
                        if (showInstructions) {
                            showInstructions = false;
                        } else {
                            gameStarted = true;
                        }
                    }
                } else if (key.getCode() == KeyCode.ESCAPE) {         // Pause game ar ESC
                    pauseGame(primaryStage);
                } else if (key.getCode() == KeyCode.R) {              // Restart game ar R
                    resetGame();
                } else {
                    if (gameStarted) {
                        // WASD un bultinas
                        if ((key.getCode() == KeyCode.W || key.getCode() == KeyCode.UP) && direction != enumDirections.down) {
                            direction = enumDirections.up;
                        } else if ((key.getCode() == KeyCode.A || key.getCode() == KeyCode.LEFT) && direction != enumDirections.right) {
                            direction = enumDirections.left;
                        } else if ((key.getCode() == KeyCode.S || key.getCode() == KeyCode.DOWN) && direction != enumDirections.up) {
                            direction = enumDirections.down;
                        } else if ((key.getCode() == KeyCode.D || key.getCode() == KeyCode.RIGHT) && direction != enumDirections.left) {
                            direction = enumDirections.right;
                        }
                    }
                }
                
            });

//=======================================//
//           Cuskas kermena X/Y          //
//=======================================//
            snake.add(new SnakesBody(10, 10));
            snake.add(new SnakesBody(10, 10));
            snake.add(new SnakesBody(10, 10));

//=======================================//
//           Game scene definesana       //
//=======================================//
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

        //=======================================//
        //           check for game over         //
        //=======================================//
        if (gameOver) {
            inGameOverState = true;
            gc.setFill(Color.RED);
            gc.setFont(new Font("Cascadia Mono", 50));
            gc.fillText("GAME OVER", 180, 300);
            if (gameOverSoundPlayed) {
                backgroundMusic.playGameOverSound();
                gameOverSoundPlayed = false;
            }
            return;
        }

        gc.drawImage(backgroundImage, 0, 0, 650, 650);         // background

        for (int i = snake.size() - 1; i >= 1; i--) {       // čūskas ķermenis
            snake.get(i).x = snake.get(i - 1).x;            // katrai čūskas daļai piešķir iepriekšējās daļas koordinātas (Izņemot galvai)
            snake.get(i).y = snake.get(i - 1).y;
        }

        //=======================================//
        //      Cuska atrodas ieksa barjera      //
        //=======================================//
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

        //====================================================//
        //           Galva saskarās ar kadu no ojektiem       //
        //====================================================//
                //=====================//
                //         food        //
                //=====================//
        if (foodX == snake.get(0).x && foodY == snake.get(0).y) { //food
            snake.add(new SnakesBody(-1, -1));
            newFood();
            counter += 1;
        }
        if (powerUpX == snake.get(0).x && powerUpY == snake.get(0).y) {  //power up
            snake.add(new SnakesBody(-1, -1));
            newFood();
            counter += 1;
            Random random = new Random();
            int x = random.nextInt(2) + 1; // 1-4 power ups
                //=====================//
                //      power ups       //
                //=====================//
            if (x == 1) {
                speed -= 3;
                gc.setFill(Color.RED);
                gc.setFont(new Font("Cascadia Mono", 50));
                gc.fillText("slow motion", 180, 300);      // SLOW MOTION
                System.out.println("slow motion");

                PauseTransition pause = new PauseTransition(Duration.seconds(5));
                pause.setOnFinished(event -> {
                    System.out.println("Five seconds have passed! Now do something.");
                    speed += 3;
                });
                pause.play();
            }

            if (x == 2) {
                counter += 5;
                speed++;
                gc.setFill(Color.RED);
                gc.setFont(new Font("Cascadia Mono", 50));    // +5 POINTS
                gc.fillText("+5 points", 180, 300);
                System.out.println("+5 points");
            }
            if (x == 3){
                gc.setFill(Color.RED);
                gc.setFont(new Font("Cascadia Mono", 50));
                gc.fillText("FAKE POWER UP HAHA", 180, 300);  // FAKE POWER UP
                System.out.println("FAKE POWER UP HAHA");
                counter -=2;
            }
        }
                //=====================//
                //      barrier       //
                //=====================//
        if (barrierX == snake.get(0).x && barrierY == snake.get(0).y) {
            gameOver = true;
            stopBackgroundMusic();
            gameOverSoundPlayed = true;
        }

        for (int i = 1; i < snake.size(); i++) {         // ja čūska saskarās ar savu ķermeni
            if (snake.get(0).x == snake.get(i).x && snake.get(0).y == snake.get(i).y) {
                gameOver = true;
                stopBackgroundMusic();
                gameOverSoundPlayed = true;

            }
        }

        //=====================//
        //      score          //
        //=====================//
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("", 30));
        gc.fillText("Score: " + counter, 10, 30);


        //=======================================//
        //      cuska galva un ķermenis          //
        //=======================================//

        SnakesBody head = snake.get(0);         //apzime cuskas galvu
        gc.drawImage(headImage(), head.x * 25, head.y * 25, 30, 30); // izmers un novietojums

        for (int i = 1; i < snake.size(); i++) {      //apzime cuskas kermeni
            SnakesBody bodyPart = snake.get(i);
            double circleRadius = 12.5; // raidus
            double circleCenterX = bodyPart.x * 25 + 12.5; // centra novietojums
            double circleCenterY = bodyPart.y * 25 + 12.5; 

            if (PrimaryController.currentPlayer.getgender() == enumGender.male) {      //gender nosaka cuskas krasu
                gc.setFill(Color.LIGHTBLUE);
            } else {
                gc.setFill(Color.PINK);
            }

            gc.fillOval(circleCenterX - circleRadius, circleCenterY - circleRadius, 2 * circleRadius,
                    2 * circleRadius);
        }

        //========================================//
        //      powerup, ediens, barjera          //
        //=======================================//
        gc.drawImage(currentPowerUp, powerUpX * 25, powerUpY * 25, 40, 35);
        gc.drawImage(currentFruit, foodX * 25, foodY * 25, 25, 25);
        gc.drawImage(currentBarrier, barrierX * 25, barrierY * 25, 25, 25);
      
    }

    //================================//
    //            pause               //
    //================================//
    private static void pauseGame(Stage primaryStage) {
        if (pauseStage == null) {                            //ja pause ekrans jau nepastāv, tad to izveido
            pauseStage = new Stage();
            pauseStage.initModality(Modality.APPLICATION_MODAL);
            pauseStage.initStyle(StageStyle.UNDECORATED);

            VBox pauseBox = new VBox(20);            //izveido background / pogas
            pauseBox.setAlignment(Pos.CENTER);
            pauseBox.setPadding(new Insets(20));
            pauseBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8);");
            showInitialButtons(pauseBox, primaryStage);
            Scene pauseScene = new Scene(pauseBox, 300, 300);
            pauseStage.setScene(pauseScene);

            gamePaused = true;
            pauseStage.show();
            pauseStage.setOnHidden(event -> gamePaused = false);
        }
        pauseStage.show();                                  //parada pause stage
    }

//=======================================//
//           Original pause pogas        //
//=======================================//
    private static void showInitialButtons(VBox pauseBox, Stage primaryStage) {
        pauseBox.getChildren().clear();
        pauseBox.getChildren().addAll(
                createButton("Resume", primaryStage, pauseBox),
                createButton("Restart", primaryStage, pauseBox),
                createButton("Options", primaryStage, pauseBox),
                createButton("Quit", primaryStage, pauseBox));
    }

    
//============================================//
//           Options pogas iekš pause         //
//============================================//
    private static void showNewButtons(VBox pauseBox, Stage primaryStage) {
        pauseBox.getChildren().clear();         // izdzes jaunas pogas
        pauseBox.getChildren().addAll(          // izveido jaunas pogas
                createButton("Back", primaryStage, pauseBox),
                createButton("NewOption1", primaryStage, pauseBox),
                createButton("NewOption2", primaryStage, pauseBox),
                createButton("NewOption3", primaryStage, pauseBox));
            }
            private static Button createButton(String text, Stage primaryStage, VBox pauseBox) {   //pogas un to dizains
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
                        
                        button.setOnAction(e -> handleButtonAction(text, primaryStage, pauseBox)); // pogas pilda darbibu atkariba no teksta
        return button;
    }

    private static void handleButtonAction(String action, Stage primaryStage, VBox pauseBox) {     // pogu nosaukumi un funkciju izsauksana
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
                showInitialButtons(pauseBox, primaryStage);                 // parada original pogas
                
                break;
            }
        }
        
    //===================================================//
    //           Back poga aizved uz pause screen        //
    //===================================================//
        private static void closeAllStages() {
        for (Stage stage : openStages) {
            stage.close();
        }
    }
    //=========================================//
    //           aptur background music        //
    //=========================================//
        public static void stopBackgroundMusic() {
            backgroundMusic.stopMusic();
        }

    //===========================//
    //       reset game          //
    //===========================//
    private static void resetGame() {
        speed = 5;
        gameOver = false;
        snake.clear();
        direction = enumDirections.left;

        snake.add(new SnakesBody(10, 10));        // Reset cusku original laukuma
        snake.add(new SnakesBody(10, 10));        // Reset cusku original laukuma
        snake.add(new SnakesBody(10, 10));        // Reset cusku original laukuma
        counter -= (counter + 1);
        gameOverSoundPlayed = false;

        musicPlayer = new backgroundMusic(new String[] { "game1.wav", "game4.wav" });
        musicPlayer.BackgroundMusic(new String[] { "game1.wav", "game4.wav" }); 
    }

    //=====================//
    //       Options       //
    //=====================//
    private static void options(VBox pauseBox, Stage primaryStage) {
        showNewButtons(pauseBox, primaryStage);
    }

    //=======================================//
    //       Instrukcijas pirms spēles       //
    //=======================================//
    private static void drawInstructions(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.setFont(new Font("", 20));
        gc.fillText("Use AWSD or arrows to move", 180, 300);
        gc.fillText("Press SPACE to go to the next page", 180, 330);
        return;
    }

    //==========================================//
    //       spawn food, powerup, barrier       //
    //==========================================//
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
            currentFruit = generateNewFruit();                //spawn kadu no objektiem
            currentPowerUp = generateNewPowerUp();            //spawn kadu no objektiem
            currentBarrier = generateNewBarrier();            //spawn kadu no objektiem
        
            speed += 0.7;  //speed
            backgroundMusic.playPickupSound();   //background music
            break;
        }
    }

    //==========================================//
    //           izveido jaunu barjeru          //
    //==========================================//
    private static Image generateNewBarrier() {
        return barjera;
    }

    //==========================================//
    //           izveido jaunu power up         //
    //==========================================//
    private static Image generateNewPowerUp() {
        return powerup;
    }

    //==========================================//
    //           izveido jaunu augli            //
    //==========================================//
    private static Image generateNewFruit() {
        Image[] fruits = { abols, banans, vinogas, zemene };
        Random random = new Random();
        int randomIndex = random.nextInt(fruits.length);
        return fruits[randomIndex];
    }

    //================================================//
    //           cuskas galvas custom attēls          //
    //================================================//
    public static Image headImage() {
        String headFileName;

        if (PrimaryController.currentPlayer.getgender() == enumGender.male) {
            headFileName = "head.png"; // male galva
        } else {
            headFileName = "head.png"; // female galva
        }

        return new Image(Game.class.getResource(headFileName).toExternalForm());
    }

    //============================//
    //           restart          //
    //============================//
        public void restart() {
        resetGame();
        gamePaused = false;
        gameOver = false;
        gameOverSoundPlayed = false;
        inGameOverState = false;
        gameStarted = true;
        showInstructions = false;

    }

    public static void main(String[] args) {
        launch(args);
    }
}
