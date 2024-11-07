package models;

public class Inventory {
    private final int size;
    private final Item[] inventory;

    public Inventory(int size) {
        this.size = size;
        this.inventory = new Item[size];
    }

    public int getSize() {
        return this.size;
    }

    public Item[] getInventory() {
        return this.inventory;
    }
}
