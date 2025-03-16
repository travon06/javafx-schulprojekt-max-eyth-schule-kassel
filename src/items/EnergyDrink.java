package items;

import java.io.File;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import models.Gate;
import models.entities.Player;
import utils.config.ConfigArguments;

public class EnergyDrink extends Item {
    private MediaPlayer mediaPlayer;
    public EnergyDrink(String name, int x, int y) {
        super(name, x, y);
        String soundPath = new File("src/sounds/drinkEnergy.mp3").toURI().toString();
        Media sound = new Media(soundPath);
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume(ConfigArguments.getVolume());
    }

    public EnergyDrink(String name, int x, int y, String imageName) {
        super(name, x, y, imageName);
        String soundPath = new File("src/sounds/drinkEnergy.mp3").toURI().toString();
        Media sound = new Media(soundPath);
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume(ConfigArguments.getVolume());
    }

    @Override
    public void use(Pane pane, Player player, ArrayList<Gate> gates) {
        player.setBoosted(true);
        mediaPlayer.play();
        
        AnimationTimer timer = new AnimationTimer() {
            private long startTime = -1;
            long duration = Long.parseLong(ConfigArguments.getConfigArgumentValue("ENERGY_DRINK_DURATION")) * 1_000_000_000;

            @Override
            public void handle(long now) {
                if(startTime == -1) {
                    startTime = now;
                }

                if(now - startTime >= duration) {
                    player.setBoosted(false);
                    stop();
                }   

            }   
        };
        timer.start();
    }
}