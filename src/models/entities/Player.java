package models.entities;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import models.CollisionDetection;
import models.Inventory;
import models.Item;
import utils.config.ConfigArguments;
import utils.keyboard.KeyboardListener;

public class Player {
    private final Inventory inventory;
    private double health;
    private double maxHealth;
    private double speed;
    private double sprintSpeed;
    private double x;
    private double y;
    private int collectRange;
    private Rectangle hitbox;
    private Node hitboxNode;
    
    public Player(double health, double speed, double sprintSpeed, int collectRange, Rectangle hitbox, double startX, double startY) {
        this.inventory = new Inventory(3);
        this.health = health;
        this.maxHealth = health;
        this.speed = speed;
        this.sprintSpeed = sprintSpeed;
        this.collectRange = collectRange;
        this.x = startX;
        this.y = startY;
        this.hitbox = hitbox;
        this.hitbox.setX(startX);
        this.hitbox.setY(startY);
        this.hitboxNode = this.hitbox;
    }
    
    public List<Item> collectItem(Pane rootPane, ArrayList<Item> items, Item nearestItem, KeyboardListener keyboardListener) {
        keyboardListener.setCollectItemPressed(false);
        if(this.inventory.addItem(nearestItem)) {
            rootPane.getChildren().remove(nearestItem.getNode());
            items.remove(nearestItem);
        }
        System.out.println(this.inventory);

        if(Boolean.parseBoolean(ConfigArguments.getConfigArgumentValue("NEAREST_ITEM_REMOVED_OUTPUT"))) {
            System.out.println(String.format("nearest Item '%s' got removed", nearestItem.toString()));
        }
    
        return items;
    }

    public void updatePlayerPosition(Rectangle playerRectangle, List<Rectangle> collisionRectangles, KeyboardListener keyboardListener) {
        double originalX = this.getX();
        double originalY = this.getY();
        double speed = this.getSpeed();
    
        // Bewege den Spieler
        if (keyboardListener.getRightPressed()) this.moveRight(speed);
        if (keyboardListener.getLeftPressed()) this.moveLeft(speed);
        if (keyboardListener.getDownPressed()) this.moveDown(speed);
        if (keyboardListener.getUpPressed()) this.moveUp(speed);
    
        // Aktualisiere die Position des Spieler-Rechtecks
        playerRectangle.setX(this.getX());
        playerRectangle.setY(this.getY());
    
        // Kollisionsprüfung und Rücksetzen der Position
        if(!keyboardListener.getGodMode()) {
            for (Rectangle collisionRectangle : collisionRectangles) {
                if (CollisionDetection.checkCollisionRight(playerRectangle, collisionRectangle, true) ||
                    CollisionDetection.checkCollisionLeft(playerRectangle, collisionRectangle, true)) {
                    this.setX(originalX);
                }
                if (CollisionDetection.checkCollisionBottom(playerRectangle, collisionRectangle, true) ||
                    CollisionDetection.checkCollisionTop(playerRectangle, collisionRectangle, true)) {
                    this.setY(originalY);
                }
            }

        }
    
        // Visuelle Darstellung nach Rücksetzung
        playerRectangle.setX(this.getX());
        playerRectangle.setY(this.getY());
    }
 
    // movement methods
    public void moveUp(double speed) {
        this.y -= speed;
    }
    
    public void moveDown(double speed) {
        this.y += speed;
    }
    
    public void moveLeft(double speed) {
        this.x -= speed;
    }
    
    public void moveRight(double speed) {
        this.x += speed;
    }
    
    public boolean addHealth(double amount) {
        if (amount < 0) {
            return false;
        }
        
        if (this.health + amount > maxHealth) {
            this.health = maxHealth;
        } else {
            this.health += amount;
        }
        return true;
    } 
    
    public void addSpeed(double amount) {
        this.speed += amount;
    }
    
    //#region getter & setter 
    public void setSprintSpeed(double sprintSpeed) {
        this.sprintSpeed = sprintSpeed;
    }

    public double getSprintSpeed() {
        return this.sprintSpeed;
    }
    
    // getter and setter methods
    public Inventory getInventory() {
        return this.inventory;
    }

    public double getHealth() {
        return this.health;
    }
    
    public void setHealth(double health) {
        if (!(health < 0 || health > maxHealth))  {
            this.health = health;
        } else {
            throw new IllegalArgumentException("health must be higher than 0 and must be lower than maxHealth");
        }
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setX(double newX) {
        this.x = newX;
    }

    public void setY(double newY) {
        this.y = newY;
    }

    public int getCollectRange() {
        return collectRange;
    }

    public void setCollectRange(int collectRange) {
        this.collectRange = collectRange;
    }

    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setHitboxNode(Node hitboxNode) {
        this.hitboxNode = hitboxNode;
    }

    public Node getHitboxNode() {
        return hitboxNode;
    }

    //#endregion
}
