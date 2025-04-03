package models.Screens.MapMaker;

import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.entities.Policeman;

public class PolicemenSpeedAdjust {

    public static VBox createPolicemenSpeedAdjustVBox(ArrayList<Policeman> policemen) {
        VBox vBox = new VBox();

        for(int i = 0; i < policemen.size(); i++) {
            vBox.getChildren().addAll(createPolicemenSector(policemen.get(i), i));
        }
        

        return vBox;
    }

    private static HBox createPolicemenSector(Policeman policeman, int index) {
        HBox hBox = new HBox();
        Label policemanLabel = new Label(String.format("Policeman: %d", index));
        TextField speedTextField = new TextField();
        Button enterBtn = new Button("Enter");

        enterBtn.setOnAction(event -> {
            policeman.setSpeed(Integer.parseInt(speedTextField.getText()));
        });

        hBox.getChildren().addAll(policemanLabel, speedTextField, enterBtn);

        return hBox;
    }
}
