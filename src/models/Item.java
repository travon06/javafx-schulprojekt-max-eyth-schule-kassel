package models;

public class Item {
    private final String name;
    private final int size;
    private boolean isAccessible;

    public Item(String name, int size) {
        this.name = name;
        this.size = size;
        this.isAccessible = false;
    }

    public String getName() {
        return this.name;
    }

    public int getSize() {
        return this.size;
    }

    public boolean getIsAccessible() {
        return this.isAccessible;
    }

    public void setIsAccessible(boolean isAccessible) {
        this.isAccessible = isAccessible;
    }

}
