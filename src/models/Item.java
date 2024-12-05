package models;

import javafx.scene.shape.Rectangle;

public class Item {
    private final String name;
    private final int size;
    private final Rectangle hitbox;
    private boolean isAccessible;
    private int x;
    private int y;

    public Item(String name, int size, int startX, int startY) {
        this.name = name;
        this.size = size;
        this.isAccessible = false;
        this.hitbox = new Rectangle(25, 25);
        this.x = startX;
        this.y = startY;
        this.hitbox.setX(startX);
        this.hitbox.setY(startY);
    }

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
}
