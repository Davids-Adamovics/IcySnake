package lv.venta;

import java.util.Random; // importē random bibliotēku

import javafx.scene.image.Image;

public class Food {
    static int barriersDodgedCounter = 0; // izvairīto barjeru skaits priekš achivements
    private static Image [] fruits; // mainīgais fruits

    public Food(Image [] images) {  // setters, kas norāda, ka game saņemtais array ir fruits
        fruits = images;
    }


    // spawn ēdienus, powerups, barjeras
    public static void newFood() {
        start: while (true) { //kamēr spēle ir aktīva
            if (Buttons.dificulty == 2) { // Objekti atrodas 23x21 laukumā POWERUP režīmā
                Game.food1X = Game.rand.nextInt(23); 
                Game.food1Y = Game.rand.nextInt(19) + 2; // nevar būt mazāks par 2, jo tur atrodas punktu lauks

                Game.food2X = Game.rand.nextInt(23); // ģenerē X koordinātas
                Game.food2Y = Game.rand.nextInt(19) + 2;// ģenerē Y koordinātas

                Game.food3X = Game.rand.nextInt(23);
                Game.food3Y = Game.rand.nextInt(19) + 2;

                Game.powerUpX = Game.rand.nextInt(23);
                Game.powerUpY = Game.rand.nextInt(19) + 2;

                Game.bombX = Game.rand.nextInt(23);
                Game.bombY = Game.rand.nextInt(19) + 2;

                Game.plus5X = Game.rand.nextInt(23);
                Game.plus5Y = Game.rand.nextInt(19) + 2;

                if (Game.willBarrierSpawn == 1) { // vai barrier spawnojas 
                    Game.barrierX = Game.rand.nextInt(23);
                    Game.barrierY = Game.rand.nextInt(19) + 2;
                }
            } else { // retro versijas auglis
                Game.food1X = Game.rand.nextInt(23); // ja nav iepriekšējais IF true / ja ir retro versija
                Game.food1Y = Game.rand.nextInt(19) + 2;
            }

            for (SnakesBody cuskasKermenaDala : Game.snake) { // iet caur Game.snake arraylist
                if (cuskasKermenaDala.x == Game.food1X && cuskasKermenaDala.y == Game.food1Y // pārbauda, vai kāda no ķermeņa daļām nesakrīt ar augļa koordinātām
                        || cuskasKermenaDala.x == Game.food2X && cuskasKermenaDala.y == Game.food2Y
                        || cuskasKermenaDala.x == Game.food3X && cuskasKermenaDala.y == Game.food3Y) {
                    continue start;
                }
            }

            if (Buttons.dificulty == 2) { // power up režīmā spawnojas objektu robežas X Y
                Game.willBarrierSpawn = Game.random.nextInt(3) + 1; // vai barjera spawnojas 1,2,3 
                Game.currentFruit1 = Game.generateNewFruit(); // objekta X un Y
                Game.currentFruit2 = Game.generateNewFruit();// objekta X un Y
                Game.currentFruit3 = Game.generateNewFruit();// objekta X un Y
                Game.currentPowerUp = Game.generateNewPowerUp();// objekta X un Y
                Game.currentBomb = Game.generateNewBomb();// objekta X un Y
                Game.currentPlus5 = Game.generateNewPlus5();// objekta X un Y

                if (Game.willBarrierSpawn == 1) { // Ja barjerai ir atļauts nospawnoties, konsolē to izvada
                    Game.currentBarrier = Game.generateNewBarrier();// objekta X un Y
                    System.out.println("Barrier spawned");
                    barriersDodgedCounter++; // Pieskaita +1 pie barriersDodgedCounter priekš achivement

                } else {
                    System.out.println("Barrier not spawned!"); // savādāk barjera nespawnojas
                }
            }

            else { // Retro režīmā spawnojas tikai 1 auglis
                Game.currentFruit1 = Game.generateNewFruit();
            }
            Game.speed++; // speed palielinās par +1
            backgroundMusic.playPickupSound(); // augļa apēšanas skaņa
            break;
        }
    }

    // izveido jaunu augli
    static Image generateNewFruit() {
        int randomIndex = Game.rand.nextInt(fruits.length); // izvēlas vienu no 4 augļiem 
        return fruits[randomIndex];
    }

}
