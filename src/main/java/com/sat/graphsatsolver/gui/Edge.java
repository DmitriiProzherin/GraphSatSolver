package com.sat.graphsatsolver.gui;

import javafx.scene.shape.Line;

public class Edge extends Line {
    private Vertex from;
    private Vertex to;

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
}
