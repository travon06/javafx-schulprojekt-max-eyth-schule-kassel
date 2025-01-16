package models;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class Item {
    private final String name;
    private final int size;
    private final Rectangle hitbox;
    private boolean isAccessible;
    private int x;
    private int y;
    private Node node;
    private ImageView image;

    public Item(String name, int size, int startX, int startY) {
        this.name = formatName(name);
        this.size = size;
        this.isAccessible = false;
        this.hitbox = new Rectangle(25, 25);
        this.x = startX;
        this.y = startY;
        this.hitbox.setX(startX);
        this.hitbox.setY(startY);
        this.node = this.hitbox;

        this.image = new ImageView(new Image("../sprites/key.png"));
    }

    private String formatName(String name) {
        return name.replace("_", " ");
    }

    @Override
    public String toString() {
        return String.format("%s(%d | %d)", this.name, this.x, this.y);
    }

    //#region getter & setter
    public String getName() {
        return this.name;
    }

    public int getSize() {
        return this.size;
    }

    public boolean getIsAccessible() {
        return this.isAccessible;
    }

    public void setIsAccessible(boolean isAccessible) {
        this.isAccessible = isAccessible;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
        this.hitbox.setX(x);
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        this.hitbox.setY(y);

    }

    public Node getNode() {
        return node;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public ImageView getImage() {
        return image;
    }
    //#endregion
    
}
