package com.sat.graphsatsolver.utils;

import com.sat.graphsatsolver.gui.GraphNode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Drawer {
    public static Line lineFromNodeToNode(GraphNode n1, GraphNode n2, Pane pane){
        Line line = new Line();

        line.setStroke(Color.GREY);
        line.setStrokeWidth(3);

        line.setStartX(n1.getCenterX());
        line.setStartY(n1.getCenterY());
        line.setEndX(n2.getCenterX());
        line.setEndY(n2.getCenterY());

        pane.getChildren().add(line);
        line.toBack();

        return line;
    }
}
