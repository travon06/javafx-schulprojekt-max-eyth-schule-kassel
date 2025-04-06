package HUD;

import items.Item;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import language.Texts;
import models.Gate;
import utils.config.ConfigArguments;
import utils.keyboard.Keybindings;

public class HUD {

    private Pane rootPane;
    private Label messageLabel;
    private Label messageLabel2;
    private Label goalLabel;
    private Label fpsLabel;
    private Label timerLabel;

    public HUD(Pane rootPane) {
        this.fpsLabel = new Label();
        this.fpsLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 1.2em;-fx-background-radius: 8px;-fx-background-color: #2A3B5F; -fx-padding: 5px; -fx-text-fill: white;");
        this.timerLabel = new Label();
        this.timerLabel.setVisible(false);
        this.timerLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 1.2em;-fx-background-radius: 8px;-fx-background-color: #2A3B5F; -fx-padding: 5px; -fx-text-fill: white;");

        
        if(!Boolean.parseBoolean(ConfigArguments.getConfigArgumentValue("SHOW_FPS"))) {
            fpsLabel.setVisible(false);
        }
        this.rootPane = rootPane;
        this.messageLabel = new Label();
        this.messageLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 1.2em;");
        this.messageLabel.setVisible(false);
        this.messageLabel.layoutXProperty().bind(rootPane.widthProperty().subtract(messageLabel.widthProperty()).divide(2));
        this.messageLabel.layoutYProperty().bind(rootPane.heightProperty().subtract(messageLabel.heightProperty()).divide(2).subtract(200));
        this.messageLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 1.2em;-fx-background-radius: 8px;-fx-background-color: #2A3B5F; -fx-padding: 5px; -fx-text-fill: white;");

        this.messageLabel2 = new Label();
        this.messageLabel2.setStyle("-fx-font-weight: bold; -fx-font-size: 1.2em;");
        this.messageLabel2.setVisible(false);
        this.messageLabel2.layoutXProperty().bind(rootPane.widthProperty().subtract(messageLabel2.widthProperty()).divide(2));
        this.messageLabel2.layoutYProperty().bind(rootPane.heightProperty().subtract(messageLabel2.heightProperty()).divide(2).subtract(200));
        this.messageLabel2.setStyle("-fx-font-weight: bold; -fx-font-size: 1.2em;-fx-background-radius: 8px;-fx-background-color: #2A3B5F; -fx-padding: 5px; -fx-text-fill: white;");

        
        this.goalLabel = new Label();
        this.goalLabel.layoutXProperty().bind(rootPane.widthProperty().subtract(goalLabel.widthProperty()).divide(2));
        this.goalLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 1.2em;-fx-background-radius: 8px;-fx-background-color: #2A3B5F; -fx-padding: 5px; -fx-text-fill: white;");
        this.rootPane.getChildren().addAll(this.messageLabel, this.messageLabel2, this.goalLabel, this.fpsLabel, this.timerLabel);

    }
    
    public void printGateMessage(Gate gate) {

        if(gate.getOpen()) {
            return;
        };
        
        if(gate.getAccessible()) {
            this.messageLabel2.setText(String.format("%s '%s' %s", 
                                        Texts.getTextByName("hudGateAccessibleP1").getTextInLanguage(),
                                        Keybindings.getKeybindingValue("INTERACT"),
                                        Texts.getTextByName("hudGateAccessibleP2").getTextInLanguage()));
        } else {
            this.messageLabel2.setText(Texts.getTextByName("hudGateNotAccessible").getTextInLanguage());
        }
        this.messageLabel2.setVisible(true);
    }

    public void hideGateMessage() {
        this.messageLabel2.setVisible(false);
    }

    public void printItemCollectable(Item nearesItem) {
        String lastNearestItemName = "";
        if(!this.messageLabel.getText().isBlank()) {
            lastNearestItemName = this.messageLabel.getText().split("'")[1];
        }
   
        if(!this.messageLabel.isVisible() ||!nearesItem.getName().equals(lastNearestItemName)) {
            String interactBind = Keybindings.getKeybindingValue("INTERACT");
            this.messageLabel.setText(String.format("%s %s %s '%s' %s",
                Texts.getTextByName("ineractLabelCollectItemP1").getTextInLanguage(),
                interactBind, 
                Texts.getTextByName("ineractLabelCollectItemP2").getTextInLanguage(),
                nearesItem.getName(),
                Texts.getTextByName("ineractLabelCollectItemP3").getTextInLanguage()
            ));
            this.messageLabel.setVisible(true);
        }
    }

    public void hideItemCollectable() {
        this.messageLabel.setVisible(false);
    }

    public void setRootPane(Pane rootPane) {
        this.rootPane = rootPane;
    }

    public Pane getRootPane() {
        return rootPane;
    }

    public void setmessageLabel(Label messageLabel) {
        this.messageLabel = messageLabel;
    }

    public Label getmessageLabel() {
        return messageLabel;
    }

    public void setGoalLabel(Label goalLabel) {
        this.goalLabel = goalLabel;
    }

    public Label getGoalLabel() {
        return goalLabel;
    }

    public void setFpsLabel(Label fpsLabel) {
        this.fpsLabel = fpsLabel;
    }

    public Label getFpsLabel() {
        return fpsLabel;
    }

    public void setMessageLabel(Label messageLabel) {
        this.messageLabel = messageLabel;
    }

    public Label getMessageLabel() {
        return messageLabel;
    }

    public void setMessageLabel2(Label messageLabel2) {
        this.messageLabel2 = messageLabel2;
    }

    public Label getMessageLabel2() {
        return messageLabel2;
    }

    public void setTimerLabel(Label timerLabel) {
        this.timerLabel = timerLabel;
    }

    public Label getTimerLabel() {
        return timerLabel;
    }


}
