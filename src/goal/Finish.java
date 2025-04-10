package goal;
import java.util.ArrayList;

import graphics.Graphics;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import models.CountdownTimer;
import items.Item;


public class Finish {
    private int x;
    private int y;
    private int width;
    private int height;
    private Rectangle hitbox;
    private boolean isAccessible;
    private ImageView imageView;
    private ArrayList<Item> itemsToCollect;
    private final String goal;
    private String graphicName;

    public Finish(int x, int y, int width, int height, String goal) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.hitbox = new Rectangle(width, height);
        this.hitbox.setX(x);
        this.hitbox.setY(y);
        this.hitbox.setFill(Color.YELLOW);
        this.isAccessible = false;
        this.goal = goal;
        this.imageView = null;
    }

    public Finish(int x, int y, int width, int height, String goal, String imageName) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.hitbox = new Rectangle(width, height);
        this.hitbox.setX(x);
        this.hitbox.setY(y);
        this.hitbox.setFill(Color.YELLOW);
        this.hitbox.setVisible(false);
        this.isAccessible = false;
        this.imageView = new ImageView(new Image(Graphics.getGraphicUrl(imageName)));
        this.imageView.setX(x);
        this.imageView.setY(y);
        this.imageView.setFitWidth(width);
        this.imageView.setFitHeight(height);
        this.goal = goal;
        this.graphicName = imageName;
    }

    public void checkState(CountdownTimer countdownTimer) {
        if(goal.equals("COLLECT_ITEMS")) {
            if(this.getItemsToCollect().size() == 0) {
                this.isAccessible = true;
            } else {
                this.isAccessible = false;
            }
        } else if (goal.equals("SURVIVE")) {
            if(countdownTimer.isDone()) {
                this.isAccessible = true;
            } else {
                this.isAccessible = false;
            }
        }
    }

    //#region getter & setter

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }

    public Rectangle getHitbox() {
        return hitbox;
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

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public void setAccessible(boolean isAccessible) {
        this.isAccessible = isAccessible;
    }

    public Boolean getAccessible() {
        return this.isAccessible;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void setItemsToCollect(ArrayList<Item> itemsToCollect) {
        this.itemsToCollect = itemsToCollect; 
    }

    public ArrayList<Item> getItemsToCollect() {
        return itemsToCollect;
    }

    public String getGoal() {
        return goal;
    }

    public String getGraphicName() {
        return graphicName;
    }

    public void setGraphicName(String graphicName) {
        this.graphicName = graphicName;
    }

    //#endregion
}
