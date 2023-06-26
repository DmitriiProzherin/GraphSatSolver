package com.sat.graphsatsolver.gui;

import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;

import static com.sat.graphsatsolver.utils.ColorScheme.*;
import static com.sat.graphsatsolver.utils.Style.*;

public class Vertex extends StackPane {

    private final Circle circle;
    private final Label label;

    private final static double CIRCLE_RADIUS = 30;
    public final static double NODE_RADIUS = 32;
    private boolean selected;
    private int designation;
    private double centerX;
    private double centerY;
    private double mouseAnchorX;
    private double mouseAnchorY;
    private ArrayList<Line> startEdgeList;
    private ArrayList<Line> endEdgeList;

    public Vertex(double x, double y, int designation) {
        this.designation = designation;
        this.startEdgeList = new ArrayList<>();
        this.endEdgeList = new ArrayList<>();

        circle = new Circle();
        label = new Label();

        circle.setRadius(CIRCLE_RADIUS);
        label.setText(String.valueOf(designation));

        circle.setStyle(CIRCLE_STYLE);
        label.setStyle(LABEL_STYLE);

        circle.setMouseTransparent(true);
        label.setMouseTransparent(true);

        this.getChildren().addAll(circle, label);
        this.setLayoutX(x - CIRCLE_RADIUS);
        this.setLayoutY(y - CIRCLE_RADIUS);
        this.setCenterX(x);
        this.setCenterY(y);
    }

    public boolean toggleSelection() {
        selected = !selected;
        if (selected) {
            this.select();
        } else {
            this.unselect();
        }
        return selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void select() {
        this.selected = true;
        this.circle.setOpacity(OPACITY_ACTIVE);
        this.label.setOpacity(OPACITY_ACTIVE);
        this.circle.setStroke(VERTEX_CIRCLE_STROKE_SELECTED);
    }

    public void unselect() {
        this.selected = false;
        this.circle.setOpacity(OPACITY_DISABLED);
        this.label.setOpacity(OPACITY_DISABLED);
        this.circle.setStroke(VERTEX_CIRCLE_STROKE_DEFAULT);
    }

    public void setCenterX(double x) {
        this.centerX = x;
    }

    public void setCenterY(double y) {
        this.centerY = y;
    }

    public double getCenterX() {
        return this.centerX;
    }


    public void setStartEdge(Edge edge) {
        this.startEdgeList.add(edge);
    }

    public void setEndEdgeList(Edge edge) {
        this.endEdgeList.add(edge);
    }

    public Circle getCircle() {
        return circle;
    }

    public double getCenterY() {
        return this.centerY;
    }


    public void resetStyle() {
        this.circle.setOpacity(OPACITY_ACTIVE);
        this.label.setOpacity(OPACITY_ACTIVE);
        this.circle.setFill(VERTEX_CIRCLE_FILL_DEFAULT);
        this.circle.setStroke(VERTEX_CIRCLE_STROKE_DEFAULT);
    }

    public void makeDraggable(boolean selected) {

        if (selected) {

            this.setOnMousePressed(event -> {

                mouseAnchorX = event.getX();
                mouseAnchorY = event.getY();

                this.setCursor(Cursor.CLOSED_HAND);
            });

            this.setOnMouseDragged(event -> {

                double x = event.getSceneX() - 183 - mouseAnchorX;
                double y = event.getSceneY() - 47 - mouseAnchorY;

                double posX;
                double posY;

                if (x < 1) posX = 1;
                else if (x > 444) posX = 444;
                else posX = x;

                if (y < 1) posY = 1;
                else if (y > 572) posY = 572;
                else posY = y;

                this.setLayoutX(posX);
                this.setLayoutY(posY);

                if (this.endEdgeList != null) {
                    this.endEdgeList.forEach(e -> {
                        e.setEndX(posX + NODE_RADIUS);
                        e.setEndY(posY + NODE_RADIUS);
                    });
                }
                if (this.startEdgeList != null) {
                    this.startEdgeList.forEach(e -> {
                        e.setStartX(posX + NODE_RADIUS);
                        e.setStartY(posY + NODE_RADIUS);
                    });
                }
            });

            this.setOnMouseReleased(e -> {

                this.setCenterX(this.getLayoutX() + NODE_RADIUS);
                this.setCenterY(this.getLayoutY() + NODE_RADIUS);

                this.setCursor(Cursor.DEFAULT);
            });
        } else {
            this.setOnMousePressed(null);
            this.setOnMouseDragged(null);
        }
    }

    public int getDesignation() {
        return designation;
    }

    public void setDesignation(int designation) {
        this.designation = designation;
    }

    public void setFill(Color color) {
        this.circle.setFill(color);
    }

    public ArrayList<Line> getStartEdgeList() {
        return startEdgeList;
    }

    public void setStartEdgeList(ArrayList<Line> startEdgeList) {
        this.startEdgeList = startEdgeList;
    }

    public ArrayList<Line> getEndEdgeList() {
        return endEdgeList;
    }

    public void setEndEdgeList(ArrayList<Line> endEdgeList) {
        this.endEdgeList = endEdgeList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vertex node = (Vertex) o;

        return designation == node.designation;
    }

    @Override
    public int hashCode() {
        return designation;
    }

    @Override
    public String toString() {
        return "Node " + designation;
    }
}
