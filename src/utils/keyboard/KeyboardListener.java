package utils.keyboard;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import models.entities.Player;
import utils.config.ConfigArguments;


public class KeyboardListener {
    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean shiftPressed = false;
    private boolean collectItemPressed = false;
    private Scene scene;
    private Stage stage;
    private final String WALK_UP = Keybindings.getKeybindingValue("WALK_UP");
    private final String WALK_LEFT = Keybindings.getKeybindingValue("WALK_LEFT");
    private final String WALK_DOWN = Keybindings.getKeybindingValue("WALK_DOWN");
    private final String WALK_RIGHT = Keybindings.getKeybindingValue("WALK_RIGHT");
    private final String COLLECT_ITEM = Keybindings.getKeybindingValue("COLLECT_ITEM");
    
    public KeyboardListener(Stage stage, Scene scene) {
        this.scene = scene;
        this.stage = stage;
    }

    public void handleKeyboardInputs(Player player, boolean playerMovement, boolean exitOnEnter, boolean allowCollectItem) {
        this.scene.setOnKeyPressed(event -> {
            if(Boolean.parseBoolean(ConfigArguments.getConfigArgumentValue("CONSOLE_KEYBOARD_OUTPUT"))) {
                System.out.println(String.format("Key pressed: %s", event.getCode().toString()));
            }
            
            String keyPressed = event.getCode().getName();

            if (keyPressed.equalsIgnoreCase(this.WALK_UP)) {
                if (playerMovement) this.upPressed = true;
            } else if (keyPressed.equalsIgnoreCase(this.WALK_LEFT)) {
                if (playerMovement) this.leftPressed = true;
            } else if (keyPressed.equalsIgnoreCase(this.WALK_DOWN)) {
                if (playerMovement) this.downPressed = true;
            } else if (keyPressed.equalsIgnoreCase(this.WALK_RIGHT)) {
                if (playerMovement) this.rightPressed = true;
            } else if (keyPressed.equalsIgnoreCase(KeyCode.SHIFT.getName())) {
                if (playerMovement) this.shiftPressed = true;
            } else if (keyPressed.equalsIgnoreCase(KeyCode.ENTER.getName())) {
                if (exitOnEnter) this.stage.close();
            } else if (keyPressed.equalsIgnoreCase(COLLECT_ITEM)) {
                if(allowCollectItem) this.collectItemPressed = true;
            }

        });

        this.scene.setOnKeyReleased(event -> {

            String keyPressed = event.getCode().getName();
            if (keyPressed.equalsIgnoreCase(this.WALK_UP)) {
                this.upPressed = false;
            } else if (keyPressed.equalsIgnoreCase(this.WALK_LEFT)) {
                this.leftPressed = false;
            } else if (keyPressed.equalsIgnoreCase(this.WALK_DOWN)) {
                this.downPressed = false;
            } else if (keyPressed.equalsIgnoreCase(this.WALK_RIGHT)) {
                this.rightPressed = false;
            } else if (keyPressed.equalsIgnoreCase(KeyCode.SHIFT.getName())) {
                this.shiftPressed = false;
            } else if (keyPressed.equalsIgnoreCase(COLLECT_ITEM)) {
                this.collectItemPressed = false;
            }
        });

    }

    public  void setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
    }

    public  boolean getUpPressed() {
        return this.upPressed;
    }

    public  void setDownPressed(boolean downPressed) {
        this.downPressed = downPressed;
    }

    public  boolean getDownPressed() {
        return this.downPressed;
    }

    public  void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public  boolean getLeftPressed() {
        return this.leftPressed;
    }

    public  void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }

    public  boolean getRightPressed() {
        return this.rightPressed;
    }

    public  void setScene(Scene scene) {
        this.scene = scene;
    }

    public  Scene getScene() {
        return this.scene;
    }

    public  void setStage(Stage stage) {
        this.stage = stage;
    }

    public  Stage getStage() {
        return this.stage;
    }

    public  void setShiftPressed(boolean shiftPressed) {
        this.shiftPressed = shiftPressed;
    }

    public  boolean getShiftPressed() {
        return this.shiftPressed;
    }

    public  boolean getCollectItemPressed() {
        return this.collectItemPressed;
    }

    public  void setCollectItemPressed(boolean collectItem) {
        this.collectItemPressed = collectItem;
    }
    
}