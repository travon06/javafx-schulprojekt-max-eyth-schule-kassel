package items;

import javafx.scene.layout.Pane;
import models.entities.Player;

public class Key extends Item{
    public Key(String name, int x, int y) {
        super(name, x, y);
    }

    public Key(String name, int x, int y, String imageName) {
        super(name, x, y, imageName);
    }
    @Override
    public void use(Pane pane, Player player) {
        System.out.println("key used");
    }
}
