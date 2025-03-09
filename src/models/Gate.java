package models;

import items.Key;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Gate {
    private int x;
    private int y;
    private int width;
    private int height;
    private Key openingKey;
    private ImageView imageView;
    private Rectangle hitbox;
    private boolean open;
    private boolean accessible;

    public Gate(int x, int y, int width, int height, ImageView imageView) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.openingKey = openingKey;
        this.imageView = imageView;
        hitbox = new Rectangle(width, height);
        hitbox.setFill(Color.GRAY);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        hitbox.setX(x);
        hitbox.setY(y);
        imageView.setX(x);
        imageView.setY(y);
        this.open = false;
        this.accessible = false;
    }

    //#region getter & setter

    public void setOpeningKey(Key openingKey) {
        this.openingKey = openingKey;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public Key getOpeningKey() {
        return openingKey;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean getOpen() {
        return this.open;
    }

    public void setAccessible(boolean accessible) {
        this.accessible = accessible;
    }

    public boolean getAccessible() {
        return this.accessible;
    }

    //#endregion

    
}
