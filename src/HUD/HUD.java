package HUD;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import language.Texts;
import items.Item;
import utils.config.ConfigArguments;
import utils.keyboard.Keybindings;

public class HUD {

    private Pane rootPane;
    private Label collectibleLabel;
    private Label goalLabel;

    public HUD(Pane rootPane) {
        this.rootPane = rootPane;

        this.collectibleLabel = new Label();
        this.collectibleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 1.2em;");
        this.collectibleLabel.setVisible(false);
        this.collectibleLabel.layoutXProperty().bind(rootPane.widthProperty().subtract(collectibleLabel.widthProperty()).divide(2));
        this.collectibleLabel.layoutYProperty().bind(rootPane.heightProperty().subtract(collectibleLabel.heightProperty()).divide(2).subtract(200));
        
        this.goalLabel = new Label();
        this.goalLabel.layoutXProperty().bind(rootPane.widthProperty().subtract(goalLabel.widthProperty()).divide(2));
        this.goalLabel.layoutYProperty().bind(rootPane.heightProperty().subtract(goalLabel.heightProperty()).divide(2).subtract(300));
        this.goalLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 1.2em;");
        this.rootPane.getChildren().addAll(this.collectibleLabel, this.goalLabel);

    } 

    public void printItemCollectable(Item nearesItem) {
        String lastNearestItemName = "";
        if(!this.collectibleLabel.getText().isBlank()) {
            lastNearestItemName = this.collectibleLabel.getText().split("'")[1];
        }
   
        if(!this.collectibleLabel.isVisible() ||!nearesItem.getName().equals(lastNearestItemName)) {
            String interactBind = Keybindings.getKeybindingValue("INTERACT");
            this.collectibleLabel.setText(String.format("%s %s %s '%s' %s",
                Texts.getTextByName("ineractLabelCollectItemP1").getTextInLanguage(),
                interactBind, 
                Texts.getTextByName("ineractLabelCollectItemP2").getTextInLanguage(),
                nearesItem.getName(),
                Texts.getTextByName("ineractLabelCollectItemP3").getTextInLanguage()
            ));
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

    public void setGoalLabel(Label goalLabel) {
        this.goalLabel = goalLabel;
    }

    public Label getGoalLabel() {
        return goalLabel;
    }

}
