package models;

import java.util.ArrayList;
import items.Item;

public class Inventory {
    private final int size;
    private final ArrayList<Item> items;

    public Inventory(int size) {
        this.size = size;
        this.items = new ArrayList<>();
    }

    public int getSize() {
        return this.size;
    }

    public Boolean addItem(Item item) {
        if(this.size > this.items.size()) {
            this.items.add(item);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        String returnString = String.format("Inventory{size=%d;items=[", this.size);
        for(int i = 0; i < this.items.size(); i++) {
            returnString += items.get(i).getName();

            if(this.items.size() - 1 > i) {
                returnString += ",";
            }  
        }

        returnString += "]}";

        return returnString;
    }


}
