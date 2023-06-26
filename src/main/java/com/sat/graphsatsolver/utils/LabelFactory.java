package com.sat.graphsatsolver.utils;

import javafx.geometry.Insets;
import javafx.scene.control.Label;

public class LabelFactory {
    private LabelFactory(){

    }

    public static Label createHelpLabel(String text, boolean title){
        Label label = new Label();
        label.setWrapText(true);
        label.setPrefWidth(270);
        label.setText(text);
        if (!title) {
            label.setPadding(new Insets(3,0,10, 0));
        }
        else {
            label.setStyle("-fx-font-weight: bold; -fx-font-size: 12");
        }
        return label;
    }
}
