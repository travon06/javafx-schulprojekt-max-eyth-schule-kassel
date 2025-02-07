package items;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import models.entities.Player;
import utils.config.ConfigArguments;

public class Coat extends Item {
    public Coat(String name, int x, int y) {
        super(name, x, y);

    }

    public Coat(String name, int x, int y, String imagename) {
        super(name, x, y, imagename);
        this.getImageView().setFitWidth(this.getHitbox().getWidth() * 2);
        this.getImageView().setFitHeight(this.getHitbox().getHeight() * 2);
        this.getImageView().setX(this.getHitbox().getX() - this.getHitbox().getWidth() / 2);
        this.getImageView().setY(this.getHitbox().getY() - this.getHitbox().getHeight() / 2);
    }

    @Override
    public void use(Pane pane, Player player) {
        player.setVissible(false);
        player.getImage().setOpacity(0.5);
            
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
