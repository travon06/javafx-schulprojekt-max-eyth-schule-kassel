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

public class ItemToCollect extends Item {
    public ItemToCollect(String name, int x, int y, boolean isItemToCollect) {
        super(name, x, y, isItemToCollect);
    }

    public ItemToCollect(String name, int x, int y, String imageName, boolean isItemToCollect) {
        super(name, x, y, imageName, isItemToCollect);
    }

    @Override
    public void use(Pane pane, Player player, ArrayList<Gate> gates) {
        return;
    }
}