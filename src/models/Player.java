package models;

public class Player {
    private final Inventory inventory;
    private int health;
    private int maxHealth;
    private int speed;
    private int x;
    private int y;
    
    public Player(int health, int speed, int startX, int startY) {
        this.inventory = new Inventory(50);
        this.health = health;
        this.maxHealth = health;
        this.speed = speed;
        this.x = startX;
        this.y = startY;
    }
    
    // movement methods
    public void moveUp() {
        this.y -= this.speed;
    }
    
    public void moveDown() {
        this.y += this.speed;
    }
    
    public void moveLeft() {
        this.x -= this.speed;
    }
    
    public void moveRight() {
        this.x += this.speed;
    }
    
    public boolean addHealth(int amount) {
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
    
    public void addSpeed(int amount) {
        this.speed += amount;
    }
    
    // getter and setter methods
    public Inventory getInventory() {
        return this.inventory;
    }

    public int getHealth() {
        return this.health;
    }
    
    public void setHealth(int health) {
        if (!(health < 0 || health > maxHealth))  {
            this.health = health;
        } else {
            throw new IllegalArgumentException("health must be higher than 0 and must be lower than maxHealth");
        }
    }


    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }


    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int newX) {
        this.x = newX;
    }

    public void setY(int newY) {
        this.y = newY;
    }
}
