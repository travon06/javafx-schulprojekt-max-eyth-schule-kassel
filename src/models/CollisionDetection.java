package models;

import javafx.scene.shape.Rectangle;
import models.tiles.Tile;
import utils.config.ConfigArguments;

public class CollisionDetection {
     public static boolean checkCollisionRigth(Rectangle player, Rectangle blockToCheck, Tile blockOfRectangle) {
        if (!blockOfRectangle.getIsSolid()) return false;
    
        double playerLeft = player.getX();
        double playerRight = player.getX() + player.getWidth();
        double playerTop = player.getY();
        double playerBottom = player.getY() + player.getHeight();
    
        double blockLeft = blockToCheck.getX();
        double blockRight = blockToCheck.getX() + blockToCheck.getWidth();
        double blockTop = blockToCheck.getY();
        double blockBottom = blockToCheck.getY() + blockToCheck.getHeight();

        if (playerRight > blockLeft && playerLeft < blockLeft &&
            playerBottom > blockTop && playerTop < blockBottom) {
            if (Boolean.parseBoolean(ConfigArguments.getConfigArgumentValue("CONSOLE_INFORMATION_OUTPUT"))) {
                System.out.println("Collision on the right side");
            }
            return true;
        }

        return false;
    }

    public static boolean checkCollisionBottom(Rectangle player, Rectangle blockToCheck, Tile blockOfRectangle) {
        if (!blockOfRectangle.getIsSolid()) return false;
    
        double playerLeft = player.getX();
        double playerRight = player.getX() + player.getWidth();
        double playerTop = player.getY();
        double playerBottom = player.getY() + player.getHeight();
    
        double blockLeft = blockToCheck.getX();
        double blockRight = blockToCheck.getX() + blockToCheck.getWidth();
        double blockTop = blockToCheck.getY() ;
        double blockBottom = blockToCheck.getY() + blockToCheck.getHeight();

        if (playerBottom > blockTop && playerTop < blockTop &&
            playerRight > blockLeft && playerLeft < blockRight) {
                if (Boolean.parseBoolean(ConfigArguments.getConfigArgumentValue("CONSOLE_INFORMATION_OUTPUT"))) {
                    System.out.println("Collision on the bottom side");
                }
            return true;
        }
        return false;
    }

    public static boolean checkCollisionTop(Rectangle player, Rectangle blockToCheck, Tile blockOfRectangle) {
        if (!blockOfRectangle.getIsSolid()) return false;
    
        double playerLeft = player.getX();
        double playerRight = player.getX() + player.getWidth();
        double playerTop = player.getY();
        double playerBottom = player.getY() + player.getHeight();
    
        double blockLeft = blockToCheck.getX();
        double blockRight = blockToCheck.getX() + blockToCheck.getWidth();
        double blockTop = blockToCheck.getY();
        double blockBottom = blockToCheck.getY() + blockToCheck.getHeight();

        if (playerTop < blockBottom && playerBottom > blockBottom &&
            playerRight > blockLeft && playerLeft < blockRight) {
                if (Boolean.parseBoolean(ConfigArguments.getConfigArgumentValue("CONSOLE_INFORMATION_OUTPUT"))) {
                    System.out.println("Collision on the top side");
                }
            return true;
        }
        return false;
    }

    public static boolean checkCollisionLeft(Rectangle player, Rectangle blockToCheck, Tile blockOfRectangle) {
        if (!blockOfRectangle.getIsSolid()) return false;
    
        double playerLeft = player.getX();
        double playerRight = player.getX() + player.getWidth();
        double playerTop = player.getY();
        double playerBottom = player.getY() + player.getHeight();
    
        double blockLeft = blockToCheck.getX();
        double blockRight = blockToCheck.getX() + blockToCheck.getWidth();
        double blockTop = blockToCheck.getY();
        double blockBottom = blockToCheck.getY() + blockToCheck.getHeight();

        if (playerLeft < blockRight && playerRight > blockRight &&
            playerBottom > blockTop && playerTop < blockBottom) {
                if (Boolean.parseBoolean(ConfigArguments.getConfigArgumentValue("CONSOLE_INFORMATION_OUTPUT"))) {
                    System.out.println("Collision on the left side");
                }
            return true;
        }
        return false;
    
    }
}


