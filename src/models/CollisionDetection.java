package models;

import javafx.scene.shape.Rectangle;
import utils.config.ConfigArguments;

public class CollisionDetection {

    // Prüft, ob zwei Bereiche überlappen (allgemeine Hilfsmethode)
    private static boolean isOverlapping(double min1, double max1, double min2, double max2) {
        return max1 > min2 && min1 < max2;
    }

    // Allgemeine Methode zur Kollisionsprüfung je nach Seite
    public static boolean checkCollision(String side, Rectangle player, Rectangle block, boolean blockIsSolid) {
        if (!blockIsSolid) return false; // Nicht feste Blöcke ignorieren

        // Spieler-Positionen
        double playerLeft = player.getX();
        double playerRight = player.getX() + player.getWidth();
        double playerTop = player.getY();
        double playerBottom = player.getY() + player.getHeight();

        // Block-Positionen
        double blockLeft = block.getX();
        double blockRight = block.getX() + block.getWidth();
        double blockTop = block.getY();
        double blockBottom = block.getY() + block.getHeight();

        // Kollision prüfen
        boolean collision = false;
        switch (side.toLowerCase()) {
            case "right":
                collision = isOverlapping(playerTop, playerBottom, blockTop, blockBottom) &&
                            playerRight > blockLeft && playerLeft < blockLeft;
                break;

            case "left":
                collision = isOverlapping(playerTop, playerBottom, blockTop, blockBottom) &&
                            playerLeft < blockRight && playerRight > blockRight;
                break;

            case "bottom":
                collision = isOverlapping(playerLeft, playerRight, blockLeft, blockRight) &&
                            playerBottom > blockTop && playerTop < blockTop;
                break;

            case "top":
                collision = isOverlapping(playerLeft, playerRight, blockLeft, blockRight) &&
                            playerTop < blockBottom && playerBottom > blockBottom;
                break;
        }

        // Optional: Konsolenausgabe bei Kollision
        if (collision) {
            logCollision(side);
        }

        return collision;
    }

    // Hilfsmethode für Konsolenausgabe
    private static void logCollision(String side) {
        if (Boolean.parseBoolean(ConfigArguments.getConfigArgumentValue("CONSOLE_COLLISION_OUTPUT"))) {
            System.out.println("Collision on the " + side + " side");
        }
    }

    // Shortcut-Methoden für jede Richtung
    public static boolean checkCollisionRight(Rectangle player, Rectangle block, boolean blockIsSolid) {
        return checkCollision("right", player, block, blockIsSolid);
    }

    public static boolean checkCollisionLeft(Rectangle player, Rectangle block, boolean blockIsSolid) {
        return checkCollision("left", player, block, blockIsSolid);
    }

    public static boolean checkCollisionBottom(Rectangle player, Rectangle block, boolean blockIsSolid) {
        return checkCollision("bottom", player, block, blockIsSolid);
    }

    public static boolean checkCollisionTop(Rectangle player, Rectangle block, boolean blockIsSolid) {
        return checkCollision("top", player, block, blockIsSolid);
    }
}
