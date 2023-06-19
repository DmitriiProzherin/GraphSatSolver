package com.sat.graphsatsolver.gui;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;

public class GraphNode extends StackPane {

    private final Circle circle;
    private final Label label;

    private final String LABEL_STYLE = "-fx-font-weight: bold;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 20;";

    private final String CIRCLE_STYLE = "-fx-fill: grey;" +
            "-fx-stroke: wheat;" +
            "-fx-stroke-width: 2";

    private final double OPACITY_SELECTED = 1;
    private final double OPACITY_UNSELECTED = 0.7;
    private final double RADIUS = 30;
    private boolean selected;
    private int designation;
    private double centerX;
    private double centerY;
    private double mouseAnchorX;
    private double mouseAnchorY;
    private ArrayList<Line> startEdgeList;
    private ArrayList<Line> endEdgeList;

    public GraphNode(double x, double y, int designation) {
        this.designation = designation;
        this.startEdgeList = new ArrayList<>();
        this.endEdgeList = new ArrayList<>();

        circle = new Circle();
        label = new Label();

        circle.setRadius(RADIUS);
        label.setText(String.valueOf(designation));

        circle.setStyle(CIRCLE_STYLE);
        label.setStyle(LABEL_STYLE);

        circle.setMouseTransparent(true);
        label.setMouseTransparent(true);

        this.getChildren().addAll(circle, label);
        this.setLayoutX(x - RADIUS);
        this.setLayoutY(y - RADIUS);
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
        this.circle.setOpacity(OPACITY_SELECTED);
        this.label.setOpacity(OPACITY_SELECTED);
        this.circle.setStroke(Color.RED);
    }

    public void unselect() {
        this.selected = false;
        this.circle.setOpacity(OPACITY_UNSELECTED);
        this.label.setOpacity(OPACITY_UNSELECTED);
        this.circle.setStroke(Color.WHEAT);
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


    public void setStartEdge(Line edge) {
        this.startEdgeList.add(edge);
    }

    public void setEndEdgeList(Line edge) {
        this.endEdgeList.add(edge);
    }

    public double getCenterY() {
        return this.centerY;
    }


    public void resetStyle() {
        this.circle.setFill(Color.GREY);
    }

    public void makeDraggable(boolean selected) {

        if (selected) {
            this.setOnMousePressed(event -> {
                mouseAnchorX = event.getX();
                mouseAnchorY = event.getY();
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
                        e.setEndX(posX + RADIUS);
                        e.setEndY(posY + RADIUS);
                    });
                }
                if (this.startEdgeList != null) {
                    this.startEdgeList.forEach(e -> {
                        e.setStartX(posX + RADIUS);
                        e.setStartY(posY + RADIUS);
                    });
                }
            });

            this.setOnMouseReleased(e -> {
                this.setCenterX(this.getLayoutX() + RADIUS);
                this.setCenterY(this.getLayoutY() + RADIUS);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GraphNode node = (GraphNode) o;

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
