package com.sat.graphsatsolver.gui;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GraphDrawingPane extends Pane {
    List<GraphNode> selectedNodes;

    List<GraphNode> nodesList;

    private int nodeCounter;

    GraphDrawingPane(){
        selectedNodes = new LinkedList<>();
        nodesList = new LinkedList<>();
        nodeCounter = 0;
    }

    public void addNode(GraphNode node){
        nodeCounter++;
        node.toBack();
        nodesList.add(node);
        this.getChildren().add(node);
    }

    public int getNodeCounter() {
        return nodeCounter;
    }

    public void setNodeCounter(int nodeCounter) {
        this.nodeCounter = nodeCounter;
    }
}
