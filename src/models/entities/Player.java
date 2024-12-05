package models.entities;

import java.util.List;
import javafx.scene.shape.Rectangle;
import models.CollisionDetection;
import models.Inventory;
import models.Item;
import utils.config.ConfigArguments;
import utils.keyboard.Keyboard;

public class Player {
    private final Inventory inventory;
    private double health;
    private double maxHealth;
    private double speed;
    private double sprintSpeed;
    private double x;
    private double y;
    private int collectRange;
    
    public Player(double health, double speed, double sprintSpeed, int collectRange, double startX, double startY) {
        this.inventory = new Inventory(50);
        this.health = health;
        this.maxHealth = health;
        this.speed = speed;
        this.sprintSpeed = sprintSpeed;
        this.collectRange = collectRange;
        this.x = startX;
        this.y = startY;
    }

    public void collectItem(List<Item> items) {
        Keyboard.setCollectItemPressed(false);
        Item nearestItem = null;
        double minDistance = Double.MAX_VALUE;

        for(int i = 0; i < items.size(); i++) {
            double distance = calculateDistance(items.get(i));

            if(distance < minDistance) {
                nearestItem = items.get(i);
                minDistance = distance;
            }
            
            // if item is in range when E is Pressed
            if(calculateDistance(nearestItem) < Integer.parseInt(ConfigArguments.getConfigArgumentValue("PLAYER_COLLECT_RANGE"))) {
                if(Boolean.parseBoolean(ConfigArguments.getConfigArgumentValue("NEAREST_ITEM_IN_RANGE_OUTPUT"))) {
                    System.out.println(String.format("Item (%d | %d) is in Range", nearestItem.getX(), nearestItem.getY()));
                }

                // code for E pressed
            }

        }
        
        // ouput for showing nearest item
        if(Boolean.parseBoolean(ConfigArguments.getConfigArgumentValue("SHOW_NEAREST_ITEM_OUTPUT"))) {
            System.out.println(String.format("Nearest item: (%d | %d)", nearestItem.getX(), nearestItem.getY()));
        }
    }

    private double calculateDistance(Item item) {
        return Math.sqrt(Math.pow(this.x - item.getX(), 2) + Math.pow(this.y - item.getY(), 2));
    }

    public void updatePlayerPosition(Rectangle playerRectangle, List<Rectangle> collisionRectangles) {
        double originalX = this.getX();
        double originalY = this.getY();
        double speed;
        
        // Bestimme die Bewegungsgeschwindigkeit
        if (Keyboard.getShiftPressed()) {
            speed = this.getSprintSpeed();
        } else {
            speed = this.getSpeed();
        }
    
        // Bewege den Spieler
        if (Keyboard.getRightPressed()) this.moveRight(speed);
        if (Keyboard.getLeftPressed()) this.moveLeft(speed);
        if (Keyboard.getDownPressed()) this.moveDown(speed);
        if (Keyboard.getUpPressed()) this.moveUp(speed);
    
        // Aktualisiere die Position des Spieler-Rechtecks
        playerRectangle.setX(this.getX());
        playerRectangle.setY(this.getY());
    
        // Kollisionsprüfung und Rücksetzen der Position
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
    
        // Visuelle Darstellung nach Rücksetzung
        playerRectangle.setX(this.getX());
        playerRectangle.setY(this.getY());
    }
    

    public void setSprintSpeed(double sprintSpeed) {
        this.sprintSpeed = sprintSpeed;
    }

    public double getSprintSpeed() {
        return this.sprintSpeed;
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
}
