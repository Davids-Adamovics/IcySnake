package lv.venta;

import java.util.Random;

import javafx.scene.image.Image;

public class Food {
    static int barriersDodgedCounter = 0;

    // spawn ēdienus, powerups, barjeras
    public static void newFood() {
        start: while (true) {
            if (Buttons.dificulty == 2) {          // Objekti atrodas 23x21 laukumā
                Game.food1X = Game.rand.nextInt(23);
                Game.food1Y = Game.rand.nextInt(19) + 2; // nevar būt mazāks par 2, jo tur atrodas punktu lauks

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
            else{   //retro versijas auglis
                Game.food1X = Game.rand.nextInt(23);
                Game.food1Y = Game.rand.nextInt(19) + 2; 
            }

            for (SnakesBody snakeB : Game.snake) {
                if (snakeB.x == Game.food1X && snakeB.y == Game.food1Y || snakeB.x == Game.food2X && snakeB.y == Game.food2Y
                        || snakeB.x == Game.food2X && snakeB.y == Game.food2Y || snakeB.x == Game.food3X && snakeB.y == Game.food3Y) {
                    continue start;
                }
            }

            if(Buttons.dificulty == 2){             // power up režīmā spawnojas objektu robežas X Y
            Game.willBarrierSpawn = Game.random.nextInt(3) + 1;
            Game.currentFruit1 = Game.generateNewFruit();
            Game.currentFruit2 = Game.generateNewFruit();
            Game.currentFruit3 = Game.generateNewFruit(); 
            Game.currentPowerUp = Game.generateNewPowerUp(); 
            Game.currentBomb = Game.generateNewBomb(); 
            Game.currentPlus5 = Game.generateNewPlus5(); 

            if (Game.willBarrierSpawn == 1) {      // Ja barjerai ir atļauts nospawnoties, konsolē to izvada 
                Game.currentBarrier = Game.generateNewBarrier(); 
                System.out.println("Barrier spawned");
                barriersDodgedCounter++;          // Pieskaita +1 pie barriersDodgedCounter priekš achivement

            } else {
                System.out.println("Barrier not spawned!");   // savādāk barjera nespawnojas
            }
        }

        else{
            Game.currentFruit1 = Game.generateNewFruit();
        }
            Game.speed++; // speed palielinās par +1
            backgroundMusic.playPickupSound(); // augļa apēšanas skaņa
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
