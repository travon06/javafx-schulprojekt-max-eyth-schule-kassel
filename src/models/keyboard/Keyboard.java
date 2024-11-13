package models.keyboard;

import java.security.Key;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import utils.config.ConfigArguments;

public class Keyboard {
    private static boolean upPressed = false;
    private static boolean downPressed = false;
    private static boolean leftPressed = false;
    private static boolean rightPressed = false;
    private static Scene scene;
    private static Stage stage;
    private static boolean listenPlayerMovement = false;

    public static void handleKeyboardInputs(boolean playerMovement, boolean exitOnEnter) {
        Keyboard.scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W -> {
                    if(playerMovement) Keyboard.upPressed = true;
                }
                case A -> {
                    if(playerMovement) Keyboard.leftPressed = true;
                }
                case S -> {
                    if(playerMovement) Keyboard.downPressed = true;
                }
                case D -> {
                    if(playerMovement) Keyboard.rightPressed = true;
                }
                case ENTER -> {
                    if(exitOnEnter) Keyboard.stage.close();
                }
            }

        });

        Keyboard.scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case W -> Keyboard.upPressed = false;
                case A -> Keyboard.leftPressed = false;
                case S -> Keyboard.downPressed = false;
                case D -> Keyboard.rightPressed = false;
            }
            });

    }

    public static void setUpPressed(boolean upPressed) {
        Keyboard.upPressed = upPressed;
    }

    public static boolean getUpPressed() {
        return Keyboard.upPressed;
    }

    public static void setDownPressed(boolean downPressed) {
        Keyboard.downPressed = downPressed;
    }

    public static boolean getDownPressed() {
        return Keyboard.downPressed;
    }

    public static void setLeftPressed(boolean leftPressed) {
        Keyboard.leftPressed = leftPressed;
    }

    public static boolean getLeftPressed() {
        return Keyboard.leftPressed;
    }

    public static void setRightPressed(boolean rightPressed) {
        Keyboard.rightPressed = rightPressed;
    }

    public static boolean getRightPressed() {
        return Keyboard.rightPressed;
    }

    public static void setScene(Scene scene) {
        Keyboard.scene = scene;
    }

    public static Scene getScene() {
        return Keyboard.scene;
    }

    public static void setStage(Stage stage) {
        Keyboard.stage = stage;
    }

    public static Stage getStage() {
        return Keyboard.stage;
    }

    
    
}
