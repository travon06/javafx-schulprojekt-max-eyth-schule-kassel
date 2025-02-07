package models.Screens;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utils.config.ConfigArguments;

public class Options {
    private Stage stage;
    private Scene scene;
    private Pane rootPane;
    private Button keyBindsButton;
    
    public Options(Stage stage) {
        this.keyBindsButton = new Button();
        this.rootPane = new Pane();
        this.stage = stage;
        this.scene = new Scene(
            this.rootPane, 
            Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_WIDTH")), 
            Integer.parseInt(ConfigArguments.getConfigArgumentValue("SCREEN_HEIGHT"))
        );

        this.rootPane.getChildren().addAll(keyBindsButton);
        this.stage.setScene(this.scene);
        this.stage.setTitle("§§§§§§§§§§§§");
        this.stage.show();
    }
    
}
