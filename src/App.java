import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import models.Player;
import utils.ConfigArguments;
import utils.ConfigReader;

public class App extends Application {


    // Size of the game window
    // width 1200, height 900
    private static final int SCREEN_WIDTH;
    private static final int SCREEN_HEIGHT;
    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    // Player visual representation
    private Rectangle playerRectangle1;
    private Rectangle playerRectangle2;
    private Player player1;
    private Player player2;
    private int counter;

    static {
        ConfigReader.readConfig();
        SCREEN_WIDTH = Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH"));
        SCREEN_HEIGHT = Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT"));
    }

    @Override
    public void start(Stage primaryStage) {
        player1 = new Player(100, 1, 0, 0);
        player2 = new Player(100, 3, 50, 50);

        playerRectangle1 = new Rectangle(20, 20, Color.BLUE);
        playerRectangle1.setX(player1.getX());
        playerRectangle1.setY(player1.getY());

        playerRectangle2 = new Rectangle(20, 20, Color.RED);
        playerRectangle2.setX(player2.getX());
        playerRectangle2.setY(player2.getY());

        Pane pane = new Pane();
        pane.getChildren().addAll(playerRectangle1, playerRectangle2);

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
                if (!checkColision()) {
                    updatePlayerPosition();
                }
            }
        };
        timer.start();
    }

    private void updatePlayerPosition() {
        if (upPressed) player1.moveUp();
        if (downPressed) player1.moveDown();
        if (leftPressed) player1.moveLeft();
        if (rightPressed) player1.moveRight();

        // Update the visual representation of the player
        playerRectangle1.setX(player1.getX());
        playerRectangle1.setY(player1.getY());
    }

    private boolean checkColision() {
        if (playerRectangle1.getX() == playerRectangle2.getX() && playerRectangle1.getY() == playerRectangle2.getY()) {
            counter++;
            System.out.println("Colision detected: " + counter);
            return true;
        }

        if(Math.abs(playerRectangle1.getX() - playerRectangle2.getX()) <= 10 && Math.abs(playerRectangle1.getY() - playerRectangle2.getY()) <= 10) {
            counter++;
            System.out.println("Colision detected: " + counter);
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
