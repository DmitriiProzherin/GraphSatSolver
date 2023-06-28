package com.sat.graphsatsolver.utils;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public class Properties {
    private static final double K = 1.42857;
    public static final Rectangle2D PRIMARY_SCREEN_BOUNDS = Screen.getPrimary().getVisualBounds();
    public static final double SCREEN_HEIGHT = PRIMARY_SCREEN_BOUNDS.getHeight();
    public static final double GRAPH_WINDOW_HEIGHT = SCREEN_HEIGHT * 0.673;
    public static final double GRAPH_WINDOW_WIDTH = GRAPH_WINDOW_HEIGHT * K;
    public static final double SAT_WINDOW_HEIGHT = SCREEN_HEIGHT * 0.673;
    public static final double SAT_WINDOW_WIDTH = SAT_WINDOW_HEIGHT / 1.52857;
    public static final double HELP_WINDOW_HEIGHT = SCREEN_HEIGHT * 0.33846;
    public static final double HELP_WINDOW_WIDTH = HELP_WINDOW_HEIGHT * K;


    // Элементы интерфейса графа
    public static final double CONTENT_BOX_HEIGHT = GRAPH_WINDOW_HEIGHT * 0.9642857;

    public static final double DRAWING_PANE_WIDTH = GRAPH_WINDOW_WIDTH * 0.507;
    public static final double DRAWING_PANE_HEIGHT = CONTENT_BOX_HEIGHT * 0.94;
}
