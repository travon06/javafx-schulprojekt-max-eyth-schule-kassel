package models.entities;

import java.util.ArrayList;
import java.util.List;

import goal.Finish;
import graphics.Graphics;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import models.CollisionDetection;
import models.Inventory;
import items.Item;
import utils.config.ConfigArguments;
import utils.keyboard.KeyboardListener;

public class Player {
    private final Inventory inventory;
    private double health;
    private double maxHealth;
    private double normalSpeed;
    private double sprintSpeed;
    private int x;
    private int y;
    private int collectRange;
    private Rectangle hitbox;
    private Node hitboxNode;
    private ImageView image;
    private double speed;
    private boolean boosted;
    private boolean vissible;
    
    public Player(double health, double normalSpeed, double sprintSpeed, int collectRange, Rectangle hitbox, int startX, int startY) {
        this.inventory = new Inventory(3);
        this.boosted = false;
        this.vissible = true;
        this.health = health;
        this.maxHealth = health;
        this.normalSpeed = normalSpeed;
        this.sprintSpeed = sprintSpeed;
        this.speed = normalSpeed;
        this.collectRange = collectRange;
        this.x = startX;
        this.y = startY;
        this.hitbox = hitbox;
        this.hitbox.setVisible(Boolean.parseBoolean(ConfigArguments.getConfigArgumentValue("PLAYER_SHOW_HITBOX")));
        this.hitbox.setX(startX);
        this.hitbox.setY(startY);
        this.hitboxNode = this.hitbox;
        this.image = new ImageView(new Image(Graphics.getGraphicUrl("playerGreen")));
        this.image.setFitHeight(Integer.parseInt(ConfigArguments.getConfigArgumentValue("PLAYER_BOUNDS")));
        this.image.setFitWidth(Integer.parseInt(ConfigArguments.getConfigArgumentValue("PLAYER_BOUNDS")));
        this.image.setPreserveRatio(true);
        this.hitbox.setVisible(Boolean.parseBoolean(ConfigArguments.getConfigArgumentValue("PLAYER_SHOW_HITBOX")));
     }
    
    public List<Item> collectItem(Pane rootPane, ArrayList<Item> items, ArrayList<Item> itemsToCollect, Item nearestItem, KeyboardListener keyboardListener, Finish finish) {
        keyboardListener.setInteractPressed(false);
        if(this.inventory.addItem(nearestItem)) {
            rootPane.getChildren().removeAll(nearestItem.getNode(), nearestItem.getImageView());
            items.remove(nearestItem);
            if(itemsToCollect.contains(nearestItem))  {
                itemsToCollect.remove(nearestItem);
            }
            System.out.println(finish.getGoal());
            if(finish.getGoal().equals("COLLECT_ITEMS")) {
                for(Item item : finish.getItemsToCollect()) {
                    if(item.getName().equals(nearestItem.getName())) {
                        System.out.println("penis");
                    }
                }
            }
        }
        System.out.println(this.inventory);

        if(Boolean.parseBoolean(ConfigArguments.getConfigArgumentValue("NEAREST_ITEM_REMOVED_OUTPUT"))) {
            System.out.println(String.format("nearest Item '%s' got removed", nearestItem.toString()));
        }
    
        return items;
    }

    public void updatePlayerPosition(Rectangle playerRectangle, List<Rectangle> collisionRectangles, KeyboardListener keyboardListener) {
        int originalX = this.getX();
        int originalY = this.getY();
        double speed = this.getSpeed();
    
        // Bewege den Spieler
        if (keyboardListener.getRightPressed()) {
            this.moveRight(speed);
            this.image.setRotate(270);

        }
        if (keyboardListener.getLeftPressed()) {
            this.moveLeft(speed);
            this.image.setRotate(90);
        }
        if (keyboardListener.getDownPressed()) {
            this.moveDown(speed);
            this.image.setRotate(0);


        }
        if (keyboardListener.getUpPressed()) {
            this.moveUp(speed);
            this.image.setRotate(180);
        } 
    
        // Aktualisiere die Position des Spieler-Rechtecks
        this.setX(this.getX());
        this.setY(this.getY());
    
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
        this.setX(this.getX());
        this.setY(this.getY());
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
        this.normalSpeed += amount;
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

    public double getNormalSpeed() {
        return this.normalSpeed;
    }

    public void setNormalSpeed(double speed) {
        this.normalSpeed = speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
        this.image.setX(x - (Integer.parseInt(ConfigArguments.getConfigArgumentValue("PLAYER_BOUNDS")) / 4));
        this.hitbox.setX(x);
    }

    public void setY(int y) {
        this.y = y;
        this.image.setY(y - (Integer.parseInt(ConfigArguments.getConfigArgumentValue("PLAYER_BOUNDS")) / 4));
        this.hitbox.setY(y);

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

    public void setImage(ImageView image) {
        this.image = image;
    }

    public ImageView getImage() {
        return image;
    }

    public void setBoosted(boolean boosted) {
        this.boosted = boosted;
    }

    public boolean getBoosted() {
        return this.boosted;
    }

    public void setVissible(boolean vissible) {
        this.vissible = vissible;
    }

    public boolean getVissible() {
        return this.vissible;
    }
    //#endregion
}
