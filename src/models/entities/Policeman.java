package models.entities;

import java.util.ArrayList;

import graphics.Graphics;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import utils.Waypoint;


public class Policeman {
    private double speed;
    private int hitboxBounds;
    private Rectangle hitbox;
    private int visionRange;
    private Node hitboxNode;
    private Node visionNode;
    private double x;
    private double y;
    private ArrayList<Waypoint> waypoints;
    private int currentWaypointIndex;
    private ImageView imageView;

    public Policeman(double speed, int hitboxBounds, int startX, int startY, String imageName) {
        this.speed = speed;
        this.hitboxBounds = hitboxBounds;
        this.hitbox = new Rectangle(hitboxBounds, hitboxBounds, Color.DARKBLUE);
        this.hitbox.setVisible(false);
        this.hitboxNode = hitbox;
        this.x = startX;
        this.y = startY;
        this.hitbox.setX(startX);
        this.hitbox.setY(startY);
        this.currentWaypointIndex = 0;
        this.waypoints = new ArrayList<>();
        this.imageView = new ImageView(new Image(Graphics.getGraphicUrl(imageName)));
        this.imageView.setFitHeight(this.hitboxBounds);
        this.imageView.setFitWidth(this.hitboxBounds);
    }

    public void followPath(Pane pane) {
        if (waypoints.isEmpty()) return;
        
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), e -> {
            Waypoint waypoint = waypoints.get(currentWaypointIndex);

            moveToWaypoint(waypoint);

            if(Math.abs(this.x - waypoint.getX()) < this.speed && Math.abs(this.y - waypoint.getY()) < this.speed)  {
                this.setX(waypoint.getX());
                this.setY(waypoint.getY());
                currentWaypointIndex = (currentWaypointIndex + 1) % waypoints.size();
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void moveToWaypoint(Waypoint waypoint) {
        if (waypoint.getAllowVerticalMovement()) {
            if (this.x < waypoint.getX()) moveRight();
            if (this.y < waypoint.getY()) moveDown();
            if (this.x > waypoint.getX()) moveLeft();
            if (this.y > waypoint.getY()) moveUp();
        } else {
            if (this.x != waypoint.getX()) {
                if (this.x < waypoint.getX()) moveRight();
                if (this.x > waypoint.getX()) moveLeft();
            } else if (this.y != waypoint.getY()) {
                if (this.y < waypoint.getY()) moveDown();
                if (this.y > waypoint.getY()) moveUp();
            }
        }
    }

    
    public void moveRight() {
        setX(this.x + speed);
        this.imageView.setRotate(270);
    }

    public void moveDown() {
        setY(this.y + speed);
        this.imageView.setRotate(0);
    }

    public void moveLeft() {
        setX(this.x - speed);
        this.imageView.setRotate(90);
    }

    public void moveUp() {
        setY(this.y - speed);
        this.imageView.setRotate(180);
    }

    //#region getter & setter
    public Rectangle getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getVisionRange() {
        return visionRange;
    }

    public void setVisionRange(int visionRange) {
        this.visionRange = visionRange;
    }

    public Node getHitboxNode() {
        return hitboxNode;
    }

    public void setHitboxNode(Node node) {
        this.hitboxNode = node;
    }

    public void setX(double x) {
        // this.x = x - this.hitboxBounds / 2;
        // this.hitbox.setX(x - this.hitboxBounds / 2);
        // this.imageView.setX(x - this.hitboxBounds / 2);
        this.x = x ;
        this.hitbox.setX(x);
        this.imageView.setX(x);
    }

    public double getX() {
        return x;
    }

    public void setY(double y) {
        // this.y = y - this.hitboxBounds / 2;
        // this.hitbox.setY(y - this.hitboxBounds / 2);
        // this.imageView.setY(y - this.hitboxBounds / 2);
        this.y = y;
        this.hitbox.setY(y);
        this.imageView.setY(y);
    }

    public double getY() {
        return y;
    }

    public ArrayList<Waypoint> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(ArrayList<Waypoint> waypoints) {
        this.waypoints = waypoints;
    }

    public void addWaypoint(Waypoint waypoint) {
        this.waypoints.add(waypoint);
    }

    public void setVisionNode(Node visionNode) {
        this.visionNode = visionNode;
    }

    public Node getVisionNode() {
        return visionNode;
    }

    public void setHitboxBounds(int hitboxBounds) {
        this.hitboxBounds = hitboxBounds;
    }

    public int getHitboxBounds() {
        return hitboxBounds;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public ImageView getImageView() {
        return imageView;
    }

    //#endregion
}
