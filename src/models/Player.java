package models;

public class Player {
    private final Inventory inventory;
    private int health;
    private int maxHealth;

    public Player(int health) {
        this.inventory = new Inventory(50);
        this.health = health;
        this.maxHealth = health;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public int getHealth() {
        return this.health;
    }

    public boolean setHealth(int health) {
        if (!(health < 0 || health > maxHealth))  {
            this.health = health;
            return true;
        } else {
            return false;
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
}
