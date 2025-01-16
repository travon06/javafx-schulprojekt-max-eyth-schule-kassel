package HUD;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import models.Item;
import utils.keyboard.Keybindings;

public class HUD {

    private Pane rootPane;
    private Label collectibleLabel;

    public HUD(Pane rootPane) {
        this.rootPane = rootPane;
        this.collectibleLabel = new Label();
        this.collectibleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 1.2em;");
        this.collectibleLabel.setVisible(false);
        collectibleLabel.layoutXProperty().bind(rootPane.widthProperty().subtract(collectibleLabel.widthProperty()).divide(2));
        collectibleLabel.layoutYProperty().bind(rootPane.heightProperty().subtract(collectibleLabel.heightProperty()).divide(2).subtract(200));
        this.rootPane.getChildren().add(this.collectibleLabel);

    } 

    public void printItemCollectable(Item nearesItem) {
        String lastNearestItemName = "";
        if(!this.collectibleLabel.getText().isBlank()) {
            lastNearestItemName = this.collectibleLabel.getText().split("'")[1];
        }
   
        if(!this.collectibleLabel.isVisible() ||!nearesItem.getName().equals(lastNearestItemName)) {
            String collectBind = Keybindings.getKeybindingValue("COLLECT_ITEM");
            this.collectibleLabel.setText(String.format("Press %s to collect '%s'", collectBind, nearesItem.getName()));
            this.collectibleLabel.setVisible(true);
        }
    }

    public void hideItemCollectable() {
        this.collectibleLabel.setVisible(false);
    }

    public void setRootPane(Pane rootPane) {
        this.rootPane = rootPane;
    }

    public Pane getRootPane() {
        return rootPane;
    }

    public void setCollectibleLabel(Label collectibleLabel) {
        this.collectibleLabel = collectibleLabel;
    }

    public Label getCollectibleLabel() {
        return collectibleLabel;
    }

}
