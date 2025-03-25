package models.tiles;

import graphics.Graphics;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile {
    private boolean isSolid;
    private int x;
    private int y;
    private Rectangle hitbox;
    private Node hitboxNode;
    private int width;
    private int height;
    private ImageView imageView;
    private String imageName;

    public Tile(boolean isSolid, int x, int y, int width, int height, String imageName) {
        this.isSolid = isSolid;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.imageName = imageName;
        this.imageView = new ImageView(new Image(Graphics.getGraphicUrl(this.imageName)));
        this.imageView.setX(x);
        this.imageView.setY(y);
        this.imageView.setFitHeight(height);
        this.imageView.setFitWidth(width);   
        this.hitbox = new Rectangle(x , y, width, height);
        this.hitboxNode = hitbox;
    }

    public Tile(boolean isSolid, int x, int y, int width, int height) {
        this.isSolid = isSolid;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height; 
        this.hitbox = new Rectangle(x , y, width, height);
        this.hitboxNode = hitbox;
        this.hitbox.setFill(Color.BLACK);

    }

    //#region getter & setter
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
        this.hitbox.setX(x);
        this.imageView.setX(x);
    }

    public void setY(int y) {
        this.y = y;
        this.hitbox.setY(y);
        this.imageView.setY(y);
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

    public void setImageView(ImageView image) {
        this.imageView = image;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
    //#endregion
}
