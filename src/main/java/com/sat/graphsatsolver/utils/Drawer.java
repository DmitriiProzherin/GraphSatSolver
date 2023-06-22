package com.sat.graphsatsolver.utils;

import com.sat.graphsatsolver.controllers.GraphController;
import com.sat.graphsatsolver.gui.Edge;
import com.sat.graphsatsolver.gui.Vertex;
import javafx.scene.layout.Pane;

public class Drawer {
    public static Edge edgeFromVertexToVertex(Vertex n1, Vertex n2, Pane pane){
        Edge edge = new Edge();
        edge.setFrom(n1);
        edge.setTo(n2);

        edge.setStroke(GraphController.EDGE_SELECTED_COLOR);
        edge.setStrokeWidth(3);


        edge.setStartX(n1.getCenterX());
        edge.setStartY(n1.getCenterY());
        edge.setEndX(n2.getCenterX());
        edge.setEndY(n2.getCenterY());

        pane.getChildren().add(edge);
        edge.toBack();

        return edge;
    }
}
