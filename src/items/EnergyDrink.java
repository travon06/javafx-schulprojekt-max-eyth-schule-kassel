package items;

import graphics.Graphics;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import models.entities.Player;
import utils.config.ConfigArguments;

public class EnergyDrink extends Item {
    public EnergyDrink(String name, int x, int y) {
        super(name, x, y);
    }

    public EnergyDrink(String name, int x, int y, String imageName) {
        super(name, x, y, imageName);
        // this.getHitbox().setVisible(true);
    }

    @Override
    public void use(Pane pane, Player player) {
        player.setBoosted(true);
        
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