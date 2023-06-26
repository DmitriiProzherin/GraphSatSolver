package com.sat.graphsatsolver.utils;

import javafx.geometry.Insets;
import javafx.scene.control.Label;

public class LabelFactory {
    private LabelFactory(){

    }

    public static Label createHelpLabel(String text, boolean title){
        Label label = new Label();
        label.setWrapText(true);
        label.setPrefWidth(260);
        label.setText(text);
        if (!title) {
            label.setPadding(new Insets(3.5));
        }
        else {
            label.setStyle("-fx-font-weight: bold;");
        }
        return label;
    }
}
