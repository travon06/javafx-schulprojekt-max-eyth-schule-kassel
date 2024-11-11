package models.entities;

import javafx.scene.shape.Rectangle;
import models.Inventory;

public class Player {
    private final Inventory inventory;
    private double health;
    private double maxHealth;
    private double speed;
    private double sprintSpeed;
    private double x;
    private double y;
    
    public Player(double health, double speed, double sprintSpeed, double startX, double startY) {
        this.inventory = new Inventory(50);
        this.health = health;
        this.maxHealth = health;
        this.speed = speed;
        this.sprintSpeed = sprintSpeed;
        this.x = startX;
        this.y = startY;
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
}
