package utils;

import graphics.Graphics;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Waypoint {
    private int x;
    private int y;
    private boolean allowVerticalMovement;
    private ImageView imageView;
    private int myPolicemanId;

    public Waypoint(int x, int y) {
        this.x = x;
        this.y = y;
        this.allowVerticalMovement = false;
    }

    public Waypoint(int x, int y, boolean allowVerticalMovement) {
        this.x = x;
        this.y = y;
        this.allowVerticalMovement = allowVerticalMovement;
    }

    public Waypoint(int x, int y, boolean allowVerticalMovement, String imageName, int myPolicemanId) {
        this.x = x;
        this.y = y;
        this.allowVerticalMovement = allowVerticalMovement;
        this.imageView = new ImageView(new Image(Graphics.getGraphicUrl(imageName)));
        this.imageView.setFitWidth(50);
        this.imageView.setFitHeight(50);
        this.imageView.setX(x - 25);
        this.imageView.setY(y - 25);
        this.myPolicemanId = myPolicemanId;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setAllowVerticalMovement(boolean allowVerticalMovement) {
        this.allowVerticalMovement = allowVerticalMovement;
    }

    public boolean getAllowVerticalMovement() {
        return this.allowVerticalMovement;
    }

    public ImageView getImageView() {
        return imageView;
    }

}
