package com.sat.graphsatsolver.gui;

import javafx.scene.shape.Line;

import static com.sat.graphsatsolver.utils.ColorScheme.*;

public class Edge extends Line {
    private Vertex from;
    private Vertex to;

    public Edge(){
        this.setStrokeWidth(3);
        this.setStroke(EDGE_SELECTED_COLOR);
    }

    public Vertex getFrom() {
        return from;
    }

    public void setFrom(Vertex from) {
        this.from = from;
    }

    public Vertex getTo() {
        return to;
    }

    public void setTo(Vertex to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return from + " -> " + to;
    }

    public void resetStyle(){
        this.setStrokeWidth(3);
        this.setStroke(EDGE_UNSELECTED_COLOR);
    }
}
