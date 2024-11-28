package utils.keyboard;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import utils.config.ConfigArguments;


public class Keyboard {
    private static boolean upPressed = false;
    private static boolean downPressed = false;
    private static boolean leftPressed = false;
    private static boolean rightPressed = false;
    private static boolean shiftPressed = false;
    private static Scene scene;
    private static Stage stage;
    private final static String walkUp = Keybindings.getKeybindingValue("WALK_UP");
    private final static String walkLeft = Keybindings.getKeybindingValue("WALK_LEFT");
    private final static String walkDown = Keybindings.getKeybindingValue("WALK_DOWN");
    private final static String walkRight = Keybindings.getKeybindingValue("WALK_RIGHT");
    

    public static void handleKeyboardInputs(boolean playerMovement, boolean exitOnEnter) {
        Keyboard.scene.setOnKeyPressed(event -> {
            if(Boolean.parseBoolean(ConfigArguments.getConfigArgumentValue("CONSOLE_KEYBOARD_OUTPUT"))) {
                System.out.println(event.getCode().toString());
            }
            String keyPressed = event.getCode().getName();

            if (keyPressed.equalsIgnoreCase(Keyboard.walkUp)) {
                if (playerMovement) Keyboard.upPressed = true;
            } else if (keyPressed.equalsIgnoreCase(Keyboard.walkLeft)) {
                if (playerMovement) Keyboard.leftPressed = true;
            } else if (keyPressed.equalsIgnoreCase(Keyboard.walkDown)) {
                if (playerMovement) Keyboard.downPressed = true;
            } else if (keyPressed.equalsIgnoreCase(Keyboard.walkRight)) {
                if (playerMovement) Keyboard.rightPressed = true;
            } else if (event.getCode() == KeyCode.SHIFT) {
                if (playerMovement) Keyboard.shiftPressed = true;
            } else if (event.getCode() == KeyCode.ENTER) {
                if (exitOnEnter) Keyboard.stage.close();
            }

        });

        Keyboard.scene.setOnKeyReleased(event -> {

            String keyPressed = event.getCode().getName();
            if (keyPressed.equalsIgnoreCase(Keyboard.walkUp)) {
                if (playerMovement) Keyboard.upPressed = false;
            } else if (keyPressed.equalsIgnoreCase(Keyboard.walkLeft)) {
                if (playerMovement) Keyboard.leftPressed = false;
            } else if (keyPressed.equalsIgnoreCase(Keyboard.walkDown)) {
                if (playerMovement) Keyboard.downPressed = false;
            } else if (keyPressed.equalsIgnoreCase(Keyboard.walkRight)) {
                if (playerMovement) Keyboard.rightPressed = false;
            } else if (event.getCode() == KeyCode.SHIFT) {
                if (playerMovement) Keyboard.shiftPressed = false;
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

    public static void setShiftPressed(boolean shiftPressed) {
        Keyboard.shiftPressed = shiftPressed;
    }

    public static boolean getShiftPressed() {
        return Keyboard.shiftPressed;
    }
    
}
