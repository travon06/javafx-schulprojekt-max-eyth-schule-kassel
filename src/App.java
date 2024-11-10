import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import models.entities.Player;
import models.tiles.Tile;
import utils.config.ConfigArguments;
import utils.config.ConfigReader;

public class App extends Application {

    private static final int SCREEN_WIDTH;
    private static final int SCREEN_HEIGHT;
    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    // Player visual representation
    private Rectangle playerRectangle1;
    private Rectangle wallRectangle1;
    private Player player1;
    private Tile wall1;
    private int counter;

    static {
        // reading data from config.txt and store them in ConfigArguments class
        ConfigReader.readConfig();
        // inserting data from ConfigArguments for aplying the screen height and width
        SCREEN_WIDTH = Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH"));
        SCREEN_HEIGHT = Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT"));
    }

    @Override
    public void start(Stage primaryStage) {
        player1 = new Player(Double.parseDouble(ConfigArguments.getConfigArgumentValue("PLAYER_HEALTH")), Double.parseDouble(ConfigArguments.getConfigArgumentValue("PLAYER_SPEED")), 0, 0);
        wall1 = new Tile(true, 50.0, 50.0);

        playerRectangle1 = new Rectangle(20, 20, Color.BLUE);
        playerRectangle1.setX(player1.getX());
        playerRectangle1.setY(player1.getY());

        wallRectangle1 = new Rectangle(20, 20, Color.RED);
        wallRectangle1.setX(wall1.getX());
        wallRectangle1.setY(wall1.getY());

        Pane pane = new Pane();
        pane.getChildren().addAll(playerRectangle1, wallRectangle1);

        Scene scene = new Scene(pane, SCREEN_WIDTH, SCREEN_HEIGHT);
        primaryStage.setTitle("2D Player Movement");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Event handling for key pressed
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W -> upPressed = true;
                case S -> downPressed = true;
                case A -> leftPressed = true;
                case D -> rightPressed = true;
                case ENTER -> primaryStage.close();
            }
        });

        // Event handling for key released
        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case W -> upPressed = false;
                case S -> downPressed = false;
                case A -> leftPressed = false;
                case D -> rightPressed = false;
            }
        });

        // Animation timer to update player position continuously
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updatePlayerPosition();
            }
        };
        timer.start();
    }
    private void updatePlayerPosition() {
        double originalX = player1.getX();
        double originalY = player1.getY();
    
        if (rightPressed) player1.moveRight();
        if (leftPressed) player1.moveLeft();
        if (downPressed) player1.moveDown();
        if (upPressed) player1.moveUp();
    
        playerRectangle1.setX(player1.getX());
        playerRectangle1.setY(player1.getY());
    
        if (checkCollisionRigth(playerRectangle1, wallRectangle1, wall1)) {
            player1.setX(originalX); 
        }
        if (checkCollisionLeft(playerRectangle1, wallRectangle1, wall1)) {
            player1.setX(originalX); 
        }
        if (checkCollisionBottom(playerRectangle1, wallRectangle1, wall1)) {
            player1.setY(originalY); 
        }
        if (checkCollisionTop(playerRectangle1, wallRectangle1, wall1)) {
            player1.setY(originalY); 
        }
    
        // Visuelle Darstellung nach Rücksetzung
        playerRectangle1.setX(player1.getX());
        playerRectangle1.setY(player1.getY());
    }
    
    private void resetOnCollision(Rectangle playerRectangle, Rectangle wallRectangle, Tile wall, int originalX, int originalY) {
        if (checkCollisionRigth(playerRectangle1, wallRectangle1, wall1)) {
            player1.setX(originalX);
        }
        if (checkCollisionLeft(playerRectangle1, wallRectangle1, wall1)) {
            player1.setX(originalX);
        }
        if (checkCollisionBottom(playerRectangle1, wallRectangle1, wall1)) {
            player1.setY(originalY);
        }
        if (checkCollisionTop(playerRectangle1, wallRectangle1, wall1)) {
            player1.setY(originalY);
        }
    }
    
    private boolean checkCollisionRigth(Rectangle player, Rectangle blockToCheck, Tile blockOfRectangle) {
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

    private boolean checkCollisionBottom(Rectangle player, Rectangle blockToCheck, Tile blockOfRectangle) {
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

    private boolean checkCollisionTop(Rectangle player, Rectangle blockToCheck, Tile blockOfRectangle) {
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

    private boolean checkCollisionLeft(Rectangle player, Rectangle blockToCheck, Tile blockOfRectangle) {
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

    public static void main(String[] args) {
        launch(args);
    }

}
