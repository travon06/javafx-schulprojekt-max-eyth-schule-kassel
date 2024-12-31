package models.entities;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import utils.Waypoint;

public class Policeman {
    private int health;
    private double speed;
    private Rectangle hitbox;
    private int visionRange;
    private Node node;
    private double x;
    private double y;
    private ArrayList<Waypoint> waypoints;
    private int currentWaypointIndex;


    public Policeman(double speed, int health, Rectangle hitbox, int visionRange, int startX, int startY) {
        this.speed = speed;
        this.health = health;
        this.hitbox = hitbox;
        this.node = hitbox;
        this.visionRange = visionRange;
        this.x = startX;
        this.hitbox.setY(startX);
        this.hitbox.setX(startY);;
        this.currentWaypointIndex = 0;
        this.waypoints = new ArrayList<>();


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
    }

    public void moveDown() {
        setY(this.y + speed);
    }

    public void moveLeft() {
        setX(this.x - speed);
    }

    public void moveUp() {
        setY(this.y - speed);
    }

    //#region getter & setter
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

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

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public void setX(double x) {
        this.x = x;
        this.hitbox.setX(x);
    }

    public double getX() {
        return x;
    }

    public void setY(double y) {
        this.y = y;
        this.hitbox.setY(y);
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

    //#endregion
}
