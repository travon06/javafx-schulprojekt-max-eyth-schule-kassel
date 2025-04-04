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
    private boolean sprintPressed = false;
    private boolean interactPressed = false;
    private boolean getCoordinates = false;
    private boolean godMode = false;
    private boolean escPressed = false;
    private Scene scene;
    private Stage stage;
    private final String WALK_UP = Keybindings.getKeybindingValue("WALK_UP");
    private final String WALK_LEFT = Keybindings.getKeybindingValue("WALK_LEFT");
    private final String WALK_DOWN = Keybindings.getKeybindingValue("WALK_DOWN");
    private final String WALK_RIGHT = Keybindings.getKeybindingValue("WALK_RIGHT");
    private final String INTERACT = Keybindings.getKeybindingValue("INTERACT");
    private final String GET_COORDINATES = Keybindings.getKeybindingValue("GET_COORDIANTES");
    private final String TOGGLE_GOD_MODE = Keybindings.getKeybindingValue("TOGGLE_GOD_MODE");  
    private final String SPRINT = Keybindings.getKeybindingValue("SPRINT");
    
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
            } else if (keyPressed.equalsIgnoreCase(this.SPRINT)) {
                if (playerMovement) this.sprintPressed = true;
            } else if (keyPressed.equalsIgnoreCase(KeyCode.ENTER.getName())) {
                if (exitOnEnter) this.stage.close();
            } else if (keyPressed.equalsIgnoreCase(INTERACT)) {
                if(allowCollectItem) this.interactPressed = true;
            } else if (keyPressed.equalsIgnoreCase(this.GET_COORDINATES)) {
                if(!Boolean.parseBoolean(ConfigArguments.getConfigArgumentValue("DEVELOPMENT_MODE"))) {
                    this.getCoordinates = true;
                }
            } else if (keyPressed.equalsIgnoreCase(this.TOGGLE_GOD_MODE)) {
                if(!Boolean.parseBoolean(ConfigArguments.getConfigArgumentValue("DEVELOPMENT_MODE"))) {
                    this.godMode = !this.godMode;
                }
            } else if (event.getCode() == (KeyCode.ESCAPE)) {
                this.escPressed = true;
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
            } else if (keyPressed.equalsIgnoreCase(this.SPRINT)) {
                this.sprintPressed = false;
            } else if (keyPressed.equalsIgnoreCase(this.INTERACT)) {
                this.interactPressed = false;
            } else if (keyPressed.equalsIgnoreCase(this.GET_COORDINATES)) {
                this.getCoordinates = false;
            }
        });

    }

    public boolean getEscPressed() {
        return this.escPressed;
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

    public  void setSprinttPressed(boolean shiftPressed) {
        this.sprintPressed = shiftPressed;
    }

    public  boolean getSprintPressed() {
        return this.sprintPressed;
    }

    public  boolean getInteractPressed() {
        return this.interactPressed;
    }

    public  void setInteractPressed(boolean collectItem) {
        this.interactPressed = collectItem;
    }

    public void setGetCoordinates(boolean getCoordinates) {
        this.getCoordinates = getCoordinates;
    }

    public boolean getGetCoordinates() {
        return this.getCoordinates;
    }
    
    public void setGodMode(boolean godMode) {
        this.godMode = godMode;
    }

    public boolean getGodMode() {
        return this.godMode;
    }
}
