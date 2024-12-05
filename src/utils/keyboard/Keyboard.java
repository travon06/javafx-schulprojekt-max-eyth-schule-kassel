package utils.keyboard;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import models.entities.Player;
import utils.config.ConfigArguments;


public class Keyboard {
    private static boolean upPressed = false;
    private static boolean downPressed = false;
    private static boolean leftPressed = false;
    private static boolean rightPressed = false;
    private static boolean shiftPressed = false;
    private static boolean collectItemPressed = false;
    private static Scene scene;
    private static Stage stage;
    private final static String WALK_UP = Keybindings.getKeybindingValue("WALK_UP");
    private final static String WALK_LEFT = Keybindings.getKeybindingValue("WALK_LEFT");
    private final static String WALK_DOWN = Keybindings.getKeybindingValue("WALK_DOWN");
    private final static String WALK_RIGHT = Keybindings.getKeybindingValue("WALK_RIGHT");
    private final static String COLLECT_ITEM = Keybindings.getKeybindingValue("COLLECT_ITEM");
    

    public static void handleKeyboardInputs(Player player, boolean playerMovement, boolean exitOnEnter, boolean allowCollectItem) {
        Keyboard.scene.setOnKeyPressed(event -> {
            if(Boolean.parseBoolean(ConfigArguments.getConfigArgumentValue("CONSOLE_KEYBOARD_OUTPUT"))) {
                System.out.println(String.format("Key pressed: %s", event.getCode().toString()));
            }
            String keyPressed = event.getCode().getName();

            if (keyPressed.equalsIgnoreCase(Keyboard.WALK_UP)) {
                if (playerMovement) Keyboard.upPressed = true;
            } else if (keyPressed.equalsIgnoreCase(Keyboard.WALK_LEFT)) {
                if (playerMovement) Keyboard.leftPressed = true;
            } else if (keyPressed.equalsIgnoreCase(Keyboard.WALK_DOWN)) {
                if (playerMovement) Keyboard.downPressed = true;
            } else if (keyPressed.equalsIgnoreCase(Keyboard.WALK_RIGHT)) {
                if (playerMovement) Keyboard.rightPressed = true;
            } else if (keyPressed.equalsIgnoreCase(KeyCode.SHIFT.getName())) {
                if (playerMovement) Keyboard.shiftPressed = true;
            } else if (keyPressed.equalsIgnoreCase(KeyCode.ENTER.getName())) {
                if (exitOnEnter) Keyboard.stage.close();
            }  else if (keyPressed.equalsIgnoreCase(COLLECT_ITEM)) {
                if(allowCollectItem) Keyboard.collectItemPressed = true;
            }

        });

        Keyboard.scene.setOnKeyReleased(event -> {

            String keyPressed = event.getCode().getName();
            if (keyPressed.equalsIgnoreCase(Keyboard.WALK_UP)) {
                Keyboard.upPressed = false;
            } else if (keyPressed.equalsIgnoreCase(Keyboard.WALK_LEFT)) {
                Keyboard.leftPressed = false;
            } else if (keyPressed.equalsIgnoreCase(Keyboard.WALK_DOWN)) {
                Keyboard.downPressed = false;
            } else if (keyPressed.equalsIgnoreCase(Keyboard.WALK_RIGHT)) {
                Keyboard.rightPressed = false;
            } else if (keyPressed.equalsIgnoreCase(KeyCode.SHIFT.getName())) {
                Keyboard.shiftPressed = false;
            } else if (keyPressed.equalsIgnoreCase(COLLECT_ITEM)) {
                Keyboard.collectItemPressed = false;
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

    public static boolean getCollectItemPressed() {
        return Keyboard.collectItemPressed;
    }

    public static void setCollectItemPressed(boolean collectItem) {
        Keyboard.collectItemPressed = collectItem;
    }
    
}
