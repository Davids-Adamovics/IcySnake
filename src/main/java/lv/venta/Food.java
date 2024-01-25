package lv.venta;

import java.util.Random;

import javafx.scene.image.Image;

public class Food {
    static int barriersDodgedCounter = 0;

    // spawn food, powerup, barrier
    public static void newFood() {
        start: while (true) {
            if (Buttons.dificulty == 2) {
                Game.food1X = Game.rand.nextInt(23);
                Game.food1Y = Game.rand.nextInt(19) + 2; 

                Game.food2X = Game.rand.nextInt(23);
                Game.food2Y = Game.rand.nextInt(19) + 2; 

                Game.food3X = Game.rand.nextInt(23);
                Game.food3Y = Game.rand.nextInt(19) + 2; 

                Game.powerUpX = Game.rand.nextInt(23);
                Game.powerUpY = Game.rand.nextInt(19) + 2; 

                Game.bombX = Game.rand.nextInt(23);
                Game.bombY = Game.rand.nextInt(19) + 2; 

                Game.plus5X = Game.rand.nextInt(23);
                Game.plus5Y = Game.rand.nextInt(19) + 2; 
                if (Game.willBarrierSpawn == 1) {
                    Game.barrierX = Game.rand.nextInt(23);
                    Game.barrierY = Game.rand.nextInt(19) + 2; 
                }
            }
            else{
                Game.food1X = Game.rand.nextInt(23);
                Game.food1Y = Game.rand.nextInt(19) + 2; 
            }

            for (SnakesBody c : Game.snake) {
                if (c.x == Game.food1X && c.y == Game.food1Y || c.x == Game.food2X && c.y == Game.food2Y
                        || c.x == Game.food2X && c.y == Game.food2Y) {
                    continue start;
                }
            }
            if(Buttons.dificulty == 2){
            Game.willBarrierSpawn = Game.random.nextInt(3) + 1;
            Game.currentFruit1 = Game.generateNewFruit();
            Game.currentFruit2 = Game.generateNewFruit();
            Game.currentFruit3 = Game.generateNewFruit(); 
            Game.currentPowerUp = Game.generateNewPowerUp(); 
            Game.currentBomb = Game.generateNewBomb(); 
            Game.currentPlus5 = Game.generateNewPlus5(); 

            if (Game.willBarrierSpawn == 1) {
                Game.currentBarrier = Game.generateNewBarrier(); 
                System.out.println("Barrier spawned");
                barriersDodgedCounter++;

            } else {
                System.out.println("Barrier not spawned!");
            }
        }
        else{
            Game.currentFruit1 = Game.generateNewFruit();
        }
            Game.speed++; // speed
            backgroundMusic.playPickupSound(); // background music
            break;
        }
    }

    // izveido jaunu augli
    static Image generateNewFruit() {
        Image[] fruits = { Game.abols, Game.banans, Game.vinogas, Game.zemene };
        int randomIndex = Game.rand.nextInt(fruits.length);
        return fruits[randomIndex];
    }

}
