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

public class Coat extends Item {
    private MediaPlayer mediaPlayer;
    public Coat(String name, int x, int y) {
        super(name, x, y);
        String soundPath = new File("src/sounds/sus.mp3").toURI().toString();
        Media sound = new Media(soundPath);
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume(1.0);

    }

    public Coat(String name, int x, int y, String imagename) {
        super(name, x, y, imagename);
        this.getImageView().setFitWidth(this.getHitbox().getWidth() * 2);
        this.getImageView().setFitHeight(this.getHitbox().getHeight() * 2);
        this.getImageView().setX(this.getHitbox().getX() - this.getHitbox().getWidth() / 2);
        this.getImageView().setY(this.getHitbox().getY() - this.getHitbox().getHeight() / 2);
        String soundPath = new File("src/sounds/sus.mp3").toURI().toString();
        Media sound = new Media(soundPath);
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume(ConfigArguments.getVolume());
    }

    @Override
    public void use(Pane pane, Player player, ArrayList<Gate> gates) {
        player.setVissible(false);
        player.getImage().setOpacity(0.5);
        mediaPlayer.play();
            
        AnimationTimer timer = new AnimationTimer() {
            private long startTime = -1;    
            long duration = Long.parseLong(ConfigArguments.getConfigArgumentValue("COAT_DURATION")) * 1_000_000_000;
            @Override
            public void handle(long now) {
                if(startTime == -1) {
                    startTime = now;
                }
                
                if(now - startTime >= duration) {
                    player.setVissible(true);
                    player.getImage().setOpacity(1);
                    stop();
                }
    
            }   
        };
        timer.start();
    }
}
