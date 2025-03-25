package items;

import java.util.ArrayList;

import javafx.scene.layout.Pane;
import models.Gate;
import models.entities.Player;

public class Key extends Item{
    public Key(String name, int x, int y, boolean isItemToCollect) {
        super(name, x, y, isItemToCollect);
    }

    public Key(String name, int x, int y, String imageName, boolean isItemToCollect) {
        super(name, x, y, imageName, isItemToCollect);
    }
    @Override
    public void use(Pane pane, Player player, ArrayList<Gate> gates) {
        for(Gate gate : gates) {
            if(gate.getOpeningKey() == this) {
                gate.setAccessible(true);
            }
        }
    }
}
