package lv.venta;

import javafx.scene.image.Image;

public class Food {

    // spawn food, powerup, barrier
    public static void newFood() {
        start: while (true) {
            Game.food1X = Game.rand.nextInt(24);
            Game.food1Y = Game.rand.nextInt(24);

            Game.food2X = Game.rand.nextInt(24);
            Game.food2Y = Game.rand.nextInt(24);

            Game.food2X = Game.rand.nextInt(24);
            Game.food2Y = Game.rand.nextInt(24);

            Game.powerUpX = Game.rand.nextInt(24);
            Game.powerUpY = Game.rand.nextInt(24);

            Game.barrierX = Game.rand.nextInt(24);
            Game.barrierY = Game.rand.nextInt(24);

            for (SnakesBody c : Game.snake) {
                if (c.x == Game.food1X && c.y == Game.food1Y || c.x == Game.food2X && c.y == Game.food2Y
                        || c.x == Game.food2X && c.y == Game.food2Y) {
                    continue start;
                }
            }
            Game.currentFruit1 = Game.generateNewFruit();
            Game.currentFruit2 = Game.generateNewFruit();
            Game.currentFruit3 = Game.generateNewFruit();    // spawn kadu no objektiem
            Game.currentPowerUp = Game.generateNewPowerUp(); // spawn kadu no objektiem
            Game.currentBarrier = Game.generateNewBarrier(); // spawn kadu no objektiem

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
