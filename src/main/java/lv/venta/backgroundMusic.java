package lv.venta;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class backgroundMusic {

    // mainīgie
    private static MediaPlayer mediaPlayer;
    private static MediaPlayer menuSong;
    private String[] musicFilePaths;
    static double volume = 0.5;

    
    public backgroundMusic(String[] strings) {
        musicFilePaths = strings;
    }

    // atjaunina skaļumu
    public static void updateVolume() {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
        }
    }

    // palaiž background mūziku
    public void BackgroundMusic(String[] musicFilePathsInput) {
        musicFilePaths = musicFilePathsInput;
        playRandomMusic();
    }

    // palaiž nejauši izvēlēto mūzikas failu
    private void playRandomMusic() {
    	stopMusic();
        String randomMusicFile = getRandomMusicFile();
        Media sound = new Media(getClass().getResource(randomMusicFile).toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume(volume);
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
        mediaPlayer.play();
       
    }

    // aptur mūziku
    public static void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        if (menuSong != null) {
            menuSong.stop();
        }
    }
    
    // palaiž start menu/register mūziku
    public static void playMenuMusic() {
        Media sound = new Media(backgroundMusic.class.getResource("gameHz.wav").toString());
        menuSong = new MediaPlayer(sound);
        menuSong.setVolume(volume * 0.2);
        menuSong.play();
        
    }
    
   // palaiž pogas nospiešanas skaņu
    public static void PlayButtonSound() {
    	Media sound = new Media(backgroundMusic.class.getResource("buttonSound.wav").toString());
        MediaPlayer buttonSoundPlayed = new MediaPlayer(sound);
        buttonSoundPlayed.setVolume(volume * 0.5);
        buttonSoundPlayed.setCycleCount(1);
        buttonSoundPlayed.play();
    }
    
    // // palaiž augļu pieskaršanās skaņu
    public static void playPickupSound() {
        Media sound = new Media(backgroundMusic.class.getResource("pickupsound.wav").toString());
        MediaPlayer pickupSoundPlayer = new MediaPlayer(sound);
        pickupSoundPlayer.setVolume(volume * 0.5);
        pickupSoundPlayer.play();
    }

    // palaiž zveigznes pieskaršanās skaņu
    public static void playStarSound() {
        Media sound = new Media(backgroundMusic.class.getResource("starSound2.wav").toString());
        MediaPlayer starSoundPlayer = new MediaPlayer(sound);
        starSoundPlayer.setVolume(volume * 0.5);
        starSoundPlayer.play();
    }

    // // palaiž "bomb" pieskaršanās skaņu
    public static void playBombSound() {
        Media sound = new Media(backgroundMusic.class.getResource("bombSound.wav").toString());
        MediaPlayer bombSoundPlayer = new MediaPlayer(sound);
        bombSoundPlayer.setVolume(volume * 0.5);
        bombSoundPlayer.play();
    }

    // palaiž monētas pieskaršanās skaņu
    public static void playCoinSound() {
        Media sound = new Media(backgroundMusic.class.getResource("coinSound.wav").toString());
        MediaPlayer coinSoundPlayer = new MediaPlayer(sound);
        coinSoundPlayer.setVolume(volume * 0.5);
        coinSoundPlayer.play();
    }

    // palaiž barjeras pieskaršanās skaņu
    public static void playBarrierSound() {
        Media sound = new Media(backgroundMusic.class.getResource("barrierSound.wav").toString());
        MediaPlayer barrierSoundPlayer = new MediaPlayer(sound);
        barrierSoundPlayer.setVolume(volume * 0.5);
        barrierSoundPlayer.play();
    }
    
    // palaiž spēles beigu skaņu
    public static void playGameOverSound() {
    	stopMusic();
        Media sound = new Media(backgroundMusic.class.getResource("gameOver.wav").toString());
        MediaPlayer gameOverSoundPlayer = new MediaPlayer(sound);
        gameOverSoundPlayer.setVolume(volume * 0.5);
        gameOverSoundPlayer.setCycleCount(1);
        gameOverSoundPlayer.play();
    }

    // palaiž nejaušu mūzikas failu
    private String getRandomMusicFile() {
        return musicFilePaths[0];
    }
}
