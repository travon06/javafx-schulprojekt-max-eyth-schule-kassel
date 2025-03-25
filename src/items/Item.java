package items;

import java.util.ArrayList;

import graphics.Graphics;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import models.Gate;
import models.entities.Player;

public abstract class Item {
    private final String name;
    private final String originalName;
    private final Rectangle hitbox;
    private boolean isAccessible;
    private int x;
    private int y;
    private Node node;
    private ImageView imageView;
    private String imageName;
    private boolean isItemToCollect;

    public Item(String name, int startX, int startY, boolean isItemToCollect) {
        this.name = formatName(name);
        this.originalName = name;
        this.isAccessible = false;
        this.hitbox = new Rectangle(25, 25);
        this.x = startX;
        this.y = startY;
        this.hitbox.setX(startX);
        this.hitbox.setY(startY);
        this.node = this.hitbox;
        this.isItemToCollect = isItemToCollect;
    }

    public Item(String name, int startX, int startY, String imageName, boolean isItemToCollect) {
        this.name = formatName(name);
        this.originalName = name;
        this.imageName = imageName;
        this.isAccessible = false;
        this.hitbox = new Rectangle(25, 25);
        this.node = this.hitbox;
        this.imageView = new ImageView(new Image(Graphics.getGraphicUrl(imageName)));
        this.imageView.setFitHeight(25);
        this.imageView.setFitWidth(25);
        this.setX(startX);
        this.setY(startY);
        this.isItemToCollect = isItemToCollect;
        this.hitbox.setVisible(false);
    }

    private String formatName(String name) {
        return name.replace("_", " ");
    }

    @Override
    public String toString() {
        return String.format("%s(%d | %d)", this.name, this.x, this.y);
    }

    public abstract void use(Pane pane, Player player, ArrayList<Gate> gates);

    //#region getter & setter
    public String getName() {
        return this.name;
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
        this.imageView.setX(x);
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        this.hitbox.setY(y);
        this.imageView.setY(y);

    }

    public Node getNode() {
        return node;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void setAccessible(boolean isAccessible) {
        this.isAccessible = isAccessible;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setItemToCollect(boolean isItemToCollect) {
        this.isItemToCollect = isItemToCollect;
    }

    public boolean getIsItemToCollect() {
        return this.isItemToCollect;
    }

    
    //#endregion
    
}
