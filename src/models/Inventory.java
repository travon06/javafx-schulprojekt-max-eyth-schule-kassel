package models;

public class Inventory {
    private final int size;
    private final Item[] items;

    public Inventory(int size) {
        this.size = size;
        this.items = new Item[size];
    }

    public int getSize() {
        return this.size;
    }

    public Item[] getInventory() {
        return this.items;
    }
}
