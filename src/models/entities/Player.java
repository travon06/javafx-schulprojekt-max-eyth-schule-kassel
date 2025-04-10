package models.entities;

import java.util.ArrayList;
import java.util.List;

import goal.Finish;
import graphics.Graphics;
import items.Item;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import models.CollisionDetection;
import models.Gate;
import models.tiles.Tile;
import utils.config.ConfigArguments;
import utils.keyboard.KeyboardListener;

public class Player {
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
    
    public Player(double normalSpeed, double sprintSpeed, int collectRange, Rectangle hitbox, int startX, int startY) {
        this.boosted = false;
        this.vissible = true;
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
        this.image.setX(startX - 25);
        this.image.setY(startY - 25);
        this.hitbox.setVisible(Boolean.parseBoolean(ConfigArguments.getConfigArgumentValue("PLAYER_SHOW_HITBOX")));
     }
    
    public List<Item> collectItem(Pane rootPane, ArrayList<Item> items, ArrayList<Item> itemsToCollect, Item nearestItem, KeyboardListener keyboardListener, Finish finish) {
        keyboardListener.setInteractPressed(false);
        rootPane.getChildren().removeAll(nearestItem.getNode(), nearestItem.getImageView());
        items.remove(nearestItem);
        if(finish.getGoal().equals("COLLECT_ITEMS")) {
            if(itemsToCollect.contains(nearestItem))  {
                itemsToCollect.remove(nearestItem);
            }
        }

        if(Boolean.parseBoolean(ConfigArguments.getConfigArgumentValue("NEAREST_ITEM_REMOVED_OUTPUT"))) {
            System.out.println(String.format("nearest Item '%s' got removed", nearestItem.toString()));
        }
    
        return items;
    }

    public void updatePlayerPosition(Rectangle playerRectangle, ArrayList<Gate> gates, List<Tile> collisionRectangles, KeyboardListener keyboardListener) {
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
            for (Tile tile : collisionRectangles) {
                if (CollisionDetection.checkCollisionRight(playerRectangle, tile.getHitbox(), tile.getIsSolid()) ||
                    CollisionDetection.checkCollisionLeft(playerRectangle, tile.getHitbox(), tile.getIsSolid())) {
                    this.setX(originalX);
                }
                if (CollisionDetection.checkCollisionBottom(playerRectangle, tile.getHitbox(), tile.getIsSolid()) ||
                    CollisionDetection.checkCollisionTop(playerRectangle, tile.getHitbox(), tile.getIsSolid())) {
                    this.setY(originalY);
                }
            }

            for (Gate gate : gates) {
                if (CollisionDetection.checkCollisionRight(playerRectangle, gate.getHitbox(), !gate.getOpen()) ||
                    CollisionDetection.checkCollisionLeft(playerRectangle, gate.getHitbox(), !gate.getOpen())) {
                    this.setX(originalX);
                }
                if (CollisionDetection.checkCollisionBottom(playerRectangle, gate.getHitbox(), !gate.getOpen()) ||
                    CollisionDetection.checkCollisionTop(playerRectangle, gate.getHitbox(), !gate.getOpen())) {
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
        this.image.setX(x - 25);
        this.hitbox.setX(x - 25);
    }

    public void setY(int y) {
        this.y = y;
        this.image.setY(y - 25);
        this.hitbox.setY(y - 25);

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
