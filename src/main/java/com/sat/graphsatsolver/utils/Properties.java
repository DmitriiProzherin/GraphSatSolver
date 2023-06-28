package com.sat.graphsatsolver.utils;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public class Properties {
    public static final Rectangle2D PRIMARY_SCREEN_BOUNDS = Screen.getPrimary().getVisualBounds();
    public static final double SCREEN_WIDTH = PRIMARY_SCREEN_BOUNDS.getWidth();
    public static final double SCREEN_HEIGHT = PRIMARY_SCREEN_BOUNDS.getHeight();
    public static final double GRAPH_WINDOW_WIDTH = SCREEN_WIDTH * 0.52;
    public static final double GRAPH_WINDOW_HEIGHT = SCREEN_HEIGHT * 0.673;
    public static final double SAT_WINDOW_WIDTH = SCREEN_WIDTH * 0.23854;
    public static final double SAT_WINDOW_HEIGHT = SCREEN_HEIGHT * 0.673;
    public static final double HELP_WINDOW_WIDTH = SCREEN_WIDTH * 0.247916;
    public static final double HELP_WINDOW_HEIGHT = SCREEN_HEIGHT * 0.33846;


    // Элементы интерфейса графа
    public static final double CONTENT_BOX_HEIGHT = GRAPH_WINDOW_HEIGHT * 0.9642857;
}
