package models;

public class Player {
    private final Inventory inventory;
    private int health;
    private int maxHealth;
    private int speed;

    public Player(int health, int speed) {
        this.inventory = new Inventory(50);
        this.health = health;
        this.maxHealth = health;
        this.speed = speed;
    }

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

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void addSpeed(int amount) {
        this.speed += amount;
    }
}
