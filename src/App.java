import java.util.Scanner;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import models.CollisionDetection;
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
    private boolean shiftPressed = false;

    // Player visual representation
    private Rectangle playerRectangle1;
    private Rectangle wallRectangle1;
    private Rectangle wallRectangle2;
    private Player player1;
    private Tile wall1;
    private Tile wall2;


    static {
        // reading data from config.txt and store them in ConfigArguments class
        ConfigReader.readConfig();
        // inserting data from ConfigArguments for aplying the screen height and width
        SCREEN_WIDTH = Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH"));
        SCREEN_HEIGHT = Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT"));
    }

    @Override
    public void start(Stage primaryStage) {
        player1 = new Player(Double.parseDouble(ConfigArguments.getConfigArgumentValue("PLAYER_HEALTH")), Double.parseDouble(ConfigArguments.getConfigArgumentValue("PLAYER_SPEED")), Double.parseDouble(ConfigArguments.getConfigArgumentValue("PLAYER_SPRINT_SPEED")), 0, 0);
        wall1 = new Tile(true, 50.0, 50.0);
        wall2 = new Tile(true, 100.0, 50.0);

        playerRectangle1 = new Rectangle(20, 20, Color.BLUE);
        playerRectangle1.setX(player1.getX());
        playerRectangle1.setY(player1.getY());

        wallRectangle1 = new Rectangle(20, 20, Color.RED);
        wallRectangle1.setX(wall1.getX());
        wallRectangle1.setY(wall1.getY());

        wallRectangle2= new Rectangle(20, 20, Color.RED);
        wallRectangle2.setX(wall1.getX());
        wallRectangle2.setY(wall1.getY());

        Pane pane = new Pane();
        pane.getChildren().addAll(playerRectangle1, wallRectangle1, wallRectangle2);

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
                case ENTER -> {
                    if(Boolean.parseBoolean(ConfigArguments.getConfigArgumentValue("EXIT_ON_ENTER"))) {
                        primaryStage.close();
                    }
                }
                case SHIFT -> shiftPressed = true;
            }
        });

        // Event handling for key released
        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case W -> upPressed = false;
                case S -> downPressed = false;
                case A -> leftPressed = false;
                case D -> rightPressed = false;
                case SHIFT -> shiftPressed = false;
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

    // update the positiona of the player 
    private void updatePlayerPosition() {
        double originalX = player1.getX();
        double originalY = player1.getY();
        double speed;
        
        if(shiftPressed) {
            speed = player1.getSprintSpeed();
        } else {
            speed = player1.getSpeed();
        }

        // if (rightPressed) player1.moveRight(speed);

        if(rightPressed) player1.moveRight(speed);
        if (leftPressed) player1.moveLeft(speed);
        if (downPressed) player1.moveDown(speed);
        if (upPressed) player1.moveUp(speed);

    
        playerRectangle1.setX(player1.getX());
        playerRectangle1.setY(player1.getY());
    
        if (CollisionDetection.checkCollisionRigth(playerRectangle1, wallRectangle1, wall1)) {
            player1.setX(originalX); 
        }
        if (CollisionDetection.checkCollisionLeft(playerRectangle1, wallRectangle1, wall1)) {
            player1.setX(originalX); 
        }
        if (CollisionDetection.checkCollisionBottom(playerRectangle1, wallRectangle1, wall1)) {
            player1.setY(originalY); 
        }
        if (CollisionDetection.checkCollisionTop(playerRectangle1, wallRectangle1, wall1)) {
            player1.setY(originalY); 
        }
    
        // Visuelle Darstellung nach Rücksetzung
        playerRectangle1.setX(player1.getX());
        playerRectangle1.setY(player1.getY());
    }
    
    public static void main(String[] args) {
        launch(args);
    }

}
