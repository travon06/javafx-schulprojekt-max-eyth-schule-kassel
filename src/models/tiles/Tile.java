package models.tiles;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile {
    private boolean isSolid;
    private double x;
    private double y;
    private Rectangle hitbox;
    private Node hitboxNode;
    private int width;
    private int height;

    public Tile(boolean isSolid, double x, double y, int width, int height) {
        this.isSolid = isSolid;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.hitbox = new Rectangle(x , y, width, height);
        this.hitboxNode = hitbox;
        this.hitbox.setFill(Color.BLACK);

    }

    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setSolid(boolean isSolid) {
        this.isSolid = isSolid;
    }

    public boolean getIsSolid() {
        return this.isSolid;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void setHitboxNode(Node hitboxNode) {
        this.hitboxNode = hitboxNode;
    }

    public Node getHitboxNode() {
        return hitboxNode;
    }
}
